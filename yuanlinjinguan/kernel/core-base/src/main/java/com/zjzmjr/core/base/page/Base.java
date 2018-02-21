/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.page;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Date;

import com.zjzmjr.core.base.util.Util;

/**
 * 
 * @author Administrator
 * @version $Id: Base.java, v 0.1 2015-8-14 下午5:21:52 Administrator Exp $
 */

public class Base implements Serializable {

	/**  */
	private static final long serialVersionUID = -2500597333789515707L;

	/**
	 * 商家编号
	 */
	private String merchantCode;

	public String getMerchantCode() {
		return merchantCode;
	}

	public void setMerchantCode(String merchantCode) {
		this.merchantCode = merchantCode;
	}

	public String toString() {
		Class<? extends Base> c = this.getClass();
		StringBuffer sb = new StringBuffer();
		sb.append(c.getSimpleName());
		sb.append(" [");
		Field[] fields = c.getDeclaredFields();
		StringBuffer strBuffer = new StringBuffer();
		for (Field field : fields) {
			field.setAccessible(true);
			try {
				Object value = field.get(this);
				if (!Modifier.isStatic(field.getModifiers()) && Util.isNotNull(value)) {

					strBuffer.append(field.getName());
					strBuffer.append("=");
					if (value instanceof Date) {
						Date date = (Date) value;
						strBuffer.append(Util.format(date));
					} else {
						strBuffer.append(value.toString());
					}

					strBuffer.append(",");
				}
			} catch (IllegalArgumentException e) {
			} catch (IllegalAccessException e) {
			}
		}
		String str = strBuffer.toString();
		if (Util.isNotNull(str)) {
			sb.append(str.substring(0, str.length() - 1));
		}

		sb.append("]");
		return sb.toString();
	}

}
