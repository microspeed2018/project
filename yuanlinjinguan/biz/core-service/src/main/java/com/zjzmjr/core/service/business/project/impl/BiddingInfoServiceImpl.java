package com.zjzmjr.core.service.business.project.impl;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.project.BiddingInfo;
import com.zjzmjr.core.model.project.BiddingInfoQuery;
import com.zjzmjr.core.model.project.TenderBidCompanyInfo;
import com.zjzmjr.core.service.business.project.BiddingInfoService;
import com.zjzmjr.core.service.mapper.dao.project.BiddingInfoMapper;

/**
 * 开标信息表的业务处理层
 * 
 * @author oms
 * @version $Id: BiddingInfoServiceImpl.java, v 0.1 2017-8-23 下午5:32:30 oms Exp $
 */
@Service("biddingInfoService")
public class BiddingInfoServiceImpl implements BiddingInfoService {

    private static final Logger logger = LoggerFactory.getLogger(BiddingInfoServiceImpl.class);

    @Resource(name = "biddingInfoMapper")
    private BiddingInfoMapper biddingInfoMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.BiddingInfoService#getBiddingInfoByCondition(com.zjzmjr.core.model.project.BiddingInfoQuery)
     */
    @Override
    public ResultPage<TenderBidCompanyInfo> getBiddingInfoByCondition(BiddingInfoQuery query) {
        ResultPage<TenderBidCompanyInfo> result = new ResultPage<TenderBidCompanyInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = biddingInfoMapper.getBiddingInfoCount(query);
        if(total > 0){
            List<TenderBidCompanyInfo> list = biddingInfoMapper.getBiddingInfoByCondition(query);
            result.setSuccess(true);
            result.setList(list);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }

        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.BiddingInfoService#insertBiddingInfo(com.zjzmjr.core.model.project.BiddingInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertBiddingInfo(BiddingInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = biddingInfoMapper.insertBiddingInfo(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":开标信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.BiddingInfoService#updateBiddingInfoById(com.zjzmjr.core.model.project.BiddingInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateBiddingInfoById(BiddingInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = biddingInfoMapper.updateBiddingInfoById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":开标信息表更新失败");
        }
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.BiddingInfoService#deleteBiddingInfoRankingData(com.zjzmjr.core.model.project.BiddingInfoQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteBiddingInfoRankingData(BiddingInfo query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query) || Util.isNull(query.getId()) || Util.isNull(query.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        BiddingInfoQuery queryBid = new BiddingInfoQuery();
        queryBid.setCompanyId(query.getCompanyId());
        queryBid.setProjectId(query.getProjectId());
        queryBid.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<TenderBidCompanyInfo> bidRst = getBiddingInfoByCondition(queryBid);
        if(bidRst.isSuccess()){
            List<TenderBidCompanyInfo> bidLst = bidRst.getList();
            TenderBidCompanyInfo tmpInfo = new TenderBidCompanyInfo();
            tmpInfo.setId(query.getId());
            int index = Collections.binarySearch(bidLst, tmpInfo, new Comparator<TenderBidCompanyInfo>() {
                @Override
                public int compare(TenderBidCompanyInfo srcObj, TenderBidCompanyInfo descObj) {
                    return srcObj.getId().compareTo(descObj.getId());
                }
            });
            if(index >=0){
                // 删除的开标信息存在的时候
                tmpInfo = bidLst.get(index);
                BiddingInfo record = null;
                for(int cnt=0;cnt<bidLst.size();cnt++){
                    if(tmpInfo.getRanking().compareTo(bidLst.get(cnt).getRanking()) < 0){
                        record = new BiddingInfo();
                        record.setId(bidLst.get(cnt).getId());
                        record.setRanking(bidLst.get(cnt).getRanking()-1);
                        record.setUpdateTime(query.getUpdateTime());
                        record.setUpdateUserId(query.getUpdateUserId());
                        result = updateBiddingInfoById(record);
                        if(!result.isSuccess()){
                            throw new ApplicationException("开标信息删除失败");
                        }
                    }
                }
                index = biddingInfoMapper.deleteBiddingInfoById(tmpInfo.getId());
                if(index <= 0){
                    throw new ApplicationException("开标信息删除失败");
                }
            }
        } else {
            result.setSuccess(false);
            result.setErrorMsg("开标信息删除失败");
        }
        
        return result;
    }
    
}
