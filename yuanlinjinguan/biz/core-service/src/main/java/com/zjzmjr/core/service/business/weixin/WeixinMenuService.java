package com.zjzmjr.core.service.business.weixin;



import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MenuForm;

public interface WeixinMenuService {
	
	ResultEntry<List<MenuForm>> getMenuList();
	
	ResultEntry<Integer> updateMenu(MenuForm menu);
	
	ResultEntry<Integer> deleteMenu(Integer pid,Integer id);
	
	ResultEntry<Integer> addMenu(MenuForm menu);
}
