package cc.eamon.open.auth.advice.strategy;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-03-01 18:11:09
 */
public class HeaderContextValueStrategy implements ContextValueStrategy {

    @Override
    public String identifier() {
        return "header";
    }

    @Override
    public String parse(HttpServletRequest request, HttpServletResponse response, String valueName) {
        String value = request.getHeader(valueName);
        String[] values = valueName.split("\\$");
        if (values.length < 2) return value;
        value = request.getHeader(values[1]);
        if (StringUtils.isEmpty(value)) value = request.getHeader(values[1].toLowerCase());
        return value;
    }

}
