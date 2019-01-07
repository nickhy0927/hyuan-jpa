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
    var msg = msg ? msg : "";
    layer.msg(msg, {icon: 16, time: 1000 * 10000, shade: [0.3, '#000']});
}

function _openTip(content, isAlert, callback, title) {
    if (isAlert) { // skin: 'layui-layer-molv' //样式类名  自定义样式
        layer.alert(content, {
            skin: 'layui-layer-molv', //样式类名  自定义样式
            // anim: 2, //动画类型
            icon: 6,    // icon
            closeBtn: 0
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

/**
 * 打开窗口
 * @param title 标题
 * @param width 宽度
 * @param height 高度
 * @param url 访问地址
 * @private
 */
function _openWindow(title, width, height, url, callback) {
    layer.open({
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

function _submitAjax(opt, success, error) {
    $.openLoading(opt.msg);
    var ctx = opt.ctx;
    $.ajax({
        url: ctx + opt.url,
        method: opt.method || 'POST',
        dataType: opt.dataType || 'JSON',
        data: opt.data,
        success: function (data) {
            _success(data, success);
        },
        error: function (err) {
            if (error) {
                error();
                return ;
            }
            $.closeLoading();
            $.openTip(err, true, function () {
                $.closeLoading();
            }, '提示信息');
            return;
        }
    })
}

function _success(data, success) {
    try {
        data = eval('(' + data + ')');
    } catch (err) {
    }
    if (data.status != 200 && data.code != 200) {
        $.openTip(data.msg, true, function () {
            $.closeLoading();
        }, '提示信息');
        return;
    }
    $.closeLoading();
    success(data);
}

function _validation(id, rules, fn, messages) {
    $("#" + id).validate({
        rules: rules,
        messages: messages || {},
        onkeyup: false,
        focusCleanup: true,
        success: "valid",
        submitHandler: function (form) {
            fn(form);
        }
    });
}


$.extend({
    /**
     * 打开确认框、弹出框
     */
    openTip: _openTip,
    openLoading: _openLoading,
    closeLoading: _closeLoading,
    datadel: _datadel,
    dateSimpleFormat: _date_format,
    openWindow: _openWindow,
    parentOpenWindow: _parentOpenWindow,
    submitAjax: _submitAjax,
    validation: _validation
});