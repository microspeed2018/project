<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>微信消息回复</title>
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
		$(function(){
			$.ajax({
				url:"/msgReply/querySubscribe.htm",
				type:"get",
				data:"",
				dataType:"json",
				success:function(result){
					if(result.success){
						if(result.form!=null){
							$("#subscribeId").val(result.form.id);
							$("#subscribeMsg").val(result.form.content);
						}
					}else{
						$.messager.alert("错误","连接失败","error");
					}
				},
				error:function(){
					$.messager.alert("错误","连接失败","error");
				}
			});
			$("#subscribeSave").click(function(){
				var val = $("#subscribeMsg").val();
				if(val!=null&&val!=""){
					$.ajax({
						url:"/msgReply/subscribeSave.htm",
						type:"post",
						data:"subscribeMsg="+val,
						dataType:"json",
						success:function(result){
							alert(result);
						},
						error:function(){
							$.messager.alert("错误","连接失败","error");
						}
					});
				}else{
					$.messager.alert("错误","请输入被添加自动回复消息","error");
				}
			});
			$("#subscribeRemove").click(function(){
				var val = $("#subscribeId").val();
				if(val!=null&&val!=""){
					$.ajax({
						url:"/msgReply/subscribeRemove.htm",
						type:"get",
						data:"id="+val,
						dataType:"json",
						success:function(result){
							alert(result);
						},
						error:function(){
							$.messager.alert("错误","连接失败","error");
						}
					});
				}else{
					$.messager.alert("错误","移除失败","error");
				}
			});
		});
	</script>
  </head>

  <body>
    <div id="msgReply" class="easyui-tabs">
	    <div title="被添加自动回复" data-options="closable:false">
	    	 <input type="hidden" id="subscribeId" value="">
	    	 <textarea id="subscribeMsg" style="width:100%;height:30%;"></textarea><br><br>
	         &nbsp;&nbsp;&nbsp;<a id="subscribeSave" href="#" class="easyui-linkbutton" data-options="iconCls:'icons-save'">保存</a>&nbsp;&nbsp;&nbsp;
	         <a id="subscribeRemove" href="#" class="easyui-linkbutton" data-options="iconCls:'icons-clear'">删除回复</a><br><br>
	    </div>
	    <div title="消息自动回复 " data-options="closable:false" >

	    </div>
	    <div title="关键字自动回复" data-options="closable:false" >

	    </div>
	</div>
  </body>
</html>
