package com.zjzmjr.finance.web.fileUpload.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.finance.web.fileUpload.form.ReceiveFileForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 文件上传处理控制层
 * 
 * @author oms
 * @version $Id: FileUpLoandController.java, v 0.1 2017-8-21 上午11:18:06 oms Exp $
 */
@Controller
@RequestMapping("/fileUpLoad")
public class FileUpLoandController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(FileUpLoandController.class);

    @Resource(name = "projectFileInfoFacade")
    private IProjectFileInfoFacade projectFileInfoFacade;
    
    /**
     * 获取所有的上传文件类型
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/user/getProjectFile.htm", method = RequestMethod.POST)
    public void getProjectFile(ModelMap model, HttpServletResponse resp){
        try {
            ResultRecord<ProjectFile> result = projectFileInfoFacade.getAllProjectFileInfo();
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取上传文件类型出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取指定的文件类型的文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/getSpecifyFile.htm", method = RequestMethod.POST)
    public void getSpecifyProjectFile(HttpServletResponse resp, ReceiveFileForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(form.getProjectId());
            query.setFileId(StringUtil.stringToInteger(form.getFileId()));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ProjectFileInfo> result = projectFileInfoFacade.getFileUploadByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取指定文件类型出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 附件上传
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/fileUpLoad.htm", method = RequestMethod.POST)
    public void fileUpLoad(HttpServletResponse resp, ReceiveFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<String> result = new ArrayList<String>();
            if (form.getFileAddress().length > 0){
                FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_FINANCE);
                fileUploadUtil.setBusiness(form.getBusiness());
                for (int i = 0; i < form.getFileAddress().length; i++){
                    fileUploadUtil.setIndex(i);
                    result.add(fileUploadUtil.uploadApk(form.getFileAddress()[i]));
                }
                model.put("data", result);
                putSuccess(model, "");
            } else {
                putError(model, "请选择上传的文件");
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 附件上传(不登录)
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/fileUpLoad.htm", method = RequestMethod.POST)
    public void fileUpLoadNotLogin(HttpServletResponse resp, ReceiveFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            List<String> result = new ArrayList<String>();
            if (form.getFileAddress().length > 0){
                FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_FINANCE);
                fileUploadUtil.setBusiness(form.getBusiness());
                for (int i = 0; i < form.getFileAddress().length; i++){
                    fileUploadUtil.setIndex(i);
                    result.add(fileUploadUtil.uploadApk(form.getFileAddress()[i]));
                }
                model.put("data", result);
                putSuccess(model, "");
            } else {
                putError(model, "请选择上传的文件");
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 基础数据选取文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getFileByBasic.htm", method = RequestMethod.POST)
    public void getFileByBasic(HttpServletResponse resp, ReceiveFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setFileName(form.getFileName());
            query.setCategoryId(form.getCategoryId());
            query.setAttributeId(form.getAttributeId());
            query.setStatus(GenerateConstants.number_1);
            ResultRecord<ProjectFileInfo> result =  projectFileInfoFacade.getFileByBasic(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("基础数据选取文件出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
