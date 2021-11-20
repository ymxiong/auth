package cc.eamon.open.auth.aop.resource;

import cc.eamon.open.auth.aop.resource.support.AuthCallBackRetriever;
import cc.eamon.open.auth.aop.resource.support.AuthContextRetriever;
import cc.eamon.open.auth.aop.resource.support.AuthFlowRetriever;
import cc.eamon.open.auth.aop.resource.support.AuthRequestRetriever;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:10 下午
 **/
public enum ResourceRetrieverEnum {

    FLOW_RETRIEVER(new AuthFlowRetriever()),

    REQUEST_RETRIEVER(new AuthRequestRetriever()),

    CALLBACK_RETRIEVER(new AuthCallBackRetriever()),

    CONTEXT_RETRIEVER(new AuthContextRetriever());

    ResourceRetriever resourceRetriever;

    ResourceRetrieverEnum(ResourceRetriever resourceRetriever) {
        this.resourceRetriever = resourceRetriever;
    }

    public ResourceRetriever getResourceRetriever() {
        return resourceRetriever;
    }

    public static ResourceRetriever getRetriever(ResourceRetrieveType resourceRetrieveType) {
        if (resourceRetrieveType == null) return REQUEST_RETRIEVER.getResourceRetriever();
        for (ResourceRetrieverEnum value : ResourceRetrieverEnum.values()) {
            if (resourceRetrieveType == value.resourceRetriever.type())
                return value.getResourceRetriever();
        }
        return REQUEST_RETRIEVER.getResourceRetriever();
    }
}
