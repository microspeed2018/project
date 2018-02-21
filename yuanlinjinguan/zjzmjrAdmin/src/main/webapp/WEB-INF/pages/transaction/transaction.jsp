<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/transaction.js?v=${ver}"></script>
<script type="text/javascript">
	$(function(){
	     getTypeList();
		$("#datagrid").datagrid({
            url: "/transaction/user/index.htm",
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
                action: "getTransactionList"
            },
            columns: [[{
                field: 'id',
                title: '事务ID',
                width: 60,
                sortable: true,
                align: 'center',
                order: 'desc'
            },
                {
                field: 'time',
                title: '事务时间',
                align: 'center',
                width: 200
            }, {
                field: 'userName',
                title: '用户名',
                align: 'center',
                width: 100
            }, {
                field: 'businessType',
                title: '事务类型',
                align: 'center',
                width: 120,
                formatter: function (value, row, index) {
                	<c:forEach items='${e:businessTypeEnum()}' var='item'>
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
</head>
<body>
<div class="easyui-layout" data-options="fit:true" id="layout">
    <div data-options="region:'north',split:false,border:false"
         style="height:auto;">
        <div class="query">
            <form id="form" class="inner-q" onsubmit="return false;">
                <table cellpadding="0" cellspacing="0">
                    <tr>
                        <th>用户名</th>
                        <td><input type="text" id="userName"
                                               name="userName" class="input"></td>
                        <th>事务类型</th>
                        <td>

                         <select id='type'>
						</select>
                     	</td>
						<th>扩展字段</th>
                        <td><input type="text" id="extendedField"
                                               name="extendedField" class="input"></td>
                        <td colspan="3"></td>
                        <td class="bar">&nbsp;<input type="button" class="btn" onclick="queryTransaction()" value="搜索">
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