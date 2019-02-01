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
		$(function () {
			$("body").css({
				'overflow':'auto'
			})
			layui.use(['form', 'tree', 'laydate'], function () {
                var form = layui.form;
                var laydate = layui.laydate;
                //执行一个laydate实例
                laydate.render({
                  	elem: '#brithday' //指定元素
                });
                //监听提交
                form.on('submit(create-form)', function (data) {    
                	$.saveInfo({
                		url: '${ctx}/platform/access/user/userSave.json',//发送请求
				    	data: $('form').getForm(),
				    	success: function (res) {
				    		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		    				parent.layer.close(index); //再执行关闭
		    				window.parent.refresh();
						}
                	})
                	return false;
                });
                $.ajax({
    		    	type: 'POST',
    		    	url: '${ctx}/platform/access/user/queryUserInfo.json',//发送请求
    		    	data: {id : '${id}'},
    		    	dataType : "json",
    		    	success: function(res) {
    		    		form.val("edit-form", {
    	    			  	"id": res.content['id'],
    	    			  	"nickName": res.content['nickName'],
    	    			  	"loginName": res.content['loginName'],
    	    			  	"email": res.content['email'],
    	    			  	"enable": res.content['enable'],
    	    			  	"locked": res.content['locked'],
    	    			  	"version": res.content['version'],
    	    			  	"remark": res.content['remark'],
    	    			  	"brithday": res.content['brithday'],
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
        <form lay-filter="edit-form" class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>用户姓名
                </label>
                <div class="layui-input-block">
                	<input id="id" name="id" type="hidden">
                	<input name="version" type="hidden" id="version"/>
                    <input type="text" name="nickName" required="required"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入用户姓名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>登录账户</label>
                <div class="layui-input-block">
                    <input type="text" name="loginName"
                    	lay-verType="tips"
                    	readonly="readonly"
                           required lay-verify="required" placeholder="请输入登录账户"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>用户邮箱</label>
                <div class="layui-input-block">
                    <input type="email" name="email"
                    	lay-verType="tips"
                           required="required"
                           lay-verify="required" placeholder="请输入确认密码"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>用户生日</label>
                <div class="layui-input-block">
                    <input type="text" name="brithday"
                    	lay-verType="tips"
                    	id="brithday"
                    		readonly="readonly"
                           required="required"
                           lay-verify="required" placeholder="请选择用户生日"
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