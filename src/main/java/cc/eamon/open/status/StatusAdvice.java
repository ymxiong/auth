package cc.eamon.open.status;

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

    /**
     * 跨服务异常捕获、解析和重抛，替换默认的feign异常处理
     *
     * @param s
     * @param response
     * @return
     */
    @Override
    public Exception decode(String s, Response response) {
        String exceptionDetail = "the remote exception method is:" + s;
        logger.error(exceptionDetail);
        //识别异常
        StatusException statusException = new StatusException(SERVICE_ERROR, "远程服务调用异常", exceptionDetail);
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
                statusException = new StatusException(code, msg, exceptionDetail);
            }
            return statusException;
        } catch (Exception e) {
            logger.error("feign处理异常错误", e);
            return statusException;
        }
    }

}
