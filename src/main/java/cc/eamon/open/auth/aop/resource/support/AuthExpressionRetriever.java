package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.deserializer.model.Body;
import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.chain.ChainContextHolder;
import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthExpressionRetriever extends AuthResourceRetrieverAdapter {

    private Authenticator authenticator;

    public AuthExpressionRetriever() {
        this.authenticator = AuthenticatorHolder.get();
    }

    @Override
    public boolean retrieve(String expressionString, HttpServletRequest request) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (!this.authenticator.open(request, response)) return true;
        String uri = request.getRequestURI();
        Body body = (Body) ChainContextHolder.get(Constant.REQUEST_BODY_KEY);
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

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.EXPRESSION;
    }
}
