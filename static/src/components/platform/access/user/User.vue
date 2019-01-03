<template>
	<div class="table-list">
		<table id="tableList" lay-filter="tableList"></table>
		<div style="display:none" class="layui-btn-container" id="toolbarDemo">
			<button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
			<button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
			<button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
		</div>
		<div style="display:none" id="barDemo">
			<a class="layui-btn layui-btn-xs" lay-event="edit" lay-callback="delete()">编辑</a>
			<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
		</div>
	</div>
</template>

<script>
	import page from "../../../../utils/page.js";
	export default {
		name: "User",
		data() {
			return {
				message: "用户列表"
			};
		},
		mounted() {
			console.log("page", page);
			page.dataTable({
				id: "tableList",
				elem: "#tableList",
				title: "用户数据表",
				filter: "tableList",
				toolbar: "#toolbarDemo",
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
							toolbar: "#barDemo",
							width: 110
						}
					]
				]
			});
		}
	};
</script>

<style scoped>
</style>