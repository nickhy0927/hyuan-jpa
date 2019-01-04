function _init_table(settings) {
    var h = window.screen.availHeight;
    layui.use("table", function () {
        var table = layui.table;
        var layer = layui.layer;
        var config = $.extend({
            totalRow: false,
            height: "full", //高度最大化减去差值
            size: h < 1000 ? 'sm' : 'lg', //小尺寸的表格
            done: function (res, curr, count) {
                var toolH = $('.layui-table-tool').height();
                var pageH = $('.layui-table-page').height();
                $(".layui-table-box").height($(document).height() - 60 - toolH - pageH + 20).css({
                    "overflow-y":"auto"
                })
                // 如果是异步请求数据方式，res即为你接口返回的信息。
                // 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                console.log(res);
                // 得到当前页码
                console.log(curr);
                // 得到数据总量
                console.log(count);
            },
            text: {
                none: '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        }, settings || {});
        console.log('config', config);
        //第一个实例
        var tableInstance = table.render(config);
        //头工具栏事件
        table.on('toolbar(' + config.filter + ')', function (obj) {
            var checkStatus = table.checkStatus(obj.config.id);
            switch (obj.event) {
                case 'getCheckData':
                    var data = checkStatus.data;
                    layer.alert(JSON.stringify(data));
                    break;
                case 'getCheckLength':
                    var d = checkStatus.data;
                    layer.msg('选中了：' + d.length + ' 个');
                    break;
                case 'isAll':
                    layer.msg(checkStatus.isAll ? '全选' : '未全选');
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(' + config.filter + ')', function (obj) {
            var data = obj.data;
            console.log('obj', obj);
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('确认删除吗？', function (index) {
                    // config.devare();
                    obj.del()
                    //这里以搜索为例
                    tableInstance.reload({
                        where: { //设定异步数据接口的额外参数，任意设
                            aaaaaa: 'xxx',
                            bbb: 'yyy'
                        }
                    });
                    layer.close(index);
                });
            } else if (obj.event === 'edit') {
                layer.prompt({
                    formType: 2,
                    value: data.email
                }, function (value, index) {
                    obj.update({
                        email: value
                    });
                    layer.close(index);
                });
            }
        });
        // console.log('page', config)
    });
}

var page = {
    dataTable: function (option) {
        var settings = $.extend({
            elem: '',
            title: "数据列表",
            totalRow: false,
            toolbar: '',
            method: 'get',
            filter: 'demo',
            url: '',
            cols: [],
            page: true, //开启分页
            parseData: function (res) {
                console.log("res", res);
                //将原始数据解析成 table 组件所规定的数据
                return {
                    code: res.status, //解析接口状态
                    msg: res.message, //解析提示文本
                    count: res.total, //解析数据长度
                    data: res.rows.item //解析数据列表
                };
            },
            devare: function () {
            },
            edit: function () {
            }
        }, option || {});
        console.log('page', settings)
        _init_table(settings);
    }
};