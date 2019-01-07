<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<hy:extends name="title">单点登录系统</hy:extends>
<hy:extends name="css">
    <link href="${ctx}/assset/hui/css/H-ui.min.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/assets/hui/css/H-ui.login.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/assets/hui/css/style.css" rel="stylesheet" type="text/css">
    <link href="${ctx}/assets/lib/Hui-iconfont/1.0.8/iconfont.css" rel="stylesheet" type="text/css"/>
    <style type="text/css">
        img {
            height: 40px;
        }
    </style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
        function doLogin() {
            $.requestAjax({
                url: '${ctx}/user/login.json',
                data: $("#loginForm").serializeArray(),
            }, function(res) {
                console.log('res:', res);
                window.location.href = "${ctx}/index.do?jsessionid=<%=request.getSession().getId()%>"
            })
        }

        $(function () {
            $('#doLogin').click(function () {
                doLogin();
            })
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <input type="hidden" id="TenantId" name="TenantId" value=""/>
    <div class="header"></div>
    <div class="loginWraper">
        <div class="loginBox">
            <form class="form form-horizontal" id="loginForm">
                <div class="row cl">
                    <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60d;</i></label>
                    <div class="formControls col-xs-8">
                        <input name="username" type="text" placeholder="账户" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                    <div class="formControls col-xs-8">
                        <input id="" name="password" type="password" placeholder="密码" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3"></label>
                    <div class="formControls col-xs-8">
                        <input class="input-text size-L" type="text" placeholder="请输入验证码"
                               value="" style="width:150px;">
                        <img src="${ctx}/auth/authCode.image">
                        <a id="kanbuq" href="javascript:;">看不清，换一张</a>
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <label for="online">
                            <input type="checkbox" name="online" id="online" value="">
                            使我保持登录状态</label>
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <input name="" type="button" id="doLogin" class="btn btn-success radius size-L"
                               value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                        <input name="" type="reset" class="btn btn-default radius size-L"
                               value="&nbsp;取&nbsp;&nbsp;&nbsp;&nbsp;消&nbsp;">
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="footer">Copyright 你的公司名称 by H-ui.admin.page.v3.0</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>