
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>人员信息</title>
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
                            <th>联系电话</th>
                            <td>
                                <input type="text" maxlength="11" id="mobile-search" class="input">
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
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
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/staff/user/getAdminStaff.htm',
                    title:'人员信息',
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
                        jobStatus:110
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
                            field : 'employeeNo',
                            title : '员工编号',
                            width : 60,
                            align:'center',
                            formatter: function (value, row, index) {
                                if(row.staffInfo){
                                  if(row.staffInfo.employeeNo!=0){
                                     return row.staffInfo.employeeNo;
                                  } 
                                } else if(row.externalPerson){
                                  if(row.externalPerson.employeeNo!=0){
                                     return row.externalPerson.employeeNo;
                                  } 
                                } else{
                                  return "";
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
                            align :'center'
                        },
                        {
                            field : 'departmentName',
                            title : '所属部门/公司',
                            width : 60,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(row.departmentName){
                                    return value;
                                } else if(row.externalPerson){
                                    return row.externalPerson.company;
                                } else{
                                	return "";
                                }
                            }
                        },
                        {
                            field : 'jobNames',
                            title : '职位',
                            width : 60,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(row.jobNames){
                                    return value;
                                } else if(row.personnelNatureName){
                                    return row.personnelNatureName;
                                } else{
                                	return "";
                                }
                            }
                        },
                        {
                            field : 'mobile',
                            title : '联系电话',
                            width : 90,
                            align:'center'
                        },
                         {
                            field : 'telephone',
                            title : '座机',
                            width : 90,
                            align:'center' ,
                            formatter: function(value, row, index) {
                                 if(row.staffInfo){
                                  return row.staffInfo.telephone;
                                }else if(row.externalPerson){
                                  return row.externalPerson.telephone;
                                }                    
                            }
                        },
                        {
							field : 'loginSucceed',
							title : '登录/失败',
							width : 80,
							align : 'center',
							formatter: function(value,row,index){
							        return row.loginSucceed+"&nbsp;/&nbsp;"+row.loginFail;
							}
						},
                        {
                            field : 'time',
                            title : '录入时间',
                            width : 110,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if (value != null) {
                                    return getDay(value.time, "yyyy/MM/dd hh:mm");
                                } else {
                                	  if(row.staffInfo.createTime != null){
                                        return getDay(row.staffInfo.createTime.time, "yyyy/MM/dd hh:mm");
                                      }else if(row.externalPerson.createTime != null){
                                        return getDay(row.externalPerson.createTime.time, "yyyy/MM/dd hh:mm");
                                      }else{
                                    	  return "";
                                      }
                                }
                            }
                        },
                        {
                            field : 'jobStatus',
                            title : '状态',
                            width : 60,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(row.staffInfo){
                                    /* if(row.staffInfo.jobStatus==1){
                                      return "在职";
                                    }else{
                                      return "离职";
                                    } */
                                    <c:forEach items='${e:adminJobStatusEnum()}' var='item'>
	    			                    if (${item.value}==row.staffInfo.jobStatus){
	    			                        return '${item.message}';
	    			                    }
	    		                    </c:forEach>
                                   
                                } else if(row.externalPerson){
                                    if(row.externalPerson.status==1){
                                      return "在职";
                                    }else{
                                      return "离职";
                                    }
                                }
                                return "";
                            }

                        },{
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                                var oper = "";
                                    oper += "<a href='javascript:showBindAuth("+row.id+")'>权限</a> ";
                                    oper += "| <a href='javascript:showBindMenu("+row.id+")'>菜单</a> ";
                                
                                return oper;
                            }
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

        var obj = {};
        var proId;
      
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

        function queryProduct(){
            var param = {};
            param.name = $('#name-search').val();
            param.mobile = $('#mobile-search').val();
            param.telephone = $('#telephone-search').val();
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
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
			        					{action:"bindMenu",menus:ids,userId:$("#datagrid").datagrid("getSelected").id},
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
			        			var userId = $("#datagrid").datagrid("getSelected").id;

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