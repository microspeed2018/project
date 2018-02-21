package com.zjzmjr.core.sms.util;

import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import com.zjzmjr.core.base.exception.ApplicationException;

/**
 * AES加密
 * 
 * @author Administrator
 * @version $Id: WandaAES.java, v 0.1 2017-2-7 下午3:48:37 Administrator Exp $
 */
public class WandaAES {

    // AES config
    private final static Integer AES_SIZE_128 = 128;

    private final static String ALGORITHM_AES = "AES";

    private static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";

    private static final String BC_PROVIDER = "BC";

    // public static final String DEFAULT_ROOT_IV = "aabbccddeeffgghh";
    private static final String DEFAULT_ROOT_IV = "0000000000000000";

    private static final String DEFAULT_CHARSET = "UTF-8";

    static {
        Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
    }

    private final byte[] passwordBytes;

    private final Integer keySize;

    public WandaAES(byte[] passwordBytes) {
        this(passwordBytes, AES_SIZE_128);
    }

    public WandaAES(byte[] passwordBytes, Integer keySize) {
        this.passwordBytes = passwordBytes;
        this.keySize = keySize;
    }

    protected Cipher getEncryptCipher() throws ApplicationException {
        return getCipher(Cipher.ENCRYPT_MODE);
    }

    protected Cipher getDecryptCipher() throws ApplicationException {
        return getCipher(Cipher.DECRYPT_MODE);
    }

    // cipher 不是线程安全的，如果需要性能上的考虑，
    // 使用cache的方案来实现，暂时应该不需要
    protected Cipher getCipher(Integer mode) throws ApplicationException {
        try {
            SecretKeySpec key = new SecretKeySpec(passwordBytes, ALGORITHM_AES);
            Cipher cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC, BC_PROVIDER);// 创建密码器
            cipher.init(mode, key, new IvParameterSpec(DEFAULT_ROOT_IV.getBytes()));// 初始化
            return cipher;
        } catch (Exception e) {
            throw new ApplicationException("Failed to get the cipher with passwordBytes [" + passwordBytes + "] key size + [" + keySize + "] mode [" + mode + "]", e);
        }
    }

    /**
     * 加密
     * 
     * @param content
     *            需要加密的内容
     * @return
     */
    public byte[] encrypt(String content) throws ApplicationException {
        try {
            byte[] byteContent = content.getBytes(DEFAULT_CHARSET);
            return encrypt(byteContent);
        } catch (Exception e) {
            throw new ApplicationException("failed to encryptInputStream the content [" + content + "]", e);
        }
    }

    public byte[] decrypt(String content) throws ApplicationException {
        try {
            byte[] byteContent = content.getBytes(DEFAULT_CHARSET);
            return decrypt(byteContent);
        } catch (Exception e) {
            throw new ApplicationException("failed to decryptOutputStream the content [" + content + "]", e);
        }
    }

    public byte[] encrypt(byte[] content) throws ApplicationException {
        try {
            return getEncryptCipher().doFinal(content);
        } catch (Exception e) {
            throw new ApplicationException("failed to encryptInputStream the content [" + content + "]", e);
        }
    }

    public byte[] decrypt(byte[] content) throws ApplicationException {
        try {
            return getDecryptCipher().doFinal(content);
        } catch (Exception e) {
            throw new ApplicationException("failed to Decrypt the content [" + content + "]", e);
        }
    }

    // public InputStream encryptInputStream(InputStream is) throws
    // ApplicationException {
    // return new CipherInputStream(is, getEncryptCipher());
    // }
    // public InputStream decryptInputStream(InputStream is) throws
    // ApplicationException {
    // return new CipherInputStream(is, getDecryptCipher());
    // }
    //
    // public OutputStream encryptOutputStream(OutputStream os) throws
    // ApplicationException {
    // return new CipherOutputStream(os, getEncryptCipher());
    // }
    //
    // public OutputStream decryptOutputStream(OutputStream os) throws
    // ApplicationException {
    // return new CipherOutputStream(os, getDecryptCipher());
    // }

}
