<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cl"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<!DOCTYPE html>
    <head>
        <meta charset="utf-8" />
        <title>后台</title>
        <c:set var="ctx" value="<%=request.getContextPath()%>"/>
        <script src="//cdn.bootcss.com/Chart.js/2.1.6/Chart.bundle.js"></script>
        <script src="${ctx }/static/assets/js/jquery-2.0.3.min.js"></script>
        <link href="${ctx }/static/assets/css/bootstrap.min.css" rel="stylesheet" />
        <link href="${ctx }/static/assets/css/font-awesome.min.css" rel="stylesheet" />
        <link href="${ctx }/static/assets/css/beyond.min.css" rel="stylesheet" />
        <link rel="stylesheet" href="${ctx }/static/dist/leaflet.css" />
        <!--[if lte IE 8]>
        <link rel="stylesheet" href="${ctx }/static/dist/leaflet-ie.css" />
        <link rel="stylesheet" href="${ctx }/static/dist/locate-ie.css"/>
        <![endif]-->
        <link rel="stylesheet" href="${ctx }/static/dist/mobile.css" />
        <link rel="stylesheet" href="${ctx }/static/dist/locate.css" />


    <style>
            .form-control, select {
            padding: 6px 1px;
            }
            .daterangepicker .ranges .input-mini {
            width: 150px;
            }
            .daterangepicker .ranges .range_inputs>div:nth-child(2) {
            padding-left: 0px;
            }
        </style>
    </head>
<body>
    <div class="page-body" style="padding:20px 20px" id="add">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="well with-header">
                    <div class="header bordered-success">
                        设备记录查询
                    </div>
                    <!--每一个功能是一个row div， 留意两个时间快需要id，是需要JavaScript-->
                    <div class="row">
                        <div class="col-lg-2 col-xs-2 col-md-2">
                            <div class="form-group">
                                <label for="time">时间区间</label>
                                <div class="controls">
                                    <div class="input-group">
                                        <span class="input-group-addon">
                                            <i class="fa fa-calendar"></i>
                                        </span>
                                        <input type="text" class="form-control" id="time"  readonly="readonly" placeholder="${TIME}" value="${TIME}">
                                    </div>
                                </div>
                            </div>
                        </div>

                    <div class="col-lg-2 col-xs-2 col-md-2">
                        <div>
                            <label>下拉选择</label>
                                <select style="width:100%;" id="selectType" name="selectType">
                                    <option value="1" selected="selected">设备ID查询</option>
                                    <option value="2">设备名查询</option>
                                </select>
                        </div>
                    </div>

                        <div class="col-lg-2 col-xs-2 col-md-2">
                            <div class="collapse in">
                                <label>搜索框</label>
                                <div class="form-group">
                                    <span class="input-icon">
                                        <input type="text" class="form-control input-sm" id="selectKey" name="selectKey" placeholder="请输入查询条件">
                                        <i class="glyphicon glyphicon-search blue"></i>
                                    </span>
                                </div>
                            </div>
                        </div>

                        <div class="col-lg-2 col-xs-2 col-md-2">
                            <div style="margin-top: 20px;">
                                <div>
                                    <a href="#" class="btn btn-default" id="query">查询</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <div id="map" style="height:800px; margin-top:20px" ></div>
                </div>
            </div>
        </div>
    </div>
</body>

<script src="${ctx }/static/assets/js/datetime/moment.js"></script>
<script src="${ctx }/static/assets/js/datetime/daterangepicker.js"></script>
<script src="${ctx }/static/dist/leaflet.js"></script>
<script src="${ctx }/static/dist/proj4js-compressed.js"></script>
<script src="${ctx }/static/dist/proj4leaflet.js"></script>
<script src="${ctx }/static/dist/locate.js" ></script>
<script>
<%--$("#download").click(function () {--%>
<%--    var selectType = $('#selectType').val();--%>
<%--    var selectKey = $('#selectKey').val();--%>
<%--    var fromLabel = $('#time').val();--%>
<%--    if(fromLabel==null||fromLabel==""){--%>
<%--        Notify("请先选择时间范围", 'top-right', '5000', 'danger', 'fa-bolt', true);--%>
<%--        return;--%>
<%--    }--%>
<%--    window.location.href="${ctx}/reportFormMgt/getDeviceDayFormReport.do?fromLabel="+fromLabel+"&selectType="+selectType+"&selectKey="+selectKey;--%>
<%--});--%>

<!--时间-->
$('#time').daterangepicker({
    format: 'YYYY/MM/DD hh:mm A',
    singleDatePicker: true,
    timePicker24Hour: true,
    autoUpdateInput: false,
    timePicker: true,
    startDate: moment().startOf('hour'),
    endDate: moment().startOf('hour').add(32, 'hour'),
    locale: {
        applyLabel: "应用",
        cancelLabel: "取消",
        resetLabel: "重置",
        fromLabel: '开始时间',
        toLabel: '结束时间',
        daysOfWeek: ["日", "一", "二", "三", "四", "五", "六"],
        monthNames: ["一月", "二月", "三月", "四月", "五月", "六月", "七月", "八月", "九月", "十月", "十一月", "十二月"],
    }
});

$("#query").click(function () {
    $("#mask").show();
    var selectTime = $('#time').val();
    if(selectTime == "" || selectTime == null){
        alert("请选择查询时间");
        return;
    }

    var time = new Date($('#time').val());
    var year = time.getFullYear();
    var month = time.getMonth()+1;
    if(month<10) month = "0"+month;
    var day = time.getDate();
    if(day<10) day = "0"+day;
    var hour = time.getHours();
    if(hour<10) hour = "0"+hour;
    var minute = time.getMinutes(); //分
    if(minute<10) minute = "0"+minute;
    var ymd = year + '-' + month + '-' + day+" "+hour+":"+minute+":00";
    //var institutions = $('#institutions').val();
    //var goodsName = $('#goodsName').val();
    var selectType = $("#selectType").val();
    var selectKey = $('#selectKey').val();
    window.location.href="${ctx}/deviceRecordMgt/deviceRecord.do?time="+ymd+"&selectType="+selectType+"&selectKey="+selectKey;
});

</script>

<script type="text/javascript">
    var url_google = 'http://localhost:8844/1818940751/{z}/{x}/{y}';
    var glayer_google = new L.TileLayer(url_google, { maxZoom: 18, attribution: 'Google普通地图' });

    var url_google_satelite = 'http://localhost:8844/47626774/{z}/{x}/{y}';
    var glayer_google_satelite = new L.TileLayer(url_google_satelite, { maxZoom: 18, attribution: 'Google卫星地图' });

    var url_amp = '${ctx }/static/map-picture/788865972/{z}/{x}/{y}.png';
    var glayer_amap = new L.TileLayer(url_amp, { maxZoom: 17, attribution: '高德普通地图' });

    var url_amap_satelite = 'http://localhost:8844/1542757547/{z}/{x}/{y}';
    var glayer_amap_satelite = new L.TileLayer(url_amap_satelite, { maxZoom: 18, attribution: '高德卫星地图' });

    //地图中心位置
    var latlng = new L.LatLng(23.163, 113.435);
    var map = new L.Map('map', { center: latlng, zoom: 17, layers: [glayer_amap] });

    var baseLayers =
    {
    "高德普通地图": glayer_amap,
    //        "高德卫星地图": glayer_amap_satelite,
    //        "Google普通地图": glayer_google,
    //        "Google卫星地图": glayer_google_satelite
    };
    //    L.control.layers(baseLayers).addTo(map);

    var data = [${MARKER}];
    for (var i = 0; i < data.length; i++) {
        var latlng2 = new L.LatLng(data[i][0], data[i][1]);
        var marker = new L.Marker(latlng2);
        map.addLayer(marker);
        marker.bindPopup("<p>"+ data[i][2] +"</p>").closePopup();
    }

    L.control.locate({
    position: 'topleft',  // set the location of the control
    drawCircle: false,     // controls whether a circle is drawn that shows the uncertainty about the location
    follow: false,    // follow the location if `watch` and `setView` are set to true in locateOptions
    circleStyle: {},  // change the style of the circle around the user's location
    markerStyle: {},
    metric: true,     // use metric or imperial units
    onLocationError: function(err) {alert(err.message)},  // define an error callback function
    title: "Show me where I am",  // title of the locat control
    popupText: ["You are within ", " from this point"],  // text to appear if user clicks on circle
    setView: true,     // automatically sets the map view to the user's location
    locateOptions: {}  // define location options e.g enableHighAccuracy: true
    }).addTo(map);
</script>

</html>

