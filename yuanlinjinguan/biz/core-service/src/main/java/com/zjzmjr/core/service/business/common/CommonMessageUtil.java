package com.zjzmjr.core.service.business.common;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.SpringContextUtil;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.message.MessageService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.user.StaffInfoService;

/**
 * 共同业务处理的工具类
 * 
 * @author oms
 * @version $Id: CommonServiceUtil.java, v 0.1 2017-9-22 下午3:25:02 oms Exp $
 */
public class CommonMessageUtil {

    private static StaffInfoService staffInfoService = SpringContextUtil.getBean("staffInfoService");

    private static GardenProjectService gardenProjectService = SpringContextUtil.getBean("gardenProjectService");
    /** 消息服务 */
    private static MessageService messageService = SpringContextUtil.getBean("messageService");

    /**
     * 插入消息
     * 
     * @param message
     * @param staffInfoLst
     */
    public static void insertMessage(Message message) {
        messageService.insertMessage(message);
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
        Admin admin = new Admin();
        admin.setId(userId);
        staffInfo.setUserInfo(admin);
        staffInfoLst.add(staffInfo);
        insertMessage(message, staffInfoLst);
    }

    /**
     * 插入消息
     * 
     * @param message
     * @param staffInfoLst
     */
    public static void insertMessage(Message message, List<StaffBasicInfo> staffInfoLst) {
        for (int index = 0; index < staffInfoLst.size(); index++) {
            message.setUserId(staffInfoLst.get(index).getUserInfo().getId());
            insertMessage(message);
        }
    }

    /**
     * 获取经营部专员人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getJinYingPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(4);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
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
    public static List<StaffBasicInfo> getKuaijiPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(7);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
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
    public static List<StaffBasicInfo> getChuNaPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(8);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
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
    public static List<StaffBasicInfo> getXingZhengPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(9);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }
    
    /**
     * 根据项目编号获取项目信息
     * 
     * @param projectId
     * @return
     */
    public static GardenProject getProjectInfoById(Integer projectId){
        GardenProject project = new GardenProject();
        GardenProjectQuery query = new GardenProjectQuery();
        query.setId(projectId);
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultEntry<GardenProject> result = gardenProjectService.getGardenProjectByIdAndStatus(query);
        if(result.isSuccess()){
            project = result.getT();
        }
        
        return project;
    }
    
    /**
     * 获取经营部经理人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getJinYingManagerPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(3);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }
    
    /**
     * 获取院办人员
     * 
     * @return
     */
    public static List<StaffBasicInfo> getYuanBanPersons(Integer companyId){
        List<StaffBasicInfo> staffLst = null;
        StaffInfoQuery query = new StaffInfoQuery();
        query.setCompanyId(companyId);
        query.setJobId(2);
        query.setNotJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> result = staffInfoService.getStaffInfoByCondition(query);
        if(result.isSuccess()){
            staffLst = result.getList();
        }else{
            staffLst = Collections.emptyList();
        }
        
        return staffLst;
    }
}
