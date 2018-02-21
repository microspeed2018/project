package com.zjzmjr.core.provider.admin;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.admin.AdminStaff;
import com.zjzmjr.core.model.admin.AdminUpdate;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.admin.AdminService;

/**
 * 
 * 
 * @author oms
 * @version $Id: AdminFacadeImpl.java, v 0.1 2016-5-25 上午10:11:54 oms Exp $
 */
@Service("adminFacade")
public class AdminFacadeImpl implements IAdminFacade {

    private static final Logger logger = LoggerFactory.getLogger(AdminFacadeImpl.class);

    @Resource(name = "adminService")
    private AdminService adminService;

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminFacade#queryByPage(com.zjzmjr.core.model.admin.AdminQuery)
     */
    @Override
    public ResultPage<Admin> queryByPage(AdminQuery adminQuery) {
        ResultPage<Admin> result = new ResultPage<Admin>();
        try{
            result = adminService.queryByPage(adminQuery);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getByUsername(java.lang.String)
     */
    @Override
    public ResultEntry<Admin> getByUsername(String userName) {
        ResultEntry<Admin> result = new ResultEntry<Admin>();
        try{
            result = adminService.getByUsername(userName);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getByMobile(java.lang.String)
     */
    @Override
    public ResultEntry<Admin> getByMobile(String mobile) {
        ResultEntry<Admin> result = new ResultEntry<Admin>();
        try {
            result = adminService.getByMobile(mobile);
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
     * @see com.yztz.finance.api.admin.IAdminFacade#create(com.yztz.finance.model.admin.AdminCreate)
     */
    @Override
    public Result create(AdminCreate adminCreate,CompanyStaffPerson companyStaffPerson) {
        Result result = new Result();
        try{
            result = adminService.create(adminCreate,companyStaffPerson);
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
     * @see com.yztz.finance.api.admin.IAdminFacade#update()
     */
    @Override
    public Result update(AdminUpdate adminUpdate) {
        Result result = new Result();
        try{
            result = adminService.update(adminUpdate);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getByID(java.lang.Integer)
     */
    @Override
    public ResultEntry<Admin> getByID(Integer id) {
        ResultEntry<Admin> result = new ResultEntry<Admin>();
        try{
            result = adminService.getById(id);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#updateAdmin(com.zjzmjr.core.model.admin.Admin)
     */
    @Override
    public Result updateAdmin(Admin admin) {
        Result result = new Result();
        try{
            result = adminService.updateAdmin(admin);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#changePassword(java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public Result changePassword(Integer adminId, String oldPwd, String newPwd) {
        Result result = new Result();
        try{
            result = adminService.changePassword(adminId, oldPwd, newPwd);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Admin> getById(Integer createUserId) {
        ResultEntry<Admin> result = new ResultEntry<Admin>();
        try{
            result = adminService.getById(createUserId);
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
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getAdminStaff(com.zjzmjr.core.model.user.StaffInfoQuery)
     */
    @Override
    public ResultPage<AdminStaff> getAdminStaff(StaffInfoQuery query) {
        ResultPage<AdminStaff> result = new ResultPage<AdminStaff>();
        try {
            result = adminService.getAdminStaff(query);
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
