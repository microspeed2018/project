<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>

<title>微信菜单</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<link rel="stylesheet" type="text/css"
	href="${res_js }/easyui/themes/default/easyui.css?v=${ver}" />
<link rel="stylesheet" type="text/css"
	href="${res_js }/easyui/themes/icon.css?v=${ver}" />
<link rel="stylesheet" type="text/css"
	href="${res_css }/common/common.css?v=${ver}" />
<script type="text/javascript"
	src="${res_js }/jquery/jquery-1.8.0.min.js?v=${ver}"></script>
<script type="text/javascript"
	src="${res_js }/easyui/jquery.easyui.min.js?v=${ver}"></script>
<script type="text/javascript"
	src="${res_js }/easyui/locale/easyui-lang-zh_CN.js?v=${ver}"></script>
<script type="text/javascript"
	src="${res_js }/common/common.js?v=${ver}"></script>
<script type="text/javascript">
	$(function() {
		change(0);
		change(1);
		$("#parentTree").tree(
				{
					url : "${ctx}/weixinmenu/getMenuList.htm",
					loadFilter : function(data) {
						console.info();
						if (data.success) {
							return [ {
								text : '微信菜单',
								state : 'open',
								degree : 0,
								children : data.data
							} ];
						} else {
							$.messager.alert("错误", "获取失败", "error");
						}
					},
					onClick : function(node) {
						if (node.text == "微信菜单") {
							clearAddForm();
							clearUpdateForm();
							$("#menuUpdateForm input[name='name']").val(
									node.text);
							$("#menuAddForm input[name='parentName']").val(
									node.text);
							return;
						}
						$("#menuUpdateForm input[name='name']").val(node.text);
						$("#menuUpdateForm input[name='order']").val(
								node.attributes.order);
						$("#menuUpdateForm input[name='url']").val(
								node.attributes.url);
						$("#menuUpdateForm input[name='key']").val(
								node.attributes.key);
						$("#menuUpdateForm select[name='type']").val(
								node.attributes.type);
						$("#menuUpdateForm input[name='id']").val(node.id);
						var parentNode = $("#parentTree").tree("getParent",
								node.target);
						$("#menuUpdateForm input[name='pid']").val(
								parentNode.id);
						$("#menuAddForm input[name='pid']").val(node.id);
						$("#menuAddForm input[name='parentName']").val(
								node.text);
						$("#menuUpdateForm .content").val(
								node.attributes.content);
						change(0);
						change(1);
					}
				});

	});
	function addMenu() {
		$("#menuAddForm").form("submit", {
			onSubmit : function() {
				if ($(this).form("validate")) {
					$.messager.progress({
						interval : 200,
						text : '处理中...'
					});
					return true;
				}
				return false;
			},
			success : function(data) {
				data=JSON.parse(data);
				$.messager.progress("close");
				if (data.success) {
					$.messager.alert("信息", "添加成功!", "info");
					clearAddForm();
					reloadParentTree();
				} else {
					$.messager.alert("错误", data.resultMsg, "error");
				}
			}
		});
	}

	function change(index) {
		var type;
		if (index == 0) {
			type=$("#menuUpdateForm").find("option:selected").val();
			if (type == "click") {
				$("#menuUpdateForm input[name='key']").removeAttr("disabled");
				$("#menuUpdateForm .content").removeAttr("disabled");
				$("#menuUpdateForm input[name='url']").val("");
				$("#menuUpdateForm input[name='url']").attr("disabled", true);
			} else if (type == "view") {
				$("#menuUpdateForm input[name='url']").removeAttr("disabled");
				$("#menuUpdateForm input[name='key']").val("");
				$("#menuUpdateForm input[name='key']").attr("disabled", true);
				$("#menuUpdateForm .content").val("");
				$("#menuUpdateForm .content").attr("disabled", "disabled");
			} else if (type == "other") {
				$("#menuUpdateForm input[name='key']").val("");
				$("#menuUpdateForm input[name='url']").val("");
				$("#menuUpdateForm input[name='key']").attr("disabled", true);
				$("#menuUpdateForm input[name='url']").attr("disabled", true);
				$("#menuUpdateForm .content").val("");
				$("#menuUpdateForm .content").attr("disabled", "disabled");
			}
		} else if (index == 1) {
			type=$("#menuAddForm").find("option:selected").val();
			if (type == "click") {
				$("#menuAddForm input[name='key']").removeAttr("disabled");
				$("#menuAddForm .content").removeAttr("disabled");
				$("#menuAddForm input[name='url']").val("");
				$("#menuAddForm input[name='url']").attr("disabled", true);
			} else if (type == "view") {
				$("#menuAddForm input[name='url']").removeAttr("disabled");
				$("#menuAddForm input[name='key']").val("");
				$("#menuAddForm input[name='key']").attr("disabled", true);
				$("#menuAddForm .content").val("");
				$("#menuAddForm .content").attr("disabled", "disabled");
			} else if (type == "other") {
				$("#menuAddForm input[name='key']").val("");
				$("#menuAddForm input[name='url']").val("");
				$("#menuAddForm input[name='key']").attr("disabled", true);
				$("#menuAddForm input[name='url']").attr("disabled", true);
				$("#menuAddForm .content").val("");
				$("#menuAddForm .content").attr("disabled", "disabled");
			}
		}
	}

	function updateMenu() {
		$("#menuUpdateForm").form("submit", {
			onSubmit : function() {
				if ($(this).form("validate")) {
					if ($("#menuUpdateForm input[name='id']").val() == '') {
						$.messager.alert("信息", "请选要修改的菜单", "info");
						return false;
					}
					$.messager.progress({
						interval : 200,
						text : '处理中...'
					});
					return true;
				}
				return false;
			},
			success : function(data) {
				var d = JSON.parse(data);
				$.messager.progress("close");
				if (d.success) {
					$.messager.alert("信息", "菜单修改成功!", "info");
					clearUpdateForm();
					clearAddForm();
					reloadParentTree();
				} else {
					$.messager.alert("错误", d.resultMsg, "error");
				}
			}
		});
	}

	function deleteMenu() {
		var id = $("#menuUpdateForm input[name='id']").val();
		if (id == '') {
			$.messager.alert("信息", "请选择要删除的菜单！", "info");
			return false;
		}
		$.messager.confirm('删除确认', '确定删除菜单?', function(r) {
			if (r) {
				$.post("${ctx}/weixinmenu/deleteMenu.htm", {
					"id" : id,
					"pid" : $("#menuUpdateForm input[name='pid']").val()
				}, function(data) {
					if (data.success) {
						$.messager.alert("信息", "菜单删除成功!", "info");
						clearUpdateForm();
						clearAddForm();
						reloadParentTree();
					} else {
						$.messager.alert("错误", data.resultMsg, "error");
					}
				}, 'json');
			}
		});
	}
	function clearAddForm() {
		$("#menuAddForm")[0].reset();
	}
	function clearUpdateForm() {
		$("#menuUpdateForm")[0].reset();
	}
	function reloadParentTree() {
		$("#parentTree").tree("reload");
		change(0);
		change(1);
	}
	function weixinaddMenu(){
		$.ajax({
			url:"${ctx}/weixinmenu/addWeixinMenu.htm",
			type:"post",
			data:"",
			success:function(data){
				data=JSON.parse(data);
				if(data.success){
					$.messager.alert("通知","执行成功","info");
				}else{
					$.messager.alert("错误","执行失败","error");
				}
			},
			error:function(){
				$.messager.alert("错误","连接失败","error");
			}
		});
	}
</script>

</head>

<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div
			data-options="region:'west',split:false,collapsible:true,border:false"
			class="query" style="width:300px;padding-bottom:20px">
			<div class="inner-q">
				<div id="parentTree" style="overflow: auto;"></div>
			</div>
		</div>
		<div data-options="region:'center',border:false" class="query"
			style="padding-left:0">
			<form id="menuAddForm" class="inner-q" method="POST"
				action="${ctx}/weixinmenu/addMenu.htm">
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th width="70px"><span class="bred">*</span>菜单名称</th>
						<td><input type="text" name="name"
							class="easyui-validatebox input" style="width:100%"
							data-options="required:true" /></td>
						<th width="70px">排序</th>
						<td><input type="text" name="order"
							class="easyui-validatebox input" style="width:100%"
							data-options="validType:'integer'" /></td>
					</tr>
					<tr>
						<th>父菜单ID</th>
						<td><input type="text" name="pid" class="input"
							style="width:100%" readonly="readonly" /></td>
						<th>父菜单名</th>
						<td><input type="text" name="parentName" class="input"
							style="width:100%" readonly="readonly" /></td>
					</tr>
					<tr>
						<th>菜单类别</th>
						<td><select name="type" class="select" style="width:100%" onchange="change(1)">
								<option value="other">其他</option>
								<option value="click">点击推事件</option>
								<option value="view">跳转URL</option>
						</select></td>
					</tr>
					<tr>
						<th>键值:</th>
						<td><input type="text" name="key" class="input"
							style="width:100%" /></td>
						<th>目标URL:</th>
						<td><input type="text" name="url" class="input"
							style="width:100%" /></td>
					</tr>
					<tr>
						<th>内容</th>
						<td>
							<textarea rows="5" name="content" class="content" style="width:100%;height:100%"></textarea>
						</td>
					</tr>
					<tr height="30">
						<td colspan="4" align="center" style="text-align: center"><input
							type="button" class="btn" onclick="addMenu()" value="新建" />
							&nbsp; <input type="button" class="btn" onclick="clearAddForm()"
							value="清空" /></td>
					</tr>
				</table>
			</form>
			<form id="menuUpdateForm" method="POST" class="inner-q"
				action="${ctx}/weixinmenu/updateMenu.htm">
				<input type="hidden" name="action" value="updateMenu" /> <input
					type="hidden" name="id" value="" /> <input type="hidden"
					name="pid" value="" />
				<table cellpadding="0" cellspacing="0">
					<tr>
						<th width="70px"><span class="bred">*</span>菜单名称:</th>
						<td><input type="text" name="name"
							class="easyui-validatebox input" style="width:100%"
							data-options="required:true" /></td>
						<th width="70px">排序:</th>
						<td><input type="text" name="order"
							class="easyui-validatebox input" style="width:100%"
							data-options="validType:'integer'" /></td>
					</tr>
					<tr>
						<th>菜单类别</th>
						<td><select name="type" class="select" style="width:100%" onchange="change(0)">
								<option value="other">其他</option>
								<option value="click">点击推事件</option>
								<option value="view">跳转URL</option>
						</select></td>
					</tr>
					<tr>
						<th>键值:</th>
						<td><input type="text" name="key" class="input"
							style="width:100%" /></td>
						<th>目标URL:</th>
						<td><input type="text" name="url" class="input"
							style="width:100%" /></td>
					</tr>
					<tr>
						<th>内容</th>
						<td>
							<textarea rows="5" name="content" class="content" style="width:100%;height:100%" ></textarea>
						</td>
					</tr>
					<tr>
						<td colspan="4" align="center" style="text-align: center"><input
							type="button" class="btn" onclick="updateMenu()" value="修改" />&nbsp;<input
							type="button" class="btn" onclick="deleteMenu()" value="删除" /></td>
					</tr>
				</table>
			</form>
			<a class="easyui-linkbutton"  href="#" data-options="iconCls:'icons-save'" onclick="weixinaddMenu()">生成微信菜单</a>
		</div>
	</div>
</body>
</html>
