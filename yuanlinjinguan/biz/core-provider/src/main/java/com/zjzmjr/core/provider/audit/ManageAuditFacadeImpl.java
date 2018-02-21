package com.zjzmjr.core.provider.audit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.audit.IManageAuditFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.service.business.audit.ManageAuditService;

/**
 * 经营部门审核对外接口
 * 
 * @author oms
 * @version $Id: ManageAuditFacadeImpl.java, v 0.1 2017-8-31 下午6:38:11 oms Exp $
 */
@Service("manageAuditFacade")
public class ManageAuditFacadeImpl implements IManageAuditFacade{

    private static final Logger logger = LoggerFactory.getLogger(ManageAuditFacadeImpl.class);
    
    @Resource(name = "manageAuditService")
    private ManageAuditService manageAuditService;

    @Override
    public ResultPage<GardenProjectManageAudit> getProjectManageAudit(GardenProjectAuditQuery query) {
        return null;
    }

    @Override
    public ResultEntry<Integer> updateManageAuditById(ManageAudit officeAudit) {
        return null;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IManageAuditFacade#insertManageAudit(com.zjzmjr.core.model.audit.ManageAudit)
     */
    @Override
    public ResultEntry<Integer> insertManageAudit(ManageAudit officeAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = manageAuditService.insertManageAudit(officeAudit);
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
