package com.zjzmjr.finance.web.company.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.company.ICompanyInfoFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;
import com.zjzmjr.finance.web.company.form.CompanyAssociatedInfoForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 关联公司控制层
 * 
 * @author lenovo
 * @version $Id: CompanyAssocitedInfoController.java, v 0.1 2017-8-24 上午10:24:24 lenovo Exp $
 */
@RequestMapping("/companyAssocited/user")
@Controller
public class CompanyAssocitedInfoController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(CompanyAssocitedInfoController.class);

    @Resource(name = "companyInfoFacade")
    private ICompanyInfoFacade companyInfoFacade;

    /**
     * 关联公司一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getCompanyAssociatedInfo.htm", method = RequestMethod.POST)
    public void getCompanyAssociatedInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultPage<CompanyAssociatedInfo> result = companyInfoFacade.getCompanyAssociatedInfo(parseAssociatedQuery(form));
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("公司信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取投标公司一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getBiddingCompanyInfo.htm", method = RequestMethod.POST)
    public void getBiddingCompanyInfo(HttpServletResponse resp, CompanyAssociatedInfoForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 投标公司list
            List<CompanyAssociatedInfo> companyLst = new ArrayList<CompanyAssociatedInfo>();
            int totalResults = 0;
            CompanyInfoQuery query = new CompanyInfoQuery();
            query.setId(SpringSecurityUtil.getIntCompany());
            query.setPageBean(new PageBean(form.getRows(), form.getPage()));
            ResultPage<CompanyInfoAptitude> result = companyInfoFacade.getCompanyInfoByCondition(query);
            if(result.isSuccess()){
                CompanyAssociatedInfo companya = null;
                for(CompanyInfoAptitude info : result.getList()){
                    companya = new CompanyAssociatedInfo();
                    companya.setId(GenerateConstants.systemGeneralizer);
                    companya.setCompanyName(info.getCompanyName());
                    companya.setMobile(info.getMobile());
                    companya.setCityId(info.getCityId());
                    companya.setAddress(info.getAddress());
                    companyLst.add(companya);
                }
                totalResults += result.getPage().getTotalResults();
            }
            ResultPage<CompanyAssociatedInfo> associatedRst = companyInfoFacade.getCompanyAssociatedInfo(parseAssociatedQuery(form));
            if (associatedRst.isSuccess()) {
                companyLst.addAll(associatedRst.getList());
                totalResults += associatedRst.getPage().getTotalResults();
            }
            if(companyLst == null || companyLst.isEmpty()){
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }else{
                model.put("rows", companyLst);
                model.put("total", totalResults);
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("投标公司信息查询一览出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 获取关联公司的查询条件
     * 
     * @param form
     * @return
     */
    private CompanyAssociatedQuery parseAssociatedQuery(CompanyAssociatedInfoForm form){
        CompanyAssociatedQuery query = new CompanyAssociatedQuery();
        if(Util.isNotNull(form.getId())){             
            query.setId(form.getId());
        }
        query.setCompanyId(SpringSecurityUtil.getIntCompany());
        if(Util.isNotNull(form.getCompanyName())){
            query.setCompanyName(form.getCompanyName()); 
        }
        if(Util.isNotNull(form.getLegalPerson())){
            query.setLegalPerson(form.getLegalPerson());
        }
        if(Util.isNotNull(form.getCompanyDistinguish())){
            query.setCompanyDistinguish(form.getCompanyDistinguish());
        }
        if(Util.isNotNull(form.getAddress())){
            query.setAddress(form.getAddress());
        }
        if(Util.isNotNull(form.getCreateTimeStart())){
            query.setCreateTimeStart(form.getCreateTimeStart());
        }
        if(Util.isNotNull(form.getCreateTimeEnd())){
            query.setCreateTimeEnd(form.getCreateTimeEnd());
        } 
        if(Util.isNotNull(form.getStatus())){
            query.setStatus(form.getStatus());
        }
        query.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
        
        return query;
    }
}
