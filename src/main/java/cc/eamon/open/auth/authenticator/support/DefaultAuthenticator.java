package cc.eamon.open.auth.authenticator.support;

import cc.eamon.open.auth.authenticator.Authenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 01:20:17
 */
public class DefaultAuthenticator implements Authenticator {

    @Override
    public boolean open() {
        return true;
    }

    @Override
    public boolean checkPermissions(HttpServletRequest request, HttpServletResponse response, String... permission) {
        return false;
    }
}
