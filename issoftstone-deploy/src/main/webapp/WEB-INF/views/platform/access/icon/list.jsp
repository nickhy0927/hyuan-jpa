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
    	function refresh() {
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
                    { title: "使用方法", width: '7%', unresize: true, width: '49%', templet: function (d) {
						return '<pre><xmp>' + d.iconClass + '</xmp></pre>';
					}},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: '10%', unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '修改图标',
							height: '240px',
							width: '70%',
							url: '${ctx}/platform/access/icon/edit.do?id=' + data.id 
						})
					},
					delAction: function (tableInstance, data) {
						console.log("del=== ", data)
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '新增图标',
							height: '200px',
							width: '70%',
							url: '${ctx}/platform/access/icon/create.do'
						})
					},
					deleteAction: function (tableInstance, data) {
						console.log(tableInstance, data);
					},
					searchAction: function (tableInstance) {
						tableInstance.reload({
							where : $("#search-form").getForm()
						});
					}
                }
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
	                    <label class="layui-form-label">图标名称</label>
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