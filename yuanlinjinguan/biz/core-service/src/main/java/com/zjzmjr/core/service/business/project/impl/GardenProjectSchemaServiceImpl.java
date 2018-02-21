package com.zjzmjr.core.service.business.project.impl;

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
import com.zjzmjr.core.enums.project.ProjectSchemaStatusEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;
import com.zjzmjr.core.model.project.ProjectSchemaUserInfo;
import com.zjzmjr.core.service.business.project.GardenProjectSchemaService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.ManageAuditMapper;
import com.zjzmjr.core.service.mapper.dao.project.GardenProjectSchemaMapper;

/**
 * 项目方案表的业务处理层
 * 
 * @author oms
 * @version $Id: GardenProjectSchemaServiceImpl.java, v 0.1 2017-8-16 上午10:51:56
 *          oms Exp $
 */
@Service("gardenProjectSchemaService")
public class GardenProjectSchemaServiceImpl implements GardenProjectSchemaService {

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectSchemaServiceImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "gardenProjectSchemaMapper")
    private GardenProjectSchemaMapper gardenProjectSchemaMapper;
    
    @Resource(name = "manageAuditMapper")
    private ManageAuditMapper manageAuditMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#getGardenProjectSchemaByCondition(com.zjzmjr.core.model.project.ProjectSchemaQuery)
     */
    @Override
    public ResultPage<GardenProjectSchemaInfo> getGardenProjectSchemaByCondition(ProjectSchemaQuery query) {
        ResultPage<GardenProjectSchemaInfo> result = new ResultPage<GardenProjectSchemaInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = gardenProjectSchemaMapper.getGardenProjectSchemaCount(query);
        if (total > 0) {
            List<GardenProjectSchemaInfo> list = gardenProjectSchemaMapper.getGardenProjectSchemaByCondition(query);
            result.setSuccess(true);
            result.setList(list);
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
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#insertGardenProjectSchema(com.zjzmjr.core.model.project.GardenProjectSchema)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertGardenProjectSchema(GardenProjectSchema record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId()) || Util.isNull(record.getSchemeProposer())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = gardenProjectSchemaMapper.insertGardenProjectSchema(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目方案表插入失败");
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#updateGardenProjectSchemaById(com.zjzmjr.core.model.project.GardenProjectSchema)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateGardenProjectSchemaById(GardenProjectSchema record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        int total = gardenProjectSchemaMapper.updateGardenProjectSchemaById(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目方案表更新失败");
        }
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#insertGardenProjectSchema(java.util.List)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertGardenProjectSchema(GardenProject project, List<GardenProjectSchema> record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || record.isEmpty()) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = gardenProjectSchemaMapper.insertBatchGardenProjectSchema(record);
        if (total > 0) {
            result.setSuccess(true);
            result = gardenProjectService.updateGardenProjectById(project);
            if (!result.isSuccess()) {
                throw new ApplicationException("项目及方案操作失败");
            }
            result.setT(total);
            //更新经营审核项目立项
            //查询此项目有无未审核理想记录
            GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
            manageQuery.setCompanyId(project.getCompanyId());
            manageQuery.setProjectId(project.getId());
            manageQuery.setType(109);
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            List<GardenProjectManageAudit> manageLst = manageAuditMapper.getProjectManageAudit(manageQuery);
            if(!(manageLst==null  || manageLst.size()==0)){
                ManageAudit manage = new ManageAudit();
                manage.setId(manageLst.get(0).getId());
                manage.setStatus(ProjectTableStatusEnum.VERIFIED.getValue());
                manage.setUpdateTime(project.getUpdateTime());
                manage.setUpdateUserId(project.getUpdateUserId());
                int cnt = manageAuditMapper.updateManageAuditById(manage);
                if(cnt<=0){
                    throw new ApplicationException("项目立项审核是更新经营审核记录失败"); 
                }
            }
            
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目方案表批量插入处理失败");
        }
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#updateGardenProjectSchemaById(com.zjzmjr.core.model.project.GardenProjectSchema)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchema record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getProjectId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        // 根据项目编号查询所有的方案
        ProjectSchemaQuery query = new ProjectSchemaQuery();
        query.setCompanyId(record.getCompanyId());
        query.setProjectId(record.getProjectId());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectSchemaInfo> schemapage = getGardenProjectSchemaByCondition(query);
        int isAccepted = 0;
        if(schemapage.isSuccess()){
            isAccepted = schemapage.getList().get(0).getSchema().size();
            for(ProjectSchemaUserInfo info : schemapage.getList().get(0).getSchema()){
                // 判断是否是当前方案提议人, 是的话，就更新
                if(info.getSchemeProposer().equals(record.getSchemeProposer())){
                    // 主键
                    record.setId(info.getId());
                    result = updateGardenProjectSchemaById(record);
                    if (result.isSuccess()) {
                        info.setAccepted(ProjectSchemaStatusEnum.APPLIED.getValue());
                    } else {
                        // 失败跳出循环
                        break;
                    }
                }
                // 判断是否还有未
                if(ProjectSchemaStatusEnum.WAITING_APPLY.getValue().equals(info.getAccepted())){
                    isAccepted--;
                }
            }
            // 判断是否所有的人都已经回复了邀请立项
//            if((isAccepted == schemapage.getList().get(0).getSchema().size()) && result.isSuccess()){
                // 更新项目表
                GardenProject project = new GardenProject();
                project.setId(record.getProjectId());
                project.setStep(GardenProjectStepEnum.P_40.getValue());
                project.setUpdateTime(record.getUpdateTime());
                project.setUpdateUserId(record.getUpdateUserId());
                result = gardenProjectService.updateGardenProjectById(project);
                if (!result.isSuccess()) {
                    throw new ApplicationException("项目及方案操作失败");
                }
//            }
        } else {
            result.setSuccess(false);
            result.setErrorMsg(schemapage.getErrorMsg());
            result.setErrorCode(schemapage.getErrorCode());
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectSchemaService#updateGardenProjectSchema(com.zjzmjr.core.model.project.GardenProjectSchemaInfo)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchemaInfo schemaInfo) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(schemaInfo)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        // 根据项目编号查询所有的方案
        ProjectSchemaQuery query = new ProjectSchemaQuery();
        query.setCompanyId(schemaInfo.getCompanyId());
        query.setProjectId(schemaInfo.getId());
        query.setSchemeProposer(schemaInfo.getProjectLeader());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<GardenProjectSchemaInfo> schemapage = getGardenProjectSchemaByCondition(query);
        if (schemapage.isSuccess()) {
            GardenProjectSchema record = new GardenProjectSchema();
            record.setId(schemapage.getList().get(0).getSchema().get(0).getId());
            record.setAccepted(ProjectSchemaStatusEnum.ACCEPTED.getValue());
            record.setUpdateTime(schemaInfo.getUpdateTime());
            record.setUpdateUserId(schemaInfo.getUpdateUserId());
            result = updateGardenProjectSchemaById(record);
        } else {
            GardenProjectSchema record = new GardenProjectSchema();
            record.setCompanyId(schemaInfo.getCompanyId());
            record.setProjectId(schemaInfo.getId());
            record.setSchemeProposer(schemaInfo.getProjectLeader());
            record.setContent(schemaInfo.getSchemaContent());
            record.setAccepted(ProjectSchemaStatusEnum.ACCEPTED.getValue());
            record.setCreateTime(schemaInfo.getUpdateTime());
            record.setCreateUserId(schemaInfo.getUpdateUserId());
            record.setUpdateTime(schemaInfo.getUpdateTime());
            record.setUpdateUserId(schemaInfo.getUpdateUserId());
            result = insertGardenProjectSchema(record);
        }
        if(result.isSuccess()){
            result = gardenProjectService.updateGardenProjectById(schemaInfo);
            if (!result.isSuccess()) {
                throw new ApplicationException("项目及方案操作失败");
            }
        }
        
        return result;
    }

}
