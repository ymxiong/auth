package cc.eamon.open.chain.temp;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Optional;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 21:53:58
 */

public class ChainContextHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement().toUpperCase();
            ChainKeyProcessor processor = ChainKeyEnum.getKeyProcessor(header);
            String value = Optional.ofNullable(request.getHeader(header)).orElse(request.getParameter(header));
            processor.handle(header, value);
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (ChainContextHolder.get() != null) {
            ChainContextHolder.clear();
        }
    }

}
