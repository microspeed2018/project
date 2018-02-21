package com.zjzmjr.core.api.weixin;




import java.util.List;

import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.model.weixin.MenuForm;
import com.zjzmjr.core.model.weixin.wechat.menu.Menu;

/**
 * 微信菜单管理接口
 * @author Administrator
 *
 */
public interface IWeixinMenuFacade {
	
	/**
	 * 获取所有微信菜单
	 * @return
	 */
	ResultEntry<List<MenuForm>> getMenuList();
	
	/**
	 * 更新微信菜单
	 * @param menu
	 * @return
	 */
	ResultEntry<Integer> updateMenu(MenuForm menu);
	
	/**
	 * 删除微信菜单
	 * @param pid
	 * @param id
	 * @return
	 */
	ResultEntry<Integer> deleteMenu(Integer pid,Integer id);
	
	/**
	 * 后台管理系统添加菜单
	 * @param menu
	 * @return
	 */
	ResultEntry<Integer> addMenu(MenuForm menu);
	
	/**
	 * 微信服务号添加微信菜单
	 * @return
	 */
	ResultEntry<Integer> addWeixinMenu(Menu menu,String access_token);
	
	/**
	 * 接收微信事件推送
	 */
	String eventHandler(String str);
	
	/**
	 * 接收用户发往微信公众号的消息
	 * 
	 * @param message
	 * @return
	 */
	String receiveCustomMessage(String message, String[] savePath);
}
