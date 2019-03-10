(function($, window, document) {
	$.fn.dataTable.defaults.language = {
	    "sProcessing": "处理中...",
	    "sLengthMenu": "显示 _MENU_ 项结果",
	    "sZeroRecords": "没有匹配结果",
	    "sInfo": "显示第 _START_ 至 _END_ 项结果，共 _TOTAL_ 项",
	    "sInfoEmpty": "显示第 0 至 0 项结果，共 0 项",
	    "sInfoFiltered": "(由 _MAX_ 项结果过滤)",
	    "sInfoPostFix": "",
	    "sSearch": "搜索：",
	    "sUrl": "",
	    "sEmptyTable": "暂时没有任何数据",
	    "sLoadingRecords": "载入中...",
	    "sInfoThousands": ",",
	    "oPaginate": {
	        "sFirst": "首页",
	        "sPrevious": "上一页",
	        "sNext": "下一页",
	        "sLast": "末页"
	    },
	    "oAria": {
	        "sSortAscending": ": 以升序排列此列",
	        "sSortDescending": ": 以降序排列此列"
	    }
	};
	$.fn.initTable = function(options) {
		var settings = $.extend({
			"iDisplayLength": 25, //jquery datatable默认每页显示多少条数据 
			"info": true,
			"bPaginate": true,
			"sort": true, //排序，asc正序 desc倒序 
			"sorting": [
	            [1, "asc" ]// desc , asc 指定单列排序： 
	        ],
	     	// 指定某列不可排序：[0]
	        "columnDefs": [{
	        	"bSortable": false, 
	        	"aTargets": []  
	        }],
	     	// 记录状态功能：开关，是否打开客户端状态记录功能。这个数据是记录在cookies中的，
	     	//打开了这个记录后，即使刷新一次页面，或重新打开浏览器，之前的状态都是保存下来的
	        stateSave: false, 
	        // 一般页数是可呈现7页，还可以用可扩展的分页机制,pagingType选项 
	        // numbers - 只显示数字页码 
	        // simple - 只显示’Previous’ and ‘Next’ buttons 
			// simple_numbers - ‘Previous’ and ‘Next’ buttons以及 page numbers 
			// full - ‘First’, ‘Previous’以及’Next’ and ‘Last’ buttons 
			// full_numbers - ‘First’, ‘Previous’, ‘Next’ and ‘Last’ buttons, plus page numbers
	        pagingType: 'full_numbers',
	        "scrollY":        "400px",
	        "scrollCollapse": true,
	        "paging":         true,
	        "bFilter": false,
	        "processing": true,
	        "lengthMenu": [ 10, 15 ,25, 50, 75, 100, 200 ]
			//"sPaginationType": 'two_button', //full_numbers
		}, options || {});
		$(this).DataTable(settings);
	}
})(jQuery, window, document)