function _init_table(settings) {
	var h = window.screen.availHeight;
	layui.use("table", function() {
		var table = layui.table;
		var layer = layui.layer;
		var config = $.extend({
			totalRow : false,
			height : "full", // 高度最大化减去差值
			size : h < 1000 ? 'sm' : 'lg', // 小尺寸的表格
			done : function(res, curr, count) {
				// 如果是异步请求数据方式，res即为你接口返回的信息。
				// 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
				console.log(res);
				// 得到当前页码
				console.log(curr);
				// 得到数据总量
				console.log(count);
			},
			text : {
				none : '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
			}
		}, settings || {});
		console.log('config', config);
		// 第一个实例
		var tableInstance = table.render(config);
		table.on('toolbar(' + config.filter + ')', function(obj) {
			var func = config.groupBtn[obj.event];
			switch (obj.event) {
			case 'add':
				if (func)
					eval(func).call(this);
				break;
			case 'del':
				if (func)
					eval(func).call(this);
				break;
			case 'search':
				var search = $("#" + config.searchForm).serializeArray();
				var searchObj = $("#" + config.searchForm).serialize();
				// console.log('search', search);
				layer.msg('你点击了搜索按钮');
				// console.log(config.method.toUpperCase() == 'POST' ? search :
				// searchObj)
				tableInstance.reload({
					where : config.method.toUpperCase() == 'POST' ? search
							: searchObj
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
		// console.log('page', config)
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
		$.openLoading('正在加载数据，请稍等...');
		var settings = $.extend({
			elem : '',
			title : "数据列表",
			totalRow : false,
			toolbar : '',
			method : 'get',
			filter : 'demo',
			loading: true, //翻页加loading
			url : '',
			cols : [],
			page : true, // 开启分页
			parseData : function(res) {
				console.log("res", res);
				// 将原始数据解析成 table 组件所规定的数据
				return {
					code : res.status, // 解析接口状态
					msg : res.message, // 解析提示文本
					count : res.object.totalRecord, // 解析数据长度
					data : res.object.content
				// 解析数据列表
				};
			},
			edit : function() {
			},
			limit: 10,
			page:{
		    	groups: 10 //只显示 5 个连续页码
		    },
			done : function() {
				var toolH = $('.layui-table-tool').height();
				var pageH = $('.layui-table-page').height();
				var sh = $('.search-block').height();
				var h = $(window).height() - 60 - toolH - pageH - sh;
				$(".layui-table-box").css({
					"overflow-y" : "auto",
					"height" : h + "px"
				});
				setTimeout(function() {
					$.closeLoading();
				}, 1000)
			}
		}, option || {});
		console.log('page', settings)
		_init_table(settings);
	}
};