<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        $(function() {
            page.dataTable({
                id: "tableList",
                elem: "#tableList",
                title: "用户数据表",
                filter: "tableList",
                toolbar: "#toolbarDemo",
                url: "${ctx}/data/menu.json",
                page: true, //开启分页
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "username", title: "用户名", fixed: "left" , width: 140},
                    { field: "email",  title: "邮箱", fixed: "left" , width: 220},
                    { field: "sex", title: "性别", width: 80, sort: true  },
                    { field: "sign", title: "签名", width: '38.5%'},
                    { field: "city", title: "城市", width: 140},
                    { field: "ip", title: "IP", width: 140},
                    { field: "joinTime", title: "加入时间", width: 140},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#barDemo",  width: 120}
                ]],
                parseData: function(res) {
                    console.log("res", res);
                    var toolH = $('.layui-table-tool').height();
                    var pageH = $('.layui-table-page').height();
                    $(".layui-table-box").height($(document).height() - 60 - toolH - pageH).css({
                        "overflow-y":"auto"
                    })
                    //将原始数据解析成 table 组件所规定的数据
                    return {
                        code: res.status, //解析接口状态
                        msg: res.message, //解析提示文本
                        count: res.total, //解析数据长度
                        data: res.rows.item //解析数据列表
                    };
                }
            });
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <table id="tableList" lay-filter="tableList"></table>
    <div style="display:none" class="layui-btn-container" id="toolbarDemo">
        <button class="layui-btn layui-btn-sm" lay-event="getCheckData">获取选中行数据</button>
        <button class="layui-btn layui-btn-sm" lay-event="getCheckLength">获取选中数目</button>
        <button class="layui-btn layui-btn-sm" lay-event="isAll">验证是否全选</button>
    </div>
    <div style="display:none" id="barDemo">
        <a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
        <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />