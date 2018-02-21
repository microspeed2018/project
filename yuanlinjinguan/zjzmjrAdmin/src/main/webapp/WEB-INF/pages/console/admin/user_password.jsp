<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>修改用户密码</title>
        <link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/gray/easyui.css?v=${ver}" />
        <link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
        <link rel="stylesheet" type="text/css" href="${res_css }/common/common.css?v=${ver}" />
        <script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript" src="${res_js }/common/common.js?v=${ver}"></script>
        <script type="text/javascript">
            $(function() {
                $("#content").panel({ fit: true });
            });

            function clearForm() {
                $("#oldPassword").val("");
                $("#newPassword").val("");
                $("#renewPassword").val("");
            }

            function resetPwd() {
                $("#form").form("submit", {
                    onSubmit: function() {
                        if($(this).form("validate")) {
                            $.messager.progress({ interval: 200, text: '处理中...' });
                            return true;
                        }
                        return false;
                    },
                    success: function(data) {
                        $.messager.progress("close");
                        data = parseResp(data);
                        if(data.success) {
                            $.messager.alert("消息", "密码修改成功！", "info");
                            clearForm();
                        } else {
                            $.messager.alert("错误", data.resultMsg, "error");
                        }
                    }
                })
            }
        </script>
    </head>
    <body>
        <div class="easyui-layout" data-options="fit:true" id="content">
            <div data-options="region:'north',split:false,border:false">
                <div class="query">
                    <form id="form" class="inner-q" method="POST" action="${ctx}/console/admin.htm">
                        <input type="hidden" name="action" value="changePassword" />
                        <table cellpadding="0" cellspacing="0">
                            <tr>
                                <th>用户ID</th>
                                <td>
                                    <c:out value="${user.id }" />
                                </td>
                                <th>用户名</th>
                                <td>
                                    <c:out value="${user.username }" />
                                </td>
                                <th>部门</th>
                                <td>
                                    <c:forEach items="${departmentEnums }" var="dep">
                                        <c:if test="${dep.value==user.department }">
                                            <c:out value="${dep.message }" />
                                        </c:if>
                                    </c:forEach>
                                </td>
                                <th>旧密码</th>
                                <td><input type="password" id="oldPassword" name="oldPassword" class="easyui-validatebox input" data-options="required:true" /></td>
                            </tr>
                            <tr>
                                <th>新密码</th>
                                <td><input type="password" id="newPassword" name="newPassword" class="easyui-validatebox input" data-options="required:true" /></td>
                                <th>确认密码</th>
                                <td><input type="password" id="renewPassword" name="renewPassword" class="easyui-validatebox input" data-options="required:true" validType="password['#newPassword']" /></td>
                                <th>
                                </th>
                                <td></td>
                                <th>
                                </th>
                                <td></td>
                            </tr>
                            <tr>
                                <td colspan="7">
                                </td>
                                <td class='bar'>
                                    <input type="button" class="btn" onclick="resetPwd()" value="修改密码" />
                                    <input type="button" class="btn" onclick="clearForm()" value="清空" />
                                </td>
                            </tr>
                        </table>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>