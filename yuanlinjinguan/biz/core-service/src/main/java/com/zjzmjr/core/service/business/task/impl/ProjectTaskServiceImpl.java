package com.zjzmjr.core.service.business.task.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.project.ProjectSchemaStatusEnum;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;
import com.zjzmjr.core.model.project.ProjectSchemaUserInfo;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.message.MessageService;
import com.zjzmjr.core.service.business.project.GardenProjectSchemaService;
import com.zjzmjr.core.service.business.task.ProjectTaskService;
import com.zjzmjr.core.service.business.user.StaffInfoService;

/**
 * 项目中所有任务的服务业务处理
 * 
 * @author oms
 * @version $Id: ProjectTaskServiceImpl.java, v 0.1 2017-9-13 下午7:15:51 oms Exp $
 */
@Service("projectTaskService")
public class ProjectTaskServiceImpl implements ProjectTaskService {

    private static final Logger logger = LoggerFactory.getLogger(ProjectTaskServiceImpl.class);
    
    @Resource(name = "gardenProjectSchemaService")
    private GardenProjectSchemaService gardenProjectSchemaService;
    
    @Resource(name = "messageService")
    private MessageService messageService;

    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.task.ProjectTaskService#applyDeadlineAlertTask()
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> applyDeadlineAlertTask(GardenProjectInfo project, MessageContextEnum messageEnum, String offset) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        // 获取当前项目的方案
        ProjectSchemaQuery schemaQuery = new ProjectSchemaQuery();
        schemaQuery.setCompanyId(project.getCompanyId());
        schemaQuery.setProjectId(project.getId());
        schemaQuery.setAccepted(ProjectSchemaStatusEnum.WAITING_APPLY.getValue());
        schemaQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectSchemaInfo> schemaRst = gardenProjectSchemaService.getGardenProjectSchemaByCondition(schemaQuery);
        if (schemaRst.isSuccess()) {
            Message message = null;
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(new Date());
            PointSymbol pointSymbol = new PointSymbol();
            for (GardenProjectSchemaInfo schemaInfo : schemaRst.getList()) {
                for (ProjectSchemaUserInfo schemaUser : schemaInfo.getSchema()) {
                    message = new Message();
                    message.setCompanyId(project.getCompanyId());
                    message.setUserId(schemaUser.getSchemeProposer());
                    message.setTitle(messageEnum.getValue());
                    message.setType(MessageTypeEnum.REMIND.getValue());
                    message.setContext(MessageFormat.format(messageEnum.getMessage(), schemaInfo.getName(), offset));
                    message.setAddress(messageEnum.getAddress());
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCreateTime(new Date());
                    message.setCreateUserId(schemaUser.getSchemeProposer());
                    message.setUpdateTime(message.getCreateTime());
                    message.setUpdateUserId(message.getUpdateUserId());
                    ResultEntry<Integer> msgRst = messageService.insertMessage(message);                    
                    if(!msgRst.isSuccess()){
                        throw new ApplicationException("申请截止日消息提醒错误");
                    }
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(schemaInfo.getName());
                    pointSymbol.setDate(offset+"报名");
                    pointSymbol.setTemplateCode(MessageContextEnum.applyDeadline.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, schemaUser.getSchemeProposer());
                }
            }
            
            // 给该项目的经营专员发送消息
            message = new Message();
            message.setCompanyId(project.getCompanyId());
            message.setUserId(project.getManagementPersonnel());
            message.setTitle(messageEnum.getValue());
            message.setType(MessageTypeEnum.REMIND.getValue());
            message.setContext(MessageFormat.format(messageEnum.getMessage(), project.getName(), offset));
            message.setAddress(messageEnum.getAddress());
            message.setStatus(MessageStatusEnum.UNREAD.getValue());
            message.setCreateTime(new Date());
            message.setCreateUserId(project.getManagementPersonnel());
            message.setUpdateTime(message.getCreateTime());
            message.setUpdateUserId(message.getUpdateUserId());
            ResultEntry<Integer> msgRst = messageService.insertMessage(message);            
            if(!msgRst.isSuccess()){
                throw new ApplicationException("申请截止日消息提醒错误");
            }
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"报名");
            pointSymbol.setTemplateCode(MessageContextEnum.applyDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getManagementPersonnel());
            
            // 获取经营部门的负责人, 然后发送消息
            StaffInfoQuery query = new StaffInfoQuery();
            query.setCompanyId(project.getCompanyId());
            query.setJobId(3);
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(query);
            if(staffRst.isSuccess()){
                for(StaffBasicInfo staff : staffRst.getList()){
                    message = new Message();
                    message.setCompanyId(project.getCompanyId());
                    message.setUserId(staff.getUserId());
                    message.setTitle(messageEnum.getValue());
                    message.setType(MessageTypeEnum.REMIND.getValue());
                    message.setContext(MessageFormat.format(messageEnum.getMessage(), project.getName(), offset));
                    message.setAddress(messageEnum.getAddress());
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCreateTime(new Date());
                    message.setCreateUserId(staff.getUserInfo().getId());
                    message.setUpdateTime(message.getCreateTime());
                    message.setUpdateUserId(message.getUpdateUserId());
                    msgRst = messageService.insertMessage(message);
                    if(!msgRst.isSuccess()){
                        throw new ApplicationException("申请截止日消息提醒错误");
                    }
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(project.getName());
                    pointSymbol.setDate(offset+"报名");
                    pointSymbol.setTemplateCode(MessageContextEnum.applyDeadline.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, staff.getUserId());
                }
            }
        }
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.task.ProjectTaskService#projectDeadlineAlertTask(com.zjzmjr.core.model.project.GardenProjectInfo, com.zjzmjr.core.enums.message.MessageContextEnum, int)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> projectDeadlineAlertTask(GardenProjectInfo project, MessageContextEnum messageEnum, String offset) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(project)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        PointSymbol pointSymbol = new PointSymbol();
        Message message = null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        // 给项目的负责人发送消息
        message = new Message();
        message.setCompanyId(project.getCompanyId());
        message.setUserId(project.getProjectLeader());
        message.setTitle(messageEnum.getValue());
        message.setType(MessageTypeEnum.REMIND.getValue());
        message.setContext(MessageFormat.format(messageEnum.getMessage(), project.getName(), offset));
        message.setAddress(messageEnum.getAddress());
        message.setStatus(MessageStatusEnum.UNREAD.getValue());
        message.setCreateTime(new Date());
        message.setCreateUserId(project.getProjectLeader());
        message.setUpdateTime(message.getCreateTime());
        message.setUpdateUserId(message.getUpdateUserId());
        ResultEntry<Integer> msgRst = messageService.insertMessage(message);
        if (!msgRst.isSuccess()) {
            throw new ApplicationException("申请截止日消息提醒错误");
        }
        if(MessageContextEnum.bidDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"招标报名");
            pointSymbol.setTemplateCode(MessageContextEnum.bidDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getProjectLeader()); 
        }else if(MessageContextEnum.marginDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"保证金");
            pointSymbol.setTemplateCode(MessageContextEnum.marginDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getProjectLeader()); 
        }else if(MessageContextEnum.tenderDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"投标");
            pointSymbol.setTemplateCode(MessageContextEnum.tenderDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getProjectLeader()); 
        }
        

        // 给该项目的经营专员发送消息
        message = new Message();
        message.setCompanyId(project.getCompanyId());
        message.setUserId(project.getManagementPersonnel());
        message.setTitle(messageEnum.getValue());
        message.setType(MessageTypeEnum.REMIND.getValue());
        message.setContext(MessageFormat.format(messageEnum.getMessage(), project.getName(), offset));
        message.setAddress(messageEnum.getAddress());
        message.setStatus(MessageStatusEnum.UNREAD.getValue());
        message.setCreateTime(new Date());
        message.setCreateUserId(project.getManagementPersonnel());
        message.setUpdateTime(message.getCreateTime());
        message.setUpdateUserId(message.getUpdateUserId());
        msgRst = messageService.insertMessage(message);
        if (!msgRst.isSuccess()) {
            throw new ApplicationException("申请截止日消息提醒错误");
        }
        if(MessageContextEnum.bidDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"招标报名");
            pointSymbol.setTemplateCode(MessageContextEnum.bidDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getManagementPersonnel()); 
        }else if(MessageContextEnum.marginDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"保证金");
            pointSymbol.setTemplateCode(MessageContextEnum.marginDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getManagementPersonnel()); 
        }else if(MessageContextEnum.tenderDeadline.getValue().equals(messageEnum.getValue())){
            pointSymbol = new PointSymbol();
            pointSymbol.setName(project.getName());
            pointSymbol.setDate(offset+"投标");
            pointSymbol.setTemplateCode(MessageContextEnum.tenderDeadline.getTemplateCode());
            MessageHandlerUtil.sendSms(pointSymbol, project.getManagementPersonnel()); 
        }
        
        // 获取经营部门的负责人, 然后发送消息
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(project.getCompanyId());
        query.setJobId(3);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(query);
        if (staffRst.isSuccess()) {
            for (StaffBasicInfo staff : staffRst.getList()) {
                message = new Message();
                message.setCompanyId(project.getCompanyId());
                message.setUserId(staff.getUserInfo().getId());
                message.setTitle(messageEnum.getValue());
                message.setType(MessageTypeEnum.REMIND.getValue());
                message.setContext(MessageFormat.format(messageEnum.getMessage(), project.getName(), offset));
                message.setAddress(messageEnum.getAddress());
                message.setStatus(MessageStatusEnum.UNREAD.getValue());
                message.setCreateTime(new Date());
                message.setCreateUserId(staff.getUserInfo().getId());
                message.setUpdateTime(message.getCreateTime());
                message.setUpdateUserId(message.getUpdateUserId());
                msgRst = messageService.insertMessage(message);
                if (!msgRst.isSuccess()) {
                    throw new ApplicationException("申请截止日消息提醒错误");
                }
                if(MessageContextEnum.bidDeadline.getValue().equals(messageEnum.getValue())){
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(project.getName());
                    pointSymbol.setDate(offset+"招标报名");
                    pointSymbol.setTemplateCode(MessageContextEnum.bidDeadline.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, staff.getUserInfo().getId()); 
                }else if(MessageContextEnum.marginDeadline.getValue().equals(messageEnum.getValue())){
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(project.getName());
                    pointSymbol.setDate(offset+"保证金");
                    pointSymbol.setTemplateCode(MessageContextEnum.marginDeadline.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, staff.getUserInfo().getId()); 
                }else if(MessageContextEnum.tenderDeadline.getValue().equals(messageEnum.getValue())){
                    pointSymbol = new PointSymbol();
                    pointSymbol.setName(project.getName());
                    pointSymbol.setDate(offset+"投标");
                    pointSymbol.setTemplateCode(MessageContextEnum.tenderDeadline.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, staff.getUserInfo().getId()); 
                }
            }
        }
        result.setSuccess(true);
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.task.ProjectTaskService#contractExpireAlertTask()
     */
    @Override
    public ResultEntry<Integer> contractExpireAlertTask(MessageContextEnum messageEnum, Integer offset) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        // 获取经营部门的负责人, 然后发送消息
        StaffInfoQuery query = new StaffInfoQuery();
        // 合同到期提前提醒时间（单位：月）
        query.setContractDueInform(offset);
        // 根据不同的短信类型设置员工状态
        if (MessageContextEnum.contractExpire.equals(messageEnum)) {
            query.setJobStatus(AdminJobStatusEnum.WORKING.getValue()); // 在职
        } else if (MessageContextEnum.practiceExpire.equals(messageEnum)) {
            query.setJobStatus(AdminJobStatusEnum.PRACTICE.getValue()); // 实习
        } else if (MessageContextEnum.probationExpire.equals(messageEnum)) {
            query.setJobStatus(AdminJobStatusEnum.PREBATIONARY.getValue()); // 试用
        }
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        // 获取合同一个月后结束的员工
        ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(query);
        if (staffRst.isSuccess()) {
            // 将合同即将到期的员工姓名拼接按公司分类封装到map中
            Map<Integer, StringBuffer> map = convertToMap(staffRst.getList());
            // 获取所有公司的办公室主任
            query = new StaffInfoQuery();
            query.setJobId(13);
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            staffRst = staffInfoService.getStaffInfoByCondition(query);
            if (staffRst.isSuccess()) {
                // 当前日期，用于短信模版中
                String date = DateUtil.format(new Date(), DateUtil.CN_YYYYMMDD);
                // 员工姓名拼接的字符串
                String name = "";
                Message message = null;
                for (StaffBasicInfo staff : staffRst.getList()) {
                    if (Util.isNull(staff.getUserInfo()) || !map.containsKey(staff.getUserInfo().getCompanyId())) {
                        continue;
                    }
                    name = map.get(staff.getUserInfo().getCompanyId()).toString();
                    message = new Message();
                    message.setCompanyId(staff.getUserInfo().getCompanyId());
                    message.setUserId(staff.getUserId());
                    message.setTitle(messageEnum.getValue());
                    message.setType(MessageTypeEnum.REMIND.getValue());
                    message.setContext(MessageFormat.format(messageEnum.getMessage(), name, date));
                    message.setAddress(messageEnum.getAddress());
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCreateTime(new Date());
                    message.setCreateUserId(staff.getUserInfo().getId());
                    message.setUpdateTime(message.getCreateTime());
                    message.setUpdateUserId(message.getUpdateUserId());
                    ResultEntry<Integer> msgRst = messageService.insertMessage(message); // 发送站内信
                    if (!msgRst.isSuccess()) {
                        throw new ApplicationException(messageEnum.getValue() + "消息提醒错误");
                    }
                    if (Util.isNull(staff.getUserInfo().getMobile())) {
                        continue;
                    }
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(name);
                    pointSymbol.setDate(date);
                    pointSymbol.setTemplateCode(messageEnum.getTemplateCode());
                    List<StaffBasicInfo> list = new ArrayList<StaffBasicInfo>();
                    list.add(staff);
                    MessageHandlerUtil.sendSms(pointSymbol, list); // 发送短信
                }
            }
        }
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 将员工姓名拼接按公司分类封装到map中
     * 
     * @param list
     * @return
     */
    private Map<Integer, StringBuffer> convertToMap (List<StaffBasicInfo> list) {
        Map<Integer, StringBuffer> map = new HashMap<Integer, StringBuffer>();
        for (StaffBasicInfo staff : list) {
            if (map.containsKey(staff.getUserInfo().getCompanyId())) {
                StringBuffer sb = map.get(staff.getUserInfo().getCompanyId());
                sb.append(",").append(staff.getUserInfo().getName());
                map.put(staff.getUserInfo().getCompanyId(), sb);
            } else {
                StringBuffer sb = new StringBuffer();
                sb.append(staff.getUserInfo().getName());
                map.put(staff.getUserInfo().getCompanyId(), sb);
            }
        }
        return map;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.task.ProjectTaskService#birthdayAlertTask()
     */
    @Override
    public ResultEntry<Integer> birthdayAlertTask() throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        // 获取经营部门的负责人, 然后发送消息
        StaffInfoQuery query = new StaffInfoQuery();
        // 生日提前提醒时间（单位：月）
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MONTH, 1);
        Integer month = cal.get(Calendar.MONTH) + 1;
        query.setBirthdayInform(month);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        // 获取下个月生日的员工
        ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(query);
        if(staffRst.isSuccess()){
         // 将下个月生日的员工姓名拼接按公司分类封装到map中
            Map<Integer, StringBuffer> map = convertToMap(staffRst.getList());
            query = new StaffInfoQuery();
            query.setJobId(13);
            query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            staffRst = staffInfoService.getStaffInfoByCondition(query);
            if (staffRst.isSuccess()) {
                String name = "";
                MessageContextEnum messageEnum = MessageContextEnum.birthdayInform;
                Message message = null;
                for (StaffBasicInfo staff : staffRst.getList()) {
                    if (Util.isNull(staff.getUserInfo()) || !map.containsKey(staff.getUserInfo().getCompanyId())) {
                        continue;
                    }
                    name = map.get(staff.getUserInfo().getCompanyId()).toString();
                    message = new Message();
                    message.setCompanyId(staff.getUserInfo().getCompanyId());
                    message.setUserId(staff.getUserId());
                    message.setTitle(messageEnum.getValue());
                    message.setType(MessageTypeEnum.REMIND.getValue());
                    message.setContext(MessageFormat.format(messageEnum.getMessage(), month, name));
                    message.setAddress(messageEnum.getAddress());
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCreateTime(new Date());
                    message.setCreateUserId(staff.getUserInfo().getId());
                    message.setUpdateTime(message.getCreateTime());
                    message.setUpdateUserId(message.getUpdateUserId());
                    ResultEntry<Integer> msgRst = messageService.insertMessage(message); // 发送站内信
                    if (!msgRst.isSuccess()) {
                        throw new ApplicationException(messageEnum.getValue() + "消息提醒错误");
                    }
                    if (Util.isNull(staff.getUserInfo().getMobile())) {
                        continue;
                    }
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(name);
                    pointSymbol.setMonth(month.toString());
                    pointSymbol.setTemplateCode(messageEnum.getTemplateCode());
                    List<StaffBasicInfo> list = new ArrayList<StaffBasicInfo>();
                    list.add(staff);
                    MessageHandlerUtil.sendSms(pointSymbol, list); // 发送短信
                } 
            }
        }
        result.setSuccess(true);
        return result;
    }
    
}
