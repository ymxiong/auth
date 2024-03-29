package cc.eamon.open.auth.authenticator;

import cc.eamon.open.auth.aop.deserializer.model.Body;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 01:14:23
 */
public interface Authenticator {

    boolean open(HttpServletRequest request, HttpServletResponse response);

    boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group, Body body);

    boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult, Body body);

    Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName);

    Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName);

}
