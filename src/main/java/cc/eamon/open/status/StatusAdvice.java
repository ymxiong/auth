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
        Status status;
        if (e instanceof StatusException) {
            StatusException exception = (StatusException) e;
            response.setStatus(exception.getCode());
            status = Status.buildStatus(false, exception.getCode(), exception.getMessage());
        }else if (e instanceof RuntimeException){
            RuntimeException exception = (RuntimeException) e;
            response.setStatus(700);
            status = Status.buildStatus(false, 700, exception.getMessage());
        }else {
            status = Status.buildFailedStatus();
        }
        status.setPath(request.getRequestURI());
        return status.buildMap();
    }


}
