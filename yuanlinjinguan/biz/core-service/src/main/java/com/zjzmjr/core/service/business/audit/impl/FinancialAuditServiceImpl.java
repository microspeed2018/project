package com.zjzmjr.core.service.business.audit.impl;

import java.text.MessageFormat;
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
import com.zjzmjr.core.base.model.PointSymbol;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectFinancialAudit;
import com.zjzmjr.core.model.audit.LawAudit;
import com.zjzmjr.core.model.audit.OfficeAudit;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.CashierConfirmationService;
import com.zjzmjr.core.service.business.audit.FinancialAuditService;
import com.zjzmjr.core.service.business.audit.LawAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;
import com.zjzmjr.core.service.business.common.CommonMessageUtil;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.FinancialAuditMapper;


/**
 * 财务审核处理
 * 
 * @author lenovo
 * @version $Id: FinancialAuditServiceImpl.java, v 0.1 2017-9-1 下午3:10:17 lenovo Exp $
 */
@Service("financialAuditService")
public class FinancialAuditServiceImpl implements FinancialAuditService{

    private static final Logger logger = LoggerFactory.getLogger(ChiefAuditServiceImpl.class);

    @Resource(name = "financialAuditMapper")
    private FinancialAuditMapper financialAuditMapper;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;
    
    @Resource(name = "cashierConfirmationService")
    private CashierConfirmationService cashierConfirmationService;
    
    @Resource(name = "lawAuditService")
    private LawAuditService lawAuditService;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.FinancialAuditService#getProjectFinancialAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectFinancialAudit> getProjectFinancialAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectFinancialAudit> result = new ResultPage<GardenProjectFinancialAudit>();
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
            total = financialAuditMapper.getProjectFinancialAuditCount(query);
            if (total > 0) {
                List<GardenProjectFinancialAudit> list = financialAuditMapper.getProjectFinancialAudit(query);
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
     * @see com.zjzmjr.core.service.business.audit.FinancialAuditService#updateFinancialAuditById(com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateFinancialAudit(GardenProjectAuditQuery query) throws  ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        FinancialAudit financial = new FinancialAudit();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery= new GardenProjectAuditQuery();
        ResultPage<GardenProjectFinancialAudit> manageRst = null;
        financial.setStatus(query.getStatus());
        financial.setCompanyId(query.getCompanyId());
        financial.setUpdateTime(query.getUpdateTime());
        financial.setUpdateUserId(query.getUpdateUserId());
        financial.setMemo(query.getMemo());
        if(Util.isNull(query.getId())){
            //通过 项目id 项目步骤  公司id查询 
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectFinancialAudit(manageQuery); 
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());               
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                financial.setId(manageRst.getList().get(0).getId()); 
            }
        }else{ 
            manageQuery.setId(query.getId());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectFinancialAudit(manageQuery); 
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());               
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                financial.setId(manageRst.getList().get(0).getId()); 
            }
        }  
        int cnt = financialAuditMapper.updateFinancialAuditById(financial);
        if(cnt > 0){
            if(ProjectTableStatusEnum.VERIFIED.getValue().equals(query.getStatus())){
                if(28==query.getType()){
                    //新增数据至出纳确认表
                    CashierConfirmation  cashierConfirmation = new CashierConfirmation();
                    cashierConfirmation.setCompanyId(query.getCompanyId());
                    cashierConfirmation.setProjectId(query.getProjectId());
                    cashierConfirmation.setAmount(manageRst.getList().get(0).getAmount());
                    cashierConfirmation.setPaymentMode(manageRst.getList().get(0).getPaymentMode());
                    cashierConfirmation.setType(32);
                    cashierConfirmation.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    cashierConfirmation.setCreateTime(query.getUpdateTime());
                    cashierConfirmation.setCreateUserId(query.getUpdateUserId());
                    result = cashierConfirmationService.insertCashierConfirmation(cashierConfirmation);
                    if(!result.isSuccess()){
                        throw new ApplicationException("财务审核保证金时,出纳确认表插入失败");  
                    } 
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if(pjRst.isSuccess()){
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_960.getValue());
                        message.setAddress(MessageContextEnum.Msg_960.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_960.getMessage(), 
                                pjRst.getList().get(0).getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, pjRst.getList().get(0).getProjectLeader()); 
                        CommonMessageUtil.insertMessage(message,CommonMessageUtil.getChuNaPersons(query.getCompanyId()));
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(pjRst.getList().get(0).getName());
                        pointSymbol.setAuditType("申请开票");
                        pointSymbol.setJob("财务");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_960.getTemplateCode()); 
                        MessageHandlerUtil.sendSms(pointSymbol, pjRst.getList().get(0).getProjectLeader());
                        MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getChuNaPersons(query.getCompanyId()));
                    }                    
                }else if(29==query.getType()){
                    //新增数据至院办审核表
                    OfficeAudit office = new OfficeAudit();
                    office.setCompanyId(query.getCompanyId());
                    office.setProjectId(query.getProjectId());
                    office.setAmount(manageRst.getList().get(0).getAmount());
                    office.setPaymentMode(manageRst.getList().get(0).getPaymentMode());
                    office.setApplicant(manageRst.getList().get(0).getApplicant());
                    office.setType(84);
                    office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    office.setCreateTime(query.getUpdateTime());
                    office.setCreateUserId(query.getUpdateUserId());
                    result = officeAuditService.insertOfficeAudit(office);
                    if(!result.isSuccess()){
                        throw new ApplicationException("财务审核保证金时,院办审核表插入失败");  
                    } 
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if(pjRst.isSuccess()){
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_1000.getValue());
                        message.setAddress(MessageContextEnum.Msg_1000.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_1000.getMessage(), 
                                pjRst.getList().get(0).getName()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, pjRst.getList().get(0).getProjectLeader());
                        CommonMessageUtil.insertMessage(message,CommonMessageUtil.getChuNaPersons(query.getCompanyId()));
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(pjRst.getList().get(0).getName());
                        pointSymbol.setAuditType("发票");
                        pointSymbol.setJob("财务");
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_1000.getTemplateCode()); 
                        MessageHandlerUtil.sendSms(pointSymbol, pjRst.getList().get(0).getProjectLeader());
                        MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getChuNaPersons(query.getCompanyId()));
                    }
                }else if(86==query.getType()){
                    //新增数据至出纳确认表
                    CashierConfirmation  cashierConfirmation = new CashierConfirmation();
                    cashierConfirmation.setCompanyId(query.getCompanyId());
                    cashierConfirmation.setProjectId(query.getProjectId());
                    cashierConfirmation.setAmount(manageRst.getList().get(0).getAmount());
                    cashierConfirmation.setPaymentMode(manageRst.getList().get(0).getPaymentMode());
                    cashierConfirmation.setType(87);
                    cashierConfirmation.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                    cashierConfirmation.setCreateTime(query.getUpdateTime());
                    cashierConfirmation.setCreateUserId(query.getUpdateUserId());
                    result = cashierConfirmationService.insertCashierConfirmation(cashierConfirmation);
                    if(!result.isSuccess()){
                        throw new ApplicationException("财务审核退保证金时,出纳确认表插入失败");  
                    }  
                    
                }else if(Util.isNotNull(query.getStep())){
                    if(GardenProjectStepEnum.P_330.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_340.getValue());
                        //新增数据至法务审核
                        LawAudit law = new LawAudit();
                        law.setCompanyId(query.getCompanyId());
                        law.setProjectId(query.getProjectId());
                        law.setApplicant(manageRst.getList().get(0).getApplicant());
                        law.setAmount(manageRst.getList().get(0).getAmount());
                        law.setType(73);
                        law.setApplicant(manageRst.getList().get(0).getApplicant());
                        law.setAmount(manageRst.getList().get(0).getAmount());
                        law.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        law.setCreateTime(query.getUpdateTime());
                        law.setCreateUserId(query.getUpdateUserId());
                        result = lawAuditService.insertLawAudit(law);
                        if (!result.isSuccess()) {
                            throw new ApplicationException("财务审核时,法务审核表插入失败");
                        }
                    }
                }else if(Util.isNotNull(query.getSubStep())){
                    if(GardenProjectStepEnum.P_150.getValue().equals(query.getSubStep())){
                        project.setSubStep(GardenProjectStepEnum.P_160.getValue());
                      //新增数据至院办审核表
                        OfficeAudit office = new OfficeAudit();
                        office.setCompanyId(query.getCompanyId());
                        office.setProjectId(query.getProjectId());
                        office.setApplicant(manageRst.getList().get(0).getApplicant());
                        office.setAmount(manageRst.getList().get(0).getAmount());
                        office.setType(52);
                        office.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
                        office.setCreateTime(query.getUpdateTime());
                        office.setCreateUserId(query.getUpdateUserId());
                        result = officeAuditService.insertOfficeAudit(office);
                        if(!result.isSuccess()){
                            throw new ApplicationException("财务审核保证金时,院办审核表插入失败");  
                        }  
                    }
                }                           
            }else{
                if(28==query.getType()){
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if(pjRst.isSuccess()){
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_970.getValue());
                        message.setAddress(MessageContextEnum.Msg_970.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_970.getMessage(), 
                                pjRst.getList().get(0).getName(),query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, pjRst.getList().get(0).getProjectLeader());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(pjRst.getList().get(0).getName());
                        pointSymbol.setAuditType("申请开票");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_970.getTemplateCode()); 
                        MessageHandlerUtil.sendSms(pointSymbol, pjRst.getList().get(0).getProjectLeader());
                    } 
                }else if(29==query.getType()){
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    projectQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                    ResultPage<GardenProjectInfo> pjRst = gardenProjectService.getGardenProjectByCondition(projectQuery);
                    if(pjRst.isSuccess()){
                        Message message = new Message();
                        message.setTitle(MessageContextEnum.Msg_1010.getValue());
                        message.setAddress(MessageContextEnum.Msg_1010.getAddress());
                        message.setContext(MessageFormat.format(MessageContextEnum.Msg_1010.getMessage(), 
                                pjRst.getList().get(0).getName(),query.getMemo()));
                        message.setStatus(MessageStatusEnum.UNREAD.getValue());
                        message.setCompanyId(query.getCompanyId());
                        CommonMessageUtil.insertMessage(message, pjRst.getList().get(0).getProjectLeader());
                        PointSymbol pointSymbol = new PointSymbol();
                        pointSymbol.setName(pjRst.getList().get(0).getName());
                        pointSymbol.setAuditType("发票");
                        pointSymbol.setReason(query.getMemo());
                        pointSymbol.setTemplateCode(MessageContextEnum.Msg_1010.getTemplateCode()); 
                        MessageHandlerUtil.sendSms(pointSymbol, pjRst.getList().get(0).getProjectLeader());
                    }
                }else if(86==query.getType()){
                    //更新项目表，将未退保证金改回
                    GardenProject gardenProject = new GardenProject();
                    gardenProject.setId(query.getProjectId());
                    gardenProject.setUpdateTime(query.getUpdateTime());
                    gardenProject.setUpdateUserId(query.getUpdateUserId());
                    GardenProjectQuery projectQuery = new GardenProjectQuery();
                    projectQuery.setId(query.getProjectId());
                    ResultEntry<GardenProject> projectRst =  gardenProjectService.getGardenProjectByIdAndStatus(projectQuery);
                    if(projectRst.isSuccess()){
                        if(Util.isNotNull(projectRst.getT().getNobackBail())){
                            gardenProject.setNobackBail(projectRst.getT().getNobackBail().add(manageRst.getList().get(0).getAmount()));
                        }else{
                            gardenProject.setNobackBail(manageRst.getList().get(0).getAmount());
                        }
                        result = gardenProjectService.updateGardenProjectById(gardenProject);
                        if(!result.isSuccess()){
                            throw new ApplicationException("财务审核退保证金不通过时,修改项目表失败");   
                        }
                    } 
                    
                }else if(Util.isNotNull(query.getStep())){
                    if(GardenProjectStepEnum.P_330.getValue().equals(query.getStep())){
                        project.setStep(GardenProjectStepEnum.P_300.getValue());
                    }
                }else if(Util.isNotNull(query.getSubStep())){
                    if(GardenProjectStepEnum.P_150.getValue().equals(query.getSubStep())){
                        project.setSubStep(GardenProjectStepEnum.P_120.getValue());
                    }
                } 
            }
            if(query.getType()!=28 && query.getType()!=29 && query.getType()!=86){
                //更新项目进度           
                project.setId(query.getProjectId());            
                project.setUpdateUserId(query.getUpdateUserId());
                project.setUpdateTime(query.getUpdateTime());
                result = gardenProjectService.updateGardenProjectById(project);
                if(!result.isSuccess()){
                    throw new ApplicationException("财务审核时,项目进度更新失败");
                }  
            }
            result.setT(cnt);
            result.setSuccess(true);              
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":财务审核表更新失败"); 
        }                    
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.FinancialAuditService#insertFinancialAudit(com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertFinancialAudit(FinancialAudit financialAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(financialAudit) || Util.isNull(financialAudit.getCompanyId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        int cnt = financialAuditMapper.insertFinancialAudit(financialAudit);
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
     * @see com.zjzmjr.core.service.business.audit.FinancialAuditService#getProjectFinancialAuditByCondition(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultRecord<FinancialAudit> getProjectFinancialAuditByCondition(GardenProjectAuditQuery query) {
        ResultRecord<FinancialAudit> result = new ResultRecord<FinancialAudit>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;    
        }
        List<FinancialAudit> list = financialAuditMapper.getProjectFinancialAuditByCondition(query);
        if(list==null || list.size()==0){
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());               
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.FinancialAuditService#getProjectFinancialAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectFinancialAuditCount(GardenProjectAuditQuery query) {
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
            total = financialAuditMapper.getProjectFinancialAuditCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }
}
