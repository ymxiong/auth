package cc.eamon.open.auth.aop.interceptor;


import cc.eamon.open.auth.aop.handler.AnnotationHandler;
import cc.eamon.open.auth.aop.resolver.AnnotationResolver;
import cc.eamon.open.auth.aop.resolver.support.DefaultAnnotationResolver;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:53:53
 */
public abstract class BaseAnnotationMethodInterceptor implements MethodInterceptor {

    /**
     * annotation handler
     * 注解处理
     */
    private AnnotationHandler handler;

    /**
     * annotation resolver
     * 注解解析
     */
    private AnnotationResolver resolver;

    public BaseAnnotationMethodInterceptor(AnnotationHandler handler) {
        this(handler, new DefaultAnnotationResolver());
    }

    public BaseAnnotationMethodInterceptor(AnnotationHandler handler, AnnotationResolver resolver) {
        if (handler == null) throw new IllegalArgumentException("AnnotationHandler argument cannot be null.");
        this.handler = handler;
        this.resolver = resolver != null ? resolver : new DefaultAnnotationResolver();
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) {
        handler.assertAuthorized(methodInvocation, this.getAnnotation(methodInvocation));
    }

    public boolean supports(MethodInvocation methodInvocation) {
        return this.getAnnotation(methodInvocation) != null;
    }

    private Annotation getAnnotation(MethodInvocation methodInvocation) {
        return this.resolver.getAnnotation(methodInvocation, this.handler.getAnnotationClass());
    }

}
