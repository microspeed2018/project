package com.zjzmjr.finance.web.area;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.zjzmjr.core.api.area.IAreaFacade;
import com.zjzmjr.core.base.constants.GenerateConstants;
import com.zjzmjr.core.base.model.AreaKeyValue;
import com.zjzmjr.core.base.model.AreaKeyValueInfo;
import com.zjzmjr.core.base.model.KeyValuePairInfo;
import com.zjzmjr.core.base.result.ResultRecord;
import com.zjzmjr.core.base.util.Util;
import com.zjzmjr.core.model.area.Area;
import com.zjzmjr.web.mvc.controller.BaseController;

/**
 * 客户端地区控制层
 * 
 * @author lenovo
 * @version $Id: AreaController.java, v 0.1 2017-9-13 下午7:01:19 lenovo Exp $
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseController{

 private static final Logger logger = LoggerFactory.getLogger(AreaController.class);
    
    @Resource(name = "areaFacade")
    private IAreaFacade areaFacade;
    
    /**
     * 获取所有地区（省市区分层）
     * 
     * @param resp
     */
    @RequestMapping(value = "/getAllNameArea.htm", method = RequestMethod.POST)
    public void getAllNameArea(HttpServletResponse resp){
        logger.debug("getAllNameArea 执行开始  入参:{}");
        Map<String, Object> model = new HashMap<String, Object>(); 
        try {
            ResultRecord<Area> result=areaFacade.getAllNameArea();            
            if(result.isSuccess()){
                List<AreaKeyValue> children = new ArrayList<AreaKeyValue>();
                List<KeyValuePairInfo> citytLst = new ArrayList<KeyValuePairInfo>();
                List<AreaKeyValueInfo> provtLst = new ArrayList<AreaKeyValueInfo>();
                
                KeyValuePairInfo city = new KeyValuePairInfo();
                AreaKeyValueInfo prov = new AreaKeyValueInfo();
                String provName = null;
                String cityName = null;
                for(int i=0;i<result.getList().size();i++){
                    if(GenerateConstants.number_0.equals(i)){
                        prov.setText(result.getList().get(0).getProvName());
                        prov.setValue(result.getList().get(0).getId());
                        city.setText(result.getList().get(0).getCityName());
                        city.setValue(result.getList().get(0).getId());
                        AreaKeyValue pair = new AreaKeyValue();
                        pair.setValue(result.getList().get(0).getId());
                        pair.setText("不限");
                        children.add(pair);
                        provName = result.getList().get(0).getProvName();
                        cityName = result.getList().get(0).getCityName();
                    }else{
                        if(provName.equals(result.getList().get(i).getProvName())){
                           if(cityName.equals(result.getList().get(i).getCityName())){
                               AreaKeyValue pair = new AreaKeyValue();
                               pair.setValue(result.getList().get(i).getId());
                               if(Util.isNotNull(result.getList().get(i).getAreaName())){
                                   pair.setText(result.getList().get(i).getAreaName());
                               }else{
                                   pair.setText("不限"); 
                               }                               
                               children.add(pair);
                               provName = result.getList().get(i).getProvName();
                               cityName = result.getList().get(i).getCityName();
                           }else{
                               city.setChildren(children);
                               citytLst.add(city);
                               children = new ArrayList<AreaKeyValue>();
                               city=new KeyValuePairInfo();
                               if(Util.isNotNull(result.getList().get(i).getCityName())){
                                   city.setText(result.getList().get(i).getCityName());
                               }else{
                                   city.setText("不限"); 
                               }
                               city.setValue(result.getList().get(i).getId());
                               AreaKeyValue pair = new AreaKeyValue();
                               pair.setValue(result.getList().get(i).getId());
                               if(Util.isNotNull(result.getList().get(i).getAreaName())){
                                   pair.setText(result.getList().get(i).getAreaName());
                               }else{
                                   pair.setText("不限"); 
                               }
                               children.add(pair);
                               provName = result.getList().get(i).getProvName();
                               cityName = result.getList().get(i).getCityName();
                           }
                        }else{
                            city.setChildren(children);
                            citytLst.add(city);
                            prov.setChildren(citytLst);
                            provtLst.add(prov);
                            children = new ArrayList<AreaKeyValue>();
                            citytLst = new ArrayList<KeyValuePairInfo>();
                            prov = new AreaKeyValueInfo();
                            city = new KeyValuePairInfo();
                            prov.setText(result.getList().get(i).getProvName());
                            prov.setValue(result.getList().get(i).getId());
                            if(Util.isNotNull(result.getList().get(i).getCityName())){
                                city.setText(result.getList().get(i).getCityName());
                            }else{
                                city.setText("不限"); 
                            }
                            city.setValue(result.getList().get(i).getId());
                            AreaKeyValue pair = new AreaKeyValue();
                            pair.setValue(result.getList().get(i).getId());
                            if(Util.isNotNull(result.getList().get(i).getAreaName())){
                                pair.setText(result.getList().get(i).getAreaName());
                            }else{
                                pair.setText("不限"); 
                            }
                            children.add(pair);
                            provName = result.getList().get(i).getProvName();
                            cityName = result.getList().get(i).getCityName();
                        }
                    }
                   
                }
                    city.setChildren(children);
                    citytLst.add(city);
                    prov.setChildren(citytLst);
                    provtLst.add(prov); 
                model.put("data", provtLst);
                putSuccess(model, "");
            }else{
                putError(model, result.getErrorCode(), result.getErrorMsg());
            }
        } catch (Exception ex) {
            logger.error("获取获取所有地区（省市区分层）失败", ex);
            putError(model, ex.getMessage());
        }
        logger.debug("getAllNameArea 执行结束  出参:{}", model);
        responseAsJson(model, resp);
    }
}
