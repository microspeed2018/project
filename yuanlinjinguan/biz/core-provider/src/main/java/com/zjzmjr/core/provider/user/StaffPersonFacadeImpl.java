package com.zjzmjr.core.provider.user;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.business.user.StaffInfoService;

/**
 * 公司员工外部人员接口
 * 
 * @author oms
 * @version $Id: StaffPersonFacadeImpl.java, v 0.1 2017-8-15 下午7:48:00 oms Exp $
 */
@Service("staffPersonFacade")
public class StaffPersonFacadeImpl implements IStaffPersonFacade {

    private static final Logger logger = LoggerFactory.getLogger(StaffPersonFacadeImpl.class);

    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    @Resource(name = "externalPersonService")
    private ExternalPersonService externalPersonService;

    /**
     * 
     * @see com.zjzmjr.core.api.user.IUserFacade#getStaffInfoByCondition(com.zjzmjr.core.model.user.StaffInfoQuery)
     */
    @Override
    public ResultPage<StaffBasicInfo> getStaffInfoByCondition(StaffInfoQuery query) {
        ResultPage<StaffBasicInfo> result = new ResultPage<StaffBasicInfo>();
        try {
            result = staffInfoService.getStaffInfoByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IUserFacade#insertStaffInfo(com.zjzmjr.core.model.user.StaffInfo)
     */
    @Override
    public ResultEntry<Integer> insertStaffInfo(StaffInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = staffInfoService.insertStaffInfo(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IUserFacade#updateStaffInfoById(com.zjzmjr.core.model.user.StaffInfo)
     */
    @Override
    public ResultEntry<Integer> updateStaffInfoById(StaffInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = staffInfoService.updateStaffInfoById(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#getExternalPersonByCondition(com.zjzmjr.core.model.user.ExternalPersonQuery)
     */
    @Override
    public ResultPage<ExternalPersonInfo> getExternalPersonByCondition(ExternalPersonQuery query) {
        ResultPage<ExternalPersonInfo> result = new ResultPage<ExternalPersonInfo>();
        try {
            result = externalPersonService.getExternalPersonByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#getExternalPersonById(java.lang.Integer)
     */
    @Override
    public ResultEntry<ExternalPerson> getExternalPersonById(Integer id) {
        ResultEntry<ExternalPerson> result = new ResultEntry<ExternalPerson>();
        try {
            result = externalPersonService.getExternalPersonById(id);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#insertExternalPerson(com.zjzmjr.core.model.user.ExternalPerson)
     */
    @Override
    public ResultEntry<Integer> insertExternalPerson(ExternalPerson record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = externalPersonService.insertExternalPerson(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#updateExternalPersonById(com.zjzmjr.core.model.user.ExternalPerson)
     */
    @Override
    public ResultEntry<Integer> updateExternalPersonById(ExternalPerson record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = externalPersonService.updateExternalPersonById(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#updateStaffInfoAndAdmin(com.zjzmjr.core.model.user.StaffBasicInfo)
     */
    @Override
    public ResultEntry<Integer> updateStaffInfoAndAdmin(StaffBasicInfo staffBasicInfo) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = staffInfoService.updateStaffInfoAndAdmin(staffBasicInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#updateExternalPersonAndAdmin(com.zjzmjr.core.model.user.ExternalPersonInfo)
     */
    @Override
    public ResultEntry<Integer> updateExternalPersonAndAdmin(ExternalPersonInfo externalPersonInfo) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = externalPersonService.updateExternalPersonAndAdmin(externalPersonInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    @Override
    public ResultEntry<Integer> getExternalPersonEmployeeMaxNo() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = externalPersonService.getExternalPersonEmployeeMaxNo();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffPersonFacade#insertStaffInfoByTalent(com.zjzmjr.core.model.user.StaffBasicInfo)
     */
    @Override
    public ResultEntry<Integer> insertStaffInfoByTalent(StaffBasicInfo staffBasicInfo) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = staffInfoService.insertStaffInfoByTalent(staffBasicInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
}
