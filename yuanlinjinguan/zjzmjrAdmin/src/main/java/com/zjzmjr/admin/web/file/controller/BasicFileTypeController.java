package com.zjzmjr.admin.web.file.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.file.form.BasicFileForm;
import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 
 * 
 * @author oms
 * @version $Id: BasicFileTypeController.java, v 0.1 2017-9-11 下午11:08:44 oms Exp $
 */
@Controller
@RequestMapping("/baseFile/user")
public class BasicFileTypeController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(BasicFileTypeController.class);

    private static final String index = "/WEB-INF/pages/file/basicFile.jsp";

    @Resource(name = "projectFileInfoFacade")
    private IProjectFileInfoFacade projectFileInfoFacade;

    /**
     * 基础资料一览
     * 
     * @param model
     * @return
     */
    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }
    
    /**
     * 获取资料文件一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/fileTypeList.htm", method = RequestMethod.POST)
    public void getBasicFileTypeList(HttpServletResponse resp, BasicFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            if(StringUtils.isNotBlank(form.getFileName())){
                query.setFileName(form.getFileName());
            }
            if(Util.isNotNull(form.getBasicId())){
                query.setBasicId(form.getBasicId());
            }
            query.setStatus(form.getStatus());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<ProjectFile> result = projectFileInfoFacade.getBasicFileTypeByCondition(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("根据条件获取资料列表出错:", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 更新文件资料信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateFile.htm", method = RequestMethod.POST)
    public void updateProjectFile(HttpServletResponse resp, BasicFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getId()),"id为空");
            Assert.isTrue(Util.isNotNull(form.getBasicId()),"请选择资料分类");
            Assert.isTrue(Util.isNotNull(form.getId()), "请选择需更新的记录");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileName()), "请输入资料名称");
            ProjectFile record = new ProjectFile();
            record.setId(form.getId());
            record.setBasicId(form.getBasicId());
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setName(form.getFileName());
            record.setStatus(form.getStatus());
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = projectFileInfoFacade.updateProjectFileById(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("更新资料列表出错:", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 插入文件资料信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/insertFile.htm", method = RequestMethod.POST)
    public void insertProjectFile(HttpServletResponse resp, BasicFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getBasicId()),"请选择资料分类");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileName()), "请输入资料名称");
            Assert.isTrue(Util.isNotNull(form.getStatus()), "请选择状态");
            ProjectFile record = new ProjectFile();
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setBasicId(form.getBasicId());
            record.setName(form.getFileName());
            record.setStatus(form.getStatus());
            record.setCreateTime(new Date());
            record.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setUpdateTime(record.getCreateTime());
            record.setUpdateUserId(record.getCreateUserId());
            ResultEntry<Integer> result = projectFileInfoFacade.insertProjectFile(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("插入资料列表出错:", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 删除文件资料信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/deleteFile.htm", method = RequestMethod.POST)
    public void deleteProjectFile(HttpServletResponse resp, BasicFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getId()), "请选择要删除的记录");
            
            ResultEntry<Integer> result = projectFileInfoFacade.deleteProjectFileById(form.getId());
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("删除资料列表出错:", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * basic条件查询员工id
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getFileName.htm", method = RequestMethod.POST)
    public void getFileName(HttpServletResponse resp, BasicFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setCategoryId(form.getCategoryId());
            query.setAttributeId(form.getAttributeId());
            ResultRecord<ProjectFile> result = projectFileInfoFacade.getProjectFileName(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception e) {
            logger.error("根据条件获取员工资料名出错:", e);
            putError(model, e.getMessage());
        }
        responseAsJson(model, resp);
    }
}
