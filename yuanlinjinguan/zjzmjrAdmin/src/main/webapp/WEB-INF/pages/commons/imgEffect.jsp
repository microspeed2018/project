<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="${res }/imgeffect/js/Image.0.1.min.js?v=${ver}"></script>
<script type="text/javascript" src="${res }/imgeffect/js/ImageTrans.js?v=${ver}"></script>
<script type="text/javascript">
<!--
/*
 * 在外部定义一个div控件。将div控件的id和图片的地址传入这个方法，
 * 就能够在这个div中对图片的效果控制
 */
function doImgEffect(idContainer, src){

    //var container = $$("idContainer"), src = "images/zoomFullScreen.jpg",
    var container = $$(idContainer), content;
    content = '<div id="btncontainer">';
    content += '<img id="idLeft'+idContainer+'" src="${res }/imgeffect/images/left.png" title="左旋转" />';
    content += '<img id="idRight'+idContainer+'" src="${res }/imgeffect/images/right.png"  title="右旋转" />';
    content += '<img id="idVertical'+idContainer+'" src="${res }/imgeffect/images/chuizhi.png"  title="垂直旋转" />';
    content += '<img id="idHorizontal'+idContainer+'" src="${res }/imgeffect/images/shuiping.png"  title="水平旋转" />';
    content += '</div>';
    container.innerHTML = content;
    container.setAttribute("class", "idContainer");
    var options = {
            mode: "css3",
            onPreLoad: function () { container.style.backgroundImage = "url('${res }/imgeffect/images/loading.gif')"; },
            onLoad: function () { container.style.backgroundImage = ""; },
            onError: function (err) { container.style.backgroundImage = ""; alert(err); }
        },
        it = new ImageTrans(container, options);
    it.load(src);
    $$("idVertical"+idContainer).onclick = function () { it.vertical(); };
    $$("idHorizontal"+idContainer).onclick = function () { it.horizontal(); };
    $$("idLeft"+idContainer).onclick = function () { it.left(); };
    $$("idRight"+idContainer).onclick = function () { it.right(); };
}

/*
 * 以一个弹出窗口的形式，在弹出窗口里对图片的效果进行控制
 * 只需传入图片的地址
 */
function doShowImgEffect(src){
	if(src == null || src == ""){
	    $.messager.alert("错误", "图片不存在", "error");
	}

	$("#showImgEffect").dialog({
		closed:false,
        closable: false,
		buttons:[
		         {
		        	 iconCls: 'icons-close',
		        	 text:'取消',
		        	 handler:function(){
		        		 $("#idContainer > img").remove();
		        	 	$("#showImgEffect").dialog('close');
		        	 }
		         }]
	});
	try{
	    var container = $$("idContainer"), //src = "images/zoomFullScreen.jpg",
	        options = {
	            mode: "css3",
	            onPreLoad: function () { container.style.backgroundImage = "url('${res }/imgeffect/images/loading.gif')"; },
	            onLoad: function () { container.style.backgroundImage = ""; },
	            onError: function (err) { container.style.backgroundImage = ""; alert(err); }
	        },
	        it = new ImageTrans(container, options);
	    it.load(src);
	    $$("idVertical").onclick = function () { it.vertical(); };
	    $$("idHorizontal").onclick = function () { it.horizontal(); };
	    $$("idLeft").onclick = function () { it.left(); };
	    $$("idRight").onclick = function () { it.right(); };

	}catch(e){
		$("#idContainer > img").remove();
		$.messager.alert("错误", "图片不存在", "error");
	}
}
//-->
</script>
<style type="text/css">
    .idContainer {
        border: 1px solid #000;
        background:#e8e8e8;
        margin: 0 auto;
        width: 100%;
        height: 100%;
    }
    #btncontainer {
        width: auto;
        height: auto;
        position:absolute;
        z-index:99999;
    }
    #btncontainer img {
        cursor:pointer;
        border:1px dashed #ffffff;
    }
</style>
<div id="showImgEffect" class="easyui-dialog"
	style="width: 730px; height: 600px; overflow: hidden;"
	data-options="title:'图片预览',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
    <div id="idContainer" class="idContainer">
        <div id="btncontainer">
            <img id="idLeft" src="${res }/imgeffect/images/left.png" title="左旋转" />
            <img id="idRight" src="${res }/imgeffect/images/right.png"  title="右旋转" />
            <img id="idVertical" src="${res }/imgeffect/images/chuizhi.png"  title="垂直旋转" />
            <img id="idHorizontal" src="${res }/imgeffect/images/shuiping.png"  title="水平旋转" />
        </div>
    </div>
</div>
