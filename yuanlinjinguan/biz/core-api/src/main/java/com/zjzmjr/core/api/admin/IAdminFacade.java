package com.zjzmjr.core.api.admin;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.admin.AdminStaff;
import com.zjzmjr.core.model.admin.AdminUpdate;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.StaffInfoQuery;

/**
 * @author shins
 * 
 */
public interface IAdminFacade {

    ResultPage<Admin> queryByPage(AdminQuery adminQuery);

    ResultEntry<Admin> getByUsername(String userName);

    /**
     * 根据手机号码获取用户信息
     * 
     * @param username
     * @return
     */
    ResultEntry<Admin> getByMobile(String mobile);
    
    Result create(AdminCreate adminCreate,CompanyStaffPerson companyStaffPerson);

    Result update(AdminUpdate adminUpdate);
    
    ResultEntry<Admin> getByID(Integer id);
    
    Result updateAdmin(Admin admin);
    
    Result changePassword(Integer adminId, String oldPwd, String newPwd );

    ResultEntry<Admin> getById(Integer createUserId);

    /**
     * 所有员工一览
     * 
     * @param query
     * @return
     */
    ResultPage<AdminStaff> getAdminStaff(StaffInfoQuery query);
}
