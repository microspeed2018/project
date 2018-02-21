<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>资料维护</title>
	<jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
	<script type="text/javascript" src="${res_js }/jslib/basicFileType.js?v=${ver}"></script>
	<style>
	    .myTable {
			padding: 50px 0px 0px 20px;
	   	}

	    .myTable tr {
			background-color: #fff;
			height: 30px;
	    }
    </style>
	<script type="text/javascript">
		$(function(){
			$("#datagrid").datagrid({
				url: "/baseFile/user/fileTypeList.htm",
				title: '资料列表',
				showFooter: true,
				pagePosition: 'bottom',
				pagination: true,
				rownumbers: true,
				pageSize: 20,
				fitColumns: true,
				fit: true,
				sortName: 'id',
				sortOrder: 'asc',
				style: 'overflow:auto',
				singleSelect: true,
				queryParams: {
					accessType : 1
				},
				pageList : [ 10, 20, 30 ],
				columns: [[{
	                field: 'attributeName',
	                title: '资料分类',
	                align: 'center',
	                width: 80
	            },{
	                field: 'name',
	                title: '资料名称',
	                align: 'center',
	                width: 80
	            }, {
	                field: 'status',
	                title: '状态',
	                align: 'center',
	                width: 80,
	                formatter: function (value, row, index) {
                        if(value==0){
                            return "冻结";
                         }else if(value==1){
                           return "正常";
                         }else{
                           return "";
                         }
	                }
	            }, {
	                field: 'updateTime',
	                title: '更新时间',
	                align: 'center',
	                width: 120,
	                formatter : function(value, row) {
	                    if (value != null) {
	                        return getDay(value.time, "yyyy/MM/dd hh:mm:ss");
	                    } else {
	                        return "";
	                    }
	                }
	            }, {
	                field: 'opt',
	                title: '操作',
	                align: 'center',
	                width: 100,
	                formatter: function (value, row, index) {
	                    return getOptByStatus(row, index);
	                }
	            }]],
	            toolbar: [{
	                iconCls: 'icons-add',
	                text: '新建',
	                handler: function () {
	                  doEdit();
	                }
	            }],
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

		 // 获取基础数据
        function getBasic(id, cb) {
            $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	categoryId : id,
                    rows:1000
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
        getBasic(29, createAttribute);
        function createAttribute(datas) {
            var box = $("#basicId"),
                boxS = $("#basic-search"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            
            box.append(dom);
            boxS.append(dom);
            //box.combobox("loadData", data);
            //boxS.combobox("loadData", data);
        }
		
		function getOptByStatus(row, index){
			var opts = "";
			opts +="<a href='javascript:editProduct("+index+")'>修改</a> | ";
			opts +="<a href='javascript:commitProduct("+row.id+")'>删除</a>";
			return opts;
		}

		function queryProduct(){
			var param = {};
 			var fileName=$('#fileTypeName').val();
			var status=$('#fileStatus').val();
			var basicId = $('#basic-search').val();
			if (basicId != -1){
				param.basicId=basicId;
			}
			if(fileName != ""){
			 param.fileName=fileName;
			}
			if(status != ""){
			 param.status=status;
			}
			$("#datagrid").datagrid("load", param);
		}

		function clear(){
			$(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
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
                    	<th>资料分类</th>
                        <td>
                            <select id="basic-search" class='select'>
                                    <option value="-1">请选择</option>
                            </select>
                        </td>
                    	<th>资料名称</th>
                        <td>
                            <input type="text" id="fileTypeName"name="fileTypeName" class="input">
                        </td>
                        <th>状态</th>
                        <td>
                            <select id='fileStatus'name='fileStatus' class='select'>
                                <option value="">请选择</option>
                                <option value='1'>正常</option>
                                <option value='0'>冻结</option>
                            </select>
                        </td>
                        <td>&nbsp;</td>
                        <td class="bar"><input type="button" class="btn"
                                               onclick="queryProduct()" value="搜索"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<input
                                type="reset" class="btn" value="清空" onclick="clear()" />
                        </td>
                    </tr>
                </table>
            </form>
        </div>
    </div>
    <div data-options="region:'center',border:false" class="data-area">
        <div id="datagrid"></div>
    </div>
</div>
<div id="productEdit"
     data-options="title:'添加产品',resizable:false,closed:false,minimizable:false,maximizable:false,modal:true">
    <div class="dg-content">
        <form id="productForm" method="post" enctype="multipart/form-data">
            <table class="myTable" cellpadding="0" cellspacing="0" border="0" style="padding: 0;">
                <tbody class="myTbody">
                	<tr>
                		<th style='width: 80px;'>资料分类</th>
                		<td style='width:250px;'>
                			<select id="basicId" name="basicId" class="select" >
                				<option value=""></option>
                			</select>
                		</td>
                	</tr>
                	<tr>
                		<th style='width: 80px;'>资料名称</th>
                		<td style='width:250px;'>
                			<input type="text" id="fileName" name="fileName" class="input" value="">
                		</td>
                	</tr>
                	<tr>
                		<th style='width: 80px;'>状态</th>
                		<td style='width: 250px;' >
                			<select id='status' name='status' class='select'>
                				<option  value='1'>正常</option>
                				<option  value='0'>冻结</option>
                			</select>
                		</td>
                	</tr>
                	<input id='id' name='id' value='' type='hidden' />
                </tbody>
            </table>
        </form>
    </div>
</div>
</body>
</body>
</html>