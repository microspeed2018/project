package com.zjzmjr.core.service.business.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;
import com.zjzmjr.core.service.business.company.CompanyBasicInfoService;
import com.zjzmjr.core.service.mapper.dao.company.CompanyBasicInfoMapper;

/**
 * 基础数据处理层
 * 
 * @author oms
 * @version $Id: CompanyBasicInfoServiceImpl.java, v 0.1 2017-8-10 下午6:05:19 oms
 *          Exp $
 */
@Service("companyBasicInfoService")
public class CompanyBasicInfoServiceImpl implements CompanyBasicInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyBasicInfoServiceImpl.class);

    @Resource(name = "companyBasicInfoMapper")
    private CompanyBasicInfoMapper companyBasicInfoMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#getBasicByCategoryId(java.lang.Integer)
     */
    @Override
    public ResultRecord<BasicData> getAllBasicData() {
        ResultRecord<BasicData> result = new ResultRecord<BasicData>();
        List<BasicData> list = companyBasicInfoMapper.getAllBasic();
        if (list.size() == 0 || list == null) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#getCategorys()
     */
    @Override
    public ResultRecord<BasicData> getCategorys() {
        ResultRecord<BasicData> result = new ResultRecord<BasicData>();
        List<BasicData> list = companyBasicInfoMapper.getCategorys();
        if (Util.isNull(list)) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#getBasic(com.zjzmjr.core.model.victory.BasicData.BasicDataQuery)
     */
    @Override
    public ResultPage<BasicData> getBasicDataByCondition(BasicDataQuery query) {
        ResultPage<BasicData> result = new ResultPage<BasicData>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        int total = companyBasicInfoMapper.getBasicCount(query);
        if (total > 0) {
            List<BasicData> list = companyBasicInfoMapper.getBasic(query);
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
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#getBasicById(com.zjzmjr.core.model.victory.BasicData.BasicDataQuery)
     */
    @Override
    public ResultEntry<BasicData> getBasicDataById(BasicDataQuery query) {
        ResultEntry<BasicData> result = new ResultEntry<BasicData>();
        if (Util.isNull(query) || Util.isNull(query.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        BasicData BasicData = companyBasicInfoMapper.getBasicById(query);
        if (Util.isNotNull(BasicData)) {
            result.setSuccess(true);
            result.setT(BasicData);
        } else {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#insertBasic(com.zjzmjr.core.model.victory.BasicData.BasicData)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertBasicData(BasicData BasicData) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(BasicData)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        logger.info("新增基础数据：{}", BasicData.toString());
        int cnt = companyBasicInfoMapper.insertBasic(BasicData);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":插入基础数据失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#updateBasic(com.zjzmjr.core.model.victory.BasicData.BasicData)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateBasicData(BasicData BasicData) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(BasicData) || Util.isNull(BasicData.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        logger.info("修改基础数据：{}", BasicData.toString());
        int cnt = companyBasicInfoMapper.updateById(BasicData);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新基础数据失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.victory.BasicData.BasicService#deleteBasic(com.zjzmjr.core.model.victory.BasicData.BasicData)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteBasicDataById(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            return result;
        }
        logger.info("删除基础数据：{}", id.toString());
        int cnt = companyBasicInfoMapper.deleteById(id);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":删除基础数据失败");
        }
        return result;
    }

}
