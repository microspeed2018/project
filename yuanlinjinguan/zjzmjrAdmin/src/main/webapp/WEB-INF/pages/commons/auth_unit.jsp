<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<script type="text/javascript">
    function showBindAuth(action, kId, dpId){
        $("#auths").children().remove();
        $.post("${ctx}/console/auth.htm",
                {action:action, id:kId},
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
                                var userId = $("#datagrid").datagrid("getSelected").id;

                                $.messager.progress({interval:200,text:'处理中...'});
                                $.post("${ctx}/console/auth.htm",
                                        {action:"bindJobAuth", jobId:kId,auths:auths,departmentId:dpId},
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
    
</script>

<style type="text/css">
    #auths{
      display:block;
      width:100%;
      height:100%;
      overflow: auto;
    }
    #auths td {
        padding: 5px;
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
       width:150px;
       text-align: center;
       vertical-align: center;
       font-weight: bold;
       border-right:1px solid gray;
    }
    .auth_item{
        display:block;
        float:left;
        padding-right: 20px; 
        width: 165px;
        height: 25px;
        line-height: 25px;
    }
</style>
    <div id="auth_bind" class="easyui-dialog" style="width:800px;height:560px;" data-options="title:'权限绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
       <div id="auths">
       </div>
    </div>
    <div id="menu_bind" class="easyui-dialog" style="width:370px;height:448px;padding:10px 10px 10px 10px" data-options="title:'菜单绑定',resizable:false,closed:true,minimizable:false,maximizable:false,resizable:false,modal:true">
       <div id="menu_tree">
       </div>
    </div>