<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
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
			url: '${ctx}/platform/access/menu/edit.do?id=' + id
		})
		return false;
	}
	function initTable() {
		$('table').dataTable({
			gridManagerName: 'menuList',
			height: 'auto',
			ajax_data: '${ctx}/platform/access/menu/list.json',
			query: {
				pluginId: 1
			},
			showFooterCheckedInfo: true,
			columnData: [{
				key: 'name',
				remind: '菜单显示的名称',
				text: '菜单名称',
				align: 'left',
				sorting: 'DESC',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
                }
			},{
				key: 'alias',
				align: 'left',
				remind: '菜单别名',
				text: '菜单别名',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
                }
			},{
				key: 'parentName',
				remind: '上级菜单名称',
				text: '上级菜单',
				align: 'left',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
                }
			},{
				key: 'localCode',
				remind: '国际化编码，进行国际化使用',
				align: 'left',
				text: '国际化编码',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
                }
			},{
				key: 'url',
				remind: '菜单访问地址',
				disableCustomize: true,
				text: '访问地址',
				align: 'left',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + (nodeData ? nodeData : '') + '</span>'
                }
			},{
				key: 'enableName',
				remind: '菜单是否启用状态',
				align: 'center',
                width: 80,
				text: '启用'
			},{
				key: 'lockedName',
				remind: '菜单是否锁定状态',
				text: '锁定',
				width: 80,
				align: 'center',
			},{
				key: 'orders',
				text: '排序',
				width: 60,
				align: 'center',
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
	<table id="tableList"></table>
</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />