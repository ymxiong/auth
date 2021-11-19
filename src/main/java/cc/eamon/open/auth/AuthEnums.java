package cc.eamon.open.auth;

import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthExpressionInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthGroupInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthInterceptor;
import cc.eamon.open.auth.aop.interceptor.support.AuthResourceInterceptor;
import org.springframework.core.annotation.AnnotationUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-24 19:32:17
 */
public enum AuthEnums {

    AUTH_DEFAULT("AUTH_DEFAULT", Auth.class, AuthInterceptor.class),

    AUTH_GROUP("AUTH_GROUP", AuthGroup.class, AuthGroupInterceptor.class),

    AUTH_EXPRESSION("AUTH_EXPRESSION", AuthExpression.class, AuthExpressionInterceptor.class),

    AUTH_RESOURCE("AUTH_RESOURCE", AuthResource.class, AuthResourceInterceptor.class);

    private String name;

    private Class<? extends Annotation> annotationClass;

    private Class<? extends BaseAnnotationMethodInterceptor> methodInterceptor;

    AuthEnums(String name, Class<? extends Annotation> annotationClass, Class<? extends BaseAnnotationMethodInterceptor> methodInterceptor) {
        this.name = name;
        this.annotationClass = annotationClass;
        this.methodInterceptor = methodInterceptor;
    }

    public static AuthEnums getEnumByAnnotationClass(Annotation annotation) {
        for (AuthEnums value : AuthEnums.values()) {
            if (annotation.annotationType() == value.getAnnotationClass()) {
                return value;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return annotationClass;
    }

    public Class<? extends BaseAnnotationMethodInterceptor> getMethodInterceptor() {
        return methodInterceptor;
    }

    public static boolean isAnnotationPresent(Class<?> targetClazz) {
        for (AuthEnums value : AuthEnums.values()) {
            if (value.getAnnotationClass() == null) continue;
            Annotation annotation = AnnotationUtils.findAnnotation(targetClazz, value.getAnnotationClass());
            if (annotation != null) return true;
        }
        return false;
    }

    public static boolean isAnnotationPresent(Method method) {
        for (AuthEnums value : AuthEnums.values()) {
            if (value.getAnnotationClass() == null) continue;
            Annotation annotation = AnnotationUtils.findAnnotation(method, value.getAnnotationClass());
            if (annotation != null) return true;
        }
        return false;
    }

}
