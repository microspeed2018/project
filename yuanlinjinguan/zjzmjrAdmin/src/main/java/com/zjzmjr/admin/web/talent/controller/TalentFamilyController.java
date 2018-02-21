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
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent")
public class TalentFamilyController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(TalentFamilyController.class);

    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;

    /**
     * 新增人才家庭
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/insertTalentFamily.htm", method = RequestMethod.POST)
    public void insertTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentFamily talentFamily = new TalentFamily();
            talentFamily.setTalentId(form.getTalentId());
            talentFamily.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentFamily.setName(form.getFamilyName());
            talentFamily.setRelation(form.getRelation());
            talentFamily.setCompany(form.getCompany());
            talentFamily.setPost(form.getFamilyPost());
            talentFamily.setTelephone(form.getTelephone());
            // 已录入过简历，直接修改
            talentFamily.setCreateTime(new Date());
            if (Util.isNotNull(SpringSecurityUtil.getIntPrincipal())) {
                talentFamily.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            } else {
                talentFamily.setCreateUserId(900000001);
            }
            ResultEntry<Integer> result = talentFacade.insertTalentFamily(talentFamily);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才家庭新增失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 修改人才家庭
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/updateTalentFamily.htm", method = RequestMethod.POST)
    public void updateTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentFamily talentFamily = new TalentFamily();
            talentFamily.setId(form.getId());
            talentFamily.setTalentId(form.getTalentId());
            talentFamily.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentFamily.setName(form.getFamilyName());
            talentFamily.setRelation(form.getRelation());
            talentFamily.setCompany(form.getCompany());
            talentFamily.setPost(form.getFamilyPost());
            talentFamily.setTelephone(form.getTelephone());
            // 已录入过简历，直接修改
            talentFamily.setUpdateTime(new Date());
            talentFamily.setUpdateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = talentFacade.updateTalentFamily(talentFamily);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才家庭修改失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    /**
     * 删除人才家庭
     * 
     * @param req
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/deleteTalentFamily.htm", method = RequestMethod.POST)
    public void deleteTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 已录入过简历，直接修改
            ResultEntry<Integer> result = talentFacade.deleteTalentFamily(form.getId());
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("人才家庭删除失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
