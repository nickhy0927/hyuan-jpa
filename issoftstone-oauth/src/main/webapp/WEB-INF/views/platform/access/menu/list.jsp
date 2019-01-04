<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
	    function initTable() {
	    	page.dataTable({
            	id: "tableList",
                elem: "#tableList",
                title: "用户数据表",
                filter: "tableList",
                searchForm: "search-form",
                toolbar: "#operate-btn",
                method: "POST",
                url: "${ctx}/platform/access/menu/list.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "name", title: "菜单名称", fixed: "left" , width: 160},
                    { field: "alias",  title: "菜单别名", fixed: "left" , width: 230},
                    { field: "parentName",  title: "上级菜单", fixed: "left" , width: 220},
                    { field: "localCode", title: "国际化编码", width: '20%'},
                    { field: "url", title: "访问地址", width: '25%', sort: true  },
                    { field: "enableName", align: "center",  title: "是否启用", width: 120},
                    { field: "lockedName", align: "center",  title: "是否锁定", width: 120},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operates",  width: 155}
                ]],
                groupBtn: {
                    add: function () {
                        page.openWindow({
                            title: "新增菜单",
                            url: "${ctx}/platform/access/menu/create.do"
                        });
                    },
                    del: function () {
                        console.log("del", "点击了删除");
                    },
                    search: function () {
                        console.log("search", "点击了搜索");
                    }
                }
            });
		}
        $(function() {
            initTable();
        })
    </script>
</hy:extends>
<hy:extends name="body">
	<div class="search-block">
        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">菜单名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">菜单别名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="alias" lay-verify="alias" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">菜单地址</label>
                    <div class="layui-input-inline">
                        <input type="text" name="url" lay-verify="url" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">是否启用</label>
                    <div class="layui-input-inline">
                        <select name="interest" lay-filter="aihao">
                            <option value="">请选择菜单状态</option>
                            <option value="0">否</option>
                            <option value="1" selected>是</option>
                        </select>
                    </div>
                </div>
            </div>
        </form>
    </div>
    <table id="tableList" lay-filter="tableList"></table>
    <div id="laypage"></div>
    <div style="display:none" class="layui-btn-container" id="operate-btn">
        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add">
            <i class="layui-icon">&#xe608;</i> 新增
        </button>
        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del">
            <i class="layui-icon">&#xe640;</i>删除
        </button>
        <button class="layui-btn layui-btn-sm" lay-event="search">
            <i class="layui-icon">&#xe615;</i>搜索
        </button>
    </div>
    <div style="display:none" id="operates">
        <a class="layui-btn layui-btn-xs" lay-event="edit">
            <i class="layui-icon">&#xe642;</i>编辑
        </a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">
            <i class="layui-icon">&#xe640;</i>删除
        </a>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />