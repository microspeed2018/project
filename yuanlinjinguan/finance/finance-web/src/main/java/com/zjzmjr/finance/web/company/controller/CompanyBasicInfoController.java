package com.zjzmjr.finance.web.company.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.area.IAreaFacade;
import com.zjzmjr.core.api.company.ICompanyBasicInfoFacade;
import com.zjzmjr.core.api.company.ICompanyDepartmentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.KeyValuePair;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyJob;
import com.zjzmjr.finance.web.company.form.AreaForm;
import com.zjzmjr.finance.web.company.form.BasicDataForm;
import com.zjzmjr.finance.web.company.form.CompanyAssociatedInfoForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 企业内部所有的基础性数据
 * 
 * @author oms
 * @version $Id: CompanyBasicInfoController.java, v 0.1 2017-8-10 上午9:07:30 oms Exp $
 */
@Controller
@RequestMapping("/company/user")
public class CompanyBasicInfoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CompanyBasicInfoController.class);

    @Resource(name = "companyBasicInfoFacade")
    private ICompanyBasicInfoFacade companyBasicInfoFacade;

    @Resource(name = "areaFacade")
    private IAreaFacade areaFacade;
    
    @Resource(name = "departmentFacade")
    private ICompanyDepartmentFacade departmentFacade;
    
    /**
     * 获取基础数据一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getBasic.htm", method = RequestMethod.POST)
    public void getBasic(HttpServletResponse resp, BasicDataForm form) {
        logger.debug("getBasic 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BasicDataQuery query = new BasicDataQuery();
            query.setCategoryId(form.getCategoryId());
            query.setCategoryName(form.getCategoryName());
            query.setAttributeId(form.getAttributeId());
            query.setAttributeName(form.getAttributeName());
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<BasicData> result = companyBasicInfoFacade.getBasicDataByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("基础数据查询一览出错：", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
        logger.debug("getBasic 执行结束  出参:{}", model);
    }

    /**
     * 获取查询地区信息
     * 
     * @param resp
     * @param form
     */
     @RequestMapping(value = "/getAllArea.htm", method = RequestMethod.POST)
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
     * 所属部门信息
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value="/department.htm", method = RequestMethod.POST)
    public void getDepartment(HttpServletResponse resp){
    	Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery  query = new CompanyDepartmentQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setStatus(GenerateConstants.number_1);
            ResultRecord<CompanyDepartment> result = departmentFacade.getCompanyDepartmentByCondition(query);
            if(result.isSuccess()){
                model.put("department", result.getList());
                putSuccess(model, ""); 
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg()); 
            }           
        } catch (Exception ex) {
            logger.error("查询部门信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 岗位信息
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value="/jobRank.htm", method = RequestMethod.POST)
    public void getJobRank(HttpServletResponse resp,CompanyAssociatedInfoForm form){
    	Map<String, Object> model = new HashMap<String, Object>();
        try {
            CompanyDepartmentQuery query = new CompanyDepartmentQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setDepartmentId(form.getDepartmentId());
            query.setStatus(GenerateConstants.number_1);
            ResultRecord<CompanyJob> result = departmentFacade.getCompanyJobByCondition(query);
            if(result.isSuccess()){
                model.put("job", result.getList());
                putSuccess(model, "");  
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());  
            }           
        } catch (Exception ex) {
            logger.error("查询员工详细信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 获取所有的项目阶段信息
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value="/projectStep.htm", method = RequestMethod.POST)
    public void getProjectStep(ModelMap model, HttpServletResponse resp){
        try {
            List<KeyValuePair> departs = new ArrayList<KeyValuePair>();
            KeyValuePair pair = null;
            for(GardenProjectStepEnum enums : GardenProjectStepEnum.values()){
                pair = new KeyValuePair();
                pair.setKey(StringUtil.nullToString(enums.getValue()));
                pair.setValue(enums.getMessage());
                departs.add(pair);
            }
            model.put("step", departs);
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("项目阶段信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
