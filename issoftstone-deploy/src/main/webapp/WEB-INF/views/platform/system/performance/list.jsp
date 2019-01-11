<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">图标列表</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		function initTable() {
			$('table').dataTable({
				gridManagerName: 'performanceList',
				height: 'auto',
				ajax_data: '${ctx}/platform/system/performance/list.json',
				query: {
					alias_eq: '${alias}',
					createDate_eq: new Date().format('yyyy-MM-dd')
				},
				pageSize: 12,
				columnData: [{
					key: 'name',
					width: '200px',
					align: 'left',
					text: '菜单名称',
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : '') + '</span>'
	                }
				},{
					key: 'alias',
					text: '菜单别名',
					align: 'left',
					width: '230px',
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : '') + '</span>'
	                }
				},{
					key: 'url',
					text: '访问地址',
					align: 'left',
					width: 'calc(100% - 680px)',
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : '') + '</span>'
	                }
				},{
					key: 'executeTime',
					text: '执行所需时间',
					align: 'left',
					sorting: 'ASC',
					width: 100,
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : 0) + 'ms</pre>'
	                }
				},{
					key: 'createDate',
					width: 160,
					text: '执行时间',
					align: 'center',
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : '') + '</xmp></pre>'
	                }
				}]
			})
		}
	
		$(function () {
			layui.use(['form', 'laydate'], function(){
				var laydate = layui.laydate;
				//执行一个laydate实例
				laydate.render({
					elem: '#createDate', //指定元素
					value: new Date()
				});
				initTable();
				$('#search').on('click', function () {
				    refresh();
				})
			});
		})
		
	    function refresh() {
	        var query = $("#search-form").serializeObject();
	        tableManager.GM("setQuery", query);
	    }
	</script>
</hy:extends>
<hy:extends name="css">
	<style>
		
	</style>	
</hy:extends>
<hy:extends name="body">
	<div class="search-block">
		<form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
			<div class="layui-form-item">
				<input type="hidden" value="${alias}" name="alias_eq">
				<div class="layui-inline">
					<label class="layui-form-label">请求时间</label>
					<div class="layui-input-inline">
						<input type="text" name="createDate_eq" 
							placeholder="请选择时间" id="createDate"
							autocomplete="off" class="layui-input">
					</div>
				</div>
			</div>
		</form>
	</div>
	<hr class="hr-line">
	<div class="grid-main">
		<div class="layui-form-item">
			<div class="layui-btn-container" id="operate-btn">
				<button class="layui-btn layui-btn-sm" id="search">
					<i class="layui-icon">&#xe615;</i>搜索
				</button>
			</div>
		</div>
		<table id="tableList"></table>
	</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />