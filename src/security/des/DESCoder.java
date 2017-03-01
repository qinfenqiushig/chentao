package security.des;

import org.apache.commons.codec.EncoderException;
import security.base.Coder;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.InvalidKeySpecException;

/**
 * 对称加密算法DES & AES
 *
 * 密钥-加密-密钥-解密
 *
 * Created by chentao on 2016/12/19.
 */
public class DESCoder extends Coder{

    /**
     * ALGORITHM 算法 <br>
     * 可替换为以下任意一种算法，同时key值的size相应改变。
     *
     * <pre>
     * DES                  key size must be equal to 56
     * DESede(TripleDES)    key size must be equal to 112 or 168
     * AES                  key size must be equal to 128, 192 or 256,but 192 and 256 bits may not be available
     * Blowfish             key size must be multiple of 8, and can only range from 32 to 448 (inclusive)
     * RC2                  key size must be between 40 and 1024 bits
     * RC4(ARCFOUR)         key size must be between 40 and 1024 bits
     * </pre>
     *
     * 在Key toKey(byte[] key)方法中使用下述代码
     * <code>SecretKey secretKey = new SecretKeySpec(key, ALGORITHM);</code> 替换
     * <code>
     * DESKeySpec dks = new DESKeySpec(key);
     * SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
     * SecretKey secretKey = keyFactory.generateSecret(dks);
     * </code>
     */
    private static final String ALGORITHM = "DES";

    /**
     * 生成密钥
     *
     * @param seed
     * @return
     * @throws NoSuchAlgorithmException
     * @throws EncoderException
     */
    public static String initKey(String seed) throws NoSuchAlgorithmException, EncoderException {
        SecureRandom secureRandom = null;
        if (seed != null)
            secureRandom = new SecureRandom(decryptBase64(seed.getBytes()));
        else
            secureRandom = new SecureRandom();
        KeyGenerator keyGenerator = KeyGenerator.getInstance(ALGORITHM);
        keyGenerator.init(secureRandom);
        SecretKey secretKey = keyGenerator.generateKey();
        return new String(encryptBase64(secretKey.getEncoded()));
    }

    /**
     * 转换密钥
     *
     * @param keyBytes
     * @return
     * @throws InvalidKeyException
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeySpecException
     */
    public static Key toKey(byte[] keyBytes) throws InvalidKeyException, NoSuchAlgorithmException, InvalidKeySpecException {
        DESKeySpec keySpec = new DESKeySpec(decryptBase64(keyBytes));
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);

        //当使用其他加密算法时，如AES、Blowfish等算法时，用下述代码替换上述三行代码
        //SecretKey secretKey = new SecretKeySpec(keyBytes, ALGORITHM);
        return secretKey;
    }

    /**
     * DES加密
     *
     * @param bytes
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] encryptDES(byte[] bytes, String key) throws Exception {
        Key secretKey = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);
        return cipher.doFinal(bytes);
    }

    /**
     * DES解密
     *
     * @param bytes
     * @param key
     * @return
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] bytes, String key) throws Exception{
        Key secretKey = toKey(key.getBytes());
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, secretKey);
        return cipher.doFinal(bytes);
    }

    public static void main(String[] args) throws Exception {
        String str = "DES";
        String key = initKey(null);
        System.out.println("DES密钥： " + key);
        byte[] encoder = encryptDES(str.getBytes(), key);
        System.out.println("DES加密后：" + new String(encryptBase64(encoder)));
        System.out.println("DES解密后：" + new String(decryptDES(encoder, key)));
    }

}
