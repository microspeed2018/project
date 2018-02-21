package com.zjzmjr.core.service.business.transaction.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;
import com.zjzmjr.core.service.business.transaction.TransactionService;
import com.zjzmjr.core.service.mapper.dao.coredb.transaction.BusinessMapper;

@Service("transactionService")
public class TransactionServiceImpl implements TransactionService{
    
    private static final Logger logger = LoggerFactory.getLogger(TransactionServiceImpl.class);

    
    @Resource(name = "businessMapper")
    private BusinessMapper businessMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#insertTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Business> insertTransaction(Business business) {
        logger.debug("insertTransaction入参:{}",business);
        ResultEntry<Business> result = new ResultEntry<Business>();
        if(Util.isNull(business) || Util.isNull(business.getBusinessType())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),business.toString());
            logger.debug("insertTransaction出参:{}",result);
            return result;
        }
        logger.info("新增事物一览参数：{}", business.toString());
        int total = businessMapper.insertTransaction(business);
        if(total > 0){
            result.setSuccess(true);
            result.setT(business);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":新增事物失败");
        }
        logger.debug("insertTransaction出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#updateTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Integer> updateTransaction(Business business) {
        logger.debug("updateTransaction入参:{}",business);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(business) || Util.isNull(business.getResult())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),business.toString());
            logger.debug("updateTransaction出参:{}",result);
            return result;
        }
        logger.info("更新事物一览参数：{}", business.toString());
        int total = businessMapper.updateTransaction(business);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新事物失败");
        }
        logger.debug("updateTransaction出参:{}",result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.transaction.TransactionService#updateTransactionByExtend3(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Integer> updateTransactionByExtend3(Business business) {
        logger.debug("updateTransactionByExtend3入参:{}",business);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(business) || Util.isNull(business.getResult()) || Util.isNull(business.getExtend3())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),business.toString());
            logger.debug("updateTransactionByExtend3出参:{}",result);
            return result;
        }
        logger.info("更新事物一览参数：{}", business.toString());
        int total = businessMapper.updateTransactionByExtend3(business);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新事物失败");
        }
        logger.debug("updateTransactionByExtend3出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    @Transactional
    public ResultEntry<Business> getTransaction(Business business) {
        logger.debug("getTransaction入参:{}",business);
        ResultEntry<Business> result = new ResultEntry<Business>();
        Business val = businessMapper.getTransaction(business);
        if(!Util.isNull(business)){
            result.setSuccess(true);
            result.setT(val);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }        
        logger.debug("getTransaction出参:{}",result);
        return result;
    }

    @Override
    public ResultPage<Business> getRechargePostalList(BusinessQuery query) {
        logger.debug("getRechargePostalList入参:{}",query);
        ResultPage<Business> result = new ResultPage<Business>();
        if(Util.isNull(query) || Util.isNull(query.getUserId()) || 
                Util.isNull(query.getBusinessType())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),query.toString());
            logger.debug("getRechargePostalList出参:{}",result);
            return result;
        }
        logger.info("查询提现充值记录一览参数：{}", query.toString());
        List<Business> val = businessMapper.getRechargePostalList(query);
        if(!Util.isNull(val)){
            result.setSuccess(true);
            result.setList(val);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        } 
        logger.debug("getRechargePostalList出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getTransactionList()
     */
    @Override
    public ResultPage<Business> getTransactionList(BusinessQuery query) {
        logger.debug("getTransactionList入参:{}",query);
        ResultPage<Business> result = new ResultPage<Business>();
        int total = businessMapper.getTransactionCount(query);
        if(total > 0){
            List<Business> business = businessMapper.getTransactionList(query);
            result.setSuccess(true);
            result.setList(business);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getTransactionList出参:{}",result);
        return result;
    }
    /**
     * @see com.zjzmjr.core.service.business.transaction.TransactionService#getFaliTransactionList(com.zjzmjr.core.model.admin.BusinessQuery)
     */
    @Override
    public ResultEntry<Business> getFaliTransactionList(BusinessQuery queryBusiness) {
        logger.debug("getFaliTransactionList入参:{}",queryBusiness);
        ResultEntry<Business> result = new ResultEntry<Business>();
            Business business = businessMapper.getFaliTransactionList(queryBusiness);
            if(null!=business){
                result.setSuccess(true);
                result.setT(business);
            }else{
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            }
            logger.debug("getFaliTransactionList出参:{}",result);
        return result;
    }
}
