/**
 * Yztz.com Inc.
 * Copyright (c) 2013-2015 All Rights Reserved.
 */
package com.zjzmjr.core.provider.menu;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.menu.IMenuFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.menu.AdminMenu;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.MenuQuery;
import com.zjzmjr.core.model.menu.MenuUserBind;
import com.zjzmjr.core.service.business.menu.MenuService;

/**
 * 
 * @author js
 * @version $Id: MenuFacadeImpl.java, v 0.1 2015年10月29日 下午3:32:13 js Exp $
 */
@Service("menuFacade")
public class MenuFacadeImpl implements IMenuFacade {

    private static final Logger logger = LoggerFactory.getLogger(MenuFacadeImpl.class);

    @Resource(name = "menuService")
    private MenuService menuService;

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#getTopMenu(com.yztz.finance.model.admin.MenuQuery)
     */
    @Override
    public ResultRecord<MenuNode> getTopMenu(MenuQuery query) {
        ResultRecord<MenuNode> result = new ResultRecord<MenuNode>();
        try {
            result = menuService.getTopMenu(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#getUserMenuTree(com.yztz.finance.model.admin.MenuQuery)
     */
    @Override
    public ResultEntry<MenuNode> getUserMenuTree(MenuQuery query) {
        ResultEntry<MenuNode> result = new ResultEntry<MenuNode>();
        try{
            result = menuService.getUserMenuTree(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#addMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    public Result addMenu(AdminMenu adminMenu) {
        Result result = new Result();
        try{
            result = menuService.addMenu(adminMenu);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#updateMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    public Result updateMenu(AdminMenu adminMenu) {
        Result result = new Result();
        try{
            result = menuService.updateMenu(adminMenu);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#deleteMenu(com.yztz.finance.model.admin.AdminMenu)
     */
    @Override
    public Result deleteMenu(AdminMenu adminMenu) {
        Result result = new Result();
        try{
            result = menuService.deleteMenu(adminMenu);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * @see com.yztz.finance.api.menu.IMenuFacade#bindUserMenu(com.yztz.finance.model.admin.MenuUserBind)
     */
    @Override
    public Result bindUserMenu(MenuUserBind menuUserBind) {
        Result result = new Result();
        try{
            result = menuService.bindUserMenu(menuUserBind);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

}
 