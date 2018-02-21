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
import com.zjzmjr.core.model.company.CompanyDepartment;
import com.zjzmjr.core.model.company.CompanyDepartmentQuery;
import com.zjzmjr.core.model.company.CompanyDepartmentStaff;
import com.zjzmjr.core.service.business.company.CompanyDepartmentService;
import com.zjzmjr.core.service.mapper.dao.company.CompanyDepartmentMapper;

@Service("companyDepartmentService")
public class CompanyDepartmentServiceImpl implements CompanyDepartmentService{

    private static final Logger logger = LoggerFactory.getLogger(CompanyDepartmentServiceImpl.class);

    @Resource(name = "companyDepartmentMapper")
    private CompanyDepartmentMapper  companyDepartmentMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyDepartmentService#insertCompanyDepartment(com.zjzmjr.core.model.company.CompanyDepartment)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertCompanyDepartment(CompanyDepartment companyDepartment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(companyDepartment) || Util.isNull(companyDepartment.getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = companyDepartmentMapper.insertCompanyDepartment(companyDepartment);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":部门表插入失败");
        }            
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyDepartmentService#getDepartmentStaff(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultPage<CompanyDepartmentStaff> getDepartmentStaff(CompanyDepartmentQuery query) {
        ResultPage<CompanyDepartmentStaff> result = new ResultPage<CompanyDepartmentStaff>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        int total = companyDepartmentMapper.getDepartmentStaffCount(query);
        if(total > 0){
            List<CompanyDepartmentStaff> list = companyDepartmentMapper.getDepartmentStaff(query);
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
     * @see com.zjzmjr.core.service.business.company.CompanyDepartmentService#updateCompanyDepartmentById(com.zjzmjr.core.model.company.CompanyDepartment)
     */
    @Override
    public ResultEntry<Integer> updateCompanyDepartmentById(CompanyDepartment companyDepartment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(companyDepartment) || Util.isNull(companyDepartment.getId()) || Util.isNull(companyDepartment.getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = companyDepartmentMapper.updateCompanyDepartmentById(companyDepartment);
        if(cnt > 0){           
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":部门表更新失败");
        }            
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyDepartmentService#getCompanyDepartmentByCondition(com.zjzmjr.core.model.company.CompanyDepartmentQuery)
     */
    @Override
    public ResultRecord<CompanyDepartment> getCompanyDepartmentByCondition(CompanyDepartmentQuery query) {
        ResultRecord<CompanyDepartment> result = new ResultRecord<CompanyDepartment>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        List<CompanyDepartment> list = companyDepartmentMapper.getCompanyDepartmentByCondition(query);
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

    /**
     * 
     * @see com.zjzmjr.core.service.business.company.CompanyDepartmentService#getStaffDepartmentAndCount()
     */
    @Override
    public ResultRecord<CompanyDepartment> getStaffDepartmentAndCount(CompanyDepartmentQuery query) {
        ResultRecord<CompanyDepartment> result = new ResultRecord<CompanyDepartment>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        List<CompanyDepartment> list = companyDepartmentMapper.getStaffDepartmentAndCount(query);
        if(list==null || list.size()==0){
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
