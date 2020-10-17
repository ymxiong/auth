package cc.eamon.open.auth.advice;

import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.aop.proxy.support.AuthMethodInterceptorProxy;
import cc.eamon.open.auth.authenticator.Authenticator;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
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
        if (this.isAnnotationPresent(method)) return true;
        if (aClass == null) return false;
        try {
            Method matchedMethod = method;
            matchedMethod = aClass.getMethod(matchedMethod.getName(), matchedMethod.getParameterTypes());
            return this.isAnnotationPresent(matchedMethod) || this.isAnnotationPresent(aClass);
        } catch (NoSuchMethodException e) {
            return false;
        }
    }

    private boolean isAnnotationPresent(Class<?> targetClazz) {
        Annotation a = AnnotationUtils.findAnnotation(targetClazz, Auth.class);
        return a != null;
    }

    private boolean isAnnotationPresent(Method method) {
        Annotation a = AnnotationUtils.findAnnotation(method, Auth.class);
        return a != null;
    }

}


