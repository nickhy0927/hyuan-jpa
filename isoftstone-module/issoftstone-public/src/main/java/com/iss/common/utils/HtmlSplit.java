package com.iss.common.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 */
public class HtmlSplit {

	private String htmlStr;
	private Integer length = 100;
    /**
     * 定义script的正则表达式
     */
	private static final String REG_EX_SCRIPT = "<script[^>]*?>[\\s\\S]*?</script>";

    /**
     * 定义style的正则表达式
     */
	private static final String REG_EX_STYLE = "<style[^>]*?>[\\s\\S]*?</style>";
    /**
     * 定义HTML标签的正则表达式
     */
	private static final String REG_EX_HTML = "<[^>]+>";

	public HtmlSplit(String htmlStr, Integer length) {
		this.htmlStr = htmlStr;
		if (length != null) {
			this.length = length;
		}
	}

	public String doStartTag() {
		StringBuffer sb = new StringBuffer();
		Pattern pScript = Pattern.compile(REG_EX_SCRIPT, Pattern.CASE_INSENSITIVE);
		Matcher mScript = pScript.matcher(htmlStr);
        // 过滤script标签
		htmlStr = mScript.replaceAll("");

		Pattern pStyle = Pattern.compile(REG_EX_STYLE, Pattern.CASE_INSENSITIVE);
		Matcher mStyle = pStyle.matcher(htmlStr);
        // 过滤style标签
		htmlStr = mStyle.replaceAll("");

		Pattern pHtml = Pattern.compile(REG_EX_HTML, Pattern.CASE_INSENSITIVE);
		Matcher mHtml = pHtml.matcher(htmlStr);
        // 过滤html标签
		htmlStr = mHtml.replaceAll("");
		if (htmlStr.length() >= 1) {
			sb.append(htmlStr.trim());
		} else {
			sb.append(htmlStr);
		}
		String str = sb.toString();
		if (length != null && length > 0) {
			if (str.length() > length) {
				str = str.replaceAll("&nbsp;", "");
				str = str.substring(0, length);
				str = str + "...";
			}
		}
		return str;
	}

}
