var tableManager = undefined;
(function($) {
	$.fn.dataTable = function(option) {   
		tableManager = $(this);
		var setting = $.extend({
			gridManagerName: '',
			disableCache: true,
			height: 'auto',
			supportAjaxPage: true,
			ajax_data: '${ctx}/platform/access/role/list.json',
			ajax_type: 'POST',
			firstLoading: true,
			// 用于配置是否支持宽度调整功能。defualt true 
			supportAdjust: false,
			// 用于配置是否支持拖拽功能。 如需禁用此功能，需要在初始化GridManager时配置该项
			supportDrag: false,
			supportMenu: false,
			// 请求参数中当前页key键值,默认为cPage
			currentPageKey: 'page',
			// 请求参数中每页显示条数key健值, 默认为pSize
			pageSizeKey: 'limit',
			mergeSort: true,
			useNoTotalsMode: true,
			useRadio: false, // 单选
			useRowCheck: false,
			/* ajax_headers: {
				'Content-Type': 'application/json'
			}, */
			query: {
				pluginId: 1
			},
			pageSize:10,
			ajax_success:function(res) {
				// console.log("ajax_success: ", res);
			},
			columnData: [{
				// 列的唯一索引。字符串类型，必设项    
			    key: '',
			    // 列的显示文本。字符串类型，必设项
			    text: '', 
			    remind: '',
				sorting: 'ASC',
				// 该列是否禁止使用个性配置功能(宽度调整、位置更换、列的显示隐藏)
			    disableCustomize: false,
			    // 是否显示, 默认值 true
			    isShow: true,
			    // 列所占宽度, 字符串类型，非必设项
			    // 需要注意的是: 
			    // 1.如果当前列的th内文本实际占用宽度大于该参数指定的宽度时，会导致出现滚动条。
			    // 2.请不要设置所有的列，最少需要保留一列交由gridmanager进行自适应(注意该列的isShow必须为true)
			    width: '100px',
			    // 列文本对齐信息，字符串类型，非必设项
			    // 三种值: 'left', 'center', 'right'
			    align: 'left',
			    // 列的排序信息。字符串类型，非必设项
			    // 1、'': 该列支持排序，但初始化时不指定排序类型
			    // 2、'DESC': 该列支持排序，并指定为降序。可通过参数[sortDownText]来指定降序所使用的字符串
			    // 3、'ASC': 该列支持排序，并指定为升序。可通过参数[sortUpText]来指定升序所使用的字符串
			    sorting: 'DESC',
			    // 该列是否禁止使用个性配置功能(宽度调整、位置更换、列的显示隐藏)
			    disableCustomize: false,
			    template: function(nodeData, rowData){
			        return nodeData != null ? nodeData : "";
			    }
			}],
			/*topFullColumn: {
		        // 顶部通栏所使用的模板
		        template: function(row){
		            return '<div>我是通栏，哈哈</div>'
		        }
		    },*/
			// 分页前事件
			pagingBefore: function(query){
				// console.log('pagingBefore', query);
			},
			// 分页后事件
			pagingAfter: function(data){
				// console.log('pagingAfter', data);
			},
			// 排序前事件
			sortingBefore: function (data) {
				console.log('sortBefore', data);
			},
			// 排序后事件
			sortingAfter: function (data) {
				// console.log('sortAfter', data);
			},
			// 宽度调整前事件
			adjustBefore: function (event) {
				// console.log('adjustBefore', event);
			},
			// 宽度调整后事件
			adjustAfter: function (event) {
				// console.log('adjustAfter', event);
			},
			// 拖拽前事件
			dragBefore: function (event) {
				// console.log('dragBefore', event);
			},
			// 拖拽后事件
			dragAfter: function (event) {
				//console.log('dragAfter', event);
			}
		}, option || {});
		tableManager = $(this);
		$(this).GM(setting);
		console.log(tableManager)
	};   
})(jQuery);