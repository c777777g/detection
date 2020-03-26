<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cl" %>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@include file="../commons/messageModals.jsp" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
    <meta charset="utf-8"/>
    <title>首页</title>

    <meta name="description" content="Dashboard"/>
    <meta name="viewport" content="width=device-width, initial-scale=1.0"/>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>

    <!--Basic Styles-->
    <link href="static/assets/css/bootstrap.min.css" rel="stylesheet"/>
    <link id="bootstrap-rtl-link" href="" rel="stylesheet"/>
    <link href="static/assets/css/font-awesome.min.css" rel="stylesheet"/>
    <link href="static/assets/css/weather-icons.min.css" rel="stylesheet"/>
    <!--Beyond styles-->
    <link href="static/assets/css/beyond.min.css" rel="stylesheet"
          type="text/css"/>
    <link href="static/assets/css/demo.min.css" rel="stylesheet"/>
    <link href="static/assets/css/typicons.min.css" rel="stylesheet"/>
    <link href="static/assets/css/animate.min.css" rel="stylesheet"/>
    <link id="skin-link" href="" rel="stylesheet" type="text/css"/>

    <!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
    <script src="static/assets/js/skins.min.js"></script>


    <!--Google Analytics::Demo Only-->
    <!--Basic Scripts-->
    <script type="text/javascript">
        function pageTo(iClass, title, url) {
            if (url != null && url != '') {
                $("#imgI").attr("class", iClass);
                $("#titleH1").html(title);
                $("#iframepage").attr("src", url);
            } else {
                warningAlert("功能完善中...");
            }
            localStorage.setItem("deviceShowDeviceId", title);
        }

        //解决当窗口改变大小的时候执行js事件，以让iframe自适就高度
        function changeFrameHeight() {
            var ifm = document.getElementById("iframepage");
            ifm.height = document.documentElement.clientHeight;
            var pageHeaderDiv = $("#pageHeaderDiv").css("width").replace("px", "");
            ifm.width = parseInt(pageHeaderDiv);
        }
        //window.onresize的作用就是当窗口大小改变的时候会触发这个事件。
        window.onresize = function () {
            changeFrameHeight();
        }
    </script>
</head>
<!-- /Head -->
<!-- Body -->
<body>
<!-- Main Container -->
<div class="main-container container-fluid">
    <!-- Page Container -->
    <div class="page-container">
        <!-- Page Sidebar -->
        <div class="page-sidebar" id="sidebar">
            <!-- Page Sidebar Header-->

            <ul class="nav sidebar-menu">
                <li>
                    <div class="page-header position-relative"></div>
                </li>

                <c:forEach items="${pagination.rows}" var="menu">
                    <li>

                        <a href="javascript:pageTo('menu-icon ','${menu.deviceId }','${ctx }/reportFromMgt/show.do?deviceId=${menu.deviceId} ')">
                            <span class="menu-text">${menu.deviceName}</span>
                            <c:if test="${menu.state=='1'}">
                                        <span class="menu-text" style="float: right;color: #00ee13">在线<i
                                                class="fa  fa-signal"></i></span>
                            </c:if>
                            <c:if test="${menu.state=='2'}">
                                        <span class="menu-text" style="float: right;color: #ee7678">离线<i
                                                class="fa fa-times"></i></span>
                            </c:if>

                        </a>
                    </li>
                </c:forEach>

                <li>
                    <a href="javascript:void(0);">
                        <span id="lastPageLi" class="menu-text" onclick="beforePage()" style="float: left;"> <i class="fa fa-chevron-left"></i>上一页</span>
                        <span class="menu-text" style="padding-left: 30px" > ${pagination.page}(${pagination.pages})</span>
                        <span id="nextPageLi" class="menu-text" onclick="nextPage()" style="float: right;">下一页<i class="fa  fa-chevron-right"></i></span>
                    </a>
                </li>
            </ul>
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


            </div>

            <!-- /Page Header -->
            <!-- Page Body -->
            <div class="page-body" style="padding-left: 10px;padding-top: 10px;">
                <div class="row">

                    <div class="col-lg-12 col-sm-12 col-xs-12">
                        <iframe src="" id="iframepage" scrolling="visible" onload="changeFrameHeight()"
                                frameborder="0"></iframe>


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
    $("#addTemplate").click(function () {
        $("#templateName").val("");
        $("#templateDescribe").val("");
        $("#addTemplateModal").modal('show');
    });
    $("#save").click(function () {
        if ($("#templateName").val() == "") {
            Notify('模块名不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#modelName").val() == "") {
            Notify('机型选择不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        $.ajax({
            url: "${ctx}/containerTemplateMgt/saveTemplate.do",
            method: "post",
            data: {
                id:${USER.id},
                templateName: $("#templateName").val(),
                templateDescribe: $("#templateDescribe").val(),
                modelName: $("#modelName").val()
            },
            success: function (data) {
                if (data.status == 1) {
                    Notify('修改成功', 'top-right', '5000', 'success', 'fa-check', true)
                    $("#addTemplateModal").modal('hide');
                    window.location.reload();
                }
                if (data.status != 1) {
                    Notify('模板已存在', 'top-right', '5000', 'failure', 'fa-check', true)

                }
            }
        });
    });

</script>

<script type="text/javascript">
    var currentPage;
    <c:if test="${pagination.page==0}">
    currentPage = '1'; //当前页
    </c:if>
    <c:if test="${pagination.page!=0}">
    currentPage = '${pagination.page}'; //当前页
    </c:if>
    var totalPage = '${pagination.pages }';	//总页数

    if(parseInt(currentPage) == parseInt(totalPage)){
        $("#nextPageLi").addClass("disabled");
        $("#lastPageLi").addClass("disabled");
    }


    //上一页
    function beforePage(){
        if(parseInt(currentPage) > 1){
            $("#page").val(parseInt(currentPage) - 1);

            console.log('${ctx }' + "/reportFromMgt/list.do?page="+parseInt(currentPage) - 1);

            window.location.href='${ctx }' + "/reportFromMgt/list.do?page="+(parseInt(currentPage) - 1)

        }
    }
    //下一页
    function nextPage(){
        if(parseInt(totalPage) > parseInt(currentPage)){
            $("#page").val(parseInt(currentPage) + 1);
            console.log('${ctx }' + "/reportFromMgt/list.do?page="+(parseInt(currentPage) + 1));
            window.location.href='${ctx }' + "/reportFromMgt/list.do?page="+(parseInt(currentPage) + 1)
        }
    }

    var t1 = window.setInterval(refreshCount, 2000);


    function refreshCount() {
      var i= localStorage.getItem("deviceList");
        if(i==1){
            localStorage.setItem("deviceList", 0);
            window.location.reload();
        }
    }
    $(document).ready(function () {
        var deviceId= localStorage.getItem("deviceShowDeviceId");
        if(deviceId !="null" && deviceId !="" && deviceId !=undefined && deviceId != null ){
            pageTo('menu-icon ',deviceId,'${ctx }/reportFromMgt/show.do?deviceId='+deviceId)
        }

    });
</script>
</body>
<!--  /Body -->
</html>
