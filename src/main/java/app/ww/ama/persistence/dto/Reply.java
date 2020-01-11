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

	@Column(name = "replier_id")
	private String replierId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "replier_id", referencedColumnName = "user_id", updatable = false, insertable = false)
	private User replier;

	@Column(name = "post_id")
	private String parentPostId;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "post_id", referencedColumnName = "post_id", updatable = false, insertable = false)
	private Post parentPost;

	@Column(name = "reply_content")
	private String content;

	@Column(name = "reply_rating")
	private int rating;

	public Integer getReplyId() {
		return replyId;
	}

	public void setReplyId(Integer replyId) {
		this.replyId = replyId;
	}

	public String getReplierId() {
		return replierId;
	}

	public void setReplierId(String replierId) {
		this.replierId = replierId;
	}

	public User getReplier() {
		return replier;
	}

	public void setReplier(User replier) {
		this.replier = replier;
	}

	public String getParentPostId() {
		return parentPostId;
	}

	public void setParentPostId(String parentPostId) {
		this.parentPostId = parentPostId;
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
