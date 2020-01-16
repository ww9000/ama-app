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
@Table(name = "REPLIES")
public class Reply extends BaseTable implements Serializable {

	/**
	 *  Generated serial UID to identify class version
	 */
	private static final long serialVersionUID = 6623434228904787237L;

	@Id
	@Column(name = "reply_id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer replyId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "replier_id", referencedColumnName = "user_id", nullable = false, updatable = false)
	private User replier;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id", referencedColumnName = "post_id", nullable = false, updatable = false)
	private Post parentPost;

	@Column(name = "reply_content", nullable = false)
	private String content;

	@Column(name = "reply_rating", nullable = false)
	private int rating;

	public Reply() {}
	
	public Reply(User replier, Post parentPost, String content) {
		this.replier = replier;
		this.parentPost = parentPost;
		this.content = content;
		this.rating = 0;
	}
	
	public Integer getId() {
		return replyId;
	}

	public void setId(Integer replyId) {
		this.replyId = replyId;
	}

	public String getReplierId() {
		return getReplier().getId();
	}

	public User getReplier() {
		return replier;
	}

	public void setReplier(User replier) {
		this.replier = replier;
	}

	public Integer getParentPostId() {
		return getParentPost().getId();
	}

	public Post getParentPost() {
		return parentPost;
	}

	public void setParentPost(Post parentPost) {
		this.parentPost = parentPost;
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
