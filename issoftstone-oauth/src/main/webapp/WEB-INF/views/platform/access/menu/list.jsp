<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
	    function initTable() {
	    	$('table').dataTable({
    			gridManagerName: 'menuList',
   				height: 'auto',
   				ajax_data: '${ctx}/platform/access/menu/list.json',
   				query: {
   					pluginId: 1
   				},
   				pageSize: 10,
   				columnData: [{
   					key: 'name',
   					remind: '菜单显示的名称',
   					text: '菜单名称',
   					align: 'left',
   					sorting: 'DESC'
   				},{
   					key: 'alias',
   					align: 'left',
   					remind: '菜单别名',
   					text: '菜单别名'
   				},{
   					key: 'parentName',
   					remind: '上级菜单名称',
   					text: '上级菜单',
   					align: 'left',
   				},{
   					key: 'localCode',
   					remind: '国际化编码，进行国际化使用',
   					align: 'left',
   					text: '国际化编码'
   				},{
   					key: 'url',
   					remind: '菜单访问地址',
   					text: '访问地址',
   					align: 'left',
   				},{
   					key: 'enableName',
   					remind: '菜单是否启用状态',
   					align: 'center',
   					text: '是否启用'
   				},{
   					key: 'lockedName',
   					remind: '菜单是否锁定状态',
   					text: '是否锁定',
   					align: 'center',
   				},{
   					key: 'action',
   					width: "160px",
   					align: 'center',
   					text: '操作',
   					template: function(action, rowObject){
   						return '<div id="operates">' + 
			   				        '<a class="layui-btn layui-btn-xs" lay-event="edit">' +
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
	    		var form = layui.form;
    			//监听提交
	    		form.on('submit(formDemo)', function(data){
	    			tableManager.GM("setQuery", data.field);
	    		    return false;
	    		});
    		});
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
            <div class="layui-form-item">
            	<div class="layui-btn-container" id="operate-btn">
			        <button class="layui-btn layui-btn-sm layui-btn-normal" lay-event="add">
			            <i class="layui-icon">&#xe608;</i> 新增
			        </button>
			        <button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del">
			            <i class="layui-icon">&#xe640;</i>删除
			        </button>
			        <button class="layui-btn layui-btn-sm" lay-submit lay-filter="formDemo">
			            <i class="layui-icon">&#xe615;</i>搜索
			        </button>
			    </div>
            </div>
        </form>
    </div>
    <section class="grid-main">
        <table id="tableList"></table>
    </section>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />