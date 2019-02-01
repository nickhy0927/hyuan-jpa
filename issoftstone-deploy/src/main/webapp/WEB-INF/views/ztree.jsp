<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://www.hy.include" prefix="hy" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
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
        var json = [{
            title: "节点1",
            value: "jd1",
            data: [{
                title: "节点1.1",
                checked: true,
                disabled: true,
                value: "jd1.1",
                data: []
            }, {
                title: "节点1.2",
                value: "jd1.2",
                checked: true,
                data: []
            }, {
                title: "节点1.3",
                value: "jd1.3",
                disabled: true,
                data: [{
                    title: "节点1.2",
                    value: "jd1.2",
                    checked: true,
                    data: []
                }]
            }, {
                title: "节点1.4",
                value: "jd1.4",
                data: []
            }]
        }, {
            title: "节点2",
            value: "jd2",
            data: [{
                title: "节点2.1",
                value: "jd2.1",
                data: []
            }, {
                title: "节点2.2",
                value: "jd2.2",
                data: []
            }, {
                title: "节点2.3",
                value: "jd2.3",
                data: []
            }, {
                title: "节点2.4",
                value: "jd2.4",
                data: []
            }]
        }, {
            title: "节点3",
            value: "jd3",
            data: []
        }, {
            title: "节点4",
            value: "jd4",
            data: []
        }];

        //********************
        //      正式开始
        //********************

        //layui 的 form 模块是必须的
        layui.use(['form'], function () {
            var form = layui.form;
            form.on('select(business)', function (data) {
                alert(1)
            })
            //3、最完整的参数用法
            var xtree3 = new layuiXtree({
                elem: 'xtree1', //必填三兄弟之老大
                form: form, //必填三兄弟之这才是真老大
                data: json, //必填三兄弟之这也算是老大
                isopen: false, //加载完毕后的展开状态，默认值：true
                ckall: true, //启用全选功能，默认值：false
                ckallback: function () {
                }, //全选框状态改变后执行的回调函数
                icon: { //三种图标样式，更改几个都可以，用的是layui的图标
                    // open : "&#xe7a0;", //节点打开的图标
                    // close : "&#xe622;", //节点关闭的图标
                    end: "&#xe705;" //末尾节点的图标
                },
                color: { //三种图标颜色，独立配色，更改几个都可以
                    open: "#EE9A00", //节点图标打开的颜色
                    close: "#EEC591", //节点图标关闭的颜色
                    end: "#828282" //末级节点图标的颜色
                },
                click: function (data) { //节点选中状态改变事件监听，全选框有自己的监听事件
                    console.log(data.elem); //得到checkbox原始DOM对象
                    console.log(data.elem.checked); //开关是否开启，true或者false
                    console.log(data.value); //开关value值，也可以通过data.elem.value得到
                    console.log(data.othis); //得到美化后的DOM对象
                }
            });
            //提供的方法们
            //获取全部[选中的][末级节点]原checkbox DOM对象，返回Array
            // document.getElementById('btn1').onclick = function() {
            // 	var oCks = xtree3.GetChecked(); //这是方法
            // 	for (var i = 0; i < oCks.length; i++) {
            // 		console.log(oCks[i].value);
            // 	}
            // }
        });
        $(function () {
            $("#area").select2({
                placeholder: '请选择',
                width: '100%',
                allowClear: true,
                ajax: {
                    url: '${ctx}/platform/access/icon/queryIconList.json',
                    dataType: 'json',
                    data: function (params) {
                        var query = {
                            name_li: params.term,
                            type: 'public'
                        }
                        return query;
                    },
                    processResults: function (data, params) {
                        console.log('processResults==', data.results)
                        return {
                            results: data.results
                        };
                    },
                    cache: true
                },
                minimumInputLength: 1,
                templateResult: formatRepo,
                templateSelection: formatRepoSelection,
                escapeMarkup: function (markup) {
                    console.log('escapeMarkup== ', markup)
                    return markup;
                }
            })
        })

        function formatRepo(repo) {
            if (repo.loading) {
                return repo.text;
            }
            var markup = "<div class='select2-result-repository clearfix'>" +
                "<div class='select2-result-repository__avatar'>" + repo.iconClass + "</div>" +
                "<div class='select2-result-repository__meta'>" +
                "<div class='select2-result-repository__title'>" + repo.name + "</div>";
            return markup;
        }

        function formatRepoSelection(repo) {
            return repo.name || repo.id;
        }
    </script>
    
    <script type="text/javascript">
	    layui.config({
			base: '${ctx}/assets/lib/layui/module/',
		}).use(['layer', 'table', 'treetable'], function () {
            var $ = layui.jquery;
            var table = layui.table;
            var layer = layui.layer;
            var treetable = layui.treetable;
            // 渲染表格
            var renderTable = function () {//树桩表格参考文档：https://gitee.com/whvse/treetable-lay
                /* layer.load(2); */
                treetable.render({
                    treeColIndex: 1,//树形图标显示在第几列
                    treeSpid: 0,//最上级的父级id
                    treeIdName: 'id',//id字段的名称
                    treePidName: 'parentId',//pid字段的名称
                    treeDefaultClose: false,//是否默认折叠
                    treeLinkage: true,//父级展开时是否自动展开所有子级
                    elem: '#permissionTable',
                    url: '${ctx}/platform/access/menu/menuTreeListToJson.json',
                    page: false,
                    cols: [[
                    	{ type: "checkbox", fixed: "left" },
                    	{ field: "name", title: '菜单名称', width: 260, fixed: "left", unresize: true},
                        { field: "alias",  title: "菜单别名", width: 200, fixed: "left", unresize: true},
                        { field: "parentName", title: "上级菜单", width: 120, unresize: true},
                        { title: "图标", width: 80, unresize: true, align: 'center', templet: function (d) {
    						return d.icon ? d.icon.iconClass : "";
    					}},
                        { field: "url", title: "访问地址",minWidth: 200},
                        { field: "localCode", title: "国际化编码", minWidth: 160},
    					{ field: "enable",  title: "启用", width: 80, align: 'center', templet: function (d) {
    						if (d.enable) {
    							return '<span class="label label-success radius">是</span>'
    						}
    						return '<span class="label label-warning radius">否</span>'
    					}},
    					{ field: "locked",  title: "锁定", width: 80, align: 'center', templet: function (d) {
    						if (d.enable) {
    							return '<span class="label label-success radius">是</span>'
    						}
    						return '<span class="label label-warning radius">否</span>'
    					}},
                        {templet: complain, title: '操作'}
                    ]],
                    done: function () {
                        layer.closeAll('loading');
                    }
                });
            };
            renderTable();
    		//触发三个button按钮
            $('#btn-expand').click(function () {
                treetable.expandAll('#permissionTable');
            });

            $('#btn-fold').click(function () {
                treetable.foldAll('#permissionTable');
            });

            $('#btn-refresh').click(function () {
                renderTable();
            });
    		
            
            function complain(d){//操作中显示的内容
            	if(d.url!=null){
            		return [
                            '<a class="operation" lay-event="edit" href="javascript:void(0)" title="编辑">',
                	     	'<i class="layui-icon layui-icon-edit"></i></a>',
                	     	'<a class="operation" lay-event="del" href="javascript:void(0)" title="删除">',
                	     	'<i class="layui-icon layui-icon-delete" ></i></a>',
                	     	].join('');
            	}else{
            		return '';
            	}
            	
            }
            //监听工具条
            table.on('tool(permissionTable)', function (obj) {
                var data = obj.data;
                console.log(obj);
                var layEvent = obj.event;
    			if(data.permissionName!=null){
    				if (layEvent === 'del') {
    	                layer.msg('删除' + data.id);
    	            } else if (layEvent === 'edit') {
    	                layer.msg('修改' + data.id);
    	            }
    			}
            });
        });
    </script>
</hy:extends>
<hy:extends name="body">
    <form class="layui-form search-select">
        <div id="xtree1" class="xtree_contianer"></div>
        <span>区域：</span>
        <select id="area" class="select2" lay-search=" " lay-filter="business">
            <option value="" selected="selected">请选择区域</option>
            <option value="1">珠海</option>
            <option value="2">深圳</option>
            <option value="3">澳门</option>
            <option value="4">香港</option>
            <option value="1">layer</option>
            <option value="2">form</option>
            <option value="3">layim</option>
            <option value="4">element</option>
            <option value="5">laytpl</option>
            <option value="6">upload</option>
            <option value="7">laydate</option>
        </select>
    </form>
    
    <div class="create-form">
        <form class="layui-form layui-form-pane" lay-filter="edit-form">
        	<input name="id" type="hidden" id="id">
            <div class="layui-form-item">
                <table id="permissionTable" class="layui-table" lay-filter="permissionTable"></table>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp"/>