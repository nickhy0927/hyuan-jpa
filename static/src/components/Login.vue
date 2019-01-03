<template>
	<div id="container">
		<div class="admin-login-background">
			<!-- <div class="admin-header">
				<img src="/favicon.ico" class="admin-logo">
			</div>-->
			<form class="layui-form" id="login">
				<div>
					<i class="layui-icon layui-icon-username admin-icon admin-icon-username"></i>
					<input
						type="text"
						name="username"
						placeholder="请输入用户名"
						autocomplete="off"
						class="layui-input admin-input admin-input-username"
					>
				</div>
				<div>
					<i class="layui-icon layui-icon-password admin-icon admin-icon-password"></i>
					<input
						type="password"
						name="password"
						placeholder="请输入密码"
						autocomplete="off"
						class="layui-input admin-input"
					>
				</div>
				<div>
					<input
						type="text"
						name="verify"
						placeholder="请输入验证码"
						autocomplete="off"
						class="layui-input admin-input admin-input-verify"
					>
					<img class="admin-captcha" src onclick="updateVerify()">
				</div>
				<button class="layui-btn admin-button" lay-submit lay-filter="loginForm">登陆</button>
			</form>
		</div>
	</div>
</template>

<script>
	export default {
		name: "Login",
		mounted() {
			var formObj = $("#login").serializeArray();
			console.log("formObj", formObj);
			let _this = this;
			layui.use(["form", "layer"], function() {
				var form = layui.form;
				var layer = layui.layer;
				form.on("submit(loginForm)", function(data) {
					// console.log(data.elem); //被执行事件的元素DOM对象，一般为button对象
					// console.log(data.form); //被执行提交的form对象，一般在存在form标签时才会返回
					console.log(data.field); //当前容器的全部表单字段，名值对形式：{name: value}
					layer.msg("正在登录，请稍等...", {
						icon: 16,
						shade: [0.6, "#8a8484"],
						time: false //取消自动关闭
					});
					if (
						data.field.username != "admin" &&
						data.field.password != "123456"
					) {
						layer.closeAll();
						layer.msg("用户名或密码错误，请重新输入");
						return false;
					}
					setTimeout(() => {
						layer.closeAll();
						layer.msg("登录成功..");
						_this.$router.push({ path: "/" });
					}, 3000);
					return false; //阻止表单跳转。如果需要表单跳转，去掉这段即可。
				});
			});
		},
		methods: {
			login: function() {}
		}
	};
</script>

<style scoped>
	body {
		background-color: #e7e7e7;
	}

	input:-webkit-autofill {
		-webkit-box-shadow: inset 0 0 0 1000px #fff;
		background-color: transparent;
	}

	.admin-login-background {
		width: 300px;
		height: 300px;
		position: absolute;
		left: 50%;
		top: 50%;
		margin-left: -150px;
		margin-top: -100px;
	}

	.admin-header {
		margin-top: -100px;
		margin-bottom: 20px;
	}

	.admin-logo {
		width: 280px;
	}

	.admin-button {
		margin-top: 20px;
	}

	.admin-input {
		border-top-style: none;
		border-right-style: solid;
		border-bottom-style: solid;
		border-left-style: solid;
		height: 50px;
		width: 300px;
		padding-bottom: 0px;
	}

	.admin-input-username {
		border-top-style: solid;
		border-radius: 10px 10px 0 0;
	}

	.admin-input-verify {
		border-radius: 0 0 10px 10px;
	}

	.admin-button {
		width: 300px;
		height: 50px;
		border-radius: 4px;
		background-color: #2d8cf0;
	}

	.admin-icon {
		margin-left: 260px;
		margin-top: 10px;
		font-size: 30px;
	}

	i {
		position: absolute;
	}

	.admin-captcha {
		position: absolute;
		margin-left: 205px;
		margin-top: -40px;
	}
</style>