package com.zjzmjr.core.service.business.menu;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.RoleMenu;
import com.zjzmjr.core.model.menu.RoleMenuBind;
import com.zjzmjr.core.model.menu.RoleMenuQuery;

/**
 * 角色菜单
 * 
 * @author oms
 * @version $Id: RoleMenuService.java, v 0.1 2017-2-14 上午10:27:26 oms Exp $
 */
public interface RoleMenuService {

    /**
     * 获取角色菜单信息
     * 
     * @param query
     * @return
     */
    ResultPage<RoleMenu> getRoleMenuByCondition(RoleMenuQuery query);
    
    /**
     * 角色菜单绑定
     * 
     * @param menuUserBind
     * @return
     */
    Result bindRoleMenu(RoleMenuBind roleMenuBind);
    
    /**
     * 获取角色菜单树
     * 
     * @param query
     * @return
     */
    ResultEntry<MenuNode> getRoleMenuTree(RoleMenuQuery query);
    
    /**
     * 创建管理员时，根据管理员的角色自动绑定菜单
     * 
     * @param roleType
     * @param userId
     * @return
     */
    ResultEntry<Integer> bindUserMenu(Integer roleType, Integer userId);
    
    /**
     * 更新管理员的时候，根据管理员的角色绑定菜单
     * 
     * @param roleType
     * @param userId
     * @return
     */
    ResultEntry<Integer> bindUpdateUserMenu(Integer roleType, Integer userId);
    
}
