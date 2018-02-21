package com.zjzmjr.admin.web.transaction.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
import com.zjzmjr.admin.web.transaction.form.BusinessQueryForm;
import com.zjzmjr.core.api.transaction.ITransactionFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.enums.admin.BusinessTypeEnum;
import com.zjzmjr.core.model.admin.Business;
import com.zjzmjr.core.model.admin.BusinessQuery;

/**
 * 
 * 
 * @author oms
 * @version $Id: TransactionController.java, v 0.1 2016-6-21 下午5:16:38 oms Exp $
 */
@Controller
@RequestMapping("/transaction/user/index.htm")
public class TransactionController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(TransactionController.class);

    /**
     * 事务查询视图
     */
    private static final String TRANSACTION_VIEW = "/WEB-INF/pages/transaction/transaction.jsp";

    @Resource(name = "transactionFacade")
    private ITransactionFacade transactionFacade;
    
    /**
     * 事务查询视图
     * 
     * @param model
     * @return
     */
    @RequestMapping(method = RequestMethod.GET, params = "action=transactionView")
    public String transaction(ModelMap model) {
        return TRANSACTION_VIEW;
    }

    /**
     * 事物查询列表
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getTransactionList" })
    public void getTransactionList(HttpServletResponse resp, HttpServletRequest req, BusinessQueryForm form,String extendedField){
        logger.debug("getTransactionList 执行开始  入参:{}", form);
        Map<String, Object> model = new HashMap<String, Object>();
        BusinessQuery query = new BusinessQuery();
        try {
            query.setBusinessType(form.getBusinessType());
            query.setUserName(form.getUserName());
            query.setExtendedMsg(extendedField);
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<Business> result = transactionFacade.getTransactionList(query);
            logger.debug("getTransactionList 执行结束  出参:{}", result);
            if (result.isSuccess()) {
                model.put("rows", result.getList());
                model.put("total", result.getPage().getTotalResults());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }
            
        } catch (Exception ex) {
            logger.error("事物查询出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);

    }
    /**
     * 事务类型下拉值获取
     * 
     * @param resp
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getTypeList" })
    public void getTypeList(HttpServletResponse resp){
        logger.debug("getTypeList 执行开始  入参:{}");
        Map<String, Object> model = new HashMap<String, Object>();
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i = 0; i < BusinessTypeEnum.values().length; i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("businessType", BusinessTypeEnum.values()[i].getValue().toString());
            map.put("businessName", BusinessTypeEnum.values()[i].getMessage());
            list.add(map);
        }
        logger.debug("getTypeList 执行结束  出参:{}", list);
        model.put("data", list);
        putSuccess(model, "");
        responseAsJson(model, resp);
    }
    
}
