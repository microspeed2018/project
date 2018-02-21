package com.zjzmjr.finance.web.face.controller;

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

import com.zjzmjr.core.api.face.IFaceFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.web.mvc.controller.BaseController;


@Controller
@RequestMapping("/ocr")
public class OCRController extends BaseController{

    private static final Logger logger = LoggerFactory.getLogger(OCRController.class);

    @Resource(name = "faceFacade")
    private IFaceFacade faceFacade;
    
    /**
     * 身份证识别
     * @param resp
     * @param req
     */
    @RequestMapping(value="/ocrIdCard.htm", method=RequestMethod.POST)
    public void ocrIdCard(HttpServletResponse resp,HttpServletRequest req){
        Map<String, Object> model = new HashMap<String, Object>();
        try {
            String imgUrl = req.getParameter("imgUrl");
            ResultEntry<Map<String, String>> result = faceFacade.ocrIDCard(imgUrl);
            if (result.isSuccess()) {
                model.put("data", result.getT());
                putSuccess(model, "");
            } else {
                putError(model,result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("身份证识别失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("ocrIdCard出参:{}",model);
        responseAsJson(model, resp);
    }
}
