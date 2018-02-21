package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ProjectContract;
import com.zjzmjr.core.model.project.ProjectContractInfo;

/**
 * 项目合同表
 * 
 * @author oms
 * @version $Id: ProjectContractMapper.java, v 0.1 2017-8-23 下午10:02:20 oms Exp $
 */
public interface ProjectContractMapper {

    /**
     * 获取同种类型项目的最大合同编号
     * 
     * @param query
     * @return
     */
    String getContractMaxNo(ContractQuery query);
    
    int deleteProjectContractById(Integer id);

    int insertProjectContract(ProjectContract record);

    ProjectContract getProjectContractById(Integer id);

    int updateProjectContractById(ProjectContract record);
    
    /**
     * 根据公司及项目的主键， 更新合同表
     * 
     * @param record
     * @return
     */
    int updateProjectContractByProjectId(ProjectContract record);

    /**
     * 根据条件查询项目合同及关联公司信息件数
     * 
     * @param query
     * @return
     */
    int getProjectContractCount(ContractQuery query);
    
    /**
     * 根据条件查询项目合同及关联公司信息
     * 
     * @param query
     * @return
     */
    List<ProjectContractInfo> getProjectContractByCondition(ContractQuery query);
    
    /**
     * 项目id状态查询合同
     * 
     * @param query
     * @return
     */
    List<ProjectContract> getProjectContractByProjectIdAndStatus(ContractQuery query);
    
    /**
     * 根据条件查询项目合同(一览)
     * 
     * @param query
     * @return
     */
    List<ProjectContractInfo> getProjectContractList(ContractQuery query);
    
}