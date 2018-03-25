/**
 * Modified by dzjin on 2018-1-30 10:32:40.
 */
//全局变量

var grid_selector = "#jqGrid";
var pager_selector = "#jqGridPager";
var rowNum = 100;
var task = null;
var loading;

$(function(){
	
    $(window).resize(function(){
        $(grid_selector).setGridWidth($(window).width()*0.95);
    });
    
    //通过AJAX方式实现对请求和响应的处理
    $(grid_selector).jqGrid({
    	
        url:"user/queryUserList",
        datatype: "json",
        mtype: 'POST',
        height:window.screen.height-400,
        
        colModel: [
            { label: 'id', name: 'id', width: 75,hidden:true},
            { label: '姓名', name: 'user', width: 60 },
            { label: '密码', name: 'password', width: 60 ,hidden:true},
            { label: '手机号', name: 'phone', width: 60 },
            { label: '接单', name: 'receive', width: 60 },
            { label: '拆单', name: 'open', width: 60 } ,
            { label: '工价', name: 'price', width: 60 },
            { label: '派单', name: 'dispatch', width: 60 } ,
            { label: '一线工人', name: 'worker', width: 60 },
            { label: '管理员', name: 'admin', width: 60 } 
        ],

        pager: pager_selector,
        rowNum:rowNum,
        rowList:[100,200,300],
        viewrecords: true,
        altRows: true,
        gridview: true,
        multiselect: true,
        multiselectWidth: 40,
        multiboxonly: true,
        shrinkToFit:true,
        forceFit:true,
        autowidth: true,
        loadComplete : function() {
            var table = this;
            setTimeout(function(){
                updatePagerIcons(table);
            }, 0);
        },
        gridComplete: function () {
            removeHorizontalScrollBar();
        },
        jsonReader: {
            root: "rows",
            total: "total",
            records:"records",
            repeatitems : false
        },
        emptyrecords: '没有记录!',
        loadtext: '正在查询服务器数据...'
        	
    });

    //设置分页按钮组
    $(grid_selector).jqGrid('navGrid',pager_selector,
        {
            edit: false,
            add: false,
            del: false,
            refresh: true,
            refreshicon : 'icon-refresh green',
            beforeRefresh:refreshData,
            search: false,
            view: true,
            alertcap:"警告",
            alerttext : "请选择需要操作的用户!"
        }
    );
    
    //新增教程，弹出新增窗口
    $("#addLearnBtn").click(function () {
        task = "addUser";
        initData();
        $('#myModalLabel').text('新增用户');
        $("#addModal").modal({
            keyboard : false,
            show : true,
            backdrop : "static"
        });

    });

    //编辑对话框取消点击事件
    $('#cancelSave').click(function(){
        $("#addModal").modal('hide');
    });

    //保存教程
    $('#saveLearnBtn').click(function(){
        saveLearn();
    });

    //修改教程，弹出修改窗
    $("#modifyLearnBtn").click(function () {
        var rows=$(grid_selector).getGridParam('selarrrow');
        if(rows==0){
            layer.msg('请选择一行记录！', {icon: 7,time: 2000});
            return;
        }else if(rows.length>1){
            layer.msg('不能同时修改多条记录！', {icon: 7,time: 2000});
            return;
        }else{
            var data = $(grid_selector).jqGrid('getRowData', rows[0]);
            task = "updateUser";
            initData();
            
            $("#id").val(data.id);
            $("#user").val(data.user);
            $("#password").val(data.password);            
            $("#phone").val(data.phone);
            
            if(data.receive=='允许'){
            	$('#receive').prop("checked", true);
            }else{
            	$('#receive').prop("checked", false);
            }
            
            if(data.open=='允许'){
                $('#open').prop("checked", true);
            }else{
                $('#open').prop("checked", false);
            }
            
            if(data.price=='允许'){
                $('#price').prop("checked", true);
            }else{
                $('#price').prop("checked", false);
            }
            
            if(data.dispatch=='允许'){
                $('#dispatch').prop("checked", true);
            }else{
                $('#dispatch').prop("checked", false);
            }
            
            if(data.worker=='允许'){
                $('#worker').prop("checked", true);
            }else{
                $('#worker').prop("checked", false);
            }
            
            if(data.admin=='允许'){
                $('#admin').prop("checked", true);
            }else{
                $('#admin').prop("checked", false);
            }
            
            $('#myModalLabel').text('修改用户信息');
            $("#addModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        }
    });

    $("#deleteLearnBtn").click(function () {
        var rows=$(grid_selector).getGridParam('selarrrow');
        if(rows.length>0){
            $.messager.confirm("温馨提示", "是否确定删除所选记录？", function() {
                $.ajax({
                    url:"user/deleteUsers",
                    cache: false,
                    type:"post",
                    data:{
                    	"ids": rows.join(",")
                    },
                    beforeSend : function(){
                        loading=layer.load("正在删除中...");
                    },
                    success:function(result){
                        $.messager.alert(result.message);
                        refreshData();
                    },error:function(){
                        $.messager.alert("温馨提示","请求错误!");
                    },
                    complete : function(){
                        layer.close(loading);
                    }
                });
            });
        }else{
            layer.msg('至少选中一行记录！', {icon: 7,time: 2000});
        }
    })

});

function removeHorizontalScrollBar() {
    $("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width", parseInt($("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width")) + 1 + "px");
    $(grid_selector).closest(".ui-jqgrid-bdiv").css("width", parseInt($(grid_selector).closest(".ui-jqgrid-bdiv").css("width")) + 1 + "px");
}


//初始化数据
function initData(){
	
    $('#user').val("");
    $('#password').val("");
    $('#phone').val("");
    $('#number').val("");
    
    $('#receive').prop("checked", false);
    $('#open').prop("checked", false);
    $('#price').prop("checked", false);
    $('#dispatch').prop("checked", false);
    $('#worker').prop("checked", false);
    $('#admin').prop("checked", false);
    
}


/**
 * 保存教程
 */
function saveLearn(){
	
	var id=$('#id').val();
    var user=$('#user').val();
    var password=$('#password').val();
    var phone=$('#phone').val();
    
    var receive=$('#receive').is(':checked');
    var open=$('#open').is(':checked');
    var price=$('#price').is(':checked');
    var dispatch=$('#dispatch').is(':checked');
    var worker=$('#worker').is(':checked');
    var admin=$('#admin').is(':checked');
    
    if(receive==true){
    	receive="允许";
    }else{
    	receive="禁止";
    }
    
    if(open==true){
        open="允许";
    }else{
        open="禁止";
    }
    
    if(price==true){
        price="允许";
    }else{
        price="禁止";
    }
    
    if(dispatch==true){
    	dispatch="允许";
    }else{
    	dispatch="禁止";
    }
    
    if(worker==true){
        worker="允许";
    }else{
    	worker="禁止";
    }
    
    if(admin==true){
    	admin="允许";
    }else{
    	admin="禁止";
    }
	
    $.ajax({
        url: "user/"+task,
        cache: false,
        dataType:'json',
        data : {
        	"id":id,
            "user":user,
            "password":password,
            "phone":phone,
            "mac":"null",
            "receive":receive,
            "open":open,
            "price":price,
            "dispatch":dispatch,
            "worker":worker,
            "admin":admin
        },
        type : 'post',
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $('#saveLearnBtn').attr({ disabled: "disabled"});
        },
        success: function(result){
            if(result.flag == true){
                $.messager.alert('温馨提示',result.message);
                $("#addModal").modal('hide');
                refreshData();
            }else{
                $.messager.alert('温馨提示',result.message);
            }
        },
        complete: function () {
            $('#saveLearnBtn').removeAttr("disabled");
        },
        error: function (data) {
            console.info("error: " + data.responseText);
        }
    });
}


function refreshData(){
    $(grid_selector).jqGrid('setGridParam',{
        postData:{author:null,title:null},
        page:1
    }).trigger("reloadGrid");
}



//这个是分页图标，必须添加
function updatePagerIcons(table) {
    var replacement =
        {
            'ui-icon-seek-first' : 'icon-double-angle-left bigger-140',
            'ui-icon-seek-prev' : 'icon-angle-left bigger-140',
            'ui-icon-seek-next' : 'icon-angle-right bigger-140',
            'ui-icon-seek-end' : 'icon-double-angle-right bigger-140'
        };
    $('.ui-pg-table:not(.navtable) > tbody > tr > .ui-pg-button > .ui-icon').each(function(){
        var icon = $(this);
        var $class = $.trim(icon.attr('class').replace('ui-icon', ''));
        console.info($class);
        if($class in replacement) icon.attr('class', 'ui-icon '+replacement[$class]);
    });
}
