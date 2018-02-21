package com.zjzmjr.core.provider.logo;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.logo.ILogoManageFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;
import com.zjzmjr.core.service.business.logo.LogoManageService;

/**
 * 图标管理接口
 * 
 * @author lenovo
 * @version $Id: LogoManageFacadeImpl.java, v 0.1 2016-9-20 下午5:07:29 lenovo Exp $
 */
@Service("logoManageFacade")
public class LogoManageFacadeImpl implements ILogoManageFacade{

    private static final Logger logger = LoggerFactory.getLogger(LogoManageFacadeImpl.class);
    
    @Resource(name = "logoManageService")
    private LogoManageService logoManageService;
   
    /**
     * 
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#getLogoManage(com.zjzmjr.core.model.logo.LogoManageQuery)
     */
    @Override
    public ResultPage<LogoManage> getLogoManage(LogoManageQuery query) {
        ResultPage<LogoManage> result = new ResultPage<LogoManage>();
        try {
            result = logoManageService.getLogoManage(query);
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
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#updateLogoManage(com.zjzmjr.core.model.logo.LogoManage)
     */
    @Override
    public ResultEntry<Integer> updateLogoManage(LogoManage logoManage) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = logoManageService.updateLogoManage(logoManage);
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
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#insertLogoManage(com.zjzmjr.core.model.logo.LogoManage)
     */
    @Override
    public ResultEntry<Integer> insertLogoManage(LogoManage logoManage) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = logoManageService.insertLogoManage(logoManage);
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
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#getMaxLogoNo()
     */
    @Override
    public ResultEntry<Integer> getMaxLogoNo(Integer logoTypeNo) {
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        try {
            result=logoManageService.getMaxLogoNo(logoTypeNo);
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
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#getAllLogoManage(com.zjzmjr.core.model.logo.LogoManageQuery)
     */
    @Override
    public ResultRecord<LogoManage> getAllLogoManage(LogoManageQuery query) {
        ResultRecord<LogoManage> result = new ResultRecord<LogoManage>();
        try {
            result = logoManageService.getAllLogoManage(query);
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
     * @see com.zjzmjr.core.api.logo.ILogoManageFacade#deleteLogoManage(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteLogoManage(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = logoManageService.deleteLogoManage(id);
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
