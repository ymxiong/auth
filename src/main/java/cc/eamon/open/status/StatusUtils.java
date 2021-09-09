package cc.eamon.open.status;

import cc.eamon.open.chain.ChainContextHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Feign;
import feign.Response;
import feign.Util;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Method;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 20:37
 **/
public class StatusUtils {

    public static String generateErrorMethodKey(String feignErrorMethod, Response response) {
        String status = ChainContextHolder.getString(StatusConstants.STATUS_KEY);
        if (!StringUtils.isEmpty(status)) {
            return generateErrorMethodKey(feignErrorMethod, status);
        }
        String exceptionContent = null;
        try {
            exceptionContent = Util.toString(response.body().asReader());
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (StringUtils.isEmpty(exceptionContent)) return null;
        exceptionContent = exceptionContent.replaceAll("\n", "").replaceAll("\t", "");

        JSONObject responseJson = (JSONObject) JSON.parse(exceptionContent);
        if (responseJson == null) return null;
        int code = Integer.parseInt(responseJson.getString(StatusConstants.STATUS_KEY));
        if (StringUtils.isEmpty(code)) {
            return null;
        }
        return generateErrorMethodKey(feignErrorMethod, "" + code);
    }

    public static String generateErrorMethodKey(Method method, String status, String message) {
        return generateErrorMethodKey(Feign.configKey(method.getDeclaringClass(), method), status);
    }

    public static String generateErrorMethodKey(String feignErrorMethod, String status) {
        return feignErrorMethod + "-" + status;
    }
}
