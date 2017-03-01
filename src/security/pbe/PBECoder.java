package security.pbe;

import security.base.Coder;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.util.Random;

/**
 * 对称加密算法PBE
 *
 * <p>
 *     PBE——Password-based encryption（基于密码加密）。
 *     其特点在于口令由用户自己掌管，不借助任何物理媒体；
 *     采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性。是一种简便的加密方式。
 *     password + salt
 * </p>
 *
 * Created by chentao on 2016/12/20.
 */
public class PBECoder extends Coder{

    /**
     * 支持以下任意一种算法
     *
     * <pre>
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     * </pre>
     */
    public static final String ALGORITHM = "PBEWITHMD5andDES";

    /**
     * 初始化盐
     *
     * @return
     */
    public static byte[] initSalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    /**
     *转换密钥
     *
     * @param password
     * @return
     * @throws Exception
     */
    private static Key toKey(String password) throws Exception {
        PBEKeySpec keySpec = new PBEKeySpec(password.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ALGORITHM);
        SecretKey secretKey = keyFactory.generateSecret(keySpec);
        return  secretKey;
    }

    /**
     * PBE加密
     *
     * @param data
     * @param password
     * @param salt
     * @return
     * @throws Exception
     */
    public static byte[] encryptPBE(byte[] data, String password, byte[] salt) throws Exception {
        Key key = toKey(password);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key, pbeParameterSpec);
        return cipher.doFinal(data);
    }

    /**
     * PBE解密
     *
     * @param data
     * @param password
     * @param salt
     * @return
     * @throws Exception
     */
    public static byte[] decryptPBE(byte[] data, String password, byte[] salt) throws Exception {
        Key key = toKey(password);
        PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, 100);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key, pbeParameterSpec);
        return cipher.doFinal(data);
    }

    public static void main(String[] args) throws Exception {
        String str = "PBE";
        String password = new String(encryptBase64("111111".getBytes()));
        byte[] salt = initSalt();
        System.out.println("加密盐：" + new String(encryptBase64(salt)));
        byte[] encrypt = encryptPBE(str.getBytes(), new String(decryptBase64(password.getBytes())), salt);
        System.out.println("PBE加密后："  + new String(encryptBase64(encrypt)));
        System.out.println("PBE解密后："  + new String(decryptPBE(encrypt, new String(decryptBase64(password.getBytes())), salt)));
    }
}
