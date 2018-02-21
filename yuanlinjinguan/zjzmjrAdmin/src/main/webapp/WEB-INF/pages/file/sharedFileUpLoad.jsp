<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<script type="text/javascript"
	src="http://api.map.baidu.com/api?v=2.0&ak=57kgfuf8NGcPOIcrWhK2zgxxuRlioMjz"></script>
<jsp:include page="/WEB-INF/pages/commons/imgEffect.jsp" />
<style>
.maxTextarea {
	display: block;
	height: 80px;
	width: 298px;
	font-family: "微软雅黑";
	color: #333333;
	border: 1px solid #D3D3D3;
	padding: 0;
	margin: 3px 0 3px 0;
	resize:none;
}
</style>
<script type="text/javascript">

    
    
</script>
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
					<th style="width: 30%;">资料名称</th>
					<td style="width: 50%;">
						<input id="fileName" name="fileName" class="input"/>
					</td>
				</tr>
				<tr>
					<th>文件说明</th>
					<td>
						<textarea id="fileExplain" name="fileExplain" class="maxTextarea"></textarea>
					</td>
				</tr>
				<tr>
					<th>上传文件</th>
					<td><input type="file" name="fileAddress" id="fileAddress" multiple onchange="fileChange(this)"/></td>
				</tr>
				<tr>
					<th>提醒</th>
					<td>
						<input id="isMessage" name="isMessage" type="checkbox" value="1" />站内消息 
						<input id="isSms" name="isSms" type="checkbox" value="1" />短信 
					</td>
				</tr>
				<tr>
					<th>置顶</th>
					<td>
						<input id="isTop" name="isTop" type="checkbox" value="1" />置顶 
					</td>
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