package cc.eamon.open.status.codec.aop.support;

import cc.eamon.open.status.StatusConstants;
import cc.eamon.open.status.StatusException;
import feign.Response;

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
        // TODO: 2021/11/23 NEED FIX 正常调用返回非Map的情况; 调用方需要接收Response.Body时不能提前操作流
        Response.Body body = response.body();
        String responseMap = null;
        try {
//            responseMap = Util.toString(body.asReader());
//            JSONObject responseMapJson = JSON.parseObject(responseMap);
//            String statusString = responseMapJson.getString(StatusConstants.STATUS_KEY);
//            // 防止多次解析response
//            ChainContextHolder.put(StatusConstants.STATUS_KEY, statusString);
//            String message = responseMapJson.getString(StatusConstants.MESSAGE_KEY);
//            ChainContextHolder.put(StatusConstants.MESSAGE_KEY, message);
        } catch (Exception e) {
            // no op
        }
    }
}
