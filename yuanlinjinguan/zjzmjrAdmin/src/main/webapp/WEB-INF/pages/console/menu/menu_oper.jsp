<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>菜单添加</title>
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/default/easyui.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_css }/common/common.css?v=${ver}" />
<script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js"></script>
<script type="text/javascript" src="${res_js }/common/common.js?v=${ver}"></script>

<script type="text/javascript">
 function addMenu(){
	 $("#menuAddForm").form("submit",{
		 onSubmit:function(){
			if($(this).form("validate")){
				if($("#menuAddForm input[name='parent']").val()==''){
					$.messager.alert("信息","请选择父菜单","info");
					return false;
				}
				$.messager.progress({interval:200,text:'处理中...'});
				return true;
			}
			return false;
		 },
	    success:function(data){
			$.messager.progress("close");
			data = parseResp(data);
			if(data.success){
				$.messager.alert("信息","添加成功!","info");
				clearAddForm();
				reloadParentTree();
			}else{
				$.messager.alert("错误",data.resultMsg,"error");
			}
		}
	  });
 }


 function updateMenu(){
	 $("#menuUpdateForm").form("submit",{
		 onSubmit:function(){
			if($(this).form("validate")){
				if($("#menuUpdateForm input[name='id']").val()==''){
					$.messager.alert("信息","请选要修改的菜单","info");
					return false;
				}
				$.messager.progress({interval:200,text:'处理中...'});
				return true;
			}
			return false;
		 },
	    success:function(data){
			$.messager.progress("close");
			data = parseResp(data);
			if(data.success){
				$.messager.alert("信息","菜单修改成功!","info");
				clearUpdateForm();
				clearAddForm();
				reloadParentTree();
			}else{
				$.messager.alert("错误",data.resultMsg,"error");
			}
		}
	  });
 }

 function deleteMenu(){
	 var id = $("#menuUpdateForm input[name='id']").val();
	 if(id==''){
		 $.messager.alert("信息","请选择要删除的菜单！","info");
		 return false;
	 }
	 $.messager.confirm('删除确认', '确定删除菜单?', function(r){
			if (r){
				 $.post("${ctx}/console/menu.htm",
						 {'action':'deleteMenu','id':id},
						 function(data){
							 if(data.success){
								 $.messager.alert("信息","菜单删除成功!","info");
								 clearUpdateForm();
								 clearAddForm();
								 reloadParentTree();
							 }else{
								 $.messager.alert("错误",data.resultMsg,"error");
							 }
						 },
						 'json');
			 }
	});
}

 function clearAddForm(){
	 $("#menuAddForm input[name='name']").val("");
	 $("#menuAddForm input[name='order']").val("");
	 $("#menuAddForm input[name='parent']").val("");
	 $("#menuAddForm input[name='parentName']").val("");
	 $("#menuAddForm input[name='url']").val("");
 }

 function clearUpdateForm(){
	 $("#menuUpdateForm input[name='name']").val("");
	 $("#menuUpdateForm input[name='order']").val("");
	 $("#menuUpdateForm input[name='url']").val("");
	 $("#menuUpdateForm input[name='id']").val("");
 }


 function reloadParentTree(){
	 $("#parentTree").tree("reload");
 }

$(function(){
	$("#parentTree").tree({
 		 url:"${ctx}/console/menu.htm?action=getMenuTree&timestamp="+new Date().getTime(),
 		 loadFilter:function(data){
 			 if(data.success){
 				 return [{id:0,text:'系统菜单',state:'open',degree:0,children:data.tree.children}];
 			 }else{
 				 $.messager.alert("错误",data.resultMsg,"error");
 			 }
 		 },
 		onClick:function(node){
 			$("#menuAddForm input[name='parentName']").val(node.text);
			$("input[name='parent']").val(node.id);

			$("#menuUpdateForm input[name='id']").val(node.id);
			$("#menuUpdateForm input[name='name']").val(node.text);
			$("#menuUpdateForm input[name='order']").val(node.order);
			$("#menuUpdateForm input[name='url']").val(node.url);
 		}
 	 });
});
</script>

</head>
<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'west',split:false,collapsible:true,border:false" class="query" style="width:300px;padding-bottom:20px;overflow: scroll;">
			<div class="inner-q">
				<div id="parentTree" style="overflow: auto;"></div>
			</div>
		</div>
		<div data-options="region:'center',border:false" class="query" style="padding-left:0">
		   <form id="menuAddForm" class="inner-q" style="${menuAddAuth?'':'display:none'}" method="POST" action="${ctx}/console/menu.htm?action=addMenu">
		    <table cellpadding="0" cellspacing="0">
		      <tr>
		         <th width="70px"><span class="bred">*</span>菜单名称</th>
		         <td><input type="text" name="name" class="easyui-validatebox input" style="width:100%" data-options="required:true"/></td>
		         <th width="70px">排序</th>
		         <td><input type="text" name="order" class="easyui-validatebox input" style="width:100%" data-options="validType:'integer'"/></td>
		      </tr>
		      <tr>
		         <th>父菜单ID</th>
		         <td>
		            <input type="text" name="parent" class="input" style="width:100%" readonly="readonly"/>
		         </td>
		         <th>父菜单名</th>
		         <td>
		             <input type="text" name="parentName" class="input" style="width:100%" readonly="readonly"/>
		         </td>
		      </tr>
		      <tr>
		        <th>目标URL</th>
		        <td colspan="3"><input id="url" type="text" name="url" class="input" style="width:100%"/></td>
		      </tr>
		      <tr height="30">
		        <td colspan="4" align="center" style="text-align: center">
		          	<input type="button" class="btn" onclick="addMenu()" value="新建"/>
					&nbsp;
					<input type="button" class="btn" onclick="clearAddForm()" value="清空"/>
		        </td>
		      </tr>
		   </table>
		   </form>
		   <form id="menuUpdateForm" method="POST" class="inner-q" style="${menuUpdateAuth?'margin-top:20px':'display:none'}" action="${ctx}/console/menu.htm">
		    <input type="hidden" name="action" value="updateMenu"/>
		   	<input type="hidden" name="id" value=""/>
		    <table cellpadding="0" cellspacing="0">
		      <tr>
		         <th width="70px"><span class="bred">*</span>菜单名称:</th>
		         <td><input type="text" name="name" class="easyui-validatebox input" style="width:100%" data-options="required:true"/></td>
		         <th width="70px">排序:</th>
		         <td><input type="text" name="order" class="easyui-validatebox input" style="width:100%" data-options="validType:'integer'"/></td>
		      </tr>
		      <tr>
		        <th>目标URL:</th>
		        <td colspan="3"><input type="text" name="url" class="input"  style="width:100%"/></td>
		      </tr>
		      <tr>
		       <td colspan="4" align="center" style="text-align: center">
		          	<input type="button" class="btn" onclick="updateMenu()" value="修改"/>&nbsp;<input type="button" class="btn" onclick="deleteMenu()" value="删除"/>
		        </td>
		      </tr>
		   </table>
		   </form>
		  </div>
		</div>
</body>
</html>