package cc.eamon.open.auth.advice;

import cc.eamon.open.auth.AuthEnums;
import cc.eamon.open.auth.aop.proxy.support.AuthMethodInterceptorProxy;
import cc.eamon.open.auth.authenticator.Authenticator;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:53:40
 */
public abstract class AuthAdvice extends StaticMethodMatcherPointcutAdvisor implements Authenticator {

    public AuthAdvice() {
        this.setAdvice(new AuthMethodInterceptorProxy(this));
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return isAnnotationPresent(method, aClass);
    }

    private boolean isAnnotationPresent(Method method, Class<?> aClass) {
        if (AuthEnums.isAnnotationPresent(method)) return true;
        if (aClass == null) return false;
        try {
            Method matchedMethod = method;
            matchedMethod = aClass.getMethod(matchedMethod.getName(), matchedMethod.getParameterTypes());
            return AuthEnums.isAnnotationPresent(matchedMethod) || AuthEnums.isAnnotationPresent(aClass);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group) {
        return true;
    }

    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult) {
        return true;
    }

    @Override
    public Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        String value = request.getHeader(valueName);
        if (valueName.contains("cookie")) {
            String[] values = valueName.split("\\$");
            if (values.length < 2) return null;
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (values[1].equals(cookie.getName())) return cookie.getValue();
                }
            }
        }else if (valueName.contains("header")){
            return value;
        }
        return value;
    }

    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return request.getParameter(valueName);
    }

}


