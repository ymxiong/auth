package cc.eamon.open.auth;

import cc.eamon.open.auth.advice.AuthAdvice;
import cc.eamon.open.auth.authenticator.Authenticator;

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
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

    /**
     * 自定义鉴权器Class
     * 用于注入自定义鉴权，供回调使用
     * @return
     */
    Class<? extends Authenticator> authenticatorClass() default AuthAdvice.class;

}
