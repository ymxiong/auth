package cc.eamon.open.status.codec.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.codec.ErrorInstance;
import cc.eamon.open.status.codec.StatusErrorHandler;
import cc.eamon.open.status.codec.prehandler.DecoderPreHandle;
import cc.eamon.open.status.codec.prehandler.support.StatusErrorDecoderPreHandle;
import cc.eamon.open.status.util.StatusUtils;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/31 19:07
 **/
public class StatusErrorDecoder implements ErrorDecoder {

    private static Logger logger = LoggerFactory.getLogger(StatusErrorDecoder.class);

    private static Map<String, ErrorInstance> errorKeyToExceptionMap = new HashMap<>(64);

    private static Map<String, Method> invokeKeyToMethodMap = new HashMap<>(64);

    private StatusErrorHandler statusErrorHandler;

    private DecoderPreHandle decoderPreHandle;

    private StatusErrorDecoder() {
    }

    public StatusErrorDecoder(StatusErrorHandler statusErrorHandler) {
        this.statusErrorHandler = statusErrorHandler;
        this.decoderPreHandle = new StatusErrorDecoderPreHandle();
    }

    /**
     * 跨服务异常捕获、解析和重抛，替换默认的feign异常处理
     *
     * @param errorMethod
     * @param response
     * @return
     */
    @Override
    public Exception decode(String errorMethod, Response response) {
        this.decoderPreHandle.preHandle(response, Map.class);
        logger.error(this.getErrorDecodeDetailLog(errorMethod, response));

        return statusErrorHandler.handle(StatusUtils.generateErrorMethodKey(ChainContextHolder.getString(StatusConstants.STATUS_CHAIN_RPC_KEY), response));
    }

    public static void addStatusMetadata(String errorKey, ErrorInstance errorInstance) {
        errorKeyToExceptionMap.put(errorKey, errorInstance);
    }

    public static void addStatusMetadata(String invokeKey, Method method) {
        invokeKeyToMethodMap.put(invokeKey, method);
    }

    public static Map<String, Method> getInvokeKeyToMethodMap() {
        return invokeKeyToMethodMap;
    }

    public static Map<String, ErrorInstance> getErrorKeyToExceptionMap() {
        return errorKeyToExceptionMap;
    }

    private String getErrorDecodeDetailLog(String errorMethod, Response response) {
        StringBuilder exceptionDetail = new StringBuilder();
        Map<String, Collection<String>> headers = response.headers();
        headers.forEach((header, values) -> {
            if (ChainKeyEnum.isChainKey(header.toUpperCase())) {
                Collection<String> collection = headers.get(header);
                String value = (String) ((List) collection).get(0);
                exceptionDetail.append(header.toUpperCase())
                        .append("-")
                        .append(value)
                        .append("::");
            }
        });
        return exceptionDetail.append("ERROR_METHOD-")
                .append(errorMethod)
                .toString();
    }

}
