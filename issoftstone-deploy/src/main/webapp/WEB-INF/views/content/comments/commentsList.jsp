<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">栏目分类列表</hy:extends>
<hy:extends name="css">
	<style></style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/content/comments/commentsDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除栏目分类信息信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable()
				}
			})
		}
    	layui.use(['form'], function () {
            var form = layui.form;
        	form.on('switch(enable)', function (data) {
				var enable = this.checked ? '1' : '0';
				$.saveInfo({
					url: '${ctx}/content/comments/commentsStatusUpdate.json',
					data: {id: $(data.elem).attr('data-id'), enable: enable, version: $(data.elem).attr('data-v')},
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
                url: "${ctx}/content/comments/commentsList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "reviewer",  title: "评论人", minWidth: 160},
                    { field: "title",  title: "评论标题", minWidth: 160},
                    { field: "content",  title: "评论内容", minWidth: 160},
                    { field: "commentDate",  title: "提交日期", minWidth: 160},
                    { field: "news",  title: "新闻标题", minWidth: 160},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 110, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
                			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改栏目分类信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/content/comments/commentsEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增栏目分类信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/content/comments/commentsCreate.do'
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
	                    <label class="layui-form-label">评论标题</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="title" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">评论日期</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="commentDate" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">新闻标题</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="news" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<table id="tableList" lay-filter="tableList"></table>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增栏目分类
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
	        </a>
	        <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />