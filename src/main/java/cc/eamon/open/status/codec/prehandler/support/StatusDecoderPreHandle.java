package cc.eamon.open.status.codec.prehandler.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;

import java.io.Reader;
import java.lang.reflect.Type;

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
    public void handleResponseBodyOptional(Response response, Type type) {
        // TODO: 2021/11/23 NEED FIX 正常调用返回非Map的情况; 调用方需要接收Response.Body时不能提前操作流
        Response.Body body = response.body();
        String responseMap = null;
        JSONObject responseMapJson = null;
        try {
            Reader reader = body.asReader();
            responseMap = Util.toString(reader);
            responseMapJson = JSON.parseObject(responseMap);
        } catch (Exception e) {
            // no exception
            return;
        }
        String statusString = responseMapJson.getString(StatusConstants.STATUS_KEY);
        ChainContextHolder.put(StatusConstants.STATUS_KEY, statusString);
        String message = responseMapJson.getString(StatusConstants.MESSAGE_KEY);
        ChainContextHolder.put(StatusConstants.MESSAGE_KEY, message);
    }
}
