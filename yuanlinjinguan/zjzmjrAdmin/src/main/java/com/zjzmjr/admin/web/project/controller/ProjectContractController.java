package com.zjzmjr.admin.web.project.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.project.form.ProjectContractForm;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.project.IProjectContractFacade;
import com.zjzmjr.core.api.project.IProjectContractInfoFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.NumberUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 项目中所有合同业务处理的控制层
 * 
 * @author oms
 * @version $Id: ProjectContractController.java, v 0.1 2017-8-24 下午7:54:37 oms Exp $
 */
@Controller
@RequestMapping("/contract/user")
public class ProjectContractController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ProjectContractController.class);

    private final static String index = "/WEB-INF/pages/project/contract.jsp";

    @Resource(name = "projectContractFacade")
    private IProjectContractFacade projectContractFacade;
           
    @Resource(name = "projectContractInfoFacade")
    private IProjectContractInfoFacade projectContractInfoFacade;
    
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    /**
     * 一览页面
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("contractAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONTRACT_ADD));
        model.put("contractUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONTRACT_UPDATE));
        model.put("contractExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONTRACT_EXPORT));
        return index;
    }

    /**
     * 获取项目中合同信息一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/contractList.htm", method = RequestMethod.POST)
    public void getContractList(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setAreaId(form.getAreaId());
            if(Util.isNotNull(form.getInvestmentMountStart())){
                query.setInvestmentMountStart(NumberUtils.string2BigDecimal(form.getInvestmentMountStart())); 
            }
            if(Util.isNotNull(form.getInvestmentMountEnd())){
                query.setInvestmentMountEnd(NumberUtils.string2BigDecimal(form.getInvestmentMountEnd()));
            }     
            if(Util.isNotNull(form.getCategory())){
                query.setCategory(form.getCategory()); 
            }
            if(Util.isNotNull(form.getNature())){
                query.setNature(form.getNature()); 
            }
            if(Util.isNotNull(form.getProjectId())){
                query.setProjectId(form.getProjectId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getContractNo())){
                query.setContractNo(form.getContractNo()); 
            }
            if(Util.isNotNull(form.getManagePerson())){
                query.setManagePerson(form.getManagePerson()); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getProjectLeader())){
                query.setProjectLeader(form.getProjectLeader());
            }
            if(Util.isNotNull(form.getProjectName())){
                query.setProjectName(form.getProjectName()); 
            }
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }   
            query.setCashierType(11);
            if(Util.isNotNull(form.getRatioFrom())){
                query.setRatioFrom(NumberUtils.string2BigDecimal(form.getRatioFrom()));  
            }
            if(Util.isNotNull(form.getRatioTo())){
                query.setRatioTo(NumberUtils.string2BigDecimal(form.getRatioTo()));
            }
            if(Util.isNotNull(form.getSubpackageStatus())){
                query.setSubpackageStatus(form.getSubpackageStatus());
            }else{
                query.setSubpackageStatus(GenerateConstants.number_1);
            }
            query.setId(form.getId());
            query.setStatus(form.getStatus());
            query.setManagePerson(form.getManagePerson());
            query.setContractAwardCompany(form.getContractAwardCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE , 1));
            query.setHaveTechnical(form.getHaveTechnical());           
            ResultPage<ProjectContractInfo> result = projectContractFacade.getProjectContractByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
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
     * 新增合同
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.CONTRACT_ADD}, checkSource = false)
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
            List<ContractPayment> list = form.getContractPayment();
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
     * 修改合同
     * 
     * @param resp
     * @param form
     * @param req
     */
    @Security(auth = { UserAuthParams.CONTRACT_UPDATE}, checkSource = false)
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
//                if(Util.isNotNull(form.getRatio())){               
//                    projectContract.setRatio(new BigDecimal(form.getRatio()));
//                }
                projectContract.setSignDate(form.getSignDate().replace("/", ""));
               // projectContract.setRatioMemo(form.getRatioMemo());
                projectContract.setStatus(GardenProjectStatusEnum.NOT_VERIFY.getValue());
                projectContract.setTemporaryContractId(form.getTemporaryContractId());
            }            
            GardenProject project = new GardenProject();
            if(GenerateConstants.number_1.equals(form.getProjectUpdate())){
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
                 list = form.getContractPayment();
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
     * 项目id状态查询合同信息
     * 
     * @param resp
     * @param form
     * @param req
     */
    @RequestMapping(value = "/getProjectContractByProjectIdAndStatus.htm", method = RequestMethod.POST)
    public void getProjectContractByProjectIdAndStatus(HttpServletResponse resp, ProjectContractForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setId(form.getId());
            query.setStatus(form.getStatus());
            query.setProjectNo(form.getProjectNo());
            query.setProjectId(form.getProjectId());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            ResultRecord<ProjectContract> result = projectContractFacade.getProjectContractByProjectIdAndStatus(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("根据主键和状态查询合同数据出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取项目中合同信息一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getProjectContractList.htm", method = RequestMethod.POST)
    public void getProjectContractList(HttpServletResponse resp, ProjectContractForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ContractQuery query = new ContractQuery();
            query.setAreaId(form.getAreaId());
            Integer isChief = null;
            if(Util.isNotNull(form.getInvestmentMountStart())){
                query.setInvestmentMountStart(NumberUtils.string2BigDecimal(form.getInvestmentMountStart())); 
            }
            if(Util.isNotNull(form.getInvestmentMountEnd())){
                query.setInvestmentMountEnd(NumberUtils.string2BigDecimal(form.getInvestmentMountEnd()));
            }     
            if(Util.isNotNull(form.getCategory())){
                query.setCategory(form.getCategory()); 
            }
            if(Util.isNotNull(form.getNature())){
                query.setNature(form.getNature()); 
            }
            if(Util.isNotNull(form.getProjectId())){
                query.setProjectId(form.getProjectId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            if(Util.isNotNull(form.getContractNo())){
                query.setContractNo(form.getContractNo()); 
            }
            if(Util.isNotNull(form.getManagePerson())){
                query.setManagePerson(form.getManagePerson()); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getProjectLeader())){
                query.setProjectLeader(form.getProjectLeader());
            }
            if(Util.isNotNull(form.getProjectName())){
                query.setProjectName(form.getProjectName()); 
            }
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }   
            query.setCashierType(11);
            if(Util.isNotNull(form.getRatioFrom())){
                query.setRatioFrom(NumberUtils.string2BigDecimal(form.getRatioFrom()));  
            }
            if(Util.isNotNull(form.getRatioTo())){
                query.setRatioTo(NumberUtils.string2BigDecimal(form.getRatioTo()));
            }           
            query.setStatus(form.getStatus());
            query.setManagePerson(form.getManagePerson());
            query.setContractAwardCompany(form.getContractAwardCompany());
            query.setProjectLiable(form.getProjectLiable());
            if(Util.isNotNull(form.getDesignAreaFrom())){
                query.setDesignAreaFrom(form.getDesignAreaFrom()); 
            }
            if(Util.isNotNull(form.getDesignAreaTo())){
                query.setDesignAreaTo(form.getDesignAreaTo()); 
            }
            if(Util.isNotNull(form.getContractCapitalFrom())){
                query.setContractCapitalFrom(new BigDecimal(form.getContractCapitalFrom()));
            }
            if(Util.isNotNull(form.getContractCapitalTo())){
                query.setContractCapitalTo(new BigDecimal(form.getContractCapitalTo()));
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            query.setHaveTechnical(form.getHaveTechnical());
          //查询操作者
            StaffInfoQuery  staffInfoQuery = new StaffInfoQuery();
            staffInfoQuery.setUserId(SpringSecurityUtil.getIntPrincipal());
            staffInfoQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> staffRst = staffPersonFacade.getStaffInfoByCondition(staffInfoQuery);
            if(staffRst.isSuccess()){
                if(GenerateConstants.number_1!=form.getHaveTechnical()){                    
                    if(GenerateConstants.number_4.equals(staffRst.getList().get(0).getDepartId())){
                        query.setHaveTechnical(GenerateConstants.number_1);
                        isChief = GenerateConstants.number_1;
                    } 
                } 
                query.setMobile(staffRst.getList().get(0).getUserInfo().getMobile());
            }
            
            ResultPage<ProjectContractInfo> result = projectContractFacade.getProjectContractList(query);
            if (result.isSuccess()) {
                if(Util.isNotNull(isChief)){
                    model.put("isChief", GenerateConstants.number_1); 
                }
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
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
            query.setTemporaryId(form.getTemporaryId());
            query.setStatus(form.getStatus());
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
    
    /**
     * 根据当前用户的浏览器不同，对文件的名字进行不同的编码设置，解决不同浏览器下文件名中文乱码问题
     * 
     * @param request
     * @param response
     * @param fileName
     */

    private void setFileDownloadHeader(HttpServletRequest request, HttpServletResponse response, String fileName) {
        final String userAgent = request.getHeader("USER-AGENT");
        try {
            String finalFileName = null;
            if (com.alibaba.dubbo.common.utils.StringUtils.isContains(userAgent, "MSIE")) {// IE浏览器
                finalFileName = URLEncoder.encode(fileName, "UTF8");
            } else if (com.alibaba.dubbo.common.utils.StringUtils.isContains(userAgent, "Mozilla")) {// google,火狐浏览器
                finalFileName = new String(fileName.getBytes(), "ISO8859-1");
            } else {
                finalFileName = URLEncoder.encode(fileName, "UTF8");// 其他浏览器
            }
            response.setHeader("Content-Disposition", "attachment; filename=\"" + finalFileName + "\"");// 这里设置一下让浏览器弹出下载提示框，而不是直接在浏览器中打开
        } catch (UnsupportedEncodingException e) {
            logger.error("设置浏览器字符编码集出错", e);
        }
    }
    
    
    /**
     * 合同导出
     * 
     * @param resp
     * @param form
     * @param req
     */
    @Security(auth = { UserAuthParams.CONTRACT_EXPORT}, checkSource = false)
    @RequestMapping(value = "/downLoadContract.htm", method = RequestMethod.POST)
    public void downLoadProject(HttpServletResponse resp, ProjectContractForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            ContractQuery query = new ContractQuery();
            query.setAreaId(form.getAreaId());
            if(Util.isNotNull(form.getInvestmentMountStart())){
                query.setInvestmentMountStart(NumberUtils.string2BigDecimal(form.getInvestmentMountStart())); 
            }
            if(Util.isNotNull(form.getInvestmentMountEnd())){
                query.setInvestmentMountEnd(NumberUtils.string2BigDecimal(form.getInvestmentMountEnd()));
            }     
            if(Util.isNotNull(form.getCategory())){
                query.setCategory(form.getCategory()); 
            }
            if(Util.isNotNull(form.getNature())){
                query.setNature(form.getNature()); 
            }
            if(Util.isNotNull(form.getProjectId())){
                query.setProjectId(form.getProjectId());
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getContractNo())){
                query.setContractNo(form.getContractNo()); 
            }
            if(Util.isNotNull(form.getManagePerson())){
                query.setManagePerson(form.getManagePerson()); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getProjectLeader())){
                query.setProjectLeader(form.getProjectLeader());
            }
            if(Util.isNotNull(form.getProjectName())){
                query.setProjectName(form.getProjectName()); 
            }
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }   
            query.setCashierType(11);
            if(Util.isNotNull(form.getRatioFrom())){
                query.setRatioFrom(NumberUtils.string2BigDecimal(form.getRatioFrom()));  
            }
            if(Util.isNotNull(form.getRatioTo())){
                query.setRatioTo(NumberUtils.string2BigDecimal(form.getRatioTo()));
            }           
            query.setStatus(form.getStatus());
            query.setManagePerson(form.getManagePerson());
            query.setContractAwardCompany(form.getContractAwardCompany());
            query.setProjectLiable(form.getProjectLiable());
            if(Util.isNotNull(form.getDesignAreaFrom())){
                query.setDesignAreaFrom(form.getDesignAreaFrom()); 
            }
            if(Util.isNotNull(form.getDesignAreaTo())){
                query.setDesignAreaTo(form.getDesignAreaTo()); 
            }
            if(Util.isNotNull(form.getContractCapitalFrom())){
                query.setContractCapitalFrom(new BigDecimal(form.getContractCapitalFrom()));
            }
            if(Util.isNotNull(form.getContractCapitalTo())){
                query.setContractCapitalTo(new BigDecimal(form.getContractCapitalTo()));
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            query.setHaveTechnical(form.getHaveTechnical());
          //查询操作者
            StaffInfoQuery  staffInfoQuery = new StaffInfoQuery();
            staffInfoQuery.setUserId(SpringSecurityUtil.getIntPrincipal());
            staffInfoQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> staffRst = staffPersonFacade.getStaffInfoByCondition(staffInfoQuery);
            if(staffRst.isSuccess()){
                if(GenerateConstants.number_1!=form.getHaveTechnical()){                
                    if(GenerateConstants.number_4.equals(staffRst.getList().get(0).getDepartId())){
                        query.setHaveTechnical(GenerateConstants.number_1);;
                    } 
                } 
                query.setMobile(staffRst.getList().get(0).getUserInfo().getMobile());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<ProjectContractInfo> result = projectContractFacade.getProjectContractList(query);            
            if(result.isSuccess()){
                List<ProjectContractInfo> list = result.getList();
                for(int i=0;i<list.size();i++){
                    if(Util.isNotNull(list.get(i).getSignDate())){
                        list.get(i).setSignDate(DateUtil.format(DateUtil.parse(list.get(i).getSignDate(), "yyyyMMdd"), "yyyy/MM/dd")); 
                    }
                    if(GenerateConstants.number_1.equals(list.get(i).getStatus())){
                        list.get(i).setStatusText("正常");
                    }else{
                        list.get(i).setStatusText("未审核");
                    }
                    list.get(i).setStatusText(GardenProjectStatusEnum.getByValue(list.get(i).getStatus()).getMessage());
                    list.get(i).setAddTime(DateUtil.format(list.get(i).getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                }
                // 设置文件标题行
                String[] headers = { "合同编号", "项目名称", "负责人", "经营专员", "签约日期", "所在城市", "合同金额(万元)",  "院方比例(%)","录入时间 ", "状态"};
                String[] rows = { "contractNo", "project.name", "leader.name", "manager.name", "signDate", "area.areaName", "contractCapital","ratio","addTime","statusText"};
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("合同信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "合同信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("导出合同信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
