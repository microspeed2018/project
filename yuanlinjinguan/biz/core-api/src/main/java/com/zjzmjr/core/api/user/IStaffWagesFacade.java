package com.zjzmjr.core.api.user;

import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;


public interface IStaffWagesFacade {

    /**
     * 查找薪酬记录
     * 
     * @param query
     * @return
     */
    ResultPage<StaffWagesInfo> queryStaffWages(StaffWagesQuery query);
    
    /**
     * 根据主键获取薪酬详情
     * 
     * @param id
     * @return
     */
    ResultEntry<StaffWagesInfo> getStaffWageById(Integer id);
    
    /**
     * 导入excel
     * 
     * @param sheet
     * @return
     */
    ResultRecord<StaffWagesInfo> importExcel(StaffWagesQuery query);
    
    /**
     * 批量新增薪酬记录
     * 
     * @param list
     * @return
     */
    ResultRecord<StaffWagesInfo> batchInsert(List<StaffWagesInfo> list);
    
    ResultEntry<String> exportExcel(StaffWagesQuery query);
}
