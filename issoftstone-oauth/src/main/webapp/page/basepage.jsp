<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<!DOCTYPE html>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<meta http-equiv="x-ua-compatible" content="IE=Edge" >  
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