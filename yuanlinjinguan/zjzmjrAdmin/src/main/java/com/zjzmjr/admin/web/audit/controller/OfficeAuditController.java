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
import com.zjzmjr.core.api.audit.IGardenProjectAuditFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/officeAudit/user")
public class OfficeAuditController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OfficeAuditController.class);
    private final static String index = "/WEB-INF/pages/audit/office.jsp";

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }
    @Resource(name = "gardenProjectAuditFacade")
    private IGardenProjectAuditFacade gardenProjectAuditFacade;
    
    /**
     * 院办审核信息查询一览
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectOfficeAudit.htm", method = RequestMethod.POST)
    public void getProjectOfficeAudit(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
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
            ResultPage<GardenProjectOfficeAudit> result = gardenProjectAuditFacade.getProjectOfficeAudit(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("院办审核信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 更新院办审核
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateOfficeAuditById.htm", method = RequestMethod.POST)
    public void updateOfficeAuditById(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery office = new GardenProjectAuditQuery();
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            office.setId(form.getId());
            office.setCompanyId(SpringSecurityUtil.getIntCompany());
            office.setProjectId(form.getProjectId());           
            office.setStatus(form.getStatus());
            office.setType(form.getType());
            office.setStep(form.getStep());
            office.setSubStep(form.getSubStep());
            office.setSubStep2(form.getSubStep2());
            office.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            office.setMemo(form.getMemo());
            office.setHaveScheme(form.getHaveScheme());
            office.setHaveDevelopment(form.getHaveDevelopment());
            office.setHaveDrawing(form.getHaveDrawing());
            office.setHavePlanning(form.getHavePlanning());
            if(Util.isNotNull(form.getStep())){
                adminBusiness.setBusinessType(form.getStep()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getStep()).getMessage());
            }else if(Util.isNotNull(form.getSubStep())){
                adminBusiness.setBusinessType(form.getSubStep());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getSubStep()).getMessage());
            }else if(Util.isNotNull(form.getSubStep2())){
                adminBusiness.setBusinessType(form.getSubStep2());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getSubStep2()).getMessage());
            }
            if(84==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_822.getValue()); 
            }else if(56==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_390.getValue());
            }
            if(ProjectTableStatusEnum.VERIFIED.getValue().equals(form.getStatus())){
                adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED.getMessage());
            }else if(ProjectTableStatusEnum.VERIFIED_FAIL.getValue().equals(form.getStatus())){
                if(Util.isNotNull(form.getMemo())){
                    adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED_FAIL.getMessage()+":"+form.getMemo());  
                }else{
                    adminBusiness.setComment(ProjectTableStatusEnum.VERIFIED_FAIL.getMessage());                    
                }                
            }
            adminBusiness.setProjectId(form.getProjectId());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = gardenProjectAuditFacade.updateOfficeAudit(office);
            if(result.isSuccess()){              
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                // 发送消息
                MessageHandlerUtil.insertMessage(office);
            } else {               
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("院办审核修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
