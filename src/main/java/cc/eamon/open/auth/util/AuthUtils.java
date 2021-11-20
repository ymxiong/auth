package cc.eamon.open.auth.util;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.deserializer.model.Body;
import cc.eamon.open.chain.ChainContextHolder;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 10:56 上午
 **/
public class AuthUtils {

    public static Map<String, Object> getAuthContextMap() {
        Body body = (Body) ChainContextHolder.get(Constant.REQUEST_BODY_KEY);
        if (body == null) {
            setAuthContextMap();
            return ((Body)ChainContextHolder.get(Constant.REQUEST_BODY_KEY)).toMap();
        }
        return body.toMap();
    }

    private static void setAuthContextMap() {
        Body body = new Body(new HashMap<String, Object>());
        ChainContextHolder.put(Constant.REQUEST_BODY_KEY, body);
    }
}
