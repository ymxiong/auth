package cc.eamon.open.status.codec;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/9/1 20:53
 **/
public interface StatusErrorHandler {

    /**
     * 处理指定methodKey的逻辑
     *
     * @param errorMethodKey InterfaceSimpleName#MethodName(ParamType...)-StatusCode => matched feign errorKey
     * @return
     */
    Exception handle(String errorMethodKey);

}
