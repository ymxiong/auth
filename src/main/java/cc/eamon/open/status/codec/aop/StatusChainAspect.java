package cc.eamon.open.status.codec.aop;

import cc.eamon.open.status.codec.ErrorHandler;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.support.StaticMethodMatcherPointcutAdvisor;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * feign client method invoke handler to add method to ChainContext
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 14:27
 **/
@Aspect
@Configuration
public class StatusChainAspect extends StaticMethodMatcherPointcutAdvisor {

    public StatusChainAspect() {
        this.setAdvice(new StatusChainMethodInterceptorProxy());
    }

    @Override
    public boolean matches(Method method, Class<?> aClass) {
        return isAnnotationPresent(method, aClass);
    }

    private boolean isAnnotationPresent(Method method, Class<?> aClass) {
        if (method.isAnnotationPresent(ErrorHandler.class)) return true;
        Class<?> methodClass = aClass;
        Method m = method;
        if (methodClass == null) {
            methodClass = method.getDeclaringClass();
        }
        try {
            m = methodClass.getMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException e) {
            return false;
        }
        return m.isAnnotationPresent(ErrorHandler.class) || methodClass.isAnnotationPresent(ErrorHandler.class);
    }
}
