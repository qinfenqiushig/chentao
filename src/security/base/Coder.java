package security.base;

import org.apache.commons.codec.EncoderException;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * BASE64与单向加密算法
 *
 * 单向加密：只能加密，无法解密，常用于唯一性校验
 *
 * Created by chentao on 2016/12/19.
 */
public abstract class Coder {

    private static final String KEY_MD5 = "MD5";

    private static final String KEY_SHA = "SHA";
    /**
     * MAC算法可选以下多种算法
     *
     * <pre>
     * HmacMD5
     * HmacSHA1
     * HmacSHA256
     * HmacSHA384
     * HmacSHA512
     * </pre>
     */
    public static final String KEY_MAC = "HmacMD5";

    public static byte[] encryptBase64(byte[] bytes) throws EncoderException {
        return new Base64().encode(bytes);
    }

    public static byte[] decryptBase64(byte[] bytes) {
        return new Base64().decode(bytes);
    }

    /**
     * <p>
     *  MD5 -- message-digest algorithm 5 （信息-摘要算法）缩写，
     *  广泛用于加密和解密技术，常用于文件校验。
     *  校验？不管文件多大，经过MD5后都能生成唯一的MD5值。好比现在的ISO校验，都是MD5校验。
     *  怎么用？当然是把ISO经过MD5后产生MD5的值。一般下载linux-ISO的朋友都见过下载链接旁边放着MD5的串。就是用来验证文件是否一致的。
     *
     *  通常我们不直接使用MD5加密。通常将MD5产生的字节数组交给BASE64再加密一把，得到相应的字符串。
     * </p>
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encryptMd5(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(KEY_MD5);
        return messageDigest.digest(bytes);
    }

    /**
     *<p>
     *   SHA(Secure Hash Algorithm，安全散列算法），
     *   数字签名等密码学应用中重要的工具，被广泛地应用于电子商务等信息安全领域。
     *   虽然，SHA与MD5通过碰撞法都被破解了， 但是SHA仍然是公认的安全加密算法，较之MD5更为安全。
     *</p>
     *
     * @param bytes
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] encryptSHA(byte[] bytes) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance(KEY_SHA);
        return  messageDigest.digest(bytes);
    }

    /**
     * 初始化Mac密钥
     *
     * @return
     * @throws NoSuchAlgorithmException
     * @throws EncoderException
     */
    public static String initMacKey() throws NoSuchAlgorithmException, EncoderException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(KEY_MAC);
        SecretKey secretKey = keyGenerator.generateKey();
        return new String(encryptBase64(secretKey.getEncoded()));
    }

    /**
     * <p>
     *    HMAC(Hash Message Authentication Code，散列消息鉴别码，基于密钥的Hash算法的认证协议。
     *    消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
     *    使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。接收方利用与发送方共享的密钥进行鉴别认证等。
     * </p>
     *
     * @param bytes
     * @param key
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     */
    public static byte[] encryptHMAC(byte[] bytes, String key) throws NoSuchAlgorithmException, InvalidKeyException {
        SecretKey secretKey = new SecretKeySpec(decryptBase64(key.getBytes()), KEY_MAC);
        Mac mac = Mac.getInstance(secretKey.getAlgorithm());
        mac.init(secretKey);
        return mac.doFinal(bytes);
    }

//    public static void main(String[] args) throws Exception {
//        String str = "111111";
//        System.out.println(new BigInteger(encryptMd5(str.getBytes())).toString());
//        System.out.println(new BigInteger(encryptSHA(str.getBytes())).toString());
//        String key = initMacKey();
//        System.out.println(key);
//        System.out.println(new BigInteger(encryptHMAC(str.getBytes(), key)));
//    }
}
