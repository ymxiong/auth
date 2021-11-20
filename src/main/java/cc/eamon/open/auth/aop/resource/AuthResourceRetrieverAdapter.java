package cc.eamon.open.auth.aop.resource;

import cc.eamon.open.auth.util.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 2:08 下午
 **/
public abstract class AuthResourceRetrieverAdapter implements ResourceRetriever, ResourceReserver {

    protected boolean reserve;

    @Override
    public boolean retrieve(String expression, HttpServletRequest request, boolean reserve) {
        this.reserve = reserve;
        return retrieve(expression, request);
    }

    public abstract boolean retrieve(String expression, HttpServletRequest request);

    @SuppressWarnings("unchecked")
    @Override
    public void reserve(String key, Object value) {
        if (!this.reserve) return;
        Map<String, Object> extra = (Map<String, Object>) AuthUtils.getAuthContextMap().get("extra");
        extra.put(key, value);
    }
}
