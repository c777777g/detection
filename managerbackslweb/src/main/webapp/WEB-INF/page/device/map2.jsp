<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <title>后台</title>
    <script charset="utf-8" src="https://map.qq.com/api/js?v=2.exp&key=GXKBZ-BXVKU-2O3VL-2XAKN-74CMO-DOFQU"></script>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <style type="text/css">
        ul, li {
            margin: 0;
            padding: 0;
        }

        #map_canvas {
            position: absolute;
            left: 10px;
            min-width: 1630px;
            min-height: 830px;
            border: 1px solid #ff0000;
        }

        #panel {
            width: 300px;
            height: 300px;
            overflow: auto;
            border: 1px solid #000000;
        }

        #attributeList li {
            border-bottom: 1px dashed #999999;
            padding: 5px 5px;
            line-height: 20px;
        }
    </style>
</head>
<body onload="window.init()">
<div id="map_canvas"></div>
<%--<div id="panel">
    <div style="padding-left: 20px;padding-right: 20px;padding-top: 5px">
        <label>下拉选择</label>
        <select style="width:100%;" id="selectType" name="selectType">
            <option value="1">片区查询</option>
            <option value="2">设备查询</option>
        </select>
    </div>
    <div style="padding-left: 20px;padding-right: 20px;">
        <label>搜索框</label>
        <div class="form-group">
                  <span class="input-icon">
                     <input type="text" class="form-control input-sm" id="selectKey" name="selectKey">
                      <i class="glyphicon glyphicon-search blue"></i>
                 </span>
        </div>
    </div>
    <div style="padding-left: 20px;padding-right: 20px;">
        <div>
            <button type="button" class="btn btn-default"  id="query">查询</button>
        </div>
    </div>
    <hr>
</div>--%>
</body>
</html>
<script type="text/javascript">
    var markerIndex = 0;
    var map;

    function $(a) {
        return document.getElementById(a);
    }
    var data = [${MARKER}];
    window.init = function() {
        var center = new qq.maps.LatLng(9.916527,6.397128);
        var Map = qq.maps.Map;
        var Marker = qq.maps.Marker;
        var LatLng = qq.maps.LatLng;
        var Event = qq.maps.event;

        var MarkerImage = qq.maps.MarkerImage;
        var MarkerShape = qq.maps.MarkerShape;
        var MarkerAnimation = qq.maps.MarkerAnimation;
        var Point = qq.maps.Point;
        var Size = qq.maps.Size;
        var ALIGN = qq.maps.ALIGN;

        var MVCArray = qq.maps.MVCArray;
        var MarkerCluster = qq.maps.MarkerCluster;
        var Cluster = qq.maps.Cluster;
        var MarkerDecoration = qq.maps.MarkerDecoration;

        var forEach = function (array, fun) {
            for (var i = 0, l = array.length; i < l; ++i) {
                if (fun(array[i], i) === false) {
                    return false;
                }
            }
        };

        /*var latlng = new LatLng(23.1, 113.28);*/
        var citylocation,map = null;
        //获取城市列表接口设置中心点
        citylocation = new qq.maps.CityService({
            complete : function(result){
                map.setCenter(result.detail.latLng);
            }
        });
        //调用searchLocalCity();方法    根据用户IP查询城市信息。
        citylocation.searchLocalCity();
        var options = {
            'zoom':11,
            'center':center,
            'mapTypeId':"roadmap"
        };

        var map = new Map($('map_canvas'), options);

        var markers = new MVCArray();
        var markerCluster;

        var infoWin = new qq.maps.InfoWindow({
            map: map
        });

        function createCluster() {
            for (var i = 0; i < data.length; i++) {
                var latLng = new LatLng(data[i][0], data[i][1]);
                /* var decoration = new MarkerDecoration(i, new Point(0, -5));*/
                var marker = new Marker({
                    'position':latLng,
                    map:map
                });
                (function(n){
                    qq.maps.event.addListener(marker, 'click', function() {
                        infoWin.open();
                        infoWin.setContent('<div style="text-align:center;white-space:'+
                                'nowrap;margin:10px;"> ' +
                                data[n][2] + ' </div>');
                        infoWin.setPosition(new LatLng(data[n][0], data[n][1]));
                        setTimeout(function(){infoWin.close();},5000);
                    });
                })(i);
                markers.push(marker);
            }

            markerClusterer = new MarkerCluster({
                map:map,
                minimumClusterSize:2, //默认2
                markers:markers,
                zoomOnClick:true, //默认为true
                gridSize:30, //默认60
                averageCenter:true, //默认false
                maxZoom:18, //默认18
            });
        };
        createCluster();
    };
</script>
