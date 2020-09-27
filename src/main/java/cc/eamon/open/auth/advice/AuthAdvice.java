package cc.eamon.open.auth.advice;

import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.aop.proxy.support.AopInterceptorProxy;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:53:40
 */
public class AuthAdvice extends StaticMethodMatcherPointcutAdvisor {

    public AuthAdvice() {
        this.setAdvice(new AopInterceptorProxy());
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        Method m = method;
        if (this.isAnnotationPresent(method)) {
            return true;
        } else {
            if (aClass != null) {
                try {
                    m = aClass.getMethod(m.getName(), m.getParameterTypes());
                    return this.isAnnotationPresent(m) || this.isAnnotationPresent(aClass);
                } catch (NoSuchMethodException e) {
                    return false;
                }
            }
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

