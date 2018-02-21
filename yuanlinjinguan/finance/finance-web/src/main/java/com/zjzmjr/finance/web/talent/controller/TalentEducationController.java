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
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentEducationQuery;
import com.zjzmjr.finance.web.talent.form.TalentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 人才学历
 * 
 * @author lenovo
 * @version $Id: TalentEducationController.java, v 0.1 2017-12-18 下午4:08:13 lenovo Exp $
 */
@Controller
@RequestMapping("/talent")
public class TalentEducationController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TalentEducationController.class);
    
    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;
    
    /**
     * 新增人才学历
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertTalentEducation.htm", method = RequestMethod.POST)
    public void insertTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            TalentEducation talentEducation = new TalentEducation();
            talentEducation.setTalentId(form.getTalentId());
            talentEducation.setCompanyId(form.getCompanyId());
            talentEducation.setStartDate(form.getEducationStartDate());
            talentEducation.setEndDate(form.getEducationEndDate());
            talentEducation.setSchoolName(form.getSchoolName());
            talentEducation.setProfessional(form.getProfessional());
            talentEducation.setCertificate(form.getCertificate());
            if (Util.isNotNull(form.getTalentId())) {
                // 已录入过简历，直接修改
                talentEducation.setCreateTime(new Date());
                if(Util.isNotNull(SpringSecurityUtil.getIntPrincipal())){
                    talentEducation.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                }else{
                    talentEducation.setCreateUserId(900000001);
                }
                ResultEntry<Integer> result = talentFacade.insertTalentEducation(talentEducation);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("教育背景新增");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
               AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                List<TalentEducation> educationLst = null;
                if(Util.isNotNull(form.getMobile())){
                    educationLst = JedisPull.getObject(form.getMobile()+"talentEducation", List.class);  
                }else{
                    educationLst = JedisPull.getObject(form.getSystemTime()+"talentEducation", List.class); 
                }
                if(Util.isNull(educationLst)){
                    talentEducation.setNo(GenerateConstants.number_0);
                    educationLst = new ArrayList<TalentEducation>();
                    educationLst.add(talentEducation);
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentEducation", educationLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentEducation", educationLst, 3600*24); 
                    }
                }else{
                    talentEducation.setNo(educationLst.get(educationLst.size()-1).getNo()+1);
                    educationLst.add(talentEducation);
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentEducation", educationLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentEducation", educationLst, 3600*24); 
                    }
                }  
                putSuccess(model, "");
            }
         } catch (Exception ex) {
             logger.error("人才学历新增失败", ex);
             putError(model, ex.getMessage());
         }
         responseAsJson(model, resp);
    }
    
    
    /**
     * 修改人才学历
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateTalentEducation.htm", method = RequestMethod.POST)
    public void updateTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            TalentEducation talentEducation = new TalentEducation();
            talentEducation.setId(form.getId());
            talentEducation.setTalentId(form.getTalentId());
            // talentEducation.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentEducation.setStartDate(form.getEducationStartDate());
            talentEducation.setEndDate(form.getEducationEndDate());
            talentEducation.setSchoolName(form.getSchoolName());
            talentEducation.setProfessional(form.getProfessional());
            talentEducation.setCertificate(form.getCertificate());
            if (Util.isNotNull(form.getTalentId())) {
                // 已录入过简历，直接修改
                talentEducation.setUpdateTime(new Date());
                ResultEntry<Integer> result = talentFacade.updateTalentEducation(talentEducation);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("教育背景修改");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                // 未录入过简历，存缓存
                List<TalentEducation> educationLst = null;
                if (Util.isNotNull(form.getMobile())) {
                    educationLst = JedisPull.getObject(form.getMobile() + "talentEducation", List.class);
                } else {
                    educationLst = JedisPull.getObject(form.getSystemTime() + "talentEducation", List.class);
                }
                if (Util.isNull(educationLst)) {
                    talentEducation.setNo(GenerateConstants.number_0);
                    educationLst = new ArrayList<TalentEducation>();
                    educationLst.add(talentEducation);
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentEducation", educationLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentEducation", educationLst, 3600 * 24);
                    }
                } else {
                    for (int i = 0; i < educationLst.size(); i++) {
                        if (educationLst.get(i).getNo().equals(form.getNo())) {
                            educationLst.get(i).setId(form.getId());
                            educationLst.get(i).setTalentEducationIds(form.getEducationIds());
                            educationLst.get(i).setTalentId(form.getTalentId());
                            // talentEducation.setCompanyId(SpringSecurityUtil.getIntCompany());
                            educationLst.get(i).setStartDate(form.getEducationStartDate());
                            educationLst.get(i).setEndDate(form.getEducationEndDate());
                            educationLst.get(i).setSchoolName(form.getSchoolName());
                            educationLst.get(i).setProfessional(form.getProfessional());
                            educationLst.get(i).setCertificate(form.getCertificate());
                        }
                    }
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentEducation", educationLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentEducation", educationLst, 3600 * 24);
                    }
                }
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("人才学历修改失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 删除人才学历
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteTalentEducation.htm", method = RequestMethod.POST)
    public void deleteTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if(Util.isNotNull(form.getTalentId())){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
                ResultEntry<Integer> result = talentFacade.deleteTalentEducation(form.getId());
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("教育背景删除");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                List<TalentEducation> educationLst = null;
                if(Util.isNotNull(form.getMobile())){
                    educationLst = JedisPull.getObject(form.getMobile()+"talentEducation", List.class);  
                }else{
                    educationLst = JedisPull.getObject(form.getSystemTime()+"talentEducation", List.class); 
                }
                if(Util.isNotNull(educationLst)){
                    for(int i=0;i<educationLst.size();i++){
                        if(educationLst.get(i).getNo().equals(form.getNo())){
                            educationLst.remove(i);
                        }
                    }
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentEducation", educationLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentEducation", educationLst, 3600*24); 
                    }
                }  
                putSuccess(model, ""); 
            }
         } catch (Exception ex) {
             logger.error("人才学历删除失败", ex);
             putError(model, ex.getMessage());
         }
         responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才学历
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTalentEducationByCondition.htm", method = RequestMethod.POST)
    public void getTalentEducationByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentEducationQuery query = new TalentEducationQuery();
            query.setId(form.getId());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentEducation> result = talentFacade.getTalentEducationByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                List<TalentEducation> educationLst = null;
                if(Util.isNotNull(form.getMobile())){
                    educationLst = JedisPull.getObject(form.getMobile()+"talentEducation", List.class);  
                }else{
                    educationLst = JedisPull.getObject(form.getSystemTime()+"talentEducation", List.class); 
                }
                if(Util.isNotNull(educationLst)){
                    model.put("data", educationLst);
                    putSuccess(model, "");  
                }else{
                    putError(model, "-1", "暂无数据"); 
                } 
            }
        } catch (Exception ex) {
            logger.error("人才学历获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
