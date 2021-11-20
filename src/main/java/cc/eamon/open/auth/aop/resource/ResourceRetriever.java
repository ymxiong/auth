package cc.eamon.open.auth.aop.resource;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:04 下午
 **/
public interface ResourceRetriever {

    boolean retrieve(String expression, HttpServletRequest request);

    ResourceRetrieveType type();
}
