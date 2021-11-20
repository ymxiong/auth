package cc.eamon.open.auth.aop.interceptor.support;

import cc.eamon.open.auth.AuthResource;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.resource.ResourceRetrieverEnum;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.error.Assert;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.lang.annotation.Annotation;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 6:11 下午
 **/
public class AuthResourceInterceptor extends BaseAnnotationMethodInterceptor {

    public AuthResourceInterceptor(MethodInterceptor preMethodInterceptor) {
        super(new SpringAnnotationResolver(), AuthResource.class, preMethodInterceptor);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) throws Exception {
        if (!(annotation instanceof AuthResource)) return;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AuthResource authResource = (AuthResource) annotation;
        String[] values = authResource.value();
        Logical[] logicalList = authResource.logical();
        ResourceRetrieveType[] resourceRetrieveTypes = authResource.retrieveTypes();
        boolean[] reserveList = authResource.reserve();

        if (values == null || values.length == 0) return;

        boolean result = ResourceRetrieverEnum.getRetriever(resourceRetrieveTypes[0]).retrieve(values[0], request, reserveList[0]);

        if (values.length > 1) {
            for (int i = 1; i < values.length; i++) {
                Logical logical = logicalList[0];
                ResourceRetrieveType resourceRetrieveType = resourceRetrieveTypes[0];
                boolean reserve = false;
                if (logicalList.length > i - 1 && logicalList[i - 1] != null) logical = logicalList[i - 1];
                if (resourceRetrieveTypes.length > i && resourceRetrieveTypes[i] != null)
                    resourceRetrieveType = resourceRetrieveTypes[i];
                if (reserveList.length > i)
                    reserve = reserveList[i];
                if (logical == Logical.AND)
                    result = result && ResourceRetrieverEnum.getRetriever(resourceRetrieveType).retrieve(values[i], request, reserve);
                if (logical == Logical.OR)
                    result = result || ResourceRetrieverEnum.getRetriever(resourceRetrieveType).retrieve(values[i], request, reserve);
            }
        }
        Assert.isTrue(result, "NO_AUTH");
    }
}
