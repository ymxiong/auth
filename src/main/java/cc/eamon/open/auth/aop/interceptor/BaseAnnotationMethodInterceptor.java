package cc.eamon.open.auth.aop.interceptor;


import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.resolver.AnnotationResolver;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.status.StatusException;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:53:53
 */
public abstract class BaseAnnotationMethodInterceptor implements MethodInterceptor {

    /**
     * annotation resolver
     * 注解解析
     */
    private AnnotationResolver resolver;

    /**
     * annotation
     * 具体注解
     */
    private Class<? extends Annotation> annotation;

    /**
     * next interceptor
     * 下一个拦截器
     */
    private MethodInterceptor preMethodInterceptor;

    public BaseAnnotationMethodInterceptor(AnnotationResolver resolver, Class<? extends Annotation> annotation, MethodInterceptor preMethodInterceptor) {
        if (annotation == null) {
            String msg = "annotation argument cannot be null";
            throw new IllegalArgumentException(msg);
        } else {
            this.annotation = annotation;
        }
        this.resolver = resolver;
        this.preMethodInterceptor = preMethodInterceptor;
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) throws Exception {
        if (preMethodInterceptor != null)
            try {
                preMethodInterceptor.assertAuthorized(methodInvocation);
            } catch (StatusException e) {
                if (getLogical() == Logical.OR) {
                    assertAuthorized(methodInvocation, this.getAnnotation(methodInvocation));
                    return;
                } else {
                    throw e;
                }
            }
        if (getLogical() == Logical.OR) return;
        assertAuthorized(methodInvocation, this.getAnnotation(methodInvocation));
    }


    @Override
    public boolean supports(MethodInvocation methodInvocation) {
        return this.getAnnotation(methodInvocation) != null;
    }

    private Annotation getAnnotation(MethodInvocation methodInvocation) {
        return this.resolver.getAnnotation(methodInvocation, annotation);
    }

    public void setResolver(AnnotationResolver resolver) {
        this.resolver = resolver;
    }

    public abstract void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) throws Exception;

    @Override
    public Logical getLogical() {
        Logical logical = null;
        if (preMethodInterceptor != null) logical = preMethodInterceptor.getLogical();
        if (logical == null) logical = Logical.AND;
        return logical;
    }

}
