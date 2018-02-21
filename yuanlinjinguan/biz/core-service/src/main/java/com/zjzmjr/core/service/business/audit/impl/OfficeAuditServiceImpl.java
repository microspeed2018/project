package com.zjzmjr.core.service.business.audit.impl;

import java.util.ArrayList;
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
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.OfficeAuditMapper;

@Service("officeAuditService")
public class OfficeAuditServiceImpl implements OfficeAuditService{

    private static final Logger logger = LoggerFactory.getLogger(OfficeAuditServiceImpl.class);

    @Resource(name = "officeAuditMapper")
    private OfficeAuditMapper officeAuditMapper;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.OfficeAuditService#getProjectOfficeAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectOfficeAudit> getProjectOfficeAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectOfficeAudit> result = new ResultPage<GardenProjectOfficeAudit>();
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
            total = officeAuditMapper.getProjectOfficeAuditCount(query);
            if (total > 0) {
                List<GardenProjectOfficeAudit> list = officeAuditMapper.getProjectOfficeAudit(query);
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
     * @see com.zjzmjr.core.service.business.audit.OfficeAuditService#insertOfficeAudit(com.zjzmjr.core.model.audit.OfficeAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertOfficeAudit(OfficeAudit officeAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(officeAudit) || Util.isNull(officeAudit.getCompanyId()) || Util.isNull(officeAudit.getStatus())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = officeAuditMapper.insertOfficeAudit(officeAudit);
        if(cnt > 0){
            result.setT(cnt);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":院办审核新增失败");
        }
        return result;
    }


    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.OfficeAuditService#getAuditBusiness(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<GardenProjectOfficeAudit> getAuditBusiness(GardenProjectAuditQuery query) {
        ResultEntry<GardenProjectOfficeAudit> result = new ResultEntry<GardenProjectOfficeAudit>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        GardenProjectOfficeAudit gardenProjectOfficeAudit= officeAuditMapper.getAuditBusiness(query);
        if(Util.isNotNull(gardenProjectOfficeAudit)){
            result.setT(gardenProjectOfficeAudit);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        return result;
    }


    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.OfficeAuditService#getProjectOfficeAuditByCondition(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultRecord<OfficeAudit> getProjectOfficeAuditByCondition(GardenProjectAuditQuery query) {
        ResultRecord<OfficeAudit> result = new ResultRecord<OfficeAudit>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        List<OfficeAudit> list = officeAuditMapper.getProjectOfficeAuditByCondition(query);
        if(list==null || list.size()==0){           
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setList(list);
            result.setSuccess(true);
        }
        return result;
    }


    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.OfficeAuditService#getProjectOfficeAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectOfficeAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
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
            total = officeAuditMapper.getProjectOfficeAuditCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }
    
}
