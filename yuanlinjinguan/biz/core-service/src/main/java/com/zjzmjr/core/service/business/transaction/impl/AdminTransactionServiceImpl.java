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
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;
import com.zjzmjr.core.service.business.transaction.AdminTransactionService;
import com.zjzmjr.core.service.mapper.dao.coredb.transaction.AdminBusinessMapper;

/**
 * 管理员事物Service层
 * 
 * @author Administrator
 * @version $Id: AdminTransactionServiceImpl.java, v 0.1 2016-10-9 下午2:40:04
 *          Administrator Exp $
 */
@Service("adminTransactionService")
public class AdminTransactionServiceImpl implements AdminTransactionService {

    private static final Logger logger = LoggerFactory.getLogger(AdminTransactionServiceImpl.class);

    @Resource(name = "adminBusinessMapper")
    private AdminBusinessMapper adminBusinessMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.transaction.AdminTransactionService#insertAdminTransaction(com.zjzmjr.core.model.admin.AdminBusiness)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<AdminBusiness> insertAdminTransaction(AdminBusiness adminBusiness) {
        logger.debug("insertAdminTransaction入参:{}",adminBusiness);
        ResultEntry<AdminBusiness> result = new ResultEntry<AdminBusiness>();
        if (Util.isNull(adminBusiness) || Util.isNull(adminBusiness.getAdminId()) || Util.isNull(adminBusiness.getBusinessType()) || Util.isNull(adminBusiness.getResult())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), adminBusiness.toString());
            logger.debug("insertAdminTransaction出参:{}",result);
            return result;
        }
        logger.info("新增管理员事物一览参数:{}", adminBusiness.toString());
        int total = adminBusinessMapper.insertAdminTransaction(adminBusiness);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(adminBusiness);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":新增管理员事物失败");
        }
        logger.debug("insertAdminTransaction出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.transaction.AdminTransactionService#updateAdminTransaction(com.zjzmjr.core.model.admin.AdminBusiness)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateAdminTransaction(AdminBusiness adminBusiness) {
        logger.debug("updateAdminTransaction入参:{}",adminBusiness);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(adminBusiness) || Util.isNull(adminBusiness.getId()) || Util.isNull(adminBusiness.getResult())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), adminBusiness.toString());
            logger.debug("updateAdminTransaction出参:{}",result);
            return result;
        }
        logger.info("更新管理员事物一览参数:{}", adminBusiness.toString());
        int total = adminBusinessMapper.updateAdminTransaction(adminBusiness);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新管理员事物失败");
        }
        logger.debug("updateAdminTransaction出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.transaction.AdminTransactionService#getAdminTransactionList(com.zjzmjr.core.model.admin.AdminBusinessQuery)
     */
    @Override
    @Transactional
    public ResultPage<AdminBusiness> getAdminTransactionList(AdminBusinessQuery query) {
        logger.debug("getAdminTransactionList入参:{}",query);
        ResultPage<AdminBusiness> result=new ResultPage<AdminBusiness>();
        if (Util.isNull(query) || Util.isNull(query.getCompanyId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"));
            return result;
        }
        
        logger.info("查询事务的参数:{}", query.toString());
        int total =adminBusinessMapper.getAdminTransactionCount(query);
        if(total>0){
            List<AdminBusiness> businesses=adminBusinessMapper.getAdminTransactionList(query);
            result.setSuccess(true);
            result.setList(businesses);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("getAdminTransactionList出参:{}",result);
        return result;
    }

}
