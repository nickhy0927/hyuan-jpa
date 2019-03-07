/**
 * Author wrwua
 * Date 2017/12/04.
 */
$(function () {
    function SuiJiNum() {
        return (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
    }

    /**
     * 创建默认参数
     */
    function imgUpload(itemcont, options) {
        var defaults = {
            auto: true,
            btntxt: "选择文件",
            onSuccess: function (file, response) {
            }, //上传成功,参数文件属性，响应结果
            onComplete: function (rs) {
            }, // 每上传一个file的回调函数
            onRemove: function (fileId) {
            }, //删除,参数文件ID
            onError: function (file) {
            }, //上传失败 ,参数文件属性，响应结果
            itemClick: null, // 点击文件名触发的事件
            innerOptions: {},
            fileSuffix: undefined,
            fileNumLimit: undefined, //验证文件总数量, 超出则不允许加入队列
            fileSizeLimit: undefined, //验证文件总大小是否超出限制, 超出则不允许加入队列
            fileSingleSizeLimit: undefined, //验证单个文件大小是否超出限制, 超出则不允许加入队列
        };
        var opts = $.extend(defaults, options);
        var target = $(itemcont); //容器
        var pickerid = "";
        var btntxt = $("#" + pickerid).text();
        //给一个唯一ID
        if (typeof guidGenerator36 != 'undefined') {
            pickerid = guidGenerator36();
        } else {
            pickerid = (((1 + Math.random()) * 0x10000) | 0).toString(16).substring(1);
        }
        var uploaderStrdiv; //= '<div class="webuploader">';

        //即时上传样式
        if (opts.auto) {
            uploaderStrdiv = '<div id="Uploadthelist" class="uploader-list"></div>' +
                '<div class="btns">' +
                '<div id="' + pickerid + '">' + opts.btntxt + '</div>' +
                '</div>';
        } else {
            uploaderStrdiv = '<div  class="uploader-list"></div>' +
                '<div class="btns">' +
                '<div id="' + pickerid + '">' + opts.btntxt + '</div>' +
                '<button class="webuploadbtn">开始上传</button>' +
                '</div>';
        }
        uploaderStrdiv += '<div style="display:none" class="UploadhiddenInput" ></div>';
        target.append(uploaderStrdiv);

        var $list = target.find('.uploader-list'),
            $btn = target.find('.webuploadbtn'), //手动上传按钮备用
            state = 'pending',
            $hiddenInput = target.find('.UploadhiddenInput'),
            uploader;
        var jsonData = {
            fileList: []
        };

        var webuploaderoptions = $.extend({
            swf: opts.swf, //flash控件路径
            server: opts.server, // 文件接收服务端
            deleteServer: opts.deleteServer, // 选择文件的按钮。可选。
            // 内部根据当前运行是创建，可能是input元素也可能是flash.
            pick: '#' + pickerid,
            resize: false, //不压缩image, 默认如果是jpeg，文件上传前会压缩一把再上传！
            accept: opts.accept, // 上传文件类型（"img"\"file"）
            fileNumLimit: opts.fileNumLimit,
            fileSizeLimit: opts.fileSizeLimit,
            fileSingleSizeLimit: opts.fileSingleSizeLimit,
            compress: {
                width: 1600,
                height: 1600,
                quality: 90, // 图片质量，只有type为`image/jpeg`的时候才有效
                allowMagnify: false, // 是否允许放大，如要生成小图时不失真，此选项应该设置为false
                crop: false, // 是否允许裁剪。
                preserveHeaders: true, // 是否保留头部meta信息。
                // 如果发现压缩后文件大小比原来还大，则使用原来图片.此属性可能会影响图片自动纠正功能
                noCompressIfLarger: false,
                // 单位字节，如果图片大小小于此值，不会采用压缩。
                compressSize: 200000
            }
        }, opts.innerOptions);

        if (opts.accept === 'img') {
            target.find('.uploader-list').addClass("imglistH");
            webuploaderoptions['accept'] = {
                title: 'Images',
                extensions: opts.fileSuffix ? opts.fileSuffix : 'gif,jpg,jpeg,bmp,png',
                mimeTypes: 'image/gif,image/jpg,image/jpeg,image/bmp,image/png'
            };
        } else if (opts.accept === 'file') {
            target.find('.uploader-list').addClass("filelistH");
            webuploaderoptions['accept'] = {
                title: 'intoTypes',
                extensions: opts.fileSuffix ? opts.fileSuffix
                    : 'doc,docx,ppt,pptx,pdf,xlsx,xls,txt,gif,jpg,jpeg,bmp,png'
            };
        }

        var uploader = WebUploader.create(webuploaderoptions);
        uploader.on('beforeFileQueued', function (file) {
            if (webuploaderoptions['accept'].extensions.toLowerCase().indexOf(file.ext) < 0) {
                layer.alert('文件格式不正确，只支持' + webuploaderoptions['accept'].extensions);//opts.fileSuffix
                return false;
            }
            var length = target.find('.file-item').length;
            var plen = $list.prev().find("img").length;
            length = plen + length;
            if (length != undefined && length >= opts.fileNumLimit) {
                layer.alert('所选文件的数量不能超过' + opts.fileNumLimit);
                return false;
            }
        });

        uploader.on('fileQueued', function (file) {
            //限制上传文件总大小
            var size = file.size / 1024;
            var picSize = (5 * 1024 * 1024) / 1024;
            if (size > picSize) {
                var filesize = "";
                if (picSize / 1024 >= 1) {
                    filesize = (picSize / 1024) + "MB";
                } else if (picSize / 1024 / 1024 >= 1) {
                    filesize = (picSize / 1024) + "GB";
                } else {
                    filesize = (picSize) + "KB";
                }
                layer.alert('所选文件的大小不能超过' + filesize);
                uploader.removeFile(file, true);
                return;
            }

            //限制上传文件length
            /*if (opts.fileNumLimit >= 0) {
                var t = $("#" + opts.fileList + " img").length;
                t = t == 0 ? $("#" + opts.fileList + " div.itemcell").length : $("#" + opts.fileList + " img").length;
                var length = target.find('.file-item').length;
                if (length >= opts.fileNumLimit) {
                    uploader.removeFile(file, true);
                    layer.alert('所选文件的数量不能超过' + opts.fileNumLimit);
                    return;
                }
            }*/
            if (opts.accept === 'file') {
                var fileSuffix = webuploaderoptions['accept'].extensions;//opts.fileSuffix
                if (!fileSuffix && opts.accept === 'file') {
                    throw "文件后缀fileSuffix不能为空，比如：ppt,doc,docx,jpg,gif等";
                }
                var fileSuffixs = fileSuffix.split(",");
                var bool = false;
                for (var i = 0; i < fileSuffixs.length; i++) {
                    var sf = fileSuffixs[i];
                    if (sf === file.ext) {
                        bool = true;
                        break;
                    }
                }
                if (!bool) {
                    layer.alert('文件格式不正确，只支持' + opts.fileSuffix);
                    return;
                }
                target.find('.uploader-list').addClass("filelistH");
                var $imgList = $('<div id="' + $(itemcont)[0].id + file.id + '" class="itemcell file-item">'),
                    $del = $('<div class="webuploadDelbtn"></div>'),
                    $fileName = $('<span class="fileloadinfo" title="' + file.name + '">' + file.name + '</span>');
                $list.append($imgList);
                $imgList.append($del).append($fileName);
            } else if (opts.accept === 'img') {
                target.find('.uploader-list').addClass("imglistH");
                var $imgList = $('<div id="' + $(itemcont)[0].id + file.id + '" class="itemcell img-item">'),
                    $del = $('<div class="webuploadDelbtn"></div>'),
                    $state = $('<span class="imgloadstate">等待上传......</span>'),
                    $fileName = $('<span class="imgloadinfo">' + file.name + '</span>'),
                    $img = $('<img alt="' + file.name + '" title="' + file.name + '" />');

                $list.append($imgList);
                $imgList.append($del).append($state).append($fileName).append($img);
                // 优化retina, 在retina下这个值是2
                var ratio = window.devicePixelRatio || 1,
                    // 缩略图大小
                    thumbnailWidth = 110 * ratio,
                    thumbnailHeight = 110 * ratio;
                // 创建缩略图
                uploader.makeThumb(file, function (error, src) {
                    if (error) {
                        $img.attr('src', "webresource/images/file.png");
                    }
                    $img.attr('src', src); //jquery设置属性的值
                }, thumbnailWidth, thumbnailHeight);
            }
        });
        if (opts.auto) {
            uploader.on('fileQueued', function (file) {
                uploader.upload();
            });
            uploader.on('startUpload', function () {
                $.openLoading();
            });
        } else {
            uploader.on('fileQueued', function (file) {
            });
        }

        //进度条事件
        uploader.on('uploadProgress', function (file, percentage) {
            if (opts.accept === 'file') {
                var $li = target.find('#' + $(itemcont)[0].id + file.id),
                    $percent = $li.find('.hr');
                // 避免重复创建
                if (!$percent.length) {
                    $percent = $('<p class="fileprogress"><span class="hr"></span></p>')
                        .appendTo($li).find('.hr');
                }
                $percent.css('width', percentage * 100 + '%');

            } else if (opts.accept === 'img') {
                var $li = target.find('#' + $(itemcont)[0].id + file.id),
                    $percent = $li.find('.imgprogress .bar');
                // 避免重复创建
                if (!$percent.length) {
                    $percent = $('<span class="imgprogress">' +
                        '<span  class="percentage"><span class="text"></span>' +
                        '<span class="bar" role="progressbar" style="width: 0%">' +
                        '</span></span>' +
                        '</span>').appendTo($li).find('.bar');
                }
                $li.find('span.imgloadstate').html('上传中');
                $li.find(".text").text(Math.round(percentage * 100) + '%');
                $percent.css('width', percentage * 100 + '%');
            }
        });

        //上传成功事件
        uploader.on('uploadSuccess', function (file, response) {
            $.closeLoading();
            if (response.responseCode && response.responseCode != 200) {
                if (opts.accept === 'file') {
                    target.find('#' + $(itemcont)[0].id + file.id).remove();
                } else if (opts.accept === 'img') {
                    target.find('#' + $(itemcont)[0].id + file.id).find('span.imgloadstate').html('上传失败');
                }
                layer.alert(response.responseMessage);
                uploader.removeFile(file);
            }
            if (response.state == "error") {
                target.find('#' + $(itemcont)[0].id + file.id).find('span.imgloadstate').html(response.message);
            } else {
                opts.onSuccess(file, response);
                target.find('#' + $(itemcont)[0].id + file.id).find('span.imgloadstate').html('');
                var innerStr = response.object;
                if (innerStr)
                    for (var i = 0; i < innerStr.length; i++) {
                        var obj = innerStr[i];
                        file.id = obj.id;
                        var objstr = JSON.stringify(obj);
                        if ($('#itemHdInput' + $(itemcont)[0].id + file.id + '').val() == "") {
                            $('#itemHdInput' + $(itemcont)[0].id + file.id + '').val(objstr);
                        } else {
                            $('#itemHdInput' + $(itemcont)[0].id + file.id + '').val($('#itemHdInput'
                                + $(itemcont)[0].id + file.id + '').val() + "," + objstr);
                        }
                        $hiddenInput.append('<input type="text" id="itemHdInput' + $(itemcont)[0].id
                            + file.id + '" class="itemHdInput" value=\'' + objstr + '\' />');
                    }
            }
        });

        //上传失败事件
        uploader.on('uploadError', function (file) {
            $.closeLoading();
            opts.onError(file);
            if (opts.accept === 'file') {
                target.find('#' + $(itemcont)[0].id + file.id).find('.fileprogress span').removeClass(".hr");
                target.find('#' + $(itemcont)[0].id + file.id).find('.fileprogress span').addClass(".hr2");
                target.find('#' + $(itemcont)[0].id + file.id).find('span').html('上传失败');
            } else if (opts.accept === 'img') {
                target.find('#' + $(itemcont)[0].id + file.id).find('span.imgloadstate').html('上传失败');
            }
        });

        //全部完成事件，删除进度条
        uploader.on('uploadComplete', function (file) {
            target.find('#' + $(itemcont)[0].id + file.id).find('.imgprogress').fadeOut();
            opts.onComplete(file);
        });

        uploader.on('all', function (type) {
            if (type === 'startUpload') {
                state = 'uploading';
            } else if (type === 'stopUpload') {
                state = 'paused';
            } else if (type === 'uploadFinished') {
                state = 'done';
            }

            if (state === 'uploading') {
                $btn.text('暂停上传');
            } else {
                $btn.text('开始上传');
            }
        });

        //删除时执行的方法
        uploader.on('fileDequeued', function (file) {
            $("#" + $(itemcont)[0].id + file.id).remove();
            $("#itemHdInput" + $(itemcont)[0].id + file.id).remove();
            opts.onRemove(file);
        });

        //多文件点击上传的方法
        $btn.on('click', function () {
            if (state === 'uploading') {
                uploader.stop();
            } else {
                uploader.upload();
            }
        });

        //删除
        $list.on("click", ".webuploadDelbtn", function () {
            var liId = $(this).parent().attr("id");
            if (!liId) {
                liId = "";
            }
            var id = liId.replace($(itemcont)[0].id, "");
            var file = uploader.getFile(id);
            //有file对象（新增）时
            if (file) {
                uploader.removeFile(file);
                $("#" + liId).remove();
            }
            //无file对象（编辑）时
            else
                opts.onRemove(file);
        });
        // 删除按钮显示影藏
        $list.on("mouseover", ".file-item", function () {
            $(this).find('.webuploadDelbtn').addClass('show');
        });

        $list.on("mouseleave", ".file-item", function () {
            $(this).find(".webuploadDelbtn").removeClass('show');
        });

        $list.on("click", ".fileloadinfo", function () {
            var liId = $(this).parent().attr("id");
            if (!liId) {
                liId = "";
            }
            var id = liId.replace($(itemcont)[0].id, "");
            var file = uploader.getFile(id);
            //有file对象(新增时)
            if (file && opts.accept == 'file') {
                if (typeof opts.itemClick == 'function') {
                    opts.itemClick(file.id);
                } else {
                    var url = ctx + '/pdf/generic/web/viewer.html?file=' + ctx + '/online/file/onlineView/' + file.id + '.json';
                    // 文件预览
                    window.open(url);
                }

            }
            //无file对象（编辑）时
            else
                opts.onRemove(file);
        });

    }

    $.fn.hiddenInputData = function (options) {
        var filesInfo = $(this).find(".UploadhiddenInput");
        var filesDataList = [];
        filesInfo.find(".itemHdInput").each(function () {
            filesDataList.push($(this).val());
        });
        return filesDataList;
    };

    $.fn.webUpload = function (options) {
        console.log(options);
        imgUpload(this, options);
    };
    // 页面回显时  鼠标浮上显示删除按钮
    $('.filelistH .file-item').mouseover(function () {
        $(this).find('.webuploadDelbtn').addClass('show');
    }).mouseleave(function () {
        $(this).find(".webuploadDelbtn").removeClass('show');
    });

});