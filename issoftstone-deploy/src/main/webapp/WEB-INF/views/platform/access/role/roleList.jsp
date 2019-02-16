<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		.xtree_contianer {
			border: 1px solid #e6e6e6;
			overflow: auto;
			background-color: #fff;
			padding: 10px 0 25px 5px;
		}
		.title-nav {
			height: 42px;
			background: #f2f2f2;
			margin-top: 10px;
			line-height: 38px;
			padding: 10px 0px;
		}
		.title-nav .title {
			float: left;
			padding: 5px 10px;
		}
		.title-nav button {
			float: right;
			margin-top: 10px;
			margin-right: 10px;
		}
		.layui-table-view {
			float: left;
		}
		.ztree-form, .xtree_contianer {
			width: 98%;
			height: 100%;
			margin-left: 2px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable()
		}
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/platform/access/role/delete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除角色信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable()
				}
			})
		}
        
        var zTreeObj, setting = {
        	check: {
                enable: true,
                chkStyle: "checkbox",
                chkboxType:  { "Y" : "ps", "N" : "ps" }
            },
            data: {
                simpleData: {
                    enable: true
                }
            }
		}
        function initTree(roleId) {
        	$.ajax({
        		url: '${ctx}/platform/access/menu/menuTreeList.json',
        		method: 'GET',
        		data: {id: (roleId ? roleId : "")},
        		success: function (res) {
        			zTreeObj = $.fn.zTree.init($("#tree"), setting, res);
				}
        	})
		}
        
        $(function() {
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/role/roleList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "code", title: '角色编号', width: 200, fixed: "left", unresize: true},
                    { field: "name", title: '角色名称', width: 160, fixed: "left", unresize: true},
                    { field: "remark", title: "备注", minWidth: 300},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 165, unresize: true},
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改角色信息',
							height: '300px',
							width: '70%',
							url: '${ctx}/platform/access/role/roleEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					addMenuAction: function (tableInstance, data) {
						$("#roleId").val(data[0].id)
						initTree(data[0].id)
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增角色信息',
							height: '300px',
							width: '70%',
							url: '${ctx}/platform/access/role/roleCreate.do'
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					deleteAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					searchAction: function (tableInstance) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
                }
            });
            
            var height = $(window).height();
            var viewH = $('.layui-table-view').height();
            if (height < viewH) height = viewH
            $('.xtree_contianer').height(height - 164);
			$('.ztree-form').height(height - 164);
			$('#saveRole').click(function () {
				var nodes = zTreeObj.getCheckedNodes(true);
				var roleId = $('#roleId').val();
				var ids = [];
				for (var i = 0; i < nodes.length; i++) ids.push(nodes[i].id);
				$.saveInfo({
                	url: '${ctx}/platform/access/role/roleMenuSave.json',
                	data: {roleId: roleId, menuIds: ids.join(',')},
                	loadMsg: '正在保存权限, 请稍等...',
                	success: function (res) {
               			refresh();
                		initTree(roleId)
					}
                })
			})
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
    	<div class="layui-row">
		    <div class="layui-col-xs9">
		      	<table id="tableList" lay-filter="tableList"></table>
		    </div>
		    <div class="layui-col-xs3">
		    	<div class="title-nav" style="padding: 5px 0px;">
	    			<div class="title">
	    				菜单列表
	    			</div>
	    			<button class="btn btn-primary radius" id="saveRole">
			        	<i class="Hui-iconfont Hui-iconfont-save"></i>保存权限
			        </button>
    			</div>
		      	<form class="layui-form ztree-form">
					<ul id="tree" class="ztree xtree_contianer" style="overflow:auto;"></ul>
					<input type="hidden" value="" name="roleId" id="roleId">
				</form>
		    </div>
		</div>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增角色
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
         	<a title="添加权限信息" class="btn btn-primary-outline radius" lay-event="addMenuAction">
	        	<i class="Hui-iconfont Hui-iconfont-add"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />