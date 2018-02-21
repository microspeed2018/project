package com.zjzmjr.core.service.mapper.dao.coredb.weixin;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.zjzmjr.core.model.weixin.MenuForm;

public interface WeixinMenuMapper {
	
	List<MenuForm> getMenuList();
	
	int updateMenu(MenuForm form);
	
	int deleteMenu(@Param("pid") Integer pid,@Param("id") Integer id);
	
	int addMenu(MenuForm form);
}
