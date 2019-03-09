<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">无权限访问</hy:extends>
<hy:extends name="css">
    <style type="text/css">
    </style>
</hy:extends>
<hy:extends name="javascript">
	${msg}--${url}
</hy:extends>
<hy:extends name="body">

</hy:extends>
<jsp:include page="/page/basepage.jsp"/>
