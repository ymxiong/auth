package cc.eamon.open.auth.aop.interceptor;


import org.aopalliance.intercept.MethodInvocation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 18:55:07
 */
public interface MethodInterceptor {

    Object invoke(MethodInvocation invocation) throws Throwable;

    void assertAuthorized(MethodInvocation methodInvocation);

}
