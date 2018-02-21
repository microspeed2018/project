package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.BiddingInfo;
import com.zjzmjr.core.model.project.BiddingInfoQuery;
import com.zjzmjr.core.model.project.TenderBidCompanyInfo;

/**
 * 开标信息表
 * 
 * @author oms
 * @version $Id: BiddingInfoMapper.java, v 0.1 2017-8-23 下午3:56:20 oms Exp $
 */
public interface BiddingInfoMapper {

    int deleteBiddingInfoById(Integer id);

    int insertBiddingInfo(BiddingInfo record);

    /**
     * 开标信息件数
     * 
     * @param query
     * @return
     */
    int getBiddingInfoCount(BiddingInfoQuery query);
    
    /**
     * 所有符合条件的开标信息及投标公司信息
     * 
     * @param query
     * @return
     */
    List<TenderBidCompanyInfo> getBiddingInfoByCondition(BiddingInfoQuery query);

    int updateBiddingInfoById(BiddingInfo record);

}