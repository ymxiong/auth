package cc.eamon.open.auth.aop.proxy;


import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:57:27
 */
public abstract class BaseInterceptorProxy implements MethodInterceptor {

    private Collection<BaseAnnotationMethodInterceptor> methodInterceptors = new ArrayList<>();

    private Authenticator authenticator;

    public BaseInterceptorProxy(Authenticator authenticator) {
        this.authenticator = authenticator;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        AuthenticatorHolder.set(authenticator);
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) {
        if (CollectionUtils.isEmpty(methodInterceptors)) return;

        for (BaseAnnotationMethodInterceptor interceptor : methodInterceptors) {
            if (interceptor.supports(methodInvocation)) {
                interceptor.assertAuthorized(methodInvocation);
            }
        }

    }

    protected void addMethodInterceptors(Collection<BaseAnnotationMethodInterceptor> methodInterceptors) {
        this.methodInterceptors.addAll(methodInterceptors);
    }
}

