<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>基础数据维护</title>
	<jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
	<script type="text/javascript" src="${res_js }/jslib/basicData.js?v=${ver}"></script>
	<style>
	    .myTable {
			padding: 50px 0px 0px 20px;
	   	}

	    .myTable tr {
			background-color: #fff;
			height: 30px;
	    }
    </style>
	<script type="text/javascript">
		$(function(){
			$("#datagrid").datagrid({
				url: "/company/user/getBasic.htm",
				title: '基础数据列表',
				showFooter: true,
				pagePosition: 'bottom',
				pagination: true,
				rownumbers: true,
				pageSize: 20,
				fitColumns: true,
				fit: true,
				sortName: 'id',
				style: 'overflow:hidden',
				singleSelect: true,
				queryParams: {
				},
				columns: [[{
	                field: 'categoryId',
	                title: '类别编号',
	                align: 'center',
	                width: 80
	            },{
	                field: 'categoryName',
	                title: '类别名称',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'attributeId',
	                title: '区分编号',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'attributeName',
	                title: '区分名称',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'createTime',
	                title: '发布时间',
	                align: 'center',
	                width: 100,
	                formatter: function (value, row, index) {
	                    return getDay(value.time, "yyyy/MM/dd hh:mm");
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
	                id: 'insert',
	                iconCls: 'icons-add',
	                text: '新建',
	                handler: function () {	                  
	                     doEdit();
	                }
	            }],
	            loadFilter: function (data) {	            
	                if(${basicDataAddAuth}){
	                    $('div.datagrid div.datagrid-toolbar').show();
	                }else{
	                	$('div.datagrid div.datagrid-toolbar').hide();
	                }
	                
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
			if(${basicDataUpdateAuth}){
	           opts +="<a href='javascript:editProduct("+row.id+")'>修改</a>";
	        }
			if(${basicDataDeleteAuth}){
			   if(${basicDataUpdateAuth}){
			    opts +=" | <a href='javascript:commitProduct("+row.id+")'>删除</a>";
			   }else{
			    opts +="<a href='javascript:commitProduct("+row.id+")'>删除</a>";
			   }
			  
			}
			return opts;
		}

		function queryProduct(){
			var param = {};
			var categoryName=$('#queryCategoryName').val();
			var attributeName=$('#queryAttributeName').val();
			if(categoryName != ""){
			 param.categoryName=categoryName;
			}
			if(attributeName != ""){
			 param.attributeName=attributeName;
			}
			$("#datagrid").datagrid("load", param);
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
                    	<th>类别名称</th>
                        <td>
                            <input type="text" id="queryCategoryName"name="categoryName" class="input">
                        </td>
                        <th>区分名称</th>
                        <td>
                            <input type="text" id="queryAttributeName"name="attributeName" class="input">
                        </td>
                        <th></th>
                        <td></td>
                        <th></th>
                        <td class="bar"><input type="button" class="btn"
                                               onclick="queryProduct()" value="搜索"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
                                type="reset" class="btn" value="清空" />
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
             <tbody class="viTbody" >
             </tbody>
         </table>
    </div>
</div>
</body>
</body>
</html>