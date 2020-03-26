<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>

<!DOCTYPE html>

<html xmlns="http://www.w3.org/1999/xhtml">
<script type="text/javascript">
    //如果不是顶级窗口，则先获取顶级窗口，再跳转到登录界面
    if (!(window.parent == window)) {
        var topWin = (function(p, c) {
            while (p != c) {
                c = p
                p = p.parent
            }
            return c
        })(window.parent, window);
        topWin.location.href = '${ctx}' + "/welcome.do";
    }
</script>

<style type="text/css" >

    body::before{
        background:url("./static/assets/img/avatars.jpg") no-repeat center center;
        background-size:cover;
        background-attachment:fixed;
        /*background-color:#5DE17F;*/
    }

</style>

<!--Head-->
<head>
	<meta charset="utf-8" />
	<title>登录</title>
	<meta name="description" content="login page" />
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
</head>

<body>


<div style="width: 500px;height: 80px;line-height: 80px;font-family: STXinwei;text-align: center;
    font-size: 40px;margin: 0 auto;margin-top: 150px;position: relative; color: #00f;">检查设备后台管理</div>
<div class="login-container animated fadeInDown" style="margin-top: 0%;">
	<div class="loginbox bg-white">
		<div class="loginbox-title" style="margin-bottom: 50px;padding-top: 50px">
			<h1 style="margin-top: -10px;">登录</h1>
		</div>


		<div class="loginbox-textbox">
			<input type="text" id="phone1" class="form-control" placeholder="账号" />
		</div>
		<div class="loginbox-textbox">
			<input type="password" id="password" class="form-control" placeholder="密码" style="display: table-cell"/>
		</div>
		<div class="loginbox-forgot">
			<a href="#" id="findPassword">忘记密码?</a>
		</div>
		<div class="loginbox-submit">
			<input type="button" id="login" class="btn btn-primary btn-block" value="登录">
		</div>
		<%--<div class="loginbox-signup">--%>
			<%--<a href="#" id="register">注册</a>--%>
		<%--</div>--%>
	</div>
	<div class="logobox">
	</div>
</div>
<div style="text-align: center; color:#000;">建议使用谷歌浏览器(chrome)访问管理平台,若使用其他浏览器访问可能存在兼容性问题,影响您的使用体验。</div>
<div id="registerModal" class="modal fade bs-example-modal-lg">
	<div class="modal-dialog" style="border-top-style: solid;margin-top: 150px;">
		<div class="widget-header bg-blue">
			<span class="widget-caption">注册</span>
		</div>
		<div class="widget-body">
			<form id="registerForm" role="form" enctype="multipart/form-data" method="post">
				<div class="form-title">
					用户信息
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="text" class="form-control" id="userName" name="userName" placeholder="请输入账号,长度4到16位（字母，数字，下划线，减号）">
                                <i class="glyphicon glyphicon-user circular"></i>
                            </span>
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="密码长度6到16位数字字母且不含特殊符号">
                                <i class="fa fa-lock circular"></i>
                            </span>
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="password" class="form-control" id="userPasswordAgain" name="userPasswordAgain" placeholder="再次输入密码">
                                <i class="fa fa-lock circular"></i>
                            </span>
				</div>
				<div class="form-title">
					个人信息
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="text" class="form-control" id="alias" name="alias" placeholder="名字">
                                <i class="fa fa-user"></i>
                            </span>
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="text" class="form-control" id="phone" name="phone" placeholder="手机号码">
                                <i class="glyphicon glyphicon-phone"></i>
                            </span>
				</div>
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="text" class="form-control" id="email" name="email" placeholder="邮箱">
                                <i class="fa fa-envelope-o circular"></i>
                            </span>
				</div>
				<div align="right">
				<button type="button" class="btn btn-blue" id="registerBtn">管理员注册</button>
				<button type="button" class="btn btn-blue" id="linkmanRegisterBtn">维护员注册</button>
				<button type="button" class="btn btn-blue" data-dismiss="modal">取消</button>
				</div>
			</form>
		</div>
	</div>
</div>

<div id="findPasswordModal" class="modal fade bs-example-modal-lg">
	<div class="modal-dialog" style="border-top-style: solid;margin-top: 150px;">
		<!-- <div class="widget flat radius-bordered"> -->
		<div class="widget-header bg-blue">
			<span class="widget-caption">通过邮件更改密码</span>
		</div>
		<div class="widget-body">
			<%--<div id="registerForm">--%>
			<form id="sendEmailForm" role="form" enctype="multipart/form-data" method="post">
				<div class="form-group">
                            <span class="input-icon icon-right">
                                <input type="text" class="form-control" id="email2" name="email2" placeholder="邮箱">
                                <i class="fa fa-envelope-o circular"></i>
                            </span>
				</div>
				<div align="right">
				<button type="button" class="btn btn-blue" id="sendEmailBtn">发送</button>
				<button type="button" class="btn btn-blue" data-dismiss="modal">取消</button>
				</div>
			</form>
		</div>
		<!-- </div> -->
	</div>
</div>

<script>
    $(document).keydown(function() {
        if (event.keyCode == "13") {//keyCode=13是回车键
            $('#login').click();
        }
    });
    (function(i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function() {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o), m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script',
        'http://www.google-analytics.com/analytics.js', 'ga');
    ga('create', 'UA-52103994-1', 'auto');
    ga('send', 'pageview');

    $('#login').click(
        function() {
            var phone1 = $('#phone1').val();
            var password = $('#password').val();
            var loginType = $('#loginType input[name="form-field-radio"]:checked').val();
            if (!phone1) {
                Notify('账号不能为空', 'top-left', '5000', 'danger',
                    'fa-warning', true);
                return false;
            }
            if (!password) {
                Notify('密码不能为空！', 'top-left', '5000', 'danger',
                    'fa-warning', true);
                return false;
            }
            var sendData = {
                "userName" : phone1,
                "userPassword" : password,
                "loginType" :loginType
            };
            $("#mask").show();
            $.post("getLogin.do", sendData, function(backData) {
                $("#mask").hide();
                if (backData.status == 1) {
                    window.location.href = "home.do";
                } else {
                    Notify(backData.message[0].msg, 'top-left', '5000', 'danger',
                        'fa-warning', true);
                }
            });

        })

    $("#register").click(function () {
        $("#userName").val("");
        $("#userPassword").val("");
        $("#userPasswordAgain").val("");
        $("#alias").val("");
        $("#phone").val("");
        $("#email").val("");
        $("#registerModal").modal('show');
    });
    $("#registerBtn").on('click',function () {

        if ($("#userName").val().search(/^[a-zA-Z0-9_-]{4,16}$/) == -1){
            Notify('请输入正确的账号格式', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#userPassword").val()==""|| $("#userPassword").val().search(/^[\w_-]{6,16}$/)==-1){
            Notify('请输入正确的密码格式', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#userPasswordAgain").val()==""){
            Notify('确认密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#userPassword").val() != $("#userPasswordAgain").val()){
            Notify('两次输入的密码不一致', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if($("#phone").val().search(/^1[3|4|5|7|8][0-9]{9}$/) == -1){
            Notify('请输入正确的手机号码', 'top-right', '5000', 'danger', 'fa-bolt', true);
            return;
        }
        if($("#email").val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1){
            Notify('请输入正确的邮箱', 'top-right', '5000', 'danger', 'fa-bolt', true);
            return;
        }
        var url='${ctx}/userMgt/register.do';
        $("#registerForm").ajaxSubmit({
            url:url,
            success:function (res) {
                console.log(res);
                if (res.status == 2){
                    $("#registerModal").modal('hide');
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            },
            error:function (res) {
                console.log(res);
            }
        });
    });
    $("#linkmanRegisterBtn").on('click',function () {

        if ($("#userName").val().search(/^[a-zA-Z0-9_-]{4,16}$/) == -1){
            Notify('请输入正确的账号格式', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ( $("#userPassword").val().search(/^[\w_-]{6,16}$/)==-1){
            Notify('请输入正确的密码格式', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#userPasswordAgain").val()==""){
            Notify('确认密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if ($("#userPassword").val() != $("#userPasswordAgain").val()){
            Notify('两次输入的密码不一致', 'top-right', '5000', 'warning', 'fa-warning', true);
            return;
        }
        if($("#phone").val().search(/^1[3|4|5|7|8][0-9]{9}$/) == -1){
            Notify('请输入正确的手机号码', 'top-right', '5000', 'danger', 'fa-bolt', true);
            return;
        }
        if($("#email").val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1){
            Notify('请输入正确的邮箱', 'top-right', '5000', 'danger', 'fa-bolt', true);
            return;
        }
        var url='${ctx}/linkmanMgt/linkmanRegister.do';
        $("#registerForm").ajaxSubmit({
            url:url,
            success:function (res) {
                console.log(res);
                if (res.status == 2){
                    $("#registerModal").modal('hide');
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            },
            error:function (res) {
                console.log(res);
            }
        });
    });

    $("#findPassword").click(function () {
        $("#email2").val("");
        $("#findPasswordModal").modal('show');
    });
    $("#sendEmailBtn").on('click',function () {

        if ($("#email2").val().search(/^\w+((-\w+)|(\.\w+))*\@[A-Za-z0-9]+((\.|-)[A-Za-z0-9]+)*\.[A-Za-z0-9]+$/) == -1){
            Notify('请输入正确的邮箱', 'top-right', '5000', 'warning', 'fa-warning', false);
            return;
        }
        var url='${ctx}/adminMgt/findPassword.do';
        $("#sendEmailForm").ajaxSubmit({
            url:url,
            success:function (res) {
                console.log(res);
                if (res.status == 2){
                    $("#findPasswordModal").modal('hide');
                    Notify(res.message[0].msg, 'top-right', '5000', 'green', 'fa-bolt', true);
                }else {
                    Notify(res.message[0].msg, 'top-right', '5000', 'danger', 'fa-bolt', true);
                }
            },
            error:function (res) {
                console.log(res);
            }
        });
    });
</script>
</body>
<!--Body Ends-->
</html>
