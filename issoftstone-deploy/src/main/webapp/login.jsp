<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"/>
<hy:extends name="title">单点登录系统</hy:extends>
<hy:extends name="css">
    <link href="${ctx}/assets/css/H-ui.login.css" rel="stylesheet" type="text/css">
    <style type="text/css">
        img {
            height: 40px;
        }
    </style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
	    if(top.location !== self.location){ 
	        top.location.href = self.location.href; 
	    } 
        function doLogin() {
            $.ajax({
                url: '${ctx}/user/login.json',
                data: $("#loginForm").serializeArray(),
                success: function(res) {
	                if (res.code == 403) {
						$("#errmsg").text(res.message);
						setTimeout(function() {
							$("#errmsg").text('')
						}, 3000);
						return;
					}
	                window.location.href = "${ctx}/index.do?JSESSIONID=<%=request.getSession().getId()%>";
            	}, error: function(res) {
					console.log(res);
				}
            })
        }

        $(function () {
            $('#doLogin').click(function () {
                doLogin();
            })
        })
        document.onkeydown = function (event) {
	        var e = event || window.event;
	        if (e && e.keyCode == 13) { //回车键的键值为13
	            $("#doLogin").click(); //调用登录按钮的登录事件
	        }
	    }; 
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
                        <input name="username" type="text" placeholder="请输入登录账户" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3"><i class="Hui-iconfont">&#xe60e;</i></label>
                    <div class="formControls col-xs-8">
                        <input id="" name="password" type="password" placeholder="请输入登录密码" class="input-text size-L">
                    </div>
                </div>
                <div class="row cl">
                    <label class="form-label col-xs-3">
                    	<i class="layui-icon layui-icon-vercode" style="font-size: 22px;margin-right: 0px;"></i> 
                    </label>
                    <div class="formControls col-xs-8">
                        <input autocomplete="off"  class="input-text size-L" type="text" placeholder="请输入登录验证码"
                               value="" style="width:150px;" name="code">
                        <img src="${ctx}/auth/authCode.image">
                        <a id="kanbuq" href="javascript:;">看不清，换一张</a>
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-8 col-xs-offset-3">
                        <label for="online">
                            <input type="checkbox" name="online" id="online" value="">
                        	使我保持登录状态
                        </label>
                    </div>
                </div>
                <div class="row cl">
                   	<label class="form-label col-xs-3"></label>
                    <div class="formControls col-xs-8">
                        <input style="width: 90%" name="" type="button" id="doLogin" class="btn btn-success radius size-XL input-text"
                               value="&nbsp;登&nbsp;&nbsp;&nbsp;&nbsp;录&nbsp;">
                    </div>
                </div>
                <div class="row cl">
                   	<label class="form-label col-xs-3"></label>
                    <div class="formControls col-xs-8">
                        <a href="${ctx}/user/wxLogin.do">微信授权</a>
                        <a href="${ctx}/three/user/gitHubLogin.do">github授权</a>
                    </div>
                </div>
                <div class="row cl">
                    <div class="formControls col-xs-12">
                        <div style="text-align: center;height: 40px;line-height: 40px;color: red;" id="errmsg"></div>
                    </div>
                </div>
            </form>
        </div>
    </div>
    <div class="footer">Copyright 你的公司名称 by H-ui.admin.page.v3.0</div>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>