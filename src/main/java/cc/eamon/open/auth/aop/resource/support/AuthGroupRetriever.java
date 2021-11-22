package cc.eamon.open.auth.aop.resource.support;

import cc.eamon.open.Constant;
import cc.eamon.open.auth.aop.deserializer.model.Body;
import cc.eamon.open.auth.aop.resource.AuthResourceRetrieverAdapter;
import cc.eamon.open.auth.aop.resource.ResourceRetrieveType;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.chain.ChainContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: Zhu yuhan
 * Email: zhuyuhan2333@qq.com
 * Date: 2021/11/19 7:07 下午
 **/
public class AuthGroupRetriever extends AuthResourceRetrieverAdapter {

    private Authenticator authenticator;

    public AuthGroupRetriever() {
        this.authenticator = AuthenticatorHolder.get();
    }

    @Override
    public boolean retrieve(String group, HttpServletRequest request) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();
        if (!this.authenticator.open(request, response)) return true;
        String uri = request.getRequestURI();
        Body body = (Body) ChainContextHolder.get(Constant.REQUEST_BODY_KEY);
        try {
            return authenticator.checkGroup(request, response, uri, group, body);
        } catch (Exception e) {
            return authenticator.checkGroup(request, response, uri, group, body);
        }
    }

    @Override
    public ResourceRetrieveType type() {
        return ResourceRetrieveType.GROUP;
    }
}
