package app.ww.ama.persistence.dao;

import java.util.List;

import app.ww.ama.persistence.dto.Reply;

public interface ReplyDAO {

	public Reply findById(Integer id);

	public List<Reply> findByUserId(String userId);

	public Integer save(Reply post);

	public Reply update(Reply post);
}
