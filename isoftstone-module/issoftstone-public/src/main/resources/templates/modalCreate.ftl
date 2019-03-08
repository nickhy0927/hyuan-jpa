[#ftl encoding="utf-8" strict_syntax=true]
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${r'${pageContext.request.contextPath}'}" var="ctx"></c:set>
<hy:extends name="title">${desc}新增</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			layui.use(['form', 'tree'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {    
                	$.saveInfo({
                		url: '${r'${ctx}'}/${pathSuffix}/${domainObjectName?lower_case}/${domainObjectName?lower_case}CreateSave.json',//发送请求
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
        	[#list columns as name]
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>${name}
                </label>
                <div class="layui-input-block">
                    <input type="text" name="${name}"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入${name}"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	[/#list]
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