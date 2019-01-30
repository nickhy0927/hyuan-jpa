<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">字典信息列表</hy:extends>
<hy:extends name="css">
	<style>
		pre, xmp {
		    padding: 3px 5px 0 5px;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/platform/access/dict/dictDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除字典信息信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable()
				}
			})
		}
        $(function() {
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/dict/dictList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "dictCode", title: '字典编号', width: 180, fixed: "left", unresize: true},
                    { field: "dictName",  title: "字典名称", width: 160},
                    { field: "dictValue",  title: "字典值", width: 100},
                    { field: "dictType",  title: "类型名称", width: 260},
                    { field: "enable",  title: "启用", align: "center", width: 80, templet: function (d) {
						if (d.enable) {
							return '<span class="label label-success radius">是</span>'
						}
						return '<span class="label label-warning radius">否</span>'
					}},
                    { field: "remarks",  title: "字典描述", align: "left", minWidth: 200},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 120, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '修改字典信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/platform/access/dict/dictEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '新增字典信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/platform/access/dict/dictCreate.do'
						})
					},
					deleteAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					searchAction: function (tableInstance) {
						tableInstance.reload({
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
	                    <label class="layui-form-label">字典名称</label>
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
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增字典
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