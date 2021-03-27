package com.horsecoder.identity.flow;

import cc.eamon.open.flow.core.FlowEngine;

import java.lang.Exception;
import java.lang.Object;
import java.lang.String;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 */
@Service
public class FlowEngineService {
    public Map<String, Object> runFlowByFlowId(String flowId, Map<String, String> results,
                                               Map<String, Object> params) throws Exception {
        // 定义返回结果;
        Map<String, Object> result = new HashMap<>();
        // 定义引擎;
        FlowEngine flowEngine = FlowEngine.instance();
        // 注入参数;
        if (params.size() > 0) params.forEach((key, value) -> flowEngine.register("initScope", key, value));
        // 运行流程;
        flowEngine.run(flowId);
        // 取出所需结果;
        for (Map.Entry<String, String> resultInfo : results.entrySet()) {
            String resultName = resultInfo.getKey();
            String resultScope = resultInfo.getValue();
            result.put(resultName, flowEngine.retrieve(resultScope, resultName));
        }
        // 释放流程引擎;
        flowEngine.release();
        return result;
    }
}
