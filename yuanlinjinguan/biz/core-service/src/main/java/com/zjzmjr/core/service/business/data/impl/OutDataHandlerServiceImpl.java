package com.zjzmjr.core.service.business.data.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.util.NumberUtils;
import com.zjzmjr.core.base.util.ShareCodeUtil;
import com.zjzmjr.core.base.util.StringUtil;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.enums.admin.AdminJobStatusEnum;
import com.zjzmjr.core.enums.project.GardenProjectStepEnum;
import com.zjzmjr.core.model.admin.AdminCreate;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.core.model.area.AreaQuery;
import com.zjzmjr.core.model.company.CompanyAssociatedInfo;
import com.zjzmjr.core.model.company.CompanyAssociatedQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.GardenProjectQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.user.CompanyStaffPerson;
import com.zjzmjr.core.model.user.ExternalPerson;
import com.zjzmjr.core.model.user.ExternalPersonInfo;
import com.zjzmjr.core.model.user.ExternalPersonQuery;
import com.zjzmjr.core.model.user.StaffBasicInfo;
import com.zjzmjr.core.model.user.StaffInfoQuery;
import com.zjzmjr.core.service.business.admin.AdminService;
import com.zjzmjr.core.service.business.area.AreaService;
import com.zjzmjr.core.service.business.company.CompanyAssociatedInfoService;
import com.zjzmjr.core.service.business.data.OutDataHandlerService;
import com.zjzmjr.core.service.business.project.GardenProjectService;
import com.zjzmjr.core.service.business.project.ProjectContractService;
import com.zjzmjr.core.service.business.user.ExternalPersonService;
import com.zjzmjr.core.service.business.user.StaffInfoService;

/**
 * 外部数据处理的业务层
 * 
 * @author oms
 * @version $Id: OutDataHandlerServiceImpl.java, v 0.1 2017-10-9 下午4:20:03 oms
 *          Exp $
 */
@Service("outDataHandlerService")
public class OutDataHandlerServiceImpl implements OutDataHandlerService {

    private static final Logger logger = LoggerFactory.getLogger(OutDataHandlerServiceImpl.class);

    private static Map<String, Integer> staffPersonMap = null;
    
    private static Map<String, Integer> areaMap = null;
    
    private static Map<String, Integer> awardCompanyMap = null;
    
    @Resource(name = "gardenProjectService")
    private GardenProjectService gardenProjectService;

    @Resource(name = "projectContractService")
    private ProjectContractService projectContractService;

    @Resource(name = "staffInfoService")
    private StaffInfoService staffInfoService;
    
    @Resource(name = "externalPersonService")
    private ExternalPersonService externalPersonService;
    
    @Resource(name = "adminService")
    private AdminService adminService;
    
    @Resource(name = "bankCardAreaService")
    private AreaService areaService;
    
    @Resource(name = "companyAssociatedInfoService")
    private CompanyAssociatedInfoService companyAssociatedInfoService;

    /**
     * 
     * @throws ApplicationException 
     * @see com.zjzmjr.core.service.business.data.OutDataHandlerService#importOutsideData(java.util.Map)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<String> importOutsideData(Map<String, String> dataMap, Integer handlerType) throws ApplicationException {
        ResultEntry<String> result = new ResultEntry<String>();
        if(Util.isNull(dataMap) || handlerType == null){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result; 
        }
        
        if (handlerType.intValue() == 1) {
            // 项目信息
            Assert.isTrue(StringUtils.isNotBlank(dataMap.get("name")), "项目名称不能为空");
            GardenProjectQuery query = new GardenProjectQuery();
            query.setName(dataMap.get("name"));
            query.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultEntry<GardenProject> grdProRst = gardenProjectService.getGardenProjectByIdAndStatus(query);
            Assert.isTrue(grdProRst.isSuccess() == false, "该项目已经存在，请勿重复导入");
            ResultEntry<Integer> projRst = gardenProjectService.insertGardenProject(evaluateProject(dataMap));
            if (projRst.isSuccess()) {
                // 合同信息
                ProjectContract contract = evaluateContract(dataMap);
                // contract = evaluateInstance(contract, dataMap);
                contract.setProjectId(projRst.getT());
                projRst = projectContractService.insertProjectContract(contract);
                if (!projRst.isSuccess()) {
                    throw new ApplicationException("项目合同数据导入失败");
                }
            }
        }else if(handlerType.intValue() == 2){
            // 关联公司信息
            Assert.isTrue(StringUtils.isNotBlank(dataMap.get("companyName")), "项目名称不能为空");
            CompanyAssociatedQuery query = new CompanyAssociatedQuery();
            query.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            query.setCompanyName(dataMap.get("companyName"));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<CompanyAssociatedInfo> compRst = companyAssociatedInfoService.getCompanyAssociatedInfo(query);
            Assert.isTrue(compRst.isSuccess() == false, "该关联公司已经存在，请勿重复导入");
            companyAssociatedInfoService.insertCompanyAssociated(evaluateCompanyAssociate(dataMap));
        }
        
        return result;
    }
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.data.OutDataHandlerService#clearMapData()
     */
    @Override
    public void clearMapData() {
        if(staffPersonMap == null || staffPersonMap.isEmpty()){
        }else{
            staffPersonMap.clear();
            staffPersonMap = null;
        }
        if(areaMap == null || areaMap.isEmpty()){
        }else{
            areaMap.clear();
            areaMap = null;
        }
        if(awardCompanyMap == null || awardCompanyMap.isEmpty()){
        }else{
            awardCompanyMap.clear();
            awardCompanyMap = null;
        }
    }
    
    /**
     * 赋值关联公司表
     * 
     * @param dataMap
     * @return
     */
    private CompanyAssociatedInfo evaluateCompanyAssociate(Map<String, String> dataMap) {
        // 关联公司信息
        CompanyAssociatedInfo company = new CompanyAssociatedInfo();
        company.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
        // 公司属性
        if ("建设单位".equals(dataMap.get("companyDistinguish"))) {
            company.setCompanyDistinguish(1);
        } else if ("施工单位".equals(dataMap.get("companyDistinguish"))) {
            company.setCompanyDistinguish(2);
        } else if ("设计院".equals(dataMap.get("companyDistinguish"))) {
            company.setCompanyDistinguish(3);
        }
        // 企业全称
        company.setCompanyName(dataMap.get("companyName"));
        // 法定代表人
        company.setLegalPerson(dataMap.get("legalPerson"));
        // 法定代表人联系方式
        company.setLegalMobile(dataMap.get("legalMobile"));
        // 所在城市
        company.setCityId(getAreaId(dataMap.get("cityId")));
        // 详细地址
        company.setAddress(dataMap.get("address"));
        // 企业介绍
        company.setIntroduction(dataMap.get("introduction"));
        // 开户银行
        company.setBankName(dataMap.get("bankName"));
        // 银行账号
        company.setAccountNo(dataMap.get("accountNo"));
        // 统一社会信用代码
        company.setLicenseNumber(dataMap.get("licenseNumber"));
        // 电话
        company.setMobile(dataMap.get("mobile"));
        // 传真
        company.setFaxPhone(dataMap.get("faxPhone"));
        // 状态
        company.setStatus(1);
        company.setCreateTime(new Date());
        company.setCreateUserId(StringUtil.stringToInteger(dataMap.get("createUserId")));
        company.setUpdateTime(company.getCreateTime());
        company.setUpdateUserId(company.getCreateUserId());

        return company;
    }
    
    /**
     * 赋值合同表
     * 
     * @param dataMap
     * @return
     */
    private ProjectContract evaluateContract(Map<String, String> dataMap){

        // 合同信息
        ProjectContract contract = new ProjectContract();
        contract.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
        // 合同编号
        contract.setContractNo(dataMap.get("contractNo"));
        // 合同金额
        contract.setContractCapital(NumberUtils.string2BigDecimal(dataMap.get("contractCapital")));
        // 签约日期
        contract.setSignDate(montageSignDate(dataMap.get("signDate")));
        // 合同备注
        contract.setContractMemo(dataMap.get("contractMemo"));
        // 院方比例
        contract.setRatio(NumberUtils.string2BigDecimal(dataMap.get("ratio")));
        // 比例备注
        contract.setRatioMemo(dataMap.get("ratioMemo"));
        contract.setStatus(1);
        contract.setCreateTime(new Date());
        contract.setCreateUserId(StringUtil.stringToInteger(dataMap.get("createUserId")));
        contract.setUpdateTime(contract.getCreateTime());
        contract.setUpdateUserId(contract.getCreateUserId());
        
        return contract;
    }
    
    /**
     * 赋值到项目表
     * 
     * @param dataMap
     * @return
     */
    private GardenProject evaluateProject(Map<String, String> dataMap){
        // 项目信息
        GardenProject project = new GardenProject();
        project.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
        project.setProjectNo(gardenProjectService.getGardenProjectMaxNo().getT());
        // 项目名称
        project.setName(dataMap.get("name"));
        // 项目介绍人
        project.setIntroducer(getIntroducerId(dataMap.get("introducer"), dataMap, 2));
        // 所在城市
        project.setCity(getAreaId(dataMap.get("city")));
        // 详细地址
        project.setAddress(dataMap.get("address"));
        // 设计面积(㎡)
        project.setDesignArea(StringUtil.stringToInteger(dataMap.get("designArea")));
        // 投资额(万元)
        project.setInvestmentMount(NumberUtils.string2BigDecimal(dataMap.get("investmentMount")));
        // 项目性质
        if("新建".equals(dataMap.get("nature"))){
            project.setNature(1);
        }else if("改造".equals(dataMap.get("nature"))){
            project.setNature(2);
        }
        // 项目类别
        if("景观".equals(dataMap.get("category"))){
            project.setCategory(1);
        }else if("规划".equals(dataMap.get("category"))){
            project.setCategory(2);
        }else if("建筑".equals(dataMap.get("category"))){
            project.setCategory(3);
        }else if("EPC".equals(dataMap.get("category"))){
            project.setCategory(4);
        }else if("旅游".equals(dataMap.get("category"))){
            project.setCategory(5);
        }
        // 项目类型
        if("市政公园".equals(dataMap.get("type"))){
            project.setType(1);
        }else if("市政道路".equals(dataMap.get("type"))){
            project.setType(2);
        }else if("市政河道".equals(dataMap.get("type"))){
            project.setType(3);
        }else if("地产住宅".equals(dataMap.get("type"))){
            project.setType(4);
        }else if("地产商业".equals(dataMap.get("type"))){
            project.setType(5);
        }else if("城乡规划".equals(dataMap.get("type"))){
            project.setType(6);
        }else if("特色城镇".equals(dataMap.get("type"))){
            project.setType(7);
        }
        // 经营部门
        if("经营一部".equals(dataMap.get("managementDepartment"))){
            project.setManagementDepartment(1);
        }else if("经营二部".equals(dataMap.get("managementDepartment"))){
            project.setManagementDepartment(2);
        }
        // 项目介绍
        project.setIntroduction(dataMap.get("introduction"));
        // 意向合作方式
        if("委托".equals(dataMap.get("intentionalCooperation"))){
            project.setIntentionalCooperation(62);
        }else if("邀标".equals(dataMap.get("intentionalCooperation"))){
            project.setIntentionalCooperation(63);
        }else if("公开招标".equals(dataMap.get("intentionalCooperation"))){
            project.setIntentionalCooperation(64);
        }else if("集团合作".equals(dataMap.get("intentionalCooperation"))){
            project.setIntentionalCooperation(74);
        }
        // 是否技术支持
        if("否".equals(dataMap.get("needTechnical"))){
            project.setNeedTechnical(0);
        }else if("是".equals(dataMap.get("needTechnical"))){
            project.setNeedTechnical(1);
        }
        // 前期技术支持要求
        project.setPreRequirements(dataMap.get("preRequirements"));
        // 发包单位
        project.setContractAwardCompany(getContractAwardCompany(dataMap));
        // 是否有方案设计
        if("否".equals(dataMap.get("haveScheme"))){
            project.setHaveScheme(0);
        }else if("是".equals(dataMap.get("haveScheme"))){
            project.setHaveScheme(1);
        }
        // 是否有扩初方案设计
        if("否".equals(dataMap.get("haveDevelopment"))){
            project.setHaveDevelopment(0);
        }else if("是".equals(dataMap.get("haveDevelopment"))){
            project.setHaveDevelopment(1);
        }
        // 是否有施工图设计
        if("否".equals(dataMap.get("haveDrawing"))){
            project.setHaveDrawing(0);
        }else if("是".equals(dataMap.get("haveDrawing"))){
            project.setHaveDrawing(1);
        }
        // 是否有规划设计
        if("否".equals(dataMap.get("havePlanning"))){
            project.setHavePlanning(0);
        }else if("是".equals(dataMap.get("havePlanning"))){
            project.setHavePlanning(1);
        }
        // 经营专员
        project.setManagementPersonnel(getIntroducerId(dataMap.get("managementPersonnel"), dataMap, 1));
        // 技术负责人
        project.setProjectLeader(getIntroducerId(dataMap.get("projectLeader"), dataMap, 3));
        // 合同或商务标技术负责人
        project.setProjectLiable(dataMap.get("projectLiable"));
        // 申请截止日期
        project.setApplyDeadline(dataMap.get("applyDeadline"));
        // 是否重点项目
        if("否".equals(dataMap.get("isMajorProject"))){
            project.setIsMajorProject(0);
        }else if("是".equals(dataMap.get("isMajorProject"))){
            project.setIsMajorProject(1);
        }
        // 招标报名截止日期
        project.setBiddingDeadline(dataMap.get("biddingDeadline"));
        // 招标备注
        project.setBiddingMemo(dataMap.get("biddingMemo"));
        // 报名文件制作人
        project.setBiddingDocumentsProducer(getIntroducerId(dataMap.get("biddingDocumentsProducer"), dataMap, 1));
        // 报名结果
        if("不成功".equals(dataMap.get("registrationResults"))){
            project.setRegistrationResults(0);
        }else if("成功".equals(dataMap.get("registrationResults"))){
            project.setRegistrationResults(1);
        }
        // 报名结果原因
        project.setRegistrationResultsMemo(dataMap.get("registrationResultsMemo"));
        // 是否有技术标
        if("否".equals(dataMap.get("haveTechnical"))){
            project.setHaveTechnical(0);
        }else if("是".equals(dataMap.get("haveTechnical"))){
            project.setHaveTechnical(1);
        }
        // 保证金截止日期
        project.setMarginDeadline(dataMap.get("marginDeadline"));
        // 投标截止日期
        project.setTenderDeadline(dataMap.get("tenderDeadline"));
        // 招标文件备注
        project.setBiddingDocumentsMemo(dataMap.get("biddingDocumentsMemo"));
        // 保证金
        project.setBail(NumberUtils.string2BigDecimal(dataMap.get("bail")));
        // 最晚汇款日期
        project.setLatestRemittanceDate(dataMap.get("latestRemittanceDate"));
        // 汇款单位
        project.setRemittanceCompany(dataMap.get("remittanceCompany"));
        // 开户银行
        project.setBankName(dataMap.get("bankName"));
        // 银行账号
        project.setAccountNo(dataMap.get("accountNo"));
        // 保证金备注
        project.setBailMemo(dataMap.get("bailMemo"));
        // 商务标制作人
        project.setBusinessProducer(getIntroducerId(dataMap.get("businessProducer"), dataMap, 1));
        // 投标报价
        project.setTenderOffer(NumberUtils.string2BigDecimal(dataMap.get("tenderOffer")));
        // 是否中标
        if(org.apache.commons.lang.math.NumberUtils.isDigits(dataMap.get("isWinBidding"))){
            project.setIsWinBidding(Integer.parseInt(dataMap.get("isWinBidding")));
        }else{
            if("未开标".equals(dataMap.get("isWinBidding"))){
                project.setIsWinBidding(0);
            }else if("中标".equals(dataMap.get("isWinBidding"))){
                project.setIsWinBidding(1);
            }else if("未中标".equals(dataMap.get("isWinBidding"))){
                project.setIsWinBidding(2);
            }
        }
        // 不通过理由
        project.setNopassReason(dataMap.get("nopassReason"));
        // 项目阶段
        Assert.isTrue(StringUtils.isNotBlank(dataMap.get("step")), "导入失败，项目阶段为空");
        if(org.apache.commons.lang.math.NumberUtils.isDigits(dataMap.get("step"))){
            project.setStep(Integer.parseInt(dataMap.get("step")));
        }else{
            GardenProjectStepEnum stepEnum = null;
            stepEnum = GardenProjectStepEnum.getByValue(dataMap.get("step"));
            if(stepEnum != null){
//                stepEnum  = GardenProjectStepEnum.P_640;
                project.setStep(stepEnum.getValue());
            }
        }
        // 状态
        if(org.apache.commons.lang.math.NumberUtils.isDigits(dataMap.get("status"))){
            project.setStatus(Integer.parseInt(dataMap.get("status")));
        }else{
            if("未归档".equals(dataMap.get("status"))){
                project.setStatus(0);
            }else if("中止".equals(dataMap.get("status"))){
                project.setStatus(1);
            }else if("终止".equals(dataMap.get("status"))){
                project.setStatus(2);
            }else if("临时".equals(dataMap.get("status"))){
                project.setStatus(3);
            }
        }
        project.setCreateTime(new Date());
        project.setCreateUserId(StringUtil.stringToInteger(dataMap.get("createUserId")));
        project.setUpdateTime(project.getCreateTime());
        project.setUpdateUserId(project.getCreateUserId());
        
        return project;
    }
    
    /**
     * 获取发包单位编号
     * 
     * @param dataMap
     * @param companyName
     * @return
     */
    private Integer getContractAwardCompany(Map<String, String> dataMap){
        Integer companyId = 0;
        if(awardCompanyMap == null || awardCompanyMap.isEmpty()){
            awardCompanyMap = new HashMap<String, Integer>();
            CompanyAssociatedQuery query = new CompanyAssociatedQuery();
            query.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<CompanyAssociatedInfo> result = companyAssociatedInfoService.getCompanyAssociatedInfo(query);
            if(result.isSuccess()){
                for(CompanyAssociatedInfo info : result.getList()){
                    awardCompanyMap.put(info.getCompanyName(), info.getId());
                }
            }
        }
        companyId = awardCompanyMap.get(dataMap.get("contractAwardCompany"));
        if(companyId == null){
            CompanyAssociatedInfo info = new CompanyAssociatedInfo();
            info.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            info.setCompanyName(dataMap.get("contractAwardCompany"));
            info.setCreateTime(new Date());
            info.setCreateUserId(0);
            info.setStatus(0);
            info.setUpdateTime(info.getCreateTime());
            info.setUpdateUserId(info.getCreateUserId());
            try {
                ResultEntry<Integer> result = companyAssociatedInfoService.insertCompanyAssociated(info);
                if(result.isSuccess()){
                    awardCompanyMap.put(dataMap.get("contractAwardCompany"), result.getT());
                    companyId = result.getT();
                }
            } catch (ApplicationException e) {
                logger.error("", e);
            }
        }
        return companyId;
    }
    
    /**
     * 获取地区编号
     * 
     * @param city
     * @return
     */
    private Integer getAreaId(String city){
        Integer areaId = null;
        if(areaMap == null || areaMap.isEmpty()){
            areaMap = new HashMap<String, Integer>();
            AreaQuery query = new AreaQuery();
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<Area> result = areaService.getAllArea(query);
            if(result.isSuccess()){
                for(Area area : result.getList()){
                    areaMap.put(area.getAreaName(), area.getId());
                }
            }
        }
        areaId = areaMap.get(city);
        if(areaId == null){
            // 当地址没有完全匹配的情况
            int shengIdx = city.indexOf("省");
            if(shengIdx < 0){
                shengIdx = city.indexOf("区");
            }
            int shiIdx = city.indexOf("市");
            if(shiIdx < 0){
                shiIdx = city.indexOf("县");
            }
            if(shengIdx > 0 && shiIdx > 0){
                city = city.substring(shengIdx + 1, shiIdx);
                for(Entry<String, Integer> entry : areaMap.entrySet()){
                    if(entry.getKey().contains(city)){
                        areaId = entry.getValue();
                        break;
                    }
                }
            }
        }
        Assert.isTrue(areaId != null, "所在城市输入有误");
        return areaId;
    }
    
    /**
     * 根据姓名获取用户的编号
     * 
     * @param dataMap
     * @param insertFlg 1 : 不存在是插入员工表  , 2 : 不存在是插入外部人员表
     * @return
     */
    private Integer getIntroducerId(String introducer, Map<String, String> dataMap, int insertFlg){
        Integer introducerId = 0;
        if(staffPersonMap == null || staffPersonMap.isEmpty()){
            staffPersonMap = new HashMap<String, Integer>();
            StaffInfoQuery query = new StaffInfoQuery();
            query.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            query.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
            ResultPage<StaffBasicInfo> staffRst = staffInfoService.getStaffInfoByCondition(query);
            if(staffRst.isSuccess()){
                for(int cnt=0;cnt<staffRst.getList().size();cnt++){
                    staffPersonMap.put(staffRst.getList().get(cnt).getUserInfo().getName(), staffRst.getList().get(cnt).getUserId());
                }
            }else{
                ExternalPersonQuery personQuery = new ExternalPersonQuery();
//                personQuery.setCompany(company)
//                personQuery.setName(dataMap.get("introducer"));
                personQuery.setPageBean(new PageBean(Integer.MAX_VALUE, 1));
                ResultPage<ExternalPersonInfo> personRst = externalPersonService.getExternalPersonByCondition(personQuery);
                if(personRst.isSuccess()){
                    for(int cnt=0;cnt<personRst.getList().size();cnt++){
                        staffPersonMap.put(personRst.getList().get(cnt).getUserInfo().getName(), personRst.getList().get(cnt).getUserId());
                    }
                }
            }
        }
        introducerId = staffPersonMap.get(introducer);
        if(introducerId == null){
            AdminCreate adminCreate = new AdminCreate();
            adminCreate.setCompanyId(StringUtil.stringToInteger(dataMap.get("companyId")));
            adminCreate.setCreateUser(0);
            adminCreate.setName(introducer);
            adminCreate.setPassword("123456");
            adminCreate.setUsername(ShareCodeUtil.randomCode(6));
            CompanyStaffPerson companyStaffPerson = new CompanyStaffPerson();
            if(insertFlg == 1){
                // 插入员工表信息
                adminCreate.setJobId(4);
                List<StaffBasicInfo> staffList = new ArrayList<StaffBasicInfo>();
                StaffBasicInfo info = new StaffBasicInfo();
                info.setCreateTime(new Date());
                info.setDepartId(2);
                info.setJobStatus(AdminJobStatusEnum.NONDUTY.getValue());
                staffList.add(info);
                companyStaffPerson.setStaff(staffList);
            }else if(insertFlg == 2 || insertFlg == 3){
                // 插入外部人员信息
                List<ExternalPerson> personList = new ArrayList<ExternalPerson>();
                ExternalPerson person = new ExternalPerson();
                person.setEmployeeNo(externalPersonService.getExternalPersonEmployeeMaxNo().getT());
                person.setCreateTime(new Date());
                if (insertFlg == 2) {
                    // 经营专员
                    person.setPersonnelNature(45);
                } else {
                    // 设计人员
                    person.setPersonnelNature(44);
                }
                person.setStatus(1);
                personList.add(person);
                companyStaffPerson.setPerson(personList);
            }
            ResultEntry<Integer> result = adminService.create(adminCreate, companyStaffPerson);
            staffPersonMap.put(introducer, result.getT());
            introducerId = result.getT();
        }
        
        return introducerId;
    }

    /**
     * 
     * 
     * @param signDate
     * @return
     */
    private String montageSignDate(String signDate) {
        StringBuilder montageDate = new StringBuilder();
        if (signDate.indexOf(".") >= 0) {
            String[] signs = signDate.split("\\.");
            Assert.isTrue(signs.length == 3, "合同签约日期不对");
            montageDate.append(signs[0]);
            if (signs[1].length() < 2) {
                montageDate.append("0").append(signs[1]);
            } else {
                montageDate.append(signs[1]);
            }
            if (signs[2].length() < 2) {
                montageDate.append("0").append(signs[2]);
            } else {
                montageDate.append(signs[2]);
            }
        } else {
            montageDate.append(signDate);
        }
        return montageDate.toString();
    }
    
    /**
     * 从map中赋值给对象
     * 
     * @param dataMap
     * @return
     */
    public <T> T evaluateInstance(T obj, Map<String, String> dataMap) {
        Class<? extends Object> clazz = obj.getClass();
        Field field = null;
        for (Map.Entry<String, String> entry : dataMap.entrySet()) {
            try {
                field = clazz.getField(entry.getKey());
                if (field != null) {
                    field.setAccessible(true);
                    field.set(obj, entry.getValue());
                }
            } catch (NoSuchFieldException e) {
                logger.error("", e);
            } catch (SecurityException e) {
                logger.error("", e);
            } catch (IllegalArgumentException e) {
                logger.error("", e);
            } catch (IllegalAccessException e) {
                logger.error("", e);
            }
        }

        return obj;
    }

}
