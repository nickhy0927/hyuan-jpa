package com.iss.common.utils;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

@SuppressWarnings("serial")
public class BaseBlock extends TagSupport {

	/**
	 * 标签的名称
	 */
	private String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public int doStartTag() throws JspException {
		return getOverriedContent() == null ? 1 : 0;
	}

	public int doEndTag() throws JspException {
		String overriedContent = getOverriedContent();
		if (overriedContent == null) {
			return 6;
		}
		try {
			this.pageContext.getOut().write(overriedContent);
		} catch (IOException e) {
			throw new JspException("write overridedContent occer IOException,block name:" + this.name, e);
		}
		return 6;
	}

	private String getOverriedContent() {
		String varName = Utils.getOverrideVariableName(this.name);
		return (String) this.pageContext.getRequest().getAttribute(varName);
	}
}
