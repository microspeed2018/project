package com.zjzmjr.core.api.project;

import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.model.project.ContractPayment;
import com.zjzmjr.core.model.project.ContractPaymentInfo;
import com.zjzmjr.core.model.project.ContractPaymentQuery;
import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.GardenProject;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;
import com.zjzmjr.core.model.project.SubpackagePayment;

/**
 * 项目合同及开标公司相关接口
 * 
 * @author oms
 * @version $Id: IProjectContractFacade.java, v 0.1 2017-8-27 下午10:54:28 oms Exp $
 */
public interface IProjectContractFacade {

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
     * 修改或插入合同信息及更新项目信息数据
     * 
     * @param query
     * @return
     */
    ResultEntry<Integer> generateProjectContractInfo(ProjectContract contract, GardenProject project, List<ContractPayment> list);
    
    /**
     * 根据条件查询项目合同及关联公司信息
     * 
     * @param query
     * @return
     */
    ResultPage<ProjectContractInfo> getProjectContractByCondition(ContractQuery query);

    /**
     * 插入项目合同分包表数据
     * 
     * @param record
     * @return
     */
    ResultEntry<Integer> insertContractSubpackage(ContractSubpackage record,List<SubpackagePayment> payLst);
    
    /**
     * 修改合同信息及更新项目信息数据
     * 
     * @param contract
     * @param project
     * @param list
     * @return
     */
    ResultEntry<Integer> updateProjectContractInfo(ProjectContract contract, GardenProject project,  List<ContractPayment> list);

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
     * @param contract
     * @return
     */
    ResultEntry<Integer> applyRatio(ProjectContract contract);
    
    /**
     * 项目id、状态查询合同信息
     * 
     * @param query
     * @return
     */
    ResultRecord<ProjectContract> getProjectContractByProjectIdAndStatus(ContractQuery query);
    
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
