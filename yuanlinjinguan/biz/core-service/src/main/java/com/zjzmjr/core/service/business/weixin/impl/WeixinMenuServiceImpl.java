package com.zjzmjr.core.service.business.weixin.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zjzmjr.core.base.enums.ErrorCodeEnum;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MenuForm;
import com.zjzmjr.core.service.business.weixin.WeixinMenuService;
import com.zjzmjr.core.service.mapper.dao.coredb.weixin.WeixinMenuMapper;

@Service("weixinMenuService")
public class WeixinMenuServiceImpl implements WeixinMenuService{

    private static final Logger logger = LoggerFactory.getLogger(WeixinMenuServiceImpl.class);
    
	@Resource(name="weixinMenuMapper")
	private WeixinMenuMapper weixinMenuMapper;
	
	/**
	 * 
	 * @see com.zjzmjr.loan.service.business.weixin.WeixinMenuService#getMenuList()
	 */
	@Override
	public ResultEntry<List<MenuForm>> getMenuList() {
	    logger.debug("getMenuList 执行开始  入参:{}", "");
		ResultEntry<List<MenuForm>> result=new ResultEntry<List<MenuForm>>();
		try{
			List<MenuForm> list=weixinMenuMapper.getMenuList();
			if(list!=null){
				result.setSuccess(true);
				result.setT(list);
			}else{
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.RECORD_NOT_EXSIST.getCode());
				result.setErrorMsg(ErrorCodeEnum.RECORD_NOT_EXSIST.getName());
			}
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		logger.debug("getMenuList 执行结束  出参:{}", result);
		return result;
	}

	/**
	 * 
	 * @see com.zjzmjr.loan.service.business.weixin.WeixinMenuService#updateMenu(com.zjzmjr.core.model.weixin.MenuForm)
	 */
	@Override
	@Transactional
	public ResultEntry<Integer> updateMenu(MenuForm menu) {
	    logger.debug("updateMenu 执行开始  入参:{}", menu);
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			int count=weixinMenuMapper.updateMenu(menu);
			if(count>0){
				result.setSuccess(true);
			}else if(count<=0){
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
				result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
			}
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		logger.debug("updateMenu 执行结束  出参:{}", result);
		return result;
	}

	/**
	 * 
	 * @see com.zjzmjr.loan.service.business.weixin.WeixinMenuService#deleteMenu(java.lang.Integer, java.lang.Integer)
	 */
	@Override
	@Transactional
	public ResultEntry<Integer> deleteMenu(Integer pid,Integer id) {
	    logger.debug("deleteMenu 执行开始  入参:{}{}", pid, id);
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			int count=weixinMenuMapper.deleteMenu(pid, id);
			if(count>0){
				result.setSuccess(true);
			}else if(count<=0){
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
				result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
			}
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		logger.debug("deleteMenu 执行结束  出参:{}", result);
		return result; 
	}

	/**
	 * 
	 * @see com.zjzmjr.loan.service.business.weixin.WeixinMenuService#addMenu(com.zjzmjr.core.model.weixin.MenuForm)
	 */
	@Override
	@Transactional
	public ResultEntry<Integer> addMenu(MenuForm menu) {
	    logger.debug("addMenu 执行开始  入参:{}", menu);
		ResultEntry<Integer> result=new ResultEntry<Integer>();
		try{
			int count=weixinMenuMapper.addMenu(menu);
			if(count>0){
				result.setSuccess(true);
			}else if(count<=0){
				result.setSuccess(false);
				result.setErrorCode(ErrorCodeEnum.DB_OPR_ERROR.getCode());
				result.setErrorMsg(ErrorCodeEnum.DB_OPR_ERROR.getName());
			}
		}catch(Exception ex){
			result.setSuccess(false);
			result.setErrorCode(ErrorCodeEnum.SYSTEM_ERROR.getCode());
			result.setErrorMsg(ErrorCodeEnum.SYSTEM_ERROR.getName());
		}
		logger.debug("addMenu 执行结束  出参:{}", result);
		return result;
	}

}
