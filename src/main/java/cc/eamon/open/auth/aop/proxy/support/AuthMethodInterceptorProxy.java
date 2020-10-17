package cc.eamon.open.auth.aop.proxy.support;

import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthInterceptor;
import cc.eamon.open.auth.aop.proxy.BaseInterceptorProxy;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.auth.authenticator.Authenticator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:52:44
 */
public class AuthMethodInterceptorProxy extends BaseInterceptorProxy implements MethodInterceptor {

    public AuthMethodInterceptorProxy(Authenticator authenticator) {
        super(authenticator);
        List<BaseAnnotationMethodInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new AuthInterceptor(new SpringAnnotationResolver()));
        this.addMethodInterceptors(interceptors);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }

}

