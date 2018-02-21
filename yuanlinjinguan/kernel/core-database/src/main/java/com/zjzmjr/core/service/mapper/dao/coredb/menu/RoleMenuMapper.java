package com.zjzmjr.core.service.mapper.dao.coredb.menu;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.RoleMenu;
import com.zjzmjr.core.model.menu.RoleMenuQuery;

/**
 * 角色菜单
 * 
 * @author oms
 * @version $Id: RoleMenuMapper.java, v 0.1 2017-2-14 上午9:49:12 oms Exp $
 */
public interface RoleMenuMapper {

    int getRoleMenuCount(RoleMenuQuery query);

    List<RoleMenu> getRoleMenuByCondition(RoleMenuQuery query);

    int deleteRoleMenuById(Integer id);

    int deleteRoleMenuByMenuIds(Map<String, Object> param);

    int insertRoleMenu(RoleMenu record);

    /**
     * 批量插入角色的菜单
     * 
     * @param userMenus
     */
    int batchInsertRoleMenu(List<RoleMenu> roleMenus);

    List<AdminMenu> getRoleMenuByRoleType(Integer roleType);
    
    /**
     * 获取当前职位的用户
     * 
     * @param param
     * @return
     */
    List<Integer> getMenuUserIdByRoleId(Map<String, Object> param);

    int updateRoleMenuById(RoleMenu record);

}