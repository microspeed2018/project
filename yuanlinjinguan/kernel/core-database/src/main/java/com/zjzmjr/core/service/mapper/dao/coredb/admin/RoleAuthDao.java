package com.zjzmjr.core.service.mapper.dao.coredb.admin;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.RoleAuth;

/**
 * 角色权限业务查询
 * 
 * @author UI
 * @version $Id: RoleAuthDao.java, v 0.1 2017-6-7 下午2:41:32 UI Exp $
 */
public interface RoleAuthDao {

    /**
     * 根据角色id获取权限数据
     * 
     * @param roleId
     * @return
     */
    public List<AdminAuth> getRoleAuthByRoleId(Integer roleId);
    
    /**
     * 批量插入
     *
     * @param roleAuths
     */
    public int insertRoleAuthList(List<RoleAuth> roleAuths);
    
    /**
     * 删除角色权限
     * 
     * @param param
     * @return
     */
    public int deleteRoleAuthByRoleId(Map<String, Object> param);

    /**
     * 根据角色id获取权限ids
     * 
     * @param roleId
     * @return
     */
    public List<Integer> getAuthIdsByRoleId(Integer roleId);
    
}
