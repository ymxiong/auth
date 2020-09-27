package cc.eamon.open.auth.aop.handler.support;


import cc.eamon.open.auth.Auth;
import cc.eamon.open.auth.aop.handler.AnnotationHandler;
import cc.eamon.open.status.StatusException;

import java.lang.annotation.Annotation;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-10 19:50:42
 */

public class AuthAnnotationHandler extends AnnotationHandler {

    public AuthAnnotationHandler() {
        super(Auth.class);
    }


    @Override
    public void assertAuthorized(Annotation annotation) throws StatusException {
        if (annotation instanceof Auth) {
            Auth authAnnotation = (Auth)annotation;
            String[] group = authAnnotation.group();
//            Subject subject = this.getSubject();
//            if (perms.length == 1) {
//                subject.checkPermission(perms[0]);
//            } else if (Logical.AND.equals(rpAnnotation.logical())) {
//                this.getSubject().checkPermissions(perms);
//            } else {
//                if (Logical.OR == rpAnnotation.logical()) {
//                    boolean hasAtLeastOnePermission = false;
//                    String[] var6 = perms;
//                    int var7 = perms.length;
//
//                    for(int var8 = 0; var8 < var7; ++var8) {
//                        String permission = var6[var8];
//                        if (this.getSubject().isPermitted(permission)) {
//                            hasAtLeastOnePermission = true;
//                        }
//                    }
//
//                    if (!hasAtLeastOnePermission) {
//                        this.getSubject().checkPermission(perms[0]);
//                    }
//                }
//            }
        }
    }


}

