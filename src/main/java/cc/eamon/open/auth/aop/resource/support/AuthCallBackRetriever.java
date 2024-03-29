package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthCallBackRetriever extends AuthResourceRetrieverAdapter {

    private Authenticator authenticator;

    public AuthCallBackRetriever() {
        this.authenticator = AuthenticatorHolder.get();
    }

    @Override
    public boolean retrieve(String expressionString, HttpServletRequest request) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        return !this.authenticator.open(request, response);
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.CALLBACK;
    }
}
