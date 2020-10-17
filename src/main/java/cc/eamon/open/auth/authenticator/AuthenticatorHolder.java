package cc.eamon.open.auth.authenticator;


import cc.eamon.open.auth.authenticator.support.DefaultAuthenticator;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-08 00:47:10
 */
public class AuthenticatorHolder {

    /**
     * init Context for current thread
     */
    private static ThreadLocal<Authenticator> local = ThreadLocal.withInitial(DefaultAuthenticator::new);

    public AuthenticatorHolder() {
    }

    public static Authenticator get() {
        if (local.get() == null) {
            local.set(new DefaultAuthenticator());
        }
        return local.get();
    }

    public static void set(Authenticator authenticator) {
        local.set(authenticator);
    }

    public static void clear() {
        local.remove();
    }

}
