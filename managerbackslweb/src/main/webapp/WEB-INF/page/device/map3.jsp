<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>
<!DOCTYPE html>
<html>
<head>
    <title>地图</title>

    <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no">

    <link rel="stylesheet" href="${ctx }/static/dist/leaflet.css" />
    <!--[if lte IE 8]>
    <link rel="stylesheet" href="${ctx }/static/dist/leaflet-ie.css" />
    <link rel="stylesheet" href="${ctx }/static/dist/locate-ie.css"/>
    <![endif]-->
    <link rel="stylesheet" href="${ctx }/static/dist/mobile.css" />
    <link rel="stylesheet" href="${ctx }/static/dist/locate.css" />

    <script src="${ctx }/static/dist/leaflet.js"></script>
    <script src="${ctx }/static/dist/proj4js-compressed.js"></script>
    <script src="${ctx }/static/dist/proj4leaflet.js"></script>
    <script src="${ctx }/static/dist/locate.js" ></script>
</head>
<body>
<div id="map"></div>
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
</body>
</html>
