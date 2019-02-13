<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">调度任务列表</hy:extends>
<hy:extends name="css">
	<style>
		pre, xmp {
		    padding: 3px 5px 0 5px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/platform/access/autoTask/autoTaskDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除调度任务信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable()
				}
			})
		}
    	layui.use(['form'], function () {
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/system/jobLog/jobLogList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "methodDesc", title: '任务描述', width: 160, fixed: "left", unresize: true},
                    { field: "methodName",  title: "方法名称", width: 240},
                    { field: "excuteStatus",  title: "运行状态", width: 100, align: 'center', templet: function (d) {
						return d.excuteStatus;
					}},
                    { field: "startTime",  title: "开始时间", minWidth: 200, align: "center"},
                    { field: "endTime",  title: "结束时间", minWidth: 200, align: "center"},
                    { field: "lastTime",  title: "持续时间(ms)", width: 130},
                    { field: "params",  title: "任务参数",  minWidth: 100},
                    { field: "result",  title: "执行结果",  minWidth: 300},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 100, unresize: true}
                ]],
                operate: {
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
					deleteAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					searchAction: function (tableInstance) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
                }
            });
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">任务名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="taskName_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<table id="tableList" lay-filter="tableList"></table>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="btn btn-danger radius" lay-event="deleteAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>搜索日志
        	</button>
	    </div>
	    <div style="display:none" id="operateBar">
	        <a class="btn btn-primary-outline radius size-S" title="预览" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-yulan"></i>
	        </a>
	        <a class="btn btn-danger-outline radius size-S" title="删除" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />