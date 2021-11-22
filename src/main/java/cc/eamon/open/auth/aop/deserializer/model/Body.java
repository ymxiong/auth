package cc.eamon.open.auth.aop.deserializer.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/18 7:07 下午
 **/
public class Body<T> {

    private T t;

    private Class tClass;

    /**
     * extra map must not be null
     */
    private Map<String, Object> extra;

    public Body(T t, Class tClass) {
        this.t = t;
        this.tClass = tClass;
        this.extra = new HashMap<>();
    }

    public Body(Map<String, Object> extra) {
        this.extra = extra;
    }

    public Body(T t, Class tClass, Map<String, Object> extra) {
        this.t = t;
        this.tClass = tClass;
        this.extra = extra;
    }

    public T getBody() {
        return t;
    }

    public Class getBodyClass() {
        return tClass;
    }

    public Map<String, Object> getExtra() {
        return extra;
    }

    public Map<String, Object> toMap() {
        Map<String, Object> bodyMap = new HashMap<>();
        bodyMap.put("body", t);
        bodyMap.put("bodyClass", tClass);
        bodyMap.put("extra", extra);
        return bodyMap;
    }
}
