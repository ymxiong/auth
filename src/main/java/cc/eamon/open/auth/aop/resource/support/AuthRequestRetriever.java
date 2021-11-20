package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.util.AuthUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthRequestRetriever extends AuthResourceRetrieverAdapter {

    @Override
    public boolean retrieve(String expression, HttpServletRequest request) {
        String parameter = request.getParameter(expression);
        if (parameter != null) {
            reserve(expression, parameter);
            return true;
        }
        Map<String, Object> authContextMap = AuthUtils.getAuthContextMap();
        if (authContextMap == null || authContextMap.isEmpty()) return false;
        Object value = authContextMap.get(expression);
        if (value != null) {
            reserve(expression, value);
            return true;
        }
        return false;
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.REQUEST;
    }
}
