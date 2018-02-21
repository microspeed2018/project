package com.zjzmjr.admin.web.data.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
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

import com.zjzmjr.admin.web.data.form.DataHandlerForm;
import com.zjzmjr.core.api.data.IOutDataHandlerFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 对外部数据进行处理
 * 
 * @author oms
 * @version $Id: OutsideDataHandlerController.java, v 0.1 2017-9-30 下午2:45:00 oms Exp $
 */
@Controller
@RequestMapping("/outside/user")
public class OutsideDataHandlerController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OutsideDataHandlerController.class);

    private final static String index = "/WEB-INF/pages/data/data_import.jsp";
    
    @Resource(name = "outDataHandlerFacade")
    private IOutDataHandlerFacade outDataHandlerFacade;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }

    /**
     * excel文件导入
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/import.htm", method = RequestMethod.POST)
    public void importOutsideData(HttpServletRequest req, HttpServletResponse resp, DataHandlerForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
//    public String importOutsideData(ModelMap model, HttpServletRequest req, HttpServletResponse resp, DataHandlerForm form) {
        try {
            Assert.isTrue(StringUtil.nullToInteger(form.getHandlerType()).intValue() > 0, "请选择导入的类型");

            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_IMPORT_DATA.getValue());
            
            XSSFWorkbook wb = new XSSFWorkbook(form.getExcelAddress().getInputStream());
            XSSFSheet sheet = null;
            XSSFRow ssfRow = null;
//            Workbook workbook = WorkbookFactory.create(form.getExcelAddress().getInputStream());
//            Sheet sheet = workbook.getSheetAt(0);
//            Row ssfRow = null;
            // 总行数
            int rowNum = 0;
            int errNum = 51;
            List<Map<String, String>> dataLst = new ArrayList<Map<String, String>>();
            Map<String, String> dataMap = null;
            if(form.getHandlerType().intValue() == 1){
                adminBusiness.setComment("导入项目及合同信息");
                // 导入项目合同信息
                sheet = wb.getSheetAt(0);
                errNum = 51;
                // 总行数
                rowNum = sheet.getLastRowNum();
                for (int row = 1; row <= rowNum; row++) {
                    ssfRow = sheet.getRow(row);
//                    ssfRow = sheet.getRow(row);
                    dataMap = new HashMap<String, String>();
                    // for(int col = 0; col < 50; col++){
                    // excelLst.add(ExcelUtil.getCellValue(ssfRow.getCell(col)));
                    // }
                    dataMap.put("companyId", SpringSecurityUtil.getCompany());
                    dataMap.put("createUserId", SpringSecurityUtil.getPrincipal());
                    evaluateProject(dataMap, ssfRow);
                    evaluateContract(dataMap, ssfRow);
                    dataLst.add(dataMap);
                }
            }
            if(form.getHandlerType().intValue() == 2){
                adminBusiness.setComment("导入关联公司信息");
                // 导入关联公司信息
                sheet = wb.getSheetAt(1);
                errNum = 13;
                // 总行数
                rowNum = sheet.getLastRowNum();
                for (int row = 1; row <= rowNum; row++) {
                    ssfRow = sheet.getRow(row);
//                    ssfRow = sheet.getRow(row);
                    dataMap = new HashMap<String, String>();
                    // for(int col = 0; col < 50; col++){
                    // excelLst.add(ExcelUtil.getCellValue(ssfRow.getCell(col)));
                    // }
                    dataMap.put("companyId", SpringSecurityUtil.getCompany());
                    dataMap.put("createUserId", SpringSecurityUtil.getPrincipal());
                    evaluateCompanyAssociate(dataMap, ssfRow);
                    dataLst.add(dataMap);
                }
            }
            if(form.getHandlerType().intValue() == 3){
                adminBusiness.setComment("导入关联公司信息");
                // 导入关联公司信息
                sheet = wb.getSheetAt(2);
                // 总行数
                rowNum = sheet.getLastRowNum();
                for (int row = 1; row <= rowNum; row++) {
                    ssfRow = sheet.getRow(row);
//                    ssfRow = sheet.getRow(row);
                    dataMap = new HashMap<String, String>();
                    // for(int col = 0; col < 50; col++){
                    // excelLst.add(ExcelUtil.getCellValue(ssfRow.getCell(col)));
                    // }
                    dataMap.put("companyId", SpringSecurityUtil.getCompany());
                    dataMap.put("createUserId", SpringSecurityUtil.getPrincipal());
                    evaluateContractPayment(dataMap, ssfRow);
                    dataLst.add(dataMap);
                }
            }
            ResultEntry<List<String>> result = outDataHandlerFacade.importOutsideData(dataLst, form.getHandlerType());
            if (result.isSuccess()) {
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                putSuccess(model, "");
                XSSFCell resultCell = null;
                for (int row = 1; row <= rowNum; row++) {
                    resultCell = sheet.getRow(row).createCell(errNum);
                    resultCell.setCellValue(result.getT().get(row - 1));
                }
                resp.setContentType("application/vnd.ms-excel");// 写出
                setFileDownloadHeader(req, resp, "导入结果信息" + ".xls");
                ServletOutputStream outputStream = resp.getOutputStream();
                wb.write(outputStream);
            } else {
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setExtend1(result.getErrorMsg());
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("外部数据导入出错：", ex);
            putError(model, ex.getMessage());
        }

        responseAsJson(model, resp);
//        return index;
    }
    
    /**
     * 赋值到项目表
     * 
     * @param dataMap
     * @return
     */
    private void evaluateProject(Map<String, String> dataMap, Row ssfRow) {
        // 项目名称
        dataMap.put("name", ExcelUtil.getCellValue(ssfRow.getCell(0)));
        // 项目介绍人
        dataMap.put("introducer", ExcelUtil.getCellValue(ssfRow.getCell(1)));
        // 所在城市
        dataMap.put("city", ExcelUtil.getCellValue(ssfRow.getCell(2)));
        // 详细地址
        dataMap.put("address", ExcelUtil.getCellValue(ssfRow.getCell(3)));
        // 设计面积(㎡)
        dataMap.put("designArea", ExcelUtil.getCellValue(ssfRow.getCell(4)));
        // 投资额(万元)
        dataMap.put("investmentMount", ExcelUtil.getCellValue(ssfRow.getCell(5)));
        // 项目性质
        dataMap.put("nature", ExcelUtil.getCellValue(ssfRow.getCell(6)));
        // 项目类别
        dataMap.put("category", ExcelUtil.getCellValue(ssfRow.getCell(7)));
        // 项目类型
        dataMap.put("type", ExcelUtil.getCellValue(ssfRow.getCell(8)));
        // 经营部门
        dataMap.put("managementDepartment", ExcelUtil.getCellValue(ssfRow.getCell(9)));
        // 项目介绍
        dataMap.put("introduction", ExcelUtil.getCellValue(ssfRow.getCell(10)));
        // 意向合作方式
        dataMap.put("intentionalCooperation", ExcelUtil.getCellValue(ssfRow.getCell(11)));
        // 是否技术支持
        dataMap.put("needTechnical", ExcelUtil.getCellValue(ssfRow.getCell(12)));
        // 前期技术支持要求
        dataMap.put("preRequirements", ExcelUtil.getCellValue(ssfRow.getCell(13)));
        // 发包单位
        dataMap.put("contractAwardCompany", ExcelUtil.getCellValue(ssfRow.getCell(14)));
        // 是否有方案设计
        dataMap.put("haveScheme", ExcelUtil.getCellValue(ssfRow.getCell(15)));
        // 是否有扩初方案设计
        dataMap.put("haveDevelopment", ExcelUtil.getCellValue(ssfRow.getCell(16)));
        // 是否有施工图设计
        dataMap.put("haveDrawing", ExcelUtil.getCellValue(ssfRow.getCell(17)));
        // 是否有规划设计
        dataMap.put("havePlanning", ExcelUtil.getCellValue(ssfRow.getCell(18)));
        // 经营专员
        dataMap.put("managementPersonnel", ExcelUtil.getCellValue(ssfRow.getCell(19)));
        // 技术负责人
        dataMap.put("projectLeader", ExcelUtil.getCellValue(ssfRow.getCell(20)));
        // 合同或商务标技术负责人
        dataMap.put("projectLiable", ExcelUtil.getCellValue(ssfRow.getCell(21)));
        // 申请截止日期
        dataMap.put("applyDeadline", ExcelUtil.getCellValue(ssfRow.getCell(22)));
        // 是否重点项目
        dataMap.put("isMajorProject", ExcelUtil.getCellValue(ssfRow.getCell(23)));
        // 招标报名截止日期
        dataMap.put("biddingDeadline", ExcelUtil.getCellValue(ssfRow.getCell(24)));
        // 招标备注
        dataMap.put("biddingMemo", ExcelUtil.getCellValue(ssfRow.getCell(25)));
        // 报名文件制作人
        dataMap.put("biddingDocumentsProducer", ExcelUtil.getCellValue(ssfRow.getCell(26)));
        // 报名结果
        dataMap.put("registrationResults", ExcelUtil.getCellValue(ssfRow.getCell(27)));
        // 报名结果原因
        dataMap.put("registrationResultsMemo", ExcelUtil.getCellValue(ssfRow.getCell(28)));
        // 是否有技术标
        dataMap.put("haveTechnical", ExcelUtil.getCellValue(ssfRow.getCell(29)));
        // 保证金截止日期
        dataMap.put("marginDeadline", ExcelUtil.getCellValue(ssfRow.getCell(30)));
        // 投标截止日期
        dataMap.put("tenderDeadline", ExcelUtil.getCellValue(ssfRow.getCell(31)));
        // 招标文件备注
        dataMap.put("biddingDocumentsMemo", ExcelUtil.getCellValue(ssfRow.getCell(32)));
        // 保证金
        dataMap.put("bail", ExcelUtil.getCellValue(ssfRow.getCell(33)));
        // 最晚汇款日期
        dataMap.put("latestRemittanceDate", ExcelUtil.getCellValue(ssfRow.getCell(34)));
        // 汇款单位
        dataMap.put("remittanceCompany", ExcelUtil.getCellValue(ssfRow.getCell(35)));
        // 开户银行
        dataMap.put("bankName", ExcelUtil.getCellValue(ssfRow.getCell(36)));
        // 银行账号
        dataMap.put("accountNo", ExcelUtil.getCellValue(ssfRow.getCell(37)));
        // 保证金备注
        dataMap.put("bailMemo", ExcelUtil.getCellValue(ssfRow.getCell(38)));
        // 商务标制作人
        dataMap.put("businessProducer", ExcelUtil.getCellValue(ssfRow.getCell(39)));
        // 投标报价
        dataMap.put("tenderOffer", ExcelUtil.getCellValue(ssfRow.getCell(40)));
        // 是否中标
        dataMap.put("isWinBidding", ExcelUtil.getCellValue(ssfRow.getCell(41)));
        // 不通过理由
        dataMap.put("nopassReason", ExcelUtil.getCellValue(ssfRow.getCell(42)));
        // 项目阶段
        dataMap.put("step", ExcelUtil.getCellValue(ssfRow.getCell(43)));
        // 状态
        dataMap.put("status", ExcelUtil.getCellValue(ssfRow.getCell(44)));
    }

    /**
     * 赋值合同表
     * 
     * @param dataMap
     * @return
     */
    private void evaluateContract(Map<String, String> dataMap, Row ssfRow) {
        // 合同编号
        dataMap.put("contractNo", ExcelUtil.getCellValue(ssfRow.getCell(45)));
        // 合同金额
        dataMap.put("contractCapital", ExcelUtil.getCellValue(ssfRow.getCell(46)));
        // 签约日期
        dataMap.put("signDate", ExcelUtil.getCellValue(ssfRow.getCell(47)));
        // 合同备注
        dataMap.put("contractMemo", ExcelUtil.getCellValue(ssfRow.getCell(48)));
        // 院方比例
        dataMap.put("ratio", ExcelUtil.getCellValue(ssfRow.getCell(49)));
        // 比例备注
        dataMap.put("ratioMemo", ExcelUtil.getCellValue(ssfRow.getCell(50)));
    }

    /**
     * 赋值关联公司表
     * 
     * @param dataMap
     * @param ssfRow
     */
    private void evaluateCompanyAssociate(Map<String, String> dataMap, Row ssfRow){
        // 公司属性             
        dataMap.put("companyDistinguish", ExcelUtil.getCellValue(ssfRow.getCell(0)));
       // 企业全称
        dataMap.put("companyName", ExcelUtil.getCellValue(ssfRow.getCell(1)));
       // 法定代表人
        dataMap.put("legalPerson", ExcelUtil.getCellValue(ssfRow.getCell(2)));
       // 法定代表人联系方式
        dataMap.put("legalMobile", ExcelUtil.getCellValue(ssfRow.getCell(3)));
       // 所在城市
        dataMap.put("cityId", ExcelUtil.getCellValue(ssfRow.getCell(4)));
       // 详细地址
        dataMap.put("address", ExcelUtil.getCellValue(ssfRow.getCell(5)));
       // 企业介绍
        dataMap.put("introduction", ExcelUtil.getCellValue(ssfRow.getCell(6)));
       // 开户银行
        dataMap.put("bankName", ExcelUtil.getCellValue(ssfRow.getCell(7)));
       // 银行账号
        dataMap.put("accountNo", ExcelUtil.getCellValue(ssfRow.getCell(8)));
       // 统一社会信用代码
        dataMap.put("licenseNumber", ExcelUtil.getCellValue(ssfRow.getCell(9)));
       // 电话
        dataMap.put("mobile", ExcelUtil.getCellValue(ssfRow.getCell(10)));
       // 传真
        dataMap.put("faxPhone", ExcelUtil.getCellValue(ssfRow.getCell(11)));
        
    }
    
    /**
     * 赋值合同付款表信息
     * 
     * @param dataMap
     * @param ssfRow
     */
    private void evaluateContractPayment(Map<String, String> dataMap, Row ssfRow) {
        // 所属项目
        dataMap.put("projectId", ExcelUtil.getCellValue(ssfRow.getCell(0)));
        // 付款方式
        dataMap.put("paymentMode", ExcelUtil.getCellValue(ssfRow.getCell(1)));
        // 付款金额(万元)
        dataMap.put("paymentAmount", ExcelUtil.getCellValue(ssfRow.getCell(2)));
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
