package com.zjzmjr.finance.web.fileUpload.controller;

import java.text.MessageFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.file.ISharedFileFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.common.util.FileUploadUtil;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.file.SharedFileOptionEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.message.NotifyContextEnum;
import com.zjzmjr.core.model.file.SharedFile;
import com.zjzmjr.core.model.file.SharedFileInfo;
import com.zjzmjr.core.model.file.SharedFileQuery;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.finance.web.fileUpload.form.SharedFileForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 共享文件
 * 
 * @author Administrator
 * @version $Id: SharedFileController.java, v 0.1 2017-12-18 上午10:19:14
 *          Administrator Exp $
 */
@Controller
@RequestMapping("/sharedFile/user")
public class SharedFileController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(SharedFileController.class);

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
            Assert.isTrue(StringUtils.isNotBlank(form.getSaveAddress()[0].getOriginalFilename()), "请选择上传的文件");
            FileUploadUtil fileUploadUtil = FileUploadUtil.getInstance(FileUploadUtil.SavePathEnums.PATH_FINANCE);
            ResultEntry<Integer> result = null;
            SharedFile file = new SharedFile();
            fileUploadUtil.setIndex(0);
            file.setCompanyId(SpringSecurityUtil.getIntCompany());
            file.setSharedStaff(SpringSecurityUtil.getIntPrincipal());
            file.setFileName(form.getFileName());
            file.setFileExplain(form.getFileExplain());
            StringBuilder address = new StringBuilder();
            StringBuilder fileNames = new StringBuilder();
            for (int i = 0; i < form.getSaveAddress().length; i++) {
                if (address.length() > 0){
                    address.append(",");
                    fileNames.append(",");
                }
                fileNames.append(form.getSaveAddress()[i].getOriginalFilename());
                address.append(fileUploadUtil.uploadApk(form.getSaveAddress()[i]));
            }
            file.setSaveName(fileNames.toString().trim());
            file.setSaveAddress(address.toString().trim());
            file.setSourceType(1);
            file.setIsMessage(Util.isNotNull(form.getIsMessage())? form.getIsMessage() : SharedFileOptionEnum.NO.getValue());
            file.setIsSms(Util.isNotNull(form.getIsSms())? form.getIsSms() : SharedFileOptionEnum.NO.getValue());
            file.setIsTop(Util.isNotNull(form.getIsTop())? form.getIsTop() : SharedFileOptionEnum.NO.getValue());
            file.setCreateTime(new Date());
            file.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            file.setUpdateTime(file.getCreateTime());
            file.setUpdateUserId(file.getCreateUserId());
            file.setUpdateTime(file.getCreateTime());
            file.setUpdateUserId(file.getCreateUserId());
            result = sharedFileFacade.insertSharedFile(file);
            if (result.isSuccess()) {
                this.sendMessage(file); // 发送站内信或短信 // 发送站内信或短信
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
     * 置顶文件/取消置顶
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/fileTop.htm", method = RequestMethod.POST)
    public void fileTop(HttpServletResponse resp, SharedFileForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            SharedFile record = new SharedFile();
            Assert.isTrue(Util.isNotNull(form.getId()), "文件id为空!");
            Assert.isTrue(Util.isNotNull(form.getIsTop()), "是否置顶参数为空!");
            Assert.isTrue(Util.isNotNull(form.getVersion()), "版本号为空!");
            record.setId(form.getId());
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            record.setIsTop(form.getIsTop());
            record.setVersion(form.getVersion());
            ResultEntry<Integer> result = sharedFileFacade.updateById(record);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("上传共享文件出错：", ex);
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
