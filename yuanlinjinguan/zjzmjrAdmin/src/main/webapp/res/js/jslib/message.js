function editProduct(index){
	$(".sendTr").hide();
	clearNotifyForm();
	var message = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
	$("#address").val(message.address);
	$("#title").val(message.title);
	$("#notifyAdd input[name='type']").each(function(){  
		if ($(this).val() == message.type){
	        $(this).attr("checked",true);  
		}
    });
	$("#context").val(message.context);
	$("#notifyAdd").dialog({
		width : 600,
		height : 450,
		closed:false,
		buttons:[
		     {
	        	 iconCls: 'icons-close',
	        	 text:'关闭',
	        	 handler:function(){
	        		 $("#notifyAdd").dialog("close");
	        		 clearNotifyForm();
	        	 }
	         }
	    ]
	});
}

function clearNotifyForm() {
	$("#address").val("");
	$("#title").val("");
	$("#notifyAdd input[name='type'][value=1]").attr("checked",true);
	$("#context").val("");
	$("input[name=sendType]").each(function(){
		$(this).removeAttr("checked");
	});
	$("#userTb tr:not(:first)").remove();
}

function notifshowAdd(){
	$(".sendTr").show();
	$("#departTr").hide();
	$("#jobTr").hide();
	$("#buttonTr").hide();
	clearNotifyForm();
	if(true){
		//$("#notifyAddForm").attr("action","${ctx}/notify/user/saveNotify.htm");
		$("#notifyAdd").dialog({
			width : 600,
			height : 560,
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
		        				 var title = $("#notifyAddForm #title").val().trim();
		        				 var context = $("#notifyAddForm .context").val().trim();
		        				 var userId = $("input[name=userIdLst]:checked");
		        				// var type = $("#notifyAddForm .type").find("option:selected").text();
		        				 if(context==''){
		        					 $.messager.alert("错误","消息内容不能为空","error");
		        					  return false;
		        				 }
		        				 if(userId.length == 0){
		        					 $.messager.alert("错误","请选择发送对象","error");
		        					 return false;
		        				 }
		        				 $("#notifyAddForm #title").val(title);
		        				 $("#notifyAddForm .content").val(context);
		        				 $.messager.progress({interval:200,text:'处理中...'});
		        				 return true;
		        			 },
		        			 success:function(data){
		        				 $.messager.progress("close");
		        				 data = parseResp(data);
		        				 if(data.success){
		        					  $.messager.alert("消息","新增消息成功","info");
		        					  $("#notifyAdd").dialog("close");
		        					  clearNotifyForm();
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
		        		 clearNotifyForm();
		        	 }
		         }
		    ]
		});
		//intiAddBody();
	}else{
	  $.messager.alert("群发消息", "您没有权限群发消息","warning");
	}
}
$(function(){
	getJob();
	getDepat();
	$("body").on("change", "input[name=sendType]", function() {
		$("#userTb tr:not(:first)").remove();
		$("#departTr").hide();
		$("#jobTr").hide();
		if($(this).val() == 1) {
			getAllAdmin(false);
		} else if($(this).val() == 2) {
			getAllStaff("", "", false);
		} else if($(this).val() == 3) {
			$("#departTr").show();
			getAllStaff($("#departSelect").val(), "", false);
		} else if($(this).val() == 4) {
			$("#jobTr").show();
			getAllStaff("", $("#jobSelect").val(), false);
		} else if($(this).val() == 5) {
			getAllAdmin(true);
		}
	});
	$("body").on("change", "#departSelect", function() {
		var box = $("input[name=sendType]:checked").val();
		if(box == 3){
			getAllStaff($(this).val(), "", true);
		}
	});
	$("body").on("change", "#jobSelect", function() {
		var box = $("input[name=sendType]:checked").val();
		if(box == 4){
			getAllStaff("", $(this).val(), true);
		}
	});
});

// 获取全体用户
function getAllAdmin(isChoose) {
	$.ajax({
		url : "/staff/user/getAdminStaff.htm",
		type : 'post',
		dataType : 'json',
		data : {
			rows : 1000000,
			page : 1
		},
		success : function(data) {
			if (data.success) {
				if(isChoose){
					$("#buttonTr").show();
				}
				var box = $("#userTb");
				$("#userTb tr:not(:first)").remove();
				var dom = '';
				for ( var i = 0; i < data.rows.length; i++) {
					if (isChoose){
						dom += '<tr><td style="width:20%"><input type="checkbox" name="userIdLst" value="'+ data.rows[i].id +'"></td><td style="width:80%">' + data.rows[i].name + '</td></tr>';
					} else {
						dom += '<tr><td style="width:20%"><input type="checkbox" name="userIdLst" checked="true"  onclick="return false;" value="'+ data.rows[i].id +'"></td><td style="width:80%">' + data.rows[i].name + '</td></tr>';
					}
					
					/* if(value !="" && value != 0 && value == data.rows[i].id){
						dom += '<option data-cost=' +data.rows[i].unitPrice +' value=' + data.rows[i].id + ' selected>'+ data.rows[i].gpsType + '</option>';
					}else{
						dom += '<option data-cost=' +data.rows[i].unitPrice +' value=' + data.rows[i].id + '>'+ data.rows[i].gpsType + '</option>';
					} */
				}
				box.append(dom);
			}
		}
	});
}

// 获取所有员工
function getAllStaff(department,job,isChoose) {
	$.ajax({
		url : "/staff/user/getStaffPerson.htm",
		type : 'post',
		dataType : 'json',
		data : {
			notJobStatus : 111,
			jobId : job,
			departmentId : department,
			rows : 1000000,
			page : 1
		},
		success : function(data) {
			if (data.success) {
				if(isChoose){
					$("#buttonTr").show();
				}
				var box = $("#userTb");
				$("#userTb tr:not(:first)").remove();
				var dom = '';
				for ( var i = 0; i < data.rows.length; i++) {
					if (isChoose){
						dom += '<tr><td style="width:20%"><input type="checkbox" name="userIdLst" checked="true"  value="'+ data.rows[i].userId +'"></td><td style="width:80%">' + data.rows[i].userInfo.name + '</td></tr>';
					} else {
						dom += '<tr><td style="width:20%"><input type="checkbox" name="userIdLst" checked="true"  onclick="return false;" value="'+ data.rows[i].userId +'"></td><td style="width:80%">' + data.rows[i].userInfo.name + '</td></tr>';
					}
					/* if(value !="" && value != 0 && value == data.rows[i].id){
						dom += '<option data-cost=' +data.rows[i].unitPrice +' value=' + data.rows[i].id + ' selected>'+ data.rows[i].gpsType + '</option>';
					}else{
						dom += '<option data-cost=' +data.rows[i].unitPrice +' value=' + data.rows[i].id + '>'+ data.rows[i].gpsType + '</option>';
					} */
				}
				box.append(dom);
			}
		}
	});
}

// 获取部门
function getDepat() {
    $.ajax({
        url: "/department/user/getDepartmentByCondition.htm",
        type: 'post',
        dataType: 'json',
        data: {
            status : 1
        },
        success: function (data) {
            if (data.success) {
            	var datas = data.rows;
            	var boxS = $("#departSelect"),
                dom = "";
	            for (var i = 0; i < datas.length; i++) {
	                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].name +"</option>";
	            }
	            boxS.empty();
	            boxS.append(dom);
            } else {
                $.messager.alert("错误", data.errorMsg, "error");
            }
        }
    });
}

function getJob() {
	 $.ajax({
         url: "/job/user/getJobByCondition.htm",
         type: 'post',
         dataType: 'json',
         data: {
             status : 1,
             //departmentId:$(this).val()
         },
         success: function (data) {
             if (data.success) {
                 var box = $("#jobSelect");
                 var dom = "";
                 for (var i = 0; i < data.rows.length; i++) {
                      dom += "<option value='"+ data.rows[i].id +"'>"+ data.rows[i].name +"</option>";
                 }
                box.empty();
                box.append(dom);
             } else {
                 $.messager.alert("错误", data.errorMsg, "error");
             }
         }
     });
}
// 全选
function selectAll(){
	$("input[name=userIdLst]").each(function() {
		$(this).attr("checked",true);
	});
}
// 反选
function unSelect(){
	$("input[name=userIdLst]").each(function() {
		$(this).removeAttr("checked");
	});
}