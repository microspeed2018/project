package com.zjzmjr.core.service.business.project.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;
import com.zjzmjr.core.service.business.audit.ManageAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectContractService;
import com.zjzmjr.core.service.mapper.dao.project.ContractPaymentMapper;
import com.zjzmjr.core.service.mapper.dao.project.ProjectContractMapper;

/**
 * 项目中所有与合同有关的业务处理类
 * 
 * @author oms
 * @version $Id: ProjectContractServiceImpl.java, v 0.1 2017-8-24 下午5:02:49 oms
 *          Exp $
 */
@Service("projectContractService")
public class ProjectContractServiceImpl implements ProjectContractService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractServiceImpl.class);

    /** 项目合同 */
    @Resource(name = "projectContractMapper")
    private ProjectContractMapper projectContractMapper;

    /** 合同缴费 */
    @Resource(name = "contractPaymentMapper")
    private ContractPaymentMapper contractPaymentMapper;

    /** 项目 */
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;

    /** 经营审核 */
    @Resource(name = "manageAuditService")
    private ManageAuditService manageAuditService;

    /** 院办审核 */
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;

    /**
     * 
     * @throws ApplicationException
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#generateProjectContractInfo
     *      (com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> generateProjectContractInfo(ProjectContract contract, GardenProject project, List<ContractPayment> list) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(contract)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        //查询此项目id是否已存在合同
        ContractQuery contractQuery = new ContractQuery();
        contractQuery.setProjectId(contract.getProjectId());
        ResultRecord<ProjectContract> contractRst = getProjectContractByProjectIdAndStatus(contractQuery);
        if(contractRst.isSuccess()){
            result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
            result.setErrorMsg("此项目合同已经存在!");
            result.setSuccess(false);
            logger.error("此项目合同已经存在!");
        }else{
            GardenProject gardenProject = new GardenProject();
            // 新增合同
            contract.setCreateTime(contract.getUpdateTime());
            contract.setCreateUserId(contract.getUpdateUserId());
            result = insertProjectContract(contract);
            if (result.isSuccess()) {
                if (Util.isNotNull(project.getId())) {
                    // 根据项目id查询项目信息比较版本号
                    GardenProjectQuery query = new GardenProjectQuery();
                    query.setId(project.getId());
                    ResultEntry<GardenProject> projectRst = gardenProjectService.getGardenProjectByIdAndStatus(query);
                    if (projectRst.isSuccess()) {
                        if (projectRst.getT().getVersion().equals(project.getVersion())) {
                            // 有临时数据直接更新
                            if (Util.isNotNull(project.getTemporaryId())) {
                                project.setId(project.getTemporaryId());
                                result = gardenProjectService.updateGardenProjectById(project);
                                if (!result.isSuccess()) {
                                    throw new ApplicationException("合同新增时，修改项目临时数据失败");
                                }
                            } else {
                                // 先插入修改后的项目
                                gardenProject.setId(project.getId());
                                project.setId(null);
                                project.setStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
                                project.setCreateTime(contract.getUpdateTime());
                                project.setCreateUserId(contract.getUpdateUserId());
                                project.setUpdateTime(contract.getUpdateTime());
                                project.setUpdateUserId(contract.getUpdateUserId());
                                // 新增一条项目数据待审核
                                result = gardenProjectService.insertProjectByContract(project);
                                if (result.isSuccess()) {
                                    // 更新原来的项目并且memo赋值为新增临时项目id
                                    gardenProject.setMemo(project.getId().toString());
                                    gardenProject.setUpdateTime(contract.getUpdateTime());
                                    gardenProject.setUpdateUserId(contract.getUpdateUserId());
                                    result = gardenProjectService.updateGardenProjectById(gardenProject);
                                    if (result.isSuccess()) {
                                        // 经营审核插入数据
                                        ManageAudit manage = new ManageAudit();
                                        manage.setCompanyId(project.getCompanyId());
                                        manage.setProjectId(gardenProject.getId());
                                        manage.setApplicant(project.getUpdateUserId());
                                        manage.setType(50);
                                        manage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                                        manage.setCreateTime(contract.getUpdateTime());
                                        manage.setCreateUserId(contract.getUpdateUserId());
                                        manage.setUpdateTime(contract.getUpdateTime());
                                        manage.setUpdateUserId(contract.getUpdateUserId());
                                        result = manageAuditService.insertManageAudit(manage);
                                        if (!result.isSuccess()) {
                                            throw new ApplicationException("修改项目时，插入经营审核表失败");
                                        }
                                    } else {
                                        throw new ApplicationException("修改原有项目失败");
                                    }
                                } else {
                                    throw new ApplicationException("项目表更新时，新增临时数据失败");
                                }
                            }
                        } else {
                            result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                            result.setErrorMsg(ErrorCodeEnum.RECORD_CHANGE.getName());
                            result.setSuccess(false);
                        }
                    } else {
                        result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                        result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                        result.setSuccess(false);
                    }
                }
                // 新增付款表
                ContractPayment payment = new ContractPayment();
                if (list != null && list.size() > 0) {
                    for (int i = 0; i < list.size(); i++) {
                        payment = list.get(i);
                        payment.setCompanyId(contract.getCompanyId());
                        payment.setProjectId(contract.getProjectId());
                        payment.setStatus(GenerateConstants.number_1);
                        payment.setUpdateTime(contract.getUpdateTime());
                        payment.setCreateTime(contract.getUpdateTime());
                        payment.setUpdateUserId(contract.getUpdateUserId());
                        payment.setCreateUserId(contract.getUpdateUserId());
                        int cnt = contractPaymentMapper.insertContractPayment(payment);
                        if (cnt <= 0) {
                            throw new ApplicationException("新增合同时，插入合同付款表失败");
                        } else {
                            result.setSuccess(true);
                            result.setT(cnt);
                        }
                    }
                }
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表插入失败");
            }
        }        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getContractMaxNo
     *      (com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultEntry<String> getContractMaxNo(ContractQuery query) {
        ResultEntry<String> result = new ResultEntry<String>();
        if (Util.isNull(query) || Util.isNull(query.getCompanyId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        // 获取现有最大的合同编号
        String maxContractNo = projectContractMapper.getContractMaxNo(query);
        if (Util.isNull(maxContractNo)) {
            maxContractNo = query.getContractNo().concat("01");
        } else {
            String increaNo = maxContractNo.substring(maxContractNo.length() - 2, maxContractNo.length());
            increaNo = String.format("%02d", (Integer.parseInt(increaNo) + 1));
            maxContractNo = maxContractNo.substring(0, maxContractNo.length() - 2).concat(increaNo);
        }
        result.setSuccess(true);
        result.setT(maxContractNo);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getProjectContractById
     *      (java.lang.Integer)
     */
    @Override
    public ResultEntry<ProjectContract> getProjectContractById(Integer id) {
        ResultEntry<ProjectContract> result = new ResultEntry<ProjectContract>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        ProjectContract contract = projectContractMapper.getProjectContractById(id);
        if (contract == null) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setT(contract);
            result.setSuccess(true);
        }

        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getProjectContractByCondition
     *      (com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    @Transactional
    public ResultPage<ProjectContractInfo> getProjectContractByCondition(ContractQuery query) {
        ResultPage<ProjectContractInfo> result = new ResultPage<ProjectContractInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = projectContractMapper.getProjectContractCount(query);
        if (total > 0) {
            List<ProjectContractInfo> list = projectContractMapper.getProjectContractByCondition(query);
            result.setList(list);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#insertProjectContract
     *      (com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertProjectContract(ProjectContract record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId()) || Util.isNull(record.getContractNo())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = projectContractMapper.insertProjectContract(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#updateProjectContractById
     *      (com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectContractById(ProjectContract record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = projectContractMapper.updateProjectContractById(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#updateProjectContractByProjectId
     *      (com.zjzmjr.core.model.project.ProjectContract)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectContractByProjectId(ProjectContract record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = projectContractMapper.updateProjectContractByProjectId(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#updateProjectContractInfo
     *      (com.zjzmjr.core.model.project.ProjectContract,
     *      com.zjzmjr.core.model.project.GardenProject, java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectContractInfo(ProjectContract contract, GardenProject project,List<ContractPayment> list) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();        
        if(Util.isNotNull(contract.getId())){
            //合同id查询版本号
            ContractQuery query = new ContractQuery();
            query.setProjectId(contract.getProjectId());
            query.setStatus(GenerateConstants.number_1);
            ResultRecord<ProjectContract> contractResult = getProjectContractByProjectIdAndStatus(query);
            if(contractResult.isSuccess()){
                if(contractResult.getList().get(0).getVersion().equals(contract.getVersion())){
                    //查询有无临时合同
                    query = new ContractQuery();
                    query.setProjectId(contract.getProjectId()); 
                    query.setStatus(GenerateConstants.number_0);
                    query.setCompanyId(contract.getCompanyId());
                    contractResult = getProjectContractByProjectIdAndStatus(query);
                    if(contractResult.isSuccess()){
                        //有临时合同，直接修改
                        contract.setId(contractResult.getList().get(0).getId());
                        int cnt = projectContractMapper.updateProjectContractById(contract);
                        if(cnt > 0){
                            if(Util.isNotNull(list)){
                                //删除原有未审核合同付款，新增修改
                                ContractQuery contractpayQuery = new ContractQuery();
                                contractpayQuery.setStatus(GenerateConstants.number_0);
                                contractpayQuery.setProjectId(query.getProjectId());
                                contractpayQuery.setCompanyId(query.getCompanyId());
                                cnt =contractPaymentMapper.deleteContractPaymentByCondition(contractpayQuery);
                                if(cnt>0){
                                    //插入付款方式表
                                    ContractPayment payment = new ContractPayment();
                                    if (list != null && list.size() > 0) {
                                        for (int i = 0; i < list.size(); i++) {
                                            payment = list.get(i);
                                            payment.setCompanyId(contract.getCompanyId());
                                            payment.setProjectId(contract.getProjectId());
                                            payment.setStatus(GenerateConstants.number_0);
                                            payment.setUpdateTime(contract.getUpdateTime());
                                            payment.setCreateTime(contract.getUpdateTime());
                                            payment.setUpdateUserId(contract.getUpdateUserId());
                                            payment.setCreateUserId(contract.getUpdateUserId());
                                            cnt = contractPaymentMapper.insertContractPayment(payment);
                                            if (cnt <= 0) {
                                                throw new ApplicationException("新增合同时，插入合同付款表失败");
                                            } else {
                                                result.setSuccess(true);
                                                result.setT(cnt);
                                            }
                                        }
                                    }
                                }else{
                                    throw new ApplicationException("修改合同修改合同付款表时，插入合同付款表失败");
                                }                               
                            }
                            result.setT(cnt);
                            result.setSuccess(true);
                        }else{
                            result.setSuccess(false);
                            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表更新失败");
                        }
                        return result;
                    }else{
                        if (Util.isNotNull(contract.getContractNo())) {
                            // 修改合同
                            ProjectContract projectContract = new ProjectContract();
                            projectContract.setId(contract.getId());
                            contract.setId(null);
                            contract.setCompanyId(contract.getCompanyId());
                            contract.setCreateTime(contract.getUpdateTime());
                            contract.setCreateUserId(contract.getUpdateUserId());
                            contract.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
                            int cnt = projectContractMapper.insertProjectContract(contract);
                            if (cnt > 0) {
                                // 经营审核插入数据
                                ManageAudit manage = new ManageAudit();
                                manage.setCompanyId(contract.getCompanyId());
                                manage.setProjectId(contract.getProjectId());
                                manage.setApplicant(contract.getUpdateUserId());
                                manage.setType(51);
                                manage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                                manage.setCreateTime(contract.getUpdateTime());
                                manage.setCreateUserId(contract.getUpdateUserId());
                                manage.setUpdateTime(contract.getUpdateTime());
                                manage.setUpdateUserId(contract.getUpdateUserId());
                                result = manageAuditService.insertManageAudit(manage);
                                if (!result.isSuccess()) {
                                    throw new ApplicationException("新增合同修改项目时，插入经营审核表失败");
                                }
                            } else {
                                result.setSuccess(false);
                                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":合同表临时数据新增失败");
                            }
                        }
                    }
                    if(Util.isNotNull(list)){
                      //删除原有未审核合同付款，新增修改
                        ContractQuery contractpayQuery = new ContractQuery();
                        contractpayQuery.setStatus(GenerateConstants.number_0);
                        contractpayQuery.setProjectId(query.getProjectId());
                        contractpayQuery.setCompanyId(query.getCompanyId());
                        contractPaymentMapper.deleteContractPaymentByCondition(contractpayQuery);
                          //插入付款方式表
                            ContractPayment payment = new ContractPayment();
                            if (list != null && list.size() > 0) {
                                for (int i = 0; i < list.size(); i++) {
                                    payment = list.get(i);
                                    payment.setCompanyId(contract.getCompanyId());
                                    payment.setProjectId(contract.getProjectId());
                                    payment.setStatus(GenerateConstants.number_0);
                                    payment.setUpdateTime(contract.getUpdateTime());
                                    payment.setCreateTime(contract.getUpdateTime());
                                    payment.setUpdateUserId(contract.getUpdateUserId());
                                    payment.setCreateUserId(contract.getUpdateUserId());
                                    int cnt = contractPaymentMapper.insertContractPayment(payment);
                                    if (cnt <= 0) {
                                        throw new ApplicationException("新增合同时，插入合同付款表失败");
                                    } else {
                                        result.setSuccess(true);
                                        result.setT(cnt);
                                    }
                                }
                            } 
                        
                    }
                }else{
                    result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                    result.setErrorMsg(ErrorCodeEnum.RECORD_CHANGE.getName());
                    result.setSuccess(false);   
                }
            }else{
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            } 
        }               
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getProjectContractByProjectIdAndStatus
     *      (com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultRecord<ProjectContract> getProjectContractByProjectIdAndStatus(ContractQuery query) {
        ResultRecord<ProjectContract> result = new ResultRecord<ProjectContract>();
        if (Util.isNull(query) || Util.isNull(query.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<ProjectContract> list = projectContractMapper.getProjectContractByProjectIdAndStatus(query);
        if (list == null || list.size() == 0) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getProjectContractPaymentInfo
     *      (com.zjzmjr.core.model.project.ContractPaymentQuery)
     */
    @Override
    public ResultPage<ContractPaymentInfo> getProjectContractPaymentInfo(ContractPaymentQuery query) {
        ResultPage<ContractPaymentInfo> result = new ResultPage<ContractPaymentInfo>();
        if (Util.isNull(query) || Util.isNull(query.getVerifyType())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = contractPaymentMapper.getProjectContractPaymentCount(query);
        if (total > 0) {
            List<ContractPaymentInfo> list = contractPaymentMapper.getProjectContractPaymentInfo(query);
            result.setList(list);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean

        ().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @throws ApplicationException
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#applyRatio
     *      (com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> applyRatio(ProjectContract contract) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(contract) || Util.isNull(contract.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        //查询合同有无待审核数据
        ContractQuery contractQuery = new ContractQuery();
        contractQuery.setProjectId(contract.getProjectId());
        contractQuery.setStatus(GenerateConstants.number_0);
        contractQuery.setCompanyId(contract.getCompanyId());
        List<ProjectContract> list = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
        if(list==null || list.size()==0){
            //无临时数据,新增临时数据
            int cnt = 0;
            if(Util.isNotNull(contract.getRatio())){
                contract.setStatus(GenerateConstants.number_0);
                contract.setCreateTime(contract.getUpdateTime());
                contract.setCreateUserId(contract.getUpdateUserId());
                cnt = projectContractMapper.insertProjectContract(contract);
            }else{
                cnt = projectContractMapper.updateProjectContractByProjectId(contract);
            }           
            if (cnt > 0) {
                if(GardenProjectStepEnum.P_380.getValue().equals(contract.getStep())){
                    // 合同表更新成功，修改项目step
                    GardenProject project = new GardenProject();
                    project.setId(contract.getProjectId());
                    project.setStep(GardenProjectStepEnum.P_390.getValue());
                    project.setUpdateTime(contract.getUpdateTime());
                    project.setUpdateUserId(contract.getUpdateUserId());
                    result = gardenProjectService.updateGardenProjectById(project);
                    if (result.isSuccess()) {
                        // 插入院办审核
                        OfficeAudit office = new OfficeAudit();
                        office.setCompanyId(contract.getCompanyId());
                        office.setProjectId(contract.getProjectId());
                        office.setApplicant(contract.getUpdateUserId());
                        office.setType(56);
                        office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        office.setCreateTime(contract.getUpdateTime());
                        office.setCreateUserId(contract.getUpdateUserId());
                        result = officeAuditService.insertOfficeAudit(office);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("申请合作比例时,院办审核表插入失败");
                        } else {
                            result.setSuccess(true);
                        }
                    } else {
                        throw new ApplicationException("申请合作比例时,修改项目step出错");
                    }
                }else{
                  //插入院办审核表 1.先查询是否已经存在审核数据，没有就新增
                    GardenProjectAuditQuery query = new GardenProjectAuditQuery();
                    query.setType(56);
                    query.setProjectId(contract.getProjectId());
                    query.setCompanyId(contract.getCompanyId());
                    query.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    ResultRecord<OfficeAudit> officeRst = officeAuditService.getProjectOfficeAuditByCondition(query);
                    if(!officeRst.isSuccess()){
                       //插入一条新的审核合作比例记录
                        OfficeAudit audit = new OfficeAudit();
                        audit.setProjectId(contract.getProjectId());
                        audit.setCompanyId(contract.getCompanyId());
                        audit.setApplicant(contract.getUpdateUserId());
                        audit.setType(56);
                        audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        audit.setCreateTime(contract.getUpdateTime());
                        audit.setCreateUserId(contract.getUpdateUserId());
                        result = officeAuditService.insertOfficeAudit(audit);
                        if(!result.isSuccess()){
                            throw new ApplicationException("修改合作比例时，插入院办审核失败");   
                        }
                    }
                }
            }else{
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode()); 
            }
        }else{
            if(Util.isNull(list.get(0).getRatio())){
                result.setSuccess(false);
                result.setErrorMsg("有合同内容仍未审核,请审核后申请合作比例!");
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode()); 
            }else{
                //在原数据上修改
                contract.setContractStatus(GenerateConstants.number_0);
                int cnt = projectContractMapper.updateProjectContractByProjectId(contract);
                if(cnt>0){
                    result.setSuccess(true);
                }else{
                    result.setSuccess(false);
                    result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                    result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());  
                }
            }            
        }
        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getProjectContractList(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    public ResultPage<ProjectContractInfo> getProjectContractList(ContractQuery query) {
        ResultPage<ProjectContractInfo> result = new ResultPage<ProjectContractInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = 0;
        GardenProjectQuery gQuery = new GardenProjectQuery();
        gQuery.setCompanyId(query.getCompanyId());
        gQuery.setProjectNo(query.getProjectNo());
        gQuery.setProjectLeader(StringUtil.nullToInteger(query.getProjectLeader()));
        gQuery.setName(query.getProjectName());
        gQuery.setType(query.getNature());
        gQuery.setCategory(query.getCategory());
        gQuery.setUserId(query.getUpdateUserId());
        gQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        gQuery.setMobile(query.getMobile());
        ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(gQuery);
        if(projectRst.isSuccess()){
            List<Integer> projectLst = new ArrayList<Integer>();
            for(GardenProjectInfo  projectInfo : projectRst.getList()) {
                projectLst.add(projectInfo.getId());
            }
            query.setProjectLst(projectLst);
            total = projectContractMapper.getProjectContractCount(query);
            if (total > 0) {
                List<ProjectContractInfo> list = projectContractMapper.getProjectContractList(query);
                result.setList(list);
                result.setSuccess(true);
            } else {
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            }
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }


        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ProjectContractService#getContractPaymentByCondition(com.zjzmjr.core.model.project.ContractPaymentQuery)
     */
    @Override
    public ResultEntry<ContractPayment> getContractPaymentByCondition(ContractPaymentQuery query) {
        ResultEntry<ContractPayment> result = new ResultEntry<ContractPayment>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        ContractPayment contractPayment = contractPaymentMapper.getContractPaymentByCondition(query);
        if (Util.isNotNull(contractPayment)) {
            result.setT(contractPayment);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        return result;
    }

}
