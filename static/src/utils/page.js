function _init_table(settings) {
    let h = window.screen.availHeight;
    layui.use("table", function () {
        var table = layui.table;
        var layer = layui.layer;
        let config = $.extend({
            totalRow: false,
            height: "full", //高度最大化减去差值
            size: h < 1000 ? 'sm' : 'lg', //小尺寸的表格
            done: function (res, curr, count) {
                // 如果是异步请求数据方式，res即为你接口返回的信息。
                // 如果是直接赋值的方式，res即为：{data: [], count: 99} data为当前页数据、count为数据总长度
                // console.log(res);
                // 得到当前页码
                // console.log(curr);
                // 得到数据总量
                // console.log(count);
            },
            text: {
                none: '暂无相关数据' // 默认：无数据。注：该属性为 layui 2.2.5 开始新增
            }
        }, settings || {});
        // console.log('config', config)
        //第一个实例
        let tableInstance = table.render(config);
        //头工具栏事件
        table.on('toolbar(' + config.filter + ')', function (obj) {
            var func = config.groupBtn[obj.event];
            switch (obj.event) {
                case 'add':
                    if (func) eval(func).call(this);
                    break;
                case 'del':
                    if (func) eval(func).call(this);
                    break;
                case 'search':
                    var search = $("#" + config.searchForm).serializeArray();
                    var searchObj = $("#" + config.searchForm).serialize();
                    // console.log('search', search);
                    layer.msg('你点击了搜索按钮');
                    // console.log(config.method.toUpperCase() == 'POST' ? search : searchObj)
                    tableInstance.reload({
                        where: config.method.toUpperCase() == 'POST' ? search : searchObj
                    });
                    break;
            }
        });

        //监听行工具事件
        table.on('tool(' + config.filter + ')', function (obj) {
            var data = obj.data;
            // console.log('obj', obj)
            //console.log(obj)
            if (obj.event === 'del') {
                layer.confirm('确认删除吗？', function (index) {
                    // config.delete();
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

let page = {
    openWindow: function (options) {
        var settings = $.extend({
            type: 2,
            title: options.title || '提示信息',
            area: [options.width || '700px', options.height || '450px'],
            fixed: false, //不固定
            move: false,
            shadeClose: false,
            shade: 0.6,
            maxmin: false,
            content: options.url,
            end: function (index) {
                options.callback ? options.callback(index) : null
            }
        })
        layui.use("layer", function () {
            var layer = layui.layer;
            layer.open(settings);
        });
    },
    dataTable: (option) => {
        let settings = $.extend({
            elem: '',
            title: "数据列表",
            totalRow: false,
            toolbar: '',
            method: 'get',
            searchForm: '',
            filter: 'demo',
            groupBtn: {
                add: function () {
                },
                del: function () {
                },
                search: function () {
                }
            },
            operates: {},
            url: '',
            cols: [],
            page: true, //开启分页
            parseData: function (res) {
                //将原始数据解析成 table 组件所规定的数据
                return {
                    code: res.status, //解析接口状态
                    msg: res.message, //解析提示文本
                    count: res.total, //解析数据长度
                    data: res.rows.item //解析数据列表
                };
            },
            delete: function () {
            },
            edit: function () {
            }
        }, option || {});
        _init_table(settings);
    },
    operate: function (params) {
        console.log('params', params)
    }
}
export default page