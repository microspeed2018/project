<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/pages/commons/resource.jsp" />
<script type="text/javascript" src="${res_js }/jslib/message.js?v=${ver}"></script>
<title>消息管理</title>
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
	resize:none;
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
.userLstDiv {
	border: 1px solid #D3D3D3;
	height: 140px;
	width: 298px;
	overflow-y :auto;
}

#userTb {
	border: 0;
	border-collapse:collapse;
}

</style>
<script type="text/javascript">
$(function() {
	$("#datagrid").datagrid(
		{
			url : '${ctx}/message/user/getAllMessage.htm',
			title:'消息列表',
			headerCls:'grid-title',
			showFooter : true,
			pagePosition : 'bottom',
			pagination : true,
			rownumbers:true,
			pageSize : 20,
			width : 'auto',
			fitColumns : true,
			fit:true,
			sortName:'id',
		    sortOrder:'desc',
			style : 'overflow:auto;',
			singleSelect : true,
			queryParams : {
				includeSystem:false
			},
			pageList : [ 10,20, 30],
			columns:[[ 
			           {
			          		field : 'title',
							title : '标题',
							width : 60,
							align:'center'
			          	},{
			          		field : 'type',
							title : '消息类型',
							width : 20,
							align:'center',
							formatter: function(value, row, index) {
								if(value ==1){
									return "提醒";
								}
								if(value ==2){
									return "公告";
								}
								return "";
							}
			          	},{
			          		field : 'user',
							title : '发送对象',
							width : 40,
							align:'center',
							formatter: function (value, row) {
			                	        if (row.admin) {
			                             return row.admin.name;
			                	        } else {
				                		     return "";
				                	    }
                	     	}
			          	},{
			          		field : 'status',
							title : '状态',
							width : 20,
							align:'center',
							formatter: function(value, row, index) {
								if(value ==0){
									return "未读";
								}
								if(value ==1){
									return "已读";
								}
								return "";
							}
			          	},{
			          		field : 'sendUserName',
							title : '发送人',
							width : 40,
							align:'center'
			          	},{
			          		field : 'messageDate',
							title : '发送时间',
							width : 50,
							align:'center'
			          	},{
			                field: 'opt',
			                title: '操作',
			                align: 'center',
			                width: 40,
			                formatter: function (value, row, index) {
			                    return getOptByStatus(row, index);
			                }
			          	}
		            ]],

		            toolbar:  [{
		                iconCls: 'icons-true',
						text:'群发消息',
						handler: function() {
									notifshowAdd();
								 }

					}],

		            loadFilter : function(data){
		            	if (data.success) {
							return data;
						} else {
							nullData(data);
						}
		            	return data;
		            }
		}
	);
});

function getOptByStatus(row, index){
	var opts = "";
	opts +="<a href='javascript:editProduct("+index+")'>查看</a>";
	return opts;
}


function queryNotify(){
	var param = {};
	var title = $("#queryTitle").val();
	var context = $("#queryContext").val();
	var type = $("#queryType").val();
	var messageDate = $("#messageDate").datebox('getValue');
	var messageDateEnd = $("#messageDateEnd").datebox('getValue');
	var userName = $("#userName").val();
	var sendUserName = $("#sendUserName").val();
	if (title != "") {
		param.title = title;
	}
	if (context != "") {
		param.context = context;
	}
	if (type != -1) {
		param.type = type;
	}
	if (messageDate != "") {
		param.messageDate = messageDate;
	}
	if (messageDateEnd != ""){
		param.messageDateEnd = messageDateEnd;
	}
	if (userName != "") {
		param.userName = userName;
	}
	if (sendUserName != "") {
		param.sendUserName = sendUserName;
	}
	$("#datagrid").datagrid("load", param);
}

function clearForm(){
	$(".query #form")[0].reset();
    $(".query :input[type='hidden']").val("");
}





/* function intiAddBody(){
	var tbody = $("#notifyAddForm .myTbody");
	var htmlContent,userCode,context,title;
	//priority="<tr><th>优先级</th><td><select id='priority' name='priority' class='select'><option value='0'>优先级0</option><option value='1'>优先级1</option><option value='2'>优先级2</option><option value='3'>优先级3</option><option value='4'>优先级4</option><option value='5' selected=\"selected\">优先级5</option><option value='6'>优先级6</option><option value='7'>优先级7</option><option value='8'>优先级8</option><option value='9'>优先级9</option></select></td></tr>";
	//sendTime="<tr><th>计划发送时间</th><td>"+getContentTime("sendTime")+"</td></tr>";
	type="<tr><th>消息类型</th><td><select id='type' name='type' class='select'><option value='1'>提醒</option><option value='2'>公告</option></select></td></tr>";
	title="<tr><th>消息标题</th><td><input id='title' name='title' class='input rate easyui-validatebox' data-options='required:true,validType:[\"length[1,100]\"]'></td></tr>";
	userCode="<tr><th>接收号码<br/></th><td><textarea id='userCode' name='userCode' class='userCode maxTextarea'></textarea>(多个号码用逗号或换行分隔，相同号码自动过滤)</td></tr>";
	context="<tr><th>消息内容<br/></th><td><textarea id='context' name='context' class='context maxTextarea'></textarea></td></tr>";
	htmlContent=type+title+context+userCode;
	tbody.html(htmlContent);
	$.parser.parse();
} */

</script>
<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'north',split:false,border:false"
			style="height:auto;">
			<div class="query">
				<form id="form" class="inner-q" onsubmit="return false;">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th>消息标题</th>
							<td>
								<input type="text" id="queryTitle" name="title" class="input">
							</td>
							<th>消息内容</th>
							<td>
								<input type="text" id="queryContext" name="context" class="input">
							</td>
							<th>消息类型</th>
							<td>
								<select id="queryType" name="type" class="select">
						            <option value="-1">不限</option>
									<option value="1">提醒</option>
									<option value="2">公告</option>
						    	</select>
						    </td>
							<th>发送时间</th>
							<td width="32%"><input type="text" id="messageDate"
								name="messageDate" class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"/>
								~<input type="text" id="messageDateEnd"
								name="messageDateEnd" class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"/>
						    </td>

						</tr>
						<tr>
							<th>发送人</th>
							<td>
								<input type="text" id="sendUserName" name="sendUserName" class="input">
							</td>
							<th>发送对象</th>
							<td>
								<input type="text" id="userName" name="userName" class="input">
							</td>
							<th colspan="3"></th>

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
		data-options="title:'发送消息',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
		<div class="dg-content">
			<form id="notifyAddForm" action="${ctx}/message/user/saveMessage.htm" method="post">
				<table class="myTable">
					<tbody class="myTbody">
						<tr>
							<th>消息标题</th>
							<td>
								<input id='title' name='title' class='input rate easyui-validatebox' data-options='required:true,validType:["length[1,100]"]'>
							</td>
						</tr>
						<!-- <tr>
							<th>接收号码<br/></th>
							<td>
								<textarea id='userCode' name='userCode' class='userCode maxTextarea'></textarea>(多个号码用逗号或换行分隔，相同号码自动过滤)
							</td>
						</tr> -->
						<tr>
							<th>消息内容<br/></th>
							<td>
								<textarea id='context' name='context' class='context maxTextarea'></textarea>
							</td>
						</tr>
						<tr>
							<th>消息类型</th>
							<td>
								<label>
									<input type="radio" value='1' name='type' >提醒&nbsp;&nbsp;
								</label>
								<label>
									<input type="radio" value='2' name='type' checked>公告
								</label>
								<!-- <select id='type' name='type' class='select'>
									<option value='1'>提醒</option>
									<option value='2'>公告</option>
								</select> -->
							</td>
						</tr>
						<tr>
							<th>跳转地址</th>
							<td>
								<input id="address" name="address" class='input'/>
							</td>
						</tr>
						<tr class="sendTr">
							<th>发送对象</th>
							<td>
								<label>
									<input type="radio" value='1' name='sendType' >全体用户&nbsp;&nbsp;
								</label>
								<label>
									<input type="radio" value='2' name='sendType' >公司全员
								</label>
							</td>
						</tr>
						<tr class="sendTr">
							<th></th>
							<td>
								<label>
									<input type="radio" value='3' name='sendType' >指定部门&nbsp;&nbsp;
								</label>
								<label>
									<input type="radio" value='4' name='sendType' >指定岗位&nbsp;&nbsp;
								</label>
								<label>
									<input type="radio" value='5' name='sendType' >指定人员
								</label>
							</td>
						</tr>
						<tr id="departTr" hidden class="sendTr">
							<th>部门</th>
							<td>
								<select id="departSelect" class="select"></select>
							</td>
						</tr>
						<tr id="jobTr" hidden class="sendTr">
							<th>岗位</th>
							<td>
								<select id="jobSelect" class="select"></select>
							</td>
						</tr>
						<tr class="sendTr">
							<th></th>
							<td style="padding-top:2px;padding-bottom:2px;">
								<div id='userIdLst'  class="userLstDiv">
									<table id="userTb" style="border:0">
										<tr id="buttonTr" hidden>
											<td style="width:20%"><button type="button" onclick="selectAll()" >全选</button>&nbsp;<button type="button" onclick="unSelect()" >反选</button></td>
											<td style="width:80%"></td>
										</tr>
									</table>
								</div>
							</td>
						</tr>
					</tbody>
				</table>
			</form>
		</div>
	</div>
	
</body>
</html>