function doEdit() {
	addProductView($("#productForm .myTbody"));
	showEditForm('新建产品');
}
function showEditForm(stitle){
    $("#productForm").attr("action", "/area/user/insertArea.htm");
    $("#productEdit").dialog({
        title: stitle,
        width: 500,
        height: 200,
        closable: false,
        buttons: [
            {
                iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                    $("#productForm").form("submit", {
                        onSubmit: function () {

                           // $("#adAddress").val(UM.getEditor('adConditionEditor').getContent());

                            $.messager.progress({interval: 200, text: '处理中...'});
                            return true;
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
	var areaName,provCode,cityCode,distCode;
	provCode='<tr><th style="width: 100px;">省级代码</th><td style="width: 20px;"><input type="text" id="provCode" name="provCode" style="width: 200px;" ></td></tr>';
	cityCode='<tr><th style="width: 100px;">市级代码</th><td style="width: 20px;"><input type="text" id="cityCode" name="cityCode" style="width: 200px;" ></td></tr>';
	distCode='<tr><th style="width: 100px;">县级代码</th><td style="width: 20px;"><input type="text" id="distCode" name="distCode" style="width: 200px;" ></td></tr>';
	areaName='<tr><th style="width: 100px;">地区名称</th><td style="width: 20px;"><input type="text" id="areaName" name="areaName" style="width: 200px;" ></td></tr>';
 	tbody.html(provCode+cityCode+distCode+areaName);

}
function editProduct(id){
	getArea(id, '', $("#productForm .myTbody"));
	showEditForm('修改产品');
}

function getArea(id,isUpdate, tbody){
	$.ajax({
        url: "/area/user/getAllArea.htm",
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
            var id, areaName,provCode,cityCode,distCode;
            id='<input type="hidden" id="id" name="id" style="width:0px;" value="'+data.rows[0].id+'">';
        	provCode='<tr><th style="width: 100px;">省级代码</th><td style="width: 20px;"><input type="text" id="provCode" name="provCode" style="width: 200px;" value="'+data.rows[0].provCode+'"></td></tr>';
        	cityCode='<tr><th style="width: 100px;">市级代码</th><td style="width: 20px;"><input type="text" id="cityCode" name="cityCode" style="width: 200px;" value="'+data.rows[0].cityCode+'"></td></tr>';
        	distCode='<tr><th style="width: 100px;">县级代码</th><td style="width: 20px;"><input type="text" id="distCode" name="distCode" style="width: 200px;" value="'+data.rows[0].distCode+'"></td></tr>';
        	areaName='<tr><th style="width: 100px;">地区名称</th><td style="width: 20px;"><input type="text" id="areaName" name="areaName" style="width: 200px;" value="'+data.rows[0].areaName+'"></td></tr>';
         	tbody.html(id+provCode+cityCode+distCode+areaName);
        }

    });
}