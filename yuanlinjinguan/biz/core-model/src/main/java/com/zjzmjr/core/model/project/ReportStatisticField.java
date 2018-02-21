package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.math.BigDecimal;

import com.zjzmjr.common.annotation.FieldName;

/**
 * 统计报表显示字段内容
 * 
 * @author oms
 * @version $Id: ReportStatisticField.java, v 0.1 2017-9-19 下午3:45:52 oms Exp $
 */
public class ReportStatisticField implements Serializable {

    /**  */
    private static final long serialVersionUID = 3941866979097303939L;

    /**
     * 项目名称
     */
    @FieldName(name = "项目名称")
    private String projectName;

    /**
     * 项目介绍人
     */
    @FieldName(name = "项目介绍人")
    private String introducer;

    /**
     * 所在城市
     */
    @FieldName(name = "所在城市")
    private String city;

    /**
     * 详细地址
     */
    @FieldName(name = "详细地址")
    private String address;

    /**
     * 设计面积(㎡)
     */
    @FieldName(name = "设计面积(㎡)")
    private Integer designArea;

    /**
     * 投资额(万元)
     */
    @FieldName(name = "投资额(万元)")
    private BigDecimal investmentMount;

    /**
     * 项目性质
     */
    @FieldName(name = "项目性质")
    private String nature;

    /**
     * 项目类别
     */
    @FieldName(name = "项目类别")
    private String category;

    /**
     * 项目类型
     */
    @FieldName(name = "项目类型")
    private String type;

    /**
     * 经营部门
     */
    @FieldName(name = "经营部门")
    private String managementDepartment;

    /**
     * 项目介绍
     */
    @FieldName(name = "项目介绍")
    private String introduction;

    /**
     * 意向合作方式
     */
    @FieldName(name = "意向合作方式")
    private String intentionalCooperation;

    /**
     * 是否技术支持
     */
    @FieldName(name = "是否技术支持")
    private String needTechnical;

    /**
     * 前期技术支持要求
     */
    @FieldName(name = "前期技术支持要求")
    private String preRequirements;

    /**
     * 发包单位
     */
    @FieldName(name = "发包单位")
    private String contractAwardCompany;

    /**
     * 是否有方案设计
     */
    @FieldName(name = "是否有方案设计")
    private String haveScheme;

    /**
     * 是否有扩初方案设计
     */
    @FieldName(name = "是否有扩初方案设计")
    private String haveDevelopment;

    /**
     * 是否有施工图设计
     */
    @FieldName(name = "是否有施工图设计")
    private String haveDrawing;

    /**
     * 是否有规划设计
     */
    @FieldName(name = "是否有规划设计")
    private String havePlanning;

    /**
     * 经营专员
     */
    @FieldName(name = "经营专员")
    private String managementPersonnel;

    /**
     * 技术负责人
     */
    @FieldName(name = "技术负责人")
    private String projectLeader;

    /**
     * 项目负责人
     */
    @FieldName(name = "项目负责人")
    private String projectLiable;

    /**
     * 申请截止日期
     */
    @FieldName(name = "申请截止日期")
    private String applyDeadline;

    /**
     * 是否重点项目
     */
    @FieldName(name = "是否重点项目")
    private String isMajorProject;

    /**
     * 招标报名截止日期
     */
    @FieldName(name = "招标报名截止日期")
    private String biddingDeadline;

    /**
     * 招标备注
     */
    @FieldName(name = "招标备注")
    private String biddingMemo;

    /**
     * 报名文件制作人
     */
    @FieldName(name = "报名文件制作人")
    private String biddingDocumentsProducer;

    /**
     * 报名结果
     */
    @FieldName(name = "报名结果")
    private String registrationResults;

    /**
     * 报名结果原因
     */
    @FieldName(name = "报名结果原因")
    private String registrationResultsMemo;

    /**
     * 是否有技术标
     */
    @FieldName(name = "是否有技术标")
    private String haveTechnical;

    /**
     * 保证金截止日期
     */
    @FieldName(name = "保证金截止日期")
    private String marginDeadline;

    /**
     * 投标截止日期
     */
    @FieldName(name = "投标截止日期")
    private String tenderDeadline;

    /**
     * 招标文件备注
     */
    @FieldName(name = "招标文件备注")
    private String biddingDocumentsMemo;

    /**
     * 保证金
     */
    @FieldName(name = "保证金(万元)")
    private BigDecimal bail;

    /**
     * 最晚汇款日期
     */
    @FieldName(name = "最晚汇款日期")
    private String latestRemittanceDate;

    /**
     * 汇款单位
     */
    @FieldName(name = "汇款单位")
    private String remittanceCompany;

    /**
     * 开户银行
     */
    @FieldName(name = "开户银行")
    private String bankName;

    /**
     * 银行账号
     */
    @FieldName(name = "银行账号")
    private String accountNo;

    /**
     * 保证金备注
     */
    @FieldName(name = "保证金备注")
    private String bailMemo;

    /**
     * 商务标制作人
     */
    @FieldName(name = "商务标制作人")
    private String businessProducer;

    /**
     * 投标报价
     */
    @FieldName(name = "投标报价(万元)")
    private BigDecimal tenderOffer;

    /**
     * 是否中标
     */
    @FieldName(name = "是否中标")
    private String isWinBidding;

    /**
     * 不通过理由
     */
    @FieldName(name = "不通过理由")
    private String nopassReason;

    /**
     * 项目阶段
     */
    @FieldName(name = "项目阶段")
    private String step;

    /**
     * 状态
     */
    @FieldName(name = "状态")
    private String status;

    /**
     * 合同编号
     */
    @FieldName(name = "合同编号")
    private String contractNo;

    /**
     * 合同金额
     */
    @FieldName(name = "合同金额(万元)")
    private BigDecimal contractCapital;

    /**
     * 签约日期
     */
    @FieldName(name = "签约日期")
    private String signDate;

    /**
     * 合同备注
     */
    @FieldName(name = "合同备注")
    private String contractMemo;

    /**
     * 院方比例
     */
    @FieldName(name = "院方比例(%)")
    private BigDecimal ratio;

    /**
     * 比例备注
     */
    @FieldName(name = "比例备注")
    private String ratioMemo;

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getIntroducer() {
        return introducer;
    }

    public void setIntroducer(String introducer) {
        this.introducer = introducer;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getNature() {
        return nature;
    }

    public void setNature(String nature) {
        this.nature = nature;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getManagementDepartment() {
        return managementDepartment;
    }

    public void setManagementDepartment(String managementDepartment) {
        this.managementDepartment = managementDepartment;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public String getIntentionalCooperation() {
        return intentionalCooperation;
    }

    public void setIntentionalCooperation(String intentionalCooperation) {
        this.intentionalCooperation = intentionalCooperation;
    }

    public String getNeedTechnical() {
        return needTechnical;
    }

    public void setNeedTechnical(String needTechnical) {
        this.needTechnical = needTechnical;
    }

    public String getPreRequirements() {
        return preRequirements;
    }

    public void setPreRequirements(String preRequirements) {
        this.preRequirements = preRequirements;
    }

    public String getContractAwardCompany() {
        return contractAwardCompany;
    }

    public void setContractAwardCompany(String contractAwardCompany) {
        this.contractAwardCompany = contractAwardCompany;
    }

    public String getHaveScheme() {
        return haveScheme;
    }

    public void setHaveScheme(String haveScheme) {
        this.haveScheme = haveScheme;
    }

    public String getHaveDevelopment() {
        return haveDevelopment;
    }

    public void setHaveDevelopment(String haveDevelopment) {
        this.haveDevelopment = haveDevelopment;
    }

    public String getHaveDrawing() {
        return haveDrawing;
    }

    public void setHaveDrawing(String haveDrawing) {
        this.haveDrawing = haveDrawing;
    }

    public String getHavePlanning() {
        return havePlanning;
    }

    public void setHavePlanning(String havePlanning) {
        this.havePlanning = havePlanning;
    }

    public String getManagementPersonnel() {
        return managementPersonnel;
    }

    public void setManagementPersonnel(String managementPersonnel) {
        this.managementPersonnel = managementPersonnel;
    }

    public String getProjectLeader() {
        return projectLeader;
    }

    public void setProjectLeader(String projectLeader) {
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

    public String getIsMajorProject() {
        return isMajorProject;
    }

    public void setIsMajorProject(String isMajorProject) {
        this.isMajorProject = isMajorProject;
    }

    public String getBiddingDeadline() {
        return biddingDeadline;
    }

    public void setBiddingDeadline(String biddingDeadline) {
        this.biddingDeadline = biddingDeadline;
    }

    public String getBiddingMemo() {
        return biddingMemo;
    }

    public void setBiddingMemo(String biddingMemo) {
        this.biddingMemo = biddingMemo;
    }

    public String getBiddingDocumentsProducer() {
        return biddingDocumentsProducer;
    }

    public void setBiddingDocumentsProducer(String biddingDocumentsProducer) {
        this.biddingDocumentsProducer = biddingDocumentsProducer;
    }

    public String getRegistrationResults() {
        return registrationResults;
    }

    public void setRegistrationResults(String registrationResults) {
        this.registrationResults = registrationResults;
    }

    public String getRegistrationResultsMemo() {
        return registrationResultsMemo;
    }

    public void setRegistrationResultsMemo(String registrationResultsMemo) {
        this.registrationResultsMemo = registrationResultsMemo;
    }

    public String getHaveTechnical() {
        return haveTechnical;
    }

    public void setHaveTechnical(String haveTechnical) {
        this.haveTechnical = haveTechnical;
    }

    public String getMarginDeadline() {
        return marginDeadline;
    }

    public void setMarginDeadline(String marginDeadline) {
        this.marginDeadline = marginDeadline;
    }

    public String getTenderDeadline() {
        return tenderDeadline;
    }

    public void setTenderDeadline(String tenderDeadline) {
        this.tenderDeadline = tenderDeadline;
    }

    public String getBiddingDocumentsMemo() {
        return biddingDocumentsMemo;
    }

    public void setBiddingDocumentsMemo(String biddingDocumentsMemo) {
        this.biddingDocumentsMemo = biddingDocumentsMemo;
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
        this.latestRemittanceDate = latestRemittanceDate;
    }

    public String getRemittanceCompany() {
        return remittanceCompany;
    }

    public void setRemittanceCompany(String remittanceCompany) {
        this.remittanceCompany = remittanceCompany;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName;
    }

    public String getAccountNo() {
        return accountNo;
    }

    public void setAccountNo(String accountNo) {
        this.accountNo = accountNo;
    }

    public String getBailMemo() {
        return bailMemo;
    }

    public void setBailMemo(String bailMemo) {
        this.bailMemo = bailMemo;
    }

    public String getBusinessProducer() {
        return businessProducer;
    }

    public void setBusinessProducer(String businessProducer) {
        this.businessProducer = businessProducer;
    }

    public BigDecimal getTenderOffer() {
        return tenderOffer;
    }

    public void setTenderOffer(BigDecimal tenderOffer) {
        this.tenderOffer = tenderOffer;
    }

    public String getIsWinBidding() {
        return isWinBidding;
    }

    public void setIsWinBidding(String isWinBidding) {
        this.isWinBidding = isWinBidding;
    }

    public String getNopassReason() {
        return nopassReason;
    }

    public void setNopassReason(String nopassReason) {
        this.nopassReason = nopassReason;
    }

    public String getStep() {
        return step;
    }

    public void setStep(String step) {
        this.step = step;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getContractNo() {
        return contractNo;
    }

    public void setContractNo(String contractNo) {
        this.contractNo = contractNo;
    }

    public BigDecimal getContractCapital() {
        return contractCapital;
    }

    public void setContractCapital(BigDecimal contractCapital) {
        this.contractCapital = contractCapital;
    }

    public String getSignDate() {
        return signDate;
    }

    public void setSignDate(String signDate) {
        this.signDate = signDate;
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

    public String getRatioMemo() {
        return ratioMemo;
    }

    public void setRatioMemo(String ratioMemo) {
        this.ratioMemo = ratioMemo;
    }

    @Override
    public String toString() {
        return "ReportStatisticField [projectName=" + projectName + ", introducer=" + introducer + ", city=" + city + ", address=" + address + ", designArea=" + designArea + ", investmentMount=" + investmentMount + ", nature=" + nature + ", category=" + category + ", type=" + type + ", managementDepartment=" + managementDepartment + ", introduction=" + introduction + ", intentionalCooperation=" + intentionalCooperation + ", needTechnical=" + needTechnical + ", preRequirements="
                + preRequirements + ", contractAwardCompany=" + contractAwardCompany + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", managementPersonnel=" + managementPersonnel + ", projectLeader=" + projectLeader + ", projectLiable=" + projectLiable + ", applyDeadline=" + applyDeadline + ", isMajorProject=" + isMajorProject + ", biddingDeadline=" + biddingDeadline + ", biddingMemo=" + biddingMemo
                + ", biddingDocumentsProducer=" + biddingDocumentsProducer + ", registrationResults=" + registrationResults + ", registrationResultsMemo=" + registrationResultsMemo + ", haveTechnical=" + haveTechnical + ", marginDeadline=" + marginDeadline + ", tenderDeadline=" + tenderDeadline + ", biddingDocumentsMemo=" + biddingDocumentsMemo + ", bail=" + bail + ", latestRemittanceDate=" + latestRemittanceDate + ", remittanceCompany=" + remittanceCompany + ", bankName=" + bankName
                + ", accountNo=" + accountNo + ", bailMemo=" + bailMemo + ", businessProducer=" + businessProducer + ", tenderOffer=" + tenderOffer + ", isWinBidding=" + isWinBidding + ", nopassReason=" + nopassReason + ", step=" + step + ", status=" + status + ", contractNo=" + contractNo + ", contractCapital=" + contractCapital + ", signDate=" + signDate + ", contractMemo=" + contractMemo + ", ratio=" + ratio + ", ratioMemo=" + ratioMemo + "]";
    }

    public static void main(String[] args) {
        Class<ReportStatisticField> clazz = ReportStatisticField.class;
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            // FieldName fieldName = field.getAnnotation(FieldName.class);
            // if(fieldName != null){
            // System.out.println(fieldName.name());
            // }
            System.out.println(field.getName());
            // for (Annotation annotation : field.getAnnotations()) {
            // System.out.println(annotation.annotationType().getSimpleName());
            // }
            // for (Annotation annotation : field.getDeclaredAnnotations()) {
            // System.out.println(annotation.annotationType());
            // }
            // System.out.println(field.getGenericType().toString());
        }
    }
}
