package com.zjzmjr.core.service.business.admin.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.enums.admin.DepartmentEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.admin.AdminStaff;
import com.zjzmjr.core.model.admin.AdminUpdate;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.admin.AdminAuthService;
import com.zjzmjr.core.service.business.admin.AdminService;
import com.zjzmjr.core.service.business.menu.RoleMenuService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminStaffMapper;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.RoleAuthDao;
import com.zjzmjr.core.service.mapper.dao.user.ExternalPersonMapper;
import com.zjzmjr.core.service.mapper.dao.user.StaffInfoMapper;
import com.zjzmjr.security.web.authentication.strategy.PasswordEncodeStrategy;

/**
 * 管理员业务
 * 
 * @author oms
 * @version $Id: AdminServiceImpl.java, v 0.1 2016-5-25 下午1:37:41 oms Exp $
 */
@Service("adminService")
public class AdminServiceImpl implements AdminService {

    private static final Logger logger = LoggerFactory.getLogger(AdminServiceImpl.class);

    @Resource(name = "adminDao")
    private AdminDao adminDao;

    @Resource(name = "passwordEncodeStrategy")
    private PasswordEncodeStrategy passwordEncodeStrategy;

    @Resource(name = "roleMenuService")
    private RoleMenuService roleMenuService;
    
    @Resource(name = "adminAuthService")
    private AdminAuthService adminAuthService;
    
    @Resource(name = "roleAuthDao")
    private RoleAuthDao RoleAuthDao;
    
    @Resource(name = "staffInfoMapper")
    private StaffInfoMapper staffInfoMapper;
    
    @Resource(name = "externalPersonMapper")
    private ExternalPersonMapper externalPersonMapper;
    
    @Resource(name = "adminStaffMapper")
    private AdminStaffMapper adminStaffMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#isSuperAdmin(java.lang.Integer)
     */
    @Override
    public boolean isSuperAdmin(Integer userId) {
        if (null == userId || userId <= 0) {
            return false;
        }
        Admin superAdmin = getSuperAdmin();
        return (superAdmin != null && superAdmin.getId().equals(userId));
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getSuperAdmin()
     */
    @Override
    public Admin getSuperAdmin() {
        return adminDao.getByUsername("admin");
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getByUsername(java.lang.String)
     */
    @Override
    public ResultEntry<Admin> getByUsername(String userName) {
        logger.debug("getByUsername入参:{}",userName);
        ResultEntry<Admin> entry = new ResultEntry<Admin>();
        if (StringUtils.isEmpty(userName)) {
            entry.setSuccess(false);
            entry.setErrorMsg("参数不正");
            entry.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), userName);
            return entry;
        }
        try {
            Admin admin = adminDao.getByUsername(userName);
            if (admin == null) {
                entry.setSuccess(false);
                entry.setErrorMsg("该员工不存在");
                entry.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            } else {
                entry.setSuccess(true);
                entry.setT(admin);
            }
        } catch (Exception e) {
            entry.setErrorMsg(e.getMessage());
            entry.setSuccess(false);
            logger.error(e.getMessage(), e);
        }
        logger.debug("getByUsername出参:{}",entry);
        return entry;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getByMobile(java.lang.String)
     */
    @Override
    public ResultEntry<Admin> getByMobile(String mobile) {
        ResultEntry<Admin> entry = new ResultEntry<Admin>();
        if (StringUtils.isEmpty(mobile)) {
            entry.setSuccess(false);
            entry.setErrorMsg("参数不正确");
            entry.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), mobile);
            return entry;
        }

        Admin admin = adminDao.getByMobile(mobile);
        if (admin == null) {
            entry.setSuccess(false);
            entry.setErrorMsg("该员工不存在");
            entry.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
        } else {
            entry.setSuccess(true);
            entry.setT(admin);
        }
        return entry;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#queryByPage(com.zjzmjr.core.model.admin.AdminQuery)
     */
    @Override
    public ResultPage<Admin> queryByPage(AdminQuery adminQuery) {
        logger.debug("getByUsername入参:{}",adminQuery);
        PageBean page = adminQuery.getPageBean();
        ResultPage<Admin> result = new ResultPage<Admin>();
        List<Admin> admins = adminDao.queryByPage(adminQuery);
        int total = adminDao.count(adminQuery);
        page.setTotalResults(total);
        result.setList(admins);
        result.setPage(page);
        logger.debug("getByUsername出参:{}",result);
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.AdminService#create(com.yztz.finance.model.admin.AdminCreate)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> create(AdminCreate adminCreate, CompanyStaffPerson companyStaffPerson) {
        logger.debug("create入参:{}",adminCreate);
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        Admin admin = new Admin();
        Date timestamp = new Date();
//        List<Integer> authIds = new ArrayList<>();// 角色对应的权限ids
        admin.setAccStatus(adminCreate.getAccStatus() == null ? AdminAccStatusEnum.NORMAL.getValue() : adminCreate.getAccStatus().getValue());
        admin.setCreateUser(adminCreate.getCreateUser());
        admin.setDepartment(adminCreate.getDepartment() == null ? DepartmentEnum.OTHER.getValue() : adminCreate.getDepartment().getValue());        
        admin.setCompanyId(adminCreate.getCompanyId());
        admin.setJobId(adminCreate.getJobId());
        admin.setEmail(StringUtils.trimToEmpty(adminCreate.getEmail()));
        admin.setLoginError(0);
        admin.setLoginFail(0);
        admin.setLoginIp("");
        admin.setLoginSucceed(0);
        admin.setLoginTime(timestamp);
        admin.setMobile(StringUtils.trimToEmpty(adminCreate.getMobile()));
        admin.setName(StringUtils.trimToEmpty(adminCreate.getName()));
        admin.setPassword(passwordEncodeStrategy.encodePassword(StringUtils.trimToEmpty(adminCreate.getPassword())));
        admin.setSecurityIp(StringUtils.trimToEmpty(adminCreate.getSecurityIp()));
        admin.setTime(timestamp);
        admin.setUsername(StringUtils.trimToEmpty(adminCreate.getUsername()));
        admin.setUserViewDate(timestamp);
        admin.setTotalUserView(0);
        admin.setCurrentUserView(0);
        admin.setMaxUserView(0);
        admin.setUserViewLimit(100);
        adminDao.create(admin);
        if(Util.isNotNull(companyStaffPerson.getStaff())){
            StaffInfo staff = companyStaffPerson.getStaff().get(0);
            staff.setUserId(admin.getId());
            staff.setCreateUserId(admin.getId());
            staffInfoMapper.insertStaffInfo(staff);
        }
        if(Util.isNotNull(companyStaffPerson.getPerson())){
            ExternalPerson externalPerson = companyStaffPerson.getPerson().get(0);
            externalPerson.setUserId(admin.getId());
            externalPerson.setCreateUserId(admin.getId());
            externalPersonMapper.insertExternalPerson(externalPerson);
        }
        result.setSuccess(true);
        result.setT(admin.getId());
//        // 创建管理员时，根据管理员的角色自动绑定菜单
//        if (adminCreate.getRoleType() != null) {
//            // 绑定菜单
//            roleMenuService.bindUserMenu(adminCreate.getRoleType(), admin.getId());
//            // 绑定权限
//            authIds = RoleAuthDao.getAuthIdsByRoleId(adminCreate.getRoleType());// 根据角色id获取当前角色下所有的的权限
//            adminAuthService.bindUserAuth(admin.getId(), authIds);
//        }
        return result;
    }

    /**
     * @see com.yztz.finance.bussiness.service.console.AdminService#update(com.yztz.finance.model.admin.AdminUpdate)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result update(AdminUpdate adminUpdate) {
        logger.debug("update入参:{}",adminUpdate);
//        // 获取更新之前的角色信息
//        Integer roleType = getById(adminUpdate.getId()).getT().getRoleType();
        Admin mod = new Admin(adminUpdate.getId());
//        List<Integer> authIds = new ArrayList<>();// 角色对应的权限ids
        if (adminUpdate.getName() != null) {
            mod.setName(StringUtils.trimToEmpty(adminUpdate.getName()));
        }
        if (adminUpdate.getDepartment() != null) {
            mod.setDepartment(adminUpdate.getDepartment().getValue());
        }
//        if (adminUpdate.getRoleType() != null) {
//            mod.setRoleType(adminUpdate.getRoleType());
//        }
        if (adminUpdate.getCompanyId() != null) {
            mod.setCompanyId(adminUpdate.getCompanyId());
        }
        if (adminUpdate.getMobile() != null) {
            mod.setMobile(StringUtils.trimToEmpty(adminUpdate.getMobile()));
        }
        if (adminUpdate.getEmail() != null) {
            mod.setEmail(StringUtils.trimToEmpty(adminUpdate.getEmail()));
        }
        if (adminUpdate.getStatus() != null) {
            mod.setAccStatus(adminUpdate.getStatus().getValue());
        }
        if (adminUpdate.getSecurityIp() != null) {
            mod.setSecurityIp(StringUtils.trimToEmpty(adminUpdate.getSecurityIp()));
        }
        if (StringUtils.isNotBlank(adminUpdate.getPassword())) {
            mod.setPassword(passwordEncodeStrategy.encodePassword(StringUtils.trimToEmpty(adminUpdate.getPassword())));
        }
        adminDao.update(mod);

//        // 只有用户有角色才能去更新绑定菜单和权限
//        if (roleType != null || adminUpdate.getRoleType() != null) {
//            // 更新绑定菜单
//            roleMenuService.bindUpdateUserMenu(adminUpdate.getRoleType(), adminUpdate.getId());
//            // 更新绑定权限
//            authIds = RoleAuthDao.getAuthIdsByRoleId(adminUpdate.getRoleType());// 根据角色id获取当前角色下所有的的权限
//            adminAuthService.bindUserAuth(adminUpdate.getId(), authIds);
//        }
        return new Result();
    }

    @Override
    public ResultEntry<Admin> getById(Integer id) {
        logger.debug("getById入参:{}",id);
        ResultEntry<Admin> result = new ResultEntry<Admin>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg("参数错误，userID为空");
        }
        Admin admin = adminDao.getById(id);
        result.setT(admin);
        logger.debug("getById出参:{}",result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result updateAdmin(Admin admin) {
        logger.debug("updateAdmin入参:{}",admin);
        Result result = new Result();
        if (Util.isNull(admin)) {
            result.setSuccess(false);
            result.setErrorMsg("参数错误，admin为空");
        }
        if (StringUtils.isNotBlank(admin.getPassword())) {
            admin.setPassword(passwordEncodeStrategy.encodePassword(StringUtils.trimToEmpty(admin.getPassword())));  
        }
        adminDao.update(admin);
        logger.debug("updateAdmin出参:{}",result);
        return result;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public Result changePassword(Integer adminId, String oldPwd, String newPwd) {
        logger.debug("changePassword入参:adminId:{},oldPwd:{},newPwd:{}",adminId,oldPwd,newPwd);
        Result result = new Result();
        if (Util.isNull(adminId) || Util.isNull(oldPwd) || Util.isNull(newPwd)) {
            result.setErrorMsg("参数错误，" + "username【" + adminId + "】," + "oldPwd【" + oldPwd + "】," + "newPwd【" + newPwd + "】");
            result.setSuccess(false);
            return result;
        }
        logger.info("changePassword RequestParams" + "username【" + adminId + "】," + "oldPwd【" + oldPwd + "】," + "newPwd【" + newPwd + "】");
        Admin admin = adminDao.getById(adminId);
        if (Util.isNull(admin)) {
            result.setErrorMsg("用户不存在");
            result.setSuccess(false);
            return result;
        }
        if (!passwordEncodeStrategy.isPasswordValid(oldPwd, admin.getPassword())) {
            result.setErrorMsg("原始密码错误");
            result.setSuccess(false);
            return result;
        }
        Admin adminM = new Admin(admin.getId());
        adminM.setPassword(passwordEncodeStrategy.encodePassword(StringUtils.trimToEmpty(newPwd)));
        if (adminDao.update(adminM) != 1) {
            result.setErrorMsg("数据已过期,请重试");
            result.setSuccess(false);
            return result;
        }
        logger.debug("changePassword出参:",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.admin.AdminService#getAdminStaff(com.zjzmjr.core.model.user.StaffInfoQuery)
     */
    @Override
    public ResultPage<AdminStaff> getAdminStaff(StaffInfoQuery query) {
        ResultPage<AdminStaff> result = new ResultPage<AdminStaff>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{getAdminStaff}"));
            return result;
        }
        int total = adminStaffMapper.getAdminStaffCount(query);
        if(total > 0){
            List<AdminStaff> list = adminStaffMapper.getAdminStaff(query);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));        
        return result;
    }

}
