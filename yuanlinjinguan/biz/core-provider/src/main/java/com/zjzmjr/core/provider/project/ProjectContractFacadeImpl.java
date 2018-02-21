package com.zjzmjr.core.provider.project;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IProjectContractFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.business.project.ProjectContractService;

/**
 * 项目合同及开标公司相关接口
 * 
 * @author oms
 * @version $Id: ProjectContractFacadeImpl.java, v 0.1 2017-8-27 下午10:58:27 oms Exp $
 */
@Service("projectContractFacade")
public class ProjectContractFacadeImpl implements IProjectContractFacade {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractFacadeImpl.class);

    @Resource(name = "projectContractService")
    private ProjectContractService projectContractService;
    
    /** 项目分包业务 */
    @Resource(name = "contractSubpackageService")
    private ContractSubpackageService contractSubpackageService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getContractMaxNo(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultEntry<String> getContractMaxNo(ContractQuery query) {
        ResultEntry<String> result = new ResultEntry<String>();
        try {
            result = projectContractService.getContractMaxNo(query);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getProjectContractById(java.lang.Integer)
     */
    @Override
    public ResultEntry<ProjectContract> getProjectContractById(Integer id) {
        ResultEntry<ProjectContract> result = new ResultEntry<ProjectContract>();
        try {
            result = projectContractService.getProjectContractById(id);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#generateProjectContractInfo(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultEntry<Integer> generateProjectContractInfo(ProjectContract contract, GardenProject project,  List<ContractPayment> list) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectContractService.generateProjectContractInfo(contract,project,list);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getProjectContractByCondition(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultPage<ProjectContractInfo> getProjectContractByCondition(ContractQuery query) {
        ResultPage<ProjectContractInfo> result = new ResultPage<ProjectContractInfo>();
        try {
            result = projectContractService.getProjectContractByCondition(query);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#insertContractSubpackage(com.zjzmjr.core.model.project.ContractSubpackage)
     */
    @Override
    public ResultEntry<Integer> insertContractSubpackage(ContractSubpackage record,List<SubpackagePayment> payLst) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = contractSubpackageService.insertContractSubpackage(record,payLst);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#updateProjectContractInfo(com.zjzmjr.core.model.project.ProjectContract, com.zjzmjr.core.model.project.GardenProject, java.util.List)
     */
    @Override
    public ResultEntry<Integer> updateProjectContractInfo(ProjectContract contract, GardenProject project, List<ContractPayment> list) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectContractService.updateProjectContractInfo(contract, project, list);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getProjectContractPaymentInfo(com.zjzmjr.core.model.project.ContractPaymentQuery)
     */
    @Override
    public ResultPage<ContractPaymentInfo> getProjectContractPaymentInfo(ContractPaymentQuery query) {
        ResultPage<ContractPaymentInfo> result = new ResultPage<ContractPaymentInfo>();
        try {
            result = projectContractService.getProjectContractPaymentInfo(query);
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
     * @see com.zjzmjr.core.api.project.IProjectContractInfoFacade#applyRatio(com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    public ResultEntry<Integer> applyRatio(ProjectContract contract) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = projectContractService.applyRatio(contract);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getProjectContractByProjectIdAndStatus(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultRecord<ProjectContract> getProjectContractByProjectIdAndStatus(ContractQuery query) {
        ResultRecord<ProjectContract> result = new ResultRecord<ProjectContract>();
        try {
            result = projectContractService.getProjectContractByProjectIdAndStatus(query);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getProjectContractList(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultPage<ProjectContractInfo> getProjectContractList(ContractQuery query) {
        ResultPage<ProjectContractInfo> result = new ResultPage<ProjectContractInfo>();
        try {
            result = projectContractService.getProjectContractList(query);
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
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#getContractPaymentByCondition(com.zjzmjr.core.model.project.ContractPaymentQuery)
     */
    @Override
    public ResultEntry<ContractPayment> getContractPaymentByCondition(ContractPaymentQuery query) {
        ResultEntry<ContractPayment> result = new ResultEntry<ContractPayment>();
        try {
            result = projectContractService.getContractPaymentByCondition(query);
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
