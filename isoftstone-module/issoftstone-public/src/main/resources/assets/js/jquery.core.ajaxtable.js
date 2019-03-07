var _dataIds = [];
var trBackgroundUnChecked = {
    "background": ""
};
var background = "rgb(239, 239, 224)";
var trBackgroundChecked = {
    "background": "rgb(239, 239, 224)"
};

//按升序排列
var _sort = function (arr) {
    for (i = 0; i < arr.length - 1; i++) {
        for (j = 0; j < arr.length - 1 - i; j++) {
            if (arr[j] > arr[j + 1]) {
                var temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
    return arr;
}

Array.prototype.distinct = function () {
    var arr = this, result = [], i, j, len = arr.length;
    for (i = 0; i < len; i++) {
        for (j = i + 1; j < len; j++) {
            if (arr[i] === arr[j]) {
                j = ++i;
            }
        }
        result.push(arr[i]);
    }
    return result;
}

function sortarr(data) {
    var arrs = [], datas = [];
    for (var index in data) {
        if (!data[index]) arrs.push(-1);
        else arrs.push(data[index]);
    }
    _sort(arrs);
    for (var i = 0; i < arrs.length; i++) {
        for (var d in data) {
            if (!data[d] && i == 0) {
                datas.push({name: d, value: ''})
                continue;
            }
            if (arrs[i] == data[d]) {
                datas.push({name: d, value: arrs[i]});
                continue;
            }
        }
    }
    return datas;
}
/*
Element.prototype.data = function (key, value) {
    var _this = this,
        _dataName = 'tableData',  // 存储至DOM上的对象标记, 这里只是测试用名
        _data = {};
    // 未指定参数,返回全部
    if (typeof key === 'undefined' && typeof value === 'undefined') {
        return _this[_dataName];
    }
    // setter
    if (typeof(value) !== 'undefined') {
        // 存储值类型为字符或数字时, 使用attr执行
        var _type = typeof(value);
        if (_type === 'string' || _type === 'number') {
            _this.setAttribute(key, value);
        }
        _data = _this[_dataName] || {};
        _data[key] = value;
        _this[_dataName] = _data;
        return this;
    }
    // getter
    else {
        _data = _this[_dataName] || {};
        return _data[key] || _this.getAttribute(key);
    }
};*/

var tableData = undefined;
/**
 * 获取所选数据的对象
 */
$.extend({
    getCheckedObjArray: function () {
        var array = [];
        $.each(_dataIds, function (index, id) {
            array.push(tableData.data(id));
        });
        return array;
    },
    getCheckedValue: function () {
        return _dataIds;
    }
});

(function ($) {

    /**
     * 下拉选择
     */
    $.fn.select = function (options) {
        var settings = $.extend({
            data: {},
            selectId: '',
            checkValue: ''
        }, options || {});
        var html = "";
        var datas = sortarr(settings.data);
        for (var i = 0; i < datas.length; i++) {
            var data = datas[i];
            if (settings.checkValue && data.value == settings.checkValue) {
                html += "<option value=\"" + data.value + "\" selected=\"selected\">" + data.name + "</>";
            } else
                html += "<option value=\"" + data.value + "\">" + data.name + "</>";
        }
        $(this).html(html);
    };

    /**
     * 获取checkbox选中的值
     */
    $.fn.getCheckedValue = function () {
        return _dataIds
    };
    /**
     * 获取所选数据的对象
     */
    $.fn.getCheckedObjArray = function () {
        var array = [];
        $.each(_dataIds, function (index, id) {
            array.push(tableData.data(id));
        });
        return array;
    };
    /**
     * 列表组件
     */
    $.fn.dataGrid = function (options) {
        var settings = $.extend({
            title: '',
            url: '',
            method: 'POST',
            pageSize: 10,
            tableId: '',
            searchButtonId: '',
            pageShow: false,
            dataType: 'JSON',
            checkbox: false,
            single: false,
            checkedIds: [],
            orderField: 'id',
            sort: 'desc',
            queryParamsId: [],
            columns: [{
                field: '',
                className: 'text-l',
                description: '',
                sort: false,
                width: undefined,
                paramFormatter: function () {
                    return '';
                }
            }],
            onLoadSuccess: undefined,
            trClick: undefined
        }, options || {});
        tableData = $(settings.tableId);
        var limits = [settings.pageSize, 10, 20, 30, 40, 50, 60];
        limits = limits.distinct();
        _sort(limits);
        settings.limits = limits;
        var array = settings.checkedIds;
        for (var i = 0; i < array.length; i++) {
            var a = array[i];
            if (a && a != null && a != "null")
                _dataIds.push(a);
        }
        _dataIds = settings.checkedIds;
        var tableId = settings.tableId.substring(1);
        var title = tableId + "-tableTitle";
        $("#" + title).remove();
        var titleClass = '';
        if (!settings.title) {
            titleClass = 'table-title-hidden';
        }
        $(this).before("<div class=\"" + titleClass + "\" id=\"" + title + "\" style=\"background:#f2f2f2;height:40px;line-height:40px;font-size:15px;\"><span style=\"margin-left:7px;font-weight:bold\">"
            + settings.title + "</span><input type=\"hidden\" value=\"1\" id=\""
            + tableId + "_currentPage\" name=\"" + tableId + "_currentPage\"/></div>");
        _create_table_head(settings);
        if (settings.searchButtonId) {
            $(settings.searchButtonId).on('click', function () {
                var tableId = settings.tableId.substring(1);
                $("#" + tableId + "_currentPage").val(1);
                setTimeout(function () {
                    _query_data_ajxa(settings);
                    _select_checkbox_all();
                }, 500);
            });
        }
        location.hash.replace('#!currentPage=', '')
        setTimeout(function () {
            _query_data_ajxa(settings);
            _select_checkbox_all();
        }, 500);
        $(document).keyup(function (event) {
            if (event.keyCode == 13) {
                console.log('点击回车搜索')
                $("#" + tableId + "_currentPage").val(1);
                _query_data_ajxa(settings);
                _select_checkbox_all();
            }
        });
    }
})(jQuery);

var _create_table_head = function (settings) {
    var trStr = "",
        th = "";
    if (settings.checkbox && !settings.single) {
        if (settings.checkedIds.length == settings.pageSize) {
            th += "<th style=\"width:25px;\"><input style=\"width: 15px;height: 18px;\" checked=\"checked\" type=\"checkbox\" id=\"ckAll\"/></th>";
        } else
            th += "<th style=\"width:25px;\"><input style=\"width: 15px;height: 18px;\" type=\"checkbox\" id=\"ckAll\"/></th>";
    } else if (settings.checkbox) {
        th += "<th style=\"width:25px;\"></th>";
    }
    var array = settings.columns;
    for (var i = 0; i < array.length; i++) {
        var className = array[i].className;
        var key = array[i].field;
        var description = array[i].description ? array[i].description : "";
        if ('id' != key) {
            if (array[i].sort) {
                if (array[i].sortField) {
                    key = array[i].sortField
                }
                th += "<th style=\"width:" + array[i].width + "px\" class=\"" + className + "\">" + description + "&nbsp;&nbsp;<a data-field=\""
                    + key + "\" class=\"sort sorting\" href=\"#\"></a></th>";
            } else
                th += "<th style=\"width:" + array[i].width + "px\" class=\"" + className + "\">" + description + "</th>";
        }
    }
    var tableId = settings.tableId.substring(1);
    var theadId = tableId + "-data-head";
    trStr += "<thead id=\"" + theadId + "\" class=\"data-head\"><tr>" + th + "</tr></thead>";
    var _body_id = tableId + "-data-body";
    trStr += "<tbody id=\"" + _body_id + "\"></tbody>";
    $(settings.tableId).html(trStr);
    _order_by_sort(settings, theadId);
};

/**
 * 排序
 */
var _order_by_sort = function (settings, theadId) {
    var dex = -1;
    $("#" + theadId).find('tr>th').each(function (index) {
        var a = $(this).find('a');
        if ($(a).hasClass('sort')) {
            $(a).click(function () {
                if (dex != index) {
                    $("#" + theadId).find('tr>th').each(function (d) {
                        var i = $(this).index();
                        if (i == dex) {
                            $(this).find('a').addClass('sorting').removeClass('sorting_desc').removeClass('sorting_asc').removeClass('actived');
                        }
                    });
                    dex = index;
                }
                if ($(this).hasClass('sorting_asc')) {
                    $(this).removeClass('sorting_asc').removeClass('sorting').addClass('sorting_desc actived');
                    $(this).parent().addClass('actCls');
                    $(this).parent().siblings().removeClass('actCls');
                    settings.sort = 'desc';
                } else if ($(this).hasClass('sorting_desc')) {
                    $(this).removeClass('sorting_desc').removeClass('sorting').addClass('sorting_asc actived');
                    $(this).parent().addClass('actCls');
                    $(this).parent().siblings().removeClass('actCls');
                    settings.sort = 'asc';
                } else if ($(this).hasClass('sorting')) {
                    $(this).removeClass('sorting').addClass('sorting_desc actived');
                    $(this).parent().addClass('actCls');
                    $(this).parent().siblings().removeClass('actCls');
                    settings.sort = 'desc';
                }
                settings.orderField = $(a).attr('data-field');
                setTimeout(function () {
                    _query_data_ajxa(settings);
                    _select_checkbox_all();
                }, 200);
            });
        }
    });
};
//获取所有的查询条件
var _get_query_condition = function (settings) {
    var ids = settings.queryParamsId;
    var jsonStr = "{";
    for (var i in ids) {
        var ff = undefined;
        try {
            ff = ids[i].substring(1);
            var val = $(ids[i]).val();
            if (!val) {
                val = "";
            }
            jsonStr += '"' + ff + '":"' + val + '",';
        } catch (e) {

        }

    }
    jsonStr = (jsonStr.length > 1 ? jsonStr.substring(0, jsonStr.length - 1) : jsonStr) + "}";
    var object = eval("(" + jsonStr + ")");
    var orderField = settings.orderField;
    object.order = orderField;
    object.sort = settings.sort;
    return object;
};
// 查询服务器数据
var _query_data_ajxa = function (settings) {
    var params = _get_query_condition(settings);
    var tableId = settings.tableId.substring(1);
    params.limit = settings.pageSize;
    params.page = $("#" + tableId + "_currentPage").val();
    $.ajax({
        method: settings.method,
        url: settings.url,
        async: false,
        data: params,
        dataType: settings.dataType,
        success: function (data) {
            if (data.code == 200) {
                data = data.object;
                _create_grid_table(data, settings);
                var func = settings.onLoadSuccess;
                if (func) {
                    var fn = eval(func);
                    fn.call(this, data.content ? data.content : data.map, $(settings.tableId));
                }
            } else
                $.openTip(data.code, true, function () {
                    $.closeLoading();
                });
        },
        error: function (err) {
            $.openTip('获取数据失败，请稍后再试...', true, function () {
                console.log(err);
                $.closeLoading();
            });
        }
    });
};

var _each_value = function (key, json) {
    var ks = key.split(".");
    if (ks.length > 1) {
        var k = ks[0];
        var jsonStr = json[k];
        if (json instanceof Object) {
            var v = _each_value(ks[1], jsonStr);
            return v;
        }
    } else
        return json ? json[key] : "";
};
// 创建表格
var _create_grid_table = function (data, settings) {
    var tableId = settings.tableId.substring(1);
    var objs = data.content;
    var trStrBuuiler = "";
    var array = settings.columns;
    var len;
    for (var j = 0; len = objs.length, j < len; j++) {
        var trStr = "";
        var id = objs[j]['id'];
        tableData.data(id, objs[j]);
        var jsonStr = eval(objs[j]);
        var bool = _is_exsit(_dataIds, jsonStr['id']);
        if (bool)
            trStr += '<tr style=\'background:rgb(239, 239, 224)\' data-id ="' + jsonStr["id"] + '">';
        else
            trStr += '<tr data-id ="' + jsonStr["id"] + '">';
        var tdObj = "";
        for (var i = 0; i < array.length; i++) {
            var key = array[i].field;
            var value = _each_value(key, jsonStr);
            var paramVal_ = _each_value(key, jsonStr);
            if (array[i].paramFormatter) {
                value = array[i].paramFormatter;
                var fn = eval(value);
                value = fn.call(this, jsonStr, paramVal_);
            }

            //列表显示序号
            if (key === '_index') {
                value = j + 1;
            }
            //公共分页组件对value转码
            value = htmlDecode(value);

            if (settings.checkbox && i == 0) {
                var checked = value ? '' : '';
                if (!settings.single) {
                    if (bool)
                        tdObj += "<td style=\"width:25px;\"><input checked=\"checked\" data-id=\"" + jsonStr['id'] + "\" "
                            + checked + " style=\"width: 15px;height: 18px;\" type=\"checkbox\" name=\"checkOne\"/></td>";
                    else
                        tdObj += "<td style=\"width:25px;\"><input data-id=\"" + jsonStr['id'] + "\" "
                            + checked + " style=\"width: 15px;height: 18px;\" type=\"checkbox\" name=\"checkOne\"/></td>";
                } else {
                    if (bool)
                        tdObj += "<td style=\"width:25px;\"><input checked=\"checked\" data-id=\"" + jsonStr['id'] + "\" "
                            + checked + " style=\"width: 15px;height: 18px;\" type=\"radio\" name=\"checkOne\"/></td>";
                    else
                        tdObj += "<td style=\"width:25px;\"><input data-id=\"" + jsonStr['id'] + "\" "
                            + checked + " style=\"width: 15px;height: 18px;\" type=\"radio\" name=\"checkOne\"/></td>";
                }
            }
            if (key != 'id') {
                if (!value) {
                    value = "";
                }
                var nowrap = array[i].nowrap;
                if (nowrap) {
                    tdObj += "<td style=\"overflow: hidden;text-overflow: ellipsis;white-space: nowrap;\" class=\"" + array[i].className + "\" title='" + value + "'>" + value + "</td>";
                } else {
                    tdObj += "<td style=\"text-overflow: clip;white-space: pre-wrap;\" class=\"" + array[i].className + "\">" + value + "</td>";
                }
            }
        }
        trStr += tdObj + "</tr>";
        trStrBuuiler += trStr;
    }
    if (settings.pageSize == 0) {
        throw "pageSize 必须大于0,初始化datagrid时必须设定pageSize的值";
    }
    layui.use(['laypage', 'layer'], function () {
        var laypage = layui.laypage;
        console.log(data)
        console.log(data.page)
        laypage.render({
            elem: 'paged',
            count: data['totals'],
            theme: '#1E9FFF',
            limit: settings.pageSize,
            limits: settings.limits,
            curr: data.page,
            layout: ['prev', 'page', 'next', 'limit', 'refresh', 'skip'],
            jump: function (obj, first) {
                if (!first) {
                    $("#" + tableId + "_currentPage").val(obj.curr);
                    setTimeout(function () {
                        settings.pageSize = obj.limit;
                        _query_data_ajxa(settings);
                        _select_checkbox_all();
                        var num = 0;
                        $('input[name="checkOne"]').each(function () {
                            var bo = $(this).is(":checked");
                            if (bo) num++;
                        });
                        if (num == settings.pageSize) {
                            $('#ckAll').prop("checked", true);
                        } else {
                            $('#ckAll').prop("checked", false);
                        }
                    }, 200)
                }
            }
        });
    });
    var _tb_id = tableId + "-data-body";
    $("#" + _tb_id).html(trStrBuuiler);
    if ($("#paged").attr('data-type') != 1) {
        $('table').after('<div id="paged" data-type="1" style="text-align:right" class="page"></div>');
    }
    jQuery("input[name='checkOne']").each(function () {
        $(this).click(function () {
            var id = $(this).attr('data-id');
            if ($(this).is(":checked") && settings.single) {
                _dataIds = [];
                _dataIds.push(id);
            } else {
                if ($(this).is(":checked")) {
                    if (!_is_exsit(_dataIds, id)) {
                        $(this).parent().parent().css(trBackgroundChecked);
                        _dataIds.push(id);
                    }
                } else {
                    _dataIds = removeObjToArray(_dataIds, id);
                    $(this).parent().parent().css(trBackgroundUnChecked);
                }
            }
        })
    });

    var _trClick = settings.trClick;
    if (_trClick) {
        $(settings.tableId).find('tr').each(function () {
            var dataId = $(this).attr('data-id');
            var data = $(settings.tableId).data(dataId);
            var _fn = eval(_trClick);
            $(this).on('dblclick', function () {
                _fn.call(this, data, $(settings.tableId))
            });
        });
    }
};

var removeObjToArray = function (array, obj) {
    var arrs = [];
    if (array.length > 0) {
        for (var i = 0; i < array.length; i++) {
            var id = array[i];
            if (id != obj) {
                arrs.push(id);
            }
        }
    }
    return arrs;
};

/**
 * 判断ID是否存在
 */
var _is_exsit = function (array, obj) {
    var isEqual = false;
    if (array.length > 0) {
        for (var i = 0; i < array.length; i++) {
            var id = array[i];
            if (id == obj) {
                isEqual = true;
                break;
            }
        }
    }
    return isEqual;
};

/**
 * checkbox全选/反选
 */
var _select_checkbox_all = function () {
    $("#ckAll").click(function () {
        if ($(this).is(":checked")) {
            var that = this;
            $("input[name='checkOne']").each(function () {
                var id = $(this).attr('data-id');
                tableData.data(id);
                var disabled = $(this).attr('disabled');
                if (disabled == undefined) {
                    $(this).prop("checked", that.checked);
                    $(this).parent().parent().css(trBackgroundChecked);
                    if ($(this).is(":checked")) {
                        if (!_is_exsit(_dataIds, id)) {
                            _dataIds.push(id);
                        }
                    } else {
                        _dataIds = removeObjToArray(_dataIds, id)
                    }
                }
            });
        } else {
            $("input[name='checkOne']").parent().parent().css(trBackgroundUnChecked);
            $("input[name='checkOne']").prop("checked", false);
            $("input[name='checkOne']").each(function () {
                var id = $(this).attr('data-id');
                _dataIds = removeObjToArray(_dataIds, id);
            });
        }
    });

    $("input[name='checkOne']").click(function () {
        var $subs = $("input[name='checkOne']");
        $("#ckAll").prop("checked", $subs.length == $subs.filter(":checked").length ? true : false);
    });
};

var iframeTimer = window.setInterval("iframeResize()", 200);
$('.resize-auto').each(function (item) {
    item.load(function () {
        iframeResizeEnd();
    });
});

//动态改变iframe的高度 (当iframe中内容高度改变时iframe的高度也一起改变)
//iframe iframe对象
function iframeResize() {
    $('.resize-auto').each(function (i, item) {
        var height = $(item).contents().find("body").height();
        $(item).height(height);
    });
}

//加载完毕后清楚定时器
function iframeResizeEnd() {
    try {
        window.clearInterval(iframeTimer);
        $('.resize-auto').each(function (i, item) {
            var height = $(item).contents().find("body").height();
            $(item).height(height);
        });
    } catch (ex) {
        window.clearInterval(iframeTimer);
    }
}

