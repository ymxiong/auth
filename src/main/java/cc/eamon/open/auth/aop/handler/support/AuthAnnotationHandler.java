package cc.eamon.open.auth.aop.handler.support;


import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.handler.BaseAnnotationHandler;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.error.Assert;
import cc.eamon.open.status.StatusException;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        boolean execute = false;
        if (!StringUtils.isEmpty(value)) {
            // TODO:
            //表达式校验问题
            try {
                Expression expression = AviatorEvaluator.compile(value);
                List<String> variableNames = expression.getVariableNames();
                String a2 = null;
                if(variableNames.size() > 1)
                    a2 = variableNames.get(1);
                String a1 = variableNames.get(0);

                Object requestValue = authenticator.getRequestValue(request, response, a1);
                Object contextValue = authenticator.getContextValue(request, response, a2);

                Map<String, Object> env = new HashMap<>();
                env.put(a1,requestValue);
                env.put(a2,contextValue);
                execute = (Boolean) expression.execute(env);
            }catch (Exception e){
                throw new StatusException("EXP_ERROR");
            }

        }
        //@Auth默认为权限名称
        else
            value = request.getRequestURI();

        String[] groups = authAnnotation.group();
        Logical[] logicalList = authAnnotation.logical();

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
        Assert.isTrue( execute || result, "NO_AUTH");
    }
}

