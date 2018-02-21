package com.zjzmjr.core.api.transaction;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;

/**
 * 
 * 
 * @author Administrator
 * @version $Id: IAdminTransactionFacade.java, v 0.1 2016-10-9 下午3:29:10
 *          Administrator Exp $
 */
public interface IAdminTransactionFacade {

    /**
     * 新增管理员事物
     * 
     * @param adminBusiness
     * @return
     */
    ResultEntry<AdminBusiness> insertAdminTransaction(AdminBusiness adminBusiness);

    /**
     * 更新管理员事物
     * 
     * @param adminBusiness
     * @return
     */
    ResultEntry<Integer> updateAdminTransaction(AdminBusiness adminBusiness);

    /**
     * 根据管理员名称 事物类型查询事物列表
     * 
     * @param query
     * @return
     */
    ResultPage<AdminBusiness> getAdminTransactionList(AdminBusinessQuery query);
    

}
