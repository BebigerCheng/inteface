package learn.encryption;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.util.Arrays;

/**
 *Created by chengliang on 2018/7/24.
 */
public class JCE {

    /**
     * 根据密钥对指定的明文plainText进行加密.
     *
     * @param plainText 明文
     * @return 加密后的密文.
     */
    public static final String encrypt(String plainText) {
        try {

            SecretKey secretKey=new SecretKeySpec("1234567812345678".getBytes(), "AES");
            
//            SecretKeyFactory sf = SecretKeyFactory.getInstance("AES");
//            SecretKey secretKey = sf.generateSecret(new DESKeySpec("88U8hmb888U8hmb8".getBytes("utf-8")));
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            byte[] p = plainText.getBytes("UTF-8");
            byte[] result = cipher.doFinal(p);
            BASE64Encoder encoder = new BASE64Encoder();
            String encoded = encoder.encode(result);
            return encoded;
        } catch (Exception e) {
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
        SecretKey secretKey=new SecretKeySpec("1234567812345678".getBytes(), "AES");
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            BASE64Decoder decoder = new BASE64Decoder();
            byte[] c = decoder.decodeBuffer(cipherText);
            byte[] result = cipher.doFinal(c);
            String plainText = new String(result, "UTF-8");
            return plainText;
        } catch (Exception e) {
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
