package com.zjzmjr.core.provider.audit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.zjzmjr.core.api.audit.IGardenProjectAuditFacade;
import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.audit.FinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectAuditQuery;
import com.zjzmjr.core.model.audit.GardenProjectCashierConfirmation;
import com.zjzmjr.core.model.audit.GardenProjectChiefAudit;
import com.zjzmjr.core.model.audit.GardenProjectFinancialAudit;
import com.zjzmjr.core.model.audit.GardenProjectLawAudit;
import com.zjzmjr.core.model.audit.GardenProjectLeaderAudit;
import com.zjzmjr.core.model.audit.GardenProjectManageAudit;
import com.zjzmjr.core.model.audit.GardenProjectOfficeAudit;
import com.zjzmjr.core.model.audit.LeaderAudit;
import com.zjzmjr.core.service.business.audit.CashierConfirmationService;
import com.zjzmjr.core.service.business.audit.ChiefAuditService;
import com.zjzmjr.core.service.business.audit.FinancialAuditService;
import com.zjzmjr.core.service.business.audit.GardenProjectAuditService;
import com.zjzmjr.core.service.business.audit.LawAuditService;
import com.zjzmjr.core.service.business.audit.LeaderAuditService;
import com.zjzmjr.core.service.business.audit.ManageAuditService;
import com.zjzmjr.core.service.business.audit.OfficeAuditService;

@Service("gardenProjectAuditFacade")
public class GardenProjectAuditFacadeImpl implements IGardenProjectAuditFacade{

    private static final Logger logger = LoggerFactory.getLogger(GardenProjectAuditFacadeImpl.class);

    @Resource(name = "officeAuditService")
    private OfficeAuditService officeAuditService;
    
    @Resource(name = "manageAuditService")
    private ManageAuditService manageAuditService;
    
    @Resource(name = "chiefAuditService")
    private ChiefAuditService chiefAuditService;
    
    @Resource(name = "financialAuditService")
    private FinancialAuditService financialAuditService;
    
    @Resource(name = "cashierConfirmationService")
    private CashierConfirmationService cashierConfirmationService;
    
    @Resource(name = "gardenProjectAuditService")
    private GardenProjectAuditService gardenProjectAuditService;
    
    @Resource(name = "leaderAuditService")
    private LeaderAuditService leaderAuditService;
    
    @Resource(name = "lawAuditService")
    private LawAuditService lawAuditService;
  
    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectOfficeAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectOfficeAudit> getProjectOfficeAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectOfficeAudit> result = new ResultPage<GardenProjectOfficeAudit>();
        try {
            result = officeAuditService.getProjectOfficeAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateOfficeAuditById(com.zjzmjr.core.model.audit.OfficeAudit)
     */
    @Override
    public ResultEntry<Integer> updateOfficeAudit(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = gardenProjectAuditService.updateOfficeAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectManageAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectManageAudit> getProjectManageAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectManageAudit> result = new ResultPage<GardenProjectManageAudit>();
        try {
            result = manageAuditService.getProjectManageAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateManageAuditById(com.zjzmjr.core.model.audit.ManageAudit)
     */
    @Override
    public ResultEntry<Integer> updateManageAudit(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = manageAuditService.updateManageAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectChiefAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectChiefAudit> getProjectChiefAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectChiefAudit> result = new ResultPage<GardenProjectChiefAudit>();
        try {
            result = chiefAuditService.getProjectChiefAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateChiefAuditById(com.zjzmjr.core.model.audit.ChiefAudit)
     */
    @Override
    public ResultEntry<Integer> updateChiefAudit(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = chiefAuditService.updateChiefAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectFinancialAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectFinancialAudit> getProjectFinancialAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectFinancialAudit> result = new ResultPage<GardenProjectFinancialAudit>();
        try {
            result = financialAuditService.getProjectFinancialAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateFinancialAuditById(com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    public ResultEntry<Integer> updateFinancialAudit(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = financialAuditService.updateFinancialAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#insertFinancialAudit(com.zjzmjr.core.model.audit.FinancialAudit)
     */
    @Override
    public ResultEntry<Integer> insertFinancialAudit(FinancialAudit financialAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = financialAuditService.insertFinancialAudit(financialAudit);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectCashierConfirmation(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectCashierConfirmation> getProjectCashierConfirmation(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectCashierConfirmation> result = new ResultPage<GardenProjectCashierConfirmation>();
        try {
            result = cashierConfirmationService.getProjectCashierConfirmation(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateCashierConfirmationById(com.zjzmjr.core.model.audit.CashierConfirmation)
     */
    @Override
    public ResultEntry<Integer> updateCashierConfirmation(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = cashierConfirmationService.updateCashierConfirmation(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectLeaderAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectLeaderAudit> getProjectLeaderAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectLeaderAudit> result = new ResultPage<GardenProjectLeaderAudit>();
        try {
            result = leaderAuditService.getProjectLeaderAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateLeaderAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> updateLeaderAudit(GardenProjectAuditQuery query){
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = leaderAuditService.updateLeaderAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#updateLawAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> updateLawAudit(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = lawAuditService.updateLawAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectLawAudit(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultPage<GardenProjectLawAudit> getProjectLawAudit(GardenProjectAuditQuery query) {
        ResultPage<GardenProjectLawAudit> result = new ResultPage<GardenProjectLawAudit>();
        try {
            result = lawAuditService.getProjectLawAudit(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getAuditBusiness(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<GardenProjectOfficeAudit> getAuditBusiness(GardenProjectAuditQuery query) {
        ResultEntry<GardenProjectOfficeAudit> result = new ResultEntry<GardenProjectOfficeAudit>();
        try {
            result = officeAuditService.getAuditBusiness(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    @Override
    public ResultEntry<Integer> insertLeaderAudit(LeaderAudit leaderAudit) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = leaderAuditService.insertLeaderAudit(leaderAudit);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectFinancialAuditByCondition(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultRecord<FinancialAudit> getProjectFinancialAuditByCondition(GardenProjectAuditQuery query) {
        ResultRecord<FinancialAudit> result = new ResultRecord<FinancialAudit>();
        try {
            result = financialAuditService.getProjectFinancialAuditByCondition(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectCashierConfirmationCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectCashierConfirmationCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = cashierConfirmationService.getProjectCashierConfirmationCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectChiefAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectChiefAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = chiefAuditService.getProjectChiefAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectFinancialAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectFinancialAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = financialAuditService.getProjectFinancialAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectLawAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectLawAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = lawAuditService.getProjectLawAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectLeaderAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectLeaderAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = leaderAuditService.getProjectLeaderAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectManageAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectManageAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = manageAuditService.getProjectManageAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.api.audit.IGardenProjectAuditFacade#getProjectOfficeAuditCount(com.zjzmjr.core.model.audit.GardenProjectAuditQuery)
     */
    @Override
    public ResultEntry<Integer> getProjectOfficeAuditCount(GardenProjectAuditQuery query) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        try {
            result = officeAuditService.getProjectOfficeAuditCount(query);
        } catch (Exception ex) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
            logger.error(ErrorCodeEnum.SYSTEM_ERROR.getName(), ex);
            return result;
        }
        return result;
    }
    
}
