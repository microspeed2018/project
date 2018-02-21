package com.zjzmjr.core.model.project;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.company.CompanyAssociated;

/**
 * 项目及相关信息
 * 
 * @author oms
 * @version $Id: GardenProjectInfo.java, v 0.1 2017-9-3 下午1:29:58 oms Exp $
 */
public class GardenProjectInfo extends GardenProject {

    /**  */
    private static final long serialVersionUID = 4660400207161379257L;

    /**
     * 技术负责人
     */
    private Admin leader;

    /**
     * 经营专员
     */
    private Admin manager;

    /**
     * 经营部门简称
     */
    private String managementDepartmentAbbreviate;

    /**
     * 项目类别简称
     */
    private String categoryAbbreviate;

    /**
     * 介绍人姓名
     */
    private String introducerName;

    /**
     * 性质名称
     */
    private String natureName;

    /**
     * 类别名称
     */
    private String categoryName;

    /**
     * 类型名称
     */
    private String typeName;

    /**
     * 经营部门名称
     */
    private String departmentName;

    /**
     * 合作方式名称
     */
    private String cooperationName;

    /**
     * 地区名称
     */
    private String cityName;

    /**
     * 关联公司
     */
    private CompanyAssociated companyAssociated;

    /**
     * 商务标制作人
     */
    private Admin busiProducer;

    /**
     * 报名文件制作人
     */
    private Admin bidDocuProducer;
    
    /**
     * 新建时间
     */
    private String addTime;
    
    /**
     * 状态文本
     */
    private String statusText;
    
    /**
     * 步骤文本
     */
    private String stepText;
    
    

    public Admin getLeader() {
        return leader;
    }

    public void setLeader(Admin leader) {
        this.leader = leader;
    }

    public Admin getManager() {
        return manager;
    }

    public void setManager(Admin manager) {
        this.manager = manager;
    }

    public String getManagementDepartmentAbbreviate() {
        return managementDepartmentAbbreviate;
    }

    public void setManagementDepartmentAbbreviate(String managementDepartmentAbbreviate) {
        this.managementDepartmentAbbreviate = managementDepartmentAbbreviate;
    }

    public String getCategoryAbbreviate() {
        return categoryAbbreviate;
    }

    public void setCategoryAbbreviate(String categoryAbbreviate) {
        this.categoryAbbreviate = categoryAbbreviate;
    }

    public CompanyAssociated getCompanyAssociated() {
        return companyAssociated;
    }

    public void setCompanyAssociated(CompanyAssociated companyAssociated) {
        this.companyAssociated = companyAssociated;
    }

    public Admin getBusiProducer() {
        return busiProducer;
    }

    public Admin getBidDocuProducer() {
        return bidDocuProducer;
    }

    public void setBidDocuProducer(Admin bidDocuProducer) {
        this.bidDocuProducer = bidDocuProducer;
    }

    public void setBusiProducer(Admin busiProducer) {
        this.busiProducer = busiProducer;
    }

    public String getIntroducerName() {
        return introducerName;
    }

    public void setIntroducerName(String introducerName) {
        this.introducerName = introducerName;
    }

    public String getNatureName() {
        return natureName;
    }

    public void setNatureName(String natureName) {
        this.natureName = natureName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getTypeName() {
        return typeName;
    }

    public void setTypeName(String typeName) {
        this.typeName = typeName;
    }

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public String getCooperationName() {
        return cooperationName;
    }

    public void setCooperationName(String cooperationName) {
        this.cooperationName = cooperationName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }
    
    public String getAddTime() {
        return addTime;
    }

    
    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    
    public String getStatusText() {
        return statusText;
    }

    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    
    public String getStepText() {
        return stepText;
    }

    
    public void setStepText(String stepText) {
        this.stepText = stepText;
    }

    @Override
    public String toString() {
        return "GardenProjectInfo [leader=" + leader + ", manager=" + manager + ", managementDepartmentAbbreviate=" + managementDepartmentAbbreviate + ", categoryAbbreviate=" + categoryAbbreviate + ", introducerName=" + introducerName + ", natureName=" + natureName + ", categoryName=" + categoryName + ", typeName=" + typeName + ", departmentName=" + departmentName + ", cooperationName=" + cooperationName + ", cityName=" + cityName + ", companyAssociated=" + companyAssociated + ", busiProducer="
                + busiProducer + ", bidDocuProducer=" + bidDocuProducer + ", addTime=" + addTime + ", statusText=" + statusText + ", stepText=" + stepText + "]";
    }

}
