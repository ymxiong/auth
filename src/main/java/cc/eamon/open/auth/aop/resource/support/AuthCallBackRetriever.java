package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.resource.ResourceRetriever;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthCallBackRetriever implements ResourceRetriever {

    @Override
    public boolean retrieve(String expression) {
        return true;
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.CALLBACK;
    }
}
