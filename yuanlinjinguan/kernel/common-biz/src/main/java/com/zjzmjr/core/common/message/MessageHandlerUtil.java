package com.zjzmjr.core.common.message;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.zjzmjr.core.api.message.IMessageFacade;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.api.user.IStaffPersonFacade;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.util.SmsUtil;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.message.GetuiMessage;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.security.web.util.SpringSecurityUtil;

/**
 * 发送系统消息工具类
 * 
 * @author oms
 * @version $Id: MessageHandlerUtil.java, v 0.1 2017-9-21 下午2:22:19 oms Exp $
 */
public class MessageHandlerUtil {

    private static final Logger logger = LoggerFactory.getLogger(MessageHandlerUtil.class);

    private static IMessageFacade messageFacade = SpringContextUtil.getBean("messageFacade");
    
    /** 人员信息接口 */
    private static IStaffPersonFacade staffPersonFacade = SpringContextUtil.getBean("staffPersonFacade");

    /**
     * 项目接口
     */
    private static IGardenProjectFacade gardenProjectFacade = SpringContextUtil.getBean("gardenProjectFacade");

    /**
     * 发送系统消息共同方法， 共同性的参数已经设置
     * 
     * @param message
     * @return
     */
    public static ResultEntry<Integer> insertMessage(Message message) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            message.setCompanyId(SpringSecurityUtil.getIntCompany());
            message.setCreateTime(new Date());
            message.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            message.setUpdateTime(message.getCreateTime());
            message.setUpdateUserId(message.getCreateUserId());
            message.setStatus(MessageStatusEnum.UNREAD.getValue());
            if (message.getType() == null) {
                message.setType(MessageTypeEnum.REMIND.getValue());
            }
            if (Util.isNull(message.getUserId())) {
                message.setUserId(SpringSecurityUtil.getIntPrincipal());
            }
            result = messageFacade.insertMessage(message);
            GetuiMessage getuiMessage = new GetuiMessage();
            getuiMessage.setCid(message.getClientId());
            getuiMessage.setTitle(message.getTitle());
            getuiMessage.setText(message.getContext());
            getuiMessage.setLogo("");
            getuiMessage.setLogoUrl("");
            getuiMessage.setUrl("");
            GetuiSendMessageUtil.pushMessageToSingle(getuiMessage);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ex.getMessage());
            logger.error("插入系统消息出错：", ex);
        }
        return result;
    }

    /**
     * 插入消息
     * 
     * @param message
     * @param staffInfoLst
     */
    public static void insertMessage(Message message, Integer userId) {
        List<StaffBasicInfo> staffInfoLst = new ArrayList<StaffBasicInfo>();
        StaffBasicInfo staffInfo = new StaffBasicInfo();
//        Admin admin = new Admin();
//        admin.setId(userId);
        staffInfo.setUserId(userId);
        staffInfoLst.add(staffInfo);
        insertMessage(message, staffInfoLst);
    }
    
    /**
     * 发送短信
     * 
     * @param pointSymbol
     * @param userId
     */
    public static void sendSms(PointSymbol pointSymbol, Integer userId) {
        List<StaffBasicInfo> staffInfoLst = getPersonByUserId(userId);
        if(staffInfoLst == null || staffInfoLst.isEmpty()){
        }else{
            sendSms(pointSymbol, staffInfoLst);
        }
    }

    /**
     * 插入消息
     * 
     * @param message
     * @param staffInfoLst
     */
    public static void insertMessage(Message message, List<StaffBasicInfo> staffInfoLst) {
        for (int index = 0; index < staffInfoLst.size(); index++) {
            message.setUserId(staffInfoLst.get(index).getUserId());
            if(Util.isNotNull(staffInfoLst.get(index).getUserInfo())){
                message.setClientId(staffInfoLst.get(index).getUserInfo().getClientId()); 
            }
            insertMessage(message);
        }
    }
    
    /**
     * 发送短信
     * 
     * @param pointSymbol
     * @param staffInfoLst
     */
    public static void sendSms(PointSymbol pointSymbol, List<StaffBasicInfo> staffInfoLst) {
        String specifyPhone = PropertyUtils.getPropertyFromFile("commBiz", "SPECIFY_PHONE");
        StringBuilder userIds = new StringBuilder();
        for (int index = 0; index < staffInfoLst.size(); index++) {
            userIds.append(staffInfoLst.get(index).getUserId()).append(",");
        }
        userIds.substring(0, userIds.length() - 1);
        pointSymbol.setReceiveUserId(userIds.toString().trim());
        if (StringUtils.isBlank(specifyPhone)) {
            StringBuilder builder = new StringBuilder();
            for (int index = 0; index < staffInfoLst.size(); index++) {
                builder.append(staffInfoLst.get(index).getUserInfo().getMobile()).append(",");
            }
            builder.substring(0, builder.length() - 1);
            pointSymbol.setMobile(builder.toString());
        } else {
            pointSymbol.setMobile(specifyPhone);
        }
        SmsUtil.sendSMS(pointSymbol);
    }
    
    /**
     * 审核类型的消息发送
     * 
     * @param query
     */
    public static void insertMessage(GardenProjectAuditQuery query){
        // 获取该项目的当时信息
        GardenProject srcProject = getGardenProjectInfo(query.getProjectId());
        insertStepMessage(query, srcProject);
        insertSubStepMessage(query, srcProject);
        insertSubStep2Message(query, srcProject);
    }
    
    /**
     * 根据step判断的消息
     * 
     * @param query
     * @param srcProject
     */
    private static void insertStepMessage(GardenProjectAuditQuery query, GardenProject srcProject){
        if(Util.isNull(query.getStep())){
            return;
        }
        // 消息对象
        Message message = new Message();
        PointSymbol pointSymbol = null;
        if(GardenProjectStepEnum.P_200.getValue().equals(query.getStep())){
            // 经营审核商务标
            message.setTitle(MessageContextEnum.Msg_320.getValue());
            message.setAddress(MessageContextEnum.Msg_320.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_320.getMessage(), 
                                srcProject.getName()));
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("商务标");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_320.getTemplateCode());
            sendSms(pointSymbol, getJinYingManagerPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_330.getValue());
                message.setAddress(MessageContextEnum.Msg_330.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_330.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定");
                pointSymbol.setJob("经营");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_330.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_340.getValue());
                message.setAddress(MessageContextEnum.Msg_340.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_340.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定经营审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_340.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_210.getValue().equals(query.getStep())){
            // 技术审核商务标
            message.setTitle(MessageContextEnum.Msg_350.getValue());
            message.setAddress(MessageContextEnum.Msg_350.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_350.getMessage(), 
                                srcProject.getName()));
            // 院办人员
            insertMessage(message, getYuanBanPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("商务标");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_350.getTemplateCode());
            sendSms(pointSymbol, getYuanBanPersons());

            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_370.getValue());
                message.setAddress(MessageContextEnum.Msg_370.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_370.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定");
                pointSymbol.setJob("负责人");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_370.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_380.getValue());
                message.setAddress(MessageContextEnum.Msg_380.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_380.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定技术审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_380.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_220.getValue().equals(query.getStep())){
            // 院办审核商务标
            message.setTitle(MessageContextEnum.Msg_390.getValue());
            message.setAddress(MessageContextEnum.Msg_390.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_390.getMessage(), 
                                srcProject.getName()));
            // 行政人员
            insertMessage(message, getXingZhengPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("商务标拟定");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_390.getTemplateCode());
            sendSms(pointSymbol, getXingZhengPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_400.getValue());
                message.setAddress(MessageContextEnum.Msg_400.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_400.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定");
                pointSymbol.setJob("院办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_400.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_410.getValue());
                message.setAddress(MessageContextEnum.Msg_410.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_410.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("商务标拟定院办");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_410.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_230.getValue().equals(query.getStep())){
            // 商务标行政盖章
            message.setTitle(MessageContextEnum.Msg_420.getValue());
            message.setAddress(MessageContextEnum.Msg_420.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_420.getMessage(), 
                                srcProject.getName()));
            insertMessage(message, srcProject.getManagementPersonnel());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("商务标");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_420.getTemplateCode());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_310.getValue().equals(query.getStep())){
            // 经营审核合同
            message.setTitle(MessageContextEnum.Msg_540.getValue());
            message.setAddress(MessageContextEnum.Msg_540.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_540.getMessage(), 
                                srcProject.getName()));
            // 总工人员
            insertMessage(message, getZongGongPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("合同");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_540.getTemplateCode());
            sendSms(pointSymbol, getZongGongPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_550.getValue());
                message.setAddress(MessageContextEnum.Msg_550.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_550.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定");
                pointSymbol.setJob("经营");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_550.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_560.getValue());
                message.setAddress(MessageContextEnum.Msg_560.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_560.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定经营审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_560.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_315.getValue().equals(query.getStep())){
            // 总工审核合同
            message.setTitle(MessageContextEnum.Msg_570.getValue());
            message.setAddress(MessageContextEnum.Msg_570.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_570.getMessage(), 
                                srcProject.getName()));
            // 技术负责人员
            insertMessage(message, srcProject.getProjectLeader());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("合同");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_570.getTemplateCode());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_580.getValue());
                message.setAddress(MessageContextEnum.Msg_580.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_580.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定");
                pointSymbol.setJob("总师办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_580.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_590.getValue());
                message.setAddress(MessageContextEnum.Msg_590.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_590.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定经营审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_590.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_320.getValue().equals(query.getStep())){
            // 技术审核合同
            message.setTitle(MessageContextEnum.Msg_600.getValue());
            message.setAddress(MessageContextEnum.Msg_600.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_600.getMessage(), 
                                srcProject.getName()));
            // 会计人员
            insertMessage(message, getKuaijiPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("合同");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_600.getTemplateCode());
            sendSms(pointSymbol, getKuaijiPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_610.getValue());
                message.setAddress(MessageContextEnum.Msg_610.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_610.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同");
                pointSymbol.setJob("负责人");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_610.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_620.getValue());
                message.setAddress(MessageContextEnum.Msg_620.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_620.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定技术审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_620.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_330.getValue().equals(query.getStep())){
            // 财务审核合同
            message.setTitle(MessageContextEnum.Msg_630.getValue());
            message.setAddress(MessageContextEnum.Msg_630.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_630.getMessage(), 
                                srcProject.getName()));
            insertMessage(message, getFaWuPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("合同拟定");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_630.getTemplateCode());
            sendSms(pointSymbol, getFaWuPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_640.getValue());
                message.setAddress(MessageContextEnum.Msg_640.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_640.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定");
                pointSymbol.setJob("财务");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_640.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_650.getValue());
                message.setAddress(MessageContextEnum.Msg_650.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_650.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定财务审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_650.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_340.getValue().equals(query.getStep())){
            // 法务审核合同
            message.setTitle(MessageContextEnum.Msg_660.getValue());
            message.setAddress(MessageContextEnum.Msg_660.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_660.getMessage(), 
                                srcProject.getName()));
            insertMessage(message, getYuanBanPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("合同拟定");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_660.getTemplateCode());
            sendSms(pointSymbol, getYuanBanPersons());
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_670.getValue());
                message.setAddress(MessageContextEnum.Msg_670.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_670.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定");
                pointSymbol.setJob("法务");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_670.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_680.getValue());
                message.setAddress(MessageContextEnum.Msg_680.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_680.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定法务审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_680.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_350.getValue().equals(query.getStep())){
            // 院办审核合同
            message.setTitle(MessageContextEnum.Msg_690.getValue());
            message.setAddress(MessageContextEnum.Msg_690.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_690.getMessage(), 
                                srcProject.getName()));
            // 行政专员
            insertMessage(message, getXingZhengPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("合同拟定");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_690.getTemplateCode());
            sendSms(pointSymbol, getXingZhengPersons());
            
            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_700.getValue());
                message.setAddress(MessageContextEnum.Msg_700.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_700.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同");
                pointSymbol.setJob("院办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_700.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_710.getValue());
                message.setAddress(MessageContextEnum.Msg_710.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_710.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合同拟定院办审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_710.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_360.getValue().equals(query.getStep())){
            // 合同行政盖章
            message.setTitle(MessageContextEnum.Msg_720.getValue());
            message.setAddress(MessageContextEnum.Msg_720.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_720.getMessage(), 
                                srcProject.getName()));
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("合同");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_720.getTemplateCode());
            // 经营专员
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_390.getValue().equals(query.getStep())){
            // 院办审核分包
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_750.getValue());
                message.setAddress(MessageContextEnum.Msg_750.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_750.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合作比例拟定");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_750.getTemplateCode());
            }else{
                // 审核不通过 --- 技术负责人
                message.setTitle(MessageContextEnum.Msg_760.getValue());
                message.setAddress(MessageContextEnum.Msg_760.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_760.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("合作比例拟定院办审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_760.getTemplateCode());
            }
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_410.getValue().equals(query.getStep())){
            // 总工审核方案
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_780.getValue());
                message.setAddress(MessageContextEnum.Msg_780.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_780.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("方案初稿审核");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_780.getTemplateCode());
            }else{
                // 审核不通过 --- 技术负责人
                message.setTitle(MessageContextEnum.Msg_790.getValue());
                message.setAddress(MessageContextEnum.Msg_790.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_790.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("方案初稿审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_790.getTemplateCode());
            }
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_440.getValue().equals(query.getStep())){
            // 总工审核扩初
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_820.getValue());
                message.setAddress(MessageContextEnum.Msg_820.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_820.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("扩初初稿审核");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_820.getTemplateCode());
            }else{
                // 审核不通过 --- 技术负责人
                message.setTitle(MessageContextEnum.Msg_830.getValue());
                message.setAddress(MessageContextEnum.Msg_830.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_830.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("扩初初稿审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_830.getTemplateCode());
            }
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_470.getValue().equals(query.getStep())){
            // 总工审核施工
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("施工初稿");
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_860.getValue());
                message.setAddress(MessageContextEnum.Msg_860.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_860.getMessage(), 
                                    srcProject.getName()));
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_860.getTemplateCode());                
            }else{
                // 审核不通过 --- 技术负责人
                message.setTitle(MessageContextEnum.Msg_870.getValue());
                message.setAddress(MessageContextEnum.Msg_870.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_870.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_870.getTemplateCode());
            }
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_500.getValue().equals(query.getStep())){
            // 总工审核规划
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("规划初稿");
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_890.getValue());
                message.setAddress(MessageContextEnum.Msg_890.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_890.getMessage(), 
                                    srcProject.getName()));
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_890.getTemplateCode()); 
            }else{
                // 审核不通过 --- 技术负责人
                message.setTitle(MessageContextEnum.Msg_900.getValue());
                message.setAddress(MessageContextEnum.Msg_900.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_900.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_900.getTemplateCode()); 
            }
            // 技术负责人
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }
    }

    /**
     * 根据substep判断的消息
     * 
     * @param query
     * @param srcProject
     */
    private static void insertSubStepMessage(GardenProjectAuditQuery query, GardenProject srcProject){
        if(Util.isNull(query.getSubStep())){
            return;
        }
        // 消息对象
        Message message = new Message();
        // 保证金的情况
        if(GardenProjectStepEnum.P_150.getValue().equals(query.getSubStep())){
            message.setTitle(MessageContextEnum.Msg_230.getValue());
            message.setAddress(MessageContextEnum.Msg_230.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_230.getMessage(), 
                                srcProject.getName()));
            // 院办人员
            insertMessage(message, getYuanBanPersons());
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("保证金");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_230.getTemplateCode());
            sendSms(pointSymbol, getYuanBanPersons());

            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_240.getValue());
                message.setAddress(MessageContextEnum.Msg_240.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_240.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("保证金申请");
                pointSymbol.setJob("财务");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_240.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_250.getValue());
                message.setAddress(MessageContextEnum.Msg_250.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_250.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("保证金申请财务审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_250.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_160.getValue().equals(query.getSubStep())){
            // 院办审核保证金
            message.setTitle(MessageContextEnum.Msg_260.getValue());
            message.setAddress(MessageContextEnum.Msg_260.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_260.getMessage(), 
                                srcProject.getName()));
            // 出纳人员
            insertMessage(message, getChuNaPersons());
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("保证金申请审核已通过，请打款");
            pointSymbol.setJob("保证金申请");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_260.getTemplateCode());
            sendSms(pointSymbol, getChuNaPersons());

            // 经营专员
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_270.getValue());
                message.setAddress(MessageContextEnum.Msg_270.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_270.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("保证金申请");
                pointSymbol.setJob("院办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_270.getTemplateCode());
            }else{
                // 审核不通过 --- 经营专员
                message.setTitle(MessageContextEnum.Msg_280.getValue());
                message.setAddress(MessageContextEnum.Msg_280.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_280.getMessage(), 
                            srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("保证金申请院办审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_280.getTemplateCode());
            }
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }else if(GardenProjectStepEnum.P_170.getValue().equals(query.getSubStep())){
            // 出纳保证金打款
            message.setTitle(MessageContextEnum.Msg_290.getValue());
            message.setAddress(MessageContextEnum.Msg_290.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_290.getMessage(), 
                                srcProject.getName(), query.getAmount()));
            // 经营专员
            insertMessage(message, srcProject.getManagementPersonnel());
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setConfirmType("保证金打款");
            pointSymbol.setMoney(query.getAmount().toString()+"万元");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_290.getTemplateCode());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
            message.setTitle(MessageContextEnum.Msg_300.getValue());
            message.setAddress(MessageContextEnum.Msg_300.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_300.getMessage(), 
                                srcProject.getName(), query.getAmount()));
            // 行政专员
            insertMessage(message, getXingZhengPersons());
            pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setConfirmType("保证金打款");
            pointSymbol.setMoney(query.getAmount().toString()+"万元");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_300.getTemplateCode());
            sendSms(pointSymbol, getXingZhengPersons());
        }
    }
    
    /**
     * 根据substep2判断的消息
     * 
     * @param query
     * @param srcProject
     */
    private static void insertSubStep2Message(GardenProjectAuditQuery query, GardenProject srcProject){
        if(Util.isNull(query.getSubStep2())){
            return;
        }
        // 消息对象
        Message message = new Message();
        // 上传技术标的情况
        if(GardenProjectStepEnum.P_250.getValue().equals(query.getSubStep2())){
            // 总工审核技术标
            message.setTitle(MessageContextEnum.Msg_440.getValue());
            message.setAddress(MessageContextEnum.Msg_440.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_440.getMessage(), 
                                srcProject.getName()));
            // 院办人员
            insertMessage(message, getYuanBanPersons());
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setAuditType("技术标");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_440.getTemplateCode());
            sendSms(pointSymbol, getYuanBanPersons());
            // 负责人
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_460.getValue());
                message.setAddress(MessageContextEnum.Msg_460.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_460.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("技术标拟定");
                pointSymbol.setJob("总师办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_460.getTemplateCode());
            }else{
                message.setTitle(MessageContextEnum.Msg_470.getValue());
                message.setAddress(MessageContextEnum.Msg_470.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_470.getMessage(), 
                                    srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("技术标拟定总工审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_470.getTemplateCode());
            }
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_260.getValue().equals(query.getSubStep2())){
            // 院办审核技术标
            message.setTitle(MessageContextEnum.Msg_480.getValue());
            message.setAddress(MessageContextEnum.Msg_480.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_480.getMessage(), 
                                srcProject.getName()));
            // 行政专员
            insertMessage(message, getXingZhengPersons());
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("技术标拟定");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_480.getTemplateCode());
            sendSms(pointSymbol, getXingZhengPersons());
            // 负责人
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                message.setTitle(MessageContextEnum.Msg_490.getValue());
                message.setAddress(MessageContextEnum.Msg_490.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_490.getMessage(), 
                                    srcProject.getName()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("技术标拟定");
                pointSymbol.setJob("院办");
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_490.getTemplateCode());
            }else{
                message.setTitle(MessageContextEnum.Msg_500.getValue());
                message.setAddress(MessageContextEnum.Msg_500.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_500.getMessage(), 
                                    srcProject.getName(), query.getMemo()));
                pointSymbol = new PointSymbol();
                pointSymbol.setName(srcProject.getName());
                pointSymbol.setAuditType("技术标拟定院办审核");
                pointSymbol.setReason(query.getMemo());
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_500.getTemplateCode());
            }
            insertMessage(message, srcProject.getProjectLeader());
            sendSms(pointSymbol, srcProject.getProjectLeader());
        }else if(GardenProjectStepEnum.P_270.getValue().equals(query.getSubStep2())){
            message.setTitle(MessageContextEnum.Msg_510.getValue());
            message.setAddress(MessageContextEnum.Msg_510.getAddress());
            message.setContext(MessageFormat.format(MessageContextEnum.Msg_510.getMessage(), 
                                srcProject.getName()));
            PointSymbol pointSymbol = new PointSymbol();
            pointSymbol.setName(srcProject.getName());
            pointSymbol.setSealType("技术标");
            pointSymbol.setTemplateCode(MessageContextEnum.Msg_510.getTemplateCode());
            // 经营专员
            insertMessage(message, srcProject.getManagementPersonnel());
            sendSms(pointSymbol, srcProject.getManagementPersonnel());
        }
    }
    
    /**
     * 获取院办人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getYuanBanPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(2);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取经营部经理人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getJinYingManagerPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(3);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取经营部专员人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getJinYingPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(4);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取会计专员人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getKuaijiPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(7);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取出纳专员人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getChuNaPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(8);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }
    
    /**
     * 获取行政专员人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getXingZhengPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(9);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取总工人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getZongGongPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(11);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }

    /**
     * 获取法务人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getFaWuPersons(){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setJobId(10);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }
    
    /**
     * 根据用户的编号查询该用户
     * 
     * @param userId
     * @return
     */
    public static List<StaffBasicInfo> getPersonByUserId(Integer userId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        query.setUserId(userId);
//        query.setJobStatus(1);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffPersonFacade.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            ExternalPersonQuery person = new ExternalPersonQuery();
            person.setCompanyId(SpringSecurityUtil.getIntCompany());
            person.setUserId(userId);
            person.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ExternalPersonInfo> personRst = staffPersonFacade.getExternalPersonByCondition(person);
            if(personRst.isSuccess()){
                staffLst = new ArrayList<StaffBasicInfo>();
                StaffBasicInfo basic = null;
                for(ExternalPersonInfo info : personRst.getList()){
                    basic = new StaffBasicInfo();
                    basic.setUserInfo(info.getUserInfo());
                    staffLst.add(basic);
                }
            }else{
                staffLst = Collections.emptyList();
            }
        }
        
        return staffLst;
    }
    
    /**
     * 根据项目编号获取项目信息
     * 
     * @param projectId
     * @return
     */
    public static GardenProject getGardenProjectInfo(Integer projectId){
        GardenProject project = new GardenProject();
        GardenProjectQuery query = new GardenProjectQuery();
        query.setId(projectId);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultEntry<GardenProject> result = gardenProjectFacade.getGardenProjectByIdAndStatus(query);
        if(result.isSuccess()){
            project = result.getT();
        }
        
        return project;
    }
    
}

