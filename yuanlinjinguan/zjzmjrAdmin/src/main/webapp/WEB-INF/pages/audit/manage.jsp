
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.cbylsj.com/jsp/jstl/enum" prefix="e" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <title>经营审核</title>
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
        .diolag-table select {
            width: 100%;
        }
        #detailInfo table{
            padding : 0px;
            border-left: 1px solid #000;
            border-top: 1px solid #000;
        }
        #detailInfo td{
            padding : 10px;
            border-right: 1px solid #000;
            border-bottom: 1px solid #000;
        }
        #contractDetailInfo table{
            padding : 0px;
            border-left: 1px solid #000;
            border-top: 1px solid #000;
        }
        #contractDetailInfo td{
            padding : 10px;
            border-right: 1px solid #000;
            border-bottom: 1px solid #000;
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
        <div data-options="region:'center',border:false" class="data-area">
            <div id="datagrid"></div>
            <div id="fileDiv">

           </div>
        </div>

         <div id="detailInfo" style="display: none;">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
               <tr>
                  <td align="center">项目修改内容</td>
                  <td align="center">原数据</td>
                  <td align="center">现数据</td>
               </tr>
               <tr id="introducerTr">
                  <td align="center">项目介绍人</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="designAreaTr">
                  <td align="center">设计面积(㎡)</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="investmentMountTr">
                  <td align="center">投资额</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="nameTr">
                  <td align="center">项目名称</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="natureTr">
                  <td align="center">项目性质</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="categoryTr">
                  <td align="center">项目类别</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="typeTr">
                  <td align="center">项目类型</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="managementDepartmentTr">
                  <td align="center">经营部门</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="cityTr">
                  <td align="center">所在城市</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="introductionTr">
                  <td align="center">项目介绍</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="needTechnicalTr">
                  <td align="center">是否技术支持</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="contractAwardCompanyTr">
                  <td align="center">发包单位</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="intentionaCooperationTr">
                  <td align="center">意向合作方式</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="preRequirementsTr">
                  <td align="center">前期技术支持要求</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="haveSchemeTr">
                  <td align="center">是否有方案设计</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="haveDevelopmentTr">
                  <td align="center">是否有扩初方案设计</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="haveDrawingTr">
                  <td align="center">是否有施工图设计</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="havePlanningTr">
                  <td align="center">是否有规划设计</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="projectLiableTr">
                  <td align="center">项目责任者</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
            </table>
       </div>
       <div id="contractDetailInfo" style="display: none;">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
               <tr>
                  <td align="center">合同修改内容</td>
                  <td align="center">原数据</td>
                  <td align="center">现数据</td>
               </tr>
               <tr id="contractCapitalTr">
                  <td align="center">合同金额</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="signDateTr">
                  <td align="center">签约日期</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="contractMemoTr">
                  <td align="center">合同备注</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
               <tr id="paymentTr">
                  <td align="center">付款方式/金额(万元)</td>
                  <td align="center"></td>
                  <td align="center"></td>
               </tr>
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
       <div id="projectDetailInfo" style="display: none;">
       
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
                <tbody>
                    <tr>
                        <th>项目编号</th>
                        <td>
                            <input type="text" name="projectNo" id="projectNo-s" class="input" readonly />
                        </td>
                        <th>项目介绍人</th>
                        <td id="introducerTd">
                           <input name="introducer" id="introducer-s" class="easyui-combobox" style="height:30px;width:220%;" panelHeight="210" disabled>
                        </td>
                         <th>设计面积(㎡)</th>
                        <td>
                            <input type="text" name="designArea" id="designArea" readonly class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>投资额(万元)</th>
                        <td>
                            <input type="text" name="investmentMount" id="investmentMount" readonly class="input"  />
                        </td>
                        <th>项目名称</th>
                        <td>
                            <input type="text" name="name" id="name-s" readonly class="input"  />
                        </td>
                         <th>项目性质</th>
                        <td>
                         	<input type="text" name="nature" id="attribute-s" readonly class="input"  />                           
                        </td>
                    </tr>
                    <tr>
                        <th>项目类别</th>
                        <td>
                        	<input type="text" name="category" id="category-s" readonly class="input"  />                           
                        </td>
                        <th>项目类型</th>
                        <td>
                        	<select name="type" id="type-s" disabled>
                              <option value="-1"></option>
                            </select>
                        </td>
                         <th>经营部门</th>
                        <td>
                           <input type="text" name="mangementDepartment" id="department-s" readonly class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>所在城市</th>
                        <td class="area-td">
                            <p id="area" class="area-btn" disabled></p>
                        </td>
                        <td colspan="2">
                            <input type="text" name="address"  id="address" readonly class="input"  />
                        </td>
                        <th>项目负责人</th>
                        <td>
                            <input type="text" name="projectLiable" id="projectLiable" readonly class="input"  />
                        </td>
                    </tr>
                    <tr>
                        <th>项目介绍</th>
                        <td colspan="5">
                            <textarea id="introduction" name="introduction" disabled></textarea>
                        </td>
                    </tr>
                    <tr>
                        <th>是否技术支持</th>
                        <td>
                            <label>
                                是
                                <input type="radio" name="needTechnical" disabled value="1" checked />
                            </label>
                            <label>
                                否
                                <input type="radio" name="needTechnical"  disabled value="0" />
                            </label>
                        </td>
                        <th>发包单位</th>
                        <td id="contractAwardCompany-sTd">
                            <input type="text" name="contractAwardCompany" id="contractAwardCompany-s" readonly class="input"  />
                        </td>
                        <th>意向合作方式</th>
                        <td>
                            <select name="intentionalCooperation" id="intentionalCooperation" disabled>
                              <option value="-1"></option>
                            </select>
                        </td>
                    </tr>
                    <tr>
                        <th>前期技术支持要求</th>
                        <td colspan="5">
                            <textarea id="preRequirements" name="preRequirements" disabled></textarea>
                        </td>
                    </tr>
                     <tr>
                        <th>项目设计阶段</th>
                        <td colspan="5">
                            <label>
                                <input type="checkbox" id="haveScheme" name="haveScheme" disabled />
                                方案设计
                            </label>
                            <label>
                                <input type="checkbox" id="haveDevelopment" name="haveDevelopment" disabled/>
                                扩初方案设计
                            </label>
                            <label>
                                <input type="checkbox" id="haveDrawing" name="haveDrawing" disabled/>
                                施工图设计及后期服务
                            </label>
                            <label>
                                <input type="checkbox" id="havePlanning" name="havePlanning" disabled/>
                                规划设计
                            </label>
                        </td>
                    </tr>
                </tbody>
            </table>          
            <!-- 公司id暂时为1为了测试 -->
        </form>
    </div>
    <div id="setUpDetailInfo" style="display: none;">
     	<form id="form1" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
               <tr>
                  <th style="text-align: center">经营专员</th>
                  <td width="50%">
                      <select style="width:50% " name="managementPersonnel" id="managementPersonnel">
                              <option value="-1"></option>
                            </select>
                  </td>
               </tr>
               <tr>
                  <th style="text-align: center">技术负责人</th>
                  <td> 
                  </td>
               </tr>
               <tr>
                  <th style="text-align: center"> <label>
                                <input type="checkbox" id="proposerIdCheck" name="proposerIdCheck" onclick="checkProposerId()" checked/>
                                建议
                            </label></th>
                  <td>
                   	  <input name="proposerId" id="proposerId" class="easyui-combobox" style="height:30px;width:215%;" panelHeight="210"/>
                      
                  </td>
               </tr>
               <tr>
                  <th style="text-align: center"> <label>
                                <input type="checkbox" id="inviterIdsCheck" name="inviterIdsCheck" onclick="checkInviterIds()" checked/>
                                邀请
                            </label></th>
                  <td>
                      <input name="inviterIds" id="inviterIds" class="easyui-combobox" multiple="multiple" editable="false" style="height:30px;width:215%;" panelHeight="210"/>
                  </td>
               </tr>
               <tr>
                  <th style="text-align: center">截至日期</th>
                  <td>
                     <input type="text" id="applyDeadline" name="applyDeadline" class="easyui-datebox e-input" data-options="height:25,width:215,editable:false,formatter:formatDate"/>
                  </td>
               </tr>
               <input type="hidden" id="gardenProjectId" name="gardenProjectId" />
               <input type="hidden" id="passed" name="passed" value = "1"/>
            </table>       
          </form>
       </div>
       <div id="setUpFailInfo" style="display: none;">
     	<form id="form2" class="inner-q" method="post">
            <table class="diolag-table" cellpadding="0" cellspacing="0" border="0">
               <tr>
                  <th style="text-align: center">不通过理由</th>
                  <td>
                     <textarea id="reason" name="reason"></textarea>
                  </td>
               </tr>
               <input type="hidden" id="gardenProjectFailId" name="gardenProjectId" />
               <input type="hidden" id="passedFail" name="passed" value = "2"/>
            </table>       
          </form>
       </div>
        <input type="hidden" id="action" name="action" value="${action}">
    </div>

    <script type="text/javascript" src="${res_js }/jslib/activity.js?v=${ver}"></script>
    <script type="text/javascript">
        var windowH = $(window).height();
        var windowW = $(window).width();
        $(function() {
            $("#datagrid").datagrid(
                {
                    url : '${ctx}/manageAudit/user/getProjectManageAudit.htm',
                    title:'经营审核',
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
                        },
                        {
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
                                    if(row.type==50){
                                      //项目修改
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:check("+ row.projectId +","+row.companyId+")'>查看</a>";
                                    }else if(row.type==51){
                                      //合同修改
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkontract("+ row.projectId +","+row.companyId+")'>查看</a>";
                                    }else if(row.type==47){
                                      //保证金
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkbail("+ row.projectId +","+row.companyId+")'>查看</a>";
                                    }else if(row.type==48){
                                      //商务标
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+5+")'>查看</a>";
                                    }else if(row.type==46){
                                      //报名文件
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+2+")'>查看</a>";
                                    }else if(row.type==49){
                                      //合同
                                      return "<a href='javascript:result("+ index +", 1)'>通过</a>|<a href='javascript:result("+ index +", 2)'>不通过</a>|<a href='javascript:checkfiles("+ row.projectId +","+row.companyId+","+9+")'>查看</a>";
                                    }else if(row.type==109){
                                      //项目立项
                                      return "<a href='javascript:checkProjectSet("+ row.projectId +","+ row.companyId +")'>审核</a>";
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
        // 通过/不通过
        function result(index, status) {
            var list = $("#datagrid").datagrid("selectRow", index).datagrid("getSelected");
            var projectId = list.gardenProject.id;
            var step = list.gardenProject.step;
            var subStep = list.gardenProject.subStep;
            var companyId = list.gardenProject.companyId;
            var applicant = list.gardenProject.applicant;
            var amount = list.amount;
            var type = list.type;
            if(type==47){
               step="";
            }else if(type==50){
               step="";
               subStep="";
            }else if(type==51){
               step="";
               subStep="";
            }else{
               subStep="";
            }
            if(type==49 && status==1){
              $.messager.confirm("合同审核", "是否需要总工审核？", function (r) {
               if (r) {
                   $.ajax({
                   url: "/manageAudit/user/updateManageAudit.htm",
                   type: 'post',
                   dataType: 'json',
                   data: {
                    status : status,
                    projectId : projectId,
                    companyId : companyId,
                    applicant : applicant,
                    amount: amount,
                    step : step,
                    subStep : subStep,
                    type: type,
                    ischief:1
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
               }else{
                   $.ajax({
                   url: "/manageAudit/user/updateManageAudit.htm",
                   type: 'post',
                   dataType: 'json',
                   data: {
                    status : status,
                    projectId : projectId,
                    companyId : companyId,
                    applicant : applicant,
                    amount: amount,
                    step : step,
                    subStep : subStep,
                    type: type,
                    ischief:2
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
            }else if(status==1){
                $.ajax({
                   url: "/manageAudit/user/updateManageAudit.htm",
                   type: 'post',
                   dataType: 'json',
                   data: {
                    status : status,
                    projectId : projectId,
                    companyId : companyId,
                    applicant : applicant,
                    amount: amount,
                    step : step,
                    subStep : subStep,
                    type: type,
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
            }else if(status==2){
            	$.messager.prompt('提示', '请输入不通过理由', function(r){
	              if (r){
		              	 $.ajax({
		                   url: "/manageAudit/user/updateManageAudit.htm",
		                   type: 'post',
		                   dataType: 'json',
		                   data: {
		                    status : status,
		                    projectId : projectId,
		                    companyId : companyId,
		                    applicant : applicant,
		                    amount: amount,
		                    step : step,
		                    subStep : subStep,
		                    type: type,
		                    memo:r
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
        function check(id,companyId){
        //获取原来项目数据
           $.ajax({
                url: "/project/user/getGardenProjectDetail.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 0,
                    id : id,
                    companyId : companyId
                },
                success: function (data) {
                    if (data.success) {
                    var oldData= data.rows;
                        $.ajax({
                            url: "/project/user/getGardenProjectDetail.htm",
                            type: 'post',
                            dataType: 'json',
                            data: {
                            status : 3,
                            id : data.rows.memo,
                            companyId : companyId
                         },
                         success: function (data) {
                         if (data.success) {
                         var newData  = data.rows;
                         if(newData.projectNo==null || newData.projectNo==""){
                            if(oldData.designArea == newData.designArea){
                              $("#designAreaTr").hide();
                            }else{
                              $("#designAreaTr td").eq(1).html(oldData.designArea);
                              $("#designAreaTr td").eq(2).html(newData.designArea);
                             }
                         if(oldData.investmentMount == newData.investmentMount){
                           $("#investmentMountTr").hide();
                         }else{
                           $("#investmentMountTr td").eq(1).html(oldData.investmentMount);
                           $("#investmentMountTr td").eq(2).html(newData.investmentMount);
                         }
                         if(oldData.name == newData.name){
                           $("#nameTr").hide();
                         }else{
                           $("#nameTr td").eq(1).html(oldData.name);
                           $("#nameTr td").eq(2).html(newData.name);
                         }
                         if(oldData.category == newData.category){
                           $("#categoryTr").hide();
                         }else{
                           $("#categoryTr td").eq(1).html(oldData.categoryName);
                           $("#categoryTr td").eq(2).html(newData.categoryName);
                         }
                         if(oldData.type == newData.type){
                           $("#typeTr").hide();
                         }else{
                           $("#typeTr td").eq(1).html(oldData.typeName);
                           $("#typeTr td").eq(2).html(newData.typeName);
                         }
                         if(oldData.city == newData.city && oldData.address==newData.address){
                           $("#cityTr").hide();
                         }else{
                           $("#cityTr td").eq(1).html(oldData.cityName+oldData.address);
                           $("#cityTr td").eq(2).html(newData.cityName+newData.address);
                         }
                         if(oldData.contractAwardCompany == newData.contractAwardCompany){
                           $("#contractAwardCompanyTr").hide();
                         }else{
                           $("#contractAwardCompanyTr td").eq(1).html(oldData.companyAssociated.companyName);
                           $("#contractAwardCompanyTr td").eq(2).html(newData.companyAssociated.companyName);
                         }
                          if(oldData.haveScheme == newData.haveScheme){
                           $("#haveSchemeTr").hide();
                         }else{
                           if(oldData.haveScheme==1){
                             $("#haveSchemeTr td").eq(1).html("是");
                           }else{
                             $("#haveSchemeTr td").eq(1).html("否");
                           }
                           if(newData.haveScheme==1){
                             $("#haveSchemeTr td").eq(2).html("是");
                           }else{
                             $("#haveSchemeTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.haveDevelopment == newData.haveDevelopment){
                           $("#haveDevelopmentTr").hide();
                         }else{
                           if(oldData.haveDevelopment==1){
                             $("#haveDevelopmentTr td").eq(1).html("是");
                           }else{
                             $("#haveDevelopmentTr td").eq(1).html("否");
                           }
                           if(newData.haveDevelopment==1){
                             $("#haveDevelopmentTr td").eq(2).html("是");
                           }else{
                             $("#haveDevelopmentTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.haveDrawing == newData.haveDrawing){
                           $("#haveDrawingTr").hide();
                         }else{
                           if(oldData.haveDrawing==1){
                             $("#haveDrawingTr td").eq(1).html("是");
                           }else{
                             $("#haveDrawingTr td").eq(1).html("否");
                           }
                           if(newData.haveDrawing==1){
                             $("#haveDrawingTr td").eq(2).html("是");
                           }else{
                             $("#haveDrawingTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.havePlanning == newData.havePlanning){
                           $("#havePlanningTr").hide();
                         }else{
                           if(oldData.havePlanning==1){
                             $("#havePlanningTr td").eq(1).html("是");
                           }else{
                             $("#havePlanningTr td").eq(1).html("否");
                           }
                           if(newData.havePlanning==1){
                             $("#havePlanningTr td").eq(2).html("是");
                           }else{
                             $("#havePlanningTr td").eq(2).html("否");
                           }
                         }
                         $("#introducerTr").hide();
                         $("#natureTr").hide();
                         $("#preRequirementsTr").hide();
                         $("#needTechnicalTr").hide();
                         $("#introductionTr").hide();
                         $("#managementDepartmentTr").hide();
                         $("#intentionaCooperationTr").hide();
                         $("#projectLiableTr").hide();
                      }else{
                          if(oldData.introducer == newData.introducer){
                           $("#introducerTr").hide();
                         }else{
                           $("#introducerTr td").eq(1).html(oldData.introducerName);
                           $("#introducerTr td").eq(2).html(newData.introducerName);
                         }
                         if(oldData.designArea == newData.designArea){
                           $("#designAreaTr").hide();
                         }else{
                           $("#designAreaTr td").eq(1).html(oldData.designArea);
                           $("#designAreaTr td").eq(2).html(newData.designArea);
                         }
                         if(oldData.investmentMount == newData.investmentMount){
                           $("#investmentMountTr").hide();
                         }else{
                           $("#investmentMountTr td").eq(1).html(oldData.investmentMount);
                           $("#investmentMountTr td").eq(2).html(newData.investmentMount);
                         }
                         if(oldData.name == newData.name){
                           $("#nameTr").hide();
                         }else{
                           $("#nameTr td").eq(1).html(oldData.name);
                           $("#nameTr td").eq(2).html(newData.name);
                         }
                         if(oldData.nature == newData.nature){
                           $("#natureTr").hide();
                         }else{
                           $("#natureTr td").eq(1).html(oldData.natureName);
                           $("#natureTr td").eq(2).html(newData.natureName);
                         }
                         if(oldData.category == newData.category){
                           $("#categoryTr").hide();
                         }else{
                           $("#categoryTr td").eq(1).html(oldData.categoryName);
                           $("#categoryTr td").eq(2).html(newData.categoryName);
                         }
                         if(oldData.type == newData.type){
                           $("#typeTr").hide();
                         }else{
                           $("#typeTr td").eq(1).html(oldData.typeName);
                           $("#typeTr td").eq(2).html(newData.typeName);
                         }
                         if(oldData.managementDepartment == newData.managementDepartment){
                           $("#managementDepartmentTr").hide();
                         }else{
                           $("#managementDepartmentTr td").eq(1).html(oldData.departmentName);
                           $("#managementDepartmentTr td").eq(2).html(newData.departmentName);
                         }
                         if(oldData.city == newData.city && oldData.address==newData.address){
                           $("#cityTr").hide();
                         }else{
                           $("#cityTr td").eq(1).html(oldData.cityName+oldData.address);
                           $("#cityTr td").eq(2).html(newData.cityName+newData.address);
                         }
                         if(oldData.introduction == newData.introduction){
                           $("#introductionTr").hide();
                         }else{
                           $("#introductionTr td").eq(1).html(oldData.introduction);
                           $("#introductionTr td").eq(2).html(newData.introduction);
                         }
                         if(oldData.projectLiable == newData.projectLiable){
                           $("#projectLiableTr").hide();
                         }else{
                           $("#projectLiableTr td").eq(1).html(oldData.projectLiable);
                           $("#projectLiableTr td").eq(2).html(newData.projectLiable);
                         }
                         if(oldData.needTechnical == newData.needTechnical){
                           $("#needTechnicalTr").hide();
                         }else{
                           if(oldData.needTechnical==1){
                             $("#needTechnicalTr td").eq(1).html("是");
                           }else{
                             $("#needTechnicalTr td").eq(1).html("否");
                           }
                           if(newData.needTechnical==1){
                             $("#needTechnicalTr td").eq(2).html("是");
                           }else{
                             $("#needTechnicalTr td").eq(2).html("否");
                           }
                         }
                         if(oldData.contractAwardCompany == newData.contractAwardCompany){
                           $("#contractAwardCompanyTr").hide();
                         }else{
                           $("#contractAwardCompanyTr td").eq(1).html(oldData.companyAssociated.companyName);
                           $("#contractAwardCompanyTr td").eq(2).html(newData.companyAssociated.companyName);
                         }
                          if(oldData.intentionalCooperation == newData.intentionalCooperation){
                           $("#intentionaCooperationTr").hide();
                         }else{
                           $("#intentionaCooperationTr td").eq(1).html(oldData.cooperationName);
                           $("#intentionaCooperationTr td").eq(2).html(newData.cooperationName);
                         }
                         if(oldData.needTechnical==1 && newData.needTechnical==1){
                           if(oldData.preRequirements == newData.preRequirements){
                             $("#preRequirementsTr").hide();
                           }else{
                             $("#preRequirementsTr td").eq(1).html(oldData.preRequirements);
                             $("#preRequirementsTr td").eq(2).html(newData.preRequirements);
                           }
                         }else if(oldData.needTechnical==1 || newData.needTechnical==1){
                           if(oldData.needTechnical==1){
                             $("#preRequirementsTr td").eq(1).html(oldData.preRequirements);
                           }else{
                             $("#preRequirementsTr td").eq(1).html("");
                           }
                           if(newData.needTechnical==1){
                             $("#preRequirementsTr td").eq(2).html(newData.preRequirements);
                           }else{
                             $("#preRequirementsTr td").eq(2).html("");
                           }
                         }else{
                            if(oldData.preRequirements!=newData.preRequirements){
                                $("#preRequirementsTr td").eq(1).html(oldData.preRequirements);
                                $("#preRequirementsTr td").eq(2).html(newData.preRequirements);
                            }else{
                                $("#preRequirementsTr").hide();
                            }
                         }
                         if(oldData.haveScheme == newData.haveScheme){
                           $("#haveSchemeTr").hide();
                         }else{
                           if(oldData.haveScheme==1){
                             $("#haveSchemeTr td").eq(1).html("是");
                           }else{
                             $("#haveSchemeTr td").eq(1).html("否");
                           }
                           if(newData.haveScheme==1){
                             $("#haveSchemeTr td").eq(2).html("是");
                           }else{
                             $("#haveSchemeTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.haveDevelopment == newData.haveDevelopment){
                           $("#haveDevelopmentTr").hide();
                         }else{
                           if(oldData.haveDevelopment==1){
                             $("#haveDevelopmentTr td").eq(1).html("是");
                           }else{
                             $("#haveDevelopmentTr td").eq(1).html("否");
                           }
                           if(newData.haveDevelopment==1){
                             $("#haveDevelopmentTr td").eq(2).html("是");
                           }else{
                             $("#haveDevelopmentTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.haveDrawing == newData.haveDrawing){
                           $("#haveDrawingTr").hide();
                         }else{
                           if(oldData.haveDrawing==1){
                             $("#haveDrawingTr td").eq(1).html("是");
                           }else{
                             $("#haveDrawingTr td").eq(1).html("否");
                           }
                           if(newData.haveDrawing==1){
                             $("#haveDrawingTr td").eq(2).html("是");
                           }else{
                             $("#haveDrawingTr td").eq(2).html("否");
                           }
                         }
                          if(oldData.havePlanning == newData.havePlanning){
                           $("#havePlanningTr").hide();
                         }else{
                           if(oldData.havePlanning==1){
                             $("#havePlanningTr td").eq(1).html("是");
                           }else{
                             $("#havePlanningTr td").eq(1).html("否");
                           }
                           if(newData.havePlanning==1){
                             $("#havePlanningTr td").eq(2).html("是");
                           }else{
                             $("#havePlanningTr td").eq(2).html("否");
                           }
                         }
                         }
                       } else {
                          $.messager.alert("错误", data.resultMsg, "error");
                       }
                }
           });
                       } else {
                          $.messager.alert("错误", data.resultMsg, "error");
                       }
                }
           });
           $("#detailInfo").show();
           $("#detailInfo").dialog({
                title: '修改对比',
                width: windowW - 50,
                height: windowH - 50,
                closable: false,
                buttons:[
                 {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#detailInfo").dialog("close");
                            $("#detailInfo .diolag-table tr").show();
                        }
                  }
                ]
            });
        }

        function checkontract(id,companyId){
         //获取原来合同数据
           $.ajax({
                url: "/contract/user/contractList.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 1,
                    projectId : id,
                    companyId : companyId
                },
                success: function (data) {
                    if (data.success) {
                       var list = data.rows[0];
                       $.ajax({
                            url: "/contract/user/contractList.htm",
                            type: 'post',
                            dataType: 'json',
                            data: {
                            status : 0,
                            projectId : id,
                            companyId : companyId
                         },
                         success: function (data) {
                         if (data.success) {
                           var newLst = data.rows[0];
                           if(list.contractCapital==newLst.contractCapital){
                             $("#contractCapitalTr").hide();
                           }else{
                             $("#contractCapitalTr td").eq(1).html(list.contractCapital);
                             $("#contractCapitalTr td").eq(2).html(newLst.contractCapital);
                           }
                           if(list.signDate==newLst.signDate){
                             $("#signDateTr").hide();
                           }else{
                            list.signDate.substring(0,4)+"/"+list.signDate.substring(4,6)+"/"+list.signDate.substring(6,8);
                             $("#signDateTr td").eq(2).html(newLst.signDate.substring(0,4)+"/"+newLst.signDate.substring(4,6)+"/"+newLst.signDate.substring(6,8));
                           }
                           if(list.contractMemo==newLst.contractMemo){
                             $("#contractMemoTr").hide();
                           }else{
                             $("#contractMemoTr td").eq(1).html(list.contractMemo);
                             $("#contractMemoTr td").eq(2).html(newLst.contractMemo);
                           } 
                           if(newLst.contractPayment!=null || list.contractPayment!=null){
                             var dom="",doms="";
                             if(newLst.contractPayment!=null){
                                for(var j=0;j<newLst.contractPayment.length;j++){
                                   if(j==0){
                                      dom+=newLst.contractPayment[j].paymentPaymentModeName+"/"+newLst.contractPayment[j].paymentAmount;
                                   }else{
                                      dom+="<br/>"+newLst.contractPayment[j].paymentPaymentModeName+"/"+newLst.contractPayment[j].paymentAmount;
                                   }
                                   
                                }
                                 $("#paymentTr td").eq(2).html(dom);
                             }
                             if(list.contractPayment!=null){
                                for(var i=0;i<list.contractPayment.length;i++){
                                   if(i==0){
                                      doms+=list.contractPayment[i].paymentPaymentModeName+"/"+list.contractPayment[i].paymentAmount;
                                   }else{
                                      doms+="<br/>"+list.contractPayment[i].paymentPaymentModeName+"/"+list.contractPayment[i].paymentAmount;
                                   }
                                   
                                }
                                 $("#paymentTr td").eq(1).html(doms);
                             }
                           } else{
                              $("#paymentTr").hide();
                           }                        
                         } else {
                          $.messager.alert("错误", data.resultMsg, "error");
                         }
                         }
                       });
                       }else{
                         $.messager.alert("错误", data.resultMsg, "error");
                       }
                    }
                    });

               $("#contractDetailInfo").show();
               $("#contractDetailInfo").dialog({
                title: '修改对比',
                width: windowW - 50,
                height: windowH - 50,
                closable: false,
                buttons:[
                 {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#contractDetailInfo tr[name='deleteTr']").remove();
                            $("#contractDetailInfo").dialog("close");
                            $("#contractDetailInfo .diolag-table tr").show();
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
        
        // 获取审核类型
        getBasic(13, creteType);
        function creteType(datas) {
            var box = $("#type-search"),
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
	    }
	    
	    
	    function getStaffPerson2() {
	      $("#proposerId").combobox({    
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
            }
	        });
        
           $("#inviterIds").combobox({    
	           url:'/staff/user/getStaffPersons.htm?notJobStatus=111&rows=1000000&orderBy=1',   
	           valueField:'value',    
	           textField:'text',
	           filter: function(q, row){ 
               var opts = $(this).combobox('options'); 
                //return row[opts.textField].indexOf(q) == 0; 
               return row[opts.textField].toLowerCase().indexOf(q.toLowerCase())>-1;//将从头位置匹配改为任意匹配 
               }
	        });
	        
	         $.ajax({
                url: "/staff/user/companyStaffs.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    jobId:4
                },
                success: function (data) {
                    if (data.success) {
                        var boxS = $("#managementPersonnel"),
                        dom = "";
			            for (var i = 0; i < data.data.length; i++) {
			                dom += "<option value='"+ data.data[i].userId +"'>"+ data.data[i].userInfo.name +"</option>";
			            }
			            boxS.append(dom);
                    } else {
                        $.messager.alert("错误", data.errorMsg, "error");
                    }
                }
            });
        }      
	    
	    function checkProposerId(){
	      if(($("#proposerIdCheck").is(':checked'))){
	         $("#proposerId").combobox({  
                    disabled:false
             }); 
	      }else{
		      $("#proposerId").combobox({  
	                disabled:true
	          });
	      }
	      
	    }
	    
	    function checkInviterIds(){
	      if(($("#inviterIdsCheck").is(':checked'))){
	         $("#inviterIds").combobox({  
                    disabled:false
             });  
	      }else{
	      	 $("#inviterIds").combobox({  
                    disabled:true
             }); 
	      }
	      
	    }
	    //项目立项
	    function checkProjectSet(projectId,companyId){
               $("#projectDetailInfo").show();
               $("#projectDetailInfo").dialog({
                title: '项目信息查看',
                width: windowW - 50,
                height: windowH - 50,
                closable: false,
                onBeforeOpen: function () {
                //获取保证金信息
                $.ajax({
                url: "/project/user/list.htm",
                type: 'post',
                dataType: 'json',
                data: {
                    status : 0,
                    id : projectId,
                    companyId : companyId
                },
                success: function (data) {
                    if (data.success) {
                        var list = data.rows[0];
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
			            $("#attribute-s").val(list.natureName);
			            $("#intentionalCooperation").val(list.intentionalCooperation);
			            $("#category-s").val(list.categoryName);
			            $("#type-s").val(list.type);
			            $("#department-s").val(list.departmentName);
			            $("#area").html(list.cityName);
			            $("#step-s").val(list.step);
			            $("#projectLiable").val(list.projectLiable);
			            $("#address").val(list.address);
			            $("#introduction").val(list.introduction);
			            if (list.needTechnical) {
			                $("input[name='needTechnical']").eq(0).attr("checked", true);
			                $("#preRequirements").attr("disabled",true);
			                $("#preRequirements").val(list.preRequirements);
			            } else {
			                $("input[name='needTechnical']").eq(1).attr("checked", true);
			                $("#preRequirements").attr("disabled",true);
			            }
			            if(list.companyAssociated ){
			              $("#contractAwardCompany-s").val(list.companyAssociated.companyName);
			            }
			            if (list.haveScheme) $("#haveScheme").attr("checked", true);
			            if (list.haveDevelopment) $("#haveDevelopment").attr("checked", true);
			            if (list.haveDrawing) $("#haveDrawing").attr("checked", true);
			            if (list.havePlanning) $("#havePlanning").attr("checked", true);
                    }else {
                       $.messager.alert("错误", data.resultMsg, "error");
                    }
                }
            });
                },
                buttons:[
                 {
                        iconCls: 'icons-true',
                        text: '审核通过',
                        handler: function () {
                            $("#setUpDetailInfo").show();
               				$("#setUpDetailInfo").dialog({
			                title: '立项审核通过',
			                width: 700,
			                height: 400,
			                closable: false,
			                onBeforeOpen: function () {
			                getStaffPerson2();
                },
                buttons:[
                 {
                        iconCls: 'icons-true',
                        text: '确定',
                        handler: function () {
                                if ($("#managementPersonnel").val()==-1) {
	                                $.messager.alert("提示", "请选择经营专员", "info");
	                            } else if(!$("#applyDeadline").datebox('getValue')){
                                    $.messager.alert("提示", "请选择截至日期", "info");
                                } else {
			                    $("#gardenProjectId").val(projectId);  
                                $("#form1").attr("action", "/manageAudit/user/approval.htm");
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
                                            $.messager.progress("close");
                                            $("#setUpDetailInfo").dialog("close");
                                            $("#projectDetailInfo").dialog("close");
                                            $.messager.alert("信息","审核成功!","info");
                                            $("#inviterIdsCheck").attr("checked", true);
                                            $("#proposerIdCheck").attr("checked", true);
								            $("#proposerId").combobox({  
								                disabled:false
								            }); 
								            $("#inviterIds").combobox({  
								                disabled:false
								            }); 
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
                 },
                 {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#setUpDetailInfo").dialog("close");
                        }
                  }
                ]
            });
                        }
                 },
                 {
                        iconCls: 'icons-true',
                        text: '审核不通过',
                        handler: function () {
                            $("#setUpFailInfo").show();
               				$("#setUpFailInfo").dialog({
			                title: '立项审核不通过',
			                width: 600,
			                height: 200,
			                closable: false,
			                onBeforeOpen: function () {
                },
                buttons:[
                 {
                        iconCls: 'icons-true',
                        text: '确定',
                        handler: function () {
                                if ($("#reason").val()=="") {
	                                $.messager.alert("提示", "请输入不通过理由", "info");
	                            } else {
			                    $("#gardenProjectFailId").val(projectId);  
                                $("#form2").attr("action", "/manageAudit/user/approval.htm");
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
                                        if(list.success){
                                            $("#form2")[0].reset();
                                            $.messager.progress("close");
                                            $("#setUpFailInfo").dialog("close");
                                            $("#projectDetailInfo").dialog("close");
                                            $.messager.alert("信息","审核成功!","info");
                                            $("#form2 :input[type='hidden']").val("");
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
                            $("#setUpFailInfo").dialog("close");
                        }
                  }
                ]
            });
                        }
                 },
                 {
                        iconCls: 'icons-close',
                        text: '关闭',
                        handler: function () {
                            $("#projectDetailInfo").dialog("close");
                        }
                  }
                ]
            });
        }
    </script>
</body>
<jsp:include page="/WEB-INF/pages/file/upLoad.jsp" />
</html>