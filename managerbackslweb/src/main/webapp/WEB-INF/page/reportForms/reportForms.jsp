<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml">
<!-- Head -->
<head>
    <meta charset="utf-8" />
    <title>Flot Charts</title>
    <meta name="description" content="flot charts" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
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
    <!-- Main Container -->
    <div class="main-container container-fluid">

    <div class="row">
    <div class="col-lg-2 col-xs-2 col-md-2">
    <div class="form-group">
    <label for="reservation">日期选择</label>
    <div class="controls">
    <div class="input-group">
    <span class="input-group-addon">
    <i class="fa fa-calendar"></i>
    </span>
    <input type="text" class="form-control" id="reservation" readonly="readonly" placeholder="${DATE}">
    </div>
    </div>
    </div>
    </div>

    <div class="col-lg-1 col-xs-1 col-md-1">
    <div style="margin-top: 20px;">
    <div>
    <a href="#" class="btn btn-default" id="query">查询</a>
    </div>
    </div>
    </div>

    </div>

    <!-- Page Container -->
    <div class="page-container">

    <div class="row">
    <div class="col-xs-12 col-md-12 col-lg-12">
    <div class="widget">
    <div class="widget-header">
    <span class="widget-caption">温度/湿度</span>
    <div class="widget-buttons">
    <a href="#" data-toggle="maximize">
    <i class="fa fa-expand"></i>
    </a>
    <a href="#" data-toggle="collapse">
    <i class="fa fa-minus"></i>
    </a>
    <a href="#" data-toggle="dispose">
    <i class="fa fa-times"></i>
    </a>
    </div>
    </div>
    <div class="widget-body">

    <div class="row">
    <div class="col-sm-12">
    <div id="selectable-chart" class="chart chart-lg"></div>

    </div>
    </div>
    </div>
    </div>
    </div>
    </div>


    <div class="row">
    <div class="col-xs-12 col-md-12 col-lg-12">
    <div class="widget">
    <div class="widget-header">
    <span class="widget-caption">人体感应/烟感</span>
    <div class="widget-buttons">
    <a href="#" data-toggle="maximize">
    <i class="fa fa-expand"></i>
    </a>
    <a href="#" data-toggle="collapse">
    <i class="fa fa-minus"></i>
    </a>
    <a href="#" data-toggle="dispose">
    <i class="fa fa-times"></i>
    </a>
    </div>
    </div>
    <div class="widget-body">

    <div class="row">
    <div class="col-sm-12">
    <div id="selectable-chart2" class="chart chart-lg"></div>

    </div>
    </div>
    </div>
    </div>
    </div>
    </div>

    <div class="row">
    <div class="col-xs-12 col-md-12 col-lg-12">
    <div class="widget">
    <div class="widget-header">
    <span class="widget-caption">照明亮度/环境光度</span>
    <div class="widget-buttons">
    <a href="#" data-toggle="maximize">
    <i class="fa fa-expand"></i>
    </a>
    <a href="#" data-toggle="collapse">
    <i class="fa fa-minus"></i>
    </a>
    <a href="#" data-toggle="dispose">
    <i class="fa fa-times"></i>
    </a>
    </div>
    </div>
    <div class="widget-body">

    <div class="row">
    <div class="col-sm-12">
    <div id="selectable-chart3" class="chart chart-lg"></div>

    </div>
    </div>
    </div>
    </div>
    </div>
    </div>


    <div class="row">
    <div class="col-xs-12 col-md-12 col-lg-12">
    <div class="widget">
    <div class="widget-header">
    <span class="widget-caption">Pm2.5</span>
    <div class="widget-buttons">
    <a href="#" data-toggle="maximize">
    <i class="fa fa-expand"></i>
    </a>
    <a href="#" data-toggle="collapse">
    <i class="fa fa-minus"></i>
    </a>
    <a href="#" data-toggle="dispose">
    <i class="fa fa-times"></i>
    </a>
    </div>
    </div>
    <div class="widget-body">

    <div class="row">
    <div class="col-sm-12">
    <div id="selectable-chart4" class="chart chart-lg"></div>

    </div>
    </div>
    </div>
    </div>
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
    <script type="text/javascript">
        var temperature = eval('${temperature}');
        var humidity = eval('${humidity}');
        var pm25 = eval('${pm25}');
        var illuminating_brightness = eval('${illuminating_brightness}');
        var body_inductor = eval('${body_inductor}');
        var smoke_sensors = eval('${smoke_sensors}');
        var optical_inductor = eval('${optical_inductor}');
    </script>

    <!--Page Related Scripts-->
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.orderBars.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.tooltip.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.resize.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.selection.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.crosshair.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.stack.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.time.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/jquery.flot.pie.js"></script>
    <script src="${ctx }/static/assets/js/charts/flot/report-flot-init.js"></script>

    <script type="text/javascript">
        $(window).bind("load", function () {
            InitiateFlotSelectableChart.init();
            InitiateFlotSelectableChart2.init();
            InitiateFlotSelectableChart3.init();
            InitiateFlotSelectableChart4.init();
        });
    </script>

    <!--Google Analytics::Demo Only-->
    <script>
        (function (i, s, o, g, r, a, m) {
            i['GoogleAnalyticsObject'] = r; i[r] = i[r] || function () {
                (i[r].q = i[r].q || []).push(arguments)
            }, i[r].l = 1 * new Date(); a = s.createElement(o),
            m = s.getElementsByTagName(o)[0]; a.async = 1; a.src = g; m.parentNode.insertBefore(a, m)
        })(window, document, 'script', 'http://www.google-analytics.com/analytics.js', 'ga');

        ga('create', 'UA-52103994-1', 'auto');
        ga('send', 'pageview');

    </script>

<script src="${ctx }/static/assets/js/datetime/bootstrap-datepicker.js"></script>
<script src="${ctx }/static/assets/js/datetime/moment.js"></script>
<script src="${ctx }/static/assets/js/datetime/daterangepicker.js"></script>

<script type="text/javascript">
    var SYS = new Date();
    var now = "\""+(SYS.getMonth()+1) + '/' + SYS.getDate() + '/' + SYS.getFullYear()+"\"";
    $('#reservation').daterangepicker({
        singleDatePicker: true,
        showDropdowns: true,
        linkedCalendars: false,
        autoUpdateInput: false,
        showCustomRangeLabel: false,
        startDate: now,
        locale: {
        daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    }
    });

    $("#query").click(function () {
        $("#mask").show();
        var time = new Date($('#reservation').val());
        var year = time.getFullYear();
        var month = time.getMonth()+1;
        var day = time.getDate();
        var ymd = year + '-' + month + '-' + day;
        var institutions = $('#institutions').val();
        var deviceId = '${deviceId}';
        var goodsName = $('#goodsName').val();
        window.location.href="${ctx}/reportFromMgt/show.do?time="+ymd+"&deviceId="+deviceId;
    });
</script>

</body>
<!--  /Body -->
</html>
