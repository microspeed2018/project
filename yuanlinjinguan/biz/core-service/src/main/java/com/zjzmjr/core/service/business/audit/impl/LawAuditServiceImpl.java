package com.zjzmjr.core.service.business.audit.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

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
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectLawAudit;
import com.zjzmjr.core.model.audit.LawAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.LawAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.LawAuditMapper;

/**
 * 法务审核处理
 * 
 * @author lenovo
 * @version $Id: LawAuditServiceImpl.java, v 0.1 2017-9-4 下午5:22:28 lenovo Exp $
 */
@Service("lawAuditService")
public class LawAuditServiceImpl implements LawAuditService{

    private static final Logger logger = LoggerFactory.getLogger(LawAuditServiceImpl.class);

    @Resource(name = "lawAuditMapper")
    private LawAuditMapper lawAuditMapper;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;   

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.audit.LawAuditService#updateLawAuditById(com.zjzmjr.core.model.audit.LawAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateLawAudit(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        LawAudit law = new LawAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
        ResultPage<GardenProjectLawAudit> manageRst = null;
        law.setStatus(query.getStatus());
        law.setUpdateTime(new Date());
        law.setUpdateUserId(query.getUpdateUserId());
        law.setMemo(query.getMemo());
        if (Util.isNull(query.getId())) {
            // 通过 项目id 项目步骤 公司id查询
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectLawAudit(manageQuery);
            if (!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())) {
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            } else if (manageRst.isSuccess()) {
                law.setId(manageRst.getList().get(0).getId());
            }
        } else {
            law.setId(query.getId());
        }
        int cnt = lawAuditMapper.updateLawAuditById(law);
        if (cnt > 0) {
            if (ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())) {
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_340.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_350.getValue());
                        // 新增数据至院办审核表
                        OfficeAudit office = new OfficeAudit();
                        office.setCompanyId(query.getCompanyId());
                        office.setProjectId(query.getProjectId());
                        office.setApplicant(manageRst.getList().get(0).getApplicant());
                        office.setType(55);
                        office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        office.setCreateTime(new Date());
                        office.setCreateUserId(query.getUpdateUserId());
                        result = officeAuditService.insertOfficeAudit(office);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("法务审核时,院办审核表插入失败");
                        }
                    }
                }
            } else {
                if (Util.isNotNull(query.getStep())) {
                    if (GardenProjectStepEnum.P_340.getValue().equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_300.getValue());
                      
                    }
                }
            }
            // 更新项目进度
            project.setId(query.getProjectId());
            project.setUpdateUserId(query.getUpdateUserId());
            project.setUpdateTime(new Date());
            result = gardenProjectService.updateGardenProjectById(project);
            if (!result.isSuccess()) {
                throw new ApplicationException("经营审核时,项目进度更新失败");
            }
            result.setT(cnt);
            result.setSuccess(true);
        } else {
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":经营审核更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.LawAuditService#insertLawAudit(com.zjzmjr.core.model.audit.LawAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertLawAudit(LawAudit lawAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(lawAudit)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = lawAuditMapper.insertLawAudit(lawAudit);
        if(cnt > 0){
            result.setT(cnt);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.LawAuditService#getProjectLawAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectLawAudit> getProjectLawAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectLawAudit> result = new ResultPage<GardenProjectLawAudit>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = 0;
        GardenProjectQuery gQuery = new GardenProjectQuery();
        gQuery.setCompanyId(query.getCompanyId());
        gQuery.setProjectNo(query.getProjectNo());
        gQuery.setUserId(query.getUpdateUserId());
        gQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(gQuery);
        if (projectRst.isSuccess()) {
            List<Integer> projectLst = new ArrayList<Integer>();
            for (GardenProjectInfo projectInfo : projectRst.getList()) {
                projectLst.add(projectInfo.getId());
            }
            query.setProjectLst(projectLst);
            total = lawAuditMapper.getProjectLawAuditCount(query);
            if (total > 0) {
                List<GardenProjectLawAudit> list = lawAuditMapper.getProjectLawAudit(query);
                result.setList(list);
                result.setSuccess(true);
            } else {
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            }
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
     * @see com.zjzmjr.core.service.business.audit.LawAuditService#getProjectLawAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectLawAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = 0;
        GardenProjectQuery gQuery = new GardenProjectQuery();
        gQuery.setCompanyId(query.getCompanyId());
        gQuery.setProjectNo(query.getProjectNo());
        gQuery.setUserId(query.getUpdateUserId());
        gQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectInfo> projectRst = gardenProjectService.getGardenProjectByCondition(gQuery);
        if (projectRst.isSuccess()) {
            List<Integer> projectLst = new ArrayList<Integer>();
            for (GardenProjectInfo projectInfo : projectRst.getList()) {
                projectLst.add(projectInfo.getId());
            }
            query.setProjectLst(projectLst);
            total = lawAuditMapper.getProjectLawAuditCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }
}
