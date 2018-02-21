package com.zjzmjr.core.model.user;

import com.zjzmjr.core.model.admin.Admin;

/**
 * 外部人员的详细信息，包括user表里面的数据
 * 
 * @author oms
 * @version $Id: ExternalPersonInfo.java, v 0.1 2017-8-16 下午3:17:04 oms Exp $
 */
public class ExternalPersonInfo extends ExternalPerson {

    /**  */
    private static final long serialVersionUID = -2268539272670055087L;

    /**
     * 
     */
    private Admin userInfo;
    
    private String personnelNatureName;
    
    private String relatedPersonName;
    
    /**
     *  在职状态中文说明
     */
    private String statusText;

    public Admin getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(Admin userInfo) {
        this.userInfo = userInfo;
    }

    
    public String getPersonnelNatureName() {
        return personnelNatureName;
    }

    
    public void setPersonnelNatureName(String personnelNatureName) {
        this.personnelNatureName = personnelNatureName;
    }

    
    public String getRelatedPersonName() {
        return relatedPersonName;
    }

    
    public void setRelatedPersonName(String relatedPersonName) {
        this.relatedPersonName = relatedPersonName;
    }

    
    public String getStatusText() {
        return statusText;
    }

    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }

    @Override
    public String toString() {
        return "ExternalPersonInfo [userInfo=" + userInfo + ", personnelNatureName=" + personnelNatureName + ", relatedPersonName=" + relatedPersonName + ", statusText=" + statusText + "]";
    }

}
