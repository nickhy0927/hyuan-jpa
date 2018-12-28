<template>
	<div class>{{message}}</div>
</template>

<script>
	export default {
		name: "User",
		data() {
			return {
				message: "用户列表"
			};
		},
		mounted() {
			console.log("列表");
		}
	};
</script>

<style scoped>
</style>