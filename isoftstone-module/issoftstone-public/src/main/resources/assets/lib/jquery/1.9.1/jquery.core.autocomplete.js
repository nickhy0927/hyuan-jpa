(function($) {
	/**
	 * 自动搜索
	 */
	$.fn.issAutocomplete = function(options) {
		var settings = $.extend({
			url : '',
			method : 'POST',
			sqlId : '',
			params: {},
			order : 'id',
			reorder : 'asc',
			onLoadSuccess: undefined,
			onKeyUp: undefined,
			onSelected: undefined,
			dataType : 'JSON'
		}, options || {});
		$(this).keyup(function(event) {
			var params = options.params;
			for(var key in params){
				var key_value = key.split("_");
				if(key_value.length > 1) {
					params['' + key_value[0] + ''] = $('#' + key_value[1]).val();
				}
			}
			settings.searchParams = params;
			var val = $(this).val();
			if(val) 
				$('.autocomplete_pt').css({'display':"block"});
			else $('.autocomplete_pt').css({'display':"none"});
			_autocomplete(settings, this);
		});
		$(this).after('<div class="autocomplete-suggestions autocomplete_pt" style="display:none;max-height:300px;z-index:200;position:absolute;"></div>');
		$('.autocomplete_pt').css({'width': $(this).width() + 10});
	}
})(jQuery)

/**
 * 自动完成搜索
 */
var _autocomplete = function(settings, obj) {
	var items = [];
	var searchParams = settings.searchParams;
	var params = settings.searchParams;
	searchParams.sqlId = settings.sqlId;
	var func = settings.onLoadSuccess;
	if(func) {
		var fn = eval(func);
		fn.call(this, obj);
	}
	if (settings.order)
		searchParams['order'] = settings.order;
	else
		searchParams['order'] = 'id';
	if (settings.reorder)
		searchParams['reorder'] = settings.reorder;
	else
		searchParams['reorder'] = 'asc';
	$.ajax({
		method : settings.method,
		url : settings.url,
		async : false,
		data : searchParams,
		dataType : settings.dataType,
		success : function(data) {
			items = $.map(data.object, function(value, key) {
				return {
					value : value.name,
					data : value.code
				};
			});
			_selectItem(items, obj);
		},
		error : function() {
			console.log('获取数据失败');
		}
	});
}

var _selectItem = function(items, obj) {
	var html = "";
	for(var i = 0; i < items.length; i ++) {
		if(i == 0) {
			html += '<div class="autocomplete-suggestion autocomplete-selected" data-index="' + i +'"><strong>' + items[i].value + '</strong></div>'
		} else 
			html += '<div class="autocomplete-suggestion" data-index="' + i +'"><strong>' + items[i].value + '</strong></div>'
	}
	var inputVal = $(obj).val();
	$('.autocomplete_pt').html(html);
	$('.autocomplete-suggestion').hover(function() {
		$('.autocomplete-suggestion').removeClass('autocomplete-selected')
		$(this).addClass('autocomplete-selected');
		$(obj).val($(this).text());
	},function() {
		$('.autocomplete-suggestion').removeClass('autocomplete-selected')
		var index = $(this).attr('data-index');
		$(this).addClass('autocomplete-selected');
		$(obj).val(inputVal);
	});
	$('.autocomplete-suggestion').click(function() {
		$('.autocomplete-suggestion').removeClass('autocomplete-selected');
		inputVal = $(this).text();
		$('.autocomplete_pt').css({'display': 'none'});
	});
}