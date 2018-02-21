package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 项目表
 * 
 * @author oms
 * @version $Id: GardenProject.java, v 0.1 2017-8-21 下午6:06:21 oms Exp $
 */
public class GardenProject implements Serializable {

    /**  */
    private static final long serialVersionUID = 7200328941989478777L;

    private Integer id;

    private Integer companyId;

    // 项目编号
    private String projectNo;

    // 项目名称
    private String name;

    // 介绍人
    private Integer introducer;

    // 所在城市
    private Integer city;

    // 详细地址
    private String address;

    // 设计面积
    private Integer designArea;

    // 投资额
    private BigDecimal investmentMount;

    // 项目性质
    private Integer nature;

    // 项目类别
    private Integer category;

    // 项目类型
    private Integer type;

    // 经营部门
    private Integer managementDepartment;

    // 项目介绍
    private String introduction;

    // 意向合作方式
    private Integer intentionalCooperation;

    // 是否技术支持 0:否 1:是
    private Integer needTechnical;

    // 前期技术支持要求
    private String preRequirements;

    // 发包单位
    private Integer contractAwardCompany;

    // 是否有方案设计 0:否 1:是
    private Integer haveScheme;

    // 是否有扩初方案设计 0:否 1:是
    private Integer haveDevelopment;

    // 是否有施工图设计 0:否 1:是
    private Integer haveDrawing;

    // 是否有规划设计 0:否 1:是
    private Integer havePlanning;

    private Integer managementPersonnel;

    /**
     * 技术负责人
     */
    private Integer projectLeader;

    /**
     * 合同或商务标技术负责人
     */
    private String projectLiable;

    /**
     * 项目申请截止日期
     */
    private String applyDeadline;

    private Integer isMajorProject;

    private String biddingDeadline;

    private String biddingMemo;

    private Integer biddingDocumentsProducer;

    private Integer registrationResults;

    private String registrationResultsMemo;

    private Integer haveTechnical;

    private String marginDeadline;

    private String tenderDeadline;

    private String biddingDocumentsMemo;

    private BigDecimal bail;

    private String latestRemittanceDate;

    private String remittanceCompany;

    private String bankName;

    private String accountNo;

    private String bailMemo;

    private Integer businessProducer;

    private BigDecimal tenderOffer;

    private Integer isWinBidding;

    private String nopassReason;

    private Integer step;

    /**
     * 保证金上传步骤
     */
    private Integer subStep;

    /**
     * 技术标上传步骤
     */
    private Integer subStep2;

    private Integer status;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

    private String memo;

    /**
     * 数据是否修改标志
     * 
     * @return
     */
    private Integer isUpdate;

    /**
     * 临时数据id
     */
    private Integer temporaryId;

    /**
     * 保存或申请操作标志
     */
    private Integer operation;
    
    /**
     * 未退保证金
     */
    private BigDecimal nobackBail;

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

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo == null ? null : projectNo.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public Integer getIntroducer() {
        return introducer;
    }

    public void setIntroducer(Integer introducer) {
        this.introducer = introducer;
    }

    public Integer getCity() {
        return city;
    }

    public void setCity(Integer city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getDesignArea() {
        return designArea;
    }

    public void setDesignArea(Integer designArea) {
        this.designArea = designArea;
    }

    public BigDecimal getInvestmentMount() {
        return investmentMount;
    }

    public void setInvestmentMount(BigDecimal investmentMount) {
        this.investmentMount = investmentMount;
    }

    public Integer getNature() {
        return nature;
    }

    public void setNature(Integer nature) {
        this.nature = nature;
    }

    public Integer getCategory() {
        return category;
    }

    public void setCategory(Integer category) {
        this.category = category;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getManagementDepartment() {
        return managementDepartment;
    }

    public void setManagementDepartment(Integer managementDepartment) {
        this.managementDepartment = managementDepartment;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Integer getIntentionalCooperation() {
        return intentionalCooperation;
    }

    public void setIntentionalCooperation(Integer intentionalCooperation) {
        this.intentionalCooperation = intentionalCooperation;
    }

    public Integer getNeedTechnical() {
        return needTechnical;
    }

    public void setNeedTechnical(Integer needTechnical) {
        this.needTechnical = needTechnical;
    }

    public String getPreRequirements() {
        return preRequirements;
    }

    public void setPreRequirements(String preRequirements) {
        this.preRequirements = preRequirements == null ? null : preRequirements.trim();
    }

    public Integer getContractAwardCompany() {
        return contractAwardCompany;
    }

    public void setContractAwardCompany(Integer contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    public Integer getHaveScheme() {
        return haveScheme;
    }

    public void setHaveScheme(Integer haveScheme) {
        this.haveScheme = haveScheme;
    }

    public Integer getHaveDevelopment() {
        return haveDevelopment;
    }

    public void setHaveDevelopment(Integer haveDevelopment) {
        this.haveDevelopment = haveDevelopment;
    }

    public Integer getHaveDrawing() {
        return haveDrawing;
    }

    public void setHaveDrawing(Integer haveDrawing) {
        this.haveDrawing = haveDrawing;
    }

    public Integer getHavePlanning() {
        return havePlanning;
    }

    public void setHavePlanning(Integer havePlanning) {
        this.havePlanning = havePlanning;
    }

    public Integer getManagementPersonnel() {
        return managementPersonnel;
    }

    public void setManagementPersonnel(Integer managementPersonnel) {
        this.managementPersonnel = managementPersonnel;
    }

    public Integer getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(Integer projectLeader) {
        this.projectLeader = projectLeader;
    }

    public String getProjectLiable() {
        return projectLiable;
    }

    public void setProjectLiable(String projectLiable) {
        this.projectLiable = projectLiable;
    }

    public String getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(String applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public Integer getIsMajorProject() {
        return isMajorProject;
    }

    public void setIsMajorProject(Integer isMajorProject) {
        this.isMajorProject = isMajorProject;
    }

    public String getBiddingDeadline() {
        return biddingDeadline;
    }

    public void setBiddingDeadline(String biddingDeadline) {
        this.biddingDeadline = biddingDeadline == null ? null : biddingDeadline.trim();
    }

    public String getBiddingMemo() {
        return biddingMemo;
    }

    public void setBiddingMemo(String biddingMemo) {
        this.biddingMemo = biddingMemo == null ? null : biddingMemo.trim();
    }

    public Integer getBiddingDocumentsProducer() {
        return biddingDocumentsProducer;
    }

    public void setBiddingDocumentsProducer(Integer biddingDocumentsProducer) {
        this.biddingDocumentsProducer = biddingDocumentsProducer;
    }

    public Integer getRegistrationResults() {
        return registrationResults;
    }

    public void setRegistrationResults(Integer registrationResults) {
        this.registrationResults = registrationResults;
    }

    public String getRegistrationResultsMemo() {
        return registrationResultsMemo;
    }

    public void setRegistrationResultsMemo(String registrationResultsMemo) {
        this.registrationResultsMemo = registrationResultsMemo == null ? null : registrationResultsMemo.trim();
    }

    public Integer getHaveTechnical() {
        return haveTechnical;
    }

    public void setHaveTechnical(Integer haveTechnical) {
        this.haveTechnical = haveTechnical;
    }

    public String getMarginDeadline() {
        return marginDeadline;
    }

    public void setMarginDeadline(String marginDeadline) {
        this.marginDeadline = marginDeadline == null ? null : marginDeadline.trim();
    }

    public String getTenderDeadline() {
        return tenderDeadline;
    }

    public void setTenderDeadline(String tenderDeadline) {
        this.tenderDeadline = tenderDeadline == null ? null : tenderDeadline.trim();
    }

    public String getBiddingDocumentsMemo() {
        return biddingDocumentsMemo;
    }

    public void setBiddingDocumentsMemo(String biddingDocumentsMemo) {
        this.biddingDocumentsMemo = biddingDocumentsMemo == null ? null : biddingDocumentsMemo.trim();
    }

    public BigDecimal getBail() {
        return bail;
    }

    public void setBail(BigDecimal bail) {
        this.bail = bail;
    }

    public String getLatestRemittanceDate() {
        return latestRemittanceDate;
    }

    public void setLatestRemittanceDate(String latestRemittanceDate) {
        this.latestRemittanceDate = latestRemittanceDate == null ? null : latestRemittanceDate.trim();
    }

    public String getRemittanceCompany() {
        return remittanceCompany;
    }

    public void setRemittanceCompany(String remittanceCompany) {
        this.remittanceCompany = remittanceCompany == null ? null : remittanceCompany.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo == null ? null : accountNo.trim();
    }

    public String getBailMemo() {
        return bailMemo;
    }

    public void setBailMemo(String bailMemo) {
        this.bailMemo = bailMemo == null ? null : bailMemo.trim();
    }

    public Integer getBusinessProducer() {
        return businessProducer;
    }

    public void setBusinessProducer(Integer businessProducer) {
        this.businessProducer = businessProducer;
    }

    public BigDecimal getTenderOffer() {
        return tenderOffer;
    }

    public void setTenderOffer(BigDecimal tenderOffer) {
        this.tenderOffer = tenderOffer;
    }

    public Integer getIsWinBidding() {
        return isWinBidding;
    }

    public void setIsWinBidding(Integer isWinBidding) {
        this.isWinBidding = isWinBidding;
    }

    public String getNopassReason() {
        return nopassReason;
    }

    public void setNopassReason(String nopassReason) {
        this.nopassReason = nopassReason == null ? null : nopassReason.trim();
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getCreateUserId() {
        return createUserId;
    }

    public void setCreateUserId(Integer createUserId) {
        this.createUserId = createUserId;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getUpdateUserId() {
        return updateUserId;
    }

    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }

    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    public Integer getSubStep() {
        return subStep;
    }

    public void setSubStep(Integer subStep) {
        this.subStep = subStep;
    }

    public Integer getSubStep2() {
        return subStep2;
    }

    public void setSubStep2(Integer subStep2) {
        this.subStep2 = subStep2;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public Integer getIsUpdate() {
        return isUpdate;
    }

    public void setIsUpdate(Integer isUpdate) {
        this.isUpdate = isUpdate;
    }

    public Integer getTemporaryId() {
        return temporaryId;
    }

    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
    }

    public Integer getOperation() {
        return operation;
    }

    public void setOperation(Integer operation) {
        this.operation = operation;
    }
    
    public BigDecimal getNobackBail() {
        return nobackBail;
    }

    
    public void setNobackBail(BigDecimal nobackBail) {
        this.nobackBail = nobackBail;
    }

    @Override
    public String toString() {
        return "GardenProject [id=" + id + ", companyId=" + companyId + ", projectNo=" + projectNo + ", name=" + name + ", introducer=" + introducer + ", city=" + city + ", address=" + address + ", designArea=" + designArea + ", investmentMount=" + investmentMount + ", nature=" + nature + ", category=" + category + ", type=" + type + ", managementDepartment=" + managementDepartment + ", introduction=" + introduction + ", intentionalCooperation=" + intentionalCooperation + ", needTechnical="
                + needTechnical + ", preRequirements=" + preRequirements + ", contractAwardCompany=" + contractAwardCompany + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", managementPersonnel=" + managementPersonnel + ", projectLeader=" + projectLeader + ", projectLiable=" + projectLiable + ", applyDeadline=" + applyDeadline + ", isMajorProject=" + isMajorProject + ", biddingDeadline="
                + biddingDeadline + ", biddingMemo=" + biddingMemo + ", biddingDocumentsProducer=" + biddingDocumentsProducer + ", registrationResults=" + registrationResults + ", registrationResultsMemo=" + registrationResultsMemo + ", haveTechnical=" + haveTechnical + ", marginDeadline=" + marginDeadline + ", tenderDeadline=" + tenderDeadline + ", biddingDocumentsMemo=" + biddingDocumentsMemo + ", bail=" + bail + ", latestRemittanceDate=" + latestRemittanceDate + ", remittanceCompany="
                + remittanceCompany + ", bankName=" + bankName + ", accountNo=" + accountNo + ", bailMemo=" + bailMemo + ", businessProducer=" + businessProducer + ", tenderOffer=" + tenderOffer + ", isWinBidding=" + isWinBidding + ", nopassReason=" + nopassReason + ", step=" + step + ", subStep=" + subStep + ", subStep2=" + subStep2 + ", status=" + status + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId
                + ", version=" + version + ", memo=" + memo + ", isUpdate=" + isUpdate + ", temporaryId=" + temporaryId + ", operation=" + operation + ", nobackBail=" + nobackBail + "]";
    }

}