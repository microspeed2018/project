package com.zjzmjr.core.provider.face;

import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.face.IFaceFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.face.Face;
import com.zjzmjr.core.model.face.FaceQuery;
import com.zjzmjr.core.model.face.FaceResult;
import com.zjzmjr.core.service.business.face.FaceService;

@Service("faceFacade")
public class FaceFacadeImpl implements IFaceFacade{
   
    private static final Logger logger = LoggerFactory.getLogger(FaceFacadeImpl.class);

    @Resource(name = "faceService")
    private FaceService faceService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.face.IFaceFacede#ocrIDCard(java.lang.String)
     */
	@Override
	public ResultEntry<Map<String, String>> ocrIDCard(String imgUrl) {
		ResultEntry<Map<String, String>> result = new ResultEntry<Map<String, String>>();
		try {
			result = faceService.ocrIDCard(imgUrl);
		} catch (Exception e) {
			result.setSuccess(false);
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
			return result;
		}
		return result;
	}

	/**
	 * 
	 * @see com.zjzmjr.core.api.face.IFaceFacade#getVerification()
	 */
    @Override
    public ResultEntry<Face> getVerification(FaceQuery query) {
        ResultEntry<Face> result = new ResultEntry<Face>();
        try {
            result = faceService.getVerification(query);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.face.IFaceFacade#getFaceResult(java.lang.String)
     */
    @Override
    public ResultEntry<FaceResult> getFaceResult(String bizId) {
        ResultEntry<FaceResult> result = new ResultEntry<FaceResult>();
        try {
            result = faceService.getFaceResult(bizId);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
}
