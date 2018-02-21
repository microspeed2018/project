package com.zjzmjr.core.api.user;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;

/**
 * 公司员工外部人员接口
 * 
 * @author oms
 * @version $Id: IStaffPersonFacade.java, v 0.1 2017-8-15 下午7:43:20 oms Exp $
 */
public interface IStaffPersonFacade {

    /**
     * 
     * 
     * @param query
     * @return
     */
    ResultPage<StaffBasicInfo> getStaffInfoByCondition(StaffInfoQuery query);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertStaffInfo(StaffInfo record);
    
    /**
     * 
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateStaffInfoById(StaffInfo record);

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

//    /**
//     * 查询公司内部的正式员工及非正式的员工，
//     * 返回的list中，第一个参数是公司的正式员工， 第二参数是公司的非正式员工
//     * 
//     * @param staffQry
//     * @param personQry
//     * @return
//     */
//    ResultRecord<Object> getCompanyStaffPersonInfo(StaffInfoQuery staffQry, ExternalPersonQuery personQry);
    
    /**
     * 管理端修改员工
     * 
     * @param staffBasicInfo
     * @return
     */
    ResultEntry<Integer> updateStaffInfoAndAdmin(StaffBasicInfo staffBasicInfo);
    
    /**
     * 外部员工、管理端修改
     * 
     * @param externalPersonInfo
     * @return
     */
    ResultEntry<Integer> updateExternalPersonAndAdmin(ExternalPersonInfo externalPersonInfo);
    
    /**
     * 获取外部员工表中最大的员工编号值，然后最大员工编号值加一返回出去
     * 
     * @return
     */
    ResultEntry<Integer> getExternalPersonEmployeeMaxNo();
    
    /**
     * 简历读取内部员工新增
     * 
     * @param staffBasicInfo
     * @return
     */
    ResultEntry<Integer> insertStaffInfoByTalent(StaffBasicInfo staffBasicInfo);
}
