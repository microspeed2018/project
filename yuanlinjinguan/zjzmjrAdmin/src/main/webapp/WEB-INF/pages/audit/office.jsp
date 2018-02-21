
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<title>院办审核</title>
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
</head>
<style type="text/css">
    #packageInfo table{
        padding : 0px;
        border-left: 1px solid #000;
        border-top: 1px solid #000;
    }
    #packageInfo td{
        padding : 10px;
        border-right: 1px solid #000;
        border-bottom: 1px solid #000;
        text-align:center;
    }
</style>
<body>
    <div class="easyui-layout" data-options="fit:true" id="layout">
        <div data-options="region:'north',split:false,border:false" style="height:auto;">
           <div class="query">
               <form id="form" class="inner-q" onsubmit="return false;">
                   <table cellpadding="0" cellspacing="0">
                       <tr>
   						<th>项目编号</th>
   						<td>
                               <input type="text" id="projectNo-search" class="input">
                           </td>
   						<th>项目名称</th>
                           <td>
                               <input type="text" id="name-search" class="input">
                           </td>
                           <th>申请人</th>
                           <td>
                               <input type="text" id="adminName-search" class="input">
                           </td>
                           <th>申请时间</th>
                           <td>
                               <input type="text" id="createTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                               <input type="text" id="createTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
                           </td>
                       </tr>
                       <tr>
                           <th>审核类型</th>
                           <td>
                               <select class="select" id="type-search">
                                   <option value="-1"></option>
                               </select>
                           </td>
                           <th>状态</th>
                           <td>
                               <select class="select" id="status-search">
                                   <option value="-1"></option>
                                   <option value="0">未审核</option>
                                   <option value="1">通过</option>
                                   <option value="1">不通过</option>
                               </select>
                           </td>
                           <td colspan="2"></td>
                           <th>审核时间</th>
                           <td>
                               <input type="text" id="verifyTimeStart" name="actiDatetime" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate"> ~
                               <input type="text" id="verifyTimeEnd" name="actiDatetimeEnd" class="easyui-datebox e-input" data-options="height:25,editable:true,formatter:formatDate">
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
        <div id="detailInfo" style="display: none;">
           <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
               <tbody>
                   <tr id="ratioTr">
                       <th>院方比例</th>
                       <td>
                           <input type="text" id="ratio-s" name="ratio"  class="input"  readonly/>
                       </td>
                       <th></th>
                       <td></td>
                       <th></th>
                       <td></td>
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
               </tbody>
           </table>
       </div>
       <div id="package" style="display: none;">
   <table class="diolag-table info-table" cellpadding="0" cellspacing="0" border="0" width="80%" >
        <tbody>
         <tr>
           <th>分包负责人</th>
           <td>
            <input readonly id="leader"/>
           </td>
         </tr>
         <tr>
           <th>分包金额(万元)</th>
           <td>
            <input readonly id="subpackageCapital"/>
           </td>
         </tr>
         <tr id="paymentTr">
           <th>分包内容</th>
           <td>
           <input readonly id="subpackageContent"/>
           </td>
         </tr>
         <tr>
           <th>分包备注</th>
           <td>
            <input readonly id="subpackageMemo"/>
           </td>
         </tr>
          <tr>
           <th>状态</th>
           <td>
            <input readonly id="packageStatus"/>
           </td>
         </tr>
        </tbody>
   </table>
   </div>
   <div id="bailDetailInfo" style="display: none;">
           <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
              <tr>
                 <th style="text-align: center">保证金(万元)</th>
                 <td>
                    <input readonly id="bail" class="input"/>
                 </td>
              </tr>
              <tr>
                 <th style="text-align: center">最晚汇款日期</th>
                 <td>
                    <input readonly id="latestRemittanceDate" class="input"/>
                 </td>
              </tr>
              <tr>
                 <th style="text-align: center">汇款单位</th>
                 <td>
                    <input readonly id="remittanceCompany" class="input"/>
                 </td>
              </tr>
              <tr>
                 <th style="text-align: center">开户行</th>
                 <td>
                    <input readonly id="bankName" class="input"/>
                 </td>
              </tr>
              <tr>
                 <th style="text-align: center">账号</th>
                 <td>
                    <input readonly id="accountNo" class="input"/>
                 </td>
              </tr>
              <tr>
                 <th style="text-align: center">备注</th>
                 <td>
                    <input readonly id="bailMemo" class="input"/>
                 </td>
              </tr>
           </table>
      </div>
      <div id="packageInfo" style="display: none;">
          <table class="diolag-table" cellpadding="0" cellspacing="0" border="0" id="packageTable">
	          <thead>
	             <tr>
	                <td align="center">合作比例内容</td>
	                <td align="center">原数据</td>
	                <td align="center">现数据</td>
	             </tr>
	             <tr id="packageRatioTr">
	                <td align="center">院方比例(%)</td>
	                <td align="center"></td>
	                <td align="center"></td>
	             </tr>
             </thead>
             <tbody>

             </tbody>
          </table>
      </div>
      <div data-options="region:'center',border:false" class="data-area">
            <div id="datagrid"></div>
            <div id="fileDiv">

           </div>
       </div>
      <div data-options="region:'center',border:false" class="data-area">
           <div id="datagrid"></div>
      </div>
       <input type="hidden" id="action" name="action" value="${action}">
   </div>
   <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
   <script type="text/javascript">
       var windowH = $(window).height();
       var windowW = $(window).width();
       var contractSubpackage=null;
       $(function() {
           $("#datagrid").datagrid(
               {
                   url : '${ctx}/officeAudit/user/getProjectOfficeAudit.htm',
                   title:'院办审核',
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
                           title : '项目编号',
                           width : 80,
                           align :'center',
                           formatter: function (value, row, index) {
                               return row.gardenProject.projectNo;
                           }
                       },
                       {
                           field : 'legalPerson',
                           title : '项目名称',
                           width : 80,
                           align:'center',
                           formatter: function (value, row, index) {
                               return row.gardenProject.name;
                           }
                       },
                       {
                           field : 'registeredCapital',
                           title : '审核类型',
                           width : 80,
                           align:'center',
                           formatter: function (value, row, index) {
                               return row.basicData.attributeName;
                           }
                       },
                       {
                           field : 'duration',
                           title : '申请人',
                           width : 80,
                           align :'center',
                           formatter: function (value, row, index) {
                               if(row.admin!=null){
                                  return row.admin.name;
                               }else{
                                  return "";
                               }

                           }
                       },{
                           field : 'paymentModeName',
                           title : '付款方式',
                           width : 80,
                           align :'center'
                       },{
                           field : 'amount',
                           title : '金额',
                           width : 80,
                           align :'center'
                       },{
                           field : 'mobile',
                           title : '申请时间',
                           width : 80,
                           align :'center',
                           formatter: function (value, row, index) {
                              return getDay(row.createTime.time, YYYYMMDD_HHMM);
                           }
                       },
                       {
                           field : 'createTime',
                           title : '审核时间',
                           width : 100,
                           align:'center',
                           formatter: function (value, row, index) {
                               if(row.status == 0){
                                	return  "";
	                            }else{
	                                if (row.updateTime) {
	                                    return getDay(row.updateTime.time, YYYYMMDD_HHMM);
	                                } else {
	                                    return "";
	                                }
	                            }
                           }
                       },
                       {
                           field : 'status',
                           title : '状态',
                           width : 80,
                           align:'center',
                           formatter: function(value, row, index) {
                               if(row.status == 0){
                                   return "未审核";
                               } else if(value == 1) {
                                   return "通过";
                               } else {
                                   return "不通过";
                               }
                               return "";
                           }

                       },{
                           field: 'opt',
                           title: '操作',
                           align: 'center',
                           width: 80,
                           formatter: function (value, row, index) {
                               if (!row.status) {
                                   if(row.type==56){
                                     //合作比例
                                     return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkLook("+ row.projectId +","+row.companyId+","+row.gardenProject.step+")'>查看</a>";
                                   }else if(row.type==52){
                                     //保证金
                                     return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkbail("+ row.projectId +","+row.companyId+")'>查看</a>";
                                   }else if(row.type==53){
                                     //商务标
                                     return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+5+")'>查看</a>";
                                   }else if(row.type==54){
                                     //技术标
                                     return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+7+")'>查看</a>";
                                   }else if(row.type==55){
                                     //合同
                                     return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+9+")'>查看</a>";
                                   }
                               }
                           },
                       }
                   ]],
                   loadFilter : function(data){
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

       // 获取审核类型
       getBasic(14, creteType);
       function creteType(datas) {
           var box = $("#type-search"),
               dom = "";
           for (var i = 0; i < datas.length; i++) {
               dom += "<option value='"+ datas[i].id +"'>"+ datas[i].attributeName +"</option>";
           }
           box.append(dom);
       }

       // 通过/不通过
       function result(index, status) {
           var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
           var projectId = list.gardenProject.id;
           var step = list.gardenProject.step;
           var subStep = list.gardenProject.subStep;
           var subStep2 = list.gardenProject.subStep2;
           var companyId = list.companyId;
           var applicant = list.applicant;
           var type = list.type;
           var id = list.id;
           if(type==52){
             step ="";
             subStep2="";
             id="";
           }else if(type==54){
             step ="";
             subStep="";
             id="";
           }else if(type==84){
             subStep="";
             subStep2="";
           } else{
             subStep="";
             subStep2="";
             id="";
           }
           if(status==2){
           	$.messager.prompt('提示', '请输入不通过理由', function(r){
              if (r){
              		$.ajax({
	                url: "/officeAudit/user/updateOfficeAuditById.htm",
	                type: 'post',
	                dataType: 'json',
	                data: {
	                    status : status,
	                    projectId : projectId,
	                    companyId : companyId,
	                    applicant : applicant,
	                    step : step,
	                    subStep : subStep,
	                    subStep2 : subStep2,
	                    type: type,
	                    memo:r,
		                id: id
	                },
	                success: function (data) {
	                    if (data.success) {
	                        $.messager.alert("提示", "提交成功", "info");
	                        queryProduct();
	                    } else {
	                        $.messager.alert("错误", data.errorMsg, "error");
	                    }
	                }
	            });
              }
            });
           }else{
           	$.ajax({
               url: "/officeAudit/user/updateOfficeAuditById.htm",
               type: 'post',
               dataType: 'json',
               data: {
                   status : status,
                   projectId : projectId,
                   companyId : companyId,
                   applicant : applicant,
                   step : step,
                   subStep : subStep,
                   subStep2 : subStep2,
                   type: type,
		           id: id
               },
               success: function (data) {
                   if (data.success) {
                       $.messager.alert("提示", "提交成功", "info");
                       queryProduct();
                   } else {
                       $.messager.alert("错误", data.errorMsg, "error");
                   }
               }
           });
           }
       }

       function queryProduct(){
           var param = {};
           param.projectNo= $('#projectNo-search').val();
           param.name= $('#name-search').val();
           param.adminName= $('#adminName-search').val();
           if ($('#type-search').val() != -1) {
               param.type = $('#type-search').val();
           }
           if ($('#status-search').val() != -1) {
               param.status = $('#status-search').val();
           }
           param.createTimeStart= $('#createTimeStart').datebox('getValue');
           param.createTimeEnd= $('#createTimeEnd').datebox('getValue');
           param.verifyTimeStart= $('#verifyTimeStart').datebox('getValue');
           param.verifyTimeEnd= $('#verifyTimeEnd').datebox('getValue');
           $("#datagrid").datagrid("load", param);
       }
       function queryProductReset(){
           $(".query #form")[0].reset();
           $(".query :input[type='hidden']").val("");
       }
       function checkLook(projectId,companyId,step){
       if(step==390){
            $("#detailInfo").show();
            $("#detailInfo").dialog({
               title: '合作比例',
               width: windowW - 50,
               height: windowH - 150,
               closable: false,
               onBeforeOpen: function () {
               	  //获取原来项目数据
	                $.ajax({
	                url: "/contract/user/contractList.htm",
	                type: 'post',
	                dataType: 'json',
	                data: {
	                    status : 0,
	                    projectId : projectId,
	                    companyId : companyId,
	                    subpackageStatus:0
	                },
	                success: function (data) {
	                    if (data.success) {
	                       var list = data.rows[0];
	                       $("#ratio-s").val(list.ratio);
	                       $("#ratioMemo").val(list.ratioMemo);
	                       // 创建分包信息
	                       var users = list.contractSubpackage;
	                       var box = $("#package-list tbody");
	                       var dom = "";
	                       contractSubpackage=list.contractSubpackage;

	                       for (var i = 0; i < users.length; i++) {
                          console.log(list.contractCapital)
	                           dom += "<tr>"+
	                           "<td width='25%'>"+ users[i].subpackageLeaderName +"</td>"+
	                           "<td width='25%'>"+ users[i].subpackageCapital +"</td>"+
	                           "<td width='25%'>"+ ((users[i].subpackageCapital/list.contractCapital)*100).toFixed(2)+"%</td>"+
	                           "<td width='25%'><a href='javascript:getData("+i+")'>详细</a></td>"+
	                           "</tr>";
	                       }
	                       box.empty();
	                       box.append(dom);
	                    }else {
	                       $.messager.alert("错误", data.resultMsg, "error");
	                    }
	                }
	            });
               },
               buttons:[
                {
                       iconCls: 'icons-close',
                       text: '关闭',
                       handler: function () {
                           $("#detailInfo").dialog("close");
                       }
                 }
               ]
           });
         }else{
           $("#packageInfo").show();
           $("#packageInfo").dialog({
             title: '合作比例',
             width: windowW - 50,
             height: windowH - 150,
             closable: false,
             onBeforeOpen: function () {
              //获取原来项目数据
	                $.ajax({
	                url: "/contract/user/contractList.htm",
	                type: 'post',
	                dataType: 'json',
	                data: {
	                    status : 1,
	                    projectId : projectId
	                },
	                success: function (data) {
	                    if (data.success) {
	                        var oldRatio = data.rows[0].ratio;
	                        $.ajax({
				                url: "/contract/user/getProjectContractByProjectIdAndStatus.htm",
				                type: 'post',
				                dataType: 'json',
				                data: {
				                    status : 0,
				                    projectId : projectId
				                },
				                success: function (data) {
				                    if (data.success) {
				                       var newRatio = data.rows[0].ratio;
				                       if(oldRatio==newRatio){
				                          $("#packageRatioTr").hide();
				                       }else{
				                          $("#packageRatioTr td").eq(1).html(oldRatio);
                                          $("#packageRatioTr td").eq(2).html(newRatio);
				                       }
				                    }else {
				                       $("#packageRatioTr").hide();
				                    }
				                }
			            	});
			         }else {
	                    $("#packageRatioTr").hide();
	                 }
	               }
	             });
			     $.ajax({
				      url: "/contract/user/getContractSubpackageInfo.htm",
				      type: 'post',
				      dataType: 'json',
				      data: {
				            status:2,
				            projectId : projectId
				      },
				      success: function (data) {
				          if (data.success) {
				              var oldSubPackage = data.data;
				              var packages = "";
				              var domi;
				              for(var i=0;i<data.data.length;i++){
			            	      if(data.data[i].status==4){
			            	         packages=data.data[i];
			            	         domi= "";
			            	         domi +="<tr><td>分包</td><td colspan='2'>操作:修改</td></tr>";
			            	         $.ajax({
						                url: "/contract/user/getContractSubpackageInfo.htm",
						                type: 'post',
						                dataType: 'json',
						                async: false,
						                data: {
						                    projectId : projectId,
						                    temporaryId:oldSubPackage[i].id
						                },
						                success: function (data) {
						                    if (data.success) {
						                       if(packages.subpackageLeaderName!=data.data[0].subpackageLeaderName){
						                          domi +="<tr><td>分包负责人</td><td>"+data.data[0].subpackageLeaderName+"</td><td>"+packages.subpackageLeaderName+"</td></tr>";
						                       }
						                       if(packages.subpackageCapital!=data.data[0].subpackageCapital){
						                          domi +="<tr><td>分包金额(万元)</td><td>"+data.data[0].subpackageCapital+"</td><td>"+packages.subpackageCapital+"</td></tr>";
						                       }
						                       if(packages.subpackageContent!=data.data[0].subpackageContent){
						                          domi +="<tr><td>分包内容</td><td>"+data.data[0].subpackageContent+"</td><td>"+packages.subpackageContent+"</td></tr>";
						                       }
						                       if(packages.subpackageMemo!=data.data[0].subpackageMemo){
						                          domi +="<tr><td>分包备注</td><td>"+data.data[0].subpackageMemo+"</td><td>"+packages.subpackageMemo+"</td></tr>";
						                       }
						                       if(packages.subpackagePayment || data.data[0].subpackagePayment){
						                       		if(packages.subpackagePayment.length>data.data[0].subpackagePayment.length){
						                       		   domi+="<tr><td rowspan='"+packages.subpackagePayment.length+"'>付款方式(万元)</td>";
						                       		   for(var j=0;j<packages.subpackagePayment.length;j++){
						                       		      if(j==0){
							                       		      if(j<data.data[0].subpackagePayment.length){
							                       		         domi+="<td>"+data.data[0].subpackagePayment[j].paymentModeName+"/"+data.data[0].subpackagePayment[j].paymentAmount+"</td>";
							                       		      }else{
							                       		         domi+="<td></td>";
							                       		      }
							                       		      domi+="<td>"+packages.subpackagePayment[j].paymentModeName+"/"+packages.subpackagePayment[j].paymentAmount+"</td></tr>";
						                       		      }else{
							                       		      if(j<data.data[0].subpackagePayment.length){
							                       		         domi+="<tr><td>"+data.data[0].subpackagePayment[j].paymentModeName+"/"+data.data[0].subpackagePayment[j].paymentAmount+"</td>";
							                       		      }else{
							                       		         domi+="<tr><td></td>";
							                       		      }
							                       		      domi+="<td>"+packages.subpackagePayment[j].paymentModeName+"/"+packages.subpackagePayment[j].paymentAmount+"</td></tr>";
						                       		      }

						                       		   }
						                       		}else{
						                       		 domi+="<tr><td rowspan='"+data.data[0].subpackagePayment.length+"'>付款方式(万元)</td>";
						                       		   for(var j=0;j<data.data[0].subpackagePayment.length;j++){
						                       		      if(j==0){
						                       		          domi+="<td>"+data.data[0].subpackagePayment[j].paymentModeName+"/"+data.data[0].subpackagePayment[j].paymentAmount+"</td>";
							                       		      if(j<packages.subpackagePayment.length){
							                       		         domi+="<td>"+packages.subpackagePayment[j].paymentModeName+"/"+packages.subpackagePayment[j].paymentAmount+"</td></tr>";
							                       		      }else{
							                       		         domi+="<td></td></tr>";
							                       		      }
						                       		      }else{
						                       		          domi+="<tr><td>"+data.data[0].subpackagePayment[j].paymentModeName+"/"+data.data[0].subpackagePayment[j].paymentAmount+"</td>";
							                       		      if(j<packages.subpackagePayment.length){
							                       		         domi+="<td>"+packages.subpackagePayment[j].paymentModeName+"/"+packages.subpackagePayment[j].paymentAmount+"</td></tr>";
							                       		      }else{
							                       		         domi+="<td></td></tr>";
							                       		      }

						                       		      }

						                       		   }
						                       		}
						                       		//.done(function() {
                                          $("#packageTable tbody").append(domi);
                                    //})
						                       }
						                    }
						                }
					            	});
			            	      }else if(data.data[i].status==5){
			            	         var domis = "";
			            	         domis +="<tr><td>分包</td><td colspan='2'>操作:删除</td></tr>";
			            	         domis +="<tr><td>分包负责人</td><td>"+oldSubPackage[i].subpackageLeaderName+"</td><td></td></tr>";
			            	         domis +="<tr><td>分包金额(万元)</td>><td>"+oldSubPackage[i].subpackageCapital+"</td><td></td></tr>";
			            	         domis +="<tr><td>分包内容</td><td>"+oldSubPackage[i].subpackageContent+"</td><td></td></tr>";
			            	         domis +="<tr><td>分包备注</td><td>"+oldSubPackage[i].subpackageMemo+"</td><td></td></tr>";
			            	         if(oldSubPackage[i].subpackagePayment!=null && oldSubPackage[i].subpackagePayment.length>0){
			            	            domis+="<tr><td rowspan='"+oldSubPackage[i].subpackagePayment.length+"'>付款方式(万元)</td>";
						                for(var j=0;j<oldSubPackage[i].subpackagePayment.length;j++){
						                     if(j==0){
				                       		      domis+="<td>"+oldSubPackage[i].subpackagePayment[j].paymentModeName+"/"+oldSubPackage[i].subpackagePayment[j].paymentAmount+"</td>";
				                       		      domis+="<td></td></tr>";
			                       		      }else{
				                       		      domis+="<tr><td>"+oldSubPackage[i].subpackagePayment[j].paymentModeName+"/"+oldSubPackage[i].subpackagePayment[j].paymentAmount+"</td>";
				                       		      domis+="<td></td></tr>";
			                       		      }
						                }
			            	         }else{
			            	            domis+="<tr><td>付款方式(万元)</td><td></td><td></td></tr>";
			            	         }
			            	         $("#packageTable tbody").append(domis);
			            	      }else if(data.data[i].status==3){
			            	         var doms = "";
			            	         doms +="<tr><td>分包</td><td colspan='2'>操作:新增</td></tr>";
			            	         doms +="<tr><td>分包负责人</td><td></td><td>"+data.data[i].subpackageLeaderName+"</td></tr>";
			            	         doms +="<tr><td>分包金额(万元)</td><td></td><td>"+data.data[i].subpackageCapital+"</td></tr>";
			            	         doms +="<tr><td>分包内容</td><td></td><td>"+data.data[i].subpackageContent+"</td></tr>";
			            	         doms +="<tr><td>分包备注</td><td></td><td>"+data.data[i].subpackageMemo+"</td></tr>";
			            	         if(data.data[i].subpackagePayment){
			            	         doms+="<tr><td rowspan='"+data.data[i].subpackagePayment.length+"'>付款方式(万元)</td>";
			            	         for(var payCount=0; payCount<data.data[i].subpackagePayment.length;payCount++){
			            	            if(payCount==0){
				                       		doms+="<td></td>";
				                       		doms+="<td>"+data.data[i].subpackagePayment[payCount].paymentModeName+"/"+data.data[i].subpackagePayment[payCount].paymentAmount+"</td></tr>";
			                       		}else{
				                       		doms+="<tr><td></td>";
				                       		doms+="<td>"+data.data[i].subpackagePayment[payCount].paymentModeName+"/"+data.data[i].subpackagePayment[payCount].paymentAmount+"</td></tr>";
			                       		}
			            	          }
			            	         }else{
			            	            doms+="<tr><td>付款方式(万元)</td><td></td><td></td></tr>";
			            	         }
			            	         $("#packageTable tbody").append(doms);
			            	      }
			            	      }

			            	      }
			            	      }
			            	});

             },
             buttons:[
             {
                iconCls: 'icons-close',
                text: '关闭',
                handler: function () {
                  $("#packageRatioTr").show();
                  $("#packageTable tbody").empty();
                  $("#packageTable tbody").html("");
                  $("#packageInfo").dialog("close");
                }
             }
             ]
           });
         }
       }
        function getData(count){
           $("#package").show();
           $("#package").dialog({
               title: '分包信息',
               width: 650,
               height: 450,
               closable: false,
               onBeforeOpen: function () {
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
        function checkbail(projectId,companyId){
              $("#bailDetailInfo").show();
              $("#bailDetailInfo").dialog({
               title: '保证金查看',
               width: 450,
               height: 350,
               closable: false,
               onBeforeOpen: function () {
               //获取保证金信息
               $.ajax({
               url: "/project/user/getGardenProjectByIdAndStatus.htm",
               type: 'post',
               dataType: 'json',
               data: {
                   status : 0,
                   id : projectId,
                   companyId : companyId
               },
               success: function (data) {
                   if (data.success) {
                      $("#bail").val(data.rows.bail);
                      $("#latestRemittanceDate").val(data.rows.latestRemittanceDate);
                      $("#remittanceCompany").val(data.rows.remittanceCompany);
                      $("#bankName").val(data.rows.remittanceCompany);
                      $("#accountNo").val(data.rows.accountNo);
                      $("#bailMemo").val(data.rows.bailMemo);
                   }else {
                      $.messager.alert("错误", data.resultMsg, "error");
                   }
               }
           });
               },
               buttons:[
                {
                       iconCls: 'icons-close',
                       text: '关闭',
                       handler: function () {
                           $("#bailDetailInfo").dialog("close");
                       }
                 }
               ]
           });
       }
   </script>
</body>
<jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>