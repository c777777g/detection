<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="cl"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="WEB-INF/el-common.tld" prefix="json"%>
<%@include file="messageModals.jsp"%>
<c:set var="ctx" value="<%=request.getContextPath()%>"/>
 <link rel="shortcut icon" href="${ctx }/static/assets/img/favicon.png" type="image/x-icon">

    <script src="${ctx }/static/jedate/jedate.js"></script>
    <script src="${ctx }/static/assets/js/jquery-2.0.3.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.form.js"></script>
    
    <!--Basic Styles-->
    <link href="${ctx }/static/assets/css/bootstrap.min.css" rel="stylesheet" />
    <link id="bootstrap-rtl-link" href="" rel="stylesheet" />
    <link href="${ctx }/static/assets/css/font-awesome.min.css" rel="stylesheet" />
    <link href="${ctx }/static/assets/css/weather-icons.min.css" rel="stylesheet" />

    <!--Fonts-->
    <%--<link href="https://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300" rel="stylesheet" type="text/css">--%>
 	
 	<!--Bootstrap Date Picker-->
 	<script src="${ctx }/static/assets/js/bootstrap.min.js"></script>
	<script src="${ctx }/static/assets/js/datetime/bootstrap-datepicker.js"></script>
	<script src="${ctx }/static/assets/js/validation/bootstrapValidator.js"></script>
	<script src="${ctx }/static/assets/js/toastr/toastr.js"></script>
	
    <!--Beyond styles-->
    <link href="${ctx }/static/assets/css/beyond.min.css" rel="stylesheet" />
    <link href="${ctx }/static/assets/css/demo.min.css" rel="stylesheet" />
    <link href="${ctx }/static/assets/css/typicons.min.css" rel="stylesheet" />
    <link href="${ctx }/static/assets/css/animate.min.css" rel="stylesheet" />
    <link id="skin-link" href="" rel="stylesheet" type="text/css" />
	
    <!--Page Related styles-->
    <link href="${ctx }/static/assets/css/dataTables.bootstrap.css" rel="stylesheet" />

    <!--Skin Script: Place this script in head to load scripts for skins and rtl support-->
    <script src="${ctx }/static/assets/js/skins.min.js"></script>
    <script src="${ctx }/static/assets/js/jquery.form.js"></script>
    <!-- 图片上传 -->
    <script src="${ctx }/static/assets/js/upload/uploadPic.js"></script>
    <script src="${ctx }/static/assets/js/bootbox/bootbox.js"></script>
    <script src="${ctx }/static/assets/js/beyond.min.js"></script>
    <script src="${ctx }/static/assets/js/spin.js"></script>
    <style>
    	.page-body{
    		padding-top: 0px;
    		padding-left: 0px;
    	}
    	.widget{
    		padding-left: 0px;
    	}
        .mask {
           display: none;
           position: absolute; top: 0px; filter: alpha(opacity=60); background-color: #333333;
           z-index: 1002; left: 0px;
           opacity:0.5; -moz-opacity:0.5;
           height: 100%;
           width: 100%;
        }
    </style>

   <div id="mask" class="mask"></div>
   <script>
       var opts = {
           lines: 12, // The number of lines to draw
           length: 32, // The length of each line
           width: 9, // The line thickness
           radius: 29, // The radius of the inner circle
           scale: 0.65, // Scales overall size of the spinner
           corners: 1, // Corner roundness (0..1)
           color: '#ffffff', // CSS color or array of colors
           fadeColor: 'transparent', // CSS color or array of colors
           opacity: 0.25, // Opacity of the lines
           rotate: 0, // The rotation offset
           direction: 1, // 1: clockwise, -1: counterclockwise
           speed: 1, // Rounds per second
           trail: 60, // Afterglow percentage
           fps: 20, // Frames per second when using setTimeout() as a fallback in IE 9
           zIndex: 2e9, // The z-index (defaults to 2000000000)
           className: 'spinner', // The CSS class to assign to the spinner
           top: '450px', // Top position relative to parent
           left: '50%', // Left position relative to parent
           position: 'absolute' // Element positioning
       };

       var target = document.getElementById('mask');
       var spinner = new Spinner(opts);
       spinner.spin(target);
   </script>
