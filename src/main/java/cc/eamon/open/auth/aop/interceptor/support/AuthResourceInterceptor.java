package cc.eamon.open.auth.aop.interceptor.support;

import cc.eamon.open.auth.AuthResource;
import cc.eamon.open.auth.AuthResources;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.auth.aop.resource.AuthCallback;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.resource.ResourceRetrieverEnum;
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
        super(new SpringAnnotationResolver(), AuthResources.class, preMethodInterceptor);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) throws Exception {
        if (!(annotation instanceof AuthResources)) return;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        AuthResources authResources = (AuthResources) annotation;
        AuthResource[] resources = authResources.resources();
        if (resources == null || resources.length == 0) return;
        Logical[] logicalList = authResources.logical();

        String value = resources[0].value();
        ResourceRetrieveType resourceRetrieveType = resources[0].retrieveType();
        boolean reserve = resources[0].reserve();
        Class<? extends AuthCallback> callback = resources[0].callback();
        boolean result = ResourceRetrieverEnum.getRetriever(resourceRetrieveType).retrieve(value, request, reserve, callback.newInstance());

        if (resources.length > 1) {
            for (int i = 1; i < resources.length; i++) {
                AuthResource resource = resources[i];
                value = resource.value();
                resourceRetrieveType = resource.retrieveType();
                reserve = resource.reserve();
                callback = resource.callback();
                Logical logical = logicalList[0];
                if (logicalList.length > i - 1 && logicalList[i - 1] != null) logical = logicalList[i - 1];
                if (logical == Logical.AND) {
                    result = result && ResourceRetrieverEnum.getRetriever(resourceRetrieveType).retrieve(value, request, reserve, callback.newInstance());
                }
                if (logical == Logical.OR) {
                    result = result || ResourceRetrieverEnum.getRetriever(resourceRetrieveType).retrieve(value, request, reserve, callback.newInstance());
                }
            }
        }
        Assert.isTrue(result, "NO_AUTH");
    }
}
