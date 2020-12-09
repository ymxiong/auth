package cc.eamon.open.chain.interceptor;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2020/12/4 20:07
 **/
public interface RequestInterceptor {

    void addChainContext(HttpServletRequest httpServletRequest);

    void parseChainContext();

    boolean checkChainContext();

    //保留
    void applyChainContext();

}
