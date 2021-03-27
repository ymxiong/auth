package cc.eamon.open.status.flow;

import cc.eamon.open.flow.core.spring.FlowComponent;
import cc.eamon.open.flow.core.stereotype.Flow;
import cc.eamon.open.status.Status;
import cc.eamon.open.status.StatusException;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/3/27 14:54
 **/
@FlowComponent()
public class StatusBuilderFlow {

    @Flow(value = "FLOW_STATUS_SUCCESS")
    public Status.Builder statusSuccess() {
        return Status.successBuilder();
    }

    @Flow(value = "FLOW_STATUS_FAIL_DEFAULT")
    public Status.Builder statusFailed() {
        return Status.failedBuilder();
    }

    @Flow(value = "FLOW_STATUS_FAIL_CODE")
    public Status.Builder statusFailed(int code) {
        return Status.failedBuilder(code);
    }

    @Flow(value = "FLOW_STATUS_FAIL_ERROR_NAME")
    public Status.Builder statusFailed(String errorName) {
        return Status.failedBuilder(errorName);
    }

    @Flow(value = "FLOW_STATUS_FAIL_STATUS_EXCEPTION")
    public Status.Builder statusFailed(StatusException statusException) {
        return Status.failedBuilder(statusException);
    }

    @Flow(value = "FLOW_STATUS_DIY")
    public Status.Builder statusDiy(boolean result, int status, String message) {
        return Status.builder(result, status, message);
    }


}
