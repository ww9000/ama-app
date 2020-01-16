package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.Reply;
import app.ww.ama.persistence.dto.User;

public interface ReplyDAO {

	public Reply findById(Integer id);

	public List<Reply> findByUser(User user);
	
	public List<Reply> findByParentPost(Post post);

	public Integer save(Reply reply);

	public Reply update(Reply reply);
	
	public void clearCache();
}
