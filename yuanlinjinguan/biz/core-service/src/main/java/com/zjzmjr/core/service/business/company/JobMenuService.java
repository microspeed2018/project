package com.zjzmjr.core.service.business.company;

import com.zjzmjr.core.base.result.Result;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.JobMenu;
import com.zjzmjr.core.model.company.JobMenuBind;
import com.zjzmjr.core.model.company.JobMenuQuery;
import com.zjzmjr.core.model.menu.MenuNode;

/**
 * 职位菜单信息
 * 
 * @author oms
 * @version $Id: JobMenuService.java, v 0.1 2017-8-29 下午4:47:36 oms Exp $
 */
public interface JobMenuService {

    /**
     * 获取角色菜单信息
     * 
     * @param query
     * @return
     */
    ResultPage<JobMenu> getJobMenuByCondition(JobMenuQuery query);
    
    /**
     * 角色菜单绑定
     * 
     * @param menuUserBind
     * @return
     */
    Result bindJobMenu(JobMenuBind JobMenuBind);
    
    /**
     * 获取角色菜单树
     * 
     * @param query
     * @return
     */
    ResultEntry<MenuNode> getJobMenuTree(JobMenuQuery query);
    
    /**
     * 创建管理员时，根据管理员的角色自动绑定菜单
     * 
     * @param jobId
     * @param userId
     * @return
     */
    ResultEntry<Integer> bindUserMenu(Integer jobId, Integer userId);
    
    /**
     * 更新管理员的时候，根据管理员的角色绑定菜单
     * 
     * @param jobId
     * @param userId
     * @return
     */
    ResultEntry<Integer> bindUpdateUserMenu(Integer jobId, Integer userId);
    
}
