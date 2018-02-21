<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<div id="header" data-options="region:'north',split:false,border:false">
	<script type="text/javascript">
		function exitSystem() {
			window.location.href = '/j_spring_security_logout';
		}
	</script>
	<div class="logo">理财-管理端 <span class="version">V.1</span></div>
	
	<div class="bar">
		您好,<a class="user">${username}</a>&nbsp;|&nbsp;<a
			class="exit" href="javascript:exitSystem()">退出</a>
	</div>
</div>