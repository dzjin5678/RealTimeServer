/**
 * Modified by dzjin on 2018-1-30 10:32:40.
 */
//全局变量

var grid_selector = "#jqGrid";
var pager_selector = "#jqGridPager";
var rowNum = 20; 			//每页显示记录数，默认的数值
var task = null;			//任务（新增或编辑）
var loading;

$(function(){
	
    $(window).resize(function(){
        $(grid_selector).setGridWidth($(window).width()*0.95);
    });
    
    //通过AJAX方式实现对请求和响应的处理
    $(grid_selector).jqGrid({
    	
        url:"urgency/getUrgencyList",
        datatype: "json",
        mtype: 'POST',
        height:window.screen.height-400,
        
        colModel: [
            { label: 'id', name: 'id', width: 75,hidden:true},
            { label: '工位', name: 'station', width: 60 },
            { label: '正常（淡绿色）>=', name: 'a1', width: 60 },
            { label: '紧急（淡黄色）<', name: 'a2', width: 60 },
            { label: '加急（淡红色）<', name: 'a3', width: 60 },
            { label: '特急（淡紫色）<', name: 'a4', width: 60 }
        ],

        pager: pager_selector,
        rowNum:rowNum,
        rowList:[20,40,60,80,100],
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
            task = "updateUrgency";
            
            $("#id").val(data.id);
            $("#a1").val(data.a1);
            $("#a2").val(data.a2);            
            $("#a3").val(data.a3);
            $("#a4").val(data.a4);
            $("#station").val(data.station);
            
            $('#myModalLabel').text('修改急迫度');
            
            $("#addModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
        }
    });

});

function removeHorizontalScrollBar() {
    $("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width", parseInt($("div.ui-state-default.ui-jqgrid-hdiv.ui-corner-top").css("width")) + 1 + "px");
    $(grid_selector).closest(".ui-jqgrid-bdiv").css("width", parseInt($(grid_selector).closest(".ui-jqgrid-bdiv").css("width")) + 1 + "px");
}


/**
 * 保存教程
 */
function saveLearn(){
	
	var id = $('#id').val();
    var a1 = $('#a1').val();
    var a2 = $('#a2').val();
    var a3 = $('#a3').val();
    var a4 = $('#a4').val();
	
    $.ajax({
        url: "urgency/"+task,
        cache: false,
        dataType:'json',
        data : {
        	"id":id,
            "a1":a1,
            "a2":a2,
            "a3":a3,
            "a4":a4
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
