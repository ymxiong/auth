package cc.eamon.open.auth.aop.proxy;


import cc.eamon.open.auth.aop.interceptor.AnnotationInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:57:27
 */
public abstract class BaseInterceptorProxy implements MethodInterceptor {

    private Collection<AnnotationInterceptor> methodInterceptors = new ArrayList<>();

    public BaseInterceptorProxy() {
        this.methodInterceptors.add(new AuthInterceptor());
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) {
        Collection<AnnotationInterceptor> interceptors = methodInterceptors;
        if (interceptors != null && !interceptors.isEmpty()) {
            for (AnnotationInterceptor interceptor : interceptors) {
                if (interceptor.supports(methodInvocation)) {
                    interceptor.assertAuthorized(methodInvocation);
                }
            }
        }
    }

    public Collection<AnnotationInterceptor> getMethodInterceptors() {
        return methodInterceptors;
    }

    public void setMethodInterceptors(Collection<AnnotationInterceptor> methodInterceptors) {
        this.methodInterceptors = methodInterceptors;
    }
}

