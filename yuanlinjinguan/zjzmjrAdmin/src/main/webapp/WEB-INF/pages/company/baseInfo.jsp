
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>公司信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <style type="text/css">
        .flex {
            display: flex;
            height: 35px;
            justify-content: flex-start;
            align-items: center;
            width: 100%;
        }
        .flex * {
            display: block;
            margin-right: 20px;
        }
        .flex .input {
            width: 200px !important;
            padding-left: 10px;
        }
        .aptitudes-list {
            position: relative;
        }
        .aptitudes-list span.btn {
            background: #fff;
            width: 50px;
            height: 30px;
            line-height: 30px;
            text-align: center;
            border-radius: 3px;
            border: 1px solid #ddd;
            cursor: pointer;
            color: #666;
        }
        .aptitudes-list span#add {
            position: absolute;
            top: 5px;
            left: 670px;
        }
    </style>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="form" class="inner-q" onsubmit="return false;" method="post">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
    						<th>企业全称</th>
    						<td>
                                <input type="text" id="companyName-search" class="input">
                            </td>
    						<th>法人代表</th>
                            <td>
                                <input type="text" id="legalPerson-seearch" class="input">
                            </td>
                            <th>单位地址</th>
                            <td>
                                <input type="text" id="cityId-search" class="input">
                            </td>
                            <th>成立日期</th>
                            <td>
                                <input type="text" id="durationFrom" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="durationTo" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>注册资金(万元)</th>
                            <td>
                                <input type="text" class="input" id="registeredCapital-search" />
                            </td>
                            <th>状态</th>
                            <td>
                                <select class="select" id="status-search">
                                    <option value="-1"></option>
                                    <option value="1">正常</option>
                                    <option value="0">冻结</option>
                                </select>
                            </td>
                            <td colspan="2"></td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
    					</tr>
                        <tr>
                            <th colspan="7"></th>
                            <td class="bar">
                                <input type="button" class="btn" value="搜索" onclick="queryProduct()" />
                                <input type="reset" class="btn" value="清空" onclick="queryProductReset()" />
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <div data-options="region:'center',border:false" class="data-area">
            <div id="datagrid"></div>
        </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>企业全称</th>
                        <td>
                            <input type="text" name="companyName" id="companyName" class="input" />
                        </td>
                        <th>统一社会信用代码</th>
                        <td>
                            <input type="text" name="licenseNumber" id="licenseNumber" class="input"  />
                        </td>
                         <th>法定代表人</th>
                        <td>
                            <input type="text" name="legalPerson" id="legalPerson" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>注册资金(万元)</th>
                        <td>
                            <input type="text" name="registeredCapital" id="registeredCapital" class="input"  />
                        </td>
                        <th>成立日期</th>
                        <td class="databox-min">
                            <input type="text" id="duration" name="duration" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                        </td>
                         <th>开户银行</th>
                        <td>
                            <input type="text" name="bankName" id="bankName" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>银行账号</th>
                        <td>
                            <input type="text" name="accountNo" id="accountNo" class="input"  />
                        </td>
                        <th>IOS管理体系</th>
                        <td>
                            <input type="text" name="iosQuality" id="iosQuality" class="input"  />
                        </td>
                         <th>电话</th>
                        <td>
                            <input type="text" name="mobile" id="mobile" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>传真</th>
                        <td>
                            <input type="text" name="faxPhone" id="faxPhone" class="input"  />
                        </td>
                        <th>邮政编码</th>
                        <td>
                            <input type="text" name="zipCode" id="zipCode" class="input"  />
                        </td>
                         <th>员工总数</th>
                        <td>
                            <input type="text" name="staffCount" id="staffCount" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>所在城市</th>
                        <td class="area-td">
                            <p class="area-btn">请选择区域</p>
                        </td>
                        <td colspan="4">
                            <input type="text" name="address" placeholder="请输入详细地址" id="address" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>企业介绍</th>
                        <td colspan="5">
                            <textarea id="introduction" name="introduction"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>主要业务范围</th>
                        <td colspan="5">
                            <textarea id="businessScope" name="businessScope"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>资质等级</th>
                        <td colspan="5" class="aptitudes-list">
                            <div id="aptitudes">
                                <div class="flex">
                                    <select id="aptitude" name="aptitudes[0].aptitudeId">
                                        <option></option>
                                    </select>
                                    <select id="grade" name="aptitudes[0].gradeId">
                                        <option></option>
                                    </select>
                                    <span>证书号</span>
                                    <input type="text" name="aptitudes[0].certificateNo" placeholder="请输入证书号" class="input" />
                                    <!-- <span class='btn' id="add">-</span> -->
                                </div>
                            </div>
                            <span class='btn' id="add">+</span>
                        </td>
                    </tr>
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="cityId" name='cityId' />
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <input type="hidden" id="provCode" />
            <input type="hidden" id="cityCode" />
            <input type="hidden" id="distCode" />
        </form>
    </div>
    <div class="area-box" id="areaBox">
        <div>
            <span>省份：</span>
            <select class="pro"></select>
        </div>
        <div>
            <span>城市：</span>
            <select class="city"><option value="-1"></option></select>
        </div>
        <div>
            <span>区/县：</span>
            <select class="area"><option value="-1"></option></select>
        </div>
    </div>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();
        console.log(windowH)
        console.log(windowW)

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/company/user/companyInfo.htm',
                    title:'公司信息',
                    headerCls:'grid-title',
                    showFooter : true,
                    pagePosition : 'bottom',
                    pagination : true,
                    rownumbers:true,
                    pageSize : 20,
                    width : 'auto',
                    fitColumns : true,
                    fit:true,
                    sortName:'id',
                    sortOrder:'desc',
                    style : 'overflow:auto;',
                    singleSelect : true,
                    queryParams : {
                        includeSystem:false
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                        {
                            field : 'companyName',
                            title : '企业全称',
                            width : 80,
                            align :'center'
                        },
                        {
                            field : 'legalPerson',
                            title : '法人代表',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'registeredCapital',
                            title : '注册资金(万元)',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'duration',
                            title : '成立日期',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                return zjzm.formatDate(value, "/");
                            }
                        },
                        {
                            field : 'mobile',
                            title : '电话',
                            width : 80,
                            align :'center'
                        },
                        {
                            field : 'cityId',
                            title : '单位地址',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.area!=null && row.area!=""){
                                  return row.area.areaName+row.address;
                                }else if(row.address!=null && row.address!=""){
                                  return row.address;
                                }
                            }
                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 100,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(value.time, "yyyy/MM/dd hh:mm");
                            }
                        },
                        {
                            field : 'status',
                            title : '状态',
                            width : 80,
                            align:'center',
                            formatter: function(value, row, index) {
                                if(value){
                                    return "正常";
                                } else {
                                    return "冻结";
                                }
                                return "";
                            }

                        },{
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 80,
                            formatter: function (value, row, index) {
                                return getOptByStatus(row, index);
                            },
                        }
                    ]],
                    toolbar: [{
                        id:'insertAuth',
                        iconCls: 'icons-add',
                        text: '新建',
                        handler: function () {
                            showDetail();
                        }
                     },
                    {
                        id:'exportAuth',
                        iconCls: 'icons-import',
                        text: '导出',
                        handler: function () {
                            downLoad();
                        } 
                    }],
                    loadFilter : function(data){
                        
	                    if(${companyInfoAddAuth} || ${companyInfoExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${companyInfoAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${companyInfoExportAuth}){
		                        $("#exportAuth").show();
		                    }else{
		                        $("#exportAuth").hide();
		                    }
		                }else{
		                	$('div.datagrid div.datagrid-toolbar').hide();
		                }
                        if (data.success) {
                            return data;
                        } else {
                            nullData(data);
                        }
                        return data;
                    }
                }
            );
        });

        function getOptByStatus(row, index){
            var status = row.applyStatus;
            var opts = "<a href='javascript:showDetail(" + index + ")'>查看</a>  ";
            return opts;
        }

        function downLoad(){
          $("#form").form("submit", {
	        url:"/company/user/downLoadCompanyInfo.htm",
		    onSubmit : function() {
		  },
		  success : function(data) {
			  $.messager.progress("close");
			  data = parseResp(data);
			  if (data.success) {
			  } else {
				  $.messager.alert("错误", data.resultMsg, "error");
			  }
		  }
	     });
      }
        var obj = {};
        var proId;
        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '公司详情',
                width: windowW - 50,
                height: windowH - 50,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        $.ajax({
                            url: "/company/user/"+ list.id +"/Aptitude.htm",
                            type: 'post',
                            dataType: 'json',
                            data: {},
                            success: function (data) {
                                setInfo(data.data);
                            }
                        });
                        if(${companyInfoUpdateAuth}){
	                       $("#updateAuth").show();
	                    }else{
	                       $("#updateAuth").hide();
	                    }
                    }
                },
                buttons: [
                    {
                        id:'updateAuth',
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                            var durationVal = $('#duration').datebox('getValue');
                            durationVal = durationVal.replace(/\//g, '');
                            $("input[name='duration']").val(durationVal);
                            if (isNaN($("#staffCount").val())) {
                                $.messager.alert("信息", "员工总数必须为数字", "info");
                            } else {
                                if (index != undefined) {
                                    // 更新
                                    $("#form1").attr("action", "/company/user/updCompany.htm");
                                    $("#id").val(list.id);
                                    $("#form1").form("submit", {
                                        onSubmit : function() {
                                            $.messager.progress({
                                                interval : 200,
                                                text : '处理中...'
                                            });
                                            return true;
                                        },
                                        success : function(list) {
                                            var list = JSON.parse(list);
                                            if(list.success){
                                                $("#provCode").val("");
                                                $("#cityCode").val("");
                                                $("#distCode").val("");
                                                $("#detailInfo").dialog("close");
                                                $("#form1")[0].reset();
                                                $(".area-btn").html("请选择区域");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","修改成功!","info");
                                                $("#detailInfo").dialog("close");
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        }
                                    });
                                } else {
                                    // 添加
                                    $("#form1").attr("action", "/company/user/instCompany.htm");
                                    $("#form1").form("submit", {
                                        onSubmit : function() {
                                            $.messager.progress({
                                                interval : 200,
                                                text : '处理中...'
                                            });
                                            return true;
                                        },
                                        success : function(list) {
                                            var list = JSON.parse(list);
                                            if(list.success) {
                                                $("#detailInfo").dialog("close");
                                                $("#provCode").val("");
                                                $("#cityCode").val("");
                                                $("#distCode").val("");
                                                $("#form1")[0].reset();
                                                $(".area-btn").html("请选择区域");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","添加成功!","info");
                                                $("#detailInfo").dialog("close");
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        },

                                    });
                                }
                            }
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#provCode").val("");
                            $("#cityCode").val("");
                            $("#distCode").val("");
                            $("#form1")[0].reset();
                            $(".area-btn").html("请选择区域");
                            $("#detailInfo").dialog("close");
                        }
                    }
                ]
            });
        }


       // 地区选择
        $(".area-btn").on("click", function () {
        if(!($("#address").prop("readonly"))){
            $("#areaBox").show();
            $("#areaBox").dialog({
                title: '地区选择',
                width: 400,
                height: 250,
                closable: false,
                onBeforeOpen: function () {
                    getPro();
                },
                buttons: [
                    {
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                            var proName = $(".pro").find("option:selected").text();
                            var cityName = $(".city").find("option:selected").text();
                            var areaName = $(".area").find("option:selected").text();
                            if (proName == "") {
                                $.messager.alert("提示", "请选择省份", "info");
                            } else {
                                $(".area-btn").html(proName + cityName + areaName);
                                $("#cityId").val($(".area").val());
                                $("#areaBox").dialog("close");
                                $("#provCode").val($(".pro").find("option:selected").val());
                                $("#cityCode").val($(".city").find("option:selected").val());
                                $("#distCode").val($(".area").find("option:selected").val());
                            }
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#areaBox").dialog("close");
                            $(".pro").find("option").eq(0).attr("selected",true);
                            $(".city").find("option").eq(0).attr("selected",true);
                            $(".area").find("option").eq(0).attr("selected",true);
                        }
                    }
                ]
            });
        }

        });

        // 省份/城市/地区相关操作接口
        $(".pro").on("change", function () {
            proId = $(this).val();
            getCityChange(proId,$(".pro").find("option:selected").attr("provid"));
        });
        $(".city").on("change", function () {
            var cityId = $(this).val();
            getAreaChange(proId, cityId);
        });
         $(".area").on("change", function () {
            var areaId = $(this).val();
        });
        // 获取省份
        function getPro() {
            $.ajax({
                url: "/area/user/getAllProv.htm",
                type: 'post',
                dataType: 'json',
                data: {},
                success: function (data) {
                    var box = $(".pro"),
                        dom = "<option value='-1'></option>";
                    for (var i = 0; i < data.data.length; i++) {
                        var areaName = data.data[i].areaName;
                        if (areaName) {
                            if($("#provCode").val()==data.data[i].provCode){
                                dom += "<option selected provId="+ data.data[i].id +" value='"+ data.data[i].provCode +"'>"+ areaName +"</option>";
                            }else{
                                dom += "<option provId="+ data.data[i].id +" value='"+ data.data[i].provCode +"'>"+ areaName +"</option>";
                            }
                        }
                    }
                    box.empty();
                    box.append(dom);
                    getCity($(".pro").find("option:selected").val());
                }
            });
        }

         // 获取市
        function getCityChange(id,provalue) {
           if(id==11 || id==12 || id==31 || id==50){
               $.ajax({
				  url : "/area/user/getCityDist.htm",
				  type : 'post',
				  dataType : 'json',
				  data : {
						provCode : id
				  },
				  success : function(data) {
				  var dom = '';
				  var doms="";
				  var box = $(".city");
				  var distBox = $(".area");
				  for ( var i = 0; i < data.data.length; i++) {
						dom += '<option value=' + data.data[i].id + '>'
								+ data.data[i].areaName + '</option>';
						}
						box.empty();
						box.append(doms);
						distBox.empty();
						distBox.append(dom);
						box.hide();
				   }
			 });
           }else{
               $.ajax({
                url: "/area/user/getCity.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    provCode: id
                },
                success: function (data) {
                    var box = $(".city"),
                        distBox = $(".area"),
				        //distDom = "<option  selected value='"+data.data[0].id+"'></option>",
				        distDom = "<option  selected value='"+provalue+"'></option>",
                        dom = "";
                        box.show();
                    for (var i = 0; i < data.data.length; i++) {
                          dom += "<option value='"+ data.data[i].cityCode +"'>"+ data.data[i].areaName +"</option>";
                    }
                    box.empty();
                    box.append(dom);
                    distBox.empty();
					distBox.append(distDom);
                }
            });
           }
        }
        // 获取区
        function getAreaChange(proId, cityId) {
            $.ajax({
                url: "/area/user/getdist.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    provCode: proId,
                    cityCode: cityId
                },
                success: function (data) {
                    var box = $(".area"),
                        dom = "";
                    for (var i = 0; i < data.data.length; i++) {
                        dom += "<option value='"+ data.data[i].id +"'>"+ data.data[i].areaName +"</option>";
                    }
                    box.empty();
                    box.append(dom);
                }
            });
        }
        // 获取市
        function getCity(id) {
           if(id==11 || id==12 || id==31 || id==50){
               $.ajax({
				  url : "/area/user/getCityDist.htm",
				  type : 'post',
				  dataType : 'json',
				  data : {
						provCode : id
				  },
				  success : function(data) {
				  var dom = '';
				  var box = $(".city");
				  var distBox = $(".area");
				  for ( var i = 0; i < data.data.length; i++) {
				      if($("#distCode").val()==data.data[i].id){
				         dom += '<option selected value=' + data.data[i].id + '>'
								+ data.data[i].areaName + '</option>';
						}else{
				         dom += '<option value=' + data.data[i].id + '>'
								+ data.data[i].areaName + '</option>';
						}
				      }

						distBox.empty();
						distBox.append(dom);
						box.hide();
				   }
			 });
           }else{
               $.ajax({
                url: "/area/user/getCity.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    provCode: id
                },
                success: function (data) {
                    var box = $(".city"),
                        distBox = $(".area"),
				        //distDom = "<option  selected value='"+data.data[0].id+"'></option>",
				         distDom = "<option  selected value=''></option>",
                        dom = "";
                        box.show();
                    for (var i = 0; i < data.data.length; i++) {
                          if($("#cityCode").val()==data.data[i].cityCode){
                             dom += "<option selected value='"+ data.data[i].cityCode +"'>"+ data.data[i].areaName +"</option>";
                          }else{
                             dom += "<option value='"+ data.data[i].cityCode +"'>"+ data.data[i].areaName +"</option>";
                          }

                    }
                    box.empty();
                    box.append(dom);
                    distBox.empty();
					distBox.append(distDom);
					getArea(id,$(".city").find("option:selected").val());
                }
            });
           }
        }
        // 获取区
        function getArea(proId, cityId) {
            $.ajax({
                url: "/area/user/getdist.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    provCode: proId,
                    cityCode: cityId
                },
                success: function (data) {
                    var box = $(".area"),
                        dom = "";
                    for (var i = 0; i < data.data.length; i++) {
                        if($("#distCode").val()==data.data[i].id){
                          dom += "<option selected value='"+ data.data[i].id +"'>"+ data.data[i].areaName +"</option>";
                        }else{
                          dom += "<option value='"+ data.data[i].id +"'>"+ data.data[i].areaName +"</option>";
                        }

                    }
                    box.empty();
                    box.append(dom);
                }
            });
        }
        // 根据id值获取详细地址
        function getDetailAddress(id) {
            $.ajax({
                url: "/area/user/getAllArea.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    id: id
                },
                success: function (data) {
                    if (data.success) {
                        $(".area-btn").html(data.rows[0].areaName);
                        $("#provCode").val(data.rows[0].provCode);
                        $("#cityCode").val(data.rows[0].cityCode);
                        $("#distCode").val(data.rows[0].id);
                    }
                }
            });
        }

        // 查看赋值
        function setInfo(datas) {
            $("#companyName").val(datas.companyName);
            $("#licenseNumber").val(datas.licenseNumber);
            $("#legalPerson").val(datas.legalPerson);
            $("#registeredCapital").val(datas.registeredCapital);
            $("#cityId").val(datas.cityId);
            getDetailAddress(datas.cityId);
            $('#duration').datebox('setValue', zjzm.formatDate(datas.duration, '/'));
            $("#bankName").val(datas.bankName);
            $("#accountNo").val(datas.accountNo);
            $("#iosQuality").val(datas.iosQuality);
            $("#mobile").val(datas.mobile);
            $("#faxPhone").val(datas.faxPhone);
            $("#zipCode").val(datas.zipCode);
            $("#staffCount").val(datas.staffCount);
            $("#address").val(datas.address);
            $("#introduction").val(datas.introduction);
            $("#businessScope").val(datas.businessScope);

            var aptitudesDom = "";
            var aptitudesBox = $("#aptitudes");
            for (var i = 0; i < datas.aptitudes.length; i++) {
                aptitudesDom += "<div class='flex'><select name='aptitudes["+ i +"].aptitudeId'>";
                for (var j = 0; j < arr1.length; j++) {
                    if (datas.aptitudes[i].aptitudeId == arr1[j].attributeId) {
                        aptitudesDom += "<option value='"+ arr1[j].attributeId +"' selected>"+ arr1[j].attributeName +"</option>";
                    } else {
                        aptitudesDom += "<option value='"+ arr1[j].attributeId +"'>"+ arr1[j].attributeName +"</option>";
                    }
                }
                aptitudesDom += "</select><select name='aptitudes["+ i +"].gradeId'>";
                for (var k = 0; k < arr1.length; k++) {
                    if (datas.aptitudes[i].aptitudeId == arr2[k].attributeId) {
                        aptitudesDom += "<option value='"+ arr2[k].attributeId +"' selected>"+ arr2[k].attributeName +"</option>";
                    } else {
                        aptitudesDom += "<option value='"+ arr2[k].attributeId +"'>"+ arr2[k].attributeName +"</option>";
                    }
                }
                aptitudesDom += "</select>"+
                    "<span>证书号</span>"+
                    "<input type='text' name='aptitudes["+ i +"].certificateNo' value='"+ datas.aptitudes[i].certificateNo +"' class='input' />"+
                    "<span class='btn remove'>-</span>"+
                "</div>";
            }
            aptitudesBox.empty();
            aptitudesBox.append(aptitudesDom);
        }

        // 添加资质等级
        $("#add").on("click", function () {
            var flag = $("#aptitudes").find(".remove").length;
            var aptitudesDom = "";
            var aptitudesBox = $("#aptitudes");
                aptitudesDom += "<div class='flex'><select name='aptitudes["+ flag +"].aptitudeId' >";
                for (var j = 0; j < arr1.length; j++) {
                    aptitudesDom += "<option value='"+ arr1[j].attributeId +"'>"+ arr1[j].attributeName +"</option>";
                }
                aptitudesDom += "</select><select name='aptitudes["+ flag +"].gradeId'>";
                for (var k = 0; k < arr1.length; k++) {
                    aptitudesDom += "<option value='"+ arr2[k].attributeId +"'>"+ arr2[k].attributeName +"</option>";
                }
                aptitudesDom += "</select>"+
                "<span>证书号</span>"+
                "<input type='text' name='aptitudes["+ flag +"].certificateNo' placeholder='请输入证书号' class='input' />"+
                "<span class='btn remove'>-</span>"+
            "</div>";
            aptitudesBox.append(aptitudesDom);
        });

        // 删除资质等级
        $("#aptitudes").on("click", ".remove", function () {
            $(this).closest('div').remove();
        });

        // 获取资质基础信息
        var arr1 = [];
        var arr2 = [];
        function getBasic(type) {
            var dom = "";
            $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {},
                success: function (data) {
                    var datas = data.rows;
                    for (var i = 0; i < datas.length; i++) {
                        if (datas[i].categoryId == type) {
                            if (type == 1) {
                                arr1.push(datas[i]);
                            }
                            if (type == 2) {
                                arr2.push(datas[i]);
                            }
                        }
                    }
                }
            });
        }
        getBasic(1);
        getBasic(2);

        function setBasic(box, arr) {
            var dom = "";
            for (var i = 0; i < arr.length; i++) {
                dom += "<option value='"+ arr[i].attributeId +"'>"+ arr[i].attributeName +"</option>";
            }
            box.html(dom);
        }
        setTimeout(function () {
            setBasic($("#aptitude"), arr1);
            setBasic($("#grade"), arr2);
        }, 500);


        // function reflushDatagrid(){
        //  $("#datagrid").datagrid("reload");
        // }
        function queryProduct(){
            var param = {};
            param.companyName= $('#companyName-search').val();
            param.legalPerson= $('#legalPerson-search').val();
            param.cityId= $('#cityId-search').val();
            param.durationFrom= $('#durationFrom').datebox('getValue');
            param.durationTo= $('#durationTo').datebox('getValue');
            param.registeredCapital = $('#registeredCapital-search').val();
            if ($('#status-search').val() != "-1") {
                param.status= $('#status-search').val();
            }
            param.createTimeStart= $('#createTimeStart').datebox('getValue');
            param.createTimeEnd= $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
    </script>
</body>
</html>