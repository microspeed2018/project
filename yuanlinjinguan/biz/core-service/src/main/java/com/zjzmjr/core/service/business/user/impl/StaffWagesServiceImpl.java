package com.zjzmjr.core.service.business.user.impl;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.annotation.Resource;

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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjzmjr.common.util.RandomCodeUtil;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.model.user.StaffWagesInfo;
import com.zjzmjr.core.model.user.StaffWagesQuery;
import com.zjzmjr.core.service.business.user.StaffInfoService;
import com.zjzmjr.core.service.business.user.StaffWagesService;
import com.zjzmjr.core.service.mapper.dao.user.StaffWagesMapper;

@Service("staffWagesService")
public class StaffWagesServiceImpl implements StaffWagesService {

    private static final Logger logger = LoggerFactory.getLogger(StaffWagesServiceImpl.class);

    @Resource(name = "staffWagesMapper")
    private StaffWagesMapper staffWagesMapper;
    
    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    // 联合银行模版路径
    private static final String WAGES_MODEL_ADDRESS = PropertyUtils.getPropertyFromFile("commBiz", "wages_model_address");
    
    // 模版存放路径
    private static final String MODEL_ADDRESS = PropertyUtils.getPropertyFromFile("commBiz", "model_address");


    /**
     * 
     * @see com.zjzmjr.core.service.business.user.StaffWagesService#queryStaffWages(com.zjzmjr.core.model.user.StaffWagesQuery)
     */
    @Override
    public ResultPage<StaffWagesInfo> queryStaffWages(StaffWagesQuery query) {
        logger.debug("queryStaffWages执行开始");
        ResultPage<StaffWagesInfo> result = new ResultPage<StaffWagesInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("queryStaffWages执行结束");
            return result;
        }
        int total = staffWagesMapper.queryStaffWagesCount(query);
        if (total > 0) {
            List<StaffWagesInfo> list = staffWagesMapper.queryStaffWages(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        logger.debug("queryStaffWages执行结束");
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.user.StaffWagesService#getStaffWageById(java.lang.Integer)
     */
    @Override
    public ResultEntry<StaffWagesInfo> getStaffWageById(Integer id) {
        logger.debug("getStaffWageById执行开始");
        ResultEntry<StaffWagesInfo> result = new ResultEntry<StaffWagesInfo>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("getStaffWageById执行结束");
            return result;
        }
        StaffWagesInfo wages = staffWagesMapper.getStaffWageById(id);
        if (Util.isNotNull(wages)) {
            result.setSuccess(true);
            result.setT(wages);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }
        logger.debug("getStaffWageById执行结束");
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.user.StaffWagesService#batchInsert(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultRecord<StaffWagesInfo> batchInsert(List<StaffWagesInfo> list) throws ApplicationException {
        logger.debug("getStaffWageById执行开始");
        ResultRecord<StaffWagesInfo> result = new ResultRecord<StaffWagesInfo>();
        if (Util.isNull(list)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("batchInsert执行结束");
            return result;
        }
        // 一次插入数据库的集合最大值
        int pointsDataLimit = 1500;
        int size = list.size();
        int cnt = 0;
        if (size > pointsDataLimit) {
            // 若集合数量大于上限值，则计算总共需要拆分成几个完整的集合
            int part = size / pointsDataLimit;
            for (int i = 0; i < part; i++) {
                List<StaffWagesInfo> listPage = list.subList(0, pointsDataLimit);
                cnt += staffWagesMapper.batchInsert(listPage);
                list.subList(0, pointsDataLimit).clear();
            }
            // 将剩余的数据插入数据库
            if(!list.isEmpty()){
                cnt += staffWagesMapper.batchInsert(list);
            }
        } else {
            // 若集合数量小于上限值则直接入库
            cnt = staffWagesMapper.batchInsert(list);
        }
        if (cnt == list.size()) {
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            throw new ApplicationException("新增员工薪酬失败");
        }
        logger.debug("batchInsert执行结束");
        return result;
    }
    
    @Override
    public ResultEntry<String> exportExcel(StaffWagesQuery query) {
        logger.debug("exportExcel执行开始");
        ResultEntry<String> result = new ResultEntry<String>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("queryStaffWages执行结束");
            return result;
        }
        // 查询数据库
        List<StaffWagesInfo> list = staffWagesMapper.queryStaffWages(query);
        if (Util.isNotNull(list)) {
            try {
                // 导出excel
                result = getPrintModel(WAGES_MODEL_ADDRESS, list);
            } catch (Exception e) {
                logger.error("", e);
                result.setSuccess(false);
                result.setErrorMsg("导出文件失败");
            }
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.RECORD_NOT_EXSIST);
        }
        logger.debug("exportExcel执行结束");
        return result;
    }

    public ResultEntry<String> getPrintModel(String filename, List<StaffWagesInfo> list) throws Exception {
        logger.debug("getPrintModel 执行开始  入参:{}{}", filename, list);
        ResultEntry<String> result = new ResultEntry<String>();
        if (Util.isNull(filename) || Util.isNull(list)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        // 获取模版文件
        File oldfile = new File(filename);
        int byteread = 0;
        if (oldfile.exists()) {
            InputStream inStream = new FileInputStream(filename); // 读入原文件
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
            String newPathName = "print" + sdf.format(new Date()) + RandomCodeUtil.getRandomCode(RandomCodeUtil.MODE_NUMBER, 3) + ".xlsx";
            String newPath = MODEL_ADDRESS;
            // 生成新的excel文件
            File file = new File(newPath);
            if (!file.exists() && !file.isDirectory()) {
                file.mkdir();
            }
            // 将新的文件输出
            file = new File(newPath, newPathName);
            FileOutputStream fs = new FileOutputStream(file.getPath());
            FileOutputStream fos = null;
            byte[] buffer = new byte[1444];
            while ((byteread = inStream.read(buffer)) != -1) {
                fs.write(buffer, 0, byteread);
            }
            try {
                XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(file.getPath()));
                workbook.setForceFormulaRecalculation(true);// 自动重新计算公式
//                HSSFFormulaEvaluator evaluator = new HSSFFormulaEvaluator(workbook);
                XSSFSheet sheet = workbook.getSheetAt(0);
                for (int i = 0; i < list.size(); i++){
                    // 遍历集合，将数据写入excel
                    StaffWagesInfo record = list.get(i);
                    XSSFRow row = sheet.getRow(i + 2);
                    int index = 1;
                    row.getCell(0).setCellValue(i + 1);
                    row.getCell(index++).setCellValue(record.getWagesYear() + "/" + record.getWagesMonth());
                    row.getCell(index++).setCellValue(record.getEmployeeNo());
                    row.getCell(index++).setCellValue(record.getName());
                    row.getCell(index++).setCellValue(record.getDepartmentName());
                    if(Util.isNotNull(record.getBasePay())){
                        row.getCell(index).setCellValue(record.getBasePay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getPostPay())){
                        row.getCell(index).setCellValue(record.getPostPay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getMeritPay())){
                        row.getCell(index).setCellValue(record.getMeritPay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getBonusPay())){
                        row.getCell(index).setCellValue(record.getBonusPay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getProfessionalPay())){
                        row.getCell(index).setCellValue(record.getProfessionalPay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getOtherPay())){
                        row.getCell(index).setCellValue(record.getOtherPay().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getPaySubtotal())){
                        row.getCell(index).setCellValue(record.getPaySubtotal().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getAbsenceDeduct())){
                        row.getCell(index).setCellValue(record.getAbsenceDeduct().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getSocialFund())){
                        row.getCell(index).setCellValue(record.getSocialFund().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getHousingProvident())){
                        row.getCell(index).setCellValue(record.getHousingProvident().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getOtherDeduct())){
                        row.getCell(index).setCellValue(record.getOtherDeduct().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getDeductSubtotal())){
                        row.getCell(index).setCellValue(record.getDeductSubtotal().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getTaxableWages())){
                        row.getCell(index).setCellValue(record.getTaxableWages().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getIndividualTax())){
                        row.getCell(index).setCellValue(record.getIndividualTax().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getCommunicationSubsidy())){
                        row.getCell(index).setCellValue(record.getCommunicationSubsidy().doubleValue());
                    }
                    index++;
                    if(Util.isNotNull(record.getRealWages())){
                        row.getCell(index).setCellValue(record.getRealWages().doubleValue());
                    }
                    index++;
                    row.getCell(index++).setCellValue(record.getMemo());
                }
                fos = new FileOutputStream(file.getPath());
                workbook.write(fos);
                result.setSuccess(true);
                result.setT(file.getPath());
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setErrorMsg("Excel导出失败!");
            } catch (IOException e) {
                e.printStackTrace();
                result.setSuccess(false);
                result.setErrorMsg("Excel导出失败!");
            } finally {
                if (null != inStream) {
                    inStream.close();
                }
                if (null != fs) {
                    fs.flush();
                    // 结束关闭
                    fs.close();
                }
                if (null != fos) {
                    fos.flush();
                    fos.close();
                }
            }
        }else{
            result.setSuccess(false);
            result.setErrorMsg("模版文件路径错误");
        }
        logger.debug("getPrintModel 执行结束  出参:{}", result);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.StaffWagesService#importExcel(org.apache.poi.xssf.usermodel.XSSFSheet)
     */
    @Override
    public ResultRecord<StaffWagesInfo> importExcel(StaffWagesQuery query) throws ApplicationException {
        logger.debug("importExcel执行开始");
        ResultRecord<StaffWagesInfo> result = new ResultRecord<StaffWagesInfo>();
        if (Util.isNull(query) || Util.isNull(query.getUserId()) ) {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.PARAM_ERROR);
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            logger.debug("importExcel执行结束");
            return result;
        }
        XSSFWorkbook wb = new XSSFWorkbook();
//        try {
////            wb = new XSSFWorkbook(query.getExcelAddress().getInputStream());
//            wb = new XSSFWorkbook();
//        } catch (IOException e1) {
//            logger.error("", e1);
//            result.setSuccess(false);
//            result.setErrorMsg("文件读取失败");
//            logger.debug("importExcel执行结束");
//            return result;
//        }
        XSSFSheet sheet = wb.getSheetAt(0);
        // 总行数
        int rowNum = sheet.getLastRowNum();
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
                StaffWagesInfo record = getStaffWages(row, query.getUserId());
                resultCell.setCellValue("成功");
                list.add(record);
            } catch (Exception e) {
                resultCell.setCellValue("失败");
                resultReasonCell.setCellValue(e.getMessage());
            }
        }
        if (Util.isNotNull(list)) {
            result.setList(list);
//            result = batchInsert(list);
        } 
        if(result.isSuccess()){
            ByteArrayOutputStream os = null;
            try {
                os = new ByteArrayOutputStream();  
                wb.write(os);
                byte[] b = os.toByteArray();
                os.close();
                JedisPull.setObject(query.getUserId() + "StaffWagesImportExcel", b, 60 * 60);
            } catch (Exception e) {
                result.setSuccess(false);
                result.setErrorMsg("生成结果文件失败");
            }finally{
                if(Util.isNotNull(os)){
                    try {
                        os.close();
                    } catch (IOException e) {
                        logger.error("", e);
                    }
                }
            }
        }
        logger.debug("importExcel执行结束");
        return result;
    }

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
     * 封装薪酬信息
     * 
     * @param row
     * @return
     */
    private StaffWagesInfo getStaffWages(XSSFRow row, Integer userId){
        StaffWagesInfo record = new StaffWagesInfo();
        int i = 0;
        String dateStr = ExcelUtil.getCellValue(row.getCell(++i)).trim(); // 月份 
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
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
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
        StaffWagesQuery wagesQuery = new StaffWagesQuery();
        wagesQuery.setEmployeeNo(record.getEmployeeNo());
        wagesQuery.setWagesYear(record.getWagesYear());
        wagesQuery.setWagesMonth(record.getWagesMonth());
        int total = staffWagesMapper.queryStaffWagesCount(wagesQuery);
        Assert.isTrue(total == 0, "该员工该月份记录已存在");
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
        record.setCreateUserId(userId);
        return record;
    }

}
