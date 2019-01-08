<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="basePath"></c:set>
<hy:extends name="title">新闻系统</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		.navbar {
            min-height: 0px;
        }
        .Hui-aside .menu_dropdown dd li a {
            padding-left: 25px;
            font-size: 14px;
        }
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript" src="${basePath}/assets/lib/jquery.contextmenu/jquery.contextmenu.r2.js"></script>
	<script type="text/javascript">
		$(function () {
	        $("body").Huitab({
	            tabBar:".navbar-wrapper .navbar-levelone",
	            tabCon:".Hui-aside .menu_dropdown",
	            className:"current",
	            index:0
	        });
	    });
	    /*个人信息*/
	    function myselfinfo() {
	        layer.open({
	            type: 1,
	            area: ['600px', '200px'],
	            fix: true, //不固定
	            shade: 0.2,
	            title: '查看信息',
	            content: '<div>管理员信息</div>'
	        });
	    }
     </script> 
</hy:extends>
<hy:extends name="body">
	<header class="navbar-wrapper">
        <div class="navbar navbar-fixed-top">
            <div class="container-fluid cl">
                <span class="logo navbar-slogan f-l mr-10 hidden-xs"></span>
                <a aria-hidden="false" class="nav-toggle Hui-iconfont visible-xs" href="javascript:;">&#xe667;</a>
                <nav class="nav navbar-nav">
                    <ul class="cl">
                    	<c:forEach items="${menus}" var="menu" varStatus="index">
                    		<li class="navbar-levelone <c:if test="${index.index ==0}">current</c:if>">
	                           <a href="javascript:;">
	                              	${menu.name}
	                           </a>
	                       	</li>
                    	</c:forEach>
                    </ul>
                </nav>
                <nav id="Hui-userbar" class="nav navbar-nav navbar-userbar hidden-xs">
                    <ul class="cl">
                        <li>超级管理员</li>
                        <li class="dropDown dropDown_hover">
                            <a href="#" class="dropDown_A">${username} <i class="Hui-iconfont">&#xe6d5;</i></a>
                            <ul class="dropDown-menu menu radius box-shadow">
                                <li><a href="${basePath}/logout">退出</a></li>
                            </ul>
                        </li>
                        <li id="Hui-skin" class="dropDown right dropDown_hover">
                            <a href="javascript:;" class="dropDown_A" title="换肤">
                                <i class="Hui-iconfont" style="font-size:18px">&#xe62a;</i>
                            </a>
                            <ul class="dropDown-menu menu radius box-shadow">
                                <li><a href="javascript:;" data-val="bluet" title="默认（蓝色）">蓝色</a></li>
                                <li><a href="javascript:;" data-val="dark" title="黑色">黑色</a></li>
                                <li><a href="javascript:;" data-val="green" title="绿色">绿色</a></li>
                                <li><a href="javascript:;" data-val="red" title="红色">红色</a></li>
                                <li><a href="javascript:;" data-val="yellow" title="黄色">黄色</a></li>
                                <li><a href="javascript:;" data-val="orange" title="橙色">橙色</a></li>
                            </ul>
                        </li>
                    </ul>
                </nav>
            </div>
        </div>
    </header>
    <aside class="Hui-aside">
    	<c:forEach items="${menus}" var="menu" varStatus="m">
    		<div class="menu_dropdown" style="<c:if test="${m.index == 0}">display: block</c:if><c:if test="${m.index != 0}">display: none</c:if>">
    			<c:if test="${not empty menu.children}">
	   				<c:forEach items="${menu.children}" var="secord" varStatus="s">
	   					<dl>
			                <dt><i class="Hui-iconfont">&#xe616;</i> ${secord.name}<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
			                <dd>
			                    <ul>
			                        <c:if test="${not empty secord.children}">
			                        	<c:forEach items="${secord.children}" var="third">
			                        		<li>
					                        	<a data-href="${basePath}${third.url}" data-title="${third.name}" href="javascript:void(0)">${third.name}</a>
					                        </li>
			                        	</c:forEach>
			                        </c:if>
			                    </ul>
			                </dd>
			            </dl>
	   				</c:forEach>
	   			</c:if>
        	</div>
   		</c:forEach>
        <div class="menu_dropdown bk_2" style="display: none">
            <dl>
                <dt><i class="Hui-iconfont">&#xe616;</i> 资讯管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="article-list.html" data-title="资讯管理" href="javascript:void(0)">资讯管理</a></li>
                    </ul>
                </dd>
            </dl>
            <dl>
                <dt><i class="Hui-iconfont">&#xe613;</i> 图片管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="picture-list.html" data-title="图片管理" href="javascript:void(0)">图片管理</a></li>
                    </ul>
                </dd>
            </dl>
            <dl>
                <dt><i class="Hui-iconfont">&#xe620;</i> 产品管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="product-brand.html" data-title="品牌管理" href="javascript:void(0)">品牌管理</a></li>
                        <li><a data-href="product-category.html" data-title="分类管理" href="javascript:void(0)">分类管理</a></li>
                        <li><a data-href="product-list.html" data-title="产品管理" href="javascript:void(0)">产品管理</a></li>
                    </ul>
                </dd>
            </dl>
            <dl>
                <dt><i class="Hui-iconfont">&#xe622;</i> 评论管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="http://h-ui.duoshuo.com/admin/" data-title="评论列表" href="javascript:;">评论列表</a></li>
                        <li><a data-href="feedback-list.html" data-title="意见反馈" href="javascript:void(0)">意见反馈</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-member">
                <dt><i class="Hui-iconfont">&#xe60d;</i> 会员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="member-list.html" data-title="会员列表" href="javascript:;">会员列表</a></li>
                        <li><a data-href="member-del.html" data-title="删除的会员" href="javascript:;">删除的会员</a></li>
                        <li><a data-href="member-level.html" data-title="等级管理" href="javascript:;">等级管理</a></li>
                        <li><a data-href="member-scoreoperation.html" data-title="积分管理" href="javascript:;">积分管理</a></li>
                        <li><a data-href="member-record-browse.html" data-title="浏览记录" href="javascript:void(0)">浏览记录</a></li>
                        <li><a data-href="member-record-download.html" data-title="下载记录" href="javascript:void(0)">下载记录</a></li>
                        <li><a data-href="member-record-share.html" data-title="分享记录" href="javascript:void(0)">分享记录</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-admin">
                <dt><i class="Hui-iconfont">&#xe62d;</i> 管理员管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="admin-role.html" data-title="角色管理" href="javascript:void(0)">角色管理</a></li>
                        <li><a data-href="admin-permission.html" data-title="权限管理" href="javascript:void(0)">权限管理</a></li>
                        <li><a data-href="admin-list.html" data-title="管理员列表" href="javascript:void(0)">管理员列表</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-tongji">
                <dt><i class="Hui-iconfont">&#xe61a;</i> 系统统计<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="charts-1.html" data-title="折线图" href="javascript:void(0)">折线图</a></li>
                        <li><a data-href="charts-2.html" data-title="时间轴折线图" href="javascript:void(0)">时间轴折线图</a></li>
                        <li><a data-href="charts-3.html" data-title="区域图" href="javascript:void(0)">区域图</a></li>
                        <li><a data-href="charts-4.html" data-title="柱状图" href="javascript:void(0)">柱状图</a></li>
                        <li><a data-href="charts-5.html" data-title="饼状图" href="javascript:void(0)">饼状图</a></li>
                        <li><a data-href="charts-6.html" data-title="3D柱状图" href="javascript:void(0)">3D柱状图</a></li>
                        <li><a data-href="charts-7.html" data-title="3D饼状图" href="javascript:void(0)">3D饼状图</a></li>
                    </ul>
                </dd>
            </dl>
            <dl id="menu-system">
                <dt><i class="Hui-iconfont">&#xe62e;</i> 系统管理<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="system-base.html" data-title="系统设置" href="javascript:void(0)">系统设置</a></li>
                        <li><a data-href="system-category.html" data-title="栏目管理" href="javascript:void(0)">栏目管理</a></li>
                        <li><a data-href="system-data.html" data-title="数据字典" href="javascript:void(0)">数据字典</a></li>
                        <li><a data-href="system-shielding.html" data-title="屏蔽词" href="javascript:void(0)">屏蔽词</a></li>
                        <li><a data-href="system-log.html" data-title="系统日志" href="javascript:void(0)">系统日志</a></li>
                    </ul>
                </dd>
            </dl>
        </div>
        <div class="menu_dropdown bk_2" style="display:none">
            <dl id="menu-aaaaa">
                <dt><i class="Hui-iconfont">&#xe616;</i> 二级导航1<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="article-list.html" data-title="资讯管理" href="javascript:void(0)">三级导航</a></li>
                    </ul>
                </dd>
            </dl>
        </div>
        <div class="menu_dropdown bk_2" style="display:none">
            <dl id="menu-bbbbb">
                <dt><i class="Hui-iconfont">&#xe616;</i> 二级导航2<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="article-list.html" data-title="资讯管理" href="javascript:void(0)">三级导航</a></li>
                    </ul>
                </dd>
            </dl>
        </div>
        <div class="menu_dropdown bk_2" style="display:none">
            <dl id="menu-ccccc">
                <dt><i class="Hui-iconfont">&#xe616;</i> 二级导航3<i class="Hui-iconfont menu_dropdown-arrow">&#xe6d5;</i></dt>
                <dd>
                    <ul>
                        <li><a data-href="article-list.html" data-title="资讯管理" href="javascript:void(0)">三级导航</a></li>
                    </ul>
                </dd>
            </dl>
        </div>
    </aside>
    <div class="dislpayArrow hidden-xs"><a class="pngfix" href="javascript:void(0);" onClick="displaynavbar(this)"></a>
    </div>
    <section class="Hui-article-box">
        <div id="Hui-tabNav" class="Hui-tabNav hidden-xs">
            <div class="Hui-tabNav-wp">
                <ul id="min_title_list" class="acrossTab cl">
                    <li class="active">
                        <span title="我的桌面" data-href="${basePath}/main.do">我的桌面</span>
                        <em></em>
                    </li>
                </ul>
            </div>
            <div class="Hui-tabNav-more btn-group">
                <a id="js-tabNav-prev" class="btn radius btn-default size-S" href="javascript:;">
                    <i class="Hui-iconfont">&#xe6d4;</i>
                </a>
                <a id="js-tabNav-next" class="btn radius btn-default size-S" href="javascript:;">
                    <i class="Hui-iconfont">&#xe6d7;</i>
                </a>
            </div>
        </div>
        <div id="iframe_box" class="Hui-article">
            <div class="show_iframe">
                <div style="display:none" class="loading"></div>
                <iframe scrolling="yes" frameborder="0" src="${basePath}/platform/access/menu/list.do"></iframe>
            </div>
        </div>
    </section>

    <div class="contextMenu" id="Huiadminmenu">
        <ul>
            <li id="closethis">关闭当前</li>
            <li id="closeall">关闭全部</li>
        </ul>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />