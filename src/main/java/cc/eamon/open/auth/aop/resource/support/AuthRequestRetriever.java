package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.resource.ResourceRetriever;
import cc.eamon.open.auth.util.AuthUtils;
import cc.eamon.open.chain.ChainContextHolder;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthRequestRetriever implements ResourceRetriever {

    @Override
    public boolean retrieve(String expression, HttpServletRequest request) {
        if (request.getParameter(expression) != null) return true;
        Map<String, Object> bodyMap = AuthUtils.getRequestBodyMap();
        if (bodyMap == null || bodyMap.isEmpty()) return false;
        return bodyMap.get(expression) != null;
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.REQUEST;
    }
}
