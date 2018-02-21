package com.zjzmjr.finance.web.project.form;

import com.zjzmjr.finance.web.fileUpload.form.ReceiveFileForm;

/**
 * 
 * 
 * @author oms
 * @version $Id: ProjectFileForm.java, v 0.1 2017-8-21 下午2:29:20 oms Exp $
 */
public class ProjectFileForm extends ReceiveFileForm {

    /**  */
    private static final long serialVersionUID = -6724601528919629314L;

    /**
     * 员工id
     */
    private Integer userId;

    /**
     * 报名截止日期
     */
    private String deadline;

    /**
     * 保证金截止日期
     */
    private String marginDeadline;

    /**
     * 投标截止日期
     */
    private String tenderDeadline;

    /**
     * 投标报价
     */
    private String tenderMoney;

    private String memo;

    // 保证金
    private String bail;

    // 最晚汇款日期
    private String latestRemittanceDate;

    // 汇款单位
    private String remittanceCompany;

    // 开户银行
    private String bankName;

    // 银行账号
    private String accountNo;

    // 保证金备注
    private String bailMemo;
    
    /**
     * 项目技术标分歩
     */
    private Integer subStep2;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
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

    /**
     * 投标报价
     * 
     * @return
     */
    public String getTenderMoney() {
        return tenderMoney;
    }

    /**
     * 投标报价
     * 
     * @param tenderMoney
     */
    public void setTenderMoney(String tenderMoney) {
        this.tenderMoney = tenderMoney;
    }

    public String getMemo() {
        return memo;
    }

    public void setMemo(String memo) {
        this.memo = memo;
    }

    public String getBail() {
        return bail;
    }

    public void setBail(String bail) {
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
    
    public Integer getSubStep2() {
        return subStep2;
    }

    
    public void setSubStep2(Integer subStep2) {
        this.subStep2 = subStep2;
    }

    @Override
    public String toString() {
        return "ProjectFileForm [userId=" + userId + ", deadline=" + deadline + ", marginDeadline=" + marginDeadline + ", tenderDeadline=" + tenderDeadline + ", tenderMoney=" + tenderMoney + ", memo=" + memo + ", bail=" + bail + ", latestRemittanceDate=" + latestRemittanceDate + ", remittanceCompany=" + remittanceCompany + ", bankName=" + bankName + ", accountNo=" + accountNo + ", bailMemo=" + bailMemo + ", subStep2=" + subStep2 + "]";
    }

}
