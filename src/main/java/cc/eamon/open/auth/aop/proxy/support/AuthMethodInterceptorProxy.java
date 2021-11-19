package cc.eamon.open.auth.aop.proxy.support;

import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.AuthEnums;
import cc.eamon.open.auth.advice.AuthAdvice;
import cc.eamon.open.auth.aop.interceptor.support.AuthResourceInterceptor;
import cc.eamon.open.auth.aop.proxy.BaseInterceptorProxy;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.auth.authenticator.support.DefaultAuthenticator;
import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.context.ApplicationContext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 21:52:44
 */
public class AuthMethodInterceptorProxy extends BaseInterceptorProxy implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        cc.eamon.open.auth.aop.interceptor.MethodInterceptor interceptor = null;
        boolean authPresent = false;
        boolean authResourcePresent = false;
        for (Annotation annotation : invocation.getMethod().getAnnotations()) {
            AuthEnums authEnum = AuthEnums.getEnumByAnnotationClass(annotation);
            if (authEnum == null) continue;
            if (authEnum == AuthEnums.AUTH_RESOURCE) {
                authResourcePresent = true;
                continue;
            }
            // 动态设置Authenticator，兼容之前逻辑
            if (authEnum == AuthEnums.AUTH_DEFAULT) {
                authPresent = true;
                Auth authAnnotation = (Auth) annotation;
                Class<? extends Authenticator> authenticatorClass = authAnnotation.authenticatorClass();
                if (authenticatorClass == AuthAdvice.class) {
                    this.setAuthenticator();
                } else {
                    AuthenticatorHolder.set(authenticatorClass.newInstance());
                }
            }
            Constructor constructor = authEnum.getMethodInterceptor().getDeclaredConstructor(cc.eamon.open.auth.aop.interceptor.MethodInterceptor.class);
            constructor.setAccessible(true);
            interceptor = (cc.eamon.open.auth.aop.interceptor.MethodInterceptor) constructor.newInstance(interceptor);
        }
        // make sure auth resource always last
        if (authResourcePresent) {
            interceptor = AuthResourceInterceptor.class.getDeclaredConstructor(cc.eamon.open.auth.aop.interceptor.MethodInterceptor.class).newInstance(interceptor);
        }
        this.addMethodInterceptors(interceptor);
        if (!authPresent) {
            this.setAuthenticator();
        }
        return super.invoke(invocation);
    }

    private void setAuthenticator() {
        ApplicationContext applicationContext = AuthAdvice.getApplicationContext();
        if (applicationContext == null) {
            AuthenticatorHolder.set(new DefaultAuthenticator());
        } else {
            AuthenticatorHolder.set(applicationContext.getBean(AuthAdvice.class));
        }
    }

}

