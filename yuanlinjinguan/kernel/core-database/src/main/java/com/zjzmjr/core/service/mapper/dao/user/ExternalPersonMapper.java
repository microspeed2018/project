package com.zjzmjr.core.service.mapper.dao.user;

import java.util.List;

import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;

/**
 * 外部人员表
 * 
 * @author oms
 * @version $Id: ExternalPersonMapper.java, v 0.1 2017-8-15 下午3:44:52 oms Exp $
 */
public interface ExternalPersonMapper {

    int deleteExternalPersonById(Integer id);

    int insertExternalPerson(ExternalPerson record);

    int getExternalPersonCount(ExternalPersonQuery query);

    List<ExternalPersonInfo> getExternalPersonByCondition(ExternalPersonQuery query);

    ExternalPerson getExternalPersonById(Integer id);

    int updateExternalPersonById(ExternalPerson record);
    
    /**
     * 获取外部人员编号最大值
     * 
     * @return
     */
    String getExternalPersonEmployeeMaxNo();

}