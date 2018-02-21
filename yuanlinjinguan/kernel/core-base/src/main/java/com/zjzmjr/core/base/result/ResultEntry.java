/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.result;

/**
 * 
 * @author Administrator
 * @version $Id: ResultEntry.java, v 0.1 2015-10-28 上午11:15:46 Administrator Exp
 *          $
 */
public class ResultEntry<T> extends Result {

    /**  */
    private static final long serialVersionUID = 4175987967703639235L;

    private T t;

    public T getT() {
        return t;
    }

    public void setT(T t) {
        this.t = t;
    }
    
    
}
