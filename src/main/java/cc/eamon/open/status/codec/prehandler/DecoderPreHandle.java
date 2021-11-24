package cc.eamon.open.status.codec.prehandler;

import feign.Response;

import java.lang.reflect.Type;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/2 20:25
 **/
public interface DecoderPreHandle {

    void preHandle(Response response, Type type);
}
