
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>人员信息</title>
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
        select {
            width: 100%;
        }
        #auths{
            display:block;
            width:100%;
            height:350px;
            overflow: auto;
        }
        .auth_tab{
            border-collapse:collapse;
            border-bottom: 1px solid gray;
            width:100%;
        }
       .auth_tab td{
            border-collapse:collapse;
            border-bottom: 1px solid gray;
        }
       .auth_title{
            width:80px;
            text-align: center;
            vertical-align: center;
            font-weight: bold;
            border-right:1px solid gray;
        }

       .auth_item{
            width:125px;
            display:block;
            float:left;
        }
        .diolag-table th {
			width: 9%; 
			font-weight: normal;
			text-align: right;
		}
		.diolag-table td {
			width: 16%; 
		}
        #detailInfo .panel div{
            padding-right:10px;
        }
        #educationTable,#jobTable, #familyTable,.documentTable{
        	width:95%;
            margin-left: 40px
        }
        .tableDiv{
        	padding-left:40px;
        	width:95%;
        }
        .tableDiv a{
        	float:right;
        }
        .imgZoom li {
        	position: relative;
        }
        .imgZoom a {
        	position: absolute;
        	top: 0;
        	right: 0;
        	z-index: 2;
        	display: block;
        	width: 20px;
        	height: 20px;
        	background: rgba(255, 255, 255, .8);
        	text-align: center;
        	line-height: 20px;
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
                            <th>姓名</th>
                            <td>
                                <input type="text" id="name-search" name="name" class="input">
                            </td>
                            <th>所属部门</th>
                            <td>
                                <select id="departmentId-search" name="departmentId">
                                    <option value="-1">不限</option>
                                </select>
                            </td>
                            <th>职位</th>
                            <td>
                                <select id="jobName-search" name="jobId">
                                    <option value="-1">不限</option>
                                </select>
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>联系电话</th>
                            <td>
                                <input type="text" maxlength="11" id="mobile-search" class="input">
                            </td>
                            <th>座机</th>
                            <td>
                                <input type="text" maxlength="20" id="telephone-search" class="input">
                            </td>
                            <th>状态</th>
                            <td>
                                <select id="jobStatusSerach" name="jobStatusSerach">
                                </select>
                            </td>
                            <th></th>
                            <td></td>
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
        <div id="menu_bind" class="easyui-dialog" style="width:370px;height:448px;padding:10px 10px 10px 10px" data-options="title:'菜单绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	        <div id="menu_tree">
	        </div>
	   </div>

	   <div id="auth_bind" class="easyui-dialog" style="width:800px;height:560px;" data-options="title:'权限绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
	       <div id="auths">
	       </div>
	   </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <!-- <tr>
                        <th>用户名</th>
                        <td>
                            <input type="text" id="username" name="username" class="input" />
                        </td> -->
                    <tr>
                        <th width="10%">姓名</th>
                        <td width="15%">
                            <input type="text" id="name" name="name" class="input" />
                        </td>
                        <th width="10%">联系电话</th>
                        <td width="15%">
                            <input type="text" id="mobile" name="mobile" maxlength="11" class="input" />
                        </td>
                        <th width="10%">所属部门</th>
                        <td width="15%">
                            <select name="departmentId" id="department">
                                <option value="-1">请选择部门</option>
                            </select>
                        </td>
                    	<th width="10%">职位</th>
                        <td width="15%">
                            <select name="jobId" id="jobId">
                                <option value="-1">请选择职位</option>
                            </select>
                        </td>
                        
                    </tr>
                    <tr>
                    	<th>身份证号</th>
                    	<td>
                    		<input type="text" id="identityNo" name="identityNo" maxlength="18" class="input"  onkeypress="getData();"/>
                    	</td>
                    	<th>性别</th>
                    	<td>
                    		<select  id="sexSelect" disabled>
                                <option value="-1"></option>
                                <option value="1">男</option>
                                <option value="2">女</option>
                            </select>
                            <input name="sex" id="sex" type="hidden">
                    	</td>
                    	<th>生日</th>
                    	<td>
                    		<input type="text" id="birthday" name="birthday" readonly class="input" />
                    	</td>
                    	<th>年龄</th>
                    	<td>
                    		<input type="text" id="age" name="age" readonly class="input" />
                    	</td>
                    </tr>
                    <tr>
                    	<th>员工编号</th>
                        <td>
                            <input type="text" id="employeeNo" name="employeeNo" placeholder="请输入以2打头员工编号"  maxlength="5" class="input"/>
                        </td>
                        <th>类别</th>
                        <td>
                            <select name="staffType" id="staffType">
                                <option value="-1"></option>
                            </select>
                        </td>
                        <th>入职日期</th>
                        <td>
                           	<input type="text" id="entryDate" name="entryDate" class="easyui-datebox e-input" data-options="height:25,width:150,editable:true,formatter:formatDate"/>
                        </td>
                        <th>司龄</th>
                        <td>
                            <input type="text" id="companyAge" name="companyAge" readonly class="input"/>
                        </td>
                    </tr>
                    <tr>
                        <th>虚拟网短号</th>
                        <td>
                            <input type="text" id="virtualCornet" name="virtualMobile" maxlength="6" class="input" />
                        </td>
                    
                        <th>座机</th>
                        <td>
                            <input type="text" id="telephone" name="telephone" maxlength="20" class="input" />
                        </td>
                        <th>座机短号</th>
                        <td>
                            <input type="text" id="shortTelephone" name="shortTelephone" maxlength="4" class="input" />
                        </td>
                        <th>邮箱</th>
                        <td>
                            <input type="text" id="email" name="email" class="input" />
                        </td>
                        
                    </tr>
                    <tr>
                        <th>门禁卡号</th>
                        <td> <input type="text" id="entranceGuardCardNumber" name="entranceGuardCardNumber" class="input" /></td>
                        <th>政治面貌</th>
						<td>
							<select name="politicalStatus" id="politicalStatus">
                                <option value="-1"></option>
                            </select>
                        </td>
                        <th>职称、职质</th>
						<td colspan="3">
							<input type="text" id="titleQuality" name="titleQuality" class="input" />
                        </td>
					</tr>
					<tr>
                        <th>毕业院校</th>
                        <td> <input type="text" id="graduateInstitutions" name="graduateInstitutions" class="input" /></td>
                        <th>所学专业</th>
                        <td>
							<input type="text" id="studyMajor" name="studyMajor" class="input" />
                        </td>
                        <th>学历</th>
						<td>
							<select name="education" id="education">
                                <option value="-1">请选择部门</option>
                            </select>
                        </td>
                        <th>首次参加工作日期</th>
						<td>
							<input type="text" id="firstWorkDate" name="firstWorkDate" class="easyui-datebox e-input" data-options="height:25,width:150,editable:true,formatter:formatDate"/>
                        </td>
					</tr>
					<tr>
						<th>户口所在地</th>
						<td colspan="3">
							<input type="text" id="registeredResidence" name="registeredResidence" class="input" />
                        </td>
                        <th>现住址</th>
						<td colspan="3">
							<input type="text" id="presentAddress" name="presentAddress" class="input" />
                        </td>
					</tr>
					<tr>
						<th>合同1</th>
						<td>
							<input type="text" id="contract1" name="contract1" class="input" />
                        </td>
                        <th>合同2</th>
						<td>
							<input type="text" id="contract2" name="contract2" class="input" />
                        </td>
                         <th>合同3</th>
						<td>
							<input type="text" id="contract3" name="contract3" class="input" />
                        </td>
                        <th>服务到期日</th>
						<td>
							<input type="text" id="contractDueDate" name="contractDueDate" class="easyui-datebox e-input" data-options="height:25,width:150,editable:true,formatter:formatDate"/>
                        </td>
					</tr>
					<tr>
						<th>社保基数(元)</th>
                        <td> <input type="text" id="socialSecurityBase" name="socialSecurityBase" class="input" /></td>
                        <th>开户银行</th>
                        <td>
							<input type="text" id="bank" name="bank" class="input" />
                        </td>
                        <th>银行卡号</th>
						<td>
							<input type="text" id="bankcardNum" name="bankcardNum" class="input" />
                        </td>
                        <th>状态</th>
						<td>
							<select name="jobStatus" id="jobStatus" class="select">
							</select>
						</td>   
					</tr>
                    <tr>
                   <!--  <th>账户状态</th>
						<td>
							<select name="accStatus" id="accStatus" class="select">
								 <option value="0">正常</option>
								 <option value="1">冻结</option>
								 <option value="2">注销</option>
							</select>
						</td> -->
						<th>账号可登录</th>
                        <td>
                            <select id="accStatus" name="accStatus" class="select" >
                               <option value="0">是</option>
                               <option value="1">否</option>
                            </select>
                        </td>
                        <th></th>
						<td></td>
                        <th></th>
						<td></td>
						<th></th>
						<td></td>
                    </tr>
                    <tr>
                    	<th>备注</th>
                    	<td colspan="7"><input type="text" id="password" name="password" class="input"/></td>
                    </tr>
                </tbody>
            </table>
            <div class="tableDiv">教育背景 <a href="javascript:getEducationData()">追加</a></div>
         	<table id ="educationTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div class="tableDiv">工作背景  <a href="javascript:getJobData()">追加</a></div>
         	<table id="jobTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div class="tableDiv">家庭成员  <a href="javascript:getFamilyData()">追加</a></div>
         	<table id="familyTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
            <input type="hidden" id="id" name='id' />
            <input type="hidden" id="userId" name='userId' />
            <input type="hidden" id="isEmployee" name='isEmployee' value="1" />
            <!-- 员工新增标识 -->
            <input type="hidden" id="companyId" name='companyId' value="1" />
            <!-- 公司id暂时为1为了测试 -->
        </form>
        <div class="tableDiv">相关附件</div>
        <div id ="documentDetail" >
        
        </div>  
        <div id="checkDiv" style="float:right;"><input id="check" name="check" type="checkbox" checked><span>我已阅读公司规章制度</span></span></div> 
    </div>
    
    <div id ="familyDetail" style="display:none">
    	<form id="form2" class="inner-q" method="post">
            <table  class="diolag-table" cellpadding="0" cellspacing="0" border="0" >
         	   <tr>
         	     <th>姓名</th><td><input type="text" id="familyName" name="familyName" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>关系</th><td><input type="text" id="familyRelation" name="relation" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>工作单位或地址</th><td><input type="text" id="familyCompany" name="company" class="input"/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>职务</th><td><input type="text" id="familyPost" name="familyPost" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>电话</th><td><input type="number" id="familyTelephone" name="telephone" class="input" maxlength="11" /></td>
        	   </tr>
         	</table>
         	<input type="hidden" id="familyId" name='id' />
         	<input type="hidden" id="familyTalentId" name='talentId' />
         </form>
    </div>
    <div id = "jobDetail" style="display:none">
    	<form id="form3" class="inner-q" method="post">
         	<table  class="diolag-table" cellpadding="0" cellspacing="0" border="0" >
         	   <tr>
         	     <th>入职年月</th><td><input type="text" id="jobStartDate" name="jobStartDate" class="easyui-datebox e-input" data-options="height:25,width:100,editable:false,formatter:formatDates"/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>离职年月</th><td><input type="text" id="jobEndDate" name="jobEndDate" class="easyui-datebox e-input" data-options="height:25,width:100,editable:false,formatter:formatDates"/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>公司名称</th><td><input type="text" id="jobCompanyName" name="companyName" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>职务</th><td><input type="text" id="jobPost" name="jobPost" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>工作描述</th><td><textarea id="description" name="description"></textarea></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>离职原因</th><td><input type="text" id="jobLeavingReason" name="leavingReason" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>证明人</th><td><input type="text" id="jobReterence" name="reterence" class="input" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>证明人电话</th><td><input type="number" id="jobReterenceTel" name="reterenceTel" class="input" maxlength="11"/></td>
        	   </tr>
         	</table>
         	<input type="hidden" id="jobIds" name='id' />
         	<input type="hidden" id="jobTalentId" name='talentId' />
        </form>
     </div>
     <div id = "educationDetail" style="display:none">
    	<form id="form4" class="inner-q" method="post">
         	<table  class="diolag-table" cellpadding="0" cellspacing="0" border="0" >
         	   <tr>
         	     <th>入学年月</th><td><input type="text" id="educationStartDate" name="educationStartDate" class="easyui-datebox e-input" data-options="height:25,width:100,editable:false,formatter:formatDates" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>毕业年月</th><td><input type="text" id="educationEndDate" name="educationEndDate" class="easyui-datebox e-input" data-options="height:25,width:100,editable:false,formatter:formatDates" /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>学校名称</th><td><input type="text" id="educationSchoolName" name="schoolName" class="input"/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>专业名称</th><td><input type="text" id="educationProfessional" name="professional" class="input"  /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>学历/证书</th><td><input type="text" id="educationCertificate" name="certificate"  class="input" /></td>
        	   </tr>
         	</table>
         	<input type="hidden" id="educationId" name='id' />
         	<input type="hidden" id="educationTalentId" name='talentId' />
       	</form>
     </div>
     <div id="imgDiv" style="display:none;">
       <img style='max-width: 100%;' src="${res_img}/2.jpg">
     </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">2
        var windowH = $(window).height();
        var windowW = $(window).width();
        var talentId;
        var isFrist = 0;
        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/staff/user/getStaffPerson.htm',
                    title:'人员信息',
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
                        jobStatus:110
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                       /* {
                            field : 'userId',
                            title : '账号',
                            width : 80,
                            align:'center'
                        }, */
                        {
                            field : 'employeeNo',
                            title : '员工编号',
                            width : 60,
                            align:'center',
                            formatter: function (value, row, index) {
                                if(value!=0){
                                  return value;
                                }                               
                            }
                        },
                       /*  {
                            field : 'username',
                            title : '账户名',
                            width : 60,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.username;
                            }
                        }, */
                        {
                            field : 'name',
                            title : '姓名',
                            width : 70,
                            align :'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.name;
                            }
                        },
                        {
                            field : 'departmentName',
                            title : '所属部门',
                            width : 60,
                            align:'center'
                        },
                        {
                            field : 'jobName',
                            title : '职位',
                            width : 60,
                            align:'center'
                        },
                        {
                            field : 'mobile',
                            title : '联系电话',
                            width : 90,
                            align:'center',
                            formatter: function (value, row, index) {
                                return row.userInfo.mobile;
                            }
                        },
                         {
                            field : 'telephone',
                            title : '座机',
                            width : 90,
                            align:'center'
                        },
                        {
							field : 'loginSucceed',
							title : '后台登录次数',
							width : 80,
							align : 'center',
							formatter: function(value,row,index){
							        return row.userInfo.loginSucceed;
							}
						},
                        {
                            field : 'createTime',
                            title : '录入时间',
                            width : 110,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(row.createTime.time, "yyyy/MM/dd hh:mm");
                            }
                        },
                        {
                            field : 'jobStatusAttribute',
                            title : '状态',
                            width : 60,
                            align:'center'
                        },{
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                                var oper = "";
                                var splitStr = "";
                                if(${hasMenuAuth}){
                                    oper += "<a href='javascript:showBindAuth("+row.userId+")'>权限</a> ";
                                    oper += "| <a href='javascript:showBindMenu("+row.userId+")'>菜单</a> ";
                                    splitStr = "| ";
                                }
                                if(${hasEditAuth}){
                                    oper += splitStr + "<a href='javascript:showDetail(" + index + ")'>修改</a>";
                                }
                                return oper;
                            }
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
                    	if(${staffAddAuth} || ${staffExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${staffAddAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${staffExportAuth}){
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
            //$("#password").show();
            //$("#pwd").html("密码");
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '员工信息详情',
                width: windowW ,
                height: windowH ,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        $("#detailInfo input").attr("readonly",false);
                        $("#detailInfo select").attr("disabled",false);
                        $("#identityNo").attr("readonly",true);
                        reGetDate(list.talentId);
                        talentId = list.talentId;
                        $("#companyAge").attr("readonly",true);
                        $("#age").attr("readonly",true);
                        $("#sexSelect").attr("disabled",true);
                        $("#birthday").attr("readonly",true);
                        getFileName(list.userId);
                        $("#jobTable").show();
                        $("#educationTable").show();
                        $("#familyTable").show();
                        $(".tableDiv").show();
                        $("#documentDetail").show();
                        $("#checkDiv").show();
                        if(${staffUpdateAuth}){
                       	   $("#updateAuth").show();
	                    }else{
	                       $("#updateAuth").hide();
	                    }
                    }else{
                        $("#detailInfo input").attr("readonly",true);
                        $("#detailInfo select").attr("disabled",true);
                        $("#identityNo").attr("readonly",false);
                        $("#jobTable").hide();
                        $("#educationTable").hide();
                        $("#familyTable").hide();
                        $(".tableDiv").hide();
                        $("#documentDetail").hide();
                        $("#checkDiv").hide();
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
                                if(checkData(1)){
                                $("#form1").attr("action", "/staff/user/updateStaff.htm");
                                $("#id").val(list.id);
                                //$("#companyId").val(list.companyId);
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
                                            $("#form1")[0].reset();
                                            $("#jobId").html("<option value='-1'>请选择职位</option>");
                                            $.messager.progress("close");
                                            $("#detailInfo").dialog("close");
                                            talentId="";
                                            $("#jobTable").empty();
				                       	    $("#educationTable").empty();
				                            $("#familyTable").empty();
                                            if(isFrist){
                                              getwelcome();
                                            }
                                            isFrist=0;
                                            queryProduct();
                                        } else {
                                            $.messager.progress("close");
                                            $.messager.alert("错误",list.resultMsg,"error");
                                        }
                                    }
                                });

                                }

                            } else {
                                // 添加
                                $("#detailInfo input").attr("readonly",false);
                        		$("#detailInfo select").attr("disabled",false);
                                $("#form1").attr("action","/staff/user/insertStaffInfo.htm");
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
                                            $("#form1")[0].reset();
                                            $("#jobId").html("<option value='-1'>请选择职位</option>");
                                            $.messager.progress("close");
                                            $.messager.alert("信息","添加成功!","info");
                                            $("#detailInfo").dialog("close");
                                             talentId="";
                                             isFrist=0;
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
                            $("#detailInfo").dialog("close");
                            $("#form1")[0].reset();
                             talentId="";
                             isFrist=0;
                             $("#jobTable").empty();
                       	     $("#educationTable").empty();
                             $("#familyTable").empty();
                             $("#jobId").html("<option value='-1'>请选择职位</option>");
                        }
                    }
                ]
            });
        }

       function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/staff/user/downLoadStaff.htm",
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
      
        // 获取基础数据
        function getBasic(cb) {
            $.ajax({
                url: "/department/user/getDepartmentByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 1
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
        // 获取经营部门
        getBasic(createDepartment);
        function createDepartment(datas) {
            var box = $("#department"),
                boxS = $("#departmentId-search"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].name +"</option>";
            }
            box.empty();
            box.append("<option value='-1'>请选择部门</option>"+dom);
            boxS.empty();
            boxS.append("<option value='-1'>不限</option>"+dom);
        }

        // 获取项目性质
        getBasicInfo(25, createCompanyType);
        // 获取员工类别
        getBasicInfo(26, createStaffType);
        // 获取学历
        getBasicInfo(27, createEducation);
        getBasicInfo(28, createJobStatus);
        function createCompanyType(datas) {
            var box = $("#politicalStatus"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
        }
        
        function createStaffType(datas){
        	var box = $("#staffType"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
        }
        
        function createEducation(datas){
        	var box = $("#education"),
                dom = "<option value='-1'></option>";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.empty();
            box.append(dom);
        }
        
        function createJobStatus(datas){
        	var box = $("#jobStatus"), boxS = $("#jobStatusSerach"), dom = "",
        	    domS = "<option value='-1'></option>";
        	
        	for (var i = 0; i < datas.length; i++) {
        		if (datas[i].attributeId == 1){
        			dom += "<option value='"+ datas[i].id +"' selected>"+ datas[i].attributeName +"</option>";
        		}else{
        			dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";	
        		}
            }
        	box.empty();
        	boxS.empty();
        	boxS.append(domS + dom);
        	box.append(dom);
        }
        
        function getBasicInfo(id,cb) {
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
        
        $("#department").on("change",function(){
          if($(this).val()!=-1){
            getJob($(this).val());
          }else{
             $("#jobId option[value='-1']").attr("selected",true);
          }

        });

        $("#departmentId-search").on("change",function(){
          if($(this).val()!=-1){
             $.ajax({
                url: "/job/user/getJobByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 1,
                    departmentId:$(this).val()
                },
                success: function (data) {
                    if (data.success) {
                        var box = $("#jobName-search");
                        var dom = "<option value = '-1'>请选择职位</option>";
                        for (var i = 0; i < data.rows.length; i++) {
                             if(jobId==data.rows[i].id){
                               dom += "<option value='"+ data.rows[i].id +"' selected>"+ data.rows[i].name +"</option>";
                             }else{
                               dom += "<option value='"+ data.rows[i].id +"'>"+ data.rows[i].name +"</option>";
                             }                             
                        }
                       box.empty();
                       box.append(dom);
                    } else {
                        $.messager.alert("错误", data.resultMsg, "error");
                    }
                }
            });
          }else{
             $("#jobName-search option[value='-1']").attr("selected",true);
          }

        });
        
        function getJob(departmentId,jobId){
          $.ajax({
                url: "/job/user/getJobByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 1,
                    departmentId:departmentId
                },
                success: function (data) {
                    if (data.success) {
                        var box = $("#jobId");
                        var dom = "<option value = '-1'>请选择职位</option>";
                        for (var i = 0; i < data.rows.length; i++) {
                             if(jobId==data.rows[i].id){
                               dom += "<option value='"+ data.rows[i].id +"' selected>"+ data.rows[i].name +"</option>";
                             }else{
                               dom += "<option value='"+ data.rows[i].id +"'>"+ data.rows[i].name +"</option>";
                             }                             
                        }
                       box.empty();
                       box.append(dom);
                    }
                }
            });
        }
        function checkData(num){
          var name = $("#name").val();
          var mobile = $("#mobile").val();
          //var password = $("#password").val();
          var department = $("#department").val();
          var jobId = $("#jobId").val();
          var employeeNo = $("#employeeNo").val();
          if(employeeNo==""){
             $.messager.alert("提示", "请输入员工编号", "info");
			 return false;
          }else{
             if(employeeNo.substring(0,1)!="2"){
               $.messager.alert("提示", "员工编号请以2打头!", "info");
			   return false;
             }
          }
           if(mobile==""){
             $.messager.alert("提示", "请输入手机号", "info");
			 return false;
          } 
          if(name==""){
             $.messager.alert("提示", "请输入姓名", "info");
			 return false;
          }
          if(num==0){
            /* if(password==""){
              $.messager.alert("提示", "请输入密码", "info");
			  return false;
            } */
          }else{
            if(!$("#check").is(':checked')){
               $.messager.alert("提示", "请阅读公司规章制度", "info");
               return false;
            }
          }
           if(department==-1){
             $.messager.alert("提示", "请选择所属部门", "info");
			 return false;
          }
           if(jobId==-1){
             $.messager.alert("提示", "请选择职位", "info");
			 return false;
          }
          return true;
        }
        // 查看赋值
        function setInfo(datas) {
            $("#department").val(datas.departId);
            getJob(datas.departId,datas.userInfo.jobId);
            $("#name").val(datas.userInfo.name);
            //$("input [name='projectId"]').val(datas.userId);
            $("#username").val(datas.userInfo.username);
            $("#mobile").val(datas.userInfo.mobile);
            $("#telephone").val(datas.telephone);
            $("#email").val(datas.email);
            if(datas.employeeNo!=0){
              $("#employeeNo").val(datas.employeeNo);
            }else{
              isFrist = 1;
            }     
            $("#identityNo").val(datas.identityNo); 
            readIdCard(datas.identityNo);
            $("#staffType").val(datas.staffType);
            $("#entryDate").datebox('setValue', zjzm.formatDate(datas.entryDate, '/'));
            getCompanyAge(datas.entryDate,2);
            $("#entranceGuardCardNumber").val(datas.entranceGuardCardNumber);
            $("#politicalStatus").val(datas.politicalStatus);
            $("#titleQuality").val(datas.titleQuality);
            $("#graduateInstitutions").val(datas.graduateInstitutions);
            $("#studyMajor").val(datas.studyMajor);
            $("#education").val(datas.education);  
            $("#firstWorkDate").datebox('setValue', zjzm.formatDate(datas.firstWorkDate, '/'));
            $("#registeredResidence").val(datas.registeredResidence);
            $("#presentAddress").val(datas.presentAddress);
            $("#contract1").val(datas.contract1);
            $("#contract2").val(datas.contract2);
            $("#contract3").val(datas.contract3);
            $("#contractDueDate").datebox('setValue', zjzm.formatDate(datas.contractDueDate, '/'));
            if(datas.socialSecurityBase){
            	$("#socialSecurityBase").val(datas.socialSecurityBase);
            }
            $("#bank").val(datas.bank);  
            $("#memo").val(datas.memo);
            $("#bankcardNum").val(datas.bankcardNum);  
            $("#jobStatus").val(datas.jobStatus);
            $("#shortTelephone").val(datas.shortTelephone);
            $("#virtualCornet").val(datas.virtualMobile);     
            $("#accStatus").val(datas.userInfo.accStatus);
            $("#userId").val(datas.userId);
            //$("#password").hide();
            //$("#pwd").html("");
        }

        function queryProduct(){
            var param = {};
            param.name = $('#name-search').val();
            param.mobile = $('#mobile-search').val();
            if($('#jobName-search').val()!=-1){
              param.jobId = $('#jobName-search').val();
            }
            param.telephone = $('#telephone-search').val();
            if($('#departmentId-search').val()!=-1){
               param.departmentId = $('#departmentId-search').val();
            }
            if($('#jobStatusSerach').val()!=-1){
               param.jobStatus = $('#jobStatusSerach').val();
            }
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }

        function showBindMenu(id,roleType,department){
		$("#menu_tree").tree({
			checkbox:true,
			url:"${ctx}/console/menu.htm?action=userMenuTree&userId="+id+"&timestamp="+new Date().getTime()+"&roleType="+roleType+"&department="+department,
			loadFilter:function(data){
	  			 if(data.success){
	  				 return data.userMenu.children;
	  			 }else{
	  				 $.messager.alert("警告",data.resultMsg,"error");
	  			 }
	  		 }
		});

		$("#menu_bind").dialog({
			closed:false,
			buttons:[
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var nodes = $.merge($("#menu_tree").tree("getChecked"),$("#menu_tree").tree("getChecked","indeterminate"));
			        			var ids = "";
			        			if(nodes.length>0){
			        				for(var i=0;i<nodes.length;i++){
			        					if(i==0){
			        						ids += nodes[i].id;
			        					}else{
			        						ids += ","+nodes[i].id;
			        					}
			        				}
			        			}
			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/menu.htm",
			        					{action:"bindMenu",menus:ids,userId:$("#datagrid").datagrid("getSelected").userId},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$.messager.alert("信息","绑定成功！","info");
			        							$('#menu_bind').dialog('close');
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#menu_bind').dialog('close');
			        	 }
			         }]
		});
	}


	function showBindAuth(id){
		$("#auths").children().remove();
		$.post("${ctx}/console/auth.htm",
				{action:"userGroupAuth",userId:id},
				function(data){
					if(data.success){
						if(!$.isEmptyObject(data.authGroup)){
							var authTab = $("<table class='auth_tab'></table>").appendTo($("#auths"));
							var auths = data.authGroup;
							for(var key in auths){
								var gp = auths[key];
								if($.isArray(gp)&&gp.length>0){
									var block = $("<tr></tr>").append($("<td class='auth_title'>"+key+"</td>")).appendTo(authTab);
									var tdgp = $("<td></td>").appendTo(block);
									for(var i=0;i<gp.length;i++){
										$("<label class='auth_item'><input type='checkbox' name='auth' value='"+gp[i].id+"' "+(gp[i].checked?"checked='checked'":"")+"/>"+gp[i].authName+"</label>").appendTo(tdgp);
									}
								}
							}
						}
					}else{
						$.messager.alert("错误","加载权限数据出错！","error");
					}
				},"json");

		$("#auth_bind").dialog({
			closed:false,
			buttons:[
					{
						id : "selectAllAuth",
					    iconCls: 'icons-true',
					    text: '全选',
					    handler: function () {
					    	isSelectAllAuth();
					    }
					},
			         {
			        	 iconCls: 'icons-true',
			        	 text:'确定',
			        	 handler:function(){
			        		 var chks = $("#auths input[type='checkbox']:checked");
			        			var auths = "";
			        			for(var i=0;i<chks.length;i++){
			        				if(i==0){
			        					auths += $(chks[i]).val();
			        				}else{
			        					auths += ","+$(chks[i]).val();
			        				}
			        			}
			        			var userId = $("#datagrid").datagrid("getSelected").userId;

			        			$.messager.progress({interval:200,text:'处理中...'});
			        			$.post("${ctx}/console/auth.htm",
			        					{action:"bindAuth",userId:userId,auths:auths},
			        					function(data){
			        						$.messager.progress("close");
			        						if(data.success){
			        							$("#auth_bind").dialog("close");
			        							$.messager.alert("消息","绑定成功！","info");
			        						}else{
			        							$.messager.alert("错误",data.resultMsg,"error");
			        						}
			        					},
			        					"json");
			         	}
			         },
			         {
			        	 iconCls: 'icons-close',
			        	 text:'取消',
			        	 handler:function(){
			        	 	$('#auth_bind').dialog('close');
			        	 }
			         }]
		});

	}

	/**
	 * 是否全选权限
	 */
	function isSelectAllAuth(){
		var textBtn = $("#selectAllAuth").find("span span").html();
		if(textBtn == "全选"){
			$("#auth_bind input[name='auth']").prop("checked",true);
			$("#selectAllAuth").find("span span").html("反选");
			$("#selectAllAuth").find("span span").removeClass("icons-true");
			$("#selectAllAuth").find("span span").addClass("icons-close");
		} else{
			$("#auth_bind input[name='auth']").prop("checked",false);
			$("#selectAllAuth").find("span span").html("全选");
			$("#selectAllAuth").find("span span").removeClass("icons-close");
			$("#selectAllAuth").find("span span").addClass("icons-true");
		}
	}
	
	$('#identityNo').blur(function () {  
    	readIdCard($(this).val());  
    }); 
     
    $('#entryDate').datebox({
    	onSelect: function(date){
        	getCompanyAge(date,1);
    	}
    });

    
	/**
	 * 根据身份证号自动计算生日、年龄、性别 
	 */	
    function readIdCard(idcard){
       if(idcard){
       	   var year = idcard.substring(6,10);
	       var month = idcard.substring(10,12);
	       var date = idcard.substring(12,14);
	       var num = idcard.substring(16,17);
	       var age = new Date().getFullYear()-year;
	       var birthday = year+"/"+month+"/"+date;
	       var sex = num%2;
	       if(sex>0){
	         $("#sex").val(1);
	         $("#sexSelect").val(1);    
	       }else{
	       	 $("#sex").val(2);
	       	 $("#sexSelect").val(1);
	       }
	       $("#age").val(age);
	       $("#birthday").val(birthday);
       }
    }
    
    /**
	 * 根据入职日期自动计算司龄
	 */	
	 function getCompanyAge(entryDate,type){
	    if(type=="1"){
	    	var date = (entryDate).toISOString().slice(0,4);
	    }else{
	    	var date = entryDate.substring(0,4);
	    }
	 	var now =  new Date();
	 	var year = now.getFullYear();
	 	if(entryDate){
	 		$("#companyAge").val(year-date);
	 	}
	 	
	 }
	 
	 function getData(){
	    $.ajax({
                url: "/talent/user/getTalentInfomation.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	identityNo:$("#identityNo").val()
                },
                success: function (data) {
                    if (data.success) {
                        var datas = data.data[0];
                        $("#name").val(datas.name);  
			          	$("#mobile").val(datas.mobile);  
			          	$("#politicalStatus").val(datas.politicalStatus);
			          	$("#titleQuality").val(datas.qualification);
			          	$("#presentAddress").val(datas.address);
			          	$("#email").val(datas.mail);
			          	talentId = datas.id;
			          	var dom = "", box = $("#jobTable");
			          	for(var i=0;i<datas.talentJob.length;i++){
			                   if(datas.talentJob[i].startDate){
			                      	   if(datas.talentJob[i].endDate){
			                      	   dom += "<tr>"+
			                       	   "<td width='25%'>"+ datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~"+ datas.talentJob[i].endDate.substring(0,4)+"/"+datas.talentJob[i].endDate.substring(4,6) +"</td>"+
			                       	   "<td width='25%'>"+ datas.talentJob[i].companyName +"</td>"+
								       "<td width='25%'>"+ datas.talentJob[i].post +"</td>"+
								       "<td width='25%'></td>"+
					                   "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                   "</tr>";
			                      	   }else{
			                      	    dom += "<tr>"+
			                       	   "<td width='25%'>"+datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~至今</td>"+
			                       	   "<td width='25%'>"+ datas.talentJob[i].companyName +"</td>"+
									   "<td width='25%'>"+ datas.talentJob[i].post +"</td>"+
								       "<td width='25%'></td>"+
					                   "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                   "</tr>";
			                      	   }
			                      	}else{
			                      		 dom += "<tr>"+
			                      		 "<td width='25%'></td>"+
			                      	     "<td width='25%'>"+ datas.talentJob[i].companyName +"</td>"+
								    	 "<td width='25%'>"+ datas.talentJob[i].post +"</td>"+
							         	 "<td width='25%'></td>"+
					                     "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                     "</tr>";
			                      	}	
			                      }
			                      box.html(dom);                        	
			                      var familyDom = "", familyBox = $("#familyTable");
			                      for(var i=0;i<datas.talentFamily.length;i++){
			                        	familyDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].relation +"</td>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].name +"</td>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].company +"</td>"+
									                "<td width='20%'>"+ datas.talentFamily[i].telephone +"</td>"+
					                                "<td width='20%'><a href='javascript:getFamilyData(\""+datas.talentFamily[i].relation+"\",\""+datas.talentFamily[i].name+"\",\""+datas.talentFamily[i].company+"\",\""+datas.talentFamily[i].telephone+"\",\""+datas.talentFamily[i].post+"\",\""+datas.talentFamily[i].id+"\",\""+datas.talentFamily[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        }
			                      familyBox.html(familyDom);
			                      var educationDom = "",educationBox = $("#educationTable");
			                      for(var i=0;i<datas.talentEducation.length;i++){
			                        if(datas.talentEducation[i].startDate){
			                        	if(datas.talentEducation[i].endDate){
			                        		educationDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~"+ datas.talentEducation[i].endDate.substring(0,4)+"/"+ datas.talentEducation[i].endDate.substring(4,6)+"</td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";									                
			                        	}else{
			                        		educationDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~至今</td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        	}
			                        }else{
			                        	educationDom += "<tr>"+
				                    				"<td width='20%'></td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        }
			                        	
			                        }
			                        educationBox.html(educationDom);			                       
			                        readIdCard($("#identityNo").val());
                    } 
                }
            });	
	 }
	 
	 function getEducationData(startDate,endDate,schoolName,professional,certificate,id,talentIds){
			$("#educationDetail").show();
            $("#educationDetail").dialog({
                title: '人才学历信息',
                width: 400,
                height: 400,
                closable: false,
                onBeforeOpen: function () {
                    if(schoolName && professional){
                    	if(startDate){
                        $("#educationStartDate").datebox('setValue', startDate.substring(0,4)+"/"+startDate.substring(4,6));
		                }
		                if(endDate){
		                	$("#educationEndDate").datebox('setValue', endDate.substring(0,4)+"/"+endDate.substring(4,6));
		                } 
		                $("#educationSchoolName").val(schoolName);
		                $("#educationProfessional").val(professional);
		                $("#educationCertificate").val(certificate);
		                $("#educationId").val(id);
		                $("#educationTalentId").val(talentIds);
		                $("#add").hide();
                    	$("#update").show();
                    	$("#delete").show();
                    }else{
                    	$("#educationTalentId").val(talentId);
                    	$("#add").show();
                    	$("#update").hide();
                    	$("#delete").hide();
                    }
                    
                },
                buttons: [
                      {
                        id:'add',
                        iconCls: 'icons-true',
                        text: '新增',
                        handler: function () {
                        if(!$('#educationStartDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择入学日期", "error");
                            }else if(!$('#educationEndDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择毕业日期", "error");
                            }else if(!$('#educationSchoolName').val()){
                                $.messager.alert("错误", "请输入学校名称", "error");
                            }else if(!$('#educationProfessional').val()){
                                $.messager.alert("错误", "请输入专业名称", "error");
                            }else if(!$('#educationCertificate').val()){
                                $.messager.alert("错误", "请输入学历/证书", "error");
                            } else { 
                              $("#form4").attr("action", "/talent/user/insertTalentEducation.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form4").form("submit", {
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
                                          $("#educationDetail").dialog("close");
                                          $("#form4")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'update',
                        iconCls: 'icons-true',
                        text: '修改',
                        handler: function () {
                        if(!$('#educationStartDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择入学日期", "error");
                            }else if(!$('#educationEndDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择毕业日期", "error");
                            }else if(!$('#educationSchoolName').val()){
                                $.messager.alert("错误", "请输入学校名称", "error");
                            }else if(!$('#educationProfessional').val()){
                                $.messager.alert("错误", "请输入专业名称", "error");
                            }else if(!$('#educationCertificate').val()){
                                $.messager.alert("错误", "请输入学历/证书", "error");
                            } else { 
                              $("#form4").attr("action", "/talent/user/updateTalentEducation.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form4").form("submit", {
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
                                          $("#educationDetail").dialog("close");
                                          $("#form4")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'delete',
                        iconCls: 'icons-clear',
                        text: '删除',
                        handler: function () {
                              $("#form4").attr("action", "/talent/user/deleteTalentEducation.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form4").form("submit", {
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
                                          $("#educationDetail").dialog("close");
                                          $("#form4")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
                                      } else {
                                          $.messager.progress("close");
                                          $.messager.alert("错误",list.resultMsg,"error");
                                      }
                                  }
                              });
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#educationDetail").dialog("close");
                            $("#form4")[0].reset();
                        }
                    }
                ]
            });
		}
		
		
		function getJobData(startDate,endDate,companyName,post,leavingReason,description,reterence,reterenceTel,id,talentIds){
			$("#jobDetail").show();
            $("#jobDetail").dialog({
                title: '人才工作信息',
                width: 400,
                height: 400,
                closable: false,
                onBeforeOpen: function () {
                    if(companyName && post){
                    	if(startDate){
		                    $("#jobStartDate").datebox('setValue', startDate.substring(0,4)+"/"+startDate.substring(4,6));
		                }
		                if(endDate){
		                	$("#jobEndDate").datebox('setValue', endDate.substring(0,4)+"/"+endDate.substring(4,6));
		                } 
		                $("#jobCompanyName").val(companyName);
		                $("#jobPost").val(post);
		                $("#jobLeavingReason").val(leavingReason);
		                $("#jobReterence").val(reterence);
		                $("#jobReterenceTel").val(reterenceTel);
		                $("#description").val(description);
		                $("#jobTalentId").val(talentIds);
		                $("#jobIds").val(id);
		                $("#jobadd").hide();
                    	$("#jobupdate").show();
                    	$("#jobdelete").show();
                    }else{
                    	$("#jobTalentId").val(talentId);
                    	$("#jobadd").show();
                    	$("#jobupdate").hide();
                    	$("#jobdelete").hide();
                    }
                    
                },
                buttons: [
                      {
                        id:'jobadd',
                        iconCls: 'icons-true',
                        text: '新增',
                        handler: function () {
                            if(!$('#jobStartDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择入职日期", "error");
                            }else if(!$('#jobEndDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择离职日期", "error");
                            }else if(!$('#jobCompanyName').val()){
                                $.messager.alert("错误", "请输入公司名称", "error");
                            }else if(!$('#jobPost').val()){
                                $.messager.alert("错误", "请输入职务", "error");
                            } else {  
                              $("#form3").attr("action", "/talent/user/insertTalentJob.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form3").form("submit", {
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
                                          $("#jobDetail").dialog("close");
                                          $("#form3")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'jobupdate',
                        iconCls: 'icons-true',
                        text: '修改',
                        handler: function () {
                              if(!$('#jobStartDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择入职日期", "error");
                              }else if(!$('#jobEndDate').datebox('getValue')){
                                $.messager.alert("错误", "请选择离职日期", "error");
                              }else if(!$('#jobCompanyName').val()){
                                $.messager.alert("错误", "请输入公司名称", "error");
                              }else if(!$('#jobPost').val()){
                                $.messager.alert("错误", "请输入职务", "error");
                              } else { 
                              $("#form3").attr("action", "/talent/user/updateTalentJob.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form3").form("submit", {
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
                                          $("#jobDetail").dialog("close");
                                          $("#form3")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'jobdelete',
                        iconCls: 'icons-clear',
                        text: '删除',
                        handler: function () {
                              $("#form3").attr("action", "/talent/user/deleteTalentJob.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form3").form("submit", {
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
                                          $("#jobDetail").dialog("close");
                                          $("#form3")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
                                      } else {
                                          $.messager.progress("close");
                                          $.messager.alert("错误",list.resultMsg,"error");
                                      }
                                  }
                              });
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#jobDetail").dialog("close");
                            $("#form3")[0].reset();
                        }
                    }
                ]
            });
		}
		
		function getFamilyData(relation,name,company,telephone,post,id,talentIds){
			$("#familyDetail").show();
            $("#familyDetail").dialog({
                title: '人才家属信息',
                width: 400,
                height: 400,
                closable: false,
                onBeforeOpen: function () {
                    if(name && relation ){
                    	$("#familyName").val(name);
		                $("#familyPost").val(post);
		                $("#familyRelation").val(relation);
		                $("#familyCompany").val(company);
		                $("#familyTelephone").val(telephone);
		                $("#familyId").val(id);
		                $("#familyadd").hide();
                    	$("#familyupdate").show();
                    	$("#familydelete").show();
                    	$("#familyTalentId").val(talentIds);
                    }else{
                    	$("#familyTalentId").val(talentId);
                    	$("#familyadd").show();
                    	$("#familyupdate").hide();
                    	$("#familydelete").hide();
                    }
                    
                },
                buttons: [
                      {
                        id:'familyadd',
                        iconCls: 'icons-true',
                        text: '新增',
                        handler: function () {
                              if(!$('#familyName').datebox('getValue')){
                                $.messager.alert("错误", "请输入姓名", "error");
                              }else if(!$('#familyRelation').datebox('getValue')){
                                $.messager.alert("错误", "请输入关系", "error");
                              }else if(!$('#familyTelephone').val()){
                                $.messager.alert("错误", "请输入电话", "error");
                              } else { 
                              $("#form2").attr("action", "/talent/user/insertTalentFamily.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form2").form("submit", {
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
                                          $("#familyDetail").dialog("close");
                                          $("#form2")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'familyupdate',
                        iconCls: 'icons-true',
                        text: '修改',
                        handler: function () {
                        if(!$('#familyName').datebox('getValue')){
                                $.messager.alert("错误", "请输入姓名", "error");
                              }else if(!$('#familyRelation').datebox('getValue')){
                                $.messager.alert("错误", "请输入关系", "error");
                              }else if(!$('#familyTelephone').val()){
                                $.messager.alert("错误", "请输入电话", "error");
                              } else {
                              $("#form2").attr("action", "/talent/user/updateTalentFamily.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form2").form("submit", {
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
                                          $("#familyDetail").dialog("close");
                                          $("#form2")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
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
                        id:'familydelete',
                        iconCls: 'icons-clear',
                        text: '删除',
                        handler: function () {
                              $("#form2").attr("action", "/talent/user/deleteTalentFamily.htm");
                              //$("#companyId").val(list.companyId);
                              $("#form2").form("submit", {
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
                                          $("#familyDetail").dialog("close");
                                          $("#form2")[0].reset();
                                          $.messager.progress("close");
                                          $.messager.alert("信息","设置成功!","info");
                                          reGetDate(talentId);
                                      } else {
                                          $.messager.progress("close");
                                          $.messager.alert("错误",list.resultMsg,"error");
                                      }
                                  }
                              });
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#familyDetail").dialog("close");
                            $("#form2")[0].reset();
                        }
                    }
                ]
            });
		}
		
		function formatDates(strDate){
	       return (new Date(strDate)).Format("yyyy/MM");
		}
		
		function reGetDate(talentId){
	    $.ajax({
                url: "/talent/user/getTalentInfomation.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	id: talentId
                },
                success: function (data) {
                    if (data.success) {
                        var datas = data.data[0];
			          	var dom = "", box = $("#jobTable");
			          	for(var i=0;i<datas.talentJob.length;i++){
			                   if(datas.talentJob[i].startDate){
			                      	   if(datas.talentJob[i].endDate){
			                      	   dom += "<tr>"+
			                       	   "<td width='20%'>"+ datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~"+ datas.talentJob[i].endDate.substring(0,4)+"/"+datas.talentJob[i].endDate.substring(4,6) +"</td>"+
			                       	   "<td width='20%'>"+ datas.talentJob[i].companyName +"</td>"+
								       "<td width='20%'>"+ datas.talentJob[i].post +"</td>"+
								       "<td width='20%'></td>"+
					                   "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                   "</tr>";
			                      	   }else{
			                      	    dom += "<tr>"+
			                       	   "<td width='20%'>"+datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~至今</td>"+
			                       	   "<td width='20%'>"+ datas.talentJob[i].companyName +"</td>"+
									   "<td width='20%'>"+ datas.talentJob[i].post +"</td>"+
								       "<td width='20%'></td>"+
					                   "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                   "</tr>";
			                      	   }
			                      	}else{
			                      		 dom += "<tr>"+
			                      		 "<td width='20%'></td>"+
			                      	     "<td width='20%'>"+ datas.talentJob[i].companyName +"</td>"+
								    	 "<td width='20%'>"+ datas.talentJob[i].post +"</td>"+
							         	 "<td width='20%'></td>"+
					                     "<td width='20%'><a href='javascript:getJobData(\""+datas.talentJob[i].startDate+"\",\""+datas.talentJob[i].endDate+"\",\""+datas.talentJob[i].companyName+"\",\""+datas.talentJob[i].post+"\",\""+datas.talentJob[i].leavingReason+"\",\""+datas.talentJob[i].description+"\",\""+datas.talentJob[i].reterence+"\",\""+datas.talentJob[i].reterenceTel+"\",\""+datas.talentJob[i].id+"\",\""+datas.talentJob[i].talentId+"\")'>详细</a></td>"+
					                     "</tr>";
			                      	}	
			                      }
			                      box.html(dom);                        	
			                      var familyDom = "", familyBox = $("#familyTable");
			                      for(var i=0;i<datas.talentFamily.length;i++){
			                        	familyDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].relation +"</td>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].name +"</td>"+
				                    				"<td width='20%'>"+ datas.talentFamily[i].company +"</td>"+
									                "<td width='20%'>"+ datas.talentFamily[i].telephone +"</td>"+
					                                "<td width='20%'><a href='javascript:getFamilyData(\""+datas.talentFamily[i].relation+"\",\""+datas.talentFamily[i].name+"\",\""+datas.talentFamily[i].company+"\",\""+datas.talentFamily[i].telephone+"\",\""+datas.talentFamily[i].post+"\",\""+datas.talentFamily[i].id+"\",\""+datas.talentFamily[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        }
			                      familyBox.html(familyDom);
			                      var educationDom = "",educationBox = $("#educationTable");
			                      for(var i=0;i<datas.talentEducation.length;i++){
			                        if(datas.talentEducation[i].startDate){
			                        	if(datas.talentEducation[i].endDate){
			                        		educationDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~"+ datas.talentEducation[i].endDate.substring(0,4)+"/"+ datas.talentEducation[i].endDate.substring(4,6)+"</td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";									                
			                        	}else{
			                        		educationDom += "<tr>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~至今</td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        	}
			                        }else{
			                        	educationDom += "<tr>"+
				                    				"<td width='20%'></td>"+
				                    				"<td width='20%'>"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].professional +"</td>"+
									                "<td width='20%'>"+ datas.talentEducation[i].certificate +"</td>"+
					                                "<td width='20%'><a href='javascript:getEducationData(\""+datas.talentEducation[i].startDate+"\",\""+datas.talentEducation[i].endDate+"\",\""+datas.talentEducation[i].schoolName+"\",\""+datas.talentEducation[i].professional+"\",\""+datas.talentEducation[i].certificate+"\",\""+datas.talentEducation[i].id+"\",\""+datas.talentEducation[i].talentId+"\")'>详细</a></td>"+
					                                "</tr>";
			                        }
			                        	
			                        }
			                        educationBox.html(educationDom);			                       
			                        readIdCard($("#identityNo").val());
                    } 
                }
            });	
	 }
	 function PreviewImg(file,fileNameId,userId) {
	// var length = $("#address").find("img").length;
	var flag = false;
	if (file.files && file.files.length > 0){
		for (var i = 0; i < file.files.length; i++){
			if(checkFile(file.files[i].name) != 1){
				$.messager.alert("错误", "请上传图片格式的文件！", "error");
				flag = true;
				break;
			}
		}
		if (flag){
			return;
		}
		$("#form"+fileNameId).attr("action","/fileManage/user/staffFileUpLoad.htm");
		$("#form"+fileNameId).form("submit", {
			url : "/fileManage/user/staffFileUpLoad.htm",
			onSubmit : function() {
				$.messager.progress({
					interval : 200,
					text : '处理中...'
				});
				return true;
			},
			success : function(data) {
			    var list = JSON.parse(data);
			    if(list.success){	
				   showImg(data,fileNameId,list.id,userId);
			    }else{
			       $.messager.progress("close");
			       $.messager.alert("错误", list.resultMsg, "error");
			    }
			}
		});		
	}
}
	function showImg(img,fileNameId,id,userId,address){
	if(img){
	  var image=eval('(' + img + ')');
	}else{
	  var image="";
	}
	var picBox = $("#picaddress"+fileNameId+" .pic-box");
	//var btnBox = $("#address .add-box");
	var historyPictureBefore = $("#documentTable"+fileNameId+" input[name='historyPicture']").val();
	if(isEmpty(historyPictureBefore)){
	    if(image){
	    	$("#documentTable"+fileNameId+" input[name='historyPicture']").val(image.imgUrl);
	    }else{
	    	$("#documentTable"+fileNameId+" input[name='historyPicture']").val(address);
	    }
		
	} else{
	    if(image){
	    	$("#documentTable"+fileNameId+" input[name='historyPicture']").val(historyPictureBefore + "," + image.imgUrl);
	    }else{
	    	$("#documentTable"+fileNameId+" input[name='historyPicture']").val(historyPictureBefore + "," + address);
	    }	
	}
	var historyPictureAfter = $("#documentTable"+fileNameId+" input[name='historyPicture']").val();
	var data = historyPictureAfter.split(',');
	picBox.empty();
	var picture = "<ul class='imgZoom'>";
	picBox.append(picture);
	//btnBox.append(addBtn);
	if (data.length > 0) {
		/*var picture = '<div style="float: left; margin-right: 10px; border: 1px solid #ddd; padding: 5px; border-radius: 3px; position: relative; width: 100px; height: 100px;">'
				+ '<img style="position: absolute; width: 100px; height: 100px; border: 0; z-index: 0;" onclick="doShowImgEffect(this.src)" src='
				+ historyPicture + ' ></div>';*/
		for(var i=0; i<data.length; i++){
			picture += "<li style='float: left; margin: 5px;'><img style='width:100px;height:100px;' data-original='"+ data[i] +"' src='"+ data[i] +"'/><a href='javascript:deleteFile("+id+","+userId+")'>x</a></li>";
		}
	    //图片放大
	    setTimeout(function () {
	    	$('#documentTable'+fileNameId).find(".imgZoom").viewer();
	    },);
		//btnBox.find("div").hide();
		picBox.append(picture);
	}
	$.messager.progress("close");
	picBox.append("</ul>");
	
} 
	
	function getFileName(userId){
		$.ajax({
                url: "/baseFile/user/getFileName.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    categoryId : 29,
                    attributeId : 2
                },
                success: function (data) {
                    if (data.success) {
                        var box = $("#documentDetail");
                        var dom="";
                        for(var i=0;i<data.data.length;i++){
                            dom +="<form id='form"+data.data[i].id+"' class='inner-q' enctype='multipart/form-data' method='post'><table style='width:95%'id='documentTable"+data.data[i].id+"' class='documentTable' class='diolag-table' cellpadding='0' cellspacing='0' style='border: 1px solid #ddd'><tr id='addressTr'>"+
         		                  "<th style='width:7% !important'>"+data.data[i].name+"</th>"+
         		                  "<td id='picaddress"+data.data[i].id+"' name='address'><div class='add-box'>"+
         		                  "<div style='float: left; position: relative; width: 100px; height: 100px;padding:5px;margin-right: 10px;'>"+
								  "<span style='display: block; border: 1px dotted #ddd; position: absolute; width: 100px; height: 100px; font-size: 50px; text-align: center; line-height: 100px;'>+</span>"+
								  "<input type='file' name='fileAddress'  multiple style='opacity: 0; width: 100px; height: 100px; position: absolute; z-index: 3;'  onchange='PreviewImg(this,"+data.data[i].id+","+userId+")' /></div>"+
							      "</div><div class='pic-box'></div></td><td><input type='hidden' id='historyPicture' name='historyPicture'></td></tr></table>"+
							      "<input type='hidden'name='adminId' value='"+userId+"' /><input type='hidden' name='fileId' value='"+data.data[i].id+"' /></form>";
							      
                        }
                        box.empty();
                        box.append(dom);
                        getFile(userId);
                    }
                }
            });
	}       
	
	function getFile(userId){
		$.ajax({
                url: "/fileManage/user/getStaffFile.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    adminId:userId
                },
                success: function (data) {
                    if (data.success) {
                       for(var i=0;i<data.data.length;i++){
                           showImg("",data.data[i].fileId,data.data[i].id,userId,data.data[i].fileAddress);
                       }
                    }
                }
            });
	
	}  
	
	function getwelcome(){
		$("#imgDiv").show();
        $("#imgDiv").dialog({
             width: windowW+5,
             height: windowH+110, 
             title:""   
       });

       $("#imgDiv").animate({opacity: "0"}, 8000, function () {
       	$("#imgDiv").dialog("close");
       	$("#imgDiv").css({"opacity": 1});
       });
	}
	
	function deleteFile(id,userId){
	    $.ajax({
                url: "/fileManage/user/deleteFileUploadById.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    id : id
                },
                success: function (data) {
                    if (data.success) {
                       $.messager.alert("信息","删除成功!","info");
                       getFileName(userId);
                    }else{
                       $.messager.alert("错误", data.resultMsg, "error");
                    }
                }
            });
	}
    </script>
</body>
    <jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>