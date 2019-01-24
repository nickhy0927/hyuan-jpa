<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">用户列表</hy:extends>
<hy:extends name="css">
	<style>
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/user/list.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "nickName", title: '用户姓名', width: 120, fixed: "left", unresize: true},
                    { field: "loginName",  title: "登录账户", width: 120, align: 'left', fixed: "left", unresize: true},
                    { field: "brithday",  title: "用户生日", width: 120, align: 'center'},
                    { field: "email",  title: "电子邮箱", width: 180},
                    { field: "enableName",  title: "启用", width: 80, align: 'center'},
                    { field: "lockedName",  title: "锁定", width: 80, align: 'center'},
                    { field: "lastLoginTime",  title: "最后登录时间", width: 130},
                    { field: "userTag",  title: "手机标识"},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 120, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '修改用户信息',
							height: '460px',
							width: '70%',
							url: '${ctx}/platform/access/user/edit.do?id=' + data.id 
						})
					},
					delAction: function (tableInstance, data) {
						console.log(data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '新增用户信息',
							height: '520px',
							width: '70%',
							url: '${ctx}/platform/access/user/create.do'
						})
					},
					deleteAction: function (tableInstance, data) {
						console.log(data);
						$.deleteInfo({
							url: '${ctx}/platform/access/user/delete.json',//发送请求
					    	data: data,
					    	loadMsg: '正在删除用户信息，请稍等...', 
					    	success: function (res) {
					    		refresh();
							}
						})
						/* tableInstance.reload({
							where : $("#search-form").getForm()
						}); */
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
	        <a class="btn btn-secondary-outline radius size-S" lay-event="editAction">
	        	<i class="Hui-iconfont Hui-iconfont-edit"></i>
	        </a>&nbsp;&nbsp;
	        <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />