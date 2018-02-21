package com.zjzmjr.core.service.business.project.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.SubpackagePayment;
import com.zjzmjr.core.model.project.SubpackagePaymentInfo;
import com.zjzmjr.core.service.business.project.SubpackagePaymentService;
import com.zjzmjr.core.service.mapper.dao.project.SubpackagePaymentMapper;

/**
 * 分包付款表业务信息处理
 * 
 * @author oms
 * @version $Id: SubpackagePaymentServiceImpl.java, v 0.1 2017-9-28 下午2:02:30 oms Exp $
 */
@Service("subpackagePaymentService")
public class SubpackagePaymentServiceImpl implements SubpackagePaymentService {

    private static final Logger logger = LoggerFactory.getLogger(SubpackagePaymentServiceImpl.class);

    @Resource(name = "subpackagePaymentMapper")
    private SubpackagePaymentMapper subpackagePaymentMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.SubpackagePaymentService#getSubpackagePaymentByCondition(com.zjzmjr.core.model.project.ContractSubpackageQuery)
     */
    @Override
    public ResultPage<SubpackagePaymentInfo> getSubpackagePaymentByCondition(ContractSubpackageQuery query) {
        ResultPage<SubpackagePaymentInfo> result = new ResultPage<SubpackagePaymentInfo>();
        if (Util.isNull(query) || Util.isNull(query.getVerifyType())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = subpackagePaymentMapper.getSubpackagePaymentCount(query);
        if(total > 0){
            List<SubpackagePaymentInfo> list =subpackagePaymentMapper.getSubpackagePaymentByCondition(query);
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
     * @see com.zjzmjr.core.service.business.project.SubpackagePaymentService#insertSubpackagePayment(com.zjzmjr.core.model.project.SubpackagePayment)
     */
    @Override
    public ResultEntry<Integer> insertSubpackagePayment(SubpackagePayment record) {
        return null;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.SubpackagePaymentService#updateSubpackagePaymentById(com.zjzmjr.core.model.project.SubpackagePayment)
     */
    @Override
    public ResultEntry<Integer> updateSubpackagePaymentById(SubpackagePayment record) {
        return null;
    }
    
}
