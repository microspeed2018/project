package com.zjzmjr.core.service.business.admin;

import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;

/**
 * 管理员登录时的权限获取
 * 
 * @author oms
 * @version $Id: AdminLoginAuthService.java, v 0.1 2016-6-14 下午1:28:34 oms Exp $
 */
public interface AdminLoginAuthService {

    /**
     * 获取管理员的权限值
     * 
     * @param admin
     * @return
     */
    ResultRecord<SimpleGrantedAuthority> getUserAuths(Admin admin);
    
}
