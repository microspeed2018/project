<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>节假日查询</title>
	<jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
	<script type="text/javascript" src="${res_js }/jslib/holiday.js?v=${ver}"></script>
	<!-- 引入日期控件 -->
	<jsp:include page="/WEB-INF/pages/commons/datepicker.jsp"/>
<script type="text/javascript">
	$(function() {
		$("#datagrid").datagrid(
				{
					url : "/holiday/user/holiday.htm",
					fit : true,
					showFooter : true,
					pagePosition : 'bottom',
					pagination : true,
					rownumbers : true,
					pageSize : 20,
					width : 'auto',
					fitColumns : true,
					style : 'overflow:auto',
					singleSelect : true,
					queryParams : {
						action : "getHolidayList"
					},
					pageList : [ 10,20, 30],
					columns : [ [
							{
								field : 'id',
								title : '节假日ID',
								width : 60,
								sortable : true,
								align : 'center',
								order : 'desc'
							},
							{
								field : 'holidayTime',
								title : '节假日',
								width : 100,
								align : 'center',
							},
							{
								field : 'tflag',
								title : '是否节假日',
								width : 100,
								align : 'center',
							},
							{
								field : 'opt',
								title : '操作',
								width : 100,
								align : 'center',
								formatter : function(value, row, index) {
									var opt = "";
									opt += "<a href='javascript:updateHoliday(" + row.id + "," + row.holidayTime +","+ row.flag+ ")'>修改</a> | ";
									opt += "<a href='javascript:commitHoliday(" + row.id + ")'>删除</a> ";
									return opt;
								}

							} ] ],
					toolbar : [ {
						iconCls : 'icon-add',
						text : '添加节假日',
						handler : function() {
							doEdit();
						}
					} ],
					loadFilter : function(data) {
						if (data.success && data.rows) {
							return data;
						} else {
							$.messager.alert("错误", data.resultMsg, "error");
							data.rows = [];
						}
						return data;
					}

				});
	});
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
	                        <th>节假日时间</th>
	                        <td>
	                        <input type="text" id="holidayTime"name="holidayTime" class="easyui-datebox e-input" data-options="height:25,width:120,editable:true">
	                        </td>

	                        <td colspan="5"></td>
	                        <td class="bar"><input type="button" class="btn"
	                                               onclick="queryHoliday()" value="搜索"/>&nbsp;<input
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
		<input type="hidden" id="action" name="action" value="${action}">
	</div>
	<div id="holidayEdit"
	     data-options="title:'添加产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
	    <div class="dg-content" >
	        <form id="holidayForm" method="post" enctype="multipart/form-data">
	            <table class="myTable" cellpadding="0" cellspacing="0" border="0">
	                <tbody class="myTbody" >
	                </tbody>
	            </table>
	        </form>
	    </div>
	</div>
	<!-- <div id="productDialog"
	     data-options="title:'查看产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
	    <div class="dg-content">
	        <table class="viTable" cellpadding="0" cellspacing="0" border="0">
	             <tbody class="viTbody">
	             </tbody>
	         </table>
	    </div>
	</div> -->
</body>
</html>