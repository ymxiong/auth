package cc.eamon.open.status.codec.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import cc.eamon.open.status.StatusUtils;
import cc.eamon.open.status.codec.StatusErrorHandler;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.FeignException;
import feign.Response;
import feign.Util;
import feign.codec.DecodeException;
import feign.codec.Decoder;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/8/30 20:27
 **/
public class StatusDecoder extends Decoder.Default {

    private StatusErrorHandler statusErrorHandler;

    private StatusDecoder() {
    }

    public StatusDecoder(StatusErrorHandler statusErrorHandler) {
        this.statusErrorHandler = statusErrorHandler;
    }

    /**
     * 将feign包装的response解码为真实返回值
     *
     * @param response
     * @param type
     * @return
     * @throws IOException
     * @throws DecodeException
     * @throws FeignException
     */
    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        if (response == null || response.body() == null) return null;
        Response.Body body = response.body();
        int status = response.status();

        String responseMap = Util.toString(body.asReader());
        JSONObject responseMapJson = JSON.parseObject(responseMap);
        String statusString = responseMapJson.getString(StatusConstants.STATUS_KEY);
        // 防止多次解析response
        ChainContextHolder.put(StatusConstants.STATUS_KEY, statusString);
        String message = responseMapJson.getString(StatusConstants.MESSAGE_KEY);
        ChainContextHolder.put(StatusConstants.MESSAGE_KEY, message);
        if (this.hasException(statusString, message, status)) {
            // TODO 异常decode逻辑， 须开放定制
            Object statusChainMethod = ChainContextHolder.get(StatusConstants.STATUS_CHAIN_RPC_KEY);
            if (!(statusChainMethod instanceof Method))
                throw new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DEFAULT_MESSAGE, StatusConstants.DECODE_ERROR_MESSAGE_DETAIL);
            return this.statusErrorHandler.handle(StatusUtils.generateErrorMethodKey((Method) statusChainMethod, statusString, message));
        }
        // 兼容feign低版本逻辑，防止文件等异常
        return super.decode(response, type);
    }

    private boolean hasException(String statusString, String message, Integer responseStatus) throws IOException {
        if (StringUtils.isEmpty(statusString) || StringUtils.isEmpty(message)) {
            return false;
        }
        // 判断是否发生异常，1. 状态码为错误状态码；2. message中含异常信息
        if (responseStatus >= 300 || Integer.parseInt(statusString) >= 300 || message.contains("exception")) {
            return true;
        }
        return false;

    }
}
