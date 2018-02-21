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
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentDocumentQuery;
import com.zjzmjr.finance.web.talent.form.TalentForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;


/**
 * 人才附件
 * 
 * @author lenovo
 * @version $Id: TalentDocumentController.java, v 0.1 2017-12-18 下午4:33:33 lenovo Exp $
 */
@Controller
@RequestMapping("/talent")
public class TalentDocumentController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TalentDocumentController.class);
    
    @Resource(name = "talentFacade")
    private ITalentFacade talentFacade;
    
    /**
     * 新增人才附件
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/insertTalentDocument.htm", method = RequestMethod.POST)
    public void insertTalentDocument(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            // 新增管理员事物
            AdminBusiness adminBusiness = new AdminBusiness();
            adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
            TalentDocument talentDocument = new TalentDocument();
            talentDocument.setTalentId(form.getTalentId());
            talentDocument.setCompanyId(form.getCompanyId());
            talentDocument.setDocumentName(form.getDocumentName());
            talentDocument.setDocumentExplain(form.getDocumentExplain());
            talentDocument.setSaveName(form.getSaveName());
            talentDocument.setSaveAddress(form.getSaveAddress());
            if (Util.isNotNull(form.getTalentId())) {
                // 已录入过简历，直接修改
                talentDocument.setCreateTime(new Date());
                if(Util.isNotNull(SpringSecurityUtil.getIntPrincipal())){
                    talentDocument.setCreateUserId(SpringSecurityUtil.getIntPrincipal());
                }else{
                    talentDocument.setCreateUserId(900000001);
                }
                ResultEntry<Integer> result = talentFacade.insertTalentDocument(talentDocument);
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("附件新增");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
                AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                // 未录入过简历，存缓存
                List<TalentDocument> documentLst = null;
                if (Util.isNotNull(form.getMobile())) {
                    documentLst = JedisPull.getObject(form.getMobile() + "talentDocument", List.class);
                } else {
                    documentLst = JedisPull.getObject(form.getSystemTime() + "talentDocument", List.class);
                }
                if (Util.isNull(documentLst)) {
                    talentDocument.setNo(GenerateConstants.number_0);
                    documentLst = new ArrayList<TalentDocument>();
                    documentLst.add(talentDocument);
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentDocument", documentLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentDocument", documentLst, 3600 * 24);
                    }
                } else {
                    talentDocument.setNo(documentLst.get(documentLst.size() - 1).getNo() + 1);
                    documentLst.add(talentDocument);
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentDocument", documentLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentDocument", documentLst, 3600 * 24);
                    }
                }
                putSuccess(model, "");
            }
        } catch (Exception ex) {
            logger.error("人才附件新增失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    
    /**
     * 修改人才附件
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/updateTalentDocument.htm", method = RequestMethod.POST)
    public void updateTalentDocument(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
           // 新增管理员事物
           AdminBusiness adminBusiness = new AdminBusiness();
           adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
           TalentDocument talentDocument = new TalentDocument();
           talentDocument.setId(form.getId());
           talentDocument.setTalentId(form.getTalentId());
           //talentDocument.setCompanyId(SpringSecurityUtil.getIntCompany());
           talentDocument.setDocumentName(form.getDocumentName());
           talentDocument.setDocumentExplain(form.getDocumentExplain());
           talentDocument.setSaveName(form.getSaveName());
           talentDocument.setSaveAddress(form.getSaveAddress());
           if (Util.isNotNull(form.getTalentId())) {
               // 已录入过简历，直接修改
               talentDocument.setUpdateTime(new Date());
               ResultEntry<Integer> result = talentFacade.updateTalentDocument(talentDocument);
               if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id：" + form.getTalentId());
                    adminBusiness.setExtend2("附件修改");
               } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
               }
               AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
           } else {
               List<TalentDocument> documentLst = null;
               if(Util.isNotNull(form.getMobile())){
                   documentLst = JedisPull.getObject(form.getMobile()+"talentDocument", List.class);  
               }else{
                   documentLst = JedisPull.getObject(form.getSystemTime()+"talentDocument", List.class); 
               }
               if(Util.isNull(documentLst)){
                   talentDocument.setNo(GenerateConstants.number_0);
                   documentLst = new ArrayList<TalentDocument>();
                   documentLst.add(talentDocument);
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentDocument", documentLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentDocument", documentLst, 3600*24); 
                   }
               }else{
                   for(int i=0;i<documentLst.size();i++){
                       if(documentLst.get(i).getNo().equals(form.getNo())){
                           documentLst.get(i).setId(form.getId());
                           documentLst.get(i).setTalentId(form.getTalentId());
                           documentLst.get(i).setCompanyId(SpringSecurityUtil.getIntCompany());
                           documentLst.get(i).setDocumentName(form.getDocumentName());
                           documentLst.get(i).setDocumentExplain(form.getDocumentExplain());
                           documentLst.get(i).setSaveName(form.getSaveName());
                           documentLst.get(i).setSaveAddress(form.getSaveAddress());
                       }
                   }
                   if(Util.isNotNull(form.getMobile())){
                       JedisPull.setObject(form.getMobile()+"talentDocument", documentLst, 3600*24); 
                   }else{
                       JedisPull.setObject(form.getSystemTime()+"talentDocument", documentLst, 3600*24); 
                   }
               }  
               putSuccess(model, "");
           }
        } catch (Exception ex) {
            logger.error("人才附件修改失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
    
    /**
     * 删除人才附件
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/deleteTalentDocument.htm", method = RequestMethod.POST)
    public void deleteTalentDocument(HttpServletRequest req, HttpServletResponse resp, TalentForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            if (Util.isNotNull(form.getTalentId())) {
                // 新增管理员事物
                AdminBusiness adminBusiness = new AdminBusiness();
                adminBusiness.setBusinessType(AdminBusinessTypeEnum.TALENT_UPDATE.getValue());
                // 已录入过简历，直接修改
                ResultEntry<Integer> result = talentFacade.deleteTalentDocument(form.getId());
                if (result.isSuccess()) {
                    putSuccess(model, "");
                    adminBusiness.setResult(HandleResultEnum.SUCCESS.getValue());
                    adminBusiness.setExtend1("人才id："+form.getTalentId());
                    adminBusiness.setExtend2("附件删除");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                    adminBusiness.setResult(HandleResultEnum.FAIL.getValue());
                    adminBusiness.setComment(result.getErrorMsg());
                }
               AdminTransactionUtil.insertAdminTransaction(adminBusiness, req);
            } else {
                List<TalentDocument> documentLst = null;
                if (Util.isNotNull(form.getMobile())) {
                    documentLst = JedisPull.getObject(form.getMobile() + "talentDocument", List.class);
                } else {
                    documentLst = JedisPull.getObject(form.getSystemTime() + "talentDocument", List.class);
                }
                if (Util.isNotNull(documentLst)) {
                    for (int i = 0; i < documentLst.size(); i++) {
                        if (documentLst.get(i).getNo().equals(form.getNo())) {
                            documentLst.remove(i);
                        }
                    }
                    if (Util.isNotNull(form.getMobile())) {
                        JedisPull.setObject(form.getMobile() + "talentDocument", documentLst, 3600 * 24);
                    } else {
                        JedisPull.setObject(form.getSystemTime() + "talentDocument", documentLst, 3600 * 24);
                    }
                }
                putSuccess(model, "");
            }
            
         } catch (Exception ex) {
             logger.error("人才附件删除失败", ex);
             putError(model, ex.getMessage());
         }
         responseAsJson(model, resp);
    }
    
    /**
     * 条件获取人才附件
     * 
     * @param req
     * @param resp
     * @param form
     */
    @SuppressWarnings("unchecked")
    @RequestMapping(value = "/getTalentDocumentByCondition.htm", method = RequestMethod.POST)
    public void getTalentDocumentByCondition(HttpServletRequest req, HttpServletResponse resp, TalentForm form){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            TalentDocumentQuery query = new TalentDocumentQuery();
            query.setId(form.getId());
            query.setTalentId(form.getTalentId());
            ResultRecord<TalentDocument> result = talentFacade.getTalentDocumentByCondition(query);
            if(result.isSuccess()){
                model.put("data", result.getList());
                putSuccess(model, "");
            }else{
                List<TalentDocument> documentLst = null;
                if(Util.isNotNull(form.getMobile())){
                    documentLst = JedisPull.getObject(form.getMobile()+"talentDocument", List.class);  
                }else{
                    documentLst = JedisPull.getObject(form.getSystemTime()+"talentDocument", List.class); 
                }
                if(Util.isNotNull(documentLst)){
                    model.put("data", documentLst);
                    putSuccess(model, "");  
                }else{
                    putError(model, "-1", "暂无数据"); 
                } 
            }
        } catch (Exception ex) {
            logger.error("人才附件获取失败", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }
}
