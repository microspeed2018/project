function showAddBadRecord(data,callback){
	if($("#badRecordAddWin").length==0){
		var text = new Text();
		text._('<div id="badRecordAddWin" class="easyui-dialog" style="width:481px;height:387px;overflow: hidden">');
		text._('<div class="dg-content">');
		text._('<form id="badRecordAddForm" action="')._(ctx.home)._('/user/badRecord.htm" method="post">');
		text._('<input type="hidden" name="action" value="addBadRecord"/>');
		text._('<input type="hidden" name="userId" class="input"/>');
		text._('<table cellpadding="0" cellspacing="0" class="dg-table">');
		text._('<tr>');
		text._('	<th class="5w-th">产品ID</th>');
		text._('	<td><input type="text" name="productid" class="input"/></td>');
		text._('</tr>');
		text._('<tr>');
		text._('	<th><span class="bred">*</span>用户</th>');
		text._('	<td id="user"><input type="text" name="username" class="input"/></td>');
		text._('</tr>');
		text._('<tr>');
		text._('	<th>不良类型</th>');
		text._('	<td><select name="type" class="select"></select></td>');
		text._('</tr>');
		text._('<tr>');
		text._('	<th>严重程度</th>');
		text._('	<td><select name="level" class="select"></select></td>');
		text._('</tr>');
		text._('<tr>');
		text._('	<th><span class="bred">*</span>涉及金额</th>');
		text._('	<td><input type="text" name="money" class="input" autocomplete="off"/></td>');
		text._('</tr>');
		text._('<tr>');
		text._('	<th><span class="bred">*</span>发生时间</th>');
		text._('	<td><input type="text" id="_bad_time" name="time"/></td>');
		text._('</tr>');
		text._('<tr>');
		text._('<th><span class="bred">*</span>详情</th>');
		text._('	<td colspan="3" style="padding-top:5px"><textarea name="detail" class="textarea"></textarea></td>');
		text._('</tr>');
		text._('</table>');
		text._('</form>');
		text._('</div>');
		text._('</div>');
		$(text.toString()).appendTo($("body")).hide();
		$.post(ctx.home+"/user/badRecord.htm",{action:"ajaxTypes"},function(data){
			if($.isArray(data.options)){
				var tp = $("#badRecordAddWin select[name='type']");
				tp.children().remove();
				for(var i=0;i<data.options.length;i++){
					var opt = data.options[i];
					$("<option value='"+opt.value+"'>"+opt.message+"</option>").appendTo(tp);
				}
			}
		},"json");
		$.post(ctx.home+"/user/badRecord.htm",{action:"ajaxLevels"},function(data){
			if($.isArray(data.options)){
				var lev = $("#badRecordAddWin select[name='level']");
				lev.children().remove();
				for(var i=0;i<data.options.length;i++){
					var opt = data.options[i];
					$("<option value='"+opt.value+"'>"+opt.message+"</option>").appendTo(lev);
				}
			}
		},"json");

		$("#badRecordAddWin input[name='username']").validatebox({
		    required: true
		});
		$("#badRecordAddWin input[name='money']").validatebox({
		    required: true,
		    validType:'currency'
		});
		$("#_bad_time").datebox({
			height:25,
			width:300,
			required:true,
			editable:true
		});

		$("#badRecordAddWin textarea[name='detail']").validatebox({
			editable:true,
			required:true
		});

	}

	$("#badRecordAddWin input[name='productid']").val(data.productid?$.trim(data.productid):'');
	$("#badRecordAddWin input[name='username']").val(data.username?$.trim(data.username):'');
	$("#badRecordAddWin input[name='userId']").val(data.userId?$.trim(data.userId):'');
	$("#_bad_time").datebox("setValue","");
	$("#badRecordAddWin").show().dialog({
		title:'不良记录添加',
		resizable:false,
		minimizable:false,
		maximizable:false,
		resizable:false,
		modal:true,
		closed:false,
		buttons:[
		         {
		        	 iconCls: 'icons-true',
		        	 text:'确定',
		        	 handler:function(){
		        	 $("#badRecordAddForm").form("submit",{
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
		     				 $("#badRecordAddWin").dialog('close');
		     				$.messager.alert("信息","不良记录添加成功！","info",callback?callback:$.noop);
		     			  }else{
		     				  $.messager.alert("错误",data.resultMsg,"error");
		     			  }
		     		  }
		     	  });
		         }},
		         {
		        	 iconCls: 'icons-clear',
		        	 text:'取消',
		        	 handler:function(){
		        	 $("#badRecordAddWin").dialog('close');
		        	 }
		         }]
	});
}
