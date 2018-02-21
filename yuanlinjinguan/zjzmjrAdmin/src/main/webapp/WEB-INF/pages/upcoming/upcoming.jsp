<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>待办</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <script type="text/javascript" src="${res_js }/jslib/upcoming.js?v=${ver}">
    </script>
    <style>
        .tab {
            border-bottom: 1px solid #d7d7d7;
            margin-bottom: 15px;
            position: fixed;
            top: 0;
            width: 100%;
            z-index: 2;
            background: #fff;
            cursor: pointer;
        }
        .tab ul {
            display: flex;
            padding-left: 20px;
            padding-top: 20px;
            justify-content: flex-start;
        }
        .tab ul li{
            width: 100px;
            font-size: 16px;
            border-bottom: 2px solid #fff;
            margin-right: 40px;
            text-align: center;
            padding-bottom: 5px;
        }
        .active {
            border-color: #0092dc !important;
            color: #0092dc;
        }
        .con {
            width: 100%;
            height: 100%;
            background: #e5edef;
            margin-top: 70px;
        }
        .con > div {
            width: 42%;
            float: left;
            margin: 0 45px 20px 20px;
            background: #f0f8fa;
            padding: 5px;
            text-align: center;
        }
        img {
            margin-top: 130px;
        }
        .tit {
            height: 30px;
            display: flex;
            justify-content: space-between;
        }
        .tit span, a {
            font-size: 14px;
            margin-left: 10px;
        }
        .tit p span{
            margin-right: 20px;
        }
        .tableCon {
            border: 1px solid #ccc;
            position: relative;
            width: 100%;
            height: 246px;
        }
        .tableCon .tableHeader, .tableCon .tableBody {
            position: absolute;
            left: 0;
            width: 100%;
        }
        .tableCon .tableHeader {
            top: 0;
            height: 20px;
        }
        .tableCon .tableHeader th {
            font-size: 14px;
            border-bottom: 1px solid #ccc;
            border-spacing: 0;

        }
        .tableCon .tableBody {
            top: 30px;
            height: 216px;
            overflow-y: auto;
        }
        table {
            width: 100%;
        }
        table td, th {
            height: 30px;
            width: 25%;
            text-align: center;
            border-bottom: 1px solid #ccc;
        }
    </style>
</head>
<body>
    <div class="tab"></div>
    <section class="con"></section>

    <script type="text/javascript">
        $(function () {

        var domUl = "<ul>" + "<li class='active'>我的待办</li>";
       /*  if (${totalUpcoming}) { 
            domUl += "<li>待办总览</li>";
        } */
        domUl += '</ul>';
        $(".tab").html(domUl);

        $(".tab li").on('click', function () {
            $(this).addClass("active").siblings().removeClass("active");
            if ($(this).html() == '我的待办') {
                $('.con').html("");
                myUpcoming(); 
            } else {
                $('.con').html("");
                getTotalUpcoming();
            }
        })

        //myUpcoming(); 
        function myUpcoming() {
            $.ajax({
                url : "/upcoming/user/getMyUpcoming.htm",
                type : 'post',
                dataType : 'json',
                data : {},
                beforeSend: addProcess(),
                success : function(data) {
                	$.messager.progress("close");
                	if (data.success) {
	                    createMyUpcomingt(data.rows, 1);
                	}
                },
                fail: function (data) {
                    Alert(data.resultMsg);
                }
            });
        }
        function getTotalUpcoming() {
            $.ajax({
                url : "/upcoming/user/getTotalUpcoming.htm",
                type : 'post',
                dataType : 'json',
                data : {},
                beforeSend: addProcess(),
                success : function(data) {
                	$.messager.progress("close");
                	if (data.success) {
                    	createMyUpcomingt(data.rows, 2);
                	}
                },
                fail: function (data) {
                    Alert(data.resultMsg);
                }
            });
        }
        function createMyUpcomingt(datas, type) {
            let domCon = '';
            if (datas) {
                for (let i = 0; i < datas.length; i++) {
                    domCon += '<div>'+ 
                        "<div class='tit'>" +
                            "<span>"+ datas[i].loanType + "</span>" +
                            "<p><span>共 "+ datas[i].total + " 条</span>";
                            if(type == 1){
                            	domCon += "<a href='"+ datas[i].address +"'>前往 >> </a></p>" ; 
                            }
                            
                     domCon += "</div>" +
                        "<div class='tableCon'>";
                    if (datas[i].upcomingList.length > 0) {
                        domCon +=  "<div class='tableHeader'>" +
                                    "<table cellspacing='0'>" +
                                        "<tr>" +
                                            "<th>借款人</th>" +
                                            "<th>业务员</th>" +
                                            "<th>业务部门</th>" +
                                            "<th>状态</th>" +
                                        "</tr>" + 
                                    "</table>" +
                                "</div>" + "<div class='tableBody'>" + "<table cellspacing='0'>";
                        for (let j = 0; j < datas[i].upcomingList.length; j++) {
                            domCon += "<tr>" +
                                    "<td>" + datas[i].upcomingList[j].userName + "</td>" +
                                    "<td>" + datas[i].upcomingList[j].agentName + "</td>" +
                                    "<td>" + datas[i].upcomingList[j].department + "</td>" +
                                    "<td>" + datas[i].upcomingList[j].statusName + "</td>" +
                                "</tr>"
                        }
                        domCon += "</table>" + "</div>";
                    } else {
                        domCon += "<img src='../../../res/images/nullData.png'>" + 
                                "<p>暂无待处理的数据</p>";
                    }
                    domCon += "</div>" + "</div>";       
                }
            }
            $(".con").append(domCon);
        }
    })
    </script>
</body>
</html> 