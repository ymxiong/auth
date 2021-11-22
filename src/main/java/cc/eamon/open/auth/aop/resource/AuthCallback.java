package cc.eamon.open.auth.aop.resource;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/20 7:05 下午
 **/
public interface AuthCallback {

    boolean auth(HttpServletRequest request, String uri, Map<String, Object> authContextMap);
}
