package cc.eamon.open.auth.aop.handler.support;


import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.aop.handler.BaseAnnotationHandler;
import cc.eamon.open.auth.authenticator.Authenticator;
import cc.eamon.open.auth.authenticator.AuthenticatorHolder;
import cc.eamon.open.error.Assert;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:50:42
 */

public class AuthAnnotationHandler extends BaseAnnotationHandler {

    public AuthAnnotationHandler() {
        super(Auth.class);
    }

    @Override
    public void assertAuthorized(MethodInvocation methodInvocation, Annotation annotation) {
        if (!(annotation instanceof Auth)) return;
        Authenticator authenticator = AuthenticatorHolder.get();
        if (!authenticator.open()) return;

        Auth authAnnotation = (Auth) annotation;
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getResponse();

        Assert.isTrue(authenticator.checkPermissions(request, response, authAnnotation.value()), "NO_AUTH");

//        Subject subject = this.getSubject();
//        if (perms.length == 1) {
//            subject.checkPermission(perms[0]);
//        } else if (Logical.AND.equals(rpAnnotation.logical())) {
//            this.getSubject().checkPermissions(perms);
//        } else {
//            if (Logical.OR == rpAnnotation.logical()) {
//                boolean hasAtLeastOnePermission = false;
//                String[] var6 = perms;
//                int var7 = perms.length;
//
//                for(int var8 = 0; var8 < var7; ++var8) {
//                    String permission = var6[var8];
//                    if (this.getSubject().isPermitted(permission)) {
//                        hasAtLeastOnePermission = true;
//                    }
//                }
//
//                if (!hasAtLeastOnePermission) {
//                    this.getSubject().checkPermission(perms[0]);
//                }
//            }
//        }
    }
}

