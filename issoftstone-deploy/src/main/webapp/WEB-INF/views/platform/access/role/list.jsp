<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		page.dataTable({
            	elem: '#tableList',
                title: "用户数据表",
                filter: "tableList",
                loading: true,
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/role/list.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "code", title: '角色编号', width: '15%', fixed: "left", unresize: true},
                    { field: "name", title: '角色名称', width: '15%', fixed: "left", unresize: true},
                    { field: "parentName", title: "上级角色", width: '15%', unresize: true},
                    { field: "remark", title: "备注", width: '40%'},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: '15%', unresize: true}
                ]]
            });
		}
        $(function() {
            refresh();
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">角色名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="name_li" autocomplete="off" class="layui-input">
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
	         <a class="btn btn-secondary-outline radius size-S">
	        	<i class="Hui-iconfont Hui-iconfont-edit"></i>
	        </a>&nbsp;&nbsp;
	        <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />