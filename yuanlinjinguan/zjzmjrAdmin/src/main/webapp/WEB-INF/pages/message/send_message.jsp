
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>商户端消息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
<script type="text/javascript">
	$(function() {
	$("#datagrid").datagrid(
		{
			url : '${ctx}/sendMessage/user/getSendMessage.htm',
			title:'商户消息申请列表',
			headerCls:'grid-title',
			showFooter : true,
			pagePosition : 'bottom',
			pagination : true,
			rownumbers:true,
			pageSize : 20,
			width : 'auto',
			fitColumns : true,
			fit:true,
			sortName:'id',
		    sortOrder:'desc',
			style : 'overflow:auto;',
			singleSelect : true,
			queryParams : {
				includeSystem:false
			},
			pageList : [ 10,20, 30],
			columns:[[
			          	{
			          		field : 'merchantName',
							title : '消息发布商户',
							width : 80,
							align:'center',
							formatter: function (value, row) {
                	        if(row.merchant){
                             return row.merchant.merchantName;
                	       }else{
                		     return "";
                	     }
                	     }
			          	},
			          	{
			          		field : 'title',
							title : '消息标题',
							width : 80,
							align:'center'
			          	},
			          	{
			          		field : 'context',
							title : '消息内容',
							width : 180,
							align:'left'
			          	},
			          	{
			          		field : 'createTime',
							title : '发布时间',
							width : 50,
							align:'center',
						    formatter: function (value, row, index) {
                           return getDay(value.time, YYYYMMDD_HHMM);
                        }
			          	},
			          	{
			          		field : 'status',
							title : '状态',
							width : 40,
							align:'center',
							formatter: function(value, row, index) {
								if(value ==10){
									return "申请中";
								}
								if(value ==20){
									return "审核通过";
								}
								if(value ==30){
									return "审核失败";
								}
								return "";

							}

			          	},{
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                            return getOptByStatus(row, index);
                },


            }]],
            loadFilter : function(data){
		            	if (data.success) {
							return data;
						} else {
							nullData(data);
						}
		            	return data;
		            }



		}
	);
});

function getOptByStatus(row, index){
		var opts = "";
		var status = row.status;
		if(10==status){
		opts+="<a href='javascript:verifyProduct("+row.id+", 20)'>审核通过</a> | ";
		opts+="<a href='javascript:verifyProduct("+row.id+", 30)'>审核不通过</a> ";

		}

		return opts;
	}

function reflushDatagrid(){
	$("#datagrid").datagrid("reload");
}

function queryProduct(){
	var param = {};
	param.merchantName=$('#merchantName').val();
	param.createTime=$('#createTime').datebox('getValue');
	param.lastTime=$('#lastTime').datebox('getValue');
	param.status=$('#status').val()==-1?null:$('#status').val();
	param.title=$('#title').val();

	$("#datagrid").datagrid("load", param);
}

function queryProductReset(){

	$(".query #form")[0].reset();
    $(".query :input[type='hidden']").val("");
}
function verifyProduct(id, status){
	$.messager.confirm("消息审核", "是否确认提交审核？", function (r) {
        if (r) {
        	doVerify(id, status);
        }
    });
}

function doVerify(id, status) {
    $.ajax({
        url: "/sendMessage/user/reviewMessage.htm",
        type: 'post',
        dataType: 'json',
        data: {
        	status: status,
        	id: id
        },
        beforeSend: addProcess(),
        success: function (data) {
            $.messager.progress("close");
            if (data.success) {
                $.messager.alert("信息", "消息审核成功", "info");
            } else {
                $.messager.alert("错误", data.errorMsg, "error");
            }
            queryProduct();
        }
    });
}


</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="layout">
    <div data-options="region:'north',split:false,border:false"
         style="height:180px;">
        <div class="query">
            <form id="form" class="inner-q" onsubmit="return false;">
                <table cellpadding="0" cellspacing="0">
                    <tr>
							<th>发布商户</th>
							<td><input type="text" id="merchantName"
								name="merchantName" class="input"></td>

							<th>发布时间</th>
                            <td width="32%"><input type="text" id="createTime" name="createTime"
                                               class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"> ~
                                               <input type="text" id="lastTime" name="lastTime"
                                               class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"></td>

							<th>消息状态</th>
							<td><select type="text" id="status"
								name="status" class="select">
						            <option value="-1">不限</option>
									<option value="10">申请中</option>
									<option value="20">审核通过</option>
									<option value="30">审核失败</option>
						    </select></td>
                       <th>消息标题</th>
					   <td><input type="text" id="title"name="title" class="input"></td>
                    </tr>
                    <tr>
                        <td colspan="7"></td>
                        <td class="bar"><input type="button" class="btn"
                                               onclick="queryProduct()" value="搜索"/>&nbsp;<input
                                type="reset" class="btn" value="清空" onclick="queryProductReset()"/>
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" class="data-area">
        <div id="datagrid"></div>
    </div>
    <input type="hidden" id="action" name="action" value="${action}">
</div>
<div id="productEdit"
     data-options="title:'添加产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
    <div class="dg-content">
        <form id="productForm" method="post" enctype="multipart/form-data">
            <table class="myTable" cellpadding="0" cellspacing="0" border="0">
                <tbody class="myTbody">
                </tbody>
            </table>
        </form>
    </div>
</div>
<div id="productDialog"
     data-options="title:'查看产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
    <div class="dg-content">
        <table class="viTable" cellpadding="0" cellspacing="0" border="0">
             <tbody class="viTbody">
             </tbody>
         </table>
    </div>
</div>
</body>
</html>