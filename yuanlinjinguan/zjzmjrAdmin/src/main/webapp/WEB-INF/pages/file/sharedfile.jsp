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
<script type="text/javascript" src="${res_js }/jslib/sharedFile.js?v=${ver}"></script>
</head>
<style type="text/css">
	* {
		font-size: 14px;
	}
</style>
<script type="text/javascript">
	$(function() {
    	$("#datagrid").datagrid({
            url : '/sharedFile/user/getSharedFile.htm',
            showFooter : true,
            title : '共享文件列表',
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
            queryParams : {},
            pageList : [ 5, 10, 20 ],
            columns : [ [ {
                    field : 'ck',
                    checkbox : true
            },{
                field : 'fileName',
                title : '资料名称',
                width : 150,
                align : 'center'
            },{
                field : 'saveName',
                title : '文件名',
                width : 150,
                align : 'center'
            },{
                field : 'fileExplain',
                title : '文件说明',
                width : 110,
                align : 'center'
            },{
                field : 'admin',
                title : '上传人',
                width : 90,
                align : 'center',
                formatter : function(value,row) {
                	if (row.admin) {
                		return row.admin.name;
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
                title : '操作',
                align : 'center',
                width : 100,
                formatter : function(value, row, index, user) {
                    var opts = "";
                    var dataArr = row.saveAddress.split(",");
                    //opts += "<a href='javascript:download(\"" + row.saveAddress + "\")'>下载</a>";
                     var fileType = checkFile(row.saveAddress);
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
                        opts += "</ul><a href='javascript:showImgViewer(\""+ index +"\");' class='pictureList'>查看</a><span>|</span>";
                    } else if(fileType == 3){
                        // pdf文件
                        for ( var i = 0; i < dataArr.length; i++) {
                        	if(i == dataArr.length - 1){
                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
                        	}else{
                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
                        	}
                        }
                    } else {
                        // pdf文件
                        for ( var i = 0; i < dataArr.length; i++) {
                        	if(i == dataArr.length - 1){
                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
                        	}else{
                                opts += "<a target='_blanck' href='"+ dataArr[i] +"'>查看</a><span>|</span></li>";
                        	}
                        }
                    }
                    setTimeout(function () {
                        $(".datagrid").find(".imgZoom").viewer();
                    }, 100);
                    opts = opts + "<a href='#' onclick='javascript:deleteFileUpload(" + row.id + ")'>删除</a>"
                    return opts;
                }
            } ] ],
            toolbar : [ {
                iconCls : 'icon-package-down',
                text : '批量下载',
                handler : function() {
                    //获取选中行所有数据
                    var list = $("#datagrid").datagrid("getChecked");
                    var saveAddress = "";
                    for(var i=0; i < list.length; i++){
                        saveAddress += list[i].saveAddress + ",";
                    }
                    saveAddress = saveAddress.substring(0,saveAddress.length-1);
                    if(saveAddress == "" || saveAddress == ","){
                        $.messager.alert("文件下载错误", "请选择要下载的文件", "error");
                    }else{
                        download(saveAddress);
                    }
                }
            },{
                iconCls : 'icons-upload',
                text : '上传附件',
                handler : function() {
                	doEdit();
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
	});
	
	var isQuery = false;
	function queryProduct() {
		isQuery = true;
		var param = {};
		var userIame = $('#queryUserName').val();
		if (userIame != "") {
			param.name = userIame;
		}
		var fileName = $('#queryFileName').val();
		if (fileName != "") {
			param.fileName = fileName;
		}
		var startDate = $("#startDate").datebox("getValue");
		var endDate = $("#endDate").datebox("getValue");
		if(startDate != ""){
			param.startDate = startDate;
		}
		if(endDate != ""){
			param.endDate = endDate;
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
					<table cellpadding="0" cellspacing="0" border="0">
						<tr>
							<th>上传人</th>
							<td><input type="text" id='queryUserName'
								name='name'  style="width:130px;" class='input'></td>
							<th >文件名</th>
							<td><input type="text" id='queryFileName'
								name='fileName'  style="width:130px;" class='input'></td>
							<th>上传时间</th>
                            <td colspan="4">
                                <input type="text" id="startDate" name="startDate" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                                ~ 
                                <input type="text" id="endDate" name="endDate" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                            </td>
						</tr>
						<tr>
							<td colspan="7"/>
							<td class="bar" colspan="2" style="text-align:right;padding-right: 18px;">
								<input type="button" class="btn" onclick="queryProduct()" value="搜索" />
								<input type="button" class="btn" onclick="clearForm()" value="清空" />
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
<jsp:include page="/WEB-INF/pages/file/sharedFileUpLoad.jsp" />
</html>
