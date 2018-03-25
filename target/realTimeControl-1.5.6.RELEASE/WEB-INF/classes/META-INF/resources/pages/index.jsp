<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page import="com.main.domain.User" %>
<!DOCTYPE HTML>
<html xmlns:th="http://www.thymeleaf.org">
<head>

	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
    <link rel="stylesheet" href="../static/css/bootstrap.min.css" />
    <link rel="stylesheet" href="../static/css/font-awesome.min.css"/>
    <link rel="stylesheet" href="../static/css/jquery-ui-1.10.3.full.min.css" />
    <link rel="stylesheet" href="../static/css/datepicker.css"/>
    <link rel="stylesheet" href="../static/css/googleFamily.css"/>
    <link rel="stylesheet" href="../static/css/ui.jqgrid.css" />
    <link rel="stylesheet" href="../static/css/chosen.css" />
    <link rel="stylesheet" href="../static/css/custom.css" />
    <link rel="stylesheet" href="../static/css/ace.min.css" />
    <link rel="stylesheet" href="../static/css/ace-rtl.min.css" />
    <link rel="stylesheet" href="../static/css/ace-skins.min.css" />

    <script src="../static/js/jquery-2.0.3.min.js" ></script>
    
    <style>
        .logo{
            width: 30px;
            height:30px;
            padding: 0px;
            margin-top: -4px;   
        }
    </style>
    
    <script type="text/javascript">

        $(function() {
        	// 
            $("#menus").on("click", ".changeUrl", function() {
            	closeTab("tab_process");
                var id = $(this).attr("id");
                var url = "/"+id;
                /*<![CDATA[*/
                if(id && url){
                    addTabs({
                        id:id,
                        title:$(this).text(),
                        url:url,
                        close:true
                    });
                }
                /*]]>*/
            }); 
            
            $(".nav-tabs").on("click", ".close-tab", function() {
                id = $(this).prev("a").attr("aria-controls");
                closeTab(id);
            });

            $("#mainIframe").load(function(){
                var mainheight =$(this).contents().find("body").height();
                /*<![CDATA[*/
                if(mainheight<400){
                    mainheight = mainheight+300
                }
                /*]]>*/
                $(this).height(mainheight);
            });
            
        });
        
        function exit(){
        	
            $.ajax({
                type : "POST",
                url : "/login/toLogout",
                success : function() {
                	window.location.reload();
                	window.location.href ="/pages/login.html";
                }
            });
        	        	
        }
        
    </script>
</head>

<body>
	
	<div class="form-group" style="display: none;">
	    <div class="col-sm-8">
	        <input id="clickedProcess" type="text" class="form-control" placeholder="" />
	    </div>
	</div>

	<%User user=(User)session.getAttribute("user"); %>

	<div class="main-container" id="main-container">
	
	    <div class="main-container-inner">
	        <a class="menu-toggler" id="menu-toggler" href="#"><span class="menu-text"></span></a>
	        <div class="sidebar" id="sidebar">
	        
	            <ul id="menus" class="nav nav-list">
	            
	                <li>
	                    <a href="#" class="dropdown-toggle">
	                        <i class="icon-th-list"></i>
	                        <span class="menu-text">表单管理</span>
	                        <b class="arrow icon-angle-down"></b>
	                    </a>
	                    <ul class="submenu">
	                    
	                    	<%if(user.getReceive().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="contract" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		工单索引表管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(true){ %>
	                        <li>
	                            <a href="#" id="record" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 生产记录表管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getAdmin().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="salary" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 员工工资查询
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getAdmin().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="urgency" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		急迫度管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(true){ %>
	                        <li>
	                            <a href="#" id="process" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 单项工序管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getAdmin().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="station" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 工序管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getPrice().equals("允许")){ %>
	                       	<li>
	                            <a href="#" id="price" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 工价管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getDispatch().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="dispatch" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 派工单管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                        <%if(user.getAdmin().equals("允许")){ %>
	                        <li>
	                            <a href="#" id="user" class="changeUrl">
	                                <i class="icon-double-angle-right"></i>
	                               		 用户管理
	                            </a>
	                        </li>
	                        <%} %>
	                        
	                    </ul>
	                </li>
	                
	               	<li onclick="exit()">
	                    <a href="/login" class="dropdown-toggle">
	                        <i class="icon-th-list"></i>
	                        <span class="menu-text">退出系统</span>
	                    </a>
	                <li>
	                
	            </ul>
	            
	            <div class="sidebar-collapse" id="sidebar-collapse">
	                <i class="icon-double-angle-left"
	                   data-icon1="icon-double-angle-left"
	                   data-icon2="icon-double-angle-right"></i>
	            </div>
	            
	        </div>
	        
	        <div class="main-content">
	            <ul class="nav nav-tabs" role="tablist" style="height:34px">
	                <li role="presentation" class="active" style="margin-right: 4px;">
	                    <a href="#home" aria-controls="home" role="tab" data-toggle="tab"><i class="icon-eye-open"></i>实时监控</a>
	                </li>
	            </ul>
	            <div class="tab-content page-content" style="height:100%;margin-right: 5px;">
	                <div role="tabpanel" class="tab-pane active" id="home">
	                    <iframe id="mainIframe" src="/pages/home.html" width="100%" height="100%" style="border: 0;height: 200px;"></iframe>
	                </div>
	            </div>
	            
	        </div>
	
	    </div>
	
	</div>
	
	<script src="../static/js/ace-extra.min.js"></script>
	<script src="../static/js/bootstrap.min.js"></script>
	<script src="../static/js/jquery-ui-1.10.3.custom.min.js"></script>
	<script src="../static/js/jquery.slimscroll.min.js" ></script>
	<script src="../static/js/chosen.jquery.min.js"></script>
	<script src="../static/js/ace-elements.min.js"></script>
	<script src="../static/js/ace.min.js"></script>
	<script src="../static/js/md5.js"></script>
	<script src="../static/js/custom.js"></script>
	<script src="../static/js/jquery.messager.js"></script>
	
</body>

</html>