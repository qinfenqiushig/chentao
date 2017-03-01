package security.dsa;

import org.apache.commons.codec.EncoderException;
import security.base.Coder;

import java.security.*;
import java.security.interfaces.DSAPrivateKey;
import java.security.interfaces.DSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 数字签名算法 DSA --- 是单向加密的升级
 *
 * 这是一种更高级的验证方式，用作数字签名。
 * 不单单只有公钥、私钥，还有数字签名。私钥加密生成数字签名，公钥验证数据及签名。
 * 如果数据和签名不匹配则认为验证失败！数字签名的作用就是校验数据在传输过程中不被修改。
 *
 * Created by chentao on 2016/12/22.
 */
public class DSACoder extends Coder{

    private static final String ALGORITHM = "DSA";
    /**
     * 默认密钥字节数
     *
     * <pre>
     * DSA
     * Default Keysize 1024
     * Keysize must be a multiple of 64, ranging from 512 to 1024 (inclusive).
     * </pre>
     */
    private static final int KEY_SIZE = 1024;

    /** 默认种子 */
    private static final String DEFAULT_SEED = "0f22507a10bbddd07d8a3082122966e3";
    private static final String PUBLIC_KEY = "DSAPublicKey";
    private static final String PRIVATE_KEY = "DSAPrivateKey";

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     */
    public static Map<String, Object> initKey(String seed) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ALGORITHM);

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());

        keyPairGenerator.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGenerator.generateKeyPair();

        DSAPublicKey publicKey = (DSAPublicKey) keyPair.getPublic();
        DSAPrivateKey privateKey = (DSAPrivateKey) keyPair.getPrivate();

        Map<String, Object> keyMap = new HashMap<String, Object>();
        keyMap.put(PUBLIC_KEY, publicKey);
        keyMap.put(PRIVATE_KEY, privateKey);
        return keyMap;
    }

    public static Map<String, Object> initKey() throws NoSuchAlgorithmException {
        return initKey(DEFAULT_SEED);
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
     * 生成数字签名
     *
     * @param data
     *              加密的数据
     * @param privateKey
     *              私钥
     * @return
     * @throws Exception
     */
    public static String sign(byte[] data, String privateKey) throws Exception {
        // 构建私钥
        byte[] privateKeyBytes = decryptBase64(privateKey.getBytes());
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PrivateKey priKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);

        // 用私钥对加密数据生成数字签名
        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initSign(priKey);
        signature.update(data);

        return new String(encryptBase64(signature.sign()));
    }

    /**
     * 验证数字签名
     *
     * @param data
     * @param publicKey
     * @param sign
     * @return
     */
    public static boolean verify(byte[] data, String publicKey, String sign) throws Exception {
        // 构建公钥
        byte[] publicKeyBytes = decryptBase64(publicKey.getBytes());
        X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);
        PublicKey pubKey = keyFactory.generatePublic(x509EncodedKeySpec);

        Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
        signature.initVerify(pubKey);
        signature.update(data);

        return signature.verify(decryptBase64(sign.getBytes()));
    }

    public static void main(String[] args) throws Exception {
        String str = "DSA";
        Map<String, Object> keyMap = initKey();
        String privateKey = getPrivateKey(keyMap);
        String publicKey = getPublicKey(keyMap);
        String sign = sign(str.getBytes(), privateKey);

        System.out.println(verify(str.getBytes(), publicKey, sign));
    }

}
