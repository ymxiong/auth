package cc.eamon.open.auth.advice;

import cc.eamon.open.auth.AuthEnums;
import cc.eamon.open.auth.advice.strategy.ContextValueStrategyEnums;
import cc.eamon.open.auth.aop.proxy.support.AuthMethodInterceptorProxy;
import cc.eamon.open.auth.authenticator.Authenticator;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:53:40
 */
public abstract class AuthAdvice extends StaticMethodMatcherPointcutAdvisor implements Authenticator, ApplicationContextAware {

    public static ApplicationContext applicationContext;

    public AuthAdvice() {
        this.setAdvice(new AuthMethodInterceptorProxy());
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
        return ContextValueStrategyEnums.getContextValueStrategy(valueName).getStrategy().parse(request, response, valueName);
    }

    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return request.getParameter(valueName);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) {
        AuthAdvice.applicationContext = applicationContext;
    }

    public String getSpringContextActiveProfile() {
        if (applicationContext == null) return null;
        return applicationContext.getEnvironment().getActiveProfiles()[0];
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}


