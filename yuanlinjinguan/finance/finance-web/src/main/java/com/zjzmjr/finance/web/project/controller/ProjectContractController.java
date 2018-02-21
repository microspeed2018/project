package com.zjzmjr.finance.web.project.controller;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.project.IProjectContractFacade;
import com.zjzmjr.core.api.project.IProjectContractInfoFacade;
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
import com.zjzmjr.core.enums.project.ProjectSubpackageStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;
import com.zjzmjr.finance.web.project.form.ProjectContractForm;
import com.zjzmjr.finance.web.project.form.SubpackageForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 项目中所有合同业务处理的控制层
 * 
 * @author oms
 * @version $Id: ProjectContractController.java, v 0.1 2017-8-24 下午7:54:37 oms
 *          Exp $
 */
@Controller
@RequestMapping("/contract/user")
public class ProjectContractController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractController.class);

    @Resource(name = "projectContractFacade")
    private IProjectContractFacade projectContractFacade;
    
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    @Resource(name = "projectContractInfoFacade")
    private IProjectContractInfoFacade projectContractInfoFacade;
    /**
     * 获取项目中合同信息一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractList.htm", method = RequestMethod.POST)
    public void getContractList(HttpServletResponse resp, ProjectContractForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(form.getProjectId());
            query.setCashierType(11);
            query.setContractNo(form.getContractNo());
            query.setManagePerson(form.getManagePerson());
            query.setProjectLeader(form.getProjectLeader());
            query.setProjectName(form.getProjectName());
            query.setProjectNo(form.getProjectNo());
            query.setStatus(form.getStatus());
            if(Util.isNotNull(form.getSubpackageStatus())){
                query.setSubpackageStatus(form.getSubpackageStatus());
            }else{
                query.setSubpackageStatus(GenerateConstants.number_1);
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ProjectContractInfo> result = projectContractFacade.getProjectContractByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("项目合同信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 根据合同付款表信息查询出开发票信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractPayment.htm", method = RequestMethod.POST)
    public void getContractPayment(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractPaymentQuery query = new ContractPaymentQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectLeader(SpringSecurityUtil.getIntPrincipal());
            if(StringUtils.isNotBlank(form.getProjectName())){
                query.setProjectName(form.getProjectName());
            }
            if(Util.isNotNull(form.getProjectId())){
                query.setProjectId(form.getProjectId());
            }
            // 审核开发票
            query.setVerifyType(28);
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<ContractPaymentInfo> result = projectContractFacade.getProjectContractPaymentInfo(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("项目合同付款信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 删除分包信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/deleteSubpackageByCondition.htm", method = RequestMethod.POST)
    public void deleteSubpackageByCondition(HttpServletResponse resp, SubpackageForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setId(form.getId());
            query.setStep(form.getStep());
            query.setUpdateTime(new Date());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            query.setProjectId(form.getProjectId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = projectContractInfoFacade.deleteSubpackageByCondition(query);
            if(result.isSuccess()){
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("项目分包删除出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 修改分包信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateContractSubpackageById.htm", method = RequestMethod.POST)
    public void updateContractSubpackageById(HttpServletResponse resp, SubpackageForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractSubpackage record = new ContractSubpackage();
            int flag = 0;
            record.setId(form.getId());
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setSubpackageLeader(form.getSubpackageLeader());
            if(Util.isNotNull(form.getSubpackageCapital())){
                record.setSubpackageCapital(new BigDecimal(form.getSubpackageCapital())); 
            }
            record.setSubpackageContent(form.getSubpackageContent());
            record.setSubpackageMemo(form.getSubpackageMemo());
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setPaymentMemo(form.getPaymentMemo()); 
            List<SubpackagePayment> payLst = new ArrayList<SubpackagePayment>();
            if(Util.isNotNull(form.getPayModes()) && Util.isNotNull(form.getPayAmounts())){
                String[] modes = form.getPayModes().split(",");  
                String[] amounts = form.getPayAmounts().split(",");
                BigDecimal contractPay = BigDecimal.ZERO;
                for(int i=0;i<modes.length;i++){
                    SubpackagePayment payment = new SubpackagePayment();
                    if(Util.isNotNull(modes[i])){
                      //查询合同付款金额
                        ContractPaymentQuery contractPaymentQuery = new ContractPaymentQuery();
                        contractPaymentQuery.setProjectId(form.getProjectId());
                        contractPaymentQuery.setCompanyId(SpringSecurityUtil.getIntCompany());
                        contractPaymentQuery.setPaymentMode(Integer.parseInt(modes[i]));
                        contractPaymentQuery.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                        ResultEntry<ContractPayment> payResult = projectContractFacade.getContractPaymentByCondition(contractPaymentQuery);
                        if(payResult.isSuccess()){
                            contractPay = payResult.getT().getPaymentAmount();
                        }else{
                            flag = GenerateConstants.number_3;
                            break;
                        }
                        payment.setPaymentMode(Integer.parseInt(modes[i])); 
                    }
                    if(Util.isNotNull(amounts[i])){
                        if(contractPay.compareTo(new BigDecimal(amounts[i]))<0){
                            flag = GenerateConstants.number_2;
                            break; 
                        }
                        payment.setPaymentAmount(new BigDecimal(amounts[i]));
                    }
                    payLst.add(payment);
                }
            }
            if(GenerateConstants.number_2.equals(flag)){
                putError(model, "-1","同一种付款阶段分包付款金额不得大于合同付款金额");
            }else if(GenerateConstants.number_3.equals(flag)){
                putError(model, "-1","合同中无此类付款方式");
            }else{
                ResultEntry<Integer> result = projectContractInfoFacade.updateContractSubpackageById(record, payLst);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                }
            }
        } catch (Exception ex) {
            logger.error("项目分包修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 申请合作比例
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/applyRatio.htm", method = RequestMethod.POST)
    public void applyRatio(HttpServletResponse resp, ProjectContractForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectContract contract = new ProjectContract();
            contract.setCompanyId(SpringSecurityUtil.getIntCompany());
            contract.setProjectId(form.getProjectId());
            contract.setStep(form.getStep());
            contract.setContractNo(form.getContractNo());
            if(Util.isNotNull(form.getContractCapital())){
                contract.setContractCapital(new BigDecimal(form.getContractCapital()));
            }  
            if(Util.isNotNull(form.getRatio())){
                contract.setRatio(new BigDecimal(form.getRatio()));  
            }
            contract.setRatioMemo(form.getRatioMemo());
            contract.setUpdateTime(new Date());
            contract.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getProjectId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_380.getValue());
            ResultEntry<Integer> result = projectContractFacade.applyRatio(contract);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                GardenProjectQuery query = new GardenProjectQuery();
                query.setId(form.getProjectId());
                query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
                if(pjRst.isSuccess()){
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_740.getValue());
                    message.setAddress(MessageContextEnum.Msg_740.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_740.getMessage(), 
                            pjRst.getList().get(0).getName()));
                    // 技术负责人员
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getYuanBanPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(pjRst.getList().get(0).getName());
                    pointSymbol.setAuditType("合作比例拟定");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_740.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getYuanBanPersons());
                }                
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("申请合作比例出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取分包付款信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/subpackagePayment.htm", method = RequestMethod.POST)
    public void getSubpackagePayment(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractSubpackageQuery query = new ContractSubpackageQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectLeader(SpringSecurityUtil.getIntPrincipal());
            if(StringUtils.isNotBlank(form.getProjectName())){
                query.setProjectName(form.getProjectName());
            }
            if(Util.isNotNull(form.getProjectId())){
                query.setProjectId(form.getProjectId());
            }
            // 审核项目支出
            query.setVerifyType(29);
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<SubpackagePaymentInfo> result = projectContractInfoFacade.getSubpackagePaymentByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取分包付款信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增合同
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/insertContract.htm", method = RequestMethod.POST)
    public void insertContract(HttpServletResponse resp, ProjectContractForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getProjectId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_819.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_819.getMessage());
            ProjectContract projectContract = new  ProjectContract();
            projectContract.setCompanyId(SpringSecurityUtil.getIntCompany());
            projectContract.setProjectId(form.getProjectId());
            projectContract.setContractNo(form.getContractNo());
            if(Util.isNotNull(form.getContractCapital())){                
                projectContract.setContractCapital(new BigDecimal(form.getContractCapital()));
            }
            projectContract.setContractMemo(form.getContractMemo());
            if(Util.isNotNull(form.getRatio())){               
                projectContract.setRatio(new BigDecimal(form.getRatio()));
            }
            projectContract.setSignDate(form.getSignDate().replace("/", ""));
            projectContract.setRatioMemo(form.getRatioMemo());
            projectContract.setStatus(GenerateConstants.number_1);
            projectContract.setUpdateTime(new Date());
            projectContract.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            GardenProject project = new GardenProject();
            if(GenerateConstants.number_1.equals(form.getProjectUpdate())){
                project.setId(form.getProjectId());
                project.setCompanyId(SpringSecurityUtil.getIntCompany());
                project.setContractAwardCompany(form.getContractAwardCompany());
                project.setCity(form.getCity());
                project.setName(form.getName());
                project.setAddress(form.getAddress());
                project.setCategory(form.getCategory());
                project.setType(form.getType());
                project.setDesignArea(form.getDesignArea());
                if(Util.isNotNull(form.getInvestmentMount())){
                    project.setInvestmentMount(new BigDecimal(form.getInvestmentMount()));
                }
                project.setHaveScheme(form.getHaveScheme());
                project.setHaveDrawing(form.getHaveDrawing());
                project.setHavePlanning(form.getHavePlanning());
                project.setHaveDevelopment(form.getHaveDevelopment());
                project.setTemporaryId(form.getTemporaryId());
                project.setVersion(form.getVersion());
            }            
            List<ContractPayment> list = new ArrayList<ContractPayment>();
            if(Util.isNotNull(form.getPaymentType()) && Util.isNotNull(form.getPaymentPrice())){
                String[] modes = form.getPaymentType().split(",");  
                String[] amounts = form.getPaymentPrice().split(",");
                for(int i=0;i<modes.length;i++){
                	ContractPayment payment = new ContractPayment();
                    if(Util.isNotNull(modes[i])){
                        payment.setPaymentMode(Integer.parseInt(modes[i])); 
                    }
                    if(Util.isNotNull(amounts[i])){
                        payment.setPaymentAmount(new BigDecimal(amounts[i]));
                    }
                    list.add(payment);
                }
            }
            ResultEntry<Integer> result =  projectContractFacade.generateProjectContractInfo(projectContract,project,list);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("项目合同新增修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取合同编号最大值
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractMaxNo.htm", method = RequestMethod.POST)
    public void getContractMaxNo(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setContractNo(form.getContractNo());
            ResultEntry<String> result = projectContractFacade.getContractMaxNo(query);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取合同编号最大值出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 修改合同
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/updateContract.htm", method = RequestMethod.POST)
    public void updateContract(HttpServletResponse resp, ProjectContractForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getProjectId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_819.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_819.getMessage());
            ProjectContract projectContract = new  ProjectContract();
            projectContract.setId(form.getId());
            projectContract.setProjectId(form.getProjectId());
            projectContract.setVersion(form.getContractVersion());
            projectContract.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(GenerateConstants.number_1.equals(form.getContractUpdate())){
                projectContract.setUpdateTime(new Date());
                projectContract.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                projectContract.setContractNo(form.getContractNo());
                if(Util.isNotNull(form.getContractCapital())){                
                    projectContract.setContractCapital(new BigDecimal(form.getContractCapital()));
                }
                projectContract.setContractMemo(form.getContractMemo());
                if(Util.isNotNull(form.getRatio())){               
                    projectContract.setRatio(new BigDecimal(form.getRatio()));
                }
                projectContract.setSignDate(form.getSignDate().replace("/", ""));
                projectContract.setRatioMemo(form.getRatioMemo());
                projectContract.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
            }            
            GardenProject project = new GardenProject();
            if(GenerateConstants.number_1.equals(form.getProjectUpdate())){
                project.setId(form.getProjectId());
                project.setContractAwardCompany(form.getContractAwardCompany());
                project.setCity(form.getCity());
                project.setName(form.getName());
                project.setAddress(form.getAddress());
                project.setCategory(form.getCategory());
                project.setType(form.getType());
                project.setDesignArea(form.getDesignArea());
                if(Util.isNotNull(form.getInvestmentMount())){
                    project.setInvestmentMount(new BigDecimal(form.getInvestmentMount()));
                }
                project.setHaveScheme(form.getHaveScheme());
                project.setHaveDrawing(form.getHaveDrawing());
                project.setHavePlanning(form.getHavePlanning());
                project.setHaveDevelopment(form.getHaveDevelopment());  
            } 
            List<ContractPayment> list = null; 
            if(GenerateConstants.number_1.equals(form.getPaymentUpdate())){            	
                if(Util.isNotNull(form.getPaymentType()) && Util.isNotNull(form.getPaymentPrice())){
                	list = new ArrayList<ContractPayment>();
                    String[] modes = form.getPaymentType().split(",");  
                    String[] amounts = form.getPaymentPrice().split(",");
                    for(int i=0;i<modes.length;i++){
                        ContractPayment payment = new ContractPayment();
                        if(Util.isNotNull(modes[i])){
                            payment.setPaymentMode(Integer.parseInt(modes[i])); 
                        }
                        if(Util.isNotNull(amounts[i])){
                            payment.setPaymentAmount(new BigDecimal(amounts[i]));
                        }
                        list.add(payment);
                    }
                }
                projectContract.setUpdateTime(new Date());
                projectContract.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                projectContract.setContractNo(form.getContractNo());   
                projectContract.setSignDate(form.getSignDate().replace("/", ""));
                if(Util.isNotNull(form.getContractCapital())){                
                    projectContract.setContractCapital(new BigDecimal(form.getContractCapital()));
                }
            }
            ResultEntry<Integer> result =  projectContractFacade.updateProjectContractInfo(projectContract, project, list);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                GardenProjectQuery query = new GardenProjectQuery();
                query.setId(form.getId());
                query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
                if(pjRst.isSuccess()){
                    Message message = new Message();
                    message.setTitle(MessageContextEnum.Msg_950.getValue());
                    message.setAddress(MessageContextEnum.Msg_950.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_950.getMessage(), 
                            pjRst.getList().get(0).getName()));
                    MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(pjRst.getList().get(0).getName());
                    pointSymbol.setAuditType("合同修改");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_950.getTemplateCode()); 
                    MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
                }                
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("项目合同新增修改出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 分包信息查询
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getContractSubpackageInfo.htm", method = RequestMethod.POST)
    public void getContractSubpackageInfo(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractSubpackageQuery query = new ContractSubpackageQuery();
            query.setId(form.getId());
            query.setProjectId(form.getProjectId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            List<Integer> packageStatus = new ArrayList<Integer>();
            packageStatus.add(ProjectSubpackageStatusEnum.NOT_VERIFY.getValue());
            packageStatus.add(ProjectSubpackageStatusEnum.VERIFIED.getValue());
            packageStatus.add(ProjectSubpackageStatusEnum.INSERT.getValue());
            packageStatus.add(ProjectSubpackageStatusEnum.UPDATE.getValue());
            query.setSubPackageStatus(packageStatus);
            ResultRecord<ContractSubpackageInfo> result = projectContractInfoFacade.getContractSubpackageInfo(query);
            if(result.isSuccess()){
               model.put("data", result.getList());
               putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("分包信息查询出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
