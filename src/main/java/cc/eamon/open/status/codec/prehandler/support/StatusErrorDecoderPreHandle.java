package cc.eamon.open.status.codec.prehandler.support;

import cc.eamon.open.chain.ChainContextHolder;
import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import feign.Response;
import feign.Util;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 20:27
 **/
public class StatusErrorDecoderPreHandle extends StatusBasePreHandle {

    @Override
    public void check(Response response) {
        if (response == null || response.body() == null)
            throw new StatusException(StatusConstants.DEFAULT_CODE, StatusConstants.DEFAULT_DECODE_MESSAGE);
    }

    @Override
    public void handleResponseBodyOptional(Response response, Type type) {
        Response.Body body = response.body();
        try {
            String responseMap = Util.toString(body.asReader());
            JSONObject responseMapJson = JSON.parseObject(responseMap);
            String statusString = responseMapJson.getString(StatusConstants.STATUS_KEY);
            String message = responseMapJson.getString(StatusConstants.MESSAGE_KEY);
            ChainContextHolder.put(StatusConstants.STATUS_KEY, statusString);
            ChainContextHolder.put(StatusConstants.MESSAGE_KEY, message);
        } catch (Exception e) {
            ChainContextHolder.put(StatusConstants.STATUS_KEY, StatusConstants.DEFAULT_CODE);
            ChainContextHolder.put(StatusConstants.MESSAGE_KEY, StatusConstants.DEFAULT_MESSAGE);
        }
    }
}
