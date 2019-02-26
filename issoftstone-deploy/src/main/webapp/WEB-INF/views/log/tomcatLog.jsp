<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">ztree</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		/* body {
			background: #000000;
		} */
		#log-container div p {
			font-family: Monaco;
			line-height: 30px;
		}
		
		#log-container div {
			padding: 10px;
		}
		.infotext {
			color: green;
		}
		.debugtext {
			color: maroon;
		}
		
		.WARN {
			color: orange;
		}
		.ERROR {
			color: red;
		}
		
		.logp {
			color: black;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(document).ready(function() {
	        // 指定websocket路径
	        var websocket = new WebSocket('ws://localhost:8081/issoftstone-deploy/ws.do?uid=log');
	        websocket.onmessage = function(event) {
	        	try {
	        		var content = JSON.parse(event.data);
	                var leverhtml = '';
	                var className = '<span class="classnametext">' + content.className + '</span>';
	                switch (content.level) {
	                    case 'INFO':
	                        leverhtml = '<span class="infotext">' + content.level + '</span>';
	                        break;
	                    case 'DEBUG':
	                        leverhtml = '<span class="debugtext">' + content.level + '</span>';
	                        break;
	                    case 'WARN':
	                        leverhtml = '<span class="warntext">' + content.level + '</span>';
	                        break;
	                    case 'ERROR':
	                        leverhtml = '<span class="errortext">' + content.level + '</span>';
	                        break;
	                }
	                $("#log-container div").append("<p class='logp'>" + content.timestamp + " " + leverhtml + " --- [" + content.threadName + "] " + className + " ：" + content.body + "</p>");
	                if (content.exception != "") {
	                    $("#log-container div").append("<p class='logp'>" + content.exception + "</p>");
	                }
	                if (content.cause != "") {
	                    $("#log-container div").append("<p class='logp'>" + content.cause + "</p>");
	                }
		            // 接收服务端的实时日志并添加到HTML页面中
		            // 滚动条滚动到最低部
		            $("#log-container").scrollTop($("#log-container div").height() - $("#log-container").height() + 20);					
				} catch (e) {
				}
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