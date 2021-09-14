package cc.eamon.open.status.util;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

    public static String generateErrorMethodKey(Method method, String status, String message, String baseUrl) {
        String url = baseUrl;
        RequestMapping requestMapping = method.getAnnotation(RequestMapping.class);
        String[] value = requestMapping.value();
        RequestMethod[] requestMethods = requestMapping.method();
        if (value.length != 0) {
            url += ("/" + value[0]);
        }
        if (requestMethods.length != 0) {
            url += requestMethods[0].name();
        }
//        return generateErrorMethodKey(Feign.configKey(method.getDeclaringClass(), method), status);
        return generateErrorMethodKey(url, status);
    }

    public static String generateErrorMethodKey(String feignErrorMethodUrl, String status) {
        return feignErrorMethodUrl + "-" + status;
    }

    public static String getActualUrl(String url) {
        url = url.substring(7);
        url = url.substring(url.indexOf("/") + 1);
        String subUrl = ChainContextHolder.getString(StatusConstants.STATUS_CHAIN_RPC_SUB_KEY);
        return url.substring(0, url.lastIndexOf(subUrl)) + subUrl;
    }
}
