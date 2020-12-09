package cc.eamon.open.chain.temp;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.error.Assert;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 21:53:58
 */

public class ChainContextHandlerInterceptor implements HandlerInterceptor {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        this.startCheck();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement().toUpperCase();
            Class<? extends ChainKeyProcessor> processorClass = ChainKeyEnum.getKeyProcessor(header);
            ChainKeyProcessor processor = null;
            try {
                processor = processorClass.getDeclaredConstructor().newInstance();
            } catch (Exception e){
                Assert.notNull(processor,"CHAIN_ERROR");
            }
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

    private void startCheck(){
        if(ChainContextHolder.get(ChainKeyEnum.TRACE_ID) == null){
            String traceID = UUID.randomUUID().toString();
            ChainContextHolder.put(ChainKeyEnum.TRACE_ID,traceID);
            ChainContextHolder.put(ChainKeyEnum.SPAN_ID,traceID);
        }
        if(ChainContextHolder.get(ChainKeyEnum.CHAIN_INVOKE_ID) == null)
            ChainContextHolder.put(ChainKeyEnum.CHAIN_INVOKE_ID, "1.");
    }

}
