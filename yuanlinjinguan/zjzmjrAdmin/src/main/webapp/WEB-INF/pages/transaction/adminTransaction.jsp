<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/adminTransaction.js?v=${ver}"></script>
    <script type="text/javascript">
	    $(function(){
		     getTypeList();
			$("#datagrid").datagrid({
	            url: "/adminTransaction/user/index.htm",
	            title: '事务列表',
	            showFooter: true,
	            pagePosition: 'bottom',
	            pagination: true,
	            rownumbers: true,
	            pageSize: 20,
	            fitColumns: false,
	            fit: true,
	            sortName: 'id',
	            sortOrder: 'desc',
	            style: 'overflow:hidden',
	            singleSelect: true,
	            queryParams: {
	                action: "getAdminTransactionList"
	            },
	            columns: [[{
	                field: 'id',
	                title: '事务ID',
	                width: 60,
	                sortable: true,
	                align: 'center',
	                order: 'desc'
	            },{
                    field: 'projectName',
                    title: '项目名称',
                    align: 'center',
                    width: 150,
                    formatter: function (value, row, index) {
	                	if(value){
	                	  return value;
	                	}else{
	                	  return "（无相关项目）";
	                	}
	                }
                },{
	                field: 'time',
	                title: '事务时间',
	                align: 'center',
	                width: 200
	            }, {
	                field: 'adminName',
	                title: '管理员',
	                align: 'center',
	                width: 100
	            }, {
	                field: 'businessType',
	                title: '事务类型',
	                align: 'center',
	                width: 120,
	                formatter: function (value, row, index) {
	                	<c:forEach items='${e:adminBusinessTypeEnum()}' var='item'>
	                    if (${item.value}==value){
	                        return '${item.message}';
	                    }
	                    </c:forEach>
	                }
	            }, {
	                field: 'result',
	                title: '处理结果',
	                width: 60,
	                align: 'center',
	                sortable: true,
	                order: 'desc',
	                formatter: function (value, row, index) {
	                	<c:forEach items='${e:handleResultEnum()}' var='item'>
	                    if (${item.value}==value){
	                        return '${item.message}';
	                    }
	                    </c:forEach>
	                }
	            }, {
	                field: 'comment',
	                title: '结果备注',
	                width: 250,
	                align: 'center',
	                sortable: true,
	                order: 'desc'
	            }, {
	                field: 'extend1',
	                title: '扩展字段1',
	                align: 'center',
	                width: 250
	                },
	                {
	                field: 'extend2',
	                title: '扩展字段2',
	                align: 'center',
	                width: 250
	                },
	                {
	                field: 'extend3',
	                title: '扩展字段3',
	                align: 'center',
	                width: 250
	                }]],
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
    </script>
    <style type="text/css">
    	* {
    		font-size: 14px;
    	}
    </style>
  </head>

  <body>
	<div class="easyui-layout" data-options="fit:true" id="layout">
	    <div data-options="region:'north',split:false,border:false"
	         style="height:auto;">
	        <div class="query">
	            <form id="form" class="inner-q" onsubmit="return false;">
	                <table cellpadding="0" cellspacing="0">
	                    <tr>
	                        <th>管理员</th>
	                        <td><input type="text" id="adminName"
	                                               name="adminName" class="input"></td>
	                        <th>事务类型</th>
	                        <td>

	                         <select id='type'>
							</select>

	                        </td>
	                        <th>扩展字段</th>
                        	<td><input type="text" id="extendedField"
                                               name="extendedField" class="input"></td>
                            <th>项目名称</th>
                        	<td><input type="text" id="projectName"
                                               name="projectName" class="input"></td>
                        </tr>
                        <tr>
	                        <td colspan="7"></td>
	                        <td class="bar">&nbsp;<input type="button" class="btn" onclick="queryAdminTransaction()" value="搜索">
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
  </body>
</html>
