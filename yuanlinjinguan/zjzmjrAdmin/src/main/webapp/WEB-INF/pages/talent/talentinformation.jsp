
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>人才信息</title>
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
        #detailInfo{
			padding:10px;
			
		}
        #detailInfo .panel div{
            padding-right:10px;
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
                               <input type="text" id="nameSearch" name="name" class="input">
                            </td>
                            <th>联系电话</th>
                            <td>
                               <input type="text" id="mobileSearch" name="mobile" class="input">
                            </td>
                            <th>身份证号</th>
                            <td>
                                <input type="text" id="identityNoSearch" name="identityNo" class="input">
                            </td>
                            <th>录入时间</th>
                            <td>
                                <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>专业名称</th>
                            <td>
                                <input type="text" id="qualificationSearch" name="qualification" class="input">
                            </td>
                            <th>应聘职位</th>
                            <td>
                                <select  id="recruitmentNameSearch" name="recruitmentId" name=""class="select">
                                 	<option value='-1'>不限</option>
                                </select>
                            </td>                           
                            <th>期望税前年收入</th>
                            <td>
                                <input type="text" id="expectedIncomeSearch" name="expectedIncome" class="input">
                            </td>
                            <th>可到岗日期</th>
                            <td>
                               <input type="text" id="comeDateSearch" name="comeDate" class="easyui-datebox e-input" data-options="editable:true,formatter:formatDate"/>
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
    <div id="detailInfo" style="display:none;">
        <form id="form1" class="inner-q" method="post">
        	<div>必填信息</div>
            	<table class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd; padding-right: 5px">
                	<tbody>
                    	<tr>
                        <th>姓名</th>
                        <td>
                            <input type="text" id="name" name="name" readonly class="input" />
                        </td>
                        <th>身份证号</th>
                        <td>
                            <input type="text" id="identityNo" name="identityNo" readonly  class="input" />
                        </td>
                        <th>手机号码</th>
                        <td>
                            <input type="text" id="mobile" name="mobile" readonly class="input" />
                        </td>
                    </tr>
                    <tr>
                    	<th>应聘职位</th>
                    	<td>
                    		<input type="text" id="recruitment" name="recruitment" readonly class="input" />
                    	</td>
                    	<th>可到岗日期</th>
                    	<td>
                    		<input type="text" id="comeDate" name="comeDate" readonly class="input" />
                    	</td>
                    	<th>期望税前年收入</th>
                    	<td>
                    		<input type="text" id="expectedIncome" name="expectedIncome" readonly class="input" />
                    	</td>
                    </tr>
                </tbody>
            </table>
         <div>基本信息</div>
         	<table class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         		<tr>
         			<th>民族</th>
         			<td><input type="text" id="nation" name="nation" readonly class="input"/></td>
         			<th>籍贯</th>
         			<td><input type="text" id="origin" name="origin" readonly class="input"/></td>
         			<th>婚姻状况</th>
         			<td>
         				 <label>
                                <input type="radio" name="maritalStatus" disabled value="90"/>
                               	 未婚                                                      
                         </label>
                         <label>
                                <input type="radio" name="maritalStatus" disabled value="91">
                               	 已婚                                                      
                         </label>
                         <label>
                                <input type="radio" name="maritalStatus" disabled value="92"/>
                               	离异                                                      
                         </label>
                         <label>
                                <input type="radio" name="maritalStatus" disabled value="93"/>
                               	 丧偶                                                     
                         </label>
         			</td>
         		</tr>
         		<tr>
         			<th>生育状况</th>
         			<td clospan="2">
         				 <label>
                                <input type="radio" name="fertilityStatus" disabled value="94"/>
                               	 未育                                                     
                         </label>
                         <label>
                                <input type="radio" name="fertilityStatus" disabled value="95"/>
                               	一子                                                     
                         </label>
                         <label>
                                <input type="radio" name="fertilityStatus" disabled value="96"/>
                               	两子及以上                                                     
                         </label>
         			</td>
         			<th>驾照类型</th>
         			<td><input type="text" id="drivingLicenseType" name="drivingLicenseType" readonly class="input"/></td>
         			<th>政治面貌</th>
         			<td>
         				<select id="politicalStatus" name="politicalStatus" disabled>
                            <option value='-1'></option>
                        </select>
                    </td>
         		</tr>
         		<tr>
         			<th>专业资格/职称</th>
         			<td><input type="text" id="qualification" name="qualification" readonly class="input"/></td>
         			<th>现住址</th>
         			<td><input type="text" id="address" name="address" readonly class="input"/></td>
         			<th>电子邮箱</th>
         			<td><input type="text" id="mail" name="mail" readonly class="input"/></td>
         		</tr>
         		<tr>
         			<th>身高(cm)</th>
         			<td><input type="text" id="emergencyPerson" name="emergencyPerson" readonly class="input"/></td>
         			<th>体重(kg)</th>
         			<td><input type="text" id="emergencyMobile" name="emergencyMobile" readonly class="input"/></td>
         			<th></th>
         			<td></td>
         		</tr>
         	</table>
            <div>教育背景</div>
         	<table id ="educationTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div>工作背景</div>
         	<table id="jobTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div>家庭成员</div>
         	<table id="familyTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div>备注信息</div>
         	<table class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	  <tr>
         	     <th><input disabled type="radio" name="haveAcquaintance" value="0"/>否&nbsp;&nbsp;&nbsp;<input disabled type="radio" name="haveAcquaintance" value="1"/>是</th><td colspan="3">&nbsp;&nbsp;&nbsp;是否有在本公司熟悉的人</td>
        	     <th></th><td width="13%"></td>
        	     <th></th><td width="13%"></td>
        	  </tr>
        	  <tr id="hiddenTrs">
         	     <th>姓名</th><td><input type="text" id="acquaintanceName" name="acquaintanceName" class="input" readonly/></td>
         	     <th>部门</th><td><input type="text" id="acquaintanceDepartment" name="acquaintanceDepartment" class="input" readonly/></td>
         	     <th>关系</th><td><input type="text" id="acquaintanceRelation" name="acquaintanceRelation" class="input" readonly/></td>
        	  </tr>
        	  <tr>
        	  	 <th><input type="radio" name="haveCriminal" disabled value="0"/>否&nbsp;&nbsp;&nbsp;<input type="radio" name="haveCriminal" disabled value="1"/>是</th><td>&nbsp;&nbsp;&nbsp;是否有犯罪记录</td>
        	  	 <th></th><td></td>
        	     <th></th><td></td>
        	  </tr>
        	  <tr>
        	  	 <th><input type="radio" name="haveFired" disabled value="0"/>否&nbsp;&nbsp;&nbsp;<input type="radio" name="haveFired" disabled value="1"/>是</th><td>&nbsp;&nbsp;&nbsp;曾被解雇过吗</td>
        	     <th></th><td></td>
        	     <th></th><td></td>
        	  </tr>
        	  <tr>
        	  	 <th><input type="radio" name="haveDiseases" disabled value="0"/>否&nbsp;&nbsp;&nbsp;<input type="radio" name="haveDiseases" disabled value="1"/>是</th><td colspan="3">&nbsp;&nbsp;&nbsp;是否患有先天性重大疾病或传染性疾病或身体残疾</td>
        	  	 <th></th><td></td>
        	  </tr>
        	  <tr>
        	  	 <th><input type="radio" name="haveCompetition" disabled value="0"/>否&nbsp;&nbsp;&nbsp;<input type="radio" name="haveCompetition" disabled value="1"/>是</th><td colspan="3">&nbsp;&nbsp;&nbsp;是否签署目前仍有效的“竞业禁止”协议或相关劳动条款?</td>
        	  	 <th></th><td></td>
        	  </tr>
         	</table>
         	<div>附件</div>
         	<table id="documentTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	</table>
         	<div>签名</div>
         	<table id="signTable" class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd">
         	        <tr id="addressTr">
						<td id="address" name="address" colspan="7">
							<div class='add-box' id="add-box">
								
							</div>
							<div class='pic-box'></div>
						</td>
						<td><input type="hidden" id="historyPicture"
							name="historyPicture"></td>
					</tr>
         	</table>
         	<table id ="familyDetail" class="diolag-table" cellpadding="0" cellspacing="0" border="0" style="display:none">
         	   <tr>
         	     <th>姓名</th><td><input type="text" id="familyName" name="familyName" class="input" readonly/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>关系</th><td><input type="text" id="familyRelation" name="familyRelation" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>工作单位或地址</th><td><input type="text" id="familyCompany" name="familyCompany" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>职务</th><td><input type="text" id="familyPost" name="familyPost" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>电话</th><td><input type="text" id="familyTelephone" name="familyTelephone" class="input" readonly /></td>
        	   </tr>
         	</table>
         	<table id = "jobDetail" class="diolag-table" cellpadding="0" cellspacing="0" border="0" style="display:none">
         	   <tr>
         	     <th>入职年月</th><td><input type="text" id="jobStartDate" name="jobStartDate" class="input" readonly/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>离职年月</th><td><input type="text" id="jobEndDate" name="jobEndDate" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>公司名称</th><td><input type="text" id="jobCompanyName" name="jobCompanyName" class="input"  readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>职务</th><td><input type="text" id="jobPost" name="jobPost" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>工作描述</th><td><textarea id="jobDescription" name="jobDescriptiont" disabled></textarea></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>离职原因</th><td><input type="text" id="jobLeavingReason" name="jobLeavingReason" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>证明人</th><td><input type="text" id="jobReterence" name="jobReterence" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>证明人电话</th><td><input type="text" id="jobReterenceTel" name="jobReterenceTel" class="input" readonly /></td>
        	   </tr>
         	</table>
         	<table id = "educationDetail" class="diolag-table" cellpadding="0" cellspacing="0" border="0" style="display:none">
         	   <tr>
         	     <th>入学年月</th><td><input type="text" id="educationStartDate" name="educationStartDate" class="input" readonly/></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>毕业年月</th><td><input type="text" id="educationEndDate" name="educationEndDate" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>学校名称</th><td><input type="text" id="educationSchoolName" name="educationSchoolName" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>专业名称</th><td><input type="text" id="educationProfessional" name="educationProfessional" class="input" readonly /></td>
        	   </tr>
        	   <tr>       	   	
         	     <th>学历/证书</th><td><input type="text" id="educationCertificate" name="educationCertificate"  class="input" readonly /></td>
        	   </tr>
         	</table>
         	<div data-options="region:'center',border:false" class="data-area">
            <div id="datagrid"></div>
            <div id="fileDiv">

           </div>
       </div>
       </form>
       
    </div>
         
    </div>
     <div id="talentInfo" style="display:none;">
       <style>
       		.table-box * {
       			font-size: 12px;
       		}
	        .table-box th {
	        	font-weight: normal;
	        	width: 13%;
	        }
	        .table-box table {
	        	margin-top: 10px;
	        }
	     	.table-box  th, .table-box td {
	     		padding: 5px;
	     	}
	     	
	     	.table-box td, .table-box th {
		      padding: 5px;
		      border-bottom: 1px solid #000;
		      border-left: 1px solid #000;
		      font-weight: normal;
		    }
		    .table-box table {
		      border: 2px solid #000;
		    }
		    .table-box .table2 td, .table-box .table2 th {
		      text-align: center;
		    }
		    .table-box p {
		      margin: 10px 0;
		    }
		    .table-box .tableTitle {
		      border: 0;
		    }
		    .table-box .tableTitle td, .table-box .tableTitle th {
		      border: 0;
		    }
		    .table-box .tableTitle img {
		      width: 60px;
		    }
       </style>
       <div class='table-box'>
       
       <table class="diolag-table" cellpadding="0" cellspacing="0" style="border: 1px solid #ddd; padding-right: 5px">      	
        <form id="form3" class="inner-q" method="post">
        	<table width="100%" border="0" cellspacing="0" cellpadding="0" class='tableTitle'>
			    <tr>
			      <td width='25%' align="left" valign="top"><img src="../../../res/images/123.jpg"></td>
			      <td width='50%' align="center" valign="bottom" style="font-size: 30px;">应聘登记表</td>
			      <td width='25%' align="right" valign="bottom">编号：CBSJY-HR-002</td>
			    </tr>
			  </table>
			  <p>应聘职位：<span id="talentRecruitment"></span></p>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class='table1'>
			    <tr>
			      <td width='10%'>姓名</td>
			      <td width='15%' id="talentName">&nbsp;</td>
			      <td width='10%'>性别</td>
			      <td width='11%' id= "talentSex">&nbsp;</td>
			      <td width='8%'>民族</td>
			      <td width='13%' id="talentNation">&nbsp;</td>
			      <td width='15%'>出生日期</td>
			      <td width='18%' align="right" id="talentBirthday">年  月  日</td>
			    </tr>
			    <tr>
			      <td>籍贯</td>
			      <td id="talentOrigin">&nbsp;</td>
			      <td>婚姻状况</td>
			      <td id="talentMaritalStatus">&nbsp;</td>
			      <td>身高</td>
			      <td><span id="talentEmergencyPerson"></span><span style="float: right">cm</span></td>
			      <td>体重</td>
			      <td><span id="talentEmergencyMobile"></span><span style="float: right">kg</span></td>
			    </tr>
			    <tr>
			      <td>驾照类型</td>
			      <td id="talentDrivingLicenseType">&nbsp;</td>
			      <td>政治面貌</td>
			      <td id="talentPoliticalStatus">&nbsp;</td>
			      <td colspan="2">专业资格/职称</td>
				  <td colspan="2" id="talentQualification">&nbsp;</td>		
			    </tr>
			    <tr>
			      <td>现住址</td>
			      <td colspan="3" id="talentAddress">&nbsp;</td>
			      <td colspan="2">电子信箱</td>
			      <td colspan="2" id="talentMail">&nbsp;</td>
			    </tr>
			    <tr>
			      <td>身份证号</td>
			      <td id="talentIdentityNo">&nbsp;</td>
			      <td>手机号码</td>
			      <td id="talentMobile">&nbsp;</td>
			      <td colspan="2">生育情况</td>
			      <td colspan="2" id="talentFertilityStatus">&nbsp;</td>
			    </tr>
			  </table>
			  <p>教育背景</p>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id ="educationTables">
				  <thead>
				    <tr>
				      <td width='25%'>起讫年月</td>
				      <td width='35%'>学校名称</td>
				      <td width='15%'>专业</td>
				      <td width='25%'>学历/证书</td>
				    </tr>
			      </thead>
			      <tbody>
			      
			      </tbody>
			  </table>
			  <p align="left">工作背景 </p>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id="jobTables">
				  <thead>
				    <tr>
				      <td width='20%'>时间</td>
				      <td width='25%'>工作单位</td>
				      <td width='15%'>职务</td>
				      <td width='20%'>离职原因</td>
				      <td width='20%'>证明人及电话</td>
				    </tr>
			      </thead>
			      <tbody>
			      
			      </tbody>
			  </table>
			  <p align="left">家庭主要成员 </p>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id="familyTables">
				  <thead>
				    <tr>
				      <td width='20%'>姓名</td>
				      <td width='15%'>关系</td>
				      <td width='25%'>工作单位或地址</td>
				      <td width='20%'>职  务</td>
				      <td width='20%'>电话</td>
				    </tr>
				  </thead>
				  <tbody></tbody>
			  </table>
			  <p>备注</p>
			  <table width="100%" border="0" cellspacing="0" cellpadding="0">
			    <tr>
			      <td colspan="2">
			      	<span id="talentHaveAcquaintance" style='padding-right: 10px;'></span>
			      	是否有在本公司熟悉的人？
			      	<span id="hiddenTr">
			      		姓名: <span id="talentAcquaintanceName"></span>部门: <span id="talentAcquaintanceDepartment"></span>关系: <span id="talentAcquaintanceRelation"></span>
			      	</span>
			      </td>
			    </tr>
			    <tr>
			      <td colspan="2">
			      	<span id="talentHaveCriminal" style='padding-right: 10px;'></span>是否有犯罪记录？
			      	<span id="talentHaveDiseases" style='padding-left: 60px;padding-right: 10px;'></span>是否患有先天性重大疾病或传染性疾病或身体残疾？
			      </td>
			    </tr>
			    <tr>
			      <td colspan="2">
			      	<span id="talentHaveFired" style='padding-right: 10px;'></span>曾被解雇过吗？
			      	<span id="talentHaveCompetition" style='padding-left: 72px;padding-right: 10px;'></span>是否签署目前仍有效的“竞业禁止”协议或相关劳动条款？
			      </td>
			    </tr>
			    <tr>
			      <td width="50%">可到岗日期:<span id="talentComeDate"></span></td>
			      <td width="50%">期望的工资（税前总年收入）:<span id="talentExpectedIncome"></span></td>
			    </tr>
			    <tr>
			      <td colspan="2"><p><b>本人兹声明在本表格内所填写各项资料均属正确无讹。本人明白如提供资料及相关证明文件或证书不属实或有误导，本人的申请将被取消，又或本人已受聘，本人亦将被公司无条件解雇。</b> </p>
				  </td>
			    </tr>
			    <tr>
			      <td colspan="2" align="right" valign="bottom">本人签字：<img src='' class='pic-box' style='height: 80px;padding-top:20px;padding-right:60px' />日期：<span id="talentDate" style='padding-right: 20px;' ></span></td>
			    </tr>
			  </table>
       </form>     
       </div>
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();
        var recruitment = getQueryString("recruitmentId");
        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/talent/user/getTalentInfo.htm',
                    title:'人才信息',
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
                    checkOnSelect: false, 
					selectOnCheck: false,
                    queryParams:{    
                         recruitmentId:recruitment
                    },
                    pageList : [ 10,20, 30],
                    columns:[[{
	                    field : 'ck',
	                    checkbox : true
	                    },
                        {
                            field : 'name',
                            title : '姓名',
                            width : 70,
                            align :'center'
                        },
                        {
                            field : 'mobile',
                            title : '联系电话',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'identityNo',
                            title : '身份证号',
                            width : 80,
                            align:'center'
                        },
                        {
                            field : 'recruitment',
                            title : '应聘职位',
                            width : 60,
                            align:'center',
							formatter: function(value,row,index){
							      if(row.recruitment){
							         return row.recruitment.positionName;
							      }
							}
                        },
                        
                         {
                            field : 'comeDate',
                            title : '可到岗日期',
                            width : 90,
                            align:'center'
                        },
                        {
							field : 'expectedIncome',
							title : '期望税前年收入',
							width : 80,
							align : 'center'
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
                            field: 'opt',
                            title: '详细',
                            align: 'center',
                            width: 100,
                            formatter: function (value, row, index) {
                                var oper = "";
                                oper += "<a href='javascript:showDetail(" + index + ")'>查看</a>";
                                return oper;
                            }
                        }
                    ]], 
                   toolbar: [{
                   		id:'insertAuth',
                        iconCls: 'icons-add',
                        text: '提交面试',
                        handler: function () {
                          setInterview();
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
                    	if(${talentSetInterviewAuth} || ${talentExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
		                    if(${talentSetInterviewAuth}){
		                        $("#insertAuth").show();
		                    }else{
		                    	$("#insertAuth").hide();
		                    }
		                    if(${talentExportAuth}){
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
            $("#detailInfo").show();
            if (index != undefined) {
               var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").dialog({
                title: '人才信息详情',
                width: windowW +1,
                height: windowH +1,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        getTalentJob(list.id);
                        getTalentFamily(list.id);
                        getTalentEducation(list.id);
                        getTalentDocument(list.id);
                    }                
                },
                buttons: [
                    {
                        iconCls: 'icons-export',
                        text: '打印',
                        handler: function () {
                           print(list.id);
                        }
                    },
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#detailInfo").dialog("close");
                            $("#form1")[0].reset();
                            $("#picBox").html("");
                        }
                    }
                ]
            });
        }

       function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/talent/user/talentInfoPrint.htm",
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
      	
       // 获取招聘岗位
      	getRecruitment();
        function getRecruitment() {
            $.ajax({
                url: "/recruitment/user/getRecruitmentByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                },
                success: function (data) {
                    if (data.success) {
                        var box = $("#recruitmentNameSearch"),
		                dom = "";
			            for (var i = 0; i < data.data.length; i++) {
			            	if(data.data[i].id==recruitment){
			            		dom += "<option selected value='"+ data.data[i].id +"'>"+ data.data[i].positionName +"</option>";
			            	}else{
			            		dom += "<option value='"+ data.data[i].id +"'>"+ data.data[i].positionName +"</option>";
			            	}
			            }
			            box.append(dom);
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
         // 获取政治面貌
        getBasic(25, createAttribute);
        function createAttribute(datas) {
            var box = $("#politicalStatus"),
                dom = "";
            for (var i = 0; i < datas.length; i++) {
                dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
            }
            box.append(dom);
        }
        
        // 查看赋值
        function setInfo(datas) {
          	$("#name").val(datas.name);  
          	$("#mobile").val(datas.mobile); 
          	$("#identityNo").val(datas.identityNo); 
          	if(datas.recruitment){
          	    $("#recruitment").val(datas.recruitment.positionName);
          	}
          	if(datas.comeDate){
          		$("#comeDate").val(datas.comeDate.substring(0,4)+"/"+datas.comeDate.substring(4,6)+"/"+datas.comeDate.substring(6,8));
          	}
          	$("#expectedIncome").val(datas.expectedIncome);
          	$("#nation").val(datas.nation);
          	$("#origin").val(datas.origin);
          	$(":radio[name='maritalStatus'][value='" + datas.maritalStatus + "']").prop("checked", "checked");
          	$(":radio[name='fertilityStatus'][value='" + datas.fertilityStatus + "']").prop("checked", "checked");
          	$("#drivingLicenseType").val(datas.drivingLicenseType);
          	$("#politicalStatus").val(datas.politicalStatus);
          	$("#qualification").val(datas.qualification);
          	$("#address").val(datas.address);
          	$("#mail").val(datas.mail);
          	if(datas.emergencyPerson){
          		$("#emergencyPerson").val(datas.emergencyPerson);
          	}
          	if(datas.emergencyMobile){
          		$("#emergencyMobile").val(datas.emergencyMobile);
          	}
          	
          	$("#talentName").html(datas.name);  
          	$("#talentMobile").html(datas.mobile); 
          	$("#talentIdentityNo").html(datas.identityNo); 
          	//readIdCard(datas.identityNo);
          	if(datas.recruitment){
          	    $("#talentRecruitment").html(datas.recruitment.positionName);
          	}
          	if(datas.comeDate){
          		$("#talentComeDate").html(datas.comeDate.substring(0,4)+"/"+datas.comeDate.substring(4,6)+"/"+datas.comeDate.substring(6,8));
          	}
          	
          	$("#talentExpectedIncome").html(datas.expectedIncome);
          	$("#talentNation").html(datas.nation);
          	$("#talentOrigin").html(datas.origin);
          	$("#talentMaritalStatus").html(datas.maritalName);
          	$("#talentFertilityStatus").html(datas.fertilityName);
          	$("#talentPoliticalStatus").html(datas.politicalName);
          	$("#talentDrivingLicenseType").html(datas.drivingLicenseType);
          	$("#talentQualification").html(datas.qualification);
          	$("#talentAddress").html(datas.address);
          	$("#talentMail").html(datas.mail);
          	$("#talentMmergencyPerson").html(datas.emergencyPerson);
          	$("#talentMmergencyMobile").html(datas.emergencyMobile);
			          	
          	$(":radio[name='haveAcquaintance'][value='" + datas.haveAcquaintance + "']").prop("checked", "checked");
          	if(datas.haveAcquaintance){
          	    $("#hiddenTrs").show();
          	    $("#acquaintanceName").val(datas.acquaintanceName);
          	    $("#acquaintanceDepartment").val(datas.acquaintanceDepartment);
          	    $("#acquaintanceRelation").val(datas.acquaintanceRelation);
          	}else{
          		$("#hiddenTrs").hide();
          	}
          	$(":radio[name='haveCriminal'][value='" + datas.haveCriminal + "']").prop("checked", "checked");
          	$(":radio[name='haveFired'][value='" + datas.haveFired + "']").prop("checked", "checked");
          	$(":radio[name='haveDiseases'][value='" + datas.haveDiseases + "']").prop("checked", "checked");
          	$(":radio[name='haveCompetition'][value='" + datas.haveCompetition + "']").prop("checked", "checked");
          	setPicture(datas.signature);
        }
        

        function queryProduct(){
            var param = {};
            param.name = $('#nameSearch').val();
            param.mobile = $('#mobileSearch').val();
            param.identityNo = $('#identityNoSearch').val();
            param.qualification = $('#qualificationSearch').val();
            if($('#recruitmentNameSearch').val()!=-1){
            	param.recruitmentId = $('#recruitmentNameSearch').val();
            }
            param.expectedIncome = $('#expectedIncomeSearch').val();
            param.comeDate = $('#comeDateSearch').datebox('getValue');
            param.createTimeStart = $('#createTimeStart').datebox('getValue');
            param.createTimeEnd = $('#createTimeEnd').datebox('getValue');
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }

		function getTalentJob(id){
			$.ajax({
                url: "/talent/user/getTalentJobByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    talentId:id
                },
                success: function (data) {
                    var dom = "",
                        box = $("#jobTable");
                    if (data.success) {                        
                        for(var i=0;i<data.data.length;i++){
                        	
                        	if(data.data[i].startDate){
                        	   if(data.data[i].endDate){
                        	   dom += "<tr>"+
	                        	   "<td width='20%'>"+ data.data[i].startDate.substring(0,4)+"/"+data.data[i].startDate.substring(4,6) +"~"+ data.data[i].endDate.substring(0,4)+"/"+data.data[i].endDate.substring(4,6) +"</td>"+
	                        	   "<td width='20%'>"+ data.data[i].companyName +"</td>"+
							       "<td width='20%'>"+ data.data[i].post +"</td>"+
							       "<td width='20%'></td>"+
						           "<td width='20%'><a href='javascript:getJobData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].companyName+"\",\""+data.data[i].post+"\",\""+data.data[i].leavingReason+"\",\""+data.data[i].reterence+"\",\""+data.data[i].description+"\","+data.data[i].reterenceTel+")'>详细</a></td>"+
						           "</tr>";
                        	   }else{
                        	    dom += "<tr>"+
	                        	   "<td width='20%'>"+data.data[i].startDate.substring(0,4)+"/"+data.data[i].startDate.substring(4,6) +"~至今</td>"+
	                        	   "<td width='20%'>"+ data.data[i].companyName +"</td>"+
								   "<td width='20%'>"+ data.data[i].post +"</td>"+
							       "<td width='20%'></td>"+
						           "<td width='20%'><a href='javascript:getJobData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].companyName+"\",\""+data.data[i].post+"\",\""+data.data[i].leavingReason+"\",\""+data.data[i].reterence+"\",\""+data.data[i].description+"\","+data.data[i].reterenceTel+")'>详细</a></td>"+
						           "</tr>";
                        	   }
                        	}else{
                        		 dom += "<tr>"+
                        		 "<td width='20%'></td>"+
                        	     "<td width='20%'>"+ data.data[i].companyName +"</td>"+
							     "<td width='20%'>"+ data.data[i].post +"</td>"+
						         "<td width='20%'></td>"+
					             "<td width='20%'><a href='javascript:getJobData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].companyName+"\",\""+data.data[i].post+"\",\""+data.data[i].leavingReason+"\",\""+data.data[i].reterence+"\",\""+data.data[i].description+"\","+data.data[i].reterenceTel+")'>详细</a></td>"+
					             "</tr>";
                        	}
	                    	
	                    				
                        }
                        box.html(dom);
                    }else{
                        box.html(dom);
                    }
                }
            });
		}

		function getTalentFamily(id){
			$.ajax({
                url: "/talent/user/getTalentFamilyByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    talentId:id
                },
                success: function (data) {
                    var dom = "",
                        box = $("#familyTable"); 
                    if (data.success) {                     
                        for(var i=0;i<data.data.length;i++){
                        	dom += "<tr>"+
	                    				"<td width='20%'>"+ data.data[i].relation +"</td>"+
	                    				"<td width='20%'>"+ data.data[i].name +"</td>"+
	                    				"<td width='20%'>"+ data.data[i].company +"</td>"+
						                "<td width='20%'>"+ data.data[i].telephone +"</td>"+
					                    "<td width='20%'><a href='javascript:getFamilyData(\""+data.data[i].relation+"\",\""+data.data[i].name+"\",\""+data.data[i].company+"\","+data.data[i].telephone+",\""+data.data[i].post+"\")'>详细</a></td>"+
					                "</tr>";
                        }
                        box.html(dom);
                    }else{
                        box.html(dom);
                    }
                }
            });
		}

		function getTalentEducation(id){
			$.ajax({
                url: "/talent/user/getTalentEducationByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    talentId:id
                },
                success: function (data) {
                    var dom = "",
                        box = $("#educationTable"); 
                    if (data.success) {                    
                        for(var i=0;i<data.data.length;i++){
                        if(data.data[i].startDate){
                        	if(data.data[i].endDate){
                        		dom += "<tr>"+
	                    				"<td width='20%'>"+ data.data[i].startDate.substring(0,4)+"/"+ data.data[i].startDate.substring(4,6)+"~"+ data.data[i].endDate.substring(0,4)+"/"+ data.data[i].endDate.substring(4,6)+"</td>"+
	                    				"<td width='20%'>"+ data.data[i].schoolName +"</td>"+
						                "<td width='20%'>"+ data.data[i].professional +"</td>"+
						                "<td width='20%'>"+ data.data[i].certificate +"</td>"+
					                    "<td width='20%'><a href='javascript:getEducationData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].schoolName+"\",\""+data.data[i].professional+"\",\""+data.data[i].certificate+"\")'>详细</a></td>"+
					                "</tr>";
                        	}else{
                        		dom += "<tr>"+
	                    				"<td width='20%'>"+ data.data[i].startDate.substring(0,4)+"/"+ data.data[i].startDate.substring(4,6)+"~至今</td>"+
	                    				"<td width='20%'>"+ data.data[i].schoolName +"</td>"+
						                "<td width='20%'>"+ data.data[i].professional +"</td>"+
						                "<td width='20%'>"+ data.data[i].certificate +"</td>"+
					                    "<td width='20%'><a href='javascript:getEducationData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].schoolName+"\",\""+data.data[i].professional+"\",\""+data.data[i].certificate+"\")'>详细</a></td>"+
					                "</tr>";
                        	}
                        }else{
                        	dom += "<tr>"+
	                    				"<td width='20%'></td>"+
	                    				"<td width='20%'>"+ data.data[i].schoolName +"</td>"+
						                "<td width='20%'>"+ data.data[i].professional +"</td>"+
						                "<td width='20%'>"+ data.data[i].certificate +"</td>"+
					                    "<td width='20%'><a href='javascript:getEducationData(\""+data.data[i].startDate+"\",\""+data.data[i].endDate+"\",\""+data.data[i].schoolName+"\",\""+data.data[i].professional+"\",\""+data.data[i].certificate+"\")'>详细</a></td>"+
					                "</tr>";
                        }
                        	
                        }
                        box.html(dom);
                    }else{
                    	box.html(dom);
                    }
                }
            });
		}

		function getTalentDocument(id){
			$.ajax({
                url: "/talent/user/getTalentDocumentByCondition.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    talentId:id
                },
                success: function (data) {
                    var dom = "",
                        box = $("#documentTable"); 
                    if (data.success) {                     
                        for(var i=0;i<data.data.length;i++){
                        	dom += "<tr>"+
	                    				"<td width='33%'>"+ parseInt(i)+parseInt(1) +"</td>"+
	                    				"<td width='33%'>"+ data.data[i].documentName +"</td>"+
	                    				"<td id='document"+i+"' style='display:none'>"+ data.data[i].saveAddress +"</td>"+
					                    "<td width='33%'><a href='javascript:getDocumentData("+i+")'>查看</a></td>"+
					                "</tr>";
                        }
                        box.html(dom);
                    }else{
                    	box.html(dom);
                    }
                }
            });
		}
	
		function getFamilyData(relation,name,company,telephone,post){
			$("#familyDetail").show();
            $("#familyDetail").dialog({
                title: '人才家属信息',
                width: 450,
                height: 500,
                closable: false,
                onBeforeOpen: function () {
                   $("#familyName").val(name);
                   $("#familyPost").val(post);
                   $("#familyRelation").val(relation);
                   $("#familyCompany").val(company);
                   $("#familyTelephone").val(telephone);
                },
                buttons: [
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#familyDetail").dialog("close");
                        }
                    }
                ]
            });
		}
		
		function getJobData(startDate,endDate,companyName,post,leavingReason,reterence,description,reterenceTel){
			$("#jobDetail").show();
            $("#jobDetail").dialog({
                title: '人才工作信息',
                width: 450,
                height: 500,
                closable: false,
                onBeforeOpen: function () {
                if(startDate){
                    $("#jobStartDate").val(startDate.substring(0,4)+"/"+startDate.substring(4,6));
                }
                if(endDate){
                	$("#jobEndDate").val(endDate.substring(0,4)+"/"+endDate.substring(4,6));
                } 
                $("#jobCompanyName").val(companyName);
                $("#jobPost").val(post);
                $("#jobLeavingReason").val(leavingReason);
                $("#jobReterence").val(reterence);
                $("#jobDescription").val(description);
                $("#jobReterenceTel").val(reterenceTel);
                },
                buttons: [
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#jobDetail").dialog("close");
                        }
                    }
                ]
            });
		}
		
		function getEducationData(startDate,endDate,schoolName,professional,certificate){
			$("#educationDetail").show();
            $("#educationDetail").dialog({
                title: '人才学历信息',
                width: 450,
                height: 600,
                closable: false,
                onBeforeOpen: function () {
                    if(startDate){
                        $("#educationStartDate").val(startDate.substring(0,4)+"/"+startDate.substring(4,6));
	                }
	                if(endDate){
	                	$("#educationEndDate").val(endDate.substring(0,4)+"/"+endDate.substring(4,6));
	                } 
	                $("#educationSchoolName").val(schoolName);
	                $("#educationProfessional").val(professional);
	                $("#educationCertificate").val(certificate);
                },
                buttons: [
                    {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#educationDetail").dialog("close");
                        }
                    }
                ]
            });
		}
		
		function getDocumentData(count){
			 var dataArr = $("#document"+count).html().split(",");
                var fileType = checkFile($("#document"+count).html());
                if(fileType == 2){
				      //视频
				      for ( var i = 0; i < dataArr.length; i++) {
					    fileDetail(encodeURIComponent(dataArr[i]));
				      }
			       } else if(fileType == 1){
				      //图片或未识别
				      var opts = "<ul class='imgZoom' id='index0' style='display: none;'>";
				      for (var i = 0; i < dataArr.length; i++) {
					    opts += "<li style='float: left; margin: 5px;'><img data-original='"+ dataArr[i] +"' src='"+ dataArr[i] +"'/></li>";
			          } 
			          opts += "</ul>";
				      $("#fileDiv").html(opts);
				      setTimeout(function () {
				      	$("#index0").find("img").click(function () {
							$("#fileDiv").find(".imgZoom").viewer();
						}); 
				      	showImgViewer(0);
		              }, 0);	
			       } else if(fileType == 3){
				      // pdf文件
				      for ( var i = 0; i < dataArr.length; i++) {
                      window.location.href= dataArr[i] ;
                   }
			       } else {
				   // pdf文件
                   for ( var i = 0; i < dataArr.length; i++) {
                      window.location.href= dataArr[i] ;
                   }
			       }
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

	//提交面试
	function setInterview(){
		var rows = $("#datagrid").datagrid("getChecked");
		var str = "";
		if(rows.length>0){
		    for(var i=0; i<rows.length; i++){
		    	str +=rows[i].id+","+rows[i].recruitmentId+";";
		    }
		    $.ajax({
                url: "/interview/user/getInterviewByCheckTalent.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    checkTalent:str
                },
                success: function (data) {
                    if (data.success) {
                        var list = data.data;
                        if(list[0]){
                             $.messager.confirm('提示','以下人员已在面试管理中，是否继续：'+list[0].substring(0,list[0].length-1),function(r){    
							        if (r){
							           if(list[1]){
							             	insertInterview(list[1]);
							           } else {
							           		$.messager.alert("信息","提交成功!","info");
                        					queryProduct();
							           }   
							           
							        }    
						     });     
                        }else{
	                       if(list[1]){
				             	insertInterview(list[1]);
				           } else {
				           		$.messager.alert("信息","提交成功!","info");
                     					queryProduct();
				           }   
                        }
                    }else{
                    	$.messager.alert("错误", data.resultMsg, "error");
                    }
                }
            });
		    
		}
	}
	
	function insertInterview(insertDate){
	   if(insertDate){
	       $.ajax({
                url: "/interview/user/setInterview.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    checkTalent:insertDate.substring(0,insertDate.length-1)
                },
                success: function (data) {
                    if (data.success) {
                        $.messager.alert("信息","提交成功!","info");
                        queryProduct();
                    }else{
                    	$.messager.alert("错误", data.resultMsg, "error");
                    }
                }
            });
	   }
	}
	function print(talentId){
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
                        $("#talentName").html(datas.name);  
			          	$("#talentMobile").html(datas.mobile); 
			          	$("#talentIdentityNo").html(datas.identityNo); 
			          	readIdCard(datas.identityNo);
			          	if(datas.recruitment){
			          	    $("#talentRecruitment").html(datas.recruitment.positionName);
			          	}
			          	if(datas.comeDate){
			          		$("#talentRomeDate").html(datas.comeDate.substring(0,4)+"/"+datas.comeDate.substring(4,6)+"/"+datas.comeDate.substring(6,8));
			          	}
			          	
			          	$("#talentRxpectedIncome").html(datas.expectedIncome);
			          	$("#talentNation").html(datas.nation);
			          	$("#talentOrigin").html(datas.origin);
			          	$("#talentMaritalStatus").html(datas.maritalName);
			          	$("#talentFertilityStatus").html(datas.fertilityName);
			          	$("#talentPoliticalStatus").html(datas.politicalName);
			          	$("#talentDrivingLicenseType").html(datas.drivingLicenseType);
			          	$("#talentQualification").html(datas.qualification);
			          	$("#talentAddress").html(datas.address);
			          	$("#talentMail").html(datas.mail);
			          	$("#talentEmergencyPerson").html(datas.emergencyPerson);
			          	$("#talentEmergencyMobile").html(datas.emergencyMobile);
			          	$("#talentDate").html(getDay(datas.createTime.time, "yyyy/MM/dd"));
			          	$(":radio[name='talentHaveAcquaintance'][value='" + datas.haveAcquaintance + "']").prop("checked", "checked");
			          	if(datas.haveAcquaintance){
			          	    $("#hiddenTr").show();
			          	    $("#talentAcquaintanceName").val(datas.acquaintanceName);
			          	    $("#talentAcquaintanceDepartment").val(datas.acquaintanceDepartment);
			          	    $("#talentAcquaintanceRelation").val(datas.acquaintanceRelation);
			          	    $("#talentHaveAcquaintance").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#hiddenTr").hide();
			          		$("#talentHaveAcquaintance").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveCriminal){
			          		$("#talentHaveCriminal").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#talentHaveCriminal").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveFired){
			          		$("#talentHaveFired").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#talentHaveFired").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveDiseases){
			          		$("#talentHaveDiseases").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#talentHaveDiseases").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveCompetition){
			          		$("#talentHaveCompetition").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#talentHaveCompetition").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	var dom = "", box = $("#jobTables tbody");
			          	for(var i=0;i<datas.talentJob.length;i++){
			                   if(datas.talentJob[i].startDate){
			                      if(datas.talentJob[i].endDate){
			                      	   dom += "<tr>"+
			                       	   "<td >"+ datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~"+ datas.talentJob[i].endDate.substring(0,4)+"/"+datas.talentJob[i].endDate.substring(4,6) +"</td>"+
			                       	   "<td >"+ datas.talentJob[i].companyName +"</td>"+
								       "<td >"+ datas.talentJob[i].post +"</td>"+
								       "<td >"+ datas.talentJob[i].leavingReason +"</td>"+
								       "<td >"+ datas.talentJob[i].reterence +" "+ datas.talentJob[i].reterenceTel +"</td></tr>";
			                      	   }else{
			                      	    dom += "<tr>"+
			                       	   "<td >"+datas.talentJob[i].startDate.substring(0,4)+"/"+datas.talentJob[i].startDate.substring(4,6) +"~至今</td>"+
			                       	   "<td >"+ datas.talentJob[i].companyName +"</td>"+
								       "<td >"+ datas.talentJob[i].post +"</td>"+
								       "<td >"+ datas.talentJob[i].leavingReason +"</td>"+
								       "<td >"+ datas.talentJob[i].reterence + " " + datas.talentJob[i].reterenceTel +"</td></tr>";
			                      	   }
			                      	}else{
			                      		 dom += "<tr>"+
			                      		 "<td ></td>"+
			                      	     "<td >"+ data.data[i].companyName +"</td>"+
								     	 "<td >"+ data.data[i].post +"</td>"+
							         	 "<td >"+ datas.talentJob[i].post +"</td>"+
								         "<td >"+ datas.talentJob[i].leavingReason +"</td>"+
								         "<td >"+ datas.talentJob[i].reterence + " " + datas.talentJob[i].reterenceTel +"</td></tr>";
			                      	}		
			                      }
			                      box.html(dom);                        	
			                      var familyDom = "", familyBox = $("#familyTables tbody");
			                      for(var i=0;i<datas.talentFamily.length;i++){
			                        	familyDom += "<tr>"+
				                    				
				                    				"<td >"+ datas.talentFamily[i].name +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].relation +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].company +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].post +"</td>"+
									                "<td >"+ datas.talentFamily[i].telephone +"</td></tr>";
			                        }
			                      familyBox.html(familyDom);
			                      var educationDom = "",educationBox = $("#educationTables tbody");
			                      for(var i=0;i<datas.talentEducation.length;i++){
			                        if(datas.talentEducation[i].startDate){
			                        	if(datas.talentEducation[i].endDate){
			                        		educationDom += "<tr>"+
				                    				"<td >"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~"+ datas.talentEducation[i].endDate.substring(0,4)+"/"+ datas.talentEducation[i].endDate.substring(4,6)+"</td>"+
				                    				"<td >"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td >"+ datas.talentEducation[i].professional +"</td>"+
									                "<td >"+ datas.talentEducation[i].certificate +"</td></tr>";
			                        	}else{
			                        		educationDom += "<tr>"+
				                    				"<td >"+ datas.talentEducation[i].startDate.substring(0,4)+"/"+ datas.talentEducation[i].startDate.substring(4,6)+"~至今</td>"+
				                    				"<td >"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td >"+ datas.talentEducation[i].professional +"</td>"+
									                "<td >"+ datas.talentEducation[i].certificate +"</td></tr>";
			                        	}
			                        }else{
			                        	educationDom += "<tr>"+
				                    				"<td ></td>"+
				                    				"<td >"+ datas.talentEducation[i].schoolName +"</td>"+
									                "<td >"+ datas.talentEducation[i].professional +"</td>"+
									                "<td >"+ datas.talentEducation[i].certificate +"</td></tr>";
			                        }
			                        	
			                        }
			                        educationBox.html(educationDom);
			                        /* var documentDom = "",documentBox = $("#documentTable");
			                        for(var i=0;i<datas.talentDocument.length;i++){
			                        	documentDom += "<tr>"+
				                    				"<td >"+ parseInt(i)+parseInt(1) +"</td>"+
				                    				"<td >"+ datas.talentDocument[i].documentName +"</td><td></td><td></td></tr>";
			                        }
			                        documentBox.html(documentDom); */
			                      	$(".pic-box").attr("src", datas.signature);
                    				//console.log($(".pic-box").attr("src"))
                    } 
                    setTimeout(function () {downprint();}, 1);
                }
            });		
	}
	function downprint(){
			var printWindow = window.open();
			printWindow.document.write( "<div style='height:100px;'>"+$('#talentInfo').html()+"</div>");
			printWindow.print();
			printWindow.close();
	}
	
	/**
	 * 根据身份证号自动计算生日、年龄、性别 
	 */	
    function readIdCard(idcard){
       if(idcard){
       	   var year = idcard.substring(6,10);
	       var month = idcard.substring(10,12);
	       var date = idcard.substring(12,14);
	       var num = idcard.substring(16,17);
	       var birthday = year+"年"+month+"月"+date+"日";
	       var sex = num%2;
	       if(sex>0){
	         $("#talentSex").html("男");    
	       }else{
	       	 $("#talentSex").html("女");
	       }
	       $("#talentBirthday").html(birthday);
       }
    }
    </script>
    
</body>
    <jsp:include page="/WEB-INF/pages/commons/imgEffect.jsp" />
    <jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>