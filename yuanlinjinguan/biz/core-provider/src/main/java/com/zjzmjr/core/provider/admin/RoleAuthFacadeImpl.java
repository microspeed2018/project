package com.zjzmjr.core.provider.admin;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.admin.IRoleAuthFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.admin.AuthNodeDTO;
import com.zjzmjr.core.service.business.admin.RoleAuthService;

/**
 * 角色权限的接口实现
 * 
 * @author UI
 * @version $Id: RoleAuthFacadeImpl.java, v 0.1 2017-6-7 下午2:36:12 UI Exp $
 */
@Service("roleAuthFacade")
public class RoleAuthFacadeImpl implements IRoleAuthFacade {

    private static final Logger logger = LoggerFactory.getLogger(RoleAuthFacadeImpl.class);

    @Resource(name = "roleAuthService")
    private RoleAuthService roleAuthService;

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IRoleAuthFacade#getGroupRoleAuth(java.lang.Long)
     */
    @Override
    public ResultEntry<Map<String, List<AuthNodeDTO>>> getGroupRoleAuth(Long roleId) {
        logger.debug("getGroupRoleAuth 执行开始  入参:{}", roleId);
        ResultEntry<Map<String, List<AuthNodeDTO>>> result = new ResultEntry<Map<String,List<AuthNodeDTO>>>();
        try{
            result = roleAuthService.getGroupRoleAuth(roleId);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        logger.debug("getGroupRoleAuth 执行结束  出参:{}", result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IRoleAuthFacade#bindRoleAuth(java.lang.Integer, java.util.List)
     */
    @Override
    public Result bindRoleAuth(Integer roleId, List<Integer> authIds, Integer currUserId) {
        logger.debug("getGroupRoleAuth 执行开始  入参:{} roleId:{}", roleId+" authIds:{}"+authIds);
        Result result = new Result();
        try{
            result = roleAuthService.bindRoleAuth(roleId, authIds, currUserId);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        logger.debug("getGroupRoleAuth 执行结束  出参:{}", result);
        return result;
    }

}
