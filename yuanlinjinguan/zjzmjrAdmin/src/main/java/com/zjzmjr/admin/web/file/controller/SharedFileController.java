package com.zjzmjr.admin.web.file.controller;

import java.io.File;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.zjzmjr.admin.web.file.form.SharedFileForm;
import com.zjzmjr.core.api.file.ISharedFileFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.common.view.ZipUtils;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.file.SharedFileOptionEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.message.NotifyContextEnum;
import com.zjzmjr.core.enums.user.UserAuthParams;
import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/sharedFile/user")
public class SharedFileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SharedFileController.class);

    private static final String index = "/WEB-INF/pages/file/sharedfile.jsp";
    
    private final String resouce_path = "file.url";

    private final String resouce_url = "img.url";

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
        model.put("sharedFileManage", SpringSecurityUtil.hasAuth(UserAuthParams.SHARED_FILE_MANAGE));
        return index;
    }

    @Resource(name = "sharedFileFacade")
    private ISharedFileFacade sharedFileFacade;
    
    @Resource(name = "staffPersonFacade")
    private IStaffPersonFacade staffPersonFacade;

    /**
     * 获取共享文件一览
     * 
     * @param model
     * @param resp
     */
    @RequestMapping(value = "/getSharedFile.htm", method = RequestMethod.POST)
    public void getSharedFile(HttpServletResponse resp, SharedFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            SharedFileQuery query = new SharedFileQuery();
            query.setFileName(form.getFileName());
            query.setName(form.getName());
            if (Util.isNotNull(form.getStartDate())) {
                String startDateStr = DateUtil.format(DateUtil.parse(form.getStartDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(startDateStr), "时间格式错误");
                query.setStartDate(startDateStr);
            }
            if (Util.isNotNull(form.getEndDate())) {
                String endDateStr = DateUtil.format(DateUtil.parse(form.getEndDate(), DateUtil.YY_MM_DD), DateUtil.YYYYMMDD);
                Assert.isTrue(Util.isNotNull(endDateStr), "时间格式错误");
                query.setEndDate(endDateStr);
            }
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<SharedFileInfo> result = sharedFileFacade.getSharedFile(query);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("共享文件一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 上传共享文件
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/fileUpLoad.htm", method = RequestMethod.POST)
    public void fileUpLoad(HttpServletResponse resp, SharedFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(Util.isNotNull(form.getFileName()), "资料名称不能为空");
//            Assert.isTrue(Util.isNotNull(form.getFileExplain()), "资料说明不能为空");
            Assert.isTrue(StringUtils.isNotBlank(form.getFileAddress()[0].getOriginalFilename()), "请选择上传的文件");
            FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_ADMIN);
            ResultEntry<Integer> result = null;
            List<SharedFile> fileLst = new ArrayList<SharedFile>();
            SharedFile file = new SharedFile();
            fileUploadUtil.setIndex(0);
            file.setCompanyId(SpringSecurityUtil.getIntCompany());
            file.setSharedStaff(SpringSecurityUtil.getIntPrincipal());
            file.setFileName(form.getFileName());
            file.setFileExplain(form.getFileExplain());
            StringBuilder address = new StringBuilder();
            StringBuilder fileNames = new StringBuilder();
            for (int i = 0; i < form.getFileAddress().length; i++) {
                if (address.length() > 0){
                    address.append(",");
                    fileNames.append(",");
                }
                fileNames.append(form.getFileAddress()[i].getOriginalFilename());
                address.append(fileUploadUtil.uploadApk(form.getFileAddress()[i]));
            }
            file.setSaveName(fileNames.toString().trim());
            file.setSaveAddress(address.toString().trim());
            file.setSourceType(2);
            file.setIsMessage(Util.isNotNull(form.getIsMessage())? form.getIsMessage() : SharedFileOptionEnum.NO.getValue());
            file.setIsSms(Util.isNotNull(form.getIsSms())? form.getIsSms() : SharedFileOptionEnum.NO.getValue());
            file.setIsTop(Util.isNotNull(form.getIsTop())? form.getIsTop() : SharedFileOptionEnum.NO.getValue());
            file.setCreateTime(new Date());
            file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            file.setUpdateTime(file.getCreateTime());
            file.setUpdateUserId(file.getCreateUserId());
            file.setUpdateTime(file.getCreateTime());
            file.setUpdateUserId(file.getCreateUserId());
            fileLst.add(file);
            result = sharedFileFacade.insertSharedFile(file);
            if (result.isSuccess()) {
                this.sendMessage(file); // 发送站内信或短信
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("上传共享文件出错：", ex);
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
    //@Security(auth = { UserAuthParams.SHARED_FILE_MANAGE }, checkSource = true)
    @RequestMapping(value = "/downloadFiles.htm", method = {RequestMethod.POST, RequestMethod.GET})
    public void downloadFiles(HttpServletRequest request, HttpServletResponse response, @RequestParam(value = "saveAddress", required = true)
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
     * 文件删除
     * 
     * @param resp
     * @param req
     * @param form
     */
    //@Security(auth = { UserAuthParams.SHARED_FILE_MANAGE }, checkSource = true)
    @RequestMapping(value = "/deleteFileManageById.htm", method = RequestMethod.POST)
    public void deleteFileManageById(HttpServletResponse resp, HttpServletRequest req, FileManageForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
//            Assert.isTrue(SpringSecurityUtil.hasAuth("SHARED_FILE_MANAGE"), "没有权限,请联系管理员");
            SharedFile record = new SharedFile();
            record.setId(form.getId());
            record.setSaveAddress(PropertyUtils.getInstance().getProperty(resouce_url));
            record.setFileName(PropertyUtils.getInstance().getProperty(resouce_path));
            ResultEntry<Integer> result = sharedFileFacade.deleteSharedFile(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("文件删除出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 根据选项发送站内信或短信
     * 
     * @param fileLst
     */
    private void sendMessage(SharedFile file) {
        String userName = SpringSecurityUtil.getUserName();
        String fileName = file.getFileName();
        boolean messageLst = false;
        // 判断是否需要发送站内信
        if (SharedFileOptionEnum.YES.getValue().equals(file.getIsMessage())) {
            messageLst = true;
        }
        // 判断是否需要发送短信
        boolean smsLst = false;
        if (SharedFileOptionEnum.YES.getValue().equals(file.getIsSms())) {
            smsLst = true;
        }
        // 需要发送站内信或短信
        if (messageLst || smsLst) {
            StaffInfoQuery query = new StaffInfoQuery();
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            // 获取同一公司所有在职员工
            ResultPage<StaffBasicInfo> adminRest = staffPersonFacade.getStaffInfoByCondition(query);
            if (adminRest.isSuccess()) {
                if (messageLst){
                    // 给每个员工发送站内信
                    Message message = new Message();
                    message.setType(MessageTypeEnum.NOTICE.getValue());
                    message.setContext(MessageFormat.format(MessageContextEnum.uploadSharedFile.getMessage(), userName, fileName));
                    message.setTitle(MessageContextEnum.uploadSharedFile.getValue());
                    MessageHandlerUtil.insertMessage(message, adminRest.getList()); // 插入站内消息
                }
                if(smsLst){
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(userName);
                    pointSymbol.setFile(fileName);
                    pointSymbol.setAuditType(NotifyContextEnum.uploadSharedFile.getValue());
                    pointSymbol.setTemplateCode(NotifyContextEnum.uploadSharedFile.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, adminRest.getList());
                }
            }
        }
    }
}
