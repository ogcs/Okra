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

package org.ogcs.utilities.codec;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 * AES Cipher Util.
 *
 * @since 2.0
 */
public final class AES {

    private static final Logger LOG = LogManager.getLogger(AES.class);
    private static final String AES = "AES";
    private static String CIPHER_AES = "AES/CBC/PKCS5Padding";
//    private static byte[] DEFAULT_KEY = "DnA DoubleSpiral".getBytes();  //  16的整数倍
    private static byte[] DEFAULT_IV = new byte[16];

    static {
        CIPHER_AES = System.getProperty("codec.cipher.aes", CIPHER_AES);
    }

    private AES() {
        //  no-op
    }

    // ENCRYPT

    public static String encryptToBase64(byte[] data, byte[] key) {
        return Base64.getEncoder().encodeToString(encrypt(data, key));
    }

    public static byte[] encrypt(byte[] data, byte[] key) {
        return encrypt(CIPHER_AES, AES, data, key, DEFAULT_IV);
    }

    public static byte[] encrypt(String cipherAes, String sks, byte[] data, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(cipherAes);
            SecretKeySpec keySpec = new SecretKeySpec(key, sks);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            LOG.warn("AES encrypt error: data : " + new String(data), e);
            return null;
        }
    }

    // DECRYPT

    public static byte[] decryptFromBase64(String content, byte[] key) {
        return decrypt(Base64.getDecoder().decode(content), key);
    }

    public static byte[] decrypt(byte[] data, byte[] key) {
        return decrypt(CIPHER_AES, AES, data, key, DEFAULT_IV);
    }

    public static byte[] decrypt(String cipherAes, String sks, byte[] data, byte[] key, byte[] iv) {
        try {
            Cipher cipher = Cipher.getInstance(cipherAes);
            SecretKeySpec keySpec = new SecretKeySpec(key, sks);
            IvParameterSpec ivSpec = new IvParameterSpec(iv);
            cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            LOG.warn("AES decrypt error: data : " + new String(data), e);
            return null;
        }
    }
}
