<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">ztree</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		body {
			background: #000000;
		}
		#log-container div p {
			font-family: Monaco;
			font-size: 25px;
			color: green;
			font-weight:bold;
			line-height: 30px;
		}
		
		#log-container div {
			padding: 10px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(document).ready(function() {
	        // 指定websocket路径
	        var websocket = new WebSocket('ws://localhost:8081/issoftstone-deploy/ws.do?uid=log');
	        websocket.onmessage = function(event) {
	        	console.log(event)
	            // 接收服务端的实时日志并添加到HTML页面中
	            $("#log-container div").append("<p>" + event.data + "</p>");
	            // 滚动条滚动到最低部
	            $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height());
	        };
	    });
	</script>
</hy:extends>
<hy:extends name="body">
	<div id="log-container" style="height: 100%; overflow-y: auto">
        <div></div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />