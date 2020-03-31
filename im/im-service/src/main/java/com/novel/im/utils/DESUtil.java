/*
 * 作者：刘时明
 * 时间：2020/3/21-10:39
 * 作用：
 */
package com.novel.im.utils;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class DESUtil
{
    /*
     * 生成密钥
     */
    public static String getKey() throws Exception
    {
        KeyGenerator keyGen = KeyGenerator.getInstance("DES");
        keyGen.init(56);
        SecretKey secretKey = keyGen.generateKey();
        return new String(secretKey.getEncoded());
    }


    /*
     * DES 加密
     */
    public static String encrypt(String content, String key) throws Exception
    {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return new String(cipher.doFinal(content.getBytes()));
    }


    /*
     * DES 解密
     */
    public static String decrypt(String content, String key) throws Exception
    {
        SecretKey secretKey = new SecretKeySpec(key.getBytes(), "DES");
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        byte[] plainBytes = cipher.doFinal(content.getBytes());
        return new String(plainBytes);
    }
}
