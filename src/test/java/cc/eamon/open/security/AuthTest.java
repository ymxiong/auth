package cc.eamon.open.security;

import cc.eamon.open.chain.ChainContextHolder;
import org.junit.Test;
import org.springframework.util.Assert;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-09 18:20:51
 */
public class AuthTest {

    @Test
    public void testAuthContext() {
        ChainContextHolder.get().put("eamon", "eamon");
        Assert.isTrue(ChainContextHolder.get().get("eamon").equals("eamon"), "不匹配");
        new Thread(() -> Assert.isNull(ChainContextHolder.get().get("py"), "为空")).start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
