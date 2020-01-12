package app.ww.ama.persistence.dto;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

@MappedSuperclass
public class BaseTable {

	@Column(name = "deleted")
	private boolean isDeleted;

	@Column(name = "creation_timestamp", updatable = false)
	@CreationTimestamp
	private Date creationTimestamp;

	@Column(name = "update_timestamp")
	@UpdateTimestamp
	private Date updateTimestamp;

	public boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreationTimestamp() {
		return creationTimestamp;
	}

	public void setCreationTimestamp(Date creationTimestamp) {
		this.creationTimestamp = creationTimestamp;
	}

	public Date getUpdatedTimestamp() {
		return updateTimestamp;
	}

	public void setUpdatedTimestamp(Date updatedTimestamp) {
		this.updateTimestamp = updatedTimestamp;
	}
}
