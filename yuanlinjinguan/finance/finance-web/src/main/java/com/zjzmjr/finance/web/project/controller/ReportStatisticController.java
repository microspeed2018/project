package com.zjzmjr.finance.web.project.controller;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.project.IReportStatisticFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.ExcelUtil;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.model.FieldNameContextHolderImpl;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.common.util.FileUploadUtil.SavePathEnums;
import com.zjzmjr.core.model.project.ReportCondQuery;
import com.zjzmjr.core.model.project.ReportCondition;
import com.zjzmjr.core.model.project.ReportStatisticField;
import com.zjzmjr.finance.web.project.form.ReportConditionForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 统计报表的检索条件及统计结果
 * 
 * @author oms
 * @version $Id: ReportStatisticController.java, v 0.1 2017-9-19 上午11:19:29 oms Exp $
 */
@Controller
@RequestMapping("/report/user")
public class ReportStatisticController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(ReportStatisticController.class);

    @Resource(name = "reportStatisticFacade")
    private IReportStatisticFacade reportStatisticFacade;

    /**
     * 统计报表的条件查询信息
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/reportCondition.htm", method = RequestMethod.POST)
    public void getReportCondition(ModelMap model, HttpServletResponse resp){
        try {
            FieldNameContextHolderImpl field = new FieldNameContextHolderImpl();
            model.put("fields", field.initialize());
            ResultEntry<ReportCondition> result = reportStatisticFacade.getReportConditionByUserId(SpringSecurityUtil.getIntPrincipal());
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("统计条件查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 操作报表统计的查询条件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/handleCondition.htm", method = RequestMethod.POST)
    public void handleCondition(HttpServletResponse resp, ReportConditionForm form){
        Map<String, Object> model = new HashMap<String, Object>();
//        OutputStream ouputStream = null;
        try {
            ReportCondQuery record = new ReportCondQuery();
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setUserId(SpringSecurityUtil.getIntPrincipal());
            if(StringUtils.isNotBlank(form.getDispField())){
                record.setDispField(form.getDispField());
            }
            if(StringUtils.isNotBlank(form.getManagePerson())){
                record.setManagePerson(form.getManagePerson());
            }
            if(StringUtils.isNotBlank(form.getProjectLeader())){
                record.setProjectLeader(form.getProjectLeader());
            }
            if(StringUtils.isNotBlank(form.getProjectStep())){
                record.setProjectStep(form.getProjectStep());
            }
            if(Util.isNotNull(form.getStatus())){
                record.setStatus(form.getStatus());
            }
            if(StringUtils.isNotBlank(form.getTimeStart())){
                record.setTimeStart(form.getTimeStart());
            }
            if(StringUtils.isNotBlank(form.getTimeEnd())){
                record.setTimeEnd(form.getTimeEnd());
            }
            record.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            
            ResultPage<ReportStatisticField> result = reportStatisticFacade.getReportStaticByCondition(record);
            if(result.isSuccess()){
                putSuccess(model, "");
                // 标题行对应的属性名
                String[] rows = record.getDispField().split(",");
                // 设置文件标题行
                FieldNameContextHolderImpl field = new FieldNameContextHolderImpl();
                List<String> fields = field.getFieldChineseName(Arrays.asList(rows));
                String[] headers = new String[fields.size()];
                fields.toArray(headers);
                for(ReportStatisticField report : result.getList()){
                    report.setProjectName(StringUtil.surplusSubStr(report.getProjectName(), 0, 55));
                }
                // 生成excel文件
                HSSFWorkbook wb = ExcelUtil.exportExcel("统计报表", headers, rows, result.getList(), "yyyy/MM/dd");
                HSSFSheet sheet = wb.getSheetAt(0);
                sheet.setColumnWidth(0, 256*55);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                wb.write(baos);
                FileUploadUtil fileUtil = FileUploadUtil.getInstance(SavePathEnums.PATH_FINANCE);
                model.put("fileAccessUrl", fileUtil.uploadApk(fileUtil.parse(baos), "统计报表1.xls"));
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("统计条件变更出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

}
