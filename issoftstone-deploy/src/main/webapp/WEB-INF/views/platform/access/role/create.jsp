<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增菜单</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			$("body").css({
				'overflow':'auto'
			});
			layui.use('form', function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {
                	$.openLoading('正在保存数据，请稍等...');
                	$.ajax({
				    	type: 'POST',
				    	url: '${ctx}/platform/access/role/save.json',//发送请求
				    	data: data.field,
				    	dataType : "json",
				    	success: function(result) {
				    		setTimeout(function() {
				    			$.tip(result, true, function() {
				    				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
				    				parent.layer.close(index); //再执行关闭
									window.parent.initTable();
								})
							}, 3000);
			    		}
			    	});
                    /* layer.msg(JSON.stringify(data.field));
                    return false; */
                	return false;
                });
                form.verify({
                    alias: [/^[a-zA-Z0-9_-]{4,16}$/, '别名由字母，数字，下划线，减号组成']
                });
            });
		});
		
		function reset() {
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        }
	</script>
</hy:extends>
<hy:extends name="body">
	<div class="create-form">
        <form class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>菜单名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="name"
                           lay-verify="required"
                           placeholder="请输入菜单名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>菜单别名</label>
                <div class="layui-input-block">
                    <input type="text" name="alias"
                           required lay-verify="required|alias" placeholder="请输入菜单别名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>访问地址</label>
                <div class="layui-input-block">
                    <input type="text" name="url"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i></i>是否显示</label>
                <div class="layui-input-inline">
                    <input type="radio" name="enable" value="0" title="否">
                    <input type="radio" name="enable" value="1" title="是" checked>
                </div>
                <label class="layui-form-label"><i></i>是否锁定</label>
                <div class="layui-input-inline">
                    <input type="radio" name="locked" value="0" title="否" checked>
                    <input type="radio" name="locked" value="1" title="是">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>国际化编码</label>
                <div class="layui-input-block">
                    <input type="text" name="localCode"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i></i>上级菜单</label>
                <div class="layui-input-block">
                    <select name="city">
                        <option value="">请选择上级菜单</option>
                        <option value="0">北京</option>
                        <option value="1">上海</option>
                        <option value="2">广州</option>
                        <option value="3">深圳</option>
                        <option value="4">杭州</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>选择图标</label>
                <div class="layui-input-block">
                    <select name="city" lay-verify="required">
                        <option value="">请选择菜单图标</option>
                        <option value="0">北京</option>
                        <option value="1">上海</option>
                        <option value="2">广州</option>
                        <option value="3">深圳</option>
                        <option value="4">杭州</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">立即提交</button>
                    <button type="reset" onclick="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />