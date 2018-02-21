package com.zjzmjr.core.service.mapper.dao.project;

import java.util.List;

import com.zjzmjr.core.model.project.ContractQuery;
import com.zjzmjr.core.model.project.ContractSubpackage;
import com.zjzmjr.core.model.project.ContractSubpackageInfo;
import com.zjzmjr.core.model.project.ContractSubpackageQuery;

/**
 * 项目分包表
 * 
 * @author oms
 * @version $Id: ContractSubpackageMapper.java, v 0.1 2017-9-3 下午4:54:09 oms Exp $
 */
public interface ContractSubpackageMapper {
    
    int insertContractSubpackage(ContractSubpackage record);

    int updateContractSubpackageById(ContractSubpackage record);
     
    /**
     * 条件查询分包表
     * 
     * @param query
     * @return
     */
    List<ContractSubpackage> getContractSubpackageByCondition(ContractSubpackageQuery query);
    
    /**
     * 删除分包信息
     * 
     * @param query
     * @return
     */
    int deleteSubpackageByCondition(ContractQuery query);

    /**
     * 分包信息查询
     * 
     * @param query
     * @return
     */
    List<ContractSubpackageInfo> getContractSubpackageInfo(ContractSubpackageQuery query);
}