<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <script type="text/javascript">
	    $(function(){
			$("#datagrid").datagrid({
	            url: "/staffWages/user/queryStaffWages.htm",
	            title: '薪酬信息',
	            showFooter: true,
	            pagePosition: 'bottom',
	            pagination: true,
	            rownumbers: true,
	            pageSize: 20,
	            fitColumns: true,
	            fit: true,
	            sortName: 'id',
	            sortOrder: 'desc',
	            style: 'overflow:hidden',
	            singleSelect: true,
	            	queryParams: {
	            },
	            columns: [[{
	                field: 'month',
	                title: '月份',
	                align: 'center',
	                width: 60, 
	                formatter: function (value, row, index) {
	                	return row.wagesYear + "/" + row.wagesMonth;
                    }
	            }, {
	                field: 'employeeNo',
	                title: '工号',
	                align: 'center',
	                width: 60
	            }, {
                    field: 'name',
                    title: '姓名',
                    align: 'center',
                    width: 60
                }, {
	                field: 'basePay',
	                title: '基本工资',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'postPay',
	                title: '岗位工资',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'meritPay',
	                title: '绩效工资',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'bonusPay',
	                title: '奖金提成',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'professionalPay',
	                title: '职称津贴',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'silingPay',
	                title: '司龄津贴',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'otherPay',
	                title: '其他',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'paySubtotal',
	                title: '应发小计',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'absenceDeduct',
	                title: '缺勤扣款',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'socialFund',
	                title: '社保',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'housingProvident',
	                title: '公积金',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'otherDeduct',
	                title: '其他',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'deductSubtotal',
	                title: '应扣小计',
	                align: 'center',
	                width: 60
	            }, {
	                field: 'taxableWages',
	                title: '应税工资',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'individualTax',
	                title: '代扣个税',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'communicationSubsidy',
	                title: '通讯补贴',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'realWages',
	                title: '实发工资',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'memo',
	                title: '备注',
	                align: 'center',
	                width: 100
	            }]],
	            toolbar: [{
	                iconCls: 'icons-upload',
	                text: 'Excel导入',
	                id : 'importAuth',
	                handler: function () {
	                    imporlExcel();
	            	}
	            },
	            {
		            iconCls: 'icons-import',
		            text: '导出',
		            id : 'exportAuth',
		            handler: function () {
		                exportExcel();
		            }
	            }],
	            loadFilter: function (data) {
	            	if(${staffWagesImportAuth} || ${staffWagesExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${staffWagesImportAuth}){
		                        $("#importAuth").show();
		                    }else{
		                    	$("#importAuth").hide();
		                    }
		                    if(${staffWagesExportAuth}){
		                        $("#exportAuth").show();
		                    }else{
		                        $("#exportAuth").hide();
		                    }
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
	            var boxS = $("#departmentId"),
	                dom = "";
	            for (var i = 0; i < datas.length; i++) {
	                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].name +"</option>";
	            }
	            boxS.empty();
	            boxS.append("<option value='-1'>不限</option>"+dom);
	        }
		});
	    
	    function queryProduct() {
			var param = {};
			var employeeNo = $('#employeeNo').val();
			if (employeeNo != "") {
				param.employeeNo = employeeNo;
			}
			var name = $('#name').val();
			if (name != "") {
				param.name = name;
			}
			var departmentId = $("#departmentId").val();
			if (departmentId != "" && departmentId != -1) {
	            param.departmentId = departmentId;
	        }
			var mobile = $('#mobile').val();
			if (mobile != "") {
				param.mobile = mobile;
			}
			var telephone = $('#telephone').val();
			if (telephone != "") {
				param.telephone = telephone;
			}
			var status = $("#status").val();
			if (status != "" && status != -1) {
	            param.status = status;
	        }
			var startDate = $("#startDate").datebox("getValue");
			var endDate = $("#endDate").datebox("getValue");
			if(startDate != ""){
				param.startDate = startDate;
			}
			if(endDate != ""){
				param.endDate = endDate;
			}
			$("#datagrid").datagrid("load", param);
		}

		function clearForm() {
			$(".query #form")[0].reset();
			$(".query :input[type='hidden']").val("");
		}
		
		//导入excel 
		function imporlExcel() {
			$("#excelAddress").val("");
			$("#importExcel").show();
			$("#importExcel").dialog(
							{
								title : '薪酬记录导入',
								width : 530,
								height : 300,
								closable : false,
								buttons : [
										{
											id : 'save',
											iconCls : 'icons-true',
											text : '确定',
											handler : function() {
												var address = $("#excelAddress").val();
												if(address != "" && address != null && address != undefined){
													var fileNamePostfix=$("#excelAddress").val().substr($("#excelAddress").val().lastIndexOf("."));
													if(fileNamePostfix == ".xlsx" || fileNamePostfix == ".xls"){
														$("#importForm").attr("action","/staffWages/user/importExcel.htm");
														$("#importForm").form("submit",{
																			url : "/staffWages/user/importExcel.htm",
																			onSubmit : function() {
																				$("#importExcel").dialog("close");
																					$.messager.progress({
																						interval : 200,
																						text : '处理中...'
																					});
																					return true;
																			},
																			success : function(data) {
																				/* $.messager.progress("close");
																				
																				data = parseResp(data);
																				if(data.success){
																					$.messager.alert("信息", "操作成功", "info");
																				}else{
																					$.messager.alert("错误", data.resultMsg, "error");
																				} */
																				$.messager.progress("close");
																				data = parseResp(data);
																				if (data.success) {
																					$.messager.alert("信息", "操作成功", "info");
																					if(!isEmpty(data.rows)){
																						$('#datagrid').datagrid('loadData',data);
																					}else{
																						$('#datagrid').datagrid('loadData', {success : true, total: 0, rows: [] , resultMsg : ''});
																					}
																					window.location.href ="/staffWages/user/getExcel.htm";
																				} else {
																					if(!isEmpty(data.rows)){
																						$('#datagrid').datagrid('loadData',data);
																					}else{
																						$('#datagrid').datagrid('loadData', {success : true, total: 0, rows: [] , resultMsg : ''});
																					}
																					$.messager.alert("错误",data.resultMsg,"error");
																				}
																			}
																		});
													}else{
														$.messager.alert("提示","请选择需格式正确的文件", "info");
													}
												}else{
													$.messager.alert("提示","请选择文件","info");
													return false;
												}
											}
										}, {
											id : 'out',
											iconCls : 'icons-close',
											text : '取消',
											handler : function() {
												$("#importExcel").dialog("close");
											}
										} ],

							});
		}
		
		function exportExcel(){
			var employeeNo = $('#employeeNo').val();
			if (employeeNo != "") {
				$('#excelForm input[name=employeeNo]').val(employeeNo);
			}
			var name = $('#name').val();
			if (name != "") {
				$('#excelForm input[name=name]').val(name);
			}
			var departmentId = $("#departmentId").val();
			if (departmentId != "" && departmentId != -1) {
				$('#excelForm input[name=departmentId]').val(departmentId);
	        }
			var mobile = $('#mobile').val();
			if (mobile != "") {
				$('#excelForm input[name=mobile]').val(mobile);
			}
			var telephone = $('#telephone').val();
			if (telephone != "") {
				$('#excelForm input[name=telephone]').val(telephone);
			}
			var status = $("#status").val();
			if (status != "" && status != -1) {
				$('#excelForm input[name=status]').val(status);
	        }
			var startDate = $("#startDate").datebox("getValue");
			var endDate = $("#endDate").datebox("getValue");
			if(startDate != ""){
				$('#excelForm input[name=startDate]').val(startDate);
			}
			if(endDate != ""){
				$('#excelForm input[name=endDate]').val(endDate);
			}
			$("#excelForm").attr("action", "/staffWages/user/exportExcel.htm");
			$("#excelForm").submit();
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
	                        <th>工号</th>
	                        <td><input type="text" id="employeeNo"
	                                               name="employeeNo" class="input"></td>
 							<th>姓名</th>
	                        <td><input type="text" id="name"
	                                               name="name" class="input"></td>	                                             
	                        <th>所属部门</th>
							<td>
								<select id="departmentId" name="departmentId" class="select">
									<option value="-1">不限</option>
								</select>
							</td>
                            <th>工资月份</th>
                            <td colspan="4">
                                <input type="text" id="startDate" name="startDate" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                                ~ 
                                <input type="text" id="endDate" name="endDate" class="easyui-datebox e-input" data-options="height:25,editable:true"/>
                            </td>
                        </tr>
                        <tr>
	                        <th>联系电话</th>
	                        <td><input type="text" id="mobile"
	                                               name="mobile" class="input"></td>
 							<th>座机</th>
	                        <td><input type="text" id="telephone"
	                                               name="telephone" class="input"></td>	                                             
	                        <th>状态</th>
	                        <td>
	                        	<select id="status" name="status" class="select">
									<option value='-1'>不限</option>
                                    <option value='1' selected>在职</option>
                                    <option value='0'>离职</option>
								</select>
	                        </td>
	                        <td colspan="5"></td>
                        </tr>
                        <tr>
	                        <td colspan="9"></td>
	                        <td class="bar" colspan="2" style="text-align:right;padding-right: 18px;">
								<input type="button" class="btn" onclick="queryProduct()" value="搜索" />
								<input type="button" class="btn" onclick="clearForm()" value="清空" />
							</td>
	                    </tr>
	                </table>
	            </form>
	        </div>
	    </div>
	    <div data-options="region:'center',border:false" class="data-area">
	        <div id="datagrid"></div>
	        <form id="excelForm" name="excelForm" method="POST" target="_self">
				<input type="hidden"  name = "employeeNo">
				<input type="hidden"  name = "name">
				<input type="hidden"  name = "departmentId">
				<input type="hidden"  name = "startDate">
				<input type="hidden"  name = "endDate">
				<input type="hidden"  name = "mobile">
				<input type="hidden"  name = "telephone">
				<input type="hidden"  name = "status">
			</form>
	    </div>
	</div>
	<div id ="importExcel" name="importExcel" data-options="region:'center',border:false" class="data-area">
	   <form id="importForm" name="importForm" method="POST" enctype="multipart/form-data" style="padding:60px;">
	     <input type="file" id="excelAddress"  name="excelAddress" style ="width:200px;">
	   </form>
	</div>
  </body>
</html>
