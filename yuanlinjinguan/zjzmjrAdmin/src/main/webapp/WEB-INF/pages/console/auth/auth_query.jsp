<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>权限查询</title>
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/gray/easyui.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_css }/common/common.css?v=${ver}" />
<script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${res_js }/common/common.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/jslib/admin.js?v=${ver}"></script>
<script type="text/javascript">
	$(function() {
		$("#datagrid").datagrid(
						{
							url : '${ctx}/console/auth.htm',
							title:'权限列表',
							fit:true,
							showFooter : true,
							pagePosition : 'bottom',
							pagination : true,
							rownumbers:true,
							pageSize : 20,
							width : 'auto',
							fitColumns : true,
							style : 'overflow:auto',
							singleSelect : true,
							queryParams : {
								action : "queryByPage"
							},
							pageList : [ 10,20, 30],
							columns :[[
									{
										field : 'id',
										title : 'ID',
										align : 'center',
										width : 100
									},
									{
										field : 'code',
										title : '权限代码',
										align : 'center',
										width : 150
									},
									{
										field : 'name',
										title : '权限名称',
										align : 'center',
										width : 150
									},
									{
										field : 'type',
										title : '类型',
										align : 'center',
										width : 150,
										formatter: function(value,row,index){
											<c:forEach items="${typeEnums }" var="type">
												if(${type.value}==value){
													return '${type.message}';
												}
											</c:forEach>
											return "";
										}
									}
									<c:if test="${authUpdateAuth}">
									,{
										field : 'auth',
										title : '操作',
										width : 120,
										align : 'center',
										formatter: function(value,row,index){
											return "<a href='javascript:deleteAuth("+row.id+")'>删除</a>";
										}
									}
									</c:if>
									]]
							<c:if test="${authAddAuth}">
								,toolbar: [{
											iconCls: 'icon-add',
											handler: function(){addAuth();}
										}]
							</c:if>
							,loadFilter : function(data) {
								if (data.success) {
									return data;
								} else {
									$.messager.alert("错误", data.resultMsg,"error");
								}
								return data;
							}
		});
	});

	function deleteAuth(id){
		 $.messager.confirm('删除确认', '确定删除权限?', function(r){
				if (r){
					 if(id==''){
						 $.messager.alert("信息","请选择要删除的权限！","info");
						 return false;
					 }
					 $.post("${ctx}/console/auth.htm",
							 {'action':'deleteAuth','id':id},
							 function(data){
								 if(data.success){
									 $.messager.alert("信息","删除成功!","info");
									 queryAuth();
								 }else{
									 $.messager.alert("错误",data.resultMsg,"error");
								 }
							 },
							 'json');
				 }
		});
	}

	function addAuth() {
		$("#authAdd").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 $("#authAddForm").form("submit",{
			        			  onSubmit:function(){
			        				  if($(this).form("validate")){
			        					  $.messager.progress({interval:200,text:'处理中...'});
			        					  return true;
			        				  }
			        				  return false;
			        			  },
			        			  success:function(data){
			        				  $.messager.progress("close");
			        				  data = parseResp(data);
			        				  if(data.success){
			        					  $.messager.alert("消息","权限添加成功！","info");
			        					  clearAddForm();
			        					  reflushDatagrid();
			        				  }else{
			        					  $.messager.alert("错误",data.resultMsg,"error");
			        				  }
			        			  }
			        		  });
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        		 $("#authAdd").dialog("close");
			        	 }
			         }]
		});
	}
	function clearAddForm(){
		$("#authAdd input[name='authCode']").val("");
		$("#authAdd input[name='authName']").val("");
		$("#authAdd select[name='type']").val("");
	}

	function clearForm(){
		$("#authCode").val("");
		$("#authName").val("");
		$('#type').val('');
	}

	function reflushDatagrid(){
		$("#datagrid").datagrid("reload");
	}

	function queryAuth() {
		var param = {
			action : "queryByPage"
		};
		param.authCode = $("#authCode").val();
		param.authName = $("#authName").val();
		param.type = $('#type').val();
		$("#datagrid").datagrid("load", param);
	}
</script>

<style type="text/css">
    #auths{
      display:block;
      width:100%;
      height:100%;
      overflow: auto;
    }
    #auths td {
        padding: 5px;
    }
    .auth_tab{
      border-collapse:collapse;
      border-bottom: 1px solid gray;
      width:100%;
    }
    .auth_tab td{
      border-collapse:collapse;
      border-bottom: 1px solid gray;
    }
    .auth_title{
       width:150px;
       text-align: center;
       vertical-align: center;
       font-weight: bold;
       border-right:1px solid gray;
    }
    .auth_item{
        display:block;
        float:left;
        padding-right: 20px; 
        width: 165px;
        height: 25px;
        line-height: 25px;
    }
</style>
</head>
<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'north',split:false,border:false" style="height: auto;">
			<div class="query">
				<form id="form" class="inner-q" method="POST"
					action="${ctx}/console/auth.htm">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th>权限类型</th>
							<td><select id="type" name="type" class="select">
									<option value="">全部</option>
									<c:forEach items="${typeEnums }" var="type">
										<option value="${type.value }">${type.message }</option>
									</c:forEach>
							</select></td>
							<th>权限代码</th>
							<td><input type="text" id="authCode" name="authCode"
								class="input" /></td>
							<th>权限名称</th>
							<td><input type="text" id="authName" name="authName"
								class="input"></td>
								<th></th>
								<td></td>
						</tr>
						<tr>
							<td colspan="7">
							</td>
							<td class="bar"><input type="button" class="btn"
								onclick="queryAuth()" value="搜索" />&nbsp;<input type="button"class="btn" onclick="clearForm()" value="清空" />
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:false" class="data-area">
			<div id="datagrid"></div>
		</div>
	</div>

	<div id="authAdd" style="width: 480px; height: 205px;overflow: hidden;"
		data-options="title:'权限添加',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
		<div class="dg-content">
			<form id="authAddForm" method="POST" action="${ctx}/console/auth.htm">
				<input type="hidden" name="action" value="addAuth"/>
				<table cellpadding="0" cellspacing="0" class="dg-table">
					<tr>
						<th class="w5-input"><span class="bred">*</span>权限代码</th>
						<td><input type="text" name="authCode" autocomplete="off" class="easyui-validatebox input"
							data-options="required:true" /></td>
					</tr>
					<tr>
						<th><span class="bred">*</span>权限名称</th>
						<td><input type="text" name="authName" autocomplete="off" class="easyui-validatebox input"
							data-options="required:true"></td>
					</tr>
					<tr>
						<th>类型</th>
						<td>
							<select name="type" class="select">
								<c:forEach var="type" items="${typeEnums}">
									<option value="${type.value }">${type.message }</option>
								</c:forEach>
							</select>
						</td>
					</tr>
				</table>
			</form>
		</div>
	</div>
</body>
</html>