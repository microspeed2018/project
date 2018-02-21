package com.zjzmjr.core.model.interview;

import java.util.Date;

import com.zjzmjr.core.base.page.BasePageQuery;


public class InterviewQuery extends BasePageQuery{

    /**  */
    private static final long serialVersionUID = -714280186900996834L;

    private Integer id;
    
    private String name;
    
    private String mobile;
    
    private String identityNo;

    private Integer companyId;

    private Integer recruitmentId;

    private Integer talentId;

    private Integer round;

    private String time;
    
    private String timeStart;
    
    private String timeEnd;
    
    private Integer interviewer;
    
    private Integer result;
    
    /**
     * 面试人员用户ID
     */
    private Integer talentAdminId;

    /**
     * 面试官姓名
     */
    private String  interviewerName;
    
    private String writtenScoreStart;
    
    private String writtenScoreEnd;
    
    private String interviewScoreStart;
    
    private String interviewScoreEnd;

    /**
     * 选中添加面试人才、职位 
     * ";"分号区分不同人才
     * ","逗号分隔人才编号和职位编号
     */
    private String checkTalent;
    
    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;
    
    private Integer isSms;
    
    /**
     * 面试人员姓名或职位名称
     */
    private String posNameOrInterview;
    
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

    
    public Integer getRecruitmentId() {
        return recruitmentId;
    }

    
    public void setRecruitmentId(Integer recruitmentId) {
        this.recruitmentId = recruitmentId;
    }

    
    public Integer getTalentId() {
        return talentId;
    }

    
    public void setTalentId(Integer talentId) {
        this.talentId = talentId;
    }

    
    public Integer getRound() {
        return round;
    }

    
    public void setRound(Integer round) {
        this.round = round;
    }

    
    public String getTime() {
        return time;
    }

    
    public void setTime(String time) {
        this.time = time;
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

    
    public Integer getInterviewer() {
        return interviewer;
    }

    
    public void setInterviewer(Integer interviewer) {
        this.interviewer = interviewer;
    }

    
    public String getInterviewerName() {
        return interviewerName;
    }

    
    public void setInterviewerName(String interviewerName) {
        this.interviewerName = interviewerName;
    }


    
    public String getWrittenScoreStart() {
        return writtenScoreStart;
    }


    
    public void setWrittenScoreStart(String writtenScoreStart) {
        this.writtenScoreStart = writtenScoreStart;
    }


    
    public String getWrittenScoreEnd() {
        return writtenScoreEnd;
    }


    
    public void setWrittenScoreEnd(String writtenScoreEnd) {
        this.writtenScoreEnd = writtenScoreEnd;
    }


    
    public String getInterviewScoreStart() {
        return interviewScoreStart;
    }


    
    public void setInterviewScoreStart(String interviewScoreStart) {
        this.interviewScoreStart = interviewScoreStart;
    }


    
    public String getInterviewScoreEnd() {
        return interviewScoreEnd;
    }


    
    public void setInterviewScoreEnd(String interviewScoreEnd) {
        this.interviewScoreEnd = interviewScoreEnd;
    }

    
    public Integer getResult() {
        return result;
    }


    
    public void setResult(Integer result) {
        this.result = result;
    }

    
    public String getCheckTalent() {
        return checkTalent;
    }


    
    public void setCheckTalent(String checkTalent) {
        this.checkTalent = checkTalent;
    }


    
    public String getName() {
        return name;
    }


    
    public void setName(String name) {
        this.name = name;
    }


    
    public String getMobile() {
        return mobile;
    }


    
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }


    
    public String getIdentityNo() {
        return identityNo;
    }


    
    public void setIdentityNo(String identityNo) {
        this.identityNo = identityNo;
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

    
    public Integer getIsSms() {
        return isSms;
    }


    
    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    
    public String getPosNameOrInterview() {
        return posNameOrInterview;
    }


    
    public void setPosNameOrInterview(String posNameOrInterview) {
        this.posNameOrInterview = posNameOrInterview;
    }
    
    
    public Integer getTalentAdminId() {
        return talentAdminId;
    }
    
    public void setTalentAdminId(Integer talentAdminId) {
        this.talentAdminId = talentAdminId;
    }

    @Override
    public String toString() {
        return "InterviewQuery [id=" + id + ", name=" + name + ", mobile=" + mobile + ", identityNo=" + identityNo + ", companyId=" + companyId + ", recruitmentId=" + recruitmentId + ", talentId=" + talentId + ", round=" + round + ", time=" + time + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", interviewer=" + interviewer + ", result=" + result + ", talentAdminId=" + talentAdminId + ", interviewerName=" + interviewerName + ", writtenScoreStart=" + writtenScoreStart
                + ", writtenScoreEnd=" + writtenScoreEnd + ", interviewScoreStart=" + interviewScoreStart + ", interviewScoreEnd=" + interviewScoreEnd + ", checkTalent=" + checkTalent + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", isSms=" + isSms + ", posNameOrInterview=" + posNameOrInterview + "]";
    }
    
    
}
