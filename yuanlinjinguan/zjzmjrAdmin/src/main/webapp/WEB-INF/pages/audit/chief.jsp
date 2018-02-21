
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>总工审核</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">  
    <script type="text/javascript" src="${res_js }/jslib/viewer.min.js"></script>
    <link rel="stylesheet" href="http://www.jq22.com/demo/jQueryViewer20160329/css/viewer.min.css">
    <script type="text/javascript" src="${res_js }/jslib/fileUpLoad.js?v=${ver}"></script>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="form" class="inner-q" onsubmit="return false;">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>项目编号</th>
                            <td>
                                <input type="text" id="projectNo-search" class="input">
                            </td>
                            <th>项目名称</th>
                            <td>
                                <input type="text" id="name-search" class="input">
                            </td>
                            <th>申请人</th>
                            <td>
                                <input type="text" id="adminName-search" class="input">
                            </td>
                            <th>申请时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>审核类型</th>
                            <td>
                                <select class="select" id="type-search">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>状态</th>
                            <td>
                                <select class="select" id="status-search">
                                    <option value="-1"></option>
                                    <option value="0">未审核</option>
                                    <option value="1">通过</option>
                                    <option value="1">不通过</option>
                                </select>
                            </td>
                            <td colspan="2"></td>
                            <th>审核时间</th>
                            <td>
                                <input type="text" id="verifyTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="verifyTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th colspan="7"></th>
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
            <div id="fileDiv">

           </div>
        </div>
        <div data-options="region:'center',border:false" class="data-area">
            <div id="datagrid"></div>
        </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/chiefAudit/user/getProjectChiefAudit.htm',
                    title:'总工审核',
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
                            field : 'companyName',
                            title : '项目编号',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                return row.gardenProject.projectNo;
                            }
                        },
                        {
                            field : 'legalPerson',
                            title : '项目名称',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.gardenProject.name;
                            }
                        },
                        {
                            field : 'registeredCapital',
                            title : '审核类型',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.basicData.attributeName;
                            }
                        },
                        {
                            field : 'duration',
                            title : '申请人',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                            if(row.admin!=null){
                             return row.admin.name;
                            }else{
                             return "";
                            }
                            }
                        },
                        {
                            field : 'mobile',
                            title : '申请时间',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                               return getDay(row.createTime.time, YYYYMMDD_HHMM);
                            }
                        },
                        {
                            field : 'createTime',
                            title : '审核时间',
                            width : 100,
                            align:'center',
                            formatter: function (value, row, index) {
                            if(row.status == 0){
                                return  "";
                            }else{
                                if (row.updateTime) {
                                    return getDay(row.updateTime.time, YYYYMMDD_HHMM);
                                } else {
                                    return "";
                                }
                            }  
                            }
                        },
                        {
                            field : 'status',
                            title : '状态',
                            width : 80,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(row.status == 0){
                                    return "未审核";
                                } else if(value == 1) {
                                    return "通过";
                                } else {
                                    return "不通过";
                                }
                                return "";
                            }

                        },{
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 80,
                            formatter: function (value, row, index) {
                                if (!row.status) {
                                    if(row.type==57){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+7+")'>查看</a>";                                
                                    }else if(row.type==58){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+11+")'>查看</a>";                                
                                    }else if(row.type==59){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+14+")'>查看</a>";                                
                                    }else if(row.type==60){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+17+")'>查看</a>";                                
                                    }else if(row.type==61){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+20+")'>查看</a>";                                
                                    }else if(row.type==77){
                                       return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+9+")'>查看</a>";
                                    }
                                }
                            },
                        }
                    ]],
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

        // 获取基础数据
        function getBasic(id, cb) {
            $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    categoryId : id
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

        // 获取审核类型
        getBasic(15, creteType);
        function creteType(datas) {
            var box = $("#type-search"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.append(dom);
        }

        // 通过/不通过
        function result(index, status) {
            var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            var projectId = list.gardenProject.id;
            var step = list.gardenProject.step;
            var subStep2 = list.gardenProject.subStep2;
            var companyId = list.companyId;
            var applicant = list.applicant;
            var type = list.type;
            var isMajorProject = list.gardenProject.isMajorProject;
            if(type==57){
              step = "";
            }else{
              subStep2 = "";
            }
            if(status==2){
              $.messager.prompt('提示', '请输入不通过理由', function(r){
	             if (r){
		           $.ajax({
                   url: "/chiefAudit/user/updateChiefAuditById.htm",
                   type: 'post',
                   dataType: 'json',
                   data: {
                       status : status,
                       projectId : projectId,
                       step : step,
                       subStep2 : subStep2,
                       companyId : companyId,
                       applicant : applicant,
                       type : type,
                       isMajorProject :isMajorProject,
                       memo:r 
                   },
                   success: function (data) {
                       if (data.success) {
                           $.messager.alert("提示", "提交成功", "info");
                           queryProduct();
                       } else {
                           $.messager.alert("错误", data.errorMsg, "error");
                       }
                   }
                  });
	             }
              });          
            }else{
                $.ajax({
                url: "/chiefAudit/user/updateChiefAuditById.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : status,
                    projectId : projectId,
                    step : step,
                    subStep2 : subStep2,
                    companyId : companyId,
                    applicant : applicant,
                    type : type,
                    isMajorProject :isMajorProject 
                },
                success: function (data) {
                    if (data.success) {
                        $.messager.alert("提示", "提交成功", "info");
                        queryProduct();
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
            });
            }           
        }

        function queryProduct(){
            var param = {};
            param.projectNo= $('#projectNo-search').val();
            param.name= $('#name-search').val();
            param.adminName= $('#adminName-search').val();
            if ($('#type-search').val() != -1) {
                param.type = $('#type-search').val();
            }
            if ($('#status-search').val() != -1) {
                param.status = $('#status-search').val();
            }
            param.createTimeStart= $('#createTimeStart').datebox('getValue');
            param.createTimeEnd= $('#createTimeEnd').datebox('getValue');
            param.verifyTimeStart= $('#verifyTimeStart').datebox('getValue');
            param.verifyTimeEnd= $('#verifyTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
    </script>
</body>
<jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>