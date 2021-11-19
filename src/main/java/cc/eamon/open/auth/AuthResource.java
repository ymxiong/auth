package cc.eamon.open.auth;

import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 6:01 下午
 **/
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthResource {

    /**
     * 资源名称
     * 用于根据资源名称自动查询资源以供鉴权：支持表达式
     * @return 资源名称
     */
    String[] value() default "";

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

    /**
     * 鉴权资源检索方式
     * 用于决定检索策略
     * @see ResourceRetrieveType
     * @return
     */
    ResourceRetrieveType[] retrieveTypes() default ResourceRetrieveType.REQUEST;

}
