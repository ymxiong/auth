package cc.eamon.open.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-09 18:09:56
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 权限名称
     * 用于根据权限名称拦截确认权限
     * @return 权限名称
     */
    String value() default "";

    /**
     * 权限集合
     * 用于分组拦截确认权限
     * @return 权限集合
     */
    String[] group() default {};

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

}
