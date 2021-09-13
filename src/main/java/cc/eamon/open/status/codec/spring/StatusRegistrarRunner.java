package cc.eamon.open.status.codec.spring;

import cc.eamon.open.status.codec.ErrorHandler;
import cc.eamon.open.status.codec.ErrorInstance;
import cc.eamon.open.status.codec.support.StatusErrorDecoder;
import cc.eamon.open.status.util.StatusAopTargetUtils;
import cc.eamon.open.status.util.StatusUtils;
import feign.Target;
import org.springframework.beans.BeansException;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 12:59
 **/
public class StatusRegistrarRunner implements ApplicationRunner, ApplicationContextAware {

    private ApplicationContext applicationContext;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        Map<String, Object> beansWithFeignClient = applicationContext.getBeansWithAnnotation(FeignClient.class);
        for (Map.Entry<String, Object> bean : beansWithFeignClient.entrySet()) {
            Object proxy = bean.getValue();
            Class<?> proxyClass = proxy.getClass();
            if (Proxy.isProxyClass(proxyClass)) {
                Target.HardCodedTarget target = (Target.HardCodedTarget) StatusAopTargetUtils.getJdkDynamicProxyTargetObject(proxy);
                String baseUrl = target.url();
                // TODO 注册元信息
                if (!StringUtils.isEmpty(baseUrl)) {
                    baseUrl = baseUrl.substring(7);
                    baseUrl = baseUrl.substring(baseUrl.indexOf("/") + 1);
                    if (proxyClass.isAnnotationPresent(ErrorHandler.class)) {
                        ErrorHandler errorHandler = proxyClass.getAnnotation(ErrorHandler.class);
                        this.parseErrorHandler(proxyClass, errorHandler, baseUrl);
                    }
                    for (Method method : target.type().getMethods()) {
                        if (method.isAnnotationPresent(RequestMapping.class) && method.isAnnotationPresent(ErrorHandler.class)) {
                            this.parseErrorHandler(method, method.getAnnotation(ErrorHandler.class), baseUrl);
                        }
                    }
                }
            }
        }
    }

    private void parseErrorHandler(Method method, ErrorHandler errorHandler, String baseUrl) {
        if (errorHandler == null) return;
        ErrorInstance[] errorInstances = errorHandler.instances();
        for (ErrorInstance errorInstance : errorInstances) {
            this.parseErrorInstance(method, errorInstance, baseUrl);
        }
    }

    private void parseErrorHandler(Class clazz, ErrorHandler errorHandler, String baseUrl) {
        if (errorHandler == null) return;
        ErrorInstance[] errorInstances = errorHandler.instances();
        for (ErrorInstance errorInstance : errorInstances) {
            this.parseErrorInstance(clazz, errorInstance, baseUrl);
        }
    }

    private void parseErrorInstance(Method method, ErrorInstance errorInstance, String baseUrl) {
        if (errorInstance == null) return;
        String errorKey = StatusUtils.generateErrorMethodKey(
                method, String.valueOf(errorInstance.statusCode()), errorInstance.message(), baseUrl);
        StatusErrorDecoder.addStatusMetadata(errorKey, errorInstance);
    }

    private void parseErrorInstance(Class clazz, ErrorInstance errorInstance, String baseUrl) {
        if (errorInstance == null) return;
        for (Method method : clazz.getMethods()) {
            String errorKey = StatusUtils.generateErrorMethodKey(
                    method, String.valueOf(errorInstance.statusCode()), errorInstance.message(), baseUrl);
            StatusErrorDecoder.addStatusMetadata(errorKey, errorInstance);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

}