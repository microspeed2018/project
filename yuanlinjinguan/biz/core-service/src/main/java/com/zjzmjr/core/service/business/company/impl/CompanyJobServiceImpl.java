package com.zjzmjr.core.service.business.company.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.company.CompanyDepartmentJob;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyJob;
import com.zjzmjr.core.service.business.company.CompanyJobService;
import com.zjzmjr.core.service.mapper.dao.company.CompanyJobMapper;

@Service("companyJobService")
public class CompanyJobServiceImpl implements CompanyJobService{

    private static final Logger logger = LoggerFactory.getLogger(CompanyJobServiceImpl.class);

    @Resource(name = "companyJobMapper")
    private CompanyJobMapper companyJobMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyJobService#getDepartmentJobInfo(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultPage<CompanyDepartmentJob> getDepartmentJobInfo(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentJob> result = new ResultPage<CompanyDepartmentJob>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyJobMapper.getDepartmentJobInfoCount(query);
        if(total > 0){
            List<CompanyDepartmentJob> list = companyJobMapper.getDepartmentJobInfo(query);
            result.setSuccess(true);
            result.setList(list);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyJobService#getInnerOuterDepartmentInfo(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultPage<CompanyDepartmentJob> getInnerOuterDepartmentInfo(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentJob> result = new ResultPage<CompanyDepartmentJob>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyJobMapper.getInnerOuterDepartmentCount(query);
        if(total > 0){
            List<CompanyDepartmentJob> list = companyJobMapper.getInnerOuterDepartmentInfo(query);
            result.setSuccess(true);
            result.setList(list);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyJobService#insertCompanyJob(com.zjzmjr.core.model.company.CompanyJob)
     */
    @Override
    public ResultEntry<Integer> insertCompanyJob(CompanyJob comapnyJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(comapnyJob) || Util.isNull(comapnyJob.getDepartmentId()) || Util.isNull(comapnyJob.getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = companyJobMapper.insertCompanyJob(comapnyJob);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":职位表新增失败");
        }            
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyJobService#updateCompanyJobById(com.zjzmjr.core.model.company.CompanyJob)
     */
    @Override
    public ResultEntry<Integer> updateCompanyJobById(CompanyJob comapnyJob) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(comapnyJob) || Util.isNull(comapnyJob.getId()) ||
                Util.isNull(comapnyJob.getDepartmentId()) || Util.isNull(comapnyJob.getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = companyJobMapper.updateCompanyJobById(comapnyJob);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":职位表修改失败");
        }            
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyJobService#getCompanyJobByCondition(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultRecord<CompanyJob> getCompanyJobByCondition(CompanyDepartmentQuery query) {
        ResultRecord<CompanyJob> result = new ResultRecord<CompanyJob>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        List<CompanyJob> list = companyJobMapper.getCompanyJobByCondition(query);
        if(list == null || list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setSuccess(true);
            result.setList(list);
        }            
        return result;
    }
}
