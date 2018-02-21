
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<%@ taglib uri="http://www.yztz.com/jsp/jstl/sec" prefix="sec"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>面试信息</title>
    <jsp:include page="/WEB-INF/pages/commons/resource.jsp"/>
    <jsp:include page="/WEB-INF/pages/commons/umeditor.jsp"/>
    <style type="text/css">
        select {
            width: 100%;
        }
        #resultInfo input{
        	width: 60%;
        	text-align:center;
        }
		#resultInfo th{
        	width:7% !important
        }
        #baseTable th{
            width:3.5% !important
        }
        #mustTable th{
            width:4.5% !important
        }
    </style>
</head>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
            <div class="query">
                <form id="form" class="inner-q" onSubmit="return false;" method="post">
                    <table cellpadding="0" cellspacing="0">
                        <tr>
                            <th>姓名</th>
                            <td>
                                 <input type="text" id="name" name="name" class="input">
                            </td>
                            <th>联系电话</th>
                            <td>
                                 <input type="text" id="mobile" name="mobile" class="input">
                            </td>
                            <th>身份证号</th>
                            <td>
                                <input type="text" id="identityNo" name="identityNo" class="input">
                            </td>
                            <th>面试时间</th>
                            <td>
                                <input type="text" id="timeStart" name="timeStart" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                                <input type="text" id="timeEnd" name="timeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                            </td>
                        </tr>
                        <tr>
                            <th>应聘职位</th>
                            <td>
                                <select id="recruitmentId" name="recruitmentId" class="select">
                                    <option value="-1">不限</option>
                                </select>
                            </td>
                            <th>面试官</th>
                            <td>
                                <input id="interviewerSearch" name="interviewer" class="easyui-combobox" style="height:30px;width:150%;" panelHeight="210">
                            </td>
                            <th>面试结果</th>
                            <td>
                               <select id="result" name="result">
                                    <option value='-1'>不限</option>
									<c:forEach var='item' items='${e:interviewResultEnum() }'>
											<option value='${item.value }'>${item.message }</option>
									</c:forEach>
                                </select>
                            </td>
                            <th>笔试分数</th>
                            <td><input type="text" id="writtenScoreStart" name="writtenScoreStart" class="input half-input">
								<span style="float: left;">~</span>
                                <input type="text" id="writtenScoreEnd" name="writtenScoreEnd" class="input half-input">
                            </td>
                        </tr>
                        <tr>
                        	<th>面试分数</th>
                            <td><input type="text" id="interviewScoreStart" name="interviewScoreStart" class="input half-input">
								<span style="float: left;">~</span>
                                <input type="text" id="interviewScoreEnd" name="interviewScoreEnd" class="input half-input">
                            </td>
                            <th colspan="5"></th>
                            <td class="bar">
                                <input type="button" class="btn" value="搜索" onClick="queryProduct()" />
                                <input type="reset" class="btn" value="清空" onClick="queryProductReset()" />
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
  	<!-- 面试设置 -->
    <div id="detailInfo">
        <form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                   <tr>
	         	     <th style="width:7% !important">面试轮次</th><td id="round"></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important">面试时间</th><td><input type="text" id="time" name="time" class="easyui-datetimebox e-input" style="width:160px;" data-options=" editable:false,showSeconds:false,formatter:formatDate1,parser:parserDate"/></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important">面试地点</th><td><input type="text" id="place" name="place" class="input" style="width:150px;" /></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important">面试官</th><td><input id="interviewer" name="interviewer" class="easyui-combobox" style="height:30px;width:160%;" panelHeight="210"></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important">面试备注</th><td><textarea id="memo" name="memo"></textarea></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important"><input type="checkBox" id="isSms" name="isSms"/></th><td>是否短信通知</td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th style="width:7% !important; text-align:left" colspan="2">*确认后将发送面试邀请</th>
	        	   </tr>
                </tbody>
                <input type="hidden" id="interviewSetId" name="id" class="input"/>
                <input type="hidden" id="roundText" name="roundText" class="input"/>
                <input type="hidden" id="recruitmentName" name="recruitmentName" class="input"/>
                <input type="hidden" id="mobiles" name="mobile" class="input"/>
                <input type="hidden" id="interviewerName" name="interviewerName" class="input"/>
            </table>
        </form>
    </div>
  	<!-- 结果评审 -->
    <div id="resultInfo">
        <form id="form2" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                   <tr>
	         	     <th>笔试得分</th>
	         	     <td><input type="text" id="writtenScore" name="writtenScore" class="input"/></td>
	         	     <th></th>
	         	     <td></td>
	        	   </tr>
	        	   <tr>       	   	
	         	     <th>面试得分</th><td id="interviewScores" name="interviewScores"></td>
	         	     <th></th>
	         	     <td></td>                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                      	      	     
	        	   </tr>
	        	   <tr>       	   	
	         	     <th></th><td><span>基本信息</span> &nbsp;&nbsp;<span id='basePoint'></span></td>
	         	     <th></th><td><span>关键胜任力</span> &nbsp;&nbsp;<span id='mustPoint'></span></td>
	        	   </tr>
	        	   <tr>
	        	   		<th colspan='2'>
	        	   			<table id='baseTable' class="diolag-table" cellpadding="0" cellspacing="0" border="0">	   		
	        	   				<tr>       	   	
				         	     <th></th><td><span style="float: left;width: 30%;">形象气质 &nbsp;&nbsp;</span><input type="text" id="temperament" name="temperament" class="input half-input"/></td>				         	     
				        	   </tr>
				        	   <tr>       	   	
				         	     <th></th><td><span style="float: left;width: 30%;">阅历匹配 &nbsp;&nbsp;</span><input type="text" id="experience" name="experience" class="input half-input"/></td>				         	     
				        	  </tr>
				        	  <tr>       	   	
				         	     <th></th><td><span style="float: left;width: 30%;">职务专业 &nbsp;&nbsp;</span><input type="text" id="specialty" name="specialty" class="input half-input"/></td>				         	     
				        	  </tr>
				        	  <tr>       	   	
				         	     <th></th><td><span style="float: left;width: 30%;">求职意愿 &nbsp;&nbsp;</span><input type="text" id="intention" name="intention" class="input half-input"/></td>				         	     
				        	  </tr>
				        	  <tr>       	   	
				         	     <th></th><td><span style="float: left;width: 30%;">工作稳定性 &nbsp;&nbsp;</span><input type="text" id="stability" name="stability" class="input half-input"/></td>		         	     
				        	  </tr>
	        	   			</table>
	        	   		</th>
	        	   		<td colspan='2' >
	        	   			<table id='mustTable' class="diolag-table" cellpadding="0" cellspacing="0" border="0">
	        	   				<tr>       	   	
				         	     
				         	     <th></th><td><span style="float: left;width: 30%; text-align:right">关注细节 &nbsp;&nbsp;</span><input type="text" id="details" name="details" class="input half-input"/></td>
				        	   </tr>
				        	   <tr>       	   	
				         	     
				         	     <th></th><td><span style="float: left;width: 30%; text-align:right">执行力 &nbsp;&nbsp;</span><input type="text" id="executive" name="executive" class="input half-input"/></td>
				        	  </tr>
				        	  <tr>       	   	
				         	     
				         	     <th></th><td><span style="float: left;width: 30%; text-align:right">工作效率 &nbsp;&nbsp;</span><input type="text" id="efficiency" name="efficiency" class="input half-input"/></td>
				        	  </tr>
				        	  <tr>       	   	
				         	     
				         	     <th></th><td><span style="float: left;width: 30%; text-align:right">人际关系 &nbsp;&nbsp;</span><input type="text" id="relationship" name="relationship" class="input half-input"/></td>
				        	  </tr>
				        	  <tr>       	   	
				         	    
				         	     <th></th><td><span style="float: left;width: 30%; text-align:right">求职表达力 &nbsp;&nbsp;</span><input type="text" id="communication" name="communication" class="input half-input"/></td>
				        	  </tr>
	        	   			</table>
	        	   		</td>
	        	  </tr>
	        	  <tr>
	        	  	 <th></th><td colspan="3">*10分制，表现优秀9-10分，良好7-8分，达标6-7分，不达标6分以下</td>
	        	  </tr>
	        	  <tr>
	        	  	 <th>评语</th><td colspan="3"><textarea id="comment" name="comment" style="width:98% !important;padding:0px !important"></textarea></td>
	        	  </tr>
	        	  <tr>
	        	  	 <th>面试结果</th>
	        	  	 <td>
	        	  	 	<input style="width:5% !important;" type="radio" value="3" name="result" checked/>不通过
	        	  	 	<input style="width:5% !important;" type="radio" value="1" name="result" />通过
	        	  	 	<input style="width:5% !important;" type="radio" value="2" name="result" />录取
	        	  	 	<input style="width:5% !important;" type="radio" value="9" name="result" />未参加
	        	  	 </td>
	        	  </tr>
                </tbody>
            </table>
            <input type="hidden" id="resultSetId" name="id" class="input"/>
            <input type="hidden" id="interviewScore" name="interviewScore" class="input"/>
            <input type="hidden" id="rounds" name="round" class="input"/>
            <input type="hidden" id="talentId" name="talentId" class="input"/>
            <input type="hidden" id="recruitmentIds" name="recruitmentId" class="input"/>
        </form>
    </div>
  	<!-- 打印简历 -->
    <div id="talentInfo">
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
					<p>应聘职位：<span id="recruitment"></span></p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class='table1'>
						<tr>
							<td width='10%'>姓名</td>
							<td width='15%' id="talentName">&nbsp;</td>
							<td width='10%'>性别</td>
							<td width='11%' id= "sex">&nbsp;</td>
							<td width='8%'>民族</td>
							<td width='13%' id="nation">&nbsp;</td>
							<td width='15%'>出生日期</td>
							<td width='18%' align="right" id="birthday">年  月  日</td>
						</tr>
						<tr>
							<td>籍贯</td>
							<td id="origin">&nbsp;</td>
							<td>婚姻状况</td>
							<td id="maritalStatus">&nbsp;</td>
							<td>身高</td>
			      		    <td><span id="emergencyPerson"></span><span style="float: right">cm</span></td>
						    <td>体重</td>
						    <td><span id="emergencyMobile"></span><span style="float: right">kg</span></td>
							
						</tr>
						<tr>
							<td>驾照类型</td>
							<td id="drivingLicenseType">&nbsp;</td>
							<td>政治面貌</td>
							<td id="politicalStatus">&nbsp;</td>
							<td colspan="2">专业资格/职称</td>
							<td colspan="2" id="qualification">&nbsp;</td>		
						</tr>
						<tr>
							<td>现住址</td>
							<td colspan="3" id="address">&nbsp;</td>
							<td colspan="2">电子信箱</td>
							<td colspan="2" id="mail">&nbsp;</td>
						</tr>
						<tr>
							<td>身份证号</td>
							<td id="talentIdentityNo">&nbsp;</td>
							<td>手机号码</td>
							<td id="talentMobile">&nbsp;</td>
							<td colspan="2">生育情况</td>
							<td colspan="2" id="fertilityStatus"></td>
						</tr>
					</table>
					<p>教育背景</p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id ="educationTable">
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
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id="jobTable">
						<thead>
							<tr>
								<td width='20%'>时间</td>
								<td width='25%'>工作单位</td>
								<td width='10%'>职务</td>
								<td width='20%'>离职原因</td>
								<td width='20%'>证明人及电话</td>
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>
					<p align="left">家庭主要成员 </p>
					<table width="100%" border="0" cellspacing="0" cellpadding="0" class='table2' id="familyTable">
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
								<span id="haveAcquaintance" style='padding-right: 10px;'></span>是否有在本公司熟悉的人？
								<span id="hiddenTr" style='padding-left: 30px;'>
								姓名: <span id="acquaintanceName"></span>部门: <span id="acquaintanceDepartment"></span>关系: <span id="acquaintanceRelation"></span>
								</span>
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span id="haveCriminal" style='padding-right: 10px;'></span>是否有犯罪记录？
								<span id="haveDiseases" style='padding-left: 60px;padding-right: 10px;'></span>是否患有先天性重大疾病或传染性疾病或身体残疾？
							</td>
						</tr>
						<tr>
							<td colspan="2">
								<span id="haveFired" style='padding-right: 10px;'></span>曾被解雇过吗？
								<span id="haveCompetition" style='padding-left: 72px;padding-right: 10px;'></span>是否签署目前仍有效的“竞业禁止”协议或相关劳动条款？
							</td>
						</tr>
						<tr>
							<td width="50%">可到岗日期:<span id="comeDate"></span></td>
							<td width="50%">期望的工资（税前总年收入）:<span id="expectedIncome"></span></td>
						</tr>
						<tr>
							<td colspan="2"><p><b>本人兹声明在本表格内所填写各项资料均属正确无讹。本人明白如提供资料及相关证明文件或证书不属实或有误导，本人的申请将被取消，又或本人已受聘，本人亦将被公司无条件解雇。</b> </p>
							</td>
						</tr>
						<tr>
							<td colspan="2" align="right" valign="bottom">本人签字：<img src='' class='pic-box' style='height: 80px; padding-right: 60px;' />日期：<span id="talentDate" style='padding-right: 20px;' ></span></td>
						</tr>
					</table>
				</form> 
			</table>
    	</div>
    </div>
    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();

        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/interview/user/getInterviewInfo.htm',
                    title:'面试信息信息',
                    headerCls:'grid-title',
                    showFooter : true,
                    pagePosition : 'bottom',
                    pagination : true,
                    rownumbers:true,
                    pageSize : 20,
                    width : 'auto',
                    fitColumns : false,
                    fit:true,
                    sortName:'id',
                    sortOrder:'desc',
                    style : 'overflow:auto;',
                    singleSelect : true,
                    queryParams : {
                    },
                    pageList : [ 10,20, 30],
                    columns:[[
                        {
                            field : 'name',
                            title : '姓名',
                            width : 80,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if(row.talent){
                            	   return row.talent.name;
                            	}
                                
                            }
                        }, 
                        {
                            field : 'mobile',
                            title : '联系电话',
                            width : 120,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if(row.talent){
                            	   return row.talent.mobile;
                            	}
                                
                            }
                        },
                        {
                            field : 'identityNo',
                            title : '身份证号',
                            width : 180,
                            align :'center',
                            formatter: function (value, row, index) {
                            	if(row.talent){
                            	   return row.talent.identityNo;
                            	}
                                
                            }
                        },
                        {
                            field : 'recruitment',
                            title : '应聘职位',
                            width : 120,
                            align:'center',
                            formatter: function (value, row, index) {
                            	if(row.recruitment){
                            	   return row.recruitment.positionName;
                            	}
                                
                            }
                        },
                        {
                            field : 'createTime',
                            title : '面试邀请时间',
                            width : 150,
                            align:'center',
                            formatter: function (value, row, index) {
                                return getDay(value.time, "yyyy/MM/dd hh:mm");
                            }
                        },
                        {
                            field : 'round',
                            title : '轮次',
                            width : 60,
                            align:'center'
                        },
                        {
                            field: 'opt',
                            title: '操作',
                            align: 'center',
                            width: 200,
                            formatter: function (value, row, index) {
                                var oper = "";
                                    if(${interviewSetAuth}){
                                        oper += "<a href='javascript:showDetail("+index+")'>面试设置</a> | ";
                                    }
                                    if(${interviewTalentPrintAuth}){
                                    	oper += "<a href='javascript:print("+index+")'>打印简历</a> | ";
                                    }
                                    if(${interviewResultAuth}){
                                        oper += "<a href='javascript:setResult("+index+")'>结果评审</a>";
                                    }
                                return oper;
                            }
                        },
                         {
                            field : 'time',
                            title : '面试时间',
                            width : 130,
                            align:'center'
                        },
                        {
							field : 'place',
							title : '面试地点',
							width : 120,
							align : 'center'
						},
						 {
							field : 'interviewer',
							title : '面试官',
							width : 120,
							align : 'center',
                            formatter: function (value, row, index) {
                            	if(row.admin){
                            		return row.admin.name;
                            	}
                                
                            }
						},
						{
							field : 'writtenScore',
							title : '笔试得分',
							width : 60,
							align : 'center',
							formatter: function (value, row, index) {
                            	if(value){
                            		return value;
                            	}
                                
                            }
						},
						{
							field : 'interviewScore',
							title : '面试得分',
							width : 60,
							align : 'center',
							formatter: function (value, row, index) {
                            	if(value){
                            		return value;
                            	}
                                
                            }
						},
						{
                            field : 'result',
                            title : '面试结果',
                            width : 80,
                            align:'center',
                            formatter: function(value, row, index) {
                            <c:forEach items='${e:interviewResultEnum()}' var='item'>
			                    if (${item.value}==value){
			                        return '${item.message}';
			                    }
		                    </c:forEach>
                            }

                        },
                        {
                            field : 'recordTime',
                            title : '评审记录时间',
                            width : 150,
                            align:'center'/* ,
                            formatter: function (value, row, index) {
                                return getDay(row.createTime.time, "yyyy/MM/dd hh:mm");
                            } */
                        }
                    ]],
                    toolbar: [
                    {
                        iconCls: 'icons-import',
                        text: '导出',
                        handler: function () {
                            downLoad();
                        } 
                    }],
                    loadFilter : function(data){
                        if(${interviewExportAuth}){
		                    $('div.datagrid div.datagrid-toolbar').show();
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
        
        function showDetail(index) {
            if (index != undefined) {
                var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            }
            $("#detailInfo").show();
            $("#detailInfo").dialog({
                title: '面试设置',
                width:  400,
                height: 500,
                closable: false,
                onBeforeOpen: function () {
                    if (index != undefined) {
                        setInfo(list);
                        $('textarea').each(function() {
							autoTextarea(this);
						});
						if(list.result){
						  $("#setSure").hide();
						}else{
						  $("#setSure").show();
						}
                    }
                },
                buttons: [
                    {
                        id:'setSure',
                        iconCls: 'icons-true',
                        text: '确认',
                        handler: function () {
                                // 更新
                                if($("#isSms").attr("checked")) {
                                   $("#isSms").val("1");
                                }
                                if(checkData()){
                                $("#form1").attr("action", "/interview/user/updateInterview.htm");
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
                                            $.messager.progress("close");
                                            $.messager.alert("信息","设置成功!","info");
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
                        }
                    }
                ]
            });
        }
        
        //下载
        function downLoad(){
          queryProduct();
          $("#form").form("submit", {
	        url:"/interview/user/interviewPrint.htm",
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
      
        getStaffPersons();
        getStaffPersons1();
        // 获取技术负责人
        function getStaffPersons() {
            $("#interviewerSearch").combobox({    
	           url:'/staff/user/getInnerStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配   
               } 
	        });
	         $("#interviewer").combobox({    
	           url:'/staff/user/getInnerStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配   
               } 
	        });
	    }
        function getStaffPersons1() {
            $("#interviewer").combobox({    
	           url:'/staff/user/getInnerStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配   
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
                	isValid:1
                },
                success: function (data) {
                    if (data.success) {
                        var box = $("#recruitmentId"),
		                dom = "";
			            for (var i = 0; i < data.data.length; i++) {
			            	dom += "<option value='"+ data.data[i].id +"'>"+ data.data[i].positionName +"</option>";
			            }
			            box.append(dom);
                    } 
                }
            });
        }
        // 查看赋值
        function setInfo(datas) {
           //var time =  Utils.numberToChinese(datas.round);
           var time =  datas.round;
           $("#round").html("第"+time+"轮");
           $("#time").datebox("setValue",datas.time);
           $("#place").val(datas.place);
           if(datas.interviewer){
           		$("#interviewer").combobox("setValue",datas.interviewer);
           }
           $("#interviewSetId").val(datas.id);
           if(datas.isSms){
             $("#isSms").prop("checked",true);
           }else{
             $("#isSms").prop("checked",false);
           }
           $("#memo").val(datas.memo);
           if(datas.talent){
             $("#mobiles").val(datas.talent.mobile);
             $("#interviewerName").val(datas.talent.name);
           }
           $("#roundText").val(time);
           if(datas.recruitment){
           	 $("#recruitmentName").val(datas.recruitment.positionName);
           }
        }

        // 查看赋值
        function setInfo1(datas) {
           var basePoint = 0;
           var mustPoint = 0;
           if(datas.writtenScore){
           		$("#writtenScore").val(datas.writtenScore);
           }
           if(datas.interviewScore){
           		$("#interviewScores").html(datas.interviewScore);
           }
           if(datas.temperament){
           		$("#temperament").val(datas.temperament);
           		basePoint +=Number(datas.temperament);
           }
           if(datas.experience){
           		$("#experience").val(datas.experience);
           		basePoint +=Number(datas.experience);
           }
           if(datas.specialty){
           		$("#specialty").val(datas.specialty);
           		basePoint +=Number(datas.specialty);
           }
           if(datas.intention){
           		$("#intention").val(datas.intention);
           		basePoint +=Number(datas.intention);
           }
           if(datas.stability){
           		$("#stability").val(datas.stability);
           		basePoint +=Number(datas.stability);
           }
           if(datas.details){
           		$("#details").val(datas.details);
           		mustPoint +=Number(datas.details);
           }
           if(datas.executive){
           		$("#executive").val(datas.executive);
           		mustPoint +=Number(datas.executive);
           }
           if(datas.efficiency){
           		$("#efficiency").val(datas.efficiency);
           		mustPoint +=Number(datas.efficiency);
           }
           if(datas.relationship){
                $("#relationship").val(datas.relationship);
                mustPoint +=Number(datas.relationship);
           }
           if(datas.communication){
                $("#communication").val(datas.communication);
                mustPoint +=Number(datas.communication);
           }
           $("#comment").val(datas.comment);
           $("#basePoint").html(basePoint);
           $("#mustPoint").html(mustPoint);
           $("#resultSetId").val(datas.id);
           $("#recruitmentIds").val(datas.recruitmentId);
           $("#talentId").val(datas.talentId);
           $("#rounds").val(datas.round);
           $(":radio[name='result'][value='" + datas.result + "']").prop("checked", "checked");
        }
        function queryProduct(){
            var param = {};
            param.name = $('#name').val();
            param.mobile = $('#mobile').val();
            param.identityNo = $('#identityNo').val();
            if($('#recruitmentId').val()!=-1){
              param.recruitmentId = $('#recruitmentId').val();
            }
            param.interviewer = $("#interviewerSearch").combobox("getValue");
            if($('#result').val()!=-1){
               param.result = $('#result').val();
            }
            param.timeStart = $('#timeStart').datebox('getValue');
            param.timeEnd = $('#timeEnd').datebox('getValue');
            param.writtenScoreStart = $('#writtenScoreStart').val();
            param.writtenScoreEnd = $('#writtenScoreEnd').val();
            param.interviewScoreStart = $('#interviewScoreStart').val();
            param.interviewScoreEnd = $('#interviewScoreEnd').val();
            $("#datagrid").datagrid("load", param);
        }
        function queryProductReset(){
            $(".query #form")[0].reset();
            $(".query :input[type='hidden']").val("");
        }
        
		/*
	    工具包
	*/
	var Utils={
	    /*
	        单位
	    */
	    units:'个十百千万@#%亿^&~',
	    /*
	        字符
	    */
	    chars:'零一二三四五六七八九',
	    /*
	        数字转中文
	        @number {Integer} 形如123的数字
	        @return {String} 返回转换成的形如 一百二十三 的字符串            
	    */
	    numberToChinese:function(number){
	        var a=(number+'').split(''),s=[],t=this;
	        if(a.length>12){
	            throw new Error('too big');
	        }else{
	            for(var i=0,j=a.length-1;i<=j;i++){
	                if(j==1||j==5||j==9){//两位数 处理特殊的 1*
	                    if(i==0){
	                        if(a[i]!='1')s.push(t.chars.charAt(a[i]));
	                    }else{
	                        s.push(t.chars.charAt(a[i]));
	                    }
	                }else{
	                    s.push(t.chars.charAt(a[i]));
	                }
	                if(i!=j){
	                    s.push(t.units.charAt(j-i));
	                }
	            }
	        }
	        //return s;
	        return s.join('').replace(/零([十百千万亿@#%^&~])/g,function(m,d,b){//优先处理 零百 零千 等
	            b=t.units.indexOf(d);
	            if(b!=-1){
	                if(d=='亿')return d;
	                if(d=='万')return d;
	                if(a[j-b]=='0')return '零'
	            }
	            return '';
	        }).replace(/零+/g,'零').replace(/零([万亿])/g,function(m,b){// 零百 零千处理后 可能出现 零零相连的 再处理结尾为零的
	            return b;
	        }).replace(/亿[万千百]/g,'亿').replace(/[零]$/,'').replace(/[@#%^&~]/g,function(m){
	            return {'@':'十','#':'百','%':'千','^':'十','&':'百','~':'千'}[m];
	        }).replace(/([亿万])([一-九])/g,function(m,d,b,c){
	            c=t.units.indexOf(d);
	            if(c!=-1){
	                if(a[j-c]=='0')return d+'零'+b
	            }
	            return m;
	        });
	    }
	};
   function setResult(index){
   		$("#resultInfo").show();
        $("#resultInfo").dialog({
            title: '结果评审',
            width:  1050,
            height: 570,
            closable: false,
            onBeforeOpen: function () {
                if (index != undefined) {
                    var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
                    setInfo1(list);
                    if(${sec:userId()}==list.interviewer){
                        $("#submit").show();
                    }else{
                    	$("#submit").hide();
                    }
                }
                $('textarea').each(function() {
					autoTextarea(this);
				});
            },
            buttons: [
                {
                    id:'submit',
                    iconCls: 'icons-true',
                    text: '提交',
                    handler: function () {
                        // 更新
                        $("#interviewScore").val($("#interviewScores").html());
                        if(checkData1()){
                        $("#form2").attr("action", "/interview/user/setInterviewResult.htm");
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
                                    $("#resultInfo").dialog("close");
                                    $("#form2")[0].reset();
                                    $.messager.progress("close");
                                    $.messager.alert("信息","设置成功!","info");
                                    $("#interviewScores").html("");
                                    $("#basePoint").html("");
                                    $("#mustPoint").html("");
                                    queryProduct();
                                } else {
                                    $.messager.progress("close");
                                    $("#interviewScores").html("");
                                    $("#basePoint").html("");
                                    $("#mustPoint").html("");
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
                        $("#resultInfo").dialog("close");
                        $("#form2")[0].reset();
                    }
                }
            ]
        });
     }
	 
	 function checkData(){
	 	if(!$("#time").datebox('getValue')){
          	$.messager.alert("错误", "请设置面试时间", "error");
          	return false;   
        }else if(!$("#place").val()){
        	$.messager.alert("错误", "请设置面试地点", "error");
        	return false;  	
        }else if(!$("#interviewer").combobox('getValue')){
        	$.messager.alert("错误", "请设置面试官", "error");
        	return false; 	
        }
        return true;
	 }
	 function checkData1(){
	 	if(!$("#writtenScore").val()){
          	$.messager.alert("错误", "请输入笔试得分", "error");
          	return false;   
        }else if(!$("#interviewScores").html()){
        	$.messager.alert("错误", "请输入面试得分", "error");
        	return false;  	
        }
        return true;
	 }
	 
	 $(function () {	 	
	 	$("#baseTable input").on("keyup", function () {
	 		var baeVal = 0;
	 		$("#baseTable input").each(function () {
	 			var val = Number($(this).val());
	 			if (numberValidate(val)) {
					baeVal += val;					
				} else {
					$.messager.alert("提示", "请输入整数！", "info");
					$(this).val("");
				}
	 			
	 		});
	 		$("#basePoint").html(baeVal);
	 		$("#interviewScores").html(Number(document.getElementById("mustPoint").innerHTML) + Number(document.getElementById("basePoint").innerHTML));
	 	});
	 	
	 	$("#mustTable input").on("keyup", function () {
	 		var musVal = 0;
	 		$("#mustTable input").each(function () {
	 			var musval = Number($(this).val());
	 			if (numberValidate(musval)) {
					musVal += musval;					
				} else {
					$.messager.alert("提示", "请输入整数！", "info");
					$(this).val("");
				}
	 			
	 		});
	 		$("#mustPoint").html(musVal);
	 		$("#interviewScores").html(Number(document.getElementById("mustPoint").innerHTML) + Number(document.getElementById("basePoint").innerHTML));	
	 	});
	 	
	 	 	
	 });
	 
	 function numberValidate(number) {
		var pattern=/^[1-9]\d*$|^0$/;
		if (!pattern.test(number)) {
			return false;
		} else {
			return true;
		}
	};
	
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
	function print(index){
		var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
		$.ajax({
                url: "/talent/user/getTalentInfomation.htm",
                type: 'post',
                dataType: 'json',
                data: {
                	id: list.talentId
                },
                success: function (data) {
                    if (data.success) {
                        var datas = data.data[0];
                        $("#talentName").html(datas.name);  
			          	$("#talentMobile").html(datas.mobile); 
			          	$("#talentIdentityNo").html(datas.identityNo); 
			          	readIdCard(datas.identityNo);
			          	if(datas.recruitment){
			          	    $("#recruitment").html(datas.recruitment.positionName);
			          	}
			          	if(datas.comeDate){
			          		$("#comeDate").html(datas.comeDate.substring(0,4)+"/"+datas.comeDate.substring(4,6)+"/"+datas.comeDate.substring(6,8));
			          	}
			          	
			          	$("#expectedIncome").html(datas.expectedIncome);
			          	$("#nation").html(datas.nation);
			          	$("#origin").html(datas.origin);
			          	$("#maritalStatus").html(datas.maritalName);
			          	$("#fertilityStatus").html(datas.fertilityName);
			          	$("#politicalStatus").html(datas.politicalName);
			          	$("#drivingLicenseType").html(datas.drivingLicenseType);
			          	$("#qualification").html(datas.qualification);
			          	$("#address").html(datas.address);
			          	$("#mail").html(datas.mail);
			          	$("#emergencyPerson").html(datas.emergencyPerson);
			          	$("#emergencyMobile").html(datas.emergencyMobile);
			          	$("#talentDate").html(getDay(datas.createTime.time, "yyyy/MM/dd"));
			          	$(":radio[name='haveAcquaintance'][value='" + datas.haveAcquaintance + "']").prop("checked", "checked");
			          	if(datas.haveAcquaintance){
			          	    $("#hiddenTr").show();
			          	    $("#acquaintanceName").val(datas.acquaintanceName);
			          	    $("#acquaintanceDepartment").val(datas.acquaintanceDepartment);
			          	    $("#acquaintanceRelation").val(datas.acquaintanceRelation);
			          	    $("#haveAcquaintance").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#hiddenTr").hide();
			          		$("#haveAcquaintance").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveCriminal){
			          		$("#haveCriminal").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#haveCriminal").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveFired){
			          		$("#haveFired").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#haveFired").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveDiseases){
			          		$("#haveDiseases").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#haveDiseases").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	if(datas.haveCompetition){
			          		$("#haveCompetition").html("是<input type='checkbox' checked />否<input type='checkbox' />");
			          	}else{
			          		$("#haveCompetition").html("是<input type='checkbox' />否<input type='checkbox' checked />");
			          	}
			          	var dom = "", box = $("#jobTable tbody");
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
			                       	   "<td >"+ datas.talentJob[i].companyName + "</td>"+
								       "<td >"+ datas.talentJob[i].post + "</td>"+
								       "<td >"+ datas.talentJob[i].leavingReason + "</td>"+
								       "<td >"+ datas.talentJob[i].reterence + " " + datas.talentJob[i].reterenceTel +"</td></tr>";
		                      	   }
			                   } else {
			                      		 dom += "<tr>"+
			                      		 "<td ></td>"+
			                      	     "<td >"+ data.data[i].companyName +"</td>"+
								     	 "<td >"+ data.data[i].post +"</td>"+
							         	 "<td >"+ datas.talentJob[i].post +"</td>"+
								         "<td >"+ datas.talentJob[i].leavingReason +"</td>"+
								         "<td >"+ datas.talentJob[i].reterence +" "+ datas.talentJob[i].reterenceTel +"</td></tr>";
			                      	}		
			                      }
			                      box.html(dom);                        	
			                      var familyDom = "", familyBox = $("#familyTable tbody");
			                      for(var i=0;i<datas.talentFamily.length;i++){
			                        	familyDom += "<tr>"+
				                    				
				                    				"<td >"+ datas.talentFamily[i].name +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].relation +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].company +"</td>"+
				                    				"<td >"+ datas.talentFamily[i].post +"</td>"+
									                "<td >"+ datas.talentFamily[i].telephone +"</td></tr>";
			                        }
			                      familyBox.html(familyDom);
			                      var educationDom = "",educationBox = $("#educationTable tbody");
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
                    				console.log($(".pic-box").attr("src"))
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
	
	function formatDate1(date) {
	var time = date.getTime();
	return getDay(time, "yyyy/MM/dd hh:mm");
}


function parserDate(date) {
	if (!date) return new Date();  
    var y = date.substring(0,4);  
    var m =date.substring(5,7);  
    var d = date.substring(8,10);  
    var h = date.substring(11,13);  
    var min = date.substring(14,16);  
    var sec = date.substring(17,19);  
    if (!isNaN(y) && !isNaN(m) && !isNaN(d) && !isNaN(h) && !isNaN(min) && !isNaN(sec)){  
        return new Date(y,m-1,d,h,min,sec);  
    } else {  
        return new Date();  
    }
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
	         $("#sex").html("男");    
	       }else{
	       	 $("#sex").html("女");
	       }
	       $("#birthday").html(birthday);
       }
    }
    
    
    /**
 * 文本框根据输入内容自适应高度
 *
 * @param {HTMLElement}
 *            输入框元素
 * @param {Number}
 *            设置光标与输入框保持的距离(默认0)
 * @param {Number}
 *            设置最大高度(可选)
 */
var autoTextarea = function(elem, extra, maxHeight) {
	extra = extra || 0;
	var isFirefox = !!document.getBoxObjectFor || 'mozInnerScreenX' in window, isOpera = !!window.opera && !!window.opera.toString().indexOf('Opera'), addEvent = function(
			type, callback) {
		elem.addEventListener ? elem.addEventListener(type, callback, false) : elem.attachEvent('on' + type, callback);
	}, getStyle = elem.currentStyle ? function(name) {
		var val = elem.currentStyle[name];

		if (name === 'height' && val.search(/px/i) !== 1) {
			var rect = elem.getBoundingClientRect();
			return rect.bottom - rect.top - parseFloat(getStyle('paddingTop')) - parseFloat(getStyle('paddingBottom')) + 'px';
		}
		;

		return val;
	} : function(name) {
		return getComputedStyle(elem, null)[name];
	}, minHeight = parseFloat(getStyle('height'));

	elem.style.resize = 'none';

	var change = function() {
		var scrollTop, height, padding = 0, style = elem.style;
		style.width = '100%';

		/*
		 * if (elem._length === elem.value.length) return; elem._length = elem.value.length;
		 */

		if (!isFirefox && !isOpera) {
			padding = parseInt(getStyle('paddingTop')) + parseInt(getStyle('paddingBottom'));
		}
		;
		scrollTop = document.body.scrollTop || document.documentElement.scrollTop;

		elem.style.height = minHeight + 'px';
		if (elem.scrollHeight > minHeight) {
			if (maxHeight && elem.scrollHeight > maxHeight) {
				height = maxHeight - padding;
				style.overflowY = 'auto';
			} else {
				height = elem.scrollHeight - padding;
				style.overflowY = 'hidden';
			}
			;
			style.height = height + extra + 'px';
			scrollTop += parseInt(style.height) - elem.currHeight;
			document.body.scrollTop = scrollTop;
			document.documentElement.scrollTop = scrollTop;
			elem.currHeight = parseInt(style.height);
		}
		;
	};

	addEvent('propertychange', change);
	addEvent('input', change);
	addEvent('focus', change);
	change();
};
    </script>
</body>
</html>