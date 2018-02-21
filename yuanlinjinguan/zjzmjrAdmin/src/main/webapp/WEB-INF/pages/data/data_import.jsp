
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>数据导入</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="excelform" name="excelform" class="inner-q" method="post" enctype="multipart/form-data">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>导入类型</th>
                            <td>
                                <select id="handlerType" name="handlerType" >
                                    <option value="-1">请选择导入类型</option>
                                    <option value="1">项目合同</option>
                                    <option value="2">关联公司</option>
                                </select>
                            <th>Excel文件选择</th>
                            <td>
                                <input type="file" id="excelAddress" name="excelAddress" value="">
                            </td>
                            <td colspan="5"></td>
                            <td class="bar">
                                <input type="button" class="btn" value="Excel导入" onclick="importProduct()" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
    </div>
	<div id ="importExcel" name="importExcel" data-options="region:'center',border:false" class="data-area">
	       <form id="importForm" name="importForm" method="POST" enctype="multipart/form-data" style="padding:60px;">
	         <input type="file" id="uploadAddress"  name="uploadAddress" style ="width:200px;">
	    </form>
	</div>
    <script type="text/javascript">
	    function importProduct(){
	        var excelAddress = $("#excelAddress").val();
	        if(excelAddress == ""){
	            $.messager.alert("错误", "请选择要上传的excel文件", "error");
	            return false;
	        }
	        var handlerType = $("#handlerType").val();
	        if(handlerType == -1){
                $.messager.alert("错误", "请选择导入的类型", "error");
                return false;
	        }
	        $.messager.confirm("确认", "是否确认提交？", function (r) {
	              if (r) {
	            	  /* $.messager.progress({interval: 200, text: '处理中...'});
	                  $("#excelform").attr("action", "/outside/user/import.htm");
	                  $("#excelform").submit(); */
	                  $("#excelform").attr("action", "/outside/user/import.htm");
	                  $("#excelform").form("submit", {
	                      onSubmit : function() {
	                          // $("#excelAddress").outerHTML = $("#uploadAddress").outerHTML;
	                          $.messager.progress({interval: 200, text: '处理中...'});
	                          setTimeout(function (){$.messager.progress("close");$("#excelAddress").outerHTML="";}, 10000);
	                          return true;
	                    },
	                    success: function (data) {
	                        $.messager.progress("close");
	                        data = parseResp(data);
	                        if (data.success) {
	                            $.messager.alert("信息", "操作成功", "info");
	                            //$("#importExcel").dialog("close");
	                        } else {
	                            $.messager.alert("错误", data.resultMsg, "error");
	                        }
	                    }
	                });
	              }
	          });
	    }
	    
       $(function(){
    	   $.messager.progress("close");
          /* if('${success}' == 'true'){
              $.messager.alert("信息", "操作成功", "info");
          }else if('${success}' == 'false'){
              $.messager.alert("错误", '${resultMsg}', "info");
          } */
          var data = '${data}';
          if(data){
              console.log(data);
              data = parseResp(data);
              if (data.success) {
                  $.messager.alert("信息", "操作成功", "info");
                  //$("#importExcel").dialog("close");
              } else {
                  $.messager.alert("错误", data.resultMsg, "error");
              }
          }
      });
    </script>
</body>
</html>