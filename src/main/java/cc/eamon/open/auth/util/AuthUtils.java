package cc.eamon.open.auth.util;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.deserializer.model.Body;
import cc.eamon.open.chain.ChainContextHolder;
import com.alibaba.fastjson.JSON;

import java.util.Collections;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 10:56 上午
 **/
public class AuthUtils {

    public static Map<String, Object> getRequestBodyMap() {
        Body body = (Body) ChainContextHolder.get(Constant.REQUEST_BODY_KEY);
        if (body == null) return Collections.emptyMap();
        return JSON.parseObject(JSON.toJSONString(body));
    }
}
