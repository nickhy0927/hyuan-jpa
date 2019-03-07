package com.baidu.ueditor;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.baidu.ueditor.ActionEnter;
import com.baidu.ueditor.ConfigManager;
import com.baidu.ueditor.define.ActionMap;
import com.baidu.ueditor.define.BaseState;
import com.baidu.ueditor.define.State;
import com.baidu.ueditor.hunter.FileManager;
import com.baidu.ueditor.hunter.ImageHunter;
import com.baidu.ueditor.upload.Uploader;

public class UeditorActionEnter extends ActionEnter {
	private String contextRootPath = "/home";
	
	private HttpServletRequest request = null;
	private String rootPath = null;
	private String contextPath = null;
	private String actionType = null;
	private ConfigManager configManager = null;

	public UeditorActionEnter(HttpServletRequest request, String rootPath) {
		super(request, rootPath);
		this.request = request;
		this.rootPath = rootPath;
		this.actionType = request.getParameter("action");
		this.contextPath = request.getContextPath();
		this.configManager = ConfigManager.getInstance(this.rootPath, this.contextPath, request.getRequestURI());
	}

	@Override
	public String invoke() {
		if (this.actionType != null && ActionMap.mapping.containsKey(this.actionType)) {
			if (this.configManager != null && this.configManager.valid()) {
				State state = null;
				int actionCode = ActionMap.getType(this.actionType);
				Map<String, Object> conf = null;
				switch (actionCode) {
				case 0:
					return this.configManager.getAllConfig().toString();
				case 1:
				case 2:
				case 3:
				case 4:
					conf = this.configManager.getConfig(actionCode);
					// 注意再这里修改rootPath和savePath，上传的实际路径为rootPath＋savePath
					conf.put("rootPath", contextRootPath);
					conf.put("savePath", "/upload" + conf.get("savePath"));
					state = (new Uploader(this.request, conf)).doExec();
					break;
				case 5:
					conf = this.configManager.getConfig(actionCode);
					// 注意再这里修改rootPath和savePath，上传的实际路径为rootPath＋savePath
					conf.put("rootPath", contextRootPath);
					conf.put("savePath", conf.get("savePath"));
					String[] list = this.request.getParameterValues((String) conf.get("fieldName"));
					state = (new ImageHunter(conf)).capture(list);
					break;
				case 6:
				case 7:
					conf = this.configManager.getConfig(actionCode);
					// 注意再这里修改rootPath和savePath，上传的实际路径为rootPath＋savePath
					conf.put("rootPath", contextRootPath);
					conf.put("savePath", conf.get("savePath"));
					int start = this.getStartIndex();
					state = (new FileManager(conf)).listFile(start);
				}
				if (state != null) {
					return state.toJSONString();
				} return null;
			} else {
				return (new BaseState(false, 102)).toJSONString();
			}
		} else {
			return (new BaseState(false, 101)).toJSONString();
		}
	}
}
