package cc.eamon.open.chain.interceptor.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.interceptor.DefaultChainContextRequestInterceptor;
import cc.eamon.open.chain.parser.ChainKeyParserEnum;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.error.Assert;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 21:00
 **/
//适配feign
public abstract class FeignChainContextRequestInterceptor extends DefaultChainContextRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        //已解决
        //解决1：不能传递已有header
        //解决2：传递ChainContext
        if(headerNames != null){
            while(headerNames.hasMoreElements()){
                String header = headerNames.nextElement();
                if(header.equalsIgnoreCase("content-length") || header == null)
                    continue;
                requestTemplate.header(header,request.getHeader(header));
            }
        }
        applyChainContextPreHandle();
        this.applyChainContext(requestTemplate);
    }

    private void applyChainContext(RequestTemplate requestTemplate){
        for (String chainKey : ChainContextHolder.get().keySet()) {
            Object chain = ChainContextHolder.get(chainKey);
            //parser.encode string not need parser
            Class<? extends ChainKeyParser> parserClass = ChainKeyParserEnum.getChainKeyParser(chain.getClass());
            if(parserClass == null){
                requestTemplate.header(chainKey,(String) ChainContextHolder.get(chainKey));
                continue;
            }
            ChainKeyParser parser = null;
            try {
                parser = parserClass.getDeclaredConstructor().newInstance();
            } catch (Exception e) {
                Assert.notNull(null, "CHAIN_PARSE_ERROR");
            }

            requestTemplate.header(chainKey,parser.encodeChainContext(chain));
        }
    }
}
