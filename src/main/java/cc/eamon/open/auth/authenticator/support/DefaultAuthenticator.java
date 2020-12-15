package cc.eamon.open.auth.authenticator.support;

import cc.eamon.open.auth.authenticator.Authenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 01:20:17
 */
public class DefaultAuthenticator implements Authenticator {

    @Override
    public boolean open(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group) {
        return true;
    }

    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult) {
        return expressionResult;
    }

    @Override
    public Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return null;
    }

    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return null;
    }

}
