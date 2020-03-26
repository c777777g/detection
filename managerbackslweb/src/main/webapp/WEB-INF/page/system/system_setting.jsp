<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>

<head>
    <meta charset="utf-8"/>
    <title>Data Tables</title>
    <link href="${ctx}/static/assets/css/laydate.css" rel="stylesheet">
    <link href="${ctx}/static/assets/css/skin/molvlaydate.css" rel="stylesheet">
    <link href="${ctx }/static/assets/css/fileinput.min.css" rel="stylesheet"/>
    <script src="${ctx }/static/assets/js/datetime/laydate.js"></script>
    <script src="${ctx }/static/assets/js/upload/fileinput.min.js"></script>
    <script src="${ctx }/static/assets/js/upload/zh.js"></script>


    <style>
        input[type="checkbox"].checkbox-slider + .text::before {
            content: '' !important;
        }
    </style>
</head>
<body>
<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">
                <div class="widget-header ">
                    <span class="widget-caption">  <a class="btn btn-info"  onclick="reload()" >刷新</a></span>


                </div>
                <div class="widget-body">


                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                            <h3>网络配置</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">IP地址</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="address"
                                           name="address" value="${address}" placeholder="静态IP地址"/>
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">子网掩码</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="netmask"
                                           name="netmask" value="${netmask}" placeholder="子网掩码"/>
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">网关</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="gateway"
                                           name="gateway" value="${gateway}" placeholder="网关"/>
                                </div>
                            </div>
                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">DNS</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="dns"
                                           name="dns" value="${dns}" placeholder="DNS"/>
                                </div>
                            </div>
                            如修改网络配置信息错误会导致服务器无法连接网络（如出现该问题请进入系统更改配置信息），确定提交后会重启系统,请内心等待！！！

                        </div>

                        <div class="col-xs-4 col-md-4">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <a class="btn btn-palegreen" onclick="setIp()" id="setIp">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="wide"/>

                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                            <h3>CPU信息</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            ${cpu}
                        </div>
                    </div>

                    <hr class="wide"/>
                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                            <h3>内存信息</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            ${memory}
                        </div>
                    </div>

                    <hr class="wide"/>

                </div>

            </div>
        </div>
    </div>
</div>
</div>


</body>
<script src="${ctx }/static/assets/js/fuelux/treeview/tree-custom.min.js"></script>
<script src="${ctx }/static/assets/js/fuelux/treeview/treeview-init.js"></script>

<script>

    function reload()
    {
        location.reload();
    }

    Date.prototype.format = function (fmt) {
        var o = {
            "M+": this.getMonth() + 1,                 //月份
            "d+": this.getDate(),                    //日
            "h+": this.getHours(),                   //小时
            "m+": this.getMinutes(),                 //分
            "s+": this.getSeconds(),                 //秒
            "q+": Math.floor((this.getMonth() + 3) / 3), //季度
            "S": this.getMilliseconds()             //毫秒
        };
        if (/(y+)/.test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
        }
        for (var k in o) {
            if (new RegExp("(" + k + ")").test(fmt)) {
                fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
            }
        }
        return fmt;
    }

    //基本信息
    function setBasicInformation() {
        var url = '${ctx}/deviceMgt/setBasicInformation.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            deviceName: $("#deviceName").val(),
            deviceAddress: $("#deviceAddress").val(),
            district: $("#district").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    }

    //设置ip
    function setIp() {
        var url = '${ctx}/systemMgt/changeIP.do';
        var sendData = {
            address: $("#address").val(),
            netmask: $("#netmask").val(),
            gateway: $("#gateway").val(),
            dns: $("#dns").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    }
    ;

    //设置WIFI
    function setWifi() {
        var url = '${ctx}/deviceMgt/setWifi.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            wifiPassword: $("#wifiPassword").val(),
            ssid: $("#ssid").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    }
    ;

    //设置蓝牙
    function setBluetooth() {
        var url = '${ctx}/deviceMgt/setBluetooth.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            bluetoothId: $("#bluetoothId").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    }
    ;

    $("#checkbox").change(function () {
        if ($("#checkbox").is(':checked')) {
            $(".shangpin").hide();
            $("#attrDiv").show();

        } else {
            $(".shangpin").show();
            $("#attrDiv").hide();
        }
    });


</script>
</html>
