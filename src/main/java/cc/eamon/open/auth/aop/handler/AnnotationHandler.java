package cc.eamon.open.auth.aop.handler;

import cc.eamon.open.status.StatusException;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:53:30
 */
public abstract class AnnotationHandler {

    private Class<? extends Annotation> annotationClass;

    public AnnotationHandler(Class<? extends Annotation> annotationClass) {
        this.setAnnotationClass(annotationClass);
    }

    private void setAnnotationClass(Class<? extends Annotation> annotationClass) throws IllegalArgumentException {
        if (annotationClass == null) {
            String msg = "annotationClass argument cannot be null";
            throw new IllegalArgumentException(msg);
        } else {
            this.annotationClass = annotationClass;
        }
    }

    public Class<? extends Annotation> getAnnotationClass() {
        return this.annotationClass;
    }

    public abstract void assertAuthorized(Annotation annotation) throws StatusException;
}

