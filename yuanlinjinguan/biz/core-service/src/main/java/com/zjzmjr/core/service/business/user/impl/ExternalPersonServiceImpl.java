package com.zjzmjr.core.service.business.user.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.user.ExternalPersonMapper;

/**
 * 公司外部员工处理的业务层
 * 
 * @author oms
 * @version $Id: ExternalPersonServiceImpl.java, v 0.1 2017-8-15 下午4:02:31 oms Exp $
 */
@Service("externalPersonService")
public class ExternalPersonServiceImpl implements ExternalPersonService {

    private static final Logger logger = LoggerFactory.getLogger(ExternalPersonServiceImpl.class);

    @Resource(name = "externalPersonMapper")
    private ExternalPersonMapper externalPersonMapper;
    
    @Resource(name = "adminDao")
    private AdminDao adminDao;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#getExternalPersonById(java.lang.Integer)
     */
    @Override
    public ResultEntry<ExternalPerson> getExternalPersonById(Integer id) {
        ResultEntry<ExternalPerson> result = new ResultEntry<ExternalPerson>();
        if (Util.isNull(id)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{getExternalPersonById}"));
            return result;
        }

        ExternalPerson person = externalPersonMapper.getExternalPersonById(id);
        if (person == null) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        } else {
            result.setSuccess(true);
            result.setT(person);
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#getExternalPersonByCondition(com.zjzmjr.core.model.user.ExternalPersonQuery)
     */
    @Override
    public ResultPage<ExternalPersonInfo> getExternalPersonByCondition(ExternalPersonQuery query) {
        ResultPage<ExternalPersonInfo> result = new ResultPage<ExternalPersonInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{getExternalPersonByCondition}"));
            return result;
        }

        int total = externalPersonMapper.getExternalPersonCount(query);
        if (total > 0) {
            List<ExternalPersonInfo> list = externalPersonMapper.getExternalPersonByCondition(query);
            result.setList(list);
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            logger.error(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#insertExternalPerson(com.zjzmjr.core.model.user.ExternalPerson)
     */
    @Override
    public ResultEntry<Integer> insertExternalPerson(ExternalPerson record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getName())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{insertExternalPerson}"));
            return result;
        }       
        int total = externalPersonMapper.insertExternalPerson(record);
        result.setT(record.getId());
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":插入外部员工信息表失败");
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#updateExternalPersonById(com.zjzmjr.core.model.user.ExternalPerson)
     */
    @Override
    public ResultEntry<Integer> updateExternalPersonById(ExternalPerson record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{updateExternalPersonById}"));
            return result;
        }
        
        int total = externalPersonMapper.updateExternalPersonById(record);
        result.setT(total);
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新外部员工信息表失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#updateExternalPersonAndAdmin(com.zjzmjr.core.model.user.ExternalPersonInfo)
     */
    @Override
    public ResultEntry<Integer> updateExternalPersonAndAdmin(ExternalPersonInfo externalPersonInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(externalPersonInfo) || Util.isNull(externalPersonInfo.getUserId()) || Util.isNull(externalPersonInfo.getUserInfo().getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        //查询手机号是否重复
        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setId(externalPersonInfo.getUserId());
        adminQuery.setMobile(externalPersonInfo.getMobile());
        Admin admin = adminDao.getRepeatByMobile(adminQuery);
        if(Util.isNull(admin)){       
            int cnt = externalPersonMapper.updateExternalPersonById(externalPersonInfo);
            if(cnt > 0){
                //用userId查询admin表数据
                cnt = adminDao.update(externalPersonInfo.getUserInfo());
                if(cnt <= 0){
                    throw new ApplicationException("管理员表更新失败");  
                }
                result.setSuccess(true);
                result.setT(cnt);
            }else{
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新外部员工信息表失败");
            }
        }else{
            result.setSuccess(false);
            result.setErrorMsg("此手机号已被他人注册!");
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":员工手机号已被使用");
        }       
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.user.ExternalPersonService#getExternalPersonEmployeeMaxNo()
     */
    @Override
    public ResultEntry<Integer> getExternalPersonEmployeeMaxNo() {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        String EmployeeMaxNo = externalPersonMapper.getExternalPersonEmployeeMaxNo();
        if (Util.isNull(EmployeeMaxNo)) {
            String formatedNo = String.format("%04d", 1);
            EmployeeMaxNo = "9"+formatedNo;
        } else {
            int calcProjectNo = StringUtil.nullToInteger(EmployeeMaxNo.substring(1)) + 1;
            String formatedNo = String.format("%04d", calcProjectNo);
            EmployeeMaxNo = EmployeeMaxNo.substring(0, 1).concat(formatedNo);
        }
        result.setT(StringUtil.stringToInteger(EmployeeMaxNo));
        result.setSuccess(true);
        return result;
    }
    
}
