function doEdit() {
	addProductView($("#productForm .myTbody"));
	showEditForm('新建产品');
}

function showEditForm(stitle){
    $("#productForm").attr("action", "/logo/user/insertLogoManage.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 600,
        height: 300,
        closable: false,
        buttons: [
            {
            	iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                    $("#productForm").form("submit", {
                        onSubmit: function () {
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
	var logoTypeNo,logoComment,logoAddress,content;
	logoTypeNo='<tr><th style="width: 100px;">图标类型</th><td style="width: 0px;">';
	logoTypeNo +='<select id="logoTypeNo" name="logoTypeNo" type="select" style="width: 254px;">';
	logoTypeNo +='<option value="1">个人设备区</option>';
	logoTypeNo +='<option value="2">业务代理区</option>';
    logoTypeNo +='<option value="3">业务功能区</option>';
	logoTypeNo +='<option value="4">业务查询区</option>';
	logoTypeNo +='<option value="5">功能一区</option>';
	logoTypeNo +='<option value="6">功能二区</option>';
	logoTypeNo +='<option value="7">协保员专区</option>';
	logoTypeNo +='<option value="8">车贷经理专区</option>';
	logoTypeNo +='<option value="9">保险代理专区</option>';
	logoTypeNo +='<option value="10">保险审核专区</option>';
	logoTypeNo +='</select></td></tr>';
	logoComment= '<tr><th style="width: 100px;">图标说明</th><td style="width: 0px;"><input type="text" id="logoComment" name="logoComment" style="width: 250px;" >';
	logoAddress = '<tr><th style="width: 100px;">图标图片</th><td>';
	logoAddress +='<input type="file" id="logoAddress"  name="logoAddress" maxlength="50" style="width: 400px;">';
	logoAddress +='<span  style="color:red;">上传图片大小需为40px*40px</span></div></br>';
	logoAddress +='</td></tr>';
	logoAddress += '<tr><th style="width: 110px;">图标链接地址</th><td>';
	logoAddress += '<input type="text" id="logoLinkAddress" name="logoLinkAddress" style="width:250px;" >';
	content = logoTypeNo+logoComment+logoAddress;
	tbody.html(content);

}

function show(id){
	getProduct(id, 'readonly="readonly"', $(".viTbody"));
	doView();
}
function getProduct(id, isUpdate, tbody) {
    $.ajax({
        url: "/logo/user/getLogoManage.htm",
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
        	var logoNo,logoAddress,logoTypeNo,logoComment;
            var title = data.rows[0].logoComment;
            var type  = data.rows[0].logoTypeNo;
            logoNo='<tr><th style="width: 100px;">排序编号</th><td style="width: 250px;"><input type="text" readonly="readonly" value="'+data.rows[0].logoNo+'" id="logoComment" name="logoComment" style="width: 250px;"></td></tr>';
            logoTypeNo='<tr><th style="width: 100px;">图标类型</th><td style="width: 250px;">';
            if(type==1){
            	logoTypeNo +='<input type="text" readonly="readonly" value="个人设备区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==2){
            	logoTypeNo +='<input type="text" readonly="readonly" value="业务代理区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==3){
            	logoTypeNo +='<input type="text" readonly="readonly" value="业务功能区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==4){
            	logoTypeNo +='<input type="text" readonly="readonly" value="业务查询区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==5){
            	logoTypeNo +='<input type="text" readonly="readonly" value="功能一区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==6){
            	logoTypeNo +='<input type="text" readonly="readonly" value="功能二区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==7){
            	logoTypeNo +='<input type="text" readonly="readonly" value="协保员专区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==8){
            	logoTypeNo +='<input type="text" readonly="readonly" value="车贷经理专区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==9){
            	logoTypeNo +='<input type="text" readonly="readonly" value="保险代理专区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            if(type==10){
            	logoTypeNo +='<input type="text" readonly="readonly" value="保险审核专区" id="logoTypeNo" name="logoTypeNo" style="width: 250px;">';
            }
            logoTypeNo +='</td></tr>';
            logoComment = '<tr><th style="width: 100px;">图标说明</th><td style="width: 250px;"><input type="text" readonly="readonly" value="'+title+'" id="logoComment" name="logoComment" style="width: 250px;"></td>';
            logoAddress = '<tr><th>图标图片</th><td colspan="3">';
    		logoAddress += '<img src="'+data.rows[0].logoAddress+'" id="logoAddress" name="logoAddress" style="width: 80px; height:80px;">';
            logoAddress += '</td></tr>';
            logoAddress += '<tr><th>图标链接地址</th><td colspan="3">';
            logoAddress += '<input type="text" readonly="readonly" id="logoLinkAddress" name="logoLinkAddress" style="width:250px;" value='+data.rows[0].logoLinkAddress+'>';
            logoAddress += '</td></tr>';
            logoNo = logoNo+logoTypeNo+logoComment +logoAddress;
        	tbody.html(logoNo);
        }
    });
}
function doView(){
	$("#productDialog").dialog({
        title: '查看产品',
        width: 600,
        height: 300,
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
	updateEditForm('修改产品');
}
function getadManage(id, isUpdate, tbody) {
    $.ajax({
        url: "/logo/user/getLogoManage.htm",
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
            var logoNo,logoTypeNo,logoTypeone,logoTypetwo,logoTypethree,logoTypefour,logoTypefive,logoTypesix,logoTypeseven,logoTypeeight,logoType,logoComment,logoAddress;
            var title = data.rows[0].logoComment;
            var type= data.rows[0].logoTypeNo;
            logoNo = '<input type="hidden" readonly="readonly" value="'+id+'" id="id" name="id" style="width: 0px;">';
            logoNo += '<tr><th style="width: 100px;">排序编号</th><td style="width: 250px;"><input type="text"  value="'+data.rows[0].logoNo+'" id="logoNo" name="logoNo" style="width: 250px;"></td>';
            logoTypeNo='<tr><th style="width: 100px;">图标类型</th><td style="width: 250px;">';
            logoTypeNo +='<select id="logoTypeNo" name="logoTypeNo" type="select"  style="width:254px;">';
            if(type==1){
            	logoTypeone='<option selected value="1">个人设备区</option>';
            }else{
            	logoTypeone='<option value="1">个人设备区</option>';
            }
            if(type==2){
            	logoTypetwo='<option selected value="2">业务代理区</option>';
            }else{
            	logoTypetwo='<option value="2">业务代理区</option>';
            }
            if(type==3){
            	logoTypethree='<option selected value="3">业务功能区</option>';
            }else{
            	logoTypethree='<option value="3">业务功能区</option>';
            }
            if(type==4){
            	logoTypefour='<option selected value="4">业务查询区</option>';
            }else{
            	logoTypefour='<option value="4">业务查询区</option>';
            }
            if(type==5){
            	logoTypefive='<option selected value="5">功能一区</option>';
            }else{
            	logoTypefive='<option value="5">功能一区</option>';
            }
            if(type==6){
            	logoTypesix='<option selected value="6">功能二区</option>';
            }else{
            	logoTypesix='<option value="6">功能二区</option>';
            }
            if(type==7){
            	logoTypeseven='<option selected value="7">协保员专区</option>';
            }else{
            	logoTypeseven='<option value="7">协保员专区</option>';
            }
            if(type==8){
            	logoTypeeight='<option selected value="8">车贷经理专区</option>';
            }else{
            	logoTypeeight='<option value="8">车贷经理专区</option>';
            }
            if(type==9){
            	logoTypenine='<option selected value="9">保险代理专区</option>';
            }else{
            	logoTypenine='<option value="9">保险代理专区</option>';
            }
            if(type==10){
            	logoTypeten='<option selected value="10">保险审核专区</option>';
            }else{
            	logoTypeten='<option value="10">保险审核专区</option>';
            }
            logoType='</select></td></tr>';
            logoComment= '<tr><th style="width: 100px;">图标说明</th><td style="width: 250px;"><input type="text"  value="'+title+'" id="logoComment" name="logoComment" style="width: 250px;"></td>';
            logoAddress='<tr><th style="width: 100px;">图标图片</th><td style="width: 450px;"><input type="file" id="logoAddress" name="logoAddress"style="width:200px;"><span style="color:red;">上传图片大小需为40px*40px</span><br/><img src="'+data.rows[0].logoAddress+'" id="logoAddress" name="logoAddress" style="width: 80px; height: 80px;">';
            logoAddress +='</td></tr>';
            logoAddress +='<tr><th>图标链接地址</th><td colspan="3"><input type="text"  id="logoLinkAddress" name="logoLinkAddress" style="width:250px;" value='+data.rows[0].logoLinkAddress+'></td></tr>';
            logoNo = logoNo+logoTypeNo+logoTypeone+logoTypetwo+logoTypethree+logoTypefour+logoTypefive+logoTypesix+logoTypeseven+logoTypeeight+logoTypenine+logoTypeten+logoType+logoComment+logoAddress;
        	tbody.html(logoNo);
        }

    });
}

function updateEditForm(stitle){
    $("#productForm").attr("action", "/logo/user/updateLogoManage.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 600,
        height: 300,
        closable: false,
        buttons: [
            {
            	iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                    $("#productForm").form("submit", {
                        onSubmit: function () {
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

/*
 * 删除图标
 */
function commitProduct(id) {
    $.messager.confirm("删除图标", "是否确认删除？", function (r) {
        if (r) {
        	deleteProduct(id);
        }
    });
}
function deleteProduct(id){
	$.ajax({
        url: "/logo/user/deleteLogoManage.htm",
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
            queryProduct();// 新建成功 重新查询
        }

    });
}