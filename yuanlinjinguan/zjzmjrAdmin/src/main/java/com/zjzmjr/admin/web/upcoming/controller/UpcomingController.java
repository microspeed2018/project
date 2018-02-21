package com.zjzmjr.admin.web.upcoming.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.web.mvc.controller.BaseController;
//import com.zjzmjr.core.api.admin.IAdminUpcomingFacade;
//import com.zjzmjr.core.model.admin.UpcomingModel;
//import com.zjzmjr.core.model.admin.UpcomingQuery;
//import com.zjzmjr.core.model.admin.UserUpcoming;
//import com.zjzmjr.decider.web.turnDepartmentalAudit.controller.TurnDepartmentalAuditController;
//import com.zjzmjr.loan.api.upcoming.IUpcomingFacade;

/**
 * 待办事务控制层
 * 
 * @author Administrator
 * @version $Id: UpcomingController.java, v 0.1 2017-9-20 下午7:49:08
 *          Administrator Exp $
 */
@Controller
@RequestMapping("/upcoming")
public class UpcomingController extends BaseController {

    private static final Logger logger = LoggerFactory.getLogger(UpcomingController.class);

    private final static String index = "/WEB-INF/pages/upcoming/upcoming.jsp";

//    @Resource(name = "adminUpcomingFacade")
//    private IAdminUpcomingFacade adminUpcomingFacade;
//    
//    @Resource(name = "upcomingFacade")
//    private IUpcomingFacade upcomingFacade;

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index(ModelMap model) {
//        // 查询是否有待办权限
//        if (Util.isNotNull(SpringSecurityUtil.getIntPrincipal())) {
//            UpcomingQuery query = new UpcomingQuery();
//            query.setUserId(SpringSecurityUtil.getIntPrincipal());
//            query.setType(GenerateConstants.number_1); // 待办总览
//            ResultRecord<UserUpcoming> result = adminUpcomingFacade.getUserUpcoming(query);
//            model.put("totalUpcoming", result.isSuccess());
//        } else {
//            model.put("myUpcoming", false);
//            model.put("totalUpcoming", false);
//        }
        return index;
    }
    
//    /**
//     * 我的待办
//     * 
//     * @param resp
//     */
//    @RequestMapping(value = "/user/getMyUpcoming.htm", method = RequestMethod.POST)
//    public void getMyUpcoming(HttpServletResponse resp){
//        logger.debug("getMyUpcoming 执行开始 ");
//        Map<String, Object> model = new HashMap<String, Object>();
//        try {
//            UpcomingQuery query = new UpcomingQuery();
//            query.setUserId(SpringSecurityUtil.getIntPrincipal());
//            query.setType(GenerateConstants.number_0);
//            ResultRecord<UpcomingModel> result = upcomingFacade.getUpcomingModel(query);
//            if(result.isSuccess()){
//                model.put("rows", result.getList());
//                putSuccess(model, "");
//            } else {
//                putError(model, result.getErrorMsg());
//            }
//        } catch (Exception e) {
//            logger.error("我的待办查询出错:", e);
//            putError(model, e.getMessage());
//        }
//        responseAsJson(model, resp);
//        logger.debug("getMyUpcoming 执行结束  出参:{}", model);
//    }
//    
//    /**
//     * 待办总览
//     * 
//     * @param resp
//     */
//    @RequestMapping(value = "/user/getTotalUpcoming.htm", method = RequestMethod.POST)
//    public void getTotalUpcoming(HttpServletResponse resp){
//        logger.debug("getTotalUpcoming 执行开始 ");
//        Map<String, Object> model = new HashMap<String, Object>();
//        try {
//            UpcomingQuery query = new UpcomingQuery();
//            query.setUserId(SpringSecurityUtil.getIntPrincipal());
//            query.setType(GenerateConstants.number_1);
//            ResultRecord<UpcomingModel> result = upcomingFacade.getUpcomingModel(query);
//            if(result.isSuccess()){
//                model.put("rows", result.getList());
//                putSuccess(model, "");
//            } else {
//                putError(model, result.getErrorMsg());
//            }
//        } catch (Exception e) {
//            logger.error("待办总览查询出错:", e);
//            putError(model, e.getMessage());
//        }
//        responseAsJson(model, resp);
//        logger.debug("getTotalUpcoming 执行结束  出参:{}", model);
//    }
}
