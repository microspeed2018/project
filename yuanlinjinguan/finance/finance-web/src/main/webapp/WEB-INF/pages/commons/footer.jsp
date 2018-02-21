<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<script type="text/javascript" src="${res_js }/jtx.js?v=${ver}"></script>
<link rel="stylesheet" type="text/css" href="${res_css }/footer.css?v=${ver}"/>
<div class="footer">
    <div class="about">
        <div class="container w1002 clearfix">
            <span class="slogan fl"></span>
            <ul class="social-contact fl">
                <li class="qq">
                    <a href=""></a>
                </li>
                <li class="wechat">
                    <a href=""></a>
                </li>
                <li class="sina">
                    <a href=""></a>
                </li>
            </ul>
            <div class="foot-nav font-s12 fr">
                <ul class="clearfix">
                    <li>
                        <a href="">关于我们</a>
                    </li>
                    <li>
                        <a href="">安全保障</a>
                    </li>
                    <li>
                        <a href="">法律声明</a>
                    </li>
                    <li>
                        <a href="">帮助中心</a>
                    </li>
                    <li>
                        <a href="">招贤纳士</a>
                    </li>
                    <li class="last">
                        <a href="">联系我们</a>
                    </li>
                </ul>
                <p class="tel text-r font-s30"><span class="font-s24">欢迎拨打</span> 400-846-0571</p>

                <p class="text-r">服务时间：9:00-17:30（工作日）</p>

                <p class="text-r">客服邮箱：jintouxing@hzfi.cn</p>
            </div>
        </div>
    </div>
    <div class="copyright font-s12">
        <div class="container text-c">Hangzhou Jintouxing Financial Assets
            Service　Co.　Ltd.　杭州金投行金融资产服务有限公司　浙ICP证　B2-20150136号　浙ICP备14036062号-1
        </div>
    </div>
</div>
<script>
    $(function () {
        showNav();
        initMinHi();
    });
    function showNav() {
        var page_view = $("#page_view").val();
        if (page_view) {
            $("#nav ." + page_view).addClass("active");
        }
    }
    var bHeight = $(window).height();
    function initMinHi() {
        $(".main").css("min-height", bHeight * 0.645 + "px");
    }

</script>
