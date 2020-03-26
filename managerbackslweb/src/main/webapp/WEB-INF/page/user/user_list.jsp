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

<form action="${ctx }/audioMgt/list.do" id="submit-form">

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
                                            <a href="#" id="addUser" class="btn btn-default"
                                               style="height:34px;">添加用户</a>
                                        </div>
                                    </div>
                                </div>

                            </div>

                        </div>
                        <table class="table table-striped table-bordered table-hover"
                               id="simpledatatable" style="margin-top: 10px">
                            <thead>
                            <tr>
                                <th class="tableTitle" style="width: 300px">账号</th>
                                <th class="tableTitle" style="width: 200px">用户别名</th>
                                <th class="tableTitle" style="width: 200px">电话</th>
                                <th class="tableTitle" style="width: 300px">备注</th>
                                <th class="tableTitle" style="width: 100px">上次登录时间</th>
                                <th class="tableTitle" style="width: 100px">状态</th>
                                <th class="tableTitle">操作</th>

                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${pagination.rows}" var="item">
                                <tr>
                                    <td>${item.userName}</td>
                                    <td>${item.alias}</td>
                                    <td>${item.phone}</td>
                                    <td>${item.note}</td>
                                    <td><h6> <fmt:formatDate
                                            value='${item. landingTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/></h6>   </td>
                                    <td>${item.state}</td>
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
<div id="userModal" class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
     aria-labelledby="myLargeModalLabel" aria-hidden="true" style="display: none;">
    <div class="modal-dialog modal-lg">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h4 class="modal-title">添加用户</h4>
            </div>
            <div class="modal-body">

                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">用户账号</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="userName"
                                   name="userName" value="" placeholder="用户账号"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">用户别名</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="alias"
                                   name="alias" value="" placeholder="用户别名"/>
                        </div>
                    </div>
                </div>
                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">电话号码</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="phone"
                                   name="phone" value="" placeholder="电话号码"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <div class="form-group">
                        <label class="col-sm-2 control-label no-padding-right">备注信息</label>
                        <div class="col-sm-10">
                            <input type="text" class="form-control" id="note"
                                   name="note" value="" placeholder="备注信息"/>
                        </div>
                    </div>
                </div>

                <div class="row">
                    <h5>默认初始密码为123456，请提醒用户登录后自行修改密码。</h5>
                </div>
            </div>
        </div>

        <div class="modal-footer">
            <button type="button" class="btn btn-primary" id="save">提交</button>
            <button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
        </div>

    </div>
</div><!-- /.modal-dialog -->
</div>


</body>


<script type="text/javascript">
    function search() {
        $("#submit-form").submit();
    }

    $("#addUser").click(function () {
        console.log("打开了addTask");
        $("#userModal").modal('show');
    });

    $("#save").click(function () {

        var userName = $("#userName").val();
        var alias = $("#alias").val();
        var phone = $("#phone").val();
        var note = $("#note").val();

        if (userName == "") {
            Notify('用户别名不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if (alias == "") {
            Notify('新密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if (phone == "") {
            Notify('电话号码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }

        $.ajax({
            url: "${ctx}/userMgt/save.do",
            method: "post",
            data: {
                userName: userName,
                alias: alias,
                phone: phone,
                note: note
            },
            success: function (data) {
                if (data.status == 1) {
                    window.location.reload();
                }
                else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            }
        });
    });


    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/userMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        window.location.href = '${ctx}/userMgt/list.do'
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