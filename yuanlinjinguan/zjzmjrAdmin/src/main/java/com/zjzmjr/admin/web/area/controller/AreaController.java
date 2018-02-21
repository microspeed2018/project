package com.zjzmjr.admin.web.area.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.area.form.AreaForm;
import com.zjzmjr.core.api.area.IAreaFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 后台地区管理控制层
 * 
 * @author lenovo
 * @version $Id: AreaController.java, v 0.1 2016-7-29 下午3:17:16 lenovo Exp $
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AreaController.class);
    
    @Resource(name = "areaFacade")
    private IAreaFacade areaFacade;
    
    private final static String index = "/WEB-INF/pages/area/area.jsp";
    
    @RequestMapping("/index.htm")
    public String index(ModelMap model) {

        return index;
    }
    
   /**
    * 获取查询地区信息
    * 
    * @param resp
    * @param form
    */
    @RequestMapping(value = "/user/getAllArea.htm", method = RequestMethod.POST)
    public void getAllArea(HttpServletResponse resp,@Valid AreaForm form){
        logger.debug("getAllArea 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            AreaQuery query=new AreaQuery();
            query.setId(form.getId());
            query.setAreaName(form.getAreaName());
            query.setProvCode(form.getProvCode());
            query.setDistCode(form.getDistCode());
            query.setCityCode(form.getCityCode());
            PageBean pageBean=new PageBean(form.getRows(),form.getPage());
            query.setPageBean(pageBean);
            ResultPage<Area> result=areaFacade.getAllArea(query);
            logger.debug("getAllArea 执行结束  出参:{}", result);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());  
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("地区信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增、修改地区
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/insertArea.htm", method = RequestMethod.POST)
    public void insertArea(HttpServletResponse resp,@Valid AreaForm form,HttpServletRequest req){
       logger.debug("insertArea 执行开始  入参:{}", form);
       Map<String, Object> model = new HashMap<String, Object>(); 
       ResultEntry<Integer> result=new ResultEntry<Integer>();
       try {
         Area area=new Area();
         Assert.isTrue(form.getAreaName() != null, "地区名称不能为空");
         Assert.isTrue(form.getProvCode() != null, "省级代码不能为空");
         area.setAreaName(form.getAreaName());
         area.setProvCode(form.getProvCode());
         area.setCityCode(form.getCityCode());
         area.setDistCode(form.getDistCode());
         AdminBusiness adminBusiness=new AdminBusiness();
         ResultEntry<AdminBusiness> val=new ResultEntry<AdminBusiness>();
         if(form.getId()==null){
         area.setCreateTime(new Date());
         area.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
         //新增管理员事物
//         adminBusiness.setBusinessType(AdminBusinessTypeEnum.INSERT_AREA.getValue());
         String message=StringUtils.isEmpty(form.getAreaName())?"未输入地区名称":"地区名称："+form.getAreaName();
         adminBusiness.setExtend1(message);
         val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
         result=areaFacade.insertArea(area);
         }else{
           area.setId(form.getId());
           area.setUpdateTime(new Date());
           area.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
           //新增管理员事物
//           adminBusiness.setBusinessType(AdminBusinessTypeEnum.MODIFY_AREA.getValue());
           String message=StringUtils.isEmpty(form.getAreaName())?"未输入地区名称":"地区名称："+form.getAreaName();
           adminBusiness.setExtend1(message);
           val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
           result=areaFacade.updateArea(area);
           
         }
         logger.debug("insertArea 执行结束  出参:{}", result);
         if(result.isSuccess()){
             putSuccess(model, "");
             adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
         } else {
             putError(model, result.getErrorCode(), result.getErrorMsg());
             adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
             adminBusiness.setComment("错误消息："+result.getErrorMsg());
         }
         //更新管理员事物
         adminBusiness.setId(val.getT().getId());
         AdminTransactionUtil.updateAdminTransaction(adminBusiness);
    } catch (Exception ex) {
        logger.error("插入地区出错：", ex);
        putError(model, ex.getMessage());
    }
       responseAsJson(model, resp);
    }
    
    /**
     * 获取所有省份
     * 
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/user/getAllProv.htm", method=RequestMethod.POST)
    public void getAllProv( HttpServletRequest req, HttpServletResponse resp){
        logger.debug("getAllProv 执行开始  入参:{}");
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            ResultRecord<Area> result=areaFacade.getAllProv();
            logger.debug("getAllProv 执行结束  出参:{}", result);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取省失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取所有市县
     * 
     * @param model
     * @param req
     * @param resp
     */
    @RequestMapping(value="/user/getAllDist.htm", method=RequestMethod.POST)
    public void getAllDist( HttpServletRequest req, HttpServletResponse resp,@Valid AreaForm form) {
        logger.debug("getAllDist 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {          
            ResultRecord<Area> result = areaFacade.Dist(form.getProvCode());
            logger.debug("getAllDist 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取市县失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取地区市
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value="/user/getCity.htm", method=RequestMethod.POST)
    public void getCity( HttpServletRequest req, HttpServletResponse resp,@Valid AreaForm form) {
        logger.debug("getCity 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            ResultRecord<Area> result = areaFacade.getCity(form.getProvCode());
            logger.debug("getCity 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取市县失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取地区县
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value="/user/getdist.htm", method=RequestMethod.POST)
    public void dist( HttpServletRequest req, HttpServletResponse resp,@Valid AreaForm form) {
        logger.debug("dist 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            Area area=new Area();
            area.setProvCode(form.getProvCode());
            area.setCityCode(form.getCityCode());
            ResultRecord<Area> result = areaFacade.dist(area);
            logger.debug("dist 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取市县失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取地区市(有空行)
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value="/user/getCityDist.htm", method=RequestMethod.POST)
    public void getCityDist( HttpServletRequest req, HttpServletResponse resp,@Valid AreaForm form) {
        logger.debug("getCityDist 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            ResultRecord<Area> result = areaFacade.getCityDist(form.getProvCode());
            logger.debug("getCityDist 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取市县失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

}


