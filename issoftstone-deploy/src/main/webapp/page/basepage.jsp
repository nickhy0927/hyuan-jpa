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
    <link href="${ctx}/static/lib/ligerui/ligerUI/skins/Aqua/css/ligerui-all.css" rel="stylesheet" type="text/css" />  
    <link href="${ctx}/static/lib/ligerui/ligerUI/index.css" rel="stylesheet" type="text/css" />  
    <link rel="stylesheet" type="text/css" id="mylink"/>   
    <script src="${ctx}/static/lib/ligerui/jquery/jquery-1.9.0.min.js" type="text/javascript"></script>    
    <script src="${ctx}/static/lib/ligerui/ligerUI/js/ligerui.all.js" type="text/javascript"></script>  
    <script src="${ctx}/static/lib/ligerui/jquery.cookie.js"></script>
    <script src="${ctx}/static/lib/ligerui/json2.js"></script>
    <script src="${ctx}/static/lib/ligerui/indexdata.js" type="text/javascript"></script>
    <title>
        <hy:block name="title"></hy:block>
    </title>
    <script type="text/javascript">
    </script>
    <hy:block name="javascript"></hy:block>
    <hy:block name="css"></hy:block>
</head>
<body style="padding:0px;background:#EAEEF5;">
    <hy:block name="body"></hy:block>
</body>
</html>