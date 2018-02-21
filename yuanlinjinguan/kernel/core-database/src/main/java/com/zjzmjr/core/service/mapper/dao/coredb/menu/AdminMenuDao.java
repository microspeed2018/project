package com.zjzmjr.core.service.mapper.dao.coredb.menu;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.model.menu.AdminMenu;

/**
 * 管理员菜单
 * 
 * @author oms
 * @version $Id: AdminMenuDao.java, v 0.1 2016-5-23 下午3:02:27 oms Exp $
 */
public interface AdminMenuDao {

    /**
     * 获取菜单
     * 
     * @param param
     * @return
     */
    List<AdminMenu> getMenu(Map<String, Object> param);

    /**
     * 增加菜单
     * 
     * @param adminMenu
     */
    void addMenu(AdminMenu adminMenu);

    /**
     * 更新菜单
     * 
     * @param adminMenu
     */
    void update(AdminMenu adminMenu);

    /**
     * 根据id获取菜单
     * 
     * @param id
     * @return
     */
    AdminMenu getById(Integer id);

    /**
     * 根据多个id获取一系列菜单
     * 
     * @param ids
     * @return
     */
    List<AdminMenu> getByIds(List<Integer> ids);

    /**
     * 根据多个id删除一系列菜单
     * 
     * @param ids
     */
    void deleteByIds(List<Integer> ids);
}
