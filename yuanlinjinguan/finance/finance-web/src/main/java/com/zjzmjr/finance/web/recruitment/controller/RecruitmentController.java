package com.zjzmjr.finance.web.recruitment.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.recruitment.IRecruitmentFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;
import com.zjzmjr.finance.web.recruitment.form.RecruitmentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 招聘信息
 * 
 * @author lenovo
 * @version $Id: RecruitmentController.java, v 0.1 2017-12-13 下午3:53:50 lenovo Exp $
 */
@Controller
@RequestMapping("/recruitment")
public class RecruitmentController extends BaseController{
    
    @Resource(name = "recruitmentFacade")
    private IRecruitmentFacade recruitmentFacade;

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentController.class);
    
    /**
     * 招聘信息一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getRecruitment.htm", method = RequestMethod.POST)
    public void getRecruitment(HttpServletResponse resp, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            RecruitmentQuery query = new RecruitmentQuery();
            query.setId(form.getId());
            if(Util.isNotNull(form.getPositionName())){
                query.setPositionName(form.getPositionName()); 
            }
            if(Util.isNotNull(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));
            } 
            if(Util.isNotNull(form.getEducation())){
                query.setEducation(form.getEducation()); 
            }
            if(Util.isNotNull(form.getSalary())){
                query.setSalary(form.getSalary()); 
            }
            query.setIsValid(form.getIsValid());
            query.setIsOpen(GenerateConstants.number_1);
            query.setPageBean(new PageBean(Integer.MAX_VALUE,GenerateConstants.number_1));
            ResultPage<RecruitmentInfo> result = recruitmentFacade.getRecruitment(query);
            if(result.isSuccess()){
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("招聘信息一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 条件查询招聘信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getRecruitmentByCondition.htm", method = RequestMethod.POST)
    public void getRecruitmentByCondition(HttpServletResponse resp, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            RecruitmentQuery query = new RecruitmentQuery();
            query.setId(form.getId());
            if(Util.isNotNull(form.getPositionName())){
                query.setPositionName(form.getPositionName()); 
            }
            if(Util.isNotNull(form.getAddress())){
                query.setAddress(form.getAddress());
            }
            query.setDepartmentId(form.getDepartmentId());
            if(Util.isNotNull(form.getCreateTimeStart())){
                query.setCreateTimeStart(form.getCreateTimeStart().replace("/", "")); 
            }
            if(Util.isNotNull(form.getCreateTimeEnd())){
                query.setCreateTimeEnd(form.getCreateTimeEnd().replace("/", ""));
            } 
            if(Util.isNotNull(form.getEducation())){
                query.setEducation(form.getEducation()); 
            }
            if(Util.isNotNull(form.getSalary())){
                query.setSalary(form.getSalary()); 
            }
            query.setIsValid(form.getIsValid());
            ResultRecord<Recruitment> result = recruitmentFacade.getRecruitmentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("条件查询招聘信息出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 新增招聘信息
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/user/insertRecruitment.htm", method = RequestMethod.POST)
    public void insertRecruitment(HttpServletResponse resp, RecruitmentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            Recruitment recruitment = new Recruitment();
            recruitment.setCompanyId(SpringSecurityUtil.getIntCompany());
            recruitment.setPositionName(form.getPositionName());
            recruitment.setDepartmentId(form.getDepartmentId());
            recruitment.setAddress(form.getAddress());
            recruitment.setAge(form.getAge());
            recruitment.setExperience(form.getExperience());
            recruitment.setEducation(form.getEducation());
            recruitment.setSalary(form.getSalary());
            recruitment.setNumbers(form.getNumbers());
            recruitment.setPostDuties(form.getPostDuties());
            recruitment.setQualification(form.getQualification());
            recruitment.setIsOpen(form.getIsOpen());
            recruitment.setIsValid(form.getIsValid());
            recruitment.setCreateTime(new Date());
            recruitment.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
            ResultEntry<Integer> result = recruitmentFacade.insertRecruitment(recruitment);
            if(result.isSuccess()){
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("招聘信息新增出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
