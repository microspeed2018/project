
/**
 * 新增基础数据
 */
function doEdit() {
	$("#productForm input").each(function(){  
        $(this).val('');  
    });  
    $("#productForm select").each(function(){  
        $(this).val('');  
    }); 
	//addProductView($("#productForm .myTbody"));
	showEditForm('新建资料数据');
}

/**
 * 展示新增详情的对话框
 * 
 * @param tbody
 */
function addProductView(tbody) {
	tbody.html("");
	var content;
	content = "<tr><th style='width: 80px;'>资料名称</th><td style='width:250px;'>";
	content += '<input type="text" id="fileName" name="fileName" class="input" value=""></td></tr>'; 
	content += "<tr><th style='width: 80px;'>状态</th><td style='width: 250px;' ><select id='status' name='status' class='select'>";
	content += "<option  value='1'>正常</option>";
	content += "<option  value='0'>冻结</option>";
	content += "</select></td></tr>";
	content += "<input id='id' name='id' value='' type='hidden' />";
	tbody.html(content);
}

function showEditForm(stitle) {
	$("#productForm").attr("action", "/baseFile/user/insertFile.htm");
	$("#productEdit").dialog({
		title : stitle,
		width : 500,
		height : 250,
		closable : false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {

				$("#productForm").form("submit", {
					onSubmit : function() {
						var basicId = $("#basicId").val();
						if(basicId == ""){
							$.messager.alert("注意", "请选择资料分类！", "Warning");
							return false;
						}
						var filename=$("#fileName").val();
						if(filename==null||filename==""){
							$.messager.alert("注意", "文件名不可为空！", "Warning");
							return false;
						}
						$.messager.progress({
							interval : 200,
							text : '处理中...'
						});
						return true;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("信息", "操作成功", "info");
							$("#productEdit").dialog("close");
							queryProduct();// 新建成功 重新查询
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-clear',
			text : '取消',
			handler : function() {
				// destroyEditor();
				$("#productEdit").dialog("close");
			}
		} ]
	});
}

/**
 * 修改基础数据
 * 
 * @param index
 */
function editProduct(index) {
	$("#productForm input").each(function(){  
        $(this).val('');  
    });  
    $("#productForm select").each(function(){  
        $(this).val('');  
    }); 
	var data = $("#datagrid").datagrid("selectRow", index).datagrid(
	"getSelected");
	getBasicData(data, $("#productForm .myTbody"));
	// $("body").on("change", "#categorySelect", function(){
	// var selectVal=$("#categorySelect").val();
	// if(selectVal != -1){
	// $("#categoryInput").attr("disable", true);
	// }else{
	// $("#categoryInput").attr("disable", false);
	// }
	// });
	updateEditForm('修改文件资料数据');
}

function getBasicData(data, tbody) {
	$("#basicId").val(data.basicId);
	$("#fileName").val(data.name);
	$("#status").val(data.status);
	$("#id").val(data.id);
//	var content;
//	content = "<tr><th style='width: 80px;'>资料名称</th><td style='width:250px;'>";
//	content += '<input type="text" id="fileName" name="fileName" class="input" value="'+data.name+'"></td></tr>'; 
//	content += "<tr><th style='width: 80px;'>状态</th><td style='width: 250px;' ><select id='status' name='status' class='select'>";
//	var select1 = "", select2 = "";
//	if(data.status == 1){
//		select1 = "selected";
//		select2 = "";
//	}else if(data.status == 0){
//		select1 = "";
//		select2 = "selected";
//	}
//	content += "<option  value='1' "+ select1 +">正常</option>";
//	content += "<option  value='0' "+ select2 +">冻结</option>";
//	content += "</select></td></tr>";
//	content += "<input id='id' name='id' value='"+data.id+"' type='hidden' />";
//	tbody.html(content);
					

}


function updateEditForm(stitle) {
	$("#productForm").attr("action", "/baseFile/user/updateFile.htm");
	$("#productEdit").dialog({
		title : stitle,
		width : 500,
		height : 250,
		closable : false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				$("#productForm").form("submit", {
					onSubmit : function() {
						var basicId = $("#basicId").val();
						if(basicId == ""){
							$.messager.alert("注意", "请选择资料分类！", "Warning");
							return false;
						}
						var filename=$("#fileName").val();
						if(filename==null||filename==""){
							$.messager.alert("注意", "资料名称不可为空！", "Warning");
							return false;
						}
						$.messager.progress({
							interval : 200,
							text : '处理中...'
						});
						return true;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("信息", "操作成功", "info");
							$("#productEdit").dialog("close");
							queryProduct();// 新建成功 重新查询
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-clear',
			text : '取消',
			handler : function() {
				$("#productEdit").dialog("close");
			}
		} ]
	});
}

/**
 * 删除资料
 * 
 * @param id
 */
function commitProduct(id) {
	$.messager.confirm("删除资料数据", "是否确认删除？", function(r) {
		if (r) {
			deleteProduct(id);
		}
	});
}
function deleteProduct(id) {
	$.ajax({
		url : "/baseFile/user/deleteFile.htm",
		type : 'post',
		dataType : 'json',
		data : {
			id : id
		},
		success : function(data) {
			if (!data.success) {
				$.messager.alert("错误", data.resultMsg, "error");
				return;
			}
			queryProduct();// 删除成功 重新查询
		}

	});
}
