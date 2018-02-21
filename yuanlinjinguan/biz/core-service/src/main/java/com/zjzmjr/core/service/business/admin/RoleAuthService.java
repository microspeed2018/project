package com.zjzmjr.core.service.business.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;

/**
 * 角色权限接口层
 * 
 * @author UI
 * @version $Id: RoleAuthService.java, v 0.1 2017-6-7 下午2:38:35 UI Exp $
 */
public interface RoleAuthService {
    
    /**
     * 获取角色权限
     * 
     * @param roleId
     * @return
     */
    ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupRoleAuth(Long roleId);
    
    /**
     * 绑定角色权限
     * 
     * @param userId
     * @param authIds
     * @return
     */
    Result bindRoleAuth(Integer roleId, List<Integer> authIds, Integer currUserId);

}
