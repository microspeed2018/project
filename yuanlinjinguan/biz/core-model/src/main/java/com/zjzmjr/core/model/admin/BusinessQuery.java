package com.zjzmjr.core.model.admin;

import com.zjzmjr.core.base.page.BasePageQuery;

/**
 * 事物查询 query
 * 
 * @author Administrator
 * @version $Id: BusinessQuery.java, v 0.1 2016-7-12 上午10:30:28 Administrator
 *          Exp $
 */
public class BusinessQuery extends BasePageQuery {

    private static final long serialVersionUID = 5641695471164259959L;

    /**
     * userId 用户编号 userName 用户名 businessType 事物类型 result 处理结果
     */
    private Integer userId;

    private String userName;

    private Integer businessType;

    private String businessName;

    private Integer result;

    private String resultName;
    
    /** 查本月的 */
    private Integer checkInstant;
    
    /** 扩展字段 */
    private String extendedMsg;
    
    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getBusinessType() {
        return businessType;
    }

    public void setBusinessType(Integer businessType) {
        this.businessType = businessType;
    }

    public String getBusinessName() {
        return businessName;
    }

    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }

    public Integer getResult() {
        return result;
    }

    public void setResult(Integer result) {
        this.result = result;
    }

    public String getResultName() {
        return resultName;
    }

    public void setResultName(String resultName) {
        this.resultName = resultName;
    }

    public Integer getCheckInstant() {
        return checkInstant;
    }

    public void setCheckInstant(Integer checkInstant) {
        this.checkInstant = checkInstant;
    }

    
    public String getExtendedMsg() {
		return extendedMsg;
	}

	public void setExtendedMsg(String extendedMsg) {
		this.extendedMsg = extendedMsg;
	}

	@Override
	public String toString() {
		return "BusinessQuery [userId=" + userId + ", userName=" + userName
				+ ", businessType=" + businessType + ", businessName="
				+ businessName + ", result=" + result + ", resultName="
				+ resultName + ", checkInstant=" + checkInstant
				+ ", extendedMsg=" + extendedMsg + "]";
	}

}
