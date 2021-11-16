package cc.eamon.open.auth.aop.deserializer;

import cc.eamon.open.Constant;
import cc.eamon.open.chain.ChainContextHolder;
import org.springframework.core.MethodParameter;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdviceAdapter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/16 11:16 上午
 **/
@ControllerAdvice
public class AuthRequestBodyAdvice extends RequestBodyAdviceAdapter {

    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return super.beforeBodyRead(inputMessage, parameter, targetType, converterType);
    }

    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        if (!StringUtils.isEmpty(targetType.getTypeName())) {
            Class typeClass;
            try {
                typeClass = (Class) targetType;
                if (body.getClass() == typeClass || typeClass.isAssignableFrom(body.getClass())) {
                    ChainContextHolder.put(Constant.REQUEST_BODY_OBJECT_KEY, body);
                    ChainContextHolder.put(Constant.REQUEST_BODY_CLASS_KEY, typeClass);
                }
            } catch (Exception e) {
                String className = body.getClass().getName();
                if (!StringUtils.isEmpty(className) && className.equals(targetType.getTypeName())) {
                    ChainContextHolder.put(Constant.REQUEST_BODY_OBJECT_KEY, body);
                    ChainContextHolder.put(Constant.REQUEST_BODY_CLASS_KEY, body.getClass());
                }
            }
        }
        return body;
    }

    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return super.handleEmptyBody(body, inputMessage, parameter, targetType, converterType);
    }
}
