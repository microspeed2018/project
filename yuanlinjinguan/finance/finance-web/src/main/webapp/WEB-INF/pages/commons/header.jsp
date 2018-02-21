 <%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link rel="stylesheet" type="text/css" href="${res_css }/header.css?v=${ver}" />
<div class="top-bar font-minor font-s12">
  <div class="container w1002 clearfix">
    <ul class="fl contact">
      <li class="font-s14"><i class="i-tel"></i>400-846-0571</li>
      <li>
        <a href=""><i class="i-service"></i>在线客服</a>
      </li>
      <li class="has-qr-code">
        <a href=""><i class="i-wechat"></i>微信公众号<i class="i-arr-down"> </i></a>
        <div class="qr-code">
          <img src="${res_img }/weixin.png" height="78" width="78" alt="">
        </div>
      </li>
    </ul> 
    <ul class="fr top-nav">
      <li class="has-qr-code">
        <a href=""><i class="i-phone"></i> 掌上金投行<i class="i-arr-down"></i></a>
        <div class="qr-code qr-code-mobile">
          <img src="${res_img }/qr_code.png" height="78" width="78" alt="">
        </div>
      </li>
      <li>
            <a href="/home/login.htm">登录</a> / <a href="/register/registerIndex.htm">注册</a>
          </li>
      <li class="no-border">
        <a href="/help/cjwt.htm">帮助中心</a>
      </li>
    </ul>
  </div>
</div>
<div class="header">
  <div class="container w1002">
    <h1 class="logo fl"><a href="${ctx }/index.htm">金投行</a></h1>
    <span class="slogan fl"> </span>
    <div id="nav" class="nav fr">
      <ul>
        <li class="index">
          <a href="/index.htm">首页</a>
        </li>
        <li class="loans">
          <a href="/loan/index.htm">金投e贷</a>
        </li>
        <li class="money-trade">
          <a href="/product/financeListHome.htm?type=no_limit">理财频道</a>
        </li>
        <li class="novice-guide">
          <a href="/help/index.htm">新手指引</a>
        </li>
        <li class="user-center">
          <a href="/user/index.htm">我的账户</a>
        </li>
      </ul>
    </div>
  </div>
</div>
