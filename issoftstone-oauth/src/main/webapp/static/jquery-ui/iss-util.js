$(function() {
	$("input[name='chks']").click(function() {
		if(this.checked){   
	        $("input[name='chk']").prop("checked", true);  
	    } else{   
			$("input[name='chk']").prop("checked", false);
	    }   
	})
});

var submitDialog = '<div id="submitDialog" style="display: none;">'
					    +'<img style="height: 30px;" src="' + ctx + '/static/jquery-ui/loading_circle_40b82ef.gif">'
					    +'&nbsp;&nbsp;<span id="msg">正在操作，请稍候...</span>'
				  +'</div>';
var tipDialog = '<div id="tipDialog" style="display: none;">'
					+'<span id="tip_content" style="color: #000"></span>'
				+'</div>';
$.extend({
	/**
	 * 打开确认框、弹出框
	 */
	openTip: _openTip,
	openLoading: openLoading,
	closeLoading:_closeLoading,
	getCheckboxIds: _getCheckboxIds,
	datadel: _datadel
})


function _datadel(chkName,ajaxOption) {
	var ids = $.getCheckboxIds(chkName);
	if(!ids) {
		$.openTip("请选择一项再进行操作.",true,function(dialog) {
			$(dialog).dialog('close');
			return;
		})
	} else {
		$.openTip('你确定删除吗？',false, function(dialogConfirm) {
			$(dialogConfirm).dialog( "close" );
			$.openLoading('正在删除，请稍等...');
			ajaxOption = !ajaxOption ? {} : ajaxOption;
			ajaxOption.data = {id : ids};
			$.ajax(ajaxOption);
		});
	}
}

function _getCheckboxIds (chkName) {
	var ids = "";
	$("input[name='" + chkName + "']").each(function () {
		if(this.checked) {
			var id = $(this).attr('data-id');
			console.log();
			ids += id + ",";
		}
	});
	if (ids && ids.length > 1) {
		ids = ids.substring(0,ids.length - 1);
	}
	return ids;
}


function _closeLoading() {
    $("#submitDialog").dialog('close')
}
function openLoading(msg) {
	$("body").after(submitDialog)
	if(msg) $("#msg").html(msg);
    $("#submitDialog").dialog({
        closeOnEscape: false,
        width: 'auto',
        dialogClass: "no-close",
        height: 100,
        resizable: false,
        draggable: false,
        modal: true,
        //隐藏默认的关闭按钮
        open: function (event, ui) {
            $('#submitDialog').parent().find(".ui-dialog-titlebar-close", $(this).parent()).hide();
            $('#submitDialog').parent().find(".ui-dialog-titlebar").remove();
            $("#submitDialog").attr('style', 'width: auto;min-height: 0px;max-height: none;height: 30px;color:white')
            $("#submitDialog").parent().css('background', "#969494");
        }
    });
}

function _openTip (content,isAlert, callback) {
	$("body").after(tipDialog)
	if(content) $("#tip_content").text(content);
	var buttons = [{
      	text: "确定",
      	click: function() {
      		callback(this);
      	}
    },{
      	text: "取消",
      	click: function() {
        	$( this ).dialog( "close" );
      	}
  	}];
	if(isAlert) {
		var buttons = [{
          	text: "确定",
          	click: function() {
            	callback(this);
          	}
		}];
	}
	$( "#tipDialog" ).dialog({
        width: 250,
        title:'提示信息',
        dialogClass: "no-close",
        height: 100,
        content:'你确定删除吗？',
        resizable: false,
        draggable: false,
        modal: true,
        //隐藏默认的关闭按钮
        open: function (event, ui) {
            $(".ui-dialog-titlebar-close", $(this).parent()).hide();
            $(".ui-dialog-buttonset button").addClass('btn radius');
            $("#tipDialog").attr('style', 'width: auto;min-height: 0px;max-height: none;height: 30px;color:white')
        },
		buttons: buttons
	});
}