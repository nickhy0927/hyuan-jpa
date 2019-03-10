<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品出库列表</hy:extends>
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
				url: '${ctx}/productmgt/store/storeDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除产品出库信息信息，请稍等...', 
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
					url: '${ctx}/productmgt/store/storeStatusUpdate.json',
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
                url: "${ctx}/productmgt/store/storeList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "product",  title: "产品名称", minWidth: 160},
                    { field: "productCode",  title: "产品编号", minWidth: 160},
                    { field: "enterStatue",  title: "登记方式", minWidth: 160},
                    { field: "changNumber",  title: "登记数量", minWidth: 160},
                    { field: "productQulity",  title: "质量等级", minWidth: 160},
                    { field: "storeDate",  title: "出/入库日期", minWidth: 160},
                    { field: "storeFrorto",  title: "来源/去向", minWidth: 160},
                    { field: "storeReason",  title: "出/入库理由", minWidth: 160},
                    { field: "storeNumber",  title: "库存数量", minWidth: 160},
                    { field: "remark",  title: "备注", minWidth: 160},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 110, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
                			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改产品出库信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/productmgt/store/storeEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增产品出库信息',
							height: '360px',
							width: '60%',
							url: '${ctx}/productmgt/store/storeCreate.do'
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
	                        <input type="text" name="product_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">产品编号</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="productCode_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">登记方式</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="enterStatue_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">登记数量</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="changNumber_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">质量等级</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="productQulity_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">出/入库日期</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="storeDate_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">来源/去向</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="storeFrorto_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">出/入库理由</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="storeReason_eq" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
				  	<div class="layui-inline">
	                    <label class="layui-form-label">库存数量</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="storeNumber_eq" autocomplete="off" class="layui-input">
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
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增产品出库
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