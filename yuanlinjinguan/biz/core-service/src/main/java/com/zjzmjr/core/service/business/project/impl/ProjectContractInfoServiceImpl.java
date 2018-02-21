package com.zjzmjr.core.service.business.project.impl;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectContractInfoService;

/**
 * 项目表、合同表、分包表及与合同有关的业务处理层
 * 
 * @author oms
 * @version $Id: ProjectContractInfoServiceImpl.java, v 0.1 2017-9-3 下午5:45:32 oms Exp $
 */
@Service("projectContractInfoService")
public class ProjectContractInfoServiceImpl implements ProjectContractInfoService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractInfoServiceImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    /** 合同分包表业务 */
    @Resource(name = "contractSubpackageService")
    private ContractSubpackageService contractSubpackageService;
    
    /** 院办审核表业务 */
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractInfoService#insertProjectSubpackage(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ContractSubpackage)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertProjectSubpackage(GardenProject project, OfficeAudit officeAudit) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project) || Util.isNull(project.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        ResultEntry<Integer> projRst = gardenProjectService.updateGardenProjectById(project);
        if(projRst.isSuccess()){
            projRst = officeAuditService.insertOfficeAudit(officeAudit);
            if(!projRst.isSuccess()){
                throw new ApplicationException("院办审核表操作失败");
            }
        }
        return projRst;
    }
    
}
