<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx" />
<hy:extends name="title">菜单列表</hy:extends>
<hy:extends name="css">
	<style type="text/css">
		.ztree-form, .xtree_contianer {
			width: 100%;
			height: 100%;
		}
		.title-nav {
			padding: 5px;
			height:35px;
			font-weight:bold;
			background:#e6e6e6;
			border-style: solid;
    		border-color: #e6e6e6;
			border-bottom: 1px solid #e6e6e6;
		    -webkit-tap-highlight-color: rgba(0,0,0,0);
		}
		.title-nav .title {
			padding: 5px;
			font-weight:bold;
			background:#e6e6e6;
			border-style: solid;
    		border-color: #e6e6e6;
			border-bottom: 1px solid #e6e6e6;
		    -webkit-tap-highlight-color: rgba(0,0,0,0);
		}
		.title-nav .title {
			float: left;
		}
		.title-nav button {
			float: right;
			margin-top: 5px;
		}
		.ztree li span.button.add {
		    margin-left: 2px;
		    margin-right: -1px;
		    background-position: -144px 0;
		    vertical-align: top;
		    *vertical-align: middle;
		}
	</style>
</hy:extends>
<hy:extends name="javascript">
    <script type="text/javascript">
    	function refresh() {
    		$("#tableList").refreshTable();
		}
    	function guid() {
  			return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {
  		  		var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);
  		    	return v.toString(16);
  		  	});
  		}
    	function deleteInfo(tableInstance, data) {
    		$.deleteInfo({
				url: '${ctx}/platform/access/icon/iconDelete.json',//发送请求
		    	data: data,
		    	loadMsg: '正在删除图标信息，请稍等...', 
		    	success: function (res) {
		    		$("#tableList").refreshTable()
				}
			})
		}
    	var zTreeObj, setting = {
           	check: {
                enable: false,
            },
            view: {
				addHoverDom: addHoverDom,
				removeHoverDom: removeHoverDom,
				selectedMulti: false
			},
			edit: {
				enable: true,
				editNameSelectAll: true,
				showRemoveBtn: showRemoveBtn,
				showRenameBtn: showRenameBtn
			},
			data: {
				simpleData: {
					enable: true
				}
			},
			callback: {
				beforeRemove: beforeRemove,
				onRemove: onRemove,
				onRename: onRename
			}
   		}
    	var zNodes =[
			{ id:1, pId:0, name:"父节点 1", open:true},
			{ id:11, pId:1, name:"叶子节点 1-1"},
			{ id:12, pId:1, name:"叶子节点 1-2"},
			{ id:13, pId:1, name:"叶子节点 1-3"},
			{ id:2, pId:0, name:"父节点 2", open:true},
			{ id:21, pId:2, name:"叶子节点 2-1"},
			{ id:22, pId:2, name:"叶子节点 2-2"},
			{ id:23, pId:2, name:"叶子节点 2-3"},
			{ id:3, pId:0, name:"父节点 3", open:true},
			{ id:31, pId:3, name:"叶子节点 3-1"},
			{ id:32, pId:3, name:"叶子节点 3-2"},
			{ id:33, pId:3, name:"叶子节点 3-3"}
		];
    	var log, className = "dark";
		function beforeRemove(treeId, treeNode) {
			className = (className === "dark" ? "":"dark");
			var zTree = $.fn.zTree.getZTreeObj("tree");
			zTree.selectNode(treeNode);
			return confirm("确认删除 节点 -- " + treeNode.name + " 吗？");
		}
		function onRemove(e, treeId, treeNode) {
		}
		function onRename(e, treeId, treeNode, isCancel) {
		}
		function showRemoveBtn(treeId, treeNode) {
			console.log(treeNode)
			// return !treeNode.isFirstNode;
			return !treeNode.isParent;
		}
		function showRenameBtn(treeId, treeNode) {
			// return !treeNode.isLastNode;
			return true;
		}
		var mydate = new Date();
		function addHoverDom(treeId, treeNode) {
			var sObj = $("#" + treeNode.tId + "_span");
			if (treeNode.editNameFlag || $("#addBtn_"+treeNode.tId).length>0) return;
			var addStr = "<span class='button add' id='addBtn_" + treeNode.tId + "' title='添加子节点' onfocus='this.blur();'></span>";
			sObj.after(addStr);
			var btn = $("#addBtn_"+treeNode.tId);
			if (btn) btn.bind("click", function(){
				var zTree = $.fn.zTree.getZTreeObj("tree");
				zTree.addNodes(treeNode, {id: guid(), pId:treeNode.id, name:"新节点"});
				return false;
			});
		};
		function removeHoverDom(treeId, treeNode) {
			$("#addBtn_"+treeNode.tId).unbind().remove();
		};
		function selectAll() {
			var zTree = $.fn.zTree.getZTreeObj("tree");
			console.log(zTree);
			zTree.setting.edit.editNameSelectAll =  $("#selectAll").attr("checked");
		}
        function initTree() {
           	$.ajax({
           		url: '${ctx}/platform/access/permission/queryPermissionTree.json',
           		data: {id: ""},
           		success: function (res) {
           			var data = JSON.parse(res.content);
           			console.log(data);
           			zTreeObj = $.fn.zTree.init($("#tree"), setting, data);
   				}
           	})
   		}
        $(function() {
        	/* initTree(); */
        	$.fn.zTree.init($("#tree"), setting, zNodes);
        	$("#saveRole").bind("click", selectAll);
        	$("#tableList").dataTable({
                toolbar: "#tableBar",
                searchForm: 'search-form',
                url: "${ctx}/platform/access/icon/iconList.json",
                cols: [[
                    { type: "checkbox", fixed: "left" },
                    { field: "name", title: '图标名称', width: 160, fixed: "left", unresize: true},
                    { field: "icon",  title: "图标", width: 80, align: 'center', fixed: "left", unresize: true},
                    { field: "className",  title: "图标样式", width: 240, fixed: "left", unresize: true},
                    { title: "使用方法", minWidth: 200, unresize: true, templet: function (d) {
						return '<pre><xmp>' + d.iconClass + '</xmp></pre>';
					}},
                    { fixed: "right", title: "操作", align: "center",  toolbar: "#operateBar",  width: 120, unresize: true}
                ]],
                operate: {
                	editAction: function (tableInstance, data) {
                		$.openWindow({
							title: '<i class="layui-icon layui-icon-form"></i>&nbsp;修改图标信息',
							height: '240px',
							width: '70%',
							url: '${ctx}/platform/access/icon/iconEdit.do?id=' + data[0].id 
						})
					},
					delAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					}
                },
                groupBtn: {
                	createAction: function () {
                		$.openWindow({
                			title: '<i class="layui-icon layui-icon-form"></i>&nbsp;新增图标信息',
							height: '200px',
							width: '70%',
							url: '${ctx}/platform/access/icon/iconCreate.do'
						})
					},
					deleteAction: function (tableInstance, data) {
						deleteInfo(tableInstance, data);
					},
					searchAction: function (tableInstance) {
						$("#tableList").refreshTable({
							where : $("#search-form").getForm()
						});
					}
                }
            })
        })
    </script>
</hy:extends>
<hy:extends name="body">
    <div class="grid-main">
    	<div class="search-block">
	        <form class="layui-form layui-form-pane" id="search-form" lay-filter="search-form">
	            <div class="layui-form-item">
	                <div class="layui-inline">
	                    <label class="layui-form-label">图标名称</label>
	                    <div class="layui-input-inline">
	                        <input type="text" name="name_li" autocomplete="off" class="layui-input">
	                    </div>
	                </div>
	            </div>
	        </form>
	    </div>
    	<hr class="hr-line">
    	<div class="layui-row">
    		 <div class="layui-col-xs3" style="border: 1px solid #e6e6e6;margin-top: 10px;">
		      	<form class="layui-form ztree-form" style="">
		      		<div class="title-nav">
		    			<span class="title">
		    				权限树
		    			</span>
		    			<span>
		    				<button class="btn btn-secondary radius size-S" id="saveRole">
				        	<i class="Hui-iconfont Hui-iconfont-save"></i>  保存权限
				        </button>
		    			</span>
	    			</div>
	    			
					<ul id="tree" class="ztree xtree_contianer" style="overflow:auto;"></ul>
					<input type="hidden" value="" name="roleId" id="roleId">
				</form>
		    </div>
		    <div class="layui-col-xs9">
		      	<table id="tableList" lay-filter="tableList"></table>
		    </div>
		</div>
	    <div style="display:none" class="layui-btn-container" id="tableBar">
	        <button class="	btn btn-primary radius" lay-event="createAction">
	        	<i class="Hui-iconfont Hui-iconfont-add2"></i>新增
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
	        <a class="btn btn-secondary-outline radius" lay-event="editAction">
	        	<i class="Hui-iconfont Hui-iconfont-edit"></i>
	        </a>
	        <a class="btn btn-danger-outline radius" lay-event="delAction">
	        	<i class="Hui-iconfont Hui-iconfont-del2"></i>
	        </a>
	    </div>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />