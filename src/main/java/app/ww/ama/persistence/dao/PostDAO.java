package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.Post;

public interface PostDAO {

	public Post findById(Integer id);

	public List<Post> findByUserId(String userId);

	public Integer save(Post post);

	public Post update(Post post);
}
