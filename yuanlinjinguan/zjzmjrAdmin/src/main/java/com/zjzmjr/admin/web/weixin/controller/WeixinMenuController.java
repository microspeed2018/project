package com.zjzmjr.admin.web.weixin.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.zjzmjr.admin.web.weixin.form.Attributes;
import com.zjzmjr.admin.web.weixin.form.TreeForm;
import com.zjzmjr.core.api.weixin.IWeixinMenuFacade;
import com.zjzmjr.core.base.result.ResultEntry;
import com.zjzmjr.core.common.biz.weixin.WeixinMessageDigestUtil;
import com.zjzmjr.core.model.weixin.MenuForm;
import com.zjzmjr.core.model.weixin.wechat.menu.Button;
import com.zjzmjr.core.model.weixin.wechat.menu.ClickButton;
import com.zjzmjr.core.model.weixin.wechat.menu.Menu;
import com.zjzmjr.core.model.weixin.wechat.menu.ViewButton;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 微信菜单管理
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/weixinmenu")
public class WeixinMenuController extends BaseController{
	
	private static final Logger logger=LoggerFactory.getLogger(WeixinMenuController.class);
	
	private static final String defaultPage="/WEB-INF/pages/weixin/menu.jsp";
	
	@Resource(name="weixinMenuFacade")
	private IWeixinMenuFacade weixinMenuFacade;
	
	/**
	 * 默认页面
	 * @return
	 */
	@RequestMapping(value="/index.htm",method=RequestMethod.GET)
	public String defaultPage(){
		return defaultPage;
	}
	
	/**
	 * 获取所有微信菜单生成菜单树
	 * @param resp
	 */
	@RequestMapping(value="/getMenuList.htm",method=RequestMethod.POST)
	public void getMenuList(HttpServletResponse resp){
	    logger.debug("getMenuList 执行开始  入参:{}");
		Map<String, Object> map=new HashMap<String, Object>();
		try{
			ResultEntry<List<MenuForm>> result= weixinMenuFacade.getMenuList();
			if(result.isSuccess()){
				putSuccess(map, "");
				List<MenuForm> list=result.getT();
				//构造菜单树
				if(list!=null){
					List<TreeForm> treeFormList=new ArrayList<TreeForm>();
					for(MenuForm form:list){
						//一级菜单处理
						TreeForm treeForm =new TreeForm();
						Attributes attributes=new Attributes();
						treeForm.setId(form.getId());
						treeForm.setText(form.getName());
						attributes.setOrder(form.getOrder());
						attributes.setType(form.getType());
						switch (form.getType()) {
						case "click":
							attributes.setKey(form.getKey());
							attributes.setContent(form.getContent());
							break;
						case "view":
							attributes.setUrl(form.getUrl());
							break;
						default:
							break;
						}
						treeForm.setAttributes(attributes);
						List<MenuForm> subList=form.getMid();
						List<TreeForm> subTreeFormList=new ArrayList<TreeForm>();
						for(MenuForm subForm:subList){
							//二级菜单处理
							TreeForm subTreeForm =new TreeForm();
							Attributes subAttributes=new Attributes();
							subTreeForm.setId(subForm.getId());
							subTreeForm.setText(subForm.getName());
							subAttributes.setOrder(subForm.getOrder());
							subAttributes.setType(subForm.getType());
							switch (subForm.getType()) {
							case "click":
								subAttributes.setKey(subForm.getKey());
								subAttributes.setContent(subForm.getContent());
								break;
							case "view":
								subAttributes.setUrl(subForm.getUrl());
								break;
							default:
								break;
							}
							subTreeForm.setAttributes(subAttributes);
							subTreeFormList.add(subTreeForm);
						}
						treeForm.setChildren(subTreeFormList);
						treeFormList.add(treeForm);
					}
					map.put("data",treeFormList);
				}else{
					putError(map, "暂无数据");
				}
			}else{
				putError(map, result.getErrorCode(),result.getErrorMsg());
			}
		}catch(Exception ex){
			putError(map, ex.getMessage());
			logger.error("菜单查询出错",ex);
		}
		logger.debug("getMenuList 执行结束  出参:{}", map);
		responseAsJson(map, resp);
	}
	
	/**
	 * 更新菜单
	 * @param form
	 * @param resp
	 */
	@RequestMapping(value="/updateMenu.htm",method=RequestMethod.POST)
	public void updateMenu(MenuForm form,HttpServletResponse resp){
	    logger.debug("updateMenu 执行开始  入参:{}", form);
		Map<String, Object> model=new HashMap<String,Object>();
		try{
			ResultEntry<Integer> result=  weixinMenuFacade.updateMenu(form);
			logger.debug("updateMenu 执行结束  出参:{}", result);
			if(result.isSuccess()){
				putSuccess(model, "");
			}else{
				putError(model, result.getErrorCode(),result.getErrorMsg());
			}
		}catch(Exception ex){
			putError(model, ex.getMessage());
			logger.error("菜单更新出错",ex);
		}
		responseAsJson(model, resp);
	}
	
	/**
	 * 删除菜单
	 * @param pid
	 * @param id
	 * @param resp
	 */
	@RequestMapping(value="/deleteMenu.htm",method=RequestMethod.POST)
	public void deleteMenu(@RequestParam(value="pid") Integer pid,@RequestParam("id") Integer id,HttpServletResponse resp){
	    logger.debug("deleteMenu 执行开始  入参:{}{}", pid,id);
		Map<String, Object> model=new HashMap<String,Object>();
		try{
			ResultEntry<Integer> result= weixinMenuFacade.deleteMenu(pid,id);
			logger.debug("deleteMenu 执行结束  出参:{}", result);
			if(result.isSuccess()){
				putSuccess(model, "");
			}else{
				putError(model, result.getErrorCode(),result.getErrorMsg());
			}
		}catch(Exception ex){
			putError(model, ex.getMessage());
			logger.error("菜单删除出错",ex);
		}
		responseAsJson(model, resp);
	}
	
	/**
	 * 添加菜单
	 * @param menu
	 * @param resp
	 */
	@RequestMapping(value="/addMenu.htm",method=RequestMethod.POST)
	public void addMenu(MenuForm menu,HttpServletResponse resp){
	    logger.debug("addMenu 执行开始  入参:{}", menu);
		Map<String, Object> model = new HashMap<String, Object>();
        try {
            ResultEntry<Integer> result= weixinMenuFacade.addMenu(menu);
            if (result.isSuccess()) {
                putSuccess(model, "");
            } else {
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("菜单添加出错", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("addMenu 执行结束  出参:{}", model);
	    responseAsJson(model, resp);
	}
	
	/**
	 * 添加微信菜单
	 */
	@RequestMapping(value="/addWeixinMenu.htm",method=RequestMethod.POST)
	public void addMenu(HttpServletResponse resp){
	    logger.debug("addMenu 执行开始  入参:{}");
		Map<String, Object> model = new HashMap<String, Object>();
        try {
        	ResultEntry<List<MenuForm>> entry= weixinMenuFacade.getMenuList();
        	if(entry.isSuccess()){
        		List<MenuForm> forms=entry.getT();
        		Menu menu=new Menu();
        		List<Button> buttons=new LinkedList<Button>();
        		for(MenuForm form:forms){
        			String type=form.getType();
        			if(type.equals("click")){
        				ClickButton button=new ClickButton();
        				button.setKey(form.getKey());
        				button.setName(form.getName());
        				buttons.add(button);
        			}else if(type.equals("view")){
        				ViewButton button=new ViewButton();
        				button.setName(form.getName());
        				button.setUrl(form.getUrl());
        				buttons.add(button);
        			}else{
        				Button button=new Button();
        				button.setName(form.getName());
        				List<Button> subButtons=new LinkedList<Button>();
        				for(MenuForm subForm:form.getMid()){
        					String subtype=subForm.getType();
                			if(subtype.equals("click")){
                				ClickButton subbutton=new ClickButton();
                				subbutton.setKey(subForm.getKey());
                				subbutton.setName(subForm.getName());
                				subButtons.add(subbutton);
                			}else if(subtype.equals("view")){
                				ViewButton subbutton=new ViewButton();
                				subbutton.setName(subForm.getName());
                				subbutton.setUrl(subForm.getUrl());
                				subButtons.add(subbutton);
                			}
        				}
        				button.setSub_button(subButtons);
        				buttons.add(button);
        			}
        		}
        		menu.setButton(buttons);
        		ResultEntry<Integer> result= weixinMenuFacade.addWeixinMenu(menu, WeixinMessageDigestUtil.getAccessToken());
                if (result.isSuccess()) {
                    putSuccess(model, "");
                } else {
                    putError(model, result.getErrorCode(), result.getErrorMsg());
                }
        	}else {
        		putError(model, entry.getErrorCode(), entry.getErrorMsg());
			}
        } catch (Exception ex) {
            logger.error("微信菜单添加出错", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("addMenu 执行结束  出参:{}", model);
	    responseAsJson(model, resp);
	}
}
