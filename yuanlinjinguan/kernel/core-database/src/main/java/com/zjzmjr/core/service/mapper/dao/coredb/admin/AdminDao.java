package com.zjzmjr.core.service.mapper.dao.coredb.admin;

import java.util.List;

import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: AdminDao.java, v 0.1 2016-5-25 上午10:38:24 oms Exp $
 */
public interface AdminDao {

    /**
     * 通过ID获取
     *
     * @param id
     * @return
     */
    Admin getById(Integer id);

    /**
     * 通过用户名获取
     *
     * @param username
     * @return
     */
    public Admin getByUsername(String username);
    
    /**
     * 根据手机号码获取用户信息
     * 
     * @param mobile
     * @return
     */
    Admin getByMobile(String mobile);

    /**
     * 条件查询
     *
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public List<Admin> query(Admin admin);

    /**
     * 分页查询
     *
     * @param query
     * @param page
     * @param pageSize
     * @return
     */
    public List<Admin> queryByPage(AdminQuery adminQuery);

    /**
     * 记录统计
     * 
     * @return
     */
    public int count(AdminQuery adminQuery);

    /**
     * 创建
     * 
     * @param admin
     */
    public void create(Admin admin);

    /**
     * 更新
     * 
     * @param admin
     */
    public int update(Admin admin);
    
    /**
     * 查询有无重复手机号
     * 
     * @param adminQuery
     * @return
     */
    Admin getRepeatByMobile(AdminQuery adminQuery);

}
