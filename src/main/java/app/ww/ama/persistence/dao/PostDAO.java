package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.Post;
import app.ww.ama.persistence.dto.User;

public interface PostDAO {

	public Post findById(Integer id);

	public List<Post> findByUser(User user);

	public Integer save(Post post);

	public Post update(Post post);
}
