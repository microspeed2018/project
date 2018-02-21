var bHeight = $(window).height();
var bWidth = $(window).width();

function getContentTime(name) {
    return "<input type='text' name='" + name + "' class='easyui-datetimebox input mydate " + name + "' data-options='required:true,validType:\"date[15]\"' style='width:180px'/>";
}

function escapeXML(content){
	return $.trim(content).replace(/</g,"&lt;");
}

function parseResp(data){
	if(data&&/^{.+}$/.test(data)){
		return $.parseJSON(data);
	}
	checkAuth();
	return {success:false,resultMsg:"服务器响应错误"};
}

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
		fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "")
				.substr(4 - RegExp.$1.length));
	for ( var k in o)
		if (new RegExp("(" + k + ")").test(fmt))
			fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k])
					: (("00" + o[k]).substr(("" + o[k]).length)));
	return fmt;
};

$.extend($.fn.validatebox.defaults.rules, {
		minLength : { // 判断最小长度
            validator : function(value, param) {
                return value.length >= param[0];
            },
            message : '最少输入 {0} 个字符。'
        },
        length : {
            validator : function(value, param) {
                var len = $.trim(value).length;
                return len >= param[0] && len <= param[1];
            },
            message : "输入内容长度必须介于{0}和{1}之间."
        },
        empty : {
            validator : function(value) {
                return true;
            },
            message : "最少输入 0个字符。"
        },
        phone : {// 验证电话号码
            validator : function(value) {
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
                        .test(value);
            },
            message : '格式不正确,请使用下面格式:020-88888888'
        },
        mobile : {// 验证手机号码
            validator : function(value) {
                return /^(13|15|18)\d{9}$/i.test(value);
            },
            message : '手机号码格式不正确'
        },
        idcard : {// 验证身份证
            validator : function(value) {
                return /^\d{15}(\d{2}[A-Za-z0-9])?$/i.test(value);
            },
            message : '身份证号码格式不正确'
        },
        intOrFloat : {// 验证整数或小数
            validator : function(value) {
                return /^\d+(\.\d+)?$/i.test(value);
            },
            message : '请输入数字，并确保格式正确'
        },
        currency : {// 验证货币
            validator : function(value) {
                return /^(\-)?\d+(\.\d{1,2})?$/i.test(value);
            },
            message : '货币格式不正确'
        },
        qq : {// 验证QQ,从10000开始
            validator : function(value) {
                return /^[1-9]\d{4,9}$/i.test(value);
            },
            message : 'QQ号码格式不正确'
        },
        integer : {// 验证整数
            validator : function(value) {
                return /^[+]?[1-9]+\d*$/i.test(value);
            },
            message : '请输入整数'
        },
        chinese : {// 验证中文
            validator : function(value) {
                return /^[\u0391-\uFFE5]+$/i.test(value);
            },
            message : '请输入中文'
        },
        english : {// 验证英语
            validator : function(value) {
                return /^[A-Za-z]+$/i.test(value);
            },
            message : '请输入英文'
        },
        unnormal : {// 验证是否包含空格和非法字符
            validator : function(value) {
                return /.+/i.test(value);
            },
            message : '输入值不能为空和包含其他非法字符'
        },
        username : {// 验证用户名
            validator : function(value) {
                return /^[a-zA-Z][a-zA-Z0-9_]{5,15}$/i.test(value);
            },
            message : '用户名不合法（字母开头，允许6-16字节，允许字母数字下划线）'
        },
        faxno : {// 验证传真
            validator : function(value) {
                // return /^[+]{0,1}(\d){1,3}[ ]?([-]?((\d)|[
                // ]){1,12})+$/i.test(value);
                return /^((\(\d{2,3}\))|(\d{3}\-))?(\(0\d{2,3}\)|0\d{2,3}-)?[1-9]\d{6,7}(\-\d{1,4})?$/i
                        .test(value);
            },
            message : '传真号码不正确'
        },
        zip : {// 验证邮政编码
            validator : function(value) {
                return /^[1-9]\d{5}$/i.test(value);
            },
            message : '邮政编码格式不正确'
        },
        ip : {// 验证IP地址
            validator : function(value) {
                return /d+.d+.d+.d+/i.test(value);
            },
            message : 'IP地址格式不正确'
        },
        name : {// 验证姓名，可以是中文或英文
            validator : function(value) {
                return /^[\u0391-\uFFE5]+$/i.test(value)
                        | /^\w+[\w\s]+\w+$/i.test(value);
            },
            message : '请输入姓名'
        },
        carNo : {
            validator : function(value) {
                return /^[\u4E00-\u9FA5][\da-zA-Z]{6}$/.test(value);
            },
            message : '车牌号码无效（例：粤J12350）'
        },
        carenergin : {
            validator : function(value) {
                return /^[a-zA-Z0-9]{16}$/.test(value);
            },
            message : '发动机型号无效(例：FG6H012345654584)'
        },
        email : {
            validator : function(value) {
                return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
                        .test(value);
            },
            message : '请输入有效的电子邮件账号(例：abc@126.com)'
        },
        msn : {
            validator : function(value) {
                return /^\w+([-+.]\w+)*@\w+([-.]\w+)*\.\w+([-.]\w+)*$/
                        .test(value);
            },
            message : '请输入有效的msn账号(例：abc@hotnail(msn/live).com)'
        },
        password:
        {
            validator: function(value, param)
            {
                var other = $(param[0]).val();
                return value == other;
            },
            message: "两次密码不一致"
        },
        password_rule:
        {
            validator: function(value, param)
            {
                return /^(?=.*\d.*)(?=.*[a-zA-Z].*).{6,50}$/
                        .test(value);
            },
            message: "密码不合法（至少6位，必须有字母和数字）"
        },
        img_upload:
        {
            validator: function(value, param)
            {
                var ext = value.substring(value.lastIndexOf(".")+1);
                return /(jpg|jpeg|gif|bmp|png)/i
                        .test(ext);
            },
            message: "只允许上传jpg、gif、png、bmp格式的图片"
        },
        url :
        {// url
            validator : function(value) {
                if(value==""){
                    return true;
                }else{
                    return /^(https?|ftp):\/\/(((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:)*@)?(((\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5])\.(\d|[1-9]\d|1\d\d|2[0-4]\d|25[0-5]))|((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?)(:\d*)?)(\/((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)+(\/(([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)*)*)?)?(\?((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|[\uE000-\uF8FF]|\/|\?)*)?(\#((([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(%[\da-f]{2})|[!\$&'\(\)\*\+,;=]|:|@)|\/|\?)*)?$/i.test(value);
                }
            },
            message : 'url不合法'
        },
        date :
	    {
	         validator : function(value, param) {
		                return value.length >= param[0];
		     },
		     message : '请输入正确的时间'
	    },
		rate:
		{
			 validator : function(value, param) {
				        return parseInt(value) >= 100;
			 },
			 message : '年化收益率不能超过100%'
		}
    });

$.fn.datebox.defaults.formatter = function(date){
	return date.Format("yy-MM-dd");
}
$.fn.datebox.defaults.parser = function(dateStr){
	if(/^(\d{2,4})\-(\d{1,2})\-(\d{1,2})$/.test(dateStr)){
		var year = parseInt(RegExp.$1,10);
		var month = parseInt(RegExp.$2,10)-1;
		var day = parseInt(RegExp.$3,10);
		return new Date(year<100?(year<50?2000+year:1900+year):year,month,day);
	}
	return new Date();
}

$.extend({
	_:function(s){if($.no(s))s=$.t();document.title=s;},
	// 获取对象(不带缓存)
	$:function(o){return $.isS(o)?$('#'+o):$(o);},
	// 获取对象(带缓存)
	o:function(o){if(!$.isS(o))return $(o);var obj=$.os[o];if(obj)return obj;obj=$('#'+o);if(obj.length>0)$.os[o]=obj;return obj;},os:{},
	// 获取对象值
	v:function(o,v){if(v)$.$(o).val(v);else{return $.trim($.$(o).val());}},
	// 返回当前时间
	t:function(){return $.d().getTime();},
	// 返回当前时间对象
	d:function(t){if(t)return new Date(t);return new Date()},
	// 转换成整数
	n:function(s){return parseInt(s);},
	// 转换成浮点数
	f:function(s){return parseFloat(s);},
	// 判断对象是否存在
	no:function(){var as=arguments;for(var i=0;i<as.length;i++)if(as[i]==null || as[i]==undefined)return true;return false;},
	// 判断对象类型
	isS:function(o){return typeof o=="string"},
	isN:function(o){return typeof o=="number"},
	isB:function(o){return typeof o=="boolean"},
	isO:function(o){return typeof o=="object"},
	// 是否包含指定内容
	cc:function(cs,c,n){var e=!$.no(n);for(var i=0;i<cs.length;i++)if((e && cs[i][n]==c) || (!e && cs[i]==c))return i;return -1;},
	// 返回RegExp
	re:function(s,c){var r=new RegExp(s);if(c)return r.test(c);return r;},
	// 是否IE浏览器
	ie:function(v){if(!$.browser.msie)return false;if(v)return $.browser.version==v || $.browser.version.indexOf(v+'.')==0;return true;},
	// 创建DOC对象
	ceok:false,
	ce:function(n){return $(document.createElement(n));},
	// 从字符串中获取第一个数值
	nv:function(s,sv){var si=-1,ei=-1,i=0;if(sv){i=s.indexOf(sv);if(i<0)i=0;}for(;i<s.length;i++)if(si==-1){if(s.charAt(i)>='0' && s.charAt(i)<='9')si=i;}else{if(s.charAt(i)<'0' || s.charAt(i)>'9'){ei=i;break;}}return $.n(si==-1 && ei==-1 ? -1 : (ei==-1 ? s.substr(si) : s.substring(si,ei)));},
	// 数值四舍五入
	round:function(n,mantissa){if(!mantissa)mantissa=0;if(mantissa<=0)return Math.round(n);var v=1;for(var i=0;i<mantissa;i++)v*=10;return Math.round(n*v)/v;},
	// 金额格式化
	formatMoney : function(num,n) {
	    num = String(num.toFixed(n));
	    var re = /(-?\d+)(\d{3})/;
	    while(re.test(num)) num = num.replace(re,"$1,$2")
	    return num;
	},
	// 字符串替换
	replace:function(s,s1,s2){return s.replace(new RegExp(s1,'g'),s2);},
	// 字符串长度(中文算2个)
	strlen:function(s){return s.replace(/[^\x00-\xff]/g,"**").length},
	// 字符串是否包含中文
	strch:function(s){return /[^\x00-\xff]+/.test(s)},
	// 清除字符串中的'"字符和头尾空格
	clear:function(){var as=arguments,s;if(as.length<1)return '';s=as[0];if(as.length<2)as=[s,"'",'"'];for(var i=1;i<as.length;i++)s=$.replace(s,as[i],'');return $.trim(s);},
	// cookie操作
	getCookie:function(name,dv){var d=document.cookie;var il1=d.indexOf(name+'=');if(il1==-1)return $.no(dv) ? null : dv;il1+=name.length+1;var il2=d.indexOf(';',il1);if(il2==-1)il2=d.length;return unescape(d.substring(il1,il2));},
	setCookie:function(name,value,expires,path,domain,secure){var s=new Text()._(name)._('=')._(escape(value));if(!expires || (expires && expires!='temp')){var day=60*60*24*1000;if(expires=='day')expires=$.d($.t()+day);else if(expires=='week')expires=$.d($.t()+day*7);else if(expires=='month')expires=$.d($.t()+day*30);else if(expires=='year')expires=$.d($.t()+day*365);else{expires=$.d($.t()+day*365*100);}s._(';expires=')._(expires.toGMTString());}if(path)s._(';path=')._(path);if(domain)s._(';domain=')._(domain);if(secure)s._(';secure=')._(secure);document.cookie=s;},
	delCookie:function(name,path,domain){var s=new Text()._(name)._('=null;expires=')._($.d($.t()-100000000).toGMTString());if(path)s._(';path=')._(path);if(domain!=null)s._(';domain=')._(domain);document.cookie=s;},
	clrCookie:function(path,domain){var ds=document.cookie.split(';');for(var i=0;i<ds.length;i++)$.delCookie($.trim(ds[i].split('=')[0]),path,domain);},
	// 获取Flash对象
	getFlash:function(name){if($.ie())return window[name];else if($.browser.mozilla)return document[name+'-1'];else{var fl=window[name+'-1'];if(!fl)fl=window[name];if(!fl)fl=document[name+'-1'];return fl;}},
	// 初始化对象
	init:function(o,dv){if(!o)return dv;for(i in dv)if($.no(o[i]))o[i]=dv[i];return o;},
	humanTimeOffset:function(time1,time2){
		var offset = Math.abs(time2-time1);
		var day = Math.floor(offset/(24*3600*1000));
		var hour = Math.floor((offset - day*24*3600*1000)/(3600*1000));
		var minute = Math.floor((offset - day*24*3600*1000 - hour*3600*1000)/(60*1000));
		var msg = "";
		var len = 0;
		if(day>0){
			len++;
			msg += day+"天";
		}
		if(day>0||hour>0){
			len++;
			msg += hour+"时";
		}
		if(len<2){
			msg += minute+"分";
		}
		return msg;
	},
	convertBorwserDate:function(date){
		if(/^(\d{4})-(\d{1,2})-(\d{1,2})(\s\d+:\d+:\d+)?$/.test($.trim(date))){
			return RegExp.$2+"/"+RegExp.$3+"/"+RegExp.$1+RegExp.$4;
		}
		return date;
	}
});
$.extend(Date.prototype,{
	toDate:function(s){var t=this;if(!s)s=new Text();s._(t.getFullYear());s._('-');if(t.getMonth()+1<10)s._('0');s._(t.getMonth()+1);s._('-');if(t.getDate()<10)s._('0');s._(t.getDate());return s.ts();},
	toTime:function(s){var t=this;if(!s)s=new Text();if(t.getHours()<10)s._('0');s._(t.getHours());s._(':');if(t.getMinutes()<10)s._('0');s._(t.getMinutes());s._(':');if(t.getSeconds()<10)s._('0');s._(t.getSeconds());return s.ts();},
	toDatetime:function(){var t=this,s=new Text();t.toDate(s);s._(' ');t.toTime(s);return s.ts();}
});
function Text(){this.s;this.b=[];};Text.prototype={
	_:function(s){var t=this;t.b.push(s);t.s=null;return t;},
	clear:function(){this.b=[];this.s=null;},
	length:function(){return this.ts().length;},
	toHtml:function(o){o=$.$(o);if(o.length==0)return;o[0].innerHTML=this.ts();},
	toString:function(){var t=this;if(!t.s)t.s=t.b.join('');return t.s;},
	ts:function(){return this.toString();}
};
var Formatter = function() {
	var MATH_ROUND = 4;// 四舍五入
	var MATH_CEIL = 0; // 进位
	var MATH_FLOOR = 1;// 舍位
	return {

		// 金额，保留2位小数
		formatAmount : function(v) {
			return Formatter.formatPrec(v, 2, true);
		},

		formatNormalPrec : function(v, prec) {
			return Formatter.formatPrec(v, prec, false);
		},

		// 比例:val:值,flag=true,放大100倍
		formatPercent : function(val, flag) {
			if (isNaN(val) || trim(val).length == 0)
				return val;
			var str = val > 0 ? "+" : "";
			if (flag) {
				val = val * Math.pow(10, 2);
			}
			return str + Formatter.formatPrec(val, 2) + "%";
		},

		formatPrec : function(v, prec, split, mathType) {
			if (isNaN(v) || trim(v).length == 0)
				return v;
			prec = (prec == undefined || typeof prec != "number" || prec < 0) ? 2
					: prec;
			v = parseFloat(v);
			var whole, sub;
			var mathfn = Math.round;
			if (typeof mathType == "number") {
				switch (mathType) {
				case MATH_CEIL:
					mathfn = Math.ceil;
					break;
				case MATH_FLOOR:
					mathfn = Math.floor;
					break;
				}
			}
			if (prec != 0) {
				v = (mathfn((v - 0) * Math.pow(10, prec))) / Math.pow(10, prec);
				var zeroFill = String(Math.pow(10, prec)).substring(1);
				v = (v == Math.floor(v)) ? v + "." + zeroFill : v;
				v = String(v);
				var ps = v.split('.');
				whole = ps[0];
				sub = ps[1] ? '.'
						+ (ps[1].length == prec ? ps[1] : ps[1]
								+ String(Math.pow(10, prec - ps[1].length))
										.substring(1)) : '.' + zeroFill;
			} else {
				whole = String(mathfn(v));
				sub = '';
			}
			if (split && split == true) {
				var r = /(\d+)(\d{3})/;
				while (r.test(whole)) {
					whole = whole.replace(r, '$1' + ',' + '$2');
				}
			}
			return whole + sub;
		}

	};
}();
var YYYYMMDD = "yyyy-MM-dd";
var YYYYMMDD_HHMM = "yyyy-MM-dd hh:mm";
var YYYYMMDD_HHMMSS = "yyyy-MM-dd hh:mm:ss";
var YYMMDD_HHMMSS = "yy-MM-dd hh:mm:ss";
/**
 * 计算时间
 *
 * @param day
 *            距离今天的天数
 * @param fmt
 *            格式
 */
function getTodayLeave(day, fmt) {
	var timeStamp = new Date().getTime();
	var dayStamp = 0;
	if (day) {
		dayStamp = day * 86400 * 1000;
	}
	var dateStr = new Date(timeStamp + dayStamp).Format(fmt);
	return dateStr;
}

function getDay(timeStamp,fmt){
	var dateStr = new Date(timeStamp).Format(fmt);
	return dateStr;
}

function trim(str) {

	return $.trim(str);
};

function addProcess(){
	$.messager.progress({interval:200,text:'处理中...'});
}

function Alert(messages, callback) {
	if ($(".ec_tip").length < 1) {
		$("body").append("<div class=\"ec_tip\">" + messages + "</div>");
	}
	// 定位居中显示
	leftW = (document.body.clientWidth - $(".ec_tip").width()) / 2;
	topH = (document.body.clientHeight - $(".ec_tip").height()) / 2;
	$(".ec_tip").css("top", topH + "px").css("left", leftW + "px").fadeIn(2000);
	// 谈出效果并执行回调
	$(".ec_tip").animate({
		top : "0px",
		opacity : 0
	}, 2000, function() {
		$(".ec_tip").remove();
		if (callback != undefined)
			callback();
	});
}

function formatDate(strDate){
	return (new Date(strDate)).Format("yyyy/MM/dd");
}

/**
* 验证身份证号
* param: 省份证号
* return:  0=位数错误 1=号码错误 2=正确
* EX: zjzm.identification("53010219200508011x")
**/
function identification (num) {
	var num = num.toUpperCase().split(''),                              // 大写/格式化成数组
	    ratio = [7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2],  // 前17未对应的系数
	    verification = [1, 0, 'X' , 9, 8, 7, 6, 5, 4, 3, 2],            // 最后一位对应值
	    type,
	    total = 0;

	if (num.length != 18 && num.length != 15) {
	    // 位数不对，返回错误信息
	    type = 0;
	} else {
	    if (num.length == 18) {
	        for (var i = 0; i < ratio.length; i++) {
	            total += Number(ratio[i]) * Number(num[i]);
	        }
	        // 计算方式 ： 前17位身份证号乘以对应的系数再除以11取余，用余数为索引去取最后一位对应的值，把值与身份证最后一位做比较
	        if (verification[total % 11] != num[num.length - 1]) {
	            // 末尾验证不过，返回错误信息
	           	type = 1;
	        } else {
	        	// 通过
	            type = 2;
	        }
	    }
	}
	return type;
}

function mobileValidate(mobile) {
	var pattern = "";
	if (mobile.length != 11) {
		pattern = /([0-9]{3,4}-)?[0-9]{7,8}/;
	} else {
		pattern = /^1[3|5|7|8|][0-9]{9}$/;
	}
	if (!pattern.test(mobile) && mobile!="") {
		return false;
	} else {
		return true;
	}
};

/**
 * 金额转大写
 * @param money
 * @returns
 */
function changeNumMoneyToChinese(money) {
	  var cnNums = new Array("零", "壹", "贰", "叁", "肆", "伍", "陆", "柒", "捌", "玖"); //汉字的数字
	  var cnIntRadice = new Array("", "拾", "佰", "仟"); //基本单位
	  var cnIntUnits = new Array("", "万", "亿", "兆"); //对应整数部分扩展单位
	  var cnDecUnits = new Array("角", "分", "毫", "厘"); //对应小数部分单位
	  var cnInteger = "整"; //整数金额时后面跟的字符
	  var cnIntLast = "元"; //整型完以后的单位
	  var maxNum = 999999999999999.9999; //最大处理的数字
	  var IntegerNum; //金额整数部分
	  var DecimalNum; //金额小数部分
	  var ChineseStr = ""; //输出的中文金额字符串
	  var parts; //分离金额后用的数组，预定义
	  if (money == "") {
	    return "";
	  }
	  money = parseFloat(money);
	  if (money >= maxNum) {
	    alert('超出最大处理数字');
	    return "";
	  }
	  if (money == 0) {
	    ChineseStr = cnNums[0] + cnIntLast + cnInteger;
	    return ChineseStr;
	  }
	  money = money.toString(); //转换为字符串
	  if (money.indexOf(".") == -1) {
	    IntegerNum = money;
	    DecimalNum = '';
	  } else {
	    parts = money.split(".");
	    IntegerNum = parts[0];
	    DecimalNum = parts[1].substr(0, 4);
	  }
	  if (parseInt(IntegerNum, 10) > 0) { //获取整型部分转换
	    var zeroCount = 0;
	    var IntLen = IntegerNum.length;
	    for (var i = 0; i < IntLen; i++) {
	      var n = IntegerNum.substr(i, 1);
	      var p = IntLen - i - 1;
	      var q = p / 4;
	      var m = p % 4;
	      if (n == "0") {
	        zeroCount++;
	      } else {
	        if (zeroCount > 0) {
	          ChineseStr += cnNums[0];
	        }
	        zeroCount = 0; //归零
	        ChineseStr += cnNums[parseInt(n)] + cnIntRadice[m];
	      }
	      if (m == 0 && zeroCount < 4) {
	        ChineseStr += cnIntUnits[q];
	      }
	    }
	    ChineseStr += cnIntLast;
	    //整型部分处理完毕
	  }
	  if (DecimalNum != '') { //小数部分
	    var decLen = DecimalNum.length;
	    for (var i = 0; i < decLen; i++) {
	      var n = DecimalNum.substr(i, 1);
	      if (n != '0') {
	        ChineseStr += cnNums[Number(n)] + cnDecUnits[i];
	      }
	    }
	  }
	  if (ChineseStr == '') {
	    ChineseStr += cnNums[0] + cnIntLast + cnInteger;
	  } else if (DecimalNum == '') {
	    ChineseStr += cnInteger;
	  }
	  return ChineseStr;

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

/**
 * 判断是否为空
 * @param value
 * @returns
 */
function isEmpty(value){
	if(value == null || value == "" || value == undefined){
		return true;
	}else{
		return false;
	}
}

//删除指定下标的数组
Array.prototype.remove=function(obj){
	for(var i =0;i <this.length;i++){
		var temp = this[i];
		if(!isNaN(obj)){
			temp=i;
		}
		if(temp == obj){
			for(var j = i;j <this.length;j++){
				this[j]=this[j+1];
			}
			this.length = this.length-1;
		}
	}
};

/* 暂无数据 */
function nullData(data, index) {
	if (data.resultMsg == "暂无数据") {
		setTimeout(function () {
			if (index != undefined) {
				$("body").find(".datagrid-view2 .datagrid-body").eq(index).addClass('asdasdasdas');
				$("body").find(".datagrid-view2 .datagrid-body").eq(index).html("<div class='nullData'><img src='"+ctx.res_img+"/nullData.png' /><p>查询无记录</p></div>");
			} else {
				$("body").find(".datagrid-view2 .datagrid-body").html("<div class='nullData'><img src='"+ctx.res_img+"/nullData.png' /><p>查询无记录</p></div>");
			}
		}, 100);
	} else {
		$.messager.alert("错误", data.resultMsg, "error");
	}
	data.rows = [];
	data.total = 0;
	return;
}

/**
 * 判断是否是图片/视频
 * @param data
 * @returns
 */
function checkFile(data) {
	// - strFilter必须是小写列举
	var strFilter = ".jpeg|.gif|.jpg|.png|.bmp|.pic|";
	var strFilter1 = ".mp4|.rmvb|.avi|.ts|.flv|.wmv|.mov|";
	var strFilter2 = ".pdf|";
	if (data.indexOf(".") > -1) {
		var p = data.lastIndexOf(".");
		var strPostfix = data.substring(p, data.length) + '|';
		strPostfix = strPostfix.toLowerCase();
		if (strFilter.indexOf(strPostfix) > -1 || strPostfix.indexOf("/") > -1) {
			return 1;
		} else if(strFilter1.indexOf(strPostfix) > -1){
			return 2;
		} else if(strFilter2.indexOf(strPostfix) > -1){
			return 3;
		} else {
			return 0;
		}
	}
	return 0;
}

/**
 * 格式化金额显示
 */
function formatMoney(value){
	value = Number(value);
	if ($.isN(value)) {
		if(value == "" || value.toString() == "NaN"){
			return "";
		} else {
			return $.formatMoney(value, 2);
		}
	} else {
		return value;
	}
}

// 公司资质
var ggzz = [
	{key : 1, name : "风景园林工程设计专项"},
	{key : 2, name : "城乡规划编制"},
	{key : 3, name : "绿化造林设计"}
];
// 资质等级
var zzdj = [
	{key : 1, name : "甲级"},
	{key : 2, name : "乙级"},
	{key : 3, name : "丙级"}
];
// 公司属性
var ggsx = [
	{key : 1, name : "建设单位"},
	{key : 2, name : "施工单位"},
	{key : 3, name : "设计院"}
];
// 项目性质
var xmxz = [
	{key : 1, name : "新建"},
	{key : 2, name : "改造"}
];
// 项目类别
var xmlb = [
	{key : 1, name : "景观"},
	{key : 2, name : "规划"},
	{key : 3, name : "建筑"},
	{key : 4, name : "EPC"},
	{key : 5, name : "旅游"}
];
// 项目类别
var xmlb = [
	{key : 1, name : "景观"},
	{key : 2, name : "规划"},
	{key : 3, name : "建筑"},
	{key : 4, name : "EPC"},
	{key : 5, name : "旅游"}
];
// 项目类型
var xmlx = [
	{key : 1, name : "市政公园"},
	{key : 2, name : "市政道路"},
	{key : 3, name : "市政河道"},
	{key : 4, name : "地产住宅"},
	{key : 5, name : "地产商业"},
	{key : 6, name : "城乡规划"},
	{key : 7, name : "特色城镇"}
];
// 经营部门
var zjbm = [
	{key : 1, name : "经营一部"},
	{key : 2, name : "经营二部"}
];
// 财务审核类型
var cwshlx = [
	{key : 1, name : "审核保证金"},
	{key : 2, name : "审核合同"},
	{key : 3, name : "审核开发票"},
	{key : 4, name : "审核发票"}
];
// 出纳确认类型
var cnqrlx = [
	{key : 1, name : "保证金打款"},
	{key : 2, name : "分包付款"},
	{key : 3, name : "金额到账"}
];
// 行政盖章类型
var xzgzlx = [
	{key : 1, name : "报名"},
	{key : 2, name : "商务标"},
	{key : 3, name : "技术标"},
	{key : 4, name : "合同"}
];
// 合同付款方式
var htfkfs = [
	{key : 1, name : "定金"},
	{key : 2, name : "方案设计"},
	{key : 3, name : "扩初设计"},
	{key : 4, name : "施工图设计"},
	{key : 5, name : "后期服务"},
	{key : 6, name : "规划初稿"},
	{key : 7, name : "规划成果"}
];
// 外部人员性质
var wbryxz = [
	{key : 1, name : "设计"},
	{key : 2, name : "经营"}
];
// 经营审核类型
var zyshlx = [
	{key : 1, name : "报名"},
	{key : 2, name : "保证金"},
	{key : 3, name : "商务标"},
	{key : 4, name : "合同"},
	{key : 5, name : "项目修改"},
	{key : 6, name : "合同修改"}
];
// 院办审核类型
var ybshlx = [
	{key : 1, name : "保证金"},
	{key : 2, name : "商务标"},
	{key : 3, name : "技术标"},
	{key : 4, name : "合同"},
	{key : 5, name : "分包"}
];
// 总工审核类型
var zsbshlx = [
	{key : 1, name : "技术标"},
	{key : 2, name : "方案设计"},
	{key : 3, name : "扩初设计"},
	{key : 4, name : "施工图设计"},
	{key : 5, name : "规划设计"}
];

var zjzm = {};
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

function checkfiles(projectId,companyId,fileId){
    //获取文件
    $.ajax({
         url: "/fileManage/user/getFileManageList.htm",
         type: 'post',
         dataType: 'json',
         data: {
             fileId : fileId,
             fileIds : fileId,
             projectId : projectId,
             companyId : companyId
         },
         success: function (data) {
             if (data.success) {                       
                var dataArr = data.rows[0].fileAddress.split(",");
                var fileType = checkFile(data.rows[0].fileAddress);
                if(fileType == 2){
				      //视频
				      for ( var i = 0; i < dataArr.length; i++) {
					    fileDetail(encodeURIComponent(dataArr[i]));
				      }
			       } else if(fileType == 1){
				      //图片或未识别
				      var opts = "<ul class='imgZoom' id='index"+ data.rows[0].id +"' style='display: none;'>";
				      for (var i = 0; i < dataArr.length; i++) {
					    opts += "<li style='float: left; margin: 5px;'><img data-original='"+ dataArr[i] +"' src='"+ dataArr[i] +"'/></li>";
			          } 
			          opts += "</ul>";
				      $("#fileDiv").html(opts);
				      setTimeout(function () {
				      	$("#index"+data.rows[0].id).find("img").click(function () {
							$("#fileDiv").find(".imgZoom").viewer();
						}); 
				      	showImgViewer(data.rows[0].id);
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
             }else{
                $.messager.alert("错误", data.resultMsg, "error");
             }
         }
    });
 }

getQueryString = function (name) {
    var reg = new RegExp('(^|&)' + name + '=([^&]*)(&|$)', 'i');
    var r = window.location.search.substr(1).match(reg);
    if (r != null) {
        return unescape(r[2]);
    }
    return null;
}

