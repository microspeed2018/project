package com.zjzmjr.core.provider.area;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.area.IAreaFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.core.model.area.InsuranceArea;
import com.zjzmjr.core.model.area.InsuranceAreaQuery;
import com.zjzmjr.core.service.business.area.AreaService;

@Service("areaFacade")
public class AreaFacadeImpl implements IAreaFacade {
    
    private static final Logger logger = LoggerFactory.getLogger(AreaFacadeImpl.class);
    
    
    @Resource(name="bankCardAreaService")
    private AreaService  bankCardAreaService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getAllProv()
     */
    @Override
    public ResultRecord<Area> getAllProv() {
       ResultRecord<Area> result=new ResultRecord<Area>();
       try {
        result=bankCardAreaService.getAllProv();
    } catch (Exception e) {
        result.setSuccess(false);
        result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
        result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
        return result;  
    }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getAllDist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getAllDist(String provCode) {
        ResultRecord<Area> result=new ResultRecord<Area>();
        try {
            result=bankCardAreaService.getAllDist(provCode);
            
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getAllArea(com.zjzmjr.core.model.area.AreaQuery)
     */
    @Override
    public ResultPage<Area> getAllArea(AreaQuery query) {
        ResultPage<Area> result=new ResultPage<Area>();
        try {
            result=bankCardAreaService.getAllArea(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#insertArea(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultEntry<Integer> insertArea(Area area) {
       ResultEntry<Integer> result=new ResultEntry<Integer>();
       try {
        result=bankCardAreaService.insertArea(area);
    } catch (Exception e) {
        result.setSuccess(false);
        result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
        result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
        return result;  
    }
    return result;

    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#updateArea(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultEntry<Integer> updateArea(Area area) {
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        try {
         result=bankCardAreaService.updateArea(area);
     } catch (Exception e) {
         result.setSuccess(false);
         result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
         result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
         logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
         return result;  
     }
     return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#Dist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> Dist(String provCode) {
        ResultRecord<Area> result=new ResultRecord<Area>();
        try {
            result=bankCardAreaService.getDist(provCode);
            
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getCity(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getCity(String provCode) {
        ResultRecord<Area> result=new ResultRecord<Area>();
        try {
            result=bankCardAreaService.getCity(provCode);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#dist(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultRecord<Area> dist(Area area) {
       ResultRecord<Area> result=new ResultRecord<Area>();
       try {
        result=bankCardAreaService.dist(area);
    } catch (Exception e) {
        result.setSuccess(false);
        result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
        result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
        logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
        return result;  
    }
    return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getCityDist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getCityDist(String provCode) {
        ResultRecord<Area> result=new ResultRecord<Area>();
        try {
            result=bankCardAreaService.getCityDist(provCode);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

   /**
    * 
    * @see com.zjzmjr.core.api.area.IAreaFacade#getLoanCity(com.zjzmjr.core.model.area.Area)
    */
    @Override
    public ResultRecord<Area> getLoanCity(Area area) {
        ResultRecord<Area> result=new ResultRecord<Area>();
        try {
            result=bankCardAreaService.getLoanCity(area);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e); 
            return result;  
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.area.IAreaFacade#getLoanDist(com.zjzmjr.core.model.area.Area)
     */
     @Override
     public ResultRecord<Area> getLoanDist(Area area) {
        ResultRecord<Area> result = new ResultRecord<Area>();
        try {
            result = bankCardAreaService.getLoanDist(area);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
   }

     /**
      * 
      * @see com.zjzmjr.core.api.area.IAreaFacade#getAllNameArea()
      */
    @Override
    public ResultRecord<Area> getAllNameArea() {
        ResultRecord<Area> result = new ResultRecord<Area>();
        try {
            result = bankCardAreaService.getAllNameArea();
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

}
