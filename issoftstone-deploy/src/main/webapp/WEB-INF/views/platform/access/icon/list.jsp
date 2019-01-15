<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="css">
	<style>
		pre, xmp {
		    padding: 3px 5px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        $(function() {
            page.dataTable({
            	elem: '#tableList',
                title: "图标列表",
                filter: "tableList",
                loading: true,
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/icon/list.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "name", title: '图标名称', width: '13%', fixed: "left", unresize: true},
                    { field: "icon",  title: "图标", width: '10%', align: 'center', fixed: "left", unresize: true},
                    { field: "className",  title: "图标样式", width: '20%', fixed: "left", unresize: true},
                    { title: "使用方法", width: '7%', unresize: true, width: '45%', templet: function (d) {
						return '<pre><xmp>' + d.iconClass + '</xmp></pre>';
					}},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: '14%', unresize: true}
                ]]
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