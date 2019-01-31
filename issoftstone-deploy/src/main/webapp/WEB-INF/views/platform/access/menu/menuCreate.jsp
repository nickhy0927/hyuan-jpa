<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.hy.include" prefix="hy"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<c:set value="${pageContext.request.contextPath}" var="ctx"></c:set>
<hy:extends name="title">新增菜单</hy:extends>
<hy:extends name="css">
    <style type="text/css">


    </style>
</hy:extends>
<hy:extends name="javascript">
	<script type="text/javascript">
		$(function () {
            $("#iconId").select2({
                placeholder: '请选择图标',
                width: '100%',
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
                        return {
                            results: data.results
                        };
                    },
                    cache: true
                },
                templateResult: formatRepo,
                templateSelection: formatRepoSelection,
                escapeMarkup: function (markup) {
                    console.log('escapeMarkup== ', markup)
                    return markup;
                }
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
			layui.use(['form', 'tree'], function () {
                var form = layui.form;
                //监听提交
                form.on('submit(create-form)', function (data) {
                    console.log($('form').getForm());
                	$.saveInfo({
                		url: '${ctx}/platform/access/menu/menuSave.json',//发送请求
				    	data: data.field,
				    	success: function(result) {
	                		var index = parent.layer.getFrameIndex(window.name); //先得到当前iframe层的索引
		    				parent.layer.close(index); //再执行关闭
							window.parent.refresh();
				    	}
					})
                	return false;
                });
                form.verify({
                    alias: [/^[a-zA-Z_-]+$/, '别名由字母，减号组成']
                });
                $.ajax({
                	type: "GET",
                    url: "${ctx}/platform/access/menu/menuCreate.json",
                    contentType: "application/json; charset=utf-8",
                    data: {},
                    success: function(res) {
                    	$("#classtree").html("");
         	         	layui.tree({
                            elem: "#classtree",
                            nodes: res.content['menuTrees'],
                            click: function (node) {
                            	var $select = $($(this)[0].elem).parents(".layui-form-select");
                                $select.removeClass("layui-form-selected").find(".layui-select-title span").html(node.name).end().find("input:hidden[name='parentId']").val(node.id);
                           }
                        });
                     },
                     error: function (message) {
                     }
                })
                $(".downpanel").on("click", ".layui-select-title", function (e) {
                    $(".layui-form-select").not($(this).parents(".layui-form-select")).removeClass("layui-form-selected");
                    $(this).parents(".downpanel").toggleClass("layui-form-selected");
                    layui.stope(e);
                }).on("click", "dl i", function (e) {
                    layui.stope(e);
                });
                console.log($('.select2').parent().find('.layui-form-select'))
                $('.select2').parent().find('.layui-form-select').css({
                    "display": 'none'
                });
            });
		})
		
		function reset() {
            layui.use('form', function () {
                var form = layui.form;
                form.render();
            });
        }
	</script>
</hy:extends>
<hy:extends name="body">
	<div class="create-form">
        <form class="layui-form layui-form-pane">
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>菜单名称
                </label>
                <div class="layui-input-block">
                    <input type="text" name="name"
                           lay-verify="required"
                           lay-verType="tips"
                           placeholder="请输入菜单名称"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>菜单别名</label>
                <div class="layui-input-block">
                    <input type="text" name="alias"
                    	lay-verType="tips"
                           required lay-verify="required|alias" placeholder="请输入菜单别名"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label">
                	<i>*</i>菜单排序
                </label>
                <div class="layui-input-block">
                    <input type="text" name="orders"
                           lay-verify="required|number"
                           lay-verType="tips"
                           placeholder="请输入菜单序号"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
		        <label class="layui-form-label">上级菜单</label>
		        <div class="layui-input-block">
		            <div class="layui-unselect layui-form-select downpanel">
		                <div class="layui-select-title">
		                   <span class="layui-input layui-unselect" id="treeclass">请选择上级菜单</span>
		                    <input type="hidden" name="parentId" value="">
		                    <i class="layui-edge"></i>
		                </div>
		                <dl class="layui-anim layui-anim-upbit">
		                    <dd>
		                        <ul id="classtree"></ul>
		                    </dd>
		                </dl>
		            </div>
		        </div>
		    </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>访问地址</label>
                <div class="layui-input-block">
                    <input type="text" name="url"
                    	lay-verType="tips"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i></i>是否显示</label>
                <div class="layui-input-inline">
                    <input type="radio" name="enable" value="0" title="否">
                    <input type="radio" name="enable" value="1" title="是" checked>
                </div>
                <label class="layui-form-label"><i></i>是否锁定</label>
                <div class="layui-input-inline">
                    <input type="radio" name="locked" value="0" title="否" checked>
                    <input type="radio" name="locked" value="1" title="是">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>国际化编码</label>
                <div class="layui-input-block">
                    <input type="text" name="localCode"
                    	lay-verType="tips"
                           required lay-verify="required" placeholder="请输入访问地址"
                           autocomplete="off" class="layui-input">
                </div>
            </div>
            <div class="layui-form-item">
                <label class="layui-form-label"><i>*</i>选择图标</label>
                <div class="layui-input-block search-select">
                    <select name="iconId" class="select2" placeholder="请选择图标" id="iconId" lay-verType="tips"></select>
                </div>
            </div>
            <div class="layui-form-item" style="text-align: right">
                <div class="layui-input-block">
                    <button class="layui-btn" lay-submit lay-filter="create-form">立即提交</button>
                    <button type="reset" onclick="reset()" class="layui-btn layui-btn-primary">重置</button>
                </div>
            </div>
        </form>
    </div>
</hy:extends>
<jsp:include page="/page/basepage.jsp" />