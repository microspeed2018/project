/**
 * time: 2016.5.3
 * author: fuzhongkuo.com
 *
 **/
var zjzm = zjzm || {};
var global = {
		viewWidth: document.body.scrollWidth,
		viewHeight: document.body.scrollHeight,
		ua: window.navigator.userAgent.toLowerCase(),
		mobileReg: /^1[3|4|5|7|8|][0-9]{9}$/,
		emailReg: /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/,
		pwdReg: /^([0-9a-zA-Z_]|[~!@#$%^&*\(\)\_\+\=\{\}\[\]\?\/\,\.]){6,20}$/,
		cardReg: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/,
		apiURL: ''
	},
	base = {
		fontSize: 50,
		maxWidth: 640,
		menu: [
			"首页",
			"项目",
			"消息",
			"我的"
		]
	};

// 检测设备是否在微信环境中
function isWeiXin() {
	if (global.ua.match(/MicroMessenger/i) == 'micromessenger') {
		return true;
	} else {
		return false;
	}
}
if (isWeiXin()) {
	document.getElementsByTagName("title")[0].innerHTML = "";
}

/**
 * init font size
 **/
zjzm.initFontSize = function(viewWidth) {
	var fontSize = viewWidth / base.maxWidth * base.fontSize;
	document.getElementsByTagName("html")[0].style.fontSize = fontSize + "px";
}
zjzm.initFontSize(global.viewWidth);
window.addEventListener("resize", function () {
	var viewWidth = document.documentElement.clientWidth;
	zjzm.initFontSize(viewWidth);
});

function mySetLocalStorage(k, v) {
	if (mui.os.plus) {
		if (typeof v == "string") {
			plus.storage.setItem(k, v);
		} else {
			plus.storage.setItem(k, JSON.stringify(v));
		}
	} else {
		if (typeof v == "string") {
			localStorage.setItem(k, v);
		} else {
			localStorage.setItem(k, JSON.stringify(v));
		}
	}
}

function myGetLocalStorage(k) {
	if (mui.os.plus) {
		return plus.storage.getItem(k);
	} else {
		return localStorage.getItem(k);
	}
}

function myRemoveLocalStorage(k) {
	if (mui.os.plus) {
		plus.storage.removeItem(k);
	} else {
		localStorage.removeItem(k);
	}
}

function myClearStorage() {
	if (mui.os.plus) {
		plus.storage.clear();
	} else {
		localStorage.clear();
	}

}

function myOpenWindow(url, id) {
	if (mui.os.plus) {
		mui.openWindow({
			url: url,
			id: id,
			createNew: true,
			show: {
				aniShow: true
			},
			waiting: {
				autoShow: true
			},
			styles: {
				scrollIndicator: 'none'
			}
		});
	} else {
		mui.openWindow({
			url: url,
			id: id,
			show: {
				aniShow: 'pop-in'
			}
		});
	}
}

function myOpenWindowFooter(url, id) {
	if (mui.os.plus) {
		mui.openWindow({
			url: url,
			id: id,
			show: {
				aniShow: false
			},
			waiting: {
				autoShow: false
			},
			styles: {
				scrollIndicator: 'none'
			}
		});
	} else {
		mui.openWindow({
			url: url,
			id: id,
			show: {
				aniShow: 'pop-in'
			}
		});
	}
}

function myInit(callback) {
	if (mui.os.plus) {
		mui.plusReady(function() {
			callback();
		});
	} else {
		callback();
	}
}

function myToast(parm) {
	if (mui.os.plus) {
		plus.nativeUI.toast(parm);
	} else {
		mui.toast(parm);
	}
}

function myAlert(parm, callback) {
	if (mui.os.plus) {
		if (!callback) {
			plus.nativeUI.alert(parm);
		} else {
			plus.nativeUI.alert(parm, callback());
		}
	} else {
		if (!callback) {
			mui.alert(parm);
		} else {
			mui.alert(parm, callback());
		}
	}
}

function myConfirm(txt, success, fail) {
	if (mui.os.plus) {
		plus.nativeUI.confirm(txt, function(e) {
			if (e.index == 0) {
				if (success) {
					success();
				}
			} else {
				if (fail) {
					fail();
				}
			}
		});
	} else {
		mui.confirm(txt, function(e) {
			if (e.index == 1) {
				if (success) {
					success();
				}
			} else {
				if (fail) {
					fail();
				}
			}
		});
	}
}

function myLoading() {
	if (mui.os.plus) {
		plus.nativeUI.showWaiting();
	} else {
		mask.loading();
	}
}

function myLoadingOver() {
	if (mui.os.plus) {
		plus.nativeUI.closeWaiting();
	} else {
		mask.remove('mask');
	}
}

function myCommon() {
	if (mui.os.plus) {
		// api url
		global.apiURL = "http://service.project.cbylsj.com/";
		mui.plusReady(function() {
			// 隐藏滚动条
			plus.webview.currentWebview().setStyle({
				scrollIndicator: 'none'
			});
		});
	} else {
		global.apiURL= window.location.origin;
	}
}
myCommon();

/**
 * REG
 **/
// 验证手机号
zjzm.mobileValidate = function(mobile) {
	if (!global.mobileReg.test(mobile)) {
		return false;
	} else {
		return true;
	}
};
// 验证email
zjzm.emailValidate = function(email) {
	if (!global.emailReg.test(email)) {
		return false;
	} else {
		return true;
	}
};
// 验证密码
zjzm.pwdValidate = function(pwd) {
	if (!global.pwdReg.test(pwd)) {
		return false;
	} else {
		return true;
	}
};

/**
	* 验证身份证号
	* param: 身份证号
	* return:  false 错误号码  true正确号码
	* EX: zjzm.identification("53010219200508011x")
**/
zjzm.identification = function (num) {
    var num = num.toUpperCase().split(''),                              // 大写/格式化成数组
        ratio = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],  // 前17未对应的系数
        verification = [1, 0, 'X' , 9, 8, 7, 6, 5, 4, 3, 2],            // 最后一位对应值
        type,
        total = 0;

    if (num.length != 18 && num.length != 15) {
        // 位数不对，返回错误信息
        type = false;
    } else {
        if (num.length == 18) {
            for (var i = 0; i < ratio.length; i++) {
                total += Number(ratio[i]) * Number(num[i]);
            }
            // 计算方式：前17位身份证号乘以对应的系数再除以11取余，用余数为索引去取最后一位对应的值，把值与身份证最后一位做比较
            if (verification[total % 11] != num[num.length - 1]) {
                // 末尾验证不过，返回错误信息
               	type = false;
            } else {
            	// 通过
                type = true;
            }
        }
    }
    return type;
}

/**
 * trim 去空格
 **/
zjzm.trim = function (param) {
	return param.replace(/\s/g, "");
}

/**
 * count down
 **/
zjzm.countDown = function(_that, time) {
	var thisVal = _that.innerHTML,
		time = time,
		i = 1,
		start = setInterval(function() {
			var countDownTime = time - i;
			if (i > time - 1) {
				i = 1;
				_that.removeAttribute("disabled");
				_that.innerHTML = thisVal;
				clearInterval(start);
			} else {
				_that.setAttribute("disabled", true);
				_that.innerHTML = countDownTime + "秒后重发";
				i++;
			}
		}, 1000);
}

/**
 * mask
 **/
function createMask() {
	var box = document.createElement("div");
	box.setAttribute("class", "mask");
	document.body.appendChild(box);
}

var mask = {
	loading: function() {
		createMask();
		document.querySelector(".mask").innerHTML = "<img src='" + global.apiURL + "/images/icon/loading.gif' class='loading' />";
	},
	remove: function(c) {
		if (c) document.querySelector('.' + c).remove();
	},
	filter: function() {
		createMask();
	}
};

/**
	* 项目阶段
	* param : step值
	* ex : zjzm.step('step')
**/
zjzm.step = function (step) {
	var status = "";
	switch (step) {
		case 5:
			status = "项目保存";
			break;
		case 10:
			status = "项目录入";
			break;
		case 20:
			status = "待项目立项";
			break;
		case 30:
			status = "待负责人反馈";
			break;
		case 40:
			status = "待确立负责人";
			break;
		case 50:
			status = "待录入招标公告";
			break;
		case 60:
			status = "待上传报名拟定";
			break;
		case 70:
			status = "待经营审核报名";
			break;
		case 80:
			status = "待技术审核报名";
			break;
		case 90:
			status = "报名行政盖章";
			break;
		case 100:
			status = "待上传报名文件";
			break;
		case 110:
			status = "待上传招标文件";
			break;
		case 120:
			status = "待申请保证金";
			break;
		case 130:
			status = "待经营审核保证金";
			break;
		case 140:
			status = "待技术审核保证金";
			break;
		case 150:
			status = "待财务审核保证金";
			break;
		case 160:
			status = "待院办审核保证金";
			break;
		case 170:
			status = "待出纳保证金打款";
			break;
		case 190:
			status = "待上传商务标拟定";
			break;
		case 200:
			status = "待经营审核商务标";
			break;
		case 210:
			status = "待技术审核商务标";
			break;
		case 220:
			status = "待院办审核商务标";
			break;
		case 230:
			status = "待商务标行政盖章";
			break;
		case 240:
			status = "待上传技术标拟定";
			break;
		case 250:
			status = "待总工审核技术标";
			break;
		case 260:
			status = "待院办审核技术标";
			break;
		case 270:
			status = "待技术标行政盖章";
			break;
		case 280:
			status = "待上传投标文件";
			break;
		case 290:
			status = "待录入开标记录";
			break;
		case 300:
			status = "待上传合同拟定";
			break;
		case 310:
			status = "待经营审核合同";
			break;
		case 315:
			status = "待总工审核合同";
			break;
		case 320:
			status = "待技术审核合同";
			break;
		case 330:
			status = "待财务审核合同";
			break;
		case 340:
			status = "待法务审核合同";
			break;
		case 350:
			status = "待院办审核合同";
			break;
		case 360:
			status = "待合同行政盖章";
			break;
		case 370:
			status = "待上传合同文件";
			break;
		case 380:
			status = "待申请合作比例";
			break;
		case 390:
			status = "待院办审核合作比例";
			break;
		case 400:
			status = "待上传方案初稿";
			break;
		case 410:
			status = "待总工审核方案";
			break;
		case 420:
			status = "待上传方案成果";
			break;
		case 430:
			status = "待上传扩初初稿";
			break;
		case 440:
			status = "待总工审核扩初";
			break;
		case 450:
			status = "待上传扩初成果";
			break;
		case 460:
			status = "待上传施工初稿";
			break;
		case 470:
			status = "待总工审核施工";
			break;
		case 480:
			status = "待上传施工成果";
			break;
		case 490:
			status = "待上传规划初稿";
			break;
		case 500:
			status = "待总工审核规划";
			break;
		case 510:
			status = "待上传规划成果";
			break;
		case 520:
			status = "待上传竣工报告";
			break;
		case 530:
			status = "待终止";
			break;
		case 610:
			status = "正常";
			break;
		case 620:
			status = "中止";
			break;
		case 630:
			status = "正常";
			break;
		case 640:
			status = "终止";
			break;
		case 818:
			status = "项目修改";
			break;
		default:
			break;
	}
	return status;
};
zjzm.subStep = function (step) {
	var status = "";
	switch (step) {
		case 120:
			status = "待申请保证金";
			break;
		case 130:
			status = "待经营审核保证金";
			break;
		case 140:
			status = "待技术审核保证金";
			break;
		case 150:
			status = "待财务审核保证金";
			break;
		case 160:
			status = "待院办审核保证金";
			break;
		case 170:
			status = "待出纳保证金打款";
			break;
		case 190:
			status = "待上传商务标拟定";
			break;
		case 200:
			status = "待经营审核商务标";
			break;
		case 210:
			status = "待技术审核商务标";
			break;
		case 220:
			status = "待院办审核商务标";
			break;
		case 230:
			status = "待商务标行政盖章";
			break;
		case 240:
			status = "待上传技术标拟定";
			break;
		case 250:
			status = "待总工审核技术标";
			break;
		case 260:
			status = "待院办审核技术标";
			break;
		case 270:
			status = "待技术标行政盖章";
			break;
	}
	return status;
};
zjzm.subStep2 = function (step) {
	var status = "";
	switch (step) {
		case 120:
			status = "待申请保证金";
			break;
		case 130:
			status = "待经营审核保证金";
			break;
		case 140:
			status = "待技术审核保证金";
			break;
		case 150:
			status = "待财务审核保证金";
			break;
		case 160:
			status = "待院办审核保证金";
			break;
		case 170:
			status = "待出纳保证金打款";
			break;
		case 190:
			status = "待上传商务标拟定";
			break;
		case 200:
			status = "待经营审核商务标";
			break;
		case 210:
			status = "待技术审核商务标";
			break;
		case 220:
			status = "待院办审核商务标";
			break;
		case 230:
			status = "待商务标行政盖章";
			break;
		case 240:
			status = "待上传技术标拟定";
			break;
		case 250:
			status = "待总工审核技术标";
			break;
		case 260:
			status = "待院办审核技术标";
			break;
		case 270:
			status = "待技术标行政盖章";
			break;
	}
	return status;
};

/**
	* 初始角色
	* param : userCode
	* ex : zjzm.step('userCode')
**/
zjzm.role = function (userCode) {
	var status = "";
	switch (userCode) {
		case 1:
			status = "院长";
			break;
		case 2:
			status = "院办";
			break;
		case 3:
			status = "经营部经理";
			break;
		case 4:
			status = "经营专员";
			break;
		case 5:
			status = "技术总监";
			break;
		case 6:
			status = "技术专员";
			break;
		case 7:
			status = "会计";
			break;
		case 8:
			status = "出纳";
			break;
		case 9:
			status = "行政";
			break;
		case 10:
			status = "法务";
			break;
		case 11:
			status = "总师办";
			break;
		case 12:
			status = "总工助理";
			break;
		default:
			break;
	}
	return status;
};

/**
 * againLogin
 **/
function againLogin(callback, param) {
	var user = JSON.parse(myGetLocalStorage("user"));
	if (user != null) {
		var mobile = user.mobile,
			springtoken = user.springtoken;
		mui.ajax(global.apiURL + '/yztz_user_login_check.htm', {
			data: {
				username: mobile,
				password: "123456",
				springtoken: springtoken
			},
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			success: function(data) {
				if (data.success) {
					setUserInfo(data, callback, param);
				} else {
					mui.toast(data.resultMsg);
				}
			},
			error: function(xhr, type, errorThrown) {
				mui.toast("服务器正忙，请稍后重试！");
			}
		});
	} else {
		myAlert('您还未登录，请先登录', callbacks);

		function callbacks() {
			myOpenWindow('/tpl/login/login.html', 'login');
		}
	}
}

function setUserInfo(datas, callback, param) {
	mui.ajax(global.apiURL + '/user/staffLogin.htm', {
        data : {},
        dataType : 'json',
        type : 'post',
        success : function(data){
            if (data.success) {
                var data = data.data;
                var company = data[0].company;
                if (!company) {
                    company = data[0].companyName;
                }
                mySetLocalStorage("user", {
                    "springtoken"       : datas.springtoken,
                    "nickname"          : datas.userName,
                    "mobile"            : data[0].userInfo.username,
                    "name"              : data[0].userInfo.name,
                    "jobId"             : data[0].userInfo.jobId,
                    "personnelNature"   : data[0].personnelNature,
                    "userId"            : data[0].userId,
                    "company"           : company,
                    "imageAddress"      : data[0].userInfo.imageAddress
                });
                if (param.length < 1) {
					callback();
				} else {
					callback(param);
				}
            } else {
                myToast(data.resultMsg);
            }
        },
        error : function() {
            myToast("服务器正忙，请稍后重试！");
        }
    });
}

/**
 * format price
 **/
function formatCurrency(num) {
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
	return (((sign) ? '' : '-') + num + '.' + cents);
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

/**
 * format bank code
 **/
zjzm.formatBankCode = function(value) {
	return value.replace(/\s/g, '').replace(/(\d{4})(?=\d)/g, "$1 ");
}

/**
 * 创建广告列表
 **/
zjzm.initAdsList = function (num) {
	// get ads images
	function getAdsImages(num) {
		var fileName;
		if (num == 1) {
			fileName = "项目页banner";
		} else {
			fileName = "首页banner";
		}
		mui.ajax(global.apiURL + '/fileUpLoad/getFileByBasic.htm', {
			data: {
				categoryId: 29,
				attributeId: 4,
				fileName: fileName
			},
			dataType: 'json',
			type: 'post',
			timeout: 10000,
			success: function(data) {
				if (data.success) {
					var datas = data.data;
					if (datas.length) {
						createAdsList(datas);
						createAdsBtn(datas);
						var slider = mui("#slider");
						slider.slider({
							interval: 5000
						});
					}
				} else {
					document.querySelector(".banner").style.display = "none"
				}
			},
			error: function(xhr, type, errorThrown) {
				mui.toast("服务器正忙，请稍后重试！");
			}
		});
	};
	getAdsImages(num);

	// create ads list
	function createAdsList(data) {
		var dom = "",
			box = document.querySelector(".mui-slider-group");
		for (var i = 0; i < data.length; i++) {
			//var source = data[i];
			var src = data[i].fileAddress;
			var url = '';
			if (data.length != 1) {
				/**
				 * 数据多条
				 **/
				// 第一条数据 插入到最后
				if (i == 0) {
					domFirst = "<div class='mui-slider-item'>";
					if (url == '') {
						domFirst += "<img src='" + src + "'>";
					} else {
						domFirst += "<a href='" + url + "'>" +
							"<img src='" + src + "'>" +
							"</a>";
					}
					domFirst += "</div>";
				}
				// 最后一条数据 插入到最前
				if (i == data.length - 1) {
					domEnd = "<div class='mui-slider-item'>";
					if (url == '') {
						domEnd += "<img src='" + src + "'>";
					} else {
						domEnd += "<a href='" + url + "'>" +
							"<img src='" + src + "'>" +
							"</a>";
					}
					domEnd += "</div>";
				}
				// 循环体数据
				dom += "<div class='mui-slider-item'>";
				if (url == '') {
					dom += "<img src='" + src + "'>";
				} else {
					dom += "<a href='" + url + "'>" +
						"<img src='" + src + "'>" +
						"</a>";
				}
				dom += "</div>";
			} else {
				/**
				 * 数据一条
				 **/
				domEnd = domFirst = dom += "<div class='mui-slider-item'>";
				if (url == '') {
					dom += "<img src='" + src + "'>";
				} else {
					dom += "<a href='" + url + "'>" +
						"<img src='" + src + "'>" +
						"</a>";
				}
				dom += "</div>";

			}
		}
		box.innerHTML = domEnd + dom + domFirst;
	}

	// create ads button
	function createAdsBtn(data) {
		var dom = '',
			box = document.querySelector(".mui-slider-indicator");
		if (data.length != 1) {
			for (var i = 0; i < data.length; i++) {
				if (i == 0) {
					dom += "<div class='mui-indicator mui-active'></div>";
				} else {
					dom += "<div class='mui-indicator'></div>";
				}
			}
			box.innerHTML = dom;
		}
	}
}


/**
 * 格式化日期
 * data 日期  格式为20161009
 * symbol 分割符  例如’/‘
 * 返回 2016/10/09
 **/
zjzm.formatDate = function(data, symbol) {
	var newData;
	if (data.length == 8) {
		var year = data.substring(0, 4);
		var month = data.substring(4, 6);
		var day = data.substring(6, 8);
		newData = year + symbol + month + symbol + day;
	} else if (data.length == 6) {
		var year = data.substring(0, 4);
		var month = data.substring(4, 6);
		newData = year + symbol + month;
	} else {
		return;
	}
	return newData;
};


/**
* 时间转化
* 参数: 时间戳,分隔符
* 用例: zjzm.getDay("1488185873000",yyyy/MM/dd)
**/
Date.prototype.Format = function(fmt) {
	var o = {
		"M+" : this.getMonth() + 1,
		"d+" : this.getDate(),
		"h+" : this.getHours(),
		"m+" : this.getMinutes(),
		"s+" : this.getSeconds(),
		"q+" : Math.floor((this.getMonth() + 3) / 3),
		"S" : this.getMilliseconds()
	};
	if (/(y+)/.test(fmt))
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};
zjzm.getDay = function(timeStamp,fmt){
	var dateStr = new Date(timeStamp).Format(fmt);
	return dateStr;
}

/**
	* 历史url
	* param : curr当前页面url
**/
zjzm.historyUrl = function(curr) {
	mySetLocalStorage("curr", curr);
};


/**
	* JS获取URL中参数值
	* param : 参数名
	* ex : zjzm.getQueryString('status') 获取url中status对应的值
**/
zjzm.getQueryString = function (name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

/**
	get message number
**/
function getMesNum() {
	mui.ajax(global.apiURL + '/message/user/getMessageCount.htm', {
		data: {},
		dataType: 'json',
		type: 'post',
		timeout: 10000,
		success: function(data) {
			if (data.success) {
				var num = data.num;
				if (num != 0) {
					if (num >= 99) {
						num = 99;
					}
					document.querySelector(".message-num").style.display = "block";
					document.querySelector(".message-num").innerHTML = num;
				} else {
					document.querySelector(".message-num").style.display = "none";
				}
			} else {
				if (data.code == "405" && data.resultMsg == "请登录系统") {
					againLogin(getMesNum, []);
				}
			}
		},
		error: function() {
			mui.toast("服务器正忙，请稍后重试！");
		}
	});
}

/**
 * 获取本地推送标识信息
 */
function getPushInfo(){
	if (mui.os.plus) {
		var info = plus.push.getClientInfo();
		alert( "获取客户端推送标识信息：" );
		alert("id: "+info.id);
		alert( "token: "+info.token );
		alert( "clientid: "+info.clientid );
		alert( "appid: "+info.appid );
		alert( "appkey: "+info.appkey );
	}
}