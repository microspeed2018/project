function addProcess() {
	$.messager.progress({
		interval : 200,
		text : '处理中...'
	});
}

/**
 * 根据条件查询节假日
 */
function queryHoliday(){
	var param={};
	param.holidayTime=$('#holidayTime').datebox('getValue');
	param.action = "getHolidayList";
	$("#datagrid").datagrid("load",param);
}

/**
 * 新增节假日
 */
function doEdit(){
	showHolidayForm($("#holidayForm .myTbody"));
	addHoliday("添加节假日");
	showDatePicker("holidayDate");
}
//显示新增节假日时的文本框
function showHolidayForm(tbody){
	   var holidayTime;
	   holidayTime="<tr><td style='width: 0px;'>" ;
	   holidayTime+="节假日日期<input type='text' id='holidayDate' name='holidayTime' style='width: 296px;' />" ;
	   holidayTime+="<br/>是否节假日<select id='flag' name='flag' class='select'><option value='0'>调休工作日</option><option value='1' selected='selected'>节假日</option></select>" ;
	   holidayTime+="</td></tr>";
	   tbody.html(holidayTime);	   
} 	

function addHoliday(stitle){
	$("#holidayForm").attr("action", "/holiday/user/holiday.htm?action=insertHoliday");
    $("#holidayEdit").dialog({
        title: stitle,
        width: 500,
        height: 200,
        closable: false,
        buttons: [
            {
                iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                    $("#holidayForm").form("submit", {
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
                                $("#holidayEdit").dialog("close");
                               // destroyEditor(); //清除特殊文本框
                                queryHoliday();// 新建成功 重新查询
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
                    $("#holidayEdit").dialog("close");
                }
            }
        ]
    });
}

/**
 * 打开日期控件
 */
/*$(function () {
	//console.log($("#holidayTime"))
	$("body").on("click", "#holidayTime", function () {
		//console.log('success');
		WdatePicker({dateFmt:"yyyyMMdd"});
	});
});*/

/**
 * 修改节假日
 */
function updateHoliday(id,holidayTime,flag){
	//getHoliday(id,'',$("#holidayForm .myTbody"));
	//显示修改节假日时的文本框
	holidayInfo(id,holidayTime,flag,$("#holidayForm .myTbody"));
	updateHolidayForm('修改节假日');
    showDatePicker("holidayStamp");
}
//打开修改节假日的文本框
function holidayInfo(id,holidayTime,flag,tbody){
	var holiday;
	holiday ="<tr><td style='width: 0px;'>";
	holiday +="节假日时间<input type='text' id='holidayStamp' name='holidayTime'value='"+holidayTime+"'  style='width: 296px;' />";
	if(flag==1){
		holiday +="是否节假日<select id='flag' name='flag' class='select'><option value='0'>调休工作日</option><option value='1' selected='selected'>节假日</option></select>" ;
	}else if(flag==0){
		holiday +="是否节假日<select id='flag' name='flag' class='select'><option value='0' selected='selected'>调休工作日</option><option value='1' >节假日</option></select>";
	}
	holiday +="<input type='hidden' id='id' name='id'value='"+id+"' style='width: 300px;' />";
	holiday +="</td></tr>";
    tbody.html(holiday);
}

function getHoliday(id,isUpdate,tbody){
	$.ajax({
        url: "/holiday/user/holiday.htm?action=getHolidayList",
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
        var holidayTime;
        holidayTime ="<tr><th style='width: 100px;'>节假日时间</th><td style='width: 0px;'>";
        holidayTime +="<input type='text' id='holidayTime' name='holidayTime'value='"+data.rows[0].holidayTime+"' style='width: 300px;' />";
        holidayTime +="<input type='hidden' id='id' name='id'value='"+id+"' style='width: 300px;' />";
        holidayTime +="</td></tr>";
        tbody.html(holidayTime);
        }
});
}

function updateHolidayForm(stitle){
    $("#holidayForm").attr("action", "/holiday/user/holiday.htm?action=updateHoliday");
    $("#holidayEdit").dialog({
        title: stitle,
        width: 500,
        height: 200,
        closable: false,
        buttons: [
            {
                iconCls: 'icons-true',
                text: '确定',
                handler: function () {
                    $("#holidayForm").form("submit", {
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
                                $("#holidayEdit").dialog("close");
                               // destroyEditor(); //清除特殊文本框
                                queryHoliday();// 新建成功 重新查询
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
                    $("#holidayEdit").dialog("close");
                }
            }
        ]
    });
}

/**
 * 删除节假日
 */
function commitHoliday(id){
	$.messager.confirm("删除节假日", "是否确认删除？", function (r) {
        if (r) {
        	//删除节假日
        	deleteHoliday(id);
        }
    });
}

function deleteHoliday(id){
	$.ajax({
			url: "/holiday/user/holiday.htm?action=deleteHoliday",
			type: 'post',
			dataType: 'json',
			data: {
			   id:id
			},
			success: function (data) {
			    if (!data.success) {
			       $.messager.alert("错误", data.errorMsg, "error");
			       return;
			 }
			 queryHoliday();// 删除成功 重新查询
			 }

 });
}

function fmtHolidayDate(strDate){
	if(strDate == null || strDate == ''){
		return "";
	}
	return strDate.substring(0,4)+"-"+strDate.substring(4,6)+"-"+strDate.substring(6,8);
}










