<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="basePath" />
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function() {
			$('#tableList').datagrid({
				url : '${basePath}/platform/access/role/list.json',
				type : 'POST',
				iconCls: 'icon-view', //图标
	            datatype: "json",
	            width: "auto",
	            height: "auto",
	            rownumbers: true,
	            /* loadMsg: "数据加载中，请稍后...", */
	            fitCloumns: true,
	            nowrap: true,
	            pagination: true,
	            singleSelect: true,
		        fitcolumns: true,
				toolbar : "#tb",
				columns : [ [ {
					field : 'code',
					title : '角色编号',
				},{
					field : 'name',
					title : '角色名称'
				}, {
					field : 'parentName',
					title : '上级角色',
				}, {
					field : 'remark',
					title : '角色备注'
				}] ],
				onLoadSuccess: function (data) {
	                //datagrid头部 table 的第一个tr 的td们，即columns的集合
	                var headerTds = $(".datagrid-header-inner table tr:first-child").children();
	                //datagrid主体 table 的第一个tr 的td们，即第一个数据行
	                var bodyTds = $(".datagrid-body table tr:first-child").children();
	                var totalWidth = 0; //合计宽度，用来为datagrid头部和主体设置宽度
	                //循环设置宽度
	                bodyTds.each(function (i, obj) {
	                    var headerTd = $(headerTds.get(i));
	                    var bodyTd = $(bodyTds.get(i));
	                    var headerTdWidth = headerTd.width(); //获取第i个头部td的宽度
	                    //这里加5个像素 是因为数据主体我们取的是第一行数据，不能确保第一行数据宽度最宽，预留5个像素。有兴趣的朋友可以先判断最大的td宽度都在进行设置
	                    var bodyTdWidth = bodyTd.width();
	                    var width = 0;
	                    //如果头部列名宽度比主体数据宽度宽，则它们的宽度都设为头部的宽度。反之亦然
	                    if (headerTdWidth > bodyTdWidth) {
	                        width = headerTdWidth;
	                        bodyTd.width(width);
	                        headerTd.width(width);
	                        totalWidth += width;
	                    } else {
	                        width = bodyTdWidth;
	                        headerTd.width(width);
	                        bodyTd.width(width);
	                        totalWidth += width;
	                    }
	                });
	            }
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
        </form>
    </div>
	<table id="tableList" class="easyui-datagrid" style="width:100%;height:auto"></table>
	<div id="tb">
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
</hy:extends>
<jsp:include page="/page/basepage.jsp" />