<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>地区管理</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/area.js?v=${ver}"></script>
<script type="text/javascript">
	$(function(){
		$("#datagrid").datagrid({
            url: "/area/user/getAllArea.htm",
            title: '地区列表',
            showFooter: true,
            pagePosition: 'bottom',
            pagination: true,
            rownumbers: true,
            pageSize: 20,
            fitColumns: true,
            fit: true,
            sortName: 'id',
            sortOrder: 'desc',
            style: 'overflow:hidden',
            singleSelect: true,
            queryParams: {
                action: "actionName",
            },
            columns: [[{
                field: 'id',
                title: '地区编号',
                width: 100,
                sortable: true,
                align: 'center',
                order: 'desc'
            }, {
                field: 'provCode',
                title: '省级代码',
                align: 'center',
                width: 50
            },{
                field: 'cityCode',
                title: '市级代码',
                align: 'center',
                width: 50
            }, {
                field: 'distCode',
                title: '县级代码',
                align: 'center',
                width: 50
            },{
                field: 'areaName',
                title: '地区名称',
                align: 'center',
                width: 100,
            }, {
                field: 'opt',
                title: '操作',
                align: 'center',
                width: 100,
                formatter: function (value, row, index) {
                    return getOptByStatus(row, index);
                }
            }]],
            toolbar: [{
                iconCls: 'icon-add',
                text: '新增地区',
                handler: function () {
                    doEdit();
                }
            }],
            loadFilter: function (data) {
                if (data.success && data.rows) {
                    return data;
                } else {
                    nullData(data);
                }
                return data;
            }
        });
	});

	function getOptByStatus(row, index){
		var opts = "";
		opts+="<a href='javascript:editProduct("+row.id+")'>修改</a> ";
		return opts;
	}

	function queryProduct(){
	var param = {};
	param.id=$('#id').val();
    param.areaName=$('#areaName').val();
	$("#datagrid").datagrid("load", param);
}

    function queryProductReset() {
    $(".query #form")[0].reset();
    $(".query :input[type='hidden']").val("");
}



</script>
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="layout">
    <div data-options="region:'north',split:false,border:false"
         style="height:auto;">
        <div class="query">
            <form id="form" class="inner-q" onsubmit="return false;">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <th>地区编号</th>
                        <td>
                            <input type="text" id="id"name="id" class="input">
                        </td>
                        <th>地区名称</th>
                        <td>
                            <input type="text" id="areaName"name="areaName" class="input">
                        </td>
                        <td colspan="2"></td>
                        <td></td>
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
    <div class="dg-content" >
        <form id="productForm" method="post" enctype="multipart/form-data">
            <table class="myTable" cellpadding="0" cellspacing="0" border="0">
                <tbody class="myTbody" >
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