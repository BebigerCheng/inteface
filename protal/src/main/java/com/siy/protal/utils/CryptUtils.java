package com.siy.protal.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 *Created by chengliang on 2018/7/24.
 */
@PropertySource("application.properties")
public class CryptUtils {

    private static final Logger logger = LoggerFactory.getLogger(CryptUtils.class);
    
    private static String key = "AINIXIAO";
    
    private static String way = "DES";
    
    private static String charset = "UTF-8";

    /*@Value("${crypt.key}")
    public void setKey(String key) {
        CryptUtils.key = key;
    }
    @Value("${crypt.way}")
    public void setWay(String way) {
        CryptUtils.way = way;
    }*/

    /**
     * 根据密钥对指定的明文plainText进行加密.
     *
     * @param plainText 明文
     * @return 加密后的密文.
     */
    public static final String encrypt(String plainText) {
        try {

            SecretKey secretKey=new SecretKeySpec(key.getBytes(), way);
            Cipher cipher = Cipher.getInstance(way+"/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] p = plainText.getBytes(charset);
            byte[] result = cipher.doFinal(p);
            BASE64Encoder encoder = new BASE64Encoder();
            String encoded = encoder.encode(result);
            return encoded;
        } catch (Exception e) {
            logger.error(plainText+"加密异常");
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据密钥{}对指定的密文cipherText进行解密.
     *
     * @param cipherText 密文
     * @return 解密后的明文.
     */
    public static final String decrypt(String cipherText) {
        SecretKey secretKey=new SecretKeySpec(key.getBytes(), way);
        try {
            Cipher cipher = Cipher.getInstance(way+"/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] c = decoder.decodeBuffer(cipherText);
            byte[] result = cipher.doFinal(c);
            String plainText = new String(result, charset);
            return plainText;
        } catch (Exception e) {
            logger.error(cipherText+"解密异常");
            throw new RuntimeException(e);
        }
    }


    public static void main(String[] args){
        String chengliang = encrypt("我爱你");
        System.out.println(chengliang);
        String decrypt = decrypt(chengliang);
        System.out.println(decrypt);
    }
}
