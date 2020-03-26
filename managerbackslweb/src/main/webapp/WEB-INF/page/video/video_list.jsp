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


<form action="${ctx }/videoMgt/list.do" id="submit-form">

    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">

                    <div class="widget-body">

                        <div class="controls">

                            <div class="row">
                                <%--<div class="col-lg-2 col-xs-2 col-md-2">--%>
                                <%--<div style="margin-top: 20px;">--%>
                                <%--<div>--%>
                                <%--<a href="#" class="btn btn-default"--%>
                                <%--onClick="test()"--%>
                                <%--style="height:34px;">test</a>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <%--</div>--%>
                                <div class="col-lg-2 col-xs-2 col-md-2">
                                    <div style="margin-top: 20px;">
                                        <div id="addVideo" class="btn btn-blue">添加监控</div>
                                    </div>
                                </div>

                                <div class="col-lg-2 col-xs-2 col-md-2">
                                    <div style="margin-top: 20px;">
                                        <a href='spotPlayer:${showVideo}' id="showVideo" class="btn btn-blue">查看组监控</a>
                                        </div>
                                </div>

                            </div>

                        </div>
                        <table class="table table-striped table-bordered table-hover"
                               id="simpledatatable" style="margin-top: 10px">
                            <thead>
                            <tr>
                                <th class="tableTitle" style="width: 200px">监控名</th>
                                <th class="tableTitle" style="width: 600px">流地址</th>

                                <%--<th class="tableTitle" style="width: 100px">状态</th>--%>
                                <th class="tableTitle" style="width: 100px">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pagination.rows}" var="item">

                                <tr>
                                    <td>${item.videoName}</td>
                                    <td>${item.videoUrl}</td>
                                        <%--<td>--%>
                                        <%--<c:if test="${item.state==1}"> <i class="fa  fa-check"--%>
                                        <%--style="color: rgb(95,212,62);"></i><span--%>
                                        <%--style="color: #0bff1c">录制中</span></c:if>--%>
                                        <%--<c:if test="${item.state==2}"> <i class="fa  fa-times"--%>
                                        <%--style="color: rgb(26, 69, 255);"></i><span--%>
                                        <%--style="color: #1b4ef7">没录制</span></c:if>--%>
                                        <%--<c:if test="${item.state==3}"> <i class="fa  fa-times"--%>
                                        <%--style="color: rgb(255,0,0);"></i><span--%>
                                        <%--style="color: #f72019">录制失败</span></c:if>--%>

                                        <%--</td>--%>

                                    <td data-id="${item.id}">
                                            <%--<a href="${ctx }/videoStoreMgt/list.do?videoName=${item.videoName}"--%>
                                            <%--class="btn btn-primary btn-xs edit"><i class="fa fa-briefcase"></i>管理录像</a>--%>
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
<div id="addVideoModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">添加绑定监控</h4>
            </div>
            <div class="modal-body">
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">监控名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="videoName"
                                   name="videoName" value="" placeholder="名称"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">媒体流地址</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="videoUrl"
                                   name="videoUrl" value="" placeholder="媒体流地址"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">描述</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="videoDescribe"
                                   name="videoDescribe" value="" placeholder="描述"/>
                        </div>
                    </div>

                    <div class="col-xs-6 text-right">
                        <button class="btn btn-primary btn-xs edit" onclick="saveVideo()">提交</button>
                    </div>
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


    $("#addVideo").click(function () {
        console.log("打开了addVideo");
        $("#addVideoModal").modal('show');
    });

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
        var url = '${ctx}/videoMgt/saveVideo.do';
        var sendData = {
            "videoName": videoName,
            "videoUrl": videoUrl,
            "videoDescribe": videoDescribe,
            "deviceId": "${bean.deviceId}",
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
                warningAlert(backData.message[0].msg);
            } else {
                warningAlert(backData.message[0].msg);
            }
        });

    }


    function test() {

        var url = '${ctx }' + "/videoMgt/test.do";
        var sendData = {};
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.reload();
            } else {
                warningAlert(backData.message[0].msg);
            }
        });

    }

    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/videoMgt/delVideo.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        window.location.reload();
                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });
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