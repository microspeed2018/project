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
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent")
public class TalentJobController extends BaseController {

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
    @RequestMapping(value = "/user/insertTalentJob.htm", method = RequestMethod.POST)
    public void insertTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentJob talentJob = new TalentJob();
            talentJob.setTalentId(form.getTalentId());
            talentJob.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentJob.setStartDate(form.getJobStartDate().replace("/", ""));
            talentJob.setEndDate(form.getJobEndDate().replace("/", ""));
            talentJob.setCompanyName(form.getCompanyName());
            talentJob.setPost(form.getJobPost());
            talentJob.setLeavingReason(form.getLeavingReason());
            talentJob.setReterence(form.getReterence());
            talentJob.setReterenceTel(form.getReterenceTel());
            talentJob.setCreateTime(new Date());
            talentJob.setDescription(form.getDescription());
            if (Util.isNotNull(SpringSecurityUtil.getIntPrincipal())) {
                talentJob.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            } else {
                talentJob.setCreateUserId(900000001);
            }
            ResultEntry<Integer> result = talentFacade.insertTalentJob(talentJob);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
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
    @RequestMapping(value = "/user/updateTalentJob.htm", method = RequestMethod.POST)
    public void updateTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentJob talentJob = new TalentJob();
            talentJob.setId(form.getId());
            talentJob.setTalentId(form.getTalentId());
            talentJob.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentJob.setStartDate(form.getJobStartDate().replace("/", ""));
            talentJob.setEndDate(form.getJobEndDate().replace("/", ""));
            talentJob.setCompanyName(form.getCompanyName());
            talentJob.setPost(form.getJobPost());
            talentJob.setLeavingReason(form.getLeavingReason());
            talentJob.setReterence(form.getReterence());
            talentJob.setReterenceTel(form.getReterenceTel());
            talentJob.setDescription(form.getDescription());
            // 已录入过简历，直接修改
            talentJob.setUpdateTime(new Date());
            talentJob.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = talentFacade.updateTalentJob(talentJob);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
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
    @RequestMapping(value = "/user/deleteTalentJob.htm", method = RequestMethod.POST)
    public void deleteTalentJob(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 已录入过简历，直接修改
            ResultEntry<Integer> result = talentFacade.deleteTalentJob(form.getId());
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }

        } catch (Exception ex) {
            logger.error("人才工作删除失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
