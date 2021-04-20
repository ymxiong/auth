package cc.eamon.open.auth.aop.proxy.support;

import cc.eamon.open.auth.AuthEnums;
import cc.eamon.open.auth.aop.proxy.BaseInterceptorProxy;
import cc.eamon.open.auth.authenticator.Authenticator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:52:44
 */
public class AuthMethodInterceptorProxy extends BaseInterceptorProxy implements MethodInterceptor {

    public AuthMethodInterceptorProxy(Authenticator authenticator) {
        super(authenticator);
    }

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        cc.eamon.open.auth.aop.interceptor.MethodInterceptor interceptor = null;
        for (Annotation annotation : invocation.getMethod().getAnnotations()) {
            AuthEnums authEnum = AuthEnums.getEnumByAnnotationClass(annotation);
            if (authEnum == null) continue;
            Constructor constructor = authEnum.getMethodInterceptor().getDeclaredConstructor(cc.eamon.open.auth.aop.interceptor.MethodInterceptor.class);
            constructor.setAccessible(true);
            interceptor = (cc.eamon.open.auth.aop.interceptor.MethodInterceptor) constructor.newInstance(interceptor);
        }
        this.addMethodInterceptors(interceptor);
        return super.invoke(invocation);
    }

}

