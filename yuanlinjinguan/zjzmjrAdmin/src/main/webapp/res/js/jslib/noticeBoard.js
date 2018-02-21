function formatDate(date) {
	var time = date.getTime();
	return getDay(time, "yyyy/MM/dd");
}
/**
 * 新建公告
 */
function doEdit() {
	  $("#Edit").show();
	  clearform();
	  showEditform('新建公告');
}
/**
 * 清除富文本编辑器的内容
 */
function clearform(){
	UM.getEditor('contentEditor').setContent('');
	UM.getEditor('contentEditor1').setContent('');
}
function updateNoticeBoard(index){
	//getNoticeBoard(id);
	$("#Update").show();
	var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
	putData(list);
	showUpdateform("修改公告");
}
function databoxSetValue(date) {
	var time = date.time;
	return getDay(time, "yyyy/MM/dd");
}

function putData(data){
	$("#id").val(data.id);
	$("#version").val(data.version);
	$("#updateForm select[name='type']").val(data.type);
	$("#updateForm input[name='title']").val(data.title);
	if(data.releaseTime == null){
		$("#releaseTime2").datebox('setValue','');
	}else{
		$("#releaseTime2").datebox('setValue',databoxSetValue(data.releaseTime));
	}
	if(data.cancelTime == null){
		$("#cancelTime2").datebox('setValue','');
	}else{
		$("#cancelTime2").datebox('setValue',databoxSetValue(data.cancelTime));
	}
	$("#cancelTime").datebox('setValue',databoxSetValue(data.cancelTime));
	UM.getEditor('contentEditor1').setContent(data.content);
}
$(function(){
	$.ajax({
		url:"/noticeBoard/getCreateUsers.htm",
		type:"POST",
		dataType:'json',
		success:function(data){
			var dom = $("#form select[name='createUserId']");
			var content = "<option value='-1'>不限</option>";
			for ( var i = 0; i < data.rows.length; i++) {
				content += "<option value="+data.rows[i].id+">"+data.rows[i].name+"</option>";
			}
			dom.html(content);
		}
	});
});


function addProcess() {
    $.messager.progress({interval: 200, text: '处理中...'});
}

function releaseNotice(index){
	/*debugger;
	var flag = "";
	if(action == "release"){
		flag=1;
	}else{
		flag=0;
	}*/
	var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
	$.messager.confirm("公告状态更新", "是否确认？", function (r) {
        if (r) {
        	//删除公告
        	$.ajax({
                url: "/noticeBoard/updateNoticeBoard.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	id:list.id,
                	delFlg:'1',
                	version:list.version
                },
                beforeSend: addProcess(),
                success: function (data) {
                	$.messager.progress("close");
                    if (!data.success) {
                        $.messager.alert("错误", data.resultMsg, "error");
                        return;
                    }else{
                    	$.messager.alert("信息", "操作成功", "info");
                    }
                    queryNoticeBoard();// 操作成功 重新查询
                }
            });
        }
    });

}
function cancelNotice(index){
	/*var flag = "";
	if(action == "release"){
		flag=1;
	}else{
		flag=0;
	}*/
	var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
	$.messager.confirm("公告状态更新", "是否确认？", function (r) {
        if (r) {
        	//删除公告
        	$.ajax({
                url: "/noticeBoard/updateNoticeBoard.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	id:list.id,
                	delFlg:'0',
                	version:list.version
                },
                beforeSend: addProcess(),
                success: function (data) {
                	$.messager.progress("close");
                    if (!data.success) {
                        $.messager.alert("错误", data.resultMsg, "error");
                        return;
                    }else{
                    	$.messager.alert("信息", "操作成功", "info");
                    }
                    queryNoticeBoard();// 操作成功 重新查询
                }
            });
        }
    });

}
function showEditform(stitle){
  $("#Edit").dialog({
      title: stitle,
      width: 930,
      height: 500,
      closable: false,
      buttons: [
          {
              iconCls: 'icons-true',
              text: '确定',
              handler: function () {
            	  $("#insertForm").attr("action", "/noticeBoard/insertNoticeBoard.htm");
            	  $("#insertForm input[name='content']").val(UM.getEditor('contentEditor').getContent());
                  $("#insertForm").form("submit", {
                      onSubmit: function () {
                    	  if ($(this).form("validate")) {
  							if (validateData()) {
  								$.messager.progress({
  									interval : 200,
  									text : '处理中...'
  								});
  								return true;
  							}
  						}
  						return false;
                      },
                      success: function (data) {
                          $.messager.progress("close");
                          data = parseResp(data);
                          if (data.success) {
                              $.messager.alert("信息", "新增公告成功", "info");
                              $("#Edit").dialog("close");
                              $(".dg-content #insertForm")[0].reset();
                              //destroyEditor(); //清除特殊文本框
                              clearform();
                              queryNoticeBoard();// 新建成功 重新查询
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
                  clearform();
                  $("#Edit").dialog("close");
                  $(".dg-content #insertForm")[0].reset();
              }
          }
      ]
  });
}
function showUpdateform(stitle){
	$("#Update").dialog({
	      title: stitle,
	      width: 930,
	      height: 500,
	      closable: false,
	      buttons: [
	          {
	              iconCls: 'icons-true',
	              text: '确定',
	              handler: function () {
	            	  $("#updateForm").attr("action", "/noticeBoard/updateNoticeBoard.htm");
	            	  $("#updateForm input[name='content']").val(UM.getEditor('contentEditor1').getContent());
	                  $("#updateForm").form("submit", {
	                      onSubmit: function () {
	                    	  if ($(this).form("validate")) {
	  							if (validateData1()) {
	  								$.messager.progress({
	  									interval : 200,
	  									text : '处理中...'
	  								});
	  								return true;
	  							}
	  						}
	  						return false;
	                      },
	                      success: function (data) {
	                          $.messager.progress("close");
	                          data = parseResp(data);
	                          if (data.success) {
	                              $.messager.alert("信息", "更新公告成功", "info");
	                              $("#Update").dialog("close");
	                              $(".dg-content #updateForm")[0].reset();
	                              $(".dg-content :input[type='hidden']").val("");
	                              //destroyEditor(); //清除特殊文本框
	                              clearform();
	                              queryNoticeBoard();// 修改成功 重新查询
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
	                  clearform();
	                  $("#Update").dialog("close");
	                  $(".dg-content #updateForm")[0].reset();
	                  $(".dg-content :input[type='hidden']").val("");
	              }
	          }
	      ]
	  });
	}
/**添加验证
 * 验证是否输入完全
 * @param id
 * @returns {Boolean}
 */
function validateData() {
	var title = $("#insertForm input[name='title']").val();
	if (title == "") {
		$.messager.alert("提示", "请输入标题", "info");
		return false;
	}
	var date = getDay(new Date(),"yyyy/MM/dd");
	var releaseTime = $("#releaseTime1").datebox('getValue');
	var cancelTime = $("#cancelTime1").datebox('getValue');
	if(releaseTime < date || cancelTime < date ){
		$.messager.alert("提示", "发布日期或者取消日期不得小于当前日期", "info");
		return false;
	}
	if(releaseTime == ""){
		$.messager.alert("提示", "请输入发布日期", "info");
		return false;
	}
	if(cancelTime == ""){
		$.messager.alert("提示", "请输入下线日期", "info");
		return false;
	}
	if(releaseTime >= cancelTime){
		$.messager.alert("提示", "发布日期不能大于或等于取消日期", "info");
		return false;
	}
	var content = $("#insertForm input[name='content']").val();
	if (content == "") {
		$.messager.alert("提示", "请输入内容", "info");
		return false;
	}
	return true;
}
/**更新验证
 * 验证是否输入完全
 * @param id
 * @returns {Boolean}
 */
function validateData1() {
	var title = $("#updateForm input[name='title']").val();
	if (title == "") {
		$.messager.alert("提示", "请输入标题", "info");
		return false;
	}
	var date = getDay(new Date(),"yyyy/MM/dd");
	var releaseTime = $("#releaseTime2").datebox('getValue');
	var cancelTime = $("#cancelTime2").datebox('getValue');
	if(releaseTime < date || cancelTime < date ){
		$.messager.alert("提示", "发布日期或者取消日期不得小于当前日期", "info");
		return false;
	}
	if(releaseTime == ""){
		$.messager.alert("提示", "请输入发布日期", "info");
		return false;
	}
	if(cancelTime == ""){
		$.messager.alert("提示", "请输入下线日期", "info");
		return false;
	}
	if(releaseTime >= cancelTime){
		$.messager.alert("提示", "发布日期不能大于或等于取消日期", "info");
		return false;
	}
	var content = $("#updateForm input[name='content']").val();
	if (content == "") {
		$.messager.alert("提示", "请输入内容", "info");
		return false;
	}
	return true;
}
function destroyEditor(){
	var um = null;
	um = UM.getEditor('contentEditor');
	if(um!=null){
		um.destroy();
		$("#contentEditor").remove();
		um=null;
	}
}