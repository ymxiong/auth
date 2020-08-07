package cc.eamon.open.security;

import cc.eamon.open.security.cipher.AES;
import cc.eamon.open.security.cipher.Base64;
import cc.eamon.open.security.cipher.MD5;
import cc.eamon.open.security.cipher.RSA;
import org.junit.Test;
import org.springframework.util.Assert;

import java.nio.charset.StandardCharsets;

/**
 * Author: eamon
 * Email: eamon@eamon.cc
 * Time: 2020-08-06 20:46:56
 */
public class SecurityTest {

    @Test
    public void testMD5() {
        String pwd = MD5.getInstance().encode("test123456");
        Assert.isTrue(pwd.equals("47EC2DD791E31E2EF2076CAF64ED9B3D"), "不匹配");
    }

    @Test
    public void testBase64() {
        String testString = "eamon";
        String encode = Base64.getInstance().encode(testString);
        byte[] decode = Base64.getInstance().decode(encode);
        Assert.isTrue((new String(decode, StandardCharsets.UTF_8)).equals(testString), "不匹配");
    }


    @Test
    public void testAES() {
        String testString = "eamon";
        String key = "1234567890ABCDEF";
        byte[] encode = AES.getInstance().encrypt(testString.getBytes(StandardCharsets.UTF_8), key);
        byte[] decode = AES.getInstance().decrypt(encode, key);
        Assert.isTrue((new String(decode, StandardCharsets.UTF_8)).equals(testString), "不匹配");
    }

    @Test
    public void testRSA() {
        String RSAPublicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDY6gvsAAspCgWMYh/KFvmUGgvl" +
                "j9+28kefXixFlLk1Gq1aa6jjFNqA/fqnkuJLqBJJOwItsdl9PK4tN4WR9P7Unpgs" +
                "4FQcKzyluDfpvPbs9kdSafkV689pj7v8eH/Ddorsc/3WwoR6fdXdKKgXInVjPtqp" +
                "6eDOSIe8XIyXR2U59wIDAQAB";
        String RSAPrivateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBANjqC+wACykKBYxi" +
                "H8oW+ZQaC+WP37byR59eLEWUuTUarVprqOMU2oD9+qeS4kuoEkk7Ai2x2X08ri03" +
                "hZH0/tSemCzgVBwrPKW4N+m89uz2R1Jp+RXrz2mPu/x4f8N2iuxz/dbChHp91d0o" +
                "qBcidWM+2qnp4M5Ih7xcjJdHZTn3AgMBAAECgYEAu3ygzV3ER7aX0R1HKN/u3Soe" +
                "Ok+/KFwFuCQn1ASWiOYEDHGdyplNu8zLCGiXHJmrJIzSdziQKgV27zJcSyodz4Ut" +
                "fjYqDU43uo6QhouisjF9VJo9JgAcEeWd0yfZcOAkwltUJDPzipl+/+ORZ9wLCdxy" +
                "ZR5d+Kz8QW3AYFYoiwECQQD51vo5+n+kqReatNm0oQf0EBOsKF7P0Z1XCM9BCLJg" +
                "7ZAoplqitFEjrQVgind3tsqVZV1IQ/uIbJXy/zPm0q63AkEA3kM9ItitpxDBEtLO" +
                "GrbjcbMui9eQwL8bJdsPeN2bNHuuM5z5xPC3Tr7gkt0QSlEYIGC2HgPLkcdxGSC3" +
                "gYqOwQJAKWtjhpMp8DF8UVCkOxbrS6ISsNrshQWaUSCLw5tef0VDPgn+QrUkMobv" +
                "ukaaccVjJotsgJuMqtxdq7B1eVH6VwJAAMJ6EwRqk4ebIVVXHwBBBsJ2BkRWWlJM" +
                "5XQ6OU+ImEVT8xk2QVYRSlOcsOPQinB8hJ/P/4pDx9vGpy9VcTvoAQJBANfgUf9G" +
                "KBGwPZ2eQy0DAib9iZhlJ6DbURNouezW/4oimm3txUnlf5DxZHQSeiQroNNnirYp" +
                "y2uawWZpngMC0Gg=";

        String testString = "eamon";
        byte[] encode = RSA.getInstance().encrypt(testString.getBytes(StandardCharsets.UTF_8), RSAPublicKey);
        byte[] decode = RSA.getInstance().decrypt(encode, RSAPrivateKey);
        Assert.isTrue((new String(decode, StandardCharsets.UTF_8)).equals(testString), "不匹配");
    }


}
