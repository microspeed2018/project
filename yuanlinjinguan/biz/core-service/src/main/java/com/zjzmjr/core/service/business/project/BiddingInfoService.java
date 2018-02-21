package com.zjzmjr.core.service.business.project;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.project.BiddingInfo;
import com.zjzmjr.core.model.project.BiddingInfoQuery;
import com.zjzmjr.core.model.project.TenderBidCompanyInfo;

/**
 * 开标信息表的业务处理层
 * 
 * @author oms
 * @version $Id: BiddingInfoService.java, v 0.1 2017-8-23 下午5:30:17 oms Exp $
 */
public interface BiddingInfoService {

    /**
     * 所有符合条件的开标信息及投标公司信息
     * 
     * @param query
     * @return
     */
    ResultPage<TenderBidCompanyInfo> getBiddingInfoByCondition(BiddingInfoQuery query);

    /**
     * 插入开标信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertBiddingInfo(BiddingInfo record);

    /**
     * 更新开标信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateBiddingInfoById(BiddingInfo record);

    /**
     * 删除开标信息，并重新排列排名信息
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> deleteBiddingInfoRankingData(BiddingInfo query) throws ApplicationException;
    
}
