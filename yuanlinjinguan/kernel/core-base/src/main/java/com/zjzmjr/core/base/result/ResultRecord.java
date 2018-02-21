/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.result;

import java.util.List;


/**
 * 
 * @author Administrator
 * @version $Id: ResultRecord.java, v 0.1 2015-6-12 下午3:13:27 Administrator Exp
 *          $
 */
public class ResultRecord<T> extends Result {

	/**  */
	private static final long serialVersionUID = 2869291459820914761L;

	private List<T> list;

	public List<T> getList() {
		return list;
	}

	public void setList(List<T> list) {
		this.list = list;
	}

}
