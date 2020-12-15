package cc.eamon.open.chain;

import cc.eamon.open.chain.interceptor.support.FeignChainContextRequestInterceptor;
import cc.eamon.open.chain.temp.ChainContextHandlerInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 22:03
 **/
//服务配置demo
/*@Configuration
public class ChainContextAdvice extends FeignChainContextRequestInterceptor implements WebMvcConfigurer {

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ChainContextHandlerInterceptor());
    }

    @Override
    public void parseChainContext() {
        //test
        ChainContextHolder.put("abc","123456");
        parseChainContext("abc", "CHAIN-DATE-cde", Date.class, new ChainKeyParser() {
            @Override
            public String encodeChainContext(Object chainContext) {
                return chainContext.toString();
            }

            @Override
            public Object decodeChainContext(String temp) {
                return new Date(Long.parseLong(temp));
            }
        });
    }

    @Override
    public void addChainContext(HttpServletRequest httpServletRequest) {
        //add into chainContext or requestAttr
//        httpServletRequest.setAttribute(xxx,xxx);
        ChainContextHolder.put("CHAIN-JSON", new User("123456"));
    }

    //如果check不通过，将不会进行链路传递
    @Override
    public boolean checkChainContext() {
        return super.checkChainContext();
    }

    //保留操作
    @Override
    public void applyChainContext() {
        super.applyChainContext();
    }
}*/
