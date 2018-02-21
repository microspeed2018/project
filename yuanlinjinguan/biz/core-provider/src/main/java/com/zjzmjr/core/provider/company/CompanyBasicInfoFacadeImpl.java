package com.zjzmjr.core.provider.company;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.company.ICompanyBasicInfoFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.BasicData;
import com.zjzmjr.core.model.company.BasicDataQuery;
import com.zjzmjr.core.service.business.company.CompanyBasicInfoService;

/**
 * 基础数据
 * 
 * @author oms
 * @version $Id: CompanyBasicInfoFacadeImpl.java, v 0.1 2017-8-10 下午6:26:11 oms Exp $
 */
@Service("companyBasicInfoFacade")
public class CompanyBasicInfoFacadeImpl implements ICompanyBasicInfoFacade {

    private static final Logger logger = LoggerFactory.getLogger(CompanyBasicInfoFacadeImpl.class);

    @Resource(name = "companyBasicInfoService")
    private CompanyBasicInfoService companyBasicInfoService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#getAllBasic()
     */
    @Override
    public ResultRecord<BasicData> getAllBasicData() {
        ResultRecord<BasicData> result = new ResultRecord<BasicData>();
        try {
            result = companyBasicInfoService.getAllBasicData();
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#getCategorys()
     */
    @Override
    public ResultRecord<BasicData> getCategorys() {
        ResultRecord<BasicData> result = new ResultRecord<BasicData>();
        try {
            result = companyBasicInfoService.getCategorys();
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#getBasicDataByCondition(com.zjzmjr.core.model.company.BasicDataQuery)
     */
    @Override
    public ResultPage<BasicData> getBasicDataByCondition(BasicDataQuery query) {
        ResultPage<BasicData> result = new ResultPage<BasicData>();
        try {
            result = companyBasicInfoService.getBasicDataByCondition(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#getBasicDataById(com.zjzmjr.core.model.company.BasicDataQuery)
     */
    @Override
    public ResultEntry<BasicData> getBasicDataById(BasicDataQuery query) {
        ResultEntry<BasicData> result = new ResultEntry<BasicData>();
        try {
            result = companyBasicInfoService.getBasicDataById(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#insertBasicData(com.zjzmjr.core.model.company.BasicData)
     */
    @Override
    public ResultEntry<Integer> insertBasicData(BasicData BasicData) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyBasicInfoService.insertBasicData(BasicData);
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#updateBasicData(com.zjzmjr.core.model.company.BasicData)
     */
    @Override
    public ResultEntry<Integer> updateBasicData(BasicData BasicData) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyBasicInfoService.updateBasicData(BasicData);
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
     * @see com.zjzmjr.core.api.company.ICompanyBasicInfoFacade#deleteBasicDataById(java.lang.Integer)
     */
    @Override
    public ResultEntry<Integer> deleteBasicDataById(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyBasicInfoService.deleteBasicDataById(id);
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
