package com.zjzmjr.finance.web.talent.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.talent.ITalentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentJobQuery;
import com.zjzmjr.finance.web.talent.form.TalentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent")
public class TalentJobController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TalentController.class);
    
    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;
    
    /**
     * 新增人才工作
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings({ "unchecked"})
    @RequestMapping(value = "/insertTalentJob.htm", method = RequestMethod.POST)
    public void insertTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
           // 新增管理员事物
           AdminBusiness adminBusiness = new AdminBusiness();
           adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
           TalentJob talentJob = new TalentJob();
           talentJob.setTalentId(form.getTalentId());
           talentJob.setCompanyId(form.getCompanyId());
           talentJob.setStartDate(form.getJobStartDate());
           talentJob.setEndDate(form.getJobEndDate());
           talentJob.setCompanyName(form.getCompanyName());
           talentJob.setPost(form.getJobPost());
           talentJob.setLeavingReason(form.getLeavingReason());
           talentJob.setReterence(form.getReterence());
           talentJob.setReterenceTel(form.getReterenceTel());
           talentJob.setDescription(form.getDescription());
           if(Util.isNotNull(form.getTalentId())){
               talentJob.setCreateTime(new Date());
               if(Util.isNotNull(SpringSecurityUtil.getIntPrincipal())){
                   talentJob.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
               }else{
                   talentJob.setCreateUserId(900000001);
               }
               ResultEntry<Integer> result = talentFacade.insertTalentJob(talentJob);
               if(result.isSuccess()){
                   putSuccess(model, "");
                   adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                   adminBusiness.setExtend1("人才id："+form.getTalentId());
                   adminBusiness.setExtend2("工作背景新增");
               }else{
                   putError(model, result.getErrorCode(), result.getErrorMsg()); 
                   adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                   adminBusiness.setComment(result.getErrorMsg());
               }
               AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
           }else{
               List<TalentJob> jobLst = null;
               if(Util.isNotNull(form.getMobile())){
                   jobLst = JedisPull.getObject(form.getMobile()+"talentJob", List.class);  
               }else{
                   jobLst = JedisPull.getObject(form.getSystemTime()+"talentJob", List.class); 
               }
               if(Util.isNull(jobLst)){
                   talentJob.setNo(GenerateConstants.number_0);
                   jobLst = new ArrayList<TalentJob>();
                   jobLst.add(talentJob);
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentJob", jobLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentJob", jobLst, 3600*24); 
                   }
               }else{
                   talentJob.setNo(jobLst.get(jobLst.size()-1).getNo()+1);
                   jobLst.add(talentJob);
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentJob", jobLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentJob", jobLst, 3600*24); 
                   }
               }  
               putSuccess(model, "");
           }
        } catch (Exception ex) {
            logger.error("人才工作新增失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 修改人才工作
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateTalentJob.htm", method = RequestMethod.POST)
    public void updateTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
           // 新增管理员事物
           AdminBusiness adminBusiness = new AdminBusiness();
           adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
           TalentJob talentJob = new TalentJob();
           talentJob.setId(form.getId());
           talentJob.setNo(form.getNo());
           talentJob.setTalentId(form.getTalentId());
           //talentJob.setCompanyId(SpringSecurityUtil.getIntCompany());
           talentJob.setStartDate(form.getJobStartDate());
           talentJob.setEndDate(form.getJobEndDate());
           talentJob.setCompanyName(form.getCompanyName());
           talentJob.setPost(form.getJobPost());
           talentJob.setLeavingReason(form.getLeavingReason());
           talentJob.setReterence(form.getReterence());
           talentJob.setReterenceTel(form.getReterenceTel());
           talentJob.setDescription(form.getDescription());
           if(Util.isNotNull(form.getTalentId())){
               //已录入过简历，直接修改
               talentJob.setUpdateTime(new Date());
               ResultEntry<Integer> result = talentFacade.updateTalentJob(talentJob);
               if(result.isSuccess()){
                   putSuccess(model, ""); 
                   adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                   adminBusiness.setExtend1("人才id："+form.getTalentId());
                   adminBusiness.setExtend2("工作背景修改");
               }else{
                   putError(model, result.getErrorCode(), result.getErrorMsg()); 
                   adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                   adminBusiness.setComment(result.getErrorMsg());
               }
               AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
           }else{
               //未录入过简历，存缓存
               List<TalentJob> jobLst = null;
               if(Util.isNotNull(form.getMobile())){
                   jobLst = JedisPull.getObject(form.getMobile()+"talentJob", List.class);  
               }else{
                   jobLst = JedisPull.getObject(form.getSystemTime()+"talentJob", List.class); 
               }
               if(Util.isNull(jobLst)){
                   talentJob.setNo(GenerateConstants.number_0);
                   jobLst = new ArrayList<TalentJob>();
                   jobLst.add(talentJob);
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentJob", jobLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentJob", jobLst, 3600*24); 
                   }
               }else{
                   for(int i=0;i<jobLst.size();i++){
                       if(jobLst.get(i).getNo().equals(form.getNo())){
                           jobLst.get(i).setId(form.getId());
                           jobLst.get(i).setNo(form.getNo());
                           jobLst.get(i).setTalentId(form.getTalentId());
                           //jobLst.get(i).setCompanyId(SpringSecurityUtil.getIntCompany());
                           jobLst.get(i).setStartDate(form.getJobStartDate());
                           jobLst.get(i).setEndDate(form.getJobEndDate());
                           jobLst.get(i).setCompanyName(form.getCompanyName());
                           jobLst.get(i).setPost(form.getJobPost());
                           jobLst.get(i).setLeavingReason(form.getLeavingReason());
                           jobLst.get(i).setReterence(form.getReterence());
                           jobLst.get(i).setReterenceTel(form.getReterenceTel());
                           jobLst.get(i).setDescription(form.getDescription());
                       }
                   }
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentJob", jobLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentJob", jobLst, 3600*24); 
                   }
               }  
               putSuccess(model, "");
           }
          
        } catch (Exception ex) {
            logger.error("人才工作修改失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 删除人才工作
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteTalentJob.htm", method = RequestMethod.POST)
    public void deleteTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if (Util.isNotNull(form.getTalentId())) {
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
                // 已录入过简历，直接修改
                ResultEntry<Integer> result = talentFacade.deleteTalentJob(form.getId());
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("工作背景删除");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                List<TalentJob> jobLst = null;
                if (Util.isNotNull(form.getMobile())) {
                    jobLst = JedisPull.getObject(form.getMobile() + "talentJob", List.class);
                } else {
                    jobLst = JedisPull.getObject(form.getSystemTime() + "talentJob", List.class);
                }
                if (Util.isNotNull(jobLst)) {
                    for (int i = 0; i < jobLst.size(); i++) {
                        if (jobLst.get(i).getNo().equals(form.getNo())) {
                            jobLst.remove(i);
                        }
                    }
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentJob", jobLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentJob", jobLst, 3600 * 24);
                    }
                }
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("人才工作删除失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才工作
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTalentJobByCondition.htm", method = RequestMethod.POST)
    public void getTalentJobByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentJobQuery query = new TalentJobQuery();
            query.setId(form.getId());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentJob> result = talentFacade.getTalentJobByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");  
            }else{
                List<TalentJob> jobLst = null;
                if(Util.isNotNull(form.getMobile())){
                    jobLst = JedisPull.getObject(form.getMobile()+"talentJob", List.class);  
                }else{
                    jobLst = JedisPull.getObject(form.getSystemTime()+"talentJob", List.class); 
                }
                if(Util.isNotNull(jobLst)){
                    model.put("data", jobLst);
                    putSuccess(model, "");  
                }else{
                    putError(model, "-1", "暂无数据"); 
                } 
            }
        } catch (Exception ex) {
            logger.error("人才工作获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
