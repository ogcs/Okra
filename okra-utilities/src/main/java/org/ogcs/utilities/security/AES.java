/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
