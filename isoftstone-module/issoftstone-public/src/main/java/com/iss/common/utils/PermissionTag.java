package com.iss.common.utils;

import java.util.Map;
import java.util.Set;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;
import javax.servlet.jsp.tagext.TagSupport;

import org.apache.commons.lang3.StringUtils;

import com.iss.common.config.InitEnvironment;

@SuppressWarnings("serial")
public class PermissionTag extends BodyTagSupport {

	private String alias;

	public void setAlias(String alias) {
		this.alias = alias;
	}

	@Override
	public int doStartTag() throws JspException {
		boolean result = false;
		// 用户登录时，取出所属的按钮id，存放在session中
		String loginName = InitEnvironment.getCurrentLoginName();
		Map<String, String> operationCodes = InitEnvironment.getPermissionSet(loginName);
		Set<String> keys = operationCodes.keySet();
		for (String key : keys) {
			String string = operationCodes.get(key);
			if (StringUtils.equals(alias, string)) {
				result = true;
				break;
			}
		}
		return result ? TagSupport.EVAL_BODY_INCLUDE : SKIP_BODY;// 真：返回EVAL_BODY_INCLUDE（执行标签）；假：返回SKIP_BODY（跳过标签不执行）
	}
}
