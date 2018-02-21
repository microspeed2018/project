<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<jsp:include page="/WEB-INF/pages/commons/context.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/gray/easyui.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_css }/common/common.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_css }/common/frame.css?v=${ver}" />
<script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/jslib/frame.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/common/common.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/common/my-map.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/jslib/admin.js?v=${ver}"></script>
<script type="text/javascript">
	(function($) {
		var buttons = $.extend([], $.fn.datebox.defaults.buttons);
		buttons.splice(1, 0, {
			text : function(target) {
				return $(target).datebox("options").cleanText;
			},
			handler : function(target) {
				$(target).datebox("setValue", "");
				$(target).datebox("hidePanel");
			}
		});
		$.extend($.fn.datebox.defaults, {
			buttons : buttons
		});

	})(jQuery);
</script>
