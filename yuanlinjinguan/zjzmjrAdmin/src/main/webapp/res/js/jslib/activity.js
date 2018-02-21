function show(productId){
	getProduct(productId, 'readonly="readonly"', $(".viTbody"));
	doView();
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

                    $("#productDialog").dialog("close");
                }
            }
        ]
    });
}

function getProduct(id, isUpdate, tbody) {
    $.ajax({
        url: "/activity/user/getActivity.htm",
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
            var content;
        	var merchantName,date,actiTitle,actiDesc,actiContentPict,actiDisplayPict;
            for(var cnt = 0;cnt < data.rows.length;cnt++){
            	content = data.rows[cnt];
            	merchantName = '<tr><th style="width: 100px;">发布商户名称</th><td style="width: 500px;"><input type="text" readonly="readonly" value="'+content.merchant.merchantName+'" id="merchantName" name="merchantName" style="width: 100px;" class="midInput name easyui-validatebox" data-options="required:true,validType:length[0,30]"></td>';
	            date =  '<tr><th>活动日期</th><td><input type="text"readonly="readonly" value="'+content.actiDatetime+'" id="actiDatetime" name="actiDatetime" style="width: 150px;" class="money myinput easyui-validatebox" >~';
	        	date += '<input type="text" readonly="readonly" value="'+content.actiDatetimeEnd+'" id="actiDatetimeEnd" name="actiDatetimeEnd"  style="width: 150px;" class="money myinput easyui-validatebox" ></td></tr>';
	        	actiTitle='<tr><th>活动标题</th><td><input type="text" readonly="readonly" value="'+content.actiTitle+'" id="actiDatetime" name="actiDatetime" style="width: 500px;height:50px;"class="midInput name easyui-validatebox"></td></tr>';
	        	actiDesc='<tr><th>活动说明</th><td><input type="text" readonly="readonly" value="'+content.actiDesc+'" id="actiDatetime" name="actiDatetime" style="width: 500px;height:50px;"class="midInput name easyui-validatebox"></td></tr>';
	        	actiContentPict='<tr><th>活动图片</th><td ><img src="'+content.actiContentPict+'" id="actiContentPict" name="actiContentPict" style="width: 640px; height: 300px;"></td></tr>';
	        	actiDisplayPict='<tr><th>活动展示</th><td ><img src="'+content.actiDisplayPict+'" id="actiContentPict" name="actiContentPict" style="width: 640px; height: 300px;"></td></tr>';
	        	merchantName = merchantName +date+actiTitle+actiDesc+actiContentPict+actiDisplayPict;
	        	tbody.html(merchantName);
            }
        }
    });
}

function verifyProduct(id, applyStatus){
	// $.messager.confirm("活动审核", "是否确认提交审核？", function (r) {
 //        if (r) {
 //        	doVerify(id, applyStatus);
 //        }
 //    });
    doVerify(id, applyStatus);
}

function doVerify(id, applyStatus) {
    $.ajax({
        url: "/activity/user/updateActivity.htm",
        type: 'post',
        dataType: 'json',
        data: {
        	applyStatus: applyStatus,
        	id: id
        },
        beforeSend: addProcess(),
        success: function (data) {
            $.messager.progress("close");
            if (data.success) {
                $.messager.alert("信息", "活动审核成功", "info");
            } else {
                $.messager.alert("错误", data.errorMsg, "error");
            }
            queryProduct();
        }
    });
}