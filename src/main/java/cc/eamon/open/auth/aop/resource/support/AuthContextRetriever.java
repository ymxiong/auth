package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthContextRetriever extends AuthResourceRetrieverAdapter {

    @Override
    public boolean retrieve(String expression, HttpServletRequest request) {
        String header = request.getHeader(expression);
        if (header != null) {
            reserve(expression, header);
            return true;
        }
        Cookie[] cookies = request.getCookies();
        if (cookies == null || cookies.length == 0) return false;
        for (Cookie cookie : cookies) {
            if (cookie.getName().equalsIgnoreCase(expression)) {
                reserve(expression, cookie.getValue());
                return true;
            }
        }
        return false;
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.CONTEXT;
    }
}
