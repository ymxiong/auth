package cc.eamon.open.chain;

import cc.eamon.open.chain.interceptor.support.ChainContextHandlerInterceptor;
import cc.eamon.open.chain.interceptor.support.FeignChainContextRequestInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 22:03
 **/
@Configuration
public class ChainContextAdvice extends FeignChainContextRequestInterceptor implements WebMvcConfigurer {


    /**
     * 注册该拦截器
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new ChainContextHandlerInterceptor());
    }

    /**
     * 注意：chainKey变名字或变类型均会导致processor和parser失效
     * 该方法用于操作不需要使用processor和parser的chainContext
     * 便于快捷变更上下游变量名或变量类型
     */
    @Override
    public void parseChainContext() {
        //调用父类重载的parseChainContext方法即可
        //有三个重载方法
    }

    /**
     * 添加chainContext，注意chainKey与processor和parser相匹配
     *
     * @param httpServletRequest
     */
    @Override
    public void addChainContext(HttpServletRequest httpServletRequest) {
    }

    /**
     * 校验链路信息，一般可不重写
     *
     * @return false则链路信息校验不通过，抛出异常，请求在此返回
     */
    @Override
    public boolean checkChainContext() {
        return super.checkChainContext();
        //check else
    }

    /**
     * 保留操作
     */
    @Override
    public void applyChainContext() {
        super.applyChainContext();
    }
}
