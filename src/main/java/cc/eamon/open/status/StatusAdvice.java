package cc.eamon.open.status;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

public abstract class StatusAdvice {

    private static Logger logger = LoggerFactory.getLogger(StatusAdvice.class);

    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    public Map<String, Object> statusExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        logger.error(e.getMessage());
        Status.Builder builder;
        if (e instanceof StatusException) {
            StatusException exception = (StatusException) e;
            if (setResponseStatus()) response.setStatus(exception.getCode());
            builder = Status.failedBuilder(exception);
        } else if (e instanceof RuntimeException) {
            if (setResponseStatus()) response.setStatus(700);
            builder = Status.failedBuilder().addMessage("[Runtime]" + e.getMessage());
        } else {
            if (setResponseStatus()) response.setStatus(700);
            builder = Status.failedBuilder().addMessage(e.getMessage());
        }
        builder.addPath(request.getRequestURI());
        return builder.map();
    }

    public abstract boolean setResponseStatus();

}
