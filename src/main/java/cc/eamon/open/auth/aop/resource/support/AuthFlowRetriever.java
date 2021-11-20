package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.util.AuthUtils;
import cc.eamon.open.flow.config.constants.FlowConstants;
import cc.eamon.open.flow.core.FlowEngine;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthFlowRetriever extends AuthResourceRetrieverAdapter {

    @Override
    public boolean retrieve(String expression, HttpServletRequest request) {
        if (StringUtils.isEmpty(expression))
            return false;
        FlowEngine flowEngine = FlowEngine.instance();
        try {
            String[] split = expression.split(":");
            if (split.length < 2) return false;
            String flowId = split[1];
            Map<String, Object> params = new HashMap<>();
            Map<String, Object> authContextMap = AuthUtils.getAuthContextMap();
            if (authContextMap == null || authContextMap.isEmpty() || ((Map)authContextMap.get("extra")).isEmpty()) {
                // get or delete
                params.put(Constant.AUTH_FLOW_REQUEST_KEY, request);
            } else {
                params.put(Constant.AUTH_FLOW_MAP_KEY, authContextMap);
            }
            params.forEach((key, value) -> flowEngine.register(FlowConstants.SCOPE_DEFAULT, key, value));
            flowEngine.run(flowId);
            Map<String, Object> result = flowEngine.retrieveFlowResults(flowId);
            return (Boolean) result.get(Constant.AUTH_FLOW_RESULT_KEY);
        } catch (Exception e) {
            return false;
        } finally {
            flowEngine.release();
        }
    }


    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.FLOW;
    }
}
