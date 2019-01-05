<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新闻系统</hy:extends>
<hy:extends name="css">
    <style type="text/css">
        body {
            overflow: hidden !important;
        }
        .layui-layout-admin .layui-body {
            bottom: 0px !important;
            overflow-y: hidden !important;
        }

    </style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        $(function () {
            layui.use("element", function () {
                $('#content').height($(document).height() - 60);
            });

            $(window).resize(function() {
                $('#content').height($(document).height() - 60);
            });
            $(".layui-nav-tree a").attr('href', 'javascript:void(0);');
            $(".layui-nav-tree a").each(function () {
                $(this).unbind('click').bind('click', function () {
                    var url = $(this).attr('data-url');
                    if (url) {
                        $('#content').attr('src', "${ctx}" + url)
                    }
                })
            })
        })

        function logout() {
            // layui.use("layer", function() {
            // 	layer.alert("", {
            // 	    title:"提示信息",
            // 		skin: "layui-layer-molv",
            // 		closeBtn: 1,
            // 		// anim: 5, // 动画类型
            // 		icon: 6 // icon
            // 	});
            // });
            $.openTip("退出系统成功", true, null);
        }
    </script>
</hy:extends>
<hy:extends name="body">
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
                    <a href="#" onclick="logout()">退出系统</a>
                </li>
            </ul>
        </div>
        <div class="layui-side">
            <div class="layui-side-scroll">
                <!-- 左侧导航区域（可配合layui已有的垂直导航） -->
                <ul class="layui-nav layui-nav-tree" lay-shrink="all" lay-filter="layui-nav-tree">
                    <li class="layui-nav-item">
                        <a href="#/index/task" data-url="/task">
                            <i class="layui-icon layui-icon-home"></i> 主页
                        </a>
                    </li>
                    <li class="layui-nav-item layui-nav-itemed">
                        <a class href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 权限管理
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a data-url="/platform/access/menu/list.do">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>菜单列表
                                </a>
                            </dd>
                            <dd>
                                <a href="#" data-url="/platform/access/role/list.do">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>角色列表
                                </a>
                            </dd>
                            <dd>
                                <a href="#/platform/access/user/userList">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>用户管理
                                </a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a class href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 监控管理
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="#/platform/system/optlog/optlogList">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>操作日志管理
                                </a>
                            </dd>
                            <dd>
                                <a href="#/platform/system/exception/exceptionLogList">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>异常日志管理
                                </a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a class href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 所有商品
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="#/list1">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>列表一
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:void(0);">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>列表二
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:void(0);">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>超链接
                                </a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 解决方案
                        </a>
                        <dl class="layui-nav-child">
                            <dd>
                                <a href="javascript:void(0);">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>列表一
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:void(0);">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>列表二
                                </a>
                            </dd>
                            <dd>
                                <a href="javascript:void(0);">
                                    <i class="fa fa-arrow-right" aria-hidden="true"></i>超链接
                                </a>
                            </dd>
                        </dl>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 云市场
                        </a>
                    </li>
                    <li class="layui-nav-item">
                        <a href="javascript:void(0);">
                            <i class="layui-icon layui-icon-app"></i> 发布商品
                        </a>
                    </li>
                </ul>
            </div>
        </div>
        <div class="layui-body">
            <!-- 内容主体区域 -->
            <iframe id="content" frameborder="0" width="100%" src="${ctx}/platform/access/menu/list.do"></iframe>
        </div>
        <!--<div class="layui-footer">
            © layui.com - 底部固定区域
        </div>-->
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>