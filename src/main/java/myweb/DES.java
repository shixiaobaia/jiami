package myweb;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;

public class DES {
	private KeyGenerator keygen;
	private SecretKey deskey;
	private Cipher c;
	private byte[] cipherByte;

	public DES() {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		try {
			// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
			keygen = KeyGenerator.getInstance("DES");
			// 生成密钥
			deskey = keygen.generateKey();
			// 生成Cipher对象，指定其支持DES算法
			c = Cipher.getInstance("DES");
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
		} catch (NoSuchPaddingException ex) {
			ex.printStackTrace();
		}
	}

	/* 对字符串str加密 */
	public byte[] createEncryptor(String str) {

		try {
			
			// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
			c.init(Cipher.ENCRYPT_MODE, deskey);
			byte[] src = str.getBytes();
			// 加密，结果保存进cipherByte
			cipherByte = c.doFinal(src);
		}catch(InvalidKeyException ex) {
			ex.printStackTrace();
		}
		catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		} catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return cipherByte;
	}

	public byte[] createDecryptor(byte[] buff) {

		try {
			//根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示解密模式
			c.init(Cipher.DECRYPT_MODE, deskey);
			// 得到明文，存入cipherByte字符数组
			cipherByte = c.doFinal(buff);
		}catch(InvalidKeyException ex) {
			ex.printStackTrace();
		}catch (javax.crypto.BadPaddingException ex) {
			ex.printStackTrace();
		}catch (javax.crypto.IllegalBlockSizeException ex) {
			ex.printStackTrace();
		}
		return cipherByte;
	}
	public static void main(String[] args) throws Exception {
		DES p12_01 = new DES();
		String msg = "郭克华_安全编程技术";
		System.out.println("明文是：" + msg);
		byte[] enc = p12_01.createEncryptor(msg);
		System.out.println("密文是：" + new String(enc));
		byte[] dec = p12_01.createDecryptor(enc);
		System.out.println("解密后的结果是：" + new String(dec));
	}

}
