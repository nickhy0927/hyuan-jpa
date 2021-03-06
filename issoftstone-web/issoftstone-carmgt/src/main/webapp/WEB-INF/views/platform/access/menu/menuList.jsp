<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
        layui.use(['form'], function () {
        	var form = layui.form;
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                title: '菜单管理列表',
                searchForm: 'search-form',
                url: "${ctx}/platform/access/menu/menuList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "name", title: '菜单名称', width: 260, fixed: "left", unresize: true},
                    { field: "alias",  title: "菜单别名", width: 200, fixed: "left", unresize: true},
                    { field: "parentName", title: "上级菜单", width: 220, unresize: true},
                    { title: "图标", width: 80, unresize: true, align: 'center', templet: function (d) {
						return d.icon ? d.icon.iconClass : "";
					}},
                    { field: "url", title: "访问地址",minWidth: 380},
                    { field: "localCode", title: "国际化编码", minWidth: 160},
                    { field: "enable",  title: "启用", width: 110, align: 'center', templet: function (d) {
						var checked = d.enable == 1 ? "checked='checked'" : "";
						return '<input data-v=' + d.version + ' ' + checked + ' data-id=' + d.id + ' type="checkbox" lay-skin="switch" lay-filter="enable" lay-text="停用|启用">';
					}},
					{ field: "locked",  title: "锁定", width: 110, align: 'center', templet: function (d) {
						var checked = d.locked == 0 ? "checked='checked'" : "";
						return '<input data-v=' + d.version + ' ' + checked+ ' data-id=' + d.id + ' type="checkbox" lay-skin="switch" lay-filter="locked" lay-text="锁定|解锁">';
					}},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 130, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '修改菜单',
							height: '450px',
							width: '800px',
							url: '${ctx}/platform/access/menu/menuEdit.do?id=' + data[0].id
						})
					},
					delAction: function (tableInstance, data) {
						console.log("del=== ", data)
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增菜单',
							height: '450px',
							width: '800px',
							url: '${ctx}/platform/access/menu/menuCreate.do'
						})
					},
					deleteAction: function (tableInstance, data) {
						$.openTip('你确定删除吗？', false, function () {
							$.ajax({
								url:'${ctx}/platform/access/menu/menuDelete.json',
								data: {id: data.join(",")},
								success: function (res) {
									console.log(res);
									$.openTip(res.message, true, function () {
										layer.closeAll();
										refresh();
									})
								}
							})
						})
					},
					searchAction: function (tableInstance) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
                }
            });
        	form.on('switch(enable)', function (data) {
				var enable = this.checked ? '1' : '0';
				$.saveInfo({
					url: '${ctx}/platform/access/menu/menuStatusUpdate.json',
					data: {id: $(data.elem).attr('data-id'), enable: enable, version: $(data.elem).attr('data-v')},
					success: function (res) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
				})
			})
            form.on('switch(locked)', function (data) {
				var locked = this.checked ? '0' : '1';
				$.saveInfo({
					url: '${ctx}/platform/access/menu/menuStatusUpdate.json',
					data: {id: $(data.elem).attr('data-id'), locked: locked, version: $(data.elem).attr('data-v')},
					success: function (res) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
				})
			})
		})
    </script>
</hy:extends>
<hy:extends name="body">
	<div id="view"></div>
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">菜单名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="name_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	                <div class="layui-inline">
	                    <label class="layui-form-label">菜单别名</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="alias_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	                <div class="layui-inline">
	                    <label class="layui-form-label">菜单地址</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="url_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	                <div class="layui-inline">
	                    <label class="layui-form-label">是否启用</label>
	                    <div class="layui-input-inline">
	                        <select name="enable_eq" lay-filter="enable">
	                            <option value="">请选择</option>
	                            <option value="0">否</option>
	                            <option value="1">是</option>
	                        </select>
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<table id="tableList" lay-filter="tableList"></table>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增菜单
	        </button>
	        <button class="btn btn-danger radius" lay-event="deleteAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>搜索
        	</button>
	    </div>
	    <div style="display:none" id="operateBar">
	        <a class="btn btn-secondary-outline radius" lay-event="editAction">
	        	<i class="Hui-iconfont Hui-iconfont-edit"></i>
	        </a>
	        <a class="btn btn-danger-outline radius" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />