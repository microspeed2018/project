package com.zjzmjr.core.sms.util;


import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;


/*
 *  DigestUtil.java
 *  Author: Hippo
 *  Last Date: 2012-4-28
 *  Description: A test progrm to encrypt a string using MD5 or SHA-1,etc.
 */

public class DigestUtil {

	public DigestUtil() {
	}

	/**
	 * 摘要算法
	 * @param strSrc 原文
	 * @param encName 算法(MD5,SHA-1,SHA-256)dafault to "MD5" default charset is GBK
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName) {
		return Encrypt(strSrc, encName, "GBK");
	}
	
	/**
	 * 摘要算法
	 * @param strSrc 原文
	 * @param encName 算法(MD5,SHA-1,SHA-256)dafault to "MD5"
	 * @param charset 原文字符集
	 * @return
	 */
	public static String Encrypt(String strSrc, String encName, String charset) {
		MessageDigest md = null;
		String strDes = null;
		
		try {
			byte[] bt = strSrc.getBytes(charset);
			if (encName == null || encName.equals("")) {
				encName = "MD5";
			}
			md = MessageDigest.getInstance(encName);
			md.update(bt);
			strDes = bytes2Hex(md.digest()); // to HexString
		} catch (UnsupportedEncodingException e) {
			System.out.println("unsupported encoding");
			return null;
		} catch (NoSuchAlgorithmException e) {
			System.out.println("Invalid algorithm.");
			return null;
		} 
		return strDes;
	}

	private static String bytes2Hex(byte[] bts) {
		String des = "";
		String tmp = null;
		for (int i = 0; i < bts.length; i++) {
			tmp = (Integer.toHexString(bts[i] & 0xFF));
			if (tmp.length() == 1) {
				des += "0";
			}
			des += tmp;
		}
		return des;
	}

	public static void main(String[] args) {
		String strSrc = "adminadmin";
		System.out.println("Source String:" + strSrc);
		System.out.println("Encrypted String:");
		System.out.println("Use Def:" + DigestUtil.Encrypt(strSrc, null));
		System.out.println("Use MD5:" + DigestUtil.Encrypt(strSrc, "MD5"));
		System.out.println("Use SHA-1:" + DigestUtil.Encrypt("58306912da2b032f05542e711851f088e3087736", "SHA-1"));
		System.out.println("Use SHA-256:" + DigestUtil.Encrypt(strSrc, "SHA-256"));
	}
}