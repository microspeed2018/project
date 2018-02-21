package com.zjzmjr.finance.web.project.controller;

import java.text.MessageFormat;
import java.util.Collections;
import java.util.Date;
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

import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectDisplayRule;
import com.zjzmjr.finance.web.project.form.DisplayRuleForm;
import com.zjzmjr.finance.web.project.form.GardenProjectForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 
 * 
 * @author oms
 * @version $Id: GardenProjectController.java, v 0.1 2017-8-14 下午1:31:57 oms Exp $
 */
@Controller
@RequestMapping("/project/user")
public class GardenProjectController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectController.class);

    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    /**
     * 根据条件获取项目信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.POST)
    public void list(HttpServletResponse resp, GardenProjectForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            if (Util.isNotNull(form.getId())) {
                query.setId(form.getId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());
            }
            if (Util.isNotNull(form.getProjectNo())) {
                query.setProjectNo(form.getProjectNo());
            }
            if (Util.isNotNull(form.getStatus())) {
                query.setStatus(form.getStatus());
            }
            if (Util.isNotNull(form.getStep())) {
                query.setStep(form.getStep());
            }
            if(Util.isNotNull(form.getMinStep())){
                // 查出最小的step
                query.setStep(form.getMinStep());
                query.setMinStep(form.getMinStep());
            }
            if(Util.isNull(form.getWorkType()) || form.getWorkType().intValue() != 1){
                query.setSubStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<GardenProjectInfo> result = gardenProjectFacade.getGardenProjectByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
            } else {
//                putError(model, result.getErrorCode(), result.getErrorMsg());
                model.put("rows", Collections.<GardenProjectInfo>emptyList());
                model.put("total", 0);
            }
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("项目信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取项目一览显示规则
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/displayOrder.htm", method = RequestMethod.POST)
    public void getDisplayOrder(ModelMap model, HttpServletResponse resp){
        try {
            ResultEntry<ProjectDisplayRule> result = gardenProjectFacade.getProjectDisplayRuleByUserId(SpringSecurityUtil.getIntPrincipal());
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取项目一览显示规则出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 操作项目一览显示规则
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/makeDisplayOrder.htm", method = RequestMethod.POST)
    public void generateDisplayOrder(HttpServletResponse resp, DisplayRuleForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectDisplayRule record = new ProjectDisplayRule();
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setUserId(SpringSecurityUtil.getIntPrincipal());
            // 项目编号
            record.setId(form.getProjectId());
            // 1 倒序  2 升序
            record.setTimeOrder(form.getTimeOrder());
            record.setProjectLeader(form.getProjectLeader());
            record.setManagePerson(form.getManagePerson());
            record.setProjectStep(form.getProjectStep());
            record.setStatus(form.getStatus());
            record.setCreateTime(new Date());
            record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setUpdateTime(record.getCreateTime());
            record.setUpdateUserId(record.getCreateUserId());
            ResultEntry<Integer> result = gardenProjectFacade.generateProjectDisplayRule(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("操作项目一览显示规则出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 更新项目状态(中止、复活、终止)
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateProjectStatus.htm", method = RequestMethod.POST)
    public void updateProject(HttpServletResponse resp, GardenProjectForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProject project = new GardenProject();
           //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();  
            adminBusiness.setProjectId(form.getId());
            project.setId(form.getId());
            project.setStatus(form.getStatus());
            if(GardenProjectStatusEnum.NOT_VERIFY.getValue().equals(form.getStatus())){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_620.getValue());
            }else if(GardenProjectStatusEnum.SUSPEND.getValue().equals(form.getStatus())){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_610.getValue());
            }else if(GardenProjectStatusEnum.END.getValue().equals(form.getStatus())){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_630.getValue());
            }else if(GardenProjectStepEnum.P_520.getValue().equals(form.getStatus())){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_520.getValue());
                project.setStatus(null);
                project.setStep(GardenProjectStepEnum.P_530.getValue());
            }
            project.setUpdateTime(new Date());
            project.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = gardenProjectFacade.updateGardenProjectById(project);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            } 
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("项目信息更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 查询项目详细信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getGardenProjectDetail.htm", method = RequestMethod.POST)
    public void getGardenProjectDetail(HttpServletResponse resp, GardenProject form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setStatus(form.getStatus());
            query.setProjectNo(form.getProjectNo());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            ResultEntry<GardenProjectInfo> result = gardenProjectFacade.getProjectDetail(query);
            if(result.isSuccess()){
                model.put("rows", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询项目详细信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 更新项目信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateProject.htm", method = RequestMethod.POST)
    public void update(HttpServletResponse resp, GardenProject form ,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try { 
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            if(form.getStep() > GardenProjectStepEnum.P_20.getValue()){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_818.getValue());
            }else if(Util.isNull(form.getOperation())){
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_10.getValue());
            }else{
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_05.getValue());
            }
            adminBusiness.setProjectId(form.getId());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_818.getMessage());
            form.setUpdateTime(new Date());
            if(Util.isNull(form.getHaveScheme())){
                form.setHaveScheme(GenerateConstants.number_0);
            }
            if(Util.isNull(form.getHaveDevelopment())){
                form.setHaveDevelopment(GenerateConstants.number_0);
            }
            if(Util.isNull(form.getHaveDrawing())){
                form.setHaveDrawing(GenerateConstants.number_0);
            }
            if(Util.isNull(form.getHavePlanning())){
                form.setHavePlanning(GenerateConstants.number_0);
            }
            form.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            form.setCompanyId(SpringSecurityUtil.getIntCompany());
            ResultEntry<Integer> result = gardenProjectFacade.updateGardenProject(form);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                if (form.getStep() > GardenProjectStepEnum.P_20.getValue()) {
                    GardenProjectQuery query = new GardenProjectQuery();
                    query.setId(form.getId());
                    query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
                    if (pjRst.isSuccess()) {
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_940.getValue());
                        message.setAddress(MessageContextEnum.Msg_940.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_940.getMessage(), pjRst.getList().get(0).getName()));
                        MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(pjRst.getList().get(0).getName());
                        pointSymbol.setAuditType("项目修改");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_940.getTemplateCode()); 
                        MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                    }
                }else if(Util.isNull(form.getOperation())){
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.projectInput.getValue());
                    message.setAddress(MessageContextEnum.projectInput.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.projectInput.getMessage(), form.getName()));
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(form.getName());
                    pointSymbol.setTemplateCode(MessageContextEnum.projectInput.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                }
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("项目信息更新出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 查询未录入合同的项目信息
     * 
     * @param resp
     */
    @RequestMapping(value = "/getGardenProjectNoContract.htm", method = RequestMethod.POST)
    public void getGardenProjectNoContract(HttpServletResponse resp){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultRecord<GardenProject> result = gardenProjectFacade.getGardenProjectNoContract();
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询未录入合同的项目信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
