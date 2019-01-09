<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">图标列表</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		function create() {
			$.openWindow({
				title: '新增图标',
				height: '230px',
				width: '700px',
				url: '${ctx}/platform/access/icon/create.do'
			})
			return false;
		}
		function edit(id) {
			$.openWindow({
				title: '修改图标',
				url: '${ctx}/platform/access/icon/edit.do?id=' + id
			})
			return false;
		}
		function initTable() {
			$('table').dataTable({
				gridManagerName: 'iconList',
				height: 'auto',
				ajax_data: '${ctx}/platform/access/icon/list.json',
				query: {
					pluginId: 1
				},
				showFooterCheckedInfo: true,
				columnData: [{
					key: 'name',
					remind: '图标显示的名称',
					text: '图标名称',
					width: '120px',
					align: 'left',
					sorting: 'DESC',
	                template: function (nodeData, rowData) {
	                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
	                }
				},{
					key: 'className',
					remind: '图标样式名称',
					width: '270px',
					text: '样式名称',
					align: 'left',
	                template: function (nodeData, rowData) {
	                    return '<code class="td-content">' + (nodeData ? nodeData : '') + '</code>'
	                }
				},{
					key: 'icon',
					width: '80px',
					text: '图标',
					align: 'center',
	                template: function (nodeData, rowData) {
	                    return '<span>' + (nodeData ? nodeData : '') + '</span>'
	                }
				},{
					key: 'iconClass',
					remind: '图标使用的方法',
					disableCustomize: true,
					text: '图标样式',
					align: 'left',
	                template: function (nodeData, rowData) {
	                    return '<pre class="td-content"><xmp>' + (nodeData ? nodeData : '') + '</xmp></pre>'
	                }
				},{
					key: 'action',
					width: "170px",
					align: 'center',
					text: '操作',
					template: function(action, rowObject){
						return '<div id="operates">' +
									'<a onclick="edit(\'' + rowObject.id + '\')" class="layui-btn layui-btn-xs" lay-event="edit">' +
										'<i class="layui-icon">&#xe642;</i>编辑' +
									'</a>' +
									'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' +
										'<i class="layui-icon">&#xe640;</i>删除' +
									'</a>' +
								'</div>';
					}
				}]
			})
		}
	
		$(function () {
			initTable();
			layui.use('form', function(){
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
		.table-wrap .table-div table[grid-manager] tbody tr td {
		    padding: 2px 4px;
		}
	</style>	
</hy:extends>
<hy:extends name="body">
<div class="search-block">
	<form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">图标名称</label>
				<div class="layui-input-inline">
					<input type="text" name="name_li" 
						placeholder="请输入图标名称"
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
			<button onclick="create()" class="layui-btn layui-btn-sm layui-btn-normal" id="add">
				<i class="layui-icon">&#xe608;</i> 新增
			</button>
			<button class="layui-btn layui-btn-sm layui-btn-danger" id="del">
				<i class="layui-icon">&#xe640;</i>删除
			</button>
			<button class="layui-btn layui-btn-sm" id="search">
				<i class="layui-icon">&#xe615;</i>搜索
			</button>
		</div>
	</div>
	<table id="tableList"></table>
</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />