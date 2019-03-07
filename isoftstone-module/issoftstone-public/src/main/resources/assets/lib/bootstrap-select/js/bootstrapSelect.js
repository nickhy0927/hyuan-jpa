(function ($) {
    //1.定义jquery的扩展方法bootstrapSelect
    $.fn.bootstrapSelect = function (options, param) {
        if (typeof options == 'string') {
            return $.fn.bootstrapSelect.methods[options](this, param);
        }
        //2.将调用时候传过来的参数和default参数合并
        options = $.extend({}, $.fn.bootstrapSelect.defaults, options || {});
        //3.添加默认值

        var target = $(this);
        if (!target.hasClass("selectpicker")) target.addClass("selectpicker");
        target.attr('valuefield', options.valueField);
        target.attr('textfield', options.textField);
        target.empty();
        var option = $('<option></option>');
        option.attr('value', '');
        option.text(options.placeholder);
        target.append(option);
        //4.判断用户传过来的参数列表里面是否包含数据data数据集，如果包含，不用发ajax从后台取，否则否送ajax从后台取数据
        if (options.defData) {
            init(target, options.defData);
        }
        else {
            options.onBeforeLoad.call(target, options.param);
            if (!options.url) return;

            var setting = $.extend({
                type: 'post',
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                data: JSON.stringify(options.param)

            }, options.innerOptions);

            $.ajax({
                url: options.url,
                type: setting.type,
                dataType: setting.dataType,
                contentType: setting.contentType,
                data: setting.data,
                async: false,
                success: function (data) {
                    init(target, data.data);
                },
                error: function (msg) {
                    console.log(msg);
                }
            });
        }

        function init(target, data) {
            console.log('init', target, data);
            $.each(data, function (i, item) {
                var option = $('<option></option>');
                option.attr('value', item[options.valueField]);
                option.text(item[options.textField]);
                target.append(option);
            });

            target.empty();
            if(options.placeholder){
                target.append("<option value=''>"+options.placeholder+"</option>");
            }
            if(options.disable){
                target.attr("disabled","disabled");
            }else {
                target.removeAttr("disabled");
            }
            for (var i = 0; i < data.length; i++) {
                var isDis = false;
                var isSel = false;
                if (options.disableValue.length > 0) {
                    var disableValues = options.disableValue;
                    for (var j = 0; j < disableValues.length; j++) {
                        if (data[i].value == disableValues[j]) {
                            isDis = true;
                            break;
                        }
                    }
                }

                if (options.selectValue.length > 0) {
                    var selectValues = options.selectValue;
                    for (var j = 0; j < selectValues.length; j++) {
                        if (data[i].value == selectValues[j]) {
                            isSel = true;
                            break;
                        }
                    }
                }

                if (isDis && isSel){
                    target.append("<option disabled='disabled' value='" + data[i].value + "'>" + data[i].text + "</option>");
                }else{
                    if(isDis){
                        target.append("<option disabled='disabled' value='" + data[i].value + "'>" + data[i].text + "</option>");
                    }else if(isSel){
                        target.append("<option value='" + data[i].value + "' selected='selected'>" + data[i].text + "</option>");
                    }else {
                        target.append("<option value='" + data[i].value + "'>" + data[i].text + "</option>");
                    }
                }

            }
            target.selectpicker('refresh');

            options.onLoadSuccess.call(target);
        }

        target.unbind("change");
        target.on("change", function (e) {
            if (options.onChange) {
                target.selectpicker('refresh');
                return options.onChange(target.val(),target);
            }
        });
    };

    //5.如果传过来的是字符串，代表调用方法。
    $.fn.bootstrapSelect.methods = {
        getValue: function (jq) {   //取值方法  例：$("#select").bootstrapSelect("getValue")
            return jq.val();
        },
        textValue: function (jq) {
            return jq.parent().find("button").attr("title");
        },
        //赋值方式  例：$("#select").bootstrapSelect("setValue",["001","002","003"])
        //单个赋值方式  例：$("#select").bootstrapSelect("setValue","001")  或 $("#select").bootstrapSelect("setValue",["001"])
        setValue: function (jq, param) {
            jq.selectpicker('val', param);
            jq.val(param);
        },
        load: function (jq, url) {    //重新加载  例：$("#select").bootstrapSelect("load","data.json")
            $.ajax({
                url: url,
                type: 'post',
                dataType: 'json',
                contentType: 'application/json;charset=UTF-8',
                data: {},
                async: false,
                success: function (data) {
                    jq.empty();
                    var option = $('<option></option>');
                    option.attr('value', '');
                    option.text('请选择');
                    jq.append(option);
                    $.each(data.data, function (i, item) {
                        var option = $('<option></option>');
                        option.attr('value', item[jq.attr('valuefield')]);
                        option.text(item[jq.attr('textfield')]);
                        jq.append(option);
                    });
                    jq.selectpicker('refresh');
                },
                error: function (msg) {
                    console.log(msg);
                }
            });
        }
    };

    //6.默认参数列表
    $.fn.bootstrapSelect.defaults = {
        url: null, //ajax请求路径
        param: null, //传参
        defData: null, //固定选项，例：[{"value":"1","text":"中国"},{"value":"2","text":"韩国"}]
        innerOptions: "", // ajax请求方式  例：innerOptions: {type:"get",contentType: "",dataType: ""}
        valueField: 'value', //标识选项值的字段名
        textField: 'text', //标识选项文本的字段名
        placeholder: '请选择', //未选择时默认显示
        disable: false, // 下拉框是否禁用
        selectValue: [], // 默认选中值(单个或多个) ["1","2"]
        disableValue: [], // 禁用值(单个或多个) ["3","4"]
        onBeforeLoad: function (param) {
        }, //加载前调用
        onLoadSuccess: function () {
        }, //加载成功调用
        onChange: function (value,target) {
        } //选项改变时执行
    };




    //1.定义jquery的扩展方法combobox
    $.fn.combobox = function (options, param) {
        if (typeof options == 'string') {
            return $.fn.combobox.methods[options](this, param);
        }
        //2.将调用时候传过来的参数和default参数合并
        options = $.extend({}, $.fn.combobox.defaults, options || {});
        //3.添加默认值
        var target = $(this);
        target.attr('valuefield', options.valueField);
        target.attr('textfield', options.textField);
        target.empty();
        var option = $('<option></option>');
        option.attr('value', '');
        option.text(options.placeholder);
        target.append(option);
        //4.判断用户传过来的参数列表里面是否包含数据data数据集，如果包含，不用发ajax从后台取，否则否送ajax从后台取数据
        if (options.data) {
            init(target, options.data);
        }
        else {
            //var param = {};
            options.onBeforeLoad.call(target, options.param);
            if (!options.url) return;
            $.getJSON(options.url, options.param, function (data) {
                init(target, data);
            });
        }
        function init(target, data) {
            $.each(data, function (i, item) {
                var option = $('<option></option>');
                option.attr('value', item[options.valueField]);
                option.text(item[options.textField]);
                target.append(option);
            });
            options.onLoadSuccess.call(target);
        }
        target.selectpicker('render');
        target.unbind("change");
        target.on("change", function (e) {
            if (options.onChange)
                return options.onChange(target.val());
        });
    };

    //5.如果传过来的是字符串，代表调用方法。
    $.fn.combobox.methods = {
        getValue: function (jq) {
            return jq.val();
        },
        setValue: function (jq, param) {
            jq.val(param);
        },
        load: function (jq, url) {
            $.getJSON(url, function (data) {
                jq.empty();
                var option = $('<option></option>');
                option.attr('value', '');
                option.text('请选择');
                jq.append(option);
                $.each(data, function (i, item) {
                    var option = $('<option></option>');
                    option.attr('value', item[jq.attr('valuefield')]);
                    option.text(item[jq.attr('textfield')]);
                    jq.append(option);
                });
            });
        }
    };

    //6.默认参数列表
    $.fn.combobox.defaults = {
        url: null,
        param: null,
        data: null,
        valueField: 'value',
        textField: 'text',
        placeholder: '请选择',
        onBeforeLoad: function (param) { },
        onLoadSuccess: function () { },
        onChange: function (value) { }
    };
})(jQuery);