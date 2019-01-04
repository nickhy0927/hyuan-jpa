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

var _date_format = function (now) {
    var year = now.getYear();
    var month = now.getMonth() + 1;
    var date = now.getDate();
    var hour = now.getHours();
    var minute = now.getMinutes();
    var second = now.getSeconds();
    return year + "-" + month + "-" + date + " " + hour + ":" + minute + ":" + second;
};

function _closeLoading() {
    layer.closeAll();
}

function _openLoading(msg) {
    layui.use("layer", function () {
        var msg = msg ? msg : '正在保存数据，请稍等...';
        layer.msg(msg, {icon: 16, time: 1000 * 10000, shade: [0.3, '#000']});
    });

}

function _openTip(content, isAlert, callback, title) {
    layui.use("layer", function () {
        if (isAlert) { // skin: 'layui-layer-molv' //样式类名  自定义样式
            layer.alert(content, {
                title: title ? title : '提示信息',
                skin: 'layui-layer-molv', //样式类名  自定义样式
                // anim: 1, //动画类型
                // icon: 6,    // icon
                closeBtn: 0
            }, function () {
                $.closeLoading();
                if (callback !== undefined)
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
    });
}
function _tip(result, isAlert, callback, title) {
	layui.use("layer", function () {
		if (isAlert) { // skin: 'layui-layer-molv' //样式类名  自定义样式
			if(result.status == 0) {
				var index = layer.alert(result.message, {
					title: title ? title : '提示信息',
					skin: 'layui-layer-molv', //样式类名  自定义样式
					anim: 1, //动画类型
					icon: 6,    // icon
					closeBtn: 0
				}, function () {
					$.closeLoading();
					if (callback !== undefined) {
						callback(layer, index);
					}
				});
			} else {
				layer.alert(result.message, {
					title: title ? title : '提示信息',
					skin: 'layui-layer-molv', //样式类名  自定义样式
					anim: 1, //动画类型
					icon: 6,    // icon
					closeBtn: 0
				}, function () {
					$.closeLoading();
					return false;
				});
			}
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
	});
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
            fmt = fmt.replace(RegExp.$1, (RegExp.$1.length === 1) ? (o[k]) : (("00" + o[k]).substr(("" + o[k]).length)));
        }
    }
    return fmt;
};

/**
 * 打开窗口
 * @private
 * @param parameters
 */
function _openWindow(parameters) {
    var title = parameters.title;
    var width = parameters.width;
    var height = parameters.height;
    var url = parameters.url;
    var callback = parameters.callback;
    layer.open({
        type: 2,
        title: title,
        area: [width || '700px', height || '450px'],
        fixed: true, //不固定
        move: false,
        shadeClose: false,
        shade: 0.6,
        maxmin: false,
        content: url,
        end: callback ? callback(index) : null
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

$.extend({
    openTip: _openTip,
    tip: _tip,
    openLoading: _openLoading,
    closeLoading: _closeLoading,
    dateSimpleFormat: _date_format,
    openWindow: _openWindow,
    parentOpenWindow: _parentOpenWindow
});