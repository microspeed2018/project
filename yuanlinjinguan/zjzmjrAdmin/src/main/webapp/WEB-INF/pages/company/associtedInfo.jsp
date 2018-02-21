
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>关联公司信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <style type="text/css">
       .info-table {
            border-top: 1px solid #ddd;
            border-right: 1px solid #ddd;
            padding: 0;
            width: 100%;
       }
       .info-table th, .info-table td {
            text-align: center;
            border-bottom: 1px solid #ddd;
            border-left: 1px solid #ddd;
            padding: 0;
            width: auto;
       }
       .diolag-table select {
            width: 100%;
        }
        #user-list input {
            text-align: center;
            border: 0;
            width: 100%;
        }
        #addUser th {
            width: 6%;
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
                                <input type="text" id="legalPerson-search" class="input">
                            </td>
                            <th>单位地址</th>
                            <td>
                                <input type="text" id="address-search" class="input">
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>状态</th>
                            <td>
                                <select class="select" id="status-search">
                                    <option value="-1"></option>>
                                    <option value="1">正常</option>
                                    <option value="0">冻结</option>
                                </select>
                            </td>
                            <td colspan="5"></td>
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
                        <th>法定代表人</th>
                        <td>
                            <input type="text" name="legalPerson" id="legalPerson" class="input"  />
                        </td>
                        <th>联系方式</th>
                        <td>
                            <input type="text" name="legalMobile" id="legalMobile" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>开户银行</th>
                        <td>
                            <input type="text" name="bankName" id="bankName" class="input"  />
                        </td>
                        <th>银行账号</th>
                        <td>
                            <input type="text" name="accountNo" id="accountNo" class="input"  />
                        </td>
                        <th>统一社会信用代码</th>
                        <td>
                            <input type="text" name="licenseNumber" id="licenseNumber" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>电话</th>
                        <td>
                            <input type="text" name="mobile" id="mobile" class="input"  />
                        </td>
                        <th>传真</th>
                        <td>
                            <input type="text" name="faxPhone" id="faxPhone" class="input"  />
                        </td>
                        <th>公司属性</th>
                        <td>
                            <select id="companyDistinguish" name="companyDistinguish"></select>
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
                        <th>联系人</th>
                        <td colspan="5">
                            <a  href='javascript:addUser()'>添加</a>
                        </td>
                    </tr>
                    <tr>
                        <th></th>
                        <td colspan="5">
                            <table class="diolag-table info-table" cellpadding="0" cellspacing="0" border="0" width="100%" id="user-list">
                                <thead>
                                    <tr>
                                        <th style= "width:5%;">No.</th>
                                        <th style= "width:10%;">姓名</th>
                                        <th style= "width:15%;">职务</th>
                                        <th style= "width:15%;">联系方式</th>
                                        <th style= "width:15%;">e-mail</th>
                                        <th style= "width:25%;">详细地址</th>
                                        <th style= "width:5%;'">详细</th>
                                    </tr>
                                </thead>
                                <tbody>

                                </tbody>
                            </table>
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
            <!-- 公司id暂时为1为了测试 -->
        </form>
    </div>
    <div id="addUser">
        <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
            <tbody>
                <tr>
                    <th>姓名</th>
                    <td>
                        <input type="text" name="name-user" id="name-user" class="input" />
                    </td>
                </tr>
                <tr>
                    <th>职务</th>
                    <td>
                        <input type="text" name="job-user" id="job-user" class="input"  />
                    </td>
                </tr>
                <tr>
                    <th>联系方式</th>
                    <td>
                        <input type="text" name="mobile-user" id="mobile-user" class="input"  />
                    </td>
                </tr>
                <tr>
                    <th>e-mail</th>
                    <td>
                        <input type="text" name="email-user" id="email-user" class="input"  />
                    </td>
                </tr>
                <tr>
                    <th>详细地址</th>
                    <td>
                        <input type="text" name="address-user" id="address-user" class="input"  />
                    </td>
                </tr>
            </tbody>
        </table>
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
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/companyAssocited/user/getCompanyAssociatedInfo.htm',
                    title:'关联公司信息',
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
                            align :'left'
                        },
                        {
                            field : 'legalPerson',
                            title : '法人代表',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'mobile',
                            title : '电话',
                            width : 80,
                            align :'center'
                        },
                        {
                            field : 'areaName',
                            title : '所在城市',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.area!=null){
                                   return row.area.areaName;
                                }                               
                            }
                        },
                        {
                            field : 'attributeName',
                            title : '属性',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.basicData!=null){
                                    return row.basicData.attributeName;
                                } 
                               
                            }
                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 100,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(row.createTime.time, "yyyy/MM/dd hh:mm");
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
                                return "<a href='javascript:showDetail(" + index + ")'>查看</a>";
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
                        if(${companyAssocitedAddAuth} || ${companyAssocitedExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${companyAssocitedAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${companyAssocitedExportAuth}){
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

        var obj = {};
        var proId;
        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '关联公司详情',
                width: windowW - 50,
                height: windowH - 200,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        if(${companyAssocitedUpdateAuth}){
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

                            if (index != undefined) {
                                // 更新
                                $("#form1").attr("action", "/companyAssocited/user/updateCompanyAssocitedInfo.htm");
                                $("#id").val(list.id);
                                $("#companyId").val(list.companyId);
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
                                            $.messager.progress("close");
                                            $.messager.alert("信息","修改成功!","info");
                                            $("#provCode").val("");
                                            $("#cityCode").val("");
                                            $("#distCode").val("");
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $(".area-btn").html("请选择区域");
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                            } else {
                                // 添加
                                $("#form1").attr("action", "/companyAssocited/user/insertCompanyAssocitedInfo.htm");
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
                                            $.messager.progress("close");
                                            $.messager.alert("信息","添加成功!","info");
                                            $("#provCode").val("");
                                            $("#cityCode").val("");
                                            $("#distCode").val("");
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $(".area-btn").html("请选择区域");
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
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

        function addUser() {
            $("#addUser").show();
            $("#addUser").dialog({
                title: '添加联系人',
                width: windowW - 250,
                height: windowH - 250,
                closable: false,
                onBeforeOpen: function () {},
                buttons: [
                    {
                        iconCls: 'icons-true',
                        text: '提交',
                        handler: function () {
                            var box = $("#user-list tbody");
                            var index = Number(box.find("tr").length);
                            var dom = "<tr>"+
                                "<td>"+ (index + 1) +"</td>"+
                                "<td><input name='companyAssociatedContact["+ index +"].name' readonly value='"+$("#name-user").val()+"' /></td>"+
                                "<td><input name='companyAssociatedContact["+ index +"].job' readonly value='"+$("#job-user").val()+"' /></td>"+
                                "<td><input name='companyAssociatedContact["+ index +"].mobile' readonly value='"+$("#mobile-user").val()+"' /></td>"+
                                "<td><input name='companyAssociatedContact["+ index +"].email' readonly value='"+$("#email-user").val()+"' /></td>"+
                                "<td><input name='companyAssociatedContact["+ index +"].address' readonly value='"+$("#address-user").val()+"' /></td>"+
                                "<td><a href='javascript:void(0)'>详细</a></td>"+
                            "</tr>";
                            box.append(dom);
                            $("#addUser").dialog("close");
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#addUser").dialog("close");
                        }
                    }
                ]
            });
        }

        function downLoad(){
          $("#form").form("submit", {
	        url:"/companyAssocited/user/downLoadCompanyAssociatedInfo.htm",
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

        // 查看联系人详细
        $("#user-list").on("click", "a", function () {
            var obj = {};
            obj.name = $(this).closest("tr").find("input").eq(0).val();
            obj.job = $(this).closest("tr").find("input").eq(1).val();
            obj.mobile = $(this).closest("tr").find("input").eq(2).val();
            obj.email = $(this).closest("tr").find("input").eq(3).val();
            obj.address = $(this).closest("tr").find("input").eq(4).val();
            lookUser(obj);
        });
        function lookUser(obj) {
            $("#addUser").show();
            $("#addUser").dialog({
                title: '查看联系人',
                width: 400,
                height: 300,
                closable: false,
                onBeforeOpen: function () {
                    $("#name-user").val(obj.name);
                    $("#job-user").val(obj.job);
                    $("#mobile-user").val(obj.mobile);
                    $("#email-user").val(obj.email);
                    $("#address-user").val(obj.address);
                },
                buttons: [
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#addUser").dialog("close");
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

        // 获取基础数据
        function getBasic(id, cb) {
            $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    categoryId : id
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
        getBasic(3, createCompanyType);
        function createCompanyType(datas) {
            var box = $("#companyDistinguish"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
        }

        // 查看赋值
        function setInfo(datas) {
            console.log(datas);
            $("#companyName").val(datas.companyName);
            $("#licenseNumber").val(datas.licenseNumber);
            $("#legalPerson").val(datas.legalPerson);
            $("#legalMobile").val(datas.legalMobile);
            $("#bankName").val(datas.bankName);
            $("#accountNo").val(datas.accountNo);
            $("#mobile").val(datas.mobile);
            $("#faxPhone").val(datas.faxPhone);
            $("#companyDistinguish").val(datas.companyDistinguish);
            $("#address").val(datas.address);
            $("#introduction").val(datas.introduction);
            $("#cityId").val(datas.cityId);
            if(datas.area){
	            $(".area-btn").html(datas.area.areaName);
	            $("#provCode").val(datas.area.provCode);
	            $("#cityCode").val(datas.area.cityCode);
	            $("#distCode").val(datas.area.id);
            }
            // 创建联系人
            var users = datas.companyAssociatedContact;
            var box = $("#user-list tbody");
             var dom = "";
            for (var i = 0; i < users.length; i++) {
                dom += "<tr>"+
                    "<td>"+ (i+1) +"</td>"+
                    "<td><input name='companyAssociatedContact["+ i +"].name' readonly value='"+ users[i].name +"' /></td>"+
                    "<td><input name='companyAssociatedContact["+ i +"].job' readonly value='"+ users[i].job +"' /></td>"+
                    "<td><input name='companyAssociatedContact["+ i +"].mobile' readonly value='"+ users[i].mobile +"' /></td>"+
                    "<td><input name='companyAssociatedContact["+ i +"].email' readonly value='"+ users[i].email +"' /></td>"+
                    "<td><input name='companyAssociatedContact["+ i +"].address' readonly value='"+ users[i].address +"' /></td>"+
                    "<td><a href='javascript:void(0)'>详细</a></td>"+
                "</tr>";
            }
            box.empty();
            box.append(dom);
        }

        function queryProduct(){
            var param = {};
            param.companyName = $('#companyName-search').val();
            param.legalPerson = $('#legalPerson-search').val();
            param.address = $('#address-search').val();
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            if ($('#status-search').val() != "-1") {
                param.status= $('#status-search').val();
            }
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
    </script>
</body>
</html>