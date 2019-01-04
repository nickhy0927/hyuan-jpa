<template>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo">新闻发布系统</div>
			<!-- 头部区域（可配合layui已有的水平导航） -->
			<ul class="layui-nav layui-layout-left">
				<li class="layui-nav-item">
					<a href="#">控制台</a>
				</li>
				<li class="layui-nav-item">
					<a href="#">商品管理</a>
				</li>
				<li class="layui-nav-item">
					<a href="#">用户</a>
				</li>
				<li class="layui-nav-item">
					<a href="javascript:void(0);">其它系统</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="#">
								<i class="fa fa-envelope-o fa-fw"></i>邮件管理
							</a>
						</dd>
						<dd>
							<a href="#">
								<i class="fa fa-wechat fa-fw"></i>消息管理
							</a>
						</dd>
					</dl>
				</li>
			</ul>
			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item">
					<a href="javascript:void(0);">
						<img src="http://t.cn/RCzsdCq" class="layui-nav-img" alt>
						系统管理员
					</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="#">基本资料</a>
						</dd>
						<dd>
							<a href="#">安全设置</a>
						</dd>
					</dl>
				</li>
				<li class="layui-nav-item">
					<a href="#" @click="logout">退出系统</a>
				</li>
			</ul>
		</div>
		<div class="layui-side">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="layui-nav-tree">
					<li class="layui-nav-item">
						<router-link to="/index/task">
							<i class="layui-icon layui-icon-home"></i> 主页
						</router-link>
					</li>
					<li class="layui-nav-item layui-nav-itemed">
						<a class href="javascript:void(0);">
							<i class="layui-icon layui-icon-app"></i> 权限管理
						</a>
						<dl class="layui-nav-child">
							<dd>
								<router-link to="/platform/access/icon/iconList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>图标列表
								</router-link>
							</dd>
							<dd>
								<router-link to="/platform/access/menu/menuList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>菜单列表
								</router-link>
							</dd>
							<dd>
								<router-link to="/platform/access/role/roleList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>角色列表
								</router-link>
							</dd>
							<dd>
								<router-link to="/platform/access/user/userList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>用户管理
								</router-link>
							</dd>
						</dl>
					</li>
					<li class="layui-nav-item">
						<a class href="javascript:void(0);">
							<i class="layui-icon layui-icon-app"></i> 监控管理
						</a>
						<dl class="layui-nav-child">
							<dd>
								<router-link to="/platform/system/optlog/optlogList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>操作日志管理
								</router-link>
							</dd>
							<dd>
								<router-link to="/platform/system/exception/exceptionLogList">
									<i class="fa fa-arrow-right" aria-hidden="true"></i>异常日志管理
								</router-link>
							</dd>
						</dl>
					</li>
				</ul>
			</div>
		</div>
		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<router-view/>
			</div>
		</div>
		<div class="layui-footer">
			<!-- 底部固定区域 -->
			© layui.com - 底部固定区域
		</div>
	</div>
</template>

<script>
	export default {
		name: "Index",
		mounted() {
			layui.use("element", function() {});
		},
		methods: {
			logout() {
				layui.use("layer", function() {
					let layer = layui.layer;
					layer.alert("退出系统成功", {
						skin: "layui-layer-molv",
						closeBtn: 1,
						// anim: 5, // 动画类型
						icon: 6 // icon
					});
				});
			}
		}
	};
</script>

<style scoped>
	* {
		font-size: 12px;
		/* font-family: Monaco, "Source Code Pro", serif; */
	}

	.layui-nav-tree {
		height: 100%;
		overflow-y: auto;
	}

	.layui-nav * {
		font-size: 12px;
	}

	@media screen and (min-width: 1600px) {
		* {
			font-size: 18px;
		}

		.layui-nav * {
			font-size: 18px !important;
		}
	}

	.layui-nav-tree::-webkit-scrollbar,
	.layui-body::-webkit-scrollbar {
		/*滚动条整体样式*/
		width: 5px; /*高宽分别对应横竖滚动条的尺寸*/
		height: 5px;
	}

	.layui-nav-tree::-webkit-scrollbar-thumb,
	.layui-body::-webkit-scrollbar-thumb {
		/*滚动条里面小方块*/
		border-radius: 10px;
		box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
		background: #3d86a8;
	}

	.layui-nav-tree::-webkit-scrollbar-track,
	.layui-body::-webkit-scrollbar-track {
		/*滚动条里面轨道*/
		box-shadow: inset 0 0 5px rgba(0, 0, 0, 0.2);
		border-radius: 10px;
		background: #ededed;
	}
	.layui-nav-child a {
		padding-left: 40px !important;
	}
	.layui-nav-item i {
		margin: 10px 10px 10px 0px;
	}
	.layui-layout-left .layui-nav-child a {
		padding-left: 10px !important;
	}
</style>
