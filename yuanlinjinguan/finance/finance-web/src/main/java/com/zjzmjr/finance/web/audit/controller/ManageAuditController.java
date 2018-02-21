package com.zjzmjr.finance.web.audit.controller;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.audit.IGardenProjectAuditFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
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
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.finance.web.audit.form.AuditForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/manageAudit/user")
public class ManageAuditController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ManageAuditController.class);

    @Resource(name = "gardenProjectAuditFacade")
    private IGardenProjectAuditFacade gardenProjectAuditFacade;
    
    
    /**
     * 经营审核修改
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateManageAudit.htm", method = RequestMethod.POST)
    public void updateManageAudit(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery manage = new GardenProjectAuditQuery();
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();  
            adminBusiness.setProjectId(form.getProjectId());
            manage.setCompanyId(SpringSecurityUtil.getIntCompany());
            manage.setProjectId(form.getProjectId());           
            manage.setStatus(form.getStatus());
            if(Util.isNotNull(form.getAmount())){
                manage.setAmount(new BigDecimal(form.getAmount()));
            }
            manage.setStep(form.getStep());
            manage.setSubStep(form.getSubStep());
            manage.setType(form.getType());
            manage.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            manage.setMemo(form.getMemo());  
            if(Util.isNotNull(form.getStep())){
                adminBusiness.setBusinessType(form.getStep()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getStep()).getMessage());
                if(49==form.getType()){
                    manage.setIschief(form.getIschief());
                }
            }else if(Util.isNotNull(form.getSubStep())){
                adminBusiness.setBusinessType(form.getSubStep());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getSubStep()).getMessage());
            }else if(50==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_810.getValue()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_810.getMessage());
            }else if(51==form.getType()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_811.getValue()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_811.getMessage());
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
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = gardenProjectAuditFacade.updateManageAudit(manage);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                // 发送消息
                MessageHandlerUtil.insertMessage(manage);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("经营审核修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 经营审核信息一览
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectManageAudit.htm", method = RequestMethod.POST)
    public void getProjectManageAudit(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
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
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<GardenProjectManageAudit> result = gardenProjectAuditFacade.getProjectManageAudit(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("经营审核信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 经营审核数量
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectManagerAuditCount.htm", method = RequestMethod.POST)
    public void getProjectManagerAuditCount(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery query = new GardenProjectAuditQuery();
            query.setId(form.getId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if (Util.isNotNull(form.getProjectNo())) {
                query.setProjectNo(form.getProjectNo());
            }
            // 项目名称
            if (Util.isNotNull(form.getName())) {
                query.setName(form.getName());
            }
            query.setStatus(GenerateConstants.number_0);
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultEntry<Integer> result = gardenProjectAuditFacade.getProjectManageAuditCount(query);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("经营审核数量出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
