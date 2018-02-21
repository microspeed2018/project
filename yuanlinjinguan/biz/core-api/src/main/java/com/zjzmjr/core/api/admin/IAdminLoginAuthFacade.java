package com.zjzmjr.core.api.admin;

import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;

/**
 * 
 * 
 * @author oms
 * @version $Id: IAdminLoginAuthFacade.java, v 0.1 2016-6-14 下午1:42:38 oms Exp $
 */
public interface IAdminLoginAuthFacade {

    /**
     * 获取管理员的权限值
     * 
     * @param admin
     * @return
     */
    ResultRecord<SimpleGrantedAuthority> getUserAuths(Admin admin);
    
}
