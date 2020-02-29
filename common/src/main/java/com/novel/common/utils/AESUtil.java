/*
 * 作者：刘时明
 * 时间：2020/1/9-11:46
 * 作用：
 */
package com.novel.common.utils;

import org.apache.commons.codec.binary.Base64;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.security.Security;

public class AESUtil
{
    private static final String EncryptAlg = "AES";

    private static final String Cipher_Mode = "AES/ECB/PKCS7Padding";

    private static final String Encode = "UTF-8";

    private static final int Secret_Key_Size = 32;

    private static final String Key_Encode = "UTF-8";

    /**
     * AES/ECB/PKCS7Padding 加密
     *
     * @param content
     * @param key     密钥
     * @return aes加密后 转base64
     * @throws Exception
     */
    public static String aesPKCS7PaddingEncrypt(String content, String key)
    {
        try
        {
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance(Cipher_Mode);
            byte[] realKey = getSecretKey(key);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(realKey, EncryptAlg));
            byte[] data = cipher.doFinal(content.getBytes(Encode));
            String result = new Base64().encodeToString(data);
            return result;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * AES/ECB/PKCS7Padding 解密
     *
     * @param content
     * @param key     密钥
     * @return 先转base64 再解密
     * @throws Exception
     */
    public static String aesPKCS7PaddingDecrypt(String content, String key)
    {
        try
        {
            byte[] decodeBytes = Base64.decodeBase64(content);
            Cipher cipher = Cipher.getInstance(Cipher_Mode);
            byte[] realKey = getSecretKey(key);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(realKey, EncryptAlg));
            byte[] realBytes = cipher.doFinal(decodeBytes);

            return new String(realBytes, Encode);
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 对密钥key进行处理：如密钥长度不够位数的则 以指定paddingChar进行填充；
     * 此处用空格字符填充，也可以 0 填充，具体可根据实际项目需求做变更
     *
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] getSecretKey(String key)
    {
        try
        {
            final byte paddingChar = ' ';
            byte[] realKey = new byte[Secret_Key_Size];
            byte[] byteKey = key.getBytes(Key_Encode);
            for (int i = 0; i < realKey.length; i++)
            {
                if (i < byteKey.length)
                {
                    realKey[i] = byteKey[i];
                } else
                {
                    realKey[i] = paddingChar;
                }
            }
            return realKey;
        } catch (Exception e)
        {
            e.printStackTrace();
            return null;
        }
    }
}
