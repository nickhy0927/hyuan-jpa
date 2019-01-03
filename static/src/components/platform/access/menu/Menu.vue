<template>
	<div class="table-list">
		<div class="search-block">
			<form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
				<div class="layui-form-item">
					<div class="layui-inline">
						<label class="layui-form-label">菜单名称</label>
						<div class="layui-input-inline">
							<input type="text" name="name" lay-verify="name" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">菜单别名</label>
						<div class="layui-input-inline">
							<input type="text" name="alias" lay-verify="alias" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">菜单地址</label>
						<div class="layui-input-inline">
							<input type="text" name="url" lay-verify="url" autocomplete="off" class="layui-input">
						</div>
					</div>
					<div class="layui-inline">
						<label class="layui-form-label">启用</label>
						<div class="layui-input-inline">
							<select name="interest" lay-filter="aihao">
								<option value></option>
								<option value="0">否</option>
								<option value="1" selected>是</option>
							</select>
						</div>
					</div>
				</div>
			</form>
		</div>
		<table id="tableList" lay-filter="tableList"></table>
		<div style="display:none" class="layui-btn-container" id="operate-btn">
			<button
				class="layui-btn layui-btn-sm layui-btn-normal"
				lay-url="http://www.baidu.com"
				lay-event="add"
			>
				<i class="layui-icon">&#xe608;</i> 新增
			</button>
			<button class="layui-btn layui-btn-sm layui-btn-danger" lay-event="del">
				<i class="layui-icon">&#xe640;</i>删除
			</button>
			<button class="layui-btn layui-btn-sm" lay-event="search">
				<i class="layui-icon">&#xe615;</i>搜索
			</button>
		</div>
		<div style="display:none" id="operates">
			<a class="layui-btn layui-btn-xs" lay-event="edit">
				<i class="layui-icon">&#xe642;</i>编辑
			</a>
			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">
				<i class="layui-icon">&#xe640;</i>删除
			</a>
		</div>
	</div>
</template>

<script>
	import page from "../../../../utils/page.js";
	export default {
		name: "Menu",
		data() {
			return {
				message: "用户列表"
			};
		},
		mounted() {
			layui.use("form", function() {
				var form = layui.form;
				//表单初始赋值
				form.val("search-form", {
					interest: 1
				});
			});
			page.dataTable({
				id: "tableList",
				elem: "#tableList",
				title: "用户数据表",
				filter: "tableList",
				searchForm: "search-form",
				toolbar: "#operate-btn",
				groupBtn: {
					add: function() {
						page.openWindow({
							title: "新增菜单",
							url: "#/platform/access/menu/menuCreate",
							callback: function(params) {
								console.log("关闭时执行...");
							}
						});
					},
					del: function() {
						console.log("del", "点击了删除");
					},
					search: function() {
						console.log("search", "点击了搜索");
					}
				},
				url: "/data/user.json",
				page: true, //开启分页
				cols: [
					[
						{
							type: "checkbox",
							fixed: "left"
						},
						{
							field: "id",
							title: "ID",
							width: 80,
							fixed: "left",
							sort: true
						},
						{
							field: "username",
							title: "用户名",
							width: 120
						},
						{
							field: "email",
							title: "邮箱",
							width: 150
						},
						{
							field: "experience",
							title: "积分",
							sort: true
						},
						{
							field: "sex",
							title: "性别",
							width: 80
						},
						{
							field: "logins",
							title: "登入次数",
							width: 100,
							sort: true
						},
						{ field: "sign", title: "签名" },
						{ field: "city", title: "城市", width: 100 },
						{ field: "ip", title: "IP", width: 120 },
						{ field: "joinTime", title: "加入时间", width: 120 },
						{
							fixed: "right",
							title: "操作",
							align: "center",
							toolbar: "#operates",
							width: 145
						}
					]
				]
			});
		}
	};
</script>

<style scoped>
	.search-block {
		border: 1px solid #ddd;
		border-radius: 2px;
		padding: 10px 10px 0px 10px;
	}
	.layui-form-item {
		margin-bottom: 5px !important;
	}
</style>