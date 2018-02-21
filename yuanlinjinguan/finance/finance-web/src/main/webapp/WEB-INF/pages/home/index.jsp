<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<meta charset="utf-8">
		<meta name="viewport" content="width=device-width, initial-scale=1,maximum-scale=1, user-scalable=no">
		<meta name="apple-mobile-web-app-capable" content="yes">
		<meta name="apple-mobile-web-app-status-bar-style" content="black"><meta name="format-detection" content="telephone=no">
		<title>登录</title>
		<link href="../../css/mui.min.css" rel="stylesheet" />
		<link href="../../css/base.min.css?v.6" rel="stylesheet" />
	</head>
	<body>
		<header>
			<span class='middle'>登录</span>
			<span class='right-txt' id='reg'>注册</span>
		</header>
		<div class="login fixdiv">
			<ul>
				<li>
					<span class="icon mobile"></span>
					<input id="mobile" type='tel' maxlength="11" placeholder='请输入手机号码' />
				</li>
				<li>
					<span class="icon pwd"></span>
					<input id="password" type='password' placeholder='请输入登录密码' />
				</li>
				<li id='validate-box'>
					<span class="icon validate"></span>
					<input id="checkCode" class="min-input" type='text' placeholder='请输入验证码' />
					<img src="" class='validate-img'  />
					<input type="hidden" value="${url}">
					<input type="hidden" id="redirect" name="redirect" value="${url1}">
				</li>

			</ul>
			<div class="forget">
				<a id='forget'>忘记密码></a>
			</div>
			<div class='submit-btn'>
				<button id='login' class="mui-btn mui-btn-block mui-btn-danger">登录</button>
			</div>
		</div>
		<script src="../../js/mui.min.js"></script>
		<script src="../../js/common.min.js?v.6" type="text/javascript"></script>
		<script type="text/javascript">
			(function($, doc) {
				$.init();

				// 校验app或wap
                myInit(indexFun);
                function indexFun() {


					var loginBtn = doc.querySelector("#login"),
						forgetBtn = doc.querySelector(".forget"),
						regPage = doc.querySelector("#reg"),
						flag = 0;


					/* find login password */
					forgetBtn.addEventListener("tap", function () {
						myOpenWindow('/tpl/password/find-login-pwd.html', 'find-login-pwd');
					});

					// reg page
					regPage.addEventListener("tap", function () {
						myOpenWindow('/tpl/reg/reg.html', 'reg');
					});

					/* login */
					loginBtn.addEventListener("tap", function (even) {
						var userName  = doc.querySelector("#mobile").value,
							password  = doc.querySelector("#password").value,
							checkCode = doc.querySelector("#checkCode").value;

						if (userName == '') {
							myToast("请输入手机号码");
							return;
						} else if(!mobileValidate(userName)) {
							myToast("请输入正确手机号");
							return;
						} else if(password == '') {
							myToast("请输入登录密码");
							return;
						} else if (!pwdValidate(password)) {
							myToast("请输入6-20位的密码");
							return;
						}
						if (flag != 0) {
							if (checkCode == '') {
								myToast("请输入图片校验码");
								return;
							}
						}
						flag++;
						// 同盾登录保护接口
						var param = {};
						param.accountMobile = userName;
						param.accountPassword = password;
						param.checkCode = checkCode;
						param.securityGuardType = 0;
						getTokenId(param, login);
					});

					function login(param) {
						myLoading();
						$.ajax(global.apiURL + '/yztz_user_login_check.htm', {
							data : {
								username : param.accountMobile,
								password : param.accountPassword,
								checkCode : param.checkCode
							},
							dataType : 'json',
							type : 'post',
							timeout : 10000,
							success : function(data){
								if (data.success) {
									setUserInfo(data);
								} else {
									myToast(data.resultMsg);
									doc.querySelector("#validate-box").style.display = "block";
									validateImg.click();
								}
								myLoadingOver();
							},
							error : function(xhr, type , errorThrown) {
								myLoadingOver();
								doc.querySelector("#validate-box").style.display = "block";
								validateImg.click();
								myToast("服务器正忙，请稍后重试！");
							}
						});
					}

					function setUserInfo(datas) {
						$.ajax(global.apiURL + '/user/getUserBankPwdIdno.htm', {
							data : {},
							dataType : 'json',
							type : 'post',
							success : function(data){
								if (data.success) {
									var data = data.data;
									var url= doc.querySelector("#redirect").value;
									mySetLocalStorage("user", {
										"id" 			: datas.userId,
										"name" 			: data.name,
										"cardID" 		: data.identityNo,
										"nickname"		: datas.userName,
										"mobile" 		: datas.userName,
										"bank"			: data.bankCard.bank,
										"bankcard"		: data.bankCard.bankcard,
										"springtoken" 	: datas.springtoken,
										"authority" 	: data.authorityId,
										"imageAddress"  : data.imageAddress
									});

									if(url != ""){
									    getUrl(url);
									}else{
										myOpenWindow('../../index.html', 'index');
									}
								} else {
									myToast(data.resultMsg);
								}
							},
							error : function() {
								myToast("服务器正忙，请稍后重试！");
							}
						});
					}
                    /*
                    * 调兑吧生成免登陆接口
                    */
                    function getUrl(url){
						myLoading();
						$.ajax(global.apiURL + '/duiba/getUrl.htm', {
								data : {
									redirect : url
								 },
									dataType : 'json',
									type : 'get',
									success : function(data){
									if (data.success) {
											myOpenWindow(data.url, 'integral_exchange');
									} else {
											myToast(data.resultMsg);
									}
								},
										error : function() {
										myToast("服务器正忙，请稍后重试！");
									}
								});
					}
					// refresh validate img
					var validateImg = doc.querySelector(".validate-img");
					validateImg.addEventListener("click", function () {
						var src = '/checkCode.htm?' + Math.random();
						validateImg.setAttribute("src", src);
					});
					validateImg.click();
				}
			}(mui, document));
		</script>
	</body>

</html>