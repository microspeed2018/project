package com.zjzmjr.core.common.biz.weixin;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON处理工具类
 * @author Administrator
 *
 */
public class JSONProcesserUtil {
	
	private static final ObjectMapper mapper=new ObjectMapper();
	
	/**
	 * 将Java对象转化为json字符串
	 * @param object
	 * @return
	 */
	public static String parseObj(Object object){
		try {
			return mapper.writeValueAsString(object);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 将json字符串转化为Java对象
	 * @param str
	 * @param obj
	 * @return
	 */
	public static <T> T parseStr(String str,Class<T> obj){
		try {
			return mapper.readValue(str, obj);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 读取json字符串指定的属性值
	 * @param str
	 * @param key
	 * @return
	 */
	public static String getValue(String str,String key){
		try {
			JsonNode jsonNode=mapper.readTree(str);
			return jsonNode.get(key).toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
