<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">博客列表</hy:extends>
<hy:extends name="css">
    <style></style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        function deleteInfo(tableInstance, data) {
            $.deleteInfo({
                url: '${ctx}/blog/article/articleDelete.json',//发送请求
                data: data,
                loadMsg: '正在删除博客信息信息，请稍等...',
                success: function () {
                    $("#tableList").refreshTable()
                }
            })
        }

        layui.use(['form'], function () {
            var form = layui.form;
            form.on('switch(enable)', function (data) {
                var enable = this.checked ? '1' : '0';
                $.saveInfo({
                    url: '${ctx}/blog/article/articleStatusUpdate.json',
                    data: {id: $(data.elem).attr('data-id'), enable: enable, version: $(data.elem).attr('data-v')},
                    success: function (res) {
                        $("#tableList").refreshTable({
                            where: $("#search-form").getForm()
                        });
                    }
                })
            });
            $("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/blog/article/articleList.json",
                cols: [[
                    {type: "checkbox", fixed: "left"},
                    {field: "title", title: "博客标题", minWidth: 160},
                    {field: "profile", title: "博客简介", minWidth: 160},
                    {
                        field: "tags", title: "tags", minWidth: 160, templet: function (d) {
                            console.log('tags', d);
                            return "";
                        }
                    },
                    {fixed: "right", title: "操作", align: "center", toolbar: "#operateBar", width: 140, unresize: true}
                ]],
                operate: {
                    editAction: function (tableInstance, data) {
                        $.openWindow({
                            title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改博客信息',
                            height: '90%',
                            width: '80%',
                            url: '${ctx}/blog/article/articleEdit.do?id=' + data[0].id
                        })
                    },
                    viewAction: function (tableInstance, data) {
                        $.openWindow({
                            title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改博客信息',
                            height: '90%',
                            width: '80%',
                            url: '${ctx}/blog/article/articleEdit.do?id=' + data[0].id
                        })
                    },
                    delAction: function (tableInstance, data) {
                        deleteInfo(tableInstance, data);
                    }
                },
                groupBtn: {
                    createAction: function () {
                        $.openWindow({
                            title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增博客信息',
                            height: '90%',
                            width: '80%',
                            url: '${ctx}/blog/article/articleCreate.do'
                        })
                    },
                    deleteAction: function (tableInstance, data) {
                        deleteInfo(tableInstance, data);
                    },
                    searchAction: function () {
                        $("#tableList").refreshTable({
                            where: $("#search-form").getForm()
                        });
                    }
                }
            });
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
        <div class="search-block">
            <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
                <div class="layui-form-item">
                    <div class="layui-inline">
                        <label class="layui-form-label">博客标题</label>
                        <div class="layui-input-inline">
                            <input type="text" name="title_eq" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">博客简介</label>
                        <div class="layui-input-inline">
                            <input type="text" name="profile_eq" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-inline">
                        <label class="layui-form-label">博客标签</label>
                        <div class="layui-input-inline">
                            <input type="text" name="tags.tag_li" autocomplete="off" class="layui-input">
                        </div>
                    </div>
                </div>
            </form>
        </div>
        <hr class="hr-line">
        <table id="tableList" lay-filter="tableList"></table>
        <div style="display:none" class="layui-btn-container" id="tableBar">
            <button class="	btn btn-primary radius" lay-event="createAction">
                <i class="Hui-iconfont Hui-iconfont-add2"></i>新增博客
            </button>
            <button class="btn btn-danger radius" lay-event="deleteAction">
                <i class="Hui-iconfont Hui-iconfont-del2"></i>批量删除
            </button>
            <button class="btn btn-success radius" lay-event="searchAction">
                <i class="Hui-iconfont Hui-iconfont-search"></i>
                搜索
            </button>
        </div>
        <div style="display:none" id="operateBar">
            <a class="btn btn-secondary-outline radius size-S" lay-event="editAction">
                <i class="Hui-iconfont Hui-iconfont-edit"></i>
            </a>
            <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
                <i class="Hui-iconfont Hui-iconfont-del2"></i>
            </a>
            <a class="btn btn-danger-outline radius size-S" lay-event="delAction">
                <i class="Hui-iconfont Hui-iconfont-del2"></i>
            </a>
        </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>