package cc.eamon.open.auth.aop.interceptor.support;


import cc.eamon.open.Constant;
import cc.eamon.open.auth.AuthExpression;
import cc.eamon.open.auth.Logical;
import cc.eamon.open.auth.aop.interceptor.BaseAnnotationMethodInterceptor;
import cc.eamon.open.auth.aop.interceptor.MethodInterceptor;
import cc.eamon.open.auth.aop.resolver.support.SpringAnnotationResolver;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.error.Assert;
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
 * Time: 2020-08-10 19:42:52
 */
public class AuthExpressionInterceptor extends BaseAnnotationMethodInterceptor {

    public AuthExpressionInterceptor(MethodInterceptor methodInterceptor) {
        super(new SpringAnnotationResolver(), AuthExpression.class, methodInterceptor);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) {
        if (!(annotation instanceof AuthExpression)) return;
        Authenticator authenticator = AuthenticatorHolder.get();
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (!authenticator.open(request, response)) return;

        AuthExpression authAnnotation = (AuthExpression) annotation;

        String[] expressions = authAnnotation.value();
        Logical[] logicalList = authAnnotation.logical();

        if (expressions == null || expressions.length == 0) return;

        boolean result = checkExpression(expressions[0], authenticator, request, response);
        if (expressions.length > 1) {
            for (int i = 1; i < expressions.length; i++) {
                Logical logical = logicalList[0];
                if (logicalList.length > i - 1 && logicalList[i - 1] != null) logical = logicalList[i - 1];
                if (logical == Logical.AND)
                    result = result && checkExpression(expressions[i], authenticator, request, response);
                if (logical == Logical.OR)
                    result = result || checkExpression(expressions[i], authenticator, request, response);
            }
        }
        Assert.isTrue(result, "NO_AUTH");
    }


    private boolean checkExpression(String expressionString, Authenticator authenticator, HttpServletRequest request, HttpServletResponse response) {
        String uri = request.getRequestURI();
        if (StringUtils.isEmpty(expressionString)) return true;
        Object body = ChainContextHolder.get(Constant.REQUEST_BODY_OBJECT_KEY);
        Class objectClass = (Class) ChainContextHolder.get(Constant.REQUEST_BODY_CLASS_KEY);
        try {
            Expression expression = AviatorEvaluator.compile(expressionString);
            List<String> variableNames = expression.getVariableNames();
            String a2 = null;
            if (variableNames.size() > 1) {
                a2 = variableNames.get(1);
            }
            String a1 = variableNames.get(0);

            Object requestValue = authenticator.getRequestValue(request, response, a1);
            Object contextValue = authenticator.getContextValue(request, response, a2);

            Map<String, Object> env = new HashMap<>();
            env.put(a1, requestValue);
            env.put(a2, contextValue);
            boolean innerExecute = (Boolean) expression.execute(env) && (requestValue != null || contextValue != null);
            return authenticator.checkExpression(request, response, uri, expressionString, innerExecute, body);
        } catch (Exception e) {
            return authenticator.checkExpression(request, response, uri, expressionString, false, body);
        }
    }

}

