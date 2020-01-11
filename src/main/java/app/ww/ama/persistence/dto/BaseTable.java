package app.ww.ama.persistence.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class BaseTable {

	@Column(name = "deleted")
	private boolean isDeleted;

//	@CreationTimestamp
	@Column(name = "creation_timestamp")
	private Date creationTimestamp;

//	@UpdateTimestamp
	@Column(name = "update_timestamp")
	private Date updateTimestamp;

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatedTimestamp() {
		return creationTimestamp;
	}

	public void setCreatedTimestamp(Date createdTimestamp) {
		this.creationTimestamp = createdTimestamp;
	}

	public Date getUpdatedTimestamp() {
		return updateTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updateTimestamp = updatedTimestamp;
	}
}
