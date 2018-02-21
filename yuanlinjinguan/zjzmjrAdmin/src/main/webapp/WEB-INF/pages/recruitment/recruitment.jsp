
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>招聘信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <style type="text/css">
        select {
            width: 100%;
        }
        #auths{
            display:block;
            width:100%;
            height:350px;
            overflow: auto;
        }
        .auth_tab{
            border-collapse:collapse;
            border-bottom: 1px solid gray;
            width:100%;
        }
       .auth_tab td{
            border-collapse:collapse;
            border-bottom: 1px solid gray;
        }
       .auth_title{
            width:80px;
            text-align: center;
            vertical-align: center;
            font-weight: bold;
            border-right:1px solid gray;
        }

       .auth_item{
            width:125px;
            display:block;
            float:left;
        }

    </style>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="form" class="inner-q" onsubmit="return false;" method="post">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>职位名称</th>
                            <td>
                                 <input type="text" id="positionNameSearch" name="positionName" class="input">
                            </td>
                            <th>工作地点</th>
                            <td>
                                 <input type="text" id="addressSearch" name="address" class="input">
                            </td>
                            <th>招聘部门</th>
                            <td>
                                <select id="departmentSearch" name="department">
                                    <option value="-1">不限</option>
                                </select>
                            </td>
                            <th>发布日期</th>
                            <td>
                                <input type="text" id="createTimeStart" name="createTimeStart" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="createTimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>学历</th>
                            <td>
                                <input type="text" maxlength="11" id="educationSearch" name="education" class="input">
                            </td>
                            <th>薪资待遇</th>
                            <td>
                                <input type="text" maxlength="20" id="salarySearch" name="salary" class="input">
                            </td>
                            <th>当前有效</th>
                            <td>
                                <select id="isValidSerach" name="isValid">
                                    <option value='-1'>不限</option>
									<c:forEach var='item' items='${e:recruitmentIsValidEnum() }'>
											<option value='${item.value }'>${item.message }</option>
									</c:forEach>
                                </select>
                            </td>
                            <th></th>
                            <td></td>
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
        </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>岗位名称</th>
                        <td>
                            <input type="text" id="positionName" name="positionName" class="input" />
                        </td>
                        <th>工作地点</th>
                        <td>
                            <input type="text" id="address" name="address" maxlength="11" class="input" />
                        </td>
                        <th>招聘部门</th>
                        <td>
                            <select name="departmentId" id="departmentId">
                                <option value="-1">请选择部门</option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                    	<th>年龄</th>
                    	<td>
                    		<input type="text" id="age" name="age" maxlength="18" class="input" />
                    	</td>
                    	<th>工作经验</th>
                    	<td>
                    		<input type="text" id="experience" name="experience" class="input" />
                    	</td>
                    	<th>学历</th>
                    	<td>
                    		<input type="text" id="education" name="education" class="input" />
                    	</td>
                    </tr>
                    
                    <tr>
                        <th>招聘人数</th>
                        <td>
                            <input type="text" id="numbers" name="numbers" class="input" />
                        </td>
                    
                        <th>薪资待遇</th>
                        <td>
                            <input type="text" id="salary" name="salary"  class="input" />
                        </td>
                        <th>当前有效</th>
                        <td>
                            <select name="isValid" id="isValid" disabled>
                                <option value='-1'></option>
									<c:forEach var='item' items='${e:recruitmentIsValidEnum() }'>
									    <c:if test="${item.value==2}">
											<option selected value='${item.value }'>${item.message }</option>
										</c:if>
										<c:if test="${item.value!=2}">
											<option  value='${item.value }'>${item.message }</option>
										</c:if>
									</c:forEach>
                            </select>
                        </td>  
                    </tr>
                    <tr>
                      <th><input type="checkbox" id="isOpen" name="isOpen" checked/>是否公开</th>
                    </tr>
                    <tr>
                        <th>岗位职责</th>
                        <td colspan="6"><script type="text/plain" id="postDutiesEditor"style="width:850px;height:200px;"></script>
                        <div class="clear"></div><input type="hidden" id="postDuties" name="postDuties"></td>
                    </tr>
                    <tr>
                        <th>任职资格</th>
                        <td colspan="6">
                        <script type="text/plain" id="qualificationEditor"style="width:850px;height:200px;"></script>
                        <div class="clear"></div><input type="hidden" id="qualification" name="qualification"></td>
                    </tr>
                </tbody>
            </table>
            <input id="id" name="id" type="hidden" />
        </form>
    </div>
    <div id="addressInfo" display="none">
    	<table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
           <tbody>
              <tr>
              	<th style="width:4% !important">邀请地址</th>
              	<td><input type="text" id="inviteAddress" name="inviteAddress" class="input" readonly /></td>
              </tr><!-- 
              <tr>
                <th style="width:4% !important"><input type="button" onclick="copyUrl()" value="复制链接" class="input"/></th>
                <td>
                	
                </td>
              </tr> -->
              <tr>
              	<th style="width:4% !important">邀请二维码</th>
              	<td>
              	  <div id="codeAddress">
              	  
              	  </div>
              	</td>
              </tr>
           </tbody>
        </table>
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/recruitment/user/getRecruitment.htm',
                    title:'招聘信息',
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
                        isValid:1
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                       /* {
                            field : 'userId',
                            title : '账号',
                            width : 80,
                            align:'center'
                        }, */
                        {
                            field : 'positionName',
                            title : '职位名称',
                            width : 90,
                            align:'center'
                        },
                        {
                            field : 'address',
                            title : '工作地点',
                            width : 70,
                            align :'center'
                        },
                        {
                            field : 'departmentName',
                            title : '招聘部门',
                            width : 70,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if(row.companyDepartment){
                            	   return row.companyDepartment.name;
                            	}
                                
                            }
                        },
                        {
                            field : 'age',
                            title : '年龄',
                            width : 60,
                            align:'center'
                        },
                        {
                            field : 'education',
                            title : '学历',
                            width : 60,
                            align:'center'
                        },
                         {
                            field : 'salary',
                            title : '薪资待遇',
                            width : 90,
                            align:'center'
                        },
                        {
							field : 'applicants',
							title : '应聘人数',
							width : 80,
							align : 'center',
							formatter: function(value, row, index) {
							   return "<a href='/talent/user/index.htm?recruitmentId="+row.id+"'>"+row.applicants+"</a>";
                            }
						},
						{
                            field : 'isValid',
                            title : '当前有效',
                            width : 60,
                            align:'center',
                            formatter: function(value, row, index) {
                            <c:forEach items='${e:recruitmentIsValidEnum()}' var='item'>
			                    if (${item.value}==value){
			                        return '${item.message}';
			                    }
		                    </c:forEach>
                            }

                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 110,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(row.createTime.time, "yyyy/MM/dd hh:mm");
                            }
                        },
                        {
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                                var oper = "";
                                    oper += "<a href='javascript:showDetail("+index+")'>详细</a>";
                                    if(${recruitmentInviteAuth}){
                                    	oper += " | <a href='javascript:setAddress("+index+")'>邀请</a> ";
                                    }
                                return oper;
                            }
                        }
                    ]],
                    toolbar: [{
                    	id:'insertAuth',
                        iconCls: 'icons-add',
                        text: '新建',
                        handler: function () {
                            showDetail();
                        }
                    },
                    {
                    	id:'exportAuth',
                        iconCls: 'icons-import',
                        text: '导出',
                        handler: function () {
                            downLoad();
                        } 
                    }],
                    loadFilter : function(data){
                    	if(${recruitmentAddAuth} || ${recruitmentExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${recruitmentAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${recruitmentExportAuth}){
		                        $("#exportAuth").show();
		                    }else{
		                        $("#exportAuth").hide();
		                    }
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

        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '招聘信息详情',
                width: 1050,
                height: 570,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        if(${recruitmentUpdateAuth}){
		                   $("#updateAuth").show();
		                }else{
		                   $("#updateAuth").hide();
		                }
                    }
                    UM.getEditor('postDutiesEditor');
                    UM.getEditor('qualificationEditor');
                },
                buttons: [
                    {
                    	id:'updateAuth',
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                            if (index != undefined) {
	                            if ($("#isOpen").attr("checked")) {
	                                $("#isOpen").val("1");
	                            } 
                                // 更新
                                if(checkData()){
                                $("#postDuties").val(UM.getEditor('postDutiesEditor').getContent());
                                $("#qualification").val(UM.getEditor('qualificationEditor').getContent());
                                $("#form1").attr("action", "/recruitment/user/updateRecruitment.htm");
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
                                        var list = JSON.parse(list);
                                        if(list.success) {
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","修改成功!","info");
                                            UM.getEditor('postDutiesEditor').setContent('');
	                                        UM.getEditor('qualificationEditor').setContent('');
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                              }
                            } else {
                                // 添加
                                if ($("#isOpen").attr("checked")) {
	                                $("#isOpen").val("1");
	                            }
                                if(checkData()){
                                $("#postDuties").val(UM.getEditor('postDutiesEditor').getContent());
                                $("#qualification").val(UM.getEditor('qualificationEditor').getContent());
                                $("#form1").attr("action","/recruitment/user/insertRecruitment.htm");
                                $("#form1").form("submit", {
                                    onSubmit : function() {
                                        $.messager.progress({
                                            interval : 200,
                                            text : '处理中...'
                                        });
                                        return true;
                                    },
                                    success : function(list) {
                                        var list = JSON.parse(list);
                                        if(list.success) {
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","添加成功!","info");
                                            UM.getEditor('postDutiesEditor').setContent('');
	                                        UM.getEditor('qualificationEditor').setContent('');
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                     }
                                 });
                               }
                            }
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#detailInfo").dialog("close");
                            UM.getEditor('postDutiesEditor').setContent('');
	                        UM.getEditor('qualificationEditor').setContent('');
                            $("#form1")[0].reset();
                        }
                    }
                ]
            });
        }
        
        //下载
        function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/recruitment/user/recruitmentInfoPrint.htm",
		    onSubmit : function() {
		  },
		  success : function(data) {
			  $.messager.progress("close");
			  data = parseResp(data);
			  if (data.success) {
			  } else {
				  $.messager.alert("错误", data.resultMsg, "error");
			  }
		  }
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
                boxS = $("#departmentSearch"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].name +"</option>";
            }
            box.empty();
            box.append("<option value='-1'></option>"+dom);
            boxS.empty();
            boxS.append("<option value='-1'></option>"+dom);
        }

        function checkData(){
          var positionName = $("#positionName").val();
          var departmentId = $("#departmentId").val();
          if(positionName==""){
             $.messager.alert("提示", "请输入招聘岗位", "info");
			 return false;
          } 
          if(departmentId==-1){
             $.messager.alert("提示", "请选择招聘部门", "info");
			 return false;
          }
          return true;
        }
        // 查看赋值
        function setInfo(datas) {
           $("#id").val(datas.id);
           $("#positionName").val(datas.positionName);
           $("#departmentId").val(datas.departmentId);
           $("#address").val(datas.address);
           $("#age").val(datas.age);
           $("#experience").val(datas.experience);
           $("#education").val(datas.education);
           $("#numbers").val(datas.numbers);
           $("#salary").val(datas.salary);
           UM.getEditor('postDutiesEditor').setContent(datas.postDuties);
	       UM.getEditor('qualificationEditor').setContent(datas.qualification);
           $("#isValid").prop("disabled",false);
           $("#isValid").val(datas.isValid);
           if(datas.isOpen==1){
              $("#isOpen").attr("checked",true);
           }else{
              $("#isOpen").attr("checked",false);
           }
        }

        function queryProduct(){
            var param = {};
            param.positionName = $('#positionNameSearch').val();
            param.address = $('#addressSearch').val();
            if($('#departmentSearch').val()!=-1){
              param.departmentId = $('#departmentSearch').val();
            }
            param.education = $('#educationSearch').val();
            param.salary = $('#salarySearch').val();
            if($('#isValidSerach').val()!=-1){
               param.isValid = $('#isValidSerach').val();
            }
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }

        function setAddress(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#addressInfo").show();
            $("#addressInfo").dialog({
                title: '招聘链接',
                width: 750,
                height: 450,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        var str = "http://service.project.cbylsj.com/tpl/resume/index.html?recruitmentId="+list.id+"&companyId="+list.companyId;
                        $("#inviteAddress").val(encodeURI(str));
                        $("#codeAddress").html("<img src='/getQrCode.htm?id='"+list.id+"'/>");
                    }
                },
                buttons: [
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#addressInfo").dialog("close");
                        }
                    }
                ]
            });
        }
      /*   function copyUrl(){
          var ele = document.getElementById("inviteAddress");
          ele.select();
          document.execCommand("Copy");
        } */
    </script>
</body>
</html>