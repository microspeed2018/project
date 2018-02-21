package com.zjzmjr.finance.web.logo.controller;

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

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.core.api.logo.ILogoManageFacade;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.logo.LogoManage;
import com.zjzmjr.core.model.logo.LogoManageQuery;
import com.zjzmjr.finance.web.logo.form.LogoManageForm;

/**
 * 图标管理控制层
 * 
 * @author lenovo
 * @version $Id: LogoManageController.java, v 0.1 2016-9-20 下午5:00:07 lenovo Exp $
 */
@Controller
@RequestMapping("/logo")
public class LogoManageController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(LogoManageController.class);

    @Resource(name = "logoManageFacade")
    private ILogoManageFacade logoManageFacade;
    
    /**
     * 图标管理一览
     * 
     * @param resp
     * @param form
     */
    @RequestMapping(value = "/getLogoManage.htm", method = RequestMethod.POST)
    public void getLogoManage(HttpServletResponse resp,LogoManageForm form){
        logger.debug("getLogoManage入参:{}",form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            LogoManageQuery query = new LogoManageQuery();
            query.setId(form.getId());
            query.setLogoTypeNo(form.getLogoTypeNo());
            query.setLogoComment(form.getLogoComment());
            query.setLogoNo(form.getLogoNo());
            ResultRecord<LogoManage> result = logoManageFacade.getAllLogoManage(query);
            if (result.isSuccess()) {
                List<LogoManage> typeone=new ArrayList<LogoManage>();
                List<LogoManage> typetwo=new ArrayList<LogoManage>();
                List<LogoManage> typethree=new ArrayList<LogoManage>();
                List<LogoManage> typefour=new ArrayList<LogoManage>();
                List<LogoManage> typefive=new ArrayList<LogoManage>();
                List<LogoManage> typesix=new ArrayList<LogoManage>();
                List<LogoManage> typeseven=new ArrayList<LogoManage>();
                List<LogoManage> typeeight=new ArrayList<LogoManage>();
                List<LogoManage> typenine=new ArrayList<LogoManage>();
                List<LogoManage> typeten=new ArrayList<LogoManage>();
                for(int i=0;i<result.getList().size();i++){
                    if(result.getList().get(i).getLogoTypeNo()==1){
                        typeone.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==2){
                        typetwo.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==3){
                        typethree.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==4){
                        typefour.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==5){
                        typefive.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==6){
                        typesix.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==7){
                        typeseven.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==8){
                        typeeight.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==9){
                        typenine.add(result.getList().get(i)); 
                    }
                    if(result.getList().get(i).getLogoTypeNo()==10){
                        typeten.add(result.getList().get(i)); 
                    }
                }
                model.put("typeone", typeone);
                model.put("typetwo", typetwo);
                model.put("typethree", typethree);
                model.put("typefour", typefour);
                model.put("typefive", typefive);
                model.put("typesix", typesix);
                model.put("typeseven", typeseven);
                model.put("typeeight", typeeight);
                model.put("typenine", typenine);
                model.put("typeten", typeten);
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("图标管理一览出错：", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("getLogoManage出参:{}",model);
        responseAsJson(model, resp);
    }
}
