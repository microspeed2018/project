package com.zjzmjr.core.service.business.area.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.core.service.business.area.AreaService;
import com.zjzmjr.core.service.mapper.dao.coredb.area.AreaMapper;
/**
 * 银行卡地区服务层实现类
 * 
 * @author lenovo
 * @version $Id: BnakCardAreaServiceImpl.java, v 0.1 2016-7-13 下午2:06:19 lenovo Exp $
 */
@Service("bankCardAreaService")
public class AreaServiceImpl implements AreaService{

    private static final Logger logger = LoggerFactory.getLogger(AreaServiceImpl.class);
    
    @Resource(name="areaMapper")
    private AreaMapper areaMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getAllProv()
     */
    @Override
    public ResultRecord<Area> getAllProv() {
        ResultRecord<Area> result = new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.selectAllProv();
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getAllProv出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getAllProv出参:{}",result);
            return result;
        }
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getAllDist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getAllDist(String provCode) {
        logger.debug("getAllDist入参:{}",provCode);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.selectAllDist(provCode);
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getAllDist出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getAllDist出参:{}",result);
            return result;
        }
    }

    
    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getAllArea(com.zjzmjr.core.model.area.AreaQuery)
     */
    @Override
    public ResultPage<Area> getAllArea(AreaQuery query) {
        logger.debug("getAllArea入参:{}",query);
        ResultPage<Area> result=new ResultPage<Area>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),query.toString());
            return result; 
        }
        logger.info("查询所有地区记录：{}", query.toString());
        int total=areaMapper.getAllAreaCount(query);
        if(total>0){
           List<Area> list=areaMapper.getAllArea(query);  
           result.setList(list);
           result.setSuccess(true);
       }else{
           result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
           result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
           result.setSuccess(false);
       }
       result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
       logger.debug("getAllArea出参:{}",result);
       return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#insertArea(com.zjzmjr.core.model.area.Area)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertArea(Area area) {
        logger.debug("insertArea入参:{}",area);
       ResultEntry<Integer> result=new ResultEntry<Integer>(); 
       if(Util.isNull(area)||Util.isNull(area.getProvCode())||Util.isNull(area.getAreaName())){
           result.setSuccess(false);
           result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
           result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
           logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),area.toString());
           return result;  
       }
       logger.info("插入地区记录：{}", area.toString());
       int cnt=areaMapper.insertArea(area);
       if(cnt>0){
           result.setSuccess(true);
           result.setT(cnt);
       }else{
           result.setSuccess(false);
           result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
           result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
           logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":插入地区失败");    
       }
       logger.debug("insertArea出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#updateArea(com.zjzmjr.core.model.area.Area)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateArea(Area area) {
        logger.debug("updateArea入参:{}",area);
        ResultEntry<Integer> result=new ResultEntry<Integer>();
        if(Util.isNull(area.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),area.toString());
            return result;   
        }
        logger.info("更新地区记录：{}", area.toString());
        int cnt=areaMapper.updateArea(area);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);  
        }
        logger.debug("updateArea出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getDist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getDist(String provCode) {
        logger.debug("getDist入参:{}",provCode);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.getAllDist(provCode);
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getDist出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getDist出参:{}",result);
            return result;
        }
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getCity(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getCity(String provCode) {
        logger.debug("getCity入参:{}",provCode);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.getCity(provCode);
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getCity出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getCity出参:{}",result);
            return result;
        }
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#dist(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultRecord<Area> dist(Area area) {
        logger.debug("dist入参:{}",area);
        ResultRecord<Area> result=new ResultRecord<Area>();
        if(Util.isNull(area)||Util.isNull(area.getProvCode())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"),area.toString());
            return result;   
        }
        logger.info("获取地区县名称：{}", area.toString()); 
        List<Area> list=areaMapper.getDist(area);
        if(list.size()>0 && list!=null){
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        logger.debug("dist出参:{}",result);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getCityDist(java.lang.String)
     */
    @Override
    public ResultRecord<Area> getCityDist(String provCode) {
        logger.debug("getCityDist入参:{}",provCode);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.getCityDist(provCode);
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getCityDist出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getCityDist出参:{}",result);
            return result;
        }
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getLoanCity(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultRecord<Area> getLoanCity(Area area) {
        logger.debug("getLoanCity入参:{}",area);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.getLoanCity(area);
        if (list.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            logger.debug("getLoanCity出参:{}",result);
            return result;
        } else {
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getLoanCity出参:{}",result);
            return result;
        }
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getLoanDist(com.zjzmjr.core.model.area.Area)
     */
    @Override
    public ResultRecord<Area> getLoanDist(Area area) {
        logger.debug("getLoanDist入参:{}",area);
        ResultRecord<Area> result=new ResultRecord<Area>();
        List<Area> list = new ArrayList<Area>();
        list = areaMapper.getLoanDist(area);
            result.setSuccess(true);
            result.setList(list);
            logger.debug("getLoanDist出参:{}",result);
            return result;
            
        
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.area.AreaService#getAllNameArea()
     */
    @Override
    public ResultRecord<Area> getAllNameArea() {
        ResultRecord<Area> result = new ResultRecord<Area>();
        List<Area> list = areaMapper.getAllNameArea();
        if(list==null || list.size()==0){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName()); 
        }else{
            result.setSuccess(true);
            result.setList(list);
        }                
        return result;
    }
}
