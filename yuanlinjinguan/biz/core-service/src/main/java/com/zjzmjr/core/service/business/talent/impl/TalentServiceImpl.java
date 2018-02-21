package com.zjzmjr.core.service.business.talent.impl;

import java.util.ArrayList;
import java.util.Date;
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
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.AdminAccStatusEnum;
import com.zjzmjr.core.model.admin.Admin;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.talent.Talent;
import com.zjzmjr.core.model.talent.TalentDocument;
import com.zjzmjr.core.model.talent.TalentEducation;
import com.zjzmjr.core.model.talent.TalentFamily;
import com.zjzmjr.core.model.talent.TalentInfo;
import com.zjzmjr.core.model.talent.TalentJob;
import com.zjzmjr.core.model.talent.TalentQuery;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.service.business.admin.AdminService;
import com.zjzmjr.core.service.business.talent.TalentDocumentService;
import com.zjzmjr.core.service.business.talent.TalentEducationService;
import com.zjzmjr.core.service.business.talent.TalentFamilyService;
import com.zjzmjr.core.service.business.talent.TalentJobService;
import com.zjzmjr.core.service.business.talent.TalentService;
import com.zjzmjr.core.service.mapper.dao.talent.TalentMapper;


/**
 * 人才信息service
 * 
 * @author lenovo
 * @version $Id: TalentServiceImpl.java, v 0.1 2017-12-14 上午11:19:17 lenovo Exp $
 */
@Service("talentService")
public class TalentServiceImpl implements TalentService{

    private static final Logger logger = LoggerFactory.getLogger(TalentServiceImpl.class);

    /** 人才信息 */
    @Resource(name = "talentMapper")
    private TalentMapper talentMapper;
    
    @Resource(name = "talentEducationService")
    private TalentEducationService talentEducationService;
    
    @Resource(name = "talentJobService")
    private TalentJobService  talentJobService;
    
    @Resource(name = "talentFamilyService")
    private TalentFamilyService talentFamilyService;
    
    @Resource(name = "talentDocumentService")
    private TalentDocumentService  talentDocumentService;
    
    @Resource(name = "adminService")
    private AdminService adminService;

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.talent.TalentService#insertTalent(com.zjzmjr.core.model.talent.Talent)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<String> insertTalent(TalentInfo talent) throws ApplicationException {
        ResultEntry<String> result = new ResultEntry<String>();
        ResultEntry<Integer> insertRst = new ResultEntry<Integer>();
        if(Util.isNull(talent) || Util.isNull(talent.getCompanyId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        //判断手机号是否注册
        String regMsg = "";
        ResultEntry<Admin> userResult = adminService.getByMobile(talent.getMobile());
        if(userResult.isSuccess()){
            talent.setCreateUserId(userResult.getT().getId()); 
            regMsg = "欢迎回来！"+talent.getMobile();
        }else{
            regMsg = "您已自动注册成为会员，可用手机号码和初始密码(123456)登录平台查看或修改简历";
          //创建用户
            AdminCreate dto = new AdminCreate();
            dto.setCreateUser(0);
            dto.setAccStatus(AdminAccStatusEnum.getByValue(AdminAccStatusEnum.FROZEN.getValue()));
            dto.setCompanyId(talent.getCompanyId());
            dto.setName(StringUtil.null2Str(talent.getName()));
            dto.setPassword(StringUtils.trimToNull("123456"));
            dto.setUsername(StringUtils.trimToNull(talent.getMobile()));
            dto.setMobile(StringUtils.trimToNull(talent.getMobile()));
            CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
            ExternalPerson externalPerson = new ExternalPerson();
            externalPerson.setPersonnelNature(89);
            externalPerson.setMobile(talent.getMobile());
            externalPerson.setCreateTime(new Date());
            externalPerson.setCreateUserId(GenerateConstants.number_0);
            List<ExternalPerson> externalLst = new ArrayList<ExternalPerson>();
            externalLst.add(externalPerson);
            companyStaffPerson.setPerson(externalLst);
            ResultEntry<Integer> adminRst = adminService.create(dto, companyStaffPerson);
            if(adminRst.isSuccess()){
                talent.setCreateUserId(adminRst.getT()); 
            }
        }
        int cnt = talentMapper.insertTalent(talent);
        if(cnt>0){
            //新增人才信息成功，将获取的talentId更新到其余信息表中
            if(Util.isNotNull(talent.getTalentEducation())){
                TalentEducation education = new TalentEducation();
                for(int i=0;i<talent.getTalentEducation().size();i++){
                    education = talent.getTalentEducation().get(i);
                    education.setTalentId(talent.getId());
                    education.setCompanyId(talent.getCompanyId());
                    education.setCreateTime(talent.getCreateTime());
                    education.setCreateUserId(talent.getCreateUserId());
                    education.setUpdateTime(talent.getCreateTime());
                    education.setUpdateUserId(talent.getCreateUserId());
                    insertRst = talentEducationService.insertTalentEducation(education);
                    if(!insertRst.isSuccess()){
                        throw new ApplicationException("新增人才学历表操作失败"); 
                    }
                }               
            }
            if(Util.isNotNull(talent.getTalentFamily())){
                TalentFamily family = new TalentFamily();
                for(int i=0;i<talent.getTalentFamily().size();i++){
                    family = talent.getTalentFamily().get(i);
                    family.setTalentId(talent.getId());
                    family.setCompanyId(talent.getCompanyId());
                    family.setCreateTime(talent.getCreateTime());
                    family.setCreateUserId(talent.getCreateUserId());
                    family.setUpdateTime(talent.getCreateTime());
                    family.setUpdateUserId(talent.getCreateUserId());
                    insertRst = talentFamilyService.insertTalentFamily(family);
                    if(!insertRst.isSuccess()){
                        throw new ApplicationException("新增人才家属表操作失败"); 
                    }
                }      
            }
            if(Util.isNotNull(talent.getTalentJob())){
                TalentJob job = new TalentJob();
                for(int i=0;i<talent.getTalentJob().size();i++){
                    job = talent.getTalentJob().get(i);
                    job.setTalentId(talent.getId());
                    job.setCompanyId(talent.getCompanyId());
                    job.setCreateTime(talent.getCreateTime());
                    job.setCreateUserId(talent.getCreateUserId());
                    job.setUpdateTime(talent.getCreateTime());
                    job.setUpdateUserId(talent.getCreateUserId());
                    insertRst = talentJobService.insertTalentJob(job);
                    if(!insertRst.isSuccess()){
                        throw new ApplicationException("新增人才工作表操作失败"); 
                    }
                }   
                
            }
            if(Util.isNotNull(talent.getTalentDocument())){
                TalentDocument document = new TalentDocument();
                for(int i=0;i<talent.getTalentDocument().size();i++){
                    document = talent.getTalentDocument().get(i);
                    document.setTalentId(talent.getId());
                    document.setCompanyId(talent.getCompanyId());
                    document.setCreateTime(talent.getCreateTime());
                    document.setCreateUserId(talent.getCreateUserId());
                    document.setUpdateTime(talent.getCreateTime());
                    document.setUpdateUserId(talent.getCreateUserId());
                    insertRst = talentDocumentService.insertTalentDocument(document);
                    if(!insertRst.isSuccess()){
                        throw new ApplicationException("新增人才附件表操作失败"); 
                    }
                }   
            }
            result.setT(regMsg);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#updateTalent(com.zjzmjr.core.model.talent.Talent)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateTalent(Talent talent){
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(talent) || Util.isNull(talent.getCompanyId()) || Util.isNull(talent.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = talentMapper.updateTalent(talent);
        if(cnt>0){
            result.setT(cnt);
            result.setSuccess(true);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":人才信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#getTalentInfo(com.zjzmjr.core.model.talent.TalentQuery)
     */
    @Override
    public ResultRecord<TalentInfo> getTalentInfo(TalentQuery talentQuery) {
        ResultRecord<TalentInfo> result = new ResultRecord<TalentInfo>();
        if(Util.isNull(talentQuery)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;    
        }
        List<TalentInfo> list = talentMapper.getTalentInfo(talentQuery);
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

    /**
     * 
     * @see com.zjzmjr.core.service.business.talent.TalentService#getTalentByCondition(com.zjzmjr.core.model.talent.TalentQuery)
     */
    @Override
    public ResultPage<TalentInfo> getTalentByCondition(TalentQuery query) {
        ResultPage<TalentInfo> result = new ResultPage<TalentInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int total = talentMapper.getTalentInfoCount(query);
        if(total > 0){
            List<TalentInfo> list = talentMapper.getTalentByCondition(query);
            result.setList(list);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        result.setPage(new PageBean(total, query.getPageBean().getPageSize(), query.getPageBean().getCurrentPage()));
        return result;
    }

    
    
}
