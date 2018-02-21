function addProcess() {
	$.messager.progress({
		interval : 200,
		text : '处理中...'
	});
}
function queryAdminTransaction() {
	var param = {};
	var adminName = $("#adminName").val(),
	projectName = $("#projectName").val(),
	businessType = $("#type").val();
	var extendedField = $("#extendedField").val();
	param.action = "getAdminTransactionList";
	if (adminName != "") {
		param.adminName = adminName == "" ? null : adminName;
	}
	if (businessType != "" && businessType != "请选事务类型") {
		param.businessType = businessType;
	}
	if (extendedField != "") {
		param.extendedField = extendedField == "" ? null : extendedField;
	}
	if(projectName!=""){
		param.projectName = projectName == "" ? null : projectName;
	}
	$("#datagrid").datagrid("load", param);
}

function getTypeList() {
	$.ajax('/adminTransaction/user/index.htm?action=getTypeList', {
		data : {},
		dataType : 'json',
		type : 'post',
		timeout : 10000,
		success : function(data) {
			// console.log(data);
			if (data.success) {
				setList(data.data);
			} else {
				$.messager.alert("错误", data.errorMsg, "error");
			}
		},
		error : function(xhr, type, errorThrown) {
			$.messager.alert("服务器正忙，请稍后重试！");
		}
	});
}

function setList(list) {
//	console.log(list);
	var dom = '<option>请选事务类型</option>', 
		box = document.querySelector("#type");
	for ( var i = 0; i < list.length; i++) {
		dom += "<option value='" + list[i].businessType + "'>" + list[i].businessName + "</option>";
	}
	box.innerHTML = dom;
}

function queryTransactionReset() {
	$(".query #form")[0].reset();
	$(".query :input[type='hidden']").val("");
}
