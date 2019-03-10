<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品出库新增</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
			layui.use(['form', 'tree'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {    
                	$.saveInfo({
                		url: '${ctx}/productmgt/store/storeCreateSave.json',//发送请求
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
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>产品名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="product"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入产品名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>产品编号
                </label>
                <div class="layui-input-block">
                    <input type="text" name="productCode"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入产品编号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>登记方式
                </label>
                <div class="layui-input-block">
                    <input type="text" name="enterStatue"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入登记方式"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>登记数量
                </label>
                <div class="layui-input-block">
                    <input type="text" name="changNumber"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入登记数量"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>质量等级
                </label>
                <div class="layui-input-block">
                    <input type="text" name="productQulity"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入质量等级"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>出/入库日期
                </label>
                <div class="layui-input-block">
                    <input type="text" name="storeDate"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入出/入库日期"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>来源/去向
                </label>
                <div class="layui-input-block">
                    <input type="text" name="storeFrorto"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入来源/去向"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>出/入库理由
                </label>
                <div class="layui-input-block">
                    <input type="text" name="storeReason"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入出/入库理由"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>库存数量
                </label>
                <div class="layui-input-block">
                    <input type="text" name="storeNumber"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入库存数量"
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