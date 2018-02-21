package com.zjzmjr.core.service.business.admin.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminAuth;
import com.zjzmjr.core.model.admin.AdminAuthQuery;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.admin.SimpleGrantedAuthority;
import com.zjzmjr.core.service.business.admin.AdminLoginAuthService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminAuthDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminUserAuthDao;

/**
 * 
 * 
 * @author oms
 * @version $Id: AdminLoginAuthServiceImpl.java, v 0.1 2016-6-14 下午1:40:09 oms Exp $
 */
@Service("adminLoginAuthService")
public class AdminLoginAuthServiceImpl implements AdminLoginAuthService{
    private static final Logger logger = LoggerFactory.getLogger(AdminLoginAuthServiceImpl.class);
    @Resource(name = "adminDao")
    private AdminDao adminDao;

    @Resource(name = "adminAuthDao")
    private AdminAuthDao adminAuthDao;
    
    @Resource(name = "adminUserAuthDao")
    private AdminUserAuthDao adminUserAuthDao;
    
    @Override
    public ResultRecord<SimpleGrantedAuthority> getUserAuths(Admin admin) {
        logger.debug("getUserAuths入参:{}",admin);
        ResultRecord<SimpleGrantedAuthority> result = new ResultRecord<SimpleGrantedAuthority>();
        List<SimpleGrantedAuthority> auths = null;
        if (admin != null && AdminAccStatusEnum.NORMAL.getValue().equals(admin.getAccStatus())) {
            List<AdminAuth> list = null;
            if ("admin".equals(admin.getUsername())) {
                AdminAuthQuery query = new AdminAuthQuery();
                PageBean bean = new PageBean(Integer.MAX_VALUE, 1);
                query.setPageBean(bean);
                list = adminAuthDao.getAuths(query);
            } else {
                list = adminUserAuthDao.getAuthByUser(admin.getId());
            }

            // 检索出所有的员工信息
            AdminQuery adminQuery = new AdminQuery();
            PageBean pageBean = new PageBean(Integer.MAX_VALUE, 1);
            adminQuery.setPageBean(pageBean);
            AdminInfoHolder.setContext(adminDao.queryByPage(adminQuery));
            
            auths = new ArrayList<SimpleGrantedAuthority>(list == null || list.size() == 0 ? 1 : list.size());
            if (list != null && !list.isEmpty()) {
                SimpleGrantedAuthority simpleAuthority = null;
                for (AdminAuth auth : list) {
                    simpleAuthority = new SimpleGrantedAuthority();
                    simpleAuthority.setRole(auth.getCode());
                    auths.add(simpleAuthority);
                }
            }
        }
        result.setList(auths == null ? Collections.<SimpleGrantedAuthority> emptyList() : auths);
        logger.debug("getUserAuths入参:{}",result);
        return result;
    }
}
