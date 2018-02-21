
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
    <style type="text/css">
        .diolag-table select {
            width: 100%;
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
    						<th>项目编号</th>
    						<td>
                                <input type="text" id="projectNo" name="projectNo" class="input">
                            </td>
    						<th>技术负责人</th>
                            <td id="projectLeaderTd">
                                <input id="projectLeader" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td>
                            <th>发包单位</th>
                            <td id="contractAwardCompanyTd">
                                <input name="contractAwardCompany" id="contractAwardCompany" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="actiDatetime" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="actiDatetimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>所在城市</th>
                            <td>
                                <input type="text" class="input" id="cityId" />
                            </td>
                            <th>项目名称</th>
                            <td>                              
                               <input type="text" id="name" name="name" class="input">
                            </td>
                            <th>
                                经营部门
                            </th>
                            <td>
                                <select class="select" id="department">
                                    <option value="-1"></option>
                                </select>
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
                            <th>项目阶段</th>
                            <td>
                                <select class="select" id="step">
                                    <option value="-1"></option>
                                    <c:forEach var="type" items="${e:gardenProjectStepEnum()}">
                                        <option value="${type.value}" >${type.message}</option>
                                    </c:forEach>
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
                            <th>设计面积(㎡)</th>
                            <td>
                                <input type="text" class="input half-input" id="designAreaStart" name="designAreaStart" />
                                <span style="float: left;">~</span>
                                <input type="text" class="input half-input" id="designAreaEnd" name="designAreaEnd" />
                            </td>
                        </tr>
                        <tr>
                        <th>项目类别</th>
                            <td>
                                <select class="select" id="category" name="category">
                                    <option value="-1"></option>
                                </select>
                            </td>
                            <th>状态</th>
                            <td>
                                <select class="select" id="status">
                                    <option value="-1"></option>
                                    <c:forEach var="type" items="${e:gardenProjectStatusEnum()}">
                                        <option value="${type.value}" >${type.message}</option>
                                    </c:forEach>
                                </select>
                            </td>
                            <th>
                              <input type="checkbox" name="haveTechnical" id="haveTechnical-s"/>
                            </th>
                            <td>只看含技术标</td>
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
                        <th>项目编号</th>
                        <td>
                            <input type="text" name="projectNo" id="projectNo-s" class="input" readonly />
                        </td>
                        <th>项目介绍人</th>
                        <td id="introducerTd">
                           <input name="introducer" id="introducer-s" class="easyui-combobox" style="height:30px;width:220%;" panelHeight="210">
                        </td>
                         <th>设计面积(㎡)</th>
                        <td>
                            <input type="text" name="designArea" id="designArea" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>投资额(万元)</th>
                        <td>
                            <input type="text" name="investmentMount" id="investmentMount" class="input"  />
                        </td>
                        <th>项目名称</th>
                        <td>
                            <input type="text" name="name" id="name-s" class="input"  />
                        </td>
                         <th>项目性质</th>
                        <td>
                            <select name="nature" id="attribute-s">
                              <option value="-1"></option>
                            </select>
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
                            <select name="type" id="type-s">
                              <option value="-1"></option>
                            </select>
                        </td>
                         <th>经营部门</th>
                        <td>
                            <select name="managementDepartment" id="department-s">
                             <option value="-1"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>所在城市</th>
                        <td class="area-td">
                            <p class="area-btn">请选择区域</p>
                        </td>
                        <td colspan="2">
                            <input type="text" name="address" placeholder="请输入详细地址" id="address" class="input"  />
                        </td>
                        <th>项目负责人</th>
                        <td>
                            <input type="text" name="projectLiable" id="projectLiable" class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>项目介绍</th>
                        <td colspan="5">
                            <textarea id="introduction" name="introduction"></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>是否技术支持</th>
                        <td>
                            <label>
                                是
                                <input type="radio" name="needTechnical" value="1" checked />
                            </label>
                            <label>
                                否
                                <input type="radio" name="needTechnical" value="0" />
                            </label>
                        </td>
                        <th>发包单位</th>
                        <td id="contractAwardCompany-sTd">
                            <input name="contractAwardCompany" id="contractAwardCompany-s" class="easyui-combobox" style="height:30px;width:220%;" panelHeight="210">
                        </td>
                        <th>意向合作方式</th>
                        <td>
                            <select name="intentionalCooperation" id="intentionalCooperation">
                              <option value="-1"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>前期技术支持要求</th>
                        <td colspan="5">
                            <textarea id="preRequirements" name="preRequirements"></textarea>
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
                </tbody>
            </table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="city" name='city' />
            <input type="hidden" id="step-s" name='step' />
            <input type="hidden" id="isUpdate" name='isUpdate'/>
            <input type="hidden" id="version" name='version'/>
            <input type="hidden" id="temporaryId" name='temporaryId'/>
            <input type="hidden" id="operation" name='operation'/>
            <input type="hidden" id="provCode" />
            <input type="hidden" id="cityCode" />
            <input type="hidden" id="distCode" />
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
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/project/user/list.htm',
                    title:'项目信息',
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
                       isAllData:1
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                        {
                            field : 'projectNo',
                            title : '项目编号',
                            width : 60,
                            align :'center'
                        },
                        {
                            field : 'name',
                            title : '项目名称',
                            width : 130,
                            align:'center'
                        },
                        {
                            field : 'managementPersonnel',
                            title : '经营专员',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.manager!=null && row.manager!=""){
                                    return row.manager.name;
                                }else{
                                    return "(未确立)";
                                }
                            }
                        },
                        {
                            field : 'projectLeader',
                            title : '负责人',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                                if(row.leader!=null && row.leader!=""){
                                    return row.leader.name;
                                }else{
                                    return "(未确立)";
                                }
                            }
                        },
                        {
                            field : 'managementDepartment',
                            title : '经营部门',
                            width : 80,
                            align :'center',
                            formatter: function (value) {
                                if (value == "1") {
                                    return "经营一部";
                                } else {
                                    return "经营二部";
                                }
                            }
                        },
                        {
                            field : 'nature',
                            title : '项目性质',
                            width : 60,
                            align :'center',
                            formatter: function (value) {
                                if (value == "1") {
                                    return "新建";
                                } else {
                                    return "改造";
                                }
                            }
                        },
                        {
                            field : 'category',
                            title : '项目类别',
                            width : 60,
                            align :'center',
                            formatter: function (value) {
                                if (value == "1") {
                                    return "景观";
                                } else if(value == "2") {
                                    return "规划";
                                } else if(value == "3") {
                                    return "建筑";
                                } else if(value == "4") {
                                    return "EPC";
                                } else {
                                    return "旅游";
                                }
                            }
                        },
                        {
                            field : 'investmentMount',
                            title : '投资额(万元)',
                            width : 80,
                            align :'right',
                            formatter: function (value) {
                               return formatCurrency1(value);
                            }
                            
                        },
                        {
                            field : 'designArea',
                            title : '设计面积(㎡)',
                            width : 80,
                            align :'right',
                            formatter: function (value) {
                               return formatCurrency1(value);
                            }
                        },
                        {
                            field : 'contractAwardCompany',
                            title : '发包单位',
                            width : 80,
                            align :'center',
                            formatter: function (value, row, index) {
                            	if(row.companyAssociated){
                                	return row.companyAssociated.companyName;
                            	} else {
                            		return row.companyAssociated
                            	}
                            }
                        },
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 120,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(value.time, "yyyy/MM/dd hh:mm:ss");
                            }
                        },
                        {
                            field : 'status',
                            title : '状态',
                            width : 80,
                            align:'center',
                            formatter: function(value) {
                                <c:forEach var="type" items="${e:gardenProjectStatusEnum()}">
                                    if (value == ${type.value}) {
                                        return '${type.message}';
                                    }
                                </c:forEach>
                            }
                        },
                        {
                            field : 'step',
                            title : '阶段',
                            width : 80,
                            align :'center',
                            formatter: function(value) {
                                <c:forEach var="type" items="${e:gardenProjectStepEnum()}">
                                    if (value == ${type.value}) {
                                        return '${type.message}';
                                    }
                                </c:forEach>
                            }
                        },
                        {
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 80,
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
                    	if(${projectAddAuth} || ${projectExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${projectAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${projectExportAuth}){
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
            if(${projectUpdateAuth}){
            	opts += "<a href='javascript:showDetail(" + index + ",2)'>| 修改</a>  ";
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
        // 获取意向合作方式
        getBasic(16, createCooperation);
        function createCooperation(datas) {
            var boxS = $("#intentionalCooperation"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
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

        // 获取项目类型
        getBasic(6, createType);
        function createType(datas) {
            var boxS = $("#type-s"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].attributeId +"'>"+ datas[i].attributeName +"</option>";
            }
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
            boxS.append(dom);
        }

        contractAwardCompany();
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
         $("#contractAwardCompanyTd").click(function(){
         		var that = $(this);
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
                    that.find("input").focus();    
                }
            });
            
       });
       
        $("#contractAwardCompany-sTd").click(function(){
             var that = $(this);
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
		                 return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
		                 }  
	                   });                        
                       }
                    }
                    that.find("input").focus();    
                }
            });
       });

       /*  // 获取技术负责人
        getStaffPerson();
        function getStaffPerson() {
            $.ajax({
                url: "/staff/user/getStaffPerson.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  jobStatus : 1,
                  rows:1000000,
                  orderBy:1
                },
                success: function (data) {
                    if (data.success) {
                        createStaffPerson(data.rows);
                    }
                }
            });
        } */
       /*  function createStaffPerson(datas) {
            var box = $("#projectLeader"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].userId +"'>"+ datas[i].userInfo.name +"</option>";
            }
            box.append(dom);
        } */
        // 获取技术负责人
        getStaffPerson1();
        function getStaffPerson1() {
	      $("#introducer-s").combobox({    
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
            }
	        });
        
          $("#projectLeader").combobox({    
	           url:'/staff/user/getStaffPersons.htm?jobId=5&notJobStatus=111&rows=1000000&orderBy=1&personnelNature=44',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配    
               } 
	        });
        }      
       $("#projectLeaderTd .combo-arrow").click(function(){
       		$("#projectLeader").combobox({    
	           url:'/staff/user/getStaffPersons.htm?jobId=5&notJobStatus=111&rows=1000000&orderBy=1&personnelNature=44',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配    
               } 
	        });
       });
       $("#projectLeaderTd .combo input").click(function(){
       		$("#projectLeader").combobox({    
	           url:'/staff/user/getStaffPersons.htm?jobId=5&notJobStatus=111&rows=1000000&orderBy=1&personnelNature=44',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配    
               } 
	        });
       		$("#projectLeaderTd .combo input").focus();
       });
       $("#introducerTd .combo-arrow").click(function(){
       		 $("#introducer-s").combobox({    
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
            }
	        });
       });
       $("#introducerTd .combo input").click(function(){
       		 $("#introducer-s").combobox({    
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
            }
	        });
       		$("#introducerTd .combo input").focus();
       });
       /*  function createStaffPerson1(datas) {
            var box = $("#introducer-s"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].userId +"'>"+ datas[i].userInfo.name +"</option>";
            }
            box.html(dom);
        } */

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
                title: '项目信息',
                width: windowW - 50,
                height: windowH - 50,
                closable: false,
                onBeforeOpen: function () {
                    if (index == undefined) {
                          getMaxNum();                                                                   
                          lockDate(1);
                          $("input[name='needTechnical']").attr("disabled",false);                          
                          $("#haveScheme").attr("disabled",false);
                          $("#haveDevelopment").attr("disabled",false);
                          $("#haveDrawing").attr("disabled",false);
                          $("#havePlanning").attr("disabled",false);
                          $("#save").show();
                    } else {
                        if (list) setInfo(list);
                        if(type==1){
                          $("#save").hide();
                          $("#apply").hide();
                          $(".area-btn").attr("disabled",true);                          
                          lockDate(0);
                          $("input[name='needTechnical']").attr("disabled",true);                          
                          $("#haveScheme").attr("disabled",true);
                          $("#haveDevelopment").attr("disabled",true);
                          $("#haveDrawing").attr("disabled",true);
                          $("#havePlanning").attr("disabled",true);
                        }else if(type==2){                                                 
                          lockDate(1);
                          $("input[name='needTechnical']").attr("disabled",false);                          
                          $("#haveScheme").attr("disabled",false);
                          $("#haveDevelopment").attr("disabled",false);
                          $("#haveDrawing").attr("disabled",false);
                          $("#havePlanning").attr("disabled",false);
                          $("#save").hide();
                          if(list.step>20){                         
                             //查询此项目有无修改的未审核记录，即临时数据是否存在
                             if (list) getUpdateDate(list);
                          }else if(list.step==10){
                            $("#save").show();
                          }
                        }                       
                    }
                },
                buttons: [
                    {
                        id : 'save',
                        iconCls: 'icons-true',
                        text: '保存',
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
                            if (index != undefined) {
                                // 更新
                                $("#operation").val(1);
                                $("#form1").attr("action", "/project/user/updateProject.htm");
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
                                            $("#provCode").val("");
                                            $("#cityCode").val("");
                                            $("#distCode").val(""); 
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","修改成功!","info");
                                            $("#detailInfo").dialog("close");
                                            $("input[name='needTechnical']").eq(0).attr("checked", true);
                                            $("#haveScheme").attr("checked", false);
                                            $("#haveDevelopment").attr("checked", false);
                                            $("#haveDrawing").attr("checked", false);
                                            $("#havePlanning").attr("checked", false);
                                            $("#form1 :input[type='hidden']").val("");
                                            $("#operation").val("");
                                            queryProduct();
                                        } else {
                                            $("#operation").val("");
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                            } else {
                                // 添加
                                $("#form1").attr("action", "/project/user/saveProject.htm");
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
                                            $("#provCode").val("");
                                            $("#cityCode").val("");
                                            $("#distCode").val(""); 
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","添加成功!","info");
                                            $("#detailInfo").dialog("close");
                                            $("#haveScheme").attr("checked", false);
                                            $("#haveDevelopment").attr("checked", false);
                                            $("#haveDrawing").attr("checked", false);
                                            $("#havePlanning").attr("checked", false);
                                            $("#preRequirements").attr("disabled",false);
                                            $("input[name='needTechnical']").eq(0).attr("checked", true);
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
                        id :'apply',
                        iconCls: 'icons-true',
                        text: '申请',
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
                            if (index != undefined) {
                                if(list.step>20 && ($("#temporaryId").val()!=null && $("#temporaryId").val()!="")){
                                  checkData(list);
                                  $.messager.confirm("修改项目", "点击申请后之前的修改将会被放弃,是否确认修改？", function (r) {
                                      if (r) {
        	                         // 更新
        	                         if ($("#introducer-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目介绍人", "error");
	                            /*} else if ($("#designArea").val() <= 0 || !$("#designArea").val()) {
	                                $.messager.alert("错误", "设计面积必须大于0", "error");
	                             } else if ($("#investmentMount").val() <= 0 || !$("#investmentMount").val()) {
	                                $.messager.alert("错误", "投资额必须大于0", "error"); */
	                            } else if (!$("#name-s").val())  {
	                                $.messager.alert("错误", "项目名称不能为空", "error");
	                            } else if ($("#attribute-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目性质", "error");
	                            } else if ($("#category-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类别", "error");
	                            } else if ($("#type-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类型", "error");
	                            } else if ($("#department-s").val() == -1) {
	                                $.messager.alert("错误", "请选择经营部门", "error");
	                            } else if ($(".area-btn").html() == "请选择区域") {
	                                $.messager.alert("错误", "请选择所在城市", "error");
	                            } else if (!$("#introduction").val()) {
	                                $.messager.alert("错误", "请输入项目介绍", "error");
	                            } else if ($("#contractAwardCompany-s").val() == -1) {
	                                $.messager.alert("错误", "请选择发包单位", "error");
	                            } else if ($("#intentionalCooperation").val() == -1) {
	                                $.messager.alert("错误", "请选择意向合作方式", "error");
	                            } else if ($("input[name='needTechnical']").eq(0).attr("checked") && !$("#preRequirements").val()) {
	                                $.messager.alert("错误", "请输入前期技术支持要求", "error");
	                            } else if($("#haveScheme").val()==0 && $("#haveDevelopment").val()==0 && $("#haveDrawing").val()==0 && $("#havePlanning").val()==0){
                                    $.messager.alert("错误", "请至少选择一个项目设计阶段", "error");
                                } else {  
                                $("#form1").attr("action", "/project/user/updateProject.htm");
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
                                            $("#provCode").val("");
                                            $("#cityCode").val("");
                                            $("#distCode").val(""); 
                                            $("#detailInfo").dialog("close");
                                            $("#form1")[0].reset();
                                            $.messager.progress("close");
                                            $.messager.alert("信息","修改成功!","info");
                                            $("#detailInfo").dialog("close");
                                            $("#haveScheme").attr("checked", false);
                                            $("#haveDevelopment").attr("checked", false);
                                            $("#haveDrawing").attr("checked", false);
                                            $("#havePlanning").attr("checked", false);
                                            $("#preRequirements").attr("disabled",false);
                                            $("input[name='needTechnical']").eq(0).attr("checked", true);
                                            $("#form1 :input[type='hidden']").val("");
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });
                                }
                                      }
                                  });
                                }else{
                                   // 更新
                                if(list.step>20){
                                   checkData(list);
                                }
                                if ($("#introducer-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目介绍人", "error");
	                            /* } else if ($("#designArea").val() <= 0 || !$("#designArea").val()) {
	                                $.messager.alert("错误", "设计面积必须大于0", "error");
	                            } else if ($("#investmentMount").val() <= 0 || !$("#investmentMount").val()) {
	                                $.messager.alert("错误", "投资额必须大于0", "error"); */
	                            } else if (!$("#name-s").val())  {
	                                $.messager.alert("错误", "项目名称不能为空", "error");
	                            } else if ($("#attribute-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目性质", "error");
	                            } else if ($("#category-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类别", "error");
	                            } else if ($("#type-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类型", "error");
	                            } else if ($("#department-s").val() == -1) {
	                                $.messager.alert("错误", "请选择经营部门", "error");
	                            } else if ($(".area-btn").html() == "请选择区域") {
	                                $.messager.alert("错误", "请选择所在城市", "error");
	                            } else if (!$("#introduction").val()) {
	                                $.messager.alert("错误", "请输入项目介绍", "error");
	                            } else if ($("#contractAwardCompany-s").val() == -1) {
	                                $.messager.alert("错误", "请选择发包单位", "error");
	                            } else if ($("#intentionalCooperation").val() == -1) {
	                                $.messager.alert("错误", "请选择意向合作方式", "error");
	                            } else if ($("input[name='needTechnical']").eq(0).attr("checked") && !$("#preRequirements").val()) {
	                                $.messager.alert("错误", "请输入前期技术支持要求", "error");
	                            } else if($("#haveScheme").val()==0 && $("#haveDevelopment").val()==0 && $("#haveDrawing").val()==0 && $("#havePlanning").val()==0){
                                    $.messager.alert("错误", "请至少选择一个项目设计阶段", "error");
                                } else { 
	                                $("#form1").attr("action", "/project/user/updateProject.htm");
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
	                                            $.messager.progress("close");
	                                            $.messager.alert("信息","修改成功!","info");
	                                            $("#provCode").val("");
	                                            $("#cityCode").val("");
	                                            $("#distCode").val(""); 
	                                            $("#detailInfo").dialog("close");
	                                            $("#haveScheme").attr("checked", false);
	                                            $("#haveDevelopment").attr("checked", false);
	                                            $("#haveDrawing").attr("checked", false);
	                                            $("#havePlanning").attr("checked", false);
	                                            $("#preRequirements").attr("disabled",false);
	                                            $("input[name='needTechnical']").eq(0).attr("checked", true);
	                                            $("#form1 :input[type='hidden']").val("");
	                                            queryProduct();
	                                        } else {
	                                            $.messager.progress("close");
	                                            $.messager.alert("错误",list.resultMsg,"error");
	                                        }
	                                    }
	                                });
	                              }
                                }                               
                            } else {
                                // 申请
                                if ($("#introducer-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目介绍人", "error");
	                            /* } else if ($("#designArea").val() <= 0 || !$("#designArea").val()) {
	                                $.messager.alert("错误", "设计面积必须大于0", "error");
	                            } else if ($("#investmentMount").val() <= 0 || !$("#investmentMount").val()) {
	                                $.messager.alert("错误", "投资额必须大于0", "error"); */
	                            } else if (!$("#name-s").val())  {
	                                $.messager.alert("错误", "项目名称不能为空", "error");
	                            } else if ($("#attribute-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目性质", "error");
	                            } else if ($("#category-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类别", "error");
	                            } else if ($("#type-s").val() == -1) {
	                                $.messager.alert("错误", "请选择项目类型", "error");
	                            } else if ($("#department-s").val() == -1) {
	                                $.messager.alert("错误", "请选择经营部门", "error");
	                            } else if ($(".area-btn").html() == "请选择区域") {
	                                $.messager.alert("错误", "请选择所在城市", "error");
	                            } else if (!$("#introduction").val()) {
	                                $.messager.alert("错误", "请输入项目介绍", "error");
	                            } else if ($("#contractAwardCompany-s").val() == -1) {
	                                $.messager.alert("错误", "请选择发包单位", "error");
	                            } else if ($("#intentionalCooperation").val() == -1) {
	                                $.messager.alert("错误", "请选择意向合作方式", "error");
	                            } else if ($("input[name='needTechnical']").eq(0).attr("checked") && !$("#preRequirements").val()) {
	                                $.messager.alert("错误", "请输入前期技术支持要求", "error");
	                            } else if($("#haveScheme").val()==0 && $("#haveDevelopment").val()==0 && $("#haveDrawing").val()==0 && $("#havePlanning").val()==0){
                                    $.messager.alert("错误", "请至少选择一个项目设计阶段", "error");
                                }else { 
	                                $("#form1").attr("action", "/project/user/applyProject.htm");
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
	                                            $("#provCode").val("");
	                                            $("#cityCode").val("");
	                                            $("#distCode").val(""); 
	                                            $("#detailInfo").dialog("close");
	                                            $("#form1")[0].reset();
	                                            $.messager.progress("close");
	                                            $.messager.alert("信息","添加成功!","info");
	                                            $("#detailInfo").dialog("close");
	                                            $("#haveScheme").attr("checked", false);
	                                            $("#haveDevelopment").attr("checked", false);
	                                            $("#haveDrawing").attr("checked", false);
	                                            $("#havePlanning").attr("checked", false);
	                                            $("#preRequirements").attr("disabled",false);
	                                            $("input[name='needTechnical']").eq(0).attr("checked", true);
	                                            queryProduct();
	                                        } else {
	                                            $.messager.progress("close");
	                                            $.messager.alert("错误",list.resultMsg,"error");
	                                        }
	                                    }
	                                });
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
                            $("#detailInfo").dialog("close");
                            $("#haveScheme").attr("checked", false);
                            $("#haveDevelopment").attr("checked", false);
                            $("#haveDrawing").attr("checked", false);
                            $("#havePlanning").attr("checked", false);
                            $("#preRequirements").attr("disabled",false);
                            $("input[name='needTechnical']").eq(0).attr("checked", true);
                            $("#id").val("");
                        }
                    }
                ]
            });
        }

        // 获取项目最大编号
        function getMaxNum() {
            $.ajax({
                url: "/project/user/projectMaxNo.htm",
                type: 'post',
                dataType: 'json',
                data: { },
                success: function (data) {
                    if (data.success) {
                        $("#projectNo-s").val(data.data);
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
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
            //$(".area").html("<option value='"+provIds+"'></option>");
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
				        //var prov = $(".pro").find("option:selected").attr("provid");
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
        function setInfo(list) {
            $("#projectNo-s").val(list.projectNo);
            //$("#introducer-s").val(list.introducer);
            if(list.introducer>0){
              $("#introducer-s").combobox("setValue",list.introducer);
            }
            if(list.designArea!=0){
               $("#designArea").val(list.designArea);
            }
            if(list.investmentMount!=0){
               $("#investmentMount").val(list.investmentMount);
            } 
            $("#name-s").val(list.name);
            $("#version").val(list.version);
            $("#attribute-s").val(list.nature);
            $("#intentionalCooperation").val(list.intentionalCooperation);
            $("#category-s").val(list.category);
            $("#type-s").val(list.type);
            $("#department-s").val(list.managementDepartment);
            $("#city").val(list.city);
            $("#step-s").val(list.step);
            $("#projectLiable").val(list.projectLiable);
            getDetailAddress(list.city);
            $("#address").val(list.address);
            $("#introduction").val(list.introduction);
            if (list.needTechnical) {
                $("input[name='needTechnical']").eq(0).attr("checked", true);
                $("#preRequirements").attr("disabled",false);
                $("#preRequirements").val(list.preRequirements);
            } else {
                $("input[name='needTechnical']").eq(1).attr("checked", true);
                $("#preRequirements").attr("disabled",true);
            }
            if(list.contractAwardCompany>0 ){
              $("#contractAwardCompany-s").combobox("setValue",list.contractAwardCompany);
            }
            if (list.haveScheme) $("#haveScheme").attr("checked", true);
            if (list.haveDevelopment) $("#haveDevelopment").attr("checked", true);
            if (list.haveDrawing) $("#haveDrawing").attr("checked", true);
            if (list.havePlanning) $("#havePlanning").attr("checked", true);
        }

        function queryProduct(){
            var param = {};
            param.projectNo = $('#projectNo').val();
            param.name = $('#name').val();
            if ($('#contractAwardCompany').combobox("getValue") != "-1") {
                param.contractAwardCompany = $('#contractAwardCompany').combobox("getValue");
            }
            param.cityId = $('#cityId').val();
            if ($("#projectLeader").combobox("getValue")!= "-1") {
                param.projectLeader = $("#projectLeader").combobox("getValue");
            }
            if ($('#department').val() != "-1") {
                param.department = $('#department').val();
            }
            if ($('#managementPersonnel').val() != "-1") {
                param.managementPersonnel = $('#managementPersonnel').val();
            }
            if ($('#step').val() != "-1") {
                param.step = $('#step').val();
            }
            if ($('#status').val() != "-1") {
                param.status = $('#status').val();
            }
            if ($('#attribute').val() != "-1") {
                param.nature = $('#attribute').val();
            }
            if ($('#category').val() != "-1") {
                param.category = $('#category').val();
            }
            param.actiDatetime= $('#actiDatetime').datebox('getValue');
            param.actiDatetimeEnd= $('#actiDatetimeEnd').datebox('getValue');
            param.investmentMountStart = $('#investmentMountStart').val();
            param.investmentMountEnd = $('#investmentMountEnd').val();
            param.designAreaStart = $('#designAreaStart').val();
            param.designAreaEnd = $('#designAreaEnd').val();
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
        
          $("input:radio[name='needTechnical']").on("change", function () {
          var v = $(this).val();
          if (v =="1"){
             $("#preRequirements").attr("disabled",false);
          }else{
             $("#preRequirements").val("");
             $("#preRequirements").attr("disabled",true);
          }
          }); 

         function getUpdateDate(list){
             $.ajax({
                url: "/project/user/getGardenProjectByIdAndStatus.htm",
                type: 'post',
                dataType: 'json',
                data: {
                  id : list.memo,
                  projectNo:list.projectNo,
                  status : 3
                },
                success: function (data) {
                    if (data.success) {
                       var userId = data.rows.createUserId;
                       var nowUserId = ${sec:userId()};
                       $("#temporaryId").val(data.rows.id);
                       if(userId!=nowUserId){
                          $.messager.alert("提示","项目已被他人修改并且未经审核，请审核后继续修改！","info");
                          $("#apply").hide();
                       }
                    }
                }
            });
         }
        function checkData(list){
           if($("#introducer-s").val()!=list.introducer){
              $("#isUpdate").val(1);
              return;
           }else if($("#designArea").val()!=list.designArea){
              $("#isUpdate").val(1);
              return;
           }else if($("#investmentMount").val()!=list.investmentMount){
              $("#isUpdate").val(1);
              return;
           }else if($("#name-s").val()!=list.name){
              $("#isUpdate").val(1);
              return;
           }else if($("#attribute-s").val()!=list.nature){
              $("#isUpdate").val(1);
              return;
           }else if($("#category-s").val()!=list.category){
              $("#isUpdate").val(1);
              return;
           }else if($("#type-s").val()!=list.type){
              $("#isUpdate").val(1);
              return;
           }else if($("#department-s").val()!=list.managementDepartment){
              $("#isUpdate").val(1);
              return;
           }else if($("#city").val()!=list.city){
              $("#isUpdate").val(1);
              return;
           }else if($("#address").val()!=list.address){
              $("#isUpdate").val(1);
              return;
           }else if($("#introduction").val()!=list.introduction){
              $("#isUpdate").val(1);
              return;
           }else if($("#preRequirements").val()!=list.preRequirements){
              $("#isUpdate").val(1);
              return
           }else if($("input[name='needTechnical']").val()!=list.needTechnical){
              $("#isUpdate").val(1);
              return;
           }else if($("#contractAwardCompany-s").val()!=list.contractAwardCompany){
              $("#isUpdate").val(1);
              return;
           }else if($("#intentionalCooperation").val()!=list.intentionalCooperation){
              $("#isUpdate").val(1);
              return;
           }else if($("#haveScheme").val()!=list.haveScheme){
              $("#isUpdate").val(1);
              return;
           }
           else if($("#haveDevelopment").val()!=list.haveDevelopment){
              $("#isUpdate").val(1);
              return;
           }
           else if($("#haveDrawing").val()!=list.haveDrawing){
              $("#isUpdate").val(1);
              return;
           }
           else if($("#havePlanning").val()!=list.havePlanning){
              $("#isUpdate").val(1);
              return;
           }
        }
        
        function  lockDate(lock){
          if(lock==0){
             $("#detailInfo input, #detailInfo textarea").each(function (){
          	   $(this).attr("readonly", true);
             });
             $("#detailInfo select").each(function (){
          	   $(this).attr("disabled", true);
             });
          }else{
             $("#detailInfo input, #detailInfo textarea").each(function (){
          	   $(this).attr("readonly", false);
          	   $("#projectNo-s").attr("readonly", true);
             });
             $("#detailInfo select").each(function (){
          	   $(this).attr("disabled", false);
             });
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
       
       function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/project/user/downLoadProject.htm",
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
       
    </script>
</body>
</html>