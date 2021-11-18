package cc.eamon.open.auth.aop.deserializer.model;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/18 7:07 下午
 **/
public class Body<T> {

    private T t;

    private Class tClass;

    public Body(T t, Class tClass) {
        this.t = t;
        this.tClass = tClass;
    }

    public T getBody() {
        return t;
    }

    public Class getBodyClass() {
        return tClass;
    }
}
