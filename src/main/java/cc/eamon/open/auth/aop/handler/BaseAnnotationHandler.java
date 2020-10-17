package cc.eamon.open.auth.aop.handler;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 00:31:25
 */
public abstract class BaseAnnotationHandler implements AnnotationHandler {

    private Class<? extends Annotation> annotationClass;

    public BaseAnnotationHandler(Class<? extends Annotation> annotationClass) {
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

}
