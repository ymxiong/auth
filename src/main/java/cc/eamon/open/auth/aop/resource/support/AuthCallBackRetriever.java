package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;

import javax.servlet.http.HttpServletRequest;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthCallBackRetriever extends AuthResourceRetrieverAdapter {

    @Override
    public boolean retrieve(String expression, HttpServletRequest request) {
        return true;
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.CALLBACK;
    }
}
