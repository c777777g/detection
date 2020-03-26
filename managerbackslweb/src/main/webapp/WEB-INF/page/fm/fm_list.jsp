<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html><head>
<meta charset="utf-8"/>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
<title>后台</title>
<script src="${ctx }/static/recorder/HZRecorder.js"></script>
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

<form action="${ctx }/fmMgt/list.do" id="submit-form">

    <div class="page-body">
        <div class="row">
            <div class="col-xs-12 col-md-12">
                <div class="widget">

                    <div class="widget-body">

                        <div class="controls">

                            <div class="row">

                                <div class="col-lg-2 col-xs-2 col-md-2">
                                    <div style="margin-top: 20px;">
                                        <div>
                                            <a href="#" id="addFm" class="btn btn-default"
                                               style="height:34px;">添加FM频道</a>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                        <table class="table table-striped table-bordered table-hover"
                               id="simpledatatable" style="margin-top: 10px">
                            <thead>
                            <tr>
                                <th class="tableTitle" style="width: 200px">名称</th>
                                <th class="tableTitle" style="width: 100px">频道</th>
                                <th class="tableTitle" style="width: 300px">描述</th>
                                <th class="tableTitle" style="width: 50px" >操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pagination.rows}" var="item">

                                <tr>

                                    <td>${item.fmName}</td>
                                    <td>${item.fmChannel}</td>
                                    <td>${item.fmDescribe}</td>

                                    <td data-id="${item.id}">

                                            <%--<a href="${ctx }/audioMgt/edit.do?id=${item.id}" class="btn btn-primary btn-xs edit"><i class="fa fa-edit"></i>编辑</a>--%>
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
<div id="fmModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">FM频道</h4>
            </div>
            <div class="modal-body">
                <div class="row">

                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">名称</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fmName"
                                   name="fmName" value="" placeholder="名称"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">FM频道</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fmChannel"
                                   name="fmChannel" value="" placeholder="FM频道"/>
                        </div>
                    </div>

                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">描述</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="fmDescribe"
                                   name="fmDescribe" value="" placeholder="描述"/>
                        </div>
                    </div>

                    <div class="col-xs-6 text-right">
                        <button  class="btn btn-primary btn-xs edit" onclick="update()">提交</button>
                    </div>
                </div>

            </div>

        </div>
    </div><!-- /.modal-dialog -->
</div>


</body>

<script>


    function isFloat(n) {
        return n === +n && n !== (n | 0);
    }

    function update() {

        var fmName = $("#fmName").val();
        var fmChannel = Number($("#fmChannel").val());
        var fmDescribe = $("#fmDescribe").val();


        if (fmName == '') {
            Notify("上传名称不能为空", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        if (!isFloat(fmChannel)) {
            Notify("上传频道有误", 'bottom-right', '5000', 'green', 'fa-bolt', false);
            return;
        }
        var url = '${ctx}/fmMgt/save.do';
        var sendData = {
            "fmName": fmName,
            "fmChannel": fmChannel,
            "fmDescribe": fmDescribe
        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                window.location.href = '${ctx}/fmMgt/list.do'
                warningAlert(backData.message[0].msg);
            } else {
                warningAlert(backData.message[0].msg);
            }
        });

    }

</script>

<script type="text/javascript">
    function search() {
        $("#submit-form").submit();
    }

    $("#addFm").click(function () {
        console.log("打开了addTask");
        $("#fmModal").modal('show');
    });


    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/audioMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        window.location.href = '${ctx}/audioMgt/list.do'
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