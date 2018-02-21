package com.zjzmjr.core.service.business.audit.impl;

import java.text.MessageFormat;
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
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.message.MessageTypeEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.AdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAdministrativeSeal;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.AdministrativeSealService;
import com.zjzmjr.core.service.business.common.CommonMessageUtil;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.AdministrativeSealMapper;

/**
 * 行政盖章表业务处理层
 * 
 * @author oms
 * @version $Id: AdministrativeSealServiceImpl.java, v 0.1 2017-9-1 下午3:21:32 oms Exp $
 */
@Service("administrativeSealService")
public class AdministrativeSealServiceImpl implements AdministrativeSealService {

    private static final Logger logger = LoggerFactory.getLogger(AdministrativeSealServiceImpl.class);

    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "administrativeSealMapper")
    private AdministrativeSealMapper administrativeSealMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#insertAdministrativeSeal(com.zjzmjr.core.model.audit.AdministrativeSeal)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertAdministrativeSeal(AdministrativeSeal record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getType())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        
        int total = administrativeSealMapper.insertAdministrativeSeal(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":行政盖章表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#updateAdministrativeSealById(com.zjzmjr.core.model.audit.AdministrativeSeal)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateAdministrativeSealById(AdministrativeSeal record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        
        int total = administrativeSealMapper.updateAdministrativeSealById(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":行政盖章表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#updateAdministrativeSealByType(com.zjzmjr.core.model.audit.AdministrativeSeal)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateAdministrativeSealByType(AdministrativeSeal record) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(record) || Util.isNull(record.getType())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        
        int total = administrativeSealMapper.updateAdministrativeSealByType(record);
        if (total > 0) {
            result.setSuccess(true);
            result.setT(total);
        } else {
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":行政盖章表更新失败");
        }
        return result;
    }
    
    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#updateAdministrativeSealByType(com.zjzmjr.core.model.project.GardenProject, com.zjzmjr.core.model.audit.AdministrativeSeal)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateAdministrativeSealByType(GardenProject project, AdministrativeSeal seal) throws ApplicationException {
        
        ResultEntry<Integer> result = updateAdministrativeSealByType(seal);
        if(result.isSuccess()){
            result = gardenProjectService.updateGardenProjectById(project);
            if(!result.isSuccess()){
                throw new ApplicationException("行政盖章时，操作项目失败");
            }
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#getProjectAdministrativeSeal(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectAdministrativeSeal> getProjectAdministrativeSeal(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectAdministrativeSeal> result = new ResultPage<GardenProjectAdministrativeSeal>();
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
            total = administrativeSealMapper.getProjectAdministrativeSealCount(query);
            if (total > 0) {
                List<GardenProjectAdministrativeSeal> list = administrativeSealMapper.getProjectAdministrativeSeal(query);
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
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#updateAdministrativeSealAndProjectStep(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateAdministrativeSealAndProjectStep(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        AdministrativeSeal administrativeSeal = new AdministrativeSeal();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery = new GardenProjectAuditQuery();
        administrativeSeal.setStatus(query.getStatus());
        administrativeSeal.setUpdateTime(new Date());
        administrativeSeal.setUpdateUserId(query.getUpdateUserId());
        if (Util.isNull(query.getId())) {
            // 通过 项目id 项目步骤 公司id查询
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            ResultPage<GardenProjectAdministrativeSeal> manageRst = getProjectAdministrativeSeal(manageQuery);
            if (!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())) {
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setErrorMsg(ErrorCodeEnum.RECORD_CHANGE.getName());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            } else if (manageRst.isSuccess()) {
                administrativeSeal.setId(manageRst.getList().get(0).getId());
            }
        } else {
            administrativeSeal.setId(query.getId());
        }

        // 插入消息
        Message message = new Message();
        message.setCompanyId(query.getCompanyId());
        message.setStatus(MessageStatusEnum.UNREAD.getValue());
        message.setType(MessageTypeEnum.REMIND.getValue());
        message.setCreateTime(new Date());
        message.setCreateUserId(query.getUpdateUserId());
        message.setUpdateTime(message.getCreateTime());
        message.setUpdateUserId(message.getCreateUserId());
        // 获取项目
        GardenProject msgProject = CommonMessageUtil.getProjectInfoById(query.getProjectId());
        if(GenerateConstants.number_1.equals(query.getHavaTechnical())){
            if (Util.isNotNull(query.getSubStep2()) && GardenProjectStepEnum.P_270.getValue().equals(query.getSubStep2())) {
                //判断保证金是否审核
                if (Util.isNull(query.getSubStep()) || (Util.isNotNull(query.getSubStep()) && (GenerateConstants.number_275.equals(query.getSubStep())))) {
                    //判断商务标是否审核
                    if (GenerateConstants.number_275.equals(query.getStep())) {
                        project.setStep(GardenProjectStepEnum.P_280.getValue());
                        project.setSubStep2(GenerateConstants.number_275);
                    } else {
                      //判断商务标未审核
                        project.setSubStep2(GenerateConstants.number_275);
                    }
                } else {
                  //保证金未审核
                    result.setSuccess(false);
                    result.setErrorMsg("保证金未审核完毕，请确认");
                    return result;
                }
            } 
        }else{          
            if (Util.isNotNull(query.getStep())) {
                if (GardenProjectStepEnum.P_90.getValue().equals(query.getStep())) {
                    project.setStep(GardenProjectStepEnum.P_100.getValue());
                    message.setTitle(MessageContextEnum.Msg_140.getValue());
                    message.setAddress(MessageContextEnum.Msg_140.getAddress());
                    message.setContext(MessageFormat.format(MessageContextEnum.Msg_140.getMessage(), 
                                msgProject.getName()));
                    message.setStatus(MessageStatusEnum.UNREAD.getValue());
                    message.setCompanyId(query.getCompanyId());
                    CommonMessageUtil.insertMessage(message, msgProject.getManagementPersonnel());
                    PointSymbol pointSymbol = new PointSymbol();
                    pointSymbol.setName(msgProject.getName());
                    pointSymbol.setSealType("报名文件");
                    pointSymbol.setTemplateCode(MessageContextEnum.Msg_140.getTemplateCode());
                    MessageHandlerUtil.sendSms(pointSymbol, msgProject.getManagementPersonnel());
                } else if (GardenProjectStepEnum.P_230.getValue().equals(query.getStep())) {
                    //判断保证金是否审核
                    if (Util.isNull(query.getSubStep()) || (Util.isNotNull(query.getSubStep()) && (GenerateConstants.number_275.equals(query.getSubStep())))) {
                        //保证金已审核，判断是否有技术标以及是否审核
                        if (Util.isNull(query.getSubStep2())|| (Util.isNotNull(query.getSubStep2()) && GenerateConstants.number_275.equals(query.getSubStep2()))) {
                            project.setStep(GardenProjectStepEnum.P_280.getValue());
                        } else {
                            //技术标未审核
                            project.setStep(GenerateConstants.number_275);
                        }
                    } else {
                        //保证金未审核
                        result.setSuccess(false);
                        result.setErrorMsg("保证金未审核完毕，请确认");
                        return result;
                    }

                } else if (GardenProjectStepEnum.P_360.getValue().equals(query.getStep())) {
                    project.setStep(GardenProjectStepEnum.P_370.getValue());
                }
            } 
        }
        int cnt = administrativeSealMapper.updateAdministrativeSealById(administrativeSeal);
        if(cnt > 0){         
          //更新项目进度
            project.setId(query.getProjectId());            
            project.setUpdateUserId(query.getUpdateUserId());
            project.setUpdateTime(new Date());
            result = gardenProjectService.updateGardenProjectById(project);
            if(!result.isSuccess()){
                throw new ApplicationException("行政盖章时,项目进度更新失败");
            }
            result.setT(cnt);
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":行政盖章表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.AdministrativeSealService#getProjectAdministrativeSealCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectAdministrativeSealCount(GardenProjectAuditQuery query) {
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
            total = administrativeSealMapper.getProjectAdministrativeSealCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }

}
