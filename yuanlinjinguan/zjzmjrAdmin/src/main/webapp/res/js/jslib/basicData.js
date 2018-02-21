/**
 * 新增基础数据
 */
function doEdit() {	
	addProductView($("#productForm .myTbody"));
	$("body").on("change", "#categorySelect", function(){
		var selectVal=$("#categorySelect").val();
		if(selectVal != -1){
			$("#categoryInput").attr("disabled", true);
		}else{
			$("#categoryInput").attr("disabled", false);
		}
	});
	showEditForm('新建基础数据');    
}

function showEditForm(stitle){
    $("#productForm").attr("action", "/company/user/insertBasic.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 500,
        height: 250,
        closable: false,
        buttons: [
            {
            	iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                	var selectVal=$("#categorySelect").val();
                	var categoryId=0;
                	var categoryName="";
                	if(selectVal==-1){
                		categoryId=parseInt($("#maxCategoryId").val())+1;
                		categoryName=$("#categoryInput").val();
                		$("#categoryId").val(categoryId);
                		$("#categoryName").val(categoryName);
                	}else{
                		categoryId=selectVal;
                		categoryName=$("#categorySelect option:selected").text();
                		$("#categoryId").val(categoryId);
                		$("#categoryName").val(categoryName);
                	}

                    $("#productForm").form("submit", {
                        onSubmit: function () {
                        	//$("#techSubOfficeName").val($("#techOfficeId").find("option:selected").text());
                            $.messager.progress({interval: 200, text: '处理中...'});
                            return true;
                        },
                        success: function (data) {
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
            },
            {
                iconCls: 'icons-clear',
                text: '取消',
                handler: function () {
                	//destroyEditor();
                    $("#productEdit").dialog("close");
                }
            }
        ]
    });
}

/**
 * 展示新增详情的对话框
 * @param tbody
 */
function addProductView(tbody){
	tbody.html("");
	var content;
	content  ="<tr><th style='width: 80px;'>类别名称</th><td style='width:250px;'>" ;
	content +="<select type='text' id='categorySelect'style='width: 250px;' ><option value='"+ -1 +"'>新 增</option></select></td></tr>" ;
	content +="<tr><th style='width: 80px;'>新增类别</th><td style='width:250px;'><input type='text' id='categoryInput' style='width: 246px;' /></td></tr>";
	content +="<tr><th style='width: 80px;'>区分名称</th><td style='width: 250px;' ><input id='attributeName' name='attributeName' style='width: 246px;' /></td></tr>";
	content +="<input id='categoryId' name='categoryId' type='hidden' />";
	content +="<input id='categoryName' name='categoryName' type='hidden' />";
	content +="<input id='maxCategoryId' type='hidden' />";


	/*content +="<tr><th style='width: 100px;'>业务部门</th><td style='width: 200px;'><input type='text' readonly='readonly' value='"+name+"' style='width: 200px;'/></td></tr>";
	content +="<tr><th style='width: 100px;'>产品种类</th><td style='width: 200px;'><select type='text' id='productTypeId' name='productTypeId' style='width: 200px;'></select></td></tr>"
	content +="<tr><th style='width: 100px;'>执行利率(%)</th><td style='width: 200px;'>" ;
    content +="<input type='text' id='executionRate' name='executionRate' style='width: 200px;'/></td></tr>";
    content +="<tr><th style='width: 100px;'>返利</th><td style='width: 200px;'>" ;
    content +="<input type='text' id='rebate' name='rebate' style='width: 200px;'/></td></tr>";
    content +="<tr><th style='width: 100px;'>状态</th><td style='width: 200px;'><select type='text' id='status' name='status' style='width: 200px;'>" ;
	content +="<option value='"+0+"'>冻结</option><option selected value='"+1+"'>正常</option></select></td></tr>";*/
	tbody.html(content);
	getCategory(-1);
}

function getCategory(categoryId){
	$.ajax({
		url:"/company/user/getCategorys.htm",
		type:'post',
		dataType:'json',
		data:{
		},
		success:function(data){
			var maxCateId=$("#maxCategoryId");
			var box=$("#categorySelect");
			var dom="<option value='"+ -1 +"'>新 增</option>";
			for(var i=0; i < data.cates.length; i++){
				if(categoryId == data.cates[i].categoryId){
					dom+="<option selected value='"+data.cates[i].categoryId+"'>"+data.cates[i].categoryName+"</option>";
				}else{
					dom+="<option value='"+data.cates[i].categoryId+"'>"+data.cates[i].categoryName+"</option>";
				}
			}

			box.html(dom);
			maxCateId.val(data.cates[data.cates.length-1].categoryId);
		}

	});
}

/**
 * 修改基础数据
 * @param id
 */
function editProduct(id){
	getBasic(id, '', $("#productForm .myTbody"));
//	$("body").on("change", "#categorySelect", function(){
//		var selectVal=$("#categorySelect").val();
//		if(selectVal != -1){
//			$("#categoryInput").attr("disable", true);
//		}else{
//			$("#categoryInput").attr("disable", false);
//		}
//	});
	updateEditForm('修改基础数据');
}

function getBasic(id, isUpdate, tbody) {
    $.ajax({
        url: "/company/user/getBasicById.htm",
        type: 'post',
        dataType: 'json',
        data: {
        	id: id
        },
        success: function (data) {
            if (!data.success) {
                $.messager.alert("错误", data.resultMsg, "error");
                return;
            }
            //将返回的信息放入修改的对话框
            var content;

        	content  ="<input type='hidden' value='"+data.BasicData.id+"' id='id' name='id' style='width:0px;' />";
        	content +="<input type='hidden' value='"+data.BasicData.attributeId+"' id='attributeId' name='attributeId' style='width:0px' />";
        	content +="<tr><th style='width: 80px;'>类别名称</th><td style='width:250px;'>" ;
        	content +="<input type='text' disabled='disabled' id='categoryName' name='categoryName' value='"+data.BasicData.categoryName+"' style='width: 250px;' /></td></tr>";
        	content +="<tr><th style='width: 80px;'>区分名称</th><td style='width: 250px;' ><input id='attributeName' name='attributeName' value='"+data.BasicData.attributeName+"' style='width: 250px;' /></td></tr>";
        	content +="<input id='categoryId' name='categoryId' type='hidden' value='"+data.BasicData.categoryId+"'/>";

            tbody.html(content);
            //加载下拉框的内容
            //getCategory(data.basic.categoryId);

        }

    });
}

function updateEditForm(stitle){
    $("#productForm").attr("action", "/company/user/updateBasic.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 500,
        height: 250,
        closable: false,
        buttons: [
            {
            	iconCls: 'icons-true',
                text: '确定',
                handler: function () {
//                	var selectVal=$("#categorySelect").val();
//                	var categoryId=0;
//                	var categoryName="";
//                	if(selectVal==-1){
//                		categoryId=$("#maxCategoryId").val()+1;
//                		categoryName=$("#categoryInput").val();
//                		$("#categoryId").val(categoryId);
//                		$("#categoryName").val(categoryName);
//                	}else{
//                		categoryId=selectVal
//                		categoryName=$("#categorySelect option:selected").text();
//                		$("#categoryId").val(categoryId);
//                		$("#categoryName").val(categoryName);
//                	}

                    $("#productForm").form("submit", {
                        onSubmit: function () {
                        	//$("#techSubOfficeName").val($("#techOfficeId").find("option:selected").text());
                            $.messager.progress({interval: 200, text: '处理中...'});
                            return true;
                        },
                        success: function (data) {
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
            },
            {
                iconCls: 'icons-clear',
                text: '取消',
                handler: function () {
                	//destroyEditor();
                    $("#productEdit").dialog("close");
                }
            }
        ]
    });
}




/**
 * 删除产品种类
 * @param id
 */
function commitProduct(id) {
    $.messager.confirm("删除基础数据", "是否确认删除？", function (r) {
        if (r) {
        	deleteProduct(id);
        }
    });
}
function deleteProduct(id){
	$.ajax({
        url: "/company/user/deleteBasic.htm",
        type: 'post',
        dataType: 'json',
        data: {
        	id:id
        },
        success: function (data) {
            if (!data.success) {
                $.messager.alert("错误", data.resultMsg, "error");
                return;
            }
            queryProduct();// 删除成功 重新查询
        }

    });
}











