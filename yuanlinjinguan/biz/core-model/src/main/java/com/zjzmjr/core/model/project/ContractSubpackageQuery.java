package com.zjzmjr.core.model.project;

import java.math.BigDecimal;
import java.util.List;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 分包表query
 * 
 * @author lenovo
 * @version $Id: ContractSubpackageQuery.java, v 0.1 2017-9-4 下午7:27:45 lenovo
 *          Exp $
 */
public class ContractSubpackageQuery extends BasePageQuery {

	/**  */
	private static final long serialVersionUID = 7185381496994723556L;

	private Integer id;

	private Integer companyId;

	private Integer projectId;

	/**
	 * 项目名称
	 */
	private String projectName;

	/**
	 * 技术负责人
	 */
	private Integer projectLeader;

	private Integer paymentMode;

	/**
	 * 分包负责人
	 */
	private Integer subpackageLeader;

	private BigDecimal subpackageCapital;

	private String subpackageContent;

	private String paymentMemo;

	private String subpackageMemo;

	private Integer status;

	/**
	 * 财务审核类型
	 */
	private Integer verifyType;

	private Integer subpackageId;
	
	private Integer temporaryId;
	
	/**
	 * 分包状态list
	 */
	private List<Integer> subPackageStatus;
	
	/**
	 * 审核状态
	 */
	private Integer verifyStatus;


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

	public Integer getSubpackageLeader() {
		return subpackageLeader;
	}

	public void setSubpackageLeader(Integer subpackageLeader) {
		this.subpackageLeader = subpackageLeader;
	}

	public BigDecimal getSubpackageCapital() {
		return subpackageCapital;
	}

	public void setSubpackageCapital(BigDecimal subpackageCapital) {
		this.subpackageCapital = subpackageCapital;
	}

	public String getSubpackageContent() {
		return subpackageContent;
	}

	public void setSubpackageContent(String subpackageContent) {
		this.subpackageContent = subpackageContent;
	}

	public String getPaymentMemo() {
		return paymentMemo;
	}

	public void setPaymentMemo(String paymentMemo) {
		this.paymentMemo = paymentMemo;
	}

	public Integer getPaymentMode() {
		return paymentMode;
	}

	public void setPaymentMode(Integer paymentMode) {
		this.paymentMode = paymentMode;
	}

	public String getSubpackageMemo() {
		return subpackageMemo;
	}

	public void setSubpackageMemo(String subpackageMemo) {
		this.subpackageMemo = subpackageMemo;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getVerifyType() {
		return verifyType;
	}

	public void setVerifyType(Integer verifyType) {
		this.verifyType = verifyType;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public Integer getProjectLeader() {
		return projectLeader;
	}

	public void setProjectLeader(Integer projectLeader) {
		this.projectLeader = projectLeader;
	}

	public Integer getSubpackageId() {
		return subpackageId;
	}

	public void setSubpackageId(Integer subpackageId) {
		this.subpackageId = subpackageId;
	}
	
    public Integer getTemporaryId() {
        return temporaryId;
    }

    
    public void setTemporaryId(Integer temporaryId) {
        this.temporaryId = temporaryId;
    }
    
    public List<Integer> getSubPackageStatus() {
        return subPackageStatus;
    }

    
    public void setSubPackageStatus(List<Integer> subPackageStatus) {
        this.subPackageStatus = subPackageStatus;
    }
    
    public Integer getVerifyStatus() {
        return verifyStatus;
    }

    
    public void setVerifyStatus(Integer verifyStatus) {
        this.verifyStatus = verifyStatus;
    }

    @Override
    public String toString() {
        return "ContractSubpackageQuery [id=" + id + ", companyId=" + companyId + ", projectId=" + projectId + ", projectName=" + projectName + ", projectLeader=" + projectLeader + ", paymentMode=" + paymentMode + ", subpackageLeader=" + subpackageLeader + ", subpackageCapital=" + subpackageCapital + ", subpackageContent=" + subpackageContent + ", paymentMemo=" + paymentMemo + ", subpackageMemo=" + subpackageMemo + ", status=" + status + ", verifyType=" + verifyType + ", subpackageId="
                + subpackageId + ", temporaryId=" + temporaryId + ", subPackageStatus=" + subPackageStatus + ", verifyStatus=" + verifyStatus + "]";
    }

}
