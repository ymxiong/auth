package cc.eamon.open.status.codec.spring;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 12:59
 **/
public class StatusErrorDecoderRegistrar {

//    public void register(Class<?> target) throws BeansException {
//        Class<?> beanClass = target;
//        if (beanClass.isAnnotationPresent(FeignClient.class)) {
//            if (beanClass.isAnnotationPresent(ErrorHandler.class)) {
//                ErrorHandler errorHandler = beanClass.getAnnotation(ErrorHandler.class);
//                this.parseErrorHandler(beanClass, errorHandler);
//            }
//            for (Method method : beanClass.getMethods()) {
//                if (method.isAnnotationPresent(ErrorHandler.class)) {
//                    this.parseErrorHandler(method, method.getAnnotation(ErrorHandler.class));
//                }
//            }
//        }
//    }
//
//    private void parseErrorHandler(Method method, ErrorHandler errorHandler) {
//        if (errorHandler == null) return;
//        ErrorInstance[] errorInstances = errorHandler.instances();
//        for (ErrorInstance errorInstance : errorInstances) {
//            this.parseErrorInstance(method, errorInstance);
//        }
//    }
//
//    private void parseErrorHandler(Class clazz, ErrorHandler errorHandler) {
//        if (errorHandler == null) return;
//        ErrorInstance[] errorInstances = errorHandler.instances();
//        for (ErrorInstance errorInstance : errorInstances) {
//            this.parseErrorInstance(clazz, errorInstance);
//        }
//    }
//
//    private void parseErrorInstance(Method method, ErrorInstance errorInstance) {
//        if (errorInstance == null) return;
//        String errorKey = StatusUtils.generateErrorMethodKey(
//                method, String.valueOf(errorInstance.statusCode()), errorInstance.message());
//        StatusErrorDecoder.addStatusMetadata(errorKey, errorInstance);
//    }
//
//    private void parseErrorInstance(Class clazz, ErrorInstance errorInstance) {
//        if (errorInstance == null) return;
//        for (Method method : clazz.getMethods()) {
//            String errorKey = StatusUtils.generateErrorMethodKey(
//                    method, String.valueOf(errorInstance.statusCode()), errorInstance.message());
//            StatusErrorDecoder.addStatusMetadata(errorKey, errorInstance);
//        }
//    }

}
