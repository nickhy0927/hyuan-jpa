var issUtils = {
    /**
     * 打开确认框、弹出框
     */
    openTip: _openTip,
    openLoading: openLoading,
    closeLoading: _closeLoading,
    getCheckboxIds: _getCheckboxIds,
    datadel: _datadel,
    dateSimpleFormat: _date_format,
    selectUser: _selectUser
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
    var ids = $.getCheckboxIds();
    if (!ids && !single) {
        $.openTip("请选择一项再进行操作.", true, function () {
            _closeLoading();
            return;
        })
    } else {
        $.openTip('你确定删除吗？', false, function () {
            _closeLoading();
            ajaxOption = !ajaxOption ? {} : ajaxOption;
            if (!ajaxOption.data || !ajaxOption.data.id) {
                ajaxOption.data = {
                    id: ids
                };
            }
            $.ajax(ajaxOption);
        });
    }
}

function _getCheckboxIds() {
    var ids = "";
    $("input[name='checkOne']").each(function () {
        if (this.checked) {
            var id = $(this).attr('data-id');
            ids += id + ",";
        }
    });
    if (ids && ids.length > 1) {
        ids = ids.substring(0, ids.length - 1);
    }
    return ids;
}


function _closeLoading() {
    layer.closeAll();
}

function openLoading(msg) {
    var msg = msg ? msg : "";
    layer.load(2, {
        shade: [0.3, '#000']
    });
}

function _openTip(content, isAlert, callback) {
    if (isAlert) {
        layer.confirm(content, {
            closeBtn: 0,
            btn: ['确定'] //按钮
        }, function () {
            if (callback)
                callback();
        }, function () {
            _closeLoading();
        });
    } else {
        layer.confirm(content, {
            closeBtn: 0,
            btn: ['确定', '取消'] //按钮
        }, function () {
            callback();
        }, function () {
            _closeLoading();
        });
    }
}

Date.prototype.format = function (fmt) {
    var o = {
        "M+": this.getMonth() + 1, //月份
        "d+": this.getDate(), //日
        "h+": this.getHours(), //小时
        "m+": this.getMinutes(), //分
        "s+": this.getSeconds(), //秒
        "q+": Math.floor((this.getMonth() + 3) / 3), //季度
        "S": this.getMilliseconds() //毫秒
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
}

function _selectUser() {
    alert(1)
}
