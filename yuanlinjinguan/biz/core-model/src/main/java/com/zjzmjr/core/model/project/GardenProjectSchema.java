package com.zjzmjr.core.model.project;

import java.io.Serializable;
import java.util.Date;

/**
 * 项目方案表
 * 
 * @author oms
 * @version $Id: GardenProjectSchema.java, v 0.1 2017-8-16 上午10:20:35 oms Exp $
 */
public class GardenProjectSchema implements Serializable {

    /**  */
    private static final long serialVersionUID = -357015127031512240L;

    private Integer id;

    private Integer companyId;

    private Integer projectId;

    /**
     * 方案提议人
     */
    private Integer schemeProposer;

    private String content;

    /**
     * 不申请理由
     */
    private String resason;

    /**
     * 是否为推荐
     */
    private Integer recommend;

    /**
     * 是否被采纳
     */
    private Integer accepted;

    private Date createTime;

    private Integer createUserId;

    private Date updateTime;

    private Integer updateUserId;

    private Integer version;

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

    public Integer getProjectId() {
        return projectId;
    }

    public void setProjectId(Integer projectId) {
        this.projectId = projectId;
    }

    public Integer getSchemeProposer() {
        return schemeProposer;
    }

    public void setSchemeProposer(Integer schemeProposer) {
        this.schemeProposer = schemeProposer;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    public String getResason() {
        return resason;
    }

    public void setResason(String resason) {
        this.resason = resason == null ? null : resason.trim();
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public Integer getAccepted() {
        return accepted;
    }

    public void setAccepted(Integer accepted) {
        this.accepted = accepted;
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

    @Override
    public String toString() {
        return "GardenProjectSchema [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", schemeProposer=" + schemeProposer + ", content=" + content + ", resason=" + resason + ", recommend=" + recommend + ", accepted=" + accepted + ", createTime=" + createTime + ", createUserId=" + createUserId + ", updateTime=" + updateTime + ", updateUserId=" + updateUserId + ", version=" + version + "]";
    }

}