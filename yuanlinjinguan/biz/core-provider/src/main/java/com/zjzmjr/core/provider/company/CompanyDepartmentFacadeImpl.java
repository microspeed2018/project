package com.zjzmjr.core.provider.company;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.company.ICompanyDepartmentFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;
import com.zjzmjr.core.model.company.CompanyJob;
import com.zjzmjr.core.service.business.company.CompanyDepartmentService;
import com.zjzmjr.core.service.business.company.CompanyJobService;

@Service("companyDepartmentFacade")
public class CompanyDepartmentFacadeImpl implements ICompanyDepartmentFacade{

    private static final Logger logger = LoggerFactory.getLogger(CompanyDepartmentFacadeImpl.class);

    @Resource(name = "companyDepartmentService")
    private CompanyDepartmentService  companyDepartmentService;
    
    @Resource(name = "companyJobService")
    private CompanyJobService  companyJobService;

   /**
    * 
    * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#insertCompanyDepartment(com.zjzmjr.core.model.company.CompanyDepartment)
    */
    @Override
    public ResultEntry<Integer> insertCompanyDepartment(CompanyDepartment companyDepartment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyDepartmentService.insertCompanyDepartment(companyDepartment);
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
    * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getDepartmentStaff(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
    */
    @Override
    public ResultPage<CompanyDepartmentStaff> getDepartmentStaff(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentStaff> result = new ResultPage<CompanyDepartmentStaff>();
        try {
            result = companyDepartmentService.getDepartmentStaff(query);
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
    * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#updateCompanyDepartmentById(com.zjzmjr.core.model.company.CompanyDepartment)
    */
    @Override
    public ResultEntry<Integer> updateCompanyDepartmentById(CompanyDepartment companyDepartment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyDepartmentService.updateCompanyDepartmentById(companyDepartment);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getDepartmentJobInfo(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultPage<CompanyDepartmentJob> getDepartmentJobInfo(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentJob> result = new ResultPage<CompanyDepartmentJob>();
        try {
            result = companyJobService.getDepartmentJobInfo(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getInnerOuterDepartmentInfo(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultPage<CompanyDepartmentJob> getInnerOuterDepartmentInfo(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentJob> result = new ResultPage<CompanyDepartmentJob>();
        try {
            result = companyJobService.getInnerOuterDepartmentInfo(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#insertCompanyJob(com.zjzmjr.core.model.company.CompanyJob)
     */
    @Override
    public ResultEntry<Integer> insertCompanyJob(CompanyJob comapnyJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyJobService.insertCompanyJob(comapnyJob);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#updateCompanyJobById(com.zjzmjr.core.model.company.CompanyJob)
     */
    @Override
    public ResultEntry<Integer> updateCompanyJobById(CompanyJob comapnyJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = companyJobService.updateCompanyJobById(comapnyJob);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getCompanyDepartmentByCondition(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultRecord<CompanyDepartment> getCompanyDepartmentByCondition(CompanyDepartmentQuery query) {
        ResultRecord<CompanyDepartment> result = new ResultRecord<CompanyDepartment>();
        try {
            result = companyDepartmentService.getCompanyDepartmentByCondition(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getCompanyJobByCondition(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultRecord<CompanyJob> getCompanyJobByCondition(CompanyDepartmentQuery query) {
        ResultRecord<CompanyJob> result = new ResultRecord<CompanyJob>();
        try {
            result = companyJobService.getCompanyJobByCondition(query);
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
     * @see com.zjzmjr.core.api.company.ICompanyDepartmentFacade#getStaffDepartmentAndCount(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultRecord<CompanyDepartment> getStaffDepartmentAndCount(CompanyDepartmentQuery query) {
        ResultRecord<CompanyDepartment> result = new ResultRecord<CompanyDepartment>();
        try {
            result = companyDepartmentService.getStaffDepartmentAndCount(query);
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
