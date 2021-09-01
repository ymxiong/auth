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

import java.lang.reflect.Method;
import java.util.*;

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
        logger.error(exceptionDetail.append("ERROR_METHOD-").append(errorMethod).toString());
        //识别异常
        StatusException statusException = new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DEFAULT_MESSAGE, exceptionDetail.toString());
        try {
            // TODO 转化异常，结合定制
            return statusErrorHandler.handle(StatusUtils.generateErrorMethodKey(errorMethod, response));
//            String exceptionContent = Util.toString(response.body().asReader());
//            if (StringUtils.isEmpty(exceptionContent)) return statusException;
//            exceptionContent = exceptionContent.replaceAll("\n", "").replaceAll("\t", "");
//
//            JSONObject responseJson = (JSONObject) JSON.parse(exceptionContent);
//            if (responseJson == null) return statusException;
//            int code = Integer.parseInt(responseJson.getString(StatusConstants.STATUS_KEY));
//            String msg = responseJson.getString(StatusConstants.MESSAGE_KEY);
//            if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(msg)) {
//                statusException = new StatusException(code, msg, exceptionDetail.toString());
//            }
//            return statusException;
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

    /**
     * get feign client method error decode key
     * exp:
     *
     * @param statusString
     * @param message
     * @return
     */
    public static String decodeErrorKey(String statusString, Method method, String message) {
        StringJoiner sj = new StringJoiner("-");
        sj.add(method.toString())
                .add(statusString);
        return sj.toString();
    }
}
