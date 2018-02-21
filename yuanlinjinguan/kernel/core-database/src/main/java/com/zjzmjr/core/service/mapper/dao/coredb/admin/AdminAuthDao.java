/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.mapper.dao.coredb.admin;

import java.util.List;

import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;

/**
 * 
 * @author js
 * @version $Id: AdminAuthDao.java, v 0.1 2015年10月29日 下午12:05:24 js Exp $
 */
public interface AdminAuthDao {

    public List<AdminAuth> getAuths(AdminAuthQuery query);

    List<AdminAuth> queryByPage(AdminAuthQuery query);

    void create(AdminAuth adminAuth);

    int count(AdminAuthQuery query);

    int delete(Integer id);

    void update(AdminAuth adminAuth);
    
    /**
     * 通过id查询
     *
     * @param ids
     * @return
     */
    List<AdminAuth> getByIds(List<Integer> ids);
    
    
}
