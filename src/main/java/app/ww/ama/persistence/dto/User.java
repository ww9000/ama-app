package app.ww.ama.persistence.dto;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "USERS")
public class User extends BaseTable implements Serializable {

	/**
	 *  Generated serial UID to identify class version
	 */
	private static final long serialVersionUID = 3576172450868466165L;

	@Id
	@Column(name = "user_id")
	private String userId;

	@Column(name = "user_name")
	private String userName;

	@Transient
	@OneToMany(mappedBy = "posterId", fetch = FetchType.LAZY)
	private Set<Post> posts;

	@Transient
	@OneToMany(mappedBy = "replierId", fetch = FetchType.LAZY)
	private Set<Reply> replyPosts;

	public User() {
	}

	public User(String id, String name) {
		this.userId = id;
		this.userName = name;
	}

	public String getId() {
		return userId;
	}

	public void setId(String id) {
		this.userId = id;
	}

	public String getName() {
		return userName;
	}

	public void setName(String name) {
		this.userName = name;
	}

	public Set<Post> getPosts() {
		return this.posts;
	}
}
