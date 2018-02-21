
function showBindMenu(action, id, dpId){
	$("#menu_tree").tree({
		checkbox:true,
		url:"/console/roleMenu.htm?action=roleMenuTree&roleType="+id+"&timestamp="+new Date().getTime(),
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
		        			$.post("/console/roleMenu.htm",
		        					{action:"bindMenu",menus:ids,roleType:id,departmentId:dpId},
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
	}).dialog('open');
}
