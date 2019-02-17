package com.iss.platform.system.database.entity;

import javax.persistence.Table;
import javax.persistence.Column;
import javax.persistence.Entity;

import com.iss.common.utils.IdEntity;

/**
 * 数据源管理
 * @author Mr's Huang
 */
@Entity
@Table(name = "t_p_s_database")
public class DataBase extends IdEntity {
	private static final long serialVersionUID = 4715920146630379341L;

	/**
	 * 数据库字符集
	 */
	private String characterEncoding;
	/**
	 * 数据库名称
	 */
	private String dataBaseName;
	
	/**
	 * 数据库驱动名称
	 */
	private String driverClassName;
	/**
	 * 数据库IP
	 */
	private String ip;
	
	/**
	 * 数据库端口
	 */
	private Integer port;
	
	/**
	 * 是否使用unicode
	 */
	private Boolean useUnicode;
	
	/**
	 * 数据库连接
	 */
	private Integer dataBaseType;
	
	/**
	 * 数据库用户名
	 */
	private String username;
	
	/**
	 * 数据库密码
	 */
	private String password;
	
	/**
	 * 是否启用 0 停用 1 启用
	 */
	private Boolean enable;
	
	@Column(columnDefinition = "bit(1) comment '是否启用 0 停用 1 启用'")
	public Boolean getEnable() {
		return enable;
	}

	public void setEnable(Boolean enable) {
		this.enable = enable;
	}

	@Column(columnDefinition = "varchar(10) comment '数据库字符集'")
	public String getCharacterEncoding() {
		return characterEncoding;
	}
	
	@Column(columnDefinition = "varchar(100) comment '数据库名称'")
	public String getDataBaseName() {
		return dataBaseName;
	}

	@Column(columnDefinition = "varchar(256) comment '数据库驱动'")
	public String getDriverClassName() {
		return driverClassName;
	}

	@Column(columnDefinition = "varchar(64) comment '数据库IP'")
	public String getIp() {
		return ip;
	}
	
	@Column(columnDefinition = "int(4) comment '数据库类型'")
	public Integer getDataBaseType() {
		return dataBaseType;
	}
	
	public void setDataBaseType(Integer dataBaseType) {
		this.dataBaseType = dataBaseType;
	}
	
	@Column(columnDefinition = "int(5) comment '数据库端口'")
	public Integer getPort() {
		return port;
	}

	@Column(columnDefinition = "bit(1) comment '是否使用unicode true 是 1 false'")
	public Boolean getUseUnicode() {
		return useUnicode;
	}

	public void setCharacterEncoding(String characterEncoding) {
		this.characterEncoding = characterEncoding;
	}

	public void setDataBaseName(String dataBaseName) {
		this.dataBaseName = dataBaseName;
	}

	public void setDriverClassName(String driverClassName) {
		this.driverClassName = driverClassName;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	public void setUseUnicode(Boolean useUnicode) {
		this.useUnicode = useUnicode;
	}

	@Column(columnDefinition = "varchar(64) comment '数据库用户名'")
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Column(columnDefinition = "varchar(64) comment '数据库密码'")
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
}
