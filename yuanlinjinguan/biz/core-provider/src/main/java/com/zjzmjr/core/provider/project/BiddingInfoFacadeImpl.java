package com.zjzmjr.core.provider.project;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IBiddingInfoFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.BiddingInfo;
import com.zjzmjr.core.model.project.BiddingInfoQuery;
import com.zjzmjr.core.model.project.TenderBidCompanyInfo;
import com.zjzmjr.core.service.business.project.BiddingInfoService;

/**
 * 开标信息表的业务处理接口
 * 
 * @author oms
 * @version $Id: BiddingInfoFacadeImpl.java, v 0.1 2017-8-23 下午6:21:58 oms Exp $
 */
@Service("biddingInfoFacade")
public class BiddingInfoFacadeImpl implements IBiddingInfoFacade{

    private static final Logger logger = LoggerFactory.getLogger(BiddingInfoFacadeImpl.class);

    @Resource(name = "biddingInfoService")
    private BiddingInfoService biddingInfoService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IBiddingInfoFacade#getBiddingInfoByCondition(com.zjzmjr.core.model.project.BiddingInfoQuery)
     */
    @Override
    public ResultPage<TenderBidCompanyInfo> getBiddingInfoByCondition(BiddingInfoQuery query) {
        ResultPage<TenderBidCompanyInfo> result = new ResultPage<TenderBidCompanyInfo>();
        try {
            result = biddingInfoService.getBiddingInfoByCondition(query);
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
     * @see com.zjzmjr.core.api.project.IBiddingInfoFacade#insertBiddingInfo(com.zjzmjr.core.model.project.BiddingInfo)
     */
    @Override
    public ResultEntry<Integer> insertBiddingInfo(BiddingInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = biddingInfoService.insertBiddingInfo(record);
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
     * @see com.zjzmjr.core.api.project.IBiddingInfoFacade#updateBiddingInfoById(com.zjzmjr.core.model.project.BiddingInfo)
     */
    @Override
    public ResultEntry<Integer> updateBiddingInfoById(BiddingInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = biddingInfoService.updateBiddingInfoById(record);
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
     * @see com.zjzmjr.core.api.project.IBiddingInfoFacade#deleteBiddingInfoRankingData(com.zjzmjr.core.model.project.BiddingInfo)
     */
    @Override
    public ResultEntry<Integer> deleteBiddingInfoRankingData(BiddingInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = biddingInfoService.deleteBiddingInfoRankingData(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
}
