package com.zjzmjr.core.service.business.project.impl;

import java.util.ArrayList;
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
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.PropertyUtils;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.project.GardenProjectStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.ManageAudit;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectDisplayRule;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectDisplayRuleService;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.business.user.StaffInfoService;
import com.zjzmjr.core.service.mapper.dao.audit.FinancialAuditMapper;
import com.zjzmjr.core.service.mapper.dao.audit.ManageAuditMapper;
import com.zjzmjr.core.service.mapper.dao.project.GardenProjectMapper;
import com.zjzmjr.core.service.mapper.dao.project.GardenProjectSchemaMapper;


/**
 * 
 * 
 * @author oms
 * @version $Id: GardenProjectServiceImpl.java, v 0.1 2017-8-13 下午9:10:21 oms Exp $
 */
@Service("gardenProjectService")
public class GardenProjectServiceImpl implements GardenProjectService{

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectServiceImpl.class);

    @Resource(name = "gardenProjectMapper")
    private GardenProjectMapper gardenProjectMapper;

    @Resource(name = "gardenProjectSchemaMapper")
    private GardenProjectSchemaMapper gardenProjectSchemaMapper;

    /**
     * 项目一览展示规则
     */
    @Resource(name = "projectDisplayRuleService")
    private ProjectDisplayRuleService projectDisplayRuleService;

    /**
     * 内部人员
     */
    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    /**
     * 外部人员
     */
    @Resource(name = "externalPersonService")
    private ExternalPersonService externalPersonService;
    
    /**经营审核*/
    @Resource(name = "manageAuditMapper")
    private ManageAuditMapper manageAuditMapper;
    
    /**财务审核*/
    @Resource(name = "financialAuditMapper")
    private FinancialAuditMapper financialAuditMapper;

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getGardenProjectByCondition(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultPage<GardenProjectInfo> getGardenProjectByCondition(GardenProjectQuery query) {
        ResultPage<GardenProjectInfo> result = new ResultPage<GardenProjectInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        // 查询用户展示的规则
        ResultEntry<ProjectDisplayRule> ruleRst = projectDisplayRuleService.getProjectDisplayRuleByUserId(query.getUserId());
        if (ruleRst.isSuccess()) {
            ProjectDisplayRule rule = ruleRst.getT();
            // if(GenerateConstants.number_1.equals(rule.getTimeOrder())){
            // // 倒序
            // query.setOrderBy(" order by  zp.update_time DESC");
            // }else if(GenerateConstants.number_2.equals(rule.getTimeOrder())){
            // // 升序
            // query.setOrderBy(" order by  zp.update_time ");
            // }

            if (Util.isNotNull(query.getSourceType())) {
                query.setOrderBy(String.valueOf(GenerateConstants.number_3));
            } else {
                query.setOrderBy(String.valueOf(rule.getTimeOrder()));
            }
            if (Util.isNotNull(rule.getProjectLeader())) {
                query.setProjectLeader(rule.getProjectLeader());
            }
            if (Util.isNotNull(rule.getManagePerson())) {
                query.setManagementPersonnel(rule.getManagePerson());
            }
            if (Util.isNotNull(rule.getProjectStep())) {
                query.setStepLst(rule.getProjectStep());
            }
            if (Util.isNotNull(rule.getStatus())) {
                query.setStatus(rule.getStatus());
            }
        }else if(Util.isNotNull(query.getSourceType())){
            query.setOrderBy(String.valueOf(GenerateConstants.number_3)); 
        }
        // 整合项目一览的条件
        int total = gardenProjectMapper.getGardenProjectCount(query);
        if(total > 0){
            List<GardenProjectInfo> list = null;
            if (Util.isNull(query.getUserId())) {
                list = gardenProjectMapper.getGardenProjectByCondition(query);
            } else {
                PageBean pageBean = query.getPageBean();
                query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                List<GardenProjectInfo> calateLst = gardenProjectMapper.getGardenProjectByCondition(query);
                query.setPageBean(new PageBean(total, pageBean.getPageSize(), pageBean.getCurrentPage()));
                list = arrangeProjectCondition(calateLst, query);
                if(list.size() < query.getPageBean().getPageSize()){
                    query.getPageBean().setCurrentPage(query.getPageBean().getTotalPages());
                }
                total = calateLst.size();
            }
            if (list == null || list.isEmpty()) {
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
                result.setSuccess(false);
            } else {
                result.setSuccess(true);
                result.setList(list);
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
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getGardenProjectMaxNo()
     */
    @Override
    public ResultEntry<String> getGardenProjectMaxNo() {
        ResultEntry<String> result = new ResultEntry<String>();
        String projectMaxNo = gardenProjectMapper.getGardenProjectMaxNo();
        if (Util.isNull(projectMaxNo)) {
            String formatedNo = String.format("%07d", 1);
            result.setT("P".concat(formatedNo));
        } else {
            int calcProjectNo = StringUtil.nullToInteger(projectMaxNo.substring(1)) + 1;
            String formatedNo = String.format("%07d", calcProjectNo);
            result.setT(projectMaxNo.substring(0, 1).concat(formatedNo));
        }
        result.setSuccess(true);
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#insertGardenProject(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertGardenProject(GardenProject record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getCompanyId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }

        GardenProjectQuery query = new GardenProjectQuery();
        query.setCompanyId(record.getCompanyId());
        query.setProjectNo(record.getProjectNo());
        query.setId(record.getId());
        query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        List<GardenProjectInfo> list = gardenProjectMapper.getGardenProjectByCondition(query);
        if(list == null || list.isEmpty()){
            // 第一次提交上来的时候
//            // 获取最大的项目编号值
//            record.setProjectNo(getGardenProjectMaxNo().getT());
            int total = gardenProjectMapper.insertGardenProject(record);
            if(total > 0){                
                //当项目状态为20时，新增一条立项经营审核记录
                if(GardenProjectStepEnum.P_20.getValue().equals(record.getStep())){
                    // 经营审核插入数据
                    ManageAudit manage = new ManageAudit();
                    manage.setCompanyId(record.getCompanyId());
                    manage.setProjectId(record.getId());
                    manage.setApplicant(record.getCreateUserId());
                    manage.setType(109);
                    manage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    manage.setCreateTime(record.getUpdateTime());
                    manage.setCreateUserId(record.getUpdateUserId());
                    manage.setUpdateTime(record.getUpdateTime());
                    manage.setUpdateUserId(record.getUpdateUserId());
                    int cnt = manageAuditMapper.insertManageAudit(manage);
                    if (cnt > 0) {
                        result.setSuccess(true);
                        result.setT(record.getId());
                    } else {
                        throw new ApplicationException("新增项目立项时，插入经营审核表失败");
                    }
                }else{
                    result.setSuccess(true);
                    result.setT(record.getId()); 
                }
            } else {
                result.setSuccess(false);
                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表插入失败");
            }
        }else{
            record.setId(list.get(0).getId());
            result = updateGardenProjectById(record);
            result.setT(record.getId());
        }
        
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#updateGardenProjectById(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateGardenProjectById(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }        
        int total = gardenProjectMapper.updateGardenProjectById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表更新失败");
        }
        return result;
    }
    
    /**
     * 整合根据人员的角色和权限查本人能查看的项目一览
     * 
     * @param query
     * @return
     */
    private List<GardenProjectInfo> arrangeProjectCondition(List<GardenProjectInfo> infoLst, GardenProjectQuery query) {
        if (Util.isNull(query.getUserId())) {
            // 不存在需要根据人员查询的情况下
            return infoLst;
        }
        List<GardenProjectInfo> result = new ArrayList<GardenProjectInfo>();
        List<GardenProjectInfo> searchLst = new ArrayList<GardenProjectInfo>(infoLst);
        boolean isDelete = true;
        boolean isLeader = false;
        boolean isSpecialManage = true;
        // 设计总监
        StaffInfoQuery staffquery = new StaffInfoQuery();
        staffquery.setUserId(query.getUserId());
        staffquery.setCompanyId(query.getCompanyId());
        staffquery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
        ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(staffquery);
        if (staffRst.isSuccess()) {
            switch (staffRst.getList().get(0).getUserInfo().getJobId()) {
            case 5:
            case 6:
                isLeader = true;
                break;
            case 4:
                // 经营专员
                //读取配置文件里的经营内勤id，如果包含query.getUserId，则读取全部条数
                if(Util.isNotNull(query.getMobile())){
                    String speialManageId = PropertyUtils.getPropertyFromFile("commBiz.properties", "special_manage_mobile");
                    String[] speialManageIds = speialManageId.split(",");
                    for(int i=0;i<speialManageIds.length;i++){
                        if(Util.isNotNull(query.getUserId()) && query.getUserId().toString().equals(speialManageIds[i])){
                           result = infoLst;
                           isSpecialManage = false;
                        }
                    } 
                }
                if (isSpecialManage) {
                    for (GardenProjectInfo projectInfo : searchLst) {
                        if (query.getUserId().equals(projectInfo.getManagementPersonnel()) || (GardenProjectStepEnum.P_20.getValue().compareTo(projectInfo.getStep()) >= 0 && query.getUserId().equals(projectInfo.getCreateUserId()))) {
                            result.add(projectInfo);
                            // if(result.size() >=
                            // query.getPageBean().getPageSize()){
                            // break;
                            // }
                        } else {
                            // 将无用的数据删除
                            infoLst.remove(projectInfo);
                        }
                    }
                }
                // 计算从第几页开始展示, 
                if (result.size() > query.getPageBean().getPageSize()) {
                    int pageRows = query.getPageBean().getOffset() + query.getPageBean().getPageSize();
                    if (pageRows > result.size()) {
                        pageRows = result.size();
                    }
                    result = result.subList(query.getPageBean().getOffset(), pageRows);
                }
                return result;
            }
        }

        if(!isLeader){
            // 外部人员
            ExternalPersonQuery personquery = new ExternalPersonQuery();
            personquery.setUserId(query.getUserId());
            personquery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<ExternalPersonInfo> personRst = externalPersonService.getExternalPersonByCondition(personquery);
            if (personRst.isSuccess()) {
                switch (personRst.getList().get(0).getPersonnelNature()) {
                case 44:
                    isLeader = true;
                    break;
                }
            }
        }
        // 把负责人负责的项目检索出来
        if (isLeader) {
            // 查询是否被邀请的人
            ProjectSchemaQuery schemaQuery = new ProjectSchemaQuery();
            schemaQuery.setCompanyId(query.getCompanyId());
            schemaQuery.setSchemeProposer(query.getUserId());
            schemaQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            List<GardenProjectSchemaInfo> list = gardenProjectSchemaMapper.getGardenProjectSchemaByCondition(schemaQuery);
            // 将无效的数据删除
            for (GardenProjectInfo projectInfo : searchLst) {
                isDelete = true;
                if (query.getUserId().equals(projectInfo.getProjectLeader())) {
                    isDelete = false;
                }else{
                    if (list == null || list.isEmpty()) {
                    } else {
                        for (GardenProjectSchemaInfo schema : list) {
                            if (projectInfo.getId().equals(schema.getId()) && projectInfo.getStep().compareTo(50) < 0) {
                                isDelete = false;
                                break;
                            }
                        }
                    }
                }
                if(isDelete){
                    infoLst.remove(projectInfo);
                }
            }
            // 计算从第几页开始展示, 
            if (infoLst.size() > query.getPageBean().getPageSize() && query.getPageBean().getOffset() > 0) {
                int pageRows = query.getPageBean().getOffset();
                if (pageRows > infoLst.size()) {
                    pageRows = infoLst.size();
                }
                infoLst = infoLst.subList(pageRows, infoLst.size());
            }
            for (GardenProjectInfo projectInfo : infoLst) {
                if (query.getUserId().equals(projectInfo.getProjectLeader())) {
                    if(result.size() >= query.getPageBean().getPageSize()){
                        break;
                    }
                    result.add(projectInfo);
                }else{
                    if (list == null || list.isEmpty()) {
                    } else {
                        for (GardenProjectSchemaInfo schema : list) {
                            if (projectInfo.getId().equals(schema.getId()) && projectInfo.getStep().compareTo(50) < 0) {
                                if(result.size() >= query.getPageBean().getPageSize()){
                                    return result;
                                }
                                result.add(projectInfo);
                            }
                        }
                    }
                }
            }
        } else {
            // 设计类职位以外的人，查看全部的
            if (infoLst.size() <= query.getPageBean().getPageSize()) {
                result = infoLst;
            } else {
                int pageRows = query.getPageBean().getOffset() + query.getPageBean().getPageSize();
                if (pageRows > infoLst.size()) {
                    pageRows = infoLst.size();
                }
                result.addAll(infoLst.subList(query.getPageBean().getOffset(), pageRows));                
            }
        }

        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#updateGardenProject(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateGardenProject(GardenProject record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId()) || Util.isNull(record.getCompanyId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        //根据项目id查询项目信息比较版本号
        GardenProjectQuery query = new GardenProjectQuery();
        query.setId(record.getId());
        ResultEntry<GardenProject> projectRst = getGardenProjectByIdAndStatus(query);
        if(projectRst.isSuccess()){
            if(projectRst.getT().getVersion().equals(record.getVersion())){
                GardenProject project = new GardenProject();
                if (GardenProjectStepEnum.P_10.getValue().equals(record.getStep()) || GardenProjectStepEnum.P_20.getValue().equals(record.getStep())) {
                    // 项目状态为10和20时，直接修改不需审核
                    if(Util.isNull(record.getOperation())){
                        record.setStep(GardenProjectStepEnum.P_20.getValue());
                    }else{
                        record.setStep(GardenProjectStepEnum.P_10.getValue());
                    }
                    int cnt = gardenProjectMapper.updateGardenProjectById(record);
                    if (cnt > 0) {
                        //当项目状态为20时，新增一条立项经营审核记录
                        if(GardenProjectStepEnum.P_20.getValue().equals(record.getStep())){
                            //查询此项目有无未审核理想记录
                            GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
                            manageQuery.setCompanyId(record.getCompanyId());
                            manageQuery.setProjectId(record.getId());
                            manageQuery.setType(109);
                            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
                            List<GardenProjectManageAudit> manageLst= manageAuditMapper.getProjectManageAudit(manageQuery);
                            if(manageLst==null  || manageLst.size()==0){
                                // 经营审核插入数据
                                ManageAudit manage = new ManageAudit();
                                manage.setCompanyId(record.getCompanyId());
                                manage.setProjectId(record.getId());
                                manage.setApplicant(record.getUpdateUserId());
                                manage.setType(109);
                                manage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                                manage.setCreateTime(record.getUpdateTime());
                                manage.setCreateUserId(record.getUpdateUserId());
                                manage.setUpdateTime(record.getUpdateTime());
                                manage.setUpdateUserId(record.getUpdateUserId());
                                cnt = manageAuditMapper.insertManageAudit(manage);
                                if (cnt > 0) {
                                    result.setSuccess(true);
                                    result.setT(record.getId());
                                } else {
                                    throw new ApplicationException("修改项目立项时，插入经营审核表失败");
                                }
                            }else{
                                result.setSuccess(true);
                                result.setT(record.getId());  
                            }                          
                        }else{
                            result.setSuccess(true);
                            result.setT(record.getId()); 
                        }
                        
                    } else {
                        result.setSuccess(false);
                        result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                        logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表更新失败");
                    }
                } else {
                    if (GenerateConstants.number_1.equals(record.getIsUpdate())) {
                         if(Util.isNotNull(record.getTemporaryId())){
                           //直接更新临时数据
                             record.setId(record.getTemporaryId());
                            int cnt = gardenProjectMapper.updateGardenProjectById(record); 
                            if (cnt > 0) {
                                result.setSuccess(true);
                                result.setT(cnt);
                            } else {
                                result.setSuccess(false);
                                result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                                logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表更新失败");
                            }
                         }else{
                          // 先插入修改后的项目
                             project.setId(record.getId());
                             record.setId(null);
                             record.setStatus(GardenProjectStatusEnum.TEMPORARY.getValue());
                             // 新增一条项目数据待审核
                             record.setCreateUserId(record.getUpdateUserId());
                             record.setCreateTime(record.getUpdateTime());
                             int cnt = gardenProjectMapper.insertGardenProject(record);
                             if (cnt > 0) {
                                 // 更新原来的项目并且memo赋值为新增临时项目id
                                 project.setMemo(record.getId().toString());
                                 project.setUpdateTime(record.getUpdateTime());
                                 project.setUpdateUserId(record.getUpdateUserId());
                                 cnt = gardenProjectMapper.updateGardenProjectById(project);
                                 if (cnt > 0) {
                                     // 经营审核插入数据
                                     ManageAudit manage = new ManageAudit();
                                     manage.setCompanyId(record.getCompanyId());
                                     manage.setProjectId(project.getId());
                                     manage.setApplicant(record.getUpdateUserId());
                                     manage.setType(50);
                                     manage.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                                     manage.setCreateTime(record.getUpdateTime());
                                     manage.setCreateUserId(record.getUpdateUserId());
                                     manage.setUpdateTime(record.getUpdateTime());
                                     manage.setUpdateUserId(record.getUpdateUserId());
                                     cnt = manageAuditMapper.insertManageAudit(manage);
                                     if (cnt > 0) {
                                         result.setSuccess(true);
                                         result.setT(cnt);
                                     } else {
                                         throw new ApplicationException("修改项目时，插入经营审核表失败");
                                     }
                                 } else {
                                     throw new ApplicationException("修改原有项目失败");
                                 }
                             } else {
                                 result.setSuccess(false);
                                 result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
                                 logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表更新时，新增临时数据失败");
                             } 
                         }                       
                    }
                }
            }else{
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_CHANGE.getName());
                result.setSuccess(false);   
            }
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }
        
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#deleteGardenProject(java.lang.Integer)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> deleteGardenProject(Integer id) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(id)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = gardenProjectMapper.deleteGardenProjectById(id);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表删除失败"); 
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#insertProjectByContract(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertProjectByContract(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getCompanyId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = gardenProjectMapper.insertGardenProject(record);
        if(cnt > 0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表新增失败"); 
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getGardenProjectByIdAndStatus(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultEntry<GardenProject> getGardenProjectByIdAndStatus(GardenProjectQuery query) {
        ResultEntry<GardenProject> result = new ResultEntry<GardenProject>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        GardenProject project = gardenProjectMapper.getGardenProjectByIdAndStatus(query);
        if(Util.isNotNull(project)){
            result.setSuccess(true);
            result.setT(project);
        }else{
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false); 
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getProjectDetail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultEntry<GardenProjectInfo> getProjectDetail(GardenProjectQuery query) {
        ResultEntry<GardenProjectInfo> result = new ResultEntry<GardenProjectInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        GardenProjectInfo projectInfo = gardenProjectMapper.getProjectDetail(query);
        if(Util.isNotNull(projectInfo)){
            result.setT(projectInfo);
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
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getGardenProjectNoContract()
     */
    @Override
    public ResultRecord<GardenProject> getGardenProjectNoContract() {
        ResultRecord<GardenProject> result = new ResultRecord<GardenProject>();
        List<GardenProject> list = gardenProjectMapper.getGardenProjectNoContract();
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#applyBackBail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> applyBackBail(GardenProjectQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        //查询此数据原有的未退保证金金额
        GardenProjectQuery projectQuery = new GardenProjectQuery();
        projectQuery.setId(query.getId());
        ResultEntry<GardenProject> projectRst =  getGardenProjectByIdAndStatus(projectQuery);
        if(projectRst.isSuccess()){
            if(Util.isNotNull(projectRst.getT().getNobackBail())){
                if(Util.isNotNull(query.getNobackBail())){
                    if(projectRst.getT().getNobackBail().compareTo(query.getNobackBail())<0){
                        result.setSuccess(false);
                        result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                        result.setErrorMsg("申请保证金退款多于未退保证金金额!");  
                    }else{
                        GardenProject gardenProject = new GardenProject();
                        gardenProject.setId(query.getId());
                        gardenProject.setNobackBail(projectRst.getT().getNobackBail().subtract(query.getNobackBail()));
                        gardenProject.setUpdateTime(query.getUpdateTime());
                        gardenProject.setUpdateUserId(query.getUpdateUserId());
                        result = updateGardenProjectById(gardenProject);
                        if(result.isSuccess()){
                            //添加财务审核
                            FinancialAudit audit = new FinancialAudit();
                            audit.setCompanyId(query.getCompanyId());
                            audit.setProjectId(query.getId());
                            // 申请人
                            audit.setApplicant(query.getUpdateUserId());
                            // 退保证金金额
                            audit.setAmount(query.getNobackBail());                           
                            // 财务审核类型  86:退保证金
                            audit.setType(query.getBailType());
                            audit.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                            audit.setCreateTime(query.getUpdateTime());
                            audit.setCreateUserId(query.getUpdateUserId());
                            int cnt = financialAuditMapper.insertFinancialAudit(audit);
                            if(cnt>0){
                                result.setSuccess(true);
                                result.setT(cnt);
                            }else{
                                throw new ApplicationException("申请退保证金时,财务审核表插入失败");  
                            }
                        }
                    }
                }
            }else{
                result.setSuccess(false);
                result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
                result.setErrorMsg("此项目保证金退款已退完!"); 
            }
            
        }else{
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg("此项目不存在");
        }
        
        return result; 
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#getGardenProjectCanBackBail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultRecord<GardenProjectInfo> getGardenProjectCanBackBail(GardenProjectQuery query) {
        ResultRecord<GardenProjectInfo> result = new ResultRecord<GardenProjectInfo>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        List<GardenProjectInfo> list = gardenProjectMapper.getGardenProjectCanBackBail(query);
        if(list==null || list.size()==0){
            result.setSuccess(false);
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.project.GardenProjectService#updateProjectSchema(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateProjectSchema(GardenProject record) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(record) || Util.isNull(record.getId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }        
        int total = gardenProjectMapper.updateGardenProjectById(record);
        if(total > 0){
            result.setSuccess(true);
            result.setT(total);
            //更新经营审核记录
            //查询此项目有无未审核理想记录
            GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
            manageQuery.setCompanyId(record.getCompanyId());
            manageQuery.setProjectId(record.getId());
            manageQuery.setType(109);
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            List<GardenProjectManageAudit> manageLst = manageAuditMapper.getProjectManageAudit(manageQuery);
            if(!(manageLst==null  || manageLst.size()==0)){
                ManageAudit manage = new ManageAudit();
                manage.setId(manageLst.get(0).getId());
                manage.setStatus(ProjectTableStatusEnum.VERIFIED_FAIL.getValue());
                manage.setUpdateTime(record.getUpdateTime());
                manage.setUpdateUserId(record.getUpdateUserId());
                int cnt = manageAuditMapper.updateManageAuditById(manage);
                if(cnt<=0){
                    throw new ApplicationException("项目立项审核是更新经营审核记录失败"); 
                }
            }
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":项目表更新失败");
        }
        return result;
    }
}
