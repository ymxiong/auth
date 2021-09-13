package cc.eamon.open.chain.interceptor.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.chain.interceptor.BaseChainContextRequestInterceptor;
import cc.eamon.open.chain.parser.ChainKeyParser;
import cc.eamon.open.chain.parser.map.UserGenericMap;
import cc.eamon.open.chain.parser.metadata.ChainKeyParserMetadata;
import cc.eamon.open.chain.parser.metadata.GenericChainKeyParserMetadata;
import cc.eamon.open.status.StatusConstants;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * 适配feign
 * <p>
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 21:00
 **/
public abstract class FeignChainContextRequestInterceptor extends BaseChainContextRequestInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        Enumeration<String> headerNames = request.getHeaderNames();
        //已解决
        //解决1：不能传递已有header
        //解决2：传递ChainContext
        if (headerNames != null) {
            while (headerNames.hasMoreElements()) {
                String header = headerNames.nextElement();
                if (header.equalsIgnoreCase("content-length") || header == null)
                    continue;
                requestTemplate.header(header, request.getHeader(header));
            }
        }
        applyChainContextPreProcess();
        this.applyChainContext(requestTemplate);
    }

    private void applyChainContext(RequestTemplate requestTemplate) {
        for (String chainKey : ChainContextHolder.get().keySet()) {
            Object chain = ChainContextHolder.get(chainKey);
            //TODO  1.出现类继承时如何匹配 例： Map -> HashMap
            //TODO  2.泛型如何匹配 例： Map<K,V> -> Map<K,V> parser 已解决，自定义UserGenericMap类型
            //首先判断是否为UserGenericMap类型
            ChainKeyParser chainKeyParser;
            Class<?> chainClass = chain.getClass();
            if (UserGenericMap.class.isAssignableFrom(chainClass)) {
                UserGenericMap userGenericMap = (UserGenericMap) chain;
                chainKeyParser = GenericChainKeyParserMetadata.getChainKeyParser(
                        new GenericChainKeyParserMetadata.GenericMetadata(
                                userGenericMap.getK(), userGenericMap.getV()
                        )
                );
            } else {
                chainKeyParser = ChainKeyParserMetadata.getChainKeyParser(chainClass);
            }
            requestTemplate.header(chainKey, chainKeyParser.encodeChainContext(chain));
            this.applyStatusChainContext(requestTemplate);
        }
    }

    /**
     * 塞入subUrl
     */
    private void applyStatusChainContext(RequestTemplate requestTemplate) {
//        String invokeKey =
//                requestTemplate.method() == null ? RequestMethod.GET.name() : requestTemplate.method()
//                        + "-" + requestTemplate.path();
        ChainContextHolder.put(StatusConstants.STATUS_CHAIN_RPC_SUB_KEY, requestTemplate.path());
    }
}
