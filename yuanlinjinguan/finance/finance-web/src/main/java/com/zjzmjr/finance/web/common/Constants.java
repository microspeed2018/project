package com.zjzmjr.finance.web.common;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: Constants.java, v 0.1 2016-5-4 上午10:31:16 Administrator Exp $
 */
public interface Constants {

    String token = "_zjzmjr";
    
    int PAGE_SIZE = 20;

    String CHECK_CODE = "check_code";
    
    String USER_INFO_SESSION = "zjzmjr_user_register_info_session_{mobile}";
    
    /**
     * 保存用户引导页面的实名信息
     */
    String IDENTITY_USER_INFO_SESSION="identity_user_info_session_{userId}";
    
    /**
     * 保存地址
     */
    String SAVE_URL = "entrance";

    /**
     * 未认证
     */
    String unauthorized = "405";

    /**
     * 系统错误
     */
    String system = "900";
    
    /**
     * 数字0
     */
    Integer number_0 = 0;
    
    /**
     * 数字1
     */
    Integer number_1 = 1;
    
    /**
     * 逗号符号
     */
    String COMMA = ",";
}
