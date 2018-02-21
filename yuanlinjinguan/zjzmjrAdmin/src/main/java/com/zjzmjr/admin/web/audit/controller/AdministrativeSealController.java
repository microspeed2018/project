package com.zjzmjr.admin.web.audit.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.audit.form.AuditForm;
import com.zjzmjr.core.api.audit.IAdministrativeSealFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.GardenProjectAdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 行政盖章控制层
 * 
 * @author lenovo
 * @version $Id: AdministrativeSealController.java, v 0.1 2017-9-2 下午1:52:00 lenovo Exp $
 */
@Controller
@RequestMapping("/administrativeSeal/user")
public class AdministrativeSealController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeSealController.class);
    
    private final static String index = "/WEB-INF/pages/audit/seal.jsp";

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }
    
    @Resource(name = "administrativeSealFacade")
    private IAdministrativeSealFacade administrativeSealFacade;
    
    /**
     * 行政盖章一览
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectAdministrativeSeal.htm", method = RequestMethod.POST)
    public void getProjectAdministrativeSeal(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery query = new GardenProjectAuditQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }
            //项目名称
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());  
            }
            if(Util.isNotNull(form.getAdminName())){
                query.setAdminName(form.getAdminName()); 
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart()); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd());                
            }
            if(Util.isNotNull(form.getType())){
                query.setType(form.getType());
            }
            if(Util.isNotNull(form.getStatus())){
                query.setStatus(form.getStatus()); 
            }
            if(Util.isNotNull(form.getVerifyTimeStart())){
                query.setVerifyTimeStart(form.getVerifyTimeStart());
            }
            if(Util.isNotNull(form.getVerifyTimeEnd())){
                query.setVerifyTimeEnd(form.getVerifyTimeEnd());
            }   
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<GardenProjectAdministrativeSeal> result = administrativeSealFacade.getProjectAdministrativeSeal(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("行政盖章信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 行政盖章修改
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateAdministrativeSeal.htm", method = RequestMethod.POST)
    public void updateAdministrativeSeal(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery administrativeSeal = new GardenProjectAuditQuery();
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();    
            administrativeSeal.setCompanyId(SpringSecurityUtil.getIntCompany());
            administrativeSeal.setProjectId(form.getProjectId());           
            administrativeSeal.setStatus(form.getStatus());
            administrativeSeal.setType(form.getType());
            administrativeSeal.setStep(form.getStep());
            administrativeSeal.setSubStep(form.getSubStep());
            administrativeSeal.setSubStep2(form.getSubStep2());
            administrativeSeal.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            administrativeSeal.setHavaTechnical(form.getHaveTechnical());
            administrativeSeal.setMemo(form.getMemo()); 
            if(Util.isNotNull(form.getStep()) && Util.isNotNull(form.getSubStep()) && Util.isNotNull(form.getSubStep2())){
                if(form.getType()==34){
                    adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_230.getValue());
                   // adminBusiness.setComment(AdminBusinessTypeEnum.B_230.getMessage());
                }else if(form.getType()==35){
                    adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_270.getValue());
                    //adminBusiness.setComment(AdminBusinessTypeEnum.B_270.getMessage());
                }
            }else if(Util.isNotNull(form.getStep())){
                adminBusiness.setBusinessType(form.getStep()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getStep()).getMessage());
            }
            adminBusiness.setComment("已盖章");
            adminBusiness.setProjectId(form.getProjectId());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = administrativeSealFacade.updateAdministrativeSealAndProjectStep(administrativeSeal);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                // 发送消息
                MessageHandlerUtil.insertMessage(administrativeSeal);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("行政盖章修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
