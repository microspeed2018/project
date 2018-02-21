package com.zjzmjr.finance.web.project.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.core.api.project.IBiddingInfoFacade;
import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.project.BiddingInfo;
import com.zjzmjr.core.model.project.BiddingInfoQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.TenderBidCompanyInfo;
import com.zjzmjr.finance.web.project.form.ProjectSchemaForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 开标信息表的业务处理
 * 
 * @author oms
 * @version $Id: BiddingInfoController.java, v 0.1 2017-8-23 下午6:30:39 oms Exp $
 */
@Controller
@RequestMapping("/project/user")
public class BiddingInfoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(BiddingInfoController.class);
    
    @Resource(name = "biddingInfoFacade")
    private IBiddingInfoFacade biddingInfoFacade;
    
    @Resource(name = "gardenProjectFacade")
    private IGardenProjectFacade gardenProjectFacade;

    /**
     * 录入开标信息时，查询开标信息一览
     * 
     * @param model
     * @param resp
     * @param projectId
     */
    @RequestMapping(value = "/bidList.htm", method = RequestMethod.POST)
    public void bidList(ModelMap model, HttpServletResponse resp, @RequestParam(required=true) Integer projectId) {
        try {
            BiddingInfoQuery query = new BiddingInfoQuery();
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(projectId);
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<TenderBidCompanyInfo> result = biddingInfoFacade.getBiddingInfoByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("开标信息及公司一览查询失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 录入开标信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/insBidding.htm", method = RequestMethod.POST)
    public void insBidding(HttpServletResponse resp, BiddingInfo form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 开标信息
            form.setCompanyId(SpringSecurityUtil.getIntCompany());
            form.setCreateTime(new Date());
            form.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            form.setUpdateTime(form.getCreateTime());
            form.setUpdateUserId(form.getCreateUserId());
            ResultEntry<Integer> result = null;
            if (Util.isNull(form.getId())) {
                // 没有主键的时候， 新插入一条
                result = biddingInfoFacade.insertBiddingInfo(form);
            } else {
                // 有主键的时候， 根据主键更新当前记录
                result = biddingInfoFacade.updateBiddingInfoById(form);
            }
            if(result.isSuccess()){
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("录入开标信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 删除开标信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/delBidding.htm", method = RequestMethod.POST)
    public void delBidding(HttpServletResponse resp, BiddingInfo form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            BiddingInfo record = new BiddingInfo();
            record.setId(form.getId());
            record.setCompanyId(SpringSecurityUtil.getIntCompany());
            record.setProjectId(form.getProjectId());
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = biddingInfoFacade.deleteBiddingInfoRankingData(record);
            if(result.isSuccess()){
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("删除开标信息失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 录入开标信息之后，更新项目表的信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/bidProject.htm", method = RequestMethod.POST)
    public void bidProject(HttpServletRequest req, HttpServletResponse resp, ProjectSchemaForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Assert.isTrue(form.getPassed() != null, "请选择是否中标");
            
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setProjectId(form.getProjectId());
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.B_290.getValue());
            //adminBusiness.setComment(AdminBusinessTypeEnum.B_290.getMessage());
            GardenProject record = new GardenProject();
            record.setId(form.getProjectId());
            // 是否中标  0:未开标 1:中标 2:未中标
            record.setIsWinBidding(form.getPassed());
            if (form.getPassed().intValue() == 2) {
                // 未中标的话，状态变成中止
                record.setStatus(GardenProjectStatusEnum.SUSPEND.getValue());
            } else {
                record.setStep(GardenProjectStepEnum.P_300.getValue());
            }
            record.setUpdateTime(new Date());
            record.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = gardenProjectFacade.updateGardenProjectById(record);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("录入开标信息更新项目表失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
