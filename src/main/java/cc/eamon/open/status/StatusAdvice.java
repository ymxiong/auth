package cc.eamon.open.status;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class StatusAdvice implements ErrorDecoder {

    private static Logger logger = LoggerFactory.getLogger(StatusAdvice.class);

    public static final int SERVICE_ERROR = 701;

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
            logger.error("error detail:" + exception.getDetail());
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
        logger.error("SPAN => " + ChainKeyEnum.TRACE_ID.getKey() + "-" + traceID + "::"
                + ChainKeyEnum.SPAN_ID.getKey() + "-" + spanID +
                (StringUtils.isEmpty(parentID) ? "" : "::" + ChainKeyEnum.PARENT_ID.getKey() + "-" + parentID));
        response.addHeader(ChainKeyEnum.TRACE_ID.getKey(), traceID);
        response.addHeader(ChainKeyEnum.SPAN_ID.getKey(), spanID);
        if (!"".equals(parentID))
            response.addHeader(ChainKeyEnum.PARENT_ID.getKey(), parentID);
    }

    public abstract boolean setResponseStatus();

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
        StatusException statusException = new StatusException(SERVICE_ERROR, "远程服务调用异常", exceptionDetail.toString());
        try {
            //转化异常
            String exceptionContent = Util.toString(response.body().asReader());
            if (StringUtils.isEmpty(exceptionContent)) return statusException;
            exceptionContent = exceptionContent.replaceAll("\n", "").replaceAll("\t", "");

            JSONObject responseJson = (JSONObject) JSON.parse(exceptionContent);
            if (responseJson == null) return statusException;
            int code = Integer.parseInt(responseJson.getString("status"));
            String msg = responseJson.getString("message");
            if (!StringUtils.isEmpty(code) && !StringUtils.isEmpty(msg)) {
                statusException = new StatusException(code, msg, exceptionDetail.toString());
            }
            return statusException;
        } catch (Exception e) {
            logger.error("feign处理异常错误", e);
            return statusException;
        }
    }

}
