package cc.eamon.open.status.flow;

import cc.eamon.open.flow.core.spring.FlowComponent;
import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.status.Status;
import org.springframework.util.StringUtils;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/3/27 14:54
 **/
@FlowComponent()
public class StatusValuesFlow {

    @Flow(value = "FLOW_STATUS_MESSAGE")
    public Status.Builder statusMessage(String message, Status.Builder builder) {
        if (StringUtils.isEmpty(message)) return builder;
        return builder.addMessage(message);
    }

    @Flow(value = "FLOW_STATUS_PATH")
    public Status.Builder statusPath(String path, Status.Builder builder) {
        if (StringUtils.isEmpty(path)) return builder;
        return builder.addPath(path);
    }

    @Flow(value = "FLOW_STATUS_DATA")
    public Status.Builder statusData(String key, Object value, Status.Builder builder) {
        if (StringUtils.isEmpty(key) || value == null) return builder;
        return builder.addData(key, value);
    }

    @Flow(value = "FLOW_STATUS_DATA_VALUE")
    public Status.Builder statusDataValue(Object data, Status.Builder builder) {
        if (data == null) return builder;
        return builder.addDataValue(data);
    }

    @Flow(value = "FLOW_STATUS_DATA_COUNT")
    public Status.Builder statusDataCount(Long count, Status.Builder builder) {
        if (count == null) return builder;
        return builder.addDataCount(count);
    }

    @Flow(value = "FLOW_STATUS_EXTRA")
    public Status.Builder statusExtra(Object extra, Status.Builder builder) {
        if (extra == null) return builder;
        return builder.addExtra(extra);
    }

}
