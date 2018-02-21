
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<%@ taglib uri="http://www.yztz.com/jsp/jstl/sec" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>项目信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <meta http-equiv="pragma" content="no-cache">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="expires" content="0">
    <meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
    <meta http-equiv="description" content="This is my page">  
    <script type="text/javascript" src="${res_js }/jslib/viewer.min.js"></script>
    <link rel="stylesheet" href="http://www.jq22.com/demo/jQueryViewer20160329/css/viewer.min.css">
    <script type="text/javascript" src="${res_js }/jslib/fileUpLoad.js?v=${ver}"></script>
    <style type="text/css">
        .diolag-table select {
            width: 100%;
        }
        #package-list td{
            width: 5%;
        }
        #cashier-list td{
            width: 5%;
        }
        #detailInfo input[type='button'] {
            width: 30px;
            height: 25px;
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
    						<th>合同编号</th>
    						<td>
                                <input type="text" id="contractNo" name="contractNo" class="input">
                            </td>						
                            <th>技术负责人</th>
                            <td>
                                <input id="projectLeader" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td>
                            <th>发包单位</th>
                            <td>
                               <input name="contractAwardCompany" id="contractAwardCompany" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210"/>
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="createTimeStart" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="createTimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>所在城市</th>
                            <td>
                                <input type="text" class="input" id="areaId" />
                            </td>
                            <th>项目名称</th>
                            <td>
                                <input type="text" id="projectName" name="projectName" class="input">
                            </td>
                            <th>
                                                                                               项目编号
                            </th>
                            <td>
                               <input type="text" class="input" id="projectNo" />
                            </td>
                            <th>投资额(万元)</th>
                            <td>
                                <input type="text" class="input half-input" id="investmentMountStart" name="investmentMountStart" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="investmentMountEnd" name="investmentMountEnd" />
                            </td>
    					</tr>
                        <tr>
                            <th>经营专员</th>
                            <td>
                                <select class="select" id="managementPersonnel">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>项目类别</th>
                            <td>
                                <select class="select" id="category">
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
                            <th>院方比例(%)</th>
                            <td>
                                <input type="text" class="input half-input" id="ratioFrom" name="ratioFrom" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="ratioTo" name="ratioTo" />
                            </td>
                        </tr>
                        <tr>
                          <th>设计面积(㎡)</th>
                            <td>
                                <input type="text" class="input half-input" id="designAreaFrom" name="designAreaFrom" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="designAreaTo" name="designAreaTo" />
                            </td>
                            <th>项目负责人</th>
                            <td>
                                <input id="projectLiable" class="input" />
                            </td>
                            <th>状态</th>
                            <td>
                                <select id="status" name="status" class="select">
                                   <option value="-1"></option>
                                   <option selected value="1">已审核</option>
                                   <option value="0">未审核</option>
                                </select>
                            </td>
                            <th>合同金额(万元)</th>
                            <td>
                                <input type="text" class="input half-input" id="contractCapitalFrom" name="contractCapitalFrom" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="contractCapitalTo" name="contractCapitalTo" />
                            </td>
                        </tr>
                        <tr>
                            <th>
                              <input type="checkbox" name="haveTechnical" id="haveTechnical-s"/>
                            </th>
                            <td>只看含技术标</td>
                            <td colspan="4"></td>
                            <th></th>
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
    </div>
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>合同编号</th>
                        <td>
                            <input type="text" name="contractNo" id="contractNo-s" class="input" readonly />
                        </td>
                        <th>所属项目</th>
                        <td>
                            <select class="select" name="projectId" id="projectId-s">
                             <option value="-1"/>
                        </select>
                        </td>
                        <th>发包单位</th>
                        <td>
                          <input name="contractAwardCompany" id="contractAwardCompany-s" class="easyui-combobox" style="height:30px;width:220%;" panelHeight="210"/>
                        </td>
                    </tr>
                    <tr>
                        <th>项目名称</th>
                        <td>
                            <input type="text" id="name-s" name="name" class="input">
                        </td>
                        <th>合同金额(万元)</th>
                        <td>
                            <input type="text" name="contractCapital" id="contractCapital" class="input"  />
                        </td>
                         <th>设计面积(㎡)</th>
                        <td>
                            <input type="text" name="designArea" id="designArea" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>项目类别</th>
                        <td>
                            <select name="category" id='category-s'>
                              <option value="-1"></option>
                            </select>
                        </td>
                        <th>项目类型</th>
                        <td>
                            <select name="type" id="type-s"></select>
                        </td>
                         <th>投资额(万元)</th>
                        <td>
                            <input type="text" name="investmentMount" id="investmentMount" class="input"  />
                        </td>
                    </tr>
                    <tr>
                    <th>签约日期</th>
                        <td>
                           <input type="text" id="signDate" name="signDate" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"/>
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
                        <th>项目设计阶段</th>
                        <td colspan="5">
                            <label>
                                <input type="checkbox" id="haveScheme" name="haveScheme" />
                                方案设计
                            </label>
                            <label>
                                <input type="checkbox" id="haveDevelopment" name="haveDevelopment" />
                                扩初方案设计
                            </label>
                            <label>
                                <input type="checkbox" id="haveDrawing" name="haveDrawing" />
                                施工图设计及后期服务
                            </label>
                            <label>
                                <input type="checkbox" id="havePlanning" name="havePlanning" />
                                规划设计
                            </label>
                        </td>
                    </tr>
                   <tr id="paymentModeTr0">
                        <th>付款方式(万元)</th>
                        <td>
                            <select name="contractPayment[0].paymentMode" id=paymentMode0></select>
                        </td>
                        <td>
                           <input type="text" id="paymentAmount0"name="contractPayment[0].paymentAmount" class="input"  />
                        </td>
                        <td>
                           <input type="button" id="paymentButton" onclick="addPayMentMode()" value="+"  /> <input type="button" id="paymentButtonReduce" onclick="reducePayMentMode()" value="-"  />
                        </td>
                    </tr>
                    <tr>
                        <th>合同备注</th>
                        <td colspan="5">
                            <textarea id="contractMemo" name="contractMemo"></textarea>
                        </td>
                    </tr>
                    <tr id="ratioTr">
                        <th>院方比例</th>
                        <td>
                            <input type="text" id="ratio-s" name="ratio"  class="input"  readonly/>
                        </td>
                    </tr>
                    <tr id="ratioMemoTr">
                        <th>比例备注</th>
                        <td colspan="5">
                            <textarea id="ratioMemo" name="ratioMemo" readonly></textarea>
                        </td>
                    </tr>
                    <tr id="packageTr">
                         <th>分包团队及金额</th>
                         <td colspan="5">
                            <table class="diolag-table info-table" cellpadding="0" cellspacing="0" border="0" width="80%" id="package-list">
                              <tbody>

                              </tbody>
                            </table>
                         </td>
                    </tr>
                     <tr id="cashierTr">
                         <th>开票及到账情况<br>(万元)</th>
                         <td colspan="5">
                            <table class="diolag-table info-table" cellpadding="0" cellspacing="0" border="0" width="80%" id="cashier-list">
                              <tbody>

                              </tbody>
                            </table>
                         </td>
                    </tr>
                    <tr id="signTable">
                   
                    </tr>
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="city" name='city' />
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <input type="hidden" id="projectId" name='projectId' />
            <input type="hidden" id="projectUpdate" name='projectUpdate' />
            <input type="hidden" id="contractUpdate" name='contractUpdate' />
            <input type="hidden" id="paymentUpdate" name='paymentUpdate' />
            <input type="hidden" id="version" name='version'/>
            <input type="hidden" id="contractVersion" name='contractVersion'/>
            <input type="hidden" id="temporaryId" name='temporaryId'/>
            <input type="hidden" id="provCode" />
            <input type="hidden" id="cityCode" />
            <input type="hidden" id="distCode" />
            <select name="managementDepartment" id="managementDepartment" style="display:none;"></select>
            <!-- 公司id暂时为1为了测试 -->
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
    <div id="package">
    <table class="diolag-table info-table" cellpadding="0" cellspacing="0" border="0" width="80%" >
         <tbody>
          <tr>
            <th>分包负责人</th>
            <td>
             <input id="leader"/>
            </td>
            <td></td>
          </tr>
          <tr>
            <th>分包金额(万元)</th>
            <td>
             <input id="subpackageCapital"/>
            </td>
            <td></td>
          </tr>
          <tr id="paymentTr">
            <th>分包内容</th>
            <td>
            <input id="subpackageContent"/>
            </td>
            <td></td>
          </tr>
          <tr>
            <th>分包备注</th>
            <td>
             <input id="subpackageMemo"/>
            </td>
            <td></td>
          </tr>
           <tr>
            <th>状态</th>
            <td>
             <input id="packageStatus"/>
            </td>
            <td></td>
          </tr>
         </tbody>
    </table>
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();
        var flag;
        var contractSubpackage=null;
        //存储修改前付款方式的数组
        var oldArr = [];
        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/contract/user/getProjectContractList.htm',
                    title:'合同信息',
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
                      status:1
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                        {
                            field : 'contractNo',
                            title : '合同编号',
                            width : 80,
                            align :'center'
                        },
                        {
                            field : 'name',
                            title : '项目名称',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                if(row.project!=null){
                                   return row.project.name;
                                }                              
                            }
                        },
                        {
                            field : 'managementPersonnel',
                            title : '经营专员',
                            width : 60,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.manager!=null){
                                   return row.manager.name;
                                }else{
                                   return "(未确立)";
                                }
                            }
                        },
                        {
                            field : 'projectLeader',
                            title : '负责人',
                            width : 60,
                            align :'center',
                            formatter: function (value, row, index) {
                                 if(row.leader!=null){
                                   return row.leader.name;
                                }else{
                                   return "(未确立)";
                                }
                            }
                        },
                        {
                            field : 'signDate',
                            title : '签约日期',
                            width : 70,
                            align :'center',
                            formatter: function (value, row, index) {
                               if(value==null || value==""){
                                   return "";
                               }else{
                                   return value.substring(0,4)+"/"+value.substring(4,6)+"/"+value.substring(6,8);
                               }

                            }
                        },
                        {
                            field : 'city',
                            title : '所在城市',
                            width : 120,
                            align :'center',
                            formatter: function (value, row, index) {
                               if(row.area!=null ){
                                   return row.area.areaName;
                               }                              
                            }
                        },
                        {
                            field : 'contractCapital',
                            title : '合同金额(万元)',
                            width : 80,
                            align :'right',
                            formatter: function (value, row, index) {
                               return formatCurrency1(value);
                            }
                        },
                        {
                            field : 'ratio',
                            title : '院方比例',
                            width : 60,
                            align :'center'
                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(value.time, "yyyy/MM/dd hh:mm:ss");
                            }
                        },
                        {
                            field : 'status',
                            title : '状态',
                            width : 60,
                            align:'center',
                            formatter: function(value) {
                               if(value=1){
                                 return "正常";
                               }else{
                                 return "未审核";
                               }
                            }
                        },
                        {
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 60,
                            formatter: function (value, row, index) {
                                return detail(row, index);
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
                    },{
                        id:'exportAuth',
                        iconCls: 'icons-import',
                        text: '导出',
                        handler: function () {
                            downLoad();
                        } 
                    }],
                    loadFilter : function(data){
                        if(${contractAddAuth} || ${contractExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${contractAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${contractExportAuth}){
		                        $("#exportAuth").show();
		                    }else{
		                        $("#exportAuth").hide();
		                    }
		                }else{
		                	$('div.datagrid div.datagrid-toolbar').hide();
		                }
                        if (data.success) {
                            if(data.isChief==1){
                              $("#haveTechnical-s").attr("checked",true);
                              $("#haveTechnical-s").attr("disabled",true);
                            }
                            return data;
                        } else {
                            nullData(data);
                        }
                        return data;
                    }
                }
            );
        });

        function detail(row, index){
            var status = row.applyStatus;
            var opts = "<a href='javascript:showDetail(" + index + ",1)'>查看 </a>  ";
            if(${contractUpdateAuth}){
               opts += "<a href='javascript:showDetail(" + index + ",2)'> | 修改</a>  ";
            }
            return opts;
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

        // 获取项目经营部门
        getBasic(7, createManagementDepartment);
        function createManagementDepartment(datas) {
            var boxS = $("#managementDepartment"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"' type='"+ datas[i].abbreviate+"'>"+ datas[i].attributeName +"</option>";
            }
            boxS.empty();
            boxS.append(dom);
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
            boxS.empty();
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
            boxS.empty();
            boxS.append(dom);
        }

        // 获取项目类别
        getBasic(5, createCategory);
        function createCategory(datas) {
            var boxS = $("#category-s"),
                box = $("#category"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"' type='"+datas[i].abbreviate+"'>"+ datas[i].attributeName +"</option>";
            }
           // boxS.empty();
            boxS.append(dom);
            box.append(dom);
        }

        // 获取项目类型
        getBasic(6, createType);
        function createType(datas) {
            var boxS = $("#type-s"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
            boxS.empty();
            boxS.append(dom);
        }

        // 公司属性
        //getBasic(3, companyType);
        function companyType(datas) {
            var box = $("#contractAwardCompany"),
                boxS = $("#contractAwardCompany-s"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].companyName +"</option>";
            }
            box.append(dom);
            //boxS.empty();
            boxS.append(dom);
        }

        // 付款方式
        var count=0;
        paymentMode(count);
        function paymentMode(count,mode) {
           $.ajax({
                url: "/company/user/getBasic.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    categoryId : 11,
                    rows:1000
                },
                success: function (data) {
                    if (data.success) {
                        var boxS = $("#paymentMode"+count),
                            dom = "";
                        for (var i = 0; i < data.rows.length; i++) {
                            if(mode==data.rows[i].id){
                             dom += "<option value='"+ data.rows[i].id +"' selected>"+ data.rows[i].attributeName +"</option>";
                            }else{
                             dom += "<option value='"+ data.rows[i].id +"'>"+ data.rows[i].attributeName +"</option>";
                            }

                    }
                         boxS.empty();
                         boxS.append(dom);
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
            });

        }

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
                        $("#contractAwardCompany-s").combobox({    
	                     url:'/companyAssocited/user/getCompanyAssociatedInfoData.htm?companyDistinguish='+datas[i].id+'&rows=1000000',   
	                     valueField:'value',    
	                     textField:'text',
	                     filter: function(q, row){ 
                         var opts = $(this).combobox('options'); 
                         //return row[opts.textField].indexOf(q) == 0; 
                         return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配  
                         } 
	                   });  
	                   
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

        // 获取技术负责人
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
        /* function createStaffPerson(datas) {
            var box = $("#projectLeader"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].userInfo.id +"'>"+ datas[i].userInfo.name +"</option>";
            }
            box.append(dom);
        } */
        // 获取项目信息
        function getProjectContent() {
            $.ajax({
                url: "/project/user/getGardenProjectNoContract.htm",
                type: 'post',
                dataType: 'json',
                data: {},
                success: function (data) {
                    if (data.success) {
                        createStaffPerson1(data.data);
                    }
                }
            });
        }
        function createStaffPerson1(datas) {
            var box = $("#projectId-s"),
                dom = "<option value='-1'> </option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"' type='"+datas[i].categoryAbbreviate+""+datas[i].managementDepartmentAbbreviate+"'>"+ datas[i].name +"</option>";
            }
            box.empty();
            box.append(dom);
        }

        // 获取经营专员
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
            var box = $("#managementPersonnel"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].userId +"'>"+ datas[i].userInfo.name +"</option>";
            }
            box.append(dom);
        }



        var obj = {};
        var proId;
        function showDetail(index,type) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '合同信息',
                width: windowW+5,
                height: windowH+5,
                closable: false,
                onBeforeOpen: function () { 
                    if (index == undefined) {
                      $("#signTable").hide();
                    getProjectContent();
                        flag=1;                        
                        getMaxNum(1);
                        $("#cashierTr").hide();
                        $("#packageTr").hide();
                        $("#ratioTr").hide();
                        $("#ratioMemoTr").hide();
                        $("#projectId-s").attr("disabled",false);
                        $("#projectId").attr("disabled",true);
                        $("#paymentButton").show();
                        $("#paymentButtonReduce").show();
                        $("#contractAwardCompany-s").attr("disabled",false);
                        $("#name-s").attr("disabled",false);
                        $("#designArea").attr("disabled",false);
                        $("#category-s").attr("disabled",false);
                        $("#type-s").attr("disabled",false);
                        $("#investmentMount").attr("disabled",false);
                        $(".area-btn").attr("disabled",false);
                        $("#address").attr("disabled",false);
                        $("#haveScheme").attr("disabled",false);
                        $("#haveDevelopment").attr("disabled",false);
                        $("#haveDrawing").attr("disabled",false);
                        $("#havePlanning").attr("disabled",false);
                        $("#havePlanning").attr("disabled",false);
                        $("#havePlanning").attr("disabled",false);
                        $("#paymentAmount0").attr("disabled",false);
                        $("#paymentMode0").attr("disabled",false);
                        $("#paymentMode0").attr('disabled',false);
                        $("#paymentAmount0").attr('readonly',false);
                        //查询此项目有无修改的未审核记录，即临时数据是否存在
                    } else {
                        $("#signTable").show();
                        if(type==1){
                          $("#submit").hide();
                          $("#paymentButton").hide();
                          $("#paymentButtonReduce").hide();
                          $("#contractCapital").attr("readonly",true);
                          $("#contractMemo").attr("readonly",true);
                          $("#signDate").datebox({ disabled: true });
                           if (list) setInfo(list,1);

                         $("#paymentMode0").attr('disabled',true);
                         $("#paymentAmount0").attr('readonly',true);
                        }else{
                          $("#submit").show();
                          $("#paymentButton").show();
                          $("#paymentButtonReduce").show();
                           if (list) setInfo(list,2);
                        $("#paymentMode0").attr('disabled',false);
                        $("#paymentAmount0").attr('readonly',false);
                        }
                        $("#cashierTr").show();
                        $("#packageTr").show();
                        $("#ratioTr").show();
                        $("#ratioMemoTr").show();
                        //项目内容设为只读
                        $("#contractAwardCompany-s").attr("disabled",true);
                        $("#name-s").attr("readonly",true);
                        $("#designArea").attr("readonly",true);
                        $("#category-s").attr("disabled",true);
                        $("#type-s").attr("disabled",true);
                        $("#investmentMount").attr("readonly",true);
                        $(".area-btn").attr("disabled",true);
                        $("#address").attr("readonly",true);
                        $("#haveScheme").attr("disabled",true);
                        $("#haveDevelopment").attr("disabled",true);
                        $("#haveDrawing").attr("disabled",true);
                        $("#havePlanning").attr("disabled",true);
                       // getMaxNum(0);
                        flag=0;
                        $("#projectId-s").attr("disabled","disabled");
                        $("#projectId").attr("disabled",false);
                        if (list) getUpdateDate(list);
                    }
                },
                buttons: [
                    {
                        id:'submit',
                        iconCls: 'icons-true',
                        text: '提交',
                         handler: function () {
                            if ($("#haveScheme").attr("checked")) {
                                $("#haveScheme").val("1");
                            } else {
                                $("#haveScheme").val("0");
                            }
                            if ($("#haveDevelopment").attr("checked")) {
                                $("#haveDevelopment").val("1");
                            } else {
                                $("#haveDevelopment").val("0");
                            }
                            if ($("#haveDrawing").attr("checked")) {
                                $("#haveDrawing").val("1");
                            } else {
                                $("#haveDrawing").val("0");
                            }
                            if ($("#havePlanning").attr("checked")) {
                                $("#havePlanning").val("1");
                            } else {
                                $("#havePlanning").val("0");
                            }
                            if (!$("#contractNo-s").val()) {
                                $.messager.alert("提示", "请选择所属项目", "info");
                            } else if (!$("#contractCapital").val()) {
                                $.messager.alert("提示", "合同金额必须大于0", "info");
                            } else if (!$("#signDate").datebox('getValue')) {
                                $.messager.alert("提示", "请选择签约日期", "info");
                            } else if($("#haveScheme").val()==0 && $("#haveDevelopment").val()==0 && $("#haveDrawing").val()==0 && $("#havePlanning").val()==0){
                                $.messager.alert("错误", "请至少选择一个项目设计阶段", "error");
                            } else {
                                if (index != undefined) {
                                    // 更新
                                    checkData(list);
                                    setTimeout(function () {                                  
                                    if(checkMoney()){
                                    $("#form1").attr("action", "/contract/user/updateContract.htm");
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
                                                $("#form1")[0].reset();
                                                $(".area-btn").html("请选择区域");
                                                $("#form1 tr").each(function () {
                                                	if($(this).data("remove")) {
    		                            	            if ($(this).attr("id").indexOf("paymentModeTr") >= 0) {
    		                            		            $(this).remove();
    		                            	            }
    	                            	            }
    	                                        });
                                                $("#detailInfo").dialog("close");
                                                $("#form1")[0].reset();
                                                $("#form1 :input[type='hidden']").val("");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","修改成功!","info");
                                                count=0;
                                                $("#detailInfo").dialog("close");
                                                $("#haveScheme").attr("checked", false);
                                                $("#haveDevelopment").attr("checked", false);
                                                $("#haveDrawing").attr("checked", false);
                                                $("#havePlanning").attr("checked", false);
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        }
                                    });
                                    }
                                    },100);                                    
                                } else {
                                    // 申请
                                    checkProjectData();
                                    setTimeout(function () {
                                       if(checkMoney()){
                                       if($("#projectUpdate").val()==1 && ($("#temporaryId").val()!=null && $("#temporaryId").val()!="")){
                                          $.messager.confirm("修改项目", "点击申请后之前未审核的项目修改将会被放弃,是否确认修改？", function (r) {
                                          if (r) {
                                             $("#form1").attr("action", "/contract/user/insertContract.htm");
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
                                                $("#form1")[0].reset();
                                                $("#provCode").val("");
                                                $("#cityCode").val("");
                                                $("#distCode").val("");
                                                $(".area-btn").html("请选择区域");
                                                $("#form1 tr").each(function () {
                                                	if($(this).data("remove")) {
    		                            	            if ($(this).attr("id").indexOf("paymentModeTr") >= 0) {
    		                            		            $(this).remove();
    		                            	            }
    	                            	            }
    	                                        });
                                                $("#detailInfo").dialog("close");
                                                $("#form1")[0].reset();
                                                $.messager.progress("close");
                                                $.messager.alert("信息","添加成功!","info");
                                                count=0;
                                                $("#detailInfo").dialog("close");
                                                $("#haveScheme").attr("checked", false);
                                                $("#haveDevelopment").attr("checked", false);
                                                $("#haveDrawing").attr("checked", false);
                                                $("#havePlanning").attr("checked", false);
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        }
                                     });
                                          }
                                         });
                                       }else{
                                             $("#form1").attr("action", "/contract/user/insertContract.htm");
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
                                                $("#form1")[0].reset();
                                                $("#provCode").val("");
                                                $("#cityCode").val("");
                                                $("#distCode").val("");
                                                $(".area-btn").html("请选择区域");
                                                $("#form1 tr").each(function () {
                                                	if($(this).data("remove")) {
    		                            	            if ($(this).attr("id").indexOf("paymentModeTr") >= 0) {
    		                            		            $(this).remove();
    		                            	            }
    	                            	            }
    	                                        });
                                                $("#detailInfo").dialog("close");
                                                $.messager.progress("close");
                                                $.messager.alert("信息","添加成功!","info");
                                                count=0;
                                                $("#haveScheme").attr("checked", false);
                                                $("#haveDevelopment").attr("checked", false);
                                                $("#haveDrawing").attr("checked", false);
                                                $("#havePlanning").attr("checked", false);
                                                queryProduct();
                                            } else {
                                                $.messager.progress("close");
                                                $.messager.alert("错误",list.resultMsg,"error");
                                            }
                                        }
                                     });
                                       }
                                    }
						           },1000); 
                                }
                            }
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                             $("#form1")[0].reset();
                              $(".area-btn").html("请选择区域");
                              $("#provCode").val("");
                              $("#cityCode").val("");
                              $("#distCode").val("");
                              $("#form1 tr").each(function () {
                                  if($(this).data("remove")) {
		                           if ($(this).attr("id").indexOf("paymentModeTr") >= 0) {
		                            	$(this).remove();
		                            }
	                              }
	                          });
                            $("#detailInfo").dialog("close");
                            $("#haveScheme").attr("checked", false);
                            $("#haveDevelopment").attr("checked", false);
                            $("#haveDrawing").attr("checked", false);
                            $("#havePlanning").attr("checked", false);
                            $("#contractCapital").attr("readonly",false);
                            $("#contractMemo").attr("readonly",false);
                            $("#address").attr("readonly",false);
                            $("#signDate").datebox({ disabled: false });
                            $("#id").val("");
                            count=0;
                        }
                    }
                ]
            });
        }

        // 获取项目最大编号
        function getMaxNum(type) {
            var contractNo ="CB"+getDay(new Date(), "yyyy").substring(2,4)+"-",
                category =  $("#category-s option:selected").attr("type");
                if(type==0){
                  var project =  $("#managementDepartment option:selected").attr("type");;
                  contractNo+= category+project;
                    $.ajax({
                     url: "/contract/user/contractMaxNo.htm",
                     type: 'post',
                     dataType: 'json',
                     data: {
                       contractNo: contractNo
                     },
                     success: function (data) {
                     if (data.success) {
                        $("#contractNo-s").val(data.data);
                     } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                     }
                    }
               });
                }else{
                  var projects = $("#projectId-s option:selected").attr("type");
                  if(projects!=null & projects!=""){
                    contractNo+= category+projects.substring(1,2);
                    $.ajax({
                     url: "/contract/user/contractMaxNo.htm",
                     type: 'post',
                     dataType: 'json',
                     data: {
                       contractNo: contractNo
                     },
                     success: function (data) {
                     if (data.success) {
                        $("#contractNo-s").val(data.data);
                     } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                     }
                    }
               });
                  }
                }
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
                                $("#city").val($(".area").val());
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
        function setInfo(list,lock) {
          $.ajax({
                url: "/contract/user/contractList.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    id: list.id,
                    status:1
                },
                success: function (data) {
                    if (data.success) {
                    list = data.rows[0];
            $("#signDate").datebox('setValue', zjzm.formatDate(list.signDate, '/'));
            $("#contractAwardCompany-s").combobox("setValue",list.project.contractAwardCompany);
            $("#name-s").val(list.project.name);
            $("#contractCapital").val(list.contractCapital);
            $("#managementDepartment").val(list.project.managementDepartment);
            $("#designArea").val(list.project.designArea);
            $("#category-s").val(list.project.category);
            $("#type-s").val(list.project.type);
            $("#projectId-s").html("<option value='"+list.project.id+"' selected>"+list.project.name+"</option>");
            $("#projectId").val(list.project.id);
            $("#investmentMount").val(list.project.investmentMount);
            $(".area-btn").html(list.area.areaName);
            $("#city").val(list.project.city);
            $("#address").val(list.project.address);
            $("#contractMemo").val(list.contractMemo);
            if(list.ratio!=0){
               $("#ratio-s").val(list.ratio);
            }   
            $("#ratioMemo").val(list.ratioMemo);
            $("#contractVersion").val(list.version);
             $("#contractNo-s").val(list.contractNo);
            if (list.project.haveScheme) $("#haveScheme").attr("checked", true);
            if (list.project.haveDevelopment) $("#haveDevelopment").attr("checked", true);
            if (list.project.haveDrawing) $("#haveDrawing").attr("checked", true);
            if (list.project.havePlanning) $("#havePlanning").attr("checked", true);
            getContractFile(list.projectId);
            var payment = list.contractPayment;
            for(var j=0;j<payment.length;j++){
                if(j>0){
                  addPayMentMode(payment[j].paymentMode,lock);                
                  $("#paymentAmount"+j).val(payment[j].paymentAmount);
                  
                  oldArr.push(payment[j].paymentMode.toString()+payment[j].paymentAmount.toString());
                }else{           
                  $("#paymentMode"+j).val(payment[j].paymentMode);
                  $("#paymentAmount"+j).val(payment[j].paymentAmount);
                  oldArr.push(payment[j].paymentMode.toString()+payment[j].paymentAmount.toString());
                }
            }
            // 创建分包信息
            var users = list.contractSubpackage;
            var box = $("#package-list tbody");
            var dom = "";
            contractSubpackage=list.contractSubpackage;
            for (var i = 0; i < users.length; i++) {
                 
                dom += "<tr>"+
                    "<td width='25%'>"+ users[i].subpackageLeaderName +"</td>"+
                    "<td width='25%'>"+ users[i].subpackageCapital +"(万元)</td>"+
                    "<td width='25%'>"+ ((users[i].subpackageCapital/list.contractCapital)*100).toFixed(2) +"%</td>"+
                    "<td width='25%'><a href='javascript:getData("+i+")'>详细</a></td>"+
                "</tr>";
            }
            box.empty();
            box.append(dom);
            
            // 创建开票信息
            var user = list.financialAudit;
            var boxs = $("#cashier-list tbody");
            var doms = "";
            for (var i = 0; i < user.length; i++) {
                doms += "<tr>";
                doms += "<td width='12%'>"+ user[i].financialPaymentModeName +"</td>";
                doms += "<td width='12%'>"+ user[i].amount +"</td>";
                if(!(user[i].createTime==null || user[i].createTime=="")){
                   doms += "<td width='12%'>"+ getDay(user[i].createTime.time, "yyyy/MM/dd")+"</td>";
                   doms += "<td width='12%'>申请</td>";
                }else{
                   doms +="<td width='12%'></td>";
                   doms +="<td width='12%'></td>"; 
                }
                if(!(user[i].cashierCreateTime==null || user[i].cashierCreateTime=="")){
                	doms += "<td width='12%'>"+ getDay(user[i].cashierCreateTime.time, "yyyy/MM/dd")+"</td>";
                	doms += "<td width='12%'>开票</td>";
                }else{
                   doms +="<td width='12%'></td>";
                   doms +="<td width='12%'></td>"; 
                }
                if(!(user[i].cashierUpdateTime==null || user[i].cashierUpdateTime=="")){
                   doms +="<td width='12%'>"+ getDay(user[i].cashierUpdateTime.time, "yyyy/MM/dd")+"</td>";
                   doms +="<td width='12%'>到账</td>";
                }else{
                   doms +="<td width='12%'></td>";
                   doms +="<td width='12%'></td>"; 
                }                            
                doms +="</tr>";
            }
            boxs.empty();
            boxs.append(doms);

                    }
                }
            });
        }                     

        function queryProduct(){
            var param = {};
            param.contractNo = $('#contractNo').val();
            param.projectName = $('#projectName').val();
             if ($('#contractAwardCompany').combobox("getValue") != "-1") {
                param.contractAwardCompany = $('#contractAwardCompany').combobox("getValue");
            }
            param.areaId = $('#areaId').val();
            if ($("#projectLeader").combobox("getValue")!= "-1") {
                param.projectLeader = $("#projectLeader").combobox("getValue");
            }
            param.projectNo = $('#projectNo').val();
            if ($('#managementPersonnel').val() != "-1") {
                param.managePerson = $('#managementPersonnel').val();
            }
            if ($('#category').val() != "-1") {
                param.category = $('#category').val();
            }
            if ($('#attribute').val() != "-1") {
                param.nature = $('#attribute').val();
            }
            if ($('#attribute').val() != "-1") {
                param.nature = $('#attribute').val();
            }
            param.createTimeStart= $('#createTimeStart').datebox('getValue');
            param.createTimeEnd= $('#createTimeEnd').datebox('getValue');
            param.investmentMountStart = $('#investmentMountStart').val();
            param.investmentMountEnd = $('#investmentMountEnd').val();
            param.ratioFrom = $('#ratioFrom').val();
            param.ratioTo= $('#ratioTo').val();
            param.designAreaFrom = $('#designAreaFrom').val();
            param.designAreaTo= $('#designAreaTo').val();
            param.contractCapitalFrom = $('#contractCapitalFrom').val();
            param.contractCapitalTo= $('#contractCapitalTo').val();
            param.projectLiable= $('#projectLiable').val();
            if($('#status').val()!=-1){
              param.status = $('#status').val();
            }
            
            if($("#haveTechnical-s").attr("checked")) {
              param.haveTechnical = 1;
            }
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
            if(!($("#haveTechnical-s").attr("disabled")=="disabled")){
              $("#haveTechnical-s").removeAttr("checked");
            }
        }

        function addPayMentMode(mode,type){
           var box = $("#paymentModeTr"+count);
           count++;
           if(type==1){
            var dom = "<tr data-remove='true' id='paymentModeTr"+count+"'><th></th><td><select  disabled  name='contractPayment["+count+"].paymentMode' id='paymentMode"+count+"'></select></td><td><input type='text' readonly id='paymentAmount"+count+"' name='contractPayment["+count+"].paymentAmount' class='input'/>";
               dom +="</td></tr>";
           }else{
            var dom = "<tr data-remove='true' id='paymentModeTr"+count+"'><th></th><td><select name='contractPayment["+count+"].paymentMode' id='paymentMode"+count+"'></select></td><td><input type='text' id='paymentAmount"+count+"' name='contractPayment["+count+"].paymentAmount' class='input'/>";
               dom +="</td></tr>";
           }

               box.after(dom);
               paymentMode(count,mode);

        }

        function reducePayMentMode(mode,type){
          if(count!=0){
             var box = $("#paymentModeTr"+count);
             count--;
             box.remove();
          }
        }
        $("#projectId-s").on("change",function(){
          $.ajax({
                url: "/project/user/list.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  id : $(this).val()
                },
                success: function (data) {
                    if (data.success) {
                         $("#name-s").val(data.rows[0].name);
                         $("#designArea").val(data.rows[0].designArea);
                         $("#category-s").val(data.rows[0].category);
                         $("#type-s").val(data.rows[0].type);
                         $("#investmentMount").val(data.rows[0].investmentMount);
                         $("#managementDepartment").val(data.rows[0].managementDepartment);
                         $("#city").val(data.rows[0].city);
                         getDetailAddress(data.rows[0].city);
                         $("#address").val(data.rows[0].address);
                         $("#contractAwardCompany-s").combobox("setValue",data.rows[0].contractAwardCompany);
                         if (data.rows[0].haveScheme){
                            $("#haveScheme").attr("checked", true);
                         }else{
                            $("#haveScheme").attr("checked", false);
                         } 
                         if (data.rows[0].haveDevelopment){
                            $("#haveDevelopment").attr("checked", true);
                         }else{
                            $("#haveDevelopment").attr("checked", false);
                         } 
                         if (data.rows[0].haveDrawing){
                            $("#haveDrawing").attr("checked", true);
                         }else{
                            $("#haveDrawing").attr("checked", false);
                         } 
                         if (data.rows[0].havePlanning){
                            $("#havePlanning").attr("checked", true);
                         }else{
                            $("#havePlanning").attr("checked", false);
                         }
                         $("#version").val(data.rows[0].version);
                         getMaxNum(0);
                         //查询选择的项目有无临时数据并未审核
                         $.ajax({
                           url: "/project/user/getGardenProjectByIdAndStatus.htm",
                           type: 'post',
                           dataType: 'json',
                           data: {
                             projectNo: data.rows[0].projectNo,
                             status:3
                           },
                           success: function (data) {
                              if (data.success) {
                                 if(data.rows.ratio){
                                    var userId = data.rows.createUserId;
                                	var nowUserId = ${sec:userId()};
		                                if(userId!=nowUserId){
		                                  $.messager.alert("提示","所选项目已被他人修改并且未经审核，请审核后继续操作！","info");
		                                  $("#submit").hide();
		                                }else{
		                                  $("#temporaryId").val(data.rows.id);
		                                  $("#submit").show();
		                                }
	                                 }else{
		                                  $.messager.alert("提示","所选项目正在审核合作比例，请审核后继续操作！","info");
		                                  $("#submit").hide();		                                
	                                 }                                
                              }else{
                                  $("#submit").show();
                              }
                           }
                        });
                    }
                }
            });
        });
        $("#category-s").on("change",function(){
          getMaxNum(flag);
        });

        function getData(count){
            var dom = "";
            var box = $("#paymentTr");

            $("#leader").val(contractSubpackage[count].subpackageLeaderName);
            $("#subpackageCapital").val(contractSubpackage[count].subpackageCapital);
            $("#subpackageContent").val(contractSubpackage[count].subpackageContent);
            for(var i=0;i<contractSubpackage[count].subpackagePayment.length;i++){
              if(i==0){
               dom +="<tr name='deleteTr'><th>付款方式(万元)</th><td><input value='"+contractSubpackage[count].subpackagePayment[i].paymentModeName+"'></td><td><input value='"+contractSubpackage[count].subpackagePayment[i].paymentAmount+"'></td></tr>";
              }else{
               dom +="<tr name='deleteTr'><th></th><td><input value='"+contractSubpackage[count].subpackagePayment[i].paymentModeName+"'></td><td><input value='"+contractSubpackage[count].subpackagePayment[i].paymentAmount+"'></td></tr>";
              }
            }
            if(dom==""){
            }else{
               box.after(dom);
            }

            $("#subpackageMemo").val(contractSubpackage[count].subpackageMemo);
            if(contractSubpackage[count].status==0){
              $("#packageStatus").val("未审核");
            }else if(contractSubpackage[count].status==1){
              $("#packageStatus").val("审核通过");
            }else if(contractSubpackage[count].status==2){
              $("#packageStatus").val("审核未通过");
            }
            $("#package").show();
            $("#package").dialog({
                title: '分包信息',
                width: 650,
                height: 450,
                closable: false,
                onBeforeOpen: function () {

                },
                buttons: [

                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#package tr[name='deleteTr']").remove();
                            $("#package").dialog("close");
                        }
                    }
                ]
            });

        }

        function getUpdateDate(list){
           $.ajax({
                url: "/contract/user/contractList.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  projectId : list.project.id,
                  status : 0
                },
                success: function (data) {
                    if (data.success) {
                       var userId = data.rows[0].createUserId;
                       var nowUserId = ${sec:userId()};
                       if(userId!=nowUserId){
                          $.messager.alert("提示","合同已被他人修改并且未经审核，请审核后继续修改！","info");
                          $("#apply").hide();
                       }else{
                          $("#temporaryId").val(data.rows[0].id);
                       }
                    }else{

                    }
                }
            });
        }
        function checkData(list){
          if($("#contractCapital").val()!=list.contractCapital){
              $("#contractUpdate").val(1);
              return;
          }else if($("#signDate").datebox('getValue')!=zjzm.formatDate(list.signDate, '/')){         
              $("#contractUpdate").val(1);
              return;
          }else if($("#contractMemo").val()!=list.contractMemo){
              $("#contractUpdate").val(1);
              return;
          }
        }

        function checkProjectData(){
          $.ajax({
                url: "/project/user/list.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  id : $("#projectId-s").val()
                },
                success: function (data) {
                    if (data.success) {
                    if($("#name-s").val()!=data.rows[0].name){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#designArea").val()!=data.rows[0].designArea){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#category-s").val()!=data.rows[0].category){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#type-s").val()!=data.rows[0].type){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#investmentMount").val()!=data.rows[0].investmentMount){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#managementDepartment").val()!=data.rows[0].managementDepartment){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#city").val()!=data.rows[0].city){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#address").val()!=data.rows[0].address){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#contractAwardCompany-s").combobox('getValue')!=data.rows[0].contractAwardCompany){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#haveScheme").val()!=data.rows[0].haveScheme){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#haveDevelopment").val()!=data.rows[0].haveDevelopment){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#haveDrawing").val()!=data.rows[0].haveDrawing){
                        $("#projectUpdate").val(1);
                        return;
                    }else if($("#havePlanning").val()!=data.rows[0].havePlanning){
                        $("#projectUpdate").val(1);
                        return;
                    }
                    }
                }
            });
        }
        function checkMoney(){
          var money = 0;
          var arr=[];
          var r1;
          try{r1=$("#contractCapital").val().toString().split(".")[1].length}catch(e){r1=0};  
          for(var i=0;i<=count;i++){
            if($("#paymentAmount"+i).val()!=null && $("#paymentAmount"+i).val()!=""){
              if($("#paymentAmount"+i).val()!=0){
                money = Number(money)+Number($("#paymentAmount"+i).val());
              }else{
                $.messager.alert("提示", "付款金额不能为0！", "info");
                return false;
              }
            }else{
              $.messager.alert("提示", "请输入付款金额！", "info");
              return false;
            }
            if(arr.indexOf($("#paymentMode"+i).val())!=-1){
              $.messager.alert("提示", "已有相同付款方式,请重新选择！", "info");
              return false;
            }else{
              arr.push($("#paymentMode"+i).val());
            }
            if(oldArr.indexOf($("#paymentMode"+i).val()+$("#paymentAmount"+i).val())==-1){
               $("#paymentUpdate").val(1);
            }
            if(count!=oldArr.length){
               $("#paymentUpdate").val(1);
            }
          }
          if(money.toFixed(r1)!=$("#contractCapital").val()){
            $.messager.alert("提示", "付款方式总金额必须与合同金额一致！", "info");
            return false;
          }else{
            return true;
          }
        }
        function formatCurrency1(num) {
	      num = num.toString().replace(/\$|\,/g, '');
	      if (isNaN(num))
		     num = "0";
	      sign = (num == (num = Math.abs(num)));
	      num = Math.floor(num * 100 + 0.50000000001);
	      cents = num % 100;
	      num = Math.floor(num / 100).toString();
	      if (cents < 10)
		     cents = "0" + cents;
	      for (var i = 0; i < Math.floor((num.length - (1 + i)) / 3); i++)
		     num = num.substring(0, num.length - (4 * i + 3)) + ',' + num.substring(num.length - (4 * i + 3));
	         return (((sign) ? '' : '-') + num);
          }
          
          function accAdd(arg1,arg2){  
            var r1,r2,m;  
            try{r1=arg1.toString().split(".")[1].length}catch(e){r1=0};  
            try{r2=arg2.toString().split(".")[1].length}catch(e){r2=0};  
            m=Math.pow(10,Math.max(r1,r2));  
            return (arg1*m+arg2*m)/m ; 
          }
          
           function downLoad(){
	          $("#form").form("submit", {
		        url:"/contract/user/downLoadContract.htm",
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
      function setPicture(picture) {
	  $("#signTable input[name='historyPicture']").val(picture);
	  var data = picture;
	  var picBox = $("#address .pic-box");
	  var btnBox = $("#address .add-box");
	  picBox.empty();
	  btnBox.empty();
		
		var pic = '<div class = "add-pic" style="float: left; margin-right: 10px; border: 1px solid #ddd; padding: 5px; border-radius: 3px; position: relative; width: 100px; height: 100px;">'
				+ '<img style="position: absolute; width: 100px; height: 100px; border: 0; z-index: 0;" onclick="doShowImgEffect(this.src)" src='
				+ data + ' ></div>';
        picBox.append(pic);
    }
    function getContractFile(projectId){
    	 $.ajax({
                url: "/fileManage/user/getFileManageList.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  projectId : projectId,
                  fileId:10,
                  basicId:118,
                  rows:1000000,
                  fileIds:"1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,23,24,26,27,28,29,30,31,"
                },
                success: function (data) {
                    if (data.success) {
                    var dom = "<th>合同资料</th>";
                    var box = $("#signTable");
                    	dom +=  "<td colspan=5 id='picaddress' name='address'><div class='pic-box'></div></td><td><input type='hidden' id='historyPicture' name='historyPicture'></td>";					  
                        box.empty();
                        box.append(dom);
                        for(var i=0 ; i<data.rows.length;i++){
                    	 
                          showImg("",data.rows[i].id,data.rows[i].fileAddress);  
                        }
                      
                    }
                }
            });
    } 	
    
 function showImg(img,fileNameId,address){
	if(img){
	  var image=eval('(' + img + ')');
	}else{
	  var image="";
	}
	var picBox = $("#picaddress .pic-box");
	//var btnBox = $("#address .add-box");
	var historyPictureBefore = $("#signTable input[name='historyPicture']").val();
	if(isEmpty(historyPictureBefore)){
	    if(image){
	    	$("#signTable input[name='historyPicture']").val(image.imgUrl);
	    }else{
	    	$("#signTable input[name='historyPicture']").val(address);
	    }
		
	 } else{
	    if(image){
	    	$("#signTable input[name='historyPicture']").val(historyPictureBefore + "," + image.imgUrl);
	    }else{
	    	$("#signTable input[name='historyPicture']").val(historyPictureBefore + "," + address);
	    }	
	} 
	var historyPictureAfter = $("#signTable input[name='historyPicture']").val();
	var data = historyPictureAfter.split(',');
	picBox.empty();
	var picture = "<ul class='imgZoom'>";
	//picBox.append(picture);
	//btnBox.append(addBtn);
	if (data.length > 0) {
		/*var picture = '<div style="float: left; margin-right: 10px; border: 1px solid #ddd; padding: 5px; border-radius: 3px; position: relative; width: 100px; height: 100px;">'
				+ '<img style="position: absolute; width: 100px; height: 100px; border: 0; z-index: 0;" onclick="doShowImgEffect(this.src)" src='
				+ historyPicture + ' ></div>';*/
		for(var i=0; i<data.length; i++){
			picture += "<li style='float: left; margin: 5px;'><img style='width:100px;height:100px;' data-original='"+ data[i] +"' src='"+ data[i] +"'/></li>";
		}
	    //图片放大
	    setTimeout(function () {
	    	$('#signTable').find(".imgZoom").viewer();
	    },);
		//btnBox.find("div").hide();
		picBox.append(picture);
	}
	$.messager.progress("close");
	picBox.append("</ul>");
	
} 
    </script>
</body>
<jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>