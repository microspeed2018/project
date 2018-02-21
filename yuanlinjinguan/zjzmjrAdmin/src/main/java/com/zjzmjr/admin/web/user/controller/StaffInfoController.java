package com.zjzmjr.admin.web.user.controller;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
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
import com.zjzmjr.core.api.admin.IAdminFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.AreaKeyValue;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.biz.weixin.JSONProcesserUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminStaff;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 人员控制层
 * 
 * @author lenovo
 * @version $Id: StaffInfoController.java, v 0.1 2017-8-26 下午5:14:40 lenovo Exp $
 */
@RequestMapping("/staff/user")
@Controller
public class StaffInfoController extends BaseController{
    private static final Logger logger = LoggerFactory.getLogger(StaffInfoController.class);

    private final static String index = "/WEB-INF/pages/company/staff.jsp";
    
    private final static String adminstaff = "/WEB-INF/pages/company/adminstaff.jsp";

    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;
    
    @Resource(name = "adminFacade")
    private IAdminFacade adminFacade;
    
    /**
     * 
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest req) {
        String manage = req.getParameter("manage");
        if ("1".equals(manage)) {
            model.put("hasMenuAuth", true);
            model.put("hasEditAuth", false);
        } else {
            model.put("hasMenuAuth", false);
            model.put("hasEditAuth", true);
        }
        model.put("staffAddAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONSOLE_ADMIN_ADD));
        model.put("staffUpdateAuth", SpringSecurityUtil.hasAuth(UserAuthParams.CONSOLE_ADMIN_UPDATE));
        model.put("staffExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.STAFF_EXPORT));
        return index;
    }
    
    @RequestMapping(value = "/adminstaff.htm", method = RequestMethod.GET)
    public String adminstaff(ModelMap model, HttpServletRequest req) {
        return adminstaff;
    }
    
    /**
     * 人员一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getStaffPerson.htm", method = RequestMethod.POST)
    public void getStaffPerson(HttpServletResponse resp, PersonForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 公司内部员工信息一览
            StaffInfoQuery query = new StaffInfoQuery();
            if(Util.isNotNull(form.getId())){
                query.setId(form.getId());               
            }
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());             
            }
            if(Util.isNotNull(form.getJobId())){
                query.setJobId(form.getJobId());         
            }
            if(Util.isNotNull(form.getJobStatus())){
                query.setJobStatus(form.getJobStatus());            
            }
            if(Util.isNotNull(form.getNotJobStatus())){
                query.setNotJobStatus(form.getNotJobStatus());
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());              
            }
            if(Util.isNotNull(form.getTelephone())){
                query.setTelephone(form.getTelephone());             
            }
            if(Util.isNotNull(form.getDepartmentId())){
                query.setDepartmentId(form.getDepartmentId());
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));           
            }           
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));        
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setOrderBy(form.getOrderBy());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询公司员工及外部人员一览信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 修改员工
     * 
     * @param resp
     * @param form
     */
    @Security(auth = "CONSOLE_ADMIN_UPDATE", checkSource = true)
    @RequestMapping(value = "/updateStaff.htm", method = RequestMethod.POST)
    public void updateStaff(HttpServletResponse resp, PersonForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            //新增管理员事物
            AdminBusiness adminBusiness=new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_MODIFY_USER_ACCOUNT.getValue());
            adminBusiness.setExtend1("员工修改");
            ResultEntry<AdminBusiness> val=AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            //查询有无相同员工编号
            StaffInfoQuery staffQuery = new StaffInfoQuery();
            staffQuery.setEmployeeNo(form.getEmployeeNo());
            PageBean bean = new PageBean(Integer.MAX_VALUE, 1);
            staffQuery.setPageBean(bean);
            ResultPage<StaffBasicInfo> results = staffPersonFacade.getStaffInfoByCondition(staffQuery);
            if(!(results.isSuccess() && !results.getList().get(0).getId().equals(form.getId()))){
                StaffBasicInfo staff = new StaffBasicInfo();
                Admin admin = new Admin();
                staff.setId(form.getId());
                staff.setUserId(form.getUserId());
                staff.setJobStatus(form.getJobStatus());
                staff.setEmployeeNo(form.getEmployeeNo());
                staff.setShortTelephone(form.getShortTelephone());
                staff.setVirtualMobile(form.getVirtualMobile());
                staff.setEmail(form.getEmail());
                staff.setTelephone(form.getTelephone());
                staff.setIdentityNo(form.getIdentityNo());
                staff.setSex(form.getSex());
                staff.setBirthday(form.getBirthday().replace("/", ""));
                if(form.getStaffType()!=-1){
                    staff.setStaffType(form.getStaffType()); 
                }
                staff.setEntryDate(form.getEntryDate().replace("/", ""));
                staff.setEntranceGuardCardNumber(form.getEntranceGuardCardNumber());
                if(form.getPoliticalStatus()!=-1){
                    staff.setPoliticalStatus(form.getPoliticalStatus()); 
                }
                staff.setTitleQuality(form.getTitleQuality());
                staff.setGraduateInstitutions(form.getGraduateInstitutions());
                staff.setStudyMajor(form.getStudyMajor());
                if(form.getEducation()!=-1){
                    staff.setEducation(form.getEducation()); 
                }
                staff.setFirstWorkDate(form.getFirstWorkDate().replace("/", ""));
                staff.setRegisteredResidence(form.getRegisteredResidence());
                staff.setPresentAddress(form.getPresentAddress());
                staff.setContract1(form.getContract1());
                staff.setContract2(form.getContract2());
                staff.setContract3(form.getContract3());
                staff.setContractDueDate(form.getContractDueDate().replace("/", ""));
                staff.setSocialSecurityBase(form.getSocialSecurityBase());
                staff.setBank(form.getBank());
                staff.setBankcardNum(form.getBankcardNum());
                staff.setMemo(form.getMemo());
                staff.setUpdateTime(new Date());
                staff.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
                admin.setId(form.getUserId());
                admin.setUsername(form.getMobile());
                admin.setDepartment(form.getDepartmentId());
                admin.setJobId(form.getJobId());
                admin.setName(form.getName());
                admin.setMobile(form.getMobile());
                admin.setEmail(form.getEmail());
                admin.setAccStatus(form.getAccStatus());
                staff.setUserInfo(admin);                              
                ResultEntry<Integer> result = staffPersonFacade.updateStaffInfoAndAdmin(staff);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
            }else{
                putError(model, "员工编号已经存在");
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment("员工编号已经存在");
            }            
            //更新管理员事物
            adminBusiness.setId(val.getT().getId());
            AdminTransactionUtil.updateAdminTransaction(adminBusiness);
        } catch (Exception ex) {
            logger.error("修改员工信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
 /**
  * 员工信息导出
  * 
  * @param resp
  * @param form
  * @param req
  */
    @Security(auth = { UserAuthParams.STAFF_EXPORT}, checkSource = false)
    @RequestMapping(value = "/downLoadStaff.htm", method = RequestMethod.POST)
    public void downLoadStaff(HttpServletResponse resp, PersonForm form,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        OutputStream outputStream = null;
        try {
            // 公司内部员工信息一览
            StaffInfoQuery query = new StaffInfoQuery();
            if(Util.isNotNull(form.getId())){
                query.setId(form.getId());               
            }
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());             
            }
            if(Util.isNotNull(form.getJobId()) && form.getJobId()!=-1){
                query.setJobId(form.getJobId());         
            }
            if(Util.isNotNull(form.getJobStatusSerach())){
                query.setJobStatus(form.getJobStatusSerach());            
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());              
            }
            if(Util.isNotNull(form.getTelephone())){
                query.setTelephone(form.getTelephone());             
            }
            if(Util.isNotNull(form.getDepartmentId()) && form.getDepartmentId()!=-1){
                query.setDepartmentId(form.getDepartmentId());
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));           
            }           
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));        
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if(result.isSuccess()){
                List<StaffBasicInfo> list = result.getList();
                for(int i=0;i<list.size();i++){
                    if(GenerateConstants.number_1.equals(list.get(i).getJobStatus())){
                        list.get(i).setStatusText("在职");
                    }else if(GenerateConstants.number_0.equals(list.get(i).getJobStatus())){
                        list.get(i).setStatusText("离职");
                    }
                    if(Util.isNotNull(list.get(i).getEntryDate())){
                        list.get(i).setCompanyAge(Integer.parseInt(DateUtil.format(new Date(), "yyyy"))-Integer.parseInt(DateUtil.format(DateUtil.parse(list.get(i).getEntryDate(), "yyyyMMdd"), "yyyy")));
                        list.get(i).setEntryDate(DateUtil.format(DateUtil.parse(list.get(i).getEntryDate(), "yyyyMMdd"), "yyyy/MM/dd"));
                    }
                    if(Util.isNotNull(list.get(i).getFirstWorkDate())){
                        list.get(i).setFirstWorkDate(DateUtil.format(DateUtil.parse(list.get(i).getFirstWorkDate(), "yyyyMMdd"), "yyyy/MM/dd"));
                    }
                    if(Util.isNotNull(list.get(i).getContractDueDate())){
                        list.get(i).setContractDueDate(DateUtil.format(DateUtil.parse(list.get(i).getContractDueDate(), "yyyyMMdd"), "yyyy/MM/dd"));
                    }
                    if(Util.isNotNull(list.get(i).getBirthday())){
                         list.get(i).setAge(Integer.parseInt(DateUtil.format(new Date(), "yyyy"))-Integer.parseInt(DateUtil.format(DateUtil.parse(list.get(i).getBirthday(), "yyyyMMdd"), "yyyy")));
                         list.get(i).setBirthday(DateUtil.format(DateUtil.parse(list.get(i).getBirthday(), "yyyyMMdd"), "yyyy/MM/dd"));                        
                    }
                    if(GenerateConstants.number_1.equals(list.get(i).getSex())){
                        list.get(i).setSexText("男");
                    }else if(GenerateConstants.number_2.equals(list.get(i).getSex())){
                        list.get(i).setSexText("女");
                    }
                    
                }
                // 设置文件标题行
                String[] headers = { "员工编号", "姓名", "所属部门", "职位", "联系电话","身份证号","生日","性别","年龄","入职日期","司龄","类别","虚拟网短号","座机","座机短号","邮箱","门禁卡号","政治面貌",
                        "职称、职质","毕业院校","所学专业","学历","首次参加工作日期","户口所在地","现住址","合同1","合同2","合同3","合同到期日","社保基数(元)",
                        "开户银行","银行卡号","状态","备注"};
                String[] rows = { "employeeNo", "userInfo.name", "departmentName", "jobName", "userInfo.mobile","identityNo","birthday","sexText","age","entryDate","companyAge","staffTypeAttribute",
                        "virtualMobile","telephone","shortTelephone","email","entranceGuardCardNumber","politicalStatusAttribute","titleQuality","graduateInstitutions",
                        "studyMajor","educationAttribute","firstWorkDate","registeredResidence","presentAddress","contract1","contract2","contract3","contractDueDate",
                        "socialSecurityBase","bank","bankcardNum","statusText","memo"};                
                // 生成excel文件
                // 标题行对应的属性名
                HSSFWorkbook wb = ExcelUtil.exportExcel("员工信息导出", headers, rows, list, "yyyyMMddhhmmssSSS");
                resp.setContentType("application/vnd.ms-excel");
                SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
                setFileDownloadHeader(req, resp, "员工信息导出" + sdf.format(new Date())+ ".xls");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            }else{
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("员工信息导出出错：", ex);
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
     * 获取可以选择人员的字段
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getStaffPersons.htm", method = RequestMethod.POST)
    public void getStaffPersons(HttpServletResponse resp,PersonForm form) {
        resp.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = resp.getWriter();
            StaffInfoQuery query = new StaffInfoQuery();
            query.setJobStatus(form.getJobStatus());
            if(Util.isNotNull(form.getNotJobStatus())){
                query.setNotJobStatus(form.getNotJobStatus());
            }
            query.setJobId(form.getJobId());
            query.setOrderBy(form.getOrderBy());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            List<AreaKeyValue> newList = new ArrayList<AreaKeyValue>();
            if(result.isSuccess()){
                for (int i=0;i<result.getList().size();i++) {
                    AreaKeyValue areaKeyValue = new AreaKeyValue();
                    areaKeyValue.setText(result.getList().get(i).getUserInfo().getName());
                    areaKeyValue.setValue(result.getList().get(i).getUserId());
                    newList.add(areaKeyValue);              
                } 
            }
            // 公司外部人员信息一览
            ExternalPersonQuery personQuery = new ExternalPersonQuery();
            personQuery.setPersonnelNature(form.getPersonnelNature());
            personQuery.setStatus(1);
            personQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<ExternalPersonInfo> personRst = staffPersonFacade.getExternalPersonByCondition(personQuery);
            if(personRst.isSuccess()){
                for (int i=0;i<personRst.getList().size();i++) {
                    AreaKeyValue areaKeyValue = new AreaKeyValue();
                    areaKeyValue.setText("(H)"+personRst.getList().get(i).getUserInfo().getName());
                    areaKeyValue.setValue(personRst.getList().get(i).getUserId());
                    newList.add(areaKeyValue);              
                } 
            }
            resp.setContentType("application/json; charset=utf-8");
            out.print(JSONProcesserUtil.parseObj(newList));
            out.flush();
            out.close();
        } catch (Exception ex) {
            logger.error("获取可以进行数据修改的字段出错：", ex);
        }
    }
    
    
    /**
     * 获取可以选择人员的字段
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getInnerStaffPersons.htm", method = RequestMethod.POST)
    public void getInnerStaffPersons(HttpServletResponse resp,PersonForm form) {
        resp.setCharacterEncoding("utf-8");
        try {
            PrintWriter out = resp.getWriter();
            StaffInfoQuery query = new StaffInfoQuery();
            query.setJobStatus(form.getJobStatus());
            if(Util.isNotNull(form.getNotJobStatus())){
                query.setNotJobStatus(form.getNotJobStatus());
            }
            query.setJobId(form.getJobId());
            query.setOrderBy(form.getOrderBy());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            List<AreaKeyValue> newList = new ArrayList<AreaKeyValue>();
            if(result.isSuccess()){
                for (int i=0;i<result.getList().size();i++) {
                    AreaKeyValue areaKeyValue = new AreaKeyValue();
                    areaKeyValue.setText(result.getList().get(i).getUserInfo().getName());
                    areaKeyValue.setValue(result.getList().get(i).getUserId());
                    newList.add(areaKeyValue);              
                } 
            }
//            // 公司外部人员信息一览
//            ExternalPersonQuery personQuery = new ExternalPersonQuery();
//            personQuery.setPersonnelNature(form.getPersonnelNature());
//            personQuery.setStatus(1);
//            personQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
//            ResultPage<ExternalPersonInfo> personRst = staffPersonFacade.getExternalPersonByCondition(personQuery);
//            if(personRst.isSuccess()){
//                for (int i=0;i<personRst.getList().size();i++) {
//                    AreaKeyValue areaKeyValue = new AreaKeyValue();
//                    areaKeyValue.setText("(H)"+personRst.getList().get(i).getUserInfo().getName());
//                    areaKeyValue.setValue(personRst.getList().get(i).getUserId());
//                    newList.add(areaKeyValue);              
//                } 
//            }
            resp.setContentType("application/json; charset=utf-8");
            out.print(JSONProcesserUtil.parseObj(newList));
            out.flush();
            out.close();
        } catch (Exception ex) {
            logger.error("获取可以进行数据修改的字段出错：", ex);
        }
    }
    
    /**
     * 所有人员一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getAdminStaff.htm", method = RequestMethod.POST)
    public void getAdminStaff(HttpServletResponse resp,PersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 公司内部员工信息一览
            StaffInfoQuery query = new StaffInfoQuery();
            if(Util.isNotNull(form.getId())){
                query.setId(form.getId());               
            }
            if(Util.isNotNull(form.getName())){
                query.setName(form.getName());             
            }
            if(Util.isNotNull(form.getJobId())){
                query.setJobId(form.getJobId());         
            }
            if(Util.isNotNull(form.getJobStatus())){
                query.setJobStatus(form.getJobStatus());            
            }
            if(Util.isNotNull(form.getMobile())){
                query.setMobile(form.getMobile());              
            }
            if(Util.isNotNull(form.getTelephone())){
                query.setTelephone(form.getTelephone());             
            }
            if(Util.isNotNull(form.getDepartmentId())){
                query.setDepartmentId(form.getDepartmentId());
            }
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", ""));           
            }           
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));        
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setOrderBy(form.getOrderBy());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<AdminStaff> result = adminFacade.getAdminStaff(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询公司员工及外部人员一览信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 根据条件查询公司员工一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/companyStaffs.htm", method = RequestMethod.POST)
    public void getCompanyStaffInfo(HttpServletResponse resp, PersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try{
            StaffInfoQuery query = new StaffInfoQuery();
            query.setDepartmentId(form.getDepartmentId());
            query.setJobId(form.getJobId());
            if(Util.isNotNull(form.getJobStatus())){
                query.setJobStatus(form.getJobStatus()); 
            }else{
                query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            }
            query.setPageBean(new PageBean(form.getRows(), form.getPage() == null ? 1 : form.getPage()));
            ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("查询员工详细信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增人员
     * 
     * @param resp
     * @param form
     */
    @Security(auth = "CONSOLE_ADMIN_ADD", checkSource = true)
    @RequestMapping(value = "/insertStaffInfo.htm", method = RequestMethod.POST)
    public void insertStaffInfo(HttpServletResponse resp, PersonForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            StaffBasicInfo staffInfo = new StaffBasicInfo();
            Admin admin = new Admin();
            staffInfo.setJobStatus(form.getJobStatus());
            admin.setCompanyId(SpringSecurityUtil.getIntCompany());
            admin.setName(form.getName());
            staffInfo.setTelephone(form.getMobile());
            staffInfo.setEmail(form.getEmail());
            staffInfo.setPresentAddress(form.getPresentAddress());
            staffInfo.setTitleQuality(form.getTitleQuality());
            staffInfo.setIdentityNo(form.getIdentityNo());
            staffInfo.setJobStatus(form.getJobStatus());
            staffInfo.setSex(form.getSex());
            staffInfo.setBirthday(form.getBirthday().replace("/", ""));
            staffInfo.setCreateTime(new Date());
            staffInfo.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            staffInfo.setUserInfo(admin);
            ResultEntry<Integer> result = staffPersonFacade.insertStaffInfoByTalent(staffInfo);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("新增员工信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
