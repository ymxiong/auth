package cc.eamon.open.auth.aop.resolver;

import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:54:23
 */
public interface AnnotationResolver {

    Annotation getAnnotation(MethodInvocation invocation, Class<? extends Annotation> annotation);

}
