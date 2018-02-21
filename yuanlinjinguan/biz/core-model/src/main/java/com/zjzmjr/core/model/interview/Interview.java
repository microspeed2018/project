package com.zjzmjr.core.model.interview;

import java.io.Serializable;
import java.util.Date;


/**
 * 面试信息实体类
 * 
 * @author lenovo
 * @version $Id: Interview.java, v 0.1 2017-12-21 下午7:12:11 lenovo Exp $
 */
public class Interview implements Serializable{

    /**  */
    private static final long serialVersionUID = -1768023162213437826L;

    private Integer id;

    private Integer companyId;

    private Integer recruitmentId;

    private Integer talentId;

    private Integer round;

    private String time;

    private String place;

    private Integer interviewer;

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

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;
    
    private Integer isSms;
    
    private String memo;

    
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

    
    public String getPlace() {
        return place;
    }

    
    public void setPlace(String place) {
        this.place = place;
    }

    
    public Integer getInterviewer() {
        return interviewer;
    }

    
    public void setInterviewer(Integer interviewer) {
        this.interviewer = interviewer;
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


    @Override
    public String toString() {
        return "Interview [id=" + id + ", companyId=" + companyId + ", recruitmentId=" + recruitmentId + ", talentId=" + talentId + ", round=" + round + ", time=" + time + ", place=" + place + ", interviewer=" + interviewer + ", writtenScore=" + writtenScore + ", interviewScore=" + interviewScore + ", temperament=" + temperament + ", experience=" + experience + ", specialty=" + specialty + ", intention=" + intention + ", stability=" + stability + ", details=" + details + ", executive="
                + executive + ", efficiency=" + efficiency + ", relationship=" + relationship + ", communication=" + communication + ", result=" + result + ", comment=" + comment + ", recordTime=" + recordTime + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + ", isSms=" + isSms + ", memo=" + memo + "]";
    }
   

}
