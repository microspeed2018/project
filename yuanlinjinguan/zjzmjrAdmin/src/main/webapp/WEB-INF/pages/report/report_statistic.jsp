<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<jsp:include page="/WEB-INF/pages/commons/resource.jsp" />
<script type="text/javascript" src="${res_js }/jslib/businessReport.js?v=${ver}"></script>
<title>业务报表</title>
</head>
<style>
    .myTable {
        width: 100%;
        border-collapse: collapse;
        border-spacing: 0;
        border: 1px solid #ccc;
    }

    .myTable tr {
        background-color: #fff;
        height: 30px;
    }

    .myTable .midInput {
        display: block;
        height: 21px;
        width: 195px;
        line-height: 21px;
        padding: 0;
        border: 1px solid #D3D3D3;
    }
    .myTbody label {
        width: 15%;
        text-align: left;
        float: left;
        display: inline-block;
    }

    .myTable .maxTextarea {
        display: block;
        height: 80px;
        width: 298px;
        font-family: "微软雅黑";
        color: #333333;
        border: 1px solid #D3D3D3;
        padding: 0;
        margin: 3px 0 3px 0;
    }

    .myTable .maxInput {
        display: block;
        height: 21px;
        width: 590px;
        line-height: 21px;
        padding: 0;
        border: 1px solid #D3D3D3;
    }

    .myTable tr:HOVER {
        background-color: #f9f9f9;
    }

    .myTable .myinput {
        display: inline;
        width: 60px;
        line-height: 21px;
        height: 21px;
        padding: 0;
        font-family: "微软雅黑";
        color: #333333;
        border: 1px solid #D3D3D3;
        color: #333333;
    }

    .myTable th {
        text-align: center;
        background-color: #f1f1f1;
        border: 1px solid #ccc;
        font-weight: normal;
        width: 30%;
        padding-right: 10px;
    }

    .myTable td {
        border: 1px solid #ccc;
        text-align: left;
        width: 70%;
        min-width: 90px;
    }
    .data-area {
        padding-top: 40px;
        position: relative;
    }
    .chooseBtn {
        width: 97%;
        text-align: right;
        z-index: 20;
        position: absolute;
        top: 0px;
        margin-right: 10px;
    }
    .layout-panel {
        position: static;
    }
    .myTbody tr td {
        width: 1.67%;
    }
    .query {
        padding: 0;
    }
    .query .inner-q {
        border: none;
        background: #fff;
    }
    .dg-content {
        padding: 0;
    }
    .cut {
        width: 96.6% !important;
        padding: 2px;
        margin: 20px;
        margin-bottom: 3px;
        overflow: auto;
        display: block;
        border: 1px solid #DDDDDD;
        background-color: #FAFAFA;
    }
    #columnForm {
        padding-left: 20px;
    }
    .btnchild {
        height: 20px;
        width: 20px;
        display: block;
        float: right;
        margin-top: 5px;
        margin-left: 30px;
    }
    .search {
        background: url(../../../res/images/baobiao/search.svg) 0 0 no-repeat;
    }
    .search:hover {
        background: url(../../../res/images/baobiao/search_1.svg) 0 0 no-repeat;
    }
    .choose {
        background: url(../../../res/images/baobiao/select.svg) 0 0 no-repeat;
    }
    .choose:hover {
        background: url(../../../res/images/baobiao/select_1.svg) 0 0 no-repeat;
    }
    .close {
        background: url(../../../res/images/baobiao/close.svg) 0 0 no-repeat;
    }
    .close:hover {
        background: url(../../../res/images/baobiao/close_1.svg) 0 0 no-repeat;
    }
    .open{
         background: url(../../../res/images/baobiao/open.svg) 0 0 no-repeat;
    }
    .open:hover {
         background: url(../../../res/images/baobiao/open_1.svg) 0 0 no-repeat;
    }
</style>

<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" id="columnDialog" class="cut blockSou" style="height:auto;">
            <div class="query">
                <!-- 搜索 -->
                <form id="form" method="post" class="inner-q">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>技术负责人</th>
                            <td id="projectLeaderTd">
                                <input id="projectLeader" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td>
                            <th>经营专员</th>
                            <td>
                                <select id='managePerson' name='managePerson' class='select'>
                                 <option value=""></option></select>
                            </td>
                            <th>项目阶段</th>
                            <td>
                                <select class="select" id="step">
                                    <option value="-1"></option>
                                    <c:forEach var="type" items="${e:gardenProjectStepEnum()}">
                                        <option value="${type.value}" >${type.message}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="createTimeStart" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="createTimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>项目编号</th>
                            <td>
                               <input type="text" id="projectNo" name="projectNo" class="input">
                            </td>
                            <th>所在城市</th>
                            <td>
                               <input type="text" id="cityId" name="cityId" class="input">
                            </td>
                            <th>项目名称</th>
                            <td>
                               <input type="text" id="name" name="name" class="input">
                            </td>
                            <th>投资额(万元)</th>
                            <td>
                                <input type="text" class="input half-input" id="investmentMountStart" name="investmentMountStart" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="investmentMountEnd" name="investmentMountEnd" />
                            </td>
                        </tr>
                        <tr>
                           <th>
                                                                                           经营部门
                            </th>
                            <td>
                                <select class="select" id="department">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>
                                                                                          项目性质
                            </th>
                            <td>
                                <select class="select" id="attribute">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>项目类别</th>
                            <td>
                                <select class="select" id="category" name="category">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>设计面积(㎡)</th>
                            <td>
                                <input type="text" class="input half-input" id="designAreaStart" name="designAreaStart" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="designAreaEnd" name="designAreaEnd" />
                            </td>

                        </tr>
                        <tr>
                            <th>发包单位</th>
                            <td id="contractAwardCompanyTd">
                                <input name="contractAwardCompany" id="contractAwardCompany" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td><th>合同编号</th>
    						<td>
                                <input type="text" id="contractNo" name="contractNo" class="input">
                            </td>
                            <th>项目负责人</th>
                            <td>
                                <input id="projectLiable" class="input" />
                            </td>
                            <th>合同金额(万元)</th>
                            <td>
                                <input type="text" class="input half-input" id="contractCapitalFrom" name="contractCapitalFrom" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="contractCapitalTo" name="contractCapitalTo" />
                            </td>

                        </tr>

                        <tr>
                            <th>院方比例(%)</th>
                            <td>
                                <input type="text" class="input half-input" id="ratioFrom" name="ratioFrom" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="ratioTo" name="ratioTo" />
                            </td>
                            <th>
                              <input type="checkbox" name="haveTechnical" id="haveTechnical-s"/>
                            </th>
                            <td>只看含技术标</td>

                            <td colspan="3"></td>
                            <td class="bar">
                                <input type="button" class="btn" onclick="queryProduct()" value="搜索" />
                                <input type="button" class="btn" onclick="clearForm()" value="清空" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <div class="dg-content" style="display: none">
            <!-- 列选择 -->
                <form id="columnForm" method="post">
                    <table class="viTable" cellpadding="0" cellspacing="0">
                        <tbody class=myTbody>
                        </tbody>
                    </table>
                </form>
            </div>
        </div>
        <div data-options="region:'center',border:false" class="data-area">
            <div class="chooseBtn">
                <i class="btnchild opreate close"></i>
                <i class="btnchild choose"></i>
                <i class="btnchild search"></i>
            </div>
            <div id="datagrid"></div>
        </div>
    </div>
    <script type="text/javascript">
        $(function() {
            //getBasic();
            var mydate = new Date();
            var str = mydate.getFullYear() + "/";
            if(mydate.getMonth()+1<10){
                str += "0"+(mydate.getMonth()+1) + "/";
            } else {
                str += (mydate.getMonth()+1) + "/";
            }
            str += "01";
            $("#createDateStart").datebox("setValue",str);
            $.ajax({
                url : "/report/user/reportCondition.htm",
                type : 'post',
                dataType : 'json',
                data : {},
                success : function(data) {
                	if(data.success){
                        addColumnView($("#columnForm .myTbody"), data.fields);
                        showColumn(data.data, data.fields);
                	}else{
                        $.messager.alert("警告",data.resultMsg,"error");
                	}
                },
                error : function(){
                	console.log("服务器异常");
                }
            });
        });
    </script>
    <script>
        // 获取负责人
        (function getStaffPerson() {
            $("#projectLeader").combobox({
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',
	           valueField:'value',
	           textField:'text',
	           filter: function(q, row){
               var opts = $(this).combobox('options');
                //return row[opts.textField].indexOf(q) == 0;
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配
               }
	        });
        })();
        //获取发包单位
        // 获取发包单位
        function contractAwardCompany() {
             $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {},
                success: function (data) {
                    var datas = data.rows;
                    for (var i = 0; i < datas.length; i++) {
                       if (datas[i].categoryId == 3 && datas[i].attributeId == 1) {
	                   $("#contractAwardCompany").combobox({
	                     url:'/companyAssocited/user/getCompanyAssociatedInfoData.htm?companyDistinguish='+datas[i].id+'&rows=1000000',
	                     valueField:'value',
	                     textField:'text',
	                     filter: function(q, row){
                         var opts = $(this).combobox('options');
                         //return row[opts.textField].indexOf(q) == 0;
                         return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配
                         }
	                   });
                       }
                    }
                }
            });
        }
        contractAwardCompany();
        // 经营专员
        (function getStaffPerson() {
            $.ajax({
                url: "/staff/user/getStaffPerson.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    jobId : 4,
                    notJobStatus : 111,
                    rows:1000000
                },
                success: function (data) { 
                    if (data.success) {
                        createStaffPerson2(data.rows);
                    }
                }
            });
        })();
        function createStaffPerson2(datas) {
            var box = $("#managePerson"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
            	if('${speialManageId}' == datas[i].userId){
                    dom += "<option value='"+ datas[i].userId +"' selected>"+ datas[i].userInfo.name +"</option>";
                    $("#managePerson").attr("onfocus", "this.defaultIndex=this.selectedIndex;");
                    $("#managePerson").attr("onchange", "this.selectedIndex=this.defaultIndex;");
            	}else{
                    dom += "<option value='"+ datas[i].userId +"'>"+ datas[i].userInfo.name +"</option>";
            	}
            }
            box.append(dom);
        }

        // 获取基础数据
        function getBasic(id, cb) {
            $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    categoryId : id,
                    rows:1000
                },
                success: function (data) {
                    if (data.success) {
                        cb(data.rows);
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
            });
        }
        // 获取项目性质
        getBasic(4, createAttribute);
        function createAttribute(datas) {
            var box = $("#attribute"),
                boxS = $("#attribute-s"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
            box.append(dom);
            boxS.append(dom);
        }

        // 获取经营部门
        getBasic(7, createDepartment);
        function createDepartment(datas) {
            var box = $("#department"),
                boxS = $("#department-s"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
            box.append(dom);
            boxS.append(dom);
        }

        // 获取项目类别
        getBasic(5, createCategory);
        function createCategory(datas) {
            var boxS = $("#category-s"),
                box = $("#category"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
            box.append(dom);
            boxS.append(dom);
        }

        var flag;
        var Height = $(window).height();   //浏览器窗口高度

        //搜索
        $(".search").click(function () {
            $(".cut").slideDown();
            $(".query").slideDown();
            $(".dg-content").hide();
            $(".opreate").val("收起");
            flag = "1";
            setTimeout(function() {
                tempFun();
            }, 500);
        })

        //列选择
        $(".choose").click(function () {
            $(".cut").slideDown();
            $(".query").hide();
            $(".dg-content").slideDown();
            $(".opreate").val("收起");
            //firstOpen();
            flag = "2";
            setTimeout(function() {
                tempFun();
            }, 500);
        })

        //收起/展开
        $(".opreate").click(function () {
            //收起
            if ($(".opreate").hasClass("close")) {
                $(".cut").slideUp();
                $(".opreate").addClass("open").removeClass("close");
            } else if ($(".opreate").hasClass("open")) { //展开
                $(".opreate").addClass("close").removeClass("open");
                if (flag == "1") {   //搜索
                    $(".cut").slideDown();
                    $(".query").slideDown();
                    $(".dg-content").hide();
                } else if (flag == "2") {  //列选择
                    $(".cut").slideDown();
                    $(".query").hide();
                    $(".dg-content").slideDown();
                } else {
                    $(".cut").slideDown();
                    $(".query").slideDown();
                    $(".dg-content").hide();
                }
            }
            setTimeout(function() {
                tempFun();
            }, 500);
        })

        //变化表格的高度
        function tempFun() {
            if($("#columnDialog").css("display") == "block") {
                $(".data-area").css({"height": (Height - 290) + "px"});
                $(".datagrid-wrap").css("height", (Height - 310) + "px");
                $(".datagrid-view").css("height", (Height - 370) + "px");
                $(".datagrid-body").css("height", (Height - 420 ) + "px");
            } else {
                $(".data-area").css({"height": (Height - 80) + "px"});
                $(".datagrid-wrap").css("height", (Height - 100) + "px");
                $(".datagrid-view").css("height", (Height - 160) + "px");
                $(".datagrid-body").css("height", (Height - 210) + "px");
            }
        }

        $(function () {
            function baseFun() {
                $(".data-area").css({
                    "height": (Height - 290) + "px",
                    "margin-top" : "0"
                });
                $(".datagrid-wrap").css("height", (Height - 310) + "px");
                $(".datagrid-view").css("height", (Height - 370) + "px");
                $(".datagrid-body").css("height", (Height - 420 ) + "px");
            }
            baseFun()
        });

        // 筛选事件 给所有的input绑定click事件
        $(".dg-content").on("click", "input", function () {
            chooseOne();
        });
    </script>
</body>
</html>