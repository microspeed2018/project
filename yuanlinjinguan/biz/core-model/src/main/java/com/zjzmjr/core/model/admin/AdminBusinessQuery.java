package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 管理员事物查询类
 * 
 * @author Administrator
 * @version $Id: AdminBusinessQuery.java, v 0.1 2016-10-9 下午2:37:13
 *          Administrator Exp $
 */
public class AdminBusinessQuery extends BasePageQuery {

    private static final long serialVersionUID = -9047282210704315417L;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 项目编号
     */
    private Integer projectId;

    /**
     * 管理员编号
     */
    private Integer adminId;

    /**
     * 管理员姓名
     */
    private String adminName;

    /**
     * 事物类型
     */
    private Integer businessType;

    /**
     * 事物类型名称
     */
    private String businessName;

    /**
     * 处理结果
     */
    private Integer result;

    /**
     * 处理结果名称
     */
    private String resultName;

    /**
     * 扩展字段
     */

    private String extendedMsg;
    
    /**
     * 项目名称
     */
    private String projectName;

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getAdminId() {
        return adminId;
    }

    public void setAdminId(Integer adminId) {
        this.adminId = adminId;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public String getAdminName() {
        return adminName;
    }

    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public String getExtendedMsg() {
        return extendedMsg;
    }

    public void setExtendedMsg(String extendedMsg) {
        this.extendedMsg = extendedMsg;
    }
    
    public String getProjectName() {
        return projectName;
    }

    
    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "AdminBusinessQuery [companyId=" + companyId + ", projectId=" + projectId + ", adminId=" + adminId + ", adminName=" + adminName + ", businessType=" + businessType + ", businessName=" + businessName + ", result=" + result + ", resultName=" + resultName + ", extendedMsg=" + extendedMsg + ", projectName=" + projectName + "]";
    }

}
