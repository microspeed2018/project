package com.zjzmjr.core.provider.project;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.project.IGardenProjectFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.GardenProjectSchema;
import com.zjzmjr.core.model.project.GardenProjectSchemaInfo;
import com.zjzmjr.core.model.project.ProjectDisplayRule;
import com.zjzmjr.core.model.project.ProjectSchemaQuery;
import com.zjzmjr.core.service.business.project.GardenProjectSchemaService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectDisplayRuleService;

/**
 * 项目管理的对外接口
 * 
 * @author oms
 * @version $Id: GardenProjectFacadeImpl.java, v 0.1 2017-8-14 下午1:16:35 oms Exp $
 */
@Service("gardenProjectFacade")
public class GardenProjectFacadeImpl implements IGardenProjectFacade{

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectFacadeImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "gardenProjectSchemaService")
    private GardenProjectSchemaService gardenProjectSchemaService;
    
    /**
     * 项目一览显示规则
     */
    @Resource(name = "projectDisplayRuleService")
    private ProjectDisplayRuleService projectDisplayRuleService;
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectMaxNo()
     */
    @Override
    public ResultEntry<String> getGardenProjectMaxNo() {
        ResultEntry<String> result = new ResultEntry<String>();
        try {
            result = gardenProjectService.getGardenProjectMaxNo();
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectByCondition(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultPage<GardenProjectInfo> getGardenProjectByCondition(GardenProjectQuery query) {
        ResultPage<GardenProjectInfo> result = new ResultPage<GardenProjectInfo>();
        try {
            result = gardenProjectService.getGardenProjectByCondition(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#insertGardenProject(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    public ResultEntry<Integer> insertGardenProject(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectService.insertGardenProject(record);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#updateGardenProjectById(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    public ResultEntry<Integer> updateGardenProjectById(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectService.updateGardenProjectById(record);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectSchemaByCondition(com.zjzmjr.core.model.project.ProjectSchemaQuery)
     */
    @Override
    public ResultPage<GardenProjectSchemaInfo> getGardenProjectSchemaByCondition(ProjectSchemaQuery query) {
        ResultPage<GardenProjectSchemaInfo> result = new ResultPage<GardenProjectSchemaInfo>();
        try {
            result = gardenProjectSchemaService.getGardenProjectSchemaByCondition(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#insertGardenProjectSchema(com.zjzmjr.core.model.project.GardenProjectSchema)
     */
    @Override
    public ResultEntry<Integer> insertGardenProjectSchema(GardenProjectSchema record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectSchemaService.insertGardenProjectSchema(record);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#insertGardenProjectSchema(java.util.List)
     */
    @Override
    public ResultEntry<Integer> insertGardenProjectSchema(GardenProject project, List<GardenProjectSchema> record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectSchemaService.insertGardenProjectSchema(project, record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#updateGardenProjectSchemaById(com.zjzmjr.core.model.project.GardenProjectSchema)
     */
    @Override
    public ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchema record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectSchemaService.updateGardenProjectSchema(record);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#updateGardenProjectSchema(com.zjzmjr.core.model.project.GardenProjectSchemaInfo)
     */
    @Override
    public ResultEntry<Integer> updateGardenProjectSchema(GardenProjectSchemaInfo schemaInfo) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectSchemaService.updateGardenProjectSchema(schemaInfo);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;}
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getProjectDisplayRuleByUserId(java.lang.Integer)
     */
    @Override
    public ResultEntry<ProjectDisplayRule> getProjectDisplayRuleByUserId(Integer userId) {
        ResultEntry<ProjectDisplayRule> result = new ResultEntry<ProjectDisplayRule>();
        try {
            result = projectDisplayRuleService.getProjectDisplayRuleByUserId(userId);
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#insertProjectDisplayRule(com.zjzmjr.core.model.project.ProjectDisplayRule)
     */
    @Override
    public ResultEntry<Integer> generateProjectDisplayRule(ProjectDisplayRule record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            ResultEntry<ProjectDisplayRule> ruleRst = projectDisplayRuleService.getProjectDisplayRuleByUserId(record.getUserId());
            if (ruleRst.isSuccess()) {
                record.setCreateTime(null);
                record.setCreateUserId(null);
                result = projectDisplayRuleService.updateProjectDisplayRuleById(record);
            } else {
                result = projectDisplayRuleService.insertProjectDisplayRule(record);
            }
        } catch (Exception e) {
            result.setSuccess(false);
            result.setErrorMsg(e.getMessage());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), e);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.project.IProjectContractFacade#updateGardenProject(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    public ResultEntry<Integer> updateGardenProject(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectService.updateGardenProject(record);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectByIdAndStatus(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultEntry<GardenProject> getGardenProjectByIdAndStatus(GardenProjectQuery query) {
        ResultEntry<GardenProject> result = new ResultEntry<GardenProject>();
        try {
            result = gardenProjectService.getGardenProjectByIdAndStatus(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getProjectDetail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultEntry<GardenProjectInfo> getProjectDetail(GardenProjectQuery query) {
        ResultEntry<GardenProjectInfo> result = new ResultEntry<GardenProjectInfo>();
        try {
            result = gardenProjectService.getProjectDetail(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectNoContract()
     */
    @Override
    public ResultRecord<GardenProject> getGardenProjectNoContract() {
        ResultRecord<GardenProject> result = new ResultRecord<GardenProject>();
        try {
            result = gardenProjectService.getGardenProjectNoContract();
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#applyBackBail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultEntry<Integer> applyBackBail(GardenProjectQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectService.applyBackBail(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#getGardenProjectCanBackBail(com.zjzmjr.core.model.project.GardenProjectQuery)
     */
    @Override
    public ResultRecord<GardenProjectInfo> getGardenProjectCanBackBail(GardenProjectQuery query) {
        ResultRecord<GardenProjectInfo> result = new ResultRecord<GardenProjectInfo>();
        try {
            result = gardenProjectService.getGardenProjectCanBackBail(query);
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
     * @see com.zjzmjr.core.api.project.IGardenProjectFacade#updateProjectSchema(com.zjzmjr.core.model.project.GardenProject)
     */
    @Override
    public ResultEntry<Integer> updateProjectSchema(GardenProject record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectService.updateProjectSchema(record);
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
