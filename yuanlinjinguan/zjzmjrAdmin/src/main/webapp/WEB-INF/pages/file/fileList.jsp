<%@ page language="java" import="java.util.*"
	contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<jsp:include page="/WEB-INF/pages/commons/resource.jsp" />
<title>附件列表</title>
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<script type="text/javascript" src="${res_js }/jslib/viewer.min.js"></script>
<link rel="stylesheet" href="http://www.jq22.com/demo/jQueryViewer20160329/css/viewer.min.css">
<script type="text/javascript" src="${res_js }/jslib/fileUpLoad.js?v=${ver}"></script>
</head>
<style type="text/css">
	* {
		font-size: 14px;
	}
</style>
<script type="text/javascript">
	$(function() {

	    $.ajax({
	        url : "/fileManage/user/fileType.htm",
	        type : 'post',
	        dataType : 'json',
	        data : {handlerJob:2},
	        success : function(data) {
	            if (!data.success) {
	                //$.messager.alert("加载文件类型下拉错误", data.resultMsg, "error");
	                return;
	            }
	            var fileTypes = data.data; // 文件类型
	            var domFileType = '';
	            var fileIds = "";
	            domFileType += "<option selected value=''>请选择文件类型</option>";
	            for ( var i = 0; i < fileTypes.length; i++) {
	                domFileType += "<option  value='" +fileTypes[i].id+ "'>"+ fileTypes[i].name + "</option>";
	                fileIds = fileTypes[i].id + "," + fileIds;
	            }
	            $("#fileTypeName").html(domFileType);
	            $("#fileTypeLst").val(fileIds);
	            loadDataList();
	        }
	    });

	    function loadDataList(){

	        $("#datagrid").datagrid({
	            url : '/fileManage/user/getFileManageList.htm',
	            showFooter : true,
	            title : '附件列表',
	            headerCls : 'grid-title',
	            showFooter : true,
	            pagePosition : 'bottom',
	            pagination : true,
	            rownumbers : true,
	            pageSize : 20,
	            width : 'auto',
	            fitColumns : true,
	            fit : true,
	            sortName : 'id',
	            sortOrder : 'desc',
	            style : 'overflow:auto;',
	            singleSelect : false,
	            selectOnCheck : false,
	            queryParams : {fileIds : $("#fileTypeLst").val()},
	            pageList : [ 5, 10, 20 ],
	            columns : [ [ {
	                    field : 'ck',
	                    checkbox : true
	            },{
                    field : 'id',
                    title : '编号',
                    width : 60,
                    hidden : 'true',
                    align : 'center'
                },{
	                field : 'project.projectNo',
	                title : '项目编号',
	                width : 80,
	                align : 'center',
                    formatter : function(value, row) {
                        if (row.project != null) {
                            return row.project.projectNo;
                        } else {
                            return "";
                        }
                    }
	            },{
	                field : 'project.name',
	                title : '项目名',
	                width : 150,
	                align : 'center',
	                formatter : function(value, row) {
	                    if (row.project != null) {
	                        return row.project.name;
	                    } else {
	                        return "";
	                    }
	                }
	            },{
	                field : 'file.name',
	                title : '文件类型',
	                width : 110,
	                align : 'center',
	                formatter : function(value, row) {
	                    if (row.file != null) {
	                        return row.file.name;
	                    } else {
	                        return "";
	                    }
	                }
	            },{
	                field : 'fileName',
	                title : '文件名',
	                width : 150,
	                align : 'center'
	            },{
	                field : 'user.name',
	                title : '上传人',
	                width : 90,
	                align : 'center',
	                formatter : function(value, row) {
	                    if (row.project != null) {
	                        return row.user.name;
	                    } else {
	                        return "";
	                    }
	                }
	            }, {
	                field : 'createTime',
	                title : '上传时间',
	                width : 120,
	                align : 'center',
	                formatter : function(value, row) {
	                    if (value != null) {
	                        return getDay(value.time, "yyyy/MM/dd hh:mm");
	                    } else {
	                        return "";
	                    }
	                }
	            }, {
	                field : 'opt',
	                title : '详细',
	                align : 'center',
	                width : 100,
	                formatter : function(value, row, index, user) {
	                    var opts = "";
	                    var dataArr = row.fileAddress.split(",");
	                    //opts += "<a href='javascript:download(\"" + row.fileAddress + "\")'>下载</a>";
	                     var fileType = checkFile(row.fileAddress);
	                    if(fileType == 2){
	                        //视频
	                        for ( var i = 0; i < dataArr.length; i++) {
	                            opts += "<a onclick=fileDetail('"+ encodeURIComponent(dataArr[i]) +"')>查看</a><span>|</span></li>";
	                        }
	                    } else if(fileType == 1){
	                        //图片或未识别
	                        opts = "<ul class='imgZoom' id='index"+ index +"' style='display: none;'>";
	                        for (var i = 0; i < dataArr.length; i++) {
	                            opts += "<li style='float: left; margin: 5px;'><img data-original='"+ dataArr[i] +"' src='"+ dataArr[i] +"'/></li>";
	                        }
	                        opts += "</ul><a href='javascript:showImgViewer(\""+ index +"\");' class='pictureList'>查看</a>";
	                    } else if(fileType == 3){
	                        // pdf文件
	                        for ( var i = 0; i < dataArr.length; i++) {
	                        	if(i == dataArr.length - 1){
	                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a></li>";
	                        	}else{
	                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
	                        	}
	                        }
	                    } else {
	                        // pdf文件
	                        for ( var i = 0; i < dataArr.length; i++) {
	                        	if(i == dataArr.length - 1){
	                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a></li>";
	                        	}else{
	                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
	                        	}
	                        }
	                    }
	                    setTimeout(function () {
	                        $(".datagrid").find(".imgZoom").viewer();
	                    }, 100);
	                    opts = opts + "| <a href='#' onclick='javascript:deleteFileUpload(" + row.id + ")'>删除</a>"
	                    return opts;
	                }
	            } ] ],
	            toolbar : [ {
	                iconCls : 'icon-package-down',
	                text : '批量下载',
	                handler : function() {
	                    //获取选中行所有数据
	                    var list = $("#datagrid").datagrid("getChecked");
	                    var fileAddress = "";
	                    for(var i=0; i < list.length; i++){
	                        fileAddress += list[i].fileAddress + ",";
	                    }
	                    fileAddress = fileAddress.substring(0,fileAddress.length-1);
	                    if(fileAddress == "" || fileAddress == ","){
	                        $.messager.alert("文件下载错误", "请选择要下载的文件", "error");
	                    }else{
	                        download(fileAddress);
	                    }
	                }
	            },{
	                iconCls : 'icons-upload',
	                text : '上传附件',
	                handler : function() {
	                    var fileEnble = $("#fileEnble").val();
	                    if(fileEnble == "2"){
	                        $.messager.alert("警告", "您没有权限上传文件", "warning");
	                    }else{
	                        doEdit();
	                    }
	                }
	            } ],
	            loadFilter : function(data) {
	                if (data.success) {
	                } else {
	                    if(isQuery){
	                        nullData(data);
	                    }else{
	                        queryProduct();
	                    }
	                }
	                return data;
	            }
	        });
	        
	    }
});
	var isQuery = false;
	function queryProduct() {
		isQuery = true;
		var param = {};
		var projectName = $('#projectName').val();
		if (projectName != "") {
			param.projectId = projectName;
		}
		var fileTypeName = $('#fileTypeName').val();
		if (fileTypeName != "") {
			param.fileId = fileTypeName;
		}
		var fileTypeLst = $("#fileTypeLst").val();
		if (fileTypeLst != "") {
            param.fileIds = fileTypeLst;
        }
		var fileName = $('#fileName').val();
		if (fileName != "") {
			param.fileName = fileName;
		}
		var basicId = $("#basicIds").val();
		if (basicId != "") {
			param.basicId = basicId;
		}
		var uploadDateStart = $("#uploadDateStart").datebox("getValue");
		var uploadDateEnd = $("#uploadDateEnd").datebox("getValue");
		if(uploadDateStart != ""){
			param.uploadDateStart = uploadDateStart;
		}
		if(uploadDateEnd != ""){
			param.uploadDateEnd = uploadDateEnd;
		}
		$("#datagrid").datagrid("load", param);
	}

	function clearForm() {
		$(".query #form")[0].reset();
		$(".query :input[type='hidden']").val("");
	}
	
</script>

<body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
		<div data-options="region:'north',split:false,border:false"
			style="height:auto;">
			<div class="query">
				<form id="form" class="inner-q" onsubmit="return false;">
					<table cellpadding="1" cellspacing="1" border="1">
						<tr>
							<th width="9%">项目名称</th>
							<td width="20%">
                                <select id="projectName" name="projectName" style="width:250px;" class="select"></select></td>
							<th width="9%">文件类型</th>
							<td width="20%">
                                <select id="fileTypeName" name="fileTypeName" style="width:170px;" class="select"></select>
                                <input type="hidden" id="fileTypeLst" name="fileTypeLst" value="">
                            </td>
							<th width="9%">文件名</th>
							<td width="20%"><input type="text" id='fileName'
								name='fileName'  style="width:130px;" class='input'></td>
							<th>&nbsp;</th>
						</tr>
						<tr><th>资料类别</th>
                            <td>
                               <select id="basicIds" name="basicId" style="width:250px;" class="select"></select>
                            </td>
	                        <th>上传时间</th>
                            <td colspan="4">
                                <input type="text" id="uploadDateStart" name="uploadDateStart" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                                ~ 
                                <input type="text" id="uploadDateEnd" name="uploadDateEnd" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                            </td>
							<td class="bar" colspan="2" style="text-align:right;padding-right: 18px;">
								<input type="button" class="btn" onclick="queryProduct()" value="搜索" />
								<input type="button" class="btn" onclick="clearForm()" value="清空" />
								<input type="hidden" name="fileEnble" id="fileEnble" value="1">
							</td>
						</tr>
					</table>
				</form>
			</div>
		</div>
		<div data-options="region:'center',border:false" class="data-area">
			<div id="datagrid"></div>
		</div>
	</div>
</body>
<jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>
