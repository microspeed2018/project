<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/default/easyui.css?v=${ver}" />
        <link rel="stylesheet" type="text/css" href="${res_js }/easyui/themes/icon.css?v=${ver}" />
        <script type="text/javascript" src="${res_js }/jquery/jquery-1.8.0.min.js"></script>
        <script type="text/javascript" src="${res_js }/easyui/jquery.easyui.min.js"></script>
        <script type="text/javascript" src="${res_js }/easyui/locale/easyui-lang-zh_CN.js"></script>
        <script type="text/javascript">
            var success = ${success};
            var errMsg = '${resultMsg}';

            $(function() {
                var frame = $("#frame");
                frame.panel({ width: 400, height: 300, top: ($(document).height() - 300) / 2, left: ($(document).width() - 400) / 2 });
                frame.panel('panel').css('position', 'absolute');

                if(!success) {
                    $.messager.alert("Warning", errMsg);
                }

                $("#password").keyup(function(e) {
                    if(e.keyCode == 13) {
                        postForm();
                    }
                });

                $("#username").keyup(function(e) {
                    if(e.keyCode == 13) {
                        postForm();
                    }
                });

                var i = Math.ceil(Math.round(Math.random() * 10) / 2);
                $("body").css({
                    height: $(window).outerHeight(true),
                    backgroundImage: "url('../res/images/login-bg" + i + ".jpg')"
                });

            });

            function reflush() {
                $("#checkCodeImg").attr("src", "checkCode.htm?timestamp=" + new Date());
            }

            // function clearForm() {
            //     $("#username").val("");
            //     $("#password").val("");
            //     $("#checkCode").val("");
            // }

            function postForm() {
                if($("#username").val() == "") {
                    $.messager.alert("提示", "用户名不能为空!");
                    return;
                }
                if($("#password").val() == "") {
                    $.messager.alert("提示", "密码不能为空!");
                    return;
                }
                $("#frame").eq(0).submit();
            }
        </script>
        <style type="text/css">
            body {
                padding: 0;
                margin: 0 auto;
                background-size: cover;
                font-family: "微软雅黑";
            }
            ul, li {
                list-style: none;
                padding: 0;
            }
            .panel-body {
                border: 0;
                background: rgba(255, 255, 255, .8);
                border-radius: 5px;
            }
            .login ul {
                padding: 0 60px;
            }
            .login .logo {
                text-align: center;
            }
            .login li {
                padding: 10px 0;
                clear: both;
            }
            .login input {
                height: 50px;
                line-height: 50px;
                width: 95%;
                padding-left: 3%;
                background: #fff;
                border: 1px solid #383e4c;
                outline: 0;
                color: #8290a3;
                border-radius: 3px;
                font-size: 16px;
            }
            .login input:focus {
                border: 1px solid #383e4c;
            }
            .login li a {
                display: inline-block;
                color: #fff;
                background: #0092dc;
                height: 50px;
                line-height: 50px;
                width: 100%;
                text-align: center;
                text-decoration: none;
                font-size: 16px;
                border-radius: 3px;
            }
            .login li a:last-child {
                float: right;
            }
            #frame {
                display: block;
            }

            #frame table {
                margin-top: 20px;
            }

            #frame table tr {
                height: 35px;
            }

            #frame table label {
                display: block;
                width: 100%;
                line-height: 160%;
                text-align: right;
                font-size: 14px;
                font-weight: bold;
            }
        </style>

        <title>管理员登录</title>
    </head>

    <body class='login'>
        <form id="frame" method="post" action="${ctx}/yztz_admin_login_check.htm">
            <div>
                <ul>
                    <li class='logo'>
                        <img src='../res/images/login-logo.png' alt='诚邦设计' />
                    </li>
                    <li>
                        <input type="text" name="username" id="username" placeholder="请输入用户名" />
                    </li>
                    <li>
                        <input type="password" id="password" name="password" placeholder="请输入密码" />
                    </li>
                    <li>
                        <a href="javascript:void(0)" onclick="postForm()">登陆</a>
                    </li>
                    <li>
                    </li>
                </ul>
            </div>
        </form>
    </body>
</html>