package cc.eamon.open.auth.aop.resolver;

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
        if (methodInvocation == null) {
            throw new IllegalArgumentException("method argument cannot be null");
        } else {
            Method method = methodInvocation.getMethod();
            if (method == null) {
                String msg = MethodInvocation.class.getName() + " parameter incorrectly constructed.  getMethod() returned null";
                throw new IllegalArgumentException(msg);
            } else {
                Annotation annotation = method.getAnnotation(clazz);
                if (annotation == null) {
                    Object miThis = methodInvocation.getThis();
                    annotation = miThis != null ? miThis.getClass().getAnnotation(clazz) : null;
                }

                return annotation;
            }
        }
    }

}
