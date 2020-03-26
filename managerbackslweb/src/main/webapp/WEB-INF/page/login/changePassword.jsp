<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>

<!DOCTYPE html>
<html xmlns="http://www.w3.org/1999/xhtml" style="overflow: auto;">
<!--Head-->
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
<style>
body {
            background: url(${ctx}/static/assets/img/bg.png) no-repeat;
            background-size: cover;
            background-attachment: fixed;
            max-height: 1080px;
        }

        #positionBox {
            width: 1000px;
            margin: 0 auto;
        }

        #loginBox {
            width: 582px;
            height: 373px;
            background: url(${ctx}/static/assets/img/loginBox.png) no-repeat;
            margin: 0 auto;
            margin-top: 20px;
            position: relative;
        }

        #loginForm {
            position: absolute;
            bottom: 49px;
            left: 13px;
            height: 50px;
            width: 582px;
            padding: 10px 0;
            margin:0 auto;
        }

        #loginTitle {
            width: 1000px;
            height: 100px;
            line-height: 100px;
            font-family: STXinwei;
            text-align: center;
            font-size: 70px;
            color: white;
            margin: 0 auto;
            margin-top: 150px;
            position: relative;
        }

        body:before {
            background-color: transparent;
        }

        #login {
            height: 20px;
            padding: 1px 15px;
        }

		#register {
			height: 20px;
			padding: 1px 15px;
		}

</style>

<head>
<title>登录</title>
</head>
<!--Head Ends-->
<!--Body-->
<body>
	
	<div class="modal-dialog modal-lg">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
					<h4 class="modal-title">修改密码</h4>
				</div>
                <h4>该连接只能访问一次，下次访问无效</h4>
				<div class="modal-body">
					<div id="horizontal-form">
						<form id="registerForm" class="form-horizontal form-bordered"
							  role="form" enctype="multipart/form-data" method="post">

							<div class="form-group">
								<label  class="col-sm-2 control-label no-padding-right">密码</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="userPassword" name="userPassword" placeholder="请输入密码">
								</div>
							</div>
							<div class="form-group">
								<label  class="col-sm-2 control-label no-padding-right">重新输入密码</label>
								<div class="col-sm-10">
									<input type="password" class="form-control" id="userPasswordAgain" name="userPasswordAgain" placeholder="请重新输入密码">
								</div>
							</div>
						</form>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-primary" id="registerBtn">确定修改</button>
				</div>
			</div>
		</div><!-- /.modal-dialog -->
	</div>

	

	<!--Google Analytics::Demo Only-->
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
					var loginType = $('#loginType').val();
					if (!phone1) {
						Notify('帐号不能为空', 'top-left', '5000', 'danger',
								'fa-warning', true);
						return false;
					}
					if (!password) {
						Notify('密码不能为空！', 'top-left', '5000', 'danger',
								'fa-warning', true);
						return false;
					}
                    var sendData = {
						"passport" : phone1,
						"passwd" : password,
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
            $("#userPassword").val("");
            $("#userPasswordAgain").val("");
            $("#registerModal").modal('show');
        });
        $("#registerBtn").on('click',function () {

            if ($("#userPassword").val()==""){
                Notify('密码不能为空', 'top-right', '5000', 'warning', 'fa-warning', true);
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
            var newPwd2 = $("#userPasswordAgain").val() ;
            var newPwd = $("#userPassword").val() ;
            url='${ctx}/adminMgt/updatePwd2.do';
            $("#registerForm").ajaxSubmit({
                url:url,
                data:{
                    newPwd2 : newPwd2,
                    newPwd : newPwd
                },
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
        
	</script>
</body>
<!--Body Ends-->
</html>
