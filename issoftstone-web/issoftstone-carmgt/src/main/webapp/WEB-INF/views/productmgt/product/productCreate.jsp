<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">产品新增</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		layui.use(['form', 'tree'], function () {
        	var form = layui.form;
            //监听提交
            form.on('submit(create-form)', function (data) {    
				$.saveInfo({
               		url: '${ctx}/productmgt/product/productCreateSave.json',//发送请求
			    	data: $('form').getForm(),
			    	success: function(result) {
                		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	    				parent.layer.close(index); //再执行关闭
						window.parent.refresh();
			    	}
				})
               	return false;
        	});
            
            $("#productType").click(function () {
            	$.parentOpenWindow({
        			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;请选择产品类别',
					height: '90%',
					width: '70%',
					url: '${ctx}/productmgt/product/createProductTypeList.do' 
				})
			})
        });
		
		function reset() {
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        }
		
		function setCheckData(data) {
			console.log("window set data", data);
			$('input[name="productTypeName"]').val(data.name)
			$('input[name="productTypeId"]').val(data.id)
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
                    <input type="text" name="productName"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入产品名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>产品类别
                </label>
                <div class="layui-input-block">
                    <input type="text" name="productTypeName"
                    	   id="productType"
                           lay-verify="required" readonly="readonly"
                           lay-verType="tips"
                           placeholder="请输入产品类别"
                           autocomplete="off" class="layui-input">
                    <input type="hidden" name="productTypeId" id="productTypeId">
                </div>
            </div>
        	<div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>成本价格
                </label>
                <div class="layui-input-block">
                    <input type="text" name="productCost"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入成本价格"
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