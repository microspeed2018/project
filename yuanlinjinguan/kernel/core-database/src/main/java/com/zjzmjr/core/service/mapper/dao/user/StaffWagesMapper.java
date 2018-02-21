package com.zjzmjr.core.service.mapper.dao.user;

import java.util.List;

import com.zjzmjr.core.model.user.StaffWages;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;

/**
 * 员工薪酬表z_staff_wages
 * 
 * @author Administrator
 * @version $Id: StaffWagesMapper.java, v 0.1 2017-12-12 上午10:40:05 Administrator Exp $
 */
public interface StaffWagesMapper {
    
    /**
     * 统计薪酬记录数量
     * 
     * @param query
     * @return
     */
    int queryStaffWagesCount(StaffWagesQuery query);
    
    /**
     * 根据条件搜索薪酬记录
     * 
     * @param query
     * @return
     */
    List<StaffWagesInfo> queryStaffWages(StaffWagesQuery query);
    
    int deleteByPrimaryKey(Integer id);

    int insertStaffWages(StaffWages record);
    
    /**
     * 批量新增薪酬记录
     * 
     * @param list
     * @return
     */
    int batchInsert(List<StaffWagesInfo> list);

    /**
     * 根据主键获取薪酬详情
     * 
     * @param id
     * @return
     */
    StaffWagesInfo getStaffWageById(Integer id);

    int updateById(StaffWages record);
}