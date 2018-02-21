<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.yztz.com/jsp/jstl/sec" prefix="sec"%>
<div class="logo" style="font:caption;margin-top: 10px">
	<img src="../res/images/logo.png" style="width: 120px;">
</div>
<div class="bar">
	<a class="user" href="javascript:personal()">
		<c:out value="${sec:username() }"/>
	</a>
	<a class="exit" href="javascript:exitSystem()">
		退出
	</a>
</div>
<div class='tool' >
	<ul>
		<li>
			<i id="search"></i>
			<span>搜索</span>
		</li>
		<li>
			<label id='serves'>
				<i id="client"></i>
				<span url='1'>客服</span>
			</label>
			<dl></dl>
		</li>
		<li>
			<i id="message"></i>
			<span>信息</span>
		</li>
		<li>
			<!-- <em>1</em> -->
			<label id=''>
				<i id="deal"></i>
				<span>待办</span>
			</label>
			<!-- <dl>
				<dd>- <a url="/mobileAudit/index.htm" count="false" href="javascript:void(0)">专员电审(1)</a></dd>
			</dl> -->
		</li>
		<li>
			<label id='microsoft'>
				<i id="system"></i>
				<span url='1018'>系统</span>
			</label>
			<dl></dl>
		</li>
	</ul>
</div>
<script type="text/javascript">
	$(function(){
		if(window.top!=window.self){
			window.top.location.reload();
		}
	});

/*客服*/
	$("#header li").hover(function () {
		var child = $(this).find("span").attr("url");
		var that = $(this).find("dl");
		if (child) {loadMenu(child, that);}
		bindMenuAction();
		$(this).find("dl").show();
	}, function () {
		$(this).find("dl").hide();
	})

/*系统*/
	// var microsoftLi = $("#microsoft").closest("li");
	// microsoftLi.hover (function () {
	// 	var child = $(this).find("span").attr("url");
	// 	var that = $(this).find("dl");
	// 	loadMenu(child, that);
	// 	$(this).find("dl").show();
	// }, function () {
	// 	$(this).find("dl").hide();
	// })

	function exitSystem() {
		window.location.href = '${ctx}/j_spring_security_logout';
	}
	function personal() {
		$("#iframe").attr("src", "${ctx}/console/admin.htm?action=userPasswordView");
	}
</script>
