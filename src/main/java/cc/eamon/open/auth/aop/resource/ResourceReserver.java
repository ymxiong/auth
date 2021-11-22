package cc.eamon.open.auth.aop.resource;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 2:05 下午
 **/
public interface ResourceReserver {

    void reserve(String key, Object value);
}
