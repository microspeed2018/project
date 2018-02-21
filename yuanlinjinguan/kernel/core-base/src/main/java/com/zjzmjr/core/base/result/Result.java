/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.base.result;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.Base;

/**
 * 
 * @author Administrator
 * @version $Id: Result.java, v 0.1 2015-6-12 下午2:54:33 Administrator Exp $
 */
public class Result extends Base {

    /**  */
    private static final long serialVersionUID = 2534019658622578421L;

    private boolean success = true;

    private String errorCode;

    private String errorMsg;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    /**
     * 根据错误枚举设置错误消息
     * 
     * @param errorCode
     */
    public void setErrorCodeEnum(ErrorCodeEnum errorCode){
        this.errorCode = errorCode.getCode();
        this.errorMsg = errorCode.getName();
    }
}
