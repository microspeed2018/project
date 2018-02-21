<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=57kgfuf8NGcPOIcrWhK2zgxxuRlioMjz"></script>
<jsp:include page="/WEB-INF/pages/commons/imgEffect.jsp" />

<div
	data-options="title:'附件列表',resizable:true,closed:false,minimizable:false,maximizable:false,modal:true"
	id="file" style='display: none;'>
	<div class="dg-content">
		<div id="fileCommon">
			<div id="fileCommon1"></div>
		</div>
	</div>
</div>
<div id="uploadCommonDialog"
	data-options="title:'上传附件',resizable:true,closed:false,minimizable:false,maximizable:false,modal:true"
	style='display: none;'>
	<div class="dg-content">
		<form id="uploadCommonForm" method="post"
			<%-- action="${ftp_url}/fileManage/fileUpLoad.htm?redirectUrl=${ctx}/test.html" --%>
			enctype="multipart/form-data" target="hidden_frame">
			<table>
			    <tr>
					<th style="width: 30%;">资料类别</th>
					<td style="width: 50%;"><select id="basicId" name="basicId" class="select" >
					</select></td>
				</tr>
				<tr>
					<th style="width: 30%;">项目名称</th>
					<td style="width: 50%;"><select id="projectId" name="projectId" class="select">
					</select></td>
				</tr>
				<tr>
					<th>文件名</th>
					<td><select id="fileId" name="fileId" class="select">
					  <option value=''>请选择文件类型</option>
					</select></td>
				</tr>
				<tr>
					<th>上传文件</th>
					<td><input type="file" name="fileAddress" id="fileAddress" multiple onchange="fileChange(this)"/></td>
				</tr>
				<tr>
				    <td colspan='2'><span style="color:red;">*</span>手机端banner图的推荐尺寸为640x190</td>
				</tr>
			</table>
		</form>
        <input type="hidden" name="iskeychange" id="iskeychange" value="0">
		<iframe id="hidden_frame" name="hidden_frame" style="display:none;"></iframe>
	</div>
	<div id="look"
		style="display:block; margin:0 auto;text-align:center; display: flex;align-items:center;justify-content: center;">
		<object classid="clsid:D27CDB6E-AE6D-11cf-96B8-444553540000"
			codebase="http://download.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=6,0,29,0"
			height="100%" width="100%">
			<param id="video1" name="movie" value="">
			<param name="quality" value="high">
			<param name="allowFullScreen" value="true" />
			<embed id="video" src="http://service.cbylsj.com/upload/decider/20170105/mayday.mp3" quality="high"
				pluginspage="http://www.macromedia.com/go/getflashplayer"
				type="application/x-shockwave-flash" width="100%" height="100%">
			</embed>
		</object>
	</div>
</div>

<script type="text/javascript">

$(function() {
	//加载项目名称
    $.ajax({
        url : "/project/user/list.htm",
        type : 'post',
        dataType : 'json',
        data : {
        	status : 0, 
        	rows: 2000
        },
        success : function(data) {
            if (!data.success) {
                //$.messager.alert("加载项目名称下拉错误", data.resultMsg, "error");
                return;
            }
            var fileTypes = data.rows; // 文件类型
            var domFileType = '';
            domFileType += "<option selected value=''>请选择项目</option>";
            for ( var i = 0; i < fileTypes.length; i++) {
                domFileType += "<option  value='" +fileTypes[i].id+ "'>"+ fileTypes[i].name + "</option>";
            }
            $("#projectId").html(domFileType);
            $("#projectName").html(domFileType);
            
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
            var box = $("#basicId"),boxs = $("#basicIds")
                doms = "<option selected value=''>请选择资料类别</option>"
                dom = "<option selected value=''>请选择文件分类</option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
                doms += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
            boxs.empty();
            boxs.append(doms);
        }
        $("#basicId").on("change",function(){
            if($("#basicId").val()==118){
              $("#projectId").prop("disabled",false);
            }else{          
              $("#projectId").val('');
              $("#projectId").prop("disabled",true);
            }
            $.ajax({
		        url : "/fileManage/user/fileType.htm",
		        type : 'post',
		        dataType : 'json',
		        data : {
		           handlerJob:1,
		           basicId : $("#basicId").val()
		        },
		        success : function(data) {
		            if (!data.success) {
		                //$.messager.alert("加载文件类型下拉错误", data.resultMsg, "error");
		                //$("#fileEnble").val("2");
		                //return;
		            }
		            var fileTypes = data.data; // 文件类型
		            var domFileType = '';
		            domFileType += "<option selected value=''>请选择文件类型</option>";
		            for ( var i = 0; i < fileTypes.length; i++) {
		                domFileType += "<option  value='" +fileTypes[i].id+ "'>"+ fileTypes[i].name + "</option>";
		            }
		            $("#fileId").html(domFileType);
		            
		        }
		
		    });
        });
</script>