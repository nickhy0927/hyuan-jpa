<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="basePath"/>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <meta http-equiv="Content-Language" content="zh-CN"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/css/H-ui.admin.css">
    <meta charset="utf-8">
    <meta name="renderer" content="webkit|ie-comp|ie-stand">
    <meta http-equiv="X-UA-Compatible" content="chrome=1">
    <meta name="viewport" content="width=device-width,initial-scale=1,minimum-scale=1.0,maximum-scale=1.0,user-scalable=no"/>
    <meta http-equiv="Cache-Control" content="no-siteapp"/>
    <link rel="Bookmark" href="/favicon.ico">
    <link rel="Shortcut Icon" href="/favicon.ico"/>
    <link rel="favicon.ico" href="${basePath}/assets/images/favicon.ico" type="image/x-icon"/>
    <link rel="icon" href="${basePath}/assets/images/favicon.ico" type="image/x-icon"/>
    <link rel="shortcut icon" href="${basePath}/assets/images/favicon.ico"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/css/H-ui.min.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/css/H-ui.admin.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/Hui-iconfont/1.0.8/iconfont.css"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/skin/bluet/skin.css" id="skin"/>
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/zTree/v3/css/zTreeStyle/zTreeStyle.css"> <!-- zTree插件 -->
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/css/pagination.css" media="screen"/> <!-- 分页样式 -->
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/font-awesome/css/font-awesome.min.css"/> <!-- 自定义样式 -->
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/webuploader/0.1.5/webuploader.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/webuploader/webloader.css">
    <link rel="stylesheet" type="text/css" href="${basePath}/assets/css/style.css"/> <!-- 自定义样式 -->
	<%--<link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/easyui/themes/default/easyui.css">
	<link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/easyui/themes/icon.css">--%>
	<style type="text/css">
	   .ui-widget-overlay {
	       opacity: .5;
	       background: #eee;
	       filter: Alpha(Opacity=80);
	   }
	
	   p {
	       margin-bottom: 0px;
	   }
	   .breadcrumb {
	       top: -14px; right, left bottom
	   }
	   .check-box, .radio-box {
	       padding-left: 5px;
	   }
	</style>
    <link rel="stylesheet" href="${basePath}/assets/lib/layui/css/layui.css" media="all">
   	<script src="${basePath}/assets/lib/layui/layui.js"></script>
	<script type="text/javascript">
	    var ctx = '${basePath}';
	</script>
	<!--[if lt IE 9]>
	<script type="text/javascript" src="${basePath}/assets/lib/html5shiv.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/respond.min.js"></script>
	<![endif]-->
	<!--[if IE 6]>
	<script type="text/javascript" src="${basePath}/assets/lib/DD_belatedPNG_0.0.8a-min.js"></script>
	<script>DD_belatedPNG.fix('*');</script>
	<![endif]-->
	<script type="text/javascript" src="${basePath}/assets/lib/jquery/1.9.1/jquery.min.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/jquery/1.9.1/jquery.core.autocomplete.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/jquery/1.9.1/jquery.mockjax.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/jquery/1.9.1/countries.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/layer/2.4/layer.js"></script>
	<script type="text/javascript" src="${basePath}/assets/js/H-ui.min.js"></script>
	<script type="text/javascript" src="${basePath}/assets/js/H-ui.admin.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/zTree/v3/js/jquery.ztree.all-3.5.min.js"></script>
	<!-- zTree插件 -->
	<script type="text/javascript" src="${basePath}/assets/js/jquery.core.ajaxtable.js"></script> <!-- table插件 -->
	<script type="text/javascript" src="${basePath}/assets/js/codeTool.js"></script> <!-- 公共编解码 定制方法 -->
	<script type="text/javascript" src="${basePath}/assets/js/jquery.pagination.js"></script> <!-- 翻页插件 -->
	<script type="text/javascript" src="${basePath}/assets/lib/jquery.validation/1.14.0/jquery.validate.js"></script>
	<%--<script type="text/javascript" src="${basePath}/assets/lib/easyui/js/jquery.easyui.min.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/easyui/locale/easyui-lang-zh_CN.js"></script>--%>
	<!-- 表单验证插件 -->
	<script type="text/javascript" src="${basePath}/assets/lib/jquery.validation/1.14.0/validate-methods.js"></script>
	<!-- 表单验证插件 -->
	<script type="text/javascript" src="${basePath}/assets/lib/jquery.validation/1.14.0/messages_zh.js"></script> <!-- 表单验证插件 -->
	<script type="text/javascript" src="${basePath}/assets/js/common/tools.js"></script>
   	<script type="text/javascript" src="${basePath}/assets/js/hooks.js"></script>
	<link rel="stylesheet" type="text/css" href="${basePath}/assets/lib/grid-mamage/css/gm.css">
	<script type="text/javascript" src="${basePath}/assets/lib/grid-mamage/js/gm.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/grid-mamage/js/layer-util.js"></script>
	<script type="text/javascript" src="${basePath}/assets/lib/grid-mamage/js/page.js"></script>
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