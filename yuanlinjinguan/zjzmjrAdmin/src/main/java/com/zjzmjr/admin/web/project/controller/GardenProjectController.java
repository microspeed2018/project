package com.zjzmjr.admin.web.project.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
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

import com.zjzmjr.admin.web.project.form.GardenProjectForm;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
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

    private final static String index = "/WEB-INF/pages/project/project.jsp";

    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("projectAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.PROJECT_ADD));
        model.put("projectUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.PROJECT_UPDATE));
        model.put("projectExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.PROJECT_EXPORT));
        return index;
    }
    
    /**
     * 获取项目最大的编号
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/projectMaxNo.htm", method = RequestMethod.POST)
    public void getProjectMaxNo(ModelMap model, HttpServletResponse resp){
        try {
            ResultEntry<String> result = gardenProjectFacade.getGardenProjectMaxNo();
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取项目最大的编号出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 项目一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/list.htm", method = RequestMethod.POST)
    public void list(HttpServletResponse resp, GardenProjectForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            Integer isChief = null;
            query.setId(form.getId());
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName()); 
            }
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }            
            query.setContractAwardCompany(form.getContractAwardCompany());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getActiDatetime())){
                query.setActiDatetime(form.getActiDatetime().replace("/", "")); 
            }
            if(Util.isNotNull(form.getActiDatetimeEnd())){
                query.setActiDatetimeEnd(form.getActiDatetimeEnd().replace("/", ""));
            }
            if(Util.isNotNull(form.getCityId())){
                query.setCityId(form.getCityId());
            }
            if(Util.isNotNull(form.getInvestmentMountStart())){
                query.setInvestmentMountStart(form.getInvestmentMountStart());
            }
            if(Util.isNotNull(form.getInvestmentMountEnd())){
                query.setInvestmentMountEnd(form.getInvestmentMountEnd());
            }
            query.setProjectLeader(form.getProjectLeader());
            query.setDepartment(form.getDepartment());
            query.setManagementPersonnel(form.getManagementPersonnel());
            query.setNature(form.getNature());
            query.setCategory(form.getCategory());
            if(Util.isNotNull(form.getDesignAreaStart())){
                query.setDesignAreaStart(form.getDesignAreaStart());
            }
            if(Util.isNotNull(form.getDesignAreaEnd())){               
                query.setDesignAreaEnd(form.getDesignAreaEnd());
            }
            query.setHaveTechnical(form.getHaveTechnical());
            query.setStatus(form.getStatus());
            if(!GardenProjectStatusEnum.TEMPORARY.getValue().equals(form.getStatus())){
                query.setSubStatus(GardenProjectStatusEnum.TEMPORARY.getValue()); 
            }
            query.setStep(form.getStep());
            // 根据用户编号查询权限
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setSourceType(GenerateConstants.number_1);
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            //查询操作者
            StaffInfoQuery  staffInfoQuery = new StaffInfoQuery();
            staffInfoQuery.setUserId(SpringSecurityUtil.getIntPrincipal());
            staffInfoQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> staffRst = staffPersonFacade.getStaffInfoByCondition(staffInfoQuery);
            if(staffRst.isSuccess()){
                if(Util.isNotNull(form.getIsAllData()) || form.getHaveTechnical()!=GenerateConstants.number_1){                    
                    if( GenerateConstants.number_4.equals(staffRst.getList().get(0).getDepartId())){
                        query.setHaveTechnical(GenerateConstants.number_1);
                        isChief = GenerateConstants.number_1;
                    }
                }
                query.setMobile(staffRst.getList().get(0).getUserInfo().getMobile());
            }
            ResultPage<GardenProjectInfo> result = gardenProjectFacade.getGardenProjectByCondition(query);
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
            logger.error("项目信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 保存项目信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/saveProject.htm", method = RequestMethod.POST)
    public void save(HttpServletResponse resp, GardenProject form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_05.getValue());
            form.setCompanyId(SpringSecurityUtil.getIntCompany());
            form.setCreateTime(new Date());
            form.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            form.setUpdateTime(form.getCreateTime());
            form.setUpdateUserId(form.getCreateUserId());
            // 项目阶段
            form.setStep(GardenProjectStepEnum.P_10.getValue());
            ResultEntry<Integer> result = gardenProjectFacade.insertGardenProject(form);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setProjectId(result.getT());
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("保存项目信息插入出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 申请项目信息
     * 
     * @param resp
     * @param form
     */
    @Security(auth = { UserAuthParams.PROJECT_ADD}, checkSource = false)
    @RequestMapping(value = "/applyProject.htm", method = RequestMethod.POST)
    public void insert(HttpServletResponse resp, GardenProject form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_10.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_817.getMessage());
            form.setCompanyId(SpringSecurityUtil.getIntCompany());
            form.setCreateTime(new Date());
            form.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            form.setUpdateTime(form.getCreateTime());
            form.setUpdateUserId(form.getCreateUserId());
            // 项目阶段
            form.setStep(GardenProjectStepEnum.P_20.getValue());
            ResultEntry<Integer> result = gardenProjectFacade.insertGardenProject(form);
            if (result.isSuccess()) {
                putSuccess(model, "");
                adminBusiness.setProjectId(result.getT());
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                // 发送消息
                Message message = new Message();
                message.setTitle(MessageContextEnum.projectInput.getValue());
                message.setAddress(MessageContextEnum.projectInput.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.projectInput.getMessage(), form.getName()));
                MessageHandlerUtil.insertMessage(message, MessageHandlerUtil.getJinYingManagerPersons());
                PointSymbol pointSymbol = new PointSymbol();
                pointSymbol.setName(form.getName());
                pointSymbol.setTemplateCode(MessageContextEnum.projectInput.getTemplateCode());
                MessageHandlerUtil.sendSms(pointSymbol, MessageHandlerUtil.getJinYingManagerPersons());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("申请项目信息插入出错：", ex);
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
    @Security(auth = { UserAuthParams.PROJECT_UPDATE}, checkSource = false)
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
                if(form.getStep()>GardenProjectStepEnum.P_20.getValue()){
                    GardenProjectQuery query = new GardenProjectQuery();
                    query.setId(form.getId());
                    query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectFacade.getGardenProjectByCondition(query);
                    if(pjRst.isSuccess()){
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_940.getValue());
                        message.setAddress(MessageContextEnum.Msg_940.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_940.getMessage(), 
                                pjRst.getList().get(0).getName()));
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
     * 根据主键和状态查询项目数据
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getGardenProjectByIdAndStatus.htm", method = RequestMethod.POST)
    public void getGardenProjectByIdAndStatus(HttpServletResponse resp, GardenProject form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            query.setStatus(form.getStatus());
            query.setProjectNo(form.getProjectNo());
            query.setCompanyId(form.getCompanyId());
            ResultEntry<GardenProject> result = gardenProjectFacade.getGardenProjectByIdAndStatus(query);
            if(result.isSuccess()){
                model.put("rows", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("根据主键和状态查询项目数据出错：", ex);
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
    
    /**
     * 项目导出
     * 
     * @param resp
     * @param form
     * @param req
     */
    @Security(auth = { UserAuthParams.PROJECT_EXPORT}, checkSource = false)
    @RequestMapping(value = "/downLoadProject.htm", method = RequestMethod.POST)
    public void downLoadProject(HttpServletResponse resp, GardenProjectForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            GardenProjectQuery query = new GardenProjectQuery();
            query.setId(form.getId());
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName()); 
            }
            if(Util.isNotNull(form.getProjectNo())){
                query.setProjectNo(form.getProjectNo()); 
            }            
            query.setContractAwardCompany(form.getContractAwardCompany());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(Util.isNotNull(form.getActiDatetime())){
                query.setActiDatetime(form.getActiDatetime().replace("/", "")); 
            }
            if(Util.isNotNull(form.getActiDatetimeEnd())){
                query.setActiDatetimeEnd(form.getActiDatetimeEnd().replace("/", ""));
            }
            if(Util.isNotNull(form.getCityId())){
                query.setCityId(form.getCityId());
            }
            if(Util.isNotNull(form.getInvestmentMountStart())){
                query.setInvestmentMountStart(form.getInvestmentMountStart());
            }
            if(Util.isNotNull(form.getInvestmentMountEnd())){
                query.setInvestmentMountEnd(form.getInvestmentMountEnd());
            }
            query.setProjectLeader(form.getProjectLeader());
            query.setDepartment(form.getDepartment());
            query.setManagementPersonnel(form.getManagementPersonnel());
            query.setNature(form.getNature());
            if(form.getCategory()!=-1){
                query.setCategory(form.getCategory());  
            }
            if(Util.isNotNull(form.getDesignAreaStart())){
                query.setDesignAreaStart(form.getDesignAreaStart());
            }
            if(Util.isNotNull(form.getDesignAreaEnd())){               
                query.setDesignAreaEnd(form.getDesignAreaEnd());
            }
            query.setHaveTechnical(form.getHaveTechnical());
            query.setStatus(form.getStatus());
            if(!GardenProjectStatusEnum.TEMPORARY.getValue().equals(form.getStatus())){
                query.setSubStatus(GardenProjectStatusEnum.TEMPORARY.getValue()); 
            }
            query.setStep(form.getStep());
            // 根据用户编号查询权限
            query.setUserId(SpringSecurityUtil.getIntPrincipal());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
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
            ResultPage<GardenProjectInfo> result = gardenProjectFacade.getGardenProjectByCondition(query);
            if(result.isSuccess()){
                List<GardenProjectInfo> list = result.getList();
                for(int i=0;i<list.size();i++){
                    list.get(i).setStepText(GardenProjectStepEnum.getByValue(list.get(i).getStep()).getMessage());
                    list.get(i).setStatusText(GardenProjectStatusEnum.getByValue(list.get(i).getStatus()).getMessage());
                    list.get(i).setAddTime(DateUtil.format(list.get(i).getCreateTime(), "yyyy/MM/dd HH:mm:ss"));
                }
                // 设置文件标题行
                String[] headers = { "项目编号", "项目名称", "负责人", "经营专员", "经营部门", "项目类别", "项目性质",  "投资额(万元)", "设计面积(㎡)", "发包单位", "录入时间 ", "状态", "阶段 "};
                String[] rows = { "projectNo", "name", "leader.name", "manager.name", "departmentName", "categoryName", "natureName","investmentMount","designArea","companyAssociated.companyName","addTime","statusText","stepText"};
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("项目信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "项目信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("导出项目信息出错：", ex);
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
}
