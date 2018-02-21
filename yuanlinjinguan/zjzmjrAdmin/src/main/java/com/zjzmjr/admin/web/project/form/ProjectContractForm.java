package com.zjzmjr.admin.web.project.form;

import java.util.List;

import com.zjzmjr.core.common.biz.BaseForm;
import com.zjzmjr.core.model.project.ContractPayment;

/**
 * 项目合同form
 * 
 * @author oms
 * @version $Id: ProjectContractForm.java, v 0.1 2017-8-28 下午1:29:36 oms Exp $
 */
public class ProjectContractForm extends BaseForm {

    /**  */
    private static final long serialVersionUID = -958793835101113700L;

    /**
     * 付款方式
     */
    private ContractPayment payment;

    /**
     * 所属公司
     */
    private Integer companyId;

    /**
     * 合同编号或者是合同编号的采番类型
     */
    private String contractNo;

    /**
     * 签约日期
     */
    private String signDate;

    /**
     * 项目编号
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 发包单位
     */
    private String companyName;

    /**
     * 项目编号
     */
    private String projectNo;

    /**
     * 技术负责人
     */
    private String projectLeader;

    /**
     * 经营专员
     */
    private Integer managePerson;

    /**
     * 所在城市
     */
    private Integer areaId;

    /**
     * 项目性质
     */
    private Integer nature;

    /**
     * 项目类别
     */
    private Integer category;

    /**
     * 项目类型
     */
    private Integer type;

    /**
     * 设计面积
     */
    private Integer designArea;

    /**
     * 投资额
     */
    private String investmentMount;

    /**
     * 所在城市
     */
    private Integer city;

    /**
     * 详细地址
     */
    private String address;

    // 是否有方案设计
    private Integer haveScheme;

    // 是否有扩初方案设计
    private Integer haveDevelopment;

    // 是否有施工图设计
    private Integer haveDrawing;

    // 是否有规划设计
    private Integer havePlanning;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 录入开始时间
     */
    private String createTimeStart;

    /**
     * 录入结束时间
     */
    private String createTimeEnd;

    /**
     * 合同金额
     */
    private String contractCapital;

    /**
     * 开始合同金额
     */
    private String capitalFrom;

    /**
     * 结束合同金额
     */
    private String capitalTo;

    /**
     * 合同备注
     */
    private String contractMemo;

    /**
     * 院方比例
     */
    private String ratio;

    /**
     * 院方比例开始值
     */
    private String ratioFrom;

    /**
     * 院方比例结束值
     */
    private String ratioTo;

    /**
     * 比例备注
     */
    private String ratioMemo;
    
    /**
     * 付款方式list
     */
    private List<ContractPayment> contractPayment; 
    
    /**
     * 合同操作发包单位
     */
    private Integer contractAwardCompany; 
    
    /**
     * 合同id
     */
    private Integer id;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 项目是否修改
     */
    private Integer projectUpdate;
    
    /**
     * 合同是否修改
     */
    private Integer contractUpdate;
    
    /**
     * 付款方式修改
     */
    private Integer paymentUpdate;
    
    /**
     * 临时项目id
     */
    private Integer temporaryId;
    
    /**
     * 项目版本号
     */
    private Integer version;
    
    /**
     * 临时合同id
     */
    private Integer temporaryContractId;
    
    /**
     * 合同版本号
     */
    private Integer contractVersion;
  
    /**
     * 投资额开始值
     */
    private String investmentMountStart;
    
    /**
     * 投资额结束值
     */
    private String investmentMountEnd;
    
    /**
     * 只看技术标
     */
    private Integer haveTechnical;
    
    private Integer subpackageStatus;
    
    /**
     * 项目负责人
     */
    private String projectLiable;
    
    /**
     * 设计面积区间(起始值)
     */
    private String  designAreaFrom;
    
    /**
     * 设计面积区间(结束值)
     */
    private String  designAreaTo;
    
    /**
     * 合同金额区间(起始值)
     */
    private String contractCapitalFrom;
    
    /**
     * 合同金额区间(结束值)
     */
    private String contractCapitalTo;
    
    @Override
    public String resolveFiled(String arg0) {
        return null;
    }

    public ContractPayment getPayment() {
        return payment;
    }

    public void setPayment(ContractPayment payment) {
        this.payment = payment;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
    }

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getProjectNo() {
        return projectNo;
    }

    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
        this.projectLeader = projectLeader;
    }

    public Integer getManagePerson() {
        return managePerson;
    }

    public void setManagePerson(Integer managePerson) {
        this.managePerson = managePerson;
    }

    public Integer getAreaId() {
        return areaId;
    }

    public void setAreaId(Integer areaId) {
        this.areaId = areaId;
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

    public Integer getDesignArea() {
        return designArea;
    }

    public void setDesignArea(Integer designArea) {
        this.designArea = designArea;
    }

    public String getInvestmentMount() {
        return investmentMount;
    }

    public void setInvestmentMount(String investmentMount) {
        this.investmentMount = investmentMount;
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
        this.address = address;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getCreateTimeStart() {
        return createTimeStart;
    }

    public void setCreateTimeStart(String createTimeStart) {
        this.createTimeStart = createTimeStart;
    }

    public String getCreateTimeEnd() {
        return createTimeEnd;
    }

    public void setCreateTimeEnd(String createTimeEnd) {
        this.createTimeEnd = createTimeEnd;
    }

    public String getContractCapital() {
        return contractCapital;
    }

    public void setContractCapital(String contractCapital) {
        this.contractCapital = contractCapital;
    }

    public String getCapitalFrom() {
        return capitalFrom;
    }

    public void setCapitalFrom(String capitalFrom) {
        this.capitalFrom = capitalFrom;
    }

    public String getCapitalTo() {
        return capitalTo;
    }

    public void setCapitalTo(String capitalTo) {
        this.capitalTo = capitalTo;
    }

    public String getContractMemo() {
        return contractMemo;
    }

    public void setContractMemo(String contractMemo) {
        this.contractMemo = contractMemo;
    }

    public String getRatio() {
        return ratio;
    }

    public void setRatio(String ratio) {
        this.ratio = ratio;
    }

    public String getRatioFrom() {
        return ratioFrom;
    }

    public void setRatioFrom(String ratioFrom) {
        this.ratioFrom = ratioFrom;
    }

    public String getRatioTo() {
        return ratioTo;
    }

    public void setRatioTo(String ratioTo) {
        this.ratioTo = ratioTo;
    }

    public String getRatioMemo() {
        return ratioMemo;
    }

    public void setRatioMemo(String ratioMemo) {
        this.ratioMemo = ratioMemo;
    }
    
    public List<ContractPayment> getContractPayment() {
        return contractPayment;
    }

    
    public void setContractPayment(List<ContractPayment> contractPayment) {
        this.contractPayment = contractPayment;
    }

    
    public Integer getContractAwardCompany() {
        return contractAwardCompany;
    }

    
    public void setContractAwardCompany(Integer contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    
    public Integer getId() {
        return id;
    }

    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public String getName() {
        return name;
    }

    
    public void setName(String name) {
        this.name = name;
    }
    
    public Integer getProjectUpdate() {
        return projectUpdate;
    }

    
    public void setProjectUpdate(Integer projectUpdate) {
        this.projectUpdate = projectUpdate;
    }

    
    public Integer getContractUpdate() {
        return contractUpdate;
    }

    
    public void setContractUpdate(Integer contractUpdate) {
        this.contractUpdate = contractUpdate;
    }
    
    public Integer getTemporaryId() {
        return temporaryId;
    }

    
    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
    }

    
    public Integer getVersion() {
        return version;
    }

    
    public void setVersion(Integer version) {
        this.version = version;
    }

    
    public Integer getTemporaryContractId() {
        return temporaryContractId;
    }

    
    public void setTemporaryContractId(Integer temporaryContractId) {
        this.temporaryContractId = temporaryContractId;
    }
    
    public Integer getContractVersion() {
        return contractVersion;
    }

    
    public void setContractVersion(Integer contractVersion) {
        this.contractVersion = contractVersion;
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
    
    public Integer getHaveTechnical() {
        return haveTechnical;
    }

    
    public void setHaveTechnical(Integer haveTechnical) {
        this.haveTechnical = haveTechnical;
    }
    
    public Integer getPaymentUpdate() {
        return paymentUpdate;
    }

    
    public void setPaymentUpdate(Integer paymentUpdate) {
        this.paymentUpdate = paymentUpdate;
    }
    
    public Integer getSubpackageStatus() {
        return subpackageStatus;
    }

    
    public void setSubpackageStatus(Integer subpackageStatus) {
        this.subpackageStatus = subpackageStatus;
    }

    
    public String getProjectLiable() {
        return projectLiable;
    }

    
    public void setProjectLiable(String projectLiable) {
        this.projectLiable = projectLiable;
    }
    
    
    public String getDesignAreaFrom() {
        return designAreaFrom;
    }

    
    public void setDesignAreaFrom(String designAreaFrom) {
        this.designAreaFrom = designAreaFrom;
    }

    
    public String getDesignAreaTo() {
        return designAreaTo;
    }

    
    public void setDesignAreaTo(String designAreaTo) {
        this.designAreaTo = designAreaTo;
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

    @Override
    public String toString() {
        return "ProjectContractForm [payment=" + payment + ", companyId=" + companyId + ", contractNo=" + contractNo + ", signDate=" + signDate + ", projectId=" + projectId + ", projectName=" + projectName + ", companyName=" + companyName + ", projectNo=" + projectNo + ", projectLeader=" + projectLeader + ", managePerson=" + managePerson + ", areaId=" + areaId + ", nature=" + nature + ", category=" + category + ", type=" + type + ", designArea=" + designArea + ", investmentMount="
                + investmentMount + ", city=" + city + ", address=" + address + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", contractCapital=" + contractCapital + ", capitalFrom=" + capitalFrom + ", capitalTo=" + capitalTo + ", contractMemo=" + contractMemo + ", ratio=" + ratio + ", ratioFrom="
                + ratioFrom + ", ratioTo=" + ratioTo + ", ratioMemo=" + ratioMemo + ", contractPayment=" + contractPayment + ", contractAwardCompany=" + contractAwardCompany + ", id=" + id + ", name=" + name + ", projectUpdate=" + projectUpdate + ", contractUpdate=" + contractUpdate + ", paymentUpdate=" + paymentUpdate + ", temporaryId=" + temporaryId + ", version=" + version + ", temporaryContractId=" + temporaryContractId + ", contractVersion=" + contractVersion + ", investmentMountStart="
                + investmentMountStart + ", investmentMountEnd=" + investmentMountEnd + ", haveTechnical=" + haveTechnical + ", subpackageStatus=" + subpackageStatus + ", projectLiable=" + projectLiable + ", designAreaFrom=" + designAreaFrom + ", designAreaTo=" + designAreaTo + ", contractCapitalFrom=" + contractCapitalFrom + ", contractCapitalTo=" + contractCapitalTo + "]";
    }

}
