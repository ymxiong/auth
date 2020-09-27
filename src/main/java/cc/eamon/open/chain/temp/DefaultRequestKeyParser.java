package cc.eamon.open.chain.temp;

import javax.servlet.http.HttpServletRequest;
import java.util.Optional;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 22:14:29
 */

public class DefaultRequestKeyParser implements IRequestKeyParser {

    @Override
    public String parse(String key, HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(key)).orElse(request.getParameter(key));
    }

}