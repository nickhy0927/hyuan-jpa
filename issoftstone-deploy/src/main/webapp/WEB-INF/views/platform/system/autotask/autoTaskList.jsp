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
            var form = layui.form;
        	form.on('switch(startStatus)', function (data) {
				var startStatus = this.checked ? '1' : '0';
				$.saveInfo({
					url: '${ctx}/platform/access/autoTask/excuteTask.json',
					data: {id: $(data.elem).attr('data-id'), startStatus: startStatus, version: $(data.elem).attr('data-v')},
					success: function (res) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
				})
			})
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/autoTask/queryAutoTaskList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "taskName", title: '任务名称', width: 160, fixed: "left", unresize: true},
                    { field: "scheduler",  title: "任务表达式", width: 180},
                    { field: "startStatus",  title: "运行状态", width: 100, align: 'center', templet: function (d) {
						var checked = d.startStatus == 1 ? "checked='checked'" : "";
						return '<input data-v=' + d.version + ' ' + checked + ' data-id=' + d.id + ' type="checkbox" lay-skin="switch" lay-filter="startStatus" lay-text="暂停|运行">';
					}},
                    { field: "className",  title: "任务类名", minWidth: 240},
                    { field: "remarks",  title: "任务描述",  minWidth: 240},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 70, unresize: true}
                ]],
                operate: {
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增调度任务',
							height: '360px',
							width: '60%',
							url: '${ctx}/platform/system/autotask/autoTaskCreate.do'
						})
					},
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
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增任务
	        </button>
	        <button class="btn btn-danger radius" lay-event="deleteAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>搜索任务
        	</button>
	    </div>
	    <div style="display:none" id="operateBar">
	        <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />