<template>
	<div class="create-form">
		<form class="layui-form layui-form-pane">
			<div class="layui-form-item">
				<label class="layui-form-label">图标名称</label>
				<div class="layui-input-block">
					<input
						type="text"
						name="name"
						required
						lay-verify="required"
						placeholder="请输入图标名称"
						autocomplete="off"
						class="layui-input"
					>
				</div>
			</div>
			<div class="layui-form-item">
				<label class="layui-form-label">图标样式</label>
				<div class="layui-input-block">
					<input
						type="text"
						name="name"
						required
						lay-verify="required|alias"
						placeholder="请输入图标样式"
						autocomplete="off"
						class="layui-input"
					>
				</div>
			</div>
			<div class="layui-form-item" style="text-align: right">
				<div class="layui-input-block">
					<button class="layui-btn" lay-submit lay-filter="create-form">确定保存</button>
					<button type="reset" @click="reset()" class="layui-btn layui-btn-primary">重置</button>
				</div>
			</div>
		</form>
	</div>
</template>

<script>
	export default {
		name: "IconCreate",
		mounted() {
			layui.use("form", function() {
				var form = layui.form;
				//监听提交
				form.on("submit(create-form)", function(data) {
					layer.msg(JSON.stringify(data.field));
					return false;
				});
				form.verify({
					alias: [
						/^[a-zA-Z0-9_-]{4,16}$/,
						"别名由字母，数字，下划线，减号组成"
					],
					secondpwd: function(value) {
						if (value != $("#firstpwd").val()) {
							$("#secondpwd").val("");
							return "确认密码与密码不一致";
						}
					}
				});
			});
		},
		methods: {
			reset() {
				layui.use("form", function() {
					var form = layui.form;
					form.render();
				});
			}
		}
	};
</script>

<style scoped>
</style>