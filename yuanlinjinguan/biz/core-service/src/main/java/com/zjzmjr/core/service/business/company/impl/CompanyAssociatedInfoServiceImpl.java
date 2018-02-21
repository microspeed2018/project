package com.zjzmjr.core.service.business.company.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.company.CompanyAssociatedContact;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService;
import com.zjzmjr.core.service.mapper.dao.company.CompanyAssociatedContactMapper;
import com.zjzmjr.core.service.mapper.dao.company.CompanyAssociatedMapper;

/**
 * 关联公司service实现
 * 
 * @author lenovo
 * @version $Id: CompanyAssociatedInfoServiceImpl.java, v 0.1 2017-8-23 下午10:00:59 lenovo Exp $
 */
@Service("companyAssociatedInfoService")
public class CompanyAssociatedInfoServiceImpl implements CompanyAssociatedInfoService{

    private static final Logger logger = LoggerFactory.getLogger(CompanyAssociatedInfoServiceImpl.class);

    @Resource(name = "companyAssociatedMapper")
    private CompanyAssociatedMapper  companyAssociatedMapper;
    
    @Resource(name = "companyAssociatedContactMapper")
    private CompanyAssociatedContactMapper  companyAssociatedContactMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService#getCompanyAssociatedInfo(com.zjzmjr.core.model.company.CompanyAssociatedQuery)
     */
    @Override
    public ResultPage<CompanyAssociatedInfo> getCompanyAssociatedInfo(CompanyAssociatedQuery query) {
        ResultPage<CompanyAssociatedInfo> result = new ResultPage<CompanyAssociatedInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyAssociatedMapper.getCompanyAssociatedInfoCount(query);
        if(total > 0){
            List<CompanyAssociatedInfo> list = companyAssociatedMapper.getCompanyAssociatedInfo(query);
            result.setSuccess(true);
            result.setList(list);
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService#insertCompanyAssociated(com.zjzmjr.core.model.company.CompanyAssociated)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertCompanyAssociated(CompanyAssociatedInfo companyAssociatedInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(companyAssociatedInfo) || Util.isNull(companyAssociatedInfo.getCompanyName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyAssociatedMapper.insertCompanyAssociated(companyAssociatedInfo);
        if(total > 0){
            CompanyAssociatedContact companyAssociatedContact = null;
            if(companyAssociatedInfo.getCompanyAssociatedContact()!=null && companyAssociatedInfo.getCompanyAssociatedContact().size()>0){
            	 for(int i=0;i<companyAssociatedInfo.getCompanyAssociatedContact().size();i++){
                     companyAssociatedContact = companyAssociatedInfo.getCompanyAssociatedContact().get(i);
                     companyAssociatedContact.setCompanyId(companyAssociatedInfo.getCompanyId());
                     companyAssociatedContact.setAssociateCompany(companyAssociatedInfo.getId());
                     companyAssociatedContact.setCreateTime(new Date());
                     companyAssociatedContact.setCreateUserId(companyAssociatedInfo.getCreateUserId());
                     companyAssociatedContact.setUpdateTime(new Date());
                     companyAssociatedContact.setUpdateUserId(companyAssociatedInfo.getCreateUserId());
                     int contactTotal = companyAssociatedContactMapper.insertCompanyAssociatedContact(companyAssociatedContact);
                     if(contactTotal <= 0){
                         throw new ApplicationException("关联公司联系人插入失败");
                     }
                 }	
            } 
            result.setSuccess(true);
            result.setT(companyAssociatedInfo.getId());
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":关联公司表插入失败");
        }
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService#updateCompanyAssociatedById(com.zjzmjr.core.model.company.CompanyAssociated)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateCompanyAssociatedById(CompanyAssociatedInfo companyAssociatedInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(companyAssociatedInfo) || Util.isNull(companyAssociatedInfo.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyAssociatedMapper.updateCompanyAssociatedById(companyAssociatedInfo);
        if(total > 0){
            List<CompanyAssociatedContact> list = companyAssociatedContactMapper.getCompanyAssociatedContactByAssociateCompany(companyAssociatedInfo.getId());
            if(list==null || list.size()==0){
                CompanyAssociatedContact companyAssociatedContact = null;
                if(companyAssociatedInfo.getCompanyAssociatedContact()!=null && companyAssociatedInfo.getCompanyAssociatedContact().size()>0){
                	for(int i=0;i<companyAssociatedInfo.getCompanyAssociatedContact().size();i++){
                        companyAssociatedContact = companyAssociatedInfo.getCompanyAssociatedContact().get(i);
                        companyAssociatedContact.setCompanyId(companyAssociatedInfo.getCompanyId());
                        companyAssociatedContact.setAssociateCompany(companyAssociatedInfo.getId());
                        companyAssociatedContact.setCreateTime(new Date());
                        companyAssociatedContact.setCreateUserId(companyAssociatedInfo.getUpdateUserId());
                        companyAssociatedContact.setUpdateTime(new Date());
                        companyAssociatedContact.setUpdateUserId(companyAssociatedInfo.getUpdateUserId());
                        int contactTotal = companyAssociatedContactMapper.insertCompanyAssociatedContact(companyAssociatedContact);
                        if(contactTotal <= 0){
                            throw new ApplicationException("关联公司联系人更新失败");
                        }
                    }        
                }                        
                result.setSuccess(true);
                result.setT(total); 
            }else{              
                total = companyAssociatedContactMapper.deleteByCompanyId(companyAssociatedInfo.getId());
                if(total > 0){
                    CompanyAssociatedContact companyAssociatedContact = null;
                    for(int i=0;i<companyAssociatedInfo.getCompanyAssociatedContact().size();i++){
                        companyAssociatedContact = companyAssociatedInfo.getCompanyAssociatedContact().get(i);
                        companyAssociatedContact.setCompanyId(companyAssociatedInfo.getCompanyId());
                        companyAssociatedContact.setAssociateCompany(companyAssociatedInfo.getId());
                        companyAssociatedContact.setCreateTime(new Date());
                        companyAssociatedContact.setCreateUserId(companyAssociatedInfo.getUpdateUserId());
                        companyAssociatedContact.setUpdateTime(new Date());
                        companyAssociatedContact.setUpdateUserId(companyAssociatedInfo.getUpdateUserId());
                        int contactTotal = companyAssociatedContactMapper.insertCompanyAssociatedContact(companyAssociatedContact);
                        if(contactTotal <= 0){
                            throw new ApplicationException("关联公司联系人更新失败");
                        }
                    }                
                    result.setSuccess(true);
                    result.setT(total);
                }else{
                    result.setSuccess(false);
                    result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                    logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":关联公司表更新失败"); 
                } 
            }            
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":关联公司表更新失败");
        }
        return result;
    }

}
