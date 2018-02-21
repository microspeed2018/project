function loadMenu(parent, that) {
	jQuery.getJSON("/checkAuth.htm", function(data) {
		if(data.isAuth == "false"){
			window.top.location.reload();
		} else {
			$.ajax({
				url : ctx.home + "/home/index.htm",
				type : 'post',
				dataType : 'json',
				data : {
					'action': 'getSecondaryMenu',
					'parent': parent
				},
				success : function(data) {
					if (data.success) {
						var ms = data.menus;
						if (ms.length > 0) {
							var menuDom = $("#menu");
							var child = ms;

							var dom = "";
							var box = that.closest('li').find("dl");
							if ($.isArray(child) && child.length > 0) {

								// 创建二级导航
								for (var j = 0; j < child.length; j++) {
									var url = child[j].url;
									dom += "<dd>- <a url='" + url.replace(/^(.*?)(_mc)?$/, "$1") + "' " + (/^.*_mc$/.test(url) ? "count='true'" : "count='false'") + " href='javascript:void(0)'>" + child[j].text + "</a></dd>";
								}

								$("a[count='true']", menuDom).each(function() {
									var dom = $(this);
									var url = dom.attr("url");
									$.get(url + (url.indexOf("?") > 0 ? "_menuCount&t=" + new Date().getTime() : "?action=menuCount&t=" + new Date().getTime()), {}, function(data) {
										if (data.success) {
											if (data.count > 0) {
												dom.html((data.autoStop ? "<font color='red'>" + dom.text() + "</font>" : dom.text()) + "<font color='red'>(" + data.count + ")</font>");
											} else if (data.autoStop) {
												dom.html((data.autoStop ? "<font color='red'>" + dom.text() + "</font>" : dom.text()));
											}
										} else {
											dom.html((data.autoStop ? "<font color='red'>" + dom.text() + "</font>" : dom.text()) + "<font color='red'>(ERR)</font>");
										}
									}, "json");
								});
							}

							box.empty();
							box.append(dom);
							addspan();
							bindMenuAction();
							//$(that.siblings("dl").find("a").eq(0)).click();
						}
					} else {
						$("#menuMsg").text("菜单加载失败...");
					}
				}
			});
		}
	});
}
function addspan () {
	$("#nav li span").click(function () {
		var dom = "<em class='ink'></em>";
		$(".ink").remove();
		$(this).append(dom);
		getMousePos(event);
	});
}

/*获取点击位置的坐标*/
function getMousePos(event) {
    var e = event || window.event;
    $(".ink").css({
    	"left" : e.pageX - 30
    });
}

function bindMenuAction() {
	$("#nav dd a, .tool dd a").click(function() {
		$(this).closest("dd").addClass("curr").siblings().removeClass("curr");
		$("#easyUi-tabs").show();
		$("#iframe").hide();
		var title = $(this).html();
		var url = $(this).attr("url");
		addTab(title, url);
		createEvent();
	});

	function addTab(title, url){
        if ($('#easyUi-tabs').tabs('exists', title)){
            $('#easyUi-tabs').tabs('select', title);
        } else {
            var content = '<iframe frameborder="0"  src="'+ url +'" width="100%" height="100%"></iframe>';
            $('#easyUi-tabs').tabs('add',{
                title:title,
                content:content,
                closable:true
            });
        }
    }
}

// 右键关闭事件
var flag = true;
function createEvent() {
	document.oncontextmenu = function(e){
    	e.preventDefault();
    };
    $('#easyUi-tabs').find(".tabs li").mousedown(function(e){
    	$(this).trigger("click");
    	// 右键
		if(3 == e.which){
			var left = e.pageX;
			var top = e.pageY;
			createMenu(left, top);
		}
    })

    // 创建右键弹出菜单
    function createMenu(left, top) {
    	$(".mousedown").css({left: left, top: top}).show();
    }

    // 离开菜单隐藏
    $(".mousedown").mouseleave(function () {
    	$(this).hide();
    });

    // 关闭全部
    $("#close-all").on("click", function () {
        closeAll();
    });
    function closeAll() {
        $(".tabs li").each(function(index, obj) {
            var tab = $(".tabs-closable", this).text();
            $(".easyui-tabs").tabs('close', tab);
        });
        $("#iframe").show();
        $("#easyUi-tabs").hide();
        $(".mousedown").hide();
    }

    // 关闭当前
    $("#close-curr").on("click", function () {
        if (flag) closeCurr();
    });
    function closeCurr() {
        flag = false;
        $(".tabs .tabs-selected").each(function(index, obj) {
            var tab = $(".tabs-closable", this).text();
            $(".easyui-tabs").tabs('close', tab);
        });
        $(".mousedown").hide();
    }

    // 关闭其他
    $("#close-other").on("click", function () {
        closeOther();
    });
    function closeOther() {
        var currTab = $(".tabs-selected .tabs-title").html();
        $(".tabs li").each(function(index, obj) {
            var tab = $(".tabs-closable", this).text();
            if (tab != currTab) {
                $(".easyui-tabs").tabs('close', tab);
            }
        });
        $(".mousedown").hide();
    }
}

/**
    * 单个关闭选项卡，关闭最后一个之后回到欢迎页
**/
$(function () {
    $("#easyUi-tabs").tabs({
        onClose: function(title,index){
            if (!$(".tabs li").length) {
                $("#iframe").show();
                $("#easyUi-tabs").hide();
            }
        }
    });
});