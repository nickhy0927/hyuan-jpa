//tyqianc 2018-6-21 前端公共编解码方法
//编码
function htmlEncode(HtmlString)
{
	var str = HtmlString;
	if(HtmlString != null && typeof (HtmlString) === "string")
	{
	HtmlString.replace(/&/g,"&amp;");
    str = str.replace(/</g,"&lt;");
    str = str.replace(/>/g,"&gt;");
    str = str.replace(/\"/g,"&quot;");
    str = str.replace(/\'/g,"&#39;");
    str = str.replace(/\(/g,"&#40;");
    str = str.replace(/\)/g,"&#41;");
    str = str.replace(/%/g,"&#37;");
    str = str.replace(/\+/g,"&#43;");
    str = str.replace(/\,/g,"&#44;");
    
    //&<>"'()%+,
    //&& lt;& gt;"& #39;& #40;& #41;%+,
	}
    return str;
}
//解码 定制
function htmlDecode(HtmlString)
{
	var str = HtmlString;
	if(HtmlString != null && typeof (HtmlString) === "string")
	{
	str = HtmlString.replace(/\&amp;/g,"&");
    str = str.replace(/\& lt;/g,"<");
    str = str.replace(/\& gt;/g,">");
    str = str.replace(/\& quot/g,"\"");
    str = str.replace(/\& #39;/g,"\'");
    str = str.replace(/\& #40;/g,"\(");
    str = str.replace(/\& #41;/g,"\)");
    str = str.replace(/\& #37;/g,"%");
    str = str.replace(/\& #43;/g,"\+");
    str = str.replace(/\& #44;/g,"\,");
	}
    return str;
}
//实际使用直接调用即可

//完成后对界面元素解码
window.onload = function () {  
    var decodeStr = "";
    $("input").each(function(){
        decodeStr = htmlDecode($(this).val());
        $(this).val(decodeStr);
    });
    $("textarea").each(function(){
        decodeStr = htmlDecode($(this).val());
        $(this).val(decodeStr);
    });
    $("p").each(function(){
        decodeStr = htmlDecode($(this).html());
        $(this).html(decodeStr);
    });
    //定制处理
} 