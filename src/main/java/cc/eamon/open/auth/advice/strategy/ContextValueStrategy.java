package cc.eamon.open.auth.advice.strategy;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-03-01 17:55:16
 */
public interface ContextValueStrategy {

    String identifier();

    String parse(HttpServletRequest request, HttpServletResponse response, String valueName);

}
