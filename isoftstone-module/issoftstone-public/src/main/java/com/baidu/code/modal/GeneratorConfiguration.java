package com.baidu.code.modal;

public class GeneratorConfiguration {

	private String pkg;

	private String clazz;

	private String pathSuffix;

	private String srcPath;

	private String entityName;

	private String pagePath;
	
	public String getPagePath() {
		return pagePath;
	}

	public void setPagePath(String pagePath) {
		this.pagePath = pagePath;
	}

	private String desc;

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getEntityName() {
		return entityName;
	}

	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}

	public String getSrcPath() {
		return srcPath;
	}

	public void setSrcPath(String srcPath) {
		this.srcPath = srcPath;
	}

	public String getPkg() {
		return pkg;
	}

	public void setPkg(String pkg) {
		this.pkg = pkg;
	}

	public String getClazz() {
		return clazz;
	}

	public void setClazz(String clazz) {
		this.clazz = clazz;
	}

	public String getPathSuffix() {
		return pathSuffix;
	}

	public void setPathSuffix(String pathSuffix) {
		this.pathSuffix = pathSuffix;
	}
}
