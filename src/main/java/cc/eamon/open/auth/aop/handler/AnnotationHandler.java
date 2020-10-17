package cc.eamon.open.auth.aop.handler;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:53:30
 */
public interface AnnotationHandler {

    void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation);

    Class<? extends Annotation> getAnnotationClass();

}

