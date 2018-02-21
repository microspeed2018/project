
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>部门信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <style type="text/css">
        select {
            width: 100%;
        }
    </style>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="form" class="inner-q" onsubmit="return false;">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
    						<th>部门名称</th>
    						<td>
                                <input type="text" id="name-search" class="input">
                            </td>
    						<th>部门负责人</th>
                            <td>
                                <select id="departmentLeader-search">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>状态</th>
                            <td>
                                <select id="status-serach">
                                    <option value="-1"></option>
                                    <option value='1'>有效</option>
                                    <option value='0'>无效</option>
                                </select>
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <td colspan="7"></td>
                            <td class="bar">
                                <input type="button" class="btn" value="搜索" onclick="queryProduct()" />
                                <input type="reset" class="btn" value="清空" onclick="queryProductReset()" />
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
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>部门名称</th>
                        <td>
                            <input type="text" name="name" id="name" class="input" />
                        </td>
                    </tr>
                    <tr>
                        <th>部门负责人</th>
                        <td>
                            <select name="departmentLeader" id="departmentLeader">
                                <option value="-1"></option>
                            </select>
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <!-- 公司id暂时为1为了测试 -->
        </form>
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/department/user/getDepartmentStaff.htm',
                    title:'部门信息',
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
                            field : 'name',
                            title : '部门名称',
                            width : 80,
                            align :'center'
                        },
                        {
                            field : 'departmentLeader',
                            title : '部门负责人',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                if(row.admin)
                                {return row.admin.name;}
                                
                            }
                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 100,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(row.createTime.time, "yyyy/MM/dd hh:mm");
                            }
                        },
                        {
                            field : 'status',
                            title : '状态',
                            width : 80,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(value){
                                    return "有效";
                                } else {
                                    return "无效";
                                }
                                return "";
                            }

                        },{
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 80,
                            formatter: function (value, row, index) {
                                var dom = "";
                                if(${departmentUpdateAuth}){
                                    if(${departmentSetInvalidAuth}){
				                        dom += "<a href='javascript:showDetail(" + index + ")'>修改</a><span style='padding: 0 5px;'>|</span>";
				                    } else {
				                     	dom += "<a href='javascript:showDetail(" + index + ")'>修改</a>";
				                    }
				                }else{
				                	dom += "";
				                }
				                if(${departmentSetInvalidAuth}){
				                    if (row.status) {
	                                    dom += "<a href='javascript:setVal(" + index + ")'>无效</a>";
	                                } else {
	                                    dom += "<a href='javascript:setVal(" + index + ")'>有效</a>";
	                                }
				                }else{
				                	dom += "";
				                }
                                return dom;
                            },
                        }
                    ]],
                    toolbar: [{
                    	id:'insertAuth',
                        iconCls: 'icons-add',
                        text: '新建',
                        handler: function () {
                            showDetail();
                        }
                    }],
                    loadFilter : function(data){
                    	 if(${departmentAddAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                }else{
		                	$('div.datagrid div.datagrid-toolbar').hide();
		                }
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

        var obj = {};
        var proId;
        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '部门详情',
                width: 350,
                height: 300,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {                     
                        setInfo(list);
                    }else{
                        $("#departmentLeader").html("<option value='-1'></option>");
                    }
                },
                buttons: [
                    {
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {

                            if (index != undefined) {
                                // 更新
                                $("#form1").attr("action", "/department/user/updateDepartment.htm");
                                $("#id").val(list.id);
                                $("#companyId").val(list.companyId);
                                $("#form1").form("submit", {
                                    onSubmit : function() {
                                        $.messager.progress({
                                            interval : 200,
                                            text : '处理中...'
                                        });
                                        return true;
                                    },
                                    success : function(list) {
                                        // if(list.success) {
                                        //     $.messager.progress("close");
                                        //     $.messager.alert("信息","添加成功!","info");
                                        //     $("#detailInfo").dialog("close");
                                        //     queryProduct();
                                        // } else {
                                        //     $.messager.alert("错误",list.resultMsg,"error");
                                        // }
                                        var list = JSON.parse(list);
                                        if(list.success){
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","修改成功!","info");
                                            $("#detailInfo").dialog("close");
                                            queryProduct();
                                        } else {
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                            } else {
                                // 添加
                                $("#form1").attr("action", "/department/user/insertDepartment.htm");
                                $("#form1").form("submit", {
                                    onSubmit : function() {
                                        $.messager.progress({
                                            interval : 200,
                                            text : '处理中...'
                                        });
                                        return true;
                                    },
                                    success : function(list) {
                                        // if(list.success) {
                                        //     $.messager.progress("close");
                                        //     $.messager.alert("信息","添加成功!","info");
                                        //     $("#detailInfo").dialog("close");
                                        //     queryProduct();
                                        // } else {
                                        //     $.messager.alert("错误",list.resultMsg,"error");
                                        // }
                                        var list = JSON.parse(list);
                                        if(list.success){
                                            $("#form1")[0].reset();
                                            $("#detailInfo").dialog("close");
                                            $.messager.progress("close");
                                            $.messager.alert("信息","添加成功!","info");
                                            $("#detailInfo").dialog("close");
                                            queryProduct();
                                        } else {
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                            }
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#form1")[0].reset();
                            $("#detailInfo").dialog("close");
                        }
                    }
                ]
            });
        }

        // 设置无效有效
        function setVal(index) {
            var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            var status = list.status;
            if (status) {
                status = 0;
            } else {
                status = 1;
            }
            $.ajax({
                url: "/department/user/updateDepartment.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    id: list.id,
                    companyId : list.id,
                    name : list.name,
                    departmentLeader : list.departmentLeader,
                    status: status
                },
                success: function (data) {
                    if (data.success) {
                        queryProduct();
                    }else{
                       $.messager.alert("错误",data.resultMsg,"error");
                    }
                }
            });
        }

        // 获取部门负责人
        function getStaffPerson(department,departmentLeader) {
            $.ajax({
                url: "/staff/user/getStaffPerson.htm",
                type: 'post',
                dataType: 'json',
                data: {
                   departmentId:department,
                   notJobStatus : 111
                },
                success: function (data) {
                    if (data.success) {
                        createStaffPerson(data.rows,departmentLeader);
                    }else{
                        $("#departmentLeader").html("<option value='-1'></option>");
                    }
                }
            });
        }
        function createStaffPerson(datas,departmentLeader) {
            var box1 = $("#departmentLeader"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                if(datas[i].userInfo.id==departmentLeader){
                   dom += "<option value='"+ datas[i].userInfo.id +"' selected>"+ datas[i].userInfo.name +"</option>";
                }else{
                   dom += "<option value='"+ datas[i].userInfo.id +"'>"+ datas[i].userInfo.name +"</option>";
                }                
            }
            box1.empty();
            box1.append(dom);
        }

        // 获取搜索框部门负责人
        (function getSearchStaffPerson(department) {
            $.ajax({
                url: "/staff/user/getStaffPerson.htm",
                type: 'post',
                dataType: 'json',
                data: {
                   departmentId:department,
                   notJobStatus : 111,
                   rows:1000000
                },
                success: function (data) {                   
                    if (data.success) {
                        var datas = data.rows;
                        var box = $("#departmentLeader-search"),
                        dom = "<option value='-1'></option>";
                        for (var i = 0; i < datas.length; i++) {
                            dom += "<option value='"+ datas[i].userInfo.id +"'>"+ datas[i].userInfo.name +"</option>";
                        }
		            box.empty();
		            box.append(dom);
                    }
                }
            });
        })();
        // 查看赋值
        function setInfo(datas) {
            $("#name").val(datas.name);
            getStaffPerson(datas.id,datas.departmentLeader);
            //$("#departmentLeader").val(datas.departmentLeader);
        }

        function queryProduct(){
            var param = {};
            param.name = $('#name-search').val();
            if ($('#departmentLeader-search').val() != "-1") {
                param.departmentLeader = $('#departmentLeader-search').val();
            }
            if ($('#status').val() != "-1") {
                param.status = $('#status').val();
            }
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
    </script>
</body>
</html>