package com.zjzmjr.core.service.mapper.dao.coredb.menu;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.AdminUserMenu;

/**
 * 管理员菜单表
 * 
 * @author oms
 * @version $Id: AdminUserMenuDao.java, v 0.1 2016-5-25 上午10:29:57 oms Exp $
 */
public interface AdminUserMenuDao {

    /**
     * 批量插入管理员的菜单
     * 
     * @param userMenus
     */
    void batchInsert(List<AdminUserMenu> userMenus);

    /**
     * 根据管理员获取菜单
     * 
     * @param param
     * @return
     */
    List<AdminMenu> getMenuByUser(Map<String, Object> param);

    /**
     * 更新管理员的菜单
     * 
     * @param param
     */
    void update(Map<String, Object> param);

    /**
     * 根据菜单id删除菜单
     * 
     * @param menuIds
     */
    void deleteByMenuIds(List<Integer> menuIds);

    /**
     * 根据菜单id删除菜单
     * 
     * @param menuId
     */
    void deleteByMenuId(Integer menuId);
    
    /**
     * 根据菜单id删除管理员的菜单
     * 
     * @param param
     */
    void deleteUserMenuByMenuId(Map<String, Object> param);
}
