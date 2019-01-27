<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<hy:extends name="title">ztree</hy:extends>
<hy:extends name="css">
	<style type="text/css">

.xtree_contianer {
	width: 500px;
	border: 1px solid #9C9C9C;
	overflow: auto;
	margin-bottom: 30px;
	background-color: #fff;
	padding: 10px 0 25px 5px;
}
</style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		var json = [ {
			title : "节点1",
			value : "jd1",
			data : [ {
				title : "节点1.1",
				checked : true,
				disabled : true,
				value : "jd1.1",
				data : []
			}, {
				title : "节点1.2",
				value : "jd1.2",
				checked : true,
				data : []
			}, {
				title : "节点1.3",
				value : "jd1.3",
				disabled : true,
				data : [ {
					title : "节点1.2",
					value : "jd1.2",
					checked : true,
					data : []
				} ]
			}, {
				title : "节点1.4",
				value : "jd1.4",
				data : []
			} ]
		}, {
			title : "节点2",
			value : "jd2",
			data : [ {
				title : "节点2.1",
				value : "jd2.1",
				data : []
			}, {
				title : "节点2.2",
				value : "jd2.2",
				data : []
			}, {
				title : "节点2.3",
				value : "jd2.3",
				data : []
			}, {
				title : "节点2.4",
				value : "jd2.4",
				data : []
			} ]
		}, {
			title : "节点3",
			value : "jd3",
			data : []
		}, {
			title : "节点4",
			value : "jd4",
			data : []
		} ];

		//********************
		//      正式开始
		//********************

		//layui 的 form 模块是必须的
		layui.use([ 'form' ], function() {
			var form = layui.form;
			//3、最完整的参数用法
			var xtree3 = new layuiXtree({
				elem : 'xtree1', //必填三兄弟之老大
				form : form, //必填三兄弟之这才是真老大
				data : json, //必填三兄弟之这也算是老大
				isopen : false, //加载完毕后的展开状态，默认值：true
				ckall : true, //启用全选功能，默认值：false
				ckallback : function() {
				}, //全选框状态改变后执行的回调函数
				icon : { //三种图标样式，更改几个都可以，用的是layui的图标
					// open : "&#xe7a0;", //节点打开的图标
					// close : "&#xe622;", //节点关闭的图标
					end : "&#xe705;" //末尾节点的图标
				},
				color : { //三种图标颜色，独立配色，更改几个都可以
					open : "#EE9A00", //节点图标打开的颜色
					close : "#EEC591", //节点图标关闭的颜色
					end : "#828282" //末级节点图标的颜色
				},
				click : function(data) { //节点选中状态改变事件监听，全选框有自己的监听事件
					console.log(data.elem); //得到checkbox原始DOM对象
					console.log(data.elem.checked); //开关是否开启，true或者false
					console.log(data.value); //开关value值，也可以通过data.elem.value得到
					console.log(data.othis); //得到美化后的DOM对象
				}
			});
			//提供的方法们
			//获取全部[选中的][末级节点]原checkbox DOM对象，返回Array
			document.getElementById('btn1').onclick = function() {
				var oCks = xtree3.GetChecked(); //这是方法
				for (var i = 0; i < oCks.length; i++) {
					console.log(oCks[i].value);
				}
			}
		});
	</script>
</hy:extends>
<hy:extends name="body">
	<form class="layui-form">
		<div id="xtree1" class="xtree_contianer"></div>
	</form>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />