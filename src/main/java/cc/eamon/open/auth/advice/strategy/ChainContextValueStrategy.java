package cc.eamon.open.auth.advice.strategy;

import cc.eamon.open.chain.ChainContextHolder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-03-01 18:11:09
 */
public class ChainContextValueStrategy implements ContextValueStrategy {

    @Override
    public String identifier() {
        return "chain";
    }

    @Override
    public String parse(HttpServletRequest request, HttpServletResponse response, String valueName) {
        String value = request.getHeader(valueName);
        String[] values = valueName.split("\\$");
        if (values.length < 2) return value;
        value = (String) ChainContextHolder.get(values[1]);
        return value;
    }

}
