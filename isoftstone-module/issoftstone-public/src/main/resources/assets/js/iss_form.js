(function($) {
	
	//普通文本框
	$.addIssText = function(p){
		
		$.each($(".iss_text"), function(i, n) {
//			$(n).css('width', '162');
		});
	};
	
	//备注文本框
	$.addIssRemark = function(p){

		$.each($(".iss_remark"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '50');
//			$(n).css('width', '550');
		});
		
		$.each($(".iss_remark100"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '100');
//			$(n).css('width', '550');
		});
		
		$.each($(".iss_remark50"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '50');
//			$(n).css('width', '550');
		});
		
		$.each($(".iss_remark200"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '200');
//			$(n).css('width', '550');
		});
		
		$.each($(".iss_remark250"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '250');
//			$(n).css('width', '550');
			$(n).css('height', '60');
		});
		
		$.each($(".iss_remark500"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '500');
//			$(n).css('width', '550');
			$(n).css('height', '100');
		});
		
		$.each($(".iss_remark4000"), function(i, n) {
			//$(n).attr('size', '60');
			$(n).attr('maxlength', '1000');
//			$(n).css('width', '550');
			$(n).css('height', '100');
		});
		
		$.each($(".iss_remarkdefine"), function(i, n) {
			// $(n).css('width', '550');
			// tcol是行高,默认2行, maxLength是最大字符限制
			if(undefined != $(n).attr('tcol')) {
				$(n).css('height', $(n).attr('tcol') * 22);
			}
			if(undefined != $(n).attr('maxlength')) {
				$(n).maxLength($(n).attr('maxlength'));
			}
			
			// 对textarea文本加的数据限制提示
			var parentTd = $(n).parent().prev('td');
			if('' != parentTd) {
				var orgText = parentTd.text();
				var maxlength = 100;
				// 找出长度,默认100
				if(undefined != $(n).attr('maxlength')) {
					maxlength = $(n).attr('maxlength');
				}
				// 验证字符中是否含有:
				var index = orgText.indexOf(':');
				
				orgText = orgText + '<br />' + '(' + maxlength + '字以内)';
				parentTd.html(orgText);
			}
		});
		
		$.each($(".iss_remarkdefinepop"), function(i, n) {
			// $(n).css('width', '550');
			// tcol是行高,默认2行, maxLength是最大字符限制
			if(undefined != $(n).attr('tcol')) {
				$(n).css('height', $(n).attr('tcol') * 16);
			}
			if(undefined != $(n).attr('maxlength')) {
				$(n).maxLength($(n).attr('maxlength'));
			}
		});
	};
	
	$.issLongText = function() {
		// 处理textarea的按格式显示
		$(".longText tbody tr td pre").each(function(i) {
			var length = 100;
	   		/*if($(this).text().length > length){
		    	$(this).attr("title", $(this).text());
		    	var text=$(this).text().substring(0, length)+"...";  
		        $(this).text(text);
		    }*/
		    
		    var $div = $("<div/>").addClass("longText-div");
		    $div.html($(this).text());
		    $(this).text('').append($div);
		    
	    });
	};
	
	$.ulLiText = function() {
		$(".td-list-ul li span").each(function(i) {
			var length = 8;
			//$(this).parent().attr("title", $(this).text());
			var text_ = $(this).text();
			var content = text_.replace(/(^\s+)|(\s+$)/g,"");
			$(this).text(content);
	   		if(content.length > length){
	   			var text=content.substring(0, length)+"...";  
	   			$(this).text(text);
		    }
	    });
	};
	
	$.ulLiContent = function() {
		$(".td-list-ul li span").each(function(i) {
			//$(this).parent().attr("title", $(this).text());
			var text_ = $(this).text();
			var content = text_.replace(/(^\s+)|(\s+$)/g,"");
			$(this).text(content);
	    });
	};
	
	// 项目放大镜向pre中去掉多一个空格的
	$.replaceText = function(str) {
		if('' != str) { 
			var nstr = str.replace(/\n\r/g,"<br/>");  
			nstr = str.replace(/\r/g,"");
			nstr = str.replace(/\n/g,"");
		}
		return nstr;
	};
	
	//日历控件框
	$.addIssDatebox = function(p){
		
		$.each($(".iss_datebox"), function(i, n) {
//			$(n).css('width', '168');
			$(n).datebox();
		});
	};
	
	//Select控件框
	$.addIssSelect = function (t, p) {
		
		p = $.extend({
			onChange:function(v,t){
			}
		}, p);
		$(t).empty();
		if(p.optModel && p.optModel.length > 0){
			for(var i=0; i<p.optModel.length; i++){
				var option = '<option value="'+ p.optModel[i].key +'">'+ p.optModel[i].value +'</option>';

				if(p.optDefault != undefined && p.optDefault == p.optModel[i].key){
					option = '<option value="'+ p.optModel[i].key +'" selected>'+ p.optModel[i].value +'</option>';
				}
				
				$(t).append(option);
//				$(t).css("width","168");
			}
		}
		
		$(t).bind('change',function(){
			p.onChange(this.value,this.textContent);
		});
	};
	
	// 可用于放大镜和datagrid的列表
	$.fn.issdatagridtitle = function(p) {
		if('undefined' == p.titleField || undefined == p.titleField) {
			return;
		}
		// 两个放大镜 一个field是小写，一个是大写. 统一处理
		if('' != p.titleField || null != p.titleField) {
			if(p.titleField.length > 0) {
				var cell = $('.datagrid-body > .datagrid-btable').find('.datagrid-cell');
		    	$.each(cell,function() {
		    		var childer = $(this).children();
		    		// 取下级显示值
		    		var title = '';
		    		if(childer.length > 0) {
		    			title = $(childer).text();
		    		} else {
		    			title = $(this).text();
		    		}
		    		// 需要的展示的列
		    		var parent = $(this).parent();
		    		var field = $(parent).attr('field');
		    		
		    		// 以，分隔的数组
		    		for(var i=0;i<p.titleField.length;i++) {
		    			if(p.titleField[i] == field){
		    				$(parent).attr('onmouseover','titleMouseOver(this,event);');
		    				$(parent).attr('onmouseout','titleMouseOut(this);');
		    			}
		    			// 统一转小写处理
		    			if(field.toLocaleLowerCase() == p.titleField[i].toLocaleLowerCase()) {
		    				$(parent).attr('title', title);
		    			}
		    		}
		    	});
			}
		}
	};

	$.fn.selectutil = function (p) {
		$.addIssSelect($(this), p);
	};
	
	//Magnifier
	$.addMagnifier = function (t, p) {

		p = $.extend({
			formid: '',
			linkName: '',
//			width: 168,
			formatter: function(value, row, index){
	 	  		if(!value == false) {
	 	  			return '<a href="javascript:$.fn.addMagnifierlink(\''+ $(t).attr('id') +'\',\''+ value +'\','+ index +')">'+ value +'</a>';
	 			}
			},
			onClose:function(){
			},
			onBeforeOpen:function(){
			},
			onChange:function(){
			}
		}, p);
		
		$(t).searchbox({
				width: p.width,
				searcher:function(value, name) {
					$('.searchbox-text[name='+name+']').blur();
					var tid = $(t).attr('id');
					$('#'+tid).val(value);

					var dialogHtml = '<div id="'+ tid +'_div"><table id="'+ tid +'_datagrid" width="100%"></table></div>';
					$(document.body).prepend(dialogHtml);
					
					//对linkName进行处理
					for(var i=0; i<p.columns[0].length; i++){
						var column = p.columns[0][i];
						if(column.field == p.linkName){
							column.formatter = p.formatter;
						}
					}
					
					var scrollTop = window.document.documentElement.scrollTop;
					//$(document.body).css({"overflow":"hidden"});
					
					$("#" + tid + "_div").dialog({
						title: '放大镜',
						width: 620,  
						height: 410,  
						cache: false,
						modal: true,
						onBeforeClose: function(){
							p.onClose.call();
							//$(document.body).css({"overflow":"auto"});
						},
						onClose: function(){
							//$(this).remove();
							$(this).empty();
							$(this).detach();
						}
					});
		
					var queryParams = p.onBeforeOpen.call();
		
					var querys = null;
					if(queryParams) {
						querys = queryParams;
						querys[name] = value;
					}
					else {
						querys = $('#' + p.formid).serializeObject();
					}
					
					$("#" + tid + "_datagrid").datagrid({
						width: 'auto',
						height: 360,
						nowrap: true,
						autoRowHeight: false,
						striped: true,
						url: p.url,
						sortName: p.sortName,
						sortOrder: p.sortOrder,
						remoteSort: true,
						pagination:true,
						rownumbers:true,
						columns: p.columns,
						queryParams: querys,
						onLoadSuccess : function(data){
							//显示AJAX请求后的提示信息
							$.fn.ajaxmessage(data);
							window.document.documentElement.scrollTop = scrollTop;
							//$.fn.issdatagridtitle(p);
						}
					});
					
					$("#" + tid + "_datagrid").datagrid('loaded');
				}
		});

		$('.searchbox-text', $(t).parent()).bind("change", function(e){
			
			var tid = $(t).attr('id');
			$('#'+tid).val($(this).val());
			var flagHiddenClean = true;
			var columns = p.columns;
			for(var i=0; i<columns[0].length; i++)
			{
				//如果不是先从放大镜中选择，不用删除查询条件
				var hidden = columns[0][i].hidden;
				var formel = columns[0][i].formel;
				if(hidden==true&&$('#' + formel).val()=="")
				{
					flagHiddenClean=false;
					break;
				}
			}
			
			for(var i=0; i<columns[0].length; i++){
				if(columns[0][i].formel){
					var formel = columns[0][i].formel;
					
					if($('#' + formel).is('input') || $('#' + formel).is('select'))
					{
						$('#' + formel).val('');
						if($('#' + formel).attr('searchboxname')&&flagHiddenClean)
						{
							$('#' + formel).searchbox('setValue', '');
							$("[name="+formel+"]").val('');
						}
						
					}
					else{
						$('#' + formel).html('');
					}
				}
			}
			
			p.onChange.call();
		});
	};
	
	/*$('.searchbox-text', $(t).parent()).bind("change", function(e){
		
		var tid = $(t).attr('id');
		$('#'+tid).val($(this).val());
		
		var columns = p.columns;
		for(var i=0; i<columns[0].length; i++){
			if(columns[0][i].formel){
				var formel = columns[0][i].formel;
				var hidden = columns[0][i].hidden;
				if(hidden==true&&$('#' + formel).val()=="")
				{
					break;
				}
				
				if($('#' + formel).is('input') || $('#' + formel).is('select'))
				{
					$('#' + formel).val('');
					if($('#' + formel).attr('searchboxname'))
					{
						$('#' + formel).searchbox('setValue', '');
					}
					$("[name="+formel+"]").val('');
				}
				else{
					$('#' + formel).html('');
				}
			}
		}
		
		p.onChange.call();
	});
};*/
	
	$.fn.addMagnifierlink = function (tid, value, index) {
		//1.link后的值给输入框
		$('#'+tid).searchbox('setValue', value);
		$('#'+tid).val(value);
		
		var allData = $("#" + tid + "_datagrid").datagrid('getData');
		var columns = $("#" + tid + "_datagrid").datagrid('options').columns;
		var rowData = allData.rows[index];

		//2.link后给hidden赋值
		for(var i=0; i<columns[0].length; i++){
			var field = columns[0][i].field;
			if(columns[0][i].formel){
				var formel = columns[0][i].formel;
				if($('#' + formel).is('input')){
					if($('#' + formel).attr('searchboxname')){
						$('#' + formel).searchbox('setValue', rowData[field]);
					}
					else if($('#' + formel).attr('numberboxname'))
					{
						$('#' + formel).numberbox('setValue', rowData[field]);
					}
					$('#' + formel).val(rowData[field]);
				}
				else{
					$('#' + formel).html(rowData[field]);
				}
			}
		}
		
		//3.link后关闭dialog
		$('#'+ tid + '_div').dialog('close');
		$('#'+ tid + '_div').remove();
	};
	
	$.fn.magnifier = function (p) {
		$.addMagnifier($(this), p);
	};

	//Window
	$.addIssWindow = function (t, p) {
	   // window.scrollTo(0,0);
		p = $.extend({
			title: '对话框',
			url : '',
			maximized : true,
			width:820,
			height:412,
			onClose:function(){
			}
		}, p);
		var iframeid = "win_iframe";

		var s = window.parent.document.activeElement;
		var frameid= $(s).attr("id");
		if(frameid == "setepFrame"){
			var windowHtml = '<iframe id="'+ iframeid +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" vspale="0" scrolling="yes" height="99%" width="100%"></iframe>';
			var body = $(s).closest('body');
			body.find("#fundWindow").prepend(windowHtml);
			window.parent.window.public_window(p);
		}else{
			var divid = "win_div";
			var windowHtml = '<div id="'+ divid +'"><iframe id="'+ iframeid +'" border="0" frameborder="0" framespacing="0" marginheight="0" marginwidth="0" vspale="0" scrolling="yes" height="99%" width="100%"></iframe></div>';
			
			$('body').prepend(windowHtml);
			$('#' + iframeid).attr('src', p.url);
			$('#' + divid).window({
				title: p.title,  
				width: p.width,  
				height: p.height,
				modal: true,
				collapsible: false,
				minimizable: false,
				maximizable: p.maximizable,
				maximized: p.maximized,
				onBeforeClose: function(){
					p.onClose.call();
				},
				onClose: function(){
					$('body').css('overflow', 'auto');
					$('#win_div').empty();
					$('#win_div').detach();
					//$(this).remove();
				}
			});
		}
		
		//$('body').css('overflow', 'hidden');
			//var t = $(this)
		
	};
	
	$.fn.closeIssWindow = function () {
			//关闭Window
			if(parent.window && parent.window != window) {
				parent.$('#win_div').window('close');
				parent.$('#win_div').remove();
			}
			if(parent.window && parent.window != window) {
				var pwindow = parent.$(".panel.window");
				pwindow.next().remove();
				pwindow.next().remove();
				pwindow.after("<div id='fundWindow'></div>");
				pwindow.remove();
			}
	};

	$.fn.isswindow = function (p) {
		
		$.addIssWindow($(this), p);
	};

	
	//CurrencyAndAmount
	var CurrencyAndAmountRegExp = /^(\d{1,18})|(\d{1,18}\.)|(\d{1,17}\.\d{0,1})|(\d{1,16}\.\d{0,2})|(\.\d{1,2})$/;
	
	$.addAmount = function(p){
		
		//搜索所有的class为amount的text
		$.each($(".iss_amount"), function(i, n) {
			
			$(n).numberbox({
			    min:0,
			    precision:2,
			    groupSeparator:',',
			    decimalSeparator:'.',
			    prefix:''
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 12){
					alert('金额整数位最大支持12位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	$.addAmountFour = function(p){
	//搜索所有的class为amount的text
		$.each($(".iss_amountFour"), function(i, n) {
			
			$(n).numberbox({
			    min:0,
			    precision:4,
			    groupSeparator:',',
			    decimalSeparator:'.',
			    prefix:''
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 12){
					alert('金额整数位最大支持12位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	$.addAmountNoPrecision = function(p){
		
		
		$.each($(".iss_noprecision"), function(i, n) {
			
			$(n).numberbox({
			    min:0,
			    max:99999,
			    precision:0
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 12){
					alert('金额整数位最大支持5位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	//支持金额为负数
	$.addNegativeAmount = function(p){
		$.each($(".iss_NegativeAmount"), function(i, n) {
			$(n).numberbox({
			    precision:2,
			    groupSeparator:',',
			    decimalSeparator:'.',
			    prefix:''
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');
			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 12){
					alert('金额整数位最大支持12位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
		});
	};
	
	//不带货币符号的金额
	$.addPureAmount = function(p){
		
		//搜索所有的class为amount的text
		$.each($(".iss_pureamount"), function(i, n) {
			
			$(n).numberbox({
				min:0,
				precision:2,
				groupSeparator:',',
				decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');
			
			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 12){
					alert('金额整数位最大支持12位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	$.fn.amount = function () {

		var n = $(this);
		
		$(n).numberbox({
		    min:0,
		    precision:2,
		    groupSeparator:',',
		    decimalSeparator:'.',
		    prefix:'￥'
		});
		
//		$(n).css('width', '162');
//		$(n).css('text-align', 'right');

		if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
		} else {
			$(n).focus(function() {
				$(n).val($(n).numberbox('getValue'));
				$(n).select();
			});
		}
		
		$(n).bind('blur',function(){
			var part = String($(n).numberbox('getValue')).split(".");
			if(part[0].length > 12){
				alert('金额整数位最大支持12位，请重新输入!');
				$(n).numberbox('clear');
			}
		});

	};
	
	//万元
	$.addBigAmount = function(p){
	
		//搜索所有的class为amount的text
		$.each($(".iss_bigamount"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,
			    precision:6,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue')).split(".");
				if(part[0].length > 8){
					alert('金额整数位最大支持8位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
		});
	};
	
	$.reverseFormatAmount = function (strData) { //反向格式化金额
		
		var rData = "";
		var strTemp = new String(strData);

		for(var i=0;i<strTemp.length;i++)
		{
			var cData = strTemp.charAt(i);
			if (cData!=",")
			{
				rData = rData+cData;
			}
		}
		return rData;
	};
	
	$.formatAmount  = function (strData, n)
	{
		var _result = CurrencyAndAmountRegExp.test(strData);
		if(_result == false){
			return strData;
		}
		
		n = n > 0 && n <= 6 ? n : 2;
		var formatData = parseFloat((strData + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";
		var l = formatData.split(".")[0].split("").reverse();
		var r = formatData.split(".")[1];
		var t = "";
		for(i = 0; i < l.length; i ++ )
		{
			t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");
		}
		return t.split("").reverse().join("") + "." + r;
	};

	//PercentageRate
	var PercentageRateRegExp = /^(\d{1})|(\d{1}\.\d{0,6})|(\.\d{1,6})$"/;
	
	$.addRate = function(p){
		
		//搜索所有的class为amount的text
		$.each($(".iss_rate"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,  
			    max:99.999999,
			    precision:6,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
						$(n).val($(n).numberbox('getValue'));
						$(n).select();
				});
			}
			
		});
		
		$.each($(".iss_percent"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,  
			    max:100.000000,
			    precision:6,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
						$(n).val($(n).numberbox('getValue'));
						$(n).select();
				});
			}
			
		});
		
	};
	
   $.addRate2 = function(p){
		
		//搜索所有的class为amount的text
		$.each($(".iss_rate2"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,  
			    max:100.000000,
			    precision:6,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
						$(n).val($(n).numberbox('getValue'));
						$(n).select();
				});
			}
			
		});
	};
	
	$.addRate3 = function(p){
		
		//搜索所有的class为amount的text
		$.each($(".iss_rate3"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,  
			    max:100.000000,
			    precision:2,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
		});
	};
	
	$.addSoftRate = function(p){
		//搜索所有的class为amount的text
		$.each($(".iss_softRate"), function(i, n) {
			
			$(n).numberbox({  
			    min:0,  
			    max:999999.999999,
			    precision:6,
			    decimalSeparator:'.'
			});
			
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');
			
			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
						$(n).val($(n).numberbox('getValue'));
						$(n).select();
				});
			}
		});
	};
	
	//解析日期JSON
	$.formatJSONDate = function (dateJSON, delimiter) {
		var returnStr = "";
		
		if(dateJSON != null)
		{
			delimiter = delimiter == null ? "-" : delimiter;
			
			var year = 1900 + dateJSON.year;
			
			var month = 1 + dateJSON.month;
			
			month = month.toString().length == 1 ? "0"+month.toString() : month;
			
			var date = dateJSON.date.toString().length == 1 ? "0"+dateJSON.date.toString() : dateJSON.date;
			
			returnStr = year + delimiter + month + delimiter + date;
		}
		
		return returnStr;
	};
	
	//issMagnifier
	$.addIssMagnifier = function (t, p) {

		p = $.extend({
			formid: '',
			linkName: '',
//			width: 168,
			clean:true,
			formatter: function(value, row, index){
	 	  		if(!value == false) {
	 	  			return '<a href="javascript:$.fn.addIssMagnifierlink(\''+ $(t).attr('id') +'\',\''+ value +'\','+ index +')">'+ value +'</a>';
	 			}
			},
			onClose:function(){
			},
			onBeforeOpen:function(){
			},
			onChange:function(){
			},
			onBeforeVaild:function(){
			}		
		}, p);
		
		$(t).searchbox({
				width: p.width,
				searcher:function(value, name) {
					$('.searchbox-text[name='+name+']').blur();
					var vaild = p.onBeforeVaild.call();
					if(vaild==false)
					{
						return;
					}
					
					var tid = $(t).attr('id');
					$('#'+tid).val(value);

					var dialogHtml = '<div id="'+ tid +'_div"><table id="'+ tid +'_datagrid" width="100%"></table></div>';
					$(document.body).prepend(dialogHtml);
					
					if(p.url==null||p.url==""){
					    alert("url is not null");
					    return;
					}
					
					if(p.sortName==null||p.sortName==""){
					    alert("sortName is not null");
					    return;
					}
					
					if(p.sortOrder==null||p.sortOrder==""){
					    alert("sortOrder is not null");
					    return;
					}
					
					if(p.linkName==null||p.linkName==""){
					    alert("linkName is not null");
					    return;
					}
					
					//对linkName进行处理
					for(var i=0; i<p.columns[0].length; i++){
						var column = p.columns[0][i];
						if(column.field.toUpperCase() == p.linkName.toUpperCase()){
							column.formatter = p.formatter;
						}
					}
					
					var scrollTop = window.document.documentElement.scrollTop;
					//$(document.body).css({"overflow":"hidden"});
					
					$("#" + tid + "_div").dialog({
						title: p.title,
						width: 620,  
						height: 410,  
						cache: false,
						modal: true,
						onBeforeClose: function(){
							p.onClose.call();
							//$(document.body).css({"overflow":"auto"});
						},
						onClose: function(){
							//$(this).remove();
							$(this).empty();
							$(this).detach();
						}
					});
		
					var queryParams = p.onBeforeOpen.call();

					var querys = {};
					if(queryParams) {
						querys = queryParams;
						querys[name] = value;
					}
					else {
						querys = $('#' + p.formid).serializeObject();
					}
					
					if(p.callSqlKey){
					    querys["callSqlKey"]=p.callSqlKey;
					}
					else{
					    alert("callSqlKey is not null");
					    return;
					}
					
					var dataColumns=p.columns;
					for(var i=0;i<dataColumns[0].length;i++){
					   //alert(dataColumns[0][i].field);
					   querys["column_data_"+i]=dataColumns[0][i].field;
					   //change uppercase
					   dataColumns[0][i].field=dataColumns[0][i].field.toUpperCase();
					}
					
					$("#" + tid + "_datagrid").datagrid({
						width: 'auto',
						height: 360,
						nowrap: true,
						autoRowHeight: false,
						striped: true,
						url: p.url,
						sortName: p.sortName,
						sortOrder: p.sortOrder,
						remoteSort: true,
						pagination:true,
						rownumbers:true,
						columns: dataColumns,
						queryParams: querys,
						onLoadSuccess : function(data){
							//显示AJAX请求后的提示信息
							$.fn.ajaxmessage(data);
							window.document.documentElement.scrollTop = scrollTop;
							//$.fn.issdatagridtitle(p);
						}
					});
					
					$("#" + tid + "_datagrid").datagrid('loaded');
				}
		});

		$('.searchbox-text', $(t).parent()).bind("change", function(e){
			
			var tid = $(t).attr('id');
			$('#'+tid).val($(this).val());
			var columns = p.columns;
			var flagHiddenClean = true;
			for(var i=0; i<columns[0].length; i++)
			{
				//如果不是先从放大镜中选择，不用删除查询条件
				var hidden = columns[0][i].hidden;
				var formel = columns[0][i].formel;
				if(hidden==true&&$('#' + formel).val()=="")
				{
					if($('#' + formel).is('span'))
					{
						if($('#' + formel).html=="")
						{
							flagHiddenClean=false;
							break;
						}
					}
					else
					{
						flagHiddenClean=false;
						break;
					}
					
				}
			}
			
			for(var i=0; i<columns[0].length; i++)
			{
				
				if(columns[0][i].formel&&flagHiddenClean&&p.clean)
				{
					var formel = columns[0][i].formel;
					if($('#' + formel).is('input')){
						$('#' + formel).val('');
						if($('#' + formel).attr('searchboxname')&&flagHiddenClean)
						{
							$('#' + formel).searchbox('setValue', '');
							$("[name="+formel+"]").val('');
						}
						
					}
					else{
						$('#' + formel).html('');
					}
				}
			}
			
			p.onChange.call();
		});
	};
	
	$.fn.addIssMagnifierlink = function (tid, value, index) {
		//1.link后的值给输入框
		$('#'+tid).searchbox('setValue', value);
		$('#'+tid).val(value);
		
		var allData = $("#" + tid + "_datagrid").datagrid('getData');
		var columns = $("#" + tid + "_datagrid").datagrid('options').columns;
		var rowData = allData.rows[index];

		//2.link后给hidden赋值
		for(var i=0; i<columns[0].length; i++){
			var field = columns[0][i].field;
			if(columns[0][i].formel){
				var formel = columns[0][i].formel;
				if($('#' + formel).is('input') || $('#' + formel).is('select')){
					if($('#' + formel).attr('searchboxname')){
						$('#' + formel).searchbox('setValue', rowData[field]);
					}
					else if($('#' + formel).attr('numberboxname'))
					{
						$('#' + formel).numberbox('setValue', rowData[field]);
					}
					$('#' + formel).val(rowData[field]);
				}
				else{
					$('#' + formel).html(rowData[field]);
				}
			}
		}
		
		//3.link后关闭dialog
		$('#'+ tid + '_div').dialog('close');
		$('#'+ tid + '_div').remove();
	};
	
	$.fn.issmagnifier = function (p) {
		$.addIssMagnifier($(this), p);
	};
	
	$.fn.addSoftRate = function (p) {
		$.addSoftRate(p);
	};
	
	$.fn.maxLength = function(max){ 
	    return this.each(function(){
	        var type = this.tagName.toLowerCase(); 
	        var inputType = this.type? this.type.toLowerCase() : null; 
	        if(type == "input" && inputType == "text" || inputType == "password"){ 
	            //Apply the standard maxLength 
	           // http://www.sharejs.com
	            this.maxLength = max; 
	        } else if(type == "textarea"){
	            this.onkeypress = function(e){ 
	                var ob = e || event; 
	                var keyCode = ob.keyCode; 
	                var hasSelection = document.selection? document.selection.createRange().text.length > 0 :this.selectionStart != this.selectionEnd; 
	                return !(this.value.length >= max && (keyCode > 50 || keyCode == 32 || keyCode == 0 || keyCode == 13) &&!ob.ctrlKey && !ob.altKey && !hasSelection); 
	            }; 
	            this.onkeyup = function(){ 
	                if(this.value.length > max){ 
	                    this.value = this.value.substring(0,max); 
	                } 
	            };
	        }
	    });
	};
	
	// 上传附件公共化
	// 地址,主健,业务类型,上传按钮,上传隐藏按钮,窗体显示类型（上传true或查看false）, 是否新增页面(true,false)
	$.fn.ajaxFileEvent = function(systemctx, id ,transType, butUpload, uploadCount, fileEdit, fileText) {
		// 初始化查询查看附件
		// 页面初始化显示文件个数
		$.fileButton(butUpload); // 注掉文字提示
		$(".fileButton").hide();
		$.ajaxFile(systemctx, id, transType, butUpload, uploadCount, fileEdit, fileText);
		
		// 给butUpload添加事件
		$("#" + butUpload).bind('click', function() {
			
			if($('#' + transType).val() == ''){
				$.messager.alert('信息提示', '没有找到有效的交易类型无法上传文件', 'error');
				return;
			}
			
			if($('#' + id).val() == ''){
				$.messager.alert('信息提示', '请先保存单据，再上传附件', 'error');
				return;
			}
			
			$.fn.isswindow({
				title: '文件管理对话框',
				maximized : false,
				url: systemctx + '/common/filemanage.jsp?transType='+ $('#' + transType).val() +'&transId=' + $('#' + id).val() +'&fileEdit=' + fileEdit,
				onClose:function(){
					$.ajaxFile(systemctx, id, transType, butUpload, uploadCount, fileEdit, fileText);
				}
			});
		});
	};
	
	$.ajaxFile = function(systemctx, id, transType, butUpload, uploadCount, fileEdit, fileText) {
		// 上传附件
		var text = '';
		if(fileEdit == true) {
			text = '上传';
		} else {
			text = '查看';
		}
		
		$.ajax({
			type:'POST',
			url: systemctx + '/common/queryFilesCount.json',
			data:{
				transType: $('#' + transType).val(),
				transId: $('#' + id).val(),
				fileEdit: 'false'
			},
			dataType:'json',
			success: function(data){
				$("#" + butUpload).val(text + "附件（"+data.map.filesCount+"）条");
				$("#" + uploadCount).val(data.map.filesCount);
		        
				if(fileText == true) {
					if(data.map.filesCount == 0) {
						// 同级存在fileButton不再追加显示
						if('' == $('#' + id).val()) {
							$(".fileButton").show();
						} else {
							// 隐藏提示
							$(".fileButton").hide();
						}
					} else {
						// 隐藏提示
						$(".fileButton").hide();
					}
				}
			}
		});
	};
	
	// 上传附件旁边提示（请先保存再上传附件）
	$.fileButton = function(butUpload) {
		// 在最后一个上传附件按钮后加上文字描述
		// 最后一个button按钮对象
		var span = $('<span />').addClass('span_required fileButton').text('请先保存再上传附件!');
		$("#" + butUpload).after(span);
	};
	
	
	//整数
	$.addNumber = function(p){
		
		//搜索所有的class为iss_number的text
		$.each($(".iss_number"), function(i, n) {
			
			$(n).numberbox({
			    min:0,
			    precision:0
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					$(n).val($(n).numberbox('getValue'));
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue'));
				if(part.length > 12){
					alert('整数最大支持12位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	// 股票代码 只能输入数字，且不格式化 可以输入000001
	$.addNumberFormat = function(p){
		
		//搜索所有的class为iss_number的text
		$.each($(".addNumberFormat"), function(i, n) {
			
			$(n).numberbox({
			    min:0,
			    precision:0,
			    max:999999,
			    formatter: function(v) {
			    	// 不足6位自动前补0
			    	if('' != v) {
			    		var count = 6;
			    		var vlength = v.length;
			    		for(var i = vlength; vlength < 6;vlength++) {
			    			v = '0' + v;
			    		}
			    		return v;
			    	}
			    }
			});
//			$(n).css('width', '162');
//			$(n).css('text-align', 'right');

			if ($(n).attr("readonly") == true || $(n).attr("disabled") == true) {
				$(n).numberbox('disabled', 'true');
			} else {
				$(n).focus(function() {
					var v = $(n).numberbox('getValue');
					// 不足6位自动前补0
			    	if('' != v) {
			    		var count = 6;
			    		var vlength = v.length;
			    		for(var i = vlength; vlength < 6;vlength++) {
			    			v = '0' + v;
			    		}
			    		$(n).val(v);
			    	}
					$(n).select();
				});
			}
			
			$(n).bind('blur',function(){
				var part = String($(n).numberbox('getValue'));
				if(part.length > 6){
					alert('最大支持6位，请重新输入!');
					$(n).numberbox('clear');
				}
			});
			
		});
	};
	
	// 自定义模板
	// 数据申请单位,模块,业务类型,数据ID,table的ID, 是否明细页面(true)
	$.defineModel = function(systemctx,agencyId, module, transType, id, defineModel, flag) {
		// 自定义模板
		$.ajax({
			type:'POST',
			url: systemctx + '/systemmanage/templateDetail/queryTemplateInfo.json',
			data:{
				orgId: agencyId,
				module: module,
				type: transType,
				businessId: id
			},
			dataType:'json',
			success:function(data){
				if(flag) {
					$("#" + defineModel).createTable(data.resultList, true);
				} else {
					$("#" + defineModel).createTable(data.resultList);
				}
				// 改变样式
				$("#" + defineModel + " tbody tr").each(function(i, n) {
					if($(this).find('.td04').length <= 1) {
						$(this).find('.td04').eq(0).removeClass().addClass("td03").attr("colspan","4");
					} else {
						$(this).find('.td04').eq(1).removeClass().addClass("td03");
					}
				});
				
				// 给各个加样式
				$("#" + defineModel).find('textarea').each(function(i, n) {
					$(n).attr('maxlength', '50');
					$(n).css('width', '580');
				});
				
				$("#" + defineModel).find('select').each(function(i, n) {
					$(n).css("width","168");
				});
				
				$("#" + defineModel).find('input[type=text]').each(function(i, n) {
					$(n).css('width', '162');
				});
				
				$.each($(".define_datebox"), function(i, n) {
					$(n).css('width', '168');
					$(n).datebox();
					$(n).datebox('setValue', $(n).attr('definedatebox'));
					$("input",$(this).next()).attr("dm", "defineModel")
					.attr("name","_datename")
					.attr("comboname",$(this).attr("comboname"));
					$("input:eq(1)",$(this).next()).removeAttr("dm")
					.attr("name",$(this).attr("comboname"))
				});
			}
		});
	};
	
	// 数据保存
	$.defineModelSave = function(defineModel, defineModelJson) {
		var modelJson = '';
		$('[dm=' + defineModel + ']').each(function(i, n){
			// 将不为空的值转成JSON
			var value = $(n).val();
			var key = $(n).attr('name');
			
			if('checkbox' == $(n).attr('type')) {
			   key = $(n).attr("id");
			   var value = '';
			   $('input[name="'+key+'"]:checked').each(function(){ 
			   		value += $(this).val()+',';
			   });
			   if('' != value) {
			   		modelJson += '{"key":"' + key + '","value":"' + value + '"},';
			   }
			} else if('radio' == $(n).attr('type')){
				if($(n).is(":checked")) {
					modelJson += '{"key":"' + key + '","value":"' + value + '"},';
				}
			} else {
				if('' != $(n).val()) {
					if("_datename" == key){
						key = $(n).attr("comboname");
					}
					modelJson += '{"key":"' + key + '","value":"' + value + '"},';
				}
			}
		});
		if('' != modelJson) {
			// 去掉最后一个,号
			var len = modelJson.length - 1;
			if(len == modelJson.lastIndexOf(',')) {
				modelJson = modelJson.substr(0, len);
				modelJson = '[' + modelJson + ']';
			}	
		}
		$('#' + defineModelJson).val(modelJson);
	};
	
	// 验证算定义模板
	// table的Id, 页面原验证对象, from的ID
	$.defineModelValidate = function(defineModel, pageModel, form1) {
		//读取自定义验证数据
		var valueModel = $("#" + defineModel).colModel();
		var formModel = '{"colModel2" : [ '+ valueModel +' ]}';
		formModel = $.parseJSON(formModel);
		// 合一起
		$.each(formModel.colModel2,function(i,n){
			pageModel.colModel.push(this);
		});
		// var pageModelJson = $.parseJSON(pageModel);
		var exp = $('#' + form1).regularExpressionFormValidate(pageModel);
		if(exp==false) {
			return false;
		}
		return true;
	};
		
	
	
	$.fn.extend({
		issWorkList:function(p){
				p = $.extend({
						data:"",
						type:1,
						onClick:function(e){
					}
				}, p);
				p.onClick(this);
				var _pan = $(this).find('p');
//				_pan.toggle();
				if(p.data != '' && p.data != null && p.data != undefined){
					_pan.each(function(){
						var pan =$(this);
						
						if(p.type == 1){
							$.each(p.data,function(key,value){
								if($(pan).is("#"+key)){
//									if(value != 0)
//										$(pan).toggle();
									$(pan).show();
									// 为0的不显示,待已都一样
									if(value == '0'){
										$(pan).hide();
									} else {
										$("b",$(pan)).text("("+value+")");
									}
								}
							});
						}else if(p.type == 2){
							$.each(p.data.remindCount,function(){
								if($(pan).is("#"+this.transType)){
//									if(this.count != 0)
//										$(pan).toggle();
									var a = $("<a/>");
									a.attr("href",systemctx+this.pageUrl + this.param);
									var b = $("<b/>");
									b.text("("+this.count+")");
									var text = $(pan).text();
									a.append(text).append(b);
									$(pan).html(a);
								}
							});
						}
						
					});
					
					
					$(this).find("tr").each(function(){
						var t =0;
						$(this).find("p").each(function(){
							if(!$(this).is(":hidden")){
								t++;
							}
						});
						if(t == 0){
							$(this).toggle();
						}
					});
					
					
					if(p.type == 2){
						// 有提醒编号时显示
						if(typeof(p.data.remindCode) != 'undefined'){
							// 根据菜单权限来显示提醒项
							$(this).find('p').each(function(i, n) {
								var menu = $(this).attr('tid');
								if(menu && menu!='workTips') {
									var $t = $(this);
									$t.attr('did', 'menudelete');
									$.each(p.data.remindCode, function(i, sn){
										if(menu == sn) {
											$t.removeAttr('did');
										}
									});
								}
							});
							$(this).find('p[did="menudelete"]').remove();
							
							// 将td行为空的tr删除
							$(this).find('td').each(function() {
								if($(this).text().trim() == '') {
									$(this).parent().remove();
								}
							});
						}
					}
					
					// 列表显示项为0时，显示提示
					$(this).each(function(){
						var visibleLength = $(this).find('td:visible').size();
						if(visibleLength == 0){
							$(this).find('p[tid="workTips"]').show();
							$(this).find('p[tid="workTips"]').parent().parent().show();
						} else {
							$(this).find('p[tid="workTips"]').hide();
							$(this).find('p[tid="workTips"]').parent().parent().hide();
						}
					});
				}
			}
		});
		
	$(document).ready(function () {
		$.addIssText();
		$.addIssRemark();
		$.addIssDatebox();
		$.addAmount();
		$.addAmountFour();
		$.addNegativeAmount();
		$.addPureAmount();
		$.addBigAmount();
		$.addRate();
		$.addRate2();
		$.addSoftRate();
		$.addAmountNoPrecision();
		$.addRate3();
		$.issLongText();
		$.addNumber();
		$.addNumberFormat();
	});

})(jQuery);