package security.rsa;

import org.apache.commons.codec.EncoderException;
import security.base.Coder;

import javax.crypto.Cipher;
import java.security.*;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称算法 RSA
 *
 *<p>
 *      这种算法1978年就出现了，它是第一个既能用于数据加密也能用于数字签名的算法。它易于理解和操作，也很流行。算法的名字以发明者的名字命名：Ron Rivest, AdiShamir 和Leonard Adleman。
 * 这种加密算法的特点主要是密钥的变化，上文我们看到DES只有一个密钥。相当于只有一把钥匙，如果这把钥匙丢了，数据也就不安全了。RSA同时有两把钥匙，公钥与私钥。同时支持数字签名。数字签名的意义在于，对传输过来的数据进行校验。确保数据在传输工程中不被修改。
 *
 * 流程分析：
 *
 * 甲方构建密钥对，将公钥公布给乙方，将私钥保留。
 * 甲方使用私钥加密数据，然后用私钥对加密后的数据签名，发送给乙方签名以及加密后的数据；
 * 乙方使用公钥、签名来验证待解密数据是否有效，如果有效使用公钥对数据解密。
 * 乙方使用公钥加密数据，向甲方发送经过加密后的数据；甲方获得加密数据，通过私钥解密。
 * </p>
 *
 * Created by chentao on 2016/12/20.
 */
public class RSACoder extends Coder{

    private static final String KEY_ALGORITHM = "RSA";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String PUBLIC_KEY = "RSAPublicKey";
    private static final String PRIVATE_KEY = "RSAPrivateKey";

    private static final Map<String, Object> keyMap = new HashMap<String, Object>();

    /**
     * 初始化密钥
     *
     */
    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(KEY_ALGORITHM);
            // 初始化key的大小
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();

            // 公钥
            RSAPublicKey publicKey = (RSAPublicKey) keyPair.getPublic();
            // 私钥
            RSAPrivateKey privateKey = (RSAPrivateKey) keyPair.getPrivate();

            keyMap.put(PUBLIC_KEY, publicKey);
            keyMap.put(PRIVATE_KEY, privateKey);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获得公钥
     *
     * @return
     * @throws EncoderException
     */
    public static String getPublicKey() throws EncoderException {
        RSAPublicKey publicKey = (RSAPublicKey) keyMap.get(PUBLIC_KEY);
        return new String(encryptBase64(publicKey.getEncoded()));
    }

    /**
     * 获得私钥
     *
     * @return
     * @throws EncoderException
     */
    public static String getPrivateKey() throws EncoderException {
        RSAPrivateKey privateKey = (RSAPrivateKey) keyMap.get(PRIVATE_KEY);
        return new String(encryptBase64(privateKey.getEncoded()));
    }

    /**
     * 用私钥加密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        // 构建私钥
        byte[] keyBytes = decryptBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key key = keyFactory.generatePrivate(keySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥加密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // 构建公钥
        byte[] keyBytes = decryptBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key key = keyFactory.generatePublic(keySpec);

        // 对数据加密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 用公钥解密
     *
     * @param data
     * @param publicKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] data, String publicKey) throws Exception {
        // 构建公钥
        byte[] keyBytes = decryptBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key key = keyFactory.generatePublic(keySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 用私钥解密
     *
     * @param data
     * @param privateKey
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] data, String privateKey) throws Exception {
        // 构建私钥
        byte[] keyBytes = decryptBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        Key key = keyFactory.generatePrivate(keySpec);

        // 对数据解密
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, key);
        return cipher.doFinal(data);
    }

    /**
     * 用私钥对信息生成数字签名
     *
     * @param data
     * @param privateKey
     * @return
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 构建私钥
        byte[] keyBytes = decryptBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey key = keyFactory.generatePrivate(keySpec);

        // 生成数字签名
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(key);
        signature.update(data);
        return new String(encryptBase64(signature.sign()));
    }

    /**
     * 校验数字签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     * @throws Exception
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // 构建公钥
        byte[] keyBytes = decryptBase64(publicKey.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PublicKey key = keyFactory.generatePublic(keySpec);

        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initVerify(key);
        signature.update(data);
        return  signature.verify(decryptBase64(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        // 1.甲方数据
        String str = "甲-乙";
        // 2.甲：生成公钥和私钥
        String privateKey = getPrivateKey();
        String publicKey = getPublicKey();


        // 3.甲：私钥对信息加密
        byte[] encPrivate = encryptByPrivateKey(str.getBytes(), privateKey);
        // 4.甲：私钥加密后的信息生成数字签名
        String sign = sign(encPrivate, privateKey);

        // 5.乙：用公钥验证数据签名
        boolean isVerify = verify(encPrivate, publicKey, sign);
        // 6.乙：公钥对信息解密
        byte[] bytesPublic = decryptByPublicKey(encPrivate, publicKey);

        // 乙方数据
        String data = "乙-甲";
        // 7.乙：公钥对信息加密
        byte[] encPublic = encryptByPublicKey(data.getBytes(), publicKey);
        // 8.甲：用私钥解密信息
        byte[] bytesPrivate = decryptByPrivateKey(encPublic, privateKey);

        System.out.println("甲：生成公钥：" + publicKey);
        System.out.println("甲：生成私钥：" + privateKey);
        System.out.println("甲：私钥加密数据：" + new String(encryptBase64(encPrivate)));
        System.out.println("甲：用加密后的数据生成数字签名：" + sign);
        System.out.println("乙：用数字签名对甲传来的加密数据验证：" + isVerify);
        System.out.println("乙：公钥解密甲传来的加密数据：" + new String(bytesPublic));
        System.out.println("乙：用公钥加密数据：" + new String(encryptBase64(encPublic)));
        System.out.println("甲：用私钥解密乙传来的加密数据：" + new String(bytesPrivate));
    }
}
