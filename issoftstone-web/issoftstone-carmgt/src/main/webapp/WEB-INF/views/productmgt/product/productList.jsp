<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品列表</hy:extends>
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
				url: '${ctx}/productmgt/product/productDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除产品信息信息，请稍等...', 
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
					url: '${ctx}/productmgt/product/productStatusUpdate.json',
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
                url: "${ctx}/productmgt/product/productList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "productName",  title: "产品名称", minWidth: 160},
                    { field: "productTypeName",  title: "产品类别", minWidth: 160},
                    { field: "productCost",  title: "成本价格", minWidth: 160},
                    { field: "remark",  title: "备注", minWidth: 160},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 110, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
                			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改产品信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/productmgt/product/productEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增产品信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/productmgt/product/productCreate.do'
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
	                    <label class="layui-form-label">产品名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="productName_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">产品类别</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="productType_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">成本价格</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="productCost_eq" autocomplete="off" class="layui-input">
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
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增产品
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