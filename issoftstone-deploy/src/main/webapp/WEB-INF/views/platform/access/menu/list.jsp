<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		table td{
			word-break: keep-all !important;
			/* white-space:nowrap !important; */
			text-overflow : ellipsis;
		}
		table th{word-break: keep-all !important;white-space:nowrap !important;}
	</style>
</hy:extends>
<hy:extends name="javascript">
<script type="text/javascript">
	function create() {
		$.openWindow({
			title: '新增菜单',
			height: '480px',
			url: '${ctx}/platform/access/menu/create.do'
		})
		return false;
	}
	function edit(id) {
		$.openWindow({
			title: '修改菜单',
			height: '480px',
			url: '${ctx}/platform/access/menu/edit.do?id=' + id
		})
		return false;
	}
	function initTable() {
		$("#dataGridList").dataGrid({
            url: '${ctx}/platform/access/menu/list.json',
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
                {field: 'name', width: 100, className: 'text-l', description: '菜单名称 ', sort: true},
                {field: 'alias', width: 160,  className: 'text-l', description: '菜单别名', sort: true},
                {field: 'parentName',width: 100, className: 'text-l', description: '上级菜单'},
                {field: 'localCode',width: 160, className: 'text-l', description: '国际化编码'},
                {field: 'url',width: 180, className: 'text-l', description: '访问地址', paramFormatter: function (row) {
                	var url = row.url;
                	/* console.log(url)*/
                	if(url && url.length > 20) {
                		url = url.substring(0, 20) + '...';
                	} 
                	return url;
                }},
                {field: 'enableName',width: 60, className: 'text-c', description: '显示'},
                {field: 'lockedName',width: 60,  className: 'text-c', description: '显示'},
                {field: 'orders',width: 60,  className: 'text-c', description: '排序'},
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
        tableManager.GM("setQuery", query);
    }
</script>
</hy:extends>
<hy:extends name="body">
<div class="search-block">
	<form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
		<div class="layui-form-item">
			<div class="layui-inline">
				<label class="layui-form-label">菜单名称</label>
				<div class="layui-input-inline">
					<input type="text" name="name_li" 
						placeholder="请输入菜单名称"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">菜单别名</label>
				<div class="layui-input-inline">
					<input type="text" name="alias_li" 
						placeholder="请输入菜单别名"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">菜单地址</label>
				<div class="layui-input-inline">
					<input type="text" name="url_li" 
						placeholder="请输入菜单地址"
						autocomplete="off" class="layui-input">
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">是否启用</label>
				<div class="layui-input-inline">
					<select name="enable_eq" lay-filter="enable">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
				</div>
			</div>
			<div class="layui-inline">
				<label class="layui-form-label">是否锁定</label>
				<div class="layui-input-inline">
					<select name="locked_eq" lay-filter="locked">
						<option value=""></option>
						<option value="0">否</option>
						<option value="1">是</option>
					</select>
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
	<div class="mt-5">
        <table id="dataGridList" class="table table-border table-bordered table-hover table-bg table-sort"></table>
    </div>
</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />