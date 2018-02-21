package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.web.mvc.form.AbstractForm;

/**
 * 立项审核
 * 
 * @author oms
 * @version $Id: ProjectSchemaForm.java, v 0.1 2017-8-16 下午5:22:29 oms Exp $
 */
public class ProjectSchemaForm extends AbstractForm {

    /**  */
    private static final long serialVersionUID = -4317847346248869442L;

    /**
     * 项目方案表主键
     */
    private Integer id;

    /**
     * 文件编号
     */
    private Integer fileId;

    /**
     * 公司编号
     */
    private Integer companyId;

    /**
     * 项目编号
     */
    private Integer projectId;

    /**
     * 项目名称
     */
    private String projectName;

    /**
     * 经营专员
     */
    private Integer managementPersonnel;

    /**
     * 建议人
     */
    private Integer proposerId;

    /**
     * 邀请人，逗号分开
     */
    private String inviterIds;

    /**
     * 申请方案内容
     */
    private String content;

    /**
     * 理由
     */
    private String reason;

    // /**
    // * 申请方案内容
    // */
    // private String projectSchema;
    /**
     * 项目申请截止日期
     */
    private String applyDeadline;

    /**
     * 是否为推荐 0:非推荐 1:推荐
     */
    private Integer recommend;

    /**
     * 是否通过， 1：通过 ， 2：不通过
     */
    private Integer passed;

    /**
     * 状态
     */
    private Integer status;

    /**
     * 发票金额
     */
    private String amount;

    /**
     * 发票付款方式
     */
    private Integer paymentMode;

    /**
     * 开发票的类型
     */
    private Integer billType;

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

    public Integer getFileId() {
        return fileId;
    }

    public void setFileId(Integer fileId) {
        this.fileId = fileId;
    }

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

    public Integer getManagementPersonnel() {
        return managementPersonnel;
    }

    public void setManagementPersonnel(Integer managementPersonnel) {
        this.managementPersonnel = managementPersonnel;
    }

    public Integer getProposerId() {
        return proposerId;
    }

    public void setProposerId(Integer proposerId) {
        this.proposerId = proposerId;
    }

    public String getInviterIds() {
        return inviterIds;
    }

    public void setInviterIds(String inviterIds) {
        this.inviterIds = inviterIds;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    // public String getProjectSchema() {
    // return projectSchema;
    // }
    //
    // public void setProjectSchema(String projectSchema) {
    // this.projectSchema = projectSchema;
    // }

    public Integer getPassed() {
        return passed;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public void setPassed(Integer passed) {
        this.passed = passed;
    }

    public String getApplyDeadline() {
        return applyDeadline;
    }

    public void setApplyDeadline(String applyDeadline) {
        this.applyDeadline = applyDeadline;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public Integer getBillType() {
        return billType;
    }

    public void setBillType(Integer billType) {
        this.billType = billType;
    }

    @Override
    public String toString() {
        return "ProjectSchemaForm [id=" + id + ", fileId=" + fileId + ", companyId=" + companyId + ", projectId=" + projectId + ", projectName=" + projectName + ", managementPersonnel=" + managementPersonnel + ", proposerId=" + proposerId + ", inviterIds=" + inviterIds + ", content=" + content + ", reason=" + reason + ", applyDeadline=" + applyDeadline + ", recommend=" + recommend + ", passed=" + passed + ", status=" + status + ", amount=" + amount + ", paymentMode=" + paymentMode
                + ", billType=" + billType + "]";
    }

}
