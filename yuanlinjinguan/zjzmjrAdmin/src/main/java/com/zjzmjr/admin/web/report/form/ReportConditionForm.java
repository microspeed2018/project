package com.zjzmjr.admin.web.report.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: ReportConditionForm.java, v 0.1 2017-9-19 下午1:21:55 oms Exp $
 */
public class ReportConditionForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = 4014183136687667628L;

    private Integer id;

    private String timeStart;

    private String timeEnd;

    private String projectLeader;

    private String managePerson;

    private String projectStep;

    private Integer status;

    private String dispField;
    
    /**
     * 项目编号
     */
    private String projectNo;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 合同编号
     */
    private String contractNo;
    
    /**
     * 城市名
     */
    private String cityId;
    
    /**
     * 经营部门
     */
    private Integer department;
    
    /**
     * 投资额范围起始
     */
    private String investmentMountStart;
    
    /**
     * 投资额范围结束
     */
    private String investmentMountEnd;
    
    /**
     * 设计面积范围起始
     */
    private String designAreaStart;
    
    /**
     * 设计面积范围结束
     */
    private String designAreaEnd;
    
    /**
     * 项目性质
     */
    private Integer type;
    
    /**
     * 项目类别
     */
    private Integer category;
    
    /**
     * 是否有技术标
     */
    private Integer haveTechnical;
    
    /**
     * 项目性质
     */
    private Integer nature;
    
    /**
     * 院方比例开始值
     */
    private String ratioFrom;

    /**
     * 院方比例结束值
     */
    private String ratioTo;

    /**
     * 开始合同金额
     */
    private String contractCapitalFrom;

    /**
     * 结束合同金额
     */
    private String contractCapitalTo;
    
    /**
     * 项目负责人
     */
    private String projectLiable;
    
    /**
     * 发包单位
     */
    private Integer contractAwardCompany;

    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    public String getProjectStep() {
        return projectStep;
    }

    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDispField() {
        return dispField;
    }

    public void setDispField(String dispField) {
        this.dispField = dispField;
    }
    
    
    public String getProjectNo() {
        return projectNo;
    }

    
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public String getContractNo() {
        return contractNo;
    }

    
    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    
    public String getCityId() {
        return cityId;
    }

    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    
    public Integer getDepartment() {
        return department;
    }

    
    public void setDepartment(Integer department) {
        this.department = department;
    }

    
    public String getInvestmentMountStart() {
        return investmentMountStart;
    }

    
    public void setInvestmentMountStart(String investmentMountStart) {
        this.investmentMountStart = investmentMountStart;
    }

    
    public String getInvestmentMountEnd() {
        return investmentMountEnd;
    }

    
    public void setInvestmentMountEnd(String investmentMountEnd) {
        this.investmentMountEnd = investmentMountEnd;
    }

    
    public String getDesignAreaStart() {
        return designAreaStart;
    }

    
    public void setDesignAreaStart(String designAreaStart) {
        this.designAreaStart = designAreaStart;
    }

    
    public String getDesignAreaEnd() {
        return designAreaEnd;
    }

    
    public void setDesignAreaEnd(String designAreaEnd) {
        this.designAreaEnd = designAreaEnd;
    }

    
    public Integer getType() {
        return type;
    }

    
    public void setType(Integer type) {
        this.type = type;
    }

    
    public Integer getCategory() {
        return category;
    }

    
    public void setCategory(Integer category) {
        this.category = category;
    }

    
    public Integer getHaveTechnical() {
        return haveTechnical;
    }

    
    public void setHaveTechnical(Integer haveTechnical) {
        this.haveTechnical = haveTechnical;
    }

    
    public Integer getNature() {
        return nature;
    }

    
    public void setNature(Integer nature) {
        this.nature = nature;
    }
    
    
    public String getRatioTo() {
        return ratioTo;
    }

    
    public void setRatioTo(String ratioTo) {
        this.ratioTo = ratioTo;
    }

    public String getRatioFrom() {
        return ratioFrom;
    }

    
    public void setRatioFrom(String ratioFrom) {
        this.ratioFrom = ratioFrom;
    }
  
    public String getContractCapitalFrom() {
        return contractCapitalFrom;
    }

    
    public void setContractCapitalFrom(String contractCapitalFrom) {
        this.contractCapitalFrom = contractCapitalFrom;
    }

    
    public String getContractCapitalTo() {
        return contractCapitalTo;
    }

    
    public void setContractCapitalTo(String contractCapitalTo) {
        this.contractCapitalTo = contractCapitalTo;
    }

    public String getProjectLiable() {
        return projectLiable;
    }

    
    public void setProjectLiable(String projectLiable) {
        this.projectLiable = projectLiable;
    }
    
    public Integer getContractAwardCompany() {
        return contractAwardCompany;
    }

    
    public void setContractAwardCompany(Integer contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    @Override
    public String toString() {
        return "ReportConditionForm [id=" + id + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", projectLeader=" + projectLeader + ", managePerson=" + managePerson + ", projectStep=" + projectStep + ", status=" + status + ", dispField=" + dispField + ", projectNo=" + projectNo + ", name=" + name + ", contractNo=" + contractNo + ", cityId=" + cityId + ", department=" + department + ", investmentMountStart=" + investmentMountStart + ", investmentMountEnd=" + investmentMountEnd
                + ", designAreaStart=" + designAreaStart + ", designAreaEnd=" + designAreaEnd + ", type=" + type + ", category=" + category + ", haveTechnical=" + haveTechnical + ", nature=" + nature + ", ratioFrom=" + ratioFrom + ", ratioTo=" + ratioTo + ", contractCapitalFrom=" + contractCapitalFrom + ", contractCapitalTo=" + contractCapitalTo + ", projectLiable=" + projectLiable + ", contractAwardCompany=" + contractAwardCompany + "]";
    }

}
