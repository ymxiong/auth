package cc.eamon.open.auth.aop.proxy;


import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:57:27
 */
public abstract class BaseInterceptorProxy implements MethodInterceptor {

    private ThreadLocal<Collection<MethodInterceptor>> methodInterceptors = null;

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        this.clearMethodInterceptors();
        AuthenticatorHolder.clear();
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) throws Exception {
        if (CollectionUtils.isEmpty(methodInterceptors.get())) return;

        for (MethodInterceptor interceptor : methodInterceptors.get()) {
            if (interceptor.supports(methodInvocation)) {
                interceptor.assertAuthorized(methodInvocation);
            }
        }

    }

    @Override
    public boolean supports(MethodInvocation methodInvocation) {
        return true;
    }

    @Override
    public Logical getLogical() {
        return Logical.AND;
    }

    protected void addMethodInterceptors(MethodInterceptor... methodInterceptors) {
        this.methodInterceptors = ThreadLocal.withInitial(ArrayList::new);
        this.methodInterceptors.get().addAll(Arrays.asList(methodInterceptors));
    }

    protected void clearMethodInterceptors() {
        this.methodInterceptors.remove();
    }

}

