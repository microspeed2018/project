package com.zjzmjr.core.provider.transaction;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.transaction.ITransactionFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;
import com.zjzmjr.core.service.business.transaction.TransactionService;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: TransactionFacadeImpl.java, v 0.1 2016-6-13 下午4:08:27
 *          Administrator Exp $
 */
@Service("transactionFacade")
public class TransactionFacadeImpl implements ITransactionFacade {

    private static final Logger logger = LoggerFactory.getLogger(TransactionFacadeImpl.class);

    @Resource(name = "transactionService")
    private TransactionService transactionService;

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminFacade#insertTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Business> insertTransaction(Business business) {
        ResultEntry<Business> result = new ResultEntry<Business>();
        try {
            result = transactionService.insertTransaction(business);
            if(result.isSuccess() && result.getT().getId() == null){
                ResultEntry<Business> businesss = transactionService.getTransaction(business);
                if (businesss.isSuccess()) {
                    result.setSuccess(true);
                    result.setT(businesss.getT());
                } else {
                    result.setSuccess(false);
                    result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                    result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                    logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName() + ":查询事物失败");
                }
            }
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminFacade#updateTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Integer> updateTransaction(Business business) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = transactionService.updateTransaction(business);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.transaction.ITransactionFacade#updateTransactionByExtend3(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Integer> updateTransactionByExtend3(Business business) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = transactionService.updateTransactionByExtend3(business);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.transaction.ITransactionFacade#getTransaction(com.zjzmjr.core.model.admin.Business)
     */
    @Override
    public ResultEntry<Business> getTransaction(Business business) {
        ResultEntry<Business> result = new ResultEntry<Business>();
        try {
            result = transactionService.getTransaction(business);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.transaction.ITransactionFacade#getRechargePostalList(com.zjzmjr.core.model.admin.BusinessQuery)
     */
    @Override
    public ResultPage<Business> getRechargePostalList(BusinessQuery query) {
        ResultPage<Business> result = new ResultPage<Business>();
        try {
            result = transactionService.getRechargePostalList(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.admin.IAdminFacade#getTransactionList()
     */
    @Override
    public ResultPage<Business> getTransactionList(BusinessQuery query) {
        ResultPage<Business> result = new ResultPage<Business>();
        try {
            result = transactionService.getTransactionList(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }
    /**
     * @see com.zjzmjr.core.api.transaction.ITransactionFacade#getFaliTransactionList(com.zjzmjr.core.model.admin.BusinessQuery)
     */
    @Override
    public ResultEntry<Business> getFaliTransactionList(BusinessQuery queryBusiness) {
        ResultEntry<Business> result = new ResultEntry<Business>();
        try {
            result = transactionService.getFaliTransactionList(queryBusiness);
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
