<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/pages/commons/resource.jsp" />
<title>短信管理</title>
</head>
<style>
.myTable {
	width: 100%;
	border-collapse: collapse;
	border-spacing: 0;
	border: 1px solid #ccc;
}

.myTable tr {
	background-color: #fff;
	height: 30px;
}

.myTable .midInput {
	display: block;
	height: 21px;
	width: 195px;
	line-height: 21px;
	padding: 0;
	border: 1px solid #D3D3D3;
}

.myTable .maxTextarea {
	display: block;
	height: 80px;
	width: 298px;
	font-family: "微软雅黑";
	color: #333333;
	border: 1px solid #D3D3D3;
	padding: 0;
	margin: 3px 0 3px 0;
}

.myTable .maxInput {
	display: block;
	height: 21px;
	width: 590px;
	line-height: 21px;
	padding: 0;
	border: 1px solid #D3D3D3;
}

.myTable tr:HOVER {
	background-color: #f9f9f9;
}

.myTable .myinput {
	display: inline;
	width: 60px;
	line-height: 21px;
	height: 21px;
	padding: 0;
	font-family: "微软雅黑";
	color: #333333;
	border: 1px solid #D3D3D3;
	color: #333333;
}

.myTable th {
	text-align: right;
	background-color: #f1f1f1;
	border: 1px solid #ccc;
	font-weight: normal;
	width: 30%;
	padding-right: 10px;
}

.myTable td {
	border: 1px solid #ccc;
	text-align: left;
	width: 70%;
	min-width: 90px;
	padding-left: 10px;
}
</style>
<script type="text/javascript">
$(function() {
	$("#datagrid").datagrid(
		{
			url : '${ctx}/notify/user/list.htm',
			title:'短信列表',
			headerCls:'grid-title',
			showFooter : true,
			pagePosition : 'bottom',
			pagination : true,
			rownumbers:true,
			pageSize : 20,
			width : 'auto',
			fitColumns : true,
			fit:true,
			sortName:'registerTime',
		    sortOrder:'desc',
			style : 'overflow:auto;',
			singleSelect : true,
			queryParams : {
				includeSystem:false
			},
			pageList : [ 10,20, 30],
			columns:[[
			          	{
			          		field : 'userName',
							title : '发送人',
							width : 80,
							align:'center',
							formatter : function(value, row, index) {
								if (isEmpty(value)){
									if(row.userId == -1){
										return "系统推送";
									}else{
										return "";
									}
								}else{
									return value;
								}
							}
			          	},
			          	{
			          		field : 'receiveName',
							title : '用户',
							width : 80,
							align:'center'
			          	},
			          	{
			          		field : 'destination',
							title : '手机号',
							width : 80,
							align:'center'
			          	},
			          	{
			          		field : 'cause',
							title : '标题',
							width : 100,
							align:'center'
			          	},
			          	{
			          		field : 'content',
							title : '内容',
							width : 180,
							align:'center'
			          	},
			          	{
			          		field : 'scheduleTime',
							title : '计划/发送时间',
							width : 140,
							align:'center',
							formatter : function(value, row, index) {
								return getDay(row.scheduleTime.time,YYYYMMDD_HHMM)+"/"+getDay(row.sendTime.time,YYYYMMDD_HHMM);
							}
			          	}
		            ]],
		            loadFilter : function(data){
		            	if (data.success) {
							return data;
						} else {
							nullData(data);
						}
		            	return {};
		            }
		}
	);
});

function reflushDatagrid(){
	$("#datagrid").datagrid("reload");
}

function queryNotify(){
	var param = {};
	param.action="${ctx}/notify/user/list.htm";
	param.userName=$('#userName').val();
	param.status=$('#status').val()==-1?null:$('#status').val();
	param.priority=$('#priority').val()==-1?null:$('#priority').val();
	param.destination=$('#destination').val();
	param.content=$('#content').val();
	param.cause=$('#cause').val();
	var messageDate = $("#messageDate").datebox('getValue');
	var messageDateEnd = $("#messageDateEnd").datebox('getValue');
	if (messageDate != "") {
		param.messageDate = messageDate;
	}
	if (messageDateEnd != ""){
		param.messageDateEnd = messageDateEnd;
	}
	$("#datagrid").datagrid("load", param);
}

function clearForm(){
	$('#userName').val('');
	$('#type').val('');
	$('#status').val('');
	$('#priority').val('');
	$('#destination').val('');
	$('#content').val('');
	$('#cause').val('');
}

function notifshowAdd(){
	//$("#notifyAddForm").attr("action","${ctx}/notify/user/saveNotify.htm");
	if(${smsSendAuth}){
	$("#notifyAdd").dialog({
		width : 600,
		height : 450,
		closed:false,
		buttons:[
		     {
		    	 iconCls: 'icons-true',
	        	 text:'确定',
	        	 handler:function(){
	        		 $("#notifyAddForm").form("submit",{
	        			 onSubmit:function(){
	        				 if(!$(this).form("validate")){
	        					  //$.messager.progress({interval:200,text:'处理中...'});
	        					  return false;
	        				  }
	        				 var destination = $("#notifyAddForm .destination").val().trim();
	        				 var content = $("#notifyAddForm .content").val().trim();
	        				 if(destination==''){
	        					 $.messager.alert("错误","接收号码不能为空","error");
	        					  return false;
	        				 }
	        				 if(content==''){
	        					 $.messager.alert("错误","短信内容不能为空","error");
	        					  return false;
	        				 }
	        				 $("#notifyAddForm .destination").val(destination);
	        				 $("#notifyAddForm .content").val(content);

	        				 $.messager.progress({interval:200,text:'处理中...'});
	        				 return true;
	        			 },
	        			 success:function(data){
	        				 $.messager.progress("close");
	        				 data = parseResp(data);
	        				 if(data.success){
	        					  $.messager.alert("信息","新增短信成功","info");
	        					  $("#notifyAdd").dialog("close");
	        					  queryNotify();// 新建成功 重新查询
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
	        		 $("#notifyAdd").dialog("close");
	        	 }
	         }
	    ]
	});

	intiAddBody();
	}else{
	 $.messager.alert("短信", "您没有权限发送短信","warning");
	}
}

function intiAddBody(){
	var tbody = $("#notifyAddForm .myTbody");
	var htmlContent,destination,sendTime,content,cause,priority;
	priority="<tr><th>优先级</th><td><select id='priority' name='priority' class='select'><option value='0'>优先级0</option><option value='1'>优先级1</option><option value='2'>优先级2</option><option value='3'>优先级3</option><option value='4'>优先级4</option><option value='5' selected=\"selected\">优先级5</option><option value='6'>优先级6</option><option value='7'>优先级7</option><option value='8'>优先级8</option><option value='9'>优先级9</option></select></td></tr>";
	sendTime="<tr><th>计划发送时间</th><td>"+getContentTime("sendTime")+"</td></tr>";
	cause="<tr><th>发送原因</th><td><input id='cause' name='cause' class='input rate easyui-validatebox' data-options='required:true,validType:[\"length[1,100]\"]'></td></tr>";
	destination="<tr><th>接收号码<br/></th><td><textarea id='destination' name='destination' class='destination maxTextarea'></textarea>(多个号码用逗号或换行分隔，相同号码自动过滤)</td></tr>";
	content="<tr><th>短信内容<br/></th><td><textarea id='content' name='content' class='content maxTextarea'></textarea></td></tr>";
	htmlContent=priority+sendTime+cause+destination+content;
	tbody.html(htmlContent);
	$.parser.parse();
}

</script>
<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'north',split:false,border:false"
			style="height:130px;">
			<div class="query">
				<form id="form" class="inner-q" onsubmit="return false;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th>用户</th>
							<td><input type="text" id="userName"
								name="userName" class="input"></td>
							<th>手机号</th>
							<td><input type="text" id="destination"
								name="destination" class="input"></td>
							<th>标题</th>
							<td><input type="text" id="cause" name="cause"
								class="input"></td>
							<th>发送时间</th>
							<td width="32%"><input type="text" id="messageDate"
								name="messageDate" class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"/>
								~<input type="text" id="messageDateEnd"
								name="messageDateEnd" class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"/>
						    </td>
						</tr>
						<tr>
							<th>消息内容</th>
							<td><input type="text" id="content"
								name="content" class="input"></td>
							<th></th>
							<td></td>
							<th></th><td></td>
							<th></th>

							<td class="bar"><input type="button" class="btn"
								onclick="queryNotify()" value="搜索" />&nbsp;<input type="button" class="btn" onclick="clearForm()" value="清空"/></td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:false" class="data-area">
			<div id="datagrid"></div>
		</div>
	</div>

	<div id="notifyAdd"
		data-options="title:'发送短信',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
		<div class="dg-content">
			<form id="notifyAddForm" action="${ctx}/notify/user/saveNotify.htm" method="post">
				<table class="myTable">
					<tbody class="myTbody">

					</tbody>
				</table>
			</form>
		</div>
	</div>
</body>
</html>