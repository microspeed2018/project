package com.zjzmjr.core.service.business.admin;

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
 * 管理员事务
 * 
 * @author oms
 * @version $Id: AdminService.java, v 0.1 2016-5-25 下午1:35:34 oms Exp $
 */
public interface AdminService {

    /**
     * 
     * 
     * @param adminQuery
     * @return
     */
    ResultPage<Admin> queryByPage(AdminQuery adminQuery);

    /**
     * 
     * 
     * @param userId
     * @return
     */
    boolean isSuperAdmin(Integer userId);

    Admin getSuperAdmin();

    ResultEntry<Integer> create(AdminCreate adminCreate,CompanyStaffPerson companyStaffPerson);

    Result update(AdminUpdate adminUpdate);
    
    ResultEntry<Admin> getById(Integer id);
    
    ResultEntry<Admin> getByUsername(String username);

    /**
     * 根据手机号码获取用户信息
     * 
     * @param username
     * @return
     */
    ResultEntry<Admin> getByMobile(String mobile);
    
    Result updateAdmin(Admin admin);
    
    Result changePassword(Integer adminId, String oldPwd, String newPwd );
    
    /**
     * 所有员工一览
     * 
     * @param query
     * @return
     */
    ResultPage<AdminStaff> getAdminStaff(StaffInfoQuery query);


}
