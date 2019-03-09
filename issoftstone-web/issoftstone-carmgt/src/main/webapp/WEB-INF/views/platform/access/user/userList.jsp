<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">用户列表</hy:extends>
<hy:extends name="css">
	<style type="text/css">
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/platform/access/user/userDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除用户信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable();
				}
			})
		}
    	layui.use(['form'], function () {
            var form = layui.form;
        	form.on('switch(enable)', function (data) {
				var enable = this.checked ? '1' : '0';
				$.saveInfo({
					url: '${ctx}/platform/access/user/userStatusUpdate.json',
					data: {id: $(data.elem).attr('data-id'), enable: enable, version: $(data.elem).attr('data-v')},
					success: function (res) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
				})
			});
        	form.on('switch(locked)', function (data) {
				var locked = this.checked ? '0' : '1';
				$.saveInfo({
					url: '${ctx}/platform/access/user/userStatusEdit.json',
					data: {id: $(data.elem).attr('data-id'), locked: locked, version: $(data.elem).attr('data-v')},
					success: function (res) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
				})
			})
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/user/userList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "nickName", title: '用户姓名', width: 160, fixed: "left", unresize: true},
                    { field: "loginName",  title: "登录账户", width: 160, align: 'left', fixed: "left", unresize: true},
                    { field: "brithday",  title: "用户生日", width: 120, align: 'center'},
                    { field: "email",  title: "电子邮箱", width: 180},
                    { field: "enable",  title: "启用", width: 110, align: 'center', templet: function (d) {
						var checked = d.enable == 1 ? "checked='checked'" : "";
						return '<input data-v=' + d.version + ' ' + checked + ' data-id=' + d.id + ' type="checkbox" lay-skin="switch" lay-filter="enable" lay-text="停用|启用">';
					}},
					{ field: "locked",  title: "锁定", width: 110, align: 'center', templet: function (d) {
						var checked = d.locked == 0 ? "checked='checked'" : "";
						return '<input data-v=' + d.version + ' ' + checked+ ' data-id=' + d.id + ' type="checkbox" lay-skin="switch" lay-filter="locked" lay-text="锁定|解锁">';
					}},
                    { field: "lastLoginTime",  title: "最后登录时间", minWidth: 130},
                    { field: "userTag",  title: "手机标识", minWidth: 200},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 130, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '修改用户信息',
							height: '460px',
							width: '70%',
							url: '${ctx}/platform/access/user/userEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					addRoleAction: function (tableInstance, data) {
						$.openWindow({
							title: '<span style="font-weight:bold;">添加用户【<span style="color:red;">' + data[0].nickName + '</span>】的权限信息</span>',
							height: '90%',
							width: '70%',
							url: '${ctx}/platform/access/user/userRoleList.do?userId=' + data[0].id 
						})
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增用户信息',
							height: '520px',
							width: '70%',
							url: '${ctx}/platform/access/user/userCreate.do'
						})
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
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">姓名</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="nick_Name_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	                <div class="layui-inline">
	                    <label class="layui-form-label">登录账户</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="loginName_li" autocomplete="off" class="layui-input">
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
    		<table id="tableList" lay-filter="tableList"></table>
    	</div>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增用户
	        </button>
	        <button class="btn btn-danger radius" lay-event="deleteAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>搜索
        	</button>
	    </div>
	    <div style="display:none" id="operateBar">
	        <div class="operate-bar">
	       		<a title="修改用户信息" class="btn btn-secondary-outline radius size-S" lay-event="editAction">
		        	<i class="Hui-iconfont Hui-iconfont-edit"></i>
		        </a>
		        <a title="删除用户信息" class="btn btn-danger-outline radius size-S" lay-event="delAction">
		        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
		        </a>
		        <a title="添加权限信息" class="btn btn-primary-outline radius size-S" lay-event="addRoleAction">
		        	<i class="Hui-iconfont Hui-iconfont-add"></i>
		        </a>
	        </div>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />