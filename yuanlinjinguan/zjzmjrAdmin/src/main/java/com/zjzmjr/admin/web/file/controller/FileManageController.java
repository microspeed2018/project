package com.zjzmjr.admin.web.file.controller;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.file.form.FileManageForm;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.project.IProjectFileInfoFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.base.video.ConverPhoneVideo;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.common.view.ZipUtils;
import com.zjzmjr.core.enums.user.DeviceEnum;
import com.zjzmjr.core.model.project.ProjectFile;
import com.zjzmjr.core.model.project.ProjectFileInfo;
import com.zjzmjr.core.model.project.ProjectFileQuery;
import com.zjzmjr.core.model.project.ProjectFileUpload;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 附件后台管理控制层
 * 
 * @author Administrator
 * @version $Id: FileManageController.java, v 0.1 2017-3-23 下午1:11:04
 *          Administrator Exp $
 */
@Controller
@RequestMapping("/fileManage")
public class FileManageController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(FileManageController.class);

    private final String resouce_path = "file.url";

    private final String resouce_url = "img.url";

    private static final String index = "/WEB-INF/pages/file/fileList.jsp";

    @Resource(name = "projectFileInfoFacade")
    private IProjectFileInfoFacade projectFileInfoFacade;

    /** 项目接口 */
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    /** 人员信息接口 */
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        return index;
    }

    /**
     * 获取上传文件类型
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/user/fileType.htm", method = RequestMethod.POST)
    public void getAllFileType(ModelMap model, HttpServletResponse resp,  HttpServletRequest req,
            @RequestParam(defaultValue = "1", value="handlerJob") Integer handlerJob) {
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setHandlerJob(handlerJob);
            String basicId = req.getParameter("basicId");
            if(Util.isNotNull(basicId)){
                query.setBasicId(Integer.parseInt(basicId));
            }
            //查询操作者
            StaffInfoQuery  staffInfoQuery = new StaffInfoQuery();
            staffInfoQuery.setUserId(SpringSecurityUtil.getIntPrincipal());
            staffInfoQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<StaffBasicInfo> staffRst = staffPersonFacade.getStaffInfoByCondition(staffInfoQuery);
            boolean isSpecialManage = true;
            if(staffRst.isSuccess()){
                String speialManageId = PropertyUtils.getPropertyFromFile("commBiz.properties", "special_manage_mobile");
                String[] speialManageIds = speialManageId.split(",");
                for(int i=0;i<speialManageIds.length;i++){
                    if(Util.isNotNull(staffRst.getList().get(0).getUserInfo()) && staffRst.getList().get(0).getUserInfo().getMobile().equals(speialManageIds[i])){
                      // 管理员的情况
                        isSpecialManage = false;
                    }
                } 
            }
            if("900000001".equals(SpringSecurityUtil.getPrincipal())){
                isSpecialManage = false;
            }
            if(isSpecialManage){
                query.setUserId(SpringSecurityUtil.getIntPrincipal());
            }
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ProjectFile> result = projectFileInfoFacade.getBasicFileTypeByCondition(query);
            if (result.isSuccess()) {
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("文件类型一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 附件列表
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(value = "/user/getFileManageList.htm", method = RequestMethod.POST)
    public void getFileManageList(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if(Util.isNotNull(form.getFileIds())) {
                ProjectFileQuery query = new ProjectFileQuery();
                query.setCompanyId(SpringSecurityUtil.getIntCompany());
                query.setBasicId(form.getBasicId());
                query.setPageBean(new PageBean(form.getRows(), form.getPage()));
                if (Util.isNotNull(form.getFileId())) {
                    query.setFileId(form.getFileId());
                }
                query.setFileIdLst(Arrays.asList(form.getFileIds().split(",")));
                if (Util.isNotNull(form.getProjectId())) {
                    query.setProjectId(form.getProjectId());
                }
                if (Util.isNotNull(form.getAdminId())) {
                    query.setAdminId(form.getAdminId());
                }
                if (Util.isNotNull(form.getFileName())) {
                    query.setFileName(form.getFileName());
                }
                if (Util.isNotNull(form.getUploadDateStart())) {
                    query.setUploadDateStart(form.getUploadDateStart());
                }
                if (Util.isNotNull(form.getUploadDateEnd())) {
                    query.setUploadDateEnd(form.getUploadDateEnd());
                }
                ResultPage<ProjectFileInfo> result = projectFileInfoFacade.getFileUploadByCondition(query);
                if (result.isSuccess()) {
                    model.put("rows", result.getList());
                    model.put("total", result.getPage().getTotalResults());
                    putSuccess(model, "");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                }
            } else {
                putError(model, "查询无记录");
            }
        } catch (Exception ex) {
            logger.error("附件一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 附件删除
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/deleteFileUploadById.htm", method = RequestMethod.POST)
    public void deleteFileManageById(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileUpload record = new ProjectFileUpload();
            record.setId(form.getId());
            record.setFileAddress(PropertyUtils.getInstance().getProperty(resouce_url));
            record.setFileName(PropertyUtils.getInstance().getProperty(resouce_path));
            ResultEntry<Integer> result = projectFileInfoFacade.deleteFileUploadHandler(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("附件删除出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 文件压缩下载
     * 
     * @param request
     * @param response
     * @throws Exception
     */
    @RequestMapping(value = "/user/downloadFiles.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "fileAddress", required = true)
    String address) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(StringUtils.isNotBlank(address), "请选择要下载的文件");
            
            String uploadFileUrl = PropertyUtils.getInstance().getProperty(resouce_path);
            // 访问路径
            String[] fileAddress = address.split(",");
            List<File> files = new ArrayList<File>();
            String fileUrl = "";
            for (int i = 0; i < fileAddress.length; i++) {
                fileUrl = fileAddress[i].replace("\\", "/");
                String actFile = fileUrl.split(".com/")[1];
                File e = new File(uploadFileUrl, actFile);
                files.add(e);
            }
            Date date = new Date();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssFFF");
            String filePath = uploadFileUrl + "down" + sdf.format(date) + ".zip";
            response = ZipUtils.downLoadFiles(files, request, response, filePath);
            putSuccess(model, "");
        } catch (Exception ex) {
            logger.error("文件压缩下载出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, response);
    }

    /**
     * 附件上传（支持批量）
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/fileUpLoad.htm", method = RequestMethod.POST)
    public void fileUpLoad(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            //Assert.isTrue(form.getProjectId() != null, "请选择项目");
            Assert.isTrue(form.getFileId() != null, "请选择文件类型");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileAddress()[0].getOriginalFilename()), "请选择上传的文件");

            FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN);
            ResultEntry<Integer> result = null;
            // 文件上传表
            List<ProjectFileUpload> fileLst = new ArrayList<ProjectFileUpload>();
            ProjectFileUpload file = null;
            for (int i = 0; i < form.getFileAddress().length; i++) {
                fileUploadUtil.setIndex(i);
                file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getProjectId());
                // 文件id
                file.setFileId(form.getFileId());
                file.setFileName(form.getFileAddress()[i].getOriginalFilename());
                file.setFileAddress(fileUploadUtil.uploadApk(form.getFileAddress()[i]));
                file.setSourceType(DeviceEnum.SYSTEM.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                fileLst.add(file);
            }
            result = projectFileInfoFacade.projectFileUpload(fileLst);
            if (result.isSuccess()) {
                model.put("imgUrl", file.getFileAddress());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 附件上传（支持批量）
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/fileUpLoad.htm", method = RequestMethod.GET)
    public void bigFileUpLoad(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(form.getProjectId() != null, "请选择项目");
            Assert.isTrue(form.getFileId() != null, "请选择文件类型");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileAccessUrl()), "请选择上传的文件");

            FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN);
            ResultEntry<Integer> result = null;
            // 文件上传表
            List<ProjectFileUpload> fileLst = new ArrayList<ProjectFileUpload>();
            ProjectFileUpload file = null;
            String[] fileAddress = form.getFileAccessUrl().split(",");
            String[] fileNames = form.getFileName().split(",");
            for (int i = 0; i < fileAddress.length; i++) {
                fileUploadUtil.setIndex(i);
                file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setProjectId(form.getProjectId());
                // 文件id
                file.setFileId(form.getFileId());
                file.setFileName(fileNames[i]);
                file.setFileAddress(fileAddress[i]);
                file.setSourceType(DeviceEnum.SYSTEM.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                fileLst.add(file);
            }
            result = projectFileInfoFacade.projectFileUpload(fileLst);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 获取视频宽高
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(value = "/user/getWidthAndHeight.htm", method = RequestMethod.POST)
    public void getWidthAndHeight(HttpServletResponse resp, HttpServletRequest req) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            String url = req.getParameter("url");
            url = url.replace(PropertyUtils.getInstance().getProperty(resouce_url), PropertyUtils.getInstance().getProperty(resouce_path));
            ConverPhoneVideo converPhoneVideo = new ConverPhoneVideo("", "");
            Map<String, Integer> map = converPhoneVideo.getVideoWidthAndHeight(url);
            model.put("success", true);
            model.put("width", map.get("width"));
            model.put("height", map.get("height"));
        } catch (Exception ex) {
            logger.error("获取视频宽高出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 执行命令，替换线上已经上传的文件
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/user/replaceFile.htm", method ={RequestMethod.GET, RequestMethod.POST})
    public void rechangeUploadFile(ModelMap model, HttpServletResponse resp){
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ProjectFileInfo> result = projectFileInfoFacade.getFileUploadByCondition(query);
            if (result.isSuccess()) {
                String accessUrl = PropertyUtils.getPropertyFromFile("auto-config", "img.url");
                logger.info("上传文件的访问地址: {}", accessUrl);
                String filePath = PropertyUtils.getPropertyFromFile("auto-config", "file.url");
                logger.info("上传文件的存储地址: {}", filePath);
                Map<String, String> fileMap = getRechangeFileMap();
                logger.info("需替换的文件的件数: {}", fileMap.size());
                logger.info("需替换的文件: {}", fileMap); 
                String fileMapKey = "";
                File mapFile = null;
                String fileAddress = "";
                File addressFile = null;
                for(ProjectFileInfo fileInfo : result.getList()) {
                    try {
                        fileAddress = fileInfo.getFileAddress().replace(accessUrl, filePath);
//                        logger.info("被替换的文件路径：{}", fileAddress);
                        addressFile = new File(fileAddress);
//                        if (!addressFile.exists()) {
//                            logger.error("线上的文件不存在 : {}", fileAddress);
//                        }
                        fileMapKey = fileMap.get(fileInfo.getFileName().trim());
//                        logger.info("替换的文件路径：{}", fileMapKey);
                        if(StringUtils.isNotBlank(fileMapKey)){
                            mapFile = new File(fileMapKey);
                            FileUtils.copyFile(mapFile, addressFile);
                        }else{
                            logger.error("需替换的文件不存在 : {}", fileInfo.getFileName());
                        }
                    } catch (IOException ie) {
                        logger.error("处理的文件失败：{}", fileMapKey);
                    }
                }
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("替换文件出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    private Map<String, String> getRechangeFileMap(){
        String savePath = "C:\\Users\\oms\\Desktop\\linux\\admin_陈宁\\新建文件夹\\遗漏文件";
        Map<String, String> fileMap = new HashMap<String, String>();
        markFile(savePath, fileMap);
        
        return fileMap;
    }
    
    private void markFile(String savePath, Map<String, String> fileMap){
        //获取系统编码  
//        String encoding = System.getProperty("file.encoding");
        String encoding = System.getProperty("sun.jnu.encoding");
        logger.info("linux系统的编码: {}", encoding);
        try {
            
            File file = new File(new String(savePath.getBytes(encoding), encoding));
            if(file.exists()){
                String tmpPath = "";
                if(file.isDirectory()){
                    File[] files = file.listFiles();
                    if (files == null || files.length == 0) {
                        return;
                    }
                    for(File tmpFile : files){
                        try {
                            tmpPath = new String(tmpFile.getPath().getBytes(encoding), encoding);
                            logger.info("替换文件的路径：{}", tmpPath);
                            markFile(tmpPath, fileMap);
                        } catch (UnsupportedEncodingException e) {
                            logger.error("", e);
                        }
                    }
                } else {
                    fileMap.put(new String(file.getName().getBytes(encoding), encoding), 
                            new String(file.getPath().getBytes(encoding), encoding));
                }
            }
        } catch (UnsupportedEncodingException e1) {
            logger.error("读取文件列表出错：", e1);
        }
    }
    
    
    /**
     * 员工证件上传（支持批量）
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/staffFileUpLoad.htm", method = RequestMethod.POST)
    public void staffFileUpLoad(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(form.getFileId() != null, "请选择文件类型");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileAddress()[0].getOriginalFilename()), "请选择上传的文件");

            FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN);
            // 文件上传表
            ProjectFileUpload file = null;
                file = new ProjectFileUpload();
                file.setCompanyId(SpringSecurityUtil.getIntCompany());
                file.setAdminId(form.getAdminId());
                // 文件id
                file.setFileId(form.getFileId());
                file.setFileName(form.getFileAddress()[0].getOriginalFilename());
                file.setFileAddress(fileUploadUtil.uploadApk(form.getFileAddress()[0]));
                file.setSourceType(DeviceEnum.SYSTEM.getValue());
                file.setCreateTime(new Date());
                file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                file.setUpdateTime(file.getCreateTime());
                file.setUpdateUserId(file.getCreateUserId());
                ResultEntry<Integer>  result = projectFileInfoFacade.staffFileUpLoad(file);
            if (result.isSuccess()) {
                model.put("imgUrl", file.getFileAddress());
                model.put("id",result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("附件上传出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取员工文件
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(value = "/user/getStaffFile.htm", method = RequestMethod.POST)
    public void getStaffFile(HttpServletResponse resp, HttpServletRequest req, FileManageForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ProjectFileQuery query = new ProjectFileQuery();
            query.setAdminId(form.getAdminId());
            ResultRecord<ProjectFileUpload> result = projectFileInfoFacade.getStaffFile(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg()); 
            }
        } catch (Exception ex) {
            logger.error("获取员工文件出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
