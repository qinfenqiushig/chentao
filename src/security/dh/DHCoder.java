package security.dh;

import org.apache.commons.codec.EncoderException;
import security.base.Coder;

import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 非对称加密算法 DH
 * <p/>
 * <p>
 *  流程分析：
 *
 *  甲方构建密钥对儿，将公钥公布给乙方，将私钥保留；双方约定数据加密算法；
 *  乙方通过甲方公钥构建密钥对儿，将公钥公布给甲方，将私钥保留。
 *  甲方使用私钥、乙方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥加密数据，发送给乙方加密后的数据；
 *  乙方使用私钥、甲方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥对数据解密。
 *  乙方使用私钥、甲方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥加密数据，发送给甲方加密后的数据；
 *  甲方使用私钥、乙方公钥、约定数据加密算法构建本地密钥，然后通过本地密钥对数据解密。
 * </p>
 * Created by chentao on 2016/12/21.
 */
public class DHCoder extends Coder {

    private static final String ALGORITHM = "DH";
    /**
     * 默认密钥字节数
     * <p/>
     * <pre>
     * DH
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;
    /**
     * DH加密下需要一种对称加密算法对数据加密，这里我们使用DES，也可以使用其他对称加密算法。
     */
    public static final String SECRET_ALGORITHM = "DES";
    private static final String PUBLIC_KEY = "DHPublicKey";
    private static final String PRIVATE_KEY = "DHPrivateKey";

    /**
     * 初始化甲方密钥
     *
     * @return
     */
    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        KeyPairGenerator pairGenerator = KeyPairGenerator.getInstance(ALGORITHM);
        pairGenerator.initialize(KEY_SIZE);

        KeyPair keyPair = pairGenerator.generateKeyPair();
        // 甲方公钥
        DHPublicKey publicKey = (DHPublicKey) keyPair.getPublic();
        // 乙方私钥
        DHPrivateKey privateKey = (DHPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    /**
     * 通过甲方公钥 生成乙方公钥和私钥
     *
     * @param key 甲方公钥
     * @return
     */
    public static Map<String, Object> initKey(String key) throws Exception {
        // 解析甲方公钥
        byte[] bytes = decryptBase64(key.getBytes());
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(bytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey publicKey = keyFactory.generatePublic(keySpec);

        // 由甲方公钥构建乙方密钥
        DHParameterSpec dhParameterSpec = ((DHPublicKey) publicKey).getParams();
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(keyFactory.getAlgorithm());
        keyPairGenerator.initialize(dhParameterSpec);

        KeyPair keyPair = keyPairGenerator.generateKeyPair();
        // 生成乙方公钥
        DHPublicKey dhPublicKey = (DHPublicKey) keyPair.getPublic();
        // 生成乙方私钥
        DHPrivateKey dhPrivateKey = (DHPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, dhPublicKey);
        keyMap.put(PRIVATE_KEY, dhPrivateKey);
        return keyMap;
    }

    /**
     * 获取公钥
     *
     * @param keyMap
     * @return
     * @throws EncoderException
     */
    public static String getPublicKey(Map<String, Object> keyMap) throws EncoderException {
        PublicKey publicKey = (PublicKey) keyMap.get(PUBLIC_KEY);
        return new String(encryptBase64(publicKey.getEncoded()));
    }

    /**
     * 获取私钥
     *
     * @param keyMap
     * @return
     * @throws EncoderException
     */
    public static String getPrivateKey(Map<String, Object> keyMap) throws EncoderException {
        PrivateKey privateKey = (PrivateKey) keyMap.get(PRIVATE_KEY);
        return new String(encryptBase64(privateKey.getEncoded()));
    }

    /**
     * 构建密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return
     */
    private static SecretKey getSecretKey(String publicKey, String privateKey) throws Exception {
        // 初始化公钥
        byte[] publicKeyBytes = decryptBase64(publicKey.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);

        // 初始化私钥
        byte[] privateKeyBytes = decryptBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        Key priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 生成密钥
        KeyAgreement keyAgreement = KeyAgreement.getInstance(ALGORITHM);
        keyAgreement.init(priKey);
        keyAgreement.doPhase(pubKey, true);
        SecretKey secretKey = keyAgreement.generateSecret(SECRET_ALGORITHM);
        return secretKey;
    }

    /**
     * 加密
     *
     * @param data
     * @param publicKey  甲方公钥（乙方公钥）
     * @param privateKey 乙方私钥（甲方公钥）
     * @return
     */
    public static byte[] encryptDH(byte[] data, String publicKey, String privateKey) throws Exception {
        // 构建本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);

        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    /**
     * 解密
     *
     * @param data
     * @param publicKey  乙方公钥（甲方公钥）
     * @param privateKey 甲方私钥（乙方公钥）
     * @return
     * @throws Exception
     */
    public static byte[] decryptDH(byte[] data, String publicKey, String privateKey) throws Exception {
        // 构建本地密钥
        SecretKey secretKey = getSecretKey(publicKey, privateKey);

        Cipher cipher = Cipher.getInstance(secretKey.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        Map<String, Object> akeyMap = initKey();
        // 甲方公钥
        String aPublicKey = getPublicKey(akeyMap);
        // 甲方私钥
        String aPrivateKey = getPrivateKey(akeyMap);

        // 由甲方公钥构建乙方密钥
        Map<String, Object> bkeyMap = initKey(aPublicKey);
        // 乙方公钥
        String bPublicKey = getPublicKey(bkeyMap);
        // 乙方私钥
        String bPrivateKey = getPrivateKey(bkeyMap);

        String str = "甲DH";
        // 乙方公钥和甲方私钥 加密
        byte[] aEnData = encryptDH(str.getBytes(), bPublicKey, aPrivateKey);
        // 甲方公钥和乙方私钥 解密
        byte[] aDeData = decryptDH(aEnData, aPublicKey, bPrivateKey);

        String s = "乙DH";
        // 甲方公钥和乙方私钥 加密
        byte[] bEnData = encryptDH(s.getBytes(), aPublicKey, bPrivateKey);
        // 乙方公钥和甲方私钥 解密
        byte[] bDeData = decryptDH(bEnData, bPublicKey, aPrivateKey);

        System.out.println("甲方公钥：" + aPublicKey);
        System.out.println("甲方私钥：" + aPrivateKey);
        System.out.println("乙方公钥：" + bPublicKey);
        System.out.println("乙方私钥：" + bPrivateKey);
        System.out.println("乙方公钥和甲方私钥 加密：" + new String(encryptBase64(aEnData)));
        System.out.println("甲方公钥和乙方私钥 解密：" + new String(aDeData));
        System.out.println("甲方公钥和乙方私钥 加密：" + new String(encryptBase64(bEnData)));
        System.out.println("乙方公钥和甲方私钥 解密：" + new String(bDeData));
    }

}
