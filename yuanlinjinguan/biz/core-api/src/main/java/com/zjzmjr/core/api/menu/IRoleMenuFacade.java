package com.zjzmjr.core.api.menu;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.RoleMenu;
import com.zjzmjr.core.model.menu.RoleMenuBind;
import com.zjzmjr.core.model.menu.RoleMenuQuery;

/**
 * 角色菜单接口层
 * 
 * @author oms
 * @version $Id: IRoleMenuFacade.java, v 0.1 2017-2-14 下午3:42:28 oms Exp $
 */
public interface IRoleMenuFacade {

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
    
}
