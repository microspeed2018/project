/**
 * zjzmjr.com Inc.
 * Copyright (c) 2013-2016 All Rights Reserved.
 */
package com.zjzmjr.core.base.exception;


/**
 * 
 * 
 * @author Administrator
 * @version $Id: ApplicationException.java, v 0.1 2016-5-4 下午1:49:56 Administrator Exp $
 */
public class ApplicationException extends Exception {

    /**  */
    private static final long serialVersionUID = 7086999033314319255L;

    // 异常消息
    private static final String message = "";

    /**
     * 无参构造函数
     * 
     * @return ApplicationException 用户未登录异常类的实例
     */
    public ApplicationException() {
        super(message);
    }

    /**
     * 构造函数
     * 
     * @param message
     *            异常消息描述
     * @return ApplicationException 用户未登录异常类的实例
     */
    public ApplicationException(String message) {
        super(message);
    }

    /**
     * 构造函数
     * 
     * @param cause
     *            异常原因
     * @return ApplicationException 用户未登录异常类的实例
     */
    public ApplicationException(Throwable cause) {
        super(cause);
    }

    /**
     * 构造函数
     * 
     * @param message
     *            异常消息
     * @param cause
     *            异常原因
     * @return ApplicationException 用户未登录异常类的实例
     */
    public ApplicationException(String message, Throwable cause) {
        super(message, cause);
    }

}
