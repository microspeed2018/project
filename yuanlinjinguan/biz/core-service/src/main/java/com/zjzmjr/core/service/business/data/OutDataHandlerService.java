package com.zjzmjr.core.service.business.data;

import java.util.Map;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;

/**
 * 外部数据处理的业务层
 * 
 * @author oms
 * @version $Id: OutDataHandlerService.java, v 0.1 2017-10-9 下午4:17:45 oms Exp $
 */
public interface OutDataHandlerService {

    /**
     * 导入外部的数据， 数据格式为列名及值的键值对
     * 
     * @param dataMap
     */
    ResultEntry<String> importOutsideData(Map<String, String> dataMap, Integer handlerType) throws ApplicationException ;
    
    /**
     * 清空内存中map的数据
     */
    void clearMapData();
    
}
