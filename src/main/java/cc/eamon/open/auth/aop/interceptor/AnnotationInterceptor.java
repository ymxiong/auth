package cc.eamon.open.auth.aop.interceptor;


import cc.eamon.open.auth.aop.resolver.AnnotationResolver;
import cc.eamon.open.auth.aop.resolver.DefaultAnnotationResolver;
import cc.eamon.open.auth.aop.handler.AnnotationHandler;
import cc.eamon.open.status.StatusException;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:53:53
 */
public abstract class AnnotationInterceptor implements MethodInterceptor {

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

    public AnnotationInterceptor(AnnotationHandler handler) {
        this(handler, new DefaultAnnotationResolver());
    }

    public AnnotationInterceptor(AnnotationHandler handler, AnnotationResolver resolver) {
        if (handler == null) {
            throw new IllegalArgumentException("AnnotationHandler argument cannot be null.");
        } else {
            this.setHandler(handler);
            this.setResolver(resolver != null ? resolver : new DefaultAnnotationResolver());
        }
    }

    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        this.assertAuthorized(methodInvocation);
        return methodInvocation.proceed();
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation) throws StatusException {
        try {
            this.getHandler().assertAuthorized(this.getAnnotation(methodInvocation));
        } catch (StatusException e) {
            if (e.getCause() == null) {
                e.initCause(new RuntimeException("Not authorized to invoke method: " + methodInvocation.getMethod()));
            }
            throw e;
        }
    }

    public boolean supports(MethodInvocation methodInvocation) {
        return this.getAnnotation(methodInvocation) != null;
    }

    private Annotation getAnnotation(MethodInvocation methodInvocation) {
        return this.getResolver().getAnnotation(methodInvocation, this.getHandler().getAnnotationClass());
    }

    private AnnotationHandler getHandler() {
        return this.handler;
    }

    private void setHandler(AnnotationHandler handler) {
        this.handler = handler;
    }

    private AnnotationResolver getResolver() {
        return this.resolver;
    }

    private void setResolver(AnnotationResolver resolver) {
        this.resolver = resolver;
    }


}
