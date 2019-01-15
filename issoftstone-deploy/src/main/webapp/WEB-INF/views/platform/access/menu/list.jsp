<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        $(function() {
            page.dataTable({
            	elem: '#tableList',
                title: "用户数据表",
                filter: "tableList",
                loading: true,
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/menu/list.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "name", title: '菜单名称', width: '10%', fixed: "left", unresize: true},
                    { field: "alias",  title: "菜单别名", width: '10%', fixed: "left", unresize: true},
                    { field: "parentName", title: "上级菜单", width: '10%', unresize: true},
                    { title: "图标", width: '7%', unresize: true, align: 'center', templet: function (d) {
						return d.icon ? d.icon.iconClass : "";
					}},
                    { field: "url", title: "访问地址", width: '24.5%'},
                    { field: "localCode", title: "国际化编码", width: '15%'},
                    { field: "enableName", title: "显示", width: '8%', align: 'center', unresize: true},
                    { field: "lockedName", title: "锁定", width: '8%', align: 'center', unresize: true},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: '14%', unresize: true}
                ]],
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '新增菜单',
							height: '450px',
							width: '90%',
							url: '${ctx}/platform/access/menu/create.do'
						})
					},
					deleteAction: function () {
						$.openWindow({
							title: '修改图标',
							height: '230px',
							width: '700px',
							url: '${ctx}/platform/access/icon/edit.do?id=' + id
						})
					},
					searchAction: function () {
						alert('搜索菜单')
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
	                            <option value="">请选择菜单状态</option>
	                            <option value="0">否</option>
	                            <option value="1" selected>是</option>
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
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增
	        </button>
	        <button class="btn btn-danger radius" lay-event="deleteAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>   
        		搜索
        	</button>
	    </div>
	    <div style="display:none" id="operateBar">
	        <a class="layui-btn layui-btn-xs" lay-event="editAction">
	        	<i class="Hui-iconfont Hui-iconfont-edit"></i>编辑
	        </a>
	        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>删除
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />