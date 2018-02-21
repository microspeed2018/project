package com.zjzmjr.core.service.mapper.dao.company;

import java.util.List;

import com.zjzmjr.core.model.company.CompanyAssociatedContact;


public interface CompanyAssociatedContactMapper {

    /**
     * 插入关联公司联系人
     * 
     * @param companyAssociated
     * @return
     */
    int insertCompanyAssociatedContact(CompanyAssociatedContact companyAssociatedContact);
    
    /**
     * 更新关联公司联系人
     * 
     * @param companyAssociatedContact
     * @return
     */
    int updateCompanyAssociatedContactById(CompanyAssociatedContact companyAssociatedContact);
    
    /**
     * 通过关联公司id删除关联公司联系人
     * 
     * @param companyId
     * @return
     */
    int deleteByCompanyId(Integer associatedCompanyId);
    
    /**
     * 关联公司id获取关联公司联系人
     * 
     * @param associateCompany
     * @return
     */
    List<CompanyAssociatedContact>getCompanyAssociatedContactByAssociateCompany(Integer associateCompany);
}
