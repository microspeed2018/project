
function doEdit(loanApplyId) {
	$("#uploadCommonDialog").show();
	$("#look").hide();
	$("#uploadCommonDialog").dialog({
		title : '附件上传',
		width : 530,
		height : 250,
		closable : false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				var fileId = $("#fileId").val();
				var projectId = $("#projectId").val();
				var fileAddress = $("#fileAddress").val();
				
				if(fileId == "" && fileId == null){
					$.messager.alert("提示", "请选择文件类型", "info");
					return;
				}
				if(projectId == "" && projectId == null){
					$.messager.alert("提示", "请选择项目", "info");
					return;
				}
				if(fileAddress == "" && fileAddress == null){
					$.messager.alert("提示", "请选择上传文件", "info");
					return;
				}
				
				if($("#iskeychange").val() == "1"){
				    $("#uploadCommonForm").attr("action", "http://192.168.1.252/uploadServlet");
				}else{
				    $("#uploadCommonForm").attr("action", "/fileManage/user/fileUpLoad.htm");
				}
				
				$("#uploadCommonForm").form("submit", {
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
						$(".dg-content #uploadCommonForm")[0].reset();
						data = parseResp(data);
						if (data.success) {
							$.messager.alert("信息", "操作成功", "info");
						    $("#uploadCommonDialog").dialog("close");
						    queryProduct();
						} else {
					    	$.messager.alert("错误", data.resultMsg,"error");
				    	}
					}
				});
			}
		}, {
			iconCls : 'icons-close',
			text : '关闭',
			handler : function() {
				$("#uploadCommonDialog").dialog("close");
				$(".dg-content #uploadCommonForm")[0].reset();
			}
		} ],
		onClose : function() {
			$(".dg-content #uploadCommonForm")[0].reset();
		}
	});
}
$(function() {
	$("#upload").on("click", function() {
		$("#file").show();
	});
});

//点击查看显示图片

function showImgViewer(id) {

	
	$("#index"+id).find("img").eq(0).trigger("click");
	
}

// 打开视频文件
function fileDetail(data) {
	data = decodeURIComponent(data);
	$("#video").attr('src', ctx.home+"/vcastr22.swf?vcastr_file="+data);
	$("#video1").val(ctx.home+"/vcastr22.swf?vcastr_file="+data);
	$("#look").show();
	if (data) {
		$.ajax({
			url : "/fileManage/user/getWidthAndHeight.htm",
			type : 'post',
			dataType : 'json',
			data : {
				url : data
			},
			success : function(list) {
				if (list.success) {
					if(list.width > $(document).innerWidth()){
						list.width = $(document).innerWidth();
					}
					if (list.height > $(document).innerHeight()){
						list.height = $(document).innerHeight();
					}
					$("#look").dialog({
						title : '附件查看',
						width : list.width,
						height : list.height,
						closable : true
					});
				} else {
					$.messager.alert("提示", list.resultMsg, "info");
				}
			}
		});
		//$("#video").attr('poster', data.replace("\\flv","\\img/").replace(".flv",".jpg"));
	} else {
		$.messager.alert("提示", "不支持的文件类型！", "error");
	}
}

// 下载文件
function download(fileAddress){
//	if(!${exportAuth}){
//		$.messager.alert("提示", "没有权限，请联系管理员", "info");
//		return;
//	}
    window.location.href = "/fileManage/user/downloadFiles.htm?fileAddress="+fileAddress;
	 /*$.ajax({
			url : "/fileManage/user/downloadFiles.htm",
			type : 'post',
			dataType : 'json',
			data : {
				fileAddress : fileAddress
			},
			success : function(data) {
				if (data.success) {
					$.messager.alert("提示", "操作成功！", "info");
				} else {
					$.messager.alert("错误", data.resultMsg, "error");
				}
//				queryProduct();
			}
		}); */
}

function deleteFileUpload(fileId){
    $.messager.confirm("确认", "确认要删除吗？删除后无法恢复。", function (r) {
          if (r) {
          	$.messager.progress({interval: 200, text: '处理中...'});
        	  $.ajax({
      			url : "/fileManage/user/deleteFileUploadById.htm",
      			type : 'post',
      			dataType : 'json',
      			data : {
      				id : fileId
      			},
      			success : function(data) {
                    $.messager.progress("close");
      				if (data.success) {
      					$.messager.alert("提示", "删除成功", "info");
      					queryProduct();
      				} else {
      					$.messager.alert("错误", data.resultMsg, "error");
      				}
      			}
      		});
          }
      });
}

//检测文件大小和类型
function fileChange(target) {
	//检测上传文件的类型 
/*	if (!(/(?:jpg|gif|png|jpeg)$/i.test(target.value))) {
		alert("只允许上传jpg|gif|png|jpeg格式的图片");
		if (window.ActiveXObject) {//for IE
			target.select();//select the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			target.type = "text";
			target.type = "file";
		} else
			target.value = "";//for FF,Chrome,Safari
		return;
	} else {
		return; //alert("ok");//or you can do nothing here.
	}
*/
	
	//检测上传文件的大小    
	var isIE = /msie/i.test(navigator.userAgent) && !window.opera;
	var fileSize = 0;
	if (isIE && !target.files) {
		var filePath = target.value;
		var fileSystem = new ActiveXObject("Scripting.FileSystemObject");
		var file = fileSystem.GetFile(filePath);
		fileSize = file.Size;
	} else {
		fileSize = target.files[0].size;
	}
	var size = fileSize / 1024;
	if (size > (5000)) {
		/*alert("文件大小不能超过5000KB");
		if (window.ActiveXObject) {//for IE
			target.select();//select the file ,and clear selection
			document.selection.clear();
		} else if (window.opera) {//for opera
			target.type = "text";
			target.type = "file";
		} else {
			target.value = "";//for FF,Chrome,Safari
		}*/
		$("#iskeychange").val(1);
		return true;
	} else {
		$("#iskeychange").val(0);
		return false;
	}
}
