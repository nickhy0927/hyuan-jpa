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
				height: '230px',
				width: '700px',
				url: '${ctx}/platform/access/icon/edit.do?id=' + id
			})
			return false;
		}
		function initTable() {
			$("#dataGridList").dataGrid({
                url: '${ctx}/platform/access/icon/list.json',
                title: '图标管理列表',
                method: 'POST',
                checkbox: true,
                pageSize: 12,
                orderField: 'createTime',
                sort: 'desc',
                searchButtonId: '#searchButton',
                queryParamsId: ['#name', "#attr"],
                tableId: '#dataGridList',
                columns: [
                    {field: 'id', className: 'text-c'},
                    {field: 'name', className: 'text-l', description: '图标名称 ', sort: true},
                    {field: 'className', className: 'text-l', description: '样式名称 ', sort: true},
                    {field: 'icon', className: 'text-c', description: '图标'},
                    {field: 'iconClass', className: 'text-l', description: '使用方法', paramFormatter: function (row) {
                    		return '<pre><xmp>' + row.iconClass + '</xmp></pre>';
                    	},
                    },
                    {field: 'operate',width: 140,  className: 'text-c', description: '操作', paramFormatter: function (row) {
                        return  '<a onclick="edit(\'' + row.id + '\')" class="layui-btn layui-btn-xs" lay-event="edit">' +
									'<i class="layui-icon">&#xe642;</i>编辑' +
								'</a>' +
								'<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">' +
									'<i class="layui-icon">&#xe640;</i>删除' +
								'</a>';
                    }}
                ]
            });
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
	    }
	</script>
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
			<button class="layui-btn layui-btn-sm" id="search">
				<i class="layui-icon">&#xe615;</i>搜索
			</button>
		</div>
	</div>
    <div class="mt-5">
        <table id="dataGridList" class="table table-border table-bordered table-hover table-bg table-sort"></table>
    </div>
</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />