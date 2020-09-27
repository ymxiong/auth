package cc.eamon.open.chain.temp;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 22:14:50
 */
public interface IRequestKeyParser {
    String parse(String key, HttpServletRequest request);
}
