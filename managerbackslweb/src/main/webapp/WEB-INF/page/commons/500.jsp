<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" isErrorPage="true"%>
<%response.setStatus(HttpServletResponse.SC_OK);%>
<%@include file="../commons/commons.jsp"%>

<html xmlns="http://www.w3.org/1999/xhtml">
<!--Head-->
<head>
    <meta charset="utf-8" />
    <title>Error 500</title>

    <meta name="description" content="Error 404" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8" />

    <link id="bootstrap-rtl-link" href="" rel="stylesheet" />


    <%--<!--Fonts-->--%>
    <%--<link href="http://fonts.googleapis.com/css?family=Open+Sans:300italic,400italic,600italic,700italic,400,600,700,300"--%>
          <%--rel="stylesheet" type="text/css">--%>

    <link id="skin-link" href="" rel="stylesheet" type="text/css" />

</head>
<!--Head Ends-->
<!--Body-->
<body class="body-500">
<div class="error-header"> </div>
<div class="container ">
    <section class="error-container text-center">
        <h1>500</h1>
        <div class="error-divider">
            <h2>ooops!!</h2>
            <p class="description">SOMETHING WENT WRONG.</p>
        </div>
        <a href="${stx}/home.do" class="return-btn"><i class="fa fa-home"></i>Home</a>
    </section>
</div>

</body>
<!--Body Ends-->
</html>

