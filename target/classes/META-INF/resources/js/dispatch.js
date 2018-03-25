/**
 * Modified by dzjin on 2018-1-30 10:32:40.
 */
//全局变量

var grid_selector = "#jqGrid";
var pager_selector = "#jqGridPager";
var rowNum = 100; 			//每页显示记录数，默认的数值
var task = null;			//任务（新增或编辑）
var loading;

$(function(){
	
    $(window).resize(function(){
        $(grid_selector).setGridWidth($(window).width()*0.95);
    });
    
    //通过AJAX方式实现对请求和响应的处理
    $(grid_selector).jqGrid({
    	
        url:"dispatch/queryDispatchList",
        datatype: "json",
        mtype: 'POST',
        height:window.screen.height-400,
        
        colModel: [
            { label: 'id', name: 'id', width: 75,hidden:true},
            { label: '工位', name: 'station', width: 60 },
            { label: '员工姓名', name: 'worker', width: 60 },
            { label: '工价', name: 'price', width: 60 },
            { label: '操作人员', name: 'superviser', width: 60 }
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
   
    
    //查询点击事件
    $("#queryBtn").click(function(){
        var contract=$("#contract").val();
        $(grid_selector).jqGrid('setGridParam',{
            postData:{
            	contract:contract},
            page:1
        }).trigger("reloadGrid");
    });
    
    //新增教程，弹出新增窗口
    $("#addLearnBtn").click(function () {
    	var contractStr = $("#contract").val();
    	var contract=contractStr.trim();
    	initData();
        //需要请求工价报价，工人列表，派工人员列表，动态填充select工位，工人，派工人员
    	if(contract != null && contract !=""){
            $.ajax({
            	url:"price/queryNotNullPriceList",
            	cache:false,
            	type:"post",
            	data:{contract:contract},
            	success:function(result){
            		
            		document.getElementById("station").innerHTML="";
            		var size=0;
            		for(var res in result){
            			document.getElementById("station").options.add(new Option(res,res));
            			size++;
            		};
            	}
            });
            //填充工作人员
            $.ajax({
            	url:"user/queryUserNameList",
            	cache:false,
            	type:"post",
            	success:function(result){
            		
            		document.getElementById("worker").innerHTML="";
            		var size=0;
            		for(var res in result){
            			document.getElementById("worker").options.add(new Option(res,res)); 
            			size++;
            		};
            	}
            });
            //填充派工员
            $.ajax({
            	url:"user/queryDispatchUserNameList",
            	cache:false,
            	type:"post",
            	success:function(result){
            		
            		document.getElementById("superviser").innerHTML="";
            		var size=0;
            		for(var res in result){
            			document.getElementById("superviser").options.add(new Option(res,res)); 
            			size++;
            		};

            	}
            });
            

            
            task = "saveDispatch";
            initData();
            $('#myModalLabel').text('新增派工');
            $("#addModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });
            
    	}else{
    		layer.msg('请输入合同号！', {icon: 7,time: 1000});
    	}



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
            task = "updateDispatch";
        	var contractStr = $("#contract").val();
        	var contract=contractStr.trim();
            initData();
            //查询工位情况
            $.ajax({
            	url:"price/queryNotNullPriceList",
            	cache:false,
            	type:"post",
            	data:{contract:contract},
            	success:function(result){
            		document.getElementById("station").options.length=0;
            		for(var res in result){
            			document.getElementById("station").options.add(new Option(res,res)); 
            		};
                    var station=data.station;
                	for(var i=0;i<document.getElementById("station").options.length;i++){
        	        	if(station==document.getElementById("station").options[i].value){
        	        		document.getElementById("station").options[i].selected = true;
        		        	break;
        	        	}
                	}
            	}
            });
            //查询工人姓名列表
            $.ajax({
            	url:"user/queryUserNameList",
            	cache:false,
            	type:"post",
            	data:{contract:contract},
            	success:function(result){
            		document.getElementById("worker").options.length=0;
            		for(var res in result){
            			document.getElementById("worker").options.add(new Option(res,res)); 
            		};
                    var worker=data.worker;
                	for(var i=0;i<document.getElementById("worker").options.length;i++){
        	        	if(worker==document.getElementById("worker").options[i].value){
        	        		document.getElementById("worker").options[i].selected = true;
        		        	break;
        	        	}
                	}
            	}
            });
            //查询派工人员姓名列表
            $.ajax({
            	url:"user/queryDispatchUserNameList",
            	cache:false,
            	type:"post",
            	success:function(result){
            		document.getElementById("superviser").options.length=0;
            		for(var res in result){
            			document.getElementById("superviser").options.add(new Option(res,res)); 
            		};
                    var superviser=data.superviser;
                	for(var i=0;i<document.getElementById("superviser").options.length;i++){
        	        	if(superviser==document.getElementById("superviser").options[i].value){
        	        		document.getElementById("superviser").options[i].selected = true;
        		        	break;
        	        	}
                	}
            	}
            });
            
            
            $("#id").val(data.id);                    
            $("#price").val(data.price);
            $('#myModalLabel').text('修改派工单');
            
            $("#addModal").modal({
                keyboard : false,
                show : true,
                backdrop : "static"
            });	
        }
    });

    //删除教程方法 选择多个的话，行id用逗号隔开比如 3,4
    $("#deleteLearnBtn").click(function () {
        var rows=$(grid_selector).getGridParam('selarrrow');
        if(rows.length>0){
            $.messager.confirm("温馨提示", "是否确定删除所选记录？", function() {
                $.ajax({
                    url:"dispatch/deleteDispatchs",
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
	document.getElementById("station").innerHTML="";
	$("#price").val("");	 
	document.getElementById("worker").innerHTML="";
	document.getElementById("superviser").innerHTML="";
}

/**
 * 保存教程
 */
function saveLearn(){
	
	var id = $('#id').val();
	//合同号
    var contract = $('#contract').val();
    var station = $('#station').val();
    var worker = $('#worker').val();
    var price = $('#price').val();
	var superviser = $('#superviser').val();
	
    $.ajax({
        url: "dispatch/"+task,
        cache: false,
        dataType:'json',
        data : {
        	"id":id,
            "contract":contract,
            "station":station,
            "worker":worker,
            "price":price,
            "superviser":superviser
        },
        type : 'post',
        beforeSend: function () {
            // 禁用按钮防止重复提交
            $('#saveLearnBtn').attr({ disabled: "disabled"});
        },
        success: function(result){
            if(result.flag == true){
                $.messager.alert('温馨提示',result.message);
                //$("#addModal").modal('hide');
                refreshData();
                $('#price').val("")
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