package com.zjzmjr.core.api.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;

/**
 * 角色权限接口层
 * 
 * @author UI
 * @version $Id: IRoleAuthFacade.java, v 0.1 2017-6-6 下午4:08:47 UI Exp $
 */
public interface IRoleAuthFacade {
    
    /**
     * 获取角色对应的权限列表
     * 
     * @param roleId
     * @return
     */
    ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupRoleAuth(Long roleId);
    
    /**
     * 绑定角色对应的权限数据
     * 
     * @param roleId
     * @param authIds
     * @return
     */
    Result bindRoleAuth(Integer roleId, List<Integer> authIds, Integer currUserId);
}
