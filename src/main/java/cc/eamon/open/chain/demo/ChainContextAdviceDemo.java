package cc.eamon.open.chain.demo;

import cc.eamon.open.chain.interceptor.support.FeignChainContextRequestInterceptor;
import cc.eamon.open.chain.parser.ChainKeyParserMetadata;
import cc.eamon.open.chain.processor.ChainKeyProcessorMetadata;
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
/*
@Configuration
public class ChainContextAdviceDemo extends FeignChainContextRequestInterceptor implements WebMvcConfigurer {

    //添加自定义parser和processor元信息
    static {
        ChainKeyParserMetadata.addChainKeyParser(new ChainKeyParserMetadata("USER", User.class, new UserParserProcessor()));
        ChainKeyProcessorMetadata.addChainKeyProcessor(new ChainKeyProcessorMetadata("USER", "USER", UserParserProcessor.class));
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ChainContextHandlerInterceptor());
    }

    @Override
    public void parseChainContext() {

    }

    @Override
    public void addChainContext(HttpServletRequest httpServletRequest) {
        //add into chainContext or requestAttr
//        httpServletRequest.setAttribute(xxx,xxx);
    }

    @Override
    public boolean checkChainContext() {
        return super.checkChainContext();
    }

    //保留操作
    @Override
    public void applyChainContext() {
        super.applyChainContext();
    }
}

 */
