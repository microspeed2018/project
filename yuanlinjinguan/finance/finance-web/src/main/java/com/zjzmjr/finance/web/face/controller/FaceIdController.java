package com.zjzmjr.finance.web.face.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.face.IFaceFacade;
import com.zjzmjr.core.base.common.OrderSerialNoUtil;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.face.Face;
import com.zjzmjr.core.model.face.FaceQuery;
import com.zjzmjr.core.model.face.FaceResult;
import com.zjzmjr.finance.web.face.form.FaceIdForm;
import com.zjzmjr.security.web.util.SpringSecurityUtil;
import com.zjzmjr.web.mvc.controller.BaseController;

@Controller
@RequestMapping("/faceId")
public class FaceIdController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OCRController.class);

    @Resource(name = "faceFacade")
    private IFaceFacade faceFacade;
    
    /**
     * faceId web页面返回
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(value="/getVerification.htm", method=RequestMethod.POST)
    public void getVerification(HttpServletResponse resp,HttpServletRequest req,FaceIdForm form){
        logger.debug("getVerification入参:{}",form);
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            FaceQuery query = new FaceQuery();
            Assert.isTrue(!StringUtils.isEmpty(form.getName()), "姓名不能为空");
            Assert.isTrue(!StringUtils.isEmpty(form.getIdCard()), "身份证号不能为空");
            query.setBizNo(OrderSerialNoUtil.getMerOrderId(SpringSecurityUtil.getIntPrincipal()));
            query.setName(form.getName());
            query.setIdCard(form.getIdCard());
            ResultEntry<Face> result = faceFacade.getVerification(query);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("Web页面获取失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("getVerification出参:{}",form);
        responseAsJson(model, resp);
    }
    
    /**
     * faceId活体验证结果
     * 
     * @param resp
     * @param req
     */
    @RequestMapping(value="/getFaceResult.htm", method=RequestMethod.POST)
    public void getFaceResult(HttpServletResponse resp,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        String bizId = req.getParameter("bizId");
        try {
            ResultEntry<FaceResult> result = faceFacade.getFaceResult(bizId);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("faceId活体验证结果获取失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("getFaceResult出参:{}",model);
        responseAsJson(model, resp);
    }
}
