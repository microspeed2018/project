package com.zjzmjr.core.service.business.audit.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectSubpackageStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.service.business.audit.AdministrativeSealService;
import com.zjzmjr.core.service.business.audit.CashierConfirmationService;
import com.zjzmjr.core.service.business.audit.FinancialAuditService;
import com.zjzmjr.core.service.business.audit.GardenProjectAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.OfficeAuditMapper;
import com.zjzmjr.core.service.mapper.dao.project.ContractPaymentMapper;
import com.zjzmjr.core.service.mapper.dao.project.ContractSubpackageMapper;
import com.zjzmjr.core.service.mapper.dao.project.ProjectContractMapper;

@Service("gardenProjectAuditService")
public class GardenProjectAuditServiceImpl implements GardenProjectAuditService{
    
    private static final Logger logger = LoggerFactory.getLogger(GardenProjectAuditServiceImpl.class);
    
    @Resource(name = "officeAuditMapper")
    private OfficeAuditMapper officeAuditMapper;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "cashierConfirmationService")
    private CashierConfirmationService cashierConfirmationService;

    @Resource(name = "administrativeSealService")
    private AdministrativeSealService administrativeSealService;
    
    @Resource(name = "financialAuditService")
    private FinancialAuditService financialAuditService;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService  officeAuditService;
    
    @Resource(name = "contractSubpackageService")
    private ContractSubpackageService  contractSubpackageService;
    
    @Resource(name = "contractPaymentMapper")
    private ContractPaymentMapper contractPaymentMapper;
    
    @Resource(name = "projectContractMapper")
    private ProjectContractMapper projectContractMapper;
    
    @Resource(name = "contractSubpackageMapper")
    private ContractSubpackageMapper contractSubpackageMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.GardenProjectAuditService#updateOfficeAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateOfficeAudit(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        OfficeAudit office = new OfficeAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery= new GardenProjectAuditQuery();
        ResultPage<GardenProjectOfficeAudit> manageRst = null;
        office.setStatus(query.getStatus());
        office.setUpdateTime(new Date());
        office.setUpdateUserId(query.getUpdateUserId());
        office.setMemo(query.getMemo());
        if(Util.isNull(query.getId())){
            //通过 项目id 项目步骤  公司id查询 
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = officeAuditService.getProjectOfficeAudit(manageQuery);
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                office.setId(manageRst.getList().get(0).getId()); 
            }
        }else{
            manageQuery.setId(query.getId());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = officeAuditService.getProjectOfficeAudit(manageQuery);
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                office.setId(manageRst.getList().get(0).getId()); 
            }
        } 
        int cnt = officeAuditMapper.updateOfficeAuditById(office);
        if(cnt > 0){
            if(ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())){
                if(Util.isNotNull(query.getStep())){
                    if (GardenProjectStepEnum.P_220.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_230.getValue());
                        // 新增数据至行政盖章表
                        AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                        administrativeSeal.setCompanyId(query.getCompanyId());
                        administrativeSeal.setProjectId(query.getProjectId());
                        administrativeSeal.setType(34);
                        administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        administrativeSeal.setCreateTime(new Date());
                        administrativeSeal.setCreateUserId(query.getUpdateUserId());
                        result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("院办审核商务标时,行政盖章表插入失败");
                        }
                    } else if (GardenProjectStepEnum.P_350.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_360.getValue());
                        // 新增数据至行政盖章表
                        AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                        administrativeSeal.setCompanyId(query.getCompanyId());
                        administrativeSeal.setProjectId(query.getProjectId());
                        administrativeSeal.setType(36);
                        administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        administrativeSeal.setCreateTime(new Date());
                        administrativeSeal.setCreateUserId(query.getUpdateUserId());
                        result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("院办审核合同时,行政盖章表插入失败");
                        }
                    } else if (GardenProjectStepEnum.P_390.getValue().equals(query.getStep())) {
                        if(GenerateConstants.number_1.equals(manageRst.getList().get(0).getGardenProject().getHaveScheme())){
                            project.setStep(GardenProjectStepEnum.P_400.getValue()); 
                        }else if(GenerateConstants.number_1.equals(manageRst.getList().get(0).getGardenProject().getHaveDevelopment())){
                            project.setStep(GardenProjectStepEnum.P_430.getValue()); 
                        }else if(GenerateConstants.number_1.equals(manageRst.getList().get(0).getGardenProject().getHaveDrawing())){
                            project.setStep(GardenProjectStepEnum.P_460.getValue()); 
                        }else if(GenerateConstants.number_1.equals(manageRst.getList().get(0).getGardenProject().getHavePlanning())){
                            project.setStep(GardenProjectStepEnum.P_490.getValue()); 
                        }else{
                            project.setStep(GardenProjectStepEnum.P_400.getValue()); 
                        }                       
//                        // 新增数据至财务审核表
//                        FinancialAudit audit = new FinancialAudit();
//                        audit.setCompanyId(query.getCompanyId());
//                        audit.setProjectId(query.getProjectId());
//                        audit.setApplicant(manageRst.getList().get(0).getApplicant());
//                        audit.setType(29);
//                        audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
//                        audit.setCreateTime(new Date());
//                        audit.setCreateUserId(query.getUpdateUserId());
//                        result = financialAuditService.insertFinancialAudit(audit);
//                        if (!result.isSuccess()) {
//                            throw new ApplicationException("院办审核合同时,财务审核表插入失败");
//                        }                        
                        //项目id 公司 id 查询 分包数据
                        ContractSubpackageQuery contractSubpackageQuery = new ContractSubpackageQuery();
                        contractSubpackageQuery.setCompanyId(query.getCompanyId());
                        contractSubpackageQuery.setProjectId(query.getProjectId());
                        contractSubpackageQuery.setVerifyStatus(ProjectSubpackageStatusEnum.NOT_VERIFY.getValue());
                        ResultRecord<ContractSubpackage> packageRst = contractSubpackageService.getContractSubpackageByCondition(contractSubpackageQuery);
                        if(packageRst.isSuccess()){
                            for(int j=0;j<packageRst.getList().size();j++){
                               //修改分包表状态
                                ContractSubpackage contractSubpackage = new ContractSubpackage();
                                contractSubpackage.setId(packageRst.getList().get(j).getId());
                                contractSubpackage.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                                int count = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage);
                                if (count<=0) {
                                    throw new ApplicationException("院办审核分包通过时,分包表更新失败");
                                } 
                            }
                            
                            //查询状态为0的合同数据
                            ContractQuery contractQuery = new ContractQuery();
                            contractQuery.setProjectId(query.getProjectId());
                            contractQuery.setStatus(GenerateConstants.number_0);
                            contractQuery.setCompanyId(query.getCompanyId());
                            List<ProjectContract> contractLst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                            if(!(contractLst==null || contractLst.size()==0)){
                                //删除临时数据
                                int count = projectContractMapper.deleteProjectContractById(contractLst.get(0).getId());
                                if (count <= 0) {
                                    throw new ApplicationException("总工审核合作比例时,删除临时合同失败");
                                }
                                //修改未审核状态为审核通过
                                ProjectContract record = new ProjectContract();
                                record.setProjectId(query.getProjectId());
                                record.setCompanyId(query.getCompanyId());
                                record.setStatus(GenerateConstants.number_1);
                                record.setRatio(contractLst.get(0).getRatio());
                                record.setRatioMemo(contractLst.get(0).getRatioMemo());
                                count = projectContractMapper.updateProjectContractByProjectId(record);
                                if (count <= 0) {
                                    throw new ApplicationException("总工审核合作比例时,更新合同表失败");
                                }
                            }
                        }                       
                    }else if(56==query.getType()){
                      //查询状态为0的合同数据
                        ContractQuery contractQuery = new ContractQuery();
                        contractQuery.setProjectId(query.getProjectId());
                        contractQuery.setStatus(GenerateConstants.number_0);
                        contractQuery.setCompanyId(query.getCompanyId());
                        List<ProjectContract> contractLst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                        if(!(contractLst==null || contractLst.size()==0)){
                            //删除临时数据
                            int count = projectContractMapper.deleteProjectContractById(contractLst.get(0).getId());
                            if (count <= 0) {
                                throw new ApplicationException("总工审核合作比例时,删除临时合同失败");
                            }
                            //修改未审核状态为审核通过
                            ProjectContract record = new ProjectContract();
                            record.setProjectId(query.getProjectId());
                            record.setCompanyId(query.getCompanyId());
                            record.setStatus(GenerateConstants.number_1);
                            record.setRatio(contractLst.get(0).getRatio());
                            record.setRatioMemo(contractLst.get(0).getRatioMemo());
                            count = projectContractMapper.updateProjectContractByProjectId(record);
                            if (count <= 0) {
                                throw new ApplicationException("总工审核合作比例时,更新合同表失败");
                            }
                        }                       
                        //查询此项目分包状态
                        ContractSubpackageQuery subPackageQuery = new ContractSubpackageQuery();
                        subPackageQuery.setProjectId(query.getProjectId());
                        subPackageQuery.setCompanyId(query.getCompanyId());
                        subPackageQuery.setStatus(GenerateConstants.number_2);
                        ResultRecord<ContractSubpackageInfo> results =  contractSubpackageService.getContractSubpackageInfo(subPackageQuery);
                        if(results.isSuccess()){
                            for (int i=0;i<results.getList().size();i++){
                                if(ProjectSubpackageStatusEnum.INSERT.getValue().equals(results.getList().get(i).getStatus())){
                                    //状态设为审核通过
                                    ContractSubpackage  contractSubpackage = new ContractSubpackage();
                                    contractSubpackage.setId(results.getList().get(i).getId());
                                    contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED.getValue());
                                    int total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage); 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                    }
                                }else if(ProjectSubpackageStatusEnum.DELETE.getValue().equals(results.getList().get(i).getStatus())){
                                    //删除数据
                                    ContractQuery contractQuerys = new ContractQuery();
                                    contractQuerys.setId(results.getList().get(i).getId());
                                    int total = contractSubpackageMapper.deleteSubpackageByCondition(contractQuerys) ; 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,删除分包表失败"); 
                                    }
                                }else if(ProjectSubpackageStatusEnum.UPDATE.getValue().equals(results.getList().get(i).getStatus())){
                                    //更新数据，将id为temporaryId的数据修改为审核通过，将此id数据设为审核不通过                                    
                                    ContractSubpackage  contractSubpackage = new ContractSubpackage();
                                    contractSubpackage.setId(results.getList().get(i).getId());
                                    contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED.getValue());
                                    int total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage); 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                    }else{
                                        contractSubpackage = new ContractSubpackage();
                                        contractSubpackage.setSubTemporaryId(results.getList().get(i).getId());
                                        contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED_FAIL.getValue());
                                        total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage);
                                        if(total<=0){
                                            throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                        }
                                    }                                   
                                }
                            }
                            return result;
                        }
                    }
                }else if(Util.isNotNull(query.getSubStep())){
                    if (GardenProjectStepEnum.P_160.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_170.getValue());
                        // 新增数据至出纳确认表
                        CashierConfirmation cashierConfirmation = new CashierConfirmation();
                        cashierConfirmation.setCompanyId(query.getCompanyId());
                        cashierConfirmation.setProjectId(query.getProjectId());
                        cashierConfirmation.setType(30);
                        cashierConfirmation.setAmount(manageRst.getList().get(0).getAmount());
                        cashierConfirmation.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        cashierConfirmation.setCreateTime(new Date());
                        cashierConfirmation.setCreateUserId(query.getUpdateUserId());
                        result = cashierConfirmationService.insertCashierConfirmation(cashierConfirmation);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("院办审核时,出纳确认表插入失败");
                        }
                    } 
                } else if(Util.isNotNull(query.getSubStep2())){
                    if (GardenProjectStepEnum.P_260.getValue().equals(query.getSubStep2())) {
                        project.setSubStep2(GardenProjectStepEnum.P_270.getValue());
                        // 新增数据至行政盖章表
                        AdministrativeSeal administrativeSeal = new AdministrativeSeal();
                        administrativeSeal.setCompanyId(query.getCompanyId());
                        administrativeSeal.setProjectId(query.getProjectId());
                        administrativeSeal.setType(35);
                        administrativeSeal.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        administrativeSeal.setCreateTime(new Date());
                        administrativeSeal.setCreateUserId(query.getUpdateUserId());
                        result = administrativeSealService.insertAdministrativeSeal(administrativeSeal);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("院办审核技术标时,行政盖章表插入失败");
                        }
                    }
                }else if(query.getType()==84){
                    //新增数据至出纳确认表
                    CashierConfirmation  cashierConfirmation = new CashierConfirmation();
                    cashierConfirmation.setCompanyId(query.getCompanyId());
                    cashierConfirmation.setProjectId(query.getProjectId());
                    cashierConfirmation.setAmount(manageRst.getList().get(0).getAmount());
                    cashierConfirmation.setPaymentMode(manageRst.getList().get(0).getPaymentMode());
                    cashierConfirmation.setType(31);
                    cashierConfirmation.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    cashierConfirmation.setCreateTime(new Date());
                    cashierConfirmation.setCreateUserId(query.getUpdateUserId());
                    result = cashierConfirmationService.insertCashierConfirmation(cashierConfirmation);
                    if(!result.isSuccess()){
                        throw new ApplicationException("院办审核项目支出时,出纳确认表插入失败");  
                    } 
                }
            }else{                
                if(Util.isNotNull(query.getStep())){
                    if(GardenProjectStepEnum.P_220.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_190.getValue());
                    }else if(GardenProjectStepEnum.P_260.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_240.getValue());
                    }else if(GardenProjectStepEnum.P_350.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_300.getValue());            
                    }else if(GardenProjectStepEnum.P_390.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_380.getValue());
                        //项目id 公司 id 查询 分包数据
                        ContractSubpackageQuery contractSubpackageQuery = new ContractSubpackageQuery();
                        contractSubpackageQuery.setCompanyId(query.getCompanyId());
                        contractSubpackageQuery.setProjectId(query.getProjectId());
                        ResultRecord<ContractSubpackage> packageRst = contractSubpackageService.getContractSubpackageByCondition(contractSubpackageQuery);
                        if(packageRst.isSuccess()){
                            //修改分包表状态
                            ContractSubpackage contractSubpackage = new ContractSubpackage();
                            contractSubpackage.setId(packageRst.getList().get(0).getId());
                            contractSubpackage.setStatus(ProjectTableStatusEnum.VERIFIED_FAIL.getValue());
                            List<SubpackagePayment> payLst = new ArrayList<SubpackagePayment>();
                            result = contractSubpackageService.updateContractSubpackageById(contractSubpackage,payLst);
                            if (!result.isSuccess()) {
                                throw new ApplicationException("院办审核分包不通过时,分包表更新失败");
                            } 
                            //查询状态为0的合同数据
                            ContractQuery contractQuery = new ContractQuery();
                            contractQuery.setProjectId(query.getProjectId());
                            contractQuery.setStatus(GenerateConstants.number_0);
                            contractQuery.setCompanyId(query.getCompanyId());
                            List<ProjectContract> contractLst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                            if(!(contractLst==null || contractLst.size()==0)){
                                //删除临时数据
                                int count = projectContractMapper.deleteProjectContractById(contractLst.get(0).getId());
                                if (count <= 0) {
                                    throw new ApplicationException("总工审核合作比例时,删除临时合同失败");
                                }
                            }
                        } 
                    }else if(56==query.getType()){
                      //查询状态为0的合同数据
                        ContractQuery contractQuery = new ContractQuery();
                        contractQuery.setProjectId(query.getProjectId());
                        contractQuery.setStatus(GenerateConstants.number_0);
                        contractQuery.setCompanyId(query.getCompanyId());
                        List<ProjectContract> contractLst = projectContractMapper.getProjectContractByProjectIdAndStatus(contractQuery);
                        if(!(contractLst==null || contractLst.size()==0)){
                            //删除临时数据
                            int count = projectContractMapper.deleteProjectContractById(contractLst.get(0).getId());
                            if (count <= 0) {
                                throw new ApplicationException("总工审核合作比例时,删除临时合同失败");
                            }
                        }                       
                        //查询此项目分包状态
                        ContractSubpackageQuery subPackageQuery = new ContractSubpackageQuery();
                        subPackageQuery.setProjectId(query.getProjectId());
                        subPackageQuery.setCompanyId(query.getCompanyId());
                        subPackageQuery.setStatus(GenerateConstants.number_2);
                        ResultRecord<ContractSubpackageInfo> results =  contractSubpackageService.getContractSubpackageInfo(subPackageQuery);
                        if(results.isSuccess()){
                            ContractSubpackage  contractSubpackage = new ContractSubpackage();
                            for (int i=0;i<results.getList().size();i++){
                                if(ProjectSubpackageStatusEnum.INSERT.getValue().equals(results.getList().get(i).getStatus())){
                                    //状态设为审核不通过                                  
                                    contractSubpackage.setId(results.getList().get(i).getId());
                                    contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED_FAIL.getValue());
                                    int total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage); 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                    }
                                }else if(ProjectSubpackageStatusEnum.DELETE.getValue().equals(results.getList().get(i).getStatus())){
                                    //状态设为审核通过  
                                    contractSubpackage.setId(results.getList().get(i).getId());
                                    contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED.getValue());
                                    int total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage); 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                    }
                                }else if(ProjectSubpackageStatusEnum.UPDATE.getValue().equals(results.getList().get(i).getStatus())){
                                    //更新数据，将id为temporaryId的数据修改为审核不通过                                   
                                    contractSubpackage.setId(results.getList().get(i).getTemporaryId());
                                    contractSubpackage.setStatus(ProjectSubpackageStatusEnum.VERIFIED_FAIL.getValue());
                                    int total = contractSubpackageMapper.updateContractSubpackageById(contractSubpackage); 
                                    if(total<=0){
                                        throw new ApplicationException("总工审核合作比例时,更新分包表失败"); 
                                    }                                 
                                }
                            }
                            return result;
                        }
                    }
                } else if(Util.isNotNull(query.getSubStep())){
                    if (GardenProjectStepEnum.P_160.getValue().equals(query.getSubStep())) {
                        project.setSubStep(GardenProjectStepEnum.P_120.getValue());
                    } 
                }else if(Util.isNotNull(query.getSubStep2())){
                    if (GardenProjectStepEnum.P_260.getValue().equals(query.getSubStep2())) {
                        project.setSubStep2(GardenProjectStepEnum.P_240.getValue());
                    }
                }
            } 
            // 更新项目进度
            project.setId(query.getProjectId());
            project.setUpdateUserId(query.getUpdateUserId());
            project.setUpdateTime(new Date());
            result = gardenProjectService.updateGardenProjectById(project);
            if (!result.isSuccess()) {
                throw new ApplicationException("院办审核时,项目进度更新失败");
            }
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":院办审核更新失败");  
        }
        return result;
    }

}
