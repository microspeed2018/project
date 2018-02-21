package com.zjzmjr.admin.web.common.util;

import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.admin.BusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.interview.InterviewResultEnum;
import com.zjzmjr.core.enums.menu.MenuProjectEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.recruitment.RecruitmentIsValidEnum;
import com.zjzmjr.core.enums.user.ExternalPersonStatusEnum;
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
     * 项目阶段枚举
     * 
     * @return
     */
    public static GardenProjectStepEnum[] getGardenProjectStepEnums() {
        return GardenProjectStepEnum.values();
    }

    /**
     * 贷款申请状态枚举
     * 
     * @return
     */
    public static GardenProjectStatusEnum[] getGardenProjectStatusEnums() {
        return GardenProjectStatusEnum.values();
    }

    /**
     * 事务类型枚举
     * 
     * @return
     */
    public static BusinessTypeEnum[] getBusinessTypeEnums() {
        return BusinessTypeEnum.values();
    }

    /**
     * 处理状态枚举
     * 
     * @return
     */
    public static HandleResultEnum[] getHandleResultEnums() {
        return HandleResultEnum.values();
    }

    /**
     * 用户的状态
     * 
     * @return
     */
    public static UserStatusEnum[] getUserStatusEnums() {
        return UserStatusEnum.values();
    }

    /**
     * 用户的类型
     * 
     * @return
     */
    public static UserTypeEnum[] getUserTypeEnums() {
        return UserTypeEnum.values();
    }

    /**
     * 管理员事物类型枚举
     * 
     * @return
     */
    public static AdminBusinessTypeEnum[] getAdminBusinessTypeEnums() {
        return AdminBusinessTypeEnum.values();
    }

    /**
     * 菜单的使用系统枚举
     * 
     * @return
     */
    public static MenuProjectEnum[] getMenuProjectEnum() {
        return MenuProjectEnum.values();
    }
    
    
    /**
     * 招聘信息的状态枚举
     * 
     * @return
     */
    public static RecruitmentIsValidEnum[] getRecruitmentIsValidEnum() {
        return RecruitmentIsValidEnum.values();
    }
    
    /**
     * 员工状态枚举型
     * 
     * @return
     */
    public static AdminJobStatusEnum[] getAdminJobStatusEnum() {
        return AdminJobStatusEnum.values();
    }
    
    /**
     * 面试结果的状态枚举
     * 
     * @return
     */
    public static InterviewResultEnum[] getInterviewResultEnum() {
        return InterviewResultEnum.values();
    }
    
    /**
     * 外部人员的状态枚举
     * 
     * @return
     */
    public static ExternalPersonStatusEnum[] getExternalPersonStatusEnum() {
        return ExternalPersonStatusEnum.values();
    }
}
