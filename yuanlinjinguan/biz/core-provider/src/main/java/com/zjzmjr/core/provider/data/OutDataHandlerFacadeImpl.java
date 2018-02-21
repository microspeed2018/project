package com.zjzmjr.core.provider.data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.data.IOutDataHandlerFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.service.business.data.OutDataHandlerService;

/**
 * 外部数据处理接口
 * 
 * @author oms
 * @version $Id: OutDataHandlerFacadeImpl.java, v 0.1 2017-10-10 下午6:57:15 oms Exp $
 */
@Service("outDataHandlerFacade")
public class OutDataHandlerFacadeImpl implements IOutDataHandlerFacade{

    private static final Logger logger = LoggerFactory.getLogger(OutDataHandlerFacadeImpl.class);

    @Resource(name = "outDataHandlerService")
    private OutDataHandlerService outDataHandlerService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.data.IOutDataHandlerFacade#importOutsideData(java.util.List)
     */
    @Override
    public ResultEntry<List<String>> importOutsideData(List<Map<String, String>> dataLst, Integer handlerType) {
        ResultEntry<List<String>> result = new ResultEntry<List<String>>();
        try {
            if (dataLst != null && !dataLst.isEmpty()){
                List<String> retInfo = new ArrayList<String>();
                for(Map<String, String> dataMap : dataLst){
                    try {
                        outDataHandlerService.importOutsideData(dataMap, handlerType);
                        retInfo.add("导入成功");
                    } catch (Exception ax) {
                        logger.error("数据导入失败: {}", dataMap);
                        logger.error("数据导入失败:", ax);
                        retInfo.add(ax.getMessage());
                        result.setErrorMsg(ax.getMessage());
                    }
                    dataMap.clear();
                }
                result.setSuccess(true);
                result.setT(retInfo);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }finally {
            outDataHandlerService.clearMapData();
        }
        return result;
    }

}
