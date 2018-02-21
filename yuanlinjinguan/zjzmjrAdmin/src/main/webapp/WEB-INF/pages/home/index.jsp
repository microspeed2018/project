<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>项目管理系统</title>
        <jsp:include page="/WEB-INF/pages/commons/resource.jsp"></jsp:include>
    </head>
    <body class="easyui-layout">
        <div id="header" data-options="region:'north',split:false,border:false">
            <jsp:include page="header.jsp"></jsp:include>
        </div>
        <div id="menu" data-options="region:'west',split:false,collapsible:true,border:false">
            <jsp:include page="menu.jsp"></jsp:include>
        </div>
        <div id="content" data-options="region:'center',split:false,border:false">
            <!-- <div id='tabs'>
                <ul>
                </ul>
            </div> -->
            <div id="easyUi-tabs" class="easyui-tabs" style="height: 100%; min-height: 100%;"></div>
            <iframe id="iframe" name="content" src="" width='100%' height='100%' frameborder='0'></iframe>
        </div>
        <div class='mousedown'>
			<ul>
				<li id='close-other'>关闭其他</li>
				<li id='close-all'>关闭全部</li>
                <li id='close-curr'>关闭当前</li>
			</ul>
		</div>
        <style>
        	.layout-panel-center {
        		overflow: auto;
        	}
        	#content {
        		min-width: 820px!important;
        	}
        </style>
    </body>
    <script type="text/javascript">
            var res_img = '${res_img}';
			var res_url = '${res_url}';
			console.log(res_url);
            $(function () {
                $("#iframe").attr("src", res_url);
                $("#easyUi-tabs").hide();

                $("#easyUi-tabs").on("click", "li", function () {
                    var loanApplyId = $(this).attr("loanapplyid");
                    if (loanApplyId) {
                        window.top.location.hash = loanApplyId;
                        window.top.$("#menu").find("#first").show();
                        window.top.$("#menu").find("#second").hide();
                        window.top.$(".toggle").removeClass("curr");
                    }
                });

                // 赋值高度
                var content = $(".layout-panel-center");
                function tempFun () {
	               	var htmlH = $("html, body").height();
	               	var headerH = $("#header").height();
	               	content.css({height: htmlH - headerH});
               	}
               	tempFun();

               	$(window).resize(function() {
	               	setTimeout(function () {
	               		tempFun();
	               	}, 200);

				});
            });

            function showUser(id) {
                openWindow(ctx.home + "/user/userDetail.htm", "用户详情", {action: 'userDetail', 'userId': id});
            }
            function showSubAccount(id) {
                openWindow(ctx.home + "/if/accountMgr.htm", "子账户列表", {action: 'subAccountList', 'mainAccountId': id});
            }

            function showScheme(id) {
                openWindow(ctx.home + "/loan/schemeDetail.htm", "方案详情", {'schemeId': id});
            }

            function showIfScheme(id) {
                openWindow(ctx.home + "/if/schemeDetail.htm", "股指方案详情", {'schemeId': id});
            }

            function showTransfer(id) {
                openWindow(ctx.home + "/loan/transfer.htm", "债权转让信息", {action: "detail", id: id});
            }

            function showRenewal(id) {
                openWindow(ctx.home + "/loan/renewal.htm", "借款续约信息", {action: "detail", id: id});
            }

            function showBadRecord(id) {
                openWindow(ctx.home + "/loan/badRecord.htm", '不良记录信息', {id: id, action: 'detail'});
            }

            function showSchemeFund(id) {
                openWindow(ctx.home + "/loan/fundRecord.htm", "方案资金信息", {action: "detail", id: id});
            }

            function getContentDocument() {
                return $("#iframe").get(0).contentWindow.document;
            }

            function getContentWindow() {
                return $("#iframe").get(0).contentWindow;
            }


        </script>
</html>