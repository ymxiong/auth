package cc.eamon.open.auth.aop.proxy.support;

import cc.eamon.open.auth.aop.interceptor.AnnotationInterceptor;
import cc.eamon.open.auth.aop.resolver.SpringAnnotationResolver;
import cc.eamon.open.auth.aop.interceptor.support.AuthInterceptor;
import cc.eamon.open.auth.aop.proxy.BaseInterceptorProxy;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:52:44
 */
public class AopInterceptorProxy extends BaseInterceptorProxy implements MethodInterceptor {

    public AopInterceptorProxy() {
        List<AnnotationInterceptor> interceptors = new ArrayList<>();
        interceptors.add(new AuthInterceptor(new SpringAnnotationResolver()));
        this.setMethodInterceptors(interceptors);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        return super.invoke(invocation);
    }

}

