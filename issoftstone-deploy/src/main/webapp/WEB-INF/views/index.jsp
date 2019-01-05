<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新闻系统</hy:extends>
<hy:extends name="javascript">
	<script src="${ctx}/static/lib/ligerui/index.js"></script>
	<script type="text/javascript">
	    
     </script> 
</hy:extends>
<hy:extends name="body">
	<div id="pageloading"></div>  
	<div id="topmenu" class="l-topmenu">
	    <div class="l-topmenu-logo">jQuery ligerUI Demos导航主页</div>
	    <div class="l-topmenu-welcome">
	        <label> 皮肤切换：</label>
	        <select id="skinSelect">
	            <option value="aqua">默认</option> 
	            <option value="silvery">Silvery</option>
	            <option value="gray">Gray</option>
	            <option value="gray2014">Gray2014</option>
	        </select>
	        <span class="space">|</span>
	        <a href="http://www.ligerui.com/server.html" class="l-link2" target="_blank">服务支持</a> 
	    </div> 
	</div>
  	<div id="layout1" style="width:99.2%; margin:0 auto; margin-top:4px; "> 
    	<div position="left"  title="主要菜单" id="accordion1"> 
            <div title="功能列表" class="l-scroll leftMenu">
                <ul id="tree1" style="margin-top:3px;"></ul>
          	</div>
           	<div title="应用场景" class="leftMenu">
	           	<div style=" height:7px;"></div>
               	<a class="l-link" href="http://www.ligerui.com/go.aspx?id=case" target="_blank">演示系统</a>  
                <a class="l-link" href="javascript:f_addTab('listpage','列表页面','demos/case/listpage.htm')">列表页面</a> 
                <a class="l-link" href="demos/dialog/win7.htm" target="_blank">模拟Window桌面</a> 
               	<a class="l-link" href="javascript:f_addTab('week','工作日志','demos/case/week.htm')">工作日志</a>  
           	</div>    
            <div title="实验室" class="leftMenu">
	           	<div style=" height:7px;"></div>
	                 <a class="l-link" href="lab/generate/index.htm" target="_blank">表格表单设计器</a> 
	                 <a class="l-link" href="lab/formdesign/index.htm" target="_blank">可视化表单设计</a> 
	           	</div> 
	        </div>
	        <div position="center" id="framecenter"> 
	            <div tabid="home" title="我的主页" style="height:300px" >
	                <iframe frameborder="0" name="home" id="home" src="welcome.htm"></iframe>
	            </div> 
	        </div> 
   		</div>
    	<div  style="height:32px; line-height:32px; text-align:center;">
            Copyright © 2011-2014 www.ligerui.com
    	</div>
   		<div style="display:none"></div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />