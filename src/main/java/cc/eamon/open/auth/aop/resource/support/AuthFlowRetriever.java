package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.aop.resource.ResourceRetriever;
import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.flow.config.constants.FlowConstants;
import cc.eamon.open.flow.core.FlowEngine;
import cc.eamon.open.flow.remote.FlowRequest;
import com.alibaba.fastjson.JSON;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthFlowRetriever implements ResourceRetriever {

    @Override
    public boolean retrieve(String expression) {
        if (StringUtils.isEmpty(expression))
            return false;
        FlowEngine flowEngine = FlowEngine.instance();
        try {
            String[] split = expression.split(":");
            if (split.length < 2) return false;
            String flowId = split[1];
            Map<String, Object> params = new HashMap<>();
            Object bodyObj = ChainContextHolder.get(Constant.REQUEST_BODY_KEY);
            if (bodyObj == null) {
                // get or delete
                params.put(Constant.AUTH_FLOW_REQUEST_KEY, ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest());
            } else {
                params.put(Constant.AUTH_FLOW_MAP_KEY, JSON.parseObject(JSON.toJSONString(bodyObj)));
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
