package cc.eamon.open.status.codec.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import cc.eamon.open.status.codec.ErrorInstance;
import cc.eamon.open.status.codec.StatusErrorHandler;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 20:54
 **/
public class DefaultStatusErrorHandler implements StatusErrorHandler {

    @Override
    public Exception handle(String errorMethodKey) {
        ErrorInstance errorInstance = null;
        if (StringUtils.isEmpty(errorMethodKey) || (errorInstance = StatusErrorDecoder.getErrorKeyToExceptionMap().get(errorMethodKey)) == null) {
            // default
            String chainContextMessage = ChainContextHolder.getString(StatusConstants.MESSAGE_KEY);
            return new StatusException(StatusConstants.DEFAULT_CODE,
                    chainContextMessage == null ? StatusConstants.DEFAULT_MESSAGE : chainContextMessage);
        }
        Class<? extends Exception> exceptionClass = errorInstance.decodeException();
        int statusCode = errorInstance.statusCode();
        String message = errorInstance.message();
        Exception exception = null;
        String exceptionMessage = JSON.toJSONString(Status.builder().addStatus(statusCode)
                .addMessage(message)
                .map());
        try {
            exception = exceptionClass.getDeclaredConstructor(String.class).newInstance(exceptionMessage);
        } catch (Exception e) {
            // no op
        }
        if (StatusException.class.isAssignableFrom(exceptionClass)) {
            StatusException statusException = (StatusException) exception;
            statusException.setCode(statusCode);
            statusException.setMessage(message);
            return statusException;
        }
        return exception;
    }
}
