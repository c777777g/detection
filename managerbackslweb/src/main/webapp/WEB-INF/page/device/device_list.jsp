<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <META HTTP-EQUIV="pragma" CONTENT="no-cache">
    <META HTTP-EQUIV="Cache-Control" CONTENT="no-cache, must-revalidate">
    <META HTTP-EQUIV="expires" CONTENT="0">
    <title>后台</title>
</head>
<body>
<div style="padding-left: 5px;padding-right: 10px;padding-top: 5px" id="add">
    <form action="${ctx }/deviceMgt/list.do" id="submit-form">
        <%--<c:if test="${ USER.role eq 'user'}">--%>
        <%--<div class="controls">--%>
        <%--<div class="row">--%>

        <%--<div class="col-lg-2 col-xs-2 col-md-2">--%>
        <%--<div>--%>
        <%--<select style="width:100%;" id="selectType" name="selectType">--%>
        <%--<option value="1" <c:if test="${ACRTYPE=='1'}"> selected="selected"</c:if>  >设备id查询--%>
        <%--</option>--%>
        <%--<option value="2" <c:if test="${ACRTYPE=='2'}"> selected="selected"</c:if>  >设备名查询--%>
        <%--</option>--%>
        <%--<option value="3" <c:if test="${ACRTYPE=='3'}"> selected="selected"</c:if> >状态查询--%>
        <%--</option>--%>
        <%--</select>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-lg-2 col-xs-2 col-md-2">--%>
        <%--<div class="collapse in">--%>
        <%--<div class="form-group">--%>
        <%--<span class="input-icon">--%>
        <%--<input type="text" class="form-control input-sm" id="selectKey" name="selectKey"--%>
        <%--value="${ACRKEY}">--%>
        <%--<i class="glyphicon glyphicon-search blue"></i>--%>
        <%--</span>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<div class="col-lg-1 col-xs-1 col-md-1">--%>
        <%--<div style="margin-top: 0px;">--%>
        <%--<div>--%>
        <%--<a href="#" class="btn btn-default" id="query">查询</a>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--<span>维护人员：<c:forEach items="${linkmanList}" var="lman">${lman}、</c:forEach></span>--%>
        <%--</div>--%>
        <%--</div>--%>
        <%--</c:if>--%>
        <a href="javascript:void(0);"
           class="btn btn-azure shiny" onclick="explore()"><i
                class="fa fa-crosshairs"></i>探索设备</a>
        <hr class="wide">
        <div class="table-scrollable">
            <table class="table table-striped table-bordered table-hover"
                   id="simpledatatable" style="margin-top: 10px">
                <thead>
                <tr>
                    <th>设备id</th>
                    <th>设备名</th>
                    <th>设备所在地址</th>
                    <th>状态</th>
                    <th>操作</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${pagination.rows}" var="item">
                    <tr>
                        <td>${item.deviceId}</td>
                        <td>${item.deviceName}</td>
                        <td>${item.deviceAddress}</td>
                        <c:if test="${item.state=='1'}">
                            <td style="color: #0bff1c"> 在线</td>
                        </c:if>
                        <c:if test="${item.state=='2'}">
                            <td style="color: #f72019">离线</td>
                        </c:if>

                        <td style="text-align: left;">
                            <a href="${ctx }/deviceMgt/show.do?deviceId=${item.deviceId}"
                               class="btn btn-primary btn-xs edit"><i
                                    class="fa fa-edit"></i>详情</a>
                            <c:if test="${ USER.role eq 'user'}">
                                <a href="#" class="btn btn-danger btn-xs delete" onclick="del('${item.id}')"><i
                                        class="fa fa-trash-o"></i>删除</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
        <%@include file="../commons/page.jsp" %>
    </form>
</div>
</body>
<script type="text/javascript">
    function explore() {
        var url = '${ctx }' + "/deviceMgt/explore.do";
        var sendData = {

        };
        $.post(url, sendData, function (backData) {
            if (backData.status == 1) {
                warningAlert("已经触发探索，请耐心等待已经安装好的设备连接服务器。");
            } else {
                warningAlert(backData.message[0].msg);
            }
        });
    }


    function del(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/deviceMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        $("#submit-form").submit();
                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });
    }

    function deviceList(id) {
        bootbox.confirm("你确定删除吗?", function (result) {
            if (result) {
                var url = '${ctx }' + "/deviceMgt/del.do";
                var sendData = {
                    "id": id
                };
                $.post(url, sendData, function (backData) {
                    if (backData.status == 1) {
                        $("#submit-form").submit();
                    } else {
                        warningAlert(backData.message[0].msg);
                    }
                });
            }
        });
    }

    $("#query").click(function () {
        var selectType = $('#selectType').val();
        var selectKey = $('#selectKey').val();
        var institutions = '${institutions}';
        $.ajax({
            url: "${ctx}/deviceMgt/list.do",
            method: "get",
            data: {
                institutions: institutions,
                selectType: selectType,
                selectKey: selectKey
            },
            success: function (res) {
                $('#add').html(res);
            }
        });
    });
</script>
</html>