/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.api.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AuthNodeDTO;

/**
 * 分页查询权限
 * 
 * @author js
 * @version $Id: IAuthFacade.java, v 0.1 2015年10月29日 上午11:16:28 js Exp $
 */
public interface IAdminAuthFacade {

    /**
     * 
     * @param query
     * @return
     */
    ResultPage<AdminAuth> queryByPage(AdminAuthQuery query);

    Result create(AdminAuth adminAuth);

    Result delete(AdminAuth adminAuth);

    Result update(AdminAuth adminAuth);
    
    ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupUserAuth(Long userId);
    
    Result bindUserAuth(Integer userId, List<Integer> authIds);
}
