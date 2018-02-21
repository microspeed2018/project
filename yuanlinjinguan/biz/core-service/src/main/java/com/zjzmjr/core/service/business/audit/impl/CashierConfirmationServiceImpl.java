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
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.common.message.MessageHandlerUtil;
import com.zjzmjr.core.enums.message.MessageContextEnum;
import com.zjzmjr.core.enums.message.MessageStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.enums.project.ProjectTableStatusEnum;
import com.zjzmjr.core.model.audit.CashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;
import com.zjzmjr.core.model.message.Message;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectInfo;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.service.business.audit.CashierConfirmationService;
import com.zjzmjr.core.service.business.common.CommonMessageUtil;
import com.zjzmjr.core.service.business.project.ContractSubpackageService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.mapper.dao.audit.CashierConfirmationMapper;

/**
 * 出纳确认处理
 * 
 * @author lenovo
 * @version $Id: CashierConfirmationServiceImpl.java, v 0.1 2017-9-1 下午4:35:32 lenovo Exp $
 */
@Service("cashierConfirmationService")
public class CashierConfirmationServiceImpl implements CashierConfirmationService{

    private static final Logger logger = LoggerFactory.getLogger(ChiefAuditServiceImpl.class);

    @Resource(name = "cashierConfirmationMapper")
    private CashierConfirmationMapper cashierConfirmationMapper;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;
    
    @Resource(name = "contractSubpackageService")
    private ContractSubpackageService contractSubpackageService;

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.CashierConfirmationService#getProjectCashierConfirmation(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectCashierConfirmation> getProjectCashierConfirmation(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectCashierConfirmation> result = new ResultPage<GardenProjectCashierConfirmation>();
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
            total = cashierConfirmationMapper.getProjectCashierConfirmationCount(query);
            if (total > 0) {
                List<GardenProjectCashierConfirmation> list = cashierConfirmationMapper.getProjectCashierConfirmation(query);
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
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.audit.CashierConfirmationService#updateCashierConfirmationById(com.zjzmjr.core.model.audit.CashierConfirmation)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateCashierConfirmation(GardenProjectAuditQuery query) throws ApplicationException {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(query)){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;   
        }
        CashierConfirmation cashierConfirmation = new CashierConfirmation();
        GardenProject project = new GardenProject();
        GardenProjectAuditQuery manageQuery= new GardenProjectAuditQuery();
        cashierConfirmation.setStatus(query.getStatus());
        cashierConfirmation.setUpdateTime(new Date());
        cashierConfirmation.setUpdateUserId(query.getUpdateUserId());
        ResultPage<GardenProjectCashierConfirmation> manageRst =null;
        if(Util.isNull(query.getId())){
            //通过 项目id 项目步骤  公司id查询 
            manageQuery.setCompanyId(query.getCompanyId());
            manageQuery.setProjectId(query.getProjectId());
            manageQuery.setType(query.getType());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectCashierConfirmation(manageQuery);
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                cashierConfirmation.setId(manageRst.getList().get(0).getId()); 
            }
        }else{
            manageQuery.setId(query.getId());
            manageQuery.setStatus(ProjectTableStatusEnum.NOT_VERIFY.getValue());
            manageQuery.setPageBean(new PageBean(Integer.MAX_VALUE, GenerateConstants.number_1));
            manageRst = getProjectCashierConfirmation(manageQuery);
            if(!manageRst.isSuccess() && ErrorCodeEnum.RECORD_NOT_EXSIST.getName().equals(manageRst.getErrorMsg())){
                result.setErrorCode(ErrorCodeEnum.RECORD_CHANGE.getCode());
                result.setSuccess(false);
                result.setErrorMsg("此项目已被审核!");
                return result;
            }else if(manageRst.isSuccess()){
                cashierConfirmation.setId(manageRst.getList().get(0).getId()); 
            }
        }                       
        int cnt = cashierConfirmationMapper.updateCashierConfirmationById(cashierConfirmation);
        if(cnt > 0){              
            if (query.getType() == 31) {
                Message message = new Message();
                message.setTitle(MessageContextEnum.Msg_980.getValue());
                message.setAddress(MessageContextEnum.Msg_980.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_980.getMessage(), 
                        manageRst.getList().get(0).getBasicData().getAttributeName(),manageRst.getList().get(0).getGardenProject().getName(),manageRst.getList().get(0).getBasicData().getAttributeName()
                        ,manageRst.getList().get(0).getAmount()));
                message.setStatus(MessageStatusEnum.UNREAD.getValue());
                message.setCompanyId(query.getCompanyId());
                CommonMessageUtil.insertMessage(message, manageRst.getList().get(0).getGardenProject().getProjectLeader()); 
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getJinYingPersons(query.getCompanyId())); 
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getJinYingManagerPersons(query.getCompanyId())); 
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getKuaijiPersons(query.getCompanyId())); 
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getYuanBanPersons(query.getCompanyId()));
                PointSymbol pointSymbol = new PointSymbol();
                pointSymbol.setName(manageRst.getList().get(0).getGardenProject().getName());
                pointSymbol.setConfirmType(manageRst.getList().get(0).getBasicData().getAttributeName());
                if(Util.isNotNull(manageRst.getList().get(0).getAmount())){
                    pointSymbol.setMoney(manageRst.getList().get(0).getAmount().toString()+"万元");  
                }else{
                    pointSymbol.setMoney("0");
                }
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_980.getTemplateCode()); 
                MessageHandlerUtil.sendSms(pointSymbol, manageRst.getList().get(0).getGardenProject().getProjectLeader());
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getJinYingPersons(query.getCompanyId()));
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getJinYingManagerPersons(query.getCompanyId()));
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getKuaijiPersons(query.getCompanyId()));
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getYuanBanPersons(query.getCompanyId()));
            } else if (query.getType() == 32) {
                Message message = new Message();
                message.setTitle(MessageContextEnum.Msg_980.getValue());
                message.setAddress(MessageContextEnum.Msg_980.getAddress());
                message.setContext(MessageFormat.format(MessageContextEnum.Msg_980.getMessage(), 
                        manageRst.getList().get(0).getBasicData().getAttributeName(),manageRst.getList().get(0).getGardenProject().getName(),manageRst.getList().get(0).getBasicData().getAttributeName()
                        ,manageRst.getList().get(0).getAmount()));
                message.setStatus(MessageStatusEnum.UNREAD.getValue());
                message.setCompanyId(query.getCompanyId());
                CommonMessageUtil.insertMessage(message, manageRst.getList().get(0).getGardenProject().getProjectLeader());                   
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getKuaijiPersons(query.getCompanyId())); 
                CommonMessageUtil.insertMessage(message, CommonMessageUtil.getYuanBanPersons(query.getCompanyId()));
                PointSymbol pointSymbol = new PointSymbol();
                pointSymbol.setName(manageRst.getList().get(0).getGardenProject().getName());
                pointSymbol.setConfirmType(manageRst.getList().get(0).getBasicData().getAttributeName());
                if(Util.isNotNull(manageRst.getList().get(0).getAmount())){
                    pointSymbol.setMoney(manageRst.getList().get(0).getAmount().toString());  
                }else{
                    pointSymbol.setMoney("0");
                }
                pointSymbol.setTemplateCode(MessageContextEnum.Msg_980.getTemplateCode()); 
                MessageHandlerUtil.sendSms(pointSymbol, manageRst.getList().get(0).getGardenProject().getProjectLeader());
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getKuaijiPersons(query.getCompanyId()));
                MessageHandlerUtil.sendSms(pointSymbol, CommonMessageUtil.getYuanBanPersons(query.getCompanyId()));
                //查询分包负责人(projectId,companyId)
                ContractSubpackageQuery packageQuery = new ContractSubpackageQuery();
                packageQuery.setCompanyId(query.getCompanyId());
                packageQuery.setProjectId(query.getProjectId());               
                ResultRecord<ContractSubpackage>  packageRst = contractSubpackageService.getContractSubpackageByCondition(packageQuery);
                if(packageRst.isSuccess()){
                    for(int i=0;i<packageRst.getList().size();i++){
                        CommonMessageUtil.insertMessage(message, packageRst.getList().get(i).getSubpackageLeader());
                        MessageHandlerUtil.sendSms(pointSymbol, packageRst.getList().get(i).getSubpackageLeader());
                    }
                }
            } else if (Util.isNotNull(query.getSubStep())) {
                //出纳确认保证金通过
                if (GardenProjectStepEnum.P_170.getValue().equals(query.getSubStep())) {                   
                    project.setSubStep(GenerateConstants.number_275);
                }
            } 
            if(query.getType()!=31 && query.getType()!=32){
              //更新项目进度
                project.setId(query.getProjectId());
                project.setUpdateUserId(query.getUpdateUserId());
                project.setUpdateTime(new Date());
                result = gardenProjectService.updateGardenProjectById(project);
                if(!result.isSuccess()){
                    throw new ApplicationException("出纳确认时,项目进度更新失败");
                } 
            }           
            result.setT(manageRst.getList().get(0).getAmount().intValue());
            result.setSuccess(true);
        }else{
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":出纳确认表更新失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.CashierConfirmationService#insertCashierConfirmation(com.zjzmjr.core.model.audit.CashierConfirmation)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertCashierConfirmation(CashierConfirmation cashierConfirmation) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if (Util.isNull(cashierConfirmation) || Util.isNull(cashierConfirmation.getCompanyId())) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int cnt = cashierConfirmationMapper.insertCashierConfirmation(cashierConfirmation);
        if (cnt > 0) {
            result.setSuccess(true);
            result.setT(cnt);
        } else {
            result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
            result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
            result.setSuccess(false);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":出纳确认表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.audit.CashierConfirmationService#getProjectCashierConfirmationCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectCashierConfirmationCount(GardenProjectAuditQuery query) {
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
            total = cashierConfirmationMapper.getProjectCashierConfirmationCount(query);
        }
        result.setSuccess(true);
        result.setT(total);
        return result;
    }

}
