package com.zjzmjr.finance.web.talent.controller;

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
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.cache.redis.JedisPull;
import com.zjzmjr.core.common.transaction.AdminTransactionUtil;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.enums.admin.HandleResultEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentQuery;
import com.zjzmjr.finance.web.talent.form.TalentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent")
public class TalentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TalentController.class);
    
    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;
    
    /**
     * 条件查询人才信息
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getTalentByCondition.htm", method = RequestMethod.POST)
    public void getTalentByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentQuery query = new TalentQuery();
            query.setId(form.getId());
            query.setMobile(form.getMobile());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<TalentInfo> result = talentFacade.getTalentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询人才信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 条件查询人才信息
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/getTalentByCondition.htm", method = RequestMethod.POST)
    public void getTalentByConditionLogin(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentQuery query = new TalentQuery();
            query.setId(form.getId());
            query.setMobile(form.getMobile());
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<TalentInfo> result = talentFacade.getTalentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询人才信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 人才新增
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertTalent.htm", method = RequestMethod.POST)
    public void insertTalent(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
         // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_INSERT.getValue());
            TalentInfo talent = new TalentInfo();
            talent.setCompanyId(form.getCompanyId());
            talent.setName(form.getName());
            talent.setIdentityNo(form.getIdentityNo());
            talent.setMobile(form.getMobile());
            talent.setRecruitmentId(form.getRecruitmentId());
            talent.setComeDate(form.getComeDate());
            talent.setExpectedIncome(form.getExpectedIncome());
            talent.setNation(form.getNation());
            talent.setOrigin(form.getOrigin());
            talent.setMaritalStatus(form.getMaritalStatus());
            talent.setFertilityStatus(form.getFertilityStatus());
            talent.setDrivingLicenseType(form.getDrivingLicenseType());
            talent.setPoliticalStatus(form.getPoliticalStatus());
            talent.setQualification(form.getQualification());
            talent.setAddress(form.getAddress());
            talent.setMail(form.getMail());
            talent.setEmergencyMobile(form.getEmergencyMobile());
            talent.setEmergencyPerson(form.getEmergencyPerson());
            talent.setHaveAcquaintance(form.getHaveAcquaintance());
            talent.setAcquaintanceDepartment(form.getAcquaintanceDepartment());
            talent.setAcquaintanceName(form.getAcquaintanceName());
            talent.setAcquaintanceRelation(form.getAcquaintanceRelation());
            talent.setHaveCompetition(form.getHaveCompetition());
            talent.setHaveFired(form.getHaveFired());
            talent.setHaveDiseases(form.getHaveDiseases());
            talent.setHaveCriminal(form.getHaveCriminal());
            talent.setSignature(form.getSignature());
            talent.setCreateTime(new Date());
            talent.setEducationIds(form.getEducationIds());
            talent.setFamilyIds(form.getFamilyIds());
            talent.setJobIds(form.getJobIds());
            talent.setDocumentIds(form.getDocumentIds());
            List<TalentJob> jobLst = null;
            List<TalentEducation> educationLst = null;
            List<TalentFamily> familyLst = null;
            List<TalentDocument> documentLst = null;
            jobLst = JedisPull.getObject(form.getMobile()+"talentJob", List.class);
            if(Util.isNull(jobLst)){
                jobLst = JedisPull.getObject(form.getSystemTime()+"talentJob", List.class);
            }
            talent.setTalentJob(jobLst);
            educationLst = JedisPull.getObject(form.getMobile()+"talentEducation", List.class);
            if(Util.isNull(educationLst)){
                educationLst = JedisPull.getObject(form.getSystemTime()+"talentEducation", List.class);
            }
            talent.setTalentEducation(educationLst);
            familyLst = JedisPull.getObject(form.getMobile()+"talentFamily", List.class);
            if(Util.isNull(familyLst)){
                familyLst = JedisPull.getObject(form.getSystemTime()+"talentFamily", List.class);
            }
            talent.setTalentFamily(familyLst);
            documentLst = JedisPull.getObject(form.getMobile()+"talentDocument", List.class);
            if(Util.isNull(documentLst)){
                documentLst = JedisPull.getObject(form.getSystemTime()+"talentDocument", List.class);
            }
            talent.setTalentDocument(documentLst);
            ResultEntry<String> result = talentFacade.insertTalent(talent);
            if(result.isSuccess()){
                model.put("data", result.getT());
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                adminBusiness.setExtend1("姓名："+form.getName());
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("新增人才信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 人才修改
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/updateTalent.htm", method = RequestMethod.POST)
    public void updateTalent(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            Talent talent = new Talent();
            talent.setId(form.getId());
            talent.setCompanyId(form.getCompanyId());
            talent.setName(form.getName());
            talent.setIdentityNo(form.getIdentityNo());
            talent.setMobile(form.getMobile());
            talent.setRecruitmentId(form.getRecruitmentId());
            talent.setComeDate(form.getComeDate());
            talent.setExpectedIncome(form.getExpectedIncome());
            talent.setNation(form.getNation());
            talent.setOrigin(form.getOrigin());
            talent.setMaritalStatus(form.getMaritalStatus());
            talent.setFertilityStatus(form.getFertilityStatus());
            talent.setDrivingLicenseType(form.getDrivingLicenseType());
            talent.setPoliticalStatus(form.getPoliticalStatus());
            talent.setQualification(form.getQualification());
            talent.setAddress(form.getAddress());
            talent.setMail(form.getMail());
            talent.setEmergencyMobile(form.getEmergencyMobile());
            talent.setEmergencyPerson(form.getEmergencyPerson());
            talent.setHaveAcquaintance(form.getHaveAcquaintance());
            talent.setAcquaintanceDepartment(form.getAcquaintanceDepartment());
            talent.setAcquaintanceName(form.getAcquaintanceName());
            talent.setAcquaintanceRelation(form.getAcquaintanceRelation());
            talent.setHaveCompetition(form.getHaveCompetition());
            talent.setHaveFired(form.getHaveFired());
            talent.setHaveDiseases(form.getHaveDiseases());
            talent.setHaveCriminal(form.getHaveCriminal());
            talent.setSignature(form.getSignature());
            talent.setUpdateTime(new Date());
            talent.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            talent.setEducationIds(form.getEducationIds());
            talent.setFamilyIds(form.getFamilyIds());
            talent.setJobIds(form.getJobIds());
            talent.setDocumentIds(form.getDocumentIds());
            ResultEntry<Integer> result = talentFacade.updateTalent(talent);
            if(result.isSuccess()){
                putSuccess(model, "");
                adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                adminBusiness.setExtend1("姓名："+form.getName());
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
                adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                adminBusiness.setComment(result.getErrorMsg());
            }
            AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
        } catch (Exception ex) {
            logger.error("修改人才信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
}
