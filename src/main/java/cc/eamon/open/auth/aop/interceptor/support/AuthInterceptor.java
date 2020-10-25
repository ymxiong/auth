package cc.eamon.open.auth.aop.interceptor.support;


import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:42:52
 */
public class AuthInterceptor extends BaseAnnotationMethodInterceptor {

    private Queue<Logical> logicalQueue = new ConcurrentLinkedQueue<>();

    public AuthInterceptor(MethodInterceptor methodInterceptor) {
        super(new SpringAnnotationResolver(), Auth.class, methodInterceptor);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) {
        if (!(annotation instanceof Auth)) return;
        Authenticator authenticator = AuthenticatorHolder.get();
        if (!authenticator.open()) return;
        Auth authAnnotation = (Auth) annotation;
        logicalQueue.add(Logical.AND);
        logicalQueue.addAll(Arrays.asList(authAnnotation.logical()));
    }

    @Override
    public Logical getLogical() {
        if (logicalQueue.isEmpty()) {
            return super.getLogical();
        }
        return logicalQueue.peek();
    }
}

