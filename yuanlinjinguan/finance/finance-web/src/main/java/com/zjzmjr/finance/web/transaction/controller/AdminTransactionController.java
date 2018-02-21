package com.zjzmjr.finance.web.transaction.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.transaction.IAdminTransactionFacade;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.enums.admin.AdminBusinessTypeEnum;
import com.zjzmjr.core.model.admin.AdminBusiness;
import com.zjzmjr.core.model.admin.AdminBusinessQuery;
import com.zjzmjr.finance.web.transaction.form.AdminBusinessQueryForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 管理员事务管理层
 * 
 * @author Administrator
 * @version $Id: AdminTransactionController.java, v 0.1 2016-10-10 上午11:04:24
 *          Administrator Exp $
 */
@Controller
@RequestMapping("/adminTransaction/user/index.htm")
public class AdminTransactionController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(AdminTransactionController.class);

    @Resource(name = "adminTransactionFacade")
    private IAdminTransactionFacade adminTransactionFacade;

    /**
     * 管理员事务查询列表
     * 
     * @param resp
     * @param req
     * @param form
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getAdminTransactionList" })
    public void getAdminTransactionList(HttpServletResponse resp, HttpServletRequest req, AdminBusinessQueryForm form) {
        Map<String, Object> model = new HashMap<String, Object>();
        AdminBusinessQuery query = new AdminBusinessQuery();
        try {
            query.setCompanyId(SpringSecurityUtil.getIntCompany());
            query.setProjectId(form.getProjectId());
            query.setAdminName(form.getAdminName());
            query.setBusinessType(form.getBusinessType());
            query.setExtendedMsg(form.getExtendedField());
            PageBean pageBean = new PageBean(form.getRows(), form.getPage());
            query.setPageBean(pageBean);
            ResultPage<AdminBusiness> result = adminTransactionFacade.getAdminTransactionList(query);
            if (result.isSuccess()) {
                List<AdminBusiness> list = result.getList();
                putBusinessTypeName(list);
                model.put("data", list);
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("事务查询出错：", ex);
            putError(model, ex.getMessage());
        }
        responseAsJson(model, resp);
    }

    
    /**
     * 管理员事务类型下拉值获取
     * 
     * @param resp
     */
    @RequestMapping(method = RequestMethod.POST, params = { "action=getTypeList" })
    public void getTypeList(HttpServletResponse resp){
        Map<String,Object> model=new HashMap<String,Object>();
        List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
        for(int i=0;i<AdminBusinessTypeEnum.values().length;i++){
            HashMap<String,String> map = new HashMap<String,String>();
            map.put("businessType", AdminBusinessTypeEnum.values()[i].getValue().toString());
            map.put("businessName", AdminBusinessTypeEnum.values()[i].getMessage());
            list.add(map);
        }
        model.put("data", list);
        putSuccess(model, "");
        responseAsJson(model, resp);
    }

    /**
     * 获取事务的中文名称
     * 
     * @param list
     */
    private void putBusinessTypeName(List<AdminBusiness> list){
        if(list == null || list.isEmpty()){
            return ;
        }
        for(AdminBusiness business : list){
            business.setBusinessName(AdminBusinessTypeEnum.getByValue(business.getBusinessType()).getMessage());
        }
    }
}
