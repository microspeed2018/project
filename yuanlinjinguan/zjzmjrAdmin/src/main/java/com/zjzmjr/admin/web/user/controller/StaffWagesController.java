package com.zjzmjr.admin.web.user.controller;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import com.zjzmjr.admin.web.user.form.StaffWagesForm;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.api.user.IStaffWagesFacade;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.common.view.ViewExcel;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;
import com.zjzmjr.security.web.annotation.Security;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 薪酬管理页面
 * 
 * @author Administrator
 * @version $Id: StaffWagesController.java, v 0.1 2017-12-12 下午4:02:06 Administrator Exp $
 */
@RequestMapping("/staffWages/user")
@Controller
public class StaffWagesController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(StaffWagesController.class);

    private final static String index = "/WEB-INF/pages/user/staffWages.jsp";

    @Resource(name = "staffWagesFacade")
    private IStaffWagesFacade staffWagesFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;
    
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model, HttpServletRequest req) {
        model.put("staffWagesImportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.STAFFWAGES_IMPORT));
        model.put("staffWagesExportAuth", SpringSecurityUtil.hasAuth(UserAuthParams.STAFF_EXPORT));
        return index;
    }

    /**
     * 薪酬记录一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/queryStaffWages.htm", method = RequestMethod.POST)
    public void queryStaffWages(HttpServletResponse resp, StaffWagesForm form) {
        logger.debug("queryStaffWages 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            StaffWagesQuery query = new StaffWagesQuery();
            query.setEmployeeNo(form.getEmployeeNo());
            query.setName(form.getName());
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getStartDate())){
                String startDateStr = DateUtil.format(DateUtil.parse(form.getStartDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(startDateStr),"时间格式错误");
                query.setStartDate(startDateStr);
                
            }
            if(Util.isNotNull(form.getEndDate())){
                String endDateStr = DateUtil.format(DateUtil.parse(form.getEndDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(endDateStr),"时间格式错误");
                query.setEndDate(endDateStr);
            }
            query.setMobile(form.getMobile());
            query.setTelephone(form.getTelephone());
            query.setStatus(form.getStatus());
            query.setStaffInfoId(form.getStaffInfoId());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<StaffWagesInfo> result = staffWagesFacade.queryStaffWages(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("查询薪酬信息失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("queryStaffWages 执行结束");
        responseAsJson(model, resp);
    }

    /**
     * 根据id获取薪酬记录详情
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/queryStaffWagesById.htm", method = RequestMethod.POST)
    public void queryStaffWagesById(HttpServletResponse resp, StaffWagesForm form) {
        logger.debug("queryStaffWagesById 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getId()),"id不能为空！");
            ResultEntry<StaffWagesInfo> result = staffWagesFacade.getStaffWageById(form.getId());
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("查询薪酬信息失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("queryStaffWagesById 执行结束");
        responseAsJson(model, resp);
    }
    
    /**
     * 导出excel
     * 
     * @param resp
     * @param form
     * @param req
     * @return
     */
    @Security(auth = { UserAuthParams.STAFFWAGES_EXPORT}, checkSource = false)
    @RequestMapping(value = "/exportExcel.htm", method = RequestMethod.POST)
    public ModelAndView exportExcel(HttpServletResponse resp, StaffWagesForm form, HttpServletRequest req){
        logger.debug("exportExcel 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        ViewExcel viewExcel = new ViewExcel();
        ModelAndView modelView = new ModelAndView(index, model);
        try {
            // 封装查询条件
            StaffWagesQuery query = new StaffWagesQuery();
            query.setEmployeeNo(form.getEmployeeNo());
            query.setName(form.getName());
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getStartDate())){
                // 转换时间，校验时间格式
                String startDateStr = DateUtil.format(DateUtil.parse(form.getStartDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(startDateStr),"时间格式错误");
                query.setStartDate(startDateStr);
                
            }
            if(Util.isNotNull(form.getEndDate())){
                // 转换时间，校验时间格式
                String endDateStr = DateUtil.format(DateUtil.parse(form.getEndDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(endDateStr),"时间格式错误");
                query.setEndDate(endDateStr);
            }
            query.setMobile(form.getMobile());
            query.setTelephone(form.getTelephone());
            query.setStatus(form.getStatus());
            query.setStaffInfoId(form.getStaffInfoId());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            // 查询数据库，并将结果写入excel
            ResultEntry<String> result = staffWagesFacade.exportExcel(query);
            if (result.isSuccess()) {
                viewExcel.setSrcFilePath(result.getT());
                modelView = new ModelAndView(viewExcel, model);
            } else {
                model.put("result",result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("导出薪酬信息一览失败", ex);
            model.put("result",ex.getMessage());      
            putError(model, ex.getMessage());
        }
        logger.debug("exportExcel 执行结束");
        return modelView;
    }
    
    /**
     * 导入excel
     * 
     * @param resp
     * @param form
     * @param req
     */
    @Security(auth = { UserAuthParams.STAFFWAGES_IMPORT}, checkSource = false)
    @RequestMapping(value = "/importExcel.htm", method = RequestMethod.POST)
    public void importExcel(HttpServletResponse resp, StaffWagesForm form, HttpServletRequest req){
        logger.debug("importExcel 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Util.isNotNull(form.getExcelAddress());
            Assert.isTrue(Util.isNotNull(form.getExcelAddress()), "请选择文件");
            XSSFWorkbook wb = new XSSFWorkbook(form.getExcelAddress().getInputStream());
            XSSFSheet sheet = wb.getSheetAt(0);
            // 总行数
            int rowNum = sheet.getLastRowNum();
            this.checkHeader(sheet);
            XSSFRow rowHead = sheet.getRow(0);
            rowHead.createCell(23).setCellValue("导入结果");
            rowHead.createCell(24).setCellValue("失败原因");
            // 合并单元格
            CellRangeAddress cra = new CellRangeAddress(0, 1, 23, 23); // 起始行, 终止行, 起始列, 终止列  
            sheet.addMergedRegion(cra);
            cra = new CellRangeAddress(0, 1, 24, 24); // 起始行, 终止行, 起始列, 终止列  
            sheet.addMergedRegion(cra);
            // 检查头部是否正确
            List<StaffWagesInfo> list = new ArrayList<StaffWagesInfo>();
            // 将excel内的数据转换成对象
            for (int i = 2; i <= rowNum; i++) {
                XSSFRow row = sheet.getRow(i);
                if (this.CheckRowNull(row)) {// 是空行就不导入
                    continue;
                }
                XSSFCell resultCell = row.createCell(23);// 导入结果
                XSSFCell resultReasonCell = row.createCell(24);// 失败原因
                try {
                    // 封装对象
                    StaffWagesInfo record = getStaffWages(row);
                    resultCell.setCellValue("成功");
                    list.add(record);
                } catch (Exception e) {
                    resultCell.setCellValue("失败");
                    resultReasonCell.setCellValue(e.getMessage());
                }
            }
            if(Util.isNotNull(list)){
                // 批量插入数据库
                ResultRecord<StaffWagesInfo> result = staffWagesFacade.batchInsert(list);
                if (result.isSuccess()) {
                    model.put("rows", list);
                    model.put("total", list.size());
                    // 将结果excel转换成byte存入缓存中
                    ByteArrayOutputStream os = new ByteArrayOutputStream();  
                    wb.write(os);
                    byte[] b = os.toByteArray();
                    os.close();
                    JedisPull.setObject(SpringSecurityUtil.getIntPrincipal() + "StaffWagesImportExcel", b, 60 * 60);
                    putSuccess(model, "");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                }
            }else{
             // 将结果excel转换成byte存入缓存中
                ByteArrayOutputStream os = new ByteArrayOutputStream();  
                wb.write(os);
                byte[] b = os.toByteArray();
                os.close();
                JedisPull.setObject(SpringSecurityUtil.getIntPrincipal() + "StaffWagesImportExcel", b, 1 * 60);
                model.put("rows", "");
                model.put("total", 0);
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("导入员工薪酬失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("importExcel 执行结束");
        responseAsJson(model, resp);
    }
    
    /**
     * 从缓存中取出excel
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(value = "/getExcel.htm", method = RequestMethod.GET)
    public void getExcel(HttpServletResponse resp, HttpServletRequest req){
        logger.debug("getExcel 执行开始");
        Map<String, Object> model = new HashMap<String, Object>();
        ServletOutputStream outputStream = null;
        ByteArrayInputStream in = null;
        try {
            byte[] bytes= JedisPull.getObject(SpringSecurityUtil.getIntPrincipal() + "StaffWagesImportExcel", byte[].class);
            if (Util.isNotNull(bytes)) {
                in = new ByteArrayInputStream(bytes); 
                XSSFWorkbook wb = new XSSFWorkbook(in);
                resp.setContentType("application/vnd.ms-excel");// 写出
                setFileDownloadHeader(req, resp, "员工薪酬导入结果" + ".xlsx");
                outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                putError(model, "没有可导出的记录");
            }
        } catch (Exception e) {
            logger.error("导出薪酬导入记录出错");
            putError(model, e.getMessage());
        } finally {
            if (Util.isNotNull(outputStream)) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    logger.error("", e);
                }
            }
        }
        logger.debug("getExcel 执行结束");
        responseAsJson(model, resp);
    }
    
    /***
     * 
     * 判断是否为空行
     * 
     * @param row
     * @return
     * @throws ApplicationException
     * @throws ParseException
     */
    private boolean CheckRowNull(XSSFRow xssFRow) {
        boolean is = true;
        Iterator<Cell> iterator = xssFRow.iterator();
        while (iterator.hasNext()) {
            Cell c = iterator.next();
            if (c.getCellType() != XSSFCell.CELL_TYPE_BLANK) {
                is = false;
            }
        }
        return is;
    }
    
    /**
     * 检验excel头部格式
     * 
     * @param headers
     * @return
     */
    private boolean checkHeader(XSSFSheet sheet) {
        boolean result = false;
        XSSFRow header1 = sheet.getRow(0);
        XSSFRow header2 = sheet.getRow(1);
        int i = 0;
        if (StringUtils.equals("序号", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("月份", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("工号", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("姓名", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("部门", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("基本工资", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("岗位工资", header2.getCell(i++).getStringCellValue().trim())
                && StringUtils.equals("绩效工资", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("奖金提成", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("职称津贴", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("司龄津贴", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("其他", header2.getCell(i++).getStringCellValue().trim())  && StringUtils.equals("应发小计", header2.getCell(i++).getStringCellValue().trim())
                && StringUtils.equals("缺勤扣款", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("社保", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("公积金", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("其他", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("应扣小计", header2.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("应税工资", header1.getCell(i++).getStringCellValue().trim())
                && StringUtils.equals("代扣个税", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("通讯补贴", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("实发工资", header1.getCell(i++).getStringCellValue().trim()) && StringUtils.equals("备注", header1.getCell(i++).getStringCellValue().trim())) {
            result = true;
        } else {
            throw new RuntimeException("文件模版错误");
        }
        return result;
    }


    /**
     * 封装薪酬信息
     * 
     * @param row
     * @return
     */
    private StaffWagesInfo getStaffWages(XSSFRow row){
        StaffWagesInfo record = new StaffWagesInfo();
        int i = 0;
        String dateStr = ExcelUtil.getCellValue(row.getCell(++i)).trim();
        Date date = DateUtil.parse(dateStr, "yyyy-MM-dd");
        Assert.isTrue(Util.isNotNull(date),"月份格式错误");
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        record.setWagesYear(cal.get(Calendar.YEAR));
        record.setWagesMonth(cal.get(Calendar.MONTH) + 1);
        String employeeNo = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 工号
        Assert.isTrue(NumberUtils.isDigits(employeeNo),"工号格式错误" );
        StaffInfoQuery query = new StaffInfoQuery();
        query.setEmployeeNo(Integer.valueOf(employeeNo));
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        Assert.isTrue(result.isSuccess() && Util.isNotNull(result.getList()) && Util.isNotNull(result.getList().get(0).getUserInfo()),"工号不存在");
        StaffBasicInfo staffInfo = result.getList().get(0);
        record.setEmployeeNo(staffInfo.getEmployeeNo());
        record.setStaffInfoId(staffInfo.getId());
        record.setCompanyId(staffInfo.getUserInfo().getCompanyId());
        String name = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 姓名
        Assert.isTrue(StringUtils.isNotBlank(name),"姓名为空");
        Assert.isTrue(staffInfo.getUserInfo().getName().equals(name),"员工姓名不正确");
        record.setName(name);
        String department = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 部门
        Assert.isTrue(StringUtils.isNotBlank(department),"部门为空");
        Assert.isTrue(staffInfo.getDepartmentName().equals(department),"部门不正确");
        record.setDepartmentName(department);
        // 查询该员工该月份是否已存在记录
        StaffWagesQuery wagesQuery = new StaffWagesQuery();
        wagesQuery.setEmployeeNo(record.getEmployeeNo());
        wagesQuery.setWagesYear(record.getWagesYear());
        wagesQuery.setWagesMonth(record.getWagesMonth());
        wagesQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffWagesInfo> queryResult = staffWagesFacade.queryStaffWages(wagesQuery);
        Assert.isTrue(!queryResult.isSuccess(), "该员工该月份记录已存在");
        String basePay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 基本工资
        Assert.isTrue(NumberUtils.isNumber(basePay),"基本工资格式错误" );
        record.setBasePay(new BigDecimal(basePay));
        String postPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 岗位工资
        Assert.isTrue(NumberUtils.isNumber(postPay),"岗位工资格式错误" );
        record.setPostPay(new BigDecimal(postPay));
        String meritPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 绩效工资
        Assert.isTrue(NumberUtils.isNumber(meritPay),"绩效工资格式错误" );
        record.setMeritPay(new BigDecimal(meritPay));
        String bonusPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 奖金提成
        Assert.isTrue(NumberUtils.isNumber(bonusPay),"奖金提成格式错误" );
        record.setBonusPay(new BigDecimal(bonusPay));
        String professionalPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 职称津贴
        Assert.isTrue(NumberUtils.isNumber(professionalPay),"职称津贴格式错误" );
        record.setProfessionalPay(new BigDecimal(professionalPay));
        String silingPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 司龄津贴
        Assert.isTrue(NumberUtils.isNumber(silingPay),"司龄津贴格式错误" );
        record.setSilingPay(new BigDecimal(silingPay));
        String otherPay = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 其他（应发）
        Assert.isTrue(NumberUtils.isNumber(otherPay),"其他(应发)格式错误" );
        record.setOtherPay(new BigDecimal(otherPay));
        String paySubtotal = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 应发小计
        Assert.isTrue(NumberUtils.isNumber(paySubtotal),"应发小计格式错误" );
        record.setPaySubtotal(new BigDecimal(paySubtotal));
        String absenceDeduct = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 缺勤扣款
        Assert.isTrue(NumberUtils.isNumber(absenceDeduct),"缺勤扣款格式错误" );
        record.setAbsenceDeduct(new BigDecimal(absenceDeduct));
        String socialFund = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 社保
        Assert.isTrue(NumberUtils.isNumber(socialFund),"社保格式错误" );
        record.setSocialFund(new BigDecimal(socialFund));
        String housingProvident = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 公积金
        Assert.isTrue(NumberUtils.isNumber(housingProvident),"公积金格式错误" );
        record.setHousingProvident(new BigDecimal(housingProvident));
        String otherDeduct = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 其他(应扣)
        Assert.isTrue(NumberUtils.isNumber(otherDeduct),"其他(应扣)格式错误" );
        record.setOtherDeduct(new BigDecimal(otherDeduct));
        String deductSubtotal = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 应扣小计
        Assert.isTrue(NumberUtils.isNumber(deductSubtotal),"应扣小计格式错误" );
        record.setDeductSubtotal(new BigDecimal(deductSubtotal));
        String taxableWages = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 应税工资
        Assert.isTrue(NumberUtils.isNumber(taxableWages),"应税工资格式错误" );
        record.setTaxableWages(new BigDecimal(taxableWages));
        String individualTax = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 代扣个税
        Assert.isTrue(NumberUtils.isNumber(individualTax),"代扣个税格式错误" );
        record.setIndividualTax(new BigDecimal(individualTax));
        String communicationSubsidy = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 通讯补贴
        Assert.isTrue(NumberUtils.isNumber(communicationSubsidy),"通讯补贴格式错误" );
        record.setCommunicationSubsidy(new BigDecimal(communicationSubsidy));
        String realWages = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 实发工资
        Assert.isTrue(NumberUtils.isNumber(realWages),"实发工资格式错误" );
        record.setRealWages(new BigDecimal(realWages));
        String memo = ExcelUtil.getCellValue(row.getCell(++i)).trim();// 备注
        record.setMemo(memo);
        record.setCreateTime(new Date());
        record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
        return record;
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
