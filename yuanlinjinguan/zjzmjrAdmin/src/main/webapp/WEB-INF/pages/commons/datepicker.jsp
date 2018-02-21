<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<link rel="stylesheet" type="text/css" href="${res_js }/jquery/datepicker/themes/base/jquery.ui.all.css?v=${ver}" />
<link rel="stylesheet" type="text/css" href="${res_js }/jquery/datepicker/demos.css?v=${ver}" />
<script type="text/javascript" src="${res_js }/jquery/datepicker/jquery.ui.core.js"></script>
<script type="text/javascript" src="${res_js }/jquery/datepicker/jquery.ui.widget.js"></script>
<script type="text/javascript" src="${res_js }/jquery/datepicker/jquery.ui.datepicker.js"></script>
<script type="text/javascript" src="${res_js }/jquery/datepicker/i18n/jquery.ui.datepicker-zh-CN.js"></script>
<script type="text/javascript">

function showDatePicker(picker){
	$(function() {
		$.datepicker.setDefaults($.datepicker.regional['']);
		$("#"+picker).datepicker($.datepicker.regional['zh-CN']);
		//$("#"+picker).datepicker();
	});
}

</script>