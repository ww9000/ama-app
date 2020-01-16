package app.ww.ama.persistence.dto;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "POSTS")
public class Post extends BaseTable implements Serializable {

	/**
	 *  Generated serial UID to identify class version
	 */
	private static final long serialVersionUID = -3440023519767173851L;

	@Id
	@Column(name = "post_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer postId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "poster_id", referencedColumnName = "user_id", nullable = false)
	private User poster;
	
	@Column(name = "post_title", nullable = false)
	private String title;
	
	@Column(name = "post_content", nullable = false)
	private String content;

	@Column(name = "post_rating", nullable = false)
	private int rating;

	public Post() {}
	
	public Post(String title, String content) {
		this.title = title;
		this.content = content;
	}
	
	public Post(String title, String content, User poster) {
		this(title, content);
		this.poster = poster;
	}
	
	public Integer getId() {
		return postId;
	}

	public void setId(Integer postId) {
		this.postId = postId;
	}

	public String getPosterId() {
		return this.getPoster().getId();
	}
	
	public User getPoster() {
		return this.poster;
	}
	
	public void setPoster(User user) {
		this.poster = user;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setTitle(String title) {
		this.title = title;
	}
	
	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public Integer getRating() {
		return rating;
	}

	public void setRating(Integer rating) {
		this.rating = rating;
	}
}
