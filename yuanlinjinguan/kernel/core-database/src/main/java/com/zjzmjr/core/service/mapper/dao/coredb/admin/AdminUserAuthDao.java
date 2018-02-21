package com.zjzmjr.core.service.mapper.dao.coredb.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminUserAuth;

/**
 * 
 * 
 * @author oms
 * @version $Id: AdminUserAuthDao.java, v 0.1 2016-5-25 上午10:38:14 oms Exp $
 */
public interface AdminUserAuthDao {

    public List<AdminAuth> getAuthByUser(Integer userId);
    
    /**
     * 批量插入
     *
     * @param userAuths
     */
    public int batchInsert(List<AdminUserAuth> userAuths);
    
    
    /**
     * 删除用户权限
     *
     * @param userId
     * @param menuIds
     * @return
     */
    public int deleteUserMenuByAuthId(Map<String, Object> param);

    /**
     * 获取当前角色用户对应的权限信息
     * 
     * @param roleId
     * @return
     */
    public List<Integer> getAuthUserIdByRoleId(Map<String, Object> param);

    /**
     * 删除当前角色用户对应的权限信息
     * 
     * @param roleId
     */
    public int deleteAuthByRoleId(Map<String, Object> param);
}
