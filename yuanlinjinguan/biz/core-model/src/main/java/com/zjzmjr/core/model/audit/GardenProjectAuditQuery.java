package com.zjzmjr.core.model.audit;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 业务操作审核条件类
 * 
 * @author lenovo
 * @version $Id: GardenProjectAuditQuery.java, v 0.1 2017-8-31 下午7:04:11 lenovo Exp $
 */
public class GardenProjectAuditQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = 1174934751722169522L;

    private Integer id;
    
    private Integer companyId;
    
    /**
     * 项目名称
     */
    private String name;
    
    /**
     * 项目编号
     */
    private List<Integer> projectLst;
    
    /**
     * 申请人姓名
     */
    private String adminName;
    
    /**
     * 项目id
     */
    private Integer projectId;
    
    /**
     * 申请人
     */
    private Integer applicant;
    
    /**
     * 所属类型
     */
    private Integer type;
    
    /**
     * 审核状态
     */
    private Integer status;
    
    /**
     * 项目编号
     */
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
     * 项目进度
     */
    private Integer step;
    
    /**
     * 保证金分支进度
     */
    private Integer subStep;

    /**
     * 技术标分支步骤
     */
    private Integer subStep2;
    
    /**
     * 是否重点项目
     */
    private Integer isMajorProject;
    
    /**
     * 修改人员id
     */
    private Integer updateUserId;
    
    /**
     * 备注
     */
    private String memo;
    
    /**
     * 确认金额
     */
    private BigDecimal amount;
    
    /**
     * 付款方式
     */
    private Integer paymentMode;
    
    /**
     * 是否有技术标
     */
    private Integer havaTechnical;
    
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
     * 审核合同是否需要总工审核1：需要 2：不需要
     */
    private Integer ischief;
    
    //审核不通过记录显示传参
    /**
     * 院办type
     */
    private Integer officeType;
    
    /**
     * 经营type
     */
    private Integer manageType;
    
    /**
     * 负责人type
     */
    private Integer leaderType;
    
    /**
     * 法务type
     */
    private Integer lawType;
    
    /**
     * 总师办type
     */
    private Integer chiefType;
    
    /**
     * 财务type
     */
    private Integer financialType;
    
    private Date updateTime;
    
    /**
     * 不需要的状态值
     */
    private Integer otherStatus;
    
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


    
    public Integer getUpdateUserId() {
        return updateUserId;
    }


    
    public void setUpdateUserId(Integer updateUserId) {
        this.updateUserId = updateUserId;
    }


    
    public String getMemo() {
        return memo;
    }


    
    public void setMemo(String memo) {
        this.memo = memo;
    }


    
    public BigDecimal getAmount() {
        return amount;
    }


    
    public void setAmount(BigDecimal amount) {
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

    
    public Integer getHavaTechnical() {
        return havaTechnical;
    }


    
    public void setHavaTechnical(Integer havaTechnical) {
        this.havaTechnical = havaTechnical;
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

    
    public Integer getSubStep2() {
        return subStep2;
    }


    
    public void setSubStep2(Integer subStep2) {
        this.subStep2 = subStep2;
    }


    
    public Integer getIsMajorProject() {
        return isMajorProject;
    }


    
    public void setIsMajorProject(Integer isMajorProject) {
        this.isMajorProject = isMajorProject;
    }

    
    public Integer getIschief() {
        return ischief;
    }


    
    public void setIschief(Integer ischief) {
        this.ischief = ischief;
    }

    
    public Integer getOfficeType() {
        return officeType;
    }


    
    public void setOfficeType(Integer officeType) {
        this.officeType = officeType;
    }


    
    public Integer getManageType() {
        return manageType;
    }


    
    public void setManageType(Integer manageType) {
        this.manageType = manageType;
    }


    
    public Integer getLeaderType() {
        return leaderType;
    }


    
    public void setLeaderType(Integer leaderType) {
        this.leaderType = leaderType;
    }


    
    public Integer getLawType() {
        return lawType;
    }


    
    public void setLawType(Integer lawType) {
        this.lawType = lawType;
    }


    
    public Integer getChiefType() {
        return chiefType;
    }


    
    public void setChiefType(Integer chiefType) {
        this.chiefType = chiefType;
    }


    
    public Integer getFinancialType() {
        return financialType;
    }


    
    public void setFinancialType(Integer financialType) {
        this.financialType = financialType;
    }

    
    public Date getUpdateTime() {
        return updateTime;
    }


    
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    
    public Integer getOtherStatus() {
        return otherStatus;
    }


    
    public void setOtherStatus(Integer otherStatus) {
        this.otherStatus = otherStatus;
    }

    
    public List<Integer> getProjectLst() {
        return projectLst;
    }


    
    public void setProjectLst(List<Integer> projectLst) {
        this.projectLst = projectLst;
    }


    @Override
    public String toString() {
        return "GardenProjectAuditQuery [id=" + id + ", companyId=" + companyId + ", name=" + name + ", projectLst=" + projectLst + ", adminName=" + adminName + ", projectId=" + projectId + ", applicant=" + applicant + ", type=" + type + ", status=" + status + ", projectNo=" + projectNo + ", createTimeStart=" + createTimeStart + ", createTimeEnd=" + createTimeEnd + ", verifyTimeStart=" + verifyTimeStart + ", verifyTimeEnd=" + verifyTimeEnd + ", step=" + step + ", subStep=" + subStep
                + ", subStep2=" + subStep2 + ", isMajorProject=" + isMajorProject + ", updateUserId=" + updateUserId + ", memo=" + memo + ", amount=" + amount + ", paymentMode=" + paymentMode + ", havaTechnical=" + havaTechnical + ", haveScheme=" + haveScheme + ", haveDevelopment=" + haveDevelopment + ", haveDrawing=" + haveDrawing + ", havePlanning=" + havePlanning + ", ischief=" + ischief + ", officeType=" + officeType + ", manageType=" + manageType + ", leaderType=" + leaderType
                + ", lawType=" + lawType + ", chiefType=" + chiefType + ", financialType=" + financialType + ", updateTime=" + updateTime + ", otherStatus=" + otherStatus + "]";
    }
    
}
