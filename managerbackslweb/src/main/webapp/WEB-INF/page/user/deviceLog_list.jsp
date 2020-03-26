<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@include file="../commons/commons.jsp" %>
<!DOCTYPE html>
<head>
    <meta charset="utf-8"/>
    <title>后台</title>
</head>

<body>

<form action="${ctx }/userMgt/log.do" id="submit-form">
    <div class="controls">
        <div class="input-group">

        </div>
    </div>
    <hr class="wide">
    <div class="table-scrollable"><!--横导航条-->
        <table class="table table-striped table-bordered table-hover"
               id="simpledatatable" style="margin-top: 10px">
            <thead>
            <tr>
                <th>设备id</th>
                <th>设备名</th>
                <th>相关用户</th>
                <th>消息</th>
                <th>时间</th>

            </tr>
            </thead>
            <tbody>
            <c:forEach items="${pagination.rows}" var="item">

                <tr>
                    <td>${item.deviceId}</td>
                    <td>${item.deviceName}</td>
                    <td>${item.userName}</td>
                    <td>${item.message}</td>
                    <td><fmt:formatDate value='${item.createTime}' type='date' pattern='yyyy-MM-dd HH:mm:ss'/></td>

                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>
    <%@include file="../commons/page.jsp" %>

</form>

</body>
<script type="text/javascript">
    function search() {
        $("#submit-form").submit();
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
</script>
</html>