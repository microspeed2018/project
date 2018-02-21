package com.zjzmjr.finance.web.interview.form;

import com.zjzmjr.core.common.biz.BaseForm;


public class InterviewForm extends BaseForm{

    /**  */
    private static final long serialVersionUID = -1172596018513846640L;

    private Integer id;

    private Integer companyId;

    private Integer recruitmentId;

    private Integer talentId;

    private Integer round;

    private String time;
    
    private String timeStart;
    
    private String timeEnd;
    
    private Integer interviewer;
    
    /**
     * 面试者姓名
     */
    private String  interviewerName;
    
    private String writtenScoreStart;
    
    private String writtenScoreEnd;
    
    private String interviewScoreStart;
    
    private String interviewScoreEnd;

    private String place;

    private Integer writtenScore;

    private Integer interviewScore;

    private Integer temperament;

    private Integer experience;

    private Integer specialty;

    private Integer intention;

    private Integer stability;

    private Integer details;

    private Integer executive;

    private Integer efficiency;

    private Integer relationship;

    private Integer communication;

    private Integer result;

    private String comment;

    private String recordTime;
    
    /**
     * 选中添加面试人才、职位 
     * ";"分号区分不同人才
     * ","逗号分隔人才编号和职位编号
     */
    private String checkTalent;
    
    private String name;
    
    private String mobile;
    
    private String identityNo;
    
    /**
     * 是否发送短信通知
     */
    private Integer isSms;
    
    private String memo;

    private String roundText;
    
    private String recruitmentName;
    
    /**
     * 面试人员姓名或职位名称
     */
    private String posNameOrInterview;

    @Override
    public String resolveFiled(String arg0) {
        // TODO Auto-generated method stub
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

    
    public String getPlace() {
        return place;
    }

    
    public void setPlace(String place) {
        this.place = place;
    }

    
    public Integer getWrittenScore() {
        return writtenScore;
    }

    
    public void setWrittenScore(Integer writtenScore) {
        this.writtenScore = writtenScore;
    }

    
    public Integer getInterviewScore() {
        return interviewScore;
    }

    
    public void setInterviewScore(Integer interviewScore) {
        this.interviewScore = interviewScore;
    }

    
    public Integer getTemperament() {
        return temperament;
    }

    
    public void setTemperament(Integer temperament) {
        this.temperament = temperament;
    }

    
    public Integer getExperience() {
        return experience;
    }

    
    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    
    public Integer getSpecialty() {
        return specialty;
    }

    
    public void setSpecialty(Integer specialty) {
        this.specialty = specialty;
    }

    
    public Integer getIntention() {
        return intention;
    }

    
    public void setIntention(Integer intention) {
        this.intention = intention;
    }

    
    public Integer getStability() {
        return stability;
    }

    
    public void setStability(Integer stability) {
        this.stability = stability;
    }

    
    public Integer getDetails() {
        return details;
    }

    
    public void setDetails(Integer details) {
        this.details = details;
    }

    
    public Integer getExecutive() {
        return executive;
    }

    
    public void setExecutive(Integer executive) {
        this.executive = executive;
    }

    
    public Integer getEfficiency() {
        return efficiency;
    }

    
    public void setEfficiency(Integer efficiency) {
        this.efficiency = efficiency;
    }

    
    public Integer getRelationship() {
        return relationship;
    }

    
    public void setRelationship(Integer relationship) {
        this.relationship = relationship;
    }

    
    public Integer getCommunication() {
        return communication;
    }

    
    public void setCommunication(Integer communication) {
        this.communication = communication;
    }

    
    public Integer getResult() {
        return result;
    }

    
    public void setResult(Integer result) {
        this.result = result;
    }

    
    public String getComment() {
        return comment;
    }

    
    public void setComment(String comment) {
        this.comment = comment;
    }

    
    public String getRecordTime() {
        return recordTime;
    }

    
    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
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

    
    public Integer getIsSms() {
        return isSms;
    }

    
    public void setIsSms(Integer isSms) {
        this.isSms = isSms;
    }

    
    public String getMemo() {
        return memo;
    }

    
    public void setMemo(String memo) {
        this.memo = memo;
    }

    
    public String getRoundText() {
        return roundText;
    }

    
    public void setRoundText(String roundText) {
        this.roundText = roundText;
    }

    
    public String getRecruitmentName() {
        return recruitmentName;
    }

    
    public void setRecruitmentName(String recruitmentName) {
        this.recruitmentName = recruitmentName;
    }

    
    public String getPosNameOrInterview() {
        return posNameOrInterview;
    }


    
    public void setPosNameOrInterview(String posNameOrInterview) {
        this.posNameOrInterview = posNameOrInterview;
    }


    @Override
    public String toString() {
        return "InterviewForm [id=" + id + ", companyId=" + companyId + ", recruitmentId=" + recruitmentId + ", talentId=" + talentId + ", round=" + round + ", time=" + time + ", timeStart=" + timeStart + ", timeEnd=" + timeEnd + ", interviewer=" + interviewer + ", interviewerName=" + interviewerName + ", writtenScoreStart=" + writtenScoreStart + ", writtenScoreEnd=" + writtenScoreEnd + ", interviewScoreStart=" + interviewScoreStart + ", interviewScoreEnd=" + interviewScoreEnd + ", place="
                + place + ", writtenScore=" + writtenScore + ", interviewScore=" + interviewScore + ", temperament=" + temperament + ", experience=" + experience + ", specialty=" + specialty + ", intention=" + intention + ", stability=" + stability + ", details=" + details + ", executive=" + executive + ", efficiency=" + efficiency + ", relationship=" + relationship + ", communication=" + communication + ", result=" + result + ", comment=" + comment + ", recordTime=" + recordTime
                + ", checkTalent=" + checkTalent + ", name=" + name + ", mobile=" + mobile + ", identityNo=" + identityNo + ", isSms=" + isSms + ", memo=" + memo + ", roundText=" + roundText + ", recruitmentName=" + recruitmentName + ", posNameOrInterview=" + posNameOrInterview + "]";
    }
    
    
}
