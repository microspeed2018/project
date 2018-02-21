package com.zjzmjr.core.service.mapper.dao.user;

import java.util.List;

import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: StaffInfoMapper.java, v 0.1 2017-8-9 下午2:23:40 oms Exp $
 */
public interface StaffInfoMapper {

    int deleteStaffInfoById(Integer id);

    int insertStaffInfo(StaffInfo record);

    int getStaffInfoCount(StaffInfoQuery query);

    List<StaffBasicInfo> getStaffInfoByCondition(StaffInfoQuery query);

    int updateStaffInfoById(StaffInfo record);

}