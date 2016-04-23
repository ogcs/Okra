package org.ogcs.utilities.security;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;

/**
 * AES加密工具
 */
public class AES {

    public static void main(String[] args) throws IOException {
        byte[] key = "lingjingdnsg2015".getBytes();
        byte[] iv = "lingjingdnsg2015".getBytes();

        String encrypt = new BASE64Encoder().encode(AES.encrypt("我是一个大好人啊呢".getBytes(), key, iv));
        System.out.println(encrypt.length());
        System.out.println(encrypt);
        byte[] bytes = new BASE64Decoder().decodeBuffer(encrypt);
        String decrypt = String.valueOf(AES.decrypt(bytes, key, iv));
        System.out.println(decrypt.length());
        System.out.println(decrypt);

        String decrypt1 = String.valueOf(AES.decrypt(new BASE64Decoder().decodeBuffer("X1mmmEFSTGaQDShFy/K8r8+dp0Q3OekaxaBNxOGo+sg="), key, iv));
        System.out.println(decrypt1.length());
        System.out.println(decrypt1);
    }

    public static final String IV = "";

    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding"); // NoPadding
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
