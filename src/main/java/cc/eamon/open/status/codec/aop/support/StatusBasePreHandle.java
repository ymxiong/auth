package cc.eamon.open.status.codec.aop.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.codec.aop.DecoderPreHandle;
import cc.eamon.open.status.util.StatusUtils;
import feign.Request;
import feign.Response;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 20:27
 **/
public abstract class StatusBasePreHandle implements DecoderPreHandle {

    @Override
    public void preHandle(Response response) {
        check(response);
        handleChainContext(response);
        handleExtra(response);
    }

    private void handleChainContext(Response response) {
        Request request = response.request();
        String url = request.url();
        Request.HttpMethod httpMethod = request.httpMethod();

        url = StatusUtils.getActualUrl(url);

        ChainContextHolder.put(StatusConstants.STATUS_CHAIN_RPC_KEY, url + httpMethod.name());
    }

    public abstract void check(Response response);

    public abstract void handleExtra(Response response);

}
