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
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;
import com.zjzmjr.finance.web.audit.form.AuditForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 出纳确认控制层
 * 
 * @author lenovo
 * @version $Id: CashierConfirmationController.java, v 0.1 2017-9-1 下午5:01:06 lenovo Exp $
 */
@Controller
@RequestMapping("/cashierConfirmation/user")
public class CashierConfirmationController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CashierConfirmationController.class);

    @Resource(name = "gardenProjectAuditFacade")
    private IGardenProjectAuditFacade gardenProjectAuditFacade;
    
    /**
     * 出纳确认一览
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectCashierConfirmation.htm", method = RequestMethod.POST)
    public void getProjectCashierConfirmation(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
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
            query.setPageBean(new PageBean(Integer.MAX_VALUE,GenerateConstants.number_1));
            ResultPage<GardenProjectCashierConfirmation> result = gardenProjectAuditFacade.getProjectCashierConfirmation(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("出纳确认信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 出纳确认修改
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateCashierConfirmationById.htm", method = RequestMethod.POST)
    public void updateCashierConfirmationById(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectAuditQuery cashierConfirmation = new GardenProjectAuditQuery();
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setProjectId(form.getProjectId());
            cashierConfirmation.setId(form.getId());
            cashierConfirmation.setCompanyId(SpringSecurityUtil.getIntCompany());
            cashierConfirmation.setProjectId(form.getProjectId());           
            cashierConfirmation.setStatus(form.getStatus());
            cashierConfirmation.setType(form.getType());
            cashierConfirmation.setStep(form.getStep());
            cashierConfirmation.setSubStep(form.getSubStep());
            cashierConfirmation.setSubStep2(form.getSubStep2());
            cashierConfirmation.setHavaTechnical(form.getHaveTechnical());
            cashierConfirmation.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            cashierConfirmation.setMemo(form.getMemo());  
            if (form.getType() == 31) {
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_815.getValue()); 
                //adminBusiness.setComment(AdminBusinessTypeEnum.B_815.getMessage());
            } else if (form.getType() == 32) {
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_813.getValue());
               // adminBusiness.setComment(AdminBusinessTypeEnum.B_813.getMessage());
            } else if(form.getType() == 87){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_825.getValue()); 
            } else if (Util.isNotNull(form.getSubStep())) {
                adminBusiness.setBusinessType(form.getSubStep());
                //adminBusiness.setComment(AdminBusinessTypeEnum.getByValue(form.getSubStep()).getMessage());
            } 
            adminBusiness.setComment("已确认");
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = gardenProjectAuditFacade.updateCashierConfirmation(cashierConfirmation);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                // 发送消息
                cashierConfirmation.setAmount(new BigDecimal(result.getT()));
                MessageHandlerUtil.insertMessage(cashierConfirmation);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("出纳确认修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 出纳确认数量
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectCashierConfirmationCount.htm", method = RequestMethod.POST)
    public void getProjectCashierConfirmationCount(HttpServletResponse resp,AuditForm form,HttpServletRequest req){
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
            ResultEntry<Integer> result = gardenProjectAuditFacade.getProjectCashierConfirmationCount(query);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("出纳确认数量出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
