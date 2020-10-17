package cc.eamon.open.auth.aop.resolver.support;

import cc.eamon.open.auth.aop.resolver.AnnotationResolver;
import org.aopalliance.intercept.MethodInvocation;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:54:43
 */
public class DefaultAnnotationResolver implements AnnotationResolver {

    public Annotation getAnnotation(MethodInvocation methodInvocation, Class<? extends Annotation> clazz) {
        if (methodInvocation == null) throw new IllegalArgumentException("method argument cannot be null");

        Method method = methodInvocation.getMethod();
        if (method == null)
            throw new IllegalArgumentException(MethodInvocation.class.getName() + " parameter incorrectly constructed.  getMethod() returned null");

        Annotation annotation = method.getAnnotation(clazz);
        if (annotation == null) {
            Object miThis = methodInvocation.getThis();
            annotation = miThis != null ? miThis.getClass().getAnnotation(clazz) : null;
        }
        return annotation;
    }

}
