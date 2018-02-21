package com.zjzmjr.core.model.project;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 项目合同的查询条件
 * 
 * @author oms
 * @version $Id: ContractQuery.java, v 0.1 2017-8-24 下午7:13:10 oms Exp $
 */
public class ContractQuery extends BasePageQuery {

    /**  */
    private static final long serialVersionUID = 1813207559548461299L;

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
     * 项目编号
     */
    private List<Integer> projectLst;

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
    private BigDecimal contractCapital;

    /**
     * 开始合同金额
     */
    private BigDecimal capitalFrom;

    /**
     * 结束合同金额
     */
    private BigDecimal capitalTo;

    /**
     * 合同备注
     */
    private String contractMemo;

    /**
     * 院方比例
     */
    private BigDecimal ratio;

    /**
     * 院方比例开始值
     */
    private BigDecimal ratioFrom;

    /**
     * 院方比例结束值
     */
    private BigDecimal ratioTo;

    /**
     * 比例备注
     */
    private String ratioMemo;

    /**
     * 审核开票type
     */
    private Integer cashierType;

    /**
     * 投资额开始值
     */
    private BigDecimal investmentMountStart;

    /**
     * 投资额结束值
     */
    private BigDecimal investmentMountEnd;

    private Integer id;

    /**
     * 发包单位
     */
    private Integer contractAwardCompany;

    /**
     * 只看含技术标
     */
    private Integer haveTechnical;

    /**
     * 项目步骤
     */
    private Integer step;

    private Date updateTime;

    private Integer updateUserId;

    private Integer subpackageStatus;

    /**
     * 项目负责人
     */
    private String projectLiable;

    /**
     * 设计面积区间(起始值)
     */
    private String designAreaFrom;

    /**
     * 设计面积区间(结束值)
     */
    private String designAreaTo;

    /**
     * 合同金额区间(起始值)
     */
    private BigDecimal contractCapitalFrom;

    /**
     * 合同金额区间(结束值)
     */
    private BigDecimal contractCapitalTo;
    
    private String mobile;

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

    public BigDecimal getContractCapital() {
        return contractCapital;
    }

    public void setContractCapital(BigDecimal contractCapital) {
        this.contractCapital = contractCapital;
    }

    public BigDecimal getCapitalFrom() {
        return capitalFrom;
    }

    public void setCapitalFrom(BigDecimal capitalFrom) {
        this.capitalFrom = capitalFrom;
    }

    public BigDecimal getCapitalTo() {
        return capitalTo;
    }

    public void setCapitalTo(BigDecimal capitalTo) {
        this.capitalTo = capitalTo;
    }

    public String getContractMemo() {
        return contractMemo;
    }

    public void setContractMemo(String contractMemo) {
        this.contractMemo = contractMemo;
    }

    public BigDecimal getRatio() {
        return ratio;
    }

    public void setRatio(BigDecimal ratio) {
        this.ratio = ratio;
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

    public String getRatioMemo() {
        return ratioMemo;
    }

    public void setRatioMemo(String ratioMemo) {
        this.ratioMemo = ratioMemo;
    }

    public Integer getCashierType() {
        return cashierType;
    }

    public void setCashierType(Integer cashierType) {
        this.cashierType = cashierType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public BigDecimal getInvestmentMountStart() {
        return investmentMountStart;
    }

    public void setInvestmentMountStart(BigDecimal investmentMountStart) {
        this.investmentMountStart = investmentMountStart;
    }

    public BigDecimal getInvestmentMountEnd() {
        return investmentMountEnd;
    }

    public void setInvestmentMountEnd(BigDecimal investmentMountEnd) {
        this.investmentMountEnd = investmentMountEnd;
    }

    public Integer getContractAwardCompany() {
        return contractAwardCompany;
    }

    public void setContractAwardCompany(Integer contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    public Integer getHaveTechnical() {
        return haveTechnical;
    }

    public void setHaveTechnical(Integer haveTechnical) {
        this.haveTechnical = haveTechnical;
    }

    public Integer getStep() {
        return step;
    }

    public void setStep(Integer step) {
        this.step = step;
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

    public List<Integer> getProjectLst() {
        return projectLst;
    }

    public void setProjectLst(List<Integer> projectLst) {
        this.projectLst = projectLst;
    }
    
    public String getMobile() {
        return mobile;
    }

    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public String toString() {
        return "ContractQuery [payment=" + payment + ", companyId=" + companyId + ", contractNo=" + contractNo + ", signDate=" + signDate + ", projectId=" + projectId + ", projectLst=" + projectLst + ", projectName=" + projectName + ", companyName=" + companyName + ", projectNo=" + projectNo + ", projectLeader=" + projectLeader + ", managePerson=" + managePerson + ", areaId=" + areaId + ", nature=" + nature + ", category=" + category + ", type=" + type + ", designArea=" + designArea
                + ", investmentMount=" + investmentMount + ", city=" + city + ", address=" + address + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", status=" + status + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", contractCapital=" + contractCapital + ", capitalFrom=" + capitalFrom + ", capitalTo=" + capitalTo + ", contractMemo=" + contractMemo + ", ratio="
                + ratio + ", ratioFrom=" + ratioFrom + ", ratioTo=" + ratioTo + ", ratioMemo=" + ratioMemo + ", cashierType=" + cashierType + ", investmentMountStart=" + investmentMountStart + ", investmentMountEnd=" + investmentMountEnd + ", id=" + id + ", contractAwardCompany=" + contractAwardCompany + ", haveTechnical=" + haveTechnical + ", step=" + step + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", subpackageStatus=" + subpackageStatus + ", projectLiable="
                + projectLiable + ", designAreaFrom=" + designAreaFrom + ", designAreaTo=" + designAreaTo + ", contractCapitalFrom=" + contractCapitalFrom + ", contractCapitalTo=" + contractCapitalTo + ", mobile=" + mobile + "]";
    }

}
