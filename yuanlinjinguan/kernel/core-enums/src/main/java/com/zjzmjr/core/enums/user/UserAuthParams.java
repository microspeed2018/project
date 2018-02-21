package com.zjzmjr.core.enums.user;

/**
 * 用户权限
 * 
 * @author oms
 * @version $Id: UserAuthParams.java, v 0.1 2016-6-14 上午11:31:22 oms Exp $
 */
public interface UserAuthParams {

    /**
     * 用户详情查询
     */
    final String USER_DETAIL_QUERY = "USER_DETAIL_QUERY";

    /**
     * 查看用户敏感信息
     */
    final String USER_CONTRACTS_QUERY = "USER_CONTRACTS_QUERY";

    /**
     * 用户信息修改
     */
    final String USER_INFO_UPDATE = "USER_INFO_UPDATE";

    /**
     * 消息发送
     */
    final String MESS_SEND = "MESS_SEND";

    /**
     * 短信发送
     */
    final String SMS_SEND = "SMS_SEND";

    /**
     * APP发布
     */
    final String APP_RELEASE = "APP_RELEASE";

    /**
     * 综合报表查看权限
     */
    final String STATISTIC_VIEW = "STATISTIC_VIEW";

    /**
     * 综合报表导出权限
     */
    final String STATISTIC_EXPORT = "STATISTIC_EXPORT";
    
    final String AD_MANAGE = "AD_MANAGE";
    
    /**
     * 共享文件管理
     */
    final String SHARED_FILE_MANAGE = "SHARED_FILE_MANAGE";
    
    /**
     * 基础数据新增
     */
    final String BASIC_DATA_ADD = "BASIC_DATA_ADD";
    
    /**
     * 基础数据更新
     */
    final String BASIC_DATA_UPDATE = "BASIC_DATA_UPDATE";
    
    /**
     * 基础数据删除
     */
    final String BASIC_DATA_DELETE = "BASIC_DATA_DELETE";
    
    /**
     * 公司信息新增
     */
    final String COMPANY_INFO_ADD = "COMPANY_INFO_ADD";
    
    /**
     * 公司信息更新
     */
    final String COMPANY_INFO_UPDATE = "COMPANY_INFO_UPDATE";
    
    /**
     * 公司信息导出
     */
    final String COMPANY_INFO_EXPORT = "COMPANY_INFO_EXPORT";
    
    /**
     * 关联公司信息新增
     */
    final String COMPANY_ASSOCITED_ADD = "COMPANY_ASSOCITED_ADD";
    
    /**
     * 关联公司信息更新
     */
    final String COMPANY_ASSOCITED_UPDATE = "COMPANY_ASSOCITED_UPDATE";
    
    /**
     * 关联公司信息导出
     */
    final String COMPANY_ASSOCITED_EXPORT = "COMPANY_ASSOCITED_EXPORT";
    
    /**
     * 部门信息新增
     */
    final String DEPARTMENT_ADD = "DEPARTMENT_ADD";
    
    /**
     * 部门信息更新
     */
    final String DEPARTMENT_UPDATE = "DEPARTMENT_UPDATE";
    
    /**
     * 部门无效设置
     */
    final String DEPARTMENT_SET_INVALID = "DEPARTMENT_SET_INVALID";
    
    /**
     * 职位信息新增
     */
    final String JOB_ADD = "JOB_ADD";
    
    /**
     * 职位信息更新
     */
    final String JOB_UPDATE = "JOB_UPDATE";
    
    /**
     * 职位无效设置
     */
    final String JOB_SET_INVALID = "JOB_SET_INVALID";
    
    /**
     * 内部员工导出
     */
    final String STAFF_EXPORT = "STAFF_EXPORT";
    
    /**
     * 外部员工导出
     */
    final String EXTERNAL_EXPORT = "EXTERNAL_EXPORT";
    
    /**
     * 员工修改
     */
    final String CONSOLE_ADMIN_UPDATE = "CONSOLE_ADMIN_UPDATE";
    
    /**
     * 员工新增
     */
    final String CONSOLE_ADMIN_ADD = "CONSOLE_ADMIN_ADD";
    
    /**
     * 人才提交面试
     */
    final String TALENT_SET_INTERVIEW = "TALENT_SET_INTERVIEW";
    
    /**
     * 人才信息导出
     */
    final String TALENT_EXPORT = "TALENT_EXPORT";
    
    /**
     * 招聘信息添加
     */
    final String RECRUITMENT_ADD = "RECRUITMENT_ADD";
    
    /**
     * 招聘信息导出
     */
    final String RECRUITMENT_EXPORT = "RECRUITMENT_EXPORT";
    
    /**
     * 招聘信息修改
     */
    final String RECRUITMENT_UPDATE = "RECRUITMENT_UPDATE";
    
    /**
     * 招聘邀请
     */
    final String RECRUITMENT_INVITE = "RECRUITMENT_INVITE";
    
    /**
     * 项目信息添加
     */
    final String PROJECT_ADD = "PROJECT_ADD";
    
    /**
     * 项目信息导出
     */
    final String PROJECT_EXPORT = "PROJECT_EXPORT";
    
    /**
     * 项目信息修改
     */
    final String PROJECT_UPDATE = "PROJECT_UPDATE";
    
    /**
     * 合同信息添加
     */
    final String CONTRACT_ADD = "CONTRACT_ADD";
    
    /**
     * 合同信息导出
     */
    final String CONTRACT_EXPORT = "CONTRACT_EXPORT";
    
    /**
     * 合同信息修改
     */
    final String CONTRACT_UPDATE = "CONTRACT_UPDATE";
    
    /**
     * 面试设置
     */
    final String INTERVIEW_SET = "INTERVIEW_SET";
    
    /**
     * 面试结果
     */
    final String INTERVIEW_RESULT = "INTERVIEW_RESULT";
    
    /**
     * 面试导出
     */
    final String INTERVIEW_EXPORT = "INTERVIEW_EXPORT";
    
    /**
     * 面试人员简历打印
     */
    final String INTERVIEW_TALENT_PRINT = "INTERVIEW_TALENT_PRINT";
    
    /**
     * 薪酬导入
     */
    final String STAFFWAGES_IMPORT= "STAFFWAGES_IMPORT";
    
    /**
     * 薪酬导出
     */
    final String STAFFWAGES_EXPORT = "STAFFWAGES_EXPORT";
}
