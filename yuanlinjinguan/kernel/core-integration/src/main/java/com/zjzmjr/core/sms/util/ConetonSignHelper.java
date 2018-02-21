package com.zjzmjr.core.sms.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.SortedMap;

import org.apache.commons.lang.StringUtils;

/**
 * 众易通API接口中hearder加密工具
 * 
 * @author oms
 * @version $Id: SignHelper.java, v 0.1 2017-5-22 上午11:10:32 oms Exp $
 */
public class ConetonSignHelper {

    public final static String CHARSET = "UTF-8";
    public final static String SIGNTYPE = "MD5"; // 默认签名类型
    public final static String SEPARATOR = "&"; // 默认分隔符

	/**
	 * 创建签名
	 *
	 * @param dictArray
	 *            待签名字典
	 * @param key
	 *            安全码（盐值）
	 * @param charset
	 *            编码方式
	 * @return
	 */
	public static String buildMD5Sign(SortedMap<String, String> dictArray, String key, String charset) {
		HashMap<String, String> dictPara = filterPara(dictArray);
		return sign(dictPara, key, null, null, null);
	}

	/**
	 * 除去数组中的空值并以字母a到z的顺序排序
	 *
	 * @param dictArrayPre
	 *            过滤前的参数组
	 * @return
	 */
	private static HashMap<String, String> filterPara(SortedMap<String, String> dictArrayPre) {
		HashMap<String, String> dictArray = new LinkedHashMap<>();
		for (String key : dictArrayPre.keySet()) {
			String value = dictArrayPre.get(key);
			if (!value.isEmpty()) {
				dictArray.put(key.toLowerCase(), value);
			}
		}
		return dictArray;
	}

    /**
     * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
     * 
     * @param dictArray
     *            需要拼接的数组
     * @param separator
     *            分隔字符
     * @return
     */
    public static String createLinkString(Map<String, String> dictArray) {
        return createLinkString(dictArray, SEPARATOR);
    }

	/**
	 * 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
	 *
	 * @param dictArray
	 *            需要拼接的数组
	 * @param separator
	 *            分隔字符
	 * @return
	 */
	private static String createLinkString(Map<String, String> dictArray, String separator) {
		StringBuilder prestr = new StringBuilder();
		for (String key : dictArray.keySet()) {
			String value = dictArray.get(key);
			prestr.append(key + "=" + value + separator);
		}
		// 去掉最后一个&字符
		String value = prestr.toString();
		value = value.substring(0, value.length() - separator.length());
		return value;
	}

	/**
	 * 生成签名结果
	 *
	 * @param dictArray
	 *            要签名的数组
	 * @param key
	 *            安全校验码
	 * @return
	 */
	public static String sign(Map<String, String> dictArray, String key) {
		String mySign = sign(dictArray, key, null, null, null);
		return mySign;
	}

	/**
	 * 生成签名结果
	 *
	 * @param dictArray
	 *            要签名的数组
	 * @param key
	 *            安全校验码
	 * @param signType
	 *            签名类型
	 * @param charset
	 *            编码格式
	 * @param separator
	 *            分隔符
	 * @return
	 */
	public static String sign(Map<String, String> dictArray, String key, String signType, String charset,
			String separator) {
		if (StringUtils.isEmpty(signType)) {
			signType = SIGNTYPE;
		}
		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET;
		}
		if (StringUtils.isEmpty(separator)) {
			separator = SEPARATOR;
		}
		String prestr = createLinkString(dictArray, separator); // 把数组所有元素，按照“参数=参数值”的模式用“&”字符拼接成字符串
		prestr += key; // 把拼接后的字符串再与安全校验码直接连接起来
		String mySign = sign(prestr, signType, charset);
		return mySign;
	}

	/**
	 * 签名字符串
	 *
	 * @param prestr
	 *            需要签名的字符串
	 * @param signType
	 *            签名类型
	 * @param charset
	 *            编码格式
	 * @return
	 */
	public static String sign(String prestr, String signType, String charset) {
		if (StringUtils.isEmpty(signType)) {
			signType = SIGNTYPE;
		}
		if (StringUtils.isEmpty(charset)) {
			charset = CHARSET;
		}
		StringBuilder sb = new StringBuilder(32);
		if (signType.toUpperCase().equals("MD5")) {
			byte[] t = MD5Utils.MD5(prestr, charset);
			for (int i = 0; i < t.length; i++) {
				String hex = Integer.toHexString(t[i] & 0xFF);
				sb.append(StringUtils.leftPad(hex, 2, '0'));
			}
		}
		return sb.toString();
	}
}
