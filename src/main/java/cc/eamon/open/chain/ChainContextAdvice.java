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
        //例如
//        Object v1 = ChainContextHolder.get(ChainKeyEnum.SPAN_ID);
//        if(v1 == null)return;
//        ChainContextHolder.put(ChainKeyEnum.PARENT_ID,v1);
        //parse else
    }

    @Override
    public void addChainContext(HttpServletRequest httpServletRequest) {
        //add into chainContext or requestAttr
//        httpServletRequest.setAttribute(xxx,xxx);
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
