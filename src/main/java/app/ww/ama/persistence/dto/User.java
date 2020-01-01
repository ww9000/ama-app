package app.ww.ama.persistence.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "USERS")
public class User {
	
	private String id;
	
	@Id
	@Column(name = "user_id")
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
}
