package com.zjzmjr.core.service.business.company.impl;

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
import com.zjzmjr.core.model.company.CompanyAptitude;
import com.zjzmjr.core.model.company.CompanyInfo;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;
import com.zjzmjr.core.service.business.company.CompanyInfoService;
import com.zjzmjr.core.service.mapper.dao.company.CompanyAptitudeMapper;
import com.zjzmjr.core.service.mapper.dao.company.CompanyInfoMapper;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoServiceImpl.java, v 0.1 2017-8-8 下午6:50:10 oms Exp $
 */
@Service("companyInfoService")
public class CompanyInfoServiceImpl implements CompanyInfoService {

    private static final Logger logger = LoggerFactory.getLogger(CompanyInfoServiceImpl.class);

    @Resource(name = "companyInfoMapper")
    private CompanyInfoMapper companyInfoMapper;
    
    /**
     * 企业资质
     */
    @Resource(name = "companyAptitudeMapper")
    private CompanyAptitudeMapper companyAptitudeMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyInfoService#getCompanyInfoAptitudeById(java.lang.Integer)
     */
    @Override
    public ResultEntry<CompanyInfoAptitude> getCompanyInfoAptitudeById(Integer id) {
        ResultEntry<CompanyInfoAptitude> result = new ResultEntry<CompanyInfoAptitude>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        CompanyInfoAptitude aptitude = companyInfoMapper.getCompanyInfoAptitudeById(id);
        if (aptitude == null) {
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        } else {
            result.setT(aptitude);
            result.setSuccess(true);
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyInfoService#getCompanyInfoByCondition(com.zjzmjr.core.model.company.CompanyInfoQuery)
     */
    @Override
    public ResultPage<CompanyInfoAptitude> getCompanyInfoByCondition(CompanyInfoQuery query) {
        ResultPage<CompanyInfoAptitude> result = new ResultPage<CompanyInfoAptitude>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = companyInfoMapper.getCompanyInfoCount(query);
        if (total > 0) {
            List<CompanyInfoAptitude> list = companyInfoMapper.getCompanyInfoByCondition(query);
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.company.CompanyInfoService#insertCompanyInfo(com.zjzmjr.core.model.company.CompanyInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertCompanyInfo(CompanyInfo record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getCompanyName())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = companyInfoMapper.insertCompanyInfo(record);
        if (total > 0) {
            CompanyInfoAptitude aptitude = (CompanyInfoAptitude) record;
            CompanyAptitude companyAptitude = null;
            for(int cnt = 0;cnt < aptitude.getAptitudes().size(); cnt++){
                companyAptitude = new CompanyAptitude();
                companyAptitude = aptitude.getAptitudes().get(cnt);
                companyAptitude.setCompanyId(record.getId());
                companyAptitude.setCreateTime(record.getCreateTime());
                companyAptitude.setCreateUserId(record.getCreateUserId());
                companyAptitude.setUpdateTime(record.getUpdateTime());
                companyAptitude.setUpdateUserId(record.getUpdateUserId());
                int apTotal = companyAptitudeMapper.insertCompanyAptitude(companyAptitude);
                if(apTotal <= 0){
                    throw new ApplicationException("企业资质插入失败");
                }
            }
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":企业信息表插入失败");
        }
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.company.CompanyInfoService#updateCompanyInfoById(com.zjzmjr.core.model.company.CompanyInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateCompanyInfoById(CompanyInfo record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = companyInfoMapper.updateCompanyInfoById(record);
        if (total > 0) {
        	total = companyAptitudeMapper.deleteCompanyAptitudeByCompanyId(record.getId());
        	if(total > 0){
                CompanyInfoAptitude aptitude = (CompanyInfoAptitude) record;
                CompanyAptitude companyAptitude = null;
                for(int cnt = 0;cnt < aptitude.getAptitudes().size(); cnt++){
                    companyAptitude = new CompanyAptitude();
                    companyAptitude = aptitude.getAptitudes().get(cnt);
                    companyAptitude.setCompanyId(record.getId());
                    companyAptitude.setCreateTime(record.getUpdateTime());
                    companyAptitude.setCreateUserId(record.getUpdateUserId());
                    companyAptitude.setUpdateTime(record.getUpdateTime());
                    companyAptitude.setUpdateUserId(record.getUpdateUserId());
                    int apTotal = companyAptitudeMapper.insertCompanyAptitude(companyAptitude);
                    if(apTotal <= 0){
                        throw new ApplicationException("企业资质更新失败");
                    }
                }
        	}
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":企业信息表更新失败");
        }
        return result;
    }

}
