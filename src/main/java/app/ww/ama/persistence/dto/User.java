package app.ww.ama.persistence.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	private List<Post> posts = new ArrayList<>();

	@Transient
	@OneToMany(mappedBy = "replierId", fetch = FetchType.LAZY)
	private List<Reply> replyPosts = new ArrayList<>();

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

	public List<Post> getPosts() {
		return this.posts;
	}
	
	public void addPost(Post post) {
		this.posts.add(post);
	}
}
