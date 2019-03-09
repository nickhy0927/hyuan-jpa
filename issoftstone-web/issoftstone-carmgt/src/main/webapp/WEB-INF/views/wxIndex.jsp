<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">微信登录首页</hy:extends>
<hy:extends name="css">
	<style type="text/css">
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		
	</script>
</hy:extends>
<hy:extends name="body">
	<div id="log-container" style="height: 100%; overflow-y: auto">
        <div>${params}</div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />