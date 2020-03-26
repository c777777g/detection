<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@include file="../commons/commons.jsp"%>
<!DOCTYPE html>

<head>
<meta charset="utf-8" />
<title>Data Tables</title>
<link href="${ctx}/static/assets/css/laydate.css" rel="stylesheet">
<link href="${ctx}/static/assets/css/skin/molvlaydate.css" rel="stylesheet">
<link href="${ctx }/static/assets/css/fileinput.min.css" rel="stylesheet"/>
<script src="${ctx }/static/assets/js/datetime/laydate.js"></script>
<script src="${ctx }/static/assets/js/upload/fileinput.min.js"></script>
<script src="${ctx }/static/assets/js/upload/zh.js"></script>



	<style>
		input[type="checkbox"].checkbox-slider + .text::before{
			content: ''!important;
		}
	</style>
</head>
<body>
	<div class="page-body">
		<div class="row">
			<div class="col-xs-12 col-md-12">
				<div class="widget">
					<div class="widget-header ">
						<span class="widget-caption"> <c:if test="${empty bean.id}">新增</c:if>
							<c:if test="${!empty bean.id}">编辑</c:if> </span>

					</div>
					<div class="widget-body">
						<form id="saveOrUpdate" class="form-horizontal form-bordered"
							role="form" enctype="multipart/form-data" method="post">
							

							<div class="form-group shangpin">
								<label class="col-sm-2 control-label no-padding-right">设备号</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="deviceId"
										   name="deviceId" value="${bean.deviceId}" placeholder="设备号" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">设备名称</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="deviceName"
										name="deviceName" value="${bean.deviceName}" placeholder="设备名称" />
								</div>
							</div>
							
							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">设备地址</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="deviceAddress"
										name="deviceAddress" value="${bean.deviceAddress}" placeholder="设备地址" />
								</div>
							</div>
                            
                            <div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">地区</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="district"
										   name="district" value="${bean.district}" placeholder="所属片区" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">高湿度警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="humidityMax"
										   name="humidityMax" value="${bean1.humidityMax}" placeholder="高湿度警告值范围1-100，例子：95" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">低湿度警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="humidityMin"
										   name="humidityMin" value="${bean1.humidityMin}" placeholder="低湿度警告值范围0-100，例子：10" />
								</div>
							</div>

							<%--<div class="form-group">--%>
								<%--<label class="col-sm-2 control-label no-padding-right">高光度警告值</label>--%>
								<%--<div class="col-sm-10">--%>
									<%--<input type="text" class="form-control" id="opticalInductorMax"--%>
										   <%--name="opticalInductorMax" value="${bean1.opticalInductorMax}" placeholder="高光度警告值" />--%>
								<%--</div>--%>
							<%--</div>--%>

							<%--<div class="form-group">--%>
								<%--<label class="col-sm-2 control-label no-padding-right">低光度警告值</label>--%>
								<%--<div class="col-sm-10">--%>
									<%--<input type="text" class="form-control" id="opticalInductorMin"--%>
										   <%--name="opticalInductorMin" value="${bean1.opticalInductorMin}" placeholder="低光度警告值" />--%>
								<%--</div>--%>
							<%--</div>--%>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">高温度警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="temperatureMax"
										   name="temperatureMax" value="${bean1.temperatureMax}" placeholder="高温度警告值范围-100至100，例子：30" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">低温度警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="temperatureMin"
										   name="temperatureMin" value="${bean1.temperatureMin}" placeholder="低温度警告值范围-100至100，例子：-5" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">高PM2.5警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="pm25Max"
										   name="pm25Max" value="${bean1.pm25Max}" placeholder="高PM2.5警告值" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">低PM2.5警告值</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="pm25Min"
										   name="pm25Min" value="${bean1.pm25Min}" placeholder="低PM2.5警告值" />
								</div>
							</div>

							<div class="form-group">
								<label class="col-sm-2 control-label no-padding-right">音量大小</label>
								<div class="col-sm-10">
									<input type="text" class="form-control" id="volume"
										   name="volume" value="${deviceSensor.volume}" placeholder="范围0至100" />
								</div>
							</div>


							<div class="form-group">
								<div class="col-sm-offset-2 col-sm-10">
									<a class="btn btn-palegreen" id="save">提交</a>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="modal fade bs-example-modal-lg" tabindex="-1" role="dialog"
		 aria-labelledby="myLargeModalLabel" aria-hidden="true"
		 style="display: none;">
		<div class="modal-dialog modal-lg">
			<div class="modal-content">

				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal"
							aria-hidden="true">×</button>
					<h4 class="modal-title" id="myLargeModalLabel">选择分类</h4>
				</div>
				<div class="modal-body">
					<div class="widget-body">
						<div id="MyTree" class="tree tree-plus-minus">
							<div class="tree-folder" style="display: none;">
								<div class="tree-folder-header">
									<i class="fa fa-folder"></i>
									<div class="tree-folder-name"></div>
								</div>
								<div class="tree-folder-content"></div>
								<div class="tree-loader" style="display: none;"></div>
							</div>
							<div class="tree-item" style="display: none;">
								<i class="tree-dot"></i>
								<div class="tree-item-name"></div>
							</div>
						</div>
						<a class="btn btn-palegreen" data-dismiss="modal"
						   style="margin-left: 90%;" id="saveType">确定</a>
					</div>
				</div>
			</div>
			<!-- /.modal-content -->
		</div>
		<!-- /.modal-dialog -->
	</div>
</body>
<script src="${ctx }/static/assets/js/fuelux/treeview/tree-custom.min.js"></script>
<script src="${ctx }/static/assets/js/fuelux/treeview/treeview-init.js"></script>

<script>

Date.prototype.format = function(fmt) {
     var o = {
        "M+" : this.getMonth()+1,                 //月份
        "d+" : this.getDate(),                    //日
        "h+" : this.getHours(),                   //小时
        "m+" : this.getMinutes(),                 //分
        "s+" : this.getSeconds(),                 //秒
        "q+" : Math.floor((this.getMonth()+3)/3), //季度
        "S"  : this.getMilliseconds()             //毫秒
    };
    if(/(y+)/.test(fmt)) {
            fmt=fmt.replace(RegExp.$1, (this.getFullYear()+"").substr(4 - RegExp.$1.length));
    }
     for(var k in o) {
        if(new RegExp("("+ k +")").test(fmt)){
             fmt = fmt.replace(RegExp.$1, (RegExp.$1.length==1) ? (o[k]) : (("00"+ o[k]).substr((""+ o[k]).length)));
         }
     }
    return fmt;
}

		//保存
		$("#save").on('click',function () {

			if (  $("#humidityMax").val() <0 || $("#humidityMax").val() > 100) {
				Notify("高湿度警告值范围0至100，例子：95", 'bottom-right', '5000', 'green', 'fa-bolt', false);
				return;
			}
			if (  $("#humidityMin").val() <0 || $("#humidityMin").val() > 100) {
				Notify("低湿度警告值范围0至100，例子：10", 'bottom-right', '5000', 'green', 'fa-bolt', false);
				return;
			}


			if (  $("#temperatureMax").val() <-100 || $("#temperatureMax").val() > 100) {
				Notify("高温度警告值范围-100至100，例子：30", 'bottom-right', '5000', 'green', 'fa-bolt', false);
				return;
			}

			if (  $("#temperatureMin").val() <-100 || $("#temperatureMin").val() > 100) {
				Notify("低温度警告值范围-100至100，例子：-5", 'bottom-right', '5000', 'green', 'fa-bolt', false);
				return;
			}

			if (  $("#volume").val() <0 || $("#volume").val() > 100) {
				Notify("音量大小访问0至100，例子：50", 'bottom-right', '5000', 'green', 'fa-bolt', false);
				return;
			}

                    url='${ctx}/deviceMgt/save.do';
            $("#mask").show();
                $("#saveOrUpdate").ajaxSubmit({
                    url:url,
                    data:{
                        type:"${TYPE}"
                    },
                    success:function (res) {
                        $("#mask").hide();
                        if (res.status == 1){
                            Notify("成功", 'bottom-right', '5000', 'green', 'fa-bolt', true);
                            self.location = document.referrer;
                        }else {
                            Notify(res.message[0].msg, 'bottom-right', '5000', 'danger', 'fa-bolt', false);
                        }
                    },
                    error:function (res) {
                        console.log(res);
                    }
                });
        });

        $("#checkbox").change(function () {
            if ($("#checkbox").is(':checked')){
                $(".shangpin").hide();
                $("#attrDiv").show();

            }else {
                $(".shangpin").show();
                $("#attrDiv").hide();
            }
        });


</script>
</html>
