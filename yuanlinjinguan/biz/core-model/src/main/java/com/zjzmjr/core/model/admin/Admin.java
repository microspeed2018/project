package com.zjzmjr.core.model.admin;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Date;

/**
 * 管理员信息
 * 
 * @author oms
 * @version $Id: Admin.java, v 0.1 2017-8-24 下午8:12:03 oms Exp $
 */
public class Admin implements Serializable {

    private static final long serialVersionUID = 1871535522618367498L;

    private Integer id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 姓名
     */
    private String name;

    /**
     * 部门类型(总部/区域)
     */
    private Integer department;

    /**
     * 职位id
     */
    private Integer jobId;

    /**
     * 权限名称
     */
    private String jobName;

    /**
     * 查询征信的银行id
     */
    private Integer companyId;

    /**
     * 手机
     */
    private String mobile;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 密码
     */
    private String password;

    /**
     * 微信的openid
     */
    private String openId;
    
    /**
     * 个推消息发送clientId;
     */
    private String clientId;

    /**
     * 图片地址
     */
    private String imageAddress;

    /**
     * 总共用户浏览量
     */
    private Integer totalUserView;

    /**
     * 当前用户浏览量
     */
    private Integer currentUserView;

    /**
     * 最大用户浏览量
     */
    private Integer maxUserView;

    /**
     * 用户浏览量限制
     */
    private Integer userViewLimit;

    /**
     * 用户浏览最后统计时间
     */
    private Date userViewDate;

    /**
     * 帐户状态
     */
    private Integer accStatus;

    /**
     * 连续失败次数
     */
    private Integer loginError;

    /**
     * 登录成功次数
     */
    private Integer loginSucceed;

    /**
     * 登录失败次数
     */
    private Integer loginFail;

    /**
     * 最后登录时间
     */
    private Date loginTime;

    /**
     * 最后登录ip
     */
    private String loginIp;

    /**
     * 安全IP
     */
    private String securityIp;

    /**
     * 创建人
     */
    private Integer createUser;

    /**
     * 创建时间
     */
    private Date time;

    private byte[] version;

    public Admin() {
    }

    public Admin(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getJobId() {
        return jobId;
    }

    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getImageAddress() {
        return imageAddress;
    }

    public void setImageAddress(String imageAddress) {
        this.imageAddress = imageAddress;
    }

    public Integer getAccStatus() {
        return accStatus;
    }

    public void setAccStatus(Integer accStatus) {
        this.accStatus = accStatus;
    }

    public Integer getTotalUserView() {
        return totalUserView;
    }

    public void setTotalUserView(Integer totalUserView) {
        this.totalUserView = totalUserView;
    }

    public Integer getCurrentUserView() {
        return currentUserView;
    }

    public void setCurrentUserView(Integer currentUserView) {
        this.currentUserView = currentUserView;
    }

    public Integer getMaxUserView() {
        return maxUserView;
    }

    public void setMaxUserView(Integer maxUserView) {
        this.maxUserView = maxUserView;
    }

    public Integer getUserViewLimit() {
        return userViewLimit;
    }

    public void setUserViewLimit(Integer userViewLimit) {
        this.userViewLimit = userViewLimit;
    }

    public Date getUserViewDate() {
        return userViewDate;
    }

    public void setUserViewDate(Date userViewDate) {
        this.userViewDate = userViewDate;
    }

    public Integer getLoginError() {
        return loginError;
    }

    public void setLoginError(Integer loginError) {
        this.loginError = loginError;
    }

    public Integer getLoginSucceed() {
        return loginSucceed;
    }

    public void setLoginSucceed(Integer loginSucceed) {
        this.loginSucceed = loginSucceed;
    }

    public Integer getLoginFail() {
        return loginFail;
    }

    public void setLoginFail(Integer loginFail) {
        this.loginFail = loginFail;
    }

    public Date getLoginTime() {
        return loginTime;
    }

    public void setLoginTime(Date loginTime) {
        this.loginTime = loginTime;
    }

    public String getLoginIp() {
        return loginIp;
    }

    public void setLoginIp(String loginIp) {
        this.loginIp = loginIp;
    }

    public String getSecurityIp() {
        return securityIp;
    }

    public void setSecurityIp(String securityIp) {
        this.securityIp = securityIp;
    }

    public Integer getCreateUser() {
        return createUser;
    }

    public void setCreateUser(Integer createUser) {
        this.createUser = createUser;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public byte[] getVersion() {
        return version;
    }

    public void setVersion(byte[] version) {
        this.version = version;
    }
    
    public String getClientId() {
        return clientId;
    }

    
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((accStatus == null) ? 0 : accStatus.hashCode());
        result = prime * result + ((createUser == null) ? 0 : createUser.hashCode());
        result = prime * result + ((currentUserView == null) ? 0 : currentUserView.hashCode());
        result = prime * result + ((department == null) ? 0 : department.hashCode());
        result = prime * result + ((email == null) ? 0 : email.hashCode());
        result = prime * result + ((id == null) ? 0 : id.hashCode());
        result = prime * result + ((loginError == null) ? 0 : loginError.hashCode());
        result = prime * result + ((loginFail == null) ? 0 : loginFail.hashCode());
        result = prime * result + ((loginIp == null) ? 0 : loginIp.hashCode());
        result = prime * result + ((loginSucceed == null) ? 0 : loginSucceed.hashCode());
        result = prime * result + ((loginTime == null) ? 0 : loginTime.hashCode());
        result = prime * result + ((maxUserView == null) ? 0 : maxUserView.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((password == null) ? 0 : password.hashCode());
        result = prime * result + ((securityIp == null) ? 0 : securityIp.hashCode());
        result = prime * result + ((time == null) ? 0 : time.hashCode());
        result = prime * result + ((totalUserView == null) ? 0 : totalUserView.hashCode());
        result = prime * result + ((userViewDate == null) ? 0 : userViewDate.hashCode());
        result = prime * result + ((userViewLimit == null) ? 0 : userViewLimit.hashCode());
        result = prime * result + ((username == null) ? 0 : username.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Admin other = (Admin) obj;
        if (accStatus == null) {
            if (other.accStatus != null)
                return false;
        } else if (!accStatus.equals(other.accStatus))
            return false;
        if (createUser == null) {
            if (other.createUser != null)
                return false;
        } else if (!createUser.equals(other.createUser))
            return false;
        if (currentUserView == null) {
            if (other.currentUserView != null)
                return false;
        } else if (!currentUserView.equals(other.currentUserView))
            return false;
        if (department == null) {
            if (other.department != null)
                return false;
        } else if (!department.equals(other.department))
            return false;
        if (email == null) {
            if (other.email != null)
                return false;
        } else if (!email.equals(other.email))
            return false;
        if (id == null) {
            if (other.id != null)
                return false;
        } else if (!id.equals(other.id))
            return false;
        if (loginError == null) {
            if (other.loginError != null)
                return false;
        } else if (!loginError.equals(other.loginError))
            return false;
        if (loginFail == null) {
            if (other.loginFail != null)
                return false;
        } else if (!loginFail.equals(other.loginFail))
            return false;
        if (loginIp == null) {
            if (other.loginIp != null)
                return false;
        } else if (!loginIp.equals(other.loginIp))
            return false;
        if (loginSucceed == null) {
            if (other.loginSucceed != null)
                return false;
        } else if (!loginSucceed.equals(other.loginSucceed))
            return false;
        if (loginTime == null) {
            if (other.loginTime != null)
                return false;
        } else if (!loginTime.equals(other.loginTime))
            return false;
        if (maxUserView == null) {
            if (other.maxUserView != null)
                return false;
        } else if (!maxUserView.equals(other.maxUserView))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (password == null) {
            if (other.password != null)
                return false;
        } else if (!password.equals(other.password))
            return false;
        if (securityIp == null) {
            if (other.securityIp != null)
                return false;
        } else if (!securityIp.equals(other.securityIp))
            return false;
        if (time == null) {
            if (other.time != null)
                return false;
        } else if (!time.equals(other.time))
            return false;
        if (totalUserView == null) {
            if (other.totalUserView != null)
                return false;
        } else if (!totalUserView.equals(other.totalUserView))
            return false;
        if (userViewDate == null) {
            if (other.userViewDate != null)
                return false;
        } else if (!userViewDate.equals(other.userViewDate))
            return false;
        if (userViewLimit == null) {
            if (other.userViewLimit != null)
                return false;
        } else if (!userViewLimit.equals(other.userViewLimit))
            return false;
        if (username == null) {
            if (other.username != null)
                return false;
        } else if (!username.equals(other.username))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "Admin [id=" + id + ", username=" + username + ", name=" + name + ", department=" + department + ", jobId=" + jobId + ", jobName=" + jobName + ", companyId=" + companyId + ", mobile=" + mobile + ", email=" + email + ", password=" + password + ", openId=" + openId + ", clientId=" + clientId + ", imageAddress=" + imageAddress + ", totalUserView=" + totalUserView + ", currentUserView=" + currentUserView + ", maxUserView=" + maxUserView + ", userViewLimit=" + userViewLimit
                + ", userViewDate=" + userViewDate + ", accStatus=" + accStatus + ", loginError=" + loginError + ", loginSucceed=" + loginSucceed + ", loginFail=" + loginFail + ", loginTime=" + loginTime + ", loginIp=" + loginIp + ", securityIp=" + securityIp + ", createUser=" + createUser + ", time=" + time + ", version=" + Arrays.toString(version) + "]";
    }

}
