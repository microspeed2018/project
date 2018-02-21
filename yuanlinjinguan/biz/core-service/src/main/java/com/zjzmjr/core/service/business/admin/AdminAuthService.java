/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.service.business.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AuthNodeDTO;

/**
 * 管理员权限
 * @author js
 * @version $Id: AuthService.java, v 0.1 2015年10月29日 上午11:57:06 js Exp $
 */
public interface AdminAuthService {

    ResultPage<AdminAuth> queryByPage(AdminAuthQuery query);

    Result create(AdminAuth adminAuth);

    Result delete(AdminAuth adminAuth);

    Result update(AdminAuth adminAuth);
    
    /**
     * 获取用户权限组
     *
     * @param userId
     * @return
     * @throws IllegalArgumentException
     */
    ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupUserAuth(Long userId);
    
    /**
     * 绑定用户权限
     *
     * @param userId
     * @param authIds
     * @throws IllegalArgumentException
     */
    Result bindUserAuth(Integer userId, List<Integer> authIds);


}
