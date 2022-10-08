package com.config;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import org.apache.commons.io.FileUtils;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 *
 */
public class SecretKeyUtils {
    private final static String RSA = "RSA";
    /**
     * 生成公私秘钥并写到文件
     * @return
     */
    public static void generateKeyPair(String pubPath, String priPath) {
        try {
            //  创建密钥对生成器对象
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA);
            // 生成密钥对
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            PrivateKey privateKey = keyPair.getPrivate();
            PublicKey publicKey = keyPair.getPublic();
            String privateKeyString = Base64.encode(privateKey.getEncoded());
            String publicKeyString = Base64.encode(publicKey.getEncoded());
            System.out.println("私钥：" + privateKeyString);
            System.out.println("公钥：" + publicKeyString);
            // 保存文件
            if (pubPath != null) {
                FileUtils.writeStringToFile(new File(pubPath), publicKeyString, String.valueOf(StandardCharsets.UTF_8));
            }
            if (priPath != null) {
                FileUtils.writeStringToFile(new File(priPath), privateKeyString, String.valueOf(StandardCharsets.UTF_8));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("生成密钥对失败！");
        }
    }

    /**
     * 从文件中加载公钥
     * @param filePath : 文件路径
     * @return : 公钥
     * @throws Exception
     */
    public static PublicKey loadPublicKeyFromFile(String filePath) {
        try {
            // 将文件内容转为字符串
            String keyString = FileUtils.readFileToString(new File(filePath), String.valueOf(StandardCharsets.UTF_8));
            return loadPublicKeyFromString(keyString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公钥文件字符串失败！");
        }
    }

    /**
     * 从字符串中加载公钥
     * @param keyString : 公钥
     * @return : 公钥
     * @throws Exception
     */
    public static PublicKey loadPublicKeyFromString(String keyString) {
        try {
            // 进行Base64解码
            byte[] decode = Base64.decode(keyString);
            // 获取密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 构建密钥规范
            X509EncodedKeySpec key = new X509EncodedKeySpec(decode);
            // 获取公钥
            return keyFactory.generatePublic(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取公钥失败！");
        }
    }

    /**
     * 从文件中加载私钥
     * @param filePath : 文件路径
     * @return : 私钥
     * @throws Exception
     */
    public static PrivateKey loadPrivateKeyFromFile(String filePath) {
        try {
            // 将文件内容转为字符串
            String keyString = FileUtils.readFileToString(new File(filePath), String.valueOf(StandardCharsets.UTF_8));
            return loadPrivateKeyFromString(keyString);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取私钥文件字符串失败！");
        }
    }

    /**
     * 从字符串中加载私钥
     *
     * @param keyString : 私钥
     * @return : 私钥
     * @throws Exception
     */
    public static PrivateKey loadPrivateKeyFromString(String keyString) {
        try {
            // 进行Base64解码
            byte[] decode = Base64.decode(keyString);
            // 获取密钥工厂
            KeyFactory keyFactory = KeyFactory.getInstance(RSA);
            // 构建密钥规范
            PKCS8EncodedKeySpec key = new PKCS8EncodedKeySpec(decode);
            // 生成私钥
            return keyFactory.generatePrivate(key);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("获取私钥失败！");
        }
    }
}
