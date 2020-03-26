<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>

<%--<script src="${ctx }/static/map/ol.js"></script>--%>
<%--<link rel="stylesheet" href="${ctx }/static/map/ol.css" type="text/css">--%>

<head>
    <title>Static Image</title>
    <%--<link rel="stylesheet" href="https://openlayers.org/en/v5.3.0/css/ol.css" type="text/css">--%>
    <%--<!-- The line below is only needed for old environments like Internet Explorer and Android 4.x -->--%>
    <%--<script src="https://cdn.polyfill.io/v2/polyfill.min.js?features=requestAnimationFrame,Element.prototype.classList,URL"></script>--%>

    <script type="text/javascript" src="${ctx }/static/map/ol.js"></script>
    <link rel="stylesheet" href="${ctx }/static/map/ol.css" type="text/css">

    <%--<style>--%>
        <%--.map:-moz-full-screen {--%>
            <%--height: 100%;--%>
        <%--}--%>
        <%--.map:-webkit-full-screen {--%>
            <%--height: 100%;--%>
        <%--}--%>
        <%--.map:-ms-fullscreen {--%>
            <%--height: 100%;--%>
        <%--}--%>
        <%--.map:fullscreen {--%>
            <%--height: 100%;--%>
        <%--}--%>
        <%--.ol-rotate {--%>
            <%--top: 3em;--%>
        <%--}--%>
    <%--</style>--%>


</head>
<body>

<div id="map" class="map">
    <div id="myposition"></div>
</div>

<script>
    //    import Map from 'ol/Map.js';
    //    import View from 'ol/View.js';
    //    import {getCenter} from 'ol/extent.js';
    //    import ImageLayer from 'ol/layer/Image.js';
    //    import Projection from 'ol/proj/Projection.js';
    //    import Static from 'ol/source/ImageStatic.js';

    // Map views always need a projection.  Here we just want to map image
    // coordinates directly to map coordinates, so we create a projection that uses
    // the image extent in pixels.
    //    var extent = [0, 0, 1024, 968];
    //    var projection = new ol.proj.Projection({
    //        code: 'xkcd-image',
    //        units: 'pixels',
    //        extent: extent
    //    });

    //显示鼠标坐标
    var mousePositionControl = new ol.control.MousePosition({
        //样式类名称
        className: 'mosuePosition',
        //投影坐标格式，显示小数点后边多少位
        coordinateFormat: ol.coordinate.createStringXY(8),
        //指定投影
        projection: 'EPSG:4326',
        //目标容器
        target: document.getElementById('myposition')
    });


    var extent = [113.388562202454, 23.151487975204, 113.453536033630, 23.178633229272];
    var projection = new ol.proj.Projection({
        code: 'EPSG:3395',
        units: 'm',
        axisOrientation: 'neu',
        extent: extent
    });

    var layer = new ol.layer.Image({
        source: new ol.source.ImageStatic({
//            attributions: '© <a href="http://xkcd.com/license.html">xkcd</a>',
//
            url: '${ctx }/static/map/L17/2.png',
            projection: projection,
            imageExtent: extent
        })
    })

    var layer1 = new ol.layer.Vector({
        source: new ol.source.Vector(),
        extent:extent
    })

    var map = new ol.Map({
//        controls: ol.control.defaults().extend([
//            new ol.control.defaults()
//        ]),
        layers: [
            layer,
            layer1
        ],
        target: 'map',
        view: new ol.View({
            projection: projection,
            center: ol.extent.getCenter(extent),
            zoom: 2,
            maxZoom: 8
        })
    });


    //显示鼠标坐标
    map.addControl(mousePositionControl);

    //    var anchor = new ol.Overlay({
    //        element: document.getElementById('anchor')
    //    });
    //    // 关键的一点，需要设置附加到地图上的位置
    //    anchor.setPosition([113.388562222454, 23.151487978204]);
    //    // 然后添加到map上
    //    map.addOverlay(anchor);
    // 创建一个Feature，并设置好在地图上的位置

    var anchor = new ol.Feature({
        geometry: new ol.geom.Point([113.429923, 23.1666981]),
        name: 'My Polygon'
    });
    // 设置样式，在样式中就可以设置图标
    anchor.setStyle(new ol.style.Style({
        image: new ol.style.Icon({
            src: '${ctx }/static/assets/img/mark_b.png'
        })
    }));
    // 添加到之前的创建的layer中去
    layer1.getSource().addFeature(anchor);





</script>
</body>