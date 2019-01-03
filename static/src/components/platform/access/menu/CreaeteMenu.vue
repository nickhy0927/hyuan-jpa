<template>
    <div class="create-form">
        <form class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <label class="layui-form-label">菜单名称</label>
                <div class="layui-input-block">
                    <input type="text" name="name"
                           lay-verify="required"
                           placeholder="请输入菜单名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">菜单别名</label>
                <div class="layui-input-block">
                    <input type="text" name="name"
                           required lay-verify="required|alias" placeholder="请输入菜单别名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">访问地址</label>
                <div class="layui-input-block">
                    <input type="text" name="url"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">是否显示</label>
                <div class="layui-input-inline">
                    <input type="radio" name="enable" value="0" title="否">
                    <input type="radio" name="enable" value="1" title="是" checked>
                </div>
                <label class="layui-form-label">是否锁定</label>
                <div class="layui-input-inline">
                    <input type="radio" name="locked" value="0" title="否" checked>
                    <input type="radio" name="locked" value="1" title="是">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">国际化编码</label>
                <div class="layui-input-block">
                    <input type="text" name="localCode"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">上级菜单</label>
                <div class="layui-input-block">
                    <select name="city" lay-verify="required">
                        <option value="">请选择上级菜单</option>
                        <option value="0">北京</option>
                        <option value="1">上海</option>
                        <option value="2">广州</option>
                        <option value="3">深圳</option>
                        <option value="4">杭州</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">选择图标</label>
                <div class="layui-input-block">
                    <select name="city" lay-verify="required">
                        <option value="">请选择菜单图标</option>
                        <option value="0">北京</option>
                        <option value="1">上海</option>
                        <option value="2">广州</option>
                        <option value="3">深圳</option>
                        <option value="4">杭州</option>
                    </select>
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">立即提交</button>
                    <button type="reset" @click="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</template>

<script>
    export default {
        name: "CraeteMenu",
        mounted() {
            layui.use('form', function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {
                    layer.msg(JSON.stringify(data.field));
                    return false;
                });
                form.verify({
                    alias: [/^[a-zA-Z0-9_-]{4,16}$/, '别名由字母，数字，下划线，减号组成'],
                    secondpwd: function (value) {
                        if (value != $("#firstpwd").val()) {
                            $("#secondpwd").val("");
                            return '确认密码与密码不一致';
                        }
                    },
                });
            });
        },
        methods: {
            reset() {
                layui.use('form', function () {
                    var form = layui.form;
                    form.render();
                });
            }
        }
    };
</script>

<style scoped>
</style>