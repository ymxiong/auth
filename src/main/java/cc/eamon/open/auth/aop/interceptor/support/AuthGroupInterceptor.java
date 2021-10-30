package cc.eamon.open.auth.aop.interceptor.support;


import cc.eamon.open.auth.AuthGroup;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.error.Assert;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:42:52
 */
public class AuthGroupInterceptor extends BaseAnnotationMethodInterceptor {

    public AuthGroupInterceptor(MethodInterceptor methodInterceptor) {
        super(new SpringAnnotationResolver(), AuthGroup.class, methodInterceptor);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) {
        if (!(annotation instanceof AuthGroup)) return;
        Authenticator authenticator = AuthenticatorHolder.get();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (!authenticator.open(request, response)) return;

        AuthGroup authAnnotation = (AuthGroup) annotation;
        String uri = request.getRequestURI();

        String[] groups = authAnnotation.value();
        Logical[] logicalList = authAnnotation.logical();

        if (groups == null || groups.length == 0) return;

        boolean result = authenticator.checkGroup(request, response, uri, groups[0]);
        if (groups.length > 1) {
            for (int i = 1; i < groups.length; i++) {
                Logical logical = logicalList[0];
                if (logicalList.length > i - 1 && logicalList[i - 1] != null) logical = logicalList[i - 1];
                if (logical == Logical.AND)
                    result = result && authenticator.checkGroup(request, response, uri, groups[i]);
                if (logical == Logical.OR)
                    result = result || authenticator.checkGroup(request, response, uri, groups[i]);
            }
        }
        Assert.isTrue(result, "NO_AUTH");
    }

}

