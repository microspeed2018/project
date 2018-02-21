<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>图标管理</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/logoManage.js?v=${ver}">
    </script>
<script type="text/javascript">
	$(function(){
		$("#datagrid").datagrid({
            url: "/logo/user/getLogoManage.htm",
            title: '图标列表',
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
                field: 'logoNo',
                title: '图标编号',
                width: 60,
                sortable: true,
                align: 'center'
            },{
                field: 'logoTypeNo',
                title: '图标类型',
                align: 'center',
                width: 60,
                formatter: function(value, row, index) {
					if(value ==1){
						return "个人设备区";
					}
					if(value ==2){
						return "业务代理区";
					}
					if(value ==3){
						return "业务功能区";
					}
					if(value ==4){
						return "业务查询区";
					}
					if(value ==5){
						return "功能一区";
					}
					if(value ==6){
						return "功能二区";
					}
					if(value ==7){
						return "协保员专区";
					}
					if(value ==8){
						return "车贷经理专区";
					}
					if(value ==9){
						return "保险代理专区";
					}
					if(value ==10){
						return "保险审核专区";
					}
				}
            }, {
                field: 'logoComment',
                title: '图标说明',
                align: 'center',
                width: 100
            }, {
                field: 'createTime',
                title: '发布时间',
                align: 'center',
                width: 100,
                formatter: function (value, row, index) {
                    return getDay(value.time, YYYYMMDD_HHMM);
                }
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
                text: '新建图标',
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
		opts +="<a href='javascript:editProduct("+row.id+")'>修改</a> | ";
		opts +="<a href='javascript:show("+row.id+")'>查看</a> |";
		opts +="<a href='javascript:commitProduct("+row.id+")'>删除</a> ";
		return opts;
	}

	function queryProduct(){
	var param = {};
	var logoComment=$('#logoComment').val();
	param.logoTypeNo=$('#logoTypeNo').val()==-1?null:$('#logoTypeNo').val();
	if(logoComment!=""){
	 param.logoComment=logoComment;
	}
    param.createTime=$('#createTime').datebox('getValue');
    param.lastTime=$('#lastTime').datebox('getValue');
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
                        <th>图标说明</th>
                        <td>
                        <input type="text" id="logoComment"name="logoComment" class="input">
                        </td>
                        <th>图标类型</th>
							<td><select id='logoTypeNo' name='logoTypeNo'
								class='select'>
									<option value="-1">不限</option>
									<option value="1">个人设备区</option>
									<option value="2">业务代理区</option>
									<option value="3">业务功能区</option>
									<option value="4">业务查询区</option>
									<option value="5">功能一区</option>
									<option value="6">功能二区</option>
									<option value="7">协保员专区</option>
									<option value="8">车贷经理专区</option>
									<option value="9">保险代理专区</option>
									<option value="10">保险审核专区</option>
							</select></td>
                        <th>查询时间</th>
                        <td clospan="3"><input type="text" id="createTime" name="createTime"
                                               class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"> ~
                                               <input type="text" id="lastTime" name="lastTime"
                                               class="easyui-datebox e-input"data-options="height:25,width:120,editable:true"></td>
                        </tr>
                        <tr>
                        <th clospan='7'></th>
                        <td class="bar"><input type="button" class="btn"
                                               onclick="queryProduct()" value="搜索"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
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