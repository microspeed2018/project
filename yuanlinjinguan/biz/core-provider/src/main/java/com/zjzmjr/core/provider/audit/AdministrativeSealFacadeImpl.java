package com.zjzmjr.core.provider.audit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.audit.IAdministrativeSealFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.service.business.audit.AdministrativeSealService;

/**
 * 行政盖章表对外接口
 * 
 * @author oms
 * @version $Id: AdministrativeSealFacadeImpl.java, v 0.1 2017-9-1 下午4:21:09 oms Exp $
 */
@Service("administrativeSealFacade")
public class AdministrativeSealFacadeImpl implements IAdministrativeSealFacade {

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectAuditFacadeImpl.class);

    @Resource(name = "administrativeSealService")
    private AdministrativeSealService administrativeSealService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.audit.IAdministrativeSealFacade#updateAdministrativeSealByType(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.audit.AdministrativeSeal)
     */
    @Override
    public ResultEntry<Integer> updateAdministrativeSealByType(GardenProject project, AdministrativeSeal seal) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = administrativeSealService.updateAdministrativeSealByType(project, seal);
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
     * @see com.zjzmjr.core.api.audit.IAdministrativeSealFacade#getProjectAdministrativeSeal(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectAdministrativeSeal> getProjectAdministrativeSeal(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectAdministrativeSeal> result = new ResultPage<GardenProjectAdministrativeSeal>();
        try {
            result = administrativeSealService.getProjectAdministrativeSeal(query);
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
     * @see com.zjzmjr.core.api.audit.IAdministrativeSealFacade#updateAdministrativeSealAndProjectStep(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> updateAdministrativeSealAndProjectStep(GardenProjectAuditQuery query) {
        ResultEntry<Integer>  result = new ResultEntry<Integer>();
        try {
            result = administrativeSealService.updateAdministrativeSealAndProjectStep(query);
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
     * @see com.zjzmjr.core.api.audit.IAdministrativeSealFacade#getProjectAdministrativeSealCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectAdministrativeSealCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = administrativeSealService.getProjectAdministrativeSealCount(query);
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
