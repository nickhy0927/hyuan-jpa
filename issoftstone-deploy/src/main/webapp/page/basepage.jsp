<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
    <meta name="viewport"
          content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <link rel="Bookmark" href="favicon.ico">
    <link rel="Shortcut Icon" href="favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/hui/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/hui/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/hui/skin/default/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="${ctx}/assets/hui/css/style.css"/>
    <!--[if lt IE 9]>
    <script type="text/javascript" src="${ctx}/assets/lib/html5.js"></script>
    <script type="text/javascript" src="${ctx}/assets/lib/respond.min.js"></script>
    <![endif]-->
    <!--/meta 作为公共模版分离出去-->
    <script type="text/javascript" src="${ctx}/assets/lib/jquery/1.9.1/jquery.min.js"></script>
    <script type="text/javascript" src="${ctx}/assets/lib/layer/2.4/layer.js"></script>
    <script type="text/javascript" src="${ctx}/assets/hui/js/H-ui.js"></script>
    <script type="text/javascript" src="${ctx}/assets/hui/js/H-ui.admin.page.js"></script>
    <script type="text/javascript" src="${ctx}/assets/hui/js/hooks.js"></script>

    <script type="text/javascript">

    </script>
    <title>
        <hy:block name="title"></hy:block>
    </title>
    <hy:block name="javascript"></hy:block>
    <hy:block name="css"></hy:block>
</head>
<body>
<hy:block name="body"></hy:block>
</body>
</html>