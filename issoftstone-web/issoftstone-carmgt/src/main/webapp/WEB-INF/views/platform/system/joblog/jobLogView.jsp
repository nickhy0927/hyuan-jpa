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
                $.ajax({
			    	url: '${ctx}/platform/access/dict/dictEdit.json',//发送请求
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
		$(function () {
			var text = '${jobLog.result}'; //获取json格式内容
			try {
				document.getElementById('result').innerHTML =  JSON.stringify(JSON.parse(text), null, 4);
			} catch (e) {
				document.getElementById('result').innerHTML = text;
			}
		})
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
                    	   value="${dictCode}"
                           readonly="readonly"
                           placeholder="请输入字典编号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />