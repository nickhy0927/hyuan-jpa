<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增数据库</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			layui.use(['form'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {    
                	$.saveInfo({
                		url: '${ctx}/platform/system/dataBase/dataBaseCreateSave.json',//发送请求
				    	data: $('form').getForm(),
				    	success: function(result) {
	                		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		    				parent.layer.close(index); //再执行关闭
							window.parent.refresh();
				    	}
					})
                	return false;
                });
            });
			$("#dataBaseType").select({
				dataSource: JSON.parse('${dataBaseType}'),
				holder: '请选择数据库类型',
			})
		})
		
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
        <form class="layui-form layui-form-pane" lay-filter="create-form">
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="dataBaseName" 
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入字数据库名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>驱动名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="driverClassName" 
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库驱动"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库IP
                </label>
                <div class="layui-input-block">
                    <input type="text" name="ip" 
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库IP"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
           	<div class="layui-form-item">
                <label class="layui-form-label"><i></i>是否启用</label>
                <div class="layui-input-inline">
                    <input type="radio" name="enable" value="1" title="是" checked>
                    <input type="radio" name="enable" value="0" title="否">
                </div>
                <label class="layui-form-label"><i></i>使用unicode</label>
                <div class="layui-input-inline">
                    <input type="radio" name="useUnicode" value="1" title="是" checked>
                    <input type="radio" name="useUnicode" value="0" title="否">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库类型
                </label>
                <div class="layui-input-block">
                	<select name="dataBaseType" id="dataBaseType" lay-verType="tips" lay-verify="required">
                		<option value="">请选择数据库类型</option>
                	</select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库端口
                </label>
                <div class="layui-input-block">
                    <input type="text" name="port"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库端口"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库用户名
                </label>
                <div class="layui-input-block">
                    <input type="text" name="username"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库用户名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库密码
                </label>
                <div class="layui-input-block">
                    <input type="password" name="password"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库密码"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>数据库字符集
                </label>
                <div class="layui-input-block">
                    <input type="password" name="characterEncoding"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入数据库字符集"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">
                    	<i class="Hui-iconfont Hui-iconfont-save"></i>&nbsp;立即保存
                    </button>
                    <button type="reset" onclick="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />