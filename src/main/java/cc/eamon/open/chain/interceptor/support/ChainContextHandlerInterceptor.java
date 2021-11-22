package cc.eamon.open.chain.interceptor.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.processor.ChainKeyProcessor;
import cc.eamon.open.chain.processor.metadata.ChainKeyProcessorMetadata;
import cc.eamon.open.status.StatusException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;
import java.util.Optional;

import static cc.eamon.open.Constant.CHAIN_START_HEADER;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-19 21:53:58
 */
public class ChainContextHandlerInterceptor implements HandlerInterceptor {

    private static Logger logger = LoggerFactory.getLogger(ChainContextHandlerInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (request.getHeader(CHAIN_START_HEADER.toLowerCase()) == null)
            this.startCheck();
        Enumeration<String> headerEnumeration = request.getHeaderNames();
        while (headerEnumeration.hasMoreElements()) {
            String header = headerEnumeration.nextElement().toUpperCase();
            Class<? extends ChainKeyProcessor> processorClass = ChainKeyProcessorMetadata.getKeyProcessor(header);
            ChainKeyProcessor processor = null;
            try {
                processor = processorClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new StatusException("CHAIN_ERROR");
            }
            //parse header to chainContext
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

    private void startCheck() {
        for (String value : ChainKeyProcessorMetadata.chainKeyProcessorMap.keySet()) {
            //TODO add all chain processor to chainContext's processor map, or make twice
            Class<? extends ChainKeyProcessor> keyProcessorClass = ChainKeyProcessorMetadata.chainKeyProcessorMap.get(value);
            ChainKeyProcessor chainKeyProcessor = null;
            try {
                chainKeyProcessor = keyProcessorClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                throw new StatusException("CHAIN_ERROR");
            }
            chainKeyProcessor.init();
        }
        ChainContextHolder.put(CHAIN_START_HEADER, "0");
    }

}
