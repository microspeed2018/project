package com.zjzmjr.core.provider.user;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.user.IStaffWagesFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;
import com.zjzmjr.core.service.business.user.StaffWagesService;

@Service("staffWagesFacade")
public class StaffWagesFacadeImpl implements IStaffWagesFacade {

    private static final Logger logger = LoggerFactory.getLogger(StaffWagesFacadeImpl.class);

    @Resource(name = "staffWagesService")
    private StaffWagesService staffWagesService;

    /**
     * 
     * @see com.zjzmjr.core.api.user.IStaffWagesFacade#queryStaffWages(com.zjzmjr.core.model.user.StaffWagesQuery)
     */
    @Override
    public ResultPage<StaffWagesInfo> queryStaffWages(StaffWagesQuery query) {
        ResultPage<StaffWagesInfo> result = new ResultPage<StaffWagesInfo>();
        try {
            result = staffWagesService.queryStaffWages(query);
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
     * @see com.zjzmjr.core.api.user.IStaffWagesFacade#getStaffWageById(java.lang.Integer)
     */
    @Override
    public ResultEntry<StaffWagesInfo> getStaffWageById(Integer id) {
        ResultEntry<StaffWagesInfo> result = new ResultEntry<StaffWagesInfo>();
        try {
            result = staffWagesService.getStaffWageById(id);
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
     * @see com.zjzmjr.core.api.user.IStaffWagesFacade#batchInsert(java.util.List)
     */
    @Override
    public ResultRecord<StaffWagesInfo> batchInsert(List<StaffWagesInfo> list) {
        ResultRecord<StaffWagesInfo> result = new ResultRecord<StaffWagesInfo>();
        try {
            result = staffWagesService.batchInsert(list);
        } catch(ApplicationException e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            return result;
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
     * @see com.zjzmjr.core.api.user.IStaffWagesFacade#importExcel(org.apache.poi.xssf.usermodel.XSSFSheet)
     */
    @Override
    public ResultRecord<StaffWagesInfo> importExcel(StaffWagesQuery query) {
        ResultRecord<StaffWagesInfo> result = new ResultRecord<StaffWagesInfo>();
        try {
            result = staffWagesService.importExcel(query);
        } catch(ApplicationException e){
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            return result;
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
     * @see com.zjzmjr.core.api.user.IStaffWagesFacade#exportExcel(com.zjzmjr.core.model.user.StaffWagesQuery)
     */
    @Override
    public ResultEntry<String> exportExcel(StaffWagesQuery query) {
        ResultEntry<String> result = new ResultEntry<String>();
        try {
            result = staffWagesService.exportExcel(query);
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
