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

	@Column(name = "poster_id")
	private String posterId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "poster_id", referencedColumnName = "user_id", updatable = false, insertable = false)
	private User poster;
	
	@Column(name = "post_content")
	private String content;

	@Column(name = "post_rating")
	private int rating;

	public Integer getPostId() {
		return postId;
	}

	public void setPostId(Integer postId) {
		this.postId = postId;
	}

	public String getPosterId() {
		return this.poster.getId();
	}

	public void setPosterId(String posterId) {
		this.posterId = posterId;
	}
	
	public User getPoster() {
		return this.poster;
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
