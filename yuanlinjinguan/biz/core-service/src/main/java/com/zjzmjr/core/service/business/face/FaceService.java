package com.zjzmjr.core.service.business.face;

import java.util.Map;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.face.Face;
import com.zjzmjr.core.model.face.FaceQuery;
import com.zjzmjr.core.model.face.FaceResult;

public interface FaceService {

    /**
     * faceId web页面返回
     * 
     * @return
     */
    ResultEntry<Face> getVerification(FaceQuery query);
    
    /**
     * 身份证OCR识别
     * @param imgUrl
     * @return
     */
    ResultEntry<Map<String, String>> ocrIDCard(String imgUrl);
    
    /**
     * faceid活体验证结果返回
     * 
     * @param bizId
     * @return
     */
    ResultEntry<FaceResult> getFaceResult(String bizId);
}
