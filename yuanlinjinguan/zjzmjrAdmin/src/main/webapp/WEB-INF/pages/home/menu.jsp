<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.yztz.com/jsp/jstl/sec" prefix="sec"%>
<div class='nav' id='nav'>
    <ul id='first'>
        <c:forEach var="userMenu" items="${userMenus}" varStatus="navStatus">
        <c:if test="${userMenu.text ne '系统'}">
            <li>
                <span url="${userMenu.id}"><i class="icon-${userMenu.id}"></i>${userMenu.text}
                    <em class='ink'></em>
                </span>
                <dl>
                </dl>
            </li>
        </c:if>
        </c:forEach>
    </ul>
    <ul id='second'>
    </ul>
</div>
<style>
	.mousedown {
		width: 100px;
		border: 1px solid #ddd;
		border-bottom: 0;
		background: #fff;
		position: fixed;
		z-index: 2;
		top: 0;
		left: 0;
		display: none;
		cursor: pointer;
		border-radius: 3px;
	}
	.mousedown li {
		border-bottom: 1px solid #ddd;
		height: 30px;
		line-height: 30px;
		padding-left: 10px;
	}
	.mousedown li:hover {
		background: #f5f5f5;
	}
</style>
<script type="text/javascript">
    $(function(){
        if(window.top!=window.self){
            window.top.location.reload();
        }
        // 二级导航
        $("#nav span").on("click", function () {
            $(this).addClass("curr").closest("li").siblings().find("span").removeClass("curr");
            $(this).siblings("dl").slideToggle();
            $(this).closest("li").siblings().find("dl").slideUp();
            loadMenu($(this).attr("url"), $(this));
        });
        $("#nav span").eq(0).trigger("click");
    });
    function exitSystem() {
        window.location.href = '${ctx}/j_spring_security_logout';
    }
    function personal() {
        $("#iframe").attr("src", "${ctx}/console/admin.htm?action=userPasswordView");
    }
</script>

