package cc.eamon.open.auth.authenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-10-17 01:14:23
 */
public interface Authenticator {

    boolean open();

    boolean checkPermissions(HttpServletRequest request, HttpServletResponse response, String... permission);

}
