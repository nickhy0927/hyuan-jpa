package com.iss.common.utils;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@MappedSuperclass
@Access(AccessType.PROPERTY)
public class IdEntity {
	protected String id;// ID
	protected Date createTime = new Date();
	protected Date updateTime;
	protected Boolean status; // 有效状态 true 有效 false 无效

	protected String statusName;

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

	@Column(updatable = false)
	public Date getCreateTime() {
		return createTime;
	}

	@Id
	@GeneratedValue(generator = "system-uuid")
	@GenericGenerator(name = "system-uuid", strategy = "uuid2")
	public String getId() {
		return id;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	@Transient
	public String getStatusName() {
		return statusName;
	}
	
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public void setStatus(Boolean status) {
		this.status = status;
	}

	public Boolean getStatus() {
		String statusName = SysContants.IsDelete.getIsDeleteName(status);
		setStatusName(statusName);
		return status;
	}
}