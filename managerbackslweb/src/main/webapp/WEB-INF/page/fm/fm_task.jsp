<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html><head>
<meta charset="utf-8"/>
<title>后台</title>

<link rel="stylesheet" type="text/css" href="${ctx }/static/time/datedropper.css">
<link rel="stylesheet" type="text/css" href="${ctx }/static/time/timedropper.min.css">

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

<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">

                <div class="widget-body">
                    <table class="table table-striped table-bordered table-hover"
                           id="simpledatatable" style="margin-top: 10px">
                        <thead>
                        <tr>
                            <th class="tableTitle" style="width: 300px">名称</th>
                            <th class="tableTitle" style="width: 300px">时间</th>
                            <th class="tableTitle" style="width: 300px">日期</th>
                            <th class="tableTitle" style="width: 200px">FM频道</th>
                            <th class="tableTitle" style="width: 200px">群组/设备</th>
                            <th class="tableTitle" style="width: 200px">状态</th>
                            <th class="tableTitle" style="width: 100px">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <c:forEach items="${pagination.rows}" var="item">

                            <tr>
                                <td>${item.fmTaskName}</td>
                                <td>${item.playTime}</td>
                                <td><c:if test="${item.playDate=='1'}"> 立刻播</c:if><c:if
                                        test="${item.playDate=='2'}"> 每天</c:if></td>
                                <td> ${item.fmName} </td>
                                <td>${item.groupName}${item.deviceId}</td>
                                <td><c:if test="${item.state=='1'}"> 启用</c:if><c:if
                                        test="${item.state=='2'}"> 禁用</c:if></td>
                                <td>
                                    <c:if test="${item.state=='2'}"><a href="#"  class="btn btn-primary btn-xs edit" onClick="start('${item.id}')">启动</a></c:if>
                                    <c:if test="${item.state=='1'}"><a href="#" class="btn btn-primary btn-xs edit" onClick="stop('${item.id}')">禁用</a></c:if>
                                    <a href="#" class="btn btn-danger btn-xs delete" onClick="del('${item.id}')"><i class="fa fa-trash-o"></i>删除</a>
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


<div class="page-body">
    <div class="row">
        <div class="col-xs-12 col-md-12">
            <div class="widget">

                <div class="widget-body">
                    <h2>群组设置</h2>

                    <div class="row">
                        <c:forEach items="${groupUserList}" var="item">
                            <div class="col-lg-2 col-sm-4 col-xs-6">
                                <div class="databox databox-lg databox-vertical databox-inverted bg-white databox-shadowed">

                                    <div class="databox-bottom no-padding text-align-center">
                                        <span class="databox-number lightcarbon no-margin">${item.groupName}</span>
                                        <span class="databox-title lightcarbon no-margin">设备数：${item.amount}</span>
                                        <span class="databox-title lightcarbon no-margin"><h6>描述：${item.groupDescribe}</h6></span>
                            <span class="databox-title lightcarbon no-margin"><h6> 创建时间：<fmt:formatDate
                                    value='${item.createdDate}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/></h6></span>
                                        <a href="#" class="btn btn-primary btn-xs edit"
                                           onClick="addTask('${item.groupName}')">设置FM频道</a>

                                    </div>

                                </div>

                            </div>
                        </c:forEach>
                    </div>

                </div>
            </div>
        </div>
    </div>
</div>


<%--添加模块--%>
<div id="addModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">添加FM音频任务</h4>
            </div>
            <div class="modal-body">
                <div id="horizontal-form">
                    <form id="saveOrUpdate" class="form-horizontal" role="form" enctype="multipart/form-data"
                          method="post">

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">群组</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="groupName" disabled="disabled">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">名称</label>
                            <div class="col-sm-10">
                                <input type="text" class="form-control" id="fmTaskName" placeholder="输入该任务名称">
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">开始时间</label>
                            <div class="col-sm-10">
                               <input type="text" class="playTime" id="playTime" />
                                <%--<input type="time" class="form-control" id="playTime" placeholder="输入时间">--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">结束时间</label>
                            <div class="col-sm-10">
                                <input type="text" class="closeTime" id="closeTime" />
                                <%--<input type="time" class="form-control" id="playTime" placeholder="输入时间">--%>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">模式</label>
                            <div class="col-sm-10">
                                <select id="playDate" name="playDate" style="width:100%;">
                                    <option value="1">立刻播</option>
                                    <option value="2">每天</option>
                                    <%--<option value="3">星期一至星期五</option>--%>
                                </select>
                            </div>
                        </div>

                        <div class="form-group">
                            <label class="col-sm-2 control-label no-padding-right">FM频道</label>
                            <div class="col-sm-10">
                                <select id="fmName" name="fmName" style="width:100%;">
                                    <c:forEach items="${fmList}" var="item">
                                        <option value="${item.fmName}"/>
                                        ${item.fmName}
                                    </c:forEach>
                                </select>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-primary" id="save">提交</button>
                <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
            </div>
        </div>
    </div><!-- /.modal-dialog -->
</div>


<%--<script type="text/javascript" src="${ctx }/static/time/jquery-1.12.3.min.js"></script>--%>
<%--<script src="${ctx }/static/time/datedropper.min.js"></script>--%>
<script src="${ctx }/static/time/timedropper.min.js"></script>
<script src="${ctx }/static/assets/js/select2/select2.js"></script>
</body>


<script type="text/javascript">

    //--Jquery Select2--
    $("#fmName").select2();

    function lookimg(str) {
        var newwin = window.open()
        myimg = newwin.document.createElement("img")
        myimg.src = str
        newwin.document.body.appendChild(myimg)
    }

    function search() {
        $("#submit-form").submit();
    }

    $("#playTime").timeDropper({
        meridians: false,
        format: 'HH:mm',
    });

    $("#closeTime").timeDropper({
        meridians: false,
        format: 'HH:mm',
    });

    function addTask(groupName) {
        console.log("打开了addTask");
        $("#groupName").val(groupName);
        $("#templateDescribe").val("");
        $("#addModal").modal('show');
    }

    //保存
    $("#save").on('click', function () {
        url = '${ctx}/fmTaskMgt/saveTask.do';
        var groupName = $("#groupName").val();
        var fmName = $("#fmName").val();
        var playTime = $("#playTime").val();
        var closeTime = $("#playTime").val();
        var playDate = $("#playDate").val();
        var fmTaskName = $("#fmTaskName").val();

        console.log("groupName=" + groupName);
        console.log("fmName=" + fmName);
        console.log("playTime=" + playTime);
        console.log("closeTime=" + closeTime);
        console.log("playDate=" + playDate);
        console.log("fmTaskName=" + fmTaskName);


        $("#saveOrUpdate").ajaxSubmit({
            url: url,
            data: {
                "groupName": groupName,
                "playTime": playTime,
                "fmTaskName": fmTaskName,
                "closeTime":closeTime
            },
            success: function (res) {
                if (res.status == 1) {
                    window.location.href = '${ctx}/fmTaskMgt/list.do'
                } else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            },
            error: function (res) {
                console.log(res);
            }
        });
    });

    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx}' + "/fmTaskMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        window.location.href = '${ctx}/fmTaskMgt/list.do'
                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });
    }

    function start(id) {
        var url = '${ctx}' + "/fmTaskMgt/start.do";
        var sendData = {
            "id": id
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.href = '${ctx}/fmTaskMgt/list.do'
                warningAlert(backData.message[0].msg);
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }
    function stop(id) {
        var url = '${ctx}' + "/fmTaskMgt/stop.do";
        var sendData = {
            "id": id
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.href = '${ctx}/fmTaskMgt/list.do'
                warningAlert(backData.message[0].msg);
            } else {
                warningAlert(backData.message[0].msg);
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
    function isnumber(obj) {
        if (isNaN(obj.value)) {
            obj.value = "";
            return false;
        }
        return true;
    }
</script>
</html>