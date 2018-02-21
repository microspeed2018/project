package com.zjzmjr.core.service.mapper.dao.coredb.admin;

import java.util.List;

import com.zjzmjr.core.model.admin.AdminStaff;
import com.zjzmjr.core.model.user.StaffInfoQuery;


public interface AdminStaffMapper {

    /**
     * 所有员工数量
     * 
     * @return
     */
    int getAdminStaffCount(StaffInfoQuery query);
    
    /**
     * 所有员工一览
     * 
     * @return
     */
    List<AdminStaff> getAdminStaff(StaffInfoQuery query);

}
