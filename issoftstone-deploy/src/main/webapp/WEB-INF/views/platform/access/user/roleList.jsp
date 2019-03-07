<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                limit: 20,
                size : 'sm', // 小尺寸的表格
                url: "${ctx}/platform/access/user/roleList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "code", title: '角色编号', width: 200, fixed: "left", unresize: true},
                    { field: "name", title: '角色名称', width: 180, fixed: "left", unresize: true},
                    { field: "remark", title: "备注"}
                ]],
                groupBtn: {
					searchAction: function (tableInstance) {
						tableInstance.reload({
							where : $("#search-form").getForm()
						});
					}
                },
                done: function(res, page, count){
                    var roles = ${roles};
                    for (var i = 0; i < res.data.length; i++) {
                    	var r = res.data[i];
						for (var j = 0; j < roles.length; j++) {
							var role = roles[j];
							if (r.id == role.id) {
			                    var index= r['LAY_TABLE_INDEX'];
			                    r["LAY_CHECKED"] = 'true';
			                    $('tr[data-index=' + index + '] input[type="checkbox"]').prop('checked', true);
			                    $('tr[data-index=' + index + '] input[type="checkbox"]').next().addClass('layui-form-checked');
			                    break;
							}
						}
					}
                }
            });
		}
        $(function() {
            refresh();
        })
        function saveRole() {
        	layui.use('table', function(){
      			var table = layui.table;
      			var checkStatus = table.checkStatus('tableList');
      			var data = checkStatus.data;
      			var ids = [];
      			for(var i in data) ids.push(data[i].id);
  	  			$.saveInfo({
  	  				url: '${ctx}/platform/access/user/userRoleSave.json',
  	  				data: {userId: $("#userId").val(), roleIds: ids.join(",")},
  	  				success: function () {
	  	  				var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
	    				parent.layer.close(index); //再执行关闭
	    				window.parent.refresh();
					}
  	  			})
            });
		}
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">角色名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="name_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<table id="tableList" lay-filter="tableList"></table>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius"  onclick="saveRole()">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>确定保存
	        </button>
	        <button class="btn btn-success radius" lay-event="searchAction">
	        	<i class="Hui-iconfont Hui-iconfont-search"></i>   
        		搜索
        	</button>
	    </div>
	    <input id="userId" name="userId" type="hidden" value="${userId}"/>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />