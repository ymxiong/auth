package cc.eamon.open.auth;

import cc.eamon.open.auth.aop.resource.AuthCallback;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.resource.callback.DefaultAuthCallback;

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
    String value() default "";

    /**
     * 鉴权资源检索方式
     * 用于决定检索策略
     * @see ResourceRetrieveType
     * @return
     */
    ResourceRetrieveType retrieveType() default ResourceRetrieveType.REQUEST;

    /**
     * 是否保留
     * 用于整条鉴权链路使用
     *
     * @return
     */
    boolean reserve() default false;

    /**
     * 回调
     * 每个鉴权资源系统判断后的钩子函数
     *
     * @return
     */
    Class<? extends AuthCallback> callback() default DefaultAuthCallback.class;

}
