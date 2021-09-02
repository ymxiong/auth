package cc.eamon.open.status.codec.aop.support;

import cc.eamon.open.status.codec.aop.DecoderPreHandle;
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
        handleExtra(response);
    }

    public abstract void check(Response response);

    public abstract void handleExtra(Response response);

}
