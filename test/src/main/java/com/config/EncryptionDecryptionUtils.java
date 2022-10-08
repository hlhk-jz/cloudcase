package com.config;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import javax.crypto.Cipher;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Arrays;
/**
 * RSA 加密/解密
 */
public class EncryptionDecryptionUtils {
    private final static String RSA = "RSA";
    /**
     * 非对称加密数据
     * @param input : 原文
     * @param key   : 密钥
     * @return : 密文
     * @throws Exception
     */
    public static String encryptByAsymmetric(String input, Key key) {
        try {
            // 获取Cipher对象
            Cipher cipher = Cipher.getInstance(RSA);
            // 初始化模式(加密)和密钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] resultBytes = getMaxResultEncrypt(input, cipher);
            return Base64.encode(resultBytes);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("加密失败！");
        }
    }

    /**
     * 分段处理加密数据
     *
     * @param input  : 加密文本
     * @param cipher : Cipher对象
     * @return
     */
    private static byte[] getMaxResultEncrypt(String input, Cipher cipher) throws Exception {
        byte[] inputArray = input.getBytes();
        int inputLength = inputArray.length;
        // 最大加密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 117;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return resultBytes;
    }

    /**
     * 非对称解密数据
     * @param encrypted : 密文
     * @param key       : 密钥
     * @return : 原文
     * @throws Exception
     */
    public static String decryptByAsymmetric(String encrypted, Key key) {
        try {
            // 获取Cipher对象
            Cipher cipher = Cipher.getInstance(RSA);
            // 初始化模式(解密)和密钥
            cipher.init(Cipher.DECRYPT_MODE, key);
            return new String(getMaxResultDecrypt(encrypted, cipher));
        } catch (
                Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解密失败！");
        }
    }

    /**
     * 分段处理解密数据
     * @param decryptText : 加密文本
     * @param cipher      : Cipher对象
     * @throws Exception
     */
    private static byte[] getMaxResultDecrypt(String decryptText, Cipher cipher) throws Exception {
        byte[] bytes = decryptText.getBytes(StandardCharsets.UTF_8);
        byte[] inputArray =java.util.Base64.getDecoder().decode(bytes);
        int inputLength = inputArray.length;

        // 最大解密字节数，超出最大字节数需要分组加密
        int MAX_ENCRYPT_BLOCK = 128;
        // 标识
        int offSet = 0;
        byte[] resultBytes = {};
        byte[] cache = {};
        while (inputLength - offSet > 0) {
            if (inputLength - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(inputArray, offSet, MAX_ENCRYPT_BLOCK);
                offSet += MAX_ENCRYPT_BLOCK;
            } else {
                cache = cipher.doFinal(inputArray, offSet, inputLength - offSet);
                offSet = inputLength;
            }
            resultBytes = Arrays.copyOf(resultBytes, resultBytes.length + cache.length);
            System.arraycopy(cache, 0, resultBytes, resultBytes.length - cache.length, cache.length);
        }
        return resultBytes;
    }
}
