package com.zjzmjr.core.sms.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;

import org.apache.commons.codec.binary.Base64;
import org.apache.log4j.Logger;

//import com.buybal.epay.util.PayEncrypt;
//import com.lakala.aps.commons.ByteArrayUtil;
//import com.lakala.aps.commons.JsonUtil;


public class RSAUtil {
	private static final Logger logger = Logger.getLogger(RSAUtil.class);
	
	  /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;
    
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECRYPT_BLOCK = 128;
	
	/** 
	 * * 生成密钥对 * 
	 *  
	 * @return KeyPair * 
	 * @throws EncryptException 
	 */  
	public static Map<String,String> generateKeyPair() {
		try {
			KeyPairGenerator keyPairGen = KeyPairGenerator.getInstance("RSA",
				new org.bouncycastle.jce.provider.BouncyCastleProvider());
			final int KEY_SIZE = 1024;// 没什么好说的了，这个值关系到块加密的大小，可以更改，但是不要太大，否则效率会低  
			keyPairGen.initialize(KEY_SIZE, new SecureRandom());
			KeyPair keyPair = keyPairGen.generateKeyPair();
			System.out.println(new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
			System.out.println(new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
			Map<String,String> map = new HashMap<String,String>();
			map.put("publicKey", new String(Base64.encodeBase64(keyPair.getPublic().getEncoded())));
			map.put("privateKey", new String(Base64.encodeBase64(keyPair.getPrivate().getEncoded())));
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	


	public static KeyPair getKeyPair() {
		try {
			ObjectInputStream oos = new ObjectInputStream(RSAUtil.class.getClassLoader().getResourceAsStream("rsa.keypair.dat"));
			KeyPair kp = (KeyPair) oos.readObject();
			oos.close();
			return kp;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			return null;
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return null;
		}
	}

	/** 
	 * * 生成公钥 * 
	 *  
	 * @param modulus * 
	 * @param publicExponent * 
	 * @return RSAPublicKey * 
	 * @throws Exception 
	 */  
	public static RSAPublicKey generateRSAPublicKey(byte[] modulus,
			byte[] publicExponent) {
		KeyFactory keyFac = null;
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}
  
		RSAPublicKeySpec pubKeySpec = new RSAPublicKeySpec(
				new BigInteger(modulus), new BigInteger(publicExponent));
		try {
			return (RSAPublicKey) keyFac.generatePublic(pubKeySpec);
		} catch (InvalidKeySpecException ex) {
			logger.error("生成公钥异常",ex);
			return null;
		}
	}

	/** 
	 * * 生成私钥 * 
	 *  
	 * @param modulus * 
	 * @param privateExponent * 
	 * @return RSAPrivateKey * 
	 * @throws Exception 
	 */
	public static RSAPrivateKey generateRSAPrivateKey(byte[] modulus, byte[] privateExponent) {
		KeyFactory keyFac = null;  
		try {
			keyFac = KeyFactory.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
		} catch (NoSuchAlgorithmException ex) {
			ex.printStackTrace();
			return null;
		}

		RSAPrivateKeySpec priKeySpec = new RSAPrivateKeySpec(
				new BigInteger(modulus), new BigInteger(privateExponent));
		try {
			return (RSAPrivateKey)keyFac.generatePrivate(priKeySpec);
		} catch (InvalidKeySpecException ex) {
			logger.error("生成私钥异常",ex);
			return null;
		}
	}
	/** 
	 * * 加密 * 
	 *  
	 * @param key  加密的密钥 * 
	 * @param data 待加密的明文数据 * 
	 * @return 加密后的数据 * 
	 * @throws Exception 
	 */  
	public static byte[] encrypt(PublicKey pk, byte[] data) throws Exception {  
		try {  
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());
			cipher.init(Cipher.ENCRYPT_MODE, pk);
			int blockSize = cipher.getBlockSize();
			// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
			// 加密块大小为127
			// byte,加密后为128个byte;因此共有2个加密块，第一个127
			// byte第二个为1个byte
			int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小  
			int leavedSize = data.length % blockSize;
			int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
			byte[] raw = new byte[outputSize * blocksSize];
			int i = 0;
			while (data.length - i * blockSize > 0) {
				if (data.length - i * blockSize > blockSize)
					cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
				else
					cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
				// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到  
				// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了  
				// OutputSize所以只好用dofinal方法。  
		
				i++;
			}
			return raw;
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	/** 
	 * * 解密 *
	 *  
	 * @param key 解密的密钥 * 
	 * @param raw 已经加密的数据 * 
	 * @return 解密后的明文 * 
	 * @throws Exception 
	 */
	public static byte[] decrypt(PrivateKey pk, byte[] raw) {  
		try {  
			Cipher cipher = Cipher.getInstance("RSA",
					new org.bouncycastle.jce.provider.BouncyCastleProvider());  
			cipher.init(cipher.DECRYPT_MODE, pk);  
			int blockSize = cipher.getBlockSize();  
			ByteArrayOutputStream bout = new ByteArrayOutputStream(64);  
			int j = 0;  

			while (raw.length - j * blockSize > 0) {
				bout.write(cipher.doFinal(raw, j * blockSize, blockSize));
				j++;
			}
			return bout.toByteArray();  
		} catch (Exception e) {  
			logger.error("解密异常",e);
			return null;
		}
	}
	
	/**
     * <p>
     * 公钥加密
     * </p>
     * 
     * @param data 源数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPublicKey(byte[] data, String publicKey) throws Exception {
    	 byte[] keyBytes = Base64.decodeBase64(publicKey);
         X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
         KeyFactory keyFactory = KeyFactory.getInstance("RSA");
         Key publicK = keyFactory.generatePublic(x509KeySpec);
//         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//         cipher.init(Cipher.ENCRYPT_MODE, publicK);
//		int blockSize = cipher.getBlockSize();
//		// 获得加密块大小，如：加密前数据为128个byte，而key_size=1024
//		// 加密块大小为127
//		// byte,加密后为128个byte;因此共有2个加密块，第一个127
//		// byte第二个为1个byte
//		int outputSize = cipher.getOutputSize(data.length);// 获得加密块加密后块大小  
//		int leavedSize = data.length % blockSize;
//		int blocksSize = leavedSize != 0 ? data.length / blockSize + 1 : data.length / blockSize;
//		byte[] raw = new byte[outputSize * blocksSize];
//		int i = 0;
//		while (data.length - i * blockSize > 0) {
//			if (data.length - i * blockSize > blockSize)
//				cipher.doFinal(data, i * blockSize, blockSize, raw, i * outputSize);
//			else
//				cipher.doFinal(data, i * blockSize, data.length - i * blockSize, raw, i * outputSize);
//			// 这里面doUpdate方法不可用，查看源代码后发现每次doUpdate后并没有什么实际动作除了把byte[]放到  
//			// ByteArrayOutputStream中，而最后doFinal的时候才将所有的byte[]进行加密，可是到了此时加密块大小很可能已经超出了  
//			// OutputSize所以只好用dofinal方法。  
//	
//			i++;
//		}
//		return raw;
         // 对数据加密
         Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
         cipher.init(Cipher.ENCRYPT_MODE, publicK);
         int inputLen = data.length;
         ByteArrayOutputStream out = new ByteArrayOutputStream();
         int offSet = 0;
         byte[] cache;
         int i = 0;
         // 对数据分段加密
         while (inputLen - offSet > 0) {
             if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                 cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
             } else {
                 cache = cipher.doFinal(data, offSet, inputLen - offSet);
             }
             out.write(cache, 0, cache.length);
             i++;
             offSet = i * MAX_ENCRYPT_BLOCK;
         }
         byte[] encryptedData = out.toByteArray();
         out.close();
         return encryptedData;
    }
    
    /**
     * <P>
     * 私钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPrivateKey(byte[] encryptedData, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
//        
//        cipher.init(cipher.DECRYPT_MODE, privateK);  
//		int blockSize = cipher.getBlockSize();  
//		ByteArrayOutputStream bout = new ByteArrayOutputStream(64);  
//		int j = 0;  
//
//		while (encryptedData.length - j * blockSize > 0) {
//			bout.write(cipher.doFinal(encryptedData, j * blockSize, blockSize));
//			j++;
//		}
//		return bout.toByteArray();  
        
        cipher.init(Cipher.DECRYPT_MODE, privateK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
    
    /**
     * <p>
     * 私钥加密
     * </p>
     * 
     * @param data 源数据
     * @param privateKey 私钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] encryptByPrivateKey(byte[] data, String privateKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(privateKey);
        PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.ENCRYPT_MODE, privateK);
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段加密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_ENCRYPT_BLOCK;
        }
        byte[] encryptedData = out.toByteArray();
        out.close();
        return encryptedData;
    }

    
    /**
     * <p>
     * 公钥解密
     * </p>
     * 
     * @param encryptedData 已加密数据
     * @param publicKey 公钥(BASE64编码)
     * @return
     * @throws Exception
     */
    public static byte[] decryptByPublicKey(byte[] encryptedData, String publicKey)
            throws Exception {
        byte[] keyBytes = Base64.decodeBase64(publicKey);
        X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        Key publicK = keyFactory.generatePublic(x509KeySpec);
        Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
        cipher.init(Cipher.DECRYPT_MODE, publicK);
        int inputLen = encryptedData.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                cache = cipher.doFinal(encryptedData, offSet, MAX_DECRYPT_BLOCK);
            } else {
                cache = cipher.doFinal(encryptedData, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * MAX_DECRYPT_BLOCK;
        }
        byte[] decryptedData = out.toByteArray();
        out.close();
        return decryptedData;
    }
    
    
    

	/** 
	 * * * 
	 *  
	 * @param args * 
	 * @throws Exception 
	 */  
	public static void main(String[] args) throws Exception {
		// 生成密钥对
//		Map<String,String> map = RSAUtil.generateKeyPair();
//		System.out.println("public==="+map.get("publicKey"));
//		System.out.println("private==="+map.get("privateKey"));
//		
//		System.out.println(ByteArrayUtil.hexString2ByteArray(ByteArrayUtil.byteArray2HexString(Base64.decodeBase64(map.get("publicKey")))));
		//测试公钥加密，私钥解密
//		String publicKey = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCQuQpNqag1vYn7KJReFFM6FcYg8JtvIdu1yqhWQXR2lxMIF7COMmIeOGKPF6BM0QRaYysw4vJ9j3+6+cBLbdd9ey7tgfWBBZcMVF99kI1WgSTyH0aO2HDhU1boUe3+f9YQgrl0s3DazhAs0OT+UyjdY/nzo4QyeUo96ml+T3WQMQIDAQAB";
//		String privateKey = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBAJC5Ck2pqDW9ifsolF4UUzoVxiDwm28h27XKqFZBdHaXEwgXsI4yYh44Yo8XoEzRBFpjKzDi8n2Pf7r5wEtt1317Lu2B9YEFlwxUX32QjVaBJPIfRo7YcOFTVuhR7f5/1hCCuXSzcNrOECzQ5P5TKN1j+fOjhDJ5Sj3qaX5PdZAxAgMBAAECgYB4wIwiRL6/KEiqsS8qWFchDe3kqh1m2p9RzagrYC3mjSMqC2obG1rW0EEcF6B+t5+kMz5kadfegA8ZWS3jQeEb4Kx2ZP/X2ElckR+P2wBmcspBbJy5L1z/BJPVuf+Kbm3Gf31Irmh2x2UB5E4KWsFHz2ddnU8DVTgo7LEZGY1JqQJBAOlpvkQAt7c/JSlvS4ZMEMLA+3ngkD+3ejBx1b4n66m0+c+mOSVbamfq/WIGjvJpwdlGIdt2Fkxj5n0KCOcJ/QcCQQCeujSxpanCvcomGMyYoYnRc2I5ut+FBe0/cHYlhnBnP7rssowSIyj1T1xQcrVC5fP10lRW0pqPJCD3evpmbvMHAkEAjv0shdQ6t5ODXILFrZvjmKtIYz1ioOXxgMP7wOYLxIcNkluuHmiGIuseFnMWFwMasdP44czRlDKO/IKtOXww+QJARaaD9OgzBAdwKyN6tAc+iLBPC/Iany6omsvorDLAJC5tAeyfVl4jXPHEGnEbZQ2u7dGrvKWRuPorlS1br5NEBQJBAKI2mguYF8KUqiwiOhCgDTroMKgH+XpMv0QE9AwwZBstPwtT33Y2oDXr2AWrc7VCmCrgv5ybj2hyU1rwfM2AmJ8=";
//		String data="2014110719160012VTDNZZI4RN1DTTCNZHRVDHZBTYW1T75P";
//		byte[] encByPub = encryptByPublicKey(data.getBytes(),publicKey);
//		String encByPubStr = ByteArrayUtil.byteArray2HexString(encByPub);
//		System.out.println("密文(公钥加密)："+encByPubStr);
//		byte[] de_test = decryptByPrivateKey(ByteArrayUtil.hexString2ByteArray(encByPubStr),privateKey);
//		System.out.println("明文(私钥解密)："+ByteArrayUtil.byteArray2HexString(de_test));
//		System.out.println("明文(私钥解密)："+new String(de_test));
//		//测试私钥加密，公钥解密
//		
//		byte[] encByPri = encryptByPrivateKey(data.getBytes(), privateKey);
//		String mac = ByteArrayUtil.byteArray2HexString(encByPri);
//		System.out.println("密文(私钥加密)："+mac);
//		byte[] de_test2 = decryptByPublicKey(ByteArrayUtil.hexString2ByteArray(mac), publicKey);
//		System.out.println("明文(公钥解密)："+ByteArrayUtil.byteArray2HexString(de_test2));
//		System.out.println("明文(公钥解密)："+new String(de_test2));
//		String merDesStr = RandomTools.getRandomString(32);
//		String json = "abc";
//		Map map = new HashMap();
//		map.put("a", "1");
//		map.put("a", "2");
//		String json2 = ByteArrayUtil.byteArray2HexString(DESCrypto.enCrypto(JsonUtil.map2Json(map).getBytes(), merDesStr));
//		PayEncrypt pe = new PayEncrypt();
//		String json1 = pe.encryptMode(merDesStr, json);
//		System.out.println("json2==="+json2);
//		MacUtil.des3Encrypt(ByteArrayUtil.hexString2ByteArray(merDesStr), ByteArrayUtil.hexStr2Bytes(json));
		// 测试公钥加密，私钥解密
//		String test = "hello world";
//		KeyPair keyPair = RSAUtil.getKeyPair();
//		System.out.println(keyPair.getPublic());
//		System.out.println(keyPair.getPrivate());
//
//		byte[] en_test = encrypt(keyPair.getPublic(), test.getBytes());
//		System.out.println("密文(公钥加密)："+ByteArrayUtil.byteArray2HexString(en_test));
//		byte[] de_test = decrypt(keyPair.getPrivate(), en_test);
//		System.out.println("明文(私钥解密)："+ByteArrayUtil.byteArray2HexString(de_test));
//		System.out.println("明文(私钥解密)："+new String(de_test));
	}

}
