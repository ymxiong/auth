package cc.eamon.open.auth.aop.resource.callback;

import cc.eamon.open.auth.aop.resource.AuthCallback;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 7:13 下午
 **/
public class DefaultAuthCallback implements AuthCallback {

    @Override
    public boolean auth(HttpServletRequest request, String uri, Map<String, Object> authContextMap) {
        return true;
    }
}
