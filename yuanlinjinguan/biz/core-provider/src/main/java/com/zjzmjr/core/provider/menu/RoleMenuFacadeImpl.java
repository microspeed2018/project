package com.zjzmjr.core.provider.menu;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.menu.IRoleMenuFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.menu.MenuNode;
import com.zjzmjr.core.model.menu.RoleMenu;
import com.zjzmjr.core.model.menu.RoleMenuBind;
import com.zjzmjr.core.model.menu.RoleMenuQuery;
import com.zjzmjr.core.service.business.menu.RoleMenuService;

/**
 * 角色菜单接口层
 * 
 * @author oms
 * @version $Id: RoleMenuFacadeImpl.java, v 0.1 2017-2-14 下午3:43:19 oms Exp $
 */
@Service("roleMenuFacade")
public class RoleMenuFacadeImpl implements IRoleMenuFacade {

    private static final Logger logger = LoggerFactory.getLogger(RoleMenuFacadeImpl.class);

    @Resource(name = "roleMenuService")
    private RoleMenuService roleMenuService;

    /**
     * 
     * @see com.zjzmjr.core.api.menu.IRoleMenuFacade#getRoleMenuByCondition(com.zjzmjr.core.model.menu.RoleMenuQuery)
     */
    @Override
    public ResultPage<RoleMenu> getRoleMenuByCondition(RoleMenuQuery query) {
        ResultPage<RoleMenu> result = new ResultPage<RoleMenu>();
        try {
            result = roleMenuService.getRoleMenuByCondition(query);
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
     * 
     * @see com.zjzmjr.core.api.menu.IRoleMenuFacade#bindRoleMenu(com.zjzmjr.core.model.menu.RoleMenuBind)
     */
    @Override
    public Result bindRoleMenu(RoleMenuBind roleMenuBind) {
        Result result = new Result();
        try {
            result = roleMenuService.bindRoleMenu(roleMenuBind);
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
     * 
     * @see com.zjzmjr.core.api.menu.IRoleMenuFacade#getRoleMenuTree(com.zjzmjr.core.model.menu.RoleMenuQuery)
     */
    @Override
    public ResultEntry<MenuNode> getRoleMenuTree(RoleMenuQuery query) {
        ResultEntry<MenuNode> result = new ResultEntry<MenuNode>();
        try {
            result = roleMenuService.getRoleMenuTree(query);
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
