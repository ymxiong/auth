package cc.eamon.open.status.flow;

import cc.eamon.open.flow.core.spring.FlowComponent;
import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusException;

import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/3/27 14:54
 **/
@FlowComponent()
public class StatusResultFlow {

    @Flow(value = "FLOW_STATUS_MAP")
    public Map<String, Object> statusMap(Status.Builder builder) {
        return builder.map();
    }

    @Flow(value = "FLOW_STATUS_BUILD")
    public Status statusBuild(Status.Builder builder) {
        return builder.build();
    }


}
