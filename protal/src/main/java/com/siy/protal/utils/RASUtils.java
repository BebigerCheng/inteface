package com.siy.protal.utils;

import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;


public class RASUtils {
    private static final Logger logger = LoggerFactory.getLogger(RASUtils.class);
    
    public static final String KEY_ALGORITHM = "RSA";
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    public final static String CONTENT_TYPE = "UTF-8";

    /**
     * 用私钥对信息生成数字签名  
     *
     * @param data
     *            加密数据  
     * @param privateKey
     *            私钥  
     *
     * @return
     * @throws Exception
     */
    public static String signByPrivate(byte[] data, String privateKey) throws Exception {
        // 解密由base64编码的私钥     
        byte[] keyBytes = Base64.decodeBase64(privateKey.getBytes(CONTENT_TYPE));

        // 构造PKCS8EncodedKeySpec对象     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法     
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取私钥匙对象     
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 用私钥对信息生成数字签名     
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(priKey);
        signature.update(data);
        String signString = new String(Base64.encodeBase64(signature.sign()));
        return signString;
    }

    /**
     * 校验数字签名  
     *
     * @param data
     *            加密数据  
     * @param publicKey
     *            公钥  
     * @param sign
     *            数字签名  
     *
     * @return 校验成功返回true 失败返回false  
     * @throws Exception
     *
     */
    public static boolean verifyByPublicKey(byte[] data, String publicKey, String sign)
            throws Exception {

        // 解密由base64编码的公钥     
        byte[] keyBytes = Base64.decodeBase64(publicKey.getBytes(CONTENT_TYPE));

        // 构造X509EncodedKeySpec对象     
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);

        // KEY_ALGORITHM 指定的加密算法     
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);

        // 取公钥匙对象     
        PublicKey pubKey = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(pubKey);
        signature.update(data);

        // 验证签名是否正常     
        return signature.verify(Base64.decodeBase64(sign.getBytes()));
    }

    /**
     * 解密 
     * 用私钥解密  
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPrivateKey(String data, String key)
            throws Exception {
        // 对密钥解密     
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(CONTENT_TYPE));

        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // 取得私钥     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据解密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, privateKey);
        String [] dateStr = new String(Base64.decodeBase64(data.getBytes(CONTENT_TYPE))).split("~~");
        for(int i = 0;i < dateStr.length; i ++){
            writer.write(cipher.doFinal(hexStrToBytes(dateStr[i])));
        }

        return new String(writer.toByteArray(), "utf-8");
    }

    /**
     * 解密
     * 用公钥解密  
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String decryptByPublicKey(String data, String key)
            throws Exception {
        // 对密钥解密     
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(CONTENT_TYPE));
        ByteArrayOutputStream writer = new ByteArrayOutputStream();
        // 取得公钥     
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据解密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicKey);
        String [] dateStr = new String(Base64.decodeBase64(data.getBytes(CONTENT_TYPE))).split("~~");
        for(int i = 0;i < dateStr.length; i ++){
            writer.write(cipher.doFinal(hexStrToBytes(dateStr[i])));
        }

        return new String(writer.toByteArray(), CONTENT_TYPE);
    }

    /**
     * 加密<br>  
     * 用公钥加密  
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPublicKey(byte[] data, String key)
            throws Exception {
        // 对公钥解密     
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(CONTENT_TYPE));

        // 取得公钥     
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key publicKey = keyFactory.generatePublic(x509KeySpec);

        // 对数据加密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, publicKey);
        StringBuffer priString = new StringBuffer();
        byte[] priTemp = new byte[64];
        for(int i = 0,x = 0; i <data.length;i++,x++){
            if(x == 64){
                priString.append(bytesToHexStr(cipher.doFinal(priTemp))).append("~~");
                x = 0;
                priTemp = new byte[64];
            }
            priTemp[x] = data[i];
            if(i+1 == data.length){
                priString.append(bytesToHexStr(cipher.doFinal(priTemp))).append("~~");
            }
        }
        return new String(Base64.encodeBase64(priString.toString().getBytes(CONTENT_TYPE)));
    }

    /**
     * 加密<br>  
     * 用私钥加密  
     *
     * @param data
     * @param key
     * @return
     * @throws Exception
     */
    public static String encryptByPrivateKey(byte[] data, String key)
            throws Exception {
        // 对密钥解密     
        byte[] keyBytes = Base64.decodeBase64(key.getBytes(CONTENT_TYPE));

        // 取得私钥     
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key privateKey = keyFactory.generatePrivate(pkcs8KeySpec);

        // 对数据加密     
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateKey);
        StringBuffer priString = new StringBuffer();
        byte[] priTemp = new byte[64];
        for(int i = 0,x = 0; i <data.length;i++,x++){
            if(x == 64){
                priString.append(bytesToHexStr(cipher.doFinal(priTemp))).append("~~");
                x = 0;
                priTemp = new byte[64];
            }
            priTemp[x] = data[i];
            if(i+1 == data.length){
                priString.append(bytesToHexStr(cipher.doFinal(priTemp))).append("~~");
            }
        }


        return new String(Base64.encodeBase64(priString.toString().getBytes(CONTENT_TYPE)));
    }

    /**
     * 取得私钥  
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPrivateKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PRIVATE_KEY);

        return new String(Base64.encodeBase64(key.getEncoded()));
    }

    /**
     * 取得公钥  
     *
     * @param keyMap
     * @return
     * @throws Exception
     */
    public static String getPublicKey(Map<String, Object> keyMap)
            throws Exception {
        Key key = (Key) keyMap.get(PUBLIC_KEY);

        return new String(Base64.encodeBase64(key.getEncoded()));
    }

    /**
     * 初始化密钥  
     *
     * @return
     * @throws Exception
     */
    public static Map<String, Object> initKey() throws Exception {
        KeyPairGenerator keyPairGen = KeyPairGenerator
                .getInstance(KEY_ALGORITHM);
        keyPairGen.initialize(1024);

        KeyPair keyPair = keyPairGen.generateKeyPair();

        // 公钥     
        RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();

        // 私钥     
        RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>(2);

        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static String bytesToString(byte[] encrytpByte) {
        String result = "";
        for (Byte bytes : encrytpByte) {
            result += bytes.toString() + " ";
        }
        return result;
    }

    /* 
     * 将16进制字符串转换为字节数组 
     */
    
    
    public static byte[] hexStrToBytes(String s)
    {
        byte[] bytes;

        bytes = new byte[s.length() / 2];

        for (int i = 0; i < bytes.length; i++)
        {
            bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2), 16);
        }

        return bytes;
    }


    /**
     * 将16进制字符串转换为byte[]
     *
     * @param str
     * @return
     */
    public static byte[] toBytes(String str) {
        if(str == null || str.trim().equals("")) {
            return new byte[0];
        }

        byte[] bytes = new byte[str.length() / 2];
        for(int i = 0; i < str.length() / 2; i++) {
            String subStr = str.substring(i * 2, i * 2 + 2);
            bytes[i] = (byte) Integer.parseInt(subStr, 16);
        }

        return bytes;
    }
    
    /* 
    * 将字符数组转换为16进制字符串 
    */
    public static String bytesToHexStr(byte[] bcd)
    {
        StringBuffer s = new StringBuffer(bcd.length * 2);

        for (int i = 0; i < bcd.length; i++)
        {
            s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
            s.append(bcdLookup[bcd[i] & 0x0f]);
        }

        return s.toString();
    }

    private static final char[] bcdLookup =
            { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };


    /**
     * char的字符数据类型，是无符号型的，占2个字节；大小范围是0-65535；

     *byte是字节数据类型，是有符号型的，占1个字节；大小范围为-128-127；
     * 字符串转换为16进制字符串
     *
     * @param charsetName 用于编码 String 的 Charset
     */
    public static String Str2hexStr(String str, String charsetName) {
        byte[] bytes = new byte[0];
        String hexString = "0123456789abcdef";
        // 使用给定的 charset 将此 String 编码到 byte 序列
        if (!charsetName.equals("")) {
            try {
                bytes = str.getBytes(charsetName);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            // 根据默认编码获取字节数组
            bytes = str.getBytes();
        }
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        // 将字节数组中每个字节拆解成2位16进制整数
        for (int i = 0; i < bytes.length; i++) {
            sb.append(hexString.charAt((bytes[i] & 0xf0) >> 4));
            sb.append(hexString.charAt((bytes[i] & 0x0f)));
        }
        return sb.toString();
    }

    public static void main(String[] args){
        try {
            /*byte[] bytes = "我".getBytes();
            String chengliang = Str2hexStr("我", "utf-8");
            byte[] bytes1 = toBytes("e68891");
            byte[] bytes2 = hexStrToBytes("e68891");*/
            
            
            //初始化秘钥
            Map<String, Object> objectMap = initKey();
            //获取私钥
            String privateKey = getPrivateKey(objectMap);
            //获取公钥
            String publicKey = getPublicKey(objectMap);
            //用公钥加密
            String s = encryptByPublicKey("chengliang".getBytes(), publicKey);
            System.out.println(s);
            //用私钥解密
            String s2 = decryptByPrivateKey(s, privateKey);
            System.out.println(s2);
            /*//用公钥解密
            String s1 = decryptByPublicKey(s, publicKey);
            System.out.println(s1);*/

        } catch (Exception e) {
            logger.error("",e);
        }
        
    }

}
