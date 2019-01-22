<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增图标</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		 .downpanel .layui-select-title span {
            line-height: 38px;
        }
 
        /*继承父类颜色*/
        .downpanel dl dd:hover {
            background-color: inherit;
        }
        body {
            height: 100%;
            width: 100%;
            background-size: cover;
            margin: 0 auto;
        }
        td {
            font-size: 12px !important;
        }

        .layui-form-checkbox span {
            height: 30px;
        }
        .layui-field-title {
            border-top: 1px solid white;
        }
        table {
            width: 100% !important;
        }
        
	</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			$("body").css({
				'overflow':'auto'
			})
			layui.use(['form', 'tree'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {    
                	// console.log($('form').serializeObject())
                	$.ajax({
                		url: '${ctx}/platform/access/icon/save.json',//发送请求
				    	data: $('form').getForm(),
				    	openType: 'alert',
				    	success: function(result) {
	                		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		    				parent.layer.close(index); //再执行关闭
							window.parent.refresh();
				    	}
					})
                	return false;
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
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>图标名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="name" required="required"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入图标名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>样式名称</label>
                <div class="layui-input-block">
                    <input type="text" name="className"
                    	lay-verType="tips"
                           required lay-verify="required" placeholder="请输入样式名称"
                           autocomplete="on" class="layui-input">
                </div>
            </div>
            <!-- <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>图标用法</label>
                <div class="layui-input-block">
                    <input type="text" name="iconClass"
                    	lay-verType="tips"
                           required lay-verify="required" placeholder="请输入图标别名"
                           autocomplete="on" class="layui-input">
                </div>
            </div> -->
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