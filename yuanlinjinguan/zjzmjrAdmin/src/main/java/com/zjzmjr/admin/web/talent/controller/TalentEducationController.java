package com.zjzmjr.admin.web.talent.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.admin.web.talent.form.TalentForm;
import com.zjzmjr.core.api.talent.ITalentFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.talent.TalentEducation;
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
    @RequestMapping(value = "/user/insertTalentEducation.htm", method = RequestMethod.POST)
    public void insertTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentEducation talentEducation = new TalentEducation();
            talentEducation.setTalentId(form.getTalentId());
            talentEducation.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentEducation.setStartDate(form.getEducationStartDate().replace("/", ""));
            talentEducation.setEndDate(form.getEducationEndDate().replace("/", ""));
            talentEducation.setSchoolName(form.getSchoolName());
            talentEducation.setProfessional(form.getProfessional());
            talentEducation.setCertificate(form.getCertificate());
            talentEducation.setCreateTime(new Date());
            if (Util.isNotNull(SpringSecurityUtil.getIntPrincipal())) {
                talentEducation.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            } else {
                talentEducation.setCreateUserId(900000001);
            }
            ResultEntry<Integer> result = talentFacade.insertTalentEducation(talentEducation);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
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
    @RequestMapping(value = "/user/updateTalentEducation.htm", method = RequestMethod.POST)
    public void updateTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentEducation talentEducation = new TalentEducation();
            talentEducation.setId(form.getId());
            talentEducation.setTalentId(form.getTalentId());
            talentEducation.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentEducation.setStartDate(form.getEducationStartDate().replace("/", ""));
            talentEducation.setEndDate(form.getEducationEndDate().replace("/", ""));
            talentEducation.setSchoolName(form.getSchoolName());
            talentEducation.setProfessional(form.getProfessional());
            talentEducation.setCertificate(form.getCertificate());
            // 已录入过简历，直接修改
            talentEducation.setUpdateTime(new Date());
            talentEducation.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = talentFacade.updateTalentEducation(talentEducation);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
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
    @RequestMapping(value = "/user/deleteTalentEducation.htm", method = RequestMethod.POST)
    public void deleteTalentEducation(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultEntry<Integer> result = talentFacade.deleteTalentEducation(form.getId());
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("人才学历删除失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
   
}
