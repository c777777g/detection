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
                    <span class="widget-caption"> 编辑</span>

                </div>
                <div class="widget-body">
                    <h3> 设备号：${bean.deviceId}</h3>
                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                           <h3>基本信息</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">


                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">设备名称</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="deviceName"
                                           name="deviceName" value="${bean.deviceName}" placeholder="设备名称" />
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">设备地址</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="deviceAddress"
                                           name="deviceAddress" value="${bean.deviceAddress}" placeholder="设备地址" />
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">地区</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="district"
                                           name="district" value="${bean.district}" placeholder="所属片区" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-4 col-md-4">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <a class="btn btn-palegreen" onclick="setBasicInformation()" id="setBasicInformation">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="wide"/>

                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                            <h3>网络配置</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">

                            <div class="loginbox-textbox">
                                <div class="radio" id="ipSetStatic">
                                    <label>
                                        <input name="ipSetStatic" value="1" type="radio" <c:if test="${bean.ipSetStatic==1}"> checked="checked" </c:if>>
                                        <span class="text">静态IP </span>
                                    </label>
                                    <label>
                                        <input name="ipSetStatic" value="2" type="radio" <c:if test="${bean.ipSetStatic!=1}"> checked="checked" </c:if> class="inverted">
                                        <span class="text">动态IP</span>
                                    </label>
                                </div>
                            </div>
                            <hr class="wide"/>
                           <h5 style="top: 20px">静态IP配置</h5>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">静态IP地址</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="staticIp"
                                           name="deviceId" value="${bean.staticIp}" placeholder="静态IP地址" />
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">子网掩码</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="staticSubnetMask"
                                           name="deviceId" value="${bean.staticSubnetMask}" placeholder="子网掩码" />
                                </div>
                            </div>

                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">网关</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="staticGateway"
                                           name="deviceId" value="${bean.staticGateway}" placeholder="网关" />
                                </div>
                            </div>

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
                            <h3>WIFI</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">SSID</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="ssid"
                                           name="deviceId" value="${bean.ssid}" placeholder="SSID" />
                                </div>
                            </div>
                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">WIFI密码</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="wifiPassword"
                                           name="deviceId" value="${bean.wifiPassword}" placeholder="WIFI密码" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-4 col-md-4">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <a class="btn btn-palegreen" onclick="setWifi()" id="setWifi">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="wide"/>

                    <div class="row">
                        <div class="col-xs-2 col-md-2">
                            <h3>蓝牙</h3>
                        </div>
                        <div class="col-xs-6 col-md-6">
                            <div class="form-group shangpin">
                                <label class="col-sm-2 control-label no-padding-right">蓝牙ID</label>
                                <div class="col-sm-10">
                                    <input type="text" class="form-control" id="bluetoothId"
                                           name="deviceId" value="${bean.bluetoothId}" placeholder="蓝牙ID" />
                                </div>
                            </div>
                        </div>

                        <div class="col-xs-4 col-md-4">
                            <div class="form-group">
                                <div class="col-sm-offset-2 col-sm-10">
                                    <a class="btn btn-palegreen" onclick="setBasicInformation()" id="setBluetooth">确定</a>
                                </div>
                            </div>
                        </div>
                    </div>

                    <hr class="wide"/>

                    <div class="col-xs-4 col-md-4">
                        <div class="form-group">
                            <div class="col-sm-offset-2 col-sm-10">
                                <a class="btn btn-darkorange" onclick="del('${bean.id}')" >删除设备</a>
                            </div>
                        </div>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>

<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true"
     style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">

            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal"
                        aria-hidden="true">×
                </button>
                <h4 class="modal-title" id="myLargeModalLabel">选择分类</h4>
            </div>
            <div class="modal-body">
                <div class="widget-body">
                    <div id="MyTree" class="tree tree-plus-minus">
                        <div class="tree-folder" style="display: none;">
                            <div class="tree-folder-header">
                                <i class="fa fa-folder"></i>
                                <div class="tree-folder-name"></div>
                            </div>
                            <div class="tree-folder-content"></div>
                            <div class="tree-loader" style="display: none;"></div>
                        </div>
                        <div class="tree-item" style="display: none;">
                            <i class="tree-dot"></i>
                            <div class="tree-item-name"></div>
                        </div>
                    </div>
                    <a class="btn btn-palegreen" data-dismiss="modal"
                       style="margin-left: 90%;" id="saveType">确定</a>
                </div>
            </div>
        </div>
        <!-- /.modal-content -->
    </div>
    <!-- /.modal-dialog -->
</div>
</body>
<script src="${ctx }/static/assets/js/fuelux/treeview/tree-custom.min.js"></script>
<script src="${ctx }/static/assets/js/fuelux/treeview/treeview-init.js"></script>

<script>

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
    function setBasicInformation(){
        var  url = '${ctx}/deviceMgt/setBasicInformation.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            deviceName:  $("#deviceName").val(),
            deviceAddress:  $("#deviceAddress").val(),
            district: $("#district").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    };

    //设置ip
    function setIp(){
        var  url = '${ctx}/deviceMgt/setIp.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            ipSetStatic:  $("input[name='ipSetStatic']:checked").val(),
            staticIp:  $("#staticIp").val(),
            staticGateway:  $("#staticGateway").val(),
            staticSubnetMask: $("#staticSubnetMask").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    };

    //设置WIFI
    function setWifi(){
        var  url = '${ctx}/deviceMgt/setWifi.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            wifiPassword:  $("#wifiPassword").val(),
            ssid:  $("#ssid").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    };

    //设置蓝牙
    function setBluetooth(){
        var  url = '${ctx}/deviceMgt/setBluetooth.do';
        var sendData = {
            deviceId: '${bean.deviceId}',
            bluetoothId:  $("#bluetoothId").val(),
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
            }
        });
    };

    $("#checkbox").change(function () {
        if ($("#checkbox").is(':checked')) {
            $(".shangpin").hide();
            $("#attrDiv").show();

        } else {
            $(".shangpin").show();
            $("#attrDiv").hide();
        }
    });

    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/deviceMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {

                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });

    }


</script>
</html>
