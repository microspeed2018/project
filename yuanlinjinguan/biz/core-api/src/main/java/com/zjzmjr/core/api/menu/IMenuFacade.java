/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.api.menu;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuQuery;
import com.zjzmjr.core.model.menu.MenuUserBind;

/**
 * 
 * @author js
 * @version $Id: IMenuFacade.java, v 0.1 2015年10月29日 下午3:19:46 js Exp $
 */
public interface IMenuFacade {

    /**
     * 获取顶级菜单
     * 
     * @param query
     * @return
     */
    ResultRecord<MenuNode> getTopMenu(MenuQuery query);

    /**
     * 获取用户菜单树
     * 
     * @param query
     * @return
     */
    ResultEntry<MenuNode> getUserMenuTree(MenuQuery query);

    /**
     * 添加菜单
     * 
     * @param adminMenu
     * @return
     */
    Result addMenu(AdminMenu adminMenu);

    /**
     * 更新菜单
     * 
     * @param adminMenu
     * @return
     */
    Result updateMenu(AdminMenu adminMenu);

    /**
     * 删除菜单
     * 
     * @param adminMenu
     * @return
     */
    Result deleteMenu(AdminMenu adminMenu);

    /**
     * 绑定用户菜单
     * 
     * @param menuUserBind
     * @return
     */
    Result bindUserMenu(MenuUserBind menuUserBind);
}
