function doEdit() {
	addProductView($("#productForm .myTbody"));
	showEditForm('APP发布');
}

function showEditForm(stitle){
    $("#productForm").attr("action", "/appRelease/user/insertAppRelease.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 800,
        height: 500,
        closable: false,
        buttons: [
            {
                iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                		$("#productForm").form("submit", {
                			onSubmit: function () {
                				var device = $("#device").val();
                				var appVersion = $("#appVersion").val();
                				var downloadUrl = $("#downloadUrl").val();
                				if(device == null || device == -1){
                					$.messager.alert("提示", "请选择设备类型", "info");
                					return false;
                				}
                				if(appVersion == null || appVersion == ""){
                					$.messager.alert("提示", "请输入设备号", "info");
                					return false;
                				}
                				if(downloadUrl == null || downloadUrl == ""){
                					$.messager.alert("提示", "请选择文件", "info");
                                	return false;
                				}

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
	tbody.html("");
	var content="";
	content +='<tr><th style="width: 100px;">设备类型</th><td style="width: 250px;"><select type="text" id="device" name="device" style="width: 250px;">';
    content +='<option value="-1">请选择设备类型</option>';
    content +='<option value="2">Android</option>';
    content +='<option value="3">iPhone</option>';
    content +='<option value="10">iPad</option>';
    content +='<option value="11">Winphone</option>';
    content +='</select></td></tr>';
    content +='<tr><th style="width: 100px;">APP版本号</th><td style="width: 250px;"><Input id="appVersion" name="appVersion" style="width: 246px;"></td></tr>';
    content +='<tr><th style="width: 100px">APP文件</th><td style="width: 250px;"><input type="file" id="downloadUrl" name="downloadUrl" maxlength="50" style="width:246px;"></td></tr>';
	tbody.html(content);
}


