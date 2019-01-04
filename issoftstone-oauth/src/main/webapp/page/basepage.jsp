<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="x-ua-compatible" content="IE=Edge">
    <link rel="icon" href="${ctx}/favicon.ico">
    <link rel="stylesheet" href="${ctx}/static/lib/layui/dist/css/layui.css">
    <link rel="stylesheet" href="${ctx}/static/lib/layui/dist/css/index.css">
    <link rel="stylesheet" href="${ctx}/static/lib/fontawesome/css/font-awesome.min.css">
    <script src="${ctx}/static/lib/jquery/dist/jquery.min.js"></script>
    <script src="${ctx}/static/lib/layui/dist/layui.js"></script>
    <script src="${ctx}/static/lib/layui/layer-util.js"></script>
    <script src="${ctx}/static/lib/layui/page.js"></script>
    <title>
        <hy:block name="title"></hy:block>
    </title>
    <script type="text/javascript">
    	/* $(function() {
    		$("body").css({
				'overflow-y':'auto'
			})
    	}) */
    </script>
    <hy:block name="javascript"></hy:block>
    <hy:block name="css"></hy:block>
</head>
<body class="layui-layout-body">
    <hy:block name="body"></hy:block>
</body>
</html>