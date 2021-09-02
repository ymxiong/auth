package cc.eamon.open.status.codec.aop.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;

import java.io.IOException;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 20:27
 **/
public class StatusDecoderPreHandle extends StatusBasePreHandle {

    @Override
    public void check(Response response) {
        if (response == null || response.body() == null)
            throw new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DEFAULT_DECODE_MESSAGE);
    }

    @Override
    public void handleExtra(Response response) {
        Response.Body body = response.body();
        String responseMap = null;
        try {
            responseMap = Util.toString(body.asReader());
        } catch (IOException e) {
            // no op
        }
        JSONObject responseMapJson = JSON.parseObject(responseMap);
        String statusString = responseMapJson.getString(StatusConstants.STATUS_KEY);
        // 防止多次解析response
        ChainContextHolder.put(StatusConstants.STATUS_KEY, statusString);
        String message = responseMapJson.getString(StatusConstants.MESSAGE_KEY);
        ChainContextHolder.put(StatusConstants.MESSAGE_KEY, message);
    }
}
