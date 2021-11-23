package cc.eamon.open.status;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
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
        this.statusExceptionChainContextHandler(response, e);
        logger.error(e.getMessage());
        Status.Builder builder;
        if (e instanceof StatusException) {
            StatusException exception = (StatusException) e;
            if (!StringUtils.isEmpty(exception.getDetail())) {
                logger.error("error detail:" + exception.getDetail());
            }
            if (exception.isSetResponseStatus() && setResponseStatus()) {
                response.setStatus(exception.getCode());
                builder = Status.failedBuilder(exception);
            } else {
                // 200，内含错误
                builder = Status.successFailedInnerBuilder(exception.toString());
            }
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

    private void statusExceptionChainContextHandler(HttpServletResponse response, Exception e) {
        String traceID = (String) ChainContextHolder.get(ChainKeyEnum.TRACE_ID);
        if (StringUtils.isEmpty(traceID)) {
            logger.error("ERROR BEFORE CHAIN PROCESS:" + "[" +
                    new StringBuilder().append("Caused by:")
                            .append(e.getCause())
                            .append("::")
                            .append("Message:")
                            .append(e.getMessage())
                            .append("]")
                            .toString()
            );
            return;
        }
        String spanID = (String) ChainContextHolder.get(ChainKeyEnum.SPAN_ID);
        String parentID = ChainContextHolder.get(ChainKeyEnum.PARENT_ID) == null ? "" : (String) ChainContextHolder.get(ChainKeyEnum.PARENT_ID);
        logger.error("SPAN-" + ChainKeyEnum.TRACE_ID.getKey() + "-" + traceID + "::"
                + ChainKeyEnum.SPAN_ID.getKey() + "-" + spanID +
                (StringUtils.isEmpty(parentID) ? "" : "::" + ChainKeyEnum.PARENT_ID.getKey() + "-" + parentID));
        response.addHeader(ChainKeyEnum.TRACE_ID.getKey(), traceID);
        response.addHeader(ChainKeyEnum.SPAN_ID.getKey(), spanID);
        if (!"".equals(parentID))
            response.addHeader(ChainKeyEnum.PARENT_ID.getKey(), parentID);
    }

    public abstract boolean setResponseStatus();

}
