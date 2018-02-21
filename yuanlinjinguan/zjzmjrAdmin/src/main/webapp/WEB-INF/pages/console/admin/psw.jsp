<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GB2312">
<title>密码修改</title>
<script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
<script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
<script type="text/javascript" src="${res_js }/jslib/psw.js?v=${ver}"></script>
<script type="text/javascript" src="${res_js }/common/common.js?v=${ver}"></script>
<script language="text/javascript">
    </script>
</head>
<body>
	<tr>
		<td style="height: 500px; width: 600px; "><form
				name="pwd_change_form" style="height: 270px; width: 440px; ">
				<table border="0" align="center">
					<tr>
						<td>请输入原密码：<input type="password" name="oldPwd" /></td>
					</tr>
					<tr>
						<td>请输入新密码：<input type="password" name="newPwd" /></td>
					</tr>
					<tr>
						<td style="height: 35px; width: 268px; ">请确认新密码：<input
							type="password" name="newPwdConfirm" /></td>
					</tr>
					<tr>
						<td align="center"><input type="button" value="确认修改"
							name="submit" onClick="return checkForm(pwd_change_form)" /><input
							type="reset" value="重置" name="reset" /></td>
					</tr>
				</table>
			</form></td>
	</tr>

</body>
</html>