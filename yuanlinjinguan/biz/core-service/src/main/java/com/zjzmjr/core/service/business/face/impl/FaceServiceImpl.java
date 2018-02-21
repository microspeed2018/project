package com.zjzmjr.core.service.business.face.impl;

import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.util.DateUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.face.Face;
import com.zjzmjr.core.model.face.FaceQuery;
import com.zjzmjr.core.model.face.FaceResult;
import com.zjzmjr.core.service.business.face.FaceService;
import com.zjzmjr.core.sms.face.FaceDetect;
import com.zjzmjr.core.sms.face.OCR;

@Service("faceService")
public class FaceServiceImpl implements FaceService{

    private static final Logger logger = LoggerFactory.getLogger(FaceServiceImpl.class);
    
    @Resource(name = "faceDetect")
    private FaceDetect faceDetect;
    
    @Resource(name = "OCR")
    private OCR OCR;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.face.FaceService#detect(java.lang.String)
     */
    public ResultEntry<Face> getVerification(FaceQuery query){
        logger.debug("getVerification入参:{}",query);
        ResultEntry<Face> result = new ResultEntry<Face>();
        Face face = faceDetect.getToken(query);
        if (Util.isNotNull(face) && Util.isNotNull(face.getToken())) {
           String webUrl = "https://api.megvii.com/faceid/lite/do?token="+face.getToken(); 
           face.setWebUrl(webUrl);
           result.setT(face);
           result.setSuccess(true);
        } else {
           result.setSuccess(false);
           result.setErrorMsg("token获取失败");
        }
        logger.debug("getVerification出参:{}",result);
        return result;
    }
   
    
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.face.FaceService#ocrIDCard(java.lang.String)
     */
	@Override
	public ResultEntry<Map<String, String>> ocrIDCard(String imgUrl) {
	    logger.debug("ocrIDCard入参:{}",imgUrl);
        ResultEntry<Map<String, String>> result = new ResultEntry<Map<String, String>>();
        if (Util.isNull(imgUrl)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), imgUrl);
            logger.debug("ocrIDCard出参:{}",result);
            return result;
        }
        Map<String, String> cardMap = OCR.ocrIdCard(imgUrl);
        if (cardMap.isEmpty() || cardMap.size() == 0) {
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.USER_IDENTITY_PHOTO_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.USER_IDENTITY_PHOTO_ERROR.getName());
        } else {
            if (cardMap.get("side").equals("back")) {
                String[] date = cardMap.get("validDate").split("-");
                if (Util.isNull(date) || "长期".equals(date[1]) ||
                        (DateUtil.parse(date[0], "yyyy.MM.dd").getTime() < new Date().getTime() && 
                                DateUtil.parse(date[1], "yyyy.MM.dd").getTime() > new Date().getTime())) {
                    result.setT(cardMap);
                    result.setSuccess(true);
                } else {
                    result.setSuccess(false);
                    result.setErrorCode(ErrorCodeEnum.USER_IDENTITY_EXPIRED.getCode());
                    result.setErrorMsg(ErrorCodeEnum.USER_IDENTITY_EXPIRED.getName());
                }
            } else {
                result.setT(cardMap);
                result.setSuccess(true);
            }
        }
        logger.debug("ocrIDCard出参:{}",result);
        return result;
    }



	/**
	 * 
	 * @see com.zjzmjr.core.service.business.face.FaceService#getFaceResult(java.lang.String)
	 */
    @Override
    public ResultEntry<FaceResult> getFaceResult(String bizId) {
        logger.debug("getFaceResult入参:{}",bizId);
        ResultEntry<FaceResult> result = new ResultEntry<FaceResult>();
        if(Util.isNull(bizId)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{}"), bizId);
            logger.debug("getFaceResult出参:{}",result);
            return result; 
        }
        FaceResult faceResult = faceDetect.getResult(bizId);
        if(Util.isNotNull(faceResult)){
            result.setT(faceResult);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorMsg("faceid活体验证结果获取失败");
        }
        logger.debug("getFaceResult出参:{}",result);
        return result;
    }
}