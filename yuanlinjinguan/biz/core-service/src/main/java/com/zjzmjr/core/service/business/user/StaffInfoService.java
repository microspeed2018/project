package com.zjzmjr.core.service.business.user;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: StaffInfoService.java, v 0.1 2017-8-9 下午3:53:12 oms Exp $
 */
public interface StaffInfoService {

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
     * 内部员工、管理端修改
     * 
     * @param staffBasicInfo
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> updateStaffInfoAndAdmin(StaffBasicInfo staffBasicInfo)  throws ApplicationException;
    
    /**
     * 简历读取内部员工新增
     * 
     * @param staffBasicInfo
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> insertStaffInfoByTalent(StaffBasicInfo staffBasicInfo)  throws ApplicationException;
    
}
