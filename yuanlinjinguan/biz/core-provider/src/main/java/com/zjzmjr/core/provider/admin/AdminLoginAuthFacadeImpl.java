package com.zjzmjr.core.provider.admin;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.admin.IAdminLoginAuthFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;
import com.zjzmjr.core.service.business.admin.AdminLoginAuthService;

/**
 * 
 * 
 * @author oms
 * @version $Id: AdminLoginAuthFacadeImpl.java, v 0.1 2016-6-14 下午1:43:33 oms Exp $
 */
@Service("adminLoginAuthFacade")
public class AdminLoginAuthFacadeImpl implements IAdminLoginAuthFacade {

    private static final Logger logger = LoggerFactory.getLogger(AdminLoginAuthFacadeImpl.class);

    @Resource(name = "adminLoginAuthService")
    private AdminLoginAuthService adminLoginAuthService;
    
    /**
     * 
     * 
     * @param admin
     * @return
     */
    @Override
    public ResultRecord<SimpleGrantedAuthority> getUserAuths(Admin admin) {
        ResultRecord<SimpleGrantedAuthority> result = new ResultRecord<SimpleGrantedAuthority>();
        try{
            result = adminLoginAuthService.getUserAuths(admin);
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
