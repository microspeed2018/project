package com.zjzmjr.core.service.business.user;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;

/**
 * 公司外部人员表的业务层
 * 
 * @author oms
 * @version $Id: ExternalPersonService.java, v 0.1 2017-8-15 下午4:01:47 oms Exp $
 */
public interface ExternalPersonService {

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<ExternalPersonInfo> getExternalPersonByCondition(ExternalPersonQuery query);

    /**
     * 
     * 
     * @param id
     * @return
     */
    ResultEntry<ExternalPerson> getExternalPersonById(Integer id);

    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertExternalPerson(ExternalPerson record);

    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateExternalPersonById(ExternalPerson record);
    
    /**
     * 外部员工、管理端修改
     * 
     * @param externalPersonInfo
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> updateExternalPersonAndAdmin(ExternalPersonInfo externalPersonInfo)  throws ApplicationException;
    
    /**
     * 获取外部员工表中最大的员工编号值，然后最大员工编号值加一返回出去
     * 
     * @return
     */
    ResultEntry<Integer> getExternalPersonEmployeeMaxNo();

}
