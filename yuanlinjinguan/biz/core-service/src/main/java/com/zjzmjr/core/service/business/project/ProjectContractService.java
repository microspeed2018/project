package com.zjzmjr.core.service.business.project;

import java.util.List;

import com.zjzmjr.core.base.exception.ApplicationException;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;

/**
 * 项目中所有合同业务处理层
 * 
 * @author oms
 * @version $Id: ProjectContractService.java, v 0.1 2017-8-24 下午4:20:56 oms Exp $
 */
public interface ProjectContractService {

    /**
     * 获取同种类型项目的最大合同编号并且加一之后的合同编号返回
     * 
     * @param query
     * @return
     */
    ResultEntry<String> getContractMaxNo(ContractQuery query);
    
    /**
     * 根据合同主键编号获取项目合同信息
     * 
     * @param id
     * @return
     */
    ResultEntry<ProjectContract> getProjectContractById(Integer id);

    /**
     * 根据条件查询项目合同及关联公司信息
     * 
     * @param query
     * @return
     */
    ResultPage<ProjectContractInfo> getProjectContractByCondition(ContractQuery query);

    /**
     * 根据公司及项目的主键， 更新合同表
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectContractByProjectId(ProjectContract record);

    /**
     * 根据合同主键编号更新项目合同信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> updateProjectContractById(ProjectContract record);

    /**
     * 插入项目合同信息
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertProjectContract(ProjectContract record);

    /**
     * 插入合同信息及更新项目信息数据
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> generateProjectContractInfo(ProjectContract contract, GardenProject project,  List<ContractPayment> list) throws ApplicationException;
    
    /**
     * 修改合同信息及更新项目信息数据
     * 
     * @param contract
     * @param project
     * @param list
     * @return
     * @throws ApplicationException
     */
    ResultEntry<Integer> updateProjectContractInfo(ProjectContract contract, GardenProject project,  List<ContractPayment> list) throws ApplicationException;

    /**
     * 项目id、状态查询合同信息
     * 
     * @param query
     * @return
     */
    ResultRecord<ProjectContract> getProjectContractByProjectIdAndStatus(ContractQuery query);

    /**
     * 根据合同付款表信息查询出开发票信息
     * 
     * @param query
     * @return
     */
    ResultPage<ContractPaymentInfo> getProjectContractPaymentInfo(ContractPaymentQuery query);
    
    /**
     * 申请合作比例(step 380)
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> applyRatio(ProjectContract contract) throws ApplicationException;

    /**
     * 根据条件查询项目合同(一览)
     * 
     * @param query
     * @return
     */
    ResultPage<ProjectContractInfo> getProjectContractList(ContractQuery query);
    
    /**
     * 条件查询合同付款内容
     * 
     * @param query
     * @return
     */
    ResultEntry<ContractPayment> getContractPaymentByCondition(ContractPaymentQuery query);
}
