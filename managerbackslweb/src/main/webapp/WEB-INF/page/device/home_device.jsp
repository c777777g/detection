<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>
<!DOCTYPE html>
<head>
    <meta charset="utf-8" />
    <title>后台</title>
    <script type="text/javascript">
       function pageTo(iClass,title,url){
           if(url!=null && url!=''){
               $("#imgI").attr("class",iClass);
               $("#titleH1").html(title);
               $("#iframepage").attr("src",url);
           }else{
               warningAlert("功能完善中...");
           }
       }
    </script>

</head>

<body>
<div class="page-body">
    <div class="mail-container">
        <div class="mail-sidebar" style="width: 225px;">
            <div class="show page-sidebar">
                <ul class="nav sidebar-menu">
                    <!--机构列表-->
                    <li class="open">
                        <a>
                            <span class="menu-text">
                                <h5>片区列表</h5>
                            </span>
                        </a>
                        <ul class="submenu">
                            <c:forEach items="${menus}" var="menu">
                                <li data-id="2"> <!--一级-->
                                    <a href="javascript:pageTo('','${menu.name }','${ctx}/deviceMgt/list.do?institutions=${menu.name}')">
                                        <span class="menu-text">${menu.name }</span>
                                    </a>
                                    <c:if test="${USER.role eq 'user'}">
                                        <a href="${ctx}/linkmanMgt/listCheckLinkman.do?institutionsId=${menu.institutionsId}" style="width: 56px;height: 0px;">
                                            <i class="fa fa-user" style="margin-top: -24px;margin-left: -34px;"></i></a>
                                    </c:if>
                                    <a class="menu-dropdown" style="width: 56px;height: 0px;"><!--meun-dropdown 控制展开动画-->
                                        <i class="menu-expand"></i>
                                    </a>
                                    <ul class="submenu">
                                        <c:forEach items="${menu.list}" var="subMenu">
                                            <li data-id="3">
                                                <a href="javascript:pageTo('','${subMenu.name }','${ctx}/deviceMgt/list.do?institutions=${subMenu.name}')">
                                                    <span class="menu-text">${subMenu.name }</span>
                                                </a>
                                                <c:if test="${USER.role eq 'user'}">
                                                    <a href="${ctx}/linkmanMgt/listCheckLinkman.do?institutionsId=${subMenu.institutionsId}" style="width: 56px;height: 0px;">
                                                        <i class="fa fa-user" style="margin-top: -24px;margin-left: -34px;"></i></a>
                                                </c:if>
                                                <a href="#" class="menu-dropdown" style="width: 56px;height: 0px;">
                                                    <i class="menu-expand"></i>
                                                </a>
                                                <ul class="submenu">
                                                    <c:forEach items="${subMenu.list}" var="subsubMenu">
                                                        <li data-id="6">
                                                            <a href="javascript:pageTo('','${subsubMenu.name }','${ctx}/deviceMgt/list.do?institutions=${subsubMenu.name}')">
                                                                <span class="menu-text">${subsubMenu.name }</span>
                                                            </a>
                                                            <c:if test="${ USER.role eq 'user'}">
                                                                <a href="${ctx}/linkmanMgt/listCheckLinkman.do?institutionsId=${subsubMenu.institutionsId}" style="width: 56px;height: 0px;">
                                                                    <i class="fa fa-user" style="margin-top: -24px;margin-left: -34px;"></i></a>
                                                            </c:if>
                                                        </li>
                                                    </c:forEach>
                                                </ul>
                                            </li>
                                        </c:forEach>
                                    </ul>
                                </li>
                            </c:forEach>
                        </ul>
                    </li>
                </ul>
            </div>
        </div>
        <div class="mail-body">
            <div class="well with-header with-footer" style="left: 29px;right: 0px;padding-right: 40px;">
                <div class="header bg-palegreen">
                    设备详情
                </div>
                <div>
                <iframe src="" id="iframepage" scrolling="visible" onload="changeFrameHeight()" frameborder="0" width="100%" height="100%">点击片区查看设备</iframe>
                </div>
            </div>
        </div>

    </div>
</div>

</body>

<!--Basic Scripts-->
<script src="../static/assets/js/jquery-2.0.3.min.js"></script>
<script src="../static/assets/js/bootstrap.min.js"></script>

<!--Beyond Scripts-->
<script src="../static/assets/js/beyond.min.js"></script>

<!--Page Related Scripts-->
<script src="../static/assets/js/nestable/jquery.nestable.min.js"></script>
<script type="text/javascript">
    jQuery(function ($) {
        $('.dd').nestable();
        $('.dd-handle a').on('mousedown', function (e) {
            e.stopPropagation();
        });
    });
</script>

<script type="text/javascript">
    function search() {
        $("#submit-form").submit();
    }
    function del(id) {
        bootbox.confirm("你确定删除吗?", function(result) {
            if (result) {
                var url = '${ctx }' + "/deviceMgt/del.do";
                var sendData = {
                    "id" : id
                };
                $.post(url, sendData, function(backData) {
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