package cc.eamon.open.auth.aop.interceptor.support;


import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.resolver.AnnotationResolver;
import cc.eamon.open.auth.aop.handler.support.AuthAnnotationHandler;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:42:52
 */
public class AuthInterceptor extends BaseAnnotationMethodInterceptor {

    public AuthInterceptor(AnnotationResolver resolver) {
        super(new AuthAnnotationHandler(), resolver);
    }
}

