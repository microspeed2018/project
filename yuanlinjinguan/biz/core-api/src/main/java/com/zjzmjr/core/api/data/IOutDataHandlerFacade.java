package com.zjzmjr.core.api.data;

import java.util.List;
import java.util.Map;

import com.zjzmjr.core.base.result.ResultEntry;

/**
 * 外部数据处理接口
 * 
 * @author oms
 * @version $Id: OutDataHandlerFacade.java, v 0.1 2017-10-10 下午6:54:56 oms Exp $
 */
public interface IOutDataHandlerFacade {

    /**
     * 导入外部的数据， 数据格式为列名及值的键值对
     * 
     * @param dataLst
     */
    ResultEntry<List<String>> importOutsideData(List<Map<String, String>> dataLst, Integer handlerType);
    
}
