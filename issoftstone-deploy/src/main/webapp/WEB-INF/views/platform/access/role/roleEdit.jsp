<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增图标</hy:extends>
<hy:extends name="css">
	<style type="text/css">
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		layui.use(['form', 'tree', 'laydate'], function () {
			$("body").css({
				'overflow':'auto'
			})
	        var form = layui.form;
	        //监听提交
	        form.on('submit(create-form)', function (data) {    
	        	$.saveInfo({
	        		url: '${ctx}/platform/access/role/roleEditUpdate.json',//发送请求
			    	data: $('form').getForm(),
			    	loadMsg: '正在保存角色，请稍等...', 
			    	success: function (res) {
			    		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	    				parent.layer.close(index); //再执行关闭
	    				window.parent.refresh();
					}
	        	})
	        	return false;
	        });
	        $.ajax({
		    	url: '${ctx}/platform/access/role/roleEditJson.json',//发送请求
		    	data: {id : '${id}'},
		    	success: function(res) {
		    		form.val("edit-form", {
	    			  	"id": res.content['id'],
	    			  	"name": res.content['name'],
	    			  	"version": res.content['version'],
	    			  	"code": res.content['code'],
	    			  	"remark": res.content['remark'],
	    			})
		    	}
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
        <form class="layui-form layui-form-pane" lay-filter="edit-form">
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>角色编号
                </label>
                <div class="layui-input-block">
                	<input type="hidden" id="id" name="id">
                	<input name="version" type="hidden" id="version"/>
                    <input type="text" name="code" required="required"
                           lay-verify="required"
                           lay-verType="tips" readonly="readonly"
                           placeholder="请输入角色编号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>角色名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="name" required="required"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入角色名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i></i>备注</label>
                <div class="layui-input-block">
			      	<textarea name="remark" style="resize:none" 
			      		placeholder="请输入内容" class="layui-textarea"></textarea>
			    </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">
                    	<i class="Hui-iconfont Hui-iconfont-save"></i>&nbsp;立即提交
                    </button>
                    <button type="reset" onclick="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />