function addProcess() {
	$.messager.progress({
		interval : 200,
		text : '处理中...'
	});
}

function doAdd() {
	getTypeListAdd();
	$("#questionAdd").dialog({
        closed: false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				$("#questionAddForm").form("submit", {
					onSubmit : function() {
						if ($(this).form("validate")) {
							$("#questionAdd input[name='qaClassId']").val($("#typeAdd").val());
							$.messager.progress({
								interval : 200,
								text : '处理中...'
							});
							return true;
						}
						return false;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("消息", "问答分类添加成功！", "info");
							query(2);
							$("#questionAdd").dialog("close");
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-close',
			text : '取消',
			handler : function() {
				$("#questionAdd").dialog("close");
			}
		} ]
	});
}

function doAddType() {
	$("#questionClassAdd").dialog({
        closed: false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				$("#questionClassAddForm").form("submit", {
					onSubmit : function() {
						if ($(this).form("validate")) {
							$.messager.progress({
								interval : 200,
								text : '处理中...'
							});
							return true;
						}
						return false;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("消息", "问答分类添加成功！", "info");
							query(1);
							$("#questionClassAdd").dialog("close");
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-close',
			text : '取消',
			handler : function() {
				$("#questionClassAdd").dialog("close");
			}
		} ]
	});
}

function doUpdate(index) {
	getTypeList(index);
	$("#questionUpdate").dialog({
        closed: false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				$("#questionUpdateForm").form("submit", {
					onSubmit : function() {
						if ($(this).form("validate")) {
							$("#questionUpdate input[name='qaClassId']").val($("#type").val());
							$.messager.progress({
								interval : 200,
								text : '处理中...'
							});
							$("#questionUpdate").dialog("close");
							return true;
						}
						return false;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("信息", "修改成功！", "info");
							query(2);
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-close',
			text : '取消',
			handler : function() {
				$("#questionUpdate").dialog("close");
			}
		} ]
	});
}
function doUpdateType(index) {
	var data = $("#datagrid1").datagrid("selectRow", index).datagrid("getSelected");
	if (data != null) {
		putUpdateTypeData(data);
	}
	$("#questionUpdateType").dialog({
		closed:false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				$("#questionUpdateTypeForm").form("submit", {
					onSubmit : function() {
						if ($(this).form("validate")) {
							$.messager.progress({
								interval : 200,
								text : '处理中...'
							});
							return true;
						}
						return false;
					},
					success : function(data) {
						$.messager.progress("close");
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("信息", "修改成功！", "info");
							query(1);
							$("#questionUpdateType").dialog("close");
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			}
		}, {
			iconCls : 'icons-close',
			text : '取消',
			handler : function() {
				$("#questionUpdateType").dialog("close");
			}
		} ]
	});
}
function putUpdateTypeData(data) {
	$("#questionUpdateType input[name='id']").val(data.id);
	$("#questionUpdateType input[name='qaClassName']").val(data.qaClassName);
}
function putUpdateData(data) {
//	console.log(data);
	$("#questionUpdate input[name='id']").val(data.id);
	$("#questionUpdate input[name='qaClassId']").val(data.qaClassId);
	$("#questionUpdate input[name='question']").val(data.question);
	$("#questionUpdate textarea[name='answer']").val(data.answer);
	$("#questionUpdate select[name='type']").val("" + data.qaClassId);
	// var dom = "<option value='" + data.qaClassId + "'>"+ data.qaClassName +"</option>";
	// $("#questionUpdate select[name='type']").html(dom);
}

function query(value) {
	var param = {};
	if (value == 1) {
		param.action = "getQuestionType";
		$("#datagrid1").datagrid("load", param);
	} else {
		param.action = "getQuestionList";
		$("#datagrid").datagrid("load", param);
	}

}

function show(id) {
	getQuestion(id, $(".viTbody"));
	doView();
}

function getQuestion(id, tbody) {
	$
			.ajax({
				url : "/question/user/qa.htm",
				type : 'post',
				dataType : 'json',
				data : {
					id : id,
					action : "getQuestionById"
				},
				success : function(data) {
					if (!data.success) {
						$.messager.alert("错误", data.errorMsg, "error");
						return;
					}
					var content = data.data;

					tbody.html("");
					var name = "";
					name += '<tr><th style="width: 100px;">问答分类名称</th><td style="width: 250px;"><input type="text" id="qaClassName" name="qaClassName" value = "'
							+ content.questionAnswerClass.qaClassName + '" style="width: 300px;"></td>';
					name += '<tr><th style="width: 100px;">问题</th><td style="width: 250px;"><input type="text" id="question" name="question" value = "'
							+ content.question + '" style="width: 300px;"></td>';
					name += '<tr><th style="width: 100px;">答案</th><td style="width: 250px;"><textarea cols= "40" rows= "6" id="answer" name="answer" '+
							 '" style="width: 300px;">'+ content.answer +'</textarea></td>';
					name += '<tr><th style="width: 100px;">更新人</th><td style="width: 250px;"><input type="text" id="updateName" name="updateName" value = "'
							+ content.admin.username + '" style="width: 300px;"></td>';
					name += '<tr><th style="width: 100px;">更新时间</th><td style="width: 250px;"><input type="text" id="updateTime" name="updateTime" value = "'
							+ content.time + '" style="width: 300px;"></td>';
					name = name + content;
					tbody.html(name);
				}

			});
}

function doView() {
	$("#questionDialog").dialog({
		title : '查看',
		height : 350,
		width : 500,
		closed:false,
		buttons : [ {
			iconCls : 'icons-close',
			text : '关闭',
			handler : function() {
				$("#questionDialog").dialog("close");
			}
		} ]
	});
}

function doDelete(flag, id) {
	var action = "";
	$.messager.confirm("删除", "是否确认删除？", function (r) {
        if (r) {

    		if (flag == 1) {
    			action = "deleteQuestionType";
    		} else {
    			action = "deleteQuestion";
    		}
    		$.ajax({
    			url : "/question/user/qa.htm",
    			type : 'post',
    			dataType : 'json',
    			data : {
    				id : id,
    				action : action
    			},
    			beforeSend : addProcess(),
    			success : function(data) {
    				$.messager.progress("close");
    				if (data.success) {
    					$.messager.alert("信息", "数据删除成功", "info");
    					query(flag);
    				} else {
    					$.messager.alert("错误", data.resultMsg, "error");
    				}
    			}
    		});

        }
    });
}

function getTypeList(_index) {
	$.ajax('/question/user/qa.htm?action=getQuestionType', {
		data : {},
		dataType : 'json',
		type : 'post',
		timeout : 10000,
		success : function(data) {
//			console.log(data);
			if (data.success) {
				setList(data.rows, _index);
			} else {
				$.messager.alert("错误", data.errorMsg, "error");
			}
		},
		error : function(xhr, type, errorThrown) {
			$.messager.alert("服务器正忙，请稍后重试！");
		}
	});
}

function setList(list, _index) {
//	console.log(list);
	var dom = '<option>请选择</option><option></option>', box = document.querySelector("#type");
	for ( var i = 0; i < list.length; i++) {
		dom += "<option value='" + list[i].id + "'>" + list[i].qaClassName + "</option>";
	}
	box.innerHTML = dom;

	var data = $("#datagrid").datagrid("selectRow", _index).datagrid("getSelected");
	if (data != null) {
		putUpdateData(data);
	}
}

function getTypeListAdd() {
	$.ajax('/question/user/qa.htm?action=getQuestionType', {
		data : {},
		dataType : 'json',
		type : 'post',
		timeout : 10000,
		success : function(data) {
//			console.log(data);
			if (data.success) {
				setListAdd(data.rows);
			} else {
				$.messager.alert("错误", data.errorMsg, "error");
			}
		},
		error : function(xhr, type, errorThrown) {
			$.messager.alert("服务器正忙，请稍后重试！");
		}
	});
}

function setListAdd(list) {
//	console.log(list);
	var dom = '<option>请选择</option><option></option>', box = document.querySelector("#typeAdd");
	for ( var i = 0; i < list.length; i++) {
		dom += "<option value='" + list[i].id + "'>" + list[i].qaClassName + "</option>";
	}
	box.innerHTML = dom;
}
function changeDivHeight(){
	var availHeight = window.parent.window.innerHeight;
	console.log(window.innerHeight,availHeight);
	document.getElementById( "tt" ).style.height = availHeight - 51 + 'px';
	document.getElementById( "t1" ).style.height = availHeight - 81 + 'px';
	document.getElementById( "t2" ).style.height = availHeight - 81 + 'px';
}