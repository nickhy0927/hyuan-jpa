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
			operate: {},
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
			var checkStatus = table.checkStatus(config.id);
			var data = checkStatus.data;
			var array = [];
			if (!data) data = [];
			else {
				$.each(data, function(index, item) {
					array.push(item.id);
				})
			}
			if(func)
				eval(func).call(this, tableInstance, array);
		});

		// 监听行工具事件
		table.on('tool(' + config.filter + ')', function(obj) {
			var func = config.operate[obj.event];
			if(func)
				eval(func).call(this, tableInstance, obj.data);
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

function number_format(number, decimals, dec_point, thousands_sep) {
    /*
    * 参数说明：
    * number：要格式化的数字
    * decimals：保留几位小数
    * dec_point：小数点符号
    * thousands_sep：千分位符号
    * */
    number = (number + '').replace(/[^0-9+-Ee.]/g, '');
    var n = !isFinite(+number) ? 0 : +number,
        prec = !isFinite(+decimals) ? 0 : Math.abs(decimals),
        sep = (typeof thousands_sep === 'undefined') ? ',' : thousands_sep,
        dec = (typeof dec_point === 'undefined') ? '.' : dec_point,
        s = '',
        toFixedFix = function (n, prec) {
            var k = Math.pow(10, prec);
            return '' + Math.ceil(n * k) / k;
        };

    s = (prec ? toFixedFix(n, prec) : '' + Math.round(n)).split('.');
    var re = /(-?\d+)(\d{3})/;
    while (re.test(s[0])) {
        s[0] = s[0].replace(re, "$1" + sep + "$2");
    }

    if ((s[1] || '').length < prec) {
        s[1] = s[1] || '';
        s[1] += new Array(prec - s[1].length + 1).join('0');
    }
    return s.join(dec);
}


Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1,                 //月份
        "d+": this.getDate(),                    //日
        "h+": this.getHours(),                   //小时
        "m+": this.getMinutes(),                 //分
        "s+": this.getSeconds(),                 //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds()             //毫秒
    };
    if (/(y+)/.test(fmt)) {
        fmt = fmt.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    }
    for (var k in o) {
        if (new RegExp("(" + k + ")").test(fmt)) {
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length == 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

var _date_format = function (now) {
    var year = now.getYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
};

function _datadel(ajaxOption, single) {
    var ids = $.getCheckedValue();
    if ((!ids && ids != "" && !single) || (!single && ids.length == 0)) {
        $.openTip("请选择一项再进行操作.", true, function () {
            _closeLoading();
            return;
        })
    } else {
    	ids = ids.join(",");
        $.openTip(ajaxOption.message ? ajaxOption.message : '确定删除吗？', false, function () {
            _closeLoading();
            $.openLoading('正在进行删除操作,请稍后...');
            ajaxOption = !ajaxOption ? {} : ajaxOption;
            if (!ajaxOption.data || !ajaxOption.data.id) {
                ajaxOption.data = {id: ids};
            }
            $.ajax(ajaxOption);
        });
    }
}

function _closeLoading() {
    layer.closeAll();
}

function _openLoading(msg) {
    var msg = msg ? msg : "正在加载中，请稍后...";
    layer.msg(msg, {icon: 16, time: 1000 * 10000, shade: [0.3, '#000']});
}

function _openTip(content, isAlert, callback, title) {
	if (isAlert) { 
        layer.alert(content, {
        	//样式类名  自定义样式
            skin: 'layui-layer-molv', 
            // anim: 2, //动画类型
            // icon
            icon: 6,    
            closeBtn: 0,
            btn: ['确定'] //按钮
        }, function () {
            $.closeLoading();
            if (callback != undefined)
                callback();
        });
    } else {
        layer.confirm(content, {
            skin: 'layui-layer-molv', //样式类名  自定义样式
            title: title ? title : '提示信息',
            closeBtn: 0,
            btn: ['确定', '取消'] //按钮
        }, function () {
            _closeLoading();
            callback();
        }, function () {
            _closeLoading();
        });
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
function _openWindow(options) {
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
function _parentOpenWindow(title, width, height, url, callback) {
    parent.layer.open({
        type: 2,
        title: title,
        area: [width || '700px', height || '450px'],
        fixed: false, //不固定
        move: false,
        shadeClose: false,
        shade: 0.6,
        maxmin: false,
        content: url,
        end: callback ? callback(index) : null
    });
}
$.ajaxSetup({
    // 发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"
    dataType: "json",
    type: "POST",
    // 布尔值，表示浏览器是否缓存被请求页面。默认是 true。
    cache: false,
    // 规定请求的字符集。
    scriptCharset: "UTF-8"
});

$(document).ready(function () {
    var startTime = new Date().getTime();
    $(document).ajaxStart(function () {
        $.openLoading();
    }).ajaxSuccess(function (event, xhr, settings) {
    	var res = JSON.parse(xhr.responseText);
    	console.log('res=== ', res)
    	if (res.code != 1) {
    		$.closeLoading();
    	}
    }).ajaxError(function () {
        $.closeLoading();
        console.log('全局错误处理...')
    })
});

$.extend({
	openTip: _openTip,
    openLoading: _openLoading,
    closeLoading: _closeLoading,
    dateSimpleFormat: _date_format,
   /* openWindow: _openWindow,*/
    parentOpenWindow: _parentOpenWindow,
    saveInfo: function(option) {
    	if(option.openType == 'alert') {}
    	$.ajax({
    		url: option.url,//发送请求
	    	data: option.data,
	    	openType: 'alert',
	    	success: function(res) {
	    		$.openTip(res.message, true, function() {
	    			var fn = eval(option.success);
	                fn.call(this, res);
	    		});
	    	}
		})
    }
});