
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>职位信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
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
                            <th>职位名称</th>
                            <td>
                                <input type="text" id="name-search" class="input">
                            </td>
                            <th>所属部门</th>
                            <td>
							<!-- <select id="departmentId-search" class="easyui-combobox" name="departmentId"  style="width:100%;" data-options="multiple:true,separator:',',editable:false,url:'/department/user/getDepartmentByCondition.htm?status=1&type=1',valueField:'id',textField:'name',onSelect:function(record){
							}"></select> -->
                                <select id="departmentId-search">
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
    <div id="detailInfo" data-options="resizable:false,closed:false,minimizable:false,maximizable:false,modal:true" style="display: none">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>职位名称</th>
                        <td>
                            <input type="text" name="name" id="name" class="input" style="width:194px"/>
                        </td>
                    </tr>
                    <tr>
                        <th>所属部门</th>
                        <td>
                        	<select id="departmentId" class="easyui-combobox" name="departmentId"  style="width:201px;height:24px;" data-options="multiple:true,separator:',',editable:false,url:'/department/user/getDepartmentByCondition.htm?status=1&type=1',valueField:'id',textField:'name',onSelect:function(record){
							}"></select>
                        	<!-- <input id="departmentId" name="departmentId" class="easyui-combobox"  style="width: 184px;" data-options="multiple:true,separator:',',editable:false" /> -->
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <!-- 公司id暂时为1为了测试 -->
        </form>
    </div>
    <jsp:include page="/WEB-INF/pages/commons/auth_unit.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/jobInfo.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
        	var accessUrl;
        	if(${hasAuth}){ 
        		accessUrl = '${ctx}/job/user/getInOutDepartment.htm';
        	}else{ 
        		accessUrl = '${ctx}/job/user/getjobDepartment.htm';
        	}
            $("#datagrid").datagrid(
                {
                	url : accessUrl,
                    title:'职位信息',
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
                            field : 'departmentId',
                            title : '职位名称',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                return row.name;
                            }
                        },
                        {
                            field : 'name',
                            title : '所属部门',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if(!isEmpty(row.companyDepartment)){
                            		var department = "";
                            		for (var i = 0; i < row.companyDepartment.length; i++){
                            			if(department.length > 0){
                            				department = department + ",";
                            			}
                            			department = department + row.companyDepartment[i].name
                            		}
                            		return department;
                            	}else{
                            		return "";
                            	}
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
                                if(row.status){
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
                                if(${hasAuth}){
                                    dom +="<a href='javascript:showBindAuth(\"JobGroupAuth\", "+row.id+", "+row.departmentId+")'>权限</a>";
                                    dom +="<span style='padding: 0 5px;'>|</span><a href='javascript:showBindMenu(\"JobGroupMenu\", "+row.id+", "+row.departmentId+")'>菜单</a>";
                                }else{
                                    dom = "";
                                    if(${jobUpdateAuth}){
                                       if(${jobSetInvalidAuth}){
                                           dom += "<a href='javascript:showDetail(" + index + ")'>修改</a><span style='padding: 0 5px;'>|</span>";
                                       }else{
                                       	   dom += "<a href='javascript:showDetail(" + index + ")'>修改</a>";
                                       }
                                    }
                                	if(${jobSetInvalidAuth}){
                                		if (row.status) {
	                                        dom += "<a href='javascript:setVal(" + index + ")'>无效</a>";
	                                    } else {
	                                        dom += "<a href='javascript:setVal(" + index + ")'>有效</a>";
	                                    }
                                	}
                                    
                                }
                                return dom;
                            }
                        }
                    ]],
                    toolbar: [{
                        iconCls: 'icons-add',
                        text: '新建',
                        handler: function () {
                            showDetail();
                        }
                    }],
                    loadFilter : function(data){
                        if(${jobAddAuth}){
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
            $("#detailInfo").show();
        	$("#departmentId").combobox('clear');
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").dialog({
                title: '职位详情',
                width: 350,
                height: 300,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                    }
                },
                buttons: [
                    {
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                            if (index != undefined) {
                                // 更新
                                $("#form1").attr("action", "/job/user/updateJob.htm");
                                $("#id").val(list.id);
                                //$("#companyId").val(list.companyId);
                                $("#form1").form("submit", {
                                    onSubmit : function() {
                                        $.messager.progress({
                                            interval : 200,
                                            text : '处理中...'
                                        });
                                        return true;
                                    },
                                    success : function(list) {                                        
                                        $("#form1")[0].reset();
                                        $.messager.progress("close");
                                        $("#detailInfo").dialog("close");
                                        $.messager.alert("信息","添加成功!","info");
                                        queryProduct();
                                    }
                                });
                            } else {
                                // 添加
                                $("#form1").attr("action", "/job/user/insertJob.htm");
                                $("#form1").form("submit", {
                                    onSubmit : function() {
                                        $.messager.progress({
                                            interval : 200,
                                            text : '处理中...'
                                        });
                                        return true;
                                    },
                                    success : function(list) {
                                        $("#form1")[0].reset();
                                        $.messager.progress("close");
                                        $("#detailInfo").dialog("close");
                                        $.messager.alert("信息","添加成功!","info");
                                        queryProduct();
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

        // 获取基础数据
        function getBasic(cb) {
            $.ajax({
                url: "/department/user/getDepartmentByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 1
                },
                success: function (data) {
                    if (data.success) {
                        cb(data.rows);
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
            });
        }
        // 获取经营部门
        getBasic(createDepartment);
        function createDepartment(datas) {
            var box = $("#departmentId"),
                boxS = $("#departmentId-search"),
                dom = "";
            var data = [];
            for (var i = 0; i < datas.length; i++) {
            	var param = {};
            	param.text = datas[i].name;
            	param.value = datas[i].id;
            	data.push(param);
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].name +"</option>";
            }
            
            //box.append(dom);
            boxS.append(dom);
            box.combobox("loadData", data);
            //boxS.combobox("loadData", data);
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
                url: "/job/user/updateJob.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    id: list.id,
                    companyId : list.companyId,
                    name : list.name,
                    departmentId : list.departmentId,
                    status: status
                },
                success: function (data) {
                    if (data.success) {
                        queryProduct();
                    }
                }
            });
        }

        // 查看赋值
        function setInfo(datas) {
            $("#name").val(datas.name);
            $("#departmentId").combobox('setValues', datas.departmentId.split(","));
        }

        function queryProduct(){
            var param = {};
            param.name = $('#name-search').val();
            if ($('#departmentId-search').val() != "-1") {
                param.departmentId = $('#departmentId-search').val();
            }
            if ($('#status-serach').val() != "-1") {
                param.status = $('#status-serach').val();
            }
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
        	$("#departmentId-search").combobox('clear');
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
    </script>
</body>
</html>