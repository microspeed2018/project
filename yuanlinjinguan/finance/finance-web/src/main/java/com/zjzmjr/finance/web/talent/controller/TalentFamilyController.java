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
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentFamilyQuery;
import com.zjzmjr.finance.web.talent.form.TalentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/talent")
public class TalentFamilyController extends BaseController{

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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertTalentFamily.htm", method = RequestMethod.POST)
    public void insertTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            TalentFamily talentFamily = new TalentFamily();
            talentFamily.setTalentId(form.getTalentId());
            talentFamily.setCompanyId(form.getCompanyId());
            talentFamily.setName(form.getFamilyName());
            talentFamily.setRelation(form.getRelation());
            talentFamily.setCompany(form.getCompany());
            talentFamily.setPost(form.getFamilyPost());
            talentFamily.setTelephone(form.getTelephone());
            if(Util.isNotNull(form.getTalentId())){
                //已录入过简历，直接修改
                talentFamily.setCreateTime(new Date());
                if(Util.isNotNull(SpringSecurityUtil.getIntPrincipal())){
                    talentFamily.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                }else{
                    talentFamily.setCreateUserId(900000001);
                }
                ResultEntry<Integer> result = talentFacade.insertTalentFamily(talentFamily);
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("家庭背景新增");
                }else{
                    putError(model, result.getErrorCode(), result.getErrorMsg()); 
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                    }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                List<TalentFamily> familyLst = null;
                if(Util.isNotNull(form.getMobile())){
                    familyLst = JedisPull.getObject(form.getMobile()+"talentFamily", List.class);  
                }else{
                    familyLst = JedisPull.getObject(form.getSystemTime()+"talentFamily", List.class); 
                }
                if(Util.isNull(familyLst)){
                    talentFamily.setNo(GenerateConstants.number_0);
                    familyLst = new ArrayList<TalentFamily>();
                    familyLst.add(talentFamily);
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentFamily", familyLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentFamily", familyLst, 3600*24); 
                    }
                }else{
                    talentFamily.setNo(familyLst.get(familyLst.size()-1).getNo()+1);
                    familyLst.add(talentFamily);
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentFamily", familyLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentFamily", familyLst, 3600*24); 
                    }
                }  
                putSuccess(model, "");
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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateTalentFamily.htm", method = RequestMethod.POST)
    public void updateTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            TalentFamily talentFamily = new TalentFamily();
            talentFamily.setId(form.getId());
            talentFamily.setTalentId(form.getTalentId());
            //talentFamily.setCompanyId(SpringSecurityUtil.getIntCompany());
            talentFamily.setName(form.getFamilyName());
            talentFamily.setRelation(form.getRelation());
            talentFamily.setCompany(form.getCompany());
            talentFamily.setPost(form.getFamilyPost());
            talentFamily.setTelephone(form.getTelephone());
            if(Util.isNotNull(form.getTalentId())){
                //已录入过简历，直接修改
                talentFamily.setUpdateTime(new Date());
                ResultEntry<Integer> result = talentFacade.updateTalentFamily(talentFamily);
                if(result.isSuccess()){
                    putSuccess(model, ""); 
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("家庭背景修改");
                }else{
                    putError(model, result.getErrorCode(), result.getErrorMsg()); 
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                //未录入过简历，存缓存
                List<TalentFamily> familyLst = null;
                if(Util.isNotNull(form.getMobile())){
                    familyLst = JedisPull.getObject(form.getMobile()+"talentFamily", List.class);  
                }else{
                    familyLst = JedisPull.getObject(form.getSystemTime()+"talentFamily", List.class); 
                }
                if(Util.isNull(familyLst)){
                    talentFamily.setNo(GenerateConstants.number_0);
                    familyLst = new ArrayList<TalentFamily>();
                    familyLst.add(talentFamily);
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentFamily", familyLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentFamily", familyLst, 3600*24); 
                    }
                }else{
                    for(int i=0;i<familyLst.size();i++){
                        if(familyLst.get(i).getNo().equals(form.getNo())){
                            familyLst.get(i).setId(form.getId());
                            familyLst.get(i).setTalentId(form.getTalentId());
                            //talentFamily.setCompanyId(SpringSecurityUtil.getIntCompany());
                            familyLst.get(i).setName(form.getFamilyName());
                            familyLst.get(i).setRelation(form.getRelation());
                            familyLst.get(i).setCompany(form.getCompany());
                            familyLst.get(i).setPost(form.getFamilyPost());
                            familyLst.get(i).setTelephone(form.getTelephone());
                        }
                    }
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentFamily", familyLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentFamily", familyLst, 3600*24); 
                    }
                }  
                putSuccess(model, ""); 
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
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteTalentFamily.htm", method = RequestMethod.POST)
    public void deleteTalentFamily(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if(Util.isNotNull(form.getTalentId())){
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
                //已录入过简历，直接修改
                ResultEntry<Integer> result = talentFacade.deleteTalentFamily(form.getId());
                if(result.isSuccess()){
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("家庭背景删除");
                }else{
                    putError(model, result.getErrorCode(), result.getErrorMsg()); 
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            }else{
                List<TalentFamily> familyLst = null;
                if(Util.isNotNull(form.getMobile())){
                    familyLst = JedisPull.getObject(form.getMobile()+"talentFamily", List.class);  
                }else{
                    familyLst = JedisPull.getObject(form.getSystemTime()+"talentFamily", List.class); 
                }
                if(Util.isNotNull(familyLst)){
                    for(int i=0;i<familyLst.size();i++){
                        if(familyLst.get(i).getNo().equals(form.getNo())){
                            familyLst.remove(i);
                        }
                    }
                    if(Util.isNotNull(form.getMobile())){
                        JedisPull.setObject(form.getMobile()+"talentFamily", familyLst, 3600*24); 
                    }else{
                        JedisPull.setObject(form.getSystemTime()+"talentFamily", familyLst, 3600*24); 
                    }
                }
                putSuccess(model, "");
            }
            
         } catch (Exception ex) {
             logger.error("人才家庭删除失败", ex);
             putError(model, ex.getMessage());
         }
         responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才家属
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTalentFamilyByCondition.htm", method = RequestMethod.POST)
    public void getTalentFamilyByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentFamilyQuery query = new TalentFamilyQuery();
            query.setId(form.getId());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentFamily> result = talentFacade.getTalentFamilyByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                List<TalentFamily> familyLst = null;
                if(Util.isNotNull(form.getMobile())){
                    familyLst = JedisPull.getObject(form.getMobile()+"talentFamily", List.class);  
                }else{
                    familyLst = JedisPull.getObject(form.getSystemTime()+"talentFamily", List.class); 
                }
                if(Util.isNotNull(familyLst)){
                    model.put("data", familyLst);
                    putSuccess(model, "");  
                }else{
                    putError(model, "-1", "暂无数据"); 
                } 
            }
        } catch (Exception ex) {
            logger.error("人才家属获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
