package cc.eamon.open.status.codec.prehandler.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.codec.prehandler.DecoderPreHandle;
import cc.eamon.open.status.util.StatusUtils;
import feign.Request;
import feign.Response;

import java.lang.reflect.Type;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 20:27
 **/
public abstract class StatusBasePreHandle implements DecoderPreHandle {

    @Override
    public void preHandle(Response response, Type type) {
        check(response);
        handleChainContext(response);
        handleResponseBodyOptional(response, type);
    }

    /**
     * 读取response基本信息，不包括流
     *
     * @param response
     */
    private void handleChainContext(Response response) {
        Request request = response.request();
        String url = request.url();
        Request.HttpMethod httpMethod = request.httpMethod();

        url = StatusUtils.getActualUrl(url);

        ChainContextHolder.put(StatusConstants.STATUS_CHAIN_RPC_KEY, url + httpMethod.name());
    }

    public abstract void check(Response response);

    /**
     * 处理流信息
     *
     * @param response
     * @param type
     */
    public abstract void handleResponseBodyOptional(Response response, Type type);

}
