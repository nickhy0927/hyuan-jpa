<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品类别修改</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			layui.use(['form'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(edit-form)', function (data) {
                	$.saveInfo({
                		url: '${ctx}/productmgt/productType/productTypeEditUpdate.json',//发送请求
				    	data: $('form').getForm(),
				    	success: function(result) {
	                		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		    				parent.layer.close(index); //再执行关闭
							window.parent.refresh();
				    	}
					})
                	return false;
                });
                $.ajax({
			    	url: '${ctx}/productmgt/productType/productTypeEditJson.json',//发送请求
			    	data: {id : '${id}'},
			    	success: function(res) {
			    		form.val("edit-form", {
		    			  	"id": res.content['id'],
		    			  	"name": res.content['name'],
		    			  	"remark": res.content['remark'],
		    			})
			    	}
                });
            });
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
        <form class="layui-form layui-form-pane">
        	<input type="hidden" name="id" value="${productType.id}">
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>类别名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="name"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入类别名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>备注
                </label>
                <div class="layui-input-block">
                    <input type="text" name="remark"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入备注"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right; bottom: 0;position: absolute; right: 0; margin-right: 10px;">
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