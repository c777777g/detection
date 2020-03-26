<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html><head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>后台</title>
</head>

<style>
    .tableTitle {
        text-align: center;
    }

    .table > tbody > tr > td {
        vertical-align: middle;
        text-align: center;
    }

</style>
<body>

<form action="${ctx }/videoStoreMgt/list.do" id="submit-form">

    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">

                    <div class="widget-body">

                        <div class="controls">

                            <div class="row">
                                <div class="col-lg-6 col-xs-6 col-md-6">
                                    <h3><div class="btn btn-blue" onclick="refresh()">刷新</div> ${video.videoName}</h3>
                                    <h4>${video.videoUrl}</h4>
                                    <h6>${video.videoDescribe}</h6>
                                </div>

                                <div class="col-lg-2 col-xs-2 col-md-2">

                                    <c:if test="${video.state=='2'}">
                                        <h3>当前状态：没有录像</h3>
                                        <div style="margin-top: 20px;">
                                            <div id="startVideo" class="btn btn-blue" onclick="startVideo('${video.id}')">开始录像</div>
                                        </div>
                                    </c:if>
                                    <c:if test="${video.state=='1'}">
                                        <h3>当前状态：录像中</h3>
                                        <div style="margin-top: 20px;">
                                            <div style="margin-top: 20px;">
                                                <div id="stopVideo" class="btn btn-danger" onclick="stopVideo('${video.id}')">停止录像</div>
                                            </div>
                                        </div>
                                    </c:if>
                                    <c:if test="${video.state=='3'}">
                                        <h3>当前状态：录像失败，请检查媒体流是否可用</h3>
                                        <div style="margin-top: 20px;">
                                            <div style="margin-top: 20px;">
                                                <div  class="btn btn-danger" onclick="startVideo('${video.id}')">开始录像</div>
                                            </div>
                                        </div>
                                    </c:if>


                                </div>

                                <a href="#" onClick="delVideo('${video.id}')"
                                   class="btn btn-danger btn-xs delete"
                                   style="height:34px ;float:right; ">删除该监控</a>

                            </div>

                        </div>
                        <table class="table table-striped table-bordered table-hover"
                               id="simpledatatable" style="margin-top: 10px">
                            <thead>
                            <tr>
                                <th class="tableTitle" style="width: 200px">监控名</th>
                                <th class="tableTitle" style="width: 200px">在线播放</th>
                                <th class="tableTitle" style="width: 200px">开始时间</th>
                                <th class="tableTitle" style="width: 200px">结束时间</th>
                                <th class="tableTitle" style="width: 100px">状态</th>
                                <th class="tableTitle">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pagination.rows}" var="item">

                                <tr>
                                    <td> ${item.videoName}</td>
                                    <td>
                                        <a href="#" class="btn  btn-blue btn-xs " onClick="look('${item.downloadLink}')"><i
                                                class="fa  fa-film"></i>在线查看</a>
                                            <%--<video src="${item.downloadLink}" controls preload="none" width="400" height="300"></video>--%>
                                            <%--${item.videoUrl}--%>
                                    </td>
                                    <td><fmt:formatDate value='${item.startTime}' type='date'
                                                        pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                    <td><fmt:formatDate value='${item.stopTime}' type='date'
                                                        pattern='yyyy-MM-dd HH:mm:ss'/></td>
                                    <c:if test="${item.state=='1'}">
                                        <td style="color: #0bff1c"> 录像完成</td>
                                    </c:if>
                                    <c:if test="${item.state=='2'}">
                                        <td style="color: #f72019">录像失败</td>
                                    </c:if>
                                    <c:if test="${item.state=='3'}">
                                        <td style="color: #042df7">未完成</td>
                                    </c:if>
                                    <td data-id="${item.id}">
                                            <%--<a href="${ctx }/audioMgt/edit.do?id=${item.id}" class="btn btn-primary btn-xs edit"><i class="fa fa-edit"></i>编辑</a>--%>

                                        <a href="#" class="btn  btn-blue btn-xs " onClick="downloadData('${item.downloadLink}')"><i
                                                class="fa  fa-download"></i>下载</a>
                                        <a href="#" class="btn btn-danger btn-xs delete" onClick="del('${item.id}')"><i
                                                class="fa fa-trash-o"></i>删除</a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                        <%@include file="../commons/page.jsp" %>
                    </div>
                </div>
            </div>
        </div>
    </div>
</form>

<%--显示组设备模块--%>
<div id="videoModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">查看录像</h4>
                <h6 class="modal-title">浏览器需要加载视频，请耐心等待</h6>
            </div>
            <div class="modal-body">
                <div class="row">
                    <video id="lookv" src="https://vemwxapp.gdwave.com:443/fulide/advertMgt/download.do?fileName=bfa578a12bee06d2013280891890a856.mp4" controls preload="none" width="800"
                           height="600"></video>
                </div>
            </div>

        </div>
    </div>
</div>
<!-- /.modal-dialog -->

</body>


<script type="text/javascript">
    function search() {
        $("#submit-form").submit();
    }


    function look(url) {
        console.log("打开了addVideo");
        document.getElementById("lookv").src = url;
        $("#videoModal").modal('show');
    }


    function saveVideo() {

        var videoName = $("#videoName").val();
        var videoUrl = $("#videoUrl").val();
        var videoDescribe = $("#videoDescribe").val();


        if (videoName == '') {
            Notify("上传名称不能为空", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        if (videoUrl == '') {
            Notify("上传媒体流地址不能为空", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        var url = '${ctx}/deviceMgt/saveVideo.do';
        var sendData = {
            "videoName": videoName,
            "videoUrl": videoUrl,
            "videoDescribe": videoDescribe,
            "deviceId": "${bean.deviceId}",
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

    function startVideo( id) {

        var url = '${ctx }' + "/videoStoreMgt/startVideo.do";
        var sendData = {
            "id": id,
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }

    function stopVideo( id) {

        var url = '${ctx }' + "/videoStoreMgt/stopVideo.do";
        var sendData = {
            "id": id,
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }

    function downloadData(url) {
        window.open(url);
    }


    function test() {

        var url = '${ctx }' + "/videoStoreMgt/test.do";
        var sendData = {};
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {

            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }

    function delVideo(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/videoMgt/delVideo.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        window.location.href = '${ctx}/videoMgt/list.do'
                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });
    }

    function refresh() {
        window.location.reload();
    }

    $("#simpledatatable").on('click', '.btn-warning', function () {
        var _this = $(this);
        $.ajax({
            url: '${ctx}/homeAdsMgt/editDo.do',
            data: {id: _this.parents('td').attr('data-id'), ifDisable: true},
            success: function (res) {
                if (res.status == 1) {
                    _this.parents("tr").find("span").removeClass("label-success");
                    _this.parents("tr").find("span").addClass("label-warning");
                    _this.parents("tr").find("span").text("已禁用")
                    _this.html("<i class='fa fa-check-circle'></i>启用");
                    _this.removeClass("btn-warning");
                    _this.addClass("btn-success");
                    Notify('修改成功！', 'top-right', '5000', 'success', 'fa-check', true);
                } else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            }
        });

    });
    $("#simpledatatable").on('click', '.btn-success', function () {
        var _this = $(this);
        $.ajax({
            url: '${ctx}/homeAdsMgt/editDo.do',
            data: {id: _this.parents('td').attr('data-id'), ifDisable: false},
            success: function (res) {
                if (res.status == 1) {
                    _this.parents("tr").find("span").removeClass("label-warning");
                    _this.parents("tr").find("span").addClass("label-success");
                    _this.parents("tr").find("span").text("已启用")
                    _this.html("<i class='fa fa-minus-circle'></i>禁用");
                    _this.removeClass("btn-success");
                    _this.addClass("btn-warning");
                    Notify('修改成功！', 'top-right', '5000', 'success', 'fa-check', true);
                } else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            }
        });

    });
</script>
</html>