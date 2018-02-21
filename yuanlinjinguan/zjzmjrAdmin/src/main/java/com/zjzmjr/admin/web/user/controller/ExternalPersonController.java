package com.zjzmjr.admin.web.user.controller;

import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
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

import com.zjzmjr.admin.web.user.form.PersonForm;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@RequestMapping("/external/user")
@Controller
public class ExternalPersonController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ExternalPersonController.class);

    private final static String index = "/WEB-INF/pages/company/externalPerson.jsp";

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
        model.put("externalAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONSOLE_ADMIN_ADD));
        model.put("externalUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONSOLE_ADMIN_UPDATE));
        model.put("externalExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.EXTERNAL_EXPORT));
        return index;
    }
    
    /**
     * 外部人员一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getExternalPerson.htm", method = RequestMethod.POST)
    public void getExternalPerson(HttpServletResponse resp, PersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ExternalPersonQuery query = new ExternalPersonQuery();
            if(Util.isNotNull(form.getId())){
                query.setId(form.getId());                
            }                
            if(Util.isNotNull(form.getUserId())){
                query.setUserId(form.getUserId());  
            } 
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());               
            }
            if(Util.isNotNull(form.getCompany())){
                query.setCompany(form.getCompany());          
            }
            if(Util.isNotNull(form.getPersonnelNature())){
                query.setPersonnelNature(form.getPersonnelNature());             
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());         
            }
            if(Util.isNotNull(form.getEmail())){
                query.setEmail(form.getEmail());            
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));          
            }
            if(Util.isNotNull(form.getJobStatus())){
                query.setStatus(form.getJobStatus());
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));         
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<ExternalPersonInfo> result = staffPersonFacade.getExternalPersonByCondition(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("外部人员一览信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 外部人员修改
     * 
     * @param resp
     * @param form
     */
    @Security(auth = "CONSOLE_ADMIN_UPDATE", checkSource = true)
    @RequestMapping(value = "/updateExternalPerson.htm", method = RequestMethod.POST)
    public void updateExternalPerson(HttpServletResponse resp, PersonForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ExternalPersonInfo  externalPersonInfo= new ExternalPersonInfo();
            Admin admin = new Admin();
            externalPersonInfo.setId(form.getId());
            externalPersonInfo.setUserId(form.getUserId());
            externalPersonInfo.setCompany(form.getCompany());
            externalPersonInfo.setPersonnelNature(form.getPersonnelNature());
            externalPersonInfo.setMobile(form.getMobile());
            externalPersonInfo.setJob(form.getJob());
            externalPersonInfo.setEmail(form.getEmail());
            externalPersonInfo.setMemo(form.getMemo());
            externalPersonInfo.setRelatedPerson(form.getRelatedPerson());
            admin.setId(form.getUserId());
            admin.setMobile(form.getMobile());
            admin.setAccStatus(form.getAccStatus());
            admin.setUsername(form.getMobile());
            admin.setName(form.getName());
            externalPersonInfo.setUserInfo(admin);
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_USER_ACCOUNT.getValue());
            adminBusiness.setExtend1("外部员工修改");
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            ResultEntry<Integer> result = staffPersonFacade.updateExternalPersonAndAdmin(externalPersonInfo);
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
            logger.error("外部人员修改信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取最大的外部员工编号
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/getExternalPersonEmployeeMaxNo.htm", method = RequestMethod.POST)
    public void getExternalPersonEmployeeMaxNo(ModelMap model, HttpServletResponse resp){
        try {
            ResultEntry<Integer> result = staffPersonFacade.getExternalPersonEmployeeMaxNo();
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取最大的外部员工编号出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 外部人员导出
     * 
     * @param resp
     * @param form
     */
    @Security(auth = "EXTERNAL_EXPORT", checkSource = true)
    @RequestMapping(value = "/downLoadExternalPerson.htm", method = RequestMethod.POST)
    public void downLoadExternalPerson(HttpServletResponse resp, PersonForm form, HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            ExternalPersonQuery query = new ExternalPersonQuery();
            if(Util.isNotNull(form.getId())){
                query.setId(form.getId());                
            }                
            if(Util.isNotNull(form.getUserId())){
                query.setUserId(form.getUserId());  
            } 
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());               
            }
            if(Util.isNotNull(form.getCompany())){
                query.setCompany(form.getCompany());          
            }
            if(Util.isNotNull(form.getPersonnelNature())){
                query.setPersonnelNature(form.getPersonnelNature());             
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());         
            }
            if(Util.isNotNull(form.getEmail())){
                query.setEmail(form.getEmail());            
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));          
            }
            if(Util.isNotNull(form.getStatus())){
                query.setStatus(form.getStatus());
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));         
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<ExternalPersonInfo> result = staffPersonFacade.getExternalPersonByCondition(query);
            if(result.isSuccess()){
                List<ExternalPersonInfo> list = result.getList();
                for(int i=0;i<list.size();i++){
                    if(GenerateConstants.number_1.equals(list.get(i).getStatus())){
                        list.get(i).setStatusText("在职");
                    }else if(GenerateConstants.number_0.equals(list.get(i).getStatus())){
                        list.get(i).setStatusText("离职");
                    }
                }
                // 设置文件标题行
                String[] headers = { "员工编号", "姓名", "所属公司", "人员性质", "联系电话", "关系人", "状态"};
                String[] rows = { "employeeNo", "userInfo.name", "company", "personnelNatureName", "userInfo.mobile", "relatedPersonName", "statusText"};                
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("外部员工信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "外部员工信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("外部人员信息导出失败", ex);
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
