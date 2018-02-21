<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>管理账户查询</title>
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/gray/easyui.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_css }/common/common.css?v=${ver}" />
<script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${res_js }/common/common.js"></script>
<script type="text/javascript" src="${res_js }/jslib/admin.js"></script>
<style type="text/css">
  #menu_tree{
    display:block;
    width:100%;
    height:355px;
    overflow: auto;
  }
  #auths{
    display:block;
    width:100%;
    height:350px;
    overflow: auto;
  }
  .bottom_btn_bar{
    display:block;
    width:100%;
    height:50px;
    text-align: center;
    padding:15px 0 0 0;
    border:1px solid red;
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
     width:80px;
     text-align: center;
     vertical-align: center;
     font-weight: bold;
     border-right:1px solid gray;
  }

 .auth_item{
     width:125px;
     display:block;
     float:left;
  }

  .clear{
  display:block;
  height:0;
  width:100%;
  clear:both;
  }
</style>

<script type="text/javascript">
	$(function() {
		$("#datagrid").datagrid(
						{
							url : '${ctx}/console/admin.htm',
							title:'管理员列表',
							fit	:	true,
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
								action : "queryByPage",
								status : 0
							},
							pageList : [ 10,20, 30],
							columns :[[
									{
										field : 'id',
										title : 'ID',
										width : 60,
										align : 'center'
									},
									{
										field : 'username',
										title : '账号',
										width : 100,
										align : 'center'
									},
									{
										field : 'department',
										title : '部门',
										width : 60,
										align : 'center',
										formatter: function(value,row,index){
											<c:forEach items="${departmentEnums }" var="dep">
												if(${dep.value}==value){
													return '${dep.message}';
												}
											</c:forEach>
											return "";
										}
									},
									{
										field : 'name',
										title : '姓名',
										width : 80,
										align : 'center'
									},
									{
										field : 'loginSucceed',
										title : '登录/失败',
										width : 70,
										align : 'center',
										formatter: function(value,row,index){
											return row.loginSucceed+"&nbsp;/&nbsp;"+row.loginFail;
										}
									},
									{
										field : 'currentUserView',
										title : '查看用户数',
										width : 120,
										align : 'center',
										formatter: function(value,row,index){
											return value+"&nbsp;/&nbsp;"+row.maxUserView+"&nbsp;/&nbsp;"+row.totalUserView+"&nbsp;/&nbsp;"+row.userViewDate;
										}
									},
									{
										field : 'accStatus',
										title : '状态',
										formatter: function(value,row,index){
										 	<c:forEach var="status" items="${statusEnums}">
										 		if(${status.value}==value){
													return '${status.message}';
												}
											</c:forEach>
											return "";
										}
									},
									{
										field : 'securityIp',
										title : '安全IP',
										width : 90,
										align : 'center'
									},
									{
										field : 'loginIp',
										title : '登录IP',
										width : 90,
										align : 'center'
									},
									{
										field : 'time',
										title : '创建时间',
										width : 120,
										align : 'center'
									}
									<c:if test="${hasAuthGrantAuth or hasMenuGrantAuth or hasUpdateAuth}">
									,{
										field : 'auth',
										title : '操作',
										width : 160,
										align : 'center',
										formatter: function(value,row,index){
											var cnt = 0;
											var oper = "";
											if(${hasAuthGrantAuth}){
												oper += "<a href='javascript:showBindAuth("+row.id+")'>权限</a>";
												cnt++;
											}
											if(${hasMenuGrantAuth}){
												oper += (cnt>0?"&nbsp;|&nbsp;":"")+"<a href='javascript:showBindMenu("+row.id+","+row.roleType+","+row.department+")'>菜单</a>";
												cnt++;
											}
											/*
											if(${hasUpdateAuth}){
												oper += (cnt>0?"&nbsp;|&nbsp;":"")+"<a href='javascript:updateUser("+index+","+row.roleType+")'>修改</a>";
												cnt++;
											}*/
											return oper;
										}
									}
									</c:if>
									]]
							<c:if test="${adminAddAuth}">
							,toolbar: [{
										iconCls: 'icon-add',
										handler: function(){addUser();}
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

	function showBindMenu(id,roleType,department){
		$("#menu_tree").tree({
			checkbox:true,
			url:"${ctx}/console/menu.htm?action=userMenuTree&userId="+id+"&timestamp="+new Date().getTime()+"&roleType="+roleType+"&department="+department,
			loadFilter:function(data){
	  			 if(data.success){
	  				 return data.userMenu.children;
	  			 }else{
	  				 $.messager.alert("警告",data.resultMsg,"error");
	  			 }
	  		 }
		});

		$("#menu_bind").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var nodes = $.merge($("#menu_tree").tree("getChecked"),$("#menu_tree").tree("getChecked","indeterminate"));
			        			var ids = "";
			        			if(nodes.length>0){
			        				for(var i=0;i<nodes.length;i++){
			        					if(i==0){
			        						ids += nodes[i].id;
			        					}else{
			        						ids += ","+nodes[i].id;
			        					}
			        				}
			        			}
			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/menu.htm",
			        					{action:"bindMenu",menus:ids,userId:$("#datagrid").datagrid("getSelected").id},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$.messager.alert("信息","绑定成功！","info");
			        							$('#menu_bind').dialog('close');
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#menu_bind').dialog('close');
			        	 }
			         }]
		});
	}

	function closeAuthBind(){
		$("#auth_bind").window("close");
	}

	function showBindAuth(id){
		$("#auths").children().remove();
		$.post("${ctx}/console/auth.htm",
				{action:"userGroupAuth",userId:id},
				function(data){
					if(data.success){
						if(!$.isEmptyObject(data.authGroup)){
							var authTab = $("<table class='auth_tab'></table>").appendTo($("#auths"));
							var auths = data.authGroup;
							for(var key in auths){
								var gp = auths[key];
								if($.isArray(gp)&&gp.length>0){
									var block = $("<tr></tr>").append($("<td class='auth_title'>"+key+"</td>")).appendTo(authTab);
									var tdgp = $("<td></td>").appendTo(block);
									for(var i=0;i<gp.length;i++){
										$("<label class='auth_item'><input type='checkbox' name='auth' value='"+gp[i].id+"' "+(gp[i].checked?"checked='checked'":"")+"/>"+gp[i].authName+"</label>").appendTo(tdgp);
									}
								}
							}
						}
					}else{
						$.messager.alert("错误","加载权限数据出错！","error");
					}
				},"json");

		$("#auth_bind").dialog({
			closed:false,
			buttons:[
					{
						id : "selectAllAuth",
					    iconCls: 'icons-true',
					    text: '全选',
					    handler: function () {
					    	isSelectAllAuth();
					    }
					},
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var chks = $("#auths input[type='checkbox']:checked");
			        			var auths = "";
			        			for(var i=0;i<chks.length;i++){
			        				if(i==0){
			        					auths += $(chks[i]).val();
			        				}else{
			        					auths += ","+$(chks[i]).val();
			        				}
			        			}
			        			var userId = $("#datagrid").datagrid("getSelected").id;

			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/auth.htm",
			        					{action:"bindAuth",userId:userId,auths:auths},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$("#auth_bind").dialog("close");
			        							$.messager.alert("消息","绑定成功！","info");
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#auth_bind').dialog('close');
			        	 }
			         }]
		});

	}

	/**
	 * 是否全选权限
	 */
	function isSelectAllAuth(){
		var textBtn = $("#selectAllAuth").find("span span").html();
		if(textBtn == "全选"){
			$("#auth_bind input[name='auth']").prop("checked",true);
			$("#selectAllAuth").find("span span").html("反选");
			$("#selectAllAuth").find("span span").removeClass("icons-true");
			$("#selectAllAuth").find("span span").addClass("icons-close");
		} else{
			$("#auth_bind input[name='auth']").prop("checked",false);
			$("#selectAllAuth").find("span span").html("全选");
			$("#selectAllAuth").find("span span").removeClass("icons-close");
			$("#selectAllAuth").find("span span").addClass("icons-true");
		}
	}
	
	function putUpdateData(data){
		$("#userUpdate input[name='id']").val(data.id);
		$("#userUpdate input[name='username']").val(data.username);
		$("#userUpdate input[name='mobile']").val(data.mobile);
		$("#userUpdate input[name='email']").val(data.email);
		$("#userUpdate input[name='name']").val(data.name);
		$("#userUpdate select[name='department']").val(""+data.department);
		$("#userUpdate select[name='creditBank']").val(""+data.creditBankId);
		$("#userUpdate select[name='status']").val(""+data.accStatus);
		$("#userUpdate input[name='securityIp']").val(data.securityIp);
	}

	function updateUser(index,roleType) {
		var data = $("#datagrid").datagrid("selectRow",index).datagrid("getSelected");
		if(data!=null){
			putUpdateData(data);
		}
		$("#uptRoleTr").css("display", "none");
		if($("#userUpdate select[name='department']").val() == 9){
			$("#uptRoleTr").css("display", "table-row");
		}
		$("#userUpdate").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 $("#userUpdateForm").form("submit",{
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
			        					  $("#userUpdate").dialog("close");
			        					  $.messager.alert("信息","用户修改成功！","info");
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
			        		 $("#userUpdate").dialog("close");
			        	 }
			         }]
		});
	}

	function addUser() {
		$("#userAdd").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 $("#userAddForm").form("submit",{
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
			        					  $.messager.alert("消息","用户添加成功！","info");
			        					  clearAddForm();
			        					  reflushDatagrid();
			        					  $("#userAdd").dialog("close");
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
			        		 $("#userAdd").dialog("close");
			        	 }
			         }]
		});
	}


	function reflushDatagrid(){
		$("#datagrid").datagrid("reload");
	}

	function clearForm(){
		$("#mobile").val("");
		$("#email").val("");
		$("#username").val("");
		$("#name").val("");
		$("#department").val("");
	}

	function clearAddForm(){
		$("#userAdd input[name='id']").val("");
		$("#userAdd input[name='mobile']").val("");
		$("#userAdd input[name='username']").val("");
		$("#userAdd input[name='name']").val("");
		$("#userAdd input[name='email']").val("");
		$("#userAdd select[name='department']").val('');
		$("#userAdd select[name='creditBank']").val('');
		$("#userAdd input[name='password']").val('');
		$("#userAdd select[name='status']").val('');
		$("#userAdd input[name='securityIp']").val('');
	}

	function queryAccount() {
		var param = {
			action : "queryByPage",
			status : 0
		};
		param.username = $("#username").val();
		param.mobile = $("#mobile").val();
		param.email = $("#email").val();
		param.name = $("#name").val();
		param.department = $("#department").val();
		$("#datagrid").datagrid("load", param);
	}

	function setSelectValue(obj, value, disConNm){
		if(obj.value == value){
			$("#" + disConNm).css("display", "table-row");
		}else{
			$("#" + disConNm).css("display", "none");
		}
	}
</script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'north',split:false,border:false" style="height: auto;">
			<div class="query">
				<form id="form" class="inner-q" method="POST" action="${ctx}/console/admin.htm">
					<table cellpadding="0" cellspacing="0">
						<tr>
							<th width="9%">账号</th>
							<td width="16%">
								<input type="text" id="username" name="username" class="input">
							</td>
							<th width="9%">姓名</th>
							<td width="16%">
								<input type="text" id="name" name="name" class="input" />
							</td>
							<th width="9%">手机</th>
							<td width="16%">
								<input type="text" id="mobile" name="mobile" class="input" /></td>
							<th width="9%">邮件</th>
							<td width="16%">
								<input type="text" id="email" name="email" class="input" />
							</td>
						</tr>
						<tr>
							<th>部门</th>
							<td>
								<select id="department" name="department" class="select">
									<option value="">所有</option>
									<c:forEach var="dep" items="${departmentEnums }">
										<option value="${dep.value }">${dep.message }</option>
									</c:forEach>
								</select>
							</td>
							<td colspan="6"></td>

						</tr>
						<tr>
							<td colspan="7">
                            </td>
                            <td class='bar'>
                                <input type="button" class="btn" onclick="queryAccount()" value="搜索" />
                                <input type="button" class="btn" onclick="clearForm()" value="清空" />
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


	<div id="menu_bind" class="easyui-dialog" style="width:370px;height:448px;padding:10px 10px 10px 10px" data-options="title:'菜单绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	   <div id="menu_tree">
	   </div>
	</div>

	<div id="auth_bind" class="easyui-dialog" style="width:800px;height:560px;" data-options="title:'权限绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	   <div id="auths">
	   </div>
	</div>

	<div id="userUpdate" style="width:480px;height:415px;overflow: hidden;" data-options="title:'用户修改',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
		<div class="dg-content">
			<form id="userUpdateForm" method="POST" action="${ctx}/console/admin.htm">
				<input type="hidden" name="action" value="updateAdmin" />
				<table cellpadding="0" cellspacing="0" class="dg-table">
					<tr>
						<th class="w5-th">账户ID</th>
						<td><input type="text" name="id" class="input"
							readonly="readonly" /></td>
					</tr>
					<tr>
						<th>用户名</th>
						<td><input type="text" name="username" class="input"
							readonly="readonly"></td>
					</tr>
					<tr>
						<th><span class="bred">*</span>姓名</th>
						<td><input name="name" class="easyui-validatebox input" autocomplete="off"
							data-options="required:true" /></td>
					</tr>
					<tr>
						<th>部门</th>
						<td><select name="department" class="select" onchange="setSelectValue(this, '9', 'uptRoleTr')">
								<c:forEach var="dep" items="${departmentEnums }">
									<option value="${dep.value }">${dep.message }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>征信银行</th>
						<td><select name="creditBank" class="select">
								<option value="">选择征信银行</option>
								<c:forEach var="bank" items="${creditBank}">
									<option value="${bank.id }">${bank.bankname }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr id="uptRoleTr" style="display: none;">
						<th>角色</th>
						<td><select id="updateRoleType" name="roleType" class="select">
						</select>
						</td>
					</tr>
					<tr>
						<th width="80px">手机</th>
						<td><input type="text" name="mobile"
							class="easyui-validatebox input" autocomplete="off"
							data-options="validType:'mobile'" /></td>
					</tr>
					<tr>
						<th width="80px">邮箱</th>
						<td><input type="text" id="m_email" name="email"
							class="easyui-validatebox input" autocomplete="off" data-options="validType:'email'"></td>
					</tr>
					<tr>
						<th>安全IP</th>
						<td><input type="text" name="securityIp" class="input" autocomplete="off"/></td>
					</tr>
					<tr>
						<th>帐户状态</th>
						<td><select name="status" class="select">
								<c:forEach var="status" items="${statusEnums }">
									<option value="${status.value }">${status.message }</option>
								</c:forEach>
						</select></td>
					</tr>
				</table>
			</form>
		</div>
	</div>

	<div id="userAdd" style="width: 480px; height: 415px;overflow: hidden;"
		data-options="title:'用户添加',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
		<div class="dg-content">
			<form id="userAddForm" method="POST"
				action="${ctx}/console/admin.htm?action=createAccount">
				<input type="hidden" name="action" value="addAccount" />
				<table cellpadding="0" cellspacing="0" class="dg-table">
					<tr>
						<th class="w5-input"><span class="bred">*</span>用户名</th>
						<td><input type="text" name="username" autocomplete="off" class="easyui-validatebox input"
							data-options="required:true" /></td>
					</tr>
					<tr>
						<th><span class="bred">*</span>姓名</th>
						<td><input type="text" name="name" autocomplete="off" class="easyui-validatebox input"
							data-options="required:true"></td>
					</tr>
					<tr>
						<th>手机</th>
						<td><input type="text" name="mobile" autocomplete="off" class="easyui-validatebox input"
							data-options="validType:'mobile'" /></td>
					</tr>
					<tr>
						<th>邮箱</th>
						<td>
							<input type="text" name="email" class="easyui-validatebox input" autocomplete="off"
							data-options="validType:'email'"></td>
					</tr>
					<tr>
						<th>部门</th>
						<td><select name="department" class="select" onchange="setSelectValue(this, '9', 'addRoleTr')">
								<c:forEach var="dep" items="${departmentEnums}">
									<option value="${dep.value }">${dep.message }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr>
						<th>征信银行</th>
						<td><select name="creditBank" class="select">
								<option value="">选择征信银行</option>
								<c:forEach var="bank" items="${creditBank}">
									<option value="${bank.id }">${bank.bankname }</option>
								</c:forEach>
						</select></td>
					</tr>
					<tr id="addRoleTr" style="display: none;">
						<th>角色</th>
						<td><select id="insertRoleType" name="roleType" class="select">

						</select></td>
					</tr>
					<tr>

						<th>安全IP</th>
						<td><input type="text" name="securityIp" class="easyui-validatebox input" autocomplete="off"></td>
					</tr>
					<tr>
						<th><span class="bred">*</span>初始密码</th>
						<td>
							<input name="password" type="password" class="easyui-validatebox input" data-options="required:true" />
						</td>
					</tr>
					<tr>
						<th>账户状态</th>
						<td>
							<select name="accStatus" class="select">
								<c:forEach var="status" items="${statusEnums}">
									<option value="${status.value }">${status.message }</option>
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