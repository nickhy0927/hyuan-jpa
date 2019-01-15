var tableManager = undefined;
(function($) {
	$.fn.select = function(option) {
		var _this = this;
		var setting = $.extend({
			data: [],
			defaultValue: '', // 默认值
			holder: '请选择',
			fields: {
				val: 'value', // value值的名称
				name: 'name' // name值的名称
			},
			syns: false // 是否异步
		}, option || {})
		if(option.syns) {
			$.ajax({
           	 	type: "GET",
                url: "${ctx}/platform/access/menu/create.json",
                contentType: "application/json; charset=utf-8",
                data: {},
                dataType: "json",
                success: function (res) {
                	var data = res.data;
                	var options = '<option value="">' + setting.holder +'</option>';
                	if (data && data.length > 0) {
                		$.each(data, function(index, item){
                			if(setting.defaultValue == item[setting.fields.val]) 
                				options += '<option selected="selected" value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
                			else
                				options += '<option value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
                		})
                	}
                	_this.html(options)
                },
                error: function (message) {
                }
           })
		} else {
			var options = '<option value="">' + setting.holder +'</option>';
        	if (setting.data && setting.data.length > 0) {
        		$.each(setting.data, function(index, item){
        			if(setting.defaultValue == item[setting.fields.val]) 
        				options += '<option selected="selected" value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
        			else
        				options += '<option value="' + item[setting.fields.val] +'">' + item[setting.fields.name] +'</option>';
        		})
        	}
        	_this.html(options)
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
})(jQuery);
/*
$(window).resize(function() {
	location.reload()
});
*/
function _init_table(settings) {
	var h = window.screen.availHeight;
	layui.use("table", function() {
		var table = layui.table;
		var layer = layui.layer;
		var config = $.extend({
			totalRow : false,
			height : "full", // 高度最大化减去差值
			size : 'lg', // 小尺寸的表格
			groupBtn: {},
			parseData : function(res) {
				return {
            		"code": res.code, //解析接口状态
            		"msg": res.message, //解析提示文本
            		"count": res.total, //解析数据长度
            		"data": res.rows.item //解析数据列表
        	    };
			},
			text : {
				none : '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
			}
		}, settings || {});
		// 第一个实例
		var tableInstance = table.render(config);
		table.on('toolbar(' + config.filter + ')', function(obj) {
			var func = config.groupBtn[obj.event];
			switch (obj.event) {
			case 'createAction':
				if (func)
					eval(func).call(this);
				break;
			case 'deleteAction':
				if (func)
					eval(func).call(this);
				break;
			case 'searchAction':
				var search = $("#" + config.searchForm).getForm();
				var searchObj = $("#" + config.searchForm).serialize();
				console.log(searchObj)
				layer.msg('你点击了搜索按钮');
				tableInstance.reload({
					where : config.method.toUpperCase() == 'POST' ? search : searchObj
				});
				break;
			}
		});

		// 监听行工具事件
		table.on('tool(' + config.filter + ')', function(obj) {
			var data = obj.data;
			console.log('obj', obj);
			// console.log(obj)
			if (obj.event === 'del') {
				layer.confirm('确认删除吗？', function(index) {
					// config.devare();
					obj.del()
					// 这里以搜索为例
					tableInstance.reload({
						where : { // 设定异步数据接口的额外参数，任意设
							aaaaaa : 'xxx',
							bbb : 'yyy'
						}
					});
					layer.close(index);
				});
			} else if (obj.event === 'edit') {
				layer.prompt({
					formType : 2,
					value : data.email
				}, function(value, index) {
					obj.update({
						email : value
					});
					layer.close(index);
				});
			}
		});
	});
}

var page = {
	openWindow : function(options) {
		var settings = $.extend({
			type : 2,
			title : options.title || '提示信息',
			area : [ options.width || '800px', options.height || '450px' ],
			fixed : true, // 不固定
			offset : 'auto',
			anim : 5,
			maxmin : true,
			move : false,
			shadeClose : false,
			shade : 0.6,
			content : options.url,
			end : function(index) {
				options.callback ? options.callback(index) : null
			}
		})
		layui.use("layer", function() {
			var layer = layui.layer;
			layer.open(settings);
		});
	},
	dataTable : function(option) {
		var settings = $.extend({
			elem : '',
			title : "数据列表",
			totalRow : false,
			toolbar : '',
			method : 'POST',
			filter : 'demo',
			loading: true, //翻页加loading
			url : '',
			cols : [],
			// 开启分页
			page: {
				groups: 6,
            	first:'首页',
             	prev:'上一页',
             	next:'下一页',
             	last:'尾页',
             	layout: ['count', 'skip','prev', 'page', 'next', 'limit', 'refresh']
            },
			limit: 10
		}, option || {});
		_init_table(settings);
	}
};