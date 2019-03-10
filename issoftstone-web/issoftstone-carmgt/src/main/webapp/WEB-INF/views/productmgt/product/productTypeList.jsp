<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品类别列表</hy:extends>
<hy:extends name="css">
	<style></style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	layui.use(['form', 'table'], function () {
            var form = layui.form;
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/productmgt/product/createProductTypePage.json",
                cols: [[
                    { type: "radio", fixed: "left" },
                    { field: "name",  title: "类别名称", minWidth: 160},
                    { field: "remark",  title: "备注", minWidth: 160}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
                			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改产品类别信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/productmgt/productType/productTypeEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	getCheckData: function (tableInstance, data) {
                		var iframe = parent.document.getElementsByTagName('iframe')[0];
                		if (data.length == 0) {
                			layer.alert('请选择产品类型', {
               				  	icon: 1,
               				})
							return;
						}
                		iframe.contentWindow.setCheckData(data[0]);
                		var index = parent.window.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
                		layer.alert(index);
                		parent.window.layer.close(index); //再执行关闭
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
	                    <label class="layui-form-label">类别名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="name_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">备注</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="remark_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<table id="tableList" lay-filter="tableList"></table>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="getCheckData">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>确定选择
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