package com.zjzmjr.core.service.business.recruitment.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.page.PageBean;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.base.result.ResultPage;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.recruitment.Recruitment;
import com.zjzmjr.core.model.recruitment.RecruitmentInfo;
import com.zjzmjr.core.model.recruitment.RecruitmentQuery;
import com.zjzmjr.core.service.business.recruitment.RecruitmentService;
import com.zjzmjr.core.service.mapper.dao.recruitment.RecruitmentMapper;

@Service("recruitmentService")
public class RecruitmentServiceImpl implements RecruitmentService{

    private static final Logger logger = LoggerFactory.getLogger(RecruitmentServiceImpl.class);

    /** 招聘信息 */
    @Resource(name = "recruitmentMapper")
    private RecruitmentMapper recruitmentMapper;
    
    /**
     * 
     * @see com.zjzmjr.core.service.business.recruitment.RecruitmentService#insertRecruitment(com.zjzmjr.core.model.recruitment.Recruitment)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> insertRecruitment(Recruitment recruitment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(recruitment) || Util.isNull(recruitment.getDepartmentId()) || Util.isNull(recruitment.getPositionName())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = recruitmentMapper.insertRecruitment(recruitment);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":招聘信息表插入失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.recruitment.RecruitmentService#updateRecruitment(com.zjzmjr.core.model.recruitment.Recruitment)
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Throwable.class)
    public ResultEntry<Integer> updateRecruitment(Recruitment recruitment) {
        ResultEntry<Integer> result = new ResultEntry<Integer>();
        if(Util.isNull(recruitment) || Util.isNull(recruitment.getDepartmentId()) || Util.isNull(recruitment.getPositionName())
                ||Util.isNull(recruitment.getId())){
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;  
        }
        int cnt = recruitmentMapper.updateRecruitment(recruitment);
        if(cnt>0){
            result.setSuccess(true);
            result.setT(cnt);
        }else{
            result.setSuccess(false);
            result.setErrorCodeEnum(ErrorCodeEnum.DB_OPR_ERROR);
            logger.error(ErrorCodeEnum.DB_OPR_ERROR.getName() + ":招聘信息表修改失败");
        }
        return result;
    }

    /**
     * 
     * @see com.zjzmjr.core.service.business.recruitment.RecruitmentService#getRecruitment(com.zjzmjr.core.model.recruitment.RecruitmentQuery)
     */
    @Override
    public ResultPage<RecruitmentInfo> getRecruitment(RecruitmentQuery query) {
        ResultPage<RecruitmentInfo> result = new ResultPage<RecruitmentInfo>();
        if (Util.isNull(query)) {
            result.setSuccess(false);
            result.setErrorMsg(ErrorCodeEnum.PARAM_ERROR.getName());
            result.setErrorCode(ErrorCodeEnum.PARAM_ERROR.getCode());
            logger.error(ErrorCodeEnum.PARAM_ERROR.getName());
            return result;
        }
        int total = recruitmentMapper.getRecruitmentCount(query);
        if (total > 0) {
            List<RecruitmentInfo> list = recruitmentMapper.getRecruitment(query);
            result.setSuccess(true);
            result.setList(list);
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
     * @see com.zjzmjr.core.service.business.recruitment.RecruitmentService#getRecruitmentByCondition(com.zjzmjr.core.model.recruitment.RecruitmentQuery)
     */
    @Override
    public ResultRecord<Recruitment> getRecruitmentByCondition(RecruitmentQuery query) {
        ResultRecord<Recruitment> result = new ResultRecord<Recruitment>();        
        List<Recruitment> list = recruitmentMapper.getRecruitmentByCondition(query);
        if(list==null || list.size()==0){            
            result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
            result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
            result.setSuccess(false);
        }else{
            result.setSuccess(true);
            result.setList(list);
        }
        return result;
    }

}
