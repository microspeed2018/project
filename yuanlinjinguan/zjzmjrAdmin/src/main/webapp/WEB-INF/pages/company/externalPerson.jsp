
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>外部人员信息</title>
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
                            <th>姓名</th>
                            <td>
                                <input type="text" id="name-search" class="input">
                            </td>
                            <th>所属公司</th>
                            <td>
                                <input type="text" id="company-search" class="input">
                            </td>
                            <th>人员性质</th>
                            <td>
                                <select id="personnelNature-search">
                                    <option value="-1">不限</option>
                                </select>
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>联系电话</th>
                            <td>
                                <input type="text" id="mobile-search" maxlength="11" class="input">
                            </td>
                            <th>邮箱</th>
                            <td>
                                <input type="text" id="email-search" class="input">
                            </td>
                            <th>状态</th>
                            <td>
                                <select id="jobStatus-serach" name="status">
                                    <option value='-1'></option>
                                    <c:forEach var='item' items='${e:externalPersonStatusEnum() }'>
                                        <c:if test="${item.value==1}">
											<option selected value='${item.value }'>${item.message }</option>
										</c:if>
										<c:if test="${item.value!=1}">
											<option  value='${item.value }'>${item.message }</option>
										</c:if>
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
        <div id="menu_bind" class="easyui-dialog" style="width:370px;height:448px;padding:10px 10px 10px 10px" data-options="title:'菜单绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	        <div id="menu_tree">
	        </div>
	   </div>

	   <div id="auth_bind" class="easyui-dialog" style="width:800px;height:560px;" data-options="title:'权限绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	       <div id="auths">
	       </div>
	   </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                     <tr>
                        <!-- <th>用户名</th>
                        <td>
                            <input type="text" id="username" name="username" class="input" />
                        </td> -->
                        <th>姓名</th>
                        <td>
                            <input type="text" id="name" name="name" class="input" />
                        </td>
                        <th>联系电话</th>
                        <td>
                            <input type="text" id="mobile" name="mobile" maxlength="11" class="input" />
                        </td>
                     
                        <th>所属公司</th>
                        <td>
                            <input type="text" id="company" name="company" class="input" />
                        </td>
                     </tr>
                     <tr>
                        <th>外部人员性质</th>
                        <td>
                            <select  id="personnelNature" name="personnelNature" >

                            </select>
                        </td>
                        <th>职务</th>
                        <td>
                            <input type="text" id="job" name="job" class="input" />
                        </td>
                     
                        <th>邮箱</th>
                        <td>
                            <input type="text" id="email" name="email" class="input" />
                        </td>
                     </tr>
                     <tr>
                        <th>关系人</th>
                        <td>
                            <select id="relatedPerson" name="relatedPerson">
                            </select>
                        </td>
                        <th>状态</th>
                        <td>
                            <select id="jobStatus" name="jobStatus">
                                <option value='-1'></option>
									<c:forEach var='item' items='${e:externalPersonStatusEnum() }'>
									    <%-- <c:if test="${item.value==2}">
											<option selected value='${item.value }'>${item.message }</option>
										</c:if>
										<c:if test="${item.value!=2}">
											<option  value='${item.value }'>${item.message }</option>
										</c:if> --%>
										<option  value='${item.value }'>${item.message }</option>
									</c:forEach>
                            </select>
                        </td>
                     
                        <!-- <th>账户状态</th>
						<td>
							<select name="accStatus" id="accStatus" class="select">
								 <option value="0">正常</option>
								 <option value="1">冻结</option>
								 <option value="2">注销</option>
							</select>

						</td> -->
						
						<th>人员编号</th>
						<td>
						<input type="text" id="employeeNo" name="employeeNo" readonly class="input" />
						</td>
					 </tr>
                     <tr>
						<th>账号可登录</th>
                        <td>
                            <select id="accStatus" name="accStatus" class="select" >
                               <option value="0">是</option>
                               <option value="1" selected>否</option>
                            </select>
                        </td>
                     </tr>
                     <tr>
                        <th>备注</th>
                        <td colspan="5">
                            <textarea placeholder="" id="memo" name="memo"></textarea>
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="userId" name='userId' />
            <!-- 默认部门为其他 -->
            <input type="hidden" id="department" name='department' value="99" />
            <input type="hidden" id="isEmployee" name='isEmployee' value="0" />
            <!-- 员工新增标识 -->
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <input type="hidden" id="password" name='password' value="123456" />
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
                    url : '${ctx}/external/user/getExternalPerson.htm',
                    title:'外部人员信息',
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
                       jobStatus:1
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                      /*  {
                            field : 'userId',
                            title : '账号',
                            width : 80,
                            align:'center'
                        }, */
                        {
                            field : 'employeeNo',
                            title : '员工编号',
                            width : 60,
                            align:'center',
                            formatter: function (value, row, index) {
                                if(value!=0){
                                  return value;
                                }                               
                            }
                        },
                       /*  {
                            field : 'username',
                            title : '账户名',
                            width : 60,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.username;
                            }
                        }, */
                        {
                            field : 'name',
                            title : '姓名',
                            width : 70,
                            align :'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.name;
                            }
                        },
                        {
                            field : 'company',
                            title : '所属公司',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'personnelNatureName',
                            title : '人员性质',
                            width : 60,
                            align:'center'
                        },
                        {
                            field : 'mobile',
                            title : '联系电话',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.mobile;
                            }
                        },
                        {
                            field : 'relatedPersonName',
                            title : '关系人',
                            width : 70,
                            align:'center'
                        },
                        {
							field : 'loginSucceed',
							title : '后台登录次数',
							width : 80,
							align : 'center',
							formatter: function(value,row,index){
							        return row.userInfo.loginSucceed;
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
                            width : 40,
                            align:'center',
                            formatter: function(value, row, index) {
                              <c:forEach items='${e:externalPersonStatusEnum()}' var='item'>
			                    if (${item.value}==value){
			                        return '${item.message}';
			                    }
		                      </c:forEach>
                            }

                        },{
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                                var oper = "";
                                //oper += "<a href='javascript:showBindAuth("+row.userId+")'>权限</a> ";
                                //oper += "| <a href='javascript:showBindMenu("+row.userId+")'>菜单</a> ";
                                oper += "<a href='javascript:showDetail(" + index + ")'>修改</a>";
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
                        if(${externalAddAuth} || ${externalExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${externalAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${externalExportAuth}){
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

        var obj = {};
        var proId;
        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");                
            }
            $("#password").show();
            $("#pwd").html("密码");
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '外部人员信息详情',
                width: 800,
                height: 400,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        if(${externalUpdateAuth}){
		                   $("#updateAuth").show();
		                }else{
		                   $("#updateAuth").hide();
		                }
                    }else{
                      getMaxNum();
                    }
                },
                buttons: [
                    {
                        id:'updateAuth',
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                           /*  if (!$("#mobile").val()) {
                                $.messager.alert("信息", "手机号不能为空", "info");
                            } else { */
                                if (index != undefined) {
                                  if(checkData(1)){
                                   // 更新
                                    $("#form1").attr("action", "/external/user/updateExternalPerson.htm");
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
                                                //$("#jobId").html("<option value='-1'>请选择职位</option>");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","添加成功!","info");
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        }
                                    });
                                  }
                                } else {
                                    if(checkData(0)){
                                      // 添加
                                      $("#form1").attr("action","/console/admin.htm?action=createAccount");
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
                                                //$("#jobId").html("<option value='-1'>请选择职位</option>");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","添加成功!","info");
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
                            $("#form1")[0].reset();
                        }
                    }
                ]
            });
        }
        
        function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/external/user/downLoadExternalPerson.htm",
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
      
        // 获取项目性质
        getBasic(12, createCompanyType);
        function createCompanyType(datas) {
            var box = $("#personnelNature"),
                boxs = $("#personnelNature-search"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
            boxs.empty();
            boxs.append("<option value='-1'>不限</option>"+dom);
        }
        function getBasic(id,cb) {
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
         // 获取联系人
        getRelatedPerson(createRelatedPerson);
        function createRelatedPerson(datas) {
            var box = $("#relatedPerson"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].userInfo.name +"</option>";
            }
            box.empty();
            box.append(dom);
        }

        function getRelatedPerson(cb) {
            $.ajax({
                url: "/staff/user/getStaffPerson.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	notJobStatus : 111,
                    rows:1000000,
                    page:1
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
        // 查看赋值
        function setInfo(datas) {
            $("#name").val(datas.userInfo.name);
            $("#username").val(datas.userInfo.username);
            $("#mobile").val(datas.userInfo.mobile);
            $("#company").val(datas.company);
            $("#email").val(datas.email);
            $("#personnelNature").val(datas.personnelNature);
            $("#jobStatus").val(datas.jobStatus);
            $("#job").val(datas.job);
            $("#relatedPerson").val(datas.relatedPerson);
            $("#accStatus").val(datas.userInfo.accStatus);
            $("#memo").val(datas.memo);
            if(datas.employeeNo!=0){
              $("#employeeNo").val(datas.employeeNo);
            }else{
               getMaxNum();
            }
            $("#userId").val(datas.userId);
            $("#password").hide();
            $("#pwd").html("");
        }

 function checkData(num){
          var name = $("#name").val();
          //var username = $("#username").val();
          var password = $("#password").val();
          /* if(username==""){
             $.messager.alert("提示", "请输入用户名", "info");
			 return false;
          } */
          if(name==""){
             $.messager.alert("提示", "请输入姓名", "info");
			 return false;
          }
          if(num==0){
            if(password==""){
              $.messager.alert("提示", "请输入密码", "info");
			  return false;
            }
          }
          return true;
        }
        function queryProduct(){
            var param = {};
            param.name = $('#name-search').val();
            param.company = $('#company-search').val();
            if($('#jobStatus-serach').val()!=-1){
                param.jobStatus = $('#jobStatus-serach').val();
            }
            if($('#personnelNature-search').val()!=-1){
                param.personnelNature =  $('#personnelNature-search').val();
            }
            param.email = $('#email-search').val();
            param.mobile = $('#mobile-search').val();
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
        // 获取项目最大编号
        function getMaxNum() {
            $.ajax({
                url: "/external/user/getExternalPersonEmployeeMaxNo.htm",
                type: 'post',
                dataType: 'json',
                data: { },
                success: function (data) {
                    if (data.success) {
                        $("#employeeNo").val(data.data);
                    }
                }
            });
        }

        function showBindMenu(id,roleType,department){
		$("#menu_tree").tree({
			checkbox:true,
			url:"${ctx}/console/menu.htm?action=userMenuTree&userId="+id+"&timestamp="+new Date().getTime()+"&roleType="+roleType+"&department="+department,
			loadFilter:function(data){
	  			 if(data.success){
	  				 return data.userMenu.children;
	  			 }else{
	  				 $.messager.alert("警告",data.resultMsg,"error");
	  			 }
	  		 }
		});

		$("#menu_bind").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var nodes = $.merge($("#menu_tree").tree("getChecked"),$("#menu_tree").tree("getChecked","indeterminate"));
			        			var ids = "";
			        			if(nodes.length>0){
			        				for(var i=0;i<nodes.length;i++){
			        					if(i==0){
			        						ids += nodes[i].id;
			        					}else{
			        						ids += ","+nodes[i].id;
			        					}
			        				}
			        			}
			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/menu.htm",
			        					{action:"bindMenu",menus:ids,userId:$("#datagrid").datagrid("getSelected").userId},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$.messager.alert("信息","绑定成功！","info");
			        							$('#menu_bind').dialog('close');
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#menu_bind').dialog('close');
			        	 }
			         }]
		});
	}


	function showBindAuth(id){
		$("#auths").children().remove();
		$.post("${ctx}/console/auth.htm",
				{action:"userGroupAuth",userId:id},
				function(data){
					if(data.success){
						if(!$.isEmptyObject(data.authGroup)){
							var authTab = $("<table class='auth_tab'></table>").appendTo($("#auths"));
							var auths = data.authGroup;
							for(var key in auths){
								var gp = auths[key];
								if($.isArray(gp)&&gp.length>0){
									var block = $("<tr></tr>").append($("<td class='auth_title'>"+key+"</td>")).appendTo(authTab);
									var tdgp = $("<td></td>").appendTo(block);
									for(var i=0;i<gp.length;i++){
										$("<label class='auth_item'><input type='checkbox' name='auth' value='"+gp[i].id+"' "+(gp[i].checked?"checked='checked'":"")+"/>"+gp[i].authName+"</label>").appendTo(tdgp);
									}
								}
							}
						}
					}else{
						$.messager.alert("错误","加载权限数据出错！","error");
					}
				},"json");

		$("#auth_bind").dialog({
			closed:false,
			buttons:[
					{
						id : "selectAllAuth",
					    iconCls: 'icons-true',
					    text: '全选',
					    handler: function () {
					    	isSelectAllAuth();
					    }
					},
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var chks = $("#auths input[type='checkbox']:checked");
			        			var auths = "";
			        			for(var i=0;i<chks.length;i++){
			        				if(i==0){
			        					auths += $(chks[i]).val();
			        				}else{
			        					auths += ","+$(chks[i]).val();
			        				}
			        			}
			        			var userId = $("#datagrid").datagrid("getSelected").userId;

			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/auth.htm",
			        					{action:"bindAuth",userId:userId,auths:auths},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$("#auth_bind").dialog("close");
			        							$.messager.alert("消息","绑定成功！","info");
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#auth_bind').dialog('close');
			        	 }
			         }]
		});

	}

	/**
	 * 是否全选权限
	 */
	function isSelectAllAuth(){
		var textBtn = $("#selectAllAuth").find("span span").html();
		if(textBtn == "全选"){
			$("#auth_bind input[name='auth']").prop("checked",true);
			$("#selectAllAuth").find("span span").html("反选");
			$("#selectAllAuth").find("span span").removeClass("icons-true");
			$("#selectAllAuth").find("span span").addClass("icons-close");
		} else{
			$("#auth_bind input[name='auth']").prop("checked",false);
			$("#selectAllAuth").find("span span").html("全选");
			$("#selectAllAuth").find("span span").removeClass("icons-close");
			$("#selectAllAuth").find("span span").addClass("icons-true");
		}
	}
    </script>
</body>
</html>