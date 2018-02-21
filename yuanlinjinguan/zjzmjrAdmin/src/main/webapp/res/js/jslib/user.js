
function queryReset() {
    $(".query #form")[0].reset();
    $(".query :input[type='hidden']").val("");
}
function recommendDetail(recommendUserId,userId){
	detail(recommendUserId,userId,$("#recommendDetailForm .myTbody"));
	 $("#recommendDetail").dialog({
	      title: '推荐明细',
	      width: 500,
	      height: 300,
	      closable: false,
	      buttons: [
	          {
	              iconCls: 'icons-close',
	              text: '关闭',
	              handler: function () {
	                  $("#recommendDetail").dialog("close");
	              }
	          }
	      ]
	  });
}
function detail(recommendUserId,userId,tbody){
	$.ajax({
		url : "/user/getAccountBalanceDetailList.htm",
		type : 'post',
		dataType : 'json',
		data : {
			recommendUserId : recommendUserId,
			userId : userId
		},
		success : function(data) {
			$.messager.progress("close");
			if (!data.success) {
                $.messager.alert("错误", data.errorMsg, "error");
                return;
            }
                var content = data.data;
                var actualCredit="";
                actualCredit += '<div class="recommend-detail">';
                actualCredit += '<div><h3><ul>';
                actualCredit += '<li><span>总积分:</span><em>'+data.sum+'</em></li>';
                actualCredit += '</ul></h3></div>';
                actualCredit +='<div><ul><li><span>被推荐人</span>&nbsp;&nbsp;<span>业务发生时间</span>&nbsp;&nbsp;<span>业务处理</span>&nbsp;&nbsp;<span>获取积分</span></li>';
                for(var i=0;i<data.data.length;i++){
                	actualCredit +='<li><span>'+content[i].name+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>'+content[i].registerTime+
                	'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>'+content[i].transactionTypeName+'</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<span>'
                	+content[i].rewardSource+'</span>&nbsp;&nbsp;<span>';
                }
                actualCredit +='</ul></div>';
                tbody.html(actualCredit);
		}
	});
}

function queryUser() {
	var param = {
		action : "queryByPage"
	};
	var userId = $("#userId").val(),
	userCode = $("#userCode").val(),
	name = $("#name").val();
	if(userId != ""){
		param.userId = userId;
	}
	if(userCode != ""){
		param.userCode = userCode;
	}
	if(name != ""){
		param.name = name;
	}
	param.status=$('#status').val();
	param.authorityId=$('#authorityId').val()==-1?null:$('#authorityId').val();
	$("#datagrid").datagrid("load", param);
}
//id 为用户ID
function showLocation(id) {
	map_load(id);
	$("#map").dialog({
		title : '地图',
		height : 500,
		width : 800,
		closed:false,
		buttons : [ {
			iconCls : 'icons-close',
			text : '关闭',
			handler : function() {
				$("#map").dialog("close");
			}
		} ]
	});
}

//激活用户 id用用户Id
function activate(id, version, mobileNo){
	getUser(id, version, mobileNo, '', $("#productForm .myTbody"));
	updateEditForm('激活用户');
}

function getUser(id, version, mobileNo, isUpdate, tbody){
	//将返回的信息放入修改的对话框
    var content;
	content  ="<input type='hidden' value='"+id+"' id='userId' name='userId' style='width:0px;' />";
	content  +="<input type='hidden' value='"+version+"' id='version' name='version' style='width:0px;' />";
	if(mobileNo != null && mobileNo != ''){
		content +="<tr><th style='width: 80px;'>手机号码</th><td style='width: 250px;' ><input id='userCode' name='userCode'  style='width: 250px;' value='" + mobileNo +"'/></td></tr>";
	}else{
		content +="<tr><th style='width: 80px;'>手机号码</th><td style='width: 250px;' ><input id='userCode' name='userCode'  style='width: 250px;' /></td></tr>";
	}
    content +="<tr><th></th><td><span style='color:red'>若您确定激活该用户并且输入的手机号码已被注册,原用户将被删除!请确认无误后激活!</span></td></tr>";
    tbody.html(content);
}

function updateEditForm(stitle){
    $("#productForm").attr("action", "/user/userOper.htm?action=activateUser");
    $("#productEdit").dialog({
        title: stitle,
        width: 500,
        height: 200,
        closable: false,
        buttons: [
            {
            	iconCls: 'icons-true',
                text: '确定',
                handler: function () {

                    $("#productForm").form("submit", {
                        onSubmit: function () {
                            $.messager.progress({interval: 200, text: '处理中...'});
                            return true;
                        },
                        success: function (data) {
                            $.messager.progress("close");
                            data = parseResp(data);
                            if (data.success) {
                                $.messager.alert("信息", "操作成功", "info");
                                $("#productEdit").dialog("close");
                                queryUser();// 新建成功 重新查询
                            } else {
                                $.messager.alert("错误", data.resultMsg, "error");
                            }
                        }
                    });
                }
            },
            {
                iconCls: 'icons-close',
                text: '取消',
                handler: function () {
                	//destroyEditor();
                    $("#productEdit").dialog("close");
                }
            }
        ]
    });
}

function map_init(id) {
	$.ajax({
		url : "/user/getLoginRecord.htm",
		type : 'post',
		dataType : 'json',
		data : {
			userId : id
		},
		beforeSend : addProcess(),
		success : function(data) {
			$.messager.progress("close");
			if (data.success) {
				var markerArr = [];
				var markerArrAddress = [];
				var times = "";
				var lat = "";
				var lon = "";
				for(var i = 0; i <data.data.length; i++){
//					loc = data.data[i].longitude.toString() + "," + data.data[i].latitude.toString();
					times = data.data[i].accessTimes;
					lat = (lat + data.data[i].latitude) + (((i + 1)== data.data.length) ? '':',');
					lon = (lon + data.data[i].longitude) + (((i + 1)== data.data.length) ? '':',');
					markerArr[i] = {title : "登录次数:" + times};
				}
				$.ajax({
					url : "/user/getAddressinate.htm",
					type : 'post',
					dataType : 'json',
					data : {
						lat: lat,
 						lon: lon
					},
					success : function(data) {
						if(data.success){
							for(var i = 0; i < data.data.length; i++){
								markerArrAddress[i] = {address: data.data[i].address,
								                       point : data.data[i].latitude + "," + data.data[i].longitude};
							}
							if(markerArrAddress.length > 0){
								map(markerArr,markerArrAddress);
							}else{
								$("#map").dialog("close");
								$.messager.alert("提示", "无常用登录地址", "error");
							}
						}else{
							$.messager.alert("错误", data.resultMsg, "error");
						}
					}
				});
			} else {
				$.messager.alert("错误", data.resultMsg, "error");
			}
		}
	});
}

function map(markerArr,markerArrAddress){
	var map = new BMap.Map("map"); // 创建Map实例
    var point = new BMap.Point(markerArrAddress[0].point.split(",")[0], markerArrAddress[0].point.split(",")[1]); //地图中心点，次数最多的位置
    map.centerAndZoom(point, 13); // 初始化地图,设置中心点坐标和地图级别。
    map.enableScrollWheelZoom(true); //启用滚轮放大缩小
    //向地图中添加缩放控件
    var ctrlNav = new window.BMap.NavigationControl({
        anchor: BMAP_ANCHOR_TOP_LEFT,
        type: BMAP_NAVIGATION_CONTROL_LARGE
    });
    map.addControl(ctrlNav);

    //向地图中添加缩略图控件
    var ctrlOve = new window.BMap.OverviewMapControl({
        anchor: BMAP_ANCHOR_BOTTOM_RIGHT,
        isOpen: 1
    });
    map.addControl(ctrlOve);

    //向地图中添加比例尺控件
    var ctrlSca = new window.BMap.ScaleControl({
        anchor: BMAP_ANCHOR_BOTTOM_LEFT
    });
    map.addControl(ctrlSca);

    var point = new Array(); //存放标注点经纬信息的数组
    var marker = new Array(); //存放标注点对象的数组
    var info = new Array(); //存放提示信息窗口对象的数组
    var i = markerArr.length - 1;
    for (i < 6; i >= 0; i--) {
        var p0 = markerArrAddress[i].point.split(",")[0]; //
        var p1 = markerArrAddress[i].point.split(",")[1]; //按照原数组的point格式将地图点坐标的经纬度分别提出来
        point[i] = new window.BMap.Point(p0, p1); //循环生成新的地图点
        marker[i] = new window.BMap.Marker(point[i]); //按照地图点坐标生成标记
        map.addOverlay(marker[i]);
        marker[i].setAnimation(BMAP_ANIMATION_BOUNCE); //跳动的动画
        var label = new window.BMap.Label(markerArr[i].title, { offset: new window.BMap.Size(20, -10) });
        marker[i].setLabel(label);
        info[i] = new window.BMap.InfoWindow("<p style=’font-size:12px;lineheight:1.8em;’>" + markerArr[i].title +
        		"</br>地址：" + markerArrAddress[i].address); // 创建信息窗口对象  + "</br> 经纬度：" + markerArrAddress[i].point + "</br></p>"
    }
    marker[0].addEventListener("mouseover", function () {
        this.openInfoWindow(info[0]);
    });
    marker[1].addEventListener("mouseover", function () {
        this.openInfoWindow(info[1]);
    });
    marker[2].addEventListener("mouseover", function () {
        this.openInfoWindow(info[2]);
    });
    marker[3].addEventListener("mouseover", function () {
        this.openInfoWindow(info[3]);
    });
    marker[4].addEventListener("mouseover", function () {
        this.openInfoWindow(info[4]);
    });
    marker[5].addEventListener("mouseover", function () {
        this.openInfoWindow(info[5]);
    });
}
//异步调用百度js
function map_load(id) {
    var load = document.createElement("script");
    load.src = "http://api.map.baidu.com/api?v=1.4";//&callback=map_init
    map_init(id);
    document.body.appendChild(load);
}