package com.zjzmjr.core.service.business.user;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;

/**
 * 员工薪酬服务层
 * 
 * @author Administrator
 * @version $Id: StaffWagesService.java, v 0.1 2017-12-12 下午2:36:30 Administrator Exp $
 */
public interface StaffWagesService {

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
    ResultRecord<StaffWagesInfo> importExcel(StaffWagesQuery query) throws ApplicationException;
    
    /**
     * 批量新增薪酬记录
     * 
     * @param list
     * @return
     * @throws ApplicationException 
     */
    ResultRecord<StaffWagesInfo> batchInsert(List<StaffWagesInfo> list) throws ApplicationException;
    
    
    ResultEntry<String> exportExcel(StaffWagesQuery query);
}
