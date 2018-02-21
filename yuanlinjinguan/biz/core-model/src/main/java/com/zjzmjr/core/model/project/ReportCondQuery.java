package com.zjzmjr.core.model.project;

import java.math.BigDecimal;
import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: ReportCondQuery.java, v 0.1 2017-11-24 下午4:11:53 oms Exp $
 */
public class ReportCondQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 3858140167337740259L;

    private Integer id;

    private Integer companyId;

    private Integer userId;

    private String timeStart;

    private String timeEnd;

    private String projectLeader;

    /**
     * 负责人集合
     */
    private List<String> projectLeaderLst;

    private String managePerson;

    private List<String> managePersonLst;

    private String projectStep;

    private List<String> projectStepLst;

    private Integer status;

    private List<String> statusLst;

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
    private BigDecimal ratioFrom;

    /**
     * 院方比例结束值
     */
    private BigDecimal ratioTo;

    /**
     * 开始合同金额
     */
    private BigDecimal contractCapitalFrom;

    /**
     * 结束合同金额
     */
    private BigDecimal contractCapitalTo;
    
    /**
     * 项目负责人
     */
    private String projectLiable;
    
    /**
     * 发包单位
     */
    private Integer contractAwardCompany;

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }

    
    public Integer getCompanyId() {
        return companyId;
    }

    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    
    public Integer getUserId() {
        return userId;
    }

    
    public void setUserId(Integer userId) {
        this.userId = userId;
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

    
    public List<String> getProjectLeaderLst() {
        return projectLeaderLst;
    }

    
    public void setProjectLeaderLst(List<String> projectLeaderLst) {
        this.projectLeaderLst = projectLeaderLst;
    }

    
    public String getManagePerson() {
        return managePerson;
    }

    
    public void setManagePerson(String managePerson) {
        this.managePerson = managePerson;
    }

    
    public List<String> getManagePersonLst() {
        return managePersonLst;
    }

    
    public void setManagePersonLst(List<String> managePersonLst) {
        this.managePersonLst = managePersonLst;
    }

    
    public String getProjectStep() {
        return projectStep;
    }

    
    public void setProjectStep(String projectStep) {
        this.projectStep = projectStep;
    }

    
    public List<String> getProjectStepLst() {
        return projectStepLst;
    }

    
    public void setProjectStepLst(List<String> projectStepLst) {
        this.projectStepLst = projectStepLst;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public List<String> getStatusLst() {
        return statusLst;
    }

    
    public void setStatusLst(List<String> statusLst) {
        this.statusLst = statusLst;
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
    
    
    public BigDecimal getRatioFrom() {
        return ratioFrom;
    }

    
    public void setRatioFrom(BigDecimal ratioFrom) {
        this.ratioFrom = ratioFrom;
    }


    
    public BigDecimal getRatioTo() {
        return ratioTo;
    }


    
    public void setRatioTo(BigDecimal ratioTo) {
        this.ratioTo = ratioTo;
    }


    
    public BigDecimal getContractCapitalFrom() {
        return contractCapitalFrom;
    }


    
    public void setContractCapitalFrom(BigDecimal contractCapitalFrom) {
        this.contractCapitalFrom = contractCapitalFrom;
    }


    
    public BigDecimal getContractCapitalTo() {
        return contractCapitalTo;
    }


    
    public void setContractCapitalTo(BigDecimal contractCapitalTo) {
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
        return "ReportCondQuery [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", projectLeader=" + projectLeader + ", projectLeaderLst=" + projectLeaderLst + ", managePerson=" + managePerson + ", managePersonLst=" + managePersonLst + ", projectStep=" + projectStep + ", projectStepLst=" + projectStepLst + ", status=" + status + ", statusLst=" + statusLst + ", dispField=" + dispField + ", projectNo=" + projectNo + ", name="
                + name + ", contractNo=" + contractNo + ", cityId=" + cityId + ", department=" + department + ", investmentMountStart=" + investmentMountStart + ", investmentMountEnd=" + investmentMountEnd + ", designAreaStart=" + designAreaStart + ", designAreaEnd=" + designAreaEnd + ", type=" + type + ", category=" + category + ", haveTechnical=" + haveTechnical + ", nature=" + nature + ", ratioFrom=" + ratioFrom + ", ratioTo=" + ratioTo + ", contractCapitalFrom=" + contractCapitalFrom
                + ", contractCapitalTo=" + contractCapitalTo + ", projectLiable=" + projectLiable + ", contractAwardCompany=" + contractAwardCompany + "]";
    }
    
    
    
}
