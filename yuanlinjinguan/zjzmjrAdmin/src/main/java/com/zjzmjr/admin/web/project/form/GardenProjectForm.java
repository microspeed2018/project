package com.zjzmjr.admin.web.project.form;

import com.zjzmjr.core.common.biz.BaseForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: GardenProjectForm.java, v 0.1 2017-8-14 下午1:45:45 oms Exp $
 */
public class GardenProjectForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -1232674001673399779L;

    private Integer id;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 用户编号
     */
    private Integer userId;

    private String projectNo;
    
    /**
     * 城市名
     */
    private String cityId;

    /**
     * 技术负责人
     */
    private Integer projectLeader;
    
    /**
     * 经营部门
     */
    private Integer department;
    

    /**
     * 经营专员
     */
    private Integer managementPersonnel;
    
    /**
     * 发包单位
     */
    private Integer contractAwardCompany;

    /**
     * 项目名称
     */
    private String name;

    /**
     * 项目步骤阶段
     */
    private Integer step;

    /**
     * 项目步骤
     */
    private String stepLst;

    /**
     * 比当前阶段都小的阶段
     */
    private Integer maxStep;

    /**
     * 比当前阶段都大的阶段
     */
    private Integer minStep;

    private Integer status;

    /**
     * 临时状态参数
     */
    private Integer subStatus;

    /**
     * 截止日期开始
     */
    private String applyDeadlineStart;

    /**
     * 截止日期终止
     */
    private String applyDeadlineEnd;

    /**
     * 招标报名截止日期
     */
    private String bidDeadlineStart;

    /**
     * 招标报名截止日期
     */
    private String bidDeadlineEnd;

    /**
     * 保证金截止日期
     */
    private String marginDeadlineStart;

    /**
     * 保证金截止日期
     */
    private String marginDeadlineEnd;

    /**
     * 投标截止日期
     */
    private String tenderDeadlineStart;

    /**
     * 投标截止日期
     */
    private String tenderDeadlineEnd;
    
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
     * 搜索录入开始时间
     */
    private String actiDatetime;
    
    /**
     * 搜索录入结束时间
     */
    private String actiDatetimeEnd;
    
    /**
     * 是否是一览操作 有值：是  无值：否
     */
    private Integer isAllData;

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

    
    public String getProjectNo() {
        return projectNo;
    }

    
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    
    public String getCityId() {
        return cityId;
    }

    
    public void setCityId(String cityId) {
        this.cityId = cityId;
    }

    
    public Integer getProjectLeader() {
        return projectLeader;
    }

    
    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    
    public Integer getDepartment() {
        return department;
    }

    
    public void setDepartment(Integer department) {
        this.department = department;
    }

    
    public Integer getManagementPersonnel() {
        return managementPersonnel;
    }

    
    public void setManagementPersonnel(Integer managementPersonnel) {
        this.managementPersonnel = managementPersonnel;
    }

    
    public Integer getContractAwardCompany() {
        return contractAwardCompany;
    }

    
    public void setContractAwardCompany(Integer contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }

    
    public Integer getStep() {
        return step;
    }

    
    public void setStep(Integer step) {
        this.step = step;
    }

    
    public String getStepLst() {
        return stepLst;
    }

    
    public void setStepLst(String stepLst) {
        this.stepLst = stepLst;
    }

    
    public Integer getMaxStep() {
        return maxStep;
    }

    
    public void setMaxStep(Integer maxStep) {
        this.maxStep = maxStep;
    }

    
    public Integer getMinStep() {
        return minStep;
    }

    
    public void setMinStep(Integer minStep) {
        this.minStep = minStep;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public Integer getSubStatus() {
        return subStatus;
    }

    
    public void setSubStatus(Integer subStatus) {
        this.subStatus = subStatus;
    }

    
    public String getApplyDeadlineStart() {
        return applyDeadlineStart;
    }

    
    public void setApplyDeadlineStart(String applyDeadlineStart) {
        this.applyDeadlineStart = applyDeadlineStart;
    }

    
    public String getApplyDeadlineEnd() {
        return applyDeadlineEnd;
    }

    
    public void setApplyDeadlineEnd(String applyDeadlineEnd) {
        this.applyDeadlineEnd = applyDeadlineEnd;
    }

    
    public String getBidDeadlineStart() {
        return bidDeadlineStart;
    }

    
    public void setBidDeadlineStart(String bidDeadlineStart) {
        this.bidDeadlineStart = bidDeadlineStart;
    }

    
    public String getBidDeadlineEnd() {
        return bidDeadlineEnd;
    }

    
    public void setBidDeadlineEnd(String bidDeadlineEnd) {
        this.bidDeadlineEnd = bidDeadlineEnd;
    }

    
    public String getMarginDeadlineStart() {
        return marginDeadlineStart;
    }

    
    public void setMarginDeadlineStart(String marginDeadlineStart) {
        this.marginDeadlineStart = marginDeadlineStart;
    }

    
    public String getMarginDeadlineEnd() {
        return marginDeadlineEnd;
    }

    
    public void setMarginDeadlineEnd(String marginDeadlineEnd) {
        this.marginDeadlineEnd = marginDeadlineEnd;
    }

    
    public String getTenderDeadlineStart() {
        return tenderDeadlineStart;
    }

    
    public void setTenderDeadlineStart(String tenderDeadlineStart) {
        this.tenderDeadlineStart = tenderDeadlineStart;
    }

    
    public String getTenderDeadlineEnd() {
        return tenderDeadlineEnd;
    }

    
    public void setTenderDeadlineEnd(String tenderDeadlineEnd) {
        this.tenderDeadlineEnd = tenderDeadlineEnd;
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

    
    public String getActiDatetime() {
        return actiDatetime;
    }


    
    public void setActiDatetime(String actiDatetime) {
        this.actiDatetime = actiDatetime;
    }


    
    public String getActiDatetimeEnd() {
        return actiDatetimeEnd;
    }


    
    public void setActiDatetimeEnd(String actiDatetimeEnd) {
        this.actiDatetimeEnd = actiDatetimeEnd;
    }

    
    public Integer getIsAllData() {
        return isAllData;
    }


    
    public void setIsAllData(Integer isAllData) {
        this.isAllData = isAllData;
    }


    @Override
    public String toString() {
        return "GardenProjectForm [id=" + id + ", companyId=" + companyId + ", userId=" + userId + ", projectNo=" + projectNo + ", cityId=" + cityId + ", projectLeader=" + projectLeader + ", department=" + department + ", managementPersonnel=" + managementPersonnel + ", contractAwardCompany=" + contractAwardCompany + ", name=" + name + ", step=" + step + ", stepLst=" + stepLst + ", maxStep=" + maxStep + ", minStep=" + minStep + ", status=" + status + ", subStatus=" + subStatus
                + ", applyDeadlineStart=" + applyDeadlineStart + ", applyDeadlineEnd=" + applyDeadlineEnd + ", bidDeadlineStart=" + bidDeadlineStart + ", bidDeadlineEnd=" + bidDeadlineEnd + ", marginDeadlineStart=" + marginDeadlineStart + ", marginDeadlineEnd=" + marginDeadlineEnd + ", tenderDeadlineStart=" + tenderDeadlineStart + ", tenderDeadlineEnd=" + tenderDeadlineEnd + ", investmentMountStart=" + investmentMountStart + ", investmentMountEnd=" + investmentMountEnd + ", designAreaStart="
                + designAreaStart + ", designAreaEnd=" + designAreaEnd + ", type=" + type + ", category=" + category + ", haveTechnical=" + haveTechnical + ", nature=" + nature + ", actiDatetime=" + actiDatetime + ", actiDatetimeEnd=" + actiDatetimeEnd + ", isAllData=" + isAllData + "]";
    }

}
