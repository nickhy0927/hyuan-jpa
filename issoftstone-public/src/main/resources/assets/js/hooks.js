$.extend({
    requestAjax: _setAjaxData,
    openLoading: _openLoading,
    closeLoading: _closeLoading
});

$.ajaxSetup({
    // 发送数据到服务器时所使用的内容类型。默认是："application/x-www-form-urlencoded"
    dataType: "json",
    type: "POST",
    // 布尔值，表示浏览器是否缓存被请求页面。默认是 true。
    cache: false,
    // 规定请求的字符集。
    scriptCharset: "UTF-8"
});

function _openLoading() {
    layer.msg('正在加载中，请稍等...', {icon: 16, shade: [0.8, '#393D49'], time: 1000000000});
}

function _closeLoading() {
    layer.closeAll();
}

function _setAjaxData(options, successcallback) {
    $.ajax({
        url: options.url,
        data: options.data || {},
        success: function (res) {
            console.log(res);
            if (res.code === 0) {
                successcallback(res.data);
            } else {
                console.log('执行操作出错了..111.')
            }
        },
        error: function () {
            console.log('执行操作出错了...')
        }
    })
}

$(document).ready(function () {
    var startTime = new Date().getTime();
    $(document).ajaxStart(function () {
        $.openLoading();
    }).ajaxSuccess(function (event, xhr, settings) {
        var endTime = new Date().getTime();
        console.log((endTime - startTime) )
        if ((endTime - startTime) / 1000 <= 3) {
            setTimeout(function() {
                $.closeLoading();
                startTime = new Date().getTime();
            }, 1000);
        } else {
            $.closeLoading();
        }
    }).ajaxError(function () {
        $.closeLoading();
        console.log('全局错误处理...')
    })
});
