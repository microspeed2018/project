package com.zjzmjr.core.provider.company;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.company.ICompanyInfoFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.core.model.company.CompanyInfo;
import com.zjzmjr.core.model.company.CompanyInfoAptitude;
import com.zjzmjr.core.model.company.CompanyInfoQuery;
import com.zjzmjr.core.model.company.WorkReport;
import com.zjzmjr.core.model.company.WorkReportInfo;
import com.zjzmjr.core.model.company.WorkReportQuery;
import com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService;
import com.zjzmjr.core.service.business.company.CompanyInfoService;
import com.zjzmjr.core.service.business.company.WorkReportService;

/**
 * 
 * 
 * @author oms
 * @version $Id: CompanyInfoFacadeImpl.java, v 0.1 2017-8-8 下午7:32:32 oms Exp $
 */
@Service("companyInfoFacade")
public class CompanyInfoFacadeImpl implements ICompanyInfoFacade {

    private static final Logger logger = LoggerFactory.getLogger(CompanyInfoFacadeImpl.class);

    @Resource(name = "companyInfoService")
    private CompanyInfoService companyInfoService;

    @Resource(name = "workReportService")
    private WorkReportService workReportService;
    
    @Resource(name = "companyAssociatedInfoService")
    private CompanyAssociatedInfoService companyAssociatedInfoService;

    /**
     * 
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#getCompanyInfoAptitudeById(java.lang.Integer)
     */
    @Override
    public ResultEntry<CompanyInfoAptitude> getCompanyInfoAptitudeById(Integer id) {
        ResultEntry<CompanyInfoAptitude> result = new ResultEntry<CompanyInfoAptitude>();
        try {
            result = companyInfoService.getCompanyInfoAptitudeById(id);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#getCompanyInfoByCondition(com.zjzmjr.core.model.company.CompanyInfoQuery)
     */
    @Override
    public ResultPage<CompanyInfoAptitude> getCompanyInfoByCondition(CompanyInfoQuery query) {
        ResultPage<CompanyInfoAptitude> result = new ResultPage<CompanyInfoAptitude>();
        try {
            result = companyInfoService.getCompanyInfoByCondition(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#insertCompanyInfo(com.zjzmjr.core.model.company.CompanyInfo)
     */
    @Override
    public ResultEntry<Integer> insertCompanyInfo(CompanyInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyInfoService.insertCompanyInfo(record);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#updateCompanyInfoById(com.zjzmjr.core.model.company.CompanyInfo)
     */
    @Override
    public ResultEntry<Integer> updateCompanyInfoById(CompanyInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyInfoService.updateCompanyInfoById(record);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#getWorkReportByCondition(com.zjzmjr.core.model.company.WorkReportQuery)
     */
    @Override
    public ResultPage<WorkReportInfo> getWorkReportByCondition(WorkReportQuery query) {
        ResultPage<WorkReportInfo> result = new ResultPage<WorkReportInfo>();
        try {
            result = workReportService.getWorkReportByCondition(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#insertWorkReport(com.zjzmjr.core.model.company.WorkReport)
     */
    @Override
    public ResultEntry<Integer> insertWorkReport(WorkReport record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = workReportService.insertWorkReport(record);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#updateWorkReportById(com.zjzmjr.core.model.company.WorkReport)
     */
    @Override
    public ResultEntry<Integer> updateWorkReportById(WorkReport record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = workReportService.updateWorkReportById(record);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#getCompanyAssociatedInfo(com.zjzmjr.core.model.company.CompanyAssociatedQuery)
     */
    @Override
    public ResultPage<CompanyAssociatedInfo> getCompanyAssociatedInfo(CompanyAssociatedQuery query) {
        ResultPage<CompanyAssociatedInfo> result = new ResultPage<CompanyAssociatedInfo>();
        try {
            result = companyAssociatedInfoService.getCompanyAssociatedInfo(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#insertCompanyAssociated(com.zjzmjr.core.model.company.CompanyAssociatedInfo)
     */
    @Override
    public ResultEntry<Integer> insertCompanyAssociated(CompanyAssociatedInfo companyAssociatedInfo) {
        ResultEntry<Integer>  result = new ResultEntry<Integer>();
        try {
            result = companyAssociatedInfoService.insertCompanyAssociated(companyAssociatedInfo);
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
     * @see com.zjzmjr.core.api.company.ICompanyInfoFacade#updateCompanyAssociatedById(com.zjzmjr.core.model.company.CompanyAssociatedInfo)
     */
    @Override
    public ResultEntry<Integer> updateCompanyAssociatedById(CompanyAssociatedInfo companyAssociatedInfo) {
        ResultEntry<Integer>  result = new ResultEntry<Integer>();
        try {
            result = companyAssociatedInfoService.updateCompanyAssociatedById(companyAssociatedInfo);
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
