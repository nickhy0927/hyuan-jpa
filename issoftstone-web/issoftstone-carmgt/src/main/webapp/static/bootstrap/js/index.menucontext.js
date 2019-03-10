(function ($, window, document) {
    $.fn.initPage = function () {
        $('.main-menu>ul').find('li>ul').hide();
        $('.main-menu>ul').find('li>ul').eq(0).show();
        $(this).css({
            'width': document.body.clientWidth - $('.main-menu').width(),
            'height': document.body.clientHeight - 50,
        });
        $('.c-f-menu').click(function () {
            $(this).parent().parent().find('li>ul').hide();
            $(this).parent().find('ul').toggle(200);
        });

        // 处理顶部菜单
        $('.container-top>.menu>ul').find('li>a').click(function () {
            $('.container-top>.menu>ul').find('li').removeClass('current');
            $(this).parent().addClass('current');
            $('.container-top>.menu>ul').find('li').each(function (index) {
                if ($(this).hasClass('current')) {
                    $('.main-menu>ul').hide();
                    $('.main-menu>ul').eq(index).show();
                    $('.main-menu>ul').eq(index).find('li').find('ul').hide();
                    $('.main-menu>ul').eq(index).find('li').eq(0).find('ul').slideDown(200);
                }
            });
        });
        $('.container-top>.menu>ul').find('li').each(function (index) {
            if ($(this).hasClass('current')) {
                $('.main-menu>ul').hide();
                $('.main-menu>ul').eq(index).show();
            }
        })
    }
})(jQuery, window, document);