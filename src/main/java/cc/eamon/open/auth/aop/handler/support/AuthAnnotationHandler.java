package cc.eamon.open.auth.aop.handler.support;


import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.handler.BaseAnnotationHandler;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.error.Assert;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:50:42
 */

public class AuthAnnotationHandler extends BaseAnnotationHandler {

    public AuthAnnotationHandler() {
        super(Auth.class);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) {
        if (!(annotation instanceof Auth)) return;
        Authenticator authenticator = AuthenticatorHolder.get();
        if (!authenticator.open()) return;

        Auth authAnnotation = (Auth) annotation;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        String value = authAnnotation.value();
        if (StringUtils.isEmpty(value)) {
            value = request.getRequestURI();
        }

        String[] groups = authAnnotation.group();
        Logical[] logicalList = authAnnotation.logical();
        // TODO:
        // 0. 获取表达式
        String exp = authAnnotation.value();
        // 1. 拿到request参数
        authenticator.getRequestValue(request, response, "userId");
        // 2. 拿到header参数
        authenticator.getRequestValue(request, response, "entityKey");
        // 3. 校验表达式

        boolean result = authenticator.checkPermissions(request, response, value, groups[0]);
        if (groups.length > 1) {
            for (int i = 1; i < groups.length; i++) {
                Logical logical = logicalList[0];
                if (logicalList.length > i - 1 && logicalList[i - 1] != null) logical = logicalList[i - 1];
                if (logical == Logical.AND)
                    result = result && authenticator.checkPermissions(request, response, value, groups[i]);
                if (logical == Logical.OR)
                    result = result || authenticator.checkPermissions(request, response, value, groups[i]);
            }
        }
        Assert.isTrue(result, "NO_AUTH");
    }
}

