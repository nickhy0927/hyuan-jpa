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
	$.fn.serializeObject = function(){
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
	$.fn.dataTable = function(option) {   
		tableManager = $(this);
		var sh = $(".search-block").height();
		var sc = $(".layui-btn-container").height();
		var h = $(window).height() - (sh || 0) - (sc || 0);
		var pageSize = option.pageSize || 10;
		if(h <= 500) pageSize = 5;
		if(h < 550 && h > 500) pageSize = 10;
		if(h <= 700 && h > 550) pageSize = 12;
		if(h <= 800 && h > 700) pageSize = 14;
		if(h > 800 && h < 850) pageSize = 17;
		if(h > 850) pageSize = 20;
		console.log(h, sh)
		var setting = $.extend({
			gridManagerName: '',
			disableCache: true,
			height: 'auto',
			supportAjaxPage: true,
			ajax_data: '${ctx}/platform/access/role/list.json',
			ajax_type: 'POST',
			firstLoading: true,
			// 用于配置是否支持宽度调整功能。defualt true 
			supportAdjust: false,
			// 用于配置是否支持拖拽功能。 如需禁用此功能，需要在初始化GridManager时配置该项
			supportDrag: false,
			supportMenu: false,
			// 请求参数中当前页key键值,默认为cPage
			currentPageKey: 'page',
			// 请求参数中每页显示条数key健值, 默认为pSize
			pageSizeKey: 'limit',
			mergeSort: true,
			useNoTotalsMode: true,
			useRadio: false, // 单选
			useRowCheck: false,
			/* ajax_headers: {
				'Content-Type': 'application/json'
			}, */
			query: {
				pluginId: 1
			},
			sizeData: [1 * pageSize, 2 * pageSize, 3 * pageSize, 4 * pageSize, 5 * pageSize, 10 * pageSize],
			pageSize: pageSize,
			ajax_success:function(res) {
				console.log("ajax_success: ", eval('(' + res + ')'));
			},
			columnData: [{
				// 列的唯一索引。字符串类型，必设项    
			    key: '',
			    // 列的显示文本。字符串类型，必设项
			    text: '', 
			    remind: '',
				sorting: 'ASC',
				// 该列是否禁止使用个性配置功能(宽度调整、位置更换、列的显示隐藏)
			    disableCustomize: false,
			    // 是否显示, 默认值 true
			    isShow: true,
			    // 列所占宽度, 字符串类型，非必设项
			    // 需要注意的是: 
			    // 1.如果当前列的th内文本实际占用宽度大于该参数指定的宽度时，会导致出现滚动条。
			    // 2.请不要设置所有的列，最少需要保留一列交由gridmanager进行自适应(注意该列的isShow必须为true)
			    width: '100px',
			    // 列文本对齐信息，字符串类型，非必设项
			    // 三种值: 'left', 'center', 'right'
			    align: 'left',
			    // 列的排序信息。字符串类型，非必设项
			    // 1、'': 该列支持排序，但初始化时不指定排序类型
			    // 2、'DESC': 该列支持排序，并指定为降序。可通过参数[sortDownText]来指定降序所使用的字符串
			    // 3、'ASC': 该列支持排序，并指定为升序。可通过参数[sortUpText]来指定升序所使用的字符串
			    sorting: 'DESC',
			    // 该列是否禁止使用个性配置功能(宽度调整、位置更换、列的显示隐藏)
			    template: function(nodeData, rowData){
			        return nodeData != null ? nodeData : "";
			    }
			}],
			/*topFullColumn: {
		        // 顶部通栏所使用的模板
		        template: function(row){
		            return '<div>我是通栏，哈哈</div>'
		        }
		    },*/
			// 分页前事件
			pagingBefore: function(query){
				// console.log('pagingBefore', query);
			},
			// 分页后事件
			pagingAfter: function(data){
				// console.log('pagingAfter', data);
			},
			// 排序前事件
			sortingBefore: function (data) {
				console.log('sortBefore', data);
			},
			// 排序后事件
			sortingAfter: function (data) {
				// console.log('sortAfter', data);
			},
			// 宽度调整前事件
			adjustBefore: function (event) {
				// console.log('adjustBefore', event);
			},
			// 宽度调整后事件
			adjustAfter: function (event) {
				// console.log('adjustAfter', event);
			},
			// 拖拽前事件
			dragBefore: function (event) {
				// console.log('dragBefore', event);
			},
			// 拖拽后事件
			dragAfter: function (event) {
				//console.log('dragAfter', event);
			}
		}, option || {});
		$(this).GM(setting);
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
			parseData : function(res) {
				return {
            		"code": res.code, //解析接口状态
            		"msg": res.message, //解析提示文本
            		"count": res.total, //解析数据长度
            		"data": res.rows.item //解析数据列表
        	    };
			},
			done : function() {
				/*var toolH = $('.layui-table-tool').height();
				var pageH = $('.layui-table-page').height();
				var sh = $('.search-block').height();
				var h = $(window).height() - 60 - toolH - pageH - sh;
				$(".layui-table-box").css({
					"overflow-y" : "auto",
					"height" : h + "px"
				});
				setTimeout(function() {
					$.closeLoading();
				}, 2000)*/
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