package cc.eamon.open.status.util;

import org.springframework.aop.framework.AdvisedSupport;
import org.springframework.aop.support.AopUtils;

import java.lang.reflect.Field;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/9 12:02
 **/
public class StatusAopTargetUtils {

    public static Object getTarget(Object proxy) throws Exception {
        if (!AopUtils.isAopProxy(proxy)) {
            return proxy;
        }
        if (AopUtils.isJdkDynamicProxy(proxy)) {
            return getJdkDynamicProxyTargetObject(proxy);
        } else {
            return getCglibProxyTargetObject(proxy);
        }
    }

    public static Object getCglibProxyTargetObject(Object proxy) throws Exception {
        Field f = proxy.getClass().getDeclaredField("CGLIB$CALLBACK_0");
        f.setAccessible(true);
        Object dynamicAdvisedInterceptor = f.get(proxy);

        Field advised = dynamicAdvisedInterceptor.getClass().getDeclaredField("advised");
        advised.setAccessible(true);

        Object target = ((AdvisedSupport) advised.get(dynamicAdvisedInterceptor)).getTargetSource().getTarget();
        return target;
    }


    public static Object getJdkDynamicProxyTargetObject(Object proxy) throws Exception {
        Field h = proxy.getClass().getSuperclass().getDeclaredField("h");
        h.setAccessible(true);
        Object hValue = h.get(proxy);

        Field targetField = hValue.getClass().getDeclaredField("target");
        targetField.setAccessible(true);
        Object target = targetField.get(hValue);

        return target;
    }

}
