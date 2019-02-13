<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增调度任务</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		layui.use(['form'], function () {
	        var form = layui.form;
	        //监听提交
	        form.on('submit(create-form)', function (data) {    
	        	$.saveInfo({
	        		url: '${ctx}/platform/access/autoTask/autoTaskSave.json',//发送请求
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
                	<i>*</i>任务名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="taskName" 
                           lay-verify="required"
                           required="required"
                           lay-verType="tips"
                           placeholder="请输入任务名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>实现类名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="className" 
                           lay-verify="required"
                           required="required"
                           lay-verType="tips"
                           placeholder="请输入任务实现类名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
           	<div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>是否运行</label>
                <div class="layui-input-block">
                    <input type="radio" name="startStatus" value="0" title="手动执行" checked>
                    <input type="radio" name="startStatus" value="1" title="立即执行" >
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>任务表达式
                </label>
                <div class="layui-input-block">
                    <input type="text" name="scheduler"
                           lay-verify="required"
                           required="required"
                           lay-verType="tips"
                           placeholder="请输入任务表达式"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>任务描述</label>
                <div class="layui-input-block">
                    <input type="text" name="remarks" 
                           lay-verify="required"
                           lay-verType="tips"
                           maxlength="256"
                           placeholder="请输入调度任务描述"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">
                    	<i class="Hui-iconfont Hui-iconfont-save"></i>&nbsp;保存执行
                    </button>
                    <button type="reset" onclick="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />