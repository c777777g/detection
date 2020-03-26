<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>

    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">

    <title>后台</title>
    <style>
        canvas {
            -moz-user-select: none;
            -webkit-user-select: none;
            -ms-user-select: none;
        }
    </style>
    <script src="${ctx }/static/assets/js/charts/chartjs/Chart.js"></script>
    <script src="${ctx }/static/recorder/HZRecorder.js"></script>
    <script>
        function loadXMLDoc(institutions) {
            var xmlhttp;
            if (window.XMLHttpRequest) {
                //  IE7+, Firefox, Chrome, Opera, Safari 浏览器执行代码
                xmlhttp = new XMLHttpRequest();
            }
            else {
                // IE6, IE5 浏览器执行代码
                xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
            }
            xmlhttp.onreadystatechange = function () {
                if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                    document.getElementById("myDiv").innerHTML = xmlhttp.responseText;
                }
            }
            xmlhttp.open("GET", "${ctx}/deviceMgt/list.do?institutions=" + institutions, true);
            xmlhttp.send();
        }
    </script>


    <script type="text/javascript">
        function pageTo(iClass, title) {
            loadXMLDoc(title);
        }

        //        //解决当窗口改变大小的时候执行js事件，以让iframe自适就高度
        //        function changeFrameHeight() {
        //            var ifm = document.getElementById("iframepage");
        //            ifm.height = document.documentElement.clientHeight;
        //            var pageHeaderDiv = $("#pageHeaderDiv").css("width").replace("px", "");
        //            ifm.width = parseInt(pageHeaderDiv);
        //        }
        //        //window.onresize的作用就是当窗口大小改变的时候会触发这个事件。
        //        window.onresize = function () {
        //            changeFrameHeight();
        //        }
    </script>

</head>
<body onmousemove="startRefreshCount()">
<div class="page-body" style="padding:20px 20px">
    <div class="row">
        <div class="col-lg-12 col-md-12 col-sm-12 col-xs-12">
            <div class="well invoice-container">
                <div class="row">
                    <div class="col-xs-6">
                        <h3 class="">
                            <i class="fa fa-laptop"></i>
                            设备名：${bean.deviceName}
                        </h3>
                        <div>
                            <span>地址:</span>
                            <span>${bean.deviceAddress}</span>
                        </div>

                        <%--<div>--%>
                        <%--<a href="${ctx }/deviceMgt/mapEdit.do?id=${bean.id}" class="btn btn-primary btn-xs edit"><i--%>
                        <%--class="fa fa-map-marker"></i>查看定位</a>--%>
                        <%--</div>--%>

                    </div>
                    <div class="col-xs-6 text-right">
                        <h1>
                            <h4 class="">
                                <i class="fa fa-bookmark-o"></i>
                                设备ID：${bean.deviceId}
                            </h4>
                        </h1>
                        <div>
                            <span>状态:</span>

                            <c:if test="${bean.state==1}"> <span>在线</span><i class="fa  fa-signal"
                                                                             style="color: rgb(95,212,62);"></i></c:if>
                            <c:if test="${bean.state==2}"> <span>离线</span><i class="fa  fa-times"
                                                                             style="color: rgb(255,0,0);"></i></c:if>
                            <div>
                                <span>IP:</span>
                                <span>${bean.ip}</span>
                            </div>

                            <%--<c:if test="${USER.role eq 'user'}">--%>
                            <%--<div>--%>
                            <%--<a href="${ctx }/deviceMgt/getDevicePropert.do?id=${bean.id}"--%>
                            <%--class="btn btn-primary btn-xs edit"><i class="fa fa-gears"></i>远程控制</a>--%>
                            <%--</div>--%>
                            <%--</c:if>--%>
                        </div>
                        <div class="horizontal-space"></div>
                    </div>
                </div>
                <hr class="wide"/>
                <div class="row">
                    <div class="col-xs-6 ">
                        <h3>传感器</h3>
                    </div>

                    <div class="col-xs-6 text-right">
                        <i class="stat-icon icon-lg fa fa-tasks " id="addDeviceLimit" style="color: rgb(46, 195, 232);">设置</i>
                    </div>

                </div>
                <div class="row">
                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <%--<div class="databox-left bg-azure">--%>
                            <div class="databox-left bg-azure">

                                <div class="databox-piechart">
                                    <div id="bluePie1" class="easyPieChart" data-percent="">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">温度</span>
                                <div class="databox-text darkgray">设备环境当前温度</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>
                                <div class="btn  btn-sm pull-right" style="top: -15px" onclick="changesAlarm(1)">
                                    <c:if test="${deviceLimit.temperatureAlarm==1}"> 报警:ON</c:if>
                                    <c:if test="${deviceLimit.temperatureAlarm!=1}"> 报警:OFF</c:if>
                                </div>
                            </div>

                        </div>
                    </div>
                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <div class="databox-left bg-azure">
                                <div class="databox-piechart">
                                    <div id="bluePie2" class="easyPieChart" data-percent="${temperature}">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">湿度</span>
                                <div class="databox-text darkgray">设备环境当前湿度</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>

                                <div class="btn  btn-sm pull-right" style="top: -15px" onclick="changesAlarm(2)">
                                    <c:if test="${deviceLimit.humidityAlarm==1}"> 报警:ON</c:if>
                                    <c:if test="${deviceLimit.humidityAlarm!=1}"> 报警:OFF</c:if>
                                </div>

                            </div>
                        </div>
                    </div>
                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <div class="databox-left bg-azure">
                                <div class="databox-piechart">
                                    <div id="bluePie3" class="easyPieChart" data-percent="${temperature}">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">PM2.5(mg/m3)</span>
                                <div class="databox-text darkgray">设备环境当前PM2.5值,单位:mg/m3</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>

                                <div class="btn  btn-sm pull-right" style="top: -15px" onclick="changesAlarm(3)">
                                    <c:if test="${deviceLimit.pm25Alarm==1}"> 报警:ON</c:if>
                                    <c:if test="${deviceLimit.pm25Alarm!=1}"> 报警:OFF</c:if>
                                </div>

                            </div>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <div class="databox-left bg-azure">
                                <div class="databox-piechart">
                                    <div id="bluePie4" class="easyPieChart" data-percent="${temperature}">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">环境亮度</span>
                                <div class="databox-text darkgray">设备当前环境亮度</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>

                                <%--<div class="btn  btn-sm pull-right" style="top: -15px">报警:ON</div>--%>
                                <%--<label class="databox-stat themesecondary blue" style="top: 50px ">--%>
                                <%--<input class="checkbox-slider toggle colored-blue" type="checkbox">--%>
                                <%--<span class="text">报警</span>--%>
                                <%--</label>--%>


                            </div>
                        </div>
                    </div>

                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <div class="databox-left bg-azure">
                                <div class="databox-piechart">
                                    <div id="bluePie7" class="easyPieChart" data-percent="">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">人体报警</span>
                                <div class="databox-text darkgray">设备当前环境下有没有人靠近</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>

                                <div class="btn  btn-sm pull-right" style="top: -15px" onclick="changesAlarm(5)">
                                    <c:if test="${deviceLimit.bodyInductoAlarm==1}"> 报警:ON</c:if>
                                    <c:if test="${deviceLimit.bodyInductoAlarm!=1}"> 报警:OFF</c:if>
                                </div>

                            </div>
                        </div>
                    </div>


                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">

                            <div class="databox-left bg-azure">
                                <div class="databox-piechart">
                                    <div id="bluePie6" class="easyPieChart" data-percent="${temperature}">
                                        <!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->
                                        <span class="white font-90 percent" style="line-height: 60px;"></span>
                                    </div>
                                </div>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">烟感报警</span>
                                <div class="databox-text darkgray">设备当前环境下检测的烟报警</div>
                                <div class="databox-stat themesecondary blue">
                                    <i class="stat-icon icon-lg fa fa-tasks"></i>
                                </div>

                                <div class="btn  btn-sm pull-right" style="top: -15px" onclick="changesAlarm(6)">
                                    <c:if test="${deviceLimit.smokeSensorsAlarm==1}"> 报警:ON</c:if>
                                    <c:if test="${deviceLimit.smokeSensorsAlarm!=1}"> 报警:OFF</c:if>
                                </div>

                            </div>
                        </div>
                    </div>

                </div>
                <div class="row">
                    <!--从这里复制，记得带下面的script-->
                    <div class="col-lg-4 col-md-4 col-sm-6 col-xs-6">
                        <div class="databox databox-lg radius-bordered databox-shadowed databox-graded">
                            <div class="databox-left bg-azure">
                                <%--<div class="databox-piechart">--%>
                                <%--<div id="bluePie5" class="easyPieChart" data-percent="${temperature}">--%>
                                <%--<!--传值到这个data-percent就可以了。script里面是靠这里的id决定-->--%>
                                <%--<span class="white font-90 percent" style="line-height: 60px;"></span>--%>
                                <%--</div>--%>
                                <div class="knob-container">
                                    <%--<input class="knob" data-width="75" data-displayprevious=true data-fgcolor="#ed4e2a" data-cursor=true value="75" data-thickness=".2">--%>
                                    <input id="illuminatingBrightness" name="illuminatingBrightness" class="knob"
                                           data-width="60" data-angleoffset=90 data-linecap=round
                                           data-fgcolor="#ff0000" value="${deviceSensor.illuminatingBrightness}"
                                           data-thickness=".2">
                                </div>
                                <%--</div>--%>
                            </div>
                            <div class="databox-right">
                                <span class="databox-number blue">照明亮度</span>
                                <div class="databox-text darkgray">设备照明的程度值</div>
                                <div class="databox-stat themesecondary blue">
                                    <%--<select id="mode1" name="mode1" class="btn btn-xs btn-blue dropdown-toggle"--%>
                                    <%--style=" font-size: 10px;top: 5px;right: 5px">--%>
                                    <%--<option style="height:10px; font-size: 10px;top: 5px" value="1"<c:if test="${deviceLimit.mode==1}">selected="selected"</c:if>>夜间模式</option>--%>
                                    <%--<option style="height:10px; font-size: 10px;top: 5px" value="0"<c:if test="${deviceLimit.mode==0}">selected="selected"</c:if> >关模式</option>--%>
                                    <%--</select>--%>

                                    <div class="btn  btn-sm " style="font-size: 10px;top: 5px;right: 5px"
                                         onclick="changesAlarm(7)">
                                        <c:if test="${deviceLimit.mode!=0}"> 夜间模式 </c:if>
                                        <c:if test="${deviceLimit.mode==0}"> 默认模式</c:if>
                                    </div>

                                    <select id="flash" name="flash" class="btn btn-xs btn-blue dropdown-toggle"
                                            style=" font-size: 10px;top: 5px">

                                        <option style="height:10px; font-size: 10px;top: 5px" value="1">闪灯</option>
                                        <option style="height:10px;  font-size: 10px;top: 5px" value="0"
                                                selected="selected">长亮
                                        </option>
                                    </select>

                                </div>


                                <%--<div class="btn  btn-sm pull-right" style="top: -15px">开关</div>--%>

                                <label class="databox-stat themesecondary blue" style="top: 50px ">
                                    <input id="IlluminatingSwitch" class="checkbox-slider toggle colored-blue"
                                    <%--checked="checked" --%>
                                           <c:if test="${deviceSensor.illuminatingBrightness> 0}">checked="checked"</c:if>
                                           type="checkbox" onclick="controlIlluminating()">
                                    <span class="text"></span>
                                </label>
                            </div>
                        </div>
                    </div>

                </div>

                <hr class="wide"/>

                <div class="row">
                    <div class="col-xs-6  text-right">
                        <span>创建日期:</span>
                        <span><fmt:formatDate value='${bean.createTime}' type='date'
                                              pattern='yyyy-MM-dd HH:mm:ss'/></span>
                    </div>

                </div>
                <hr class="wide"/>
            </div>

            <div class="col-xs-6 text-right">
                <a href="${ctx }/deviceMgt/edit.do?id=${bean.id}" class="btn btn-primary btn-xs edit"><i
                        class="fa fa-edit"></i>编辑</a>
                <div>

                </div>
            </div>
        </div>
    </div>


    <div id="addDeviceLimitModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
         aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
        <div class="modal-dialog modal-lg">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                    <h4 class="modal-title">设置传感器</h4>
                </div>
                <div class="modal-body">
                    <div class="widget-body">

                        <div class="row">
                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">高湿度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="humidityMax"
                                           name="humidityMax" value="${deviceLimit.humidityMax}"
                                           placeholder="高湿度警告值范围1-100，例子：95"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">低湿度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="humidityMin"
                                           name="humidityMin" value="${deviceLimit.humidityMin}"
                                           placeholder="低湿度警告值范围0-100，例子：10"/>
                                </div>
                            </div>


                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">高温度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="temperatureMax"
                                           name="temperatureMax" value="${deviceLimit.temperatureMax}"
                                           placeholder="高温度警告值范围-100至100，例子：30"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">低温度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="temperatureMin"
                                           name="temperatureMin" value="${deviceLimit.temperatureMin}"
                                           placeholder="低温度警告值范围-100至100，例子：-5"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">高PM2.5警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="pm25Max"
                                           name="pm25Max" value="${deviceLimit.pm25Max}" placeholder="高PM2.5警告值"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">低PM2.5警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="pm25Min"
                                           name="pm25Min" value="${deviceLimit.pm25Min}" placeholder="低PM2.5警告值"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">高光度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="opticalInductorMax"
                                           name="opticalInductorMax" value="${deviceLimit.opticalInductorMax}"
                                           placeholder="高光度警告值"/>
                                </div>
                            </div>

                            <div class="form-group">
                                <label class="col-sm-2 control-label no-padding-right">低光度警告值</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="opticalInductorMin"
                                           name="opticalInductorMin" value="${deviceLimit.opticalInductorMin}"
                                           placeholder="低光度警告值"/>
                                </div>
                            </div>


                            <div class="col-xs-6 text-right">
                                <button class="btn btn-primary btn-xs edit" onclick="saveDeviceLimit()">提交</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>

<!--Easy Pie Charts Needed Scripts  小饼图-->

<script src="${ctx }/static/assets/js/charts/easypiechart/easypiechart.js"></script>

<!--jQUery Knob-->
<script src="${ctx }/static/assets/js/knob/jquery.knob.js"></script>


<%--提交按键--%>
<script>


    function controlIlluminating() {
        var illuminatingBrightness = $("#illuminatingBrightness").val();
        var flash = $("#flash").val();
        var mode1 = $("#mode1").val();
        var IlluminatingSwitch = $("#IlluminatingSwitch").is(":checked")
//        console.log("illuminatingBrightness=" + illuminatingBrightness);
//        console.log("flash=" + flash);
//        console.log("mode1=" + mode1);
//        console.log("IlluminatingSwitch=" + IlluminatingSwitch);

        if ($("#IlluminatingSwitch").is(":checked")) {
            var url = '${ctx}' + "/remoteMgt/controlIlluminating.do";
            var sendData = {
                "deviceId": "${bean.deviceId}",
                "illuminatingBrightness": illuminatingBrightness,
                "flash": flash,
                "mode": mode1
            };
            $.post(url, sendData, function (backData) {
                if (backData.status == 1) {
                    window.location.href = '${ctx}/deviceMgt/show.do?deviceId=' +${bean.deviceId};
                } else {
                    warningAlert(backData.message[0].msg);
                }
            });
        } else {
            var url = '${ctx}' + "/remoteMgt/closeIlluminating.do";
            var sendData = {
                "deviceId": "${bean.deviceId}",
                "illuminatingBrightness": illuminatingBrightness
            };
            $.post(url, sendData, function (backData) {
                if (backData.status == 1) {
                    window.location.href = '${ctx}/deviceMgt/show.do?deviceId=' +${bean.deviceId};
                } else {
                    warningAlert(backData.message[0].msg);
                }
            });
        }
    }


</script>


<script>
    //---Jquery Knob--
    $(".knob").knob();

    $(".illuminatingBrightness").knob();


    //   温度
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie1'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + '℃';
            }
        });
    });
    <!--湿度 -->
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie2'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + '℃';
            }
        });
    });
    <!--PM2.5 -->
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie3'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + 'mg/m3';
            }
        });
    });
    <!--环境亮度 -->
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie4'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + 'XL';
            }
        });
    });
    //
    <!--照明 -->
    //    document.addEventListener('DOMContentLoaded', function () {
    //        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie5'), {
    //            easing: 'easeOutElastic',
    //            size: 60,
    //            barColor: '#ffffff', // 进度条颜色
    //            trackColor: '#55cfec',  // 进度条背景颜色
    //            scaleColor: false,
    //            lineWidth: 4,
    //            trackWidth: 3,
    //            lineCap: 'butt',
    //            animate: 500,
    //            onStep: function (from, to, percent) {
    //                this.el.children[0].innerHTML = Math.round(percent) + '℃';
    //            }
    //        });
    //    });
    <!--烟 -->
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie6'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + '℃';
            }
        });
    });
    <!--人体 -->
    document.addEventListener('DOMContentLoaded', function () {
        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie7'), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 500,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = Math.round(percent) + '℃';
            }
        });
    });

    //
    //
    <!--人体 -->
    //        document.addEventListener('DOMContentLoaded', function () {
    //            var chart = window.chart = new EasyPieChart(document.getElementById('bluePie8'), {
    //                easing: 'easeOutElastic',
    //                size: 60,
    //                barColor: '#ffffff', // 进度条颜色
    //                trackColor: '#55cfec',  // 进度条背景颜色
    //                scaleColor: false,
    //                lineWidth: 4,
    //                trackWidth: 3,
    //                lineCap: 'butt',
    //                animate: 500,
    //                onStep: function (from, to, percent) {
    //                    this.el.children[0].innerHTML = Math.round(percent) ;
    //                }
    //            });
    //        });


    //    document.addEventListener('DOMContentLoaded', function () {
    //        var chart = window.chart = new EasyPieChart(document.getElementById('bluePie9'), {
    //            easing: 'easeOutElastic',
    //            size: 60,
    //            barColor: '#ffffff', // 进度条颜色
    //            trackColor: '#55cfec',  // 进度条背景颜色
    //            scaleColor: false,
    //            lineWidth: 4,
    //            trackWidth: 3,
    //            lineCap: 'butt',
    //            animate: 500,
    //            onStep: function (from, to, percent) {
    //                this.el.children[0].innerHTML = Math.round(percent);
    //            }
    //        });
    //    });


</script>


<script>


    function setEasyPieChart(id, percent) {
        if (Number(${deviceLimit.temperatureMax}) > Number(percent) && Number(percent) > Number(${deviceLimit.temperatureMin})) {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ffffff', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (percent < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = Math.round(percent) + '℃';
                    }
                }
            });
        } else {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ff0000', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (percent < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = Math.round(percent) + '℃';
                    }
                }
            });
        }


    }
    function setEasyPieChart2(id, percent) {

        if (Number(${deviceLimit.humidityMax}) > Number(percent) && Number(percent) > Number(${deviceLimit.humidityMin})) {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ffffff', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (percent < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = Math.round(percent) + '%';
                    }
                }
            });
        } else {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ff0000', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (percent < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = Math.round(percent) + '%';
                    }
                }
            });
        }
    }

    function setEasyPieChart3(id, p) {
        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                if (p < -350) {
                    this.el.children[0].innerHTML = '无信号';
                } else {
                    this.el.children[0].innerHTML = "正常";
                }
            }
        });
    }
    function setEasyPieChart4(id, p) {
        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ff0000', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                if (p < -350) {
                    this.el.children[0].innerHTML = '无信号';
                } else {
                    this.el.children[0].innerHTML = "报警";
                }
            }
        });
    }
    function setEasyPieChart5(id, p) {
        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                if (p < -350) {
                    this.el.children[0].innerHTML = '无信号';
                } else {
                    this.el.children[0].innerHTML = Math.round(p) + 'LX';
                }
            }
        });
    }

    function setEasyPieChart6(id, p) {

        if (Number(${deviceLimit.pm25Max}) > Number(p) && Number(p) > Number(${deviceLimit.pm25Min})) {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ffffff', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (p < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = p;
                    }
                }
            });
        } else {
            var chart = window.chart = new EasyPieChart(document.getElementById(id), {
                easing: 'easeOutElastic',
                size: 60,
                barColor: '#ff0000', // 进度条颜色
                trackColor: '#55cfec',  // 进度条背景颜色
                scaleColor: false,
                lineWidth: 4,
                trackWidth: 3,
                lineCap: 'butt',
                animate: 1,
                onStep: function (from, to, percent) {
                    if (p < -350) {
                        this.el.children[0].innerHTML = '无信号';
                    } else {
                        this.el.children[0].innerHTML = p;
                    }
                }
            });
        }
    }
    function setEasyPieChart7(id, percent) {

        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
            easing: 'easeOutElastic',
            size: 60,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 4,
            trackWidth: 3,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                if (percent < -350) {
                    this.el.children[0].innerHTML = '无信号';
                } else {
                    this.el.children[0].innerHTML = Math.round(percent) + '%';
                }
            }
        });
    }


    function setEasyPieChart8(id, p) {

        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
//            easing: 'easeOutElastic',
            size: 1,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 0,
            trackWidth: 0,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                if (Math.round(percent) < -350 || Math.round(percent) > 350) {
                    this.el.children[0].innerHTML = '无信号';
                } else {
                    this.el.children[0].innerHTML = p;
                }
            }
        });
    }
    function setEasyPieChart9(id, percent) {
        var chart = window.chart = new EasyPieChart(document.getElementById(id), {
//            easing: 'easeOutElastic',
            size: 1,
            barColor: '#ffffff', // 进度条颜色
            trackColor: '#55cfec',  // 进度条背景颜色
            scaleColor: false,
            lineWidth: 0,
            trackWidth: 0,
            lineCap: 'butt',
            animate: 1,
            onStep: function (from, to, percent) {
                this.el.children[0].innerHTML = "停止";
            }
        });
    }


    $("#addDeviceLimit").click(function () {
        $("#addDeviceLimitModal").modal('show');
    });

    function changesAlarm(type) {
        var url = '${ctx}/deviceMgt/changesAlarm.do';
        var sendData = {
            "type": type,
            "deviceId": '${bean.deviceId}'
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.href = '${ctx}/deviceMgt/show.do?deviceId=${bean.deviceId}'
                warningAlert(backData.message[0].msg);
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }




    $("#insideAmplifier").click(function () {
        var insideAmplifier = 0;
        if ($("#insideAmplifier").is(":checked")) {
            insideAmplifier = 1;
        }
        var url = '${ctx}/remoteMgt/setInsideAmplifier.do';
        var sendData = {
            "deviceId": "${bean.deviceId}",
            "insideAmplifier": insideAmplifier,
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {

            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    });

    $("#outerAmplifier").click(function () {
        var outerAmplifier = 0;
        if ($("#outerAmplifier").is(":checked")) {
            outerAmplifier = 1;
        }
        var url = '${ctx}/remoteMgt/setOuterAmplifier.do';
        var sendData = {
            "deviceId": "${bean.deviceId}",
            "outerAmplifier": outerAmplifier,
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {

            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    });

    function saveDeviceLimit() {

        if ($("#humidityMax").val() < 0 || $("#humidityMax").val() > 100) {
            Notify("高湿度警告值范围0至100，例子：95", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        if ($("#humidityMin").val() < 0 || $("#humidityMin").val() > 100) {
            Notify("低湿度警告值范围0至100，例子：10", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        if ($("#temperatureMax").val() < -100 || $("#temperatureMax").val() > 100) {
            Notify("高温度警告值范围-100至100，例子：30", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }

        if ($("#temperatureMin").val() < -100 || $("#temperatureMin").val() > 100) {
            Notify("低温度警告值范围-100至100，例子：-5", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }

        if ($("#opticalInductorMax").val() < 0 || $("#opticalInductorMax").val() > 4000) {
            Notify("高温度警告值范围0至4000，例子：1000", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }

        if ($("#opticalInductorMin").val() < 0 || $("#opticalInductorMin").val() > 4000) {
            Notify("低温度警告值范围0至4000，例子：50", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }

        var url = '${ctx}/deviceMgt/saveDeviceLimit.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            humidityMax: $("#humidityMax").val(),
            humidityMin: $("#humidityMin").val(),
            temperatureMax: $("#temperatureMax").val(),
            temperatureMin: $("#temperatureMin").val(),
            pm25Max: $("#pm25Max").val(),
            pm25Min: $("#pm25Min").val(),
            opticalInductorMax: $("#opticalInductorMax").val(),
            opticalInductorMin: $("#opticalInductorMin").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.href = '${ctx}/deviceMgt/show.do?deviceId=${bean.deviceId}';
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }


    var refresh = 0;  //全局变量
    var t1;
    function startRefreshCount() {
        refresh = 0
        if (t1 != undefined) {
            window.clearTimeout(t1);
        }
        t1 = window.setInterval(refreshCount, 1000);
    }

    function refreshCount() {
        refresh = refresh + 1;
//        console.log("refresh:" + refresh);
        if (refresh > 60) {
//            console.log("ready");
            window.clearTimeout(t1);
            window.location.href = '${ctx}/deviceMgt/show.do?deviceId=${bean.deviceId}';
        }
    }


    $(document).ready(function () {
        startRefreshCount();

        localStorage.setItem("pageTitle", "device_show");
        var dt = localStorage.getItem("pageTitle");
        console.info("localStorage存是device_show")
        console.info(dt)

        document.cookie = "pageTitle=device_show";
        var x = document.cookie;
        console.info("cookie存是device_show")
        console.info(x)


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
            ws.send("{\"type\":\"device\",\"id\":\"${bean.deviceId}\"}");
        };
        //接收到消息
        ws.onmessage = function (evnt) {

            var x = document.cookie;
//            console.info("cookie是device_show")
//            console.info(x)

//            alert(evnt.data);
//            console.info("是:" + evnt.data);

            var obj = JSON.parse(evnt.data);


            var bluePie1 = document.getElementById("bluePie1");
            bluePie1.setAttribute('data-percent', obj.deviceSensor.temperature);
            setEasyPieChart("bluePie1", obj.deviceSensor.temperature);

            var bluePie2 = document.getElementById("bluePie2");
            bluePie2.setAttribute('data-percent', obj.deviceSensor.humidity);
            setEasyPieChart2("bluePie2", obj.deviceSensor.humidity);

            var bluePie3 = document.getElementById("bluePie3");
            bluePie3.setAttribute('data-percent', obj.deviceSensor.pm25 * 100 / 0.5);
            setEasyPieChart6("bluePie3", obj.deviceSensor.pm25);

            var bluePie4 = document.getElementById("bluePie4");
            bluePie4.setAttribute('data-percent', obj.deviceSensor.opticalInductor * 100 / 3612);
            setEasyPieChart5("bluePie4", obj.deviceSensor.opticalInductor);

            var bluePie6 = document.getElementById("bluePie6");
            if (obj.deviceSensor.smokeSensors > 0) {
                bluePie6.setAttribute('data-percent', 100);
                setEasyPieChart4("bluePie6", 100);
            } else {
                bluePie6.setAttribute('data-percent', 100);
                setEasyPieChart3("bluePie6", obj.deviceSensor.smokeSensors);
            }

            var bluePie7 = document.getElementById("bluePie7");
            if (obj.deviceSensor.bodyInductor > 0) {
                bluePie7.setAttribute('data-percent', 100);
                setEasyPieChart4("bluePie7", 100);
            } else {
                bluePie7.setAttribute('data-percent', 100);
                setEasyPieChart3("bluePie7", obj.deviceSensor.smokeSensors);
            }

            //刷新
            if ("${bean.state}" != obj.device.state) {
//                window.location.reload();
            }


        };
        ws.onerror = function (evnt) {
//            console.log(evnt)
        };
        ws.onclose = function (evnt) {
        }

        $("#btn1").click(function () {

            ws.send($("#text").val());
        });
    });

    function sleep(d) {
        for (var t = Date.now(); Date.now() - t <= d;);
    }


</script>

</body>


