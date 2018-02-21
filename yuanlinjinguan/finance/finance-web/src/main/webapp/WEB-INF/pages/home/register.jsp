<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="360-site-verification" content="16fef83fb2c88e83913adfb5e8d308c0" />
<title>金投行</title>
<jsp:include page="/WEB-INF/pages/commons/context.jsp"></jsp:include>
<link rel="stylesheet" type="text/css" href="${res_css }/base.min.css?v=${ver}"/>
<link rel="stylesheet" type="text/css" href="${res_css }/global.css?v=${ver}"/>
<link rel="stylesheet" type="text/css" href="${res_css }/user.css?v=${ver}"/>
<script src="${res_js }/jquery/jquery-1.8.0.min.js?v=${ver}" type="text/javascript"></script>
<script src="${res_js }/common.min.js?v=${ver}" type="text/javascript"></script>

<script type="text/javascript">
  var checkName = true;
  var checkmobile = true;

  var isNull = /^[\s]{0,}$/;
  var loginName = /^[a-z]([\w]{5,17})$/i;
  var codeVal=/^[A-Za-z0-9]{0,20}$/;
  var mobilePat = /^1\d{10}$/;

  function onSubmit(){
    var iAgree = $("input:checkbox[name='iagree']").attr("checked");
    /* if (checkName && checkmobile && iAgree == "checked" && nameCheck() && passwordCheck()
      && rePasswordCheck()&& verifyCodeCheck()&&codeCheck()) {
      return true;
    } */
    var userName = $('#userName').val();
    var passWord = $('#passWord').val();
    var phoneCode = $('#phoneCode').val();
    var checkCode = $('#code').val();
    var inviteCode = $('#inviteCode').val();
    var p = $('#checkMsg');

    if(userName == null || userName == "输入用户名" || !loginName.test(userName)){
      p.find("span").text("用户名不能为空");
      p.css('display', 'block');
      return false;
    }
    if(passWord == "" || passWord == null || passWord == "输入密码"){
      p.find("span").text("密码不能为空");
      p.css('display', 'block');
      return false;
    }
    if(phoneCode == null || phoneCode == "输入手机号码" || !mobilePat.test(phoneCode)){
      p.find("span").text("手机号码不能为空");
      p.css('display', 'block');
      return false;
    }
    if(checkCode == null || checkCode == "输入验证码"){
      p.find("span").text("验证码不能为空");
      p.css('display', 'block');
      return false;
    }
    if(inviteCode == "输入邀请码"){
    	inviteCode = "";
    }
    if(passCheck() == false){
      return false;
    }
    if(iAgree != "checked"){
    	p.find("span").text("请同意注册协议");
      p.css('display', 'block');
    	return false;
    }
    if ( checkName) {
        $.ajax({
          url : "/register/userRegister.htm",
          type : 'post',
          dataType : 'json',
          data : {
            userName : userName,
            passWord : passWord,
            phoneCode : phoneCode,
            inviteCode : inviteCode,
            code : checkCode
          },
          success : function(data) {
            data = parseResp(data);
            if (data.success) {
              //X.dialog.alert("Warning", "注册成功");
              //window.location.href = "/user/index.htm";
              window.location.href = "/register/registerIndex2.htm";
              return true;
            } else {
              X.dialog.alert("Warning", data.resultMsg);
              return false;
          }
        }
      });
    }
    return false;
  }

  function checkoxBtn() {
    var iAgree = $("input:checkbox[name='iagree']").attr("checked");
    if (iAgree != "checked") {
      $("#sub-btn").attr('disabled', true);
    } else {
      $("#sub-btn").attr('disabled', false);
    }

  }

  function passCheck(){
    var pass = $('#passWord').val();
    var passSure = $('#passSure');
    var passSureVal = passSure.val();
    var p = passSure.parent().find("span");
    if(pass != passSureVal){
      p.addClass("red");
      p.text("两次输入的密码不一样！");
      return false;
    }
    return true;
  }

  function nameCheck() {
    var ipt = $("input[name='userName']");
    var val = ipt.val();
    var p = ipt.parent().find("span");
    var loginSuccess = $("#loginSuccess");
    loginSuccess.removeClass("success_tip");
    p.removeClass();
    p.addClass("gray9");
    p.text("6-18个字符，可使用字母、数字、下划线，需以字母开头");
    if (isNull.test(val)) {
      p.addClass("red");
      p.removeClass("gray9");
      p.text("用户名不能为空");
      return false;
    } else if (!loginName.test(val)) {
      p.addClass("red");
      p.removeClass("gray9");
      return false;
    }
    $.post("checkUserName.htm", {
      username : val
    }, function(data) {
      data = parseResp(data);
      if (!data.success) {
        loginSuccess.removeClass("success_tip");
        p.addClass("red");
        p.removeClass("gray9");
        p.text(data.resultMsg);
        checkName = false;
        return false;
      }
    });
    loginSuccess.addClass("success_tip");
    checkName = true;
    return true;
  }

  function phoneCheck() {
    var ipt = $("input[name='phoneCode']");
    var val = ipt.val();
    var p = ipt.parent().find("span");
    //var loginSuccess = $("#loginSuccess");
    //loginSuccess.removeClass("success_tip");
    p.removeClass();
    p.addClass("gray9");
    p.text(" ");
    if (isNull.test(val)) {
      p.addClass("red");
      p.removeClass("gray9");
      p.text("手机号码不能为空");
      return false;
    } else if (!mobilePat.test(val)) {
      p.addClass("red");
      p.removeClass("gray9");
      p.text("手机号码格式不正确");
      return false;
    }
    $.post("checkUserName.htm", {
    	action : "mobileCheck",
      username : val
    }, function(data) {
      data = parseResp(data);
      if (!data.success) {
        //loginSuccess.removeClass("success_tip");
        p.addClass("red");
        p.removeClass("gray9");
        p.text("该手机号码已被注册");
        checkName = false;
        return false;
      }
    });
    //loginSuccess.addClass("success_tip");
    checkName = true;
    return true;
  }

  function anotherImg(contextPath) {
    $("#verify-img").attr("src", contextPath);
  }
</script>
</head>
<body>
<!--------------------- header--------------------->
<jsp:include page="/WEB-INF/pages/commons/header.jsp" />
<!--------------------- header end--------------------->
  <div class="main main-login">
  <form method="post" onsubmit="return onSubmit()">
    <div class="w1002 container box-border white-bg">
      <h3 class="font-s20 text-minor">免费注册</h3>
      <div class="login-wrap">
        <ul>
          <li id="checkMsg" class="result-tip" style="display: none;">
            <label class="msg"><i class="tip-btn">X</i><span>注册信息输入不完整！</span></label>
          </li>
          <li>
            <label><i class="icon-star">*</i>用户名</label>
            <input id="userName" class="input input-has-icon" type="text" name="userName" maxlength="20" value="输入用户名" onblur="nameCheck()" />
            <i id="loginSuccess" class="icon icon-user"></i>
            <span class="gray9" id="loginNameError">6-18个字符，可使用字母、数字、下划线，需以字母开头</span>
          </li>
          <li>
            <label><i class="icon-star">*</i>密码</label>
            <input id="passWord" class="input input-has-icon" type="password" name="passWord" value="" />
            <i class="icon icon-lock"></i>
            <p class="placeholder">输入密码</p>
            <span id="loginNameError">6~20个字符，区分大小写 </span>
          </li>
          <li>
            <label><i class="icon-star">*</i>重复密码</label>
            <input id="passSure" class="input input-has-icon" type="password" name="passSure" value="" onblur="passCheck();" />
            <i class="icon icon-lock"></i>
            <p class="placeholder">再次输入密码</p>
            <span>6~20个字符，区分大小写 </span>
          </li>
          <li>
            <label><i class="icon-star">*</i>手机号码</label>
            <input id="phoneCode" class="input" type="text" name="phoneCode" maxlength="11" value="输入手机号码" onblur="phoneCheck();"/>
            <span>&nbsp;</span>
          </li>
          <li>
            <label>邀请码</label>
            <input id="inviteCode" class="input" type="text" name="inviteCode" value="输入邀请码" maxlength="6" />
            <span>有则填写 <a href="${ctx }/help/mcjs.htm" class="font-link">什么是邀请码？</a></span>
          </li>
          <li>
            <label><i class="icon-star">*</i>验证码</label>
            <input id="code" class="input w130" type="text" name="code" value="输入验证码" />
            <a href="javascript:void(0)" class="img-code">
              <img  id="verify-img"  src="/checkCode.htm" name="" alt="验证码" />
            </a>
            <a href="javascript:void(0)" onclick="anotherImg('/checkCode.htm?'+Math.random())">
            <span class="update">看不清楚?换一张</span></a>
          </li>
        </ul>
        <div class="agreement pd-l150">
          <input name="iagree" type="checkbox" id="iagree" class="m_cb" checked="checked" onchange="checkoxBtn();"/>
          <label for="iagree">我同意</label> <a target="_blank" href="/term/ZCXY.html" class="font-link">《金投行注册协议》
          </a>
        </div>
        <div class="pd-l150">
          <input type="submit" id="sub-btn" class="btn font-s18" value="立即注册" />
        </div>
      </div>
    </div>
    </form>
  </div>
<!---------------footer------------------->
<jsp:include page="/WEB-INF/pages/commons/footer.jsp" />
<!---------------footer结束------------------->
<script type="text/javascript">

	$(function() {
		if ('${user.username}' != '') {
			$('#userName').val('${user.username}');
		}
		//$('#passWord').val('${user.password}');
		if ('${user.loginMobile}' != '') {
			$('#phoneCode').val('${user.loginMobile}');
		}
		if ('${user.inviteCode}' != '') {
			$('#inviteCode').val('${user.inviteCode}');
		}
	});
</script>
</body>
</html>