(function($, window, document) {
	$.fn.select = function(options) {
		var _this = this;
		var setting = $.extend({
			dataSource: [],
			defaultValue: '', // 默认值
			holder: '请选择',
			fields: {
				val: 'value', // value值的名称
				name: 'name' // name值的名称
			},
			syns: false // 是否异步
		}, options || {})
		var opts = '<option value="">' + setting.holder +'</option>';
		if(typeof setting.dataSource == 'string') {
			$.ajax({
           	 	type: "GET",
                url: setting.dataSource,
                contentType: "application/json; charset=utf-8",
                success: function (res) {
                	var data = res.data;
                	if (data && data.length > 0) {
                		$.each(data, function(index, item){
                			if(setting.defaultValue == item[setting.fields.val]) 
                				opts += '<option selected="selected" value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
                			else
                				opts += '<option value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
                		})
                	}
                	_this.html(opts)
                },
                error: function (message) {
                }
           })
		} else {
        	if (setting.dataSource && setting.dataSource.length > 0) {
        		$.each(setting.dataSource, function(index, item){
        			if(setting.defaultValue == item[setting.fields.val]) 
        				opts += '<option selected="selected" value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
        			else
        				opts += '<option value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
        		})
        	}
        	_this.html(opts)
		}
	}
	$.fn.getForm = function(){
		var obj = {}, names = {};
		$.each( this.serializeArray(), function(i,o){
			var n = o.name,
	        v = o.value;
	        if ( n.includes( '[]' ) ) {
	          names.n = !names.n ? 1 : names.n+1;
	          var indx = names.n - 1;
	          n = n.replace( '[]', '[' + indx + ']' );
	        }
	        obj[n] = obj[n] === undefined ? v : $.isArray( obj[n] ) ? obj[n].concat( v ) : [ obj[n], v ];
	    });
	    return obj;
	}; 
	var globleOpts = undefined;
	var _init_table = function(options, _this) {
		layui.use("table", function() {
			var table = layui.table;
			var settings = $.extend({
				elem : _this,
				title : "数据列表",
				id : _this.attr('id'),
				totalRow : false,
				toolbar : '',
				method : 'POST',
				filter : _this.attr('lay-filter'),
				loading : true, // 翻页加loading
				url : '',
				cols : [],
				height : "100%", // 高度最大化减去差值
				size : 'lg', // 小尺寸的表格
				groupBtn : {},
				operate : {},
				parseData : function(res) {
					return {
						"code" : res.code, // 解析接口状态
						"msg" : res.message, // 解析提示文本
						"count" : res.total, // 解析数据长度
						"data" : res.rows.item
					// 解析数据列表
					};
				},
				text : {
					none : '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
				},
				// 开启分页
				page : {
					groups : 7,
					first : '首页',
					prev : '上一页',
					next : '下一页',
					last : '尾页',
					theme: '#FF5722',
					layout : [ 'count', 'skip', 'prev', 'page', 'next', 'limit', 'refresh' ],
				},
				limit : 15
			}, options || {});
			var l = settings.limit;
			var limits = [10, l * 1, l * 2, l * 3, l * 4, l * 5, 100, 200]
			limits.sort(function(a, b){
		        if (a > b) {
		            return 1;
		        } else if(a < b){
		            return -1
		        } else{
		            return 0;
		        }    
		    });
			settings.limits = limits;
			globleOpts = settings;
			// 第一个实例
			var tableInstance = table.render(settings);
			table.on('toolbar(' + _this.attr('lay-filter') + ')', function(obj) {
				var func = settings.groupBtn[obj.event];
				var checkStatus = table.checkStatus(settings.id);
				var data = checkStatus.data;
				if (func)
					eval(func).call(this, tableInstance, data);
			});
			// 监听行工具事件
			table.on('tool(' + settings.filter + ')', function(obj) {
				var func = settings.operate[obj.event];
				var res = [];
				res.push(obj.data)
				if (func)
					eval(func).call(this, tableInstance, res);
			});
		});
	};
	$.fn.dataTable = function(options) {
		var _this = $(this);
		try {
			var d = $(document).height();
			var h = $('#search-form').height() + 25;
			options.height = d - h;
		} catch(e){}
		_init_table(options, _this);
		$('body').css({
			'overflow': 'hidden'
		})
	};
	$.fn.refreshTable = function(config) {
		if(!globleOpts) globleOpts = {};
		var _this = $(this);
		var d = $(document).height();
		var h = $('#search-form').height() + 25 + 10;
		globleOpts.height = d - h;
		var $settings = $.extend(globleOpts, config || {})
		_init_table($settings, $(this))
	};
	$(window).resize(function() {
		document.execCommand('Refresh')
	})
	
	var _init_select_tree = function(_this, setting) {
		//id div 的id，isMultiple 是否多选，chkboxType 多选框类型{"Y": "ps", "N": "s"} 详细请看ztree官网
		var chkboxType = setting.chkboxType;
	    if (setting.isMultiple) {
	        setting.check.enable = setting.isMultiple;
	    }
	    if (chkboxType !== undefined && chkboxType != null) {
	        setting.check.chkboxType = chkboxType;
	    }
	    var html = '<div class = "layui-select-title" >' +
    			       '<input id="' + $(_this).attr('id') + 'Show"' + 'type="text" placeholder="' + setting.placeholder + '" value="" class ="layui-input" readonly>' +
    			       '<i class= "layui-edge" ></i>' +
			       '</div>';
	    $(_this).append(html);
	    $(_this).parent().append('<div class="tree-content scrollbar">' +
	        '<input hidden id="' + $(_this).attr('id') + 'Hide" name="' + $(".select-tree").attr("id") + '">' +
	        '<ul id="' + $(_this).attr('id') + 'Tree" class="ztree scrollbar" style="margin-top:0;"></ul>' +
	        '</div>');
	    $(_this).bind("click", function () {
	        if ($(this).parent().find(".tree-content").css("display") !== "none") {
	            hideMenu()
	        } else {
	            $(this).addClass("layui-form-selected");
	            $(this).parent().find(".tree-content").slideDown("fast");
	            $("body").bind("mousedown", onBodyDown);
	        }
	    });
	    $.fn.zTree.init($("#" + $(_this).attr('id') + "Tree"), setting, setting.zNodes);
	}
	// 初始化下拉树
	$.fn.initSelectTree = function (options) {
		var _this = this;
		var setting = $.extend({
			isMultiple: true,
			dataSource: '',
			placeholder: '请选择',
			view: {
	            dblClickExpand: false,
	            showLine: true
	        },
	        data: {
	            simpleData: {
	                enable: true
	            }
	        },
	        callback: {
	            onClick: onClick,
	            onCheck: onCheck,
	            onNodeCreated: function (event, treeId, treeNode) { },
	            onNodeCreated: function (event, treeId, treeNode) { },
	            onRightClick: function (event, treeId, treeNode) {}
	        }
		}, options || {});
		if(!setting.dataSource) throw '没有数据资源，请配置dataSource';
		if(typeof setting.dataSource == 'string') {
			$.ajax({
				 type: "POST",
				 url: setting.dataSource,
				 success: function(res) {
					 setting.zNodes = res.content;
					 _init_select_tree(_this, setting);
				 }
			})
		} else {
			setting.zNodes = setting.dataSource;
			_init_select_tree(_this, setting);
		}
	}
})(jQuery, window, document);
function onClick(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    if (zTree.setting.check.enable == true) {
        zTree.checkNode(treeNode, !treeNode.checked, false)
        assignment(treeId, zTree.getCheckedNodes());
    } else {
        assignment(treeId, zTree.getSelectedNodes());
    }
    hideMenu();
}
function onCheck(event, treeId, treeNode) {
    var zTree = $.fn.zTree.getZTreeObj(treeId);
    assignment(treeId, zTree.getCheckedNodes());
}

function hideMenu() {
    $(".select-tree").removeClass("layui-form-selected");
    $(".tree-content").fadeOut("fast");
    $("body").unbind("mousedown", onBodyDown);
}

function assignment(treeId, nodes) {
    var names = "";
    var ids = "";
    for (var i = 0, l = nodes.length; i < l; i++) {
        names += nodes[i].name + ",";
        ids += nodes[i].id + ",";
    }
    if (names.length > 0) {
        names = names.substring(0, names.length - 1);
        ids = ids.substring(0, ids.length - 1);
    }
    treeId = treeId.substring(0, treeId.length - 4);
    $("#" + treeId + "Show").attr("value", names);
    $("#" + treeId + "Show").attr("title", names);
    $("#" + treeId + "Hide").attr("value", ids);
}

function onBodyDown(event) {
    if ($(event.target).parents(".tree-content").html() == null) {
        hideMenu();
    }
}
/**
 * 打开窗口
 * @param title 标题
 * @param width 宽度
 * @param height 高度
 * @param url 访问地址
 * @private
 */
var _openWindow = function(options) {
    layer.open({
        type: 2,
        title: options.title || '新建窗口',
        area: [options.width || '80%', options.height || '95%'],
        fixed: true, //不固定
        move: false,
        shadeClose: false,
        shade: 0.6,
        maxmin: false,
        content: options.url,
        end: options.callback ? options.callback(index) : null
    });
}

/**
 * 在父窗口打开
 * @param title 标题
 * @param width 宽度
 * @param height 高度
 * @param url 访问地址
 */
var _parentOpenWindow = function(options) {
    parent.layer.open({
    	 type: 2,
         title: options.title || '新建窗口',
         area: [options.width || '80%', options.height || '95%'],
         fixed: true, //不固定
         move: false,
         shadeClose: false,
         shade: 0.6,
         maxmin: false,
         content: options.url,
         end: options.callback ? options.callback(index) : null
    });
}

// 关闭加载框
var _closeLoading = function() {
    layer.closeAll();
};

// 打开加载框
var _openLoading = function(msg) {
    var msg = msg ? msg : "正在加载中，请稍后...";
    layer.msg(msg, {icon: 16, time: 1000 * 10000, shade: [0.3, '#000']});
}

var _openTip = function(content, callback) {
	layer.alert(content, {
        skin: 'layui-layer-molv', 
        // anim: 2, //动画类型
        icon: 6, // icon   
        closeBtn: 0,
        title: '提示信息',
        btn: ['确定'] //按钮
    }, function () {
        if (callback != undefined)
            callback();
    });
}
var _confirmTip = function(content, callback) {
	layer.comfirm(content, {
		skin: 'layui-layer-molv', 
		// anim: 2, //动画类型
		icon: 6, // icon   
		closeBtn: 0,
		btn: ['确定'] //按钮
	}, function () {
		if (callback != undefined)
			callback();
	});
}
$(document).ready(function () {
	$.ajaxSetup({
	    // 发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"
	    dataType: "json",
	    type: "POST",
	    // 布尔值，表示浏览器是否缓存被请求页面。默认是 true。
	    cache: false,
	    // 规定请求的字符集。
	    scriptCharset: "UTF-8",
	    error: function(jqXHR, textStatus, errorThrown){  
	    	console.log(jqXHR)
            switch (jqXHR.status){  
                case(500):  
	                $.openTip('服务器系统内部错误', function() {
	    				_closeLoading();
		    		});
                    break;  
                case(401):  
                	$.openTip('未登录，刷新后重新登录', function() {
	    				_closeLoading();
		    		}); 
                    break;  
                case(403):  
	                $.openTip('无权限执行此操作', function() {
	    				_closeLoading();
		    		});
                    break;  
                case(408):  
                	$.openTip('请求超时，请稍后再试', function() {
	    				_closeLoading();
		    		});
                    break;  
                default:  
                    alert("未知错误");  
            }  
        }
	});
    $(document).ajaxStart(function () {
    }).ajaxSend(function(e,xhr,opt){
    	_openLoading(opt.loadMsg);
    }).ajaxSuccess(function (event, xhr, settings) {
    	var res = JSON.parse(xhr.responseText);
    	if (res.code != 1 && res.code != 2) {
    		_closeLoading();
    	}
    });
    $.extend({
		openWindow : _openWindow,
		openTip: _openTip,
	    openLoading: _openLoading,
	    closeLoading: _closeLoading,
	    saveInfo: function(option) {
	    	console.log(option.data);
	    	$.ajax({
	    		url: option.url,//发送请求
		    	data: option.data,
		    	loadMsg: option.loadMsg, 
		    	success: function(res) {
		    		if(res.code == 1) {
		    			$.openTip(res.message, function() {
			    			var fn = eval(option.success);
			                fn.call(this, res);
			    		});
		    		} else if (res.code == 2){
		    			$.openTip(res.message, function() {
		    				_closeLoading();
			    		});
		    			return;
		    		}
		    	}
			})
	    },
	    deleteInfo: function(options) {
	    	var ids = [];
            var data = options.data;
            for(var i in data) ids.push(data[i].id)
            if(ids.length == 0) {
            	$.openTip('请选择一项进行删除', function() {
            		$.closeLoading();
            	});
            	return;
            }
	    	layer.confirm("确定要删除信息吗？", {
	            skin: 'layui-layer-molv', //样式类名  自定义样式
	            title: options.title ? options.title : '提示信息',
	            closeBtn: 0,
	            btn: ['确定', '取消'] //按钮
	        }, function () {
	            _closeLoading();
	            $.ajax({
		    		url: options.url,//发送请求
			    	data: {id: ids.join(",")},
			    	loadMsg: options.loadMsg, 
			    	success: function(res) {
			    		if(res.code == 1) {
			    			$.openTip(res.message, function() {
				    			var fn = eval(options.success);
				                fn.call(this, res);
				    		});
			    		} else if (res.code == 2){
			    			$.openTip(res.message, function() {
			    				_closeLoading();
				    		});
			    			return;
			    		}
			    	}
				})
	        }, function () {
	            _closeLoading();
	        });
	    }
	})
});
