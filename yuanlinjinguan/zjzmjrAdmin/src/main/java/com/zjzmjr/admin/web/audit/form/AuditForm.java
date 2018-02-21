package com.zjzmjr.admin.web.audit.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class AuditForm extends BaseForm{

   /**  */
    private static final long serialVersionUID = -8032795548645776812L;

    private Integer id;
    
    private Integer companyId;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 申请人姓名
     */
    private String adminName;
    
    private Integer projectId;
    
    private Integer applicant;
    
    private Integer type;
    
    private Integer status;
    
    private String projectNo;
    
    private String createTimeStart;

    private String createTimeEnd;

    /**
     * 审核时间搜索开始时间
     */
    private String verifyTimeStart;

    /**
     * 审核时间搜索结束时间
     */
    private String verifyTimeEnd;
    
    /**
     * 项目进行步骤
     */
    private Integer step;
    
    /**
     * 项目分支步骤
     */
    private Integer subStep;
    
    /**
     * 技术标分支步骤
     */
    private Integer subStep2;
    
    /**
     * 备注
     */
    private String memo;
    
    /**
     * 确认金额
     */
    private String amount;
    
    /**
     * 付款方式
     */
    private Integer paymentMode;
    
    
    /**
     * 是否有技术标
     */
    private Integer haveTechnical;
    
    /**
     * 是否有方案设计
     */
    private Integer haveScheme;
    
    /**
     * 是否有扩初方案设计
     */
    private Integer haveDevelopment;
    
    /**
     * 是否有施工图设计
     */
    private Integer haveDrawing;
    
    /**
     * 是否有规划设计
     */
    private Integer havePlanning;
    
    /**
     * 是否重点项目
     */
    private Integer isMajorProject;
    
    /**
     * step 315 审核合同是否需要总工审核1：需要 2：不需要
     */
    private Integer ischief;
    
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

    
    public String getAdminName() {
        return adminName;
    }

    
    public void setAdminName(String adminName) {
        this.adminName = adminName;
    }

    
    public Integer getProjectId() {
        return projectId;
    }

    
    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    
    public Integer getApplicant() {
        return applicant;
    }

    
    public void setApplicant(Integer applicant) {
        this.applicant = applicant;
    }

    
    public Integer getType() {
        return type;
    }

    
    public void setType(Integer type) {
        this.type = type;
    }

    
    public Integer getStatus() {
        return status;
    }

    
    public void setStatus(Integer status) {
        this.status = status;
    }

    
    public String getProjectNo() {
        return projectNo;
    }

    
    public void setProjectNo(String projectNo) {
        this.projectNo = projectNo;
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


    
    public String getVerifyTimeStart() {
        return verifyTimeStart;
    }


    
    public void setVerifyTimeStart(String verifyTimeStart) {
        this.verifyTimeStart = verifyTimeStart;
    }


    
    public String getVerifyTimeEnd() {
        return verifyTimeEnd;
    }

    
    public void setVerifyTimeEnd(String verifyTimeEnd) {
        this.verifyTimeEnd = verifyTimeEnd;
    }


    
    public Integer getStep() {
        return step;
    }


    
    public void setStep(Integer step) {
        this.step = step;
    }


    
    public Integer getCompanyId() {
        return companyId;
    }


    
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }


    
    public String getMemo() {
        return memo;
    }


    
    public void setMemo(String memo) {
        this.memo = memo;
    }


    
    public String getAmount() {
        return amount;
    }


    
    public void setAmount(String amount) {
        this.amount = amount;
    }

    
    public Integer getPaymentMode() {
        return paymentMode;
    }


    
    public void setPaymentMode(Integer paymentMode) {
        this.paymentMode = paymentMode;
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


    
    public Integer getHaveTechnical() {
        return haveTechnical;
    }


    
    public void setHaveTechnical(Integer haveTechnical) {
        this.haveTechnical = haveTechnical;
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


    
    public Integer getIsMajorProject() {
        return isMajorProject;
    }


    
    public void setIsMajorProject(Integer isMajorProject) {
        this.isMajorProject = isMajorProject;
    }

    
    @Override
    public String toString() {
        return "AuditForm [id=" + id + ", companyId=" + companyId + ", name=" + name + ", adminName=" + adminName + ", projectId=" + projectId + ", applicant=" + applicant + ", type=" + type + ", status=" + status + ", projectNo=" + projectNo + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", verifyTimeStart=" + verifyTimeStart + ", verifyTimeEnd=" + verifyTimeEnd + ", step=" + step + ", subStep=" + subStep + ", subStep2=" + subStep2 + ", memo=" + memo
                + ", amount=" + amount + ", paymentMode=" + paymentMode + ", haveTechnical=" + haveTechnical + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", isMajorProject=" + isMajorProject + ", ischief=" + ischief + "]";
    }


    public Integer getIschief() {
        return ischief;
    }


    
    public void setIschief(Integer ischief) {
        this.ischief = ischief;
    }


    @Override
    public String resolveFiled(String arg0) {
        return null;
    }
    
    
}
