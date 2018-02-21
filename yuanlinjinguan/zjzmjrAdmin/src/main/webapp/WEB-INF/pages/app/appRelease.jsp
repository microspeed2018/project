<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>APP版本管理</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/appRelease.js?v=${ver}"></script>
    <script type="text/javascript">
    	$(function(){
    		$("#datagrid").datagrid({
    			url: "/appRelease/user/getAppReleases.htm",
    			title: 'APP版本信息列表',
	            showFooter: true,
	            pagePosition: 'bottom',
	            pagination: true,
	            rownumbers: true,
	            pageSize: 20,
	            fitColumns: true,
	            fit: true,
	            style: 'overflow:hidden',
	            singleSelect: true,
	            queryParams: {
            	},
            	columns: [[{
	                field: 'appVersion',
	                title: 'APP版本号',
	                align: 'center',
	                width: 80
	            },{
	                field: 'device',
	                title: '设备类型',
	                align: 'center',
	                width: 60,
	                formatter: function (value, row, index) {
	                    if(value == 2){
	                    	return "Android";
	                    }
	                    if(value == 3){
	                    	return "iPhone";
	                    }
	                    if(value == 10){
	                    	return "iPad";
	                    }
	                    if(value == 11){
	                    	return "Winphone";
	                    }
	                    return "";
	                }
	            },{
	                field: 'downloadUrl',
	                title: '下载地址',
	                align: 'center',
	                width: 160
	            }, {
	                field: 'createTime',
	                title: '发布时间',
	                align: 'center',
	                width: 80,
	                formatter: function (value, row, index) {
	                    return getDay(value.time, YYYYMMDD_HHMM);
	                }
	            }]],
	            toolbar: [{
	                iconCls: 'icon-add',
	                text: '发布App',
	                handler: function () {
		                if(${appReleaseAuth}){
			                  doEdit();
			            }else{
			                  $.messager.alert("APP发布", "您没有权限发布APP","warning");
			            }
	                }
	            }],
	            loadFilter: function (data) {
	                if (data.success && data.rows) {
	                    return data;
	                } else {
	                    nullData(data);
	                }
	                return data;
	            }
    		});
    	});

		function queryProduct(){
			var param = {};
			var device = $('#queryDevice').val();
			if(device != -1){
				param.device = device;
			}else{
				param.device = null;
			}
		    param.createTime=$('#queryCreateTime').datebox('getValue');
		    param.lastTime=$('#queryLastTime').datebox('getValue');
			$("#datagrid").datagrid("load", param);
		}

	    function queryProductReset() {
		    $(".query #form")[0].reset();
		    $(".query :input[type='hidden']").val("");
		}
    </script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="layout">
    <div data-options="region:'north',split:false,border:false"
         style="height:auto;">
        <div class="query">
            <form id="form" class="inner-q" onsubmit="return false;">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <th>设备类型</th>
                        <td>
                            <select type="text" id="queryDevice" name="device" class="select">
                            	<option value="-1">不限</option>
                            	<option value="2">Android</option>
                            	<option value="3">iPhone</option>
                            	<option value="5">iPad</option>
                            	<option value="6">Winphone</option>
                            </select>
                        </td>
                        <th>发布时间</th>
                        <td><input type="text" id="queryCreateTime" name="createTime"
                           class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"> ~
                           <input type="text" id="queryLastTime" name="lastTime"
                           class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"></td>
                       <td colspan="4"></td>
                    </tr>
                    <tr>
                        <td colspan="7"></td>
                        <td class="bar"><input type="button" class="btn"
                                               onclick="queryProduct()" value="搜索"/>&nbsp;<input
                                type="reset" class="btn" value="清空" onclick="queryProductReset()"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" class="data-area">
        <div id="datagrid"></div>
    </div>
    <input type="hidden" id="action" name="action" value="${action}">
</div>
<div id="productEdit"
     data-options="title:'添加产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
    <div class="dg-content">
        <form id="productForm" method="post" enctype="multipart/form-data">
            <table class="myTable" cellpadding="0" cellspacing="0" border="0">
                <tbody class="myTbody">
                </tbody>
            </table>
        </form>
    </div>
</div>
<div id="productDialog"
     data-options="title:'查看产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
    <div class="dg-content">
        <table class="viTable" cellpadding="0" cellspacing="0" border="0">
             <tbody class="viTbody">
             </tbody>
         </table>
    </div>
</div>
</body>
</html>