<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        var config = {
            gridManagerName: 'roleList',
            ajax_data: '${ctx}/platform/access/role/list.json',
            pageSize:10,
            columnData: [{
                key: 'code',
                remind: '角色编号',
                text: '角色编号',
                align: 'left',
                sorting: 'DESC',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + nodeData + '</span>'
                }
            },{
                key: 'name',
                remind: '角色名称',
                align: 'left',
                text: '名称',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + nodeData + '</span>'
                }
            },{
                key: 'parentName',
                remind: '上级角色名称',
                align: 'left',
                text: '上级角色',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + nodeData + '</span>'
                }
            },{
                key: 'remark',
                remind: '描述角色的详细信息',
                align: 'left',
                text: '角色描述',
                template: function (nodeData, rowData) {
                    return '<span class="td-content" title="' + nodeData + '">' + nodeData + '</span>'
                }
            },{
                key: 'action',
                width: 120,
                align: 'center',
                text: '操作',
                template: function(action, rowObject){
                    return '<span class="plugin-action edit-action" learnLink-id="'+rowObject.id+'">编辑</span>'
                          +'<span class="plugin-action del-action" learnLink-id="'+rowObject.id+'">删除</span>';
                }
            }]
        };
        function initTable() {
            $('table').dataTable(config)
        }
        $(function () {
            initTable();
            layui.use('form', function(){
                $('#search').on('click', function () {
                    var query = $("#search-form").serializeObject();
                    console.log(query)
                    tableManager.GM("setQuery", query);
                })
            });
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="search-block">
        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
            <div class="layui-form-item">
                <div class="layui-inline">
                    <label class="layui-form-label">角色名称</label>
                    <div class="layui-input-inline">
                        <input type="text" name="name_li" lay-verify="name" autocomplete="off" class="layui-input">
                    </div>
                </div>
                <div class="layui-inline">
                    <label class="layui-form-label">菜单别名</label>
                    <div class="layui-input-inline">
                        <input type="text" name="alias_li" lay-verify="alias" autocomplete="off" class="layui-input">
                    </div>
                </div>
            </div>
        </form>
    </div>
    <hr class="hr-line">
    <section class="grid-main">
        <div class="layui-form-item">
            <div class="layui-btn-container" id="operate-btn">
                <button class="layui-btn layui-btn-sm layui-btn-normal" id="add">
                    <i class="layui-icon">&#xe608;</i> 新增
                </button>
                <button class="layui-btn layui-btn-sm layui-btn-danger" id="del">
                    <i class="layui-icon">&#xe640;</i>删除
                </button>
                <button class="layui-btn layui-btn-sm" id="search">
                    <i class="layui-icon">&#xe615;</i>搜索
                </button>
            </div>
        </div>
        <table id="tableList"></table>
    </section>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>