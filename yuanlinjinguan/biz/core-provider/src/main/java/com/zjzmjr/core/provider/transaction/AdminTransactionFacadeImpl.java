package com.zjzmjr.core.provider.transaction;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.transaction.IAdminTransactionFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;
import com.zjzmjr.core.service.business.transaction.AdminTransactionService;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: AdminTransactionFacadeImpl.java, v 0.1 2016-10-9 下午3:28:47
 *          Administrator Exp $
 */
@Service("adminTransactionFacade")
public class AdminTransactionFacadeImpl implements IAdminTransactionFacade {

    private static final Logger logger = LoggerFactory.getLogger(AdminTransactionFacadeImpl.class);

    @Resource(name = "adminTransactionService")
    private AdminTransactionService adminTransactionService;

    /**
     * 
     * @see com.zjzmjr.core.api.transaction.IAdminTransactionFacade#insertAdminTransaction(com.zjzmjr.core.model.admin.AdminBusiness)
     */
    @Override
    public ResultEntry<AdminBusiness> insertAdminTransaction(AdminBusiness adminBusiness) {
        ResultEntry<AdminBusiness> result = new ResultEntry<AdminBusiness>();
        try {
            result = adminTransactionService.insertAdminTransaction(adminBusiness);
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
     * @see com.zjzmjr.core.api.transaction.IAdminTransactionFacade#updateAdminTransaction(com.zjzmjr.core.model.admin.AdminBusiness)
     */
    @Override
    public ResultEntry<Integer> updateAdminTransaction(AdminBusiness adminBusiness) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = adminTransactionService.updateAdminTransaction(adminBusiness);
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
     * @see com.zjzmjr.core.api.transaction.IAdminTransactionFacade#getAdminTransactionList(com.zjzmjr.core.model.admin.AdminBusinessQuery)
     */
    @Override
    public ResultPage<AdminBusiness> getAdminTransactionList(AdminBusinessQuery query) {
        ResultPage<AdminBusiness> result = new ResultPage<AdminBusiness>();
        try {
            result = adminTransactionService.getAdminTransactionList(query);
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
