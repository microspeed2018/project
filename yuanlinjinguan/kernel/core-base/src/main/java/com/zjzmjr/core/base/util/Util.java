/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.util;

import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import net.sf.json.JSONNull;

/**
 * 
 * @author Administrator
 * @version $Id: StrUtil.java, v 0.1 2015-6-16 上午9:07:39 Administrator Exp $
 */
public final class Util {

	private Util() {

	}

	public static boolean isNotNull(Object argObj) {
		return !Util.isNull(argObj);
	}

	@SuppressWarnings("rawtypes")
    public static boolean isNull(Object argObj) {
		if (argObj == null) {
			return true;
		}

		if (argObj instanceof String) {
			return ((String) argObj).trim().equals("")
					|| ((String) argObj).trim().equals(" ")
					|| ((String) argObj).trim().equals("null");
		}

		if (argObj instanceof Collection) {

			return ((Collection) argObj).isEmpty();
		}

		if (argObj instanceof Map) {

			return ((Map) argObj).isEmpty();
		}
		
		if (argObj instanceof JSONNull){
		    return JSONNull.getInstance().equals(argObj);
		}

		return false;
	}

	public static String format(Date datetime) {
		if (Util.isNull(datetime))
			return null;

		try {
			SimpleDateFormat dateFromat = new SimpleDateFormat();
			dateFromat.applyPattern("yyyy-MM-dd HH:mm:ss");
			return dateFromat.format(datetime);
		} catch (Exception e) {
			return null;
		}
	}

}
