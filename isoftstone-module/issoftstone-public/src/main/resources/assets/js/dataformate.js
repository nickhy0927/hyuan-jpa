/**
 * 获取当前时间 yyyy-MM-dd
 * @returns
 */
Date.prototype.currentDate = function () {
    var myDate = new Date();
    var year = myDate.getYear();
    var month = myDate.getMonth() + 1;
    if (month < 10) month = '0' + month;
    var date = myDate.getDate();
    if (date < 10)
        date = '0' + date;
    return (year + 1900) + "-" + month + "-" + date;
};

/**
 * 获取上个月的今天
 * @param date
 * @returns {string}
 */
Date.prototype.getPreMonth = function (date) {
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var day = arr[2]; //获取当前日期的日
    var days = new Date(year, month, 0);
    days = days.getDate(); //获取当前日期中月的天数
    var year2 = year;
    var month2 = parseInt(month) - 1;
    if (month2 == 0) {
        year2 = parseInt(year2) - 1;
        month2 = 12;
    }
    var day2 = day;
    var days2 = new Date(year2, month2, 0);
    days2 = days2.getDate();
    if (day2 > days2) day2 = days2;
    if (day2 < 10) day2 = "0" + day2;
    if (month2 < 10) month2 = '0' + month2;
    return year2 + '-' + month2 + '-' + day2;
};

/**
 * 获取下一个月的今天
 * @param date
 * @returns {string}
 */
Date.prototype.getNextMonth = function (date) {
    var arr = date.split('-');
    var year = arr[0]; //获取当前日期的年份
    var month = arr[1]; //获取当前日期的月份
    var day = arr[2]; //获取当前日期的日
    var year2 = year;
    var month2 = parseInt(month) + 1;
    if (month2 == 13) {
        year2 = parseInt(year2) + 1;
        month2 = 1;
    }
    var day2 = day;
    var days2 = new Date(year2, month2, 0);
    days2 = days2.getDate();
    if (day2 > days2) day2 = days2;
    if (day2 < 10) day2 = "0" + day2;
    if (month2 < 10) month2 = '0' + month2;
    var t2 = year2 + '-' + month2 + '-' + day2;
    return t2;
};

/**
 * 两个日期之间相个多少天
 * @param  {Date} startTime [开始日期]
 * @param  {Date} endTime   [结束日期]
 * @return {[type]}           [description]
 */
Date.prototype.getDateBetweenDays = function (startTime, endTime) {
    startTime = new Date(startTime.replace(/-/g, "/"));
    endTime = new Date(endTime.replace(/-/g, "/"));
    var time = endTime.getTime() - startTime.getTime();
    var days = parseInt(time / 86400000);
    return days;
};

$.extend({
    isNull: function (object) {
        if (object == null || typeof object == "undefined") {
            return true;
        }
        return false;
    },
    /**
     * 获取两个时间直接的所有时间
     * @param startTime
     * @param endTime
     * @returns {Array}
     */
    getDateArray: function (startTime, endTime) {
        var dayArray = [];
        var days = new Date().getDateBetweenDays(startTime, endTime)
        for (var addDayCount = 0; addDayCount <= days; addDayCount++) {
            var dd = new Date(startTime.replace(/-/g, "/"));
            dd.setDate(dd.getDate() + addDayCount);//获取addDayCount天后的日期
            var y = dd.getFullYear();
            var m = dd.getMonth() + 1; //获取当前月份的日期
            if (m < 10) m = "0" + m;
            var d = dd.getDate();
            if (d < 10) d = "0" + d;
            var date = y + "-" + m + "-" + d;
            dayArray.push(date);
        }
        return dayArray;
    },
    /**
     * 获取英文星期
     * @param  {[type]} dateString [description]
     * @return {string}            [description]
     */
    getEnWeekName: function (dateString) {
        var date;
        if (this.isNull(dateString)) {
            date = new Date();
        } else {
            var dateArray = dateString.split("-");
            date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
        }
        var index = date.getDay();
        var weeks = ['Sunday', 'Monday', 'Tuesday', 'Wednesday', 'Thursday', 'Friday', 'Saturday'];
        return weeks[index];
    },
    /**
     * 获取中文星期
     * @param  {[type]} dateString [description]
     * @return {string}            [description]
     */
    getZhWeekName: function (dateString) {
        var date;
        if (this.isNull(dateString)) {
            date = new Date();
        } else {
            var dateArray = dateString.split("-");
            date = new Date(dateArray[0], parseInt(dateArray[1] - 1), dateArray[2]);
        }
        var index = date.getDay();
        var weeks = ['星期日', '星期一', '星期二', '星期三', '星期四', '星期五', '星期六'];
        return weeks[index];
    },
    getRecursion: function (val, num) {
        var reg = /^[0-9]$/;
        if (!reg.test(num)) {
            throw '参数类型不正确，num必须是正整数'
        }
        for (var i = 1; i <= num; i++) {
            var dd = new Date().getNextMonth(val);
            return this.getRecursion(dd, num - i)
        }
        // 创建日期对象
        var date = new Date(val);
        // 减一天
        date.setDate(date.getDate() - 1);
        val = date.getFullYear() + '-' + (parseInt(date.getMonth()) + 1) + '-' + (date.getDate() < 10 ? '0' + date.getDate() : date.getDate());
        return val;
    }
});