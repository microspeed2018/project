package com.zjzmjr.core.provider.project;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IProjectContractInfoFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.business.project.ProjectContractInfoService;
import com.zjzmjr.core.service.business.project.SubpackagePaymentService;

/**
 * 项目表、合同表、分包表及与合同有关的业务处理接口
 * 
 * @author oms
 * @version $Id: ProjectContractInfoFacadeImpl.java, v 0.1 2017-9-3 下午6:10:08 oms Exp $
 */
@Service("projectContractInfoFacade")
public class ProjectContractInfoFacadeImpl implements IProjectContractInfoFacade {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractInfoFacadeImpl.class);
    
    @Resource(name = "projectContractInfoService")
    private ProjectContractInfoService projectContractInfoService;
    
    @Resource(name = "contractSubpackageService")
    private ContractSubpackageService contractSubpackageService;
    
    /**
     * 分包付款表业务
     */
    @Resource(name = "subpackagePaymentService")
    private SubpackagePaymentService subpackagePaymentService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#insertProjectSubpackage(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.project.ContractSubpackage, com.zjzmjr.core.model.audit.OfficeAudit)
     */
    @Override
    public ResultEntry<Integer> insertProjectSubpackage(GardenProject project, OfficeAudit officeAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectContractInfoService.insertProjectSubpackage(project, officeAudit);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#deleteSubpackageByCondition(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultEntry<Integer> deleteSubpackageByCondition(ContractQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = contractSubpackageService.deleteSubpackageByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#updateContractSubpackageById(com.zjzmjr.core.model.project.ContractSubpackage)
     */
    public ResultEntry<Integer> updateContractSubpackageById(ContractSubpackage record, List<SubpackagePayment> payLst) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = contractSubpackageService.updateContractSubpackageById(record,payLst);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#getSubpackagePaymentByCondition(com.zjzmjr.core.model.project.ContractSubpackageQuery)
     */
    @Override
    public ResultPage<SubpackagePaymentInfo> getSubpackagePaymentByCondition(ContractSubpackageQuery query) {
        ResultPage<SubpackagePaymentInfo> result = new ResultPage<SubpackagePaymentInfo>();
        try {
            result = subpackagePaymentService.getSubpackagePaymentByCondition(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#getContractSubpackageInfo(com.zjzmjr.core.model.project.ContractSubpackageQuery)
     */
    @Override
    public ResultRecord<ContractSubpackageInfo> getContractSubpackageInfo(ContractSubpackageQuery query) {
        ResultRecord<ContractSubpackageInfo> result = new ResultRecord<ContractSubpackageInfo>();
        try {
            result = contractSubpackageService.getContractSubpackageInfo(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
}
