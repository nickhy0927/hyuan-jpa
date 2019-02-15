<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增菜单</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			layui.use(['form'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(edit-form)', function (data) {
                	$.saveInfo({
                		url: '${ctx}/platform/access/dict/dictEditUpdate.json',//发送请求
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
			    	url: '${ctx}/platform/access/dict/dictEditJson.json',//发送请求
			    	data: {id : '${id}'},
			    	success: function(res) {
			    		form.val("edit-form", {
		    			  	"id": res.content['id'],
		    			  	"dictCode": res.content['dictCode'],
		    			  	"dictName": res.content['dictName'],
		    			  	"dictType": res.content['dictType'],
		    			  	"dictValue": res.content['dictValue'],
		    			  	"pid": res.content['dict'] ? res.content['dict'].id : '',
		    			  	"version": res.content['version'],
		    			  	"enable": res.content['enable'],
		    			  	"remarks": res.content['remarks'],
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
        <form class="layui-form layui-form-pane" lay-filter="edit-form">
        	<input name="id" type="hidden" id="id">
        	<input name="version" type="hidden" id="version"/>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>字典编号
                </label>
                <div class="layui-input-block">
                    <input type="text" name="dictCode" 
                    	   required="dictCode"
                    	   value="${dictCode}"
                           lay-verify="required"
                           lay-verType="tips"
                           readonly="readonly"
                           placeholder="请输入字典编号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>字典名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="dictName" 
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入字典名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>字典类型
                </label>
                <div class="layui-input-block">
                	<select name="pid" id="pid" lay-verType="tips" lay-verify="required">
                		<option value="">请选择字典类型</option>
                		<c:forEach var="dict" items="${dictTypeList}">
                			<option value="${dict.id}">${dict.dictName}</option>
                		</c:forEach>
                	</select>
                </div>
            </div>
           	<div class="layui-form-item">
                <label class="layui-form-label"><i></i>是否启用</label>
                <div class="layui-input-inline">
                    <input type="radio" name="enable" value="1" title="是" checked>
                    <input type="radio" name="enable" value="0" title="否">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>字典值
                </label>
                <div class="layui-input-block">
                    <input type="text" name="dictValue"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入字典值"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>字典描述</label>
                <div class="layui-input-block">
                    <input type="text" name="remarks" 
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入字典描述"
                           autocomplete="off" class="layui-input">
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