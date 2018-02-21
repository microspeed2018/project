package com.zjzmjr.core.service.business.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectSubpackageStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.mapper.dao.project.ContractSubpackageMapper;
import com.zjzmjr.core.service.mapper.dao.project.SubpackagePaymentMapper;

/**
 * 项目分包表的业务处理层
 * 
 * @author oms
 * @version $Id: ContractSubpackageServiceImpl.java, v 0.1 2017-9-3 下午5:05:20
 *          oms Exp $
 */
@Service("contractSubpackageService")
public class ContractSubpackageServiceImpl implements ContractSubpackageService {

    private static final Logger logger = LoggerFactory.getLogger(ContractSubpackageServiceImpl.class);

    @Resource(name = "contractSubpackageMapper")
    private ContractSubpackageMapper contractSubpackageMapper;
    
    @Resource(name = "subpackagePaymentMapper")
    private SubpackagePaymentMapper  subpackagePaymentMapper;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService  officeAuditService;

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.ContractSubpackageService#insertContractSubpackage(com.zjzmjr.core.model.project.ContractSubpackage)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertContractSubpackage(ContractSubpackage record,List<SubpackagePayment> payLst) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        if(GardenProjectStepEnum.P_380.getValue().equals(record.getStep())){
            int total = contractSubpackageMapper.insertContractSubpackage(record);
            if (total > 0) {
                //插入分包付款数据
                if(Util.isNotNull(payLst) && payLst.size()>0){
                    SubpackagePayment payment = null;
                    for(int i=0;i<payLst.size();i++){
                        payment = payLst.get(i);
                        payment.setCompanyId(record.getCompanyId());
                        payment.setSubpackageId(record.getId());
                        payment.setCreateTime(record.getUpdateTime());
                        payment.setCreateUserId(record.getUpdateUserId());
                        payment.setUpdateTime(record.getUpdateTime());
                        payment.setUpdateUserId(record.getUpdateUserId());
                        total = subpackagePaymentMapper.insertSubpackagePayment(payment);
                        if(total<=0){
                            throw new ApplicationException("修改分包时，插入分包付款表新记录失败");   
                        }
                    }
                }
                result.setSuccess(true);
                result.setT(total);
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表插入失败");
            }
        }else{
            if(Util.isNull(record.getId())){
                //步骤之外录入分包信息 
                record.setStatus(ProjectSubpackageStatusEnum.INSERT.getValue());
                int total = contractSubpackageMapper.insertContractSubpackage(record);
                if (total > 0) {
                    //插入分包付款数据
                    if(Util.isNotNull(payLst) && payLst.size()>0){
                        SubpackagePayment payment = null;
                        for(int i=0;i<payLst.size();i++){
                            payment = payLst.get(i);
                            payment.setCompanyId(record.getCompanyId());
                            payment.setSubpackageId(record.getId());
                            payment.setCreateTime(record.getUpdateTime());
                            payment.setCreateUserId(record.getUpdateUserId());
                            payment.setUpdateTime(record.getUpdateTime());
                            payment.setUpdateUserId(record.getUpdateUserId());
                            total = subpackagePaymentMapper.insertSubpackagePayment(payment);
                            if(total<=0){
                                throw new ApplicationException("修改分包时，插入分包付款表新记录失败");   
                            }
                        }
                    }
                    //插入院办审核表 1.先查询是否已经存在审核数据，没有就新增
                    GardenProjectAuditQuery query = new GardenProjectAuditQuery();
                    query.setType(56);
                    query.setProjectId(record.getProjectId());
                    query.setCompanyId(record.getCompanyId());
                    query.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    ResultRecord<OfficeAudit> officeRst = officeAuditService.getProjectOfficeAuditByCondition(query);
                    if(!officeRst.isSuccess()){
                       //插入一条新的审核合作比例记录
                        OfficeAudit audit = new OfficeAudit();
                        audit.setProjectId(record.getProjectId());
                        audit.setType(56);
                        audit.setCompanyId(record.getCompanyId());
                        audit.setApplicant(record.getUpdateUserId());
                        audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        audit.setCreateTime(record.getUpdateTime());
                        audit.setCreateUserId(record.getUpdateUserId());
                        result = officeAuditService.insertOfficeAudit(audit);
                        if(!result.isSuccess()){
                            throw new ApplicationException("修改分包时，插入院办审核失败");   
                        }
                    }
                    result.setSuccess(true);
                    result.setT(total);
                } else {
                    result.setSuccess(false);
                    result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                    logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表插入失败");
                }
            }else{
                record.setStatus(ProjectSubpackageStatusEnum.UPDATE.getValue());
                int temporarySaveId = record.getId();
                record.setId(null);
                //查询此分包id数据temporaryId是否有值，有值更新id为temporaryId的数据
                ContractSubpackageQuery packageQuery = new ContractSubpackageQuery();
                packageQuery.setId(temporarySaveId);
                List<ContractSubpackage> list= contractSubpackageMapper.getContractSubpackageByCondition(packageQuery);
                if(!(list==null || list.size()==0)){
                    if(ProjectSubpackageStatusEnum.UPDATE.getValue().equals(list.get(0).getStatus())){
                        //有临时数据，直接更新临时数据
                        record.setId(temporarySaveId);
                        int cnt = contractSubpackageMapper.updateContractSubpackageById(record);
                        if(cnt<=0){
                            throw new ApplicationException("修改分包时，修改临时数据失败");
                        }else{
                            //先删除临时id的分包付款数据
                            ContractSubpackageQuery contractSubpackageQuery = new ContractSubpackageQuery();
                            contractSubpackageQuery.setSubpackageId(list.get(0).getTemporaryId());
                            int total = subpackagePaymentMapper.deleteSubpackagePaymentByCondition(contractSubpackageQuery);
                            if(total<=0){
                                throw new ApplicationException("修改分包时，删除分包付款表原记录失败");   
                            }
                            //插入分包付款数据
                            if(Util.isNotNull(payLst) && payLst.size()>0){
                                SubpackagePayment payment = null;
                                for(int i=0;i<payLst.size();i++){
                                    payment = payLst.get(i);
                                    payment.setCompanyId(record.getCompanyId());
                                    payment.setSubpackageId(record.getId());
                                    payment.setCreateTime(record.getUpdateTime());
                                    payment.setCreateUserId(record.getUpdateUserId());
                                    payment.setUpdateTime(record.getUpdateTime());
                                    payment.setUpdateUserId(record.getUpdateUserId());
                                    total = subpackagePaymentMapper.insertSubpackagePayment(payment);
                                    if(total<=0){
                                        throw new ApplicationException("修改分包时，插入分包付款表新记录失败");   
                                    }
                                }
                            }
                            result.setSuccess(true);
                            result.setT(cnt);
                        }
                    }else{
                        int total = contractSubpackageMapper.insertContractSubpackage(record);
                        if (total > 0) {
                            //将刚生成临时id存入原数据
                            ContractSubpackage subpackage = new ContractSubpackage();
                            subpackage.setId(temporarySaveId);
                            subpackage.setTemporaryId(record.getId());
                            subpackage.setUpdateTime(record.getUpdateTime());
                            subpackage.setUpdateUserId(record.getUpdateUserId());
                            total = contractSubpackageMapper.updateContractSubpackageById(subpackage);
                            if(total<=0){
                                throw new ApplicationException("修改分包时，原始数据插入临时数据id失败");
                            }
                            //插入分包付款数据
                            if(Util.isNotNull(payLst) && payLst.size()>0){
                                SubpackagePayment payment = null;
                                for(int i=0;i<payLst.size();i++){
                                    payment = payLst.get(i);
                                    payment.setCompanyId(record.getCompanyId());
                                    payment.setSubpackageId(record.getId());
                                    payment.setCreateTime(record.getUpdateTime());
                                    payment.setCreateUserId(record.getUpdateUserId());
                                    payment.setUpdateTime(record.getUpdateTime());
                                    payment.setUpdateUserId(record.getUpdateUserId());
                                    total = subpackagePaymentMapper.insertSubpackagePayment(payment);
                                    if(total<=0){
                                        throw new ApplicationException("修改分包时，插入分包付款表新记录失败");   
                                    }
                                }
                            }
                            //插入院办审核表 1.先查询是否已经存在审核数据，没有就新增
                            GardenProjectAuditQuery query = new GardenProjectAuditQuery();
                            query.setType(56);
                            query.setProjectId(record.getProjectId());
                            query.setCompanyId(record.getCompanyId());
                            query.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            ResultRecord<OfficeAudit> officeRst = officeAuditService.getProjectOfficeAuditByCondition(query);
                            if(!officeRst.isSuccess()){
                               //插入一条新的审核合作比例记录
                                OfficeAudit audit = new OfficeAudit();
                                audit.setProjectId(record.getProjectId());
                                audit.setCompanyId(record.getCompanyId());
                                audit.setType(56);
                                audit.setApplicant(record.getUpdateUserId());
                                audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                                audit.setCreateTime(record.getUpdateTime());
                                audit.setCreateUserId(record.getUpdateUserId());
                                result = officeAuditService.insertOfficeAudit(audit);
                                if(!result.isSuccess()){
                                    throw new ApplicationException("修改分包时，插入院办审核失败");   
                                }
                            }
                            result.setSuccess(true);
                            result.setT(total);
                        } else {
                            result.setSuccess(false);
                            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表插入失败");
                        }
                    }                    
                }                
            }
             
        }                
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ContractSubpackageService#getContractSubpackageByCondition(com.zjzmjr.core.model.project.ContractSubpackageQuery)
     */
    @Override
    public ResultRecord<ContractSubpackage> getContractSubpackageByCondition(ContractSubpackageQuery query) {
        ResultRecord<ContractSubpackage> result = new ResultRecord<ContractSubpackage>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<ContractSubpackage> list= contractSubpackageMapper.getContractSubpackageByCondition(query);
        if (list==null || list.size()==0) {
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.ContractSubpackageService#updateContractSubpackageById(com.zjzmjr.core.model.project.ContractSubpackage)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateContractSubpackageById(ContractSubpackage record,List<SubpackagePayment> payLst) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = contractSubpackageMapper.updateContractSubpackageById(record);
        if (total > 0) {
            //修改分包成功之后修改分包付款表
            //先删除分包付款表此条数据原有的信息
            ContractSubpackageQuery packageQuery = new ContractSubpackageQuery();
            packageQuery.setSubpackageId(record.getId());
            total = subpackagePaymentMapper.deleteSubpackagePaymentByCondition(packageQuery);
            if(total>0){
                //插入修改数据
                if(Util.isNotNull(payLst) && payLst.size()>0){
                    SubpackagePayment payment = null;
                    for(int i=0;i<payLst.size();i++){
                        payment = payLst.get(i);
                        payment.setCompanyId(record.getCompanyId());
                        payment.setSubpackageId(record.getId());
                        payment.setCreateTime(record.getUpdateTime());
                        payment.setCreateUserId(record.getUpdateUserId());
                        payment.setUpdateTime(record.getUpdateTime());
                        payment.setUpdateUserId(record.getUpdateUserId());
                        total = subpackagePaymentMapper.insertSubpackagePayment(payment);
                        if(total<=0){
                            throw new ApplicationException("修改分包时，插入分包付款表新记录失败");   
                        }
                    }  
                }                
            }else{
                throw new ApplicationException("修改分包时，删除分包付款表原有记录失败"); 
            }
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表更新失败");
        }
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.ContractSubpackageService#deleteSubpackageByCondition(com.zjzmjr.core.model.project.ContractQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteSubpackageByCondition(ContractQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        if(GardenProjectStepEnum.P_390.getValue()<query.getStep()){
            //审核完之后的删除,在原数据标明操作类型
            ContractSubpackage subpackage = new ContractSubpackage();
            subpackage.setStatus(ProjectSubpackageStatusEnum.DELETE.getValue());
            subpackage.setStep(query.getStep());
            subpackage.setId(query.getId());
            subpackage.setUpdateTime(query.getUpdateTime());
            subpackage.setUpdateUserId(query.getUpdateUserId());
            int total = contractSubpackageMapper.updateContractSubpackageById(subpackage);
            if(total > 0) {
              //插入院办审核表 1.先查询是否已经存在审核数据，没有就新增
                GardenProjectAuditQuery auditQuery = new GardenProjectAuditQuery();
                auditQuery.setType(56);
                auditQuery.setProjectId(query.getProjectId());
                auditQuery.setCompanyId(query.getCompanyId());
                auditQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                ResultRecord<OfficeAudit> officeRst = officeAuditService.getProjectOfficeAuditByCondition(auditQuery);
                if(!officeRst.isSuccess()){
                   //插入一条新的审核合作比例记录
                    OfficeAudit audit = new OfficeAudit();
                    audit.setProjectId(query.getProjectId());
                    audit.setCompanyId(query.getCompanyId());
                    audit.setApplicant(query.getUpdateUserId());
                    audit.setType(56);
                    audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    audit.setCreateTime(query.getUpdateTime());
                    audit.setCreateUserId(query.getUpdateUserId());
                    result = officeAuditService.insertOfficeAudit(audit);
                    if(!result.isSuccess()){
                        throw new ApplicationException("修改分包时，插入院办审核失败");   
                    }
                }
                result.setSuccess(true);
                result.setT(total);  
            }else{
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表删除修改状态失败"); 
            }
        }else{
            int total = contractSubpackageMapper.deleteSubpackageByCondition(query);
            if (total > 0) {
                //同时删除分包付款表此条数据的信息
                ContractSubpackageQuery packageQuery = new ContractSubpackageQuery();
                packageQuery.setSubpackageId(query.getId());
                total = subpackagePaymentMapper.deleteSubpackagePaymentByCondition(packageQuery);
                if(total>0){
                    result.setSuccess(true);
                    result.setT(total);  
                }else{
                    throw new ApplicationException("删除分包时，删除分包付款表失败"); 
                }            
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目分包表删除失败");
            }
        }        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.ContractSubpackageService#getContractSubpackageInfo(com.zjzmjr.core.model.project.ContractSubpackageQuery)
     */
    @Override
    public ResultRecord<ContractSubpackageInfo> getContractSubpackageInfo(ContractSubpackageQuery query) {
        ResultRecord<ContractSubpackageInfo> result = new ResultRecord<ContractSubpackageInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        List<ContractSubpackageInfo> list = contractSubpackageMapper.getContractSubpackageInfo(query);
        if(list==null || list.size()==0){
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

}
