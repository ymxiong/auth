package cc.eamon.open.security.cipher;

import cc.eamon.open.security.CipherMethod;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;


/**
 * Created by Eamon on 2017/7/28.
 */
public class RSA implements CipherMethod {

	private RSA() {}

	private static RSA rsa;

	public static RSA getInstance() {
		if (rsa == null)
			rsa = new RSA();
		return rsa;
	}

	/**
	 * 得到私钥
	 *
	 * @param key 密钥字符串（经过base64编码）
	 * @throws NoSuchAlgorithmException 无加密算法
	 * @throws InvalidKeySpecException 无有效秘钥
	 */
	private PrivateKey getPrivateKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes;
		keyBytes = Base64.getInstance().decode(key);
		PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePrivate(keySpec);
	}


	/**
	 * 得到公钥
	 *
	 * @param key 密钥字符串（经过base64编码）
	 * @throws NoSuchAlgorithmException 无加密算法
	 * @throws InvalidKeySpecException 无有效秘钥
	 */
	private PublicKey getPublicKey(String key) throws NoSuchAlgorithmException, InvalidKeySpecException {
		byte[] keyBytes;
		keyBytes = Base64.getInstance().decode(key);
		X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
		KeyFactory keyFactory = KeyFactory.getInstance("RSA");
		return keyFactory.generatePublic(keySpec);
	}

	/**
	 * 加密
	 * @param content 原文
	 * @param publicKey 商户公钥
	 * @return 加密后的字符流
	 */
	public byte[] encrypt(byte[] content, String publicKey) {
		PublicKey pubKey;
		try {
			pubKey = getPublicKey(publicKey);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, pubKey);

			InputStream ins = new ByteArrayInputStream(content);
			ByteArrayOutputStream writer = new ByteArrayOutputStream();
			// rsa加密的字节大小最多是117，将需要加密的内容，按117位拆开加密
			byte[] buf = new byte[117];
			int bufl;
			while ((bufl = ins.read(buf)) != -1) {
				byte[] block = null;
				if (buf.length == bufl) {
					block = buf;
				} else {
					block = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						block[i] = buf[i];
					}
				}
				writer.write(cipher.doFinal(block));
			}
			return writer.toByteArray();

		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * 解密
	 * @param content 密文
	 * @param privateKey 商户私钥
	 * @return 解密后的字符流
	 */
	public byte[] decrypt(byte[] content, String privateKey) {
		PrivateKey prikey;
		try {
			prikey = getPrivateKey(privateKey);
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.DECRYPT_MODE, prikey);
			InputStream ins = new ByteArrayInputStream(content);
			ByteArrayOutputStream writer = new ByteArrayOutputStream();
			// rsa解密的字节大小最多是128，将需要解密的内容，按128位拆开解密
			byte[] buf = new byte[128];
			int bufl;
			while ((bufl = ins.read(buf)) != -1) {
				byte[] block = null;
				if (buf.length == bufl) {
					block = buf;
				} else {
					block = new byte[bufl];
					for (int i = 0; i < bufl; i++) {
						block[i] = buf[i];
					}
				}
				writer.write(cipher.doFinal(block));
			}
			return writer.toByteArray();
		} catch (NoSuchAlgorithmException | InvalidKeySpecException | NoSuchPaddingException | InvalidKeyException
				| IllegalBlockSizeException | BadPaddingException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

}
