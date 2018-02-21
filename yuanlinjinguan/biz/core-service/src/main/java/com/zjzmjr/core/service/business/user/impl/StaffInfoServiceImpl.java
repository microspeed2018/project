package com.zjzmjr.core.service.business.user.impl;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.user.ExternalPersonStatusEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.admin.AdminQuery;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.admin.AdminService;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.business.user.StaffInfoService;
import com.zjzmjr.core.service.mapper.dao.coredb.admin.AdminDao;
import com.zjzmjr.core.service.mapper.dao.user.StaffInfoMapper;

/**
 * 
 * 
 * @author oms
 * @version $Id: StaffInfoServiceImpl.java, v 0.1 2017-8-9 下午3:55:51 oms Exp $
 */
@Service("staffInfoService")
public class StaffInfoServiceImpl implements StaffInfoService{

    private static final Logger logger = LoggerFactory.getLogger(StaffInfoServiceImpl.class);

    @Resource(name = "staffInfoMapper")
    private StaffInfoMapper staffInfoMapper;
    
    @Resource(name = "adminDao")
    private AdminDao adminDao;
    
    @Resource(name = "externalPersonService")
    private ExternalPersonService externalPersonService;
    
    @Resource(name = "adminService")
    private AdminService adminService;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.StaffInfoService#getStaffInfoByCondition(com.zjzmjr.core.model.user.StaffInfoQuery)
     */
    @Override
    public ResultPage<StaffBasicInfo> getStaffInfoByCondition(StaffInfoQuery query) {
        ResultPage<StaffBasicInfo> result = new ResultPage<StaffBasicInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{getStaffInfoByCondition}"));
            return result;
        }

        int total = staffInfoMapper.getStaffInfoCount(query);
        if (total > 0) {
            List<StaffBasicInfo> list = staffInfoMapper.getStaffInfoByCondition(query);
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
     * @see com.zjzmjr.core.service.business.user.StaffInfoService#insertStaffInfo(com.zjzmjr.core.model.user.StaffInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertStaffInfo(StaffInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getUserId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{insertStaffInfo}"));
            return result;
        }
        
        int total = staffInfoMapper.insertStaffInfo(record);
        result.setT(total);
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":插入员工信息表失败");
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.user.StaffInfoService#updateStaffInfoById(com.zjzmjr.core.model.user.StaffInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateStaffInfoById(StaffInfo record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName().concat(":{updateStaffInfoById}"));
            return result;
        }
        int total = staffInfoMapper.updateStaffInfoById(record);
        result.setT(total);
        if (total > 0) {
            result.setSuccess(true);
        } else {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新员工信息表失败");
        }
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.user.StaffInfoService#updateStaffInfoAndAdmin(com.zjzmjr.core.model.user.StaffBasicInfo)
     */
    @Override
    public ResultEntry<Integer> updateStaffInfoAndAdmin(StaffBasicInfo staffBasicInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(staffBasicInfo) || Util.isNull(staffBasicInfo.getUserId()) || Util.isNull(staffBasicInfo.getUserInfo().getName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
      //查询手机号是否重复
        AdminQuery adminQuery = new AdminQuery();
        adminQuery.setId(staffBasicInfo.getUserId());
        adminQuery.setMobile(staffBasicInfo.getUserInfo().getMobile());
        Admin admin = adminDao.getRepeatByMobile(adminQuery);
        if(Util.isNull(admin)){
            int cnt = staffInfoMapper.updateStaffInfoById(staffBasicInfo);
            if(cnt > 0){
                //用userId查询admin表数据
                cnt = adminDao.update(staffBasicInfo.getUserInfo());
                if(cnt <= 0){
                    throw new ApplicationException("管理员表更新失败");  
                }
                result.setSuccess(true);
                result.setT(cnt);
            }else{
                result.setSuccess(false);
                result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
                result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":更新员工信息表失败");
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
     * @see com.zjzmjr.core.service.business.user.StaffInfoService#insertStaffInfoByTalent(com.zjzmjr.core.model.user.StaffBasicInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertStaffInfoByTalent(StaffBasicInfo staffBasicInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(staffBasicInfo) || Util.isNull(staffBasicInfo.getTelephone())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        //手机号查询外部员工信息，将外部员工设置为已入职，再新增内部员工信息
        ExternalPersonQuery externalPersonQuery = new ExternalPersonQuery();
        externalPersonQuery.setMobile(staffBasicInfo.getTelephone());
        externalPersonQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
        ResultPage<ExternalPersonInfo> exterResult = externalPersonService.getExternalPersonByCondition(externalPersonQuery);
        if(exterResult.isSuccess()){
           //将外部员工设置为已入职
            ExternalPerson externalPerson = new ExternalPerson();
            externalPerson.setId(exterResult.getList().get(0).getId());
            externalPerson.setMobile("已入职");
            externalPerson.setStatus(ExternalPersonStatusEnum.INTO_POST.getValue());
            externalPerson.setUpdateTime(staffBasicInfo.getCreateTime());
            externalPerson.setUpdateUserId(staffBasicInfo.getCreateUserId());
            result = externalPersonService.updateExternalPersonById(externalPerson);
            if(result.isSuccess()){
               //新增内部员工
                StaffInfo staffInfo = new StaffInfo();
                staffInfo.setUserId(exterResult.getList().get(0).getUserId());
                staffInfo.setEmail(staffBasicInfo.getEmail());
                staffInfo.setPresentAddress(staffBasicInfo.getPresentAddress());
                staffInfo.setTitleQuality(staffBasicInfo.getTitleQuality());
                staffInfo.setIdentityNo(staffBasicInfo.getIdentityNo());
                staffInfo.setSex(staffBasicInfo.getSex());
                staffInfo.setBirthday(staffBasicInfo.getBirthday());
                staffInfo.setJobStatus(staffBasicInfo.getJobStatus());
                staffInfo.setCreateTime(staffBasicInfo.getCreateTime());
                staffInfo.setCreateUserId(staffBasicInfo.getCreateUserId());
                result = insertStaffInfo(staffInfo);
                if(!result.isSuccess()){
                    throw new ApplicationException("新增内部员工出错!");
                }
            }    
        }else{
            //查询admin表是否有此人，有新增内部员工，无插入两张表
            Admin admin = adminDao.getByMobile(staffBasicInfo.getTelephone());
            if(Util.isNotNull(admin)){
              //新增内部员工
                StaffInfo staffInfo = new StaffInfo();
                staffInfo.setUserId(admin.getId());
                staffInfo.setEmail(staffBasicInfo.getEmail());
                staffInfo.setPresentAddress(staffBasicInfo.getPresentAddress());
                staffInfo.setTitleQuality(staffBasicInfo.getTitleQuality());
                staffInfo.setIdentityNo(staffBasicInfo.getIdentityNo());
                staffInfo.setSex(staffBasicInfo.getSex());
                staffInfo.setBirthday(staffBasicInfo.getBirthday());
                staffInfo.setCreateTime(staffBasicInfo.getCreateTime());
                staffInfo.setCreateUserId(staffBasicInfo.getCreateUserId());
                result = insertStaffInfo(staffInfo); 
            }else{
                //先新增admin表，在新增员工表
                //创建用户
                AdminCreate dto = new AdminCreate();
                dto.setCreateUser(staffBasicInfo.getCreateUserId());
                dto.setCompanyId(staffBasicInfo.getUserInfo().getCompanyId());
                dto.setName(StringUtil.null2Str(staffBasicInfo.getUserInfo().getName()));
                dto.setPassword(StringUtils.trimToNull("123456"));
                dto.setUsername(StringUtils.trimToNull(staffBasicInfo.getTelephone()));
                dto.setMobile(StringUtils.trimToNull(staffBasicInfo.getTelephone()));
                CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
                StaffBasicInfo  staffBasicInfos= new StaffBasicInfo();
                staffBasicInfos.setEmail(staffBasicInfo.getEmail());
                staffBasicInfos.setPresentAddress(staffBasicInfo.getPresentAddress());
                staffBasicInfos.setTitleQuality(staffBasicInfo.getTitleQuality());
                staffBasicInfos.setIdentityNo(staffBasicInfo.getIdentityNo());
                staffBasicInfos.setSex(staffBasicInfo.getSex());
                staffBasicInfos.setBirthday(staffBasicInfo.getBirthday());
                staffBasicInfos.setCreateTime(staffBasicInfo.getCreateTime());
                staffBasicInfos.setCreateUserId(staffBasicInfo.getCreateUserId());
                List<StaffBasicInfo> externalLst = new ArrayList<StaffBasicInfo>();
                externalLst.add(staffBasicInfos);
                companyStaffPerson.setStaff(externalLst);
                result = adminService.create(dto, companyStaffPerson);
            }
        }
                
        return result;
    }
    
}
