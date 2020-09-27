package cc.eamon.open.auth.aop.resolver;

import org.aopalliance.intercept.MethodInvocation;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-14 22:09:30
 */
public class SpringAnnotationResolver implements AnnotationResolver {

    public Annotation getAnnotation(MethodInvocation methodInvocation, Class<? extends Annotation> clazz) {
        Method method = methodInvocation.getMethod();
        Annotation annotation = AnnotationUtils.findAnnotation(method, clazz);
        if (annotation == null) {
            Class<?> targetClass = methodInvocation.getThis().getClass();
            method = ClassUtils.getMostSpecificMethod(method, targetClass);
            annotation = AnnotationUtils.findAnnotation(method, clazz);
        }
        return annotation != null ? annotation : AnnotationUtils.findAnnotation(methodInvocation.getThis().getClass(), clazz);
    }
}
