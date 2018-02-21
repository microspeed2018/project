function doEdit() {
	addProductView($("#productForm .myTbody"));
	showEditForm('新建产品');
}
function showEditForm(stitle){
    $("#productForm").attr("action", "/admanage/user/insert.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 930,
        height: 500,
        closable: false,
        buttons: [
            {
                iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                	var flag = false;
                	//判断是否每个文件都已上传，若有文件为空，则提示其上次文件
                	for (var i = 0; i < $("#address div").length; i++) {
                		if ($($("#address div")[i]).find("img").length <= 0) {
                			var value = $($("#address div")[i]).find("input[name=adAddress]").val();
                			if (value == '') {
                				flag = true;
                			}
                		}
                	}
                	if(flag){
                		$.messager.alert("信息","请选择文件","info");
                	}else{
                		$("#productForm").form("submit", {
                			onSubmit: function () {
                				if($(this).form("validate")){

                					// $("#adAddress").val(UM.getEditor('adConditionEditor').getContent());

                					$.messager.progress({interval: 200, text: '处理中...'});
                					return true;
                				}
                				return false;
                			},
                			success: function (data) {
                				$.messager.progress("close");
                				data = parseResp(data);
                				if (data.success) {
                					$.messager.alert("信息", "操作成功", "info");
                					$("#productEdit").dialog("close");
                					// destroyEditor(); //清除特殊文本框
                					queryProduct();// 新建成功 重新查询
                				} else {
                					$.messager.alert("错误", data.resultMsg, "error");
                				}
                			}
                		});
                	}
                }
            },
            {
                iconCls: 'icons-close',
                text: '取消',
                handler: function () {
                	//destroyEditor();
                    $("#productEdit").dialog("close");
                }
            }
        ]
    });
}
 function addProductView(tbody){
	 $.ajax({
	        url: "/admanage/user/getMaxAdNo.htm",
	        type: 'post',
	        dataType: 'json',
	        data: {
	        },
	        success: function (data) {
	            if (!data.success) {
	                $.messager.alert("错误", data.errorMsg, "error");
	                return;
	            }
	            var adNo,adComment,adAddress,content;
	            adNo='<tr><th style="width: 100px;">广告位编号</th><td style="width: 0px;"><input type="text" readonly="true" value="'+data.addate+'" id="adNo" name="adNo" style="width: 20px;" >';
	        	adComment= '<tr><th style="width: 100px;">广告位说明</th><td style="width: 0px;"><input type="text" id="adComment" name="adComment" style="width: 300px;" >';
	        	//adUrl= '<tr><th style="width: 100px;">广告位链接</th><td style="width: 0px;"><input type="text" id="adUrl" name="adUrl" style="width: 300px;" >';
	         	adAddress = '<tr><th style="width: 100px;">广告位图片和链接</th><td>';
	         	adAddress +='<div id="address" name="address"><input type="button" style="font-size:15px;width: 20px;" id="add" name="add" onClick="addPicture()" value="+" >';
	         	adAddress +='<input type="file" id="adAddress"  name="adAddress" maxlength="50" style="width: 200px;"><input type="text" id="adUrl" name="adUrl" style="width:416px;" >';
	         	adAddress +='<span style="color:red;">上传图片大小需为640px*280px</span></br></div>';
	         	adAddress +='</td></tr>';

	         	content = adNo + adComment+adAddress;
	         	tbody.html(content);
	        	}

	       });
}

function addPicture(){
	 var picture='<div><input type="button" style="font-size:15px;width: 20px;" class="delete" name="delete" value="-" ><input type="file" id="adAddress"  name="adAddress" maxlength="50" style="width: 200px;"><input type="text" id="adUrl" name="adUrl" style="width: 416px;" ></div><br/>';
	 var box = $("#address");
      box.append(picture);
	 }
$(window).on("click", ".delete", function () {
	$(this).closest("div").remove();
});
 function show(id){
		getProduct(id, 'readonly="readonly"', $(".viTbody"));
		doView();
	}
 function getProduct(id, isUpdate, tbody) {
	    $.ajax({
	        url: "/admanage/user/adList.htm",
	        type: 'post',
	        dataType: 'json',
	        data: {
	        	id: id
	        },
	        success: function (data) {
	            if (!data.success) {
	                $.messager.alert("错误", data.errorMsg, "error");
	                return;
	            }

	        	var adNo,adAddress;
	            var title = data.rows[0].adComment;
	            adNo = '<tr><th style="width: 100px;">广告位说明</th><td style="width: 250px;"><input type="text" readonly="readonly" value="'+title+'" id="adComment" name="adComment" style="width: 300px;" class="midInput name easyui-validatebox" data-options="required:true,validType:length[0,30]"></td>';
	            adAddress = '<tr><th>广告位图片和链接</th><td colspan="3">';
	            // create ads list
	            for (var i = 0; i < data.adAddress[0].length; i++) {
	            	var url = '',
	            		href = "";
	            	url = data.adAddress[0][i].substring(0, data.adAddress[0][i].indexOf(","));
	            	href = data.adAddress[0][i].substring(data.adAddress[0][i].indexOf(",") + 1, data.adAddress[0][i].length);
        		    adAddress += '<br/><img src="'+url+'" id="adAddress" name="adAddress" style="width: 640px; height: 280px;">';
        		    adAddress +='<input type="text" id="adUrl" name="adUrl" style="width:636px;" value='+href+' ><br/><br/>';
	            }
	            adAddress +='</td></tr>';
	            adNo = adNo +adAddress;
	        	tbody.html(adNo);
	        }
	    });
	}
 function doView(){
		$("#productDialog").dialog({
	        title: '查看产品',
	        width: 930,
	        height: 500,
	        closable: false,
	        buttons: [
	            {
	                iconCls: 'icons-close',
	                text: '取消',
	                handler: function () {
	                	//destroyEditor();
	                    $("#productDialog").dialog("close");
	                }
	            }
	        ]
	    });
	}
 function editProduct(id){
		getadManage(id, '', $("#productForm .myTbody"));
		showEditForm('修改产品');
	}
 function getadManage(id, isUpdate, tbody) {
	    $.ajax({
	        url: "/admanage/user/adList.htm",
	        type: 'post',
	        dataType: 'json',
	        data: {
	        	id: id
	        },
	        success: function (data) {
	            if (!data.success) {
	                $.messager.alert("错误", data.errorMsg, "error");
	                return;
	            }
	            var adNo,adAddress;
	            var title = data.rows[0].adComment;
	            adNo = '<input type="hidden" readonly="readonly" value="'+id+'" id="id" name="id" style="width: 0px;" class="midInput name easyui-validatebox" data-options="required:true,validType:length[0,30]">';
	            adNo += '<tr><th style="width: 100px;">广告位说明</th><td style="width: 250px;"><input type="text" value="'+title+'" id="adComment" name="adComment" style="width: 300px;" class="midInput name easyui-validatebox" data-options="required:true,validType:length[0,30]"></td>';
	            adAddress = '<tr><th>广告位图片和链接<input type="button" style="font-size:15px;width: 20px;" id="add" name="add" onClick="addPicture()" value="+" ></th><td colspan="3">';
	            adAddress +='<div id="address" name="address">';
	            // create ads list
	            for (var i = 0; i < data.adAddress[0].length; i++) {
	            	var url = '',
	            		href = "";
	            	url = data.adAddress[0][i].substring(0, data.adAddress[0][i].indexOf(","));
	            	href = data.adAddress[0][i].substring(data.adAddress[0][i].indexOf(",") + 1, data.adAddress[0][i].length);
	            	 adAddress += '<br/><div><input type="button" style="font-size:15px;width: 20px;" class="delete" name="delete" value="-" ><input type="file" id="adAddress" value="'+url+'" name="adAddress"  maxlength="50" style="width:200px;"><span style="color:red;">上传图片大小需为640px*280px</span><img src="'+url+'" id="adAddress" name="aaaaadAddress" style="width: 640px; height: 280px;">';
	        		 adAddress += '<input type="text" id="adUrl" name="adUrl" style="width:636px;" value='+href+' >';
        		     adAddress += '<input type="hidden" id="url" name="url" style="width:0px;" value='+url+' ></div><br/>';
	            }
	            adAddress +='</div></td></tr>';
	            adNo = adNo +adAddress;
	        	tbody.html(adNo);
	        }

	    });
	}

