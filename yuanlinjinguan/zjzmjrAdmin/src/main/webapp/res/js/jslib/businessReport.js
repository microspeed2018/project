var dat;
function queryProduct() {
	var param = {};
	var projectLeader = $("#projectLeader").val();
	if (projectLeader <= 0) {
		param.projectLeader = "";
	} else {
		param.projectLeader = projectLeader;
	}
	var managePerson = $("#managePerson").val();
	if (managePerson <= 0) {
		param.managePerson = "";
	} else {
		param.managePerson = managePerson;
	}
	var projectStep = $("#step").val();
	if (projectStep <= 0) {
		param.projectStep = "";
	} else {
		param.projectStep = projectStep;
	}
	var timeStart = $('#createTimeStart').datebox('getValue');
	if (timeStart) {
		param.timeStart = timeStart;
	}
	var timeEnd = $('#createTimeEnd').datebox('getValue');
	if (timeEnd) {
		param.timeEnd = timeEnd;
	}
	param.contractNo = $('#contractNo').val();
	param.projectNo = $('#projectNo').val();
    param.name = $('#name').val();
    if ($('#contractAwardCompany').combobox("getValue") != "-1") {
        param.contractAwardCompany = $('#contractAwardCompany').combobox("getValue");
    }
    param.cityId = $('#cityId').val();
    if ($("#projectLeader").combobox("getValue")!= "-1") {
        param.projectLeader = $("#projectLeader").combobox("getValue");
    }
    if ($('#department').val() != "-1") {
        param.department = $('#department').val();
    }
    if ($('#attribute').val() != "-1") {
        param.nature = $('#attribute').val();
    }
    if ($('#category').val() != "-1") {
        param.category = $('#category').val();
    }
    param.investmentMountStart = $('#investmentMountStart').val();
    param.investmentMountEnd = $('#investmentMountEnd').val();
    param.designAreaStart = $('#designAreaStart').val();
    param.designAreaEnd = $('#designAreaEnd').val();
    if($("#haveTechnical-s").attr("checked")) {
      param.haveTechnical = 1;
    }

    param.ratioFrom = $('#ratioFrom').val();
    param.ratioTo= $('#ratioTo').val();
    param.contractCapitalFrom = $('#contractCapitalFrom').val();
    param.contractCapitalTo= $('#contractCapitalTo').val();
    param.projectLiable= $('#projectLiable').val();
	var names = [];
	var values = [];
	$("#columnForm label").each(function () {
		if ($(this).find("input").is(":checked")) {
			names.push($(this).find("input").attr("names"));
			values.push($(this).find("input").val());
		}
	});
	var columns =[];
	for (var i = 0; i < names.length; i++) {
		var column = {};
		column["title"] = names[i];
		column["field"] = values[i];
		column["align"] = "center";
		column["width"] = '100';
		column["sortable"] = "true";
		column["order"] = 'desc';
		columns.push(column);
	}
	$("#datagrid").datagrid("load", param);
	$('#datagrid').datagrid({columns:[ columns ]});
}

function clearForm() {
	$(".query #form")[0].reset();
	$(".query :input[type='hidden']").val("");
}
function showColumn(data, fields) {
	var dat = data;
	if (fields) {
		data = fields;
	}
	var columns = [];
	for ( var i = 0; i < data.length; i++) {
		var column = {};
		column["title"] = data[i].b;
		column["field"] = data[i].a;
		column["align"] = "center";
		column["width"] = '100';
		column["sortable"] = "true";
		column["order"] = 'desc';
		column["formatter"] = function(value,row,index){
			if ($.isS(value)) {
				return value;
			} else if ($.isN(value)) {
				if(value == 0){
					return "";
				} else {
					return $.formatMoney(value, 2);
				}
			} else {
				return value;
			}
        };
	    columns.push(column);
	}
	var pageSize = 10;
	$("#datagrid").datagrid({
		url : '/report/user/reportList.htm',
		title : '综合报表',
		headerCls : 'grid-title',
		showFooter : true,
		pagePosition : 'bottom',
		pagination : true,
		rownumbers : true,
		pageSize : pageSize,
		width : 'auto',
		fitColumns : false,
		height: 100,
		fit : true,
		sortName : 'id',
		sortOrder : 'desc',
		queryParams : {
			//businessType : 3,
			//createDateStart : $('#createDateStart').datebox('getValue')
			managePerson:$("#managePerson").val()
		},
		style : 'overflow:auto;',
		singleSelect : true,
		pageList : [10, 20, 30],
		columns : [ columns ],
		toolbar : [
		{
			iconCls : 'icons-import',
			text : '导出',
			handler : function() {
				doExport(dat);
			}
		} ],
		loadFilter : function(data) {
			if (data.success) {
			} else {
				nullData(data);
			}
			return data;
		}
	});
}

function doExport(data){
	var obj = {};
	obj.projectLeader = $("#projectLeader").val();
	obj.managePerson = $("#managePerson").val();
	obj.projectStep = $("#step").val();
	obj.timeStart = $('#createTimeStart').datebox('getValue');
	obj.timeEnd = $('#createTimeEnd').datebox('getValue');
 	var arr = [];
    $("#columnForm label").each(function () {
    	if($(this).find("input").is(":checked")) {
    		arr.push($(this).find("input").val());
    	}
    });
    if (arr.length <= 0) {
    	obj.dispField = 0;
    } else {
    	obj.dispField = arr.join(",");
    }
	if (obj.projectLeader < 0) {
		obj.projectLeader = "";
	}
	if (obj.managePerson < 0) {
		obj.managePerson = "";
	}
	if (obj.projectStep < 0) {
		obj.projectStep = "";
	}
    if (!data.status) {
        obj.status = "";
    } else {
        obj.status = data.status;
    }
    obj.contractNo = $('#contractNo').val();
    obj.projectNo = $('#projectNo').val();
    obj.name = $('#name').val();
    if ($('#contractAwardCompany').combobox("getValue") != "-1") {
    	obj.contractAwardCompany = $('#contractAwardCompany').combobox("getValue");
    }
    obj.cityId = $('#cityId').val();
    if ($("#projectLeader").combobox("getValue")!= "-1") {
    	obj.projectLeader = $("#projectLeader").combobox("getValue");
    }
    if ($('#department').val() != "-1") {
    	obj.department = $('#department').val();
    }
    if ($('#attribute').val() != "-1") {
    	obj.nature = $('#attribute').val();
    }
    if ($('#category').val() != "-1") {
    	obj.category = $('#category').val();
    }
    obj.investmentMountStart = $('#investmentMountStart').val();
    obj.investmentMountEnd = $('#investmentMountEnd').val();
    obj.designAreaStart = $('#designAreaStart').val();
    obj.designAreaEnd = $('#designAreaEnd').val();
    if($("#haveTechnical-s").attr("checked")) {
    	obj.haveTechnical = 1;
    }

    obj.ratioFrom = $('#ratioFrom').val();
    obj.ratioTo= $('#ratioTo').val();
    obj.contractCapitalFrom = $('#contractCapitalFrom').val();
    obj.contractCapitalTo= $('#contractCapitalTo').val();
    obj.projectLiable= $('#projectLiable').val();

	$.ajax({
        url : "/report/user/handleCondition.htm",
        type : 'post',
        dataType : 'json',
        data : {
           timeStart: obj.timeStart,
           timeEnd: obj.timeEnd,
           projectLeader: obj.projectLeader,
           managePerson: obj.managePerson,
           projectStep: obj.projectStep,
           status: obj.status,
           dispField: obj.dispField,
           contractNo : obj.contractNo,
           projectNo : obj.projectNo,
           name : obj.name,
           contractAwardCompany : obj.contractAwardCompany,
           cityId : obj.cityId,
           projectLeader : obj.projectLeader,
           department : obj.department,
           nature : obj.nature,
           category : obj.category,
           investmentMountStart : obj.investmentMountStart,
           investmentMountEnd : obj.investmentMountEnd,
           designAreaStart : obj.designAreaStart,
           designAreaEnd : obj.designAreaEnd,
           haveTechnical : obj.haveTechnical,
           ratioFrom : obj.ratioFrom,
           ratioTo : obj.ratioTo,
           contractCapitalFrom : obj.contractCapitalFrom,
           contractCapitalTo : obj.contractCapitalTo,
           projectLiable : obj.projectLiable,
        },
        success : function(data) {
           window.location.href = data.fileAccessUrl;
        }
    });

}
function doEdit(list) {
	$(window).resize(function(){
		$("#columnDialog").dialog({
			width : $(document).innerWidth(),
			height : $(window).innerHeight()
		});
      });
	$("#columnDialog").dialog({
		title : '列选择',
		width : $(document).innerWidth(),
		height : $(window).innerHeight(),
		closable : false,
		buttons : [ {
			iconCls : 'icons-true',
			text : '确定',
			handler : function() {
				var columnList = "";
				var columnText = "";
				$("#columnDialog input[type='checkbox']").each(function() {
					if ($(this).is(":checked")) {
						columnList += $(this).attr("name")+"."+$(this).val()+",";
						columnText += $(this)[0].nextSibling.nodeValue+",";
						$("#datagrid").datagrid('showColumn', $(this).val());
					} else{
					    $("#datagrid").datagrid('hideColumn', $(this).val());
					}
				});
				columnList = columnList.substring(0,columnList.length -1);
				columnText = columnText.substring(0,columnText.length -1);
				$("#layout input[name='columnList']").val(columnList);
				$("#layout input[name='columnText']").val(columnText);
				$("#columnDialog").dialog("close");
			}
		}, {
			iconCls : 'icons-close',
			text : '关闭',
			handler : function() {
				$("#columnDialog").dialog("close");
			}
		} ],
		onOpen : function() {
			if($("#layout input[name='columnList']").val()){
				list = $("#layout input[name='columnList']").val().split(",");
			}
			if (list[0] != "")
				$("#columnDialog input[type='checkbox']").each(function() {
				    var value = $(this).val();
					for ( var i = 0; i < list.length; i++) {
					    var list1 = list[i].split(".");
						if (value == list1[1]) {
							$(this).attr("checked", true);
						}
					}
				});
		}
	}).dialog('open');
}
function addColumnView(tbody, data) {
	var content = "";
	for (var i = 0; i < data.length; i++) {
		content+= "<label><input checked type='checkbox' names='"+ data[i].b +"' value='"+ data[i].a +"' />"+ data[i].b +"</label>";
	}
	tbody.html(content);
}
//列选择 选择事项
function chooseOne () {
	var names = [];
	var values = [];
	$("#columnForm label").each(function () {
		if ($(this).find("input").is(":checked")) {
			names.push($(this).find("input").attr("names"));
			values.push($(this).find("input").val());
		}
	});
	var columns =[];
	for (var i = 0; i < names.length; i++) {
		var column = {};
		column["title"] = names[i];
		column["field"] = values[i];
		column["align"] = "center";
		column["width"] = '100';
		column["sortable"] = "true";
		column["order"] = 'desc';
		columns.push(column);
	}
	$('#datagrid').datagrid({columns:[ columns ]});
}

function firstOpen () {
	if($("#layout input[name='columnList']").val()){
		list = $("#layout input[name='columnList']").val().split(",");
	}
	if (list[0] != "") {
		$("#columnDialog input[type='checkbox']").each(function() {
		    var value = $(this).val();
			for ( var i = 0; i < list.length; i++) {
			    var list1 = list[i].split(".");
				if (value == list1[1]) {
					$(this).attr("checked", true);
				}
			}
		});
	}
}

// 含返回true
function contains(arr, obj) {
	var i = arr.length;
	while (i--) {
		if (arr[i] === obj) {
			return true;
		}
	}
	return false;
}


/*
 * 产品种类
 */
$(function() {
	$("#productTypeId1").on("change", function() {
		var productTypeId = $(this).val();
		var deptId = $("#departmentName").val();
		getProductType(productTypeId, deptId);
	});
});

function getProductType(key, deptId) {
	$.ajax({
		url : "/product/user/getProductTypeByDeptId.htm",
		type : 'post',
		dataType : 'json',
		data : {
			productLineId : key,
			deptId : deptId
		},
		success : function(data) {
			var box = $("#productId1");
			var dom = '';
			if (data.rows)
				for ( var i = 0; i < data.rows.length; i++) {
					dom += '<option value=' + data.rows[i].id + '>'
							+ data.rows[i].name + '</option>';
				}
			box.html(dom);
			$("#custEdit input[name='rate']").val(
					data.rows[0].departProduct.executionRate);
		}
	});
}

$(function() {
	$("#debitBankId").on("change", function() {
		var bank = $(this).val();
		getUpcardArea(bank);
	});
});