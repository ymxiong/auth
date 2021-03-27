package cc.eamon.open.auth.advice.strategy;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2021-03-01 18:11:09
 */
public class CookieContextValueStrategy implements ContextValueStrategy {

    @Override
    public String identifier() {
        return "cookie";
    }

    @Override
    public String parse(HttpServletRequest request, HttpServletResponse response, String valueName) {
        String value = request.getHeader(valueName);
        String[] values = valueName.split("\\$");
        if (values.length < 2) return value;
        Cookie[] cookies = request.getCookies();
        if (null != cookies) {
            for (Cookie cookie : cookies) {
                if (values[1].toLowerCase().equals(cookie.getName().toLowerCase())) return cookie.getValue();
            }
        }
        return value;
    }

}
