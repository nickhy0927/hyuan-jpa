package com.iss.common.utils;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;
import javax.persistence.Version;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@SuppressWarnings("serial")
@Access(AccessType.PROPERTY)
public class IdEntity extends PageSupport implements Serializable {
	protected Date createTime = new Date();
	protected String id;// ID
	protected Boolean status; // 有效状态 true 有效 false 无效
	protected String statusName;

	protected Date updateTime;
	
	/**
	 * 版本号
	 */
	protected int version = 0;

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		IdEntity other = (IdEntity) obj;
		if (getId() == null) {
			return false;
		}
		return getId().equals(other.getId());
	}

	@Column(insertable = true, columnDefinition = "datetime comment '创建时间'")
	public Date getCreateTime() {
		return createTime;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	@Column(columnDefinition = "varchar(64) comment '主键ID'")
	public String getId() {
		return id;
	}

	@Column(columnDefinition = "bit(1) comment '是否删除：0 否 1 是'")
	public Boolean getStatus() {
		String statusName = SysContants.IsDelete.getIsDeleteName(status);
		setStatusName(statusName);
		return status;
	}

	@Transient
	public String getStatusName() {
		return statusName;
	}

	@Column(updatable = true, columnDefinition = "datetime comment '修改时间'")
	public Date getUpdateTime() {
		return updateTime;
	}

	@Version
	@Column(name = "version",columnDefinition = "int(11) default 0  comment '版本号,防重' ")
	public int getVersion() {
		return version;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
	public void setVersion(int version) {
		this.version = version;
	}
}