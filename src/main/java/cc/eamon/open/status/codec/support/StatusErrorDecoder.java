package cc.eamon.open.status.codec.support;

import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import cc.eamon.open.status.StatusUtils;
import cc.eamon.open.status.codec.ErrorInstance;
import cc.eamon.open.status.codec.StatusErrorHandler;
import feign.Response;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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

    private StatusErrorHandler statusErrorHandler;

    private StatusErrorDecoder() {
    }

    public StatusErrorDecoder(StatusErrorHandler statusErrorHandler) {
        this.statusErrorHandler = statusErrorHandler;
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
        String errorDecodeDetail = this.getErrorDecodeDetail(errorMethod, response);
        logger.error(errorDecodeDetail);
        //识别异常
        StatusException statusException = new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DEFAULT_MESSAGE, errorDecodeDetail);
        try {
            return statusErrorHandler.handle(StatusUtils.generateErrorMethodKey(errorMethod, response));
        } catch (Exception e) {
            logger.error(StatusConstants.DECODE_ERROR_MESSAGE_DETAIL, e);
            return statusException;
        }
    }

    public static void addStatusMetadata(String errorKey, ErrorInstance errorInstance) {
        errorKeyToExceptionMap.put(errorKey, errorInstance);
    }

    public static Map<String, ErrorInstance> getErrorKeyToExceptionMap() {
        return errorKeyToExceptionMap;
    }

    private String getErrorDecodeDetail(String errorMethod, Response response) {
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
