<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cl" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../commons/messageModals.jsp" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>
<!--
BeyondAdmin - Responsive Admin Dashboard Template build with Twitter Bootstrap 3.2.0
Version: 1.0.0
Purchase: http://wrapbootstrap.com
-->
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
    <meta charset="utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <%--<title>闪优客</title>--%>

    <meta name="description" content="Dashboard"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!--Basic Styles-->
    <link href="static/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link id="bootstrap-rtl-link" href="" rel="stylesheet"/>
    <link href="static/assets/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="static/assets/css/weather-icons.min.css" rel="stylesheet"/>

    <!--Fonts-->
    <%--<link--%>
    <%--href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300"--%>
    <%--rel="stylesheet" type="text/css">--%>

    <!--Beyond styles-->
    <link href="static/assets/css/beyond.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="static/assets/css/demo.min.css" rel="stylesheet"/>
    <link href="static/assets/css/typicons.min.css" rel="stylesheet"/>
    <link href="static/assets/css/animate.min.css" rel="stylesheet"/>
    <link id="skin-link" href="" rel="stylesheet" type="text/css"/>

    <!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
    <script src="static/assets/js/skins.min.js"></script>
    <script src="//cdn.bootcss.com/Chart.js/2.1.6/Chart.bundle.js"></script>
    <script src="${ctx }/static/assets/js/charts/chartjs/Chart.js"></script>

    <!--Google Analytics::Demo Only-->
    <!--Basic Scripts-->
    <script type="text/javascript">
        //        document.cookie="pageTitle=00";
        //        var x = document.cookie;
        //        console.info("cookie存是00")
        //        console.info(x)
        localStorage.setItem("pageTitle", "00");
        var dt = localStorage.getItem("pageTitle");
        console.info("localStorage存是00")
        console.info(dt)

        var pageTitle = "首页";

        var deviceListiClass;
        var deviceListtitle;
        var deviceListurl;


        function pageTo(iClass, title, url) {
            localStorage.setItem("pageTitle", "00");
            pageTitle = title;

            if (title == "状态信息") {
                deviceListiClass = iClass;
                deviceListtitle = title;
                deviceListurl = url;
            }

            if (url != null && url != '') {
                $("#imgI").attr("class", iClass);
                $("#titleH1").html(title);
                $("#mask").show();
                $("#iframepage").attr("src", url);
            }
            else {
                warningAlert("功能完善中...");
            }
        }

        //解决当窗口改变大小的时候执行js事件，以让iframe自适就高度
        function changeFrameHeight() {
            $("#mask").hide();
            var ifm = document.getElementById("iframepage");
            ifm.height = document.documentElement.clientHeight;
            document.getElementById('iframepage').height = "100%";
            var pageHeaderDiv = $("#pageHeaderDiv").css("width").replace("px", "");
            if (pageHeaderDiv < 800) {
                pageHeaderDiv = 800;
                ifm.height = 800;
            }
            /* console.info("pageHeaderDiv是："+pageHeaderDiv)*/
            ifm.width = parseInt(pageHeaderDiv);
        }
        //window.onresize的作用就是当窗口大小改变的时候会触发这个事件。
        window.onresize = function () {
            changeFrameHeight();
        }

    </script>

    <style>
        body {
            min-width: 800px;
            min-height: 800px;
        }
    </style>

</head>
<!-- /Head -->
<!-- Body -->
<body>

<!-- Loading Container -->
<div class="loading-container">
    <div class="loading-progress">
        <div class="rotator">
            <div class="rotator">
                <div class="rotator colored">
                    <div class="rotator">
                        <div class="rotator colored">
                            <div class="rotator colored"></div>
                            <div class="rotator"></div>
                        </div>
                        <div class="rotator colored"></div>
                    </div>
                    <div class="rotator"></div>
                </div>
                <div class="rotator"></div>
            </div>
            <div class="rotator"></div>
        </div>
        <div class="rotator"></div>
    </div>
</div>
<!--  /Loading Container -->
<!-- Navbar -->
<div class="navbar">
    <div class="navbar-inner" style="background: #11c7f9">
        <div class="navbar-container">
            <!-- Navbar Barnd -->
            <div class="navbar-header pull-left">

            </div>
            <!-- /Navbar Barnd -->

            <!-- Sidebar Collapse -->
            <div class="sidebar-collapse" id="sidebar-collapse">
                <i class="collapse-icon fa fa-bars"></i>
            </div>


            <!-- /Sidebar Collapse -->
            <!-- Account Area and Settings --->
            <div class="navbar-header pull-right">

                <div class="navbar-account">
                    <ul class="account-area">
                        <li>
                            <a class="login-area dropdown-toggle" data-toggle="dropdown" style="background: #11c7f9">
                                <div class="avatar" title="点击展开图片">
                                    <img src="static/assets/img/avatars/adam-jansen.jpg">
                                </div>
                                <section>
                                    <h2><span class="profile"><span>您好，${USER.userName }</span></span></h2>
                                </section>
                            </a>
                            <!--Login Area Dropdown-->
                            <ul class="pull-right dropdown-menu dropdown-arrow dropdown-login-area">
                                <li class="username"><a>${USER.userName }</a></li>
                                <!--Avatar Area-->
                                <li>
                                    <div class="avatar-area">
                                        <img src="static/assets/img/avatars/adam-jansen.jpg" class="avatar">
                                        <span class="caption" style="background: #11c7f9">${USER.userName }</span>
                                    </div>
                                </li>
                                <li>
                                    <a href="${ctx }/welcome.do">退出</a>
                                </li>
                            </ul>
                        </li>
                    </ul>
                </div>
            </div>
            <!-- /Account Area and Settings -->
        </div>
    </div>
</div>
<!-- /Navbar -->
<!-- Main Container -->
<div class="main-container container-fluid" style="height: 86%">
    <!-- Page Container -->
    <div class="page-container">
        <!-- Page Sidebar -->
        <div class="page-sidebar" id="sidebar">
            <!-- Page Sidebar Header-->
            <div class="sidebar-header-wrapper">

            </div>
            <ul class="nav sidebar-menu">
                <li>
                    <a href=""> <i class="menu-icon glyphicon glyphicon-home"></i> <span class="menu-text"> 首页 </span>
                    </a>
                </li>
                <c:forEach items="${menus}" var="menu">
                <li>
                    <a href="javascript:;" class="menu-dropdown"> <i class="menu-icon ${menu.icon}"></i> <span
                            class="menu-text">${menu.name }</span> <i class="menu-expand"></i> </a>
                    <ul class="submenu">
                        <c:forEach items="${menu.list}" var="subMenu">
                            <li>
                                <a href="javascript:pageTo('menu-icon ${menu.icon}','${subMenu.name }','${subMenu.url }')">
                                    <span class="menu-text">${subMenu.name }</span> </a>
                            </li>
                        </c:forEach>
                    </ul>
                </li>
                </c:forEach>
                <%--<c:if test="${USER.role eq 'user'}">--%>
                <%--<li><a href="javascript:pageTo('fa fa-file-text-o','使用手册','${ctx}/static/jedate/userBook.pdf')"> <i class="menu-icon fa fa-file-text-o"></i> <span class="menu-text">使用手册</span></a></li>--%>
                <%--</c:if>--%>
                <%--<c:if test="${USER.role eq 'manage'}">--%>
                <%--<li><a href="javascript:pageTo('fa fa-file-text-o','使用手册','${ctx}/static/jedate/manageBook.pdf')"> <i class="menu-icon fa fa-file-text-o"></i> <span class="menu-text">使用手册</span></a></li>--%>
                <%--</c:if>--%>
                <li>
                    <a href="javascript:;" id="changePassword"> <i class="menu-icon fa fa-lock"></i> <span
                            class="menu-text">修改密码</span></a>
                </li>
                <!-- /Sidebar Menu -->
        </div>

        <!-- /Page Sidebar -->
        <!-- Page Content -->
        <div class="page-content">
            <!-- Page Breadcrumb -->
            <%--
            <div class="page-breadcrumbs">
                <ul class="breadcrumb">
                    <li>
                        <i class="fa fa-home"></i>
                        <a href="#">Home</a>
                    </li>
                    <li class="active">Dashboard</li>
                </ul>
            </div>
            --%>
            <!-- /Page Breadcrumb -->
            <!-- Page Header -->
            <div id="pageHeaderDiv" class="page-header position-relative">
                <div class="header-title">
                    <i id="imgI" class="menu-icon glyphicon glyphicon-home"></i>
                    <h1 id="titleH1">首页</h1>
                </div>

                <!--Header Buttons-->
                <div class="header-buttons">

                    <div class="navbar-header pull-left">
                        <%--<input type="text" id="text">--%>
                        <%--<button id="btn1" value="发送给后台">发送给后台</button>--%>
                        <div id="msg"></div>
                    </div>

                    <a class="sidebar-toggler" href="#">
                        <i class="fa fa-arrows-h"></i>
                    </a>
                    <a class="refresh" id="refresh-toggler" href="">
                        <i class="glyphicon glyphicon-refresh"></i>
                    </a>
                    <a class="fullscreen" id="fullscreen-toggler" href="#">
                        <i class="glyphicon glyphicon-fullscreen"></i>
                    </a>
                    <a href="javascript:history.go(-1)">
                        <i class="fa  fa-reply"></i>
                    </a>
                </div>
                <!--Header Buttons End-->
            </div>
            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body" style="padding-left: 10px;padding-top: 10px;">
                <div class="row">
                    <div class="col-lg-12 col-sm-12 col-xs-12">

                        <iframe src="${ctx}/deviceMgt/list.do?" id="iframepage" scrolling="assible"
                                onload="changeFrameHeight()" frameborder="0">
                        </iframe>
                    </div>
                </div>
            </div>
            <!-- /Page Body -->
        </div>
        <!-- /Page Content -->
    </div>
    <!-- /Page Container -->
    <!-- Main Container -->
</div>

<%--修改密码--%>
<div id="changePasswordModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">修改密码</h4>
            </div>
            <div class="modal-body">
                <div id="horizontal-form">
                    <form class="form-horizontal" role="form">
                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">旧密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="oldPassword" placeholder="请输入旧密码">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">新密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="newPassword"
                                       placeholder="请输入新密码,密码长度6到16位数字字母且不含特殊符号">
                            </div>
                        </div>
                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">确认新密码</label>
                            <div class="col-sm-10">
                                <input type="password" class="form-control" id="newPasswordAgain"
                                       placeholder="请再一次输入新密码">
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save">保存</button>
                <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div>


<!--Beyond Scripts-->
<script src="static/assets/js/beyond.min.js"></script>


<!--Page Related Scripts-->
<!--Sparkline Charts Needed Scripts-->
<script src="static/assets/js/charts/sparkline/jquery.sparkline.js"></script>
<script src="static/assets/js/charts/sparkline/sparkline-init.js"></script>

<!--Easy Pie Charts Needed Scripts-->
<script src="static/assets/js/charts/easypiechart/jquery.easypiechart.js"></script>
<script src="static/assets/js/charts/easypiechart/easypiechart-init.js"></script>

<!--Flot Charts Needed Scripts-->
<script src="static/assets/js/charts/flot/jquery.flot.js"></script>
<script src="static/assets/js/charts/flot/jquery.flot.resize.js"></script>
<script src="static/assets/js/charts/flot/jquery.flot.pie.js"></script>
<script src="static/assets/js/charts/flot/jquery.flot.tooltip.js"></script>
<script src="static/assets/js/charts/flot/jquery.flot.orderBars.js"></script>

<script>
    $(document).ready(function () {
        var ws;
        if ('WebSocket' in window) {
            ws = new WebSocket("ws://" + window.location.host + "/back/webSocketServer.do");
        } else if ('MozWebSocket' in window) {
            ws = new MozWebSocket("ws://" + window.location.host + "/back/webSocketServer.do");
        } else {
            //如果是低版本的浏览器，则用SockJS这个对象，对应了后台“sockjs/webSocketServer”这个注册器，
            //它就是用来兼容低版本浏览器的
            ws = new SockJS("http://" + window.location.host + "/back/sockjs/webSocketServer.do");
        }
        ws.onopen = function (evnt) {
            <%--ws.send(${USER.id});--%>
            ws.send("{\"type\":\"user\",\"id\":\"${USER.userName}\"}");
        };
        //接收到消息
        ws.onmessage = function (evnt) {
//            alert(evnt.data);

            var obj = JSON.parse(evnt.data);
            console.log("收到")
            console.log(obj.event)
            console.log(obj.url)
            if (obj.type == 1) {
                warningAlert1(obj.event, obj.url);
            }
            if (obj.type == 2) {
                var x = localStorage.getItem("pageTitle");
                console.info("判断cookie是")
                console.info(x)
                if (x.indexOf("device_show") < 0 && obj.event.state != 0) {
                    if (pageTitle == "状态信息") {
                        pageTo('menu-icon ' + deviceListiClass, deviceListtitle, deviceListurl)
                    }
                    if (pageTitle == "首页") {
                        window.location.reload();
                    }
                } else {
                    localStorage.setItem("deviceList", 1);
                }
                warningAlert2(obj.event, obj.url);
            }

            $("#msg").html(obj.s);
        };
        ws.onerror = function (evnt) {
            console.log(evnt)
        };
        ws.onclose = function (evnt) {
        }

        $("#btn1").click(function () {

            ws.send($("#text").val());
        });
    });

</script>

<script>
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
                    (i[r].q = i[r].q || []).push(arguments)
                }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', 'http://www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-52103994-1', 'auto');
    ga('send', 'pageview');

</script>
<script>
    $("#changePassword").click(function () {
        $("#oldPassword").val("");
        $("#newPassword").val("");
        $("#newPasswordAgain").val("");
        $("#changePasswordModal").modal('show');
    });
    $("#save").click(function () {
        if ($("#oldPassword").val() == "") {
            Notify('旧密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#newPassword").val() == "") {
            Notify('新密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#newPassword").val().search(/^[\w_-]{6,16}$/) == -1) {
            Notify('请输入正确的密码格式', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#newPasswordAgain").val() == "") {
            Notify('确认新密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#newPasswordAgain").val() != $("#newPassword").val()) {
            Notify('两次输入的密码不一致', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        $.ajax({
            url: "${ctx}/userMgt/updatePwd.do",
            method: "post",
            data: {
                id:${USER.id},
                pwd: $("#oldPassword").val(),
                newPwd: $("#newPassword").val(),
                newPwd2: $("#newPasswordAgain").val()
            },
            success: function (data) {
                if (data.status == 1) {
                    Notify('修改成功，下次登录请使用新密码', 'top-right', '5000', 'success', 'fa-check', true);
                    $("#changePasswordModal").modal('hide');
                }
                else {
                    Notify(data.message[0].msg, 'top-right', '5000', 'success', 'fa-check', true)
                }
            }
        });
    });

</script>

</body>
<!--  /Body -->
</html>
