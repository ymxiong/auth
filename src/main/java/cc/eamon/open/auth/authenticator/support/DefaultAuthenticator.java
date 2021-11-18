package cc.eamon.open.auth.authenticator.support;

import cc.eamon.open.auth.advice.AuthAdvice;
import cc.eamon.open.auth.aop.deserializer.model.Body;
import cc.eamon.open.auth.authenticator.Authenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 01:20:17
 */
public class DefaultAuthenticator extends AuthAdvice implements Authenticator {

    public DefaultAuthenticator() {
        super();
    }

    @Override
    public boolean open(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group, Body body) {
        return super.checkGroup(request, response, uri, group, body);
    }

    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult, Body body) {
        return super.checkExpression(request, response, uri, expression, expressionResult, body);
    }

    @Override
    public Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return super.getContextValue(request, response, valueName);
    }

    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return super.getRequestValue(request, response, valueName);
    }

}
