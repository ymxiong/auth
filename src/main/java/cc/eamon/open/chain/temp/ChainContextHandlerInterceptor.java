package cc.eamon.open.chain.temp;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.ChainKeyParserEnum;
import cc.eamon.open.chain.processor.ChainKeyEnum;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.error.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.Enumeration;
import java.util.Optional;
import java.util.UUID;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 21:53:58
 */
public class ChainContextHandlerInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ChainContextHandlerInterceptor.class);

    private static final String START_HEADER = "CHAIN-START";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if(request.getHeader(START_HEADER.toLowerCase()) == null)
            this.startCheck();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement().toUpperCase();
            Class<? extends ChainKeyProcessor> processorClass = ChainKeyEnum.getKeyProcessor(header);
            ChainKeyProcessor processor = null;
            try {
                processor = processorClass.getDeclaredConstructor().newInstance();
            } catch (Exception e){
                Assert.notNull(null,"CHAIN_ERROR");
            }
            Class<? extends ChainKeyParser> parserClass = ChainKeyParserEnum.getChainKeyParser(header);
            String value = Optional.ofNullable(request.getHeader(header)).orElse(request.getParameter(header));
            processor.handle(header, value, parserClass);
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
            String traceID = UUID.randomUUID().toString().replaceAll("-","");
            ChainContextHolder.put(ChainKeyEnum.TRACE_ID,traceID);
            logger.info("CHAIN => " + traceID + "-START");
            ChainContextHolder.put(ChainKeyEnum.SPAN_ID,traceID);
            logger.info("SPAN => " + ChainKeyEnum.TRACE_ID.getKey() + "-" + traceID + "::"
                    + ChainKeyEnum.SPAN_ID.getKey() + "-" + traceID);
            ChainContextHolder.put(ChainKeyEnum.PARENT_ID,traceID);
        }
        if(ChainContextHolder.get(ChainKeyEnum.CHAIN_INVOKE_ID) == null) {
            ChainContextHolder.put(ChainKeyEnum.CHAIN_INVOKE_ID, "1.");
            logger.info("INVOKE => " + ChainKeyEnum.CHAIN_INVOKE_ID + "-" + "1");
        }
        if(ChainContextHolder.get(ChainKeyEnum.CHAIN_OPEN_TIME) == null){
            Date openTime = new Date();
            ChainContextHolder.put(ChainKeyEnum.CHAIN_OPEN_TIME, openTime);
            ChainContextHolder.put(ChainKeyEnum.APP_OPEN_TIME, openTime);
            logger.info("CHAIN_OPEN_TIME => " + openTime);
        }
        ChainContextHolder.put(START_HEADER, "0");
    }

}
