package com.zjzmjr.core.common.util;

import com.zjzmjr.core.enums.admin.BusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserStatusEnum;
import com.zjzmjr.core.enums.user.UserTypeEnum;

/**
 * 枚举类的工具类
 * 
 * @author oms
 * @version $Id: EnumUtils.java, v 0.1 2016-5-26 上午10:36:50 oms Exp $
 */
public class EnumUtils {

    /**
     * 事务类型枚举
     * 
     * @return
     */
    public static BusinessTypeEnum[] getBusinessTypeEnums(){
        return BusinessTypeEnum.values();
    }
    
    /**
     * 处理状态枚举
     * 
     * @return
     */
    public static HandleResultEnum[] getHandleResultEnums(){
        return HandleResultEnum.values();
    }
    
    /**
     * 用户的状态
     * 
     * @return
     */
    public static UserStatusEnum[] getUserStatusEnums(){
        return UserStatusEnum.values();
    }
    
    /**
     * 用户的类型
     * 
     * @return
     */
    public static UserTypeEnum[] getUserTypeEnums(){
        return UserTypeEnum.values();
    }
    
    /**
     * 用户的状态
     * 
     * @return
     */
    public static UserStatusEnum[] getUserStatusEnum(){
        return UserStatusEnum.values();
    }
}
